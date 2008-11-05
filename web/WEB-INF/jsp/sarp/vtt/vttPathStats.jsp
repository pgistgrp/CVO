<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<b>Units of Measurement Specialist Analysis and Selection:</b>
<table width="100%" width="1">
  <tr>
    <th>Unit</th>
    <th>Appropriate</th>
    <th>Available</th>
    <th>Duplicate</th>
    <th>Recommend</th>
    <th>Selection</th>
  </tr>
  <c:forEach var="unit" items="${units}">
  <tr>
    <td>${unit}</td>
    <td>${apprFreqs[unit]}</td>
    <td>${availFreqs[unit]}</td>
    <td>${dupFreqs[unit]}</td>
    <td>${recoFreqs[unit]}</td>
    <td align="left">
      <c:choose>
        <c:when test="${unit==userSelection}">
        <input type="radio" name="userSel" value="${unit}" checked>
        </c:when>
        <c:otherwise>
        <input type="radio" name="userSel" value="${unit}">
        </c:otherwise>
      </c:choose>
      <pg:msetSelection mset="${mset}" unit="${unit}" />
    </td>
  </tr>
  </c:forEach>
  <tr>
    <td colspan="5" align="right"></td>
    <td align="left">
      <c:choose>
        <c:when test="${userSelection!=null}">
          <input type="radio" name="userSel" value="">
        </c:when>
        <c:otherwise>
          <input type="radio" name="userSel" value="" checked>
        </c:otherwise>
      </c:choose>
      None
    </td>
  </tr>
</table>
<input id="btnSave" type="button" value="Save" onclick="saveUnitSelection(${pathId});">
