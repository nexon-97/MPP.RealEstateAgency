<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Admin Data</title>
    <jsp:include page="../html_head_common.jsp" />
</head>
<body>
<div class="wrapper">
    <jsp:include page="../header.jsp" />
    <div class="content">
        <div class="profileViewContent">
            <jsp:include page="data_containers/user_container.jsp" />
            <jsp:include page="data_containers/property_container.jsp" />
            <jsp:include page="data_containers/offer_container.jsp" />
            <jsp:include page="data_containers/deal_container.jsp" />
        </div>
    </div>
    <jsp:include page="../footer.jsp" />
</div>
</body>
</html>