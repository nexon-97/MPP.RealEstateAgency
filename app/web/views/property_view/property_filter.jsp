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
                        <h2 class="filterCriteriaLabel">Критерии поиска</h2>
                        <div class="criteriaForm">
                            <form action="/propertyFilter" method="post">
                                <div class="criteriaBlock">
                                    <div class="criteriaLabel">Цена</div>
                                    <div class="criteriaRangeWrapper">
                                        <input type="number" name="costMin" class="criteriaInput criteriaRangeInputLeft"
                                               value="<c:out value="${filterParams['costMin']}"/>" placeholder="От" />
                                        <input type="number" name="costMax" class="criteriaInput criteriaRangeInputRight"
                                               value="<c:out value="${filterParams['costMax']}"/>" placeholder="До" />
                                    </div>
                                </div>
                                <div class="criteriaBlock">
                                    <div class="criteriaLabel">Площадь</div>
                                    <div class="criteriaRangeWrapper">
                                        <input type="number" name="areaMin" class="criteriaInput criteriaRangeInputLeft"
                                               value="<c:out value="${filterParams['areaMin']}"/>" placeholder="От" />
                                        <input type="number" name="areaMax" class="criteriaInput criteriaRangeInputRight"
                                               value="<c:out value="${filterParams['areaMax']}"/>" placeholder="До" />
                                    </div>
                                </div>
                                <div class="criteriaBlock">
                                    <div class="criteriaLabel">Количество комнат</div>
                                    <div class="criteriaRangeWrapper">
                                        <input type="number" name="roomCountMin" class="criteriaInput criteriaRangeInputLeft"
                                               value="<c:out value="${filterParams['roomCountMin']}"/>" placeholder="От" />
                                        <input type="number" name="roomCountMax" class="criteriaInput criteriaRangeInputRight"
                                               value="<c:out value="${filterParams['roomCountMax']}"/>" placeholder="До" />
                                    </div>
                                </div>
                                <div class="criteriaBlock">
                                    <div class="criteriaLabel">Расстояние до метро</div>
                                    <div class="criteriaRangeWrapper">
                                        <input type="number" name="distanceToSubwayMin" class="criteriaInput criteriaRangeInputLeft"
                                               value="<c:out value="${filterParams['distanceToSubwayMin']}"/>" placeholder="От" />
                                        <input type="number" name="distanceToSubwayMax" class="criteriaInput criteriaRangeInputRight"
                                               value="<c:out value="${filterParams['distanceToSubwayMax']}"/>" placeholder="До" />
                                    </div>
                                </div>
                                <div class="criteriaBlock">
                                    <div class="criteriaLabel">Расстояние до остановки транспорта</div>
                                    <div class="criteriaRangeWrapper">
                                        <input type="number" name="distanceToTransportStopMin" class="criteriaInput criteriaRangeInputLeft"
                                               value="<c:out value="${filterParams['distanceToTransportStopMin']}"/>" placeholder="От" />
                                        <input type="number" name="distanceToTransportStopMax" class="criteriaInput criteriaRangeInputRight"
                                               value="<c:out value="${filterParams['distanceToTransportStopMax']}"/>" placeholder="До" />
                                    </div>
                                </div>
                                <div>
                                    <h3 class="comfortsLabel">Удобства</h3>
                                    <div class="criteriaBlock"/>
										<label>
											<div class="criteriaLabel">Мебель</div>
                                        	<div class="criteriaValue">
												<input type="checkbox" name="comforts" value="furniture" class="property-form-checkbox" <c:if test="${comfortsParams['furniture']}">checked</c:if> />
												<span class="property-form-checkbox-custom"></span>
											</div>
										</label>
                                    </div>
                                    <div class="criteriaBlock">
										<label>
											<div class="criteriaLabel">Телевизор</div>
											<div class="criteriaValue">
												<input type="checkbox" name="comforts" value="tv" class="property-form-checkbox" <c:if test="${comfortsParams['tv']}">checked</c:if> />
												<span class="property-form-checkbox-custom"></span>
											</div>
										</label>
                                    </div>
                                    <div class="criteriaBlock">
										<label>
											<div class="criteriaLabel">Интернет</div>
											<div class="criteriaValue">
												<input type="checkbox" name="comforts" value="internet" class="property-form-checkbox" <c:if test="${comfortsParams['internet']}">checked</c:if> />
												<span class="property-form-checkbox-custom"></span>
											</div>
										</label>
                                    </div>
                                    <div class="criteriaBlock">
										<label>
											<div class="criteriaLabel">Холодильник</div>
											<div class="criteriaValue">
												<input type="checkbox" name="comforts" value="fridge" class="property-form-checkbox" <c:if test="${comfortsParams['fridge']}">checked</c:if> />
												<span class="property-form-checkbox-custom"></span>
											</div>
										</label>
                                    </div>
                                    <div class="criteriaBlock">
										<label>
											<div class="criteriaLabel">Телефон</div>
											<div class="criteriaValue">
												<input type="checkbox" name="comforts" value="phone" class="property-form-checkbox" <c:if test="${comfortsParams['phone']}">checked</c:if> />
												<span class="property-form-checkbox-custom"></span>
											</div>
										</label>
                                    </div>
                                    <div class="criteriaBlock">
										<label>
											<div class="criteriaLabel">Кухонная плита</div>
											<div class="criteriaValue">
												<input type="checkbox" name="comforts" value="stove" class="property-form-checkbox" <c:if test="${comfortsParams['stove']}">checked</c:if> />
												<span class="property-form-checkbox-custom"></span>
											</div>
										</label>
                                    </div>
                                </div>
                                <input type="submit" value="Найти" class="buttonSimple" />
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