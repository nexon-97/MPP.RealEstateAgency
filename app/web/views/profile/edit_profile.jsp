<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <div class="profileLogin">${user.login}</div>
            <form method="post" action="/profileEdit" accept-charset="UTF-8">
                <div class="personalInfoBlockWrapper">
                    <div class="personalInfoBlock">
                        <div>
                            <div class="contactInfoBlockNameDiv">Фамилия</div>
                            <div class="contactInfoBlockValueDiv">
                                <input type="text" name="surname" value="${user.surname}" class="contactInfoBlockValueInput"/>
                            </div>
                        </div>
                        <div>
                            <div class="contactInfoBlockNameDiv">Имя</div>
                            <div class="contactInfoBlockValueDiv">
                                <input type="text" name="name" value="${user.name}" class="contactInfoBlockValueInput" />
                            </div>
                        </div>
                        <div>
                            <div class="contactInfoBlockNameDiv">Отчество</div>
                            <div class="contactInfoBlockValueDiv">
                                <input type="text" name="patronymic" value="${user.patronymic}" class="contactInfoBlockValueInput" />
                            </div>
                        </div>
                        <div>
                            <div class="contactInfoBlockNameDiv">E-mail</div>
                            <div class="contactInfoBlockValueDiv">
                                <input type="text" name="email" value="${user.email}" class="contactInfoBlockValueInput" />
                            </div>
                        </div>
                        <div>
                            <div class="contactInfoBlockNameDiv">Телефон</div>
                            <div class="contactInfoBlockValueDiv">
                                <input type="text" name="phone" value="${user.phone}" class="contactInfoBlockValueInput" />
                            </div>
                        </div>
                        <div>
                            <div class="contactInfoBlockNameDiv">О себе</div>
                        </div>
                        <div>
                            <textarea name="info" class="personalInfoTextInput">${user.info}</textarea>
                        </div>
                    </div>
                </div>
                <div>
                    <input type="submit" value="Сохранить" class="buttonSimple" />
                    <a href="/profile"><div class="buttonSimple">Отмена</div></a>
                </div>
            </form>
        </div>
    </div>
    <jsp:include page="../footer.jsp" />
</div>
</body>
</html>