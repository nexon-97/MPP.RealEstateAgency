<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Real estate agency - index</title>
    <jsp:include page="html_head_common.jsp" />
</head>
<body>
<div class="wrapper">
    <jsp:include page="header.jsp" />
    <div class="content">
        <div class="siteDescriptionArea">
            <p>Агентство недвижимости «Название» уже более 10 лет оказывает юридические и риэлтэрские услуги на территории Республики Беларусь.</p>
            <p>В нашей компании работает 15 квалифицированных специалистов, знающих своё дело  и качественно осуществляющих все виды операции с недвижимостью.</p>
            <p>За годы успешной практики мы придерживаемся своего основного кредо - каждому Белорусу – достойное собственное жильё. Наша цель: поднять на самый высокий уровень жилищные условия каждого белоруса!</p>
            <p>Агентство недвижимости «Название» - это надёжность и гарантия качества оказываемых услуг потребителям рынка недвижимости в независимости от степени сложности!</p>
            <br>
            <p><i>Трудное – это то, что можно сделать немедленно, а невозможное – потребует немного больше времени!</i></p>
        </div>
        <a href="${pageContext.request.contextPath}/offerFilter"><div class="buttonSimple" style="margin: 10px">Фильтр</div></a>
        <h2>Предложения</h2>
        <div class="offers-container">
            <c:forEach var="offer" items="${offers}">
                <c:set var="offer" value="${offer}" scope="request" />
                <jsp:include page="offer/offer_compact_view.jsp" />
            </c:forEach>
        </div>
    </div>
    <jsp:include page="footer.jsp" />
</div>
</body>
</html>