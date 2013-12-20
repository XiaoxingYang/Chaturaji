package chaturaji.servlet;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chaturaji.game.GameFactory;
import chaturaji.game.GameServer;

/**
 * Servlet implementation class GameList
 */
@WebServlet("/GameList")
public class GameList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GameList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		String data = "";
		GameFactory games=GameFactory.getInstance();
		Map<String, GameServer> pending=games.pendingGames();
		Iterator<GameServer> serversIterator=pending.values().iterator();
		data=data+pending.size()+" ";
		while (serversIterator.hasNext()) {
		    GameServer gameServer = serversIterator.next();
		    data=data+gameServer.getName()+" "+gameServer.numberOfPlayers()
		    		+ " " + "Derpiderp" + " "+gameServer.getAINumber()+" ";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		System.out.println(data);
		response.getWriter().write(data);
		response.getWriter().flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
