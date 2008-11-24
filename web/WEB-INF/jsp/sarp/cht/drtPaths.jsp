<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>

<b>CHT Path Voting:</b>
<table id="catTable" width="100%" cellpadding="2" cellspacing="0">
  <tr style="font-weight:bold;">
    <td style="width:50%"><a href="javascript:infoObject.getPaths('title');">Label</a></td>
    <td style="width:16%;"><a href="javascript:infoObject.getPaths('freq');">Frequency</a></td>
    <td style="width:16%;"><a href="javascript:infoObject.getPaths('vote');"># of Votes</a></td>
    <td style="width:20%;">Voting</td>
  </tr>
<pg:chtGetPaths var="paths" chtId="${chtId}" orderby="${orderby}" />
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
      <td id="pathnum-${path.id}">${path.numAgree}/${path.numVote}</td>
      <td id="pathvot-${path.id}">
        <c:choose>
          <c:when test="${pg:voted(pageContext, path)}">
            <img src="images/btn_thumbsdown_off.png" alt="Disabled Button"/> <img src="images/btn_thumbsup_off.png" alt="Disabled Button"/>
          </c:when>
          <c:otherwise>
            <a href="javascript:infoObject.setVotingOnPath(${path.id}, 'false');"><img src="/images/btn_thumbsdown.png" alt="I disagree!" border="0"/></a> 
            <a href="javascript:infoObject.setVotingOnPath(${path.id}, 'true');"><img src="/images/btn_thumbsup.png" alt="I agree!" border="0"/></a>
          </c:otherwise>
        </c:choose>
      </td>
      <td>${path.frequency}</td>
    </tr>
</c:forEach>
</table>

