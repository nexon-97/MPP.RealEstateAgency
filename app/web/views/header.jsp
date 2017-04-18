<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="admin_panel.jsp" />
<a href="/">
<header>
    <div class="headerLeftBlock">
        <h1>Real Estate Agency - Header</h1>
    </div>
    <div class="headerRightBlockWrapper">
        <div class="headerRightBlock">
            <c:choose>
                <c:when test="${user != null}">
                    <a href="/profile"><span class="buttonSimple grayHeaderButton">Добро пожаловать, ${user.login}!</span></a>
                    <a href="/logout"><span class="buttonSimple grayHeaderButton">Выйти</span></a>
                </c:when>
                <c:otherwise>
                    <a href="/register"><span class="buttonSimple grayHeaderButton">Регистрация</span></a>
                    <a href="/auth"><span class="buttonSimple grayHeaderButton">Вход</span></a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>
</a>