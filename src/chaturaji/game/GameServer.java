package chaturaji.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONException;
import org.json.JSONObject;

import com.Chess.bitBoard.BitBoardImpl;
import com.Chess.engine.Engine;
import com.Chess.piece.Piece;

import chaturaji.communication.Message;
import chaturaji.communication.Protocol;
import chaturaji.util.HibernateUtil;
import static chaturaji.communication.Message.*;

@Entity
@Table(name="Games")
public class GameServer implements Serializable {

	/**
	 * @author: Jeremy RIVIERE
	 * This class holds information about players in one game and
	 * is responsible for creating the game, checking the rules and
	 * allowing communication between the players
	 * 
	 */
    
	// The name of the game server
	@Column(nullable = false, length=15, unique=true)
	@Id
	private String name;
	
	// List of players playing on the server
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Player> persist_players;
    
    transient
	private List<Player> players;
	
	// List of players out of the game
    transient
	private List<Player> out;
	
	// Gives status of the game
	private boolean playing;
	
	// Sudden death
    transient
	private boolean suddenDeath;
	
	private boolean boatTriumph;
	
	transient
	private static int remainningRounds = 10;
	
	// Player currently playing
	private Player currentPlayer;
	
	transient
	private Engine checkEngine;
	
	// Increment used for default name
	transient
	private static Integer nameIncr = 0;
	
	/** Flag used to specify whether we are testing of not.
	 * If we are in tests, we ignore db modification.
	 */
	transient
	public boolean test;
	
	// Number of AI on the game
	private Integer AInumber;
	
	/**
	 * Class constructor
	 */
	public GameServer() {
		players = new ArrayList<Player>();
		out = new ArrayList<Player>();
		playing = false;
		suddenDeath = false;
		boatTriumph = false;
		currentPlayer = null;
		name = "Default" + nameIncr++;
		checkEngine = new ChaturajiEngine(new BitBoardImpl());
		AInumber = 0;
		test = false;
	}
	
	public GameServer(String name, Integer aInumber) {
		this(name);
		AInumber = aInumber;
	}
	
	public Integer getAINumber() {
		return AInumber;
	}

	public GameServer(String name) {
		this();
		this.name = (name.equals("") ? this.name : name);
	}
	
	/**
	 * This method is responsible for creating a new player for 
	 * one instance of the game server
	 * @param p Protocol to be used by the user the server to communicate
	 * with the player
	 * @return Returns the player created is successful, null otherwise
	 */
	public Player createPlayer(Protocol p) {
		if (players.size() < 4) {
			/* Attach the communication medium to this server,
			 * so that the server can be notified when a player sends
			 * a message
			 */
			Player player = new Player(this, p, getColor());
			player.send(Message.newPlayer(player.getColor().getTurn()));
			players.add(player);
			if (players.size() == 4) {
				/*
				 * As soon as four players have joined the game, the party is
				 * started, with the current player being the one who has the red 
				 * set of pieces
				 */
				playing = true;
				currentPlayer = players.get(0);
				sendToAll(startGame(players));
				persist_players = new ArrayList<Player>(players);
				persist(this);
			}
			return player;
		}
		return null;
	}
	
	/**
	 * Gives a unique color to each newly created player
	 */
	private Color getColor() {
		Color color = null;
		for (Color c : Color.values()) {
			if (c.getTurn() == players.size()) {
				color = c;
			}
		}
		return color;
	}

	/**
	 * Delete a player from the game
	 * @param p Player to be deleted
	 * @return Whether the player was successfully deleted.
	 * A delete may fail if the player does not belong to this game
	 */
	public boolean deletePlayer(Player p) {
		boolean ret = players.remove(p);
		if (players.size() == 0) {
			playing = false;
			update(this);
			GameFactory.getInstance().deleteGame(name);
		}
		return ret;
	}
	
	/**
	 * Gives the status of the game
	 * @return True if the game has started, false otherwise
	 */
	public boolean isPlaying() {
		return playing;
	}
	
	/**
	 * 
	 * @return Number of players currently in the game
	 */
	public int numberOfPlayers() {
		return players.size();
	}

