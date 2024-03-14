import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import usedb.DbInsertReservation;
import usedb.DbInsertAvailability;

public class RegisterReservation extends HttpServlet {


	public void doPost(HttpServletRequest request,
            HttpServletResponse response)
	throws IOException, ServletException
	{
		String user_id = request.getParameter("USER");
		String password = request.getParameter("PASSWORD");
        request.setCharacterEncoding("UTF-8");

        // 予約情報の取得
        String reserve_name = request.getParameter("RESERVENAME");
		//String room_id = request.getParameter("SELECTEDROOM");
        int room_id_INT = Integer.parseInt(request.getParameter("SELECTEDROOM"));
        System.out.println(room_id_INT);
        int num_p = Integer.parseInt(request.getParameter("NUM_OF_PEOPLE"));
        String reason = request.getParameter("REASON");
        String remarks = request.getParameter("REMARKS");
        String reserve_day = request.getParameter("RESERVEDAY");
		String start_time = request.getParameter("STARTTIME");
        String end_time = request.getParameter("ENDTIME");
        request.setCharacterEncoding("UTF-8");

        System.out.println(reserve_day);
        System.out.println(start_time);
        System.out.println(end_time);

        DbInsertAvailability dia = new DbInsertAvailability();
        dia.Insert(room_id_INT, reserve_day, start_time, end_time);
        DbInsertReservation dir = new DbInsertReservation();
		dir.Insert(reserve_name, room_id_INT, user_id, num_p, reason, remarks, reserve_day, start_time, end_time); 
        
		
		request.setAttribute("User_id", user_id);
		request.setAttribute("Password", password);
		
		//JSPのURL
		String url="/registeredReservation.jsp";
		RequestDispatcher dispatcher
					=getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}
}