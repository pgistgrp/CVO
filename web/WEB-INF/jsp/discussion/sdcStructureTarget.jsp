<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<pg:fragment type="html">
	<!-- Begin voting tally menu -->
	<div id="votingMenu" class="floatLeft"><div id="voting-structure${infoStructure.id}">
		<div id="votingMenuTally" class="box1">
			<span id="structure_question_status">
				<h2>${infoStructure.numAgree} of ${infoStructure.numVote}</h2>
			agree with summary</div>
		</span>
		<p>Does this list reflect the group's concerns?</p>
		<span id="structure_question">
			<c:choose>
				<c:when test="${voting == null}">
					<a href="javascript:io.setVote('structure','${infoStructure.id}', 'true');"><img src="images/btn_thumbsup_large.png" alt="YES" class="floatRight" style="margin-right:5px;"><a href="javascript:io.setVote('structure', '${infoStructure.id}', 'false');"><img src="images/btn_thumbsdown_large.png" alt="NO" class="floatLeft" style="margin-left:5px;" /></a></span>
				</c:when>
				<c:otherwise>
					Your vote has been recorded. Thank you for your participation.
				</c:otherwise>
			</c:choose>
		</span></div>
	</div>
	<!-- end voting tally menu -->

	<div id="summary" class="box3 floatLeft">
		Below is a list of all the themes the moderator has identified in the concerns you and other participants submitted during Step 1a: Brainstorm Concerns. Each theme has its own room.  This room is for discussing whether any themes on that list should be added, removed, or changed.
	   <c:forEach var="infoObject" items="${infoStructure.infoObjects}">
		     <p>${infoObject.object}</p>
		</c:forEach>	
	</div>
</pg:fragment>

<pg:fragment type="script">
	SDAgent.getSummary({isid: io.structureId, ioid: io.objectId}, {  // change to getTarget()
		callback:function(data){
			if (data.successful){
				$(io.objectDiv).innerHTML = data.source.html;
				displayIndicator(false);
				}else{
					alert(data.reason);
					displayIndicator(false);
				}
			},
			errorHandler:function(errorString, exception){
				alert("get targets error:" + errorString + exception);
			}
		});
</pg:fragment>