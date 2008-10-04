<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<center><b>Current Path Values:</b></center>
<table id="catsTable" width="100%" cell-padding="1" cell-spacing="0">
  <c:forEach var="path" items="${vtt.paths}">
    <pg:pathValue var="value" userId="${user.id}" path="${path}" />
    <tr id="vtrow-${path.id}" class="catUnSelected" onclick="tree1.select(${path.id});">
      <c:if test="${fn:length(value.name)==0}">
      <td id="col-${path.id}">${path.title}</td>
      </c:if>
      <c:if test="${fn:length(value.name)>0}">
      <td id="col-${path.id}">${path.title} (${value.name} : ${value.criterion})</td>
      </c:if>
    </tr>
  </c:forEach>
</table>

