package chaturaji.game;

import java.util.Collections;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Jeremy RIVIERE
 * This class is used to create games. One can either create a server or request
 * to join a pending game.
 *
 */

public class GameFactory {

	// Contains the games waiting for players to be launched.
	private Map<String, GameServer> pending;
	
	// Holds the games already started.
	private Map<String, GameServer> created;
	
	// Used for singleton pattern
	private static GameFactory instance;
	
	/**
	 * Singleton pattern
	 */
	private GameFactory() {
		pending = Collections.synchronizedMap(new HashMap<String, GameServer>());
		created = Collections.synchronizedMap(new HashMap<String, GameServer>());
	}
	
	/**
	 * Singleton pattern
	 * @return The unique instance of GameFactory
	 */
	public static GameFactory getInstance() {
		if (instance == null)
			instance = new GameFactory();
		return instance;
	}
	
	/**
	 * Creates a new game and adds it to the list of pending games
	 * @param name - The name of the new Server
	 * @return A new game server
	 */
	public GameServer createGame(String name) {
		GameServer server = new GameServer(name);
		pending.put(name, server);
		return server;
	}
	
	/**
	 * Finds a game in the list of pending ones or creates one if none exist
	 * @return A game server where clients can connect
	 */
	public GameServer findGame(String name) {
		GameServer server = pending.get(name);
		// If no server exists, then we create one.
		if (server == null) {
			server = createGame("");
		}
		// If the server is full, we change it from pending to playing.
		if (server.numberOfPlayers() == 3) {
			server = pending.remove(name);
			// We change the name of the server if it already exists.
			if (created.containsKey(server.getName())) {
				server.setName(server.getName() + server.hashCode());
			}
			created.put(server.getName(), server);
		}
		return server;
	}
	
	public void deleteGame(String name) {
		created.remove(name);
	}
	
	public Map<String, GameServer> pendingGames() {
		return pending;
	}
	
	public Map<String, GameServer> createdGames() {
		return created;
	}
	
}
