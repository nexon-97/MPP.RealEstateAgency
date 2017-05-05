<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="personalInfoBlockWrapper">
    <form method="post" name="${user.login}" action="/user_roles">
        <div class="user-short-info-block">
            <div class="profileLogin">
                <input type="text" class="user-short-info-login" readonly="true" name="user_login" value="${userInfo.login}"/>
                <select class="user-short-info-select" name="user_role">
                    <c:forEach var="role" items="${roles}">
                        <option <c:if test="${role == userInfo.roleId}">selected</c:if>>${role}</option>
                    </c:forEach>
                </select>
            </div>
            <div>${userInfo.surname} ${userInfo.name} ${userInfo.patronymic}</div>
            <div class="contactInfoBlock">
                <div>
                    <span>${userInfo.email}</span>
                </div>
            </div>
            <div>
                <input type="submit" class="user-short-info-button" value="Change role"/>
            </div>
    </div>
    </form>
</div>
