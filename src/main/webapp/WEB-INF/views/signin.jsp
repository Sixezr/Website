<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="t"%>

<t:mainLayout title="Вход">
  <div class="container">
    <form method="post" class="register-form">
      <div class="mb-3">
        <label for="email" class="form-label">Email</label>
        <input name="email" type="email" class="form-control" id="email" aria-describedby="emailHelp" value="${repeated_email == "" ? "" : repeated_email}" required>
        <div id="emailHelp" class="form-text">Никто не узнает ваши данные</div>
      </div>
      <div class="mb-3">
        <label for="pass" class="form-label">Password</label>
        <input name="pass" type="password" class="form-control" id="pass" required>
      </div>
      <div><a href="<c:url value="/register"/>" id="register-href">Нет аккаунта? Создайте его!</a></div>

      <c:if test="${error != null}">
        <div id="error">${error}</div>
      </c:if>

      <button type="submit" class="submit-button">Отправить</button>
    </form>
  </div>
</t:mainLayout>
