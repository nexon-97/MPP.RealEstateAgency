<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="personalAreaLabel">Пользователи</div>
<div class="admin-table-container">
    <table class="admin-data-table">
        <tr>
            <td class="admin-data-column-name" width="10%">id</td>
            <td class="admin-data-column-name" width="10%">role</td>
            <td class="admin-data-column-name" width="10%">login</td>
            <td class="admin-data-column-name" width="10%">email</td>
            <td class="admin-data-column-name" width="10%">name</td>
            <td class="admin-data-column-name" width="10%">surname</td>
            <td class="admin-data-column-name" width="10%">patronymic</td>
            <td class="admin-data-column-name" width="10%">phone</td>
            <td class="admin-data-column-name" width="20%">info</td>
        </tr>
        <c:forEach var="user" items="${userList}">
            <tr>
                <td class="admin-data-column-field">${user.id}</td>
                <td class="admin-data-column-field">${user.roleId}</td>
                <td class="admin-data-column-field">${user.login}</td>
                <td class="admin-data-column-field">${user.email}</td>
                <td class="admin-data-column-field">${user.name}</td>
                <td class="admin-data-column-field">${user.surname}</td>
                <td class="admin-data-column-field">${user.patronymic}</td>
                <td class="admin-data-column-field">${user.phone}</td>
                <td class="admin-data-column-field">${user.info}</td>
            </tr>
        </c:forEach>
    </table>
</div>
