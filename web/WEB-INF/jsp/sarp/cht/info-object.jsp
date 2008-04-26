<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<div id="col-left" style="float:left;width:50%;overflow:auto;height:100%;border-right:1px solid #B4D579;">
  <b>Categories:</b>
  <table id="catTable" width="100%" cellpadding="2" cellspacing="0">
  <c:forEach var="category" items="${infoObject.target.winnerCategory.children}" varStatus="loop">
    <c:choose>
      <c:when test="${loop.first}">
      <tr id="catRow-${category.id}" style="font-weight:bold;cursor:pointer;" onclick="catTree.category0Clicked(${category.id})">
        <td style="width:4em;"></td>
        <td style="width:3px;"></td>
        <td id="catCol-${category.id}" align="left">${category}</td>
      </tr>
      </c:when>
      <c:otherwise>
      <tr id="catRow-${category.id}" style="border-top:1px dotted red;font-weight:bold;cursor:pointer;" onclick="catTree.category0Clicked(${category.id})">
        <td style="width:4em;border-top:1px dotted red;"></td>
        <td style="width:3px;border-top:1px dotted red;"></td>
        <td id="catCol-${category.id}" align="left" style="border-top:1px dotted red;">${category}</td>
      </tr>
      </c:otherwise>
    </c:choose>
  </c:forEach>
  </table>
</div>

<div id="col-right" style="overflow:auto;height:100%;border-bottom:1px solid #B4D579;">
  <b>Tag cluster for category "<span id="catName"></span>":</b>
  <div id="tags"></div>
</div>

