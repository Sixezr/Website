<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "title" value = "Меню"/>
<%@include file="/WEB-INF/views/components/_header.jsp"%>

<div class="album py-5">
  <div class="container">
    <form action="${pageContext.request.contextPath}/menu/add">
      <button type="submit" class="btn btn-secondary">Добавить товар</button>
    </form>
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

      <c:forEach items="${products}" var="product">
        <div class="col">
          <div class="card shadow-sm">
            <img src="${pageContext.request.contextPath}/img/products/${product.getPicture()}" alt="${product.getName()}">
            <div class="card-body">
              <p>${product.getName()}</p>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                  <button type="button" class="btn btn-sm btn-outline-secondary">В корзину</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary">Изменить</button>
                </div>
                <small class="text-muted">${product.getPrice()} р</small>
              </div>
            </div>
          </div>
        </div>
      </c:forEach>

    </div>
  </div>
</div>

<%@include file="/WEB-INF/views/components/_footer.jsp"%>
