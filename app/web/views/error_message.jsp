<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Server error</title>
    <jsp:include page="html_head_common.jsp" />
</head>
<body>
    <jsp:include page="header.jsp" />
    <div class="content">
        <div class="errorMessage">
            ${msg}
        </div>
    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>
