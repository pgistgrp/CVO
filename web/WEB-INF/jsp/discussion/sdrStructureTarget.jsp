<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<pg:fragment type="html">
	<!--####
		Project: Let's Improve Transportation!
		Page: SDR Structure Target
		Description: This is the object in the SDReport discussion room
		Author(s): 
		     Front End: Jordan Isip, Adam Hindman,  
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
			Here you can review proposed projects for improving or expanding our regional transportation system.  Click on a project
			to review information and discuss its merits or drawbacks with other participants.
		</p>
		<p>
			Each of the project has been examined by a panel of specialists who assigned scores based on the criteria we reviewed in Step 2.
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

	<div id="summary" class="box3 floatLeft">
		<h3 class="headerColor">Let's Improve Transportation Final Report: Executive Summary (DRAFT)</h3>
		<div id="executiveSummary"> 298 residents of King, Pierce, and Snohomish county
			worked together online over the course of 5 weeks to learn about transportation
			problems in our region, discuss their own concerns, and create a package of
			transportation projects and funding sources to address our transportation needs.
			On November 2, 2007 they released the results of their efforts.
			<p>The package contains 32 road and transit projects across the 3 county region.
				It is funded by a combination of bridge tolls, parking taxes, and vehicle excise
				fees. The total cost of the package is $16 billion. The package was endorsed
				by 81% of the participants (256 our of 298 participating).</p>
			<div>
			    <strong>This report includes 4 sections:</strong>
				<ol>
					<li><a href="report.do#participants">The participants and their concerns about transportation</a></li>
					<li><a href="report.do#planningfactors">"Planning factors" used in project evaluation</a></li>
					<li><a href="report.do#projects">Project selection and personal package creation</a></li>
					<li><a href="report.do#packages">Evaluation of packages</a></li>
				</ol>
			</div>
			<input type="button" class="floatRight" value="Read the full report" />
		</div>
	</div>
</pg:fragment>

<pg:fragment type="script">

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

	/* *************** load a dynamic javascript or css file ****************/

	io.loadDynamicFile = function(file){
		var start = file.indexOf('.') + 1
		var finish = file.length
		var type = file.substring(start,finish)
		
		var headElem = document.getElementsByTagName('head')[0];
		if(type == "css"){
			var cssLinkElem = document.createElement('link');
			cssLinkElem.setAttribute('href', file);
			cssLinkElem.setAttribute('type', 'text/css');
			cssLinkElem.setAttribute('rel', 'stylesheet');
			headElem.appendChild(cssLinkElem);
		}else{ //javascript
			var jsLinkElem = document.createElement('script');
			jsLinkElem.setAttribute('src', file);
			jsLinkElem.setAttribute('type', 'text/javascript');
			headElem.appendChild(jsLinkElem);
		}
		
	}

	
	/* *************** loading on getTargets() in SDRoomMain *************** */
	io.loadDynamicFile('styles/step5.css');
	io.loadDynamicFile('/dwr/interface/ReportAgent.js');

</pg:fragment>