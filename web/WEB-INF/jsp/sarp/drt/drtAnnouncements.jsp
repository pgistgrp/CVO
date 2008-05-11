<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<table width="100%" border="1" cellpadding="3" style="border-collapse:collapse;border-color:#B4D579;">
<c:forEach var="announcement" items="${pg:reverse(announcements)}" varStatus="loop">
  <tr>
    <td width="20%">${announcement.title}</td>
    <td>${announcement.description}</td>
    <td width="18%">
      <div id="voting-announcement${announcement.id}">
        <c:choose>
          <c:when test="${pg:voted(pageContext, announcement)}">
            <img src="/images/btn_thumbsdown_off.png" alt="Disabled Button"/> <img src="/images/btn_thumbsup_off.png" alt="Disabled Button"/>
          </c:when>
          <c:otherwise>
            <a href="javascript:infoObject.setVoteOnAnnouncement(${announcement.id}, 'false');"><img src="/images/btn_thumbsdown.png" alt="I disagree!" border="0"/></a> 
            <a href="javascript:infoObject.setVoteOnAnnouncement(${announcement.id}, 'true');"><img src="/images/btn_thumbsup.png" alt="I agree!" border="0"/></a>
          </c:otherwise>
        </c:choose>
      ${announcement.numAgree}/${announcement.numVote} agree.
      </div>
    </td>
  </tr>
</c:forEach>
</table>
