<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
	<head>
		<title>Real estate agency - index</title>
		<jsp:include page="../html_head_common.jsp" />
	</head>
	<body>
		<div class="wrapper">
			<jsp:include page="../header.jsp" />
			<div class="content">
				<h1>Фильтр собственности</h1>
				<div class="filterContentWrapper">
					<div class="filterBlockWrapper">
						<h2>Критерии поиска</h2>
						<div class="criteriaForm">
							<form action="/propertyFilter" method="post">
								<div class="criteriaBlock">
									<div class="criteriaLabel">Цена</div>
									<div class="criteriaRangeWrapper">
										<input type="text" name="costMin" class="criteriaInput criteriaRangeInputLeft" value="<c:out value="${filterParams['costMin']}"/>" />
										<input type="text" name="costMax" class="criteriaInput criteriaRangeInputRight" value="<c:out value="${filterParams['costMax']}"/>" />
									</div>
								</div>
								<div class="criteriaBlock">
									<div class="criteriaLabel">Площадь</div>
									<div class="criteriaRangeWrapper">
										<input type="text" name="areaMin" class="criteriaInput criteriaRangeInputLeft" value="<c:out value="${filterParams['areaMin']}"/>" />
										<input type="text" name="areaMax" class="criteriaInput criteriaRangeInputRight" value="<c:out value="${filterParams['areaMax']}"/>" />
									</div>
								</div>
								<div class="criteriaBlock">
									<div class="criteriaLabel">Количество комнат</div>
									<div class="criteriaRangeWrapper">
										<input type="text" name="roomCountMin" class="criteriaInput criteriaRangeInputLeft" value="<c:out value="${filterParams['roomCountMin']}"/>" />
										<input type="text" name="roomCountMax" class="criteriaInput criteriaRangeInputRight" value="<c:out value="${filterParams['roomCountMax']}"/>" />
									</div>
								</div>
								<div class="criteriaBlock">
									<div class="criteriaLabel">Расстояние до метро</div>
									<div class="criteriaRangeWrapper">
										<input type="text" name="distanceToSubwayMin" class="criteriaInput criteriaRangeInputLeft" value="<c:out value="${filterParams['distanceToSubwayMin']}"/>" />
										<input type="text" name="distanceToSubwayMax" class="criteriaInput criteriaRangeInputRight" value="<c:out value="${filterParams['distanceToSubwayMax']}"/>" />
									</div>
								</div>
								<div class="criteriaBlock">
									<div class="criteriaLabel">Расстояние до остановки транспорта</div>
									<div class="criteriaRangeWrapper">
										<input type="text" name="distanceToTransportStopMin" class="criteriaInput criteriaRangeInputLeft" value="<c:out value="${filterParams['distanceToTransportStopMin']}"/>" />
										<input type="text" name="distanceToTransportStopMax" class="criteriaInput criteriaRangeInputRight" value="<c:out value="${filterParams['distanceToTransportStopMax']}"/>" />
									</div>
								</div>
								<div>
									<h3>Удобства</h3>
									<div class="criteriaBlock">
										<div class="criteriaLabel">Мебель</div>
										<div class="criteriaValue">
											<input type="checkbox" name="furnitureCheck" />
										</div>
									</div>
									<div class="criteriaBlock">
										<div class="criteriaLabel">Телевизор</div>
										<div class="criteriaValue">
											<input type="checkbox" name="tvCheck" />
										</div>
									</div>
									<div class="criteriaBlock">
										<div class="criteriaLabel">Интернет</div>
										<div class="criteriaValue">
											<input type="checkbox" name="internetCheck" />
										</div>
									</div>
									<div class="criteriaBlock">
										<div class="criteriaLabel">Холодильник</div>
										<div class="criteriaValue">
											<input type="checkbox" name="fridgeCheck" />
										</div>
									</div>
									<div class="criteriaBlock">
										<div class="criteriaLabel">Телефон</div>
										<div class="criteriaValue">
											<input type="checkbox" name="phoneCheck" />
										</div>
									</div>
									<div class="criteriaBlock">
										<div class="criteriaLabel">Кухонная плита</div>
										<div class="criteriaValue">
											<input type="checkbox" name="stoveCheck" />
										</div>
									</div>
								</div>
								<input type="submit" value="Найти" />
							</form>
						</div>
					</div>
					<div class="filterResultWrapper">
						<h2>Результаты поиска</h2>
						<h3>Matches: ${fn:length(properties)}</h3>
                        <c:forEach var="property" items="${properties}">
                            <div>
                                <a href="/property?id=${property.id}">Собственность [${property.id}]</a>
                            </div>
                        </c:forEach>
					</div>
				</div>
			</div>
			<jsp:include page="../footer.jsp" />
		</div>
	</body>
</html>