<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Real estate agency - index</title>
    <jsp:include page="../html_head_common.jsp" />
</head>
<body>
<div class="wrapper">
    <jsp:include page="../header.jsp" />
    <div class="content">
        <div class="propertyTableWrapper">
            <div class="propertyDescriptionTable">
                <table style="width: 100%">
                    <tr>
                        <td colspan="2" class="propertyDescriptionTableHeaderCell">
                            <div class="propertyDescriptionTableHeader">Собственность [${property.id}]</div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" class="propertyInfoNameCell">Описание:</td>
                    </tr>
                    <tr>
                        <td colspan="2" class="propertyDescriptionCell">
                            <div class="propertyDescription">${property.description}</div>
                        </td>
                    </tr>
                    <tr>
                        <td class="propertyInfoNameCell" width="25%">Площадь</td>
                        <td>${property.area} м^2</td>
                    </tr>
                    <tr>
                        <td class="propertyInfoNameCell">Адрес</td>
                        <td>
                            <span>${property.city}, ${property.street}, ${property.houseNumber}</span>
                            <c:if test="${property.flatNumber != null}">
                                <span>, ${property.flatNumber}</span>
                            </c:if>
                        </td>
                    </tr>
                    <c:if test="${property.roomsCount != null}">
                        <tr>
                            <td class="propertyInfoNameCell">Количество комнат</td>
                            <td>${property.roomsCount}</td>
                        </tr>
                    </c:if>
                    <c:if test="${property.distanceToSubway != null}">
                        <tr>
                            <td class="propertyInfoNameCell">Расстояние до метро</td>
                            <td>${property.distanceToSubway} м</td>
                        </tr>
                    </c:if>
                    <c:if test="${property.distanceToTransportStop != null}">
                        <tr>
                            <td class="propertyInfoNameCell">Расстояние до остановки</td>
                            <td>${property.distanceToTransportStop} м</td>
                        </tr>
                    </c:if>
                    <tr>
                        <td class="propertyInfoNameCell">Удобства:</td>
                        <td>
                            <div class="comforts">
                                <div class="convenienceIcon">Мебель: ${property.hasFurniture ? "Есть" : "Нет"}</div>
                                <div class="convenienceIcon">Телевизор: ${property.hasTv ? "Есть" : "Нет"}</div>
                                <div class="convenienceIcon">Интернет: ${property.hasInternet ? "Есть" : "Нет"}</div>
                                <div class="convenienceIcon">Холодильник: ${property.hasFridge ? "Есть" : "Нет"}</div>
                                <div class="convenienceIcon">Плита: ${property.hasStove ? "Есть" : "Нет"}</div>
                                <div class="convenienceIcon">Телефон: ${property.hasPhone ? "Есть" : "Нет"}</div>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <jsp:include page="../footer.jsp" />
</div>
</body>
</html>