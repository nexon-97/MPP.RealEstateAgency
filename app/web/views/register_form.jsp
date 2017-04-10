<%--
  Created by IntelliJ IDEA.
  User: Alex
  Date: 09.04.2017
  Time: 23:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="loginFormWrapper">
    <form id="registerForm" method="post" action="/auth">
        <table>
            <tr>
                <td width="30%">Login</td>
                <td><input id="registerInput" type="text"  class="loginFormInput" name="login"/></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input id="passwordInput" type="password" class="loginFormInput" name="password"/></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input id="submitInput" type="submit" class="loginButton" value="Login"/>
                </td>
            </tr>
        </table>
    </form>
</div>
