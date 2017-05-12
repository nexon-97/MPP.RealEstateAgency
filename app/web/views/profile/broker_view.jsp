<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div style="text-align: left">
    <h2>История сделок</h2>
    <div>
        <c:forEach var="deal" items="${dealHistory}">
            <c:set var="owner" value="${deal.offer.property.owner}" />
            <div class="deal-short-view">
                <div class="deal-short-view-header">Сделка [${deal.id}]</div>
                <div class="deal-short-view-info">
                    <table>
                        <tr>
                            <td class="deal-short-view-property-name">Продавец</td>
                            <td class="deal-short-view-property-value">
                                <c:choose>
                                    <c:when test="${owner != null}">
                                        <a href="/user?id=${owner.id}">${owner.surname} ${owner.name} ${owner.patronymic}</a>
                                    </c:when>
                                    <c:otherwise>
                                        Нет
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td class="deal-short-view-property-name">Покупатель</td>
                            <td class="deal-short-view-property-value">
                                <c:choose>
                                    <c:when test="${deal.buyer != null}">
                                        <a href="/user?id=${deal.buyer.id}">${deal.buyer.surname} ${deal.buyer.name} ${deal.buyer.patronymic}</a>
                                    </c:when>
                                    <c:otherwise>
                                        Нет
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td class="deal-short-view-property-name">Брокер</td>
                            <td class="deal-short-view-property-value">
                                <c:choose>
                                    <c:when test="${deal.broker != null}">
                                        <a href="/user?id=${deal.broker.id}">${deal.broker.surname} ${deal.broker.name} ${deal.broker.patronymic}</a>
                                    </c:when>
                                    <c:otherwise>
                                        Нет
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td class="deal-short-view-property-name">Риэлтор</td>
                            <td class="deal-short-view-property-value">
                                <c:choose>
                                    <c:when test="${deal.realtor != null}">
                                        <a href="/user?id=${deal.realtor.id}">${deal.realtor.surname} ${deal.realtor.name} ${deal.realtor.patronymic}</a>
                                    </c:when>
                                    <c:otherwise>
                                        Нет
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </c:forEach>
    </div>
    <h2>Сделки в обработке</h2>
    <div>
        <c:forEach var="deal" items="${unsignedDeals}">
            <c:set var="owner" value="${deal.offer.property.owner}" />
            <div class="deal-short-view">
                <div class="deal-short-view-header">Сделка [${deal.id}]</div>
                <div class="deal-short-view-info">
                    <table>
                        <tr>
                            <td class="deal-short-view-property-name">Продавец</td>
                            <td class="deal-short-view-property-value">
                                <c:choose>
                                    <c:when test="${owner != null}">
                                        <a href="/user?id=${owner.id}">${owner.surname} ${owner.name} ${owner.patronymic}</a>
                                    </c:when>
                                    <c:otherwise>
                                        Нет
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td class="deal-short-view-property-name">Покупатель</td>
                            <td class="deal-short-view-property-value">
                                <c:choose>
                                    <c:when test="${deal.buyer != null}">
                                        <a href="/user?id=${deal.buyer.id}">${deal.buyer.surname} ${deal.buyer.name} ${deal.buyer.patronymic}</a>
                                    </c:when>
                                    <c:otherwise>
                                        Нет
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td class="deal-short-view-property-name">Риэлтор</td>
                            <td class="deal-short-view-property-value">
                                <c:choose>
                                    <c:when test="${deal.realtor != null}">
                                        <a href="/user?id=${deal.realtor.id}">${deal.realtor.surname} ${deal.realtor.name} ${deal.realtor.patronymic}</a>
                                    </c:when>
                                    <c:otherwise>
                                        Нет
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </table>
                    <div style="text-align: center; padding: 4px;">
                        <form method="post" action="/confirmDealBroker?id=${deal.id}&amp;user=${user.id}">
                            <input type="submit" value="Заверить" class="buttonSimple deal-broker-confirm-button" />
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>