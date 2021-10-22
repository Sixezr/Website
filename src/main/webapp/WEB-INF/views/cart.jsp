<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:mainLayout title="Моя Корзина">
    <div class="container">
        <p class="fs-2 fw-bolder">Моя корзина</p>
        <p class="fs-4 fw-normal price">Общая стоимость - ${cart.getPrice()}</p>

        <c:if test="${!cart.getProducts().isEmpty()}">
            <t:showCartTable products="${cart.getProducts()}"/>
            <div class="d-grid gap-2 col-6 mx-auto">
                <button class="btn btn-outline-success" type="button">Оформить заказ</button>
            </div>
        </c:if>
        <c:if test="${cart.getProducts().isEmpty()}">
            <p class="fs-1 fst-italic">Корзина пуста, вперед к покупкам <img src="<c:url value="/img/logo.png"/>"
                                                                             width="100" height="100" alt="logo"></p>
        </c:if>
    </div>
</t:mainLayout>
