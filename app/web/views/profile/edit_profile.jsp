<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <div>
                    <div>Личный кабинет</div>
                    <div class="profileLogin">${user.login}</div>
                    <form method="post" action="/profileEdit" accept-charset="UTF-8">
                        <div>Фамилия</div>
                        <div><input type="text" name="surname" value="${user.surname}" /></div>
                        <div>Имя</div>
                        <div><input type="text" name="name" value="${user.name}" /></div>
                        <div>Отчество</div>
                        <div><input type="text" name="patronymic" value="${user.patronymic}" /></div>
                        <div>E-mail</div>
                        <div><input type="text" name="email" value="${user.email}" /></div>
                        <div>Телефон</div>
                        <div><input type="text" name="phone" value="${user.phone}" /></div>
                        <div><input type="submit" value="Сохранить" /></div>
                        <div><input type="reset" value="Отмена" /></div>
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