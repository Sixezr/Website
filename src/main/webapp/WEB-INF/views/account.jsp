<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:mainLayout title="Мой аккаунт">
    <form class="account-form">
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
                <input type="text" class="form-control" placeholder="Номер телефона" value="${user.getPhoneNumber()}"
                       disabled>
            </div>
        </div>
        <div class="d-grid gap-2">
            <form method="get">
                <button type="submit" class="btn btn-primary btn-secondary" name="action" value="change">Изменить
                    данные
                </button>
                <button type="submit" class="btn btn-primary btn-danger" name="action" value="quit">Выйти</button>
            </form>

        </div>
    </form>
</t:mainLayout>
