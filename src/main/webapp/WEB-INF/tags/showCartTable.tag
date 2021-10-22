<%@tag description="Default Layout Tag" pageEncoding="UTF-8" %>
<%@attribute name="products" type="semestrovka.module.collestions.CustomProductCollections" required="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table class="table">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Название</th>
        <th scope="col">Стоимость</th>
        <th scope="col">Количество</th>
        <th scope="col">Действие</th>
        <form method="POST">
            <button type="submit" class="btn btn-outline-danger" name="action" value="clearAll">Очистить корзину
            </button>
        </form>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${products}" var="product">
        <tr>
            <th scope="row">${cart.getProducts().getCurrentIndex()}</th>
            <td>${product.getName()}</td>
            <td><span class="price">${cart.getProducts().getCurrentPriceForProducts()}</span></td>
            <td>${cart.getProducts().getCurrentProductCount()}</td>
            <form method="post">
                <input name="product_id" value="${product.getId()}" hidden>
                <td>
                    <button type="submit" class="btn btn-outline-success" name="action" value="add">+</button>
                    <button type="submit" class="btn btn-outline-danger" name="action" value="remove">-</button>
                </td>
            </form>

        </tr>
    </c:forEach>
    </tbody>
</table>