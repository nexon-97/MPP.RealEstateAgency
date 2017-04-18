<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="loginFormWrapper">
    <form id="loginForm" method="post" action="/auth">
        <table>
            <tr>
                <td width="30%">Логин</td>
                <td><input id="loginInput" type="text"  class="loginFormInput" name="login"/></td>
            </tr>
            <tr>
                <td>Пароль</td>
                <td><input id="passwordInput" type="password" class="loginFormInput" name="password"/></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input id="submitInput" type="submit" class="loginButton" value="Войти"/>
                </td>
            </tr>
        </table>
    </form>
</div>