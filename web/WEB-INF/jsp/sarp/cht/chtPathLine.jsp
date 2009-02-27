<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<td>${path.title}</td>
<td>${path.frequency}</td>
<td>${path.numAgree}/${path.numVote}</td>
<td>
  <c:choose>
    <c:when test="${pg:voted(pageContext, path)}">
      <img src="images/btn_thumbsdown_off.png" alt="Disabled Button"> <img src="images/btn_thumbsup_off.png" alt="Disabled Button">
    </c:when>
    <c:otherwise>
      <a href="javascript:infoObject.setVotingOnPath(${path.id}, 'false');"><img src="/images/btn_thumbsdown.png" alt="I disagree!" border="0"/></a> 
      <a href="javascript:infoObject.setVotingOnPath(${path.id}, 'true');"><img src="/images/btn_thumbsup.png" alt="I agree!" border="0"/></a>
    </c:otherwise>
  </c:choose>
</td>

