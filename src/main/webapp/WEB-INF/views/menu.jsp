<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "title" value = "Меню"/>
<%@include file="/WEB-INF/views/components/_header.jsp"%>

<div class="album py-5">
  <div class="container">
    <c:if test="${user.isAdmin()}">
      <form action="<c:url value = "/menu/add"/>">
        <button type="submit" class="btn btn-secondary">Добавить товар</button>
      </form>
    </c:if>

    <c:if test="${!products.isEmpty()}">
      <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
        <c:forEach items="${products}" var="product">
          <div class="col">
            <div class="card shadow-sm">
              <img src="<c:url value = "/img/products/${product.getPicture()}"/>" alt="${product.getName()}" width="100%" height="200px">
              <div class="card-body">
                <p>${product.getName()}</p>
                <div class="d-flex justify-content-between align-items-center">
                  <div class="btn-group">
                    <form method="POST">
                      <button type="submit" type="button" class="btn btn-sm btn-outline-secondary" name="product-id" value="${product.getId()}">В корзину</button>
                    </form>
                    <c:if test="${user.isAdmin()}">
                      <form action="<c:url value="/menu/change"/>">
                        <button type="submit" name="product_id" value="${product.getId()}" class="btn btn-sm btn-outline-secondary">Изменить</button>
                      </form>
                    </c:if>
                  </div>
                  <small class="text-muted">${product.getPrice()} р</small>
                </div>
              </div>
            </div>
          </div>
        </c:forEach>
      </div>
    </c:if>

    <c:if test="${products.isEmpty()}">
      <p class="fs-1 fst-italic">Пока здесть пусто :с <img src="<c:url value="/img/logo.png"/>" width="100" height="100§" alt="logo"></p>
    </c:if>
  </div>
</div>

<%@include file="/WEB-INF/views/components/_footer.jsp"%>
