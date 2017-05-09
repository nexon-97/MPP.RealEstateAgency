<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Real estate agency - отчет работы компании</title>
    <jsp:include page="html_head_common.jsp" />
</head>
<body>
<jsp:include page="header.jsp" />

<h1>Отчет работы компании </h1>
<div class="content">
    <div class="wrapper">
        <table class="company-report-table">
            <tr class="elem-work-report-tr">
                <th class="elem-work-report-td">Покупатель</th>
                <th class="elem-work-report-td">Продавец</th>
                <th class="elem-work-report-td">Дата проведения транзакции</th>
                <th class="elem-work-report-td">Прибыль компании</th>
            </tr>
            <c:forEach var="transaction" items="${transactions}">
                <tr class="elem-work-report-tr">
                    <c:if test="${transaction != null}">
                        <td class="elem-work-report-td">${transaction.buyer.name} ${transaction.buyer.surname} ${transaction.buyer.patronymic}</td>
                        <td class="elem-work-report-td">${transaction.seller.name} ${transaction.seller.surname} ${transaction.seller.patronymic}</td>
                        <td class="elem-work-report-td"><fmt:formatDate value="${transaction.date}" pattern="MM/dd/yyyy HH:mm" /></td>
                        <td class="elem-work-report-td">${transaction.companyFine}</td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
        <div class="sum-report-fine">Общая прибыль компании: ${totalFine} у.е.</div>
    </div>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>