package chaturaji.servlet;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import chaturaji.login.User;
import chaturaji.login.UserDb;
import chaturaji.login.UserDbItf;

/**
 * Servlet implementation class UserHandler
 */
@WebServlet("/UserHandler")
public class UserHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDbItf psql;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserHandler() {
        super();
        psql = new UserDb();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		
		if (type.equals("login")) {
			if (psql.checkLoginInfo(new User(login, password))) {
				System.out.println("derp");
				HttpSession session = request.getSession(true);
				session.setAttribute("login", login);
			}
		}
		else {
			System.out.println(psql.addUser(new User(login, password)));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
