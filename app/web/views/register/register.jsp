<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Real estate agency - register</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <jsp:include page="../html_head_common.jsp" />
        <script type="text/javascript" src="${pageContext.request.contextPath}scripts/validateData.js" ></script>
    </head>
    <body>
        <jsp:include page="../header.jsp" />
        <h1>Register page</h1>
        <div class="content">
            <jsp:include page="register_form.jsp"/>
        </div>
        <jsp:include page="../footer.jsp" />
    </body>
</html>
