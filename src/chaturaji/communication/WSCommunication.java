package chaturaji.communication;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.json.JSONException;
import org.json.JSONObject;

import chaturaji.game.GameServer;
import chaturaji.game.Player;

public class WSCommunication extends MessageInbound implements Protocol {

	private GameServer server;
	private Player player;

	public WSCommunication(GameServer server) {
		if (server == null)
			System.out.println("Probleme serveur");
		this.server = server;
	}

	@Override
	public synchronized void send(JSONObject msg) {
		try {
			getWsOutbound().writeTextMessage(CharBuffer.wrap(msg.toString()));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
		// Not implemented
	}

	@Override
	protected void onTextMessage(CharBuffer msg) throws IOException {
		try {
			server.onMessage(new JSONObject(msg.toString()));
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onOpen(WsOutbound out) {
		player = server.createPlayer(this);
	}

	@Override
	protected void onClose(int status) {
		server.deletePlayer(player);
	}

}
