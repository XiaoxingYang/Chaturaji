package chaturaji.servlet;

import java.net.URI;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

import com.Chess.engine.ChaturajiEngine;

import chaturaji.communication.WSCommunication;
import chaturaji.communication.AICommunication;
import chaturaji.game.GameFactory;
import chaturaji.game.GameServer;
/**
 * Servlet implementation class Test
 */
@WebServlet("/WSHandler")
public class WSHandler extends WebSocketServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected StreamInbound createWebSocketInbound(String arg0, HttpServletRequest request) {
		HttpSession s = request.getSession();
		String gameId = request.getParameter("id");
		String name = request.getParameter("create");
		String nb_ais = request.getParameter("nb_ais");
		if (gameId != null) {
			synchronized(this) {
				return new WSCommunication(GameFactory.getInstance().findGame(gameId));
			}
		}
		else if (name != null && nb_ais != null) {
			GameServer server = GameFactory.getInstance().createGame(name);
			for (int i = 0; i < Integer.parseInt(nb_ais); ++i) {
				try {
					new AICommunication(new URI("ws://localhost:8080/Chaturaji/WSHandler?id=" + name), new ChaturajiEngine());
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			return new WSCommunication(GameFactory.getInstance().findGame(server.getName()));
		}
		return null;
	}
	
}
