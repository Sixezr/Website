<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "title" value = "Изменить"/>
<c:set var = "form_width" value = "45"/>
<%@include file="/WEB-INF/views/components/_header.jsp"%>

<form enctype="multipart/form-data" method="post">
    <div class="mb-3">
        <label for="formGroupExampleInput" class="form-label">Измените название</label>
        <input name="name" value="${name}" type="text" class="form-control" id="formGroupExampleInput" placeholder="Название" required>
    </div>
    <div class="mb-3">
        <label for="formGroupExampleInput2" class="form-label">Измените цену</label>
        <input name="price" value="${price}" type="text" class="form-control" id="formGroupExampleInput2" placeholder="Цена" required>
    </div>
    <div class="mb-3">
        <label for="formFileMultiple" class="form-label">Выберите новую фотографию, если надо</label>
        <input class="form-control" id="formFileMultiple" type="file" name="photo" accept="image/*,image/jpeg">
        <c:if test="${error != null}">
            <div id="error">${error}</div>
        </c:if>
        <c:if test="${ok != null}">
            <div id="ok">${ok}</div>
        </c:if>
        <input name="id" value="${id}" hidden>
        <input name="picture" value="${picture}" hidden>
        <input type="submit" value="Изменить">
    </div>
</form>

<%@include file="/WEB-INF/views/components/_footer.jsp"%>
