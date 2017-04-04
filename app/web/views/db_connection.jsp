<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DB Connection Test</title>
    <jsp:include page="../html_head_common.jsp" />
</head>
<body>
<jsp:include page="header.jsp" />

<h1>DB Connection test</h1>

<div class="testUserWrapper">
<table class="testUserTable">

<tr>
<td class="tableCellLeftEven">ID</td>
<td class="tableCellLeftOdd">Login</td>
<td class="tableCellLeftEven">Name</td>
<td class="tableCellLeftOdd">Surname</td>
<td class="tableCellLeftEven">Email</td>
<td class="tableCellLeftOdd">Phone</td>
</tr>

<c:forEach var="user" items="${users}">
<tr>
<td class="tableCellLeftOdd">${user.id}</td>
<td class="tableCellLeftEven">${user.login}</td>
<td class="tableCellLeftOdd">${user.name}</td>
<td class="tableCellLeftEven">${user.surname}</td>
<td class="tableCellLeftOdd">${user.email}</td>
<td class="tableCellLeftEven">${user.phoneNumber}</td>
</tr>
</c:forEach>

</table>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
