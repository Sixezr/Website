<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "title" value = "Моя Корзина"/>
<%@include file="/WEB-INF/views/components/_header.jsp"%>

<div class="container">
    <table width="100%" class="table-bordered">
        <tr align="center">
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Почта</th>
            <th>Телефон</th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr><td align="center">${user.getName()}</td><td align="center">${user.getSecondName()}</td><td align="center">${user.getEmail()}</td><td align="center">${user.getPhoneNumber()}</td></tr>
        </c:forEach>
    </table>
</div>

<%@include file="/WEB-INF/views/components/_footer.jsp"%>
