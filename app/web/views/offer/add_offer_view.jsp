<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Предложение</title>
    <jsp:include page="../html_head_common.jsp" />
</head>
<body>
<div class="wrapper">
    <jsp:include page="../header.jsp" />
    <div class="content">
        <div class="add-offer-form-wrapper">
            <form method="post" action="/addOffer">
                <div>Собственность</div>
                <div>
                    <select class="property-form-input" name="property">
                        <c:forEach var="property" items="${userProperties}" varStatus="i">
                            <option value="${property.id}"
                            <c:choose>
                                <c:when test="${offer != null}">
                                    <c:if test="${offer.property.id == property.id}"> selected="selected"</c:if>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${i.index == 0}"> selected="selected"</c:if>
                                </c:otherwise>
                            </c:choose>
                            >${property.city}, ${property.street}, ${property.houseNumber}</option>
                        </c:forEach>
                    </select>
                </div>
                <c:if test="${errors != null && errors['property'] != null}">
                    <div class="error-field">${errors['property']}</div>
                </c:if>
                <div>Тип предложения</div>
                <div>
                    <select class="property-form-input" name="offerType">
                        <c:forEach var="offerType" items="${offerTypes}">
                            <option value="${offerType.ordinal()}"
                                <c:choose>
                                    <c:when test="${offer != null}">
                                        <c:if test="${offer.offerType == offerType}"> selected="selected"</c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${i.index == 0}"> selected="selected"</c:if>
                                    </c:otherwise>
                                </c:choose>
                            >${offerType}</option>
                        </c:forEach>
                    </select>
                </div>
                <c:if test="${errors != null && errors['offerType'] != null}">
                    <div class="error-field">${errors['offerType']}</div>
                </c:if>
                <div>Стоимость</div>
                <input type="text" class="property-form-input" name="cost" value="<c:choose><c:when test="${offer != null}">${offer.cost}</c:when><c:otherwise>0</c:otherwise></c:choose>"/>
                <c:if test="${errors != null && errors['cost'] != null}">
                    <div class="error-field">${errors['cost']}</div>
                </c:if>
                <div style="text-align: center; margin-top: 10px;">
                    <input type="submit" value="Добавить" class="buttonSimple" />
                </div>
            </form>
        </div>
    </div>
    <jsp:include page="../footer.jsp" />
</div>
</body>
</html>