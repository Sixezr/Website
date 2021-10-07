<%@ page contentType="text/html;charset=UTF-8" %>
<div class="container">
    <header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
        <a href="${pageContext.request.contextPath}/" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
            <img src="${pageContext.request.contextPath}/img/logo.png" alt="logo" width="40" height="32" id="logo">
            <span class="fs-4">Myaso</span>
        </a>
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="<c:url value = "/cart"/>" class="nav-link">Корзина</a></li>
            <li class="nav-item"><a href="<c:url value = "/menu"/>" class="nav-link">Меню</a></li>
            <li class="nav-item"><a href="<c:url value = "/about/"/>" class="nav-link">О нас</a></li>
            <li class="nav-item"><a href="<c:url value = "/account"/>" class="nav-link">Аккаунт</a></li>
        </ul>
    </header>
</div>
