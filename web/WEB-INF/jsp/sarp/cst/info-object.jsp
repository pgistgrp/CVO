<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<div id="col-left" style="float:left;width:50%;overflow:auto;height:100%;border-right:1px solid #B4D579;">
  <b>Categories:</b>
  <table width="100%" cellpadding="2" cellspacing="0">
  <c:forEach var="category" items="${infoObject.target.winnerCategory.children}" varStatus="loop">
    <c:choose>
      <c:when test="${loop.first}">
      <tr>
        <td style="width:2em;background-color:#D6E7EF;cursor:pointer;" onclick="catTree.category0Clicked(${category.id})"></td>
        <td style="width:2em;background-color:#FFF1DC;cursor:pointer;" onclick="catTree.category1Clicked(${category.id})"></td>
        <td style="width:3px;"></td>
        <td id="cat-${category.id}" align="left" style="font-weight:bold;">${category}</td>
      </tr>
      </c:when>
      <c:otherwise>
      <tr>
        <td style="width:2em;border-top:1px dotted red;background-color:#D6E7EF;cursor:pointer;" onclick="catTree.category0Clicked(${category.id})"></td>
        <td style="width:2em;border-top:1px dotted red;background-color:#FFF1DC;cursor:pointer;" onclick="catTree.category1Clicked(${category.id})"></td>
        <td style="width:3px;border-top:1px dotted red;"></td>
        <td id="cat-${category.id}" align="left" style="border-top:1px dotted red;font-weight:bold;">${category}</td>
      </tr>
      </c:otherwise>
    </c:choose>
  </c:forEach>
  </table>
</div>

<div id="col-right" style="overflow:auto;height:50%;border-bottom:1px solid #B4D579;">
<b style="background-color:#D6E7EF;">Tags:</b>
<div id="tagsTop"></div>
</div>
<div id="col-right" style="overflow:auto;height:50%;">
<b style="background-color:#FFF1DC;">Tags:</b>
<div id="tagsBottom"></div>
</div>
