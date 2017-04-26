<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Real estate agency - index</title>
  <jsp:include page="html_head_common.jsp" />
</head>
<body>
    <div class="wrapper">
        <jsp:include page="header.jsp" />
        <div class="content">
            <div>Описание сайта</div>
            <h2>Предложения</h2>
            <a href="/propertyFilter"><div class="buttonSimple" style="margin: 10px">Фильтр</div></a>
            <div>Здесь дефолтные предложения</div>
        </div>
        <jsp:include page="footer.jsp" />
    </div>
</body>
</html>