<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>User profile</title>
    <jsp:include page="../html_head_common.jsp" />
</head>
<body>
<div class="wrapper">
    <jsp:include page="../header.jsp" />
    <div class="content">
        <c:choose>
            <c:when test="${user != null}">
                <div>Личный кабинет</div>
                <div class="profileLogin">${user.login}</div>
                <div>${user.surname} ${user.name} ${user.patronymic}</div>
                <div>E-mail: ${user.email}</div>
                <c:if test="${user.phone != null and fn:length(user.phone) > 0}">
                    <div>Телефон: ${user.phone}</div>
                </c:if>
                <div>
                    <form method="get" action="/profileEdit">
                        <input type="submit" value="Изменить" />
                    </form>
                </div>
            </c:when>
            <c:otherwise>
                <div>Вы не авторизированы!</div>
                <div class="buttonSimple">
                    <a href="/auth">Войти</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <jsp:include page="../footer.jsp" />
</div>
</body>
</html>