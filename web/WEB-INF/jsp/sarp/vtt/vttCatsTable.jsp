<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<p><b>Hierarchical Categories</b>
<table id="catsTable" width="100%" cell-padding="1" cell-spacing="0">
  <pg:dfIterator var="catRef" root="${root}" level="level">
    <tr id="row-${catRef.id}" class="catUnSelected" onclick="tree1.select(${catRef.id});"><td id="col-${catRef.id}" style="padding-left:${level*2}em;">${catRef.category.name}</td></tr>
  </pg:dfIterator>
</table>

