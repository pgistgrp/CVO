<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<p><b>Units of Measurement for category path "${path.title}"</b>
<div style="height:250px;overflow:auto;clear:both;">
<form name="umForm" action="#">
<table id="catsTable" width="100%" cell-padding="1" cell-spacing="0">
  <tr>
    <th align="center">Unit</th>
    <th align="center">Appropriate</th>
    <th align="center">Available</th>
    <th align="center">Duplicate</th>
    <th align="center">Recommend</th>
  </tr>
  <c:forEach var="freq" items="${freqs}">
    <tr>
      <td align="center">${freq.key} (${freq.value})</td>
      <td align="center">
        <c:if test="${isOwner==true}">
          <c:if test="${euset.apprs[freq.key]==true}">
            <input id="appr-${freq.key}" type="checkbox" CHECKED value="true" onclick="return toggleSelection(${muset.id}, 'appr','${freq.key}', this.checked);">
          </c:if>
          <c:if test="${euset.apprs[freq.key]!=true}">
            <input id="appr-${freq.key}" type="checkbox" value="true" onclick="return toggleSelection(${muset.id}, 'appr','${freq.key}', this.checked);">
          </c:if>
        </c:if>
        <c:if test="${isOwner!=true}">
          <c:if test="${euset.apprs[freq.key]==true}">Yes</c:if>
          <c:if test="${euset.apprs[freq.key]!=true}">No</c:if>
        </c:if>
      </td>
      <td align="center">
        <c:if test="${isOwner==true}">
          <c:if test="${euset.avails[freq.key]==true}">
            <input id="avail-${freq.key}" type="checkbox" CHECKED value="true" onclick="return toggleSelection(${muset.id}, 'avail','${freq.key}', this.checked);">
          </c:if>
          <c:if test="${euset.avails[freq.key]!=true}">
            <input id="avail-${freq.key}" type="checkbox" value="true" onclick="return toggleSelection(${muset.id}, 'avail','${freq.key}', this.checked);">
          </c:if>
        </c:if>
        <c:if test="${isOwner!=true}">
          <c:if test="${euset.avails[freq.key]==true}">Yes</c:if>
          <c:if test="${euset.avails[freq.key]!=true}">No</c:if>
        </c:if>
      </td>
      <td align="center">
        <c:if test="${isOwner==true}">
          <c:if test="${euset.dups[freq.key]==true}">
            <input id="dup-${freq.key}" type="checkbox" CHECKED value="true" onclick="return toggleSelection(${muset.id}, 'dup','${freq.key}', this.checked);">
          </c:if>
          <c:if test="${euset.dups[freq.key]!=true}">
            <input id="dup-${freq.key}" type="checkbox" value="true" onclick="return toggleSelection(${muset.id}, 'dup','${freq.key}', this.checked);">
          </c:if>
        </c:if>
        <c:if test="${isOwner!=true}">
          <c:if test="${euset.dups[freq.key]==true}">Yes</c:if>
          <c:if test="${euset.dups[freq.key]!=true}">No</c:if>
        </c:if>
      </td>
      <td align="center">
        <c:if test="${isOwner==true}">
          <c:if test="${euset.recs[freq.key]==true}">
            <input id="rec-${freq.key}" name="rec-${euset.id}" type="radio" CHECKED value="${freq.key}" onclick="return toggleSelection(${muset.id}, 'rec','${freq.key}', this.checked);">
          </c:if>
          <c:if test="${euset.recs[freq.key]!=true}">
            <input id="rec-${freq.key}" name="rec-${euset.id}" type="radio" value="${freq.key}" onclick="return toggleSelection(${muset.id}, 'rec','${freq.key}', this.checked);">
          </c:if>
        </c:if>
        <c:if test="${isOwner!=true}">
          <c:if test="${euset.recs[freq.key]==true}">Yes</c:if>
          <c:if test="${euset.recs[freq.key]!=true}">No</c:if>
        </c:if>
      </td>
    </tr>
  </c:forEach>
</table>
</form>
</div>
<div style="clear:both;height:150px;overflow:none;">
<b>Comment:</b><br>
<c:if test="${isOwner==true}">
<center>
  <textarea id="unitComment" style="width:95%;height:100px;">${comment}</textarea>
  <input type="button" value="Submit" onclick="saveUnitComment(${muset.id});">
</center>
</c:if>
<c:if test="${isOwner!=true}">
<center>
  <textarea id="unitComment" style="width:95%;height:100px;" readonly="true">${comment}</textarea>
</center>
</c:if>
</div>
