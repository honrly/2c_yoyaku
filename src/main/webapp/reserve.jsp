<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" charset="UTF-8" pageEncoding="UTF-8">
    <title>予約ページ</title>
    <style>

    table {
        border-collapse: collapse;
        width: 90%;
        margin-left: auto;
        margin-right: auto;
    }

    th, td {
        border: 1px solid green;
        padding: 10px;
        text-align: center;
    }

    th {
        background-color: lightgreen;
        border: 2px solid darkgreen;
    }

    td:first-child {
        min-width: 135px;
        text-align: left;
    }

    button {
        width: 10em;
        height: 3em;
    }

</style>
</head>
<body>
    <%
        String user_id = (String) request.getAttribute("User_id");
        String password = (String) request.getAttribute("Password");
        
        List<String[]> room_info = (List<String[]>) request.getAttribute("Rooms");
        int start = 9;
        int end = 23;

        // フォームが初めてアクセスされたときの日付の取得
        String initialDate = (String) request.getAttribute("InitialDate");
        if (initialDate == null) {
            // フォームが初めてアクセスされたとき
            java.util.Date today = new java.util.Date();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            initialDate = sdf.format(today);
            request.setAttribute("InitialDate", initialDate);
        }

        String ToDay = "";
        java.util.Date today = new java.util.Date();
        java.text.SimpleDateFormat sdf_today = new java.text.SimpleDateFormat("yyyy-MM-dd");
        ToDay = sdf_today.format(today);

        // 日付が選択されたときの処理
        String selectedDate = request.getParameter("DATE");
        if (selectedDate != null) {
            initialDate = selectedDate; // 選択された日付で更新
        }
        
        String[][] AvailableData = (String[][]) request.getAttribute("Available");
    %>

    <h2>空き状況</h2>
    <form name="AkiForm" method="POST" action="Reserve">
        <input type="hidden" name="USER" value="<%= user_id %>">
        <input type="hidden" name="PASSWORD" value="<%= password %>">
        <div id="container">
            <input type="date" id="date" name="DATE" onchange="submitForm()" value="<%= initialDate %>" min="<%= ToDay %>">
        </div>
    </form>
    <br>

    <table>
        <tr>
            <th>部屋</th>
            <% for (int i = start; i < end-1; i++) { %>
                <th><%=i%>:00~</th>
            <% } %>
        </tr>
        <% for (int roomIndex = 0; roomIndex < room_info.size(); roomIndex++) { %>
    <tr>
        <td>
            <%= roomIndex == 0 ? "部屋番号：" + room_info.get(roomIndex)[0] : room_info.get(roomIndex)[0] %><br>

            <% for (int i = 1; i < room_info.get(roomIndex).length; i++) { %>
                <%= room_info.get(roomIndex)[i] %><br>
            <% } %>
        </td>

        <% for (int hourIndex = 0; hourIndex < AvailableData[roomIndex].length; hourIndex++) { %>
            <td><%= AvailableData[roomIndex][hourIndex] != null ? AvailableData[roomIndex][hourIndex] : "〇" %></td>
        <% } %>
    </tr>
<% } %>

    </table>

    <h2>予約フォーム</h2>
    <form name="ReservationForm" method="POST" action="RegisterReservation" onsubmit="return validateForm();">
        <input type="hidden" name="USER" value="<%= user_id %>">
        <input type="hidden" name="PASSWORD" value="<%= password %>">

        <h3>予約タイトル</h3>
        <input type="text" name="RESERVENAME" size="50" maxlength="25"/>

        <h3>予約日時</h3>
        <div id="container">
            <input type="date" id="date" name="RESERVEDAY" value="<%= initialDate %>" min="<%= ToDay %>">
        </div>

        <h3>利用開始時間</h3>
        <input type="time" id="time" name="STARTTIME" step="1" value="09:00:00" min="09:00:00" max="21:00:00">

        <h3>利用終了時間</h3>
        <input type="time" id="time" name="ENDTIME" step="1" value="10:00:00" min="09:00:00" max="22:00:00">
        
        <h3>部屋選択</h3>
        <select id="selectedRoom" name="SELECTEDROOM">
            <% for (int i = 0; i < room_info.size(); i++) { %>
                <option value="<%= room_info.get(i)[0] %>"><%= room_info.get(i)[1] %></option>
            <% } %>
        </select>

        <h3>利用人数</h3>
        <input type="number" id="numOfPeople" name="NUM_OF_PEOPLE" value="1" min="1">

        <h3>目的(理由)</h3>
        <textarea id="reason" name="REASON"></textarea>

        <h3>備考</h3>
        <textarea id="remarks" name="REMARKS"></textarea>

        <br>
        <button type="submit">予約する</button>
    </form>

    <br>
    <form name="HomeForm" method="POST" action="Home">
        <input type="hidden" name="USER" value="<%= user_id %>">
        <input type="hidden" name="PASSWORD" value="<%= password %>">
        <a href="home.jsp"><button><strong>ホームに戻る</strong></button></a>
    </form>

    <script>
        function submitForm() {
            document.forms["AkiForm"].submit();
        }

        function validateForm() {
            var reservationName = document.forms["ReservationForm"]["RESERVENAME"].value;
            var reason = document.forms["ReservationForm"]["REASON"].value;

            if (reservationName.trim() === "" || reason.trim() === "") {
                alert("予約タイトルと目的(理由)は必須入力項目です。");
                return false;
            }

            var startTime = document.forms["ReservationForm"]["STARTTIME"].value;
            var endTime = document.forms["ReservationForm"]["ENDTIME"].value;

            if (startTime >= endTime) {
                alert("利用終了時間は利用開始時間より後でなければなりません。");
                return false;
            }

                document.forms["ReservationForm"].submit();
                return true;
        }

        

        document.addEventListener('DOMContentLoaded', function () {
            // 初回アクセス時にフォームの初期値が未設定ならば今日の日付に設定
            var dateInput = document.getElementById('date');
            if (!dateInput.value) {
                var today = new Date();
                var year = today.getFullYear();
                var month = String(today.getMonth() + 1).padStart(2, '0');
                var day = String(today.getDate()).padStart(2, '0');
                var todayFormatted = year + '-' + month + '-' + day;

                dateInput.value = todayFormatted;
            }
        });

        function japaneseTextInput(input) {
		// 入力値が全角の日本語のパターンに一致しない場合、入力をクリア
			if (!japaneseTextPattern.test(input.value)) {
				alert("全角の日本語のみ入力できます");
				input.value = "";
			}
		}
    </script>



</body>
</html>