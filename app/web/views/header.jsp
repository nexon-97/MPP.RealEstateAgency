<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<a href="/">
<div id="header">
    <div class="headerLeftBlock">
        <h1>Real Estate Agency - Header</h1>
    </div>
    <div class="headerRightBlockWrapper">
        <div class="headerRightBlock">
            <c:choose>
                <c:when test="${user != null}">
                    Hello, ${user.login}!
                </c:when>
                <c:otherwise>
                    <span><a href="#">Register</a></span>
                    <span>|</span>
                    <span><a href="/auth">Log In</a></span>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</a>