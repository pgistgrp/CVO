<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<b>Existing Paths:</b>
<pg:chtGetPaths var="paths" chtId="${cht.id}" orderby="${orderby}" />
<table id="catTable" width="100%" cellpadding="2" cellspacing="0">
  <tr style="font-weight:bold;">
    <td>Label</td>
    <td style="width:10%;">Frequency</td>
    <td style="width:10%;"># of Votes</td>
  </tr>
<c:forEach var="path" items="${paths}" varStatus="loop">
  <c:choose>
    <c:when test="${loop.index % 2 == 0}">
    <tr id="catRow-${path.id}" style="background-color:#D6E7EF;">
    </c:when>
    <c:otherwise>
    <tr id="catRow-${path.id}">
    </c:otherwise>
  </c:choose>
      <td>${path.title}</td>
      <td>${path.frequency}</td>
      <td>${path.numAgree}/${path.numVote}</td>
    </tr>
</c:forEach>
</table>

