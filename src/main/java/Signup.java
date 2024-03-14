import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import usedb.DbInsertUser;
import usedb.DbSelectUser;

public class Signup extends HttpServlet {


	public void doGet(HttpServletRequest request,
            HttpServletResponse response)
	throws IOException, ServletException
	{
		String user_id = request.getParameter("USER");
		String password = request.getParameter("PASSWORD");
		String reserver_name = request.getParameter("RESERVER_NAME");
		String tell = request.getParameter("TELL");

		DbInsertUser dib = new DbInsertUser();
		dib.Insert(user_id, password, reserver_name, tell);

		DbSelectUser dsu = new DbSelectUser();
		String[] user_info;
		user_info = dsu.Select(user_id);

		request.setAttribute("User_info", user_info);
		
		String url="/signup.jsp";
		RequestDispatcher dispatcher
					=getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);

	}
}