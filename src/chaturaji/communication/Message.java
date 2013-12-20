package chaturaji.communication;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import chaturaji.game.Player;

/**
 * 
 * @author Jeremy RIVIERE
 * This class is responsible for creating the JSON objects
 * we are using to communicate with the clients.
 *
 */

public class Message {

	public static JSONObject validMove(String from, String to) {
		JSONObject data = new JSONObject();
		try {
			data.put("to_sq", to);
			data.put("from_sq", from);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return message(2, data);
	}
	
	public static JSONObject updateScore(int p1, int p2, int p3, int p4) {
		JSONObject data = new JSONObject();
		try {
			data.put("p4", p4);
			data.put("p3", p3);
			data.put("p2", p2);
			data.put("p1", p1);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return message(4, data);
	}
	
	public static JSONObject playerOut(int player) {
		JSONObject data = new JSONObject();
		try {
			data.put("player", player);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return message(5, data);
	}
	
	public static JSONObject endGame(String outcome, List<Player> players) {
		JSONObject data = new JSONObject();
		try {
			JSONArray buf = new JSONArray();
			for (Player p : players) {
				buf.put(p.getColor().getTurn() + 1);
			}
			data.put("player", buf);
			data.put("outcome", outcome);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return message(6, data);
	}
	
	public static JSONObject suddenDeath() {
		JSONObject data = new JSONObject();
		try {
			data.put("moveLimit", 10);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return message(7, data);
	}
	
	public static JSONObject pawnPromotion(String sq, String piece) {
		JSONObject data = new JSONObject();
		try {
			data.put("sq", sq);
			data.put("pieceType", piece);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return message(8, data);
	}
	
	public static JSONObject startGame(List<Player> players) {
		JSONObject data = new JSONObject();
		try {
			for (int i = 0; i < players.size(); ++i) {
				data.put("p" + (i + 1), players.get(i).getColor().getTurn());
			}
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return message(10, data);
	}
	
	public static JSONObject boatTriumph(String tBoat, List<String> boats) {
		JSONObject data = new JSONObject();
		try {
			JSONArray triumph = new JSONArray();
			for (String b : boats) {
				triumph.put(b);
			}
			data.put("boats", triumph);
			data.put("tBoat", tBoat);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return message(11, data);
	}
	
	public static JSONObject newPlayer(int id) {
		JSONObject data = new JSONObject();
		try {
			data.put("Id", id);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return message(50, data);
	}
	
	public static JSONObject submitMove(String move) {
		JSONObject data = new JSONObject();
		try {
			data.put("from_sq", move.split("-")[0]);
			data.put("to_sq", move.split("-")[1]);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return message(1, data);
	}
	
	private static JSONObject message(int msg_type, JSONObject data) {
		JSONObject res = new JSONObject();
		try {
			res.put("type", msg_type);
			res.put("data", data);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return res;
	}
	
}
