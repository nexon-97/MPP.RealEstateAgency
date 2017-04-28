<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Real estate agency - Add Property</title>
    <jsp:include page="../html_head_common.jsp" />
</head>
<body>
<div class="wrapper">
    <jsp:include page="../header.jsp" />
    <div class="content">
        <h1>Add property page</h1>
        <div class="property-form-wrapper">
            <form id="property-form" method="post" action="/addProperty">
                <table>
                    <tr>
                        <td width="30%">Property Type:</td>
                        <td>
                            <select id="propertyType" class="property-form-input" name="type">
                                <c:forEach var="item" items="${types}">
                                    <option>${item}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>City:</td>
                        <td>
                            <c:if test="${(propertyInfo != null) && (propertyInfo.city == null)}">
                                <div id="house-number-error" class="error-field">Должно содержать только русские буквы(А-ЯЁ, а-яё), цифры(0-9), ('), (-) и пробел</div>
                            </c:if>
                            <input id="cityInput" type="text" class="property-form-input" name="city"
                                   value="<c:if test="${(propertyInfo != null) && (propertyInfo.city != null)}">${propertyInfo.city}</c:if>"
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>Street:</td>
                        <td>
                            <c:if test="${(propertyInfo != null) && (propertyInfo.street == null)}">
                                <div id="house-number-error" class="error-field">Должно содержать только русские буквы(А-ЯЁ, а-яё), цифры(0-9), ('), (-) и пробел</div>
                            </c:if>
                            <input id="streetInput" type="text" class="property-form-input" name="street"
                                   value="<c:if test="${(propertyInfo != null) && (propertyInfo.street != null)}">${propertyInfo.street}</c:if>"
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>House number:</td>
                        <td>
                            <c:if test="${(propertyInfo != null) && (propertyInfo.houseNumber == -1)}">
                                <div id="house-number-error" class="error-field">Должно быть больше 0</div>
                            </c:if>
                            <input id="houseNumberInput" type="number" class="property-form-input" name="houseNumber"
                                   value="<c:if test="${(propertyInfo != null) && (propertyInfo.houseNumber != -1)}">${propertyInfo.houseNumber}</c:if>"
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>Block number:</td>
                        <td>
                            <c:if test="${(propertyInfo != null) && (propertyInfo.blockNumber == -1)}">
                                <div id="house-number-error" class="error-field">Должно быть больше 0</div>
                            </c:if>
                            <input id="blockNumberInput" type="number" class="property-form-input" name="blockNumber"
                                   value="<c:if test="${(propertyInfo != null) && (propertyInfo.blockNumber != -1)}">${propertyInfo.blockNumber}</c:if>"
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>Flat number:</td>
                        <td>
                            <c:if test="${(propertyInfo != null) && (propertyInfo.flatNumber == -1)}">
                                <div id="house-number-error" class="error-field">Должно быть больше 0</div>
                            </c:if>
                            <input id="flatNumberInput" type="number" class="property-form-input" name="flatNumber"
                                value="<c:if test="${(propertyInfo != null) && (propertyInfo.flatNumber != -1)}">${propertyInfo.flatNumber}</c:if>"
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>Rooms count:</td>
                        <td>
                            <c:if test="${(propertyInfo != null) && (propertyInfo.roomsCount == -1)}">
                                <div id="house-number-error" class="error-field">Должно быть больше 0</div>
                            </c:if>
                            <input id="roomsCountInput" type="number" class="property-form-input" name="roomsCount"
                                   value="<c:if test="${(propertyInfo != null) && (propertyInfo.roomsCount != -1)}">${propertyInfo.roomsCount}</c:if>"
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>Area:</td>
                        <td>
                            <c:if test="${(propertyInfo != null) && (propertyInfo.area == -1)}">
                                <div id="house-number-error" class="error-field">Должно быть больше 0</div>
                            </c:if>
                            <input id="areaInput" type="number" class="property-form-input" name="area"
                                   value="<c:if test="${(propertyInfo != null) && (propertyInfo.area != -1)}">${propertyInfo.area}</c:if>"
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>Distance to subway:</td>
                        <td>
                            <c:if test="${(propertyInfo != null) && (propertyInfo.distanceToSubway == -1)}">
                                <div id="house-number-error" class="error-field">Дистанция не должна быть отрицательной</div>
                            </c:if>
                            <input id="subwayInput" type="number" class="property-form-input" name="subway"
                                   value="<c:if test="${(propertyInfo != null) && (propertyInfo.distanceToSubway != -1)}">${propertyInfo.distanceToSubway}</c:if>"
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>Distance to bus stop:</td>
                        <td>
                            <c:if test="${(propertyInfo != null) && (propertyInfo.distanceToTransportStop == -1)}">
                                <div id="house-number-error" class="error-field">Дистанция не должна быть отрицательной</div>
                            </c:if>
                            <input id="busInput" type="number" class="property-form-input" name="bus"
                                   value="<c:if test="${(propertyInfo != null) && (propertyInfo.distanceToTransportStop != -1)}">${propertyInfo.distanceToTransportStop}</c:if>"
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>Furniture:</td>
                        <td>
                            <label>
                                <input id="furnitureInput" type="checkbox" class="property-form-checkbox" name="furniture"
                                        <c:if test="${(propertyInfo != null) && (propertyInfo.hasFurniture)}">checked</c:if>
                                />
                                <span class="property-form-checkbox-custom"></span>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td>Internet:</td>
                        <td>
                            <label>
                                <input id="internetInput" type="checkbox" class="property-form-checkbox" name="internet"
                                       <c:if test="${(propertyInfo != null) && (propertyInfo.hasInternet)}">checked</c:if>
                                />
                                <span class="property-form-checkbox-custom"></span>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td>TV:</td>
                        <td>
                            <label>
                                <input id="tvInput" type="checkbox" class="property-form-checkbox" name="tv"
                                       <c:if test="${(propertyInfo != null) && (propertyInfo.hasTv)}">checked</c:if>
                                />
                                <span class="property-form-checkbox-custom"></span>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td>Phone:</td>
                        <td>
                            <label>
                                <input id="phoneInput" type="checkbox" class="property-form-checkbox" name="phone"
                                       <c:if test="${(propertyInfo != null) && (propertyInfo.hasPhone)}">checked</c:if>
                                />
                                <span class="property-form-checkbox-custom"></span>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td>Fridge:</td>
                        <td>
                            <label>
                                <input id="fridgeInput" type="checkbox" class="property-form-checkbox" name="fridge"
                                       <c:if test="${(propertyInfo != null) && (propertyInfo.hasFridge)}">checked</c:if>
                                />
                                <span class="property-form-checkbox-custom"></span>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td>Stove:</td>
                        <td>
                            <label>
                                <input id="stoveInput" type="checkbox" class="property-form-checkbox" name="stove"
                                       <c:if test="${(propertyInfo != null) && (propertyInfo.hasStove)}">checked</c:if>
                                />
                                <span class="property-form-checkbox-custom"></span>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td>Description:</td>
                        <td>
                            <textarea id="descriptionInput" class="property-form-textarea" name="description" rows="6"><c:if test="${(propertyInfo != null) && (propertyInfo.description != null)}">${propertyInfo.description}</c:if></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input id="submitInput" type="submit" class="property-form-button" value="Add"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <jsp:include page="../footer.jsp" />
</div>
</body>
</html>