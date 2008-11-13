<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<center><b>Category Paths</b></center>
<table id="catsTable" width="98%" cell-padding="1" cell-spacing="0">
  <c:forEach var="path" items="${vtt.paths}">
    <tr id="row-${path.id}" class="catUnSelected" onclick="tree1.select(${path.id});">
      <td id="col-${path.id}">${path.title}</td>
      <td align="right"><a href="javascript:tree1.deletePath(${vtt.id}, ${path.id});">Delete</a></td>
    </tr>
  </c:forEach>
</table>

