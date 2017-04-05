<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<a href="/">
<div id="header">
    <div style="display: inline-block">
        <h1>Real Estate Agency - Header</h1>
    </div>
    <div style="display: inline-block; vertical-align: middle;">
        <c:choose>
            <c:when test="${loginSucceeded}">
                Hello, ${login}!
            </c:when>
            <c:otherwise>
                <a href="#">Register</a>
                <a href="/auth">Log In</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</a>