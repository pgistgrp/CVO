<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<div id="col-left" style="float:left;width:50%;overflow:auto;height:50%;border-right:1px solid #B4D579;">
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

<div id="col-right" style="overflow:auto;height:50%;">
  <b>Tag cluster for category "<span id="catName"></span>":</b>
  <div id="tags"></div>
</div>

<div id="col-right2" style="overflow:hidden;clear:both;height:20px;border-top:1px solid #B4D579;">
  <div style="float:left;overflow:hidden;">
    Moderator Announcements: &nbsp;&nbsp;&nbsp;&nbsp;
    <span style="cursor:pointer;color:blue;text-decoration:underline;" onclick="infoObject.getAnnouncements();">Refresh</span>
  </div>
	<pg:show roles="moderator">
    <div style="float:right;clear:right;">
      <span style="cursor:pointer;color:blue;text-decoration:underline;" onclick="$('announceEditor').style.display = 'block';">Create Announcements</span>
    </div>
	</pg:show>
</div>
<div id="col-right3" style="overflow:auto;clear:both;height:270px;border-top:1px solid #B4D579;">
	<pg:show roles="moderator">
    <div id="announceEditor" style="display:none;position:relative;clear:both;width:98%;height:80%;">
    Title: <br><input id="announceEditor_title" type="text" size="32"><br>
    Description: <br><textarea id="announceEditor_description" rows="5" style="width:600px;"></textarea><br>
    <input type="button" value="Submit" onclick="infoObject.createAnnouncement();">
    <input type="button" value="Cancel" onclick="$('announceEditor').style.display = 'none';">
    </div>
	</pg:show>
  <center>
    <div id="announcements" style="clear:both;padding:2px;overflow:auto;width:98%;height:80%;">
      <jsp:include page="../drt/drtAnnouncements.jsp" />
    </div>
  </center>
</div>
