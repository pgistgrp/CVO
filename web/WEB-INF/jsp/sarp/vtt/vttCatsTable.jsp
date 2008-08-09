<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<p><b>Category Paths</b>
<table id="catsTable" width="100%" cell-padding="1" cell-spacing="0">
  <c:forEach var="path" items="${vtt.paths}">
    <tr id="row-${path.id}" class="catUnSelected" onclick="tree1.select(${path.id});"><td id="col-${path.id}">${path.title}</td></tr>
  </c:forEach>
</table>

