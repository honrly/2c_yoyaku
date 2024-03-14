import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import usedb.DbSelectReservation;
import usedb.DbSelectRegisteredUser;

public class Home extends HttpServlet {


	public void doPost(HttpServletRequest request,
            HttpServletResponse response)
	throws IOException, ServletException
	{
		request.setAttribute("ErrorMessage", null);
		
		String user_id = request.getParameter("USER");
		String password = request.getParameter("PASSWORD");

		DbSelectRegisteredUser dbsru = new DbSelectRegisteredUser();
		Boolean registered = dbsru.Select(user_id, password);

        if (registered) {
            // ログイン成功
			DbSelectReservation dsres = new DbSelectReservation();
			List<String[]> reservations = dsres.Select();

			request.setAttribute("Reservations", reservations);
			request.setAttribute("User_id", user_id);
			request.setAttribute("Password", password);
			String url="/home.jsp";
			RequestDispatcher dispatcher
						=getServletContext().getRequestDispatcher(url);
			dispatcher.forward(request, response);
        } else {
            // ログイン失敗
			String errorMessage = "ユーザーIDまたはパスワードが正しくありません。";
			request.setAttribute("ErrorMessage", errorMessage);
			
			String url = "/index.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
			dispatcher.forward(request, response);
        }
		
	}
}