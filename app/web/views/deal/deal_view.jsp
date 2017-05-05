<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Real estate agency - Сделки</title>
    <jsp:include page="../html_head_common.jsp" />
</head>
<body>
<div class="wrapper">
    <jsp:include page="../header.jsp" />
    <div class="content">
        <div style="text-align: left; padding: 15px;">
            <h1>Сделка [${deal.id}]</h1>
            <div>
                <c:set var="property" value="${deal.offer.property}" />
                <div>Собственность: ${property.city}, ${property.street}, ${property.houseNumber}</div>
                <div>Продавец: ${property.owner.surname} ${property.owner.name} ${property.owner.patronymic}</div>
                <div>Покупатель: ${deal.buyer.surname} ${deal.buyer.name} ${deal.buyer.patronymic}</div>
                <div>Риэлтор: ${deal.realtor.surname} ${deal.realtor.name} ${deal.realtor.patronymic}</div>
                <div>Брокер: ${deal.broker.surname} ${deal.broker.name} ${deal.broker.patronymic}</div>
            </div>
        </div>
    </div>
    <jsp:include page="../footer.jsp" />
</div>
</body>
</html>