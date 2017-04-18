<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Real estate agency - результат авторизации</title>
    <jsp:include page="html_head_common.jsp" />
</head>
<body>
<jsp:include page="header.jsp" />

<h1>Страница авторизации</h1>
<div class="content">
    <c:choose>
        <c:when test="${user != null}">
            <div>Здравствуйте, ${user.login}</div>
            <div>Здесь должно произойти перенаправление</div>
        </c:when>
        <c:otherwise>
            <div>Ошибка авторизации</div>
            <jsp:include page="login_form.jsp"/>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>