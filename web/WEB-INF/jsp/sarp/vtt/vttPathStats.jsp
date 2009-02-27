<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<center>
<b>Units of Measurement Specialist Analysis and Selection:</b>
</center>
<form>
<div style="clear:both; text-align:right; margin-right:30px;">
  <label style="cursor:pointer;" for="rec-none">Recommend none of the following<label> <input id="rec-none" name="userSel" type="radio" value="" onclick="return toggleSelection(${path.id}, '${block.key.name}', 'rec','', this.checked);"></td>
</div>
<c:forEach var="block" items="${grid}" varStatus="loop">
Indicator: <span style="font-weight:bold; color:red;">${block.key.name}</span>
<table width="100%" width="1">
  <tr>
    <th>Unit</th>
    <th>Appropriate</th>
    <th>Available</th>
    <th>Duplicate</th>
    <th>Recommend</th>
    <th>Selection</th>
  </tr>
  <c:forEach var="row" items="${block.value}" varStatus="status">
  <tr>
    <td>${row.key}</td>
    <td align="center">${row.value[0]}</td>
    <td align="center">${row.value[1]}</td>
    <td align="center">${row.value[2]}</td>
    <td align="center">${row.value[3]}</td>
    <td align="left">
      <c:choose>
        <c:when test="${row.value[4]}">
        <input type="radio" name="userSel" value="${block.key.id}-${row.key}" checked>
        </c:when>
        <c:otherwise>
        <input type="radio" name="userSel" value="${block.key.id}-${row.key}">
        </c:otherwise>
      </c:choose>
      (${row.value[5]}/${row.value[6]})
    </td>
  </tr>
  </c:forEach>
</table>
<br>
</c:forEach>
<center>
<input id="btnSave" type="button" value="Save" onclick="saveUnitSelection(${pathId});">
</center>
</form>

