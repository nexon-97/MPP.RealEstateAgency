<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="personalAreaLabel">Сделки</div>
<div class="admin-table-container">
    <table class="admin-data-table">
        <tr>
            <td class="admin-data-column-name" width="5%">id</td>
            <td class="admin-data-column-name" width="5%">offerType</td>
            <td class="admin-data-column-name" width="20%">property</td>
            <td class="admin-data-column-name" width="20%">buyer</td>
            <td class="admin-data-column-name" width="20%">seller</td>
            <td class="admin-data-column-name" width="20%">realtor</td>
            <td class="admin-data-column-name" width="20%">broker</td>
        </tr>
        <c:forEach var="deal" items="${dealList}">
            <tr>
                <td class="admin-data-column-field">${deal.id}</td>
                <td class="admin-data-column-field">${deal.offer.offerType}</td>
                <td class="admin-data-column-field">
                    г.${deal.offer.property.city} ул.${deal.offer.property.street} д.${deal.offer.property.houseNumber}<c:if test="${deal.offer.property.blockNumber!=null}">-${deal.offer.property.blockNumber}</c:if> кв.${deal.offer.property.houseNumber}
                </td>
                <td class="admin-data-column-field">
                    ${deal.buyer.name} ${deal.buyer.surname} ${deal.buyer.patronymic}
                </td>
                <td class="admin-data-column-field">
                    ${deal.offer.property.owner.name} ${deal.offer.property.owner.surname} ${deal.offer.property.owner.patronymic}
                </td>
                <td class="admin-data-column-field">
                    ${deal.realtor.name} ${deal.realtor.surname} ${deal.realtor.patronymic}
                </td>
                <td class="admin-data-column-field">
                    ${deal.broker.name} ${deal.broker.surname} ${deal.broker.patronymic}
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