	/**
	 * 
	 * @return The player who's currently playing
	 */
	public Player currentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * Function called by the protocol handler to notify the server
	 * of an incoming message
	 * @param message Message sent by the player
	 * @throws JSONException
	 */
	public void onMessage(JSONObject message) throws JSONException {
		JSONObject data = (JSONObject)message.get("data");
		Integer type = (Integer)message.getInt("type");
		switch (type) {
		case 1:
			if (validMove(data)) {
				sendToAll(Message.validMove((String)data.get("from_sq"), (String)data.get("to_sq")));
				Piece taken = ((ChaturajiEngine)checkEngine).getLastTaken();
				if (taken != null) {
					currentPlayer.addPoints(taken.getPoints());
					if (taken.getType() == Type.KING) {
						Player pout = this.players.get(taken.getColor().getTurn());
						out.add(pout);
						sendToAll(Message.playerOut(pout.getColor().getTurn()));
					}
					int p1 = players.get(0).getPoints();
					int p2 = players.get(1).getPoints();
					int p3 = players.get(2).getPoints();
					int p4 = players.get(3).getPoints();
					sendToAll(Message.updateScore(p1, p2, p3, p4));
					if (suddenDeath) {
						remainningRounds--;
					}
				}
				
				if (!boatTriumph && ((ChaturajiEngine)checkEngine).boatTriumph() != null) {
					boatTriumph = true;
					currentPlayer.addPoints(3 * Type.BOAT.getType());
					String tBoat = ((ChaturajiEngine)checkEngine).boats().get(3);
					ArrayList<String> boats = new ArrayList<String>();
					for (int i = 0; i < 3; ++i) {
						boats.add(((ChaturajiEngine)checkEngine).boats().get(i));
					}
					sendToAll(Message.boatTriumph(tBoat, boats));
					int p1 = players.get(0).getPoints();
					int p2 = players.get(1).getPoints();
					int p3 = players.get(2).getPoints();
					int p4 = players.get(3).getPoints();
					sendToAll(Message.updateScore(p1, p2, p3, p4));
				}
				
				if (!suddenDeath && out.size() == 2) {
					sendToAll(Message.suddenDeath());
					suddenDeath = true;
				}
				
				if (suddenDeath && remainningRounds == 0) {
					ArrayList<Player> p_end = new ArrayList<Player>();
					for (Player p : players) {
						if (!out.contains(p)) {
							p_end.add(p);
						}
					}
					sendToAll(Message.endGame("draw", p_end));
					System.out.println(Message.endGame("draw", p_end));
					out = players;
				}
				
				if (out.size() == 3) {
					ArrayList<Player> p_end = new ArrayList<Player>();
					for (Player p : players) {
						/* Special rule:
						 * If a player has taken all three kings, he is granted
						 * 57 more points
						 */
						if (p.getNbKings() == 3) {
							p.addPoints(57);
							int p1 = players.get(0).getPoints();
							int p2 = players.get(1).getPoints();
							int p3 = players.get(2).getPoints();
							int p4 = players.get(3).getPoints();
							sendToAll(Message.updateScore(p1, p2, p3, p4));
						}
						
						if (p_end.isEmpty() || p_end.get(0).getPoints() < p.getPoints()) {
							p_end.add(0, p);
						}
					}
					for (Player p : players) {
						if (p_end.get(0) != p && p_end.get(0).getPoints() == p.getPoints()) {
							p_end.add(p);
						}
					}
					sendToAll(Message.endGame(p_end.size() > 1 ? "draw" : "win", p_end));
					System.out.println(Message.endGame(p_end.size() > 1 ? "draw" : "win", p_end));
				}
				update(currentPlayer);
				do {
					currentPlayer = players.get((currentPlayer.getColor().getTurn() + 1) % 4);
				} while (out.contains(currentPlayer));
				System.out.println(currentPlayer.getColor());
			}
			else {
				System.out.println("Wrong move: " + currentPlayer.getColor() + " " + message.toString());
				//currentPlayer.send(message) Should we send an error msg back?
			}	
			break;

		case 51:
			sendToAll(message);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Function called to notify all the players of a new message
	 * @param message Message to send to the players
	 */
	private void sendToAll(JSONObject message) {
		for (Player p : players) {
			p.send(message);
		}
	}

	/**
	 * This function implements the game's logic and is responsible
	 * for checking that the players are not cheating
	 * @param move The move made by the current player
	 * @return Returns whether the move is legal or not
	 */
	private boolean validMove(JSONObject move) {
		return ((ChaturajiEngine)checkEngine).checkMove(currentPlayer.getColor(), move);
	}
	
	/**
	 * Retrieves information about a player according to the color
	 * of his set of pieces
	 * @param color The color associated to the player we want to retrieve
	 * @return The player who's set of pieces is of the specified color
	 */
	public Player getPlayer(Color color) {
		for (Player p : players) {
			if (p.getColor() == color)
				return p;
		}
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	private void persist(Object o) {
		if (!test) {
			try {
				Session session = HibernateUtil.getSessionFactory().openSession();
				Transaction t = session.beginTransaction();
				session.save(o);
				t.commit();
				session.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void update(Object o) {
		if (!test) {
			try {
				Session session = HibernateUtil.getSessionFactory().openSession();
				Transaction t = session.beginTransaction();
				session.update(o);
				t.commit();
				session.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
