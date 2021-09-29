<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "title" value = "Страница не найдена"/>
<%@include file="/WEB-INF/views/components/_header.jsp"%>

<div class="container not-found">
  Страница не найдена
  <img src="<c:url value = "/img/not-found.png"/>" alt="logo" width="160" height="160">
</div>

<%@include file="/WEB-INF/views/components/_footer.jsp"%>
