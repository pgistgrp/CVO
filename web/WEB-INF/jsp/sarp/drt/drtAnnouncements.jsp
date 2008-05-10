<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<table width="100%" border="1" cellpadding="3" style="border-collapse:collapse;border-color:#B4D579;">
<c:forEach var="announcement" items="${announcements}" varStatus="loop">
  <tr>
    <td>${announcement.title}</td>
    <td>${announcement.description}</td>
    <td>Vote</td>
  </tr>
</c:forEach>
</table>
