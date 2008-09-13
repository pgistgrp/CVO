<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<p><b>Units of Measurement</b>
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
        <c:if test="${euset.apprs[freq.key]==true}">
          <input id="appr-${freq.key}" type="checkbox" CHECKED value="true">
        </c:if>
        <c:if test="${euset.apprs[freq.key]!=true}">
          <input id="appr-${freq.key}" type="checkbox" value="true">
        </c:if>
      </td>
      <td align="center">
        <c:if test="${euset.avails[avails.key]==true}">
          <input id="appr-${freq.key}" type="checkbox" CHECKED value="true">
        </c:if>
        <c:if test="${euset.avails[freq.key]!=true}">
          <input id="appr-${freq.key}" type="checkbox" value="true">
        </c:if>
      </td>
      <td align="center">
        <c:if test="${euset.dups[freq.key]==true}">
          <input id="appr-${freq.key}" type="checkbox" CHECKED value="true">
        </c:if>
        <c:if test="${euset.dups[freq.key]!=true}">
          <input id="appr-${freq.key}" type="checkbox" value="true">
        </c:if>
      </td>
      <td align="center">
        <c:if test="${euset.recs[freq.key]==true}">
          <input id="rec-${freq.key}" name="rec-${euset.id}" type="radio" CHECKED value="${freq.key}">
        </c:if>
        <c:if test="${euset.recs[freq.key]!=true}">
          <input id="rec-${freq.key}" name="rec-${euset.id}" type="radio" value="${freq.key}">
        </c:if>
      </td>
    </tr>
  </c:forEach>
</table>
</form>
