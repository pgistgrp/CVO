<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<center><b>Path : ${path.title}</b> <input type="button" value="add indicator" onclick="infoObject.addIndicator(${path.id});"></center>
<br>
<table>
  <c:forEach var="block" items="${grid}" varStatus="loop0">
    <tr>
      <td>
        <span style="font-weight:bold;">Indicator:</span> <span style="font-weight:bold; color:red;">${block.key.name}</span>
        <input type="button" value="delete indicator" onclick="infoObject.deleteIndicator(${path.id}, ${block.key.id});">
        <input type="button" value="add unit" onclick="infoObject.addUnit(${path.id}, ${block.key.id});">
      </td>
    </tr>
    <c:forEach var="unit" items="${block.value}" varStatus="loop1">
      <tr>
        <td style="padding-left:40px;">
          ${unit}
          <input type="button" value="delete unit" onclick="infoObject.deleteUnit(${path.id}, ${block.key.id}, '${unit}');">
        </td>
      </tr>
    </c:forEach>
  </c:forEach>
</table>
