<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:mainLayout title="Добавить">
    <form enctype="multipart/form-data" method="post" class="product-form">
        <div class="mb-3">
            <label for="formGroupExampleInput" class="form-label">Введите название</label>
            <input name="name" type="text" class="form-control" id="formGroupExampleInput" placeholder="Название"
                   required>
        </div>
        <div class="mb-3">
            <label for="formGroupExampleInput2" class="form-label">Введите цену</label>
            <input name="price" type="text" class="form-control" id="formGroupExampleInput2" placeholder="Цена"
                   required>
        </div>
        <div class="mb-3">
            <label for="formFileMultiple" class="form-label">Выберите фотографию</label>
            <input class="form-control" id="formFileMultiple" type="file" name="photo" accept="image/*,image/jpeg"
                   required>
            <c:if test="${error != null}">
                <div id="error">${error}</div>
                <c:remove var="error" scope="session" />
            </c:if>
            <c:if test="${ok != null}">
                <div id="ok">${ok}</div>
                <c:remove var="ok" scope="session" />
            </c:if>
            <input type="submit" value="Добавить">
        </div>
    </form>
</t:mainLayout>
