<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Real estate agency - User roles</title>
    <jsp:include page="../html_head_common.jsp" />

</head>
<body>
<div class="wrapper">
    <jsp:include page="../header.jsp" />
    <div class="content">
        <div class="profileViewContent">
            <c:choose>
                <c:when test="${user != null}">
                    <c:if test="${(error != null)}">
                        <div id="add-error" class="error-field">${error}</div>
                    </c:if>
                    <c:if test="${(success != null)}">
                        <div id="add-error" class="success-field">${success}</div>
                    </c:if>
                    <c:forEach var="userInfo" items="${userList}">
                        <c:set var="userInfo" value="${userInfo}" scope="request" />
                        <jsp:include page="user_short_info.jsp" />
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div><h2>Вы не авторизированы!</h2></div>
                    <div class="buttonSimple" style="margin-top: 10px;">
                        <a href="/auth">Войти</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <jsp:include page="../footer.jsp" />
</div>
</body>
</html>