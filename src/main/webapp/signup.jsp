<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" charset=UTF-8" pageEncoding="UTF-8">
<title>アカウント登録</title>
</head>
<body>
	<%
		String[] user_info = (String[]) request.getAttribute("User_info");
	%>

	<h1>登録しました。</h1>
	
	<h3>ユーザーID</h3>
	<p><%=user_info[0]%></p>

	<h3>パスワード</h3>
	<p><%=user_info[1]%></p>

	<h3>お名前</h3>
	<p><%=user_info[2]%></p>

	<h3>TELL</h3>
	<p><%=user_info[3]%></p>

	<h3><a href="index.jsp">ログイン</a></h3>

</body>
</html>
