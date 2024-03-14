import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import usedb.DbDeleteReservation;
import usedb.DbSelectReservation;
import usedb.DbDeleteAvailability;

public class CancelReservation extends HttpServlet {


	public void doPost(HttpServletRequest request,
            HttpServletResponse response)
	throws IOException, ServletException
	{	
		String user_id = request.getParameter("USER");
		String password = request.getParameter("PASSWORD");
        String reserve_id = request.getParameter("RESERVEID");

        int room_id = Integer.parseInt(request.getParameter("ROOMID"));
        String start_time = request.getParameter("STARTTIME");

        DbDeleteReservation ddres = new DbDeleteReservation();
        ddres.Delete(reserve_id);

        DbDeleteAvailability dda = new DbDeleteAvailability();
        dda.Delete(room_id, start_time);

        DbSelectReservation dsres = new DbSelectReservation();
		List<String[]> reservations = dsres.Select();

		request.setAttribute("Reservations", reservations);
        request.setAttribute("User_id", user_id);
        request.setAttribute("Password", password);
        String url="/home.jsp";
        RequestDispatcher dispatcher
                    =getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);		
	}
}