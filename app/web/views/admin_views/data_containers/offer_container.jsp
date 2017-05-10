<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="personalAreaLabel">Предложения</div>
<div class="admin-table-container">
    <table class="admin-data-table">
        <tr>
            <td class="admin-data-column-name" width="10%">id</td>
            <td class="admin-data-column-name" width="20%">type</td>
            <td class="admin-data-column-name" width="40%">property</td>
            <td class="admin-data-column-name" width="20%">cost</td>
            <td class="admin-data-column-name" width="5%">delete</td>
        </tr>
        <c:forEach var="offer" items="${offerList}">
            <tr>
                <td class="admin-data-column-field">${offer.id}</td>
                <td class="admin-data-column-field">${offer.offerType}</td>
                <td class="admin-data-column-field">
                    г.${offer.property.city} ул.${offer.property.street} д.${offer.property.houseNumber}<c:if test="${offer.property.blockNumber!=null}">-${offer.property.blockNumber}</c:if> кв.${offer.property.houseNumber}
                </td>
                <td class="admin-data-column-field">${offer.cost}</td>
                <td class="admin-data-column-field">
                    <c:if test="${!hasDeal.get(offer)}">
                        <a href="/deleteOffer?id=${offer.id}">delete</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>