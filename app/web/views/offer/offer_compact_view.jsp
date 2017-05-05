<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<a href="${pageContext.request.contextPath}/offer?id=${offer.id}">
    <div class="offer-view-short-container">
        <div>Предложение [${offer.id}]</div>
        <c:set var="property" value="${offer.property}" />
        <div>${property.owner.surname} ${property.owner.name} ${property.owner.patronymic}</div>
        <div>Тип: ${offer.offerType}</div>
        <div>Стоимость: ${offer.cost} $</div>
        <div>Адрес: ${property.city}, ${property.street}, ${property.houseNumber}</div>
        <c:if test="${property.owner.id == user.id}">
            <a href="/editOffer?id=${offer.id}"><div class="pen-button"><img src="/img/pen.png" /></div></a>
        </c:if>
        <c:if test="${property.owner.id == user.id || user.roleId == 'Admin'}">
            <a href="/deleteOffer?id=${offer.id}"><div class="delete-button"><img src="/img/cross.png" /></div></a>
        </c:if>
    </div>
</a>