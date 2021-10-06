<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "title" value = "Мой аккаунт - Изменить"/>
<c:set var = "form_width" value = "35"/>
<%@include file="/WEB-INF/views/components/_header.jsp"%>

<form method="post">
    <div class="row">
        <div class="col">
            <input type="text" name="name" class="form-control" placeholder="Имя" value="${user.getName()}">
        </div>
        <div class="col">
            <input type="text" name="second-name" class="form-control" placeholder="Фамилия" value="${user.getSecondName()}">
        </div>
    </div>
    <div class="row">
        <div class="col">
            <input type="email" class="form-control" placeholder="Email" value="${user.getEmail()}" disabled>
        </div>
        <div class="col">
            <input type="text" name="phone-number" class="form-control" placeholder="Номер телефона" value="${user.getPhoneNumber()}">
        </div>
    </div>
    <div class="d-grid gap-2">
        <c:if test="${error != null}">
            <div id="error">${error}</div>
        </c:if>
        <button type="submit" class="btn btn-primary btn-success" name="action" value="save">Сохранить</button>
        <button type="submit" class="btn btn-primary btn-dark"  name="action" value="cancel">Отмена</button>
    </div>
</form>

<%@include file="/WEB-INF/views/components/_footer.jsp"%>
