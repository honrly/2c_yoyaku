<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" charset=UTF-8" pageEncoding="UTF-8">
<title>予約しました</title>
</head>
<body>
	<%
		String user_id = (String) request.getAttribute("User_id");
        String password = (String) request.getAttribute("Password");
	%>

	<h1>予約しました。</h1>

	<br>

	<form name="HomeForm" method="POST" action="Home">
        <input type="hidden" name="USER" value="<%= user_id %>">
        <input type="hidden" name="PASSWORD" value="<%= password %>">
        <a href="home.jsp"><button><strong>ホームに戻る</strong></button></a>
    </form>

</body>
</html>
