<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<p><b>Current Value Tree:</b>

<table id="catsTable" width="100%" cell-padding="1" cell-spacing="0">
  <pg:dfIterator var="catRef" root="${root}" level="level">
    <pg:categoryValue var="value" category="${catRef}" />
    <tr id="vtrow-${catRef.id}" class="catUnSelected" onclick="tree2.select(${catRef.id});"><td id="col-${catRef.id}" style="padding-left:${level*2}em;">${value.name} (${value.criterion})</td></tr>
  </pg:dfIterator>
</table>

