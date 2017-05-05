<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="personalAreaLabel">Собственности</div>
<div class="admin-table-container">
    <table class="admin-data-table">
        <tr>
            <td class="admin-data-column-name" width="10%">id</td>
            <td class="admin-data-column-name" width="10%">type</td>
            <td class="admin-data-column-name" width="10%">owner</td>
            <td class="admin-data-column-name" width="20%">city</td>
            <td class="admin-data-column-name" width="20%">street</td>
            <td class="admin-data-column-name" width="10%">house №</td>
            <td class="admin-data-column-name" width="10%">block №</td>
            <td class="admin-data-column-name" width="10%">flat №</td>
            <td class="admin-data-column-name" width="5%">change</td>
            <td class="admin-data-column-name" width="5%">delete</td>
        </tr>
        <c:forEach var="property" items="${propertyList}">
            <tr>
                <td class="admin-data-column-field">${property.id}</td>
                <td class="admin-data-column-field">${property.type}</td>
                <td class="admin-data-column-field">${property.owner.login}</td>
                <td class="admin-data-column-field">${property.city}</td>
                <td class="admin-data-column-field">${property.street}</td>
                <td class="admin-data-column-field">${property.houseNumber}</td>
                <td class="admin-data-column-field">${property.blockNumber}</td>
                <td class="admin-data-column-field">${property.flatNumber}</td>
                <td class="admin-data-column-field">change</td>
                <td class="admin-data-column-field">delete</td>
            </tr>
        </c:forEach>
    </table>
</div>