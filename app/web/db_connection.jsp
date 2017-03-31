<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DB Connection Test</title>
    <link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
    <h1>DB Connection test</h1>
    <table style="margin: 0px 10px">
        <tr><td>ID</td><td>${user.id}</td></tr>
        <tr><td>Login</td><td>${user.login}</td></tr>
        <tr><td>Name</td><td>${user.name}</td></tr>
        <tr><td>Surname</td><td>${user.surname}</td></tr>
        <tr><td>Email</td><td>${user.email}</td></tr>
        <tr><td>Phone</td><td>${user.phoneNumber}</td></tr>
    </table>
</body>
</html>
