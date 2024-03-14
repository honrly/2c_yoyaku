import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import usedb.DbSelectRoom;
import usedb.DbSelectAvailability;

public class Reserve extends HttpServlet {


	public void doPost(HttpServletRequest request,
            HttpServletResponse response)
	throws IOException, ServletException
	{
		String user_id = request.getParameter("USER");
		String password = request.getParameter("PASSWORD");
		
		DbSelectRoom dsr = new DbSelectRoom();
		List<String[]> rooms = dsr.Select();
		request.setAttribute("Rooms", rooms);

		String setDate = request.getParameter("DATE");
        // 初回アクセス時にはsetDateがnullならば今日の日付を設定
        if (setDate == null) {
            java.util.Date today = new java.util.Date();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            setDate = sdf.format(today);
        }
        request.setAttribute("InitialDate", setDate);
		
        DbSelectAvailability dsa = new DbSelectAvailability();
		String[][] available = dsa.Select(setDate);
		request.setAttribute("Available", available);
		
		request.setAttribute("User_id", user_id);
		request.setAttribute("Password", password);

		
		//JSPのURL
		String url="/reserve.jsp";
		RequestDispatcher dispatcher
					=getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}
}