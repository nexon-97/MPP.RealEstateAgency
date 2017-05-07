<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Страница пользователя</title>
    <jsp:include page="../html_head_common.jsp" />
</head>
<body>
<div class="wrapper">
    <jsp:include page="../header.jsp" />
    <div class="content">
        <div class="profileViewContent">
            <div class="personalAreaLabel">Личный кабинет</div>
            <div class="personalInfoBlockWrapper">
                <div class="personalInfoBlock">
                    <div class="profileLogin">${selectedUser.login} [${selectedUser.roleId}]</div>
                    <div>${selectedUser.surname} ${selectedUser.name} ${selectedUser.patronymic}</div>
                    <div class="contactInfoBlock">
                        <div>
                            <span class="contactInfoBlockName">E-mail</span>
                            <span>${selectedUser.email}</span>
                        </div>
                        <c:if test="${selectedUser.phone != null and fn:length(selectedUser.phone) > 0}">
                            <div>
                                <span class="contactInfoBlockName">Телефон</span>
                                <span>${selectedUser.phone}</span>
                            </div>
                        </c:if>
                    </div>
                    <c:if test="${selectedUser.info != null and fn:length(selectedUser.info) > 0}">
                        <div>О себе:</div>
                        <div class="personalInfoField">${selectedUser.info}</div>
                    </c:if>
                </div>
            </div>
            <c:if test="${fn:length(userProperties) > 0}">
                <div class="personalAreaLabel" style="margin-top: 15px">Собственность</div>
                <c:forEach var="property" items="${userProperties}">
                    <div>
                        <a href="/property?id=${property.id}">Собственность [${property.id}]</a>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${fn:length(userOffers) > 0}">
                <div class="personalAreaLabel" style="margin-top: 15px">Предложения</div>
                <div class="offers-container">
                    <c:forEach var="offer" items="${userOffers}">
                        <c:set var="offer" value="${offer}" scope="request" />
                        <jsp:include page="../offer/offer_compact_view.jsp" />
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </div>
    <jsp:include page="../footer.jsp" />
</div>
</body>
</html>