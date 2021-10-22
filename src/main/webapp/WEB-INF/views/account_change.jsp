<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:mainLayout title="Мой аккаунт - Изменить">
    <form method="post" class="account-form">
        <div class="row">
            <div class="col">
                <input type="text" name="name" class="form-control" placeholder="Имя" value="${user.getName()}">
            </div>
            <div class="col">
                <input type="text" name="second-name" class="form-control" placeholder="Фамилия"
                       value="${user.getSecondName()}">
            </div>
        </div>
        <div class="row">
            <div class="col">
                <input type="password" name="pass" class="form-control" placeholder="Password"
                       value="${user.getPass()}">
            </div>
            <div class="col">
                <input type="text" name="phone-number" class="form-control" placeholder="Номер телефона"
                       value="${user.getPhoneNumber()}">
            </div>
        </div>
        <div class="d-grid gap-2">
            <c:if test="${error != null}">
                <div id="error">${error}</div>
            </c:if>
            <c:if test="${ok != null}">
                <div id="ok">${ok}</div>
            </c:if>
            <button type="submit" class="btn btn-primary btn-success" name="action" value="save">Сохранить</button>
            <button type="submit" class="btn btn-primary btn-dark" name="action" value="cancel">Отмена</button>
        </div>
    </form>
</t:mainLayout>
