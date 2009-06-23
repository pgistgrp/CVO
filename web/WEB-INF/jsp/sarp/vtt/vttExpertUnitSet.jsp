<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<p><b>Units of measurement for category path "${path.title}"</b>
<div style="height:400px;overflow:auto;clear:both;">
<form name="umForm" action="#">
<div style="clear:both; text-align:right; margin-right:20px;">
  <label style="cursor:pointer;" for="rec-none">Recommend none of the following<label>
  <c:choose>
    <c:when test="${noneSelected}">
      <input id="rec-none" name="rec-${path.id}" type="radio" value="" CHECKED onclick="return toggleSelection(${path.id}, '${block.key.name}', 'rec','', this.checked);">
    </c:when>
    <c:otherwise>
      <input id="rec-none" name="rec-${path.id}" type="radio" value="" onclick="return toggleSelection(${path.id}, '${block.key.name}', 'rec','', this.checked);">
    </c:otherwise>
  </c:choose>
</div>
<c:forEach var="block" items="${grid}" varStatus="loop">
Indicator: <span style="font-weight:bold; color:red;">${block.key.name}</span>
<table id="catsTable" width="100%" cell-padding="1" cell-spacing="0">
  <tr>
    <th align="center">Unit</th>
    <th align="center">Appropriate</th>
    <th align="center">Available</th>
    <th align="center">Duplicate</th>
    <th align="center">Recommend</th>
  </tr>
  <c:forEach var="row" items="${block.value}" varStatus="status">
  <tr>
    <td align="center">${row.key} (${row.value[0]})</td>
    <td align="center">
      <c:if test="${isOwner==true}">
        <c:if test="${row.value[1]==true}">
          <input id="appr-${block.key.id}-${status.index}" type="checkbox" CHECKED value="true" onclick="return toggleSelection(${path.id}, '${block.key.name}', 'appr','${row.key}', this.checked);">
        </c:if>
        <c:if test="${row.value[1]!=true}">
          <input id="appr-${block.key.id}-${status.index}" type="checkbox" value="true" onclick="return toggleSelection(${path.id}, '${block.key.name}', 'appr','${row.key}', this.checked);">
        </c:if>
      </c:if>
      <c:if test="${isOwner!=true}">
        <c:if test="${row.value[1]==true}">Yes</c:if>
        <c:if test="${row.value[1]!=true}">No</c:if>
      </c:if>
    </td>
    <td align="center">
      <c:if test="${isOwner==true}">
        <c:if test="${row.value[2]==true}">
          <input id="avail-${block.key.id}-${status.index}" type="checkbox" CHECKED value="true" onclick="return toggleSelection(${path.id}, '${block.key.name}', 'avail','${row.key}', this.checked);">
        </c:if>
        <c:if test="${row.value[2]!=true}">
          <input id="avail-${block.key.id}-${status.index}" type="checkbox" value="true" onclick="return toggleSelection(${path.id}, '${block.key.name}', 'avail','${row.key}', this.checked);">
        </c:if>
      </c:if>
      <c:if test="${isOwner!=true}">
        <c:if test="${row.value[2]==true}">Yes</c:if>
        <c:if test="${row.value[2]!=true}">No</c:if>
      </c:if>
    </td>
    <td align="center">
      <c:if test="${isOwner==true}">
        <c:if test="${row.value[3]==true}">
          <input id="dup-${block.key.id}-${status.index}" type="checkbox" CHECKED value="true" onclick="return toggleSelection(${path.id}, '${block.key.name}', 'dup','${row.key}', this.checked);">
        </c:if>
        <c:if test="${row.value[3]!=true}">
          <input id="dup-${block.key.id}-${status.index}" type="checkbox" value="true" onclick="return toggleSelection(${path.id}, '${block.key.name}', 'dup','${row.key}', this.checked);">
        </c:if>
      </c:if>
      <c:if test="${isOwner!=true}">
        <c:if test="${row.value[3]==true}">Yes</c:if>
        <c:if test="${row.value[3]!=true}">No</c:if>
      </c:if>
    </td>
    <td align="center">
      <c:if test="${isOwner==true}">
        <c:if test="${row.value[4]==true}">
          <input id="rec-${block.key.id}-${status.index}" name="rec-${path.id}" type="radio" CHECKED value="${block.key.id}" onclick="return toggleSelection(${path.id}, '${block.key.name}', 'rec','${row.key}', this.checked);">
        </c:if>
        <c:if test="${row.value[4]!=true}">
          <input id="rec-${block.key.id}-${status.index}" name="rec-${path.id}" type="radio" value="${block.key.id}" onclick="return toggleSelection(${path.id}, '${block.key.name}', 'rec','${row.key}', this.checked);">
        </c:if>
      </c:if>
      <c:if test="${isOwner!=true}">
        <c:if test="${row.value[4]==true}">Yes</c:if>
        <c:if test="${row.value[4]!=true}">No</c:if>
      </c:if>
    </td>
  </tr>
  </c:forEach>
</table>
<br>
</c:forEach>
</form>
</div>
<div style="clear:both;height:120px;overflow:none;">
<b>Comment:</b><br>
<c:if test="${isOwner==true}">
<center>
  <textarea id="unitComment" style="width:95%;height:100px;">${comment.content}</textarea>
</center>
  <b>Data Source:</b><br>
<center>
  <input type="text" id="unitSource" style="width:95%;" value="${comment.source}">
  <input type="button" value="Submit comment" onclick="saveUnitComment(${path.id});">
</center>
</c:if>
<c:if test="${isOwner!=true}">
<center>
  <textarea id="unitComment" style="width:95%;height:100px;" readonly="true">${comment.content}</textarea>
</center>
  <b>Data Source:</b><br>
<center>
  <input type="text" id="unitSource" style="width:95%;" readonly="true" value="${comment.source}">
</center>
</c:if>
</div>
