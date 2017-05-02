<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="loginFormWrapper">
    <form id="registerForm" accept-charset="UTF-8" method="post" onsubmit="checkValues()" action="/register">
        <table>
            <tr>
                <td>Login</td>
                <td><input id="loginInput" type="text"
                        value="<c:choose><c:when test="${(values != null) && (values.login != null)}">${values.login}</c:when><c:otherwise></c:otherwise></c:choose>"
                        class="loginFormInput" name="login"/>
                    <div id="login-error" class="error-field">
                        <c:if test="${(errors != null) && (errors.login != null)}">${errors.login}</c:if>
                        <c:if test="${loginEmpty != null}" >Пользователь с таким логином уже существует</c:if>
                    </div>
                </td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input id="passwordInput" type="password" class="loginFormInput" name="password"/>
                    <div id="password-error" class="error-field">
                        <c:if test="${(errors != null) && (errors.password != null)}">${errors.password}</c:if>
                    </div>
                </td>
            </tr>
            <tr>
                <td>Email</td>
                <td><input id="emailInput" type="email"
                           value="<c:if test="${(values != null) && (values.email != null)}">${values.email}</c:if>"
                           class="loginFormInput" name="email"/>
                    <div id="email-error" class="error-field">
                        <c:if test="${(errors != null) && (errors.email != null)}">${errors.email}</c:if>
                    </div>
                </td>
            </tr>
            <tr>
                <td>Name</td>
                <td><input id="nameInput" type="text"
                           value="<c:if test="${(values != null) && (values.name != null)}">${values.name}</c:if>"
                           class="loginFormInput" name="name"/>
                    <div id="name-error" class="error-field">
                        <c:if test="${(errors != null) && (errors.name != null)}">${errors.name}</c:if>
                    </div>
                </td>
            </tr>
            <tr>
                <td>Surname</td>
                <td><input id="surnameInput" type="text"
                           value="<c:if test="${(values != null) && (values.surname != null)}">${values.surname}</c:if>"
                           class="loginFormInput" name="surname"/>
                    <div id="surname-error" class="error-field">
                        <c:if test="${(errors != null) && (errors.surname != null)}">${errors.surname}</c:if>
                    </div>
                </td>
            </tr>
            <tr>
                <td>Patronymic</td>
                <td><input id="patronymicInput" type="text"
                           value="<c:if test="${(values != null) && (values.patronymic != null)}">${values.patronymic}</c:if>"
                           class="loginFormInput" name="patronymic"/>
                    <div id="patronymic-error" class="error-field">
                        <c:if test="${(errors != null) && (errors.patronymic != null)}">${errors.patronymic}</c:if>
                    </div>
                </td>
            </tr>
            <tr>
                <td>Phone</td>
                <td><input id="phoneInput" type="text" placeholder="Example: +375297654532"
                           value="<c:if test="${(values != null) && (values.phone != null)}">${values.phone}</c:if>"
                           class="loginFormInput" name="phone"/>
                    <div id="phone-error" class="error-field">
                        <c:if test="${(errors != null) && (errors.phone != null)}">${errors.phone}</c:if>
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
