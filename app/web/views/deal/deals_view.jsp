<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Real estate agency - index</title>
    <jsp:include page="../html_head_common.jsp" />
</head>
<body>
<div class="wrapper">
    <jsp:include page="../header.jsp" />
    <div class="content">
        <div style="text-align: left">
            <h1>Все сделки</h1>
            <div>
                <c:forEach var="deal" items="${deals}">
                    <div>
                        <a href="/deal?id=${deal.id}">Сделка [${deal.id}]</a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <jsp:include page="../footer.jsp" />
</div>
</body>
</html>