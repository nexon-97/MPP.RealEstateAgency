<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="personalAreaLabel" style="margin-top: 15px">Неподтвержденные предложения сделок</div>
<c:forEach var="dealRequest" items="${uncommittedRequests}">
    <div>
        <span>Отклик на предложение [${dealRequest.offer.id}]</span>
        <a href="${pageContext.request.contextPath}/confirmDealRealtor?id=${dealRequest.id}&amp;realtor=${user.id}">
            <div class="buttonSimple deal-seller-confirm-button">Подтвердить участие</div>
        </a>
    </div>
</c:forEach>