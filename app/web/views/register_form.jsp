<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="loginFormWrapper">
    <form id="registerForm" accept-charset="UTF-8" method="post" onsubmit="checkValues()" action="/register">
        <table>
            <tr>
                <td>Login</td>
                <td><input id="loginInput" type="text"
                        value="<c:choose><c:when test="${(registerUser != null) && (registerUser.login != null)}">${registerUser.login}</c:when><c:otherwise></c:otherwise></c:choose>"
                        class="loginFormInput" name="login"/></td>
                <td>
                    <div id="login-error" class="error-field">
                        <c:if test="${(registerUser != null) && (registerUser.login == null)}">Login should contains only english symbols(A-Z, a-z), numbers(0-9) or ( _ )</c:if>
                    </div>
                </td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input id="passwordInput" type="password" class="loginFormInput" name="password"/></td>
                <td>
                    <div id="password-error" class="error-field">
                        <c:if test="${(registerUser != null) && (registerUser.passwordHash == null)}">Password length should be 5-20 symbols</c:if>
                    </div>
                </td>
            </tr>
            <tr>
                <td>Email</td>
                <td><input id="emailInput" type="email"
                           value="<c:if test="${(registerUser != null) && (registerUser.email != null)}">${registerUser.email}</c:if>"
                           class="loginFormInput" name="email"/></td>
                <td>
                    <div id="email-error" class="error-field">
                        <c:if test="${(registerUser != null) && (registerUser.email == null)}">Email should be correct</c:if>
                    </div>
                </td>
            </tr>
            <tr>
                <td>Name</td>
                <td><input id="nameInput" type="text"
                           value="<c:if test="${(registerUser != null) && (registerUser.name != null)}">${registerUser.name}</c:if>"
                           class="loginFormInput" name="name"/></td>
                <td>
                    <div id="name-error" class="error-field">
                        <c:if test="${(registerUser != null) && (registerUser.name == null)}">Name should contains only english symbols(A-Z, a-z), dash ( - ) or quotes ( ' )</c:if>
                    </div>
                </td>
            </tr>
            <tr>
                <td>Surname</td>
                <td><input id="surnameInput" type="text"
                           value="<c:if test="${(registerUser != null) && (registerUser.surname != null)}">${registerUser.surname}</c:if>"
                           class="loginFormInput" name="surname"/></td>
                <td>
                    <div id="surname-error" class="error-field">
                        <c:if test="${(registerUser != null) && (registerUser.surname == null)}">Surname should contains only english symbols(A-Z, a-z), dashes ( - ) or quotes ( ' )</c:if>
                    </div>
                </td>
            </tr>
            <tr>
                <td>Patronymic</td>
                <td><input id="patronymicInput" type="text"
                           value="<c:if test="${(registerUser != null) && (registerUser.patronymic != null)}">${registerUser.patronymic}</c:if>"
                           class="loginFormInput" name="patronymic"/></td>
                <td>
                    <div id="patronymic-error" class="error-field">
                        <c:if test="${(registerUser != null) && (registerUser.patronymic == null)}">Patronymic should contains only english symbols(A-Z, a-z), dashes ( - ) or quotes ( ' )</c:if>
                    </div>
                </td>
            </tr>
            <tr>
                <td>Phone</td>
                <td><input id="phoneInput" type="text" placeholder="Example: +375297654532"
                           value="<c:if test="${(registerUser != null) && (registerUser.phone != null)}">${registerUser.phone}</c:if>"
                           class="loginFormInput" name="phone"/></td>
                <td>
                    <div id="phone-error" class="error-field">
                        <c:if test="${(registerUser != null) && (registerUser.phone == null)}">Phone should be correct(+375xxYYYYYYY or 80xxYYYYYYY)</c:if>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div id="error-empty_field" class="error-field"></div>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input id="submitRegister" type="submit" class="loginButton"  value="Register"/>
                </td>
            </tr>

        </table>
    </form>
</div>
