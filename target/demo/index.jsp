<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" charset=UTF-8" pageEncoding="UTF-8">
    <title>Login</title>
    <script>
        function validateForm() {
            var userId = document.forms["loginForm"]["USER"].value;
            var password = document.forms["loginForm"]["PASSWORD"].value;

            if (userId === "" || password === "") {
                alert("ユーザーIDとパスワードを入力してください。");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
	<%
		String errorMessage = "";
		if (request.getAttribute("ErrorMessage") != null) {
			errorMessage = (String) request.getAttribute("ErrorMessage");
		}
		
	%>
	<h2>ログイン</h2>
    <p><%=errorMessage%></p>
    <form name="loginForm" method="POST" action="Home" onsubmit="return validateForm();">
        <h3>ユーザーID</h3>
        <input type="text" name="USER" size="50" maxlength="30" />
        
        <h3>パスワード</h3>
        <input type="text" name="PASSWORD" size="50" maxlength="64" />

        <input type="submit" value="送信" />
    </form>

    <h3><a href="signup.html">新しくアカウントを作成</a></h3>
</body>
</html>
