<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Real estate agency - Добавление недвижимости</title>
    <jsp:include page="../html_head_common.jsp" />
</head>
<body>
<div class="wrapper">
    <jsp:include page="../header.jsp" />
    <div class="content">
        <h1>Add property page</h1>
        <div class="property-form-wrapper">
            <form:form method="post" action="/addProperty" modelAttribute="property" id="property-form">
                <table>
                    <tr>
                        <td width="30%">Property Type:</td>
                        <td>
                            <form:errors path="type"><div class="error-field">Неопознанный тип собственности</div></form:errors>
                            <form:select path="type" items="${propertyTypes}" cssClass="property-form-input" />
                        </td>
                    </tr>
                    <tr>
                        <td>City:</td>
                        <td>
                            <form:errors path="city"><div class="error-field">Должно содержать только русские буквы(А-ЯЁ, а-яё), цифры(0-9), ('), (-) и пробел</div></form:errors>
                            <form:input path="city" cssClass="property-form-input" />
                        </td>
                    </tr>
                    <tr>
                        <td>Street:</td>
                        <td>
                            <form:errors path="street"><div class="error-field">Должно содержать только русские буквы(А-ЯЁ, а-яё), цифры(0-9), ('), (-) и пробел</div></form:errors>
                            <form:input path="street" cssClass="property-form-input" />
                        </td>
                    </tr>
                    <tr>
                        <td>House number:</td>
                        <td>
                            <form:errors path="houseNumber"><div class="error-field">Должно быть больше 0</div></form:errors>
                            <form:input path="houseNumber" cssClass="property-form-input" />
                        </td>
                    </tr>
                    <tr>
                        <td>Block number:</td>
                        <td>
                            <form:errors path="blockNumber"><div class="error-field">Должно быть больше 0</div></form:errors>
                            <form:input path="blockNumber" cssClass="property-form-input" />
                        </td>
                    </tr>
                    <tr>
                        <td>Flat number:</td>
                        <td>
                            <form:errors path="flatNumber"><div class="error-field">Должно быть больше 0</div></form:errors>
                            <form:input path="flatNumber" cssClass="property-form-input" />
                        </td>
                    </tr>
                    <tr>
                        <td>Rooms count:</td>
                        <td>
                            <form:errors path="roomsCount"><div class="error-field">Должно быть больше 0</div></form:errors>
                            <form:input path="roomsCount" cssClass="property-form-input" />
                        </td>
                    </tr>
                    <tr>
                        <td>Area:</td>
                        <td>
                            <form:errors path="area"><div class="error-field">Должно быть больше 0</div></form:errors>
                            <form:input path="area" cssClass="property-form-input" />
                        </td>
                    </tr>
                    <tr>
                        <td>Distance to subway:</td>
                        <td>
                            <form:errors path="distanceToSubway"><div class="error-field">Дистанция не должна быть отрицательной</div></form:errors>
                            <form:input path="distanceToSubway" cssClass="property-form-input" />
                        </td>
                    </tr>
                    <tr>
                        <td>Distance to bus stop:</td>
                        <td>
                            <form:errors path="distanceToTransportStop"><div class="error-field">Дистанция не должна быть отрицательной</div></form:errors>
                            <form:input path="distanceToTransportStop" cssClass="property-form-input" />
                        </td>
                    </tr>
                    <tr>
                        <td>Furniture:</td>
                        <td>
                            <form:checkbox path="hasFurniture"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Internet:</td>
                        <td>
                            <form:checkbox path="hasInternet"/>
                        </td>
                    </tr>
                    <tr>
                        <td>TV:</td>
                        <td>
                            <form:checkbox path="hasTv"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Phone:</td>
                        <td>
                            <form:checkbox path="hasPhone"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Fridge:</td>
                        <td>
                            <form:checkbox path="hasFridge"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Stove:</td>
                        <td>
                            <form:checkbox path="hasStove"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Description:</td>
                        <td>
                            <form:textarea path="description" cssClass="property-form-textarea"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input id="submitInput" type="submit" class="property-form-button" value="Add"/>
                        </td>
                    </tr>
                </table>
            </form:form>
        </div>
    </div>
    <jsp:include page="../footer.jsp" />
</div>
</body>
</html>