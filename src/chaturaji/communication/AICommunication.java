package chaturaji.communication;

import java.net.URI;
import java.util.ArrayList;

import org.eclipse.jetty.util.ajax.JSON;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import chaturaji.game.Color;

import com.Chess.engine.ChaturajiEngine;
import com.Chess.engine.Engine;
import com.Chess.position.Position;
import com.Chess.position.PositionImpl;

public class AICommunication implements WebSocket.OnTextMessage {
	
	private Connection connection;
	
	private Engine engine;
	
	private Color color;
	
	private static WebSocketClientFactory factory;
	
	private boolean started;
	
	public AICommunication(URI uri, Engine engine) {
		factory = new WebSocketClientFactory();
		try {
			factory.start();
			WebSocketClient client = factory.newWebSocketClient();
			client.open(uri, this);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		this.engine = engine;
		started = false;
	}
	
	@Override
	public void onClose(int arg0, String arg1) {
		// Nothing to do
	}

	@Override
	public void onOpen(Connection arg0) {
		connection = arg0;
	}

	@Override
	public void onMessage(String msg) {
		JSONObject message = null;
		System.out.println(msg);
		try {
			message =  new JSONObject(msg);
			JSONObject data = (JSONObject)message.get("data");
			switch ((Integer)message.get("type")) {
			case 50:
				color = Color.getColorFromInt(Integer.parseInt( (String)((JSONObject)message.get("data")).getString("Id") ));
				System.out.println("From server, I get: " + color);
				break;
			case 10:
				engine.startGame(color);
				System.out.println("I start the game with: " + color);
				started = true;
				break;
			case 2:
				String a =(String)data.get("from_sq");
				String b = (String)data.get("to_sq");

				char cs = a.charAt(0);
				char cd = b.charAt(0);
				int numbers = a.charAt(1);
				int numberd = b.charAt(1);
				int source = (8-numbers)*8+cs-'A';
				int destination = (8-numberd)*8+cd-'A';
				Position ps = new PositionImpl(source);
				Position pd = new PositionImpl(destination);
				engine.updateBoard(ps, pd);
				break;
			case 5:
				int out = data.getInt("player");
				engine.playerOut(Color.getColorFromInt(out));
				break;
			case 11:
				JSONArray JsonBoats = data.getJSONArray("boats");
				ArrayList<String> boats = new ArrayList<String>();
				for (int i = 0; i < JsonBoats.length(); ++i) {
					boats.add(JsonBoats.getJSONArray(i).toString());
				}
				engine.boatTriumph(boats);
				break;
			default:
				break;
			}
			if (engine.myTurn() && started) {
				String move = engine.generatePly();
				connection.sendMessage(Message.submitMove(move).toString());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String [] args) throws Exception {
		new AICommunication(new URI("ws://batch1.doc.ic.ac.uk:8080/Chaturaji/WSHandler"), new ChaturajiEngine());
	}

}
