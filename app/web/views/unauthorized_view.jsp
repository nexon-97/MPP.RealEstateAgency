<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Авторизация</title>
    <jsp:include page="html_head_common.jsp" />
</head>
<body>
<div class="wrapper">
    <jsp:include page="header.jsp" />
    <div class="content">
        <h2 style="margin-top: 25px;">Для выполнения данного действия необходима авторизация!</h2>
        <div>
            <div class="buttonSimple" style="margin-top: 10px;">
                <a href="/auth">Войти</a>
            </div>
        </div>
    </div>
    <jsp:include page="footer.jsp" />
</div>
</body>
</html>