<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="property" value="${offer.property}"/>
<div class="propertyTableWrapper">
    <div class="propertyDescriptionTable">
        <table style="width: 100%">
            <tr>
                <td colspan="2" class="propertyDescriptionTableHeaderCell">
                    <div class="propertyDescriptionTableHeader">
                        <h3>Предложение [${offer.id}]</h3>
                        <c:set var="property" value="${offer.property}" />
                        <c:if test="${property.owner.id == user.id}">
                            <a href="/editOffer?id=${offer.id}"><div class="pen-button-full"><img src="/img/pen.png" /></div></a>
                        </c:if>
                        <c:if test="${property.owner.id == user.id || user.roleId == 'Admin'}">
                            <a href="/deleteOffer?id=${offer.id}"><div class="delete-button-full"><img src="/img/cross.png" /></div></a>
                        </c:if>
                    </div>
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
                <td class="propertyInfoNameCell" width="25%">Владелец</td>
                <c:set var="owner" value="${property.owner}"/>
                <td><a href="/user?id=${owner.id}">${owner.surname} ${owner.name} ${owner.patronymic}</a></td>
            </tr>
            <tr>
                <td class="propertyInfoNameCell" width="25%">Тип сделки</td>
                <td>${offer.offerType}</td>
            </tr>
            <tr>
                <td class="propertyInfoNameCell" width="25%">Стоимость</td>
                <td>${offer.cost} $</td>
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
                <td class="propertyInfoNameCell">Удобства</td>
                <td>
                    <div class="comforts">
                        <div class="convenienceIconBack${property.hasFurniture ? "Yes" : "No"}">
                            <img src="img/furniture_icon_${property.hasFurniture ? "yes" : "no"}.png" width="64" />
                        </div>
                        <div class="convenienceIconBack${property.hasTv ? "Yes" : "No"}">
                            <img src="img/tv_icon_${property.hasTv ? "yes" : "no"}.png" width="64" />
                        </div>
                        <div class="convenienceIconBack${property.hasPhone ? "Yes" : "No"}">
                            <img src="img/phone_icon_${property.hasPhone ? "yes" : "no"}.png" width="64" />
                        </div>
                        <div class="convenienceIconBack${property.hasInternet ? "Yes" : "No"}">
                            <img src="img/wifi_icon_${property.hasInternet ? "yes" : "no"}.png" width="64" />
                        </div>
                        <div class="convenienceIconBack${property.hasFridge ? "Yes" : "No"}">
                            <img src="img/fridge_icon_${property.hasFridge ? "yes" : "no"}.png" width="64" />
                        </div>
                        <div class="convenienceIconBack${property.hasStove ? "Yes" : "No"}">
                            <img src="img/stove_icon_${property.hasStove ? "yes" : "no"}.png" width="64" />
                        </div>
                    </div>
                </td>
            </tr>
        </table>
        <div style="padding: 15px">
           <a href="/addDealRequest?id=${offer.id}&amp;buyer=${user.id}"><div class="buttonSimple">Откликнуться</div></a>
        </div>
    </div>
</div>