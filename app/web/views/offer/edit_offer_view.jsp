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
            <form method="post" action="/editOffer?id=${offer.id}">
                <div>Собственность</div>
                <div>
                    <select class="property-form-input" name="property">
                        <c:forEach var="property" items="${userProperties}">
                            <option value="${property.id}"<c:if test="${property.id == offer.property.id}"> selected="selected"</c:if>>${property.city}, ${property.street}, ${property.houseNumber}</option>
                        </c:forEach>
                    </select>
                </div>
                <div>Тип предложения</div>
                <div>
                    <select class="property-form-input" name="offerType">
                        <c:forEach var="offerType" items="${offerTypes}">
                            <option<c:if test="${currentOfferType == offerType}"> selected="selected"</c:if>>${offerType}</option>
                        </c:forEach>
                    </select>
                </div>
                <div>
                    Стоимость <input type="number" class="property-form-input" name="cost" value="${offer.cost}"/>
                </div>
                <div style="text-align: center; margin-top: 10px;">
                    <input type="submit" value="Изменить" class="buttonSimple" />
                </div>
            </form>
        </div>
    </div>
    <jsp:include page="../footer.jsp" />
</div>
</body>
</html>