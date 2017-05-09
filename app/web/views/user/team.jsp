<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Страница пользователя</title>
    <jsp:include page="../html_head_common.jsp" />
</head>
<body>
<div class="wrapper">
    <jsp:include page="../header.jsp" />
    <div class="content">
        <div style="text-align: left">
            <h1>Команда сайта</h1>
            <div id="members-container" style="padding-left: 30px">
                <h2>Администраторы</h2>
                <c:forEach var="admin" items="${adminsList}">
                    <div><a href="/user?id=${admin.id}">${admin.surname} ${admin.name} ${admin.patronymic}</a></div>
                </c:forEach>
                <h2>Риэлторы</h2>
                <c:forEach var="realtor" items="${realtorsList}">
                    <div><a href="/user?id=${realtor.id}">${realtor.surname} ${realtor.name} ${realtor.patronymic}</a></div>
                </c:forEach>
                <h2>Брокеры</h2>
                <c:forEach var="broker" items="${brokersList}">
                    <div><a href="/user?id=${broker.id}">${broker.surname} ${broker.name} ${broker.patronymic}</a></div>
                </c:forEach>
            </div>
        </div>
    </div>
    <jsp:include page="../footer.jsp" />
</div>
</body>
</html>