<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<pg:fragment type="html">
<style type="text/css" media="screen">
		  @import "styles/step2.css";
</style>

	<!--####
		Project: Let's Improve Transportation!
		Page: SDCrit Structure Target
		Description: This is the object in the SDcriteria discussion room
		Author(s): 
		     Front End: Jordan Isip, Adam Hindman, Issac Yang
		     Back End: Zhong Wang, John Le
		Todo Items:
			[x] Initial Skeleton Code (Jordan)
			[ ] Add JavaScript to get criteria (Jordan)
			[ ] Integrate Layout (Adam)
	#### -->
	<!-- begin "overview and instructions" area -->
	<div id="overview" class="box2">
		<h3 class="headerColor">Instructions</h3>
		<p>
			"Planning factors" are common used to help evaluate which proposed transportation improvement projects are best 
			suited to address problems with our transportation system.  Here we review and discuss nine planning factors. 
			In step 3 you will review proposed transportation projects which are evaluated based on the nine factors.
		</p>
		<p>
			To assist in your review of these factors, the moderator determined which concern themes are most closely related 
			to each factor.  Consider how well these factors address the range of concerns expressed by participants in Step 1.
			Consider also which factors may be more important to you in the evaluation of proposed transportation projects.
		</p>
			
		
	</div>
	<!-- end overview -->

	<h3 class="headerColor clearBoth">All planning factors and related concern themes</h3>

			<!-- Begin voting tally menu -->
	<div id="votingMenu" class="floatLeft"><div id="voting-structure${infoStructure.id}">
		<div id="votingMenuTally" class="box1">
			<span id="structure_question_status">
				<h2>${infoStructure.numAgree} of ${infoStructure.numVote}</h2>
			agree with that these planning factors adequately address the concerns expressed by participants in Step 1.</div>
		</span>
		<p>Do these planning factors adequately address the concerns expressed by participants in step 1?</p>
		<span id="structure_question">

			<c:choose>
				<c:when test="${voting == null}">
					<a href="javascript:io.setVote('structure','${infoStructure.id}', 'true');"><img src="images/btn_thumbsup_large.png" alt="YES" class="floatRight" style="margin-right:5px;"><a href="javascript:io.setVote('structure', '${infoStructure.id}', 'false');"><img src="images/btn_thumbsdown_large.png" alt="NO" class="floatLeft" style="margin-left:5px;"></a></span>
				</c:when>
				<c:otherwise>
					Your vote has been recorded. Thank you for your participation.
				</c:otherwise>
			</c:choose>
		</span></div>
	</div>
		<!-- end voting tally menu -->

	<div id="criteria" class="box3 floatLeft">
		<!--load summary javascript here -->
	</div>
	
</pg:fragment>

<pg:fragment type="script">
	/* *************** Get information structure for object *************** */
	io.getCriteria = function(){
		CriteriaAgent.getAllCriterion({}, {
			callback:function(data){
				if (data.successful){
					$(criteria).innerHTML = data.html;
				}else{
					$(criteria).innerHTML = "<b>Error in CriteriaAgent.getAllCriterion Method: </b>" + data.reason; 
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("CriteriaAgent.getAllCriterion( error:" + errorString + exception);
			}
		});
	};
	
	/* *************** Toggle Tree Node to view Asscociated Objectives for a Given Criterion *************** */
	io.expandList = function(objective,icon){
		Effect.toggle(objective, 'appear', {duration: .5, afterFinish:
			//window.setTimeout(toggleIcon,100);
			function(){
				if ($(objective).style.display != ""){
						$(icon).src = "/images/plus.gif";
					}else{
						$(icon).src = "/images/minus.gif";
					}
				}
		});
	};
	
	//DO ON LOAD
	io.getCriteria();

</pg:fragment>