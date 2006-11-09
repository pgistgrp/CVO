<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<pg:fragment type="html">
	
		<!-- Begin voting tally menu -->
<div id="votingMenu" class="floatLeft"><div id="voting-object${infoObject.id}">
	<div id="votingMenuTally" class="box1">
		<span id="structure_question_status">
			<h2>${infoObject.numAgree} of ${infoObject.numVote}</h2>
		agree with summary</div>
	</span>
	<p>Does this summary reflect the group's concerns?</p>
	<span id="structure_question">

		<c:choose>
			<c:when test="${voting == null}">
				<a href="javascript:io.setVote('object',${infoObject.id}, 'true');"><img src="images/btn_thumbsup_large.png" alt="YES" class="floatRight" style="margin-right:5px;"><a href="javascript:io.setVote('object', ${infoObject.id}, 'false');"><img src="images/btn_thumbsdown_large.png" alt="NO" class="floatLeft" margin-left:5px;"></a></span>
			</c:when>
			<c:otherwise>
				<div class="box3" style="text-align:left;padding:2px;">Your vote has been recorded. Thank you for your participation.</div>
			</c:otherwise>
		</c:choose>
	</span></div>
</div>
	<!-- end voting tally menu -->
	
	
	
	<!-- begin summary -->
		<div id="summary" class="box3 floatLeft">
			<pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">
			  <p>${infoObject.object.theme.summary}</p>
			</pg:termHighlight>
	
			<ul class="tagsInline">
				<li class="tagsInline"><strong>Tags highlighted by the moderator:</strong> </li>
				<c:forEach var="tag" items="${infoObject.object.theme.tags}">
					<li class="box8 tagsInline"><a href="javascript:changeCurrentFilter(${tag.id});">${tag.name}</a></li>
				</c:forEach>
			</ul>
			<div style="clear: left;"></div>
			<p align="right"><a href="javascript:window.open('sdConcerns.do?ioid=${infoObject.id}&isid=${structure.id}','themeConcerns','width=730,height=500,resizable=yes,scrollbars=yes'); void(0);">View all Concerns for this Summary <img src="images/external.png" alt="(new window)" /></a></p>
		</div>
  <!-- end summary -->
  
  	
	



</pg:fragment>





