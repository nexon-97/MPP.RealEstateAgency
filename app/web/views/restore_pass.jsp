<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Real estate agency - авторизация</title>
    <jsp:include page="html_head_common.jsp" />
</head>
<body>
<jsp:include page="header.jsp" />

<h1>Восстановление пароля</h1>
<div class="content">
    <div class="loginFormWrapper">
    <form id="loginForm" action="/restorePassword" method="post">
        <table>
            <tr>
                <td width="30%">Логин</td>
                <td><input class="loginFormInput" type="text" placeholder="Логин" name="login"></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input id="submitInput" type="submit" class="loginButton" value="Восстановить"/>
                </td>
            </tr>
        </table>
    </form>
    </div>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>