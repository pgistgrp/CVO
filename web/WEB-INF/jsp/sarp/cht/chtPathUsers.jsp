<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<div id="pathUsers" class="pathUsers">
  <c:forEach var="user" items="${users}">
    ${user.loginname}<br>
  </c:forEach>
</div>
