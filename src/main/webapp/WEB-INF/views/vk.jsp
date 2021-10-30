<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:mainLayout title="VK">
    <c:if test="${error != null}">
        <div class="container">
            <div id="error">${error}</div>
        </div>
    </c:if>
</t:mainLayout>

