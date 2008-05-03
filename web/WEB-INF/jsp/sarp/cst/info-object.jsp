<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<div id="col-left" style="float:left;width:30%;overflow:auto;height:50%;border-right:1px solid #B4D579;">
  <b>Categories:</b>
  <table id="catTable" width="100%" cellpadding="2" cellspacing="0">
  <c:forEach var="category" items="${infoObject.target.winnerCategory.children}" varStatus="loop">
    <c:choose>
      <c:when test="${loop.first}">
      <tr id="catRow-${category.id}">
        <td style="width:4em;"><a id="catcmp-${category.id}" style="display:none;cursor:pointer;" href="javascript:catTree.category1Clicked(${category.id});">compare</a></td>
        <td style="width:3px;"></td>
        <td id="catCol-${category.id}" align="left" style="font-weight:bold;cursor:pointer;" onclick="catTree.category0Clicked(${category.id})">${category}</td>
      </tr>
      </c:when>
      <c:otherwise>
      <tr id="catRow-${category.id}">
        <td style="width:4em;border-top:1px dotted red;"><a id="catcmp-${category.id}" style="display:none;cursor:pointer;" href="javascript:catTree.category1Clicked(${category.id});">compare</a></td>
        <td style="width:3px;border-top:1px dotted red;"></td>
        <td id="catCol-${category.id}" align="left" style="border-top:1px dotted red;font-weight:bold;cursor:pointer;" onclick="catTree.category0Clicked(${category.id})">${category}</td>
      </tr>
      </c:otherwise>
    </c:choose>
  </c:forEach>
  </table>
</div>

<div id="col-right1" style="overflow:auto;height:50%;">
  <div style="width:48%;height:100%;float:left;border-right:1px solid #B4D579;">
    <b style="background-color:#D6E7EF;">Tags for "<span id="catTop"></span>":</b>
    <div id="tagsTop"></div>
  </div>
  <div style="width:48%;height:100%;float:left;">
    <b style="background-color:#FFF1DC;">Tags for "<span id="catBottom"></span>":</b>
    <div id="tagsBottom"></div>
  </div>
</div>

<div id="col-right2" style="overflow:auto;height:50%;border-top:1px solid #B4D579;">
  <b>Moderator Announcement:</b>
  <div id="tags"></div>
</div>
