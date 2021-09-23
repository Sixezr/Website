<%@ page import="ru.sixzr.module.entities.UserModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "title" value = "Мой аккаунт"/>
<c:set var = "form_width" value = "35"/>
<%@include file="/WEB-INF/views/components/_header.jsp"%>

<form>
    <div class="row">
        <div class="col">
            <input type="text" class="form-control" placeholder="Имя" value="${user.getName()}" disabled>
        </div>
        <div class="col">
            <input type="text" class="form-control" placeholder="Фамилия" value="${user.getSecondName()}" disabled>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <input type="email" class="form-control" placeholder="Email" value="${user.getEmail()}" disabled>
        </div>
        <div class="col">
            <input type="text" class="form-control" placeholder="Номер телефона" value="${user.getPhoneNumber()}" disabled>
        </div>
    </div>
    <div class="d-grid gap-2">
        <form action="http://localhost:8080/WebAppFirst_war/account" method="get">
            <button type="submit" class="btn btn-primary btn-secondary" name="action" value="change">Изменить данные</button>
            <button type="submit" class="btn btn-primary btn-danger"  name="action" value="quit">Выйти</button>
        </form>

    </div>
</form>

<%@include file="/WEB-INF/views/components/_footer.jsp"%>
