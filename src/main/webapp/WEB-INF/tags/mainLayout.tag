<%@tag description="Default Layout Tag" pageEncoding="UTF-8" %>
<%@attribute name="title" required="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <link rel="shortcut icon" href="<c:url value = "/img/logo.png"/>" type="image/x-icon">
    <link rel="stylesheet" href="<c:url value = "/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value = "/css/style.css"/>">
</head>
<body>
<div class="container">
    <header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
        <a href="<c:url value="/"/>"
           class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
            <img src="${pageContext.request.contextPath}/img/logo.png" alt="logo" width="40" height="32" id="logo">
            <span class="fs-4">Myaso</span>
        </a>
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="<c:url value="/cart"/>" class="nav-link">Корзина</a></li>
            <li class="nav-item"><a href="<c:url value="/menu"/>" class="nav-link">Меню</a></li>
            <li class="nav-item"><a href="<c:url value="/about/"/>" class="nav-link">О нас</a></li>
            <li class="nav-item"><a href="<c:url value="/account"/>" class="nav-link">Аккаунт</a></li>
        </ul>
    </header>
</div>
<jsp:doBody/>
<div class="container">
    <footer class="d-flex flex-wrap justify-content-between align-items-center py-3 my-4 border-top">
        <div class="col-md-4 d-flex align-items-center">
            <span class="text-muted">&copy; 2021 Официальный сайт ресторана «Myaso».</span>
        </div>

        <ul class="nav col-md-4 justify-content-end list-unstyled d-flex">
            <li class="ms-3"><a class="text-muted" href="#"><img src="<c:url value="/img/vk-logo.png"/>" alt="vk"
                                                                 width="32" height="32"></a></li>
            <li class="ms-3"><a class="text-muted" href="#"><img src="<c:url value="/img/tg-logo.png"/>" alt="tg"
                                                                 width="32" height="32"></a></li>
            <li class="ms-3"><a class="text-muted" href="#"><img src="<c:url value="/img/insta-logo.png"/>" alt="insta"
                                                                 width="32" height="32"></a></li>
        </ul>
    </footer>
</div>
</body>
</html>
