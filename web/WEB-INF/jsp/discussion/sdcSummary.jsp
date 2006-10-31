<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<pg:fragment type="html">
	<div class="voting">
		<h4>Quick Vote</h4>
		<div id="structure_question_status">
			<p><span class="smalltext" style="line-height: 15px; font-style: italic; "><strong>${infoObject.numAgree} of ${infoObject.numVote}</strong> participants have said that this summary adequately reflects concerns expressed by participants.</span></p>
		</div>
		<div id="structure_question">
			<c:choose>
				<c:when test="${voting == null}">
					<span class="smalltext">Do you feel this summary adequately reflects concerns expressed by participants? <a href="javascript:infoObject.setVote('object',${infoObject.id}, 'true');"><img src="/images/btn_yes_a.gif" alt="YES" class="button"><a href="javascript:infoObject.setVote('object', ${infoObject.id}, 'true');"><img src="/images/btn_no_a.gif" alt="NO" class="button"></a></span>
				</c:when>
				<c:otherwise>
					<span class="smalltext">Your vote has been recorded. Thank you for your participation.</span>
				</c:otherwise>
			</c:choose>
		</div>
</div>
	<pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">
	  <p>${infoObject.object.theme.summary}</p>
	</pg:termHighlight>

	Tags highlighted by the moderator: 
	<ul class="tagsList">
	<c:forEach var="tag" items="${infoObject.object.theme.tags}">
		<li class="tagsList"><small><a href="javascript: sideBar.changeCurrentFilter(${tag.id});">${tag.name}</a></small></li>
	</c:forEach>		
	</ul>
</pg:fragment>

	<pg:fragment type="script">

</pg:fragment>