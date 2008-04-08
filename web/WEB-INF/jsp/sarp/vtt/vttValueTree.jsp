<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<p><b>Current Value Tree:</b>

<table id="catsTable" width="100%" cell-padding="1" cell-spacing="0">
  <pg:dfIterator var="catRef" root="${root}" level="level">
    <pg:categoryValue var="value" isLeaf="isLeaf" category="${catRef}" />
    <tr id="vtrow-${catRef.id}" class="catUnSelected" onclick="tree1.select(${catRef.id});">
      <c:if test="${fn:length(value.name)==0}">
      <td id="col-${catRef.id}" style="padding-left:${level*2}em;">${catRef.category.name}</td>
      </c:if>
      <c:if test="${fn:length(value.name)>0}">
      <td id="col-${catRef.id}" style="padding-left:${level*2}em;">${catRef.category.name} (${value.name} : ${value.criterion})</td>
      </c:if>
    </tr>
  </pg:dfIterator>
</table>

