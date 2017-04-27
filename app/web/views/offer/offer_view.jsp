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
                <h2>Сделка [${offer.id}]</h2>
            </c:when>
            <c:otherwise>
                <h2>Такой сделки не существует!</h2>
            </c:otherwise>
        </c:choose>
    </div>
    <jsp:include page="../footer.jsp" />
</div>
</body>
</html>