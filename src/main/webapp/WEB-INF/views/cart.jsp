<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "title" value = "Моя Корзина"/>
<%@include file="/WEB-INF/views/components/_header.jsp"%>

<div class="container">
    <p class="fs-2 fw-bolder">Моя корзина</p>
    <p class="fs-4 fw-normal price">Общая стоимость - ${cart.getPrice()}</p>

    <table class="table">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Название</th>
            <th scope="col">Стоимость</th>
            <th scope="col">Количество</th>
            <th scope="col">Действие</th>
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
</div>

<%@include file="/WEB-INF/views/components/_footer.jsp"%>
