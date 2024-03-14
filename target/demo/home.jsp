<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" pageEncoding="UTF-8">
    <title>Home</title>
    <style>
        h3 {
            color: darkgreen;
        }
        table {
            border-collapse: collapse;
            width: 95%;
            text-align: center;
        }

        th, td {
            border: 1px solid green;
            padding: 10px;
            min-width: 70px;
        }

        th {
            background-color: lightgreen;
            border: 2px solid darkgreen;
        }

        th:first-child{
            background-color: white;
            border: none;
        }

        td:first-child{
            border: none;
        }
        
        button {
            width: 10em;
            height: 3em;
        }

        .cancel {
            width: 5em;
            height: 3em;
        }
    </style>
    <script>
        function confirmCancellation(reservationId) {
            return confirm("予約ID " + reservationId + " を削除しますか？");
        }
    </script>
</head>
<body>
    <%
        List<String[]> Reservations = (List<String[]>) request.getAttribute("Reservations");
        String user_id = (String) request.getAttribute("User_id");
        String password = (String) request.getAttribute("Password");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
    %>
    
    <h2>ホーム</h2>
    <form name="ReservationForm" method="POST" action="Reserve">
        <input type="hidden" name="USER" value="<%= user_id %>">
        <input type="hidden" name="PASSWORD" value="<%= password %>">
        <a href="reserve.jsp"><button><strong>予約する</strong></button></a>
    </form>
    
    <h2>予約履歴</h2>
    <table>
        <tr>
            <th> </th>
            <th>予約ID</th>
            <th>予約名</th>
            <th>部屋ID</th>
            <th>人数</th>
            <th>理由</th>
            <th>備考</th>
            <th>受付日</th>
            <th>開始</th>
            <th>終了</th>
        </tr>
        <% for (String[] info : Reservations) { %>
            <tr>
                <td>
                    <% if (sdf.parse(info[7]).after(today)) { %>
                        <form name="CancelForm" method="POST" action="CancelReservation" onsubmit="return confirmCancellation('<%= info[0] %>')">
                            <input type="hidden" name="USER" value="<%= user_id %>">
                            <input type="hidden" name="PASSWORD" value="<%= password %>">
                            <input type="hidden" name="RESERVEID" value="<%= info[0] %>">
                            <input type="hidden" name="ROOMID" value="<%= info[2] %>">
                            <input type="hidden" name="STARTTIME" value="<%= info[7] %>">
                            <button class="cancel" type="submit"><strong>取消</strong></button>
                        </form>
                    <% } %>
                </td>
                <% for (int i = 0; i < info.length; i++) { %>
                    <td><%=info[i]%></td>
                <% } %>
            </tr>
        <% } %>
    </table>
</body>
</html>
