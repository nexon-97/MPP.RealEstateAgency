<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${user.roleId == 'Admin'}">
<div id="adminPanel">
    <span style="font-weight: bold;">Admin bar</span>
    <span class="buttonSimple grayHeaderButton"><a href="/deals">Сделки</a></span>
</div>
</c:if>