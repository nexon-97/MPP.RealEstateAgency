<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Предложение</title>
    <jsp:include page="../html_head_common.jsp" />
</head>
<body>
<div class="wrapper">
    <jsp:include page="../header.jsp" />
    <div class="content">
        <c:choose>
            <c:when test="${offer != null}">
                <jsp:include page="offer_form.jsp" />
            </c:when>
            <c:otherwise>
                <h2 style="margin-top: 15px;">Такого предложения не существует!</h2>
            </c:otherwise>
        </c:choose>
    </div>
    <jsp:include page="../footer.jsp" />
</div>
</body>
</html>