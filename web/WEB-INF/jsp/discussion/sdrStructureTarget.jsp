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
		<h3>Overview and instructions</h3>
		Our final step is to prepare a final report to communicate the results of the <em>LIT Challenge</em>. A draft report has already been created for your review.
		<ul><strong>Activities in this step</strong>
			<li>Review the report and discuss with other participants</li>
			<li>Suggest revisions to the report in the discussion forum (below)</li>
			<li>Vote in the draft report poll</li>
		</ul>
		<a href="#" onclick="Effect.toggle('hiddenRM','blind'); return false">Read more about this step</a>
			<div id="hiddenRM" style="display:none">
				<ul><li>The moderator will begin making revisions to the draft report. Once the revisions are complete, all participants will have the opportunity to vote to endorse (or not) the report. The vote results will appear in the executive summary of the final report.</li>
				<li>The final report will be published online for the public to view. Special invitations to read the report will be sent to transportation decision makers and members of the media.</li></ul>				
			</div>
	</div>
	<!-- end overview -->
	
	
	<h3 class="headerColor clearBoth">Let's Improve Transportation DRAFT Report: Executive Summary</h3>

	<!-- Begin voting tally menu -->
	<div id="votingMenu" class="floatLeft"><div id="voting-structure${infoStructure.id}">
		<div id="votingMenuTally" class="box1">
			<span id="structure_question_status">
			<h2>${infoStructure.numAgree} of ${infoStructure.numVote}</h2>
			agree that this draft report adequately describes the outcome of the LIT Challenge?</div>
		</span>
		<p>Does this draft report adequately describe the outcomes of the LIT Challenge?</p>
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
  <pg:grab var="suite" name="org.pgist.report.ReportSuite" id="${infoStructure.suiteId}" />
  <pg:narrow name="suite" />
	<div id="summary" class="box3 floatLeft">
		<h3 class="headerColor">Let's Improve Transportation Final Report: Executive Summary (DRAFT)</h3>
		<div id="executiveSummary">

			This report describes the results of the <em>Let's Improve Transportation Challenge</em>, an online experiment in participatory democracy facilitated by researchers at the University of Washington. ${suite.statsPart1.totalUsers} residents of King, Pierce, and Shohomish and neighboring counties  contributed  their ideas and concerns in the LIT Challenge.
			</p>
		    <p> 
		  The recommended package contains ${suite.statsES.totalProjects} road and transit projects. The total cost of the package is $11.8 Billion. The package was endorsed 61% of the voting participants. 

		  <div>
			    <strong>This report includes 4 sections:</strong>
				<ol>
					<li>
					    <a href="javascript:io.goToFullReport('participants');">The participants and their concerns about transportation</a></li>
					<li><a href="javascript:io.goToFullReport('planningfactors');">Improvement factors used in project evaluation</a></li>
					<li><a href="javascript:io.goToFullReport('projects');">Project selection, funding selection, and personal package creation</a></li>
					<li><a href="javascript:io.goToFullReport('packages');">Evaluation of candidate packages and package selection</a></li>
				</ol>
		  </div>
			<input type="button" class="floatRight" onclick="io.goToFullReport();" value="Read the full report" />
	  </div>
	</div>
</pg:fragment>

<pg:fragment type="script">
	//All Javascript that is internal to this page must go here - not sdRoomMain.
	//Add Javascript to build tree list

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
	io.loadDynamicFile('/styles/step4-start.css');
	io.loadDynamicFile('/dwr/interface/ProjectAgent.js');
	
	/* *************** Build Package Link *************** */
	
	io.goToFullReport = function(anchor){
    anchor = (anchor) ? "#"+anchor : "";
    
    var url = "report.do?";
    url += "workflowId=${wfinfo.workflowId}&contextId=${wfinfo.contextId}&activityId=${wfinfo.activityId}";
    url += "&cct_id=${experiment.cct.id}";
    <c:set var="voteSuite" value="null" />
    <c:forEach var="item" items="${experiment.pkgSuite.voteSuites}">
      <c:if test="${item.finalVote}">
        <c:set var="voteSuite" value="${item}" />
      </c:if>
    </c:forEach>
    url += "&voteSuiteId=${voteSuite.id}";
    url += "&repoSuiteId=${experiment.reportSuite.id}";
    url += "&fundSuiteId=${experiment.fundingSuite.id}";
    url += "&projSuiteId=${experiment.projectSuite.id}";
    url += "&critSuiteId=${experiment.critSuite.id}";
    url += "&pkgSuiteId=${experiment.pkgSuite.id}";
    url += anchor;
    
		window.open(url,'_blank','width=1000,height=600,resizable=yes,scrollbars=yes');
    
		//window.open("report.do?"+io.wfInfo+"&cct_id="+io.cctId+"&repoSuiteId="+ io.repoSuiteId +"&projSuiteId="+io.projSuiteId+"&critSuiteId="+io.critSuiteId+"&fundSuiteId="+io.fundSuiteId+"&projSuiteId="+io.projSuiteId+"&voteSuiteId="+io.voteSuiteId+"&pkgSuiteId="+io.pkgSuiteId+anchor,'_blank','width=1000,height=600,resizable=yes,scrollbars=yes');
	}
	
</pg:fragment>
