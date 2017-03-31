<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Server error</title>
    <jsp:include page="../html_head_common.jsp" />
</head>
<body>
<jsp:include page="header.jsp" />
<div class="errorMessage">
    ${msg}
</div>
<jsp:include page="footer.jsp" />
</body>
</html>
