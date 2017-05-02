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
                            <option value="${property.id}"<c:if test="${i.index == 0}"> selected="selected"</c:if>>${property.city}, ${property.street}, ${property.houseNumber}</option>
                        </c:forEach>
                    </select>
                </div>
                <div>Тип предложения</div>
                <div>
                    <select class="property-form-input" name="offerType">
                        <c:forEach var="offerType" items="${offerTypes}">
                            <option>${offerType}</option>
                        </c:forEach>
                    </select>
                </div>
                <div>
                    Стоимость <input type="number" class="property-form-input" name="cost" value="0"/>
                </div>
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