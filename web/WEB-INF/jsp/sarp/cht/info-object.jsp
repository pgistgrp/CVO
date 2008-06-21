<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<div id="col-left" style="float:left;width:100%;overflow:auto;height:50%;border-right:1px solid #B4D579;">
  <b>CHT Paths Voting:</b>
  <pg:chtGetPaths var="paths" chtId="${infoObject.target.id}" orderby="${orderby}" />
  <table id="catTable" width="100%" cellpadding="2" cellspacing="0">
    <tr style="font-weight:bold;">
      <td style="width:50%">Label</td>
      <td style="width:16%;">Frequency</td>
      <td style="width:16%;"># of Votes</td>
      <td style="width:20%;">Voting</td>
    </tr>
  <c:forEach var="path" items="${paths}" varStatus="loop">
    <c:choose>
      <c:when test="${loop.index % 2 == 0}">
      <tr id="catRow-${path.id}" style="background-color:#D6E7EF;">
      </c:when>
      <c:otherwise>
      <tr id="catRow-${path.id}">
      </c:otherwise>
    </c:choose>
        <td>${path.title}</td>
        <td>${path.frequency}</td>
        <td>${path.numAgree}/${path.numVote}</td>
        <td>
          <a href="javascript:infoObject.setVoteOnPath(${path.id}, 'false');"><img src="/images/btn_thumbsdown.png" alt="I disagree!" border="0"/></a> 
          <a href="javascript:infoObject.setVoteOnPath(${path.id}, 'true');"><img src="/images/btn_thumbsup.png" alt="I agree!" border="0"/></a>
        </td>
      </tr>
  </c:forEach>
  </table>
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
