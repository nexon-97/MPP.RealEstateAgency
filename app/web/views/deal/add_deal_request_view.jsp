<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Отклик на предложение</title>
    <jsp:include page="../html_head_common.jsp" />
</head>
<body>
<div class="wrapper">
    <jsp:include page="../header.jsp" />
    <div class="content">
        <div class="profileViewContent">
            <h2 style="margin-top: 25px;">Отклик на предложение</h2>
            <div style="padding: 10px">
                <form method="get" action="/addDealRequest">
                    <div style="text-align: left">
                        Выберите риэлтора, который помог вам в поиске этого предложения!
                    </div>
                    <div>
                        <select class="property-form-input" name="realtor">
                            <c:forEach var="realtor" items="${realtorsList}">
                                <option value="${realtor.id}">${realtor.surname} ${realtor.name} ${realtor.patronymic}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <input type="submit" value="Добавить" class="buttonSimple" style="margin: 10px" />
                    <input type="hidden" name="id" value="${offer.id}" />
                    <input type="hidden" name="buyer" value="${user.id}" />
                </form>
                <form method="get" action="/addDealRequest">
                    <input type="submit" value="Пропустить" class="buttonSimple" />
                    <input type="hidden" name="id" value="${offer.id}" />
                    <input type="hidden" name="buyer" value="${user.id}" />
                    <input type="hidden" name="realtor" value="0" />
                </form>
            </div>
        </div>
    </div>
    <jsp:include page="../footer.jsp" />
</div>
</body>
</html>