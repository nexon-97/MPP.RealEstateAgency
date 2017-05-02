<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>User profile</title>
    <jsp:include page="../html_head_common.jsp" />
</head>
<body>
<div class="wrapper">
    <jsp:include page="../header.jsp" />
    <div class="content">
        <div class="profileViewContent">
            <c:choose>
                <c:when test="${user != null}">
                    <div class="personalAreaLabel">Личный кабинет</div>
                    <div class="personalInfoBlockWrapper">
                        <div class="personalInfoBlock">
                            <div class="profileLogin">${user.login} [${user.roleId}]</div>
                            <div>${user.surname} ${user.name} ${user.patronymic}</div>
                            <div class="contactInfoBlock">
                                <div>
                                    <span class="contactInfoBlockName">E-mail</span>
                                    <span>${user.email}</span>
                                </div>
                                <c:if test="${user.phone != null and fn:length(user.phone) > 0}">
                                    <div>
                                        <span class="contactInfoBlockName">Телефон</span>
                                        <span>${user.phone}</span>
                                    </div>
                                </c:if>
                            </div>
                            <c:if test="${user.info != null and fn:length(user.info) > 0}">
                            <div>О себе:</div>
                            <div class="personalInfoField">${user.info}</div>
                            </c:if>
                        </div>
                    </div>
                    <div>
                        <form method="get" action="/profileEdit">
                            <input type="submit" value="Изменить" class="buttonSimple" />
                        </form>
                    </div>
                    <div class="personalAreaLabel" style="margin-top: 15px">Собственность</div>
                    <c:forEach var="property" items="${userProperties}">
                        <div>
                            <a href="/property?id=${property.id}">Собственность [${property.id}]</a>
                        </div>
                    </c:forEach>
                    <div>
                        <div class="buttonSimple" style="margin-top: 10px;">
                            <a href="/addProperty">Добавить</a>
                        </div>
                    </div>
                    <div class="personalAreaLabel" style="margin-top: 15px">Предложения</div>
                    <c:choose>
                        <c:when test="${fn:length(userProperties) > 0}">
                            <div class="offers-container">
                                 <c:forEach var="offer" items="${userOffers}">
                                     <c:set var="offer" value="${offer}" scope="request" />
                                     <jsp:include page="../offer/offer_compact_view.jsp" />
                                 </c:forEach>
                            </div>
                            <div>
                                <div class="buttonSimple">
                                    <a href="/addOffer">Добавить</a>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            Добавьте собственность, прежде чем добавлять предложения!
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <div><h2>Вы не авторизированы!</h2></div>
                    <div class="buttonSimple" style="margin-top: 10px;">
                        <a href="/auth">Войти</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <jsp:include page="../footer.jsp" />
</div>
</body>
</html>