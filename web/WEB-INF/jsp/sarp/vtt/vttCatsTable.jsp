<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<center>
  <b>Category Paths</b>
  <select id="sorting" onchange="changeSorting($('sorting').value);">
    <option value="a-z"<c:if test="${sorting=='a-z'}"> SELECTED</c:if>>a-z</option>
    <option value="z-a"<c:if test="${sorting=='z-a'}"> SELECTED</c:if>>z-a</option>
    <option value="0-9"<c:if test="${sorting=='0-9'}"> SELECTED</c:if>>0-9</option>
    <option value="9-0"<c:if test="${sorting=='9-0'}"> SELECTED</c:if>>9-0</option>
  </select>
</center>
<table id="catsTable" width="98%" cell-padding="1" cell-spacing="0">
  <c:forEach var="path" items="${pg:sortPaths(vtt.paths, sorting)}">
    <tr id="row-${path.id}" class="catUnSelected" onclick="tree1.select(${path.id});"><td id="col-${path.id}">(${path.numAgree}/${path.numVote}) ${path.title}</td></tr>
  </c:forEach>
</table>

