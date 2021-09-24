<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
        <a href="${pageContext.request.contextPath}/" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
            <img src="${pageContext.request.contextPath}/img/logo.png" alt="logo" width="40" height="32" id="logo">
            <span class="fs-4">Myaso</span>
        </a>
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="${pageContext.request.contextPath}/cart" class="nav-link">Корзина</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/menu" class="nav-link">Меню</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/about/" class="nav-link">О нас</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/account" class="nav-link">Аккаунт</a></li>
        </ul>
    </header>
</div>
