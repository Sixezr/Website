<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "title" value = "Моя Корзина"/>
<%@include file="/WEB-INF/views/components/_header.jsp"%>

<div class="container">
    <p class="fs-2 fw-bolder">Моя корзина</p>
    <p class="fs-4 fw-normal price">Общая стоимость - ${cart.getPrice()}</p>

    <c:if test="${!cart.getProducts().isEmpty()}">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Название</th>
                <th scope="col">Стоимость</th>
                <th scope="col">Количество</th>
                <th scope="col">Действие</th>
                <form method="POST">
                    <button type="submit" class="btn btn-outline-danger" name="action" value="clearAll">Очистить корзину</button>
                </form>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${cart.getProducts()}" var="product">
                <tr>
                    <th scope="row">${cart.getProducts().getCurrentIndex()}</th>
                    <td>${product.getName()}</td>
                    <td><span class="price">${cart.getProducts().getCurrentPriceForProducts()}</span></td>
                    <td>${cart.getProducts().getCurrentProductCount()}</td>
                    <td>+ -</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="d-grid gap-2 col-6 mx-auto">
            <button class="btn btn-outline-success" type="button">Оформить заказ</button>
        </div>
    </c:if>

    <c:if test="${cart.getProducts().isEmpty()}">
        <p class="fs-1 fst-italic">Корзина пуста, вперед к покупкам <img src="<c:url value="/img/logo.png"/>" width="100" height="100§" alt="logo"></p>
    </c:if>

</div>

<%@include file="/WEB-INF/views/components/_footer.jsp"%>
