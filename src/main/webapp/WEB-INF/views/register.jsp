<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "title" value = "Регистрация"/>
<c:set var = "form_width" value = "40"/>
<%@include file="/WEB-INF/views/components/_header.jsp"%>

<form method="post">
    <div id="reg-title">Регистрация</div>
    <input name="name" class="form-control" type="text" placeholder="Имя" aria-label="default input example" required>
    <input name="second-name" class="form-control" type="text" placeholder="Фамилия" aria-label="default input example" required>
    <input name="phone-number" class="form-control"  type="tel" placeholder="Номер телефона" pattern="^((8|\+7)[\- ]?)?(\(?\d{3}\)?[\- ]?)?[\d\- ]{7,10}$" value="+7" required>
    <div class="mb-3">
        <label for="email" class="form-label">Email</label>
        <input name="email" type="email" class="form-control" id="email" aria-describedby="emailHelp" required="">
        <div id="emailHelp" class="form-text">Никто не узнает ваши данные</div>
    </div>
    <div class="mb-3">
        <label for="pass" class="form-label">Password</label>
        <input name="pass" type="password" class="form-control" id="pass" required>
    </div>

    <c:if test="${error != null}">
        <div id="error">${error}</div>
    </c:if>

    <button type="submit" class="submit-button">Зарегестрироваться</button>
</form>

<%@include file="/WEB-INF/views/components/_footer.jsp"%>
