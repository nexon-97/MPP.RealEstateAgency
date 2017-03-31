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
    <td class="tableCellLeftOdd">ID</td>
    <td class="tableCellLeftEven">${user.id}</td>
</tr>
<tr>
    <td class="tableCellLeftEven">Login</td>
    <td  class="tableCellLeftOdd">${user.login}</td>
</tr>
<tr>
    <td class="tableCellLeftOdd">Name</td>
    <td class="tableCellLeftEven">${user.name}</td>
</tr>
<tr>
    <td class="tableCellLeftEven">Surname</td>
    <td  class="tableCellLeftOdd">${user.surname}</td>
</tr>
<tr>
    <td class="tableCellLeftOdd">Email</td>
    <td class="tableCellLeftEven">${user.email}</td>
</tr>
<tr>
    <td class="tableCellLeftEven">Phone</td>
    <td  class="tableCellLeftOdd">${user.phoneNumber}</td>
</tr>
</table>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
