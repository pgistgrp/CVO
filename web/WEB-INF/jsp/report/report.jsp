<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<!--####
	Project: Let's Improve Transportation!
	Page: Full Report
	Description: This is a full report of the entire challenge.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: John Le, Zhong Wang
	[ ] Order project and funding sources within cp (John)
	[ ] Appendix A and B hardcoded? (Matt and Kevin)
	[ ] Package vote results (John and Jordan)
	[ ] Statistics
#### -->

<html:html>
<head>
<title>Let's Improve Transportation Final Report (DRAFT)</title>
<style type="text/css" media="screen">
        @import "styles/lit.css";
				@import "styles/step4b-voteresults.css";
				@import "styles/step5-report.css";
</style>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/interface/ReportAgent.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>

<script type="text/javascript" charset="utf-8">
function vote(vote) {
	ReportAgent.createReportVote({suiteId:${repoSuiteId}, vote:vote});
	setTimeout("checkvoted()",100);
}

function checkvoted() {
	 ReportAgent.getUserVoted({suiteId:${repoSuiteId}}, {
		callback:function(data){
			if (data.voted){
				hide('votingBox');
				hide('pollresults');
				pollresults();
			}else{
					
			}
		},
		
	});
}

function hide(divid){
	if(document.getElementById(divid).style.display!="none") {
		document.getElementById(divid).style.display="none";
	} else {
		document.getElementById(divid).style.display="block";
	}
}

function pollresults() {
		 ReportAgent.getVoteStats({suiteId:${repoSuiteId}}, {
		callback:function(data){
			if (data.successful){
				$('pollresults').innerHTML = data.html;
				
			}else{
				$('pollresults').innerHTML = "<b>Error in ReportAgent.getVoteStats Method: </b>" + data.reason; 
			}
		},
		errorHandler:function(errorString, exception){ 
		alert("ReportAgent.getVoteStats( error:" + errorString + exception);
		}
	});
}
</script>
</head>
<body>
	

	<!--
		Themes for summaries: ***${summaries}***
		Criteria References: ***${cr}***
		Clustered Packages: ***${cp}***
		Preferred Package: ***${pp}***
		Vote Suite Stats: ***${vss}***
	-->
	

<div id="container">
	<c:choose>
			<c:when test='${finalized == "true"}'>
				<h1 style="text-align:center">Let's Improve Transportation Final Report</h1>
				<c:choose>
					<c:when test='${finalized == "true"}'>
						<div id="votingBox" class="box6 clearfix">
							<h3>Please respond to this endorsement vote by <strong>${reportVoteDate}</strong></h3>
							<div class="left">
								This final report will be made public on ${finalReportDate}<br/>
							</div>
						  <div class="right">
								<p>Do you wish to endorse the Let's Improve Transportation Final Report?</p>
								<input name="yes" id="yes" type="button" value="yes" onClick="javascript:vote('yes');" /> <input name="no" id="no" type="button" value="no" onClick="javascript:vote('no');" />
							</div>		
						</div>
						<!--hidden poll results -->
						<div id="pollresults" class="box6 clearfix" style="display:none;">
							<h3>Poll Results</h3>
							<div class="left">
								Text Here<br/>
							</div>
						  <div class="right">
								<p>Text Here</p>
							</div>		
						</div>
						
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<h1 style="text-align:center">Let's Improve Transportation DRAFT Report</h1>
			</c:otherwise>
	</c:choose>
	
	
	<div id="executiveSummary" class="box3 padding5 section peekaboobugfix">
		<h3 class="headingColor padding5 centerAlign">Executive Summary</h3>
		<p>
This report describes the results of the <em>Let's Improve Transportation Challenge</em>, an online experiment in participatory democracy facilitated by researchers at the University of Washington. ${statsES.totalUsers} residents 


<c:choose>
	<c:when test="${fn:length(counties) == 0}">
		
	</c:when>
	<c:otherwise>
	of 
	<c:set var="size" value="${fn:length(counties)}" />

	<c:forEach var="i" begin="0" end="${size-2}" step="1" varStatus ="status">
		${counties[i].name},
	</c:forEach>
	and ${counties[size-1].name} county 	
	</c:otherwise>
</c:choose>


 worked together over the course of four weeks to learn about transportation problems, discuss their concerns, and collectively recommend a package of improvement projects and funding sources to address regional transportation needs. On ${finalReportDate} they released the results of their efforts. 

The recommended package contains ${statsES.totalProjects} road and transit projects across the three-county region. 
<c:choose>
	<c:when test="${statsES.totalCost == null}">
		*** Error Total Cost for project was null ***
	</c:when>
	<c:otherwise>
It is funded by a combination of bridge tolls, parking taxes, and vehicle excise fees. The total cost of the package is $${statsES.totalCost}. 	
	</c:otherwise>
</c:choose>

<c:set var="numEndorsed" value="${statsES.numEndorsed}" />
<c:set var="totalVotes" value="${statsES.totalVotes}" />
<c:choose>
	<c:when test="${totalVotes == 0}">
		*** Error no users voted on this package ***
	</c:when>
	<c:otherwise>
	The package was endorsed by 
	<fmt:formatNumber type="percent">${numEndorsed / totalVotes}</fmt:formatNumber>
	of the participants (${numEndorsed} our of ${totalVotes} participating).		
	</c:otherwise>
</c:choose>
</p>
		<p>${executiveSummary}</p>
		<div class="floatLeft" style="margin:0em 2em"><strong>This report includes 4 sections:</strong>
			<ol>
				<li><a href="#participants">The participants and their concerns about transportation</a></li>
				<li><a href="#planningFactors">Improvement factors used in project evaluation</a></li>
				<li><a href="#projects">Individual project review and  package creation</a></li>
				<li><a href="#packages">The participant's recommended transportation package </a></li>
			</ol>
		</div>
		<div class="floatLeft" style="margin-right:1em"> <strong>Report appendices</strong><p>
			<a href="#appendixA">Appendix A: Project scoring methodology</a><br />
			<a href="#appendixB">Appendix B: Candidate package creation methodology</a><br />
			<a href="#appendixC">Appendix C: The six candidate packages</a></p>
		</div>
		<div class="clearBoth"></div>
	</div>
	<!-- Begin participants + concerns -->
	<div id="participants" class="box3 padding5 section">
		<h3 class="headingColor padding5 centerAlign">1. The participants and their concerns
			about transportation</h3>
		<p>
		${statsPart1.totalUsers} residents
		<c:choose>
			<c:when test="${fn:length(statsPart1.counties) == 0}">
				
			</c:when>
			<c:otherwise>
			of 
			<c:set var="stats1size" value="${fn:length(statsPart1.counties)}" />
			<c:set var="temp" value="0" />
				<c:forEach var="county" items="${statsPart1.counties}" varStatus="loop">
					<c:set var="temp" value="${temp+1}"/>
					<c:choose>
						<c:when test="${temp == stats1size}">
						and ${county.name}
						</c:when>
						<c:otherwise>
						${county.name},
						</c:otherwise>
					</c:choose>				
				</c:forEach>
			</c:otherwise>
		</c:choose>

		contributed their ideas and concerns in the LIT Challenge. Here is some demographic information about these contributors. </p>
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr class="odd">
				<td><strong>Gender:</strong></td>
				<td>
				<c:choose>
					<c:when test="${statsPart1.totalUsers == 0}">
						N/A no users submitted concerns
					</c:when>
					<c:otherwise>
					
					<fmt:formatNumber type="percent">${statsPart1.males / statsPart1.totalUsers}</fmt:formatNumber>,
					<fmt:formatNumber type="percent">${statsPart1.females / statsPart1.totalUsers}</fmt:formatNumber>
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr>
				<td><strong>County of residence:</strong></td>
				<td>
				<!-- display counties -->
				<c:choose>	
					<c:when test="${fn:length(statsPart1.counties) == 0}">
						<p>No Counties Available</p>
					</c:when>
					<c:otherwise>

					<c:forEach var="county" items="${statsPart1.counties}" varStatus="loop">
						<fmt:formatNumber type="percent">${statsPart1.countyStats[county]/statsPart1.totalUsers}</fmt:formatNumber> ${county.name},					
					</c:forEach>
					
					</c:otherwise>
				</c:choose>		</td>
			</tr>
			<tr class="odd">
				<td><strong>Primary mode of transportation (daily commute):</strong></td>
				<td>
				<c:choose>	
					<c:when test="${fn:length(statsPart1.transTypes) == 0}">
						<p>No Transportation Types Available</p>
					</c:when>
					<c:otherwise>

					<c:forEach var="transport" items="${statsPart1.transTypes}" varStatus="loop">
						<fmt:formatNumber type="percent">${statsPart1.transportStats[transport]/statsPart1.totalUsers}</fmt:formatNumber>  ${transport},
					</c:forEach>
					</c:otherwise>
				</c:choose>
				</td>
			</tr> 
			<tr>
				<td><strong>Yearly household income</strong></td>
				<td>
				<c:choose>	
					<c:when test="${fn:length(statsPart1.incomeRanges) == 0}">
						<p>No Transportation Types Available</p>
					</c:when>
					<c:otherwise>

					<c:forEach var="income" items="${statsPart1.incomeRanges}" varStatus="loop">
						<fmt:formatNumber type="percent">${statsPart1.incomeStats[income]/statsPart1.totalUsers}</fmt:formatNumber> ${income}, </c:forEach>
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
		</table>
		<br />
		<p>${part1a}</p>
		<h3>Concerns expressed by participants</h3>
		<p>${part1b}</p>
		<blockquote>
			<c:forEach var="theme" items="${summaries}" varStatus="loop">
				<h4>${theme.title}</h4>
				<p>${theme.summary}</p>
				<p>110 out of 134 agree that this summary reflects the concerns of all participants.<br />
					<a href="#">Read participant concerns related to ${theme.title}</a></p>
			</c:forEach>
		</blockquote>
	</div>
	<!-- End participants + concerns -->
	<!-- Begin planning factors -->
	<div id="planningFactors" class="box3 padding5 section">
		<h3 class="headingColor padding5 centerAlign">2. Planning factors used in project
			evaluation</h3>
	    <p>
		The second step included a review, discussion, and weighting of ${statsPart2.quanity} "improvement factors" used to evaluate the proposed transportation projects. They were used by a panel of transportation specialists to qualitatively “score” proposed projects. (Appendix B includes a detailed discussion of the factors and scoring process). 

After a period of discussion about the relevance of the improvement factors to the concerns discussed in Step 1, participants weighted the relative importance of each factor based on their individual preferences. 100 total points were available to distribute between the ${statsPart2.quanity} factors. The following table displays information about the improvement factors and participant weight preferences.		</p>
		
		<p>${part2a}</p>
		<div id="criteria" class="box3 floatLeft">
			<!-- START All Criteria List -->
			<div id="allCriteriaList">
				<div class="criteriaListHeader">
					<div class="weighCriteriaCol1 floatLeft">
						<h4 class="headerColor">Planning factor</h4>
					</div>
					<div class="weighCriteriaCol2 floatLeft">
						<h4 class="headerColor">Your weight</h4>
					</div>
					<div class="clearBoth"></div>
				</div>
				
				<c:forEach var="ref" items="${cr}" varStatus="loop">
					<div class="criteriaListRow row">
						<div class="weighCriteriaCol1 floatLeft">
							<div class="floatLeft">${ref.criterion.name}</div>
						</div>
						<div class="weighCriteriaCol2 floatLeft">${ref.grade}</div>
						<div class="clearBoth"></div>
						<div class="objectives" id="objectives${loop.index}">
							<ul>
								<strong>Objectives</strong>
								<c:forEach var="obj" items="${ref.criterion.objectives}" varStatus="loop2">
									<li>${obj.description}</li>
								</c:forEach>							
							</ul>
						</div>
					</div>
				</c:forEach>
			</div>
			<!-- END All Criteria List -->
		</div>
		<!-- end criteria (summary) -->
		<div class="clearBoth"></div>
	</div>
	<!-- End planning factors -->
	<!-- Begin projects -->
	<div id="projects" class="box3 padding5 section">
		<h3 class="headingColor padding5 centerAlign"><a name="projects">3. Project selection, Funding selection, 
				and personal package creation</a></h3>
		
		<p>
		In the third step participants first reviewed and discussed a list of ${statsPart3.quanity} proposed transportation improvement projects in the three-county region. This was followed by a review and discussion of available options for funding these transportation improvements. Finally, participants were each given the opportunity to create their own personal transportation package including both a set of projects and a set of funding sources which provide enough money to pay for the projects. The results of participant project and funding source selection are described below. Note: only  ${statsPart4.userCompleted} of the total ${statsES.totalUsers} participants completed this step.		</p>
		
			
			<p>${part3a}</p>
		<table cellpadding=0 cellspacing=0>
			<tr class="tableHeading">
				<th class="col1">Project</th>
				<th class="col2">Money needed</th>
				<th class="col3">County</th>
				<th class="col4">Number of participants who selected</th>
				<th class="col5">% of participants who selected</th>
			</tr>
			<c:if test="${fn:length(up.projAltRefs) == 0}">
				<tr colspan="5">
					<td colspan="5"><p>You did not select any projects when you created your package.</p></td>
				</tr>
			</c:if>
			
			<c:set var="prevProj" value="" />
			<c:forEach var="altRef" items="${up.projAltRefs}" varStatus="loop">
				<c:if test="${prevProj != altRef.projectRef.project.name}"> <!--workaround -->
					<tr class="project" colspan="5">
						<td>${altRef.projectRef.project.name}</td>
					</tr>
				</c:if>
				<c:set var="prevProj" value="altRef.projectRef.project.name" />
				
				<tr class="project-options">
					<td class="col1">${altRef.alternative.name}</td>
					<td class="col2">$<fmt:formatNumber type="number">${altRef.alternative.cost}</fmt:formatNumber>  million</td>
					<td class="col3">${altRef.alternative.county}</td>
					<td class="col4">145 of 212</td>
					<td class="col5">65%</td>
				</tr>
			</c:forEach>
		</table>
		
		<table cellpadding=0 cellspacing=0>
			<tr class="tableHeading">
				<th class="col1">Funding source</th>
				<th class="col2">Money raised</th>
				<th class="col3">Annual cost to average resident</th>
				<th class="col4">Number of participants who selected</th>
				<th class="col5">% of participants who selected</th>
			</tr>
			<c:if test="${fn:length(up.fundAltRefs) == 0}">
				<tr colspan="5">
					<td colspan="5"><p>You did not select any funding sources when you created your package.</p></td>
				</tr>
			</c:if>
			
			<c:set var="prevFund" value="" />
			<c:forEach var="altRef" items="${up.fundAltRefs}" varStatus="loop">
				<c:if test="${prevFund != altRef.sourceRef.source.name}"> <!--workaround -->
					<tr class="project" colspan="5">
						<td>${altRef.sourceRef.source.name}</td>
					</tr>
				</c:if>
				<c:set var="prevFund" value="altRef.sourceRef.project.name" />
				
				<tr class="project-options">
					<td class="col1">${altRef.alternative.name}</td>
					<td class="col2">$<fmt:formatNumber type="number">${altRef.alternative.revenue}</fmt:formatNumber>  million</td>
					<td class="col3">${altRef.alternative.avgCost}</td>
					<td class="col4">145 of 212</td>
					<td class="col5">65%</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<!-- End projects -->
	
	<!-- Begin packages -->
	<div id="packages" class="box3 padding5 section">
		<h3 class="headingColor padding5 centerAlign"><a name="packages">4. Evaluation
				of packages</a></h3>
				
				<p>
				Participants created ${statsPart4.totalPackages} unique packages. In order to narrow the field of packages under consideration for recommendation, five new packages were computationally generated. These new candidate packages collectively represent the diversity of packages created by participants in Step 3. Details about each of these five packages, as well as the methodology used to create them is available in Appendix C. </p>
<p>
In the fourth step, participants reviewed and evaluated these five candidate packages as well as the “Roads and Transit” package which will appear on the ballot in the November 2007 election. A preliminary poll regarding participants’ degree of support for each of the packages was used to inform the discussion. This was followed by a final package recommendation vote.</p>
				
		<p>${part4a}</p>
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr class="odd">
				<td><strong>Gender:</strong></td>
				<td>${statsPart4.males}% male, ${statsPart4.females}% female</td>
			</tr>
			<tr>
				<td><strong>County of residence:</strong></td>
				<td><c:choose>	
					<c:when test="${fn:length(statsPart4.counties) == 0}">
						<p>No Counties Available</p>
					</c:when>
					<c:otherwise>

					<c:forEach var="county" items="${statsPart1.counties}" varStatus="loop">
						${statsPart4.countyStats[county].name} ${county.name},					</c:forEach>
					</c:otherwise>
				</c:choose>	</td>
			</tr>
			<tr class="odd">
				<td><strong>Primary mode of transportation (daily commute):</strong></td>
				<td><c:choose>	
					<c:when test="${fn:length(statsPart4.transTypes) == 0}">
						<p>No Transportation Types Available</p>
					</c:when>
					<c:otherwise>

					<c:forEach var="transport" items="${statsPart4.transTypes}" varStatus="loop">
						${statsPart4.transportStats[transport]} ${transport},					</c:forEach>
					</c:otherwise>
				</c:choose></td>
			</tr>
		</table>
		<div class="clearBoth"></div>
		<br />
		<!-- begin PACKAGE ENDORSEMENT VOTE RESULTS -->
		<div id="voteResults">
			<h3 class="headerColor">Package Endorsement Vote Results</h3>
			<div class="VoteListRow row clearfix" style="background:#D6E7EF">
				<div class="voteCol1 floatLeft">&nbsp;</div>
				<div class="voteCol2 floatLeft">I would <strong>enthusiastically endorse</strong> this
					package</div>
				<div class="voteCol3 floatLeft">I am <strong>willing to endorse</strong> this
					package if it receives greatest participant support</div>
				<div class="voteCol4 floatLeft">I would <strong>not endorse</strong> this package,
					regardless of its support among other participants</div>
				<div class="clearBoth"></div>
			</div>
			<div class="VoteListRow row highlight">
				<div class="voteCol1 floatLeft clearfix">
					<div class="floatLeft">Package A </div>
				</div>
				<div class="voteCol2 floatLeft">50%</div>
				<div class="voteCol3 floatLeft">44%</div>
				<div class="voteCol4 floatLeft">6%</div>
				<div class="clearBoth"></div>
			</div>
			<div class="VoteListRow row">
				<div class="voteCol1 floatLeft">Package B </div>
				<div class="voteCol2 floatLeft">43%</div>
				<div class="voteCol3 floatLeft">39%</div>
				<div class="voteCol4 floatLeft">18%</div>
				<div class="clearBoth"></div>
			</div>
			<div class="VoteListRow row">
				<div class="voteCol1 floatLeft"> Package C </div>
				<div class="voteCol2 floatLeft">40%</div>
				<div class="voteCol3 floatLeft">23%</div>
				<div class="voteCol4 floatLeft">37%</div>
				<div class="clearBoth"></div>
			</div>
			<div class="VoteListRow row">
				<div class="voteCol1 floatLeft">
					<div class="floatLeft">Package D </div>
				</div>
				<div class="voteCol2 floatLeft">33%</div>
				<div class="voteCol3 floatLeft">23%</div>
				<div class="voteCol4 floatLeft">44%</div>
				<div class="clearBoth"></div>
			</div>
			<div class="VoteListRow row">
				<div class="voteCol1 floatLeft">
					<div class="floatLeft">Package E </div>
				</div>
				<div class="voteCol2 floatLeft">12%</div>
				<div class="voteCol3 floatLeft">44%</div>
				<div class="voteCol4 floatLeft">44%</div>
				<div class="clearBoth"></div>
			</div>
			<div class="VoteListRow row">
				<div class="voteCol1 floatLeft">
					<div class="floatLeft">RTID Package</div>
				</div>
				<div class="voteCol2 floatLeft">12%</div>
				<div class="voteCol3 floatLeft">44%</div>
				<div class="voteCol4 floatLeft">44%</div>
				<div class="clearBoth"></div>
			</div>
		</div>
		<!-- end PACKAGE ENDORSEMENT VOTE RESULTS -->
		
		<div class="projSummary clearfix">
			<h3 class="headingColor padding5 clearfix">
				<span class="packageNum">Package 3 (The winning/preferred package)</span>
				<span class="totalCost">Total cost: $13 billion</span>
				<span class="yearlyCostToAvg">Yearly cost to the average resident: $230/year</span>
			</h3>
			<div class="obj-left floatLeft clearBoth">
				<!--Begin project list -->
				<div class="listRow row heading">
					<div class="projCol1 floatLeft" style="margin-left:.2em">
						<div class="floatLeft">
							<h4>Projects included in this package</h4>
						</div>
					</div>
					<div class="projCol2 floatLeft">
						<h4>Money Needed</h4>
					</div>
					<div class="projCol3 floatRight">
						<h4>County</h4>
					</div>
					<div class="clearBoth"></div>
				</div>
				<div class="listRow row">
					<h4 class="subHeading">Alaskan Way Viaduct</h4>
					<div class="clearBoth"></div>
				</div>
				<div class="listRow row">
					<div class="projCol1 floatLeft">
						<div class="floatLeft">Replace viaduct with 6-lane tunnel</div>
					</div>
					<div class="projCol2 floatLeft">$5 billion</div>
					<div class="projCol3 floatRight">King</div>
					<div class="clearBoth"></div>
				</div>
				<div class="listRow row">
					<h4 class="subHeading">SR 520 Floating Bridge</h4>
					<div class="clearBoth"></div>
				</div>
				<div class="listRow row">
					<div class="projCol1 floatLeft">
						<div class="floatLeft">6-lane bridge with ramp to UW</div>
					</div>
					<div class="projCol2 floatLeft">$4 billion</div>
					<div class="projCol3 floatRight">King</div>
					<div class="clearBoth"></div>
				</div>
				<div class="listRow row">
					<h4 class="subHeading">Light Rail: Seattle to Eastside</h4>
					<div class="clearBoth"></div>
				</div>
				<div class="listRow row">
					<div class="projCol1 floatLeft">
						<div class="floatLeft">Light rail Seattle to Overlake with tunnel</div>
					</div>
					<div class="projCol2 floatLeft">$4 billion</div>
					<div class="projCol3 floatRight">King</div>
					<div class="clearBoth"></div>
				</div>
				<div class="listRow row">
					<h4 class="subHeading">I-405 Improvements</h4>
					<div class="clearBoth"></div>
				</div>
				<div class="listRow row">
					<div class="projCol1 floatLeft">
						<div class="floatLeft">Two additional HOV lanes from SR 169 to I-90</div>
					</div>
					<div class="projCol2 floatLeft">$212 million</div>
					<div class="projCol3 floatRight">King</div>
					<div class="clearBoth"></div>
				</div>
				<div class="listRow row">
					<div class="projCol1 floatLeft">
						<div class="floatLeft">Two additional HOV lanes from SR 520 to I-5</div>
					</div>
					<div class="projCol2 floatLeft">$345 million</div>
					<div class="projCol3 floatRight">King</div>
					<div class="clearBoth"></div>
				</div>
				<!--End project list -->
			</div>
			<!-- end obj-left -->
			<!-- begin obj-right -->
			<div class="floatRight obj-right">
				<!-- Begin funding sources table -->
				<div class="listRow row heading">
					<div class="fundingCol1 floatLeft" style="margin-left:.2em">
						<div class="floatLeft">
							<h4>Projects included in this package</h4>
						</div>
					</div>
					<div class="fundingCol2 floatRight">
						<h4>Money raised</h4>
					</div>
					<div class="clearBoth"></div>
				</div>
				<div class="listRow row">
					<h4 class="subHeading">Gas Tax</h4>
					<div class="clearBoth"></div>
				</div>
				<div class="listRow row">
					<div class="fundingCol1 floatLeft">
						<div class="floatLeft">2.3 cent increase per gallon</div>
					</div>
					<div class="fundingCol2 floatRight">400 million</div>
					<div class="clearBoth"></div>
				</div>
				<div class="listRow row">
					<h4 class="subHeading">Sales Tax</h4>
					<div class="clearBoth"></div>
				</div>
				<div class="listRow row">
					<div class="fundingCol1 floatLeft">
						<div class="floatLeft">0.07% increase</div>
					</div>
					<div class="fundingCol2 floatRight">1000 million</div>
					<div class="clearBoth"></div>
				</div>
				<!-- end funding sources table -->
				<br />
			</div>
			<!-- end obj-right -->
		</div>
		<div class="clearBoth"></div>
		<!-- End packages -->
	</div>
	<!-- Begin Appendix A -->
	<div id="appendixA" class="box3 padding5 section">
		<h3 class="headingColor padding5 centerAlign"><a name="appendixA">Appendix A: Project
				scoring methodology</a></h3>
		<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla eleifend nibh
			tincidunt nibh dapibus vestibulum. Mauris nec nulla at nunc convallis cursus.
			Vivamus tincidunt nibh adipiscing risus. Sed vel odio. In dapibus. Nunc pulvinar,
			metus sed venenatis porttitor, mauris urna eleifend velit, vel adipiscing sapien
			risus et ante. In nunc. Sed eget dolor facilisis tortor aliquam auctor. Maecenas
			sit amet sem commodo orci lacinia interdum. Donec quam. Sed leo tortor, hendrerit
			nec, auctor et, rhoncus a, tortor. Ut dictum eros in metus.</p>
		<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla eleifend nibh
			tincidunt nibh dapibus vestibulum. Mauris nec nulla at nunc convallis cursus.
			Vivamus tincidunt nibh adipiscing risus. Sed vel odio.
		<p>
		<p>In dapibus. Nunc pulvinar, metus sed venenatis porttitor, mauris urna eleifend
			velit, vel adipiscing sapien risus et ante. In nunc. Sed eget dolor facilisis
			tortor aliquam auctor. Maecenas sit amet sem commodo orci lacinia interdum. Donec
			quam. Sed leo tortor, hendrerit nec, auctor et, rhoncus a, tortor. Ut dictum eros
			in metus.</p>
		<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla eleifend nibh
			tincidunt nibh dapibus vestibulum. Mauris nec nulla at nunc convallis cursus.
			Vivamus tincidunt nibh adipiscing risus. Sed vel odio. In dapibus. Nunc pulvinar,
			metus sed venenatis porttitor, mauris urna eleifend velit, vel adipiscing sapien
			risus et ante. In nunc. Sed eget dolor facilisis tortor aliquam auctor. Maecenas
			sit amet sem commodo orci lacinia interdum. Donec quam. Sed leo tortor, hendrerit
			nec, auctor et, rhoncus a, tortor. Ut dictum eros in metus.</p>
		<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla eleifend nibh
			tincidunt nibh dapibus vestibulum. Mauris nec nulla at nunc convallis cursus.
			Vivamus tincidunt nibh adipiscing risus. Sed vel odio. In dapibus. Nunc pulvinar,
			metus sed venenatis porttitor, mauris urna eleifend velit, vel adipiscing sapien
			risus et ante. In nunc. Sed eget dolor facilisis tortor aliquam auctor. Maecenas
			sit amet sem commodo orci lacinia interdum. Donec quam. Sed leo tortor, hendrerit
			nec, auctor et, rhoncus a, tortor. Ut dictum eros in metus. Lorem ipsum dolor
			sit amet, consectetuer adipiscing elit. Nulla eleifend nibh tincidunt nibh dapibus
			vestibulum. Mauris nec nulla at nunc convallis cursus. Vivamus tincidunt nibh
			adipiscing risus. Sed vel odio. In dapibus. Nunc pulvinar, metus sed venenatis
			porttitor, mauris urna eleifend velit, vel adipiscing sapien risus et ante. In
			nunc. Sed eget dolor facilisis tortor aliquam auctor. Maecenas sit amet sem commodo
			orci lacinia interdum. Donec quam. Sed leo tortor, hendrerit nec, auctor et, rhoncus
			a, tortor. Ut dictum eros in metus.</p>
		<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla eleifend nibh
			tincidunt nibh dapibus vestibulum. Mauris nec nulla at nunc convallis cursus.
			Vivamus tincidunt nibh adipiscing risus. Sed vel odio. In dapibus. Nunc pulvinar,
			metus sed venenatis porttitor, mauris urna eleifend velit, vel adipiscing sapien
			risus et ante. In nunc. Sed eget dolor facilisis tortor aliquam auctor. Maecenas
			sit amet sem commodo orci lacinia interdum. Donec quam. Sed leo tortor, hendrerit
			nec, auctor et, rhoncus a, tortor. Ut dictum eros in metus.</p>
		<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla eleifend nibh
			tincidunt nibh dapibus vestibulum. Mauris nec nulla at nunc convallis cursus.
			Vivamus tincidunt nibh adipiscing risus. Sed vel odio.
		<p>
		<p>In dapibus. Nunc pulvinar, metus sed venenatis porttitor, mauris urna eleifend
			velit, vel adipiscing sapien risus et ante. In nunc. Sed eget dolor facilisis
			tortor aliquam auctor. Maecenas sit amet sem commodo orci lacinia interdum. Donec
			quam. Sed leo tortor, hendrerit nec, auctor et, rhoncus a, tortor. Ut dictum eros
			in metus.</p>
		<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla eleifend nibh
			tincidunt nibh dapibus vestibulum. Mauris nec nulla at nunc convallis cursus.
			Vivamus tincidunt nibh adipiscing risus. Sed vel odio. In dapibus. Nunc pulvinar,
			metus sed venenatis porttitor, mauris urna eleifend velit, vel adipiscing sapien
			risus et ante. In nunc. Sed eget dolor facilisis tortor aliquam auctor. Maecenas
			sit amet sem commodo orci lacinia interdum. Donec quam. Sed leo tortor, hendrerit
			nec, auctor et, rhoncus a, tortor. Ut dictum eros in metus.</p>
		<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla eleifend nibh
			tincidunt nibh dapibus vestibulum. Mauris nec nulla at nunc convallis cursus.
			Vivamus tincidunt nibh adipiscing risus. Sed vel odio. In dapibus. Nunc pulvinar,
			metus sed venenatis porttitor, mauris urna eleifend velit, vel adipiscing sapien
			risus et ante. In nunc. Sed eget dolor facilisis tortor aliquam auctor. Maecenas
			sit amet sem commodo orci lacinia interdum. Donec quam. Sed leo tortor, hendrerit
			nec, auctor et, rhoncus a, tortor. Ut dictum eros in metus. Lorem ipsum dolor
			sit amet, consectetuer adipiscing elit. Nulla eleifend nibh tincidunt nibh dapibus
			vestibulum. Mauris nec nulla at nunc convallis cursus. Vivamus tincidunt nibh
			adipiscing risus. Sed vel odio. In dapibus. Nunc pulvinar, metus sed venenatis
			porttitor, mauris urna eleifend velit, vel adipiscing sapien risus et ante. In
			nunc. Sed eget dolor facilisis tortor aliquam auctor. Maecenas sit amet sem commodo
			orci lacinia interdum. Donec quam. Sed leo tortor, hendrerit nec, auctor et, rhoncus
			a, tortor. Ut dictum eros in metus.</p>
	</div>
	<!-- End Appendix A -->
	<!-- Begin Appendix B -->
	<div id="appendixB" class="box3 padding5 section"><a name="appendixB"></a>
		<h3 class="headingColor padding5 centerAlign">Appendix B: Candidate package creation
			methodology</h3>
		<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla eleifend nibh
			tincidunt nibh dapibus vestibulum. Mauris nec nulla at nunc convallis cursus.
			Vivamus tincidunt nibh adipiscing risus. Sed vel odio. In dapibus. Nunc pulvinar,
			metus sed venenatis porttitor, mauris urna eleifend velit, vel adipiscing sapien
			risus et ante. In nunc. Sed eget dolor facilisis tortor aliquam auctor. Maecenas
			sit amet sem commodo orci lacinia interdum. Donec quam. Sed leo tortor, hendrerit
			nec, auctor et, rhoncus a, tortor. Ut dictum eros in metus.</p>
		<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla eleifend nibh
			tincidunt nibh dapibus vestibulum. Mauris nec nulla at nunc convallis cursus.
			Vivamus tincidunt nibh adipiscing risus. Sed vel odio.
		<p>
		<p>In dapibus. Nunc pulvinar, metus sed venenatis porttitor, mauris urna eleifend
			velit, vel adipiscing sapien risus et ante. In nunc. Sed eget dolor facilisis
			tortor aliquam auctor. Maecenas sit amet sem commodo orci lacinia interdum. Donec
			quam. Sed leo tortor, hendrerit nec, auctor et, rhoncus a, tortor. Ut dictum eros
			in metus.</p>
		<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla eleifend nibh
			tincidunt nibh dapibus vestibulum. Mauris nec nulla at nunc convallis cursus.
			Vivamus tincidunt nibh adipiscing risus. Sed vel odio. In dapibus. Nunc pulvinar,
			metus sed venenatis porttitor, mauris urna eleifend velit, vel adipiscing sapien
			risus et ante. In nunc. Sed eget dolor facilisis tortor aliquam auctor. Maecenas
			sit amet sem commodo orci lacinia interdum. Donec quam. Sed leo tortor, hendrerit
			nec, auctor et, rhoncus a, tortor. Ut dictum eros in metus.</p>
		<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nulla eleifend nibh
			tincidunt nibh dapibus vestibulum. Mauris nec nulla at nunc convallis cursus.
			Vivamus tincidunt nibh adipiscing risus. Sed vel odio. In dapibus. Nunc pulvinar,
			metus sed venenatis porttitor, mauris urna eleifend velit, vel adipiscing sapien
			risus et ante. In nunc. Sed eget dolor facilisis tortor aliquam auctor. Maecenas
			sit amet sem commodo orci lacinia interdum. Donec quam. Sed leo tortor, hendrerit
			nec, auctor et, rhoncus a, tortor. Ut dictum eros in metus. Lorem ipsum dolor
			sit amet, consectetuer adipiscing elit. Nulla eleifend nibh tincidunt nibh dapibus
			vestibulum. Mauris nec nulla at nunc convallis cursus. Vivamus tincidunt nibh
			adipiscing risus. Sed vel odio. In dapibus. Nunc pulvinar, metus sed venenatis
			porttitor, mauris urna eleifend velit, vel adipiscing sapien risus et ante. In
			nunc. Sed eget dolor facilisis tortor aliquam auctor. Maecenas sit amet sem commodo
			orci lacinia interdum. Donec quam. Sed leo tortor, hendrerit nec, auctor et, rhoncus
			a, tortor. Ut dictum eros in metus.</p>
	</div>
	<!-- End Appendix B -->
	<!-- Begin Appendix C -->
	<div id="appendixC" class="box3 padding5 section"><a name="appendixC"></a>
		<h3 class="headingColor padding5 centerAlign">Appendix C: The ${fn:length(cp)} candidate packages</h3>
		<p>[Some static text here providing context]</p>
		
			
			<c:forEach var="cPkg" items="${cp}" varStatus="loop">
			<div class="projSummary clearfix">
				<h3 class="headingColor padding5 clearfix">
					<span class="packageNum">${cPkg.description}</span>
					<span class="totalCost">Total cost: $<fmt:formatNumber type="number">${cPkg.totalCost}</fmt:formatNumber> Million</span>
					<span class="yearlyCostToAvg">Yearly cost to the average resident: $<fmt:formatNumber type="number">${cPkg.avgResidentCost}</fmt:formatNumber> per year</span>
				</h3>
				<div class="obj-left floatLeft clearBoth">
					<!--Begin project list -->
					<div class="listRow row heading">
						<div class="projCol1 floatLeft" style="margin-left:.2em">
							<div class="floatLeft">
								<h4>Projects included in this package</h4>
							</div>
						</div>
						<div class="projCol2 floatLeft">
							<h4>Money Needed</h4>
						</div>
						<div class="projCol3 floatRight">
							<h4>County</h4>
						</div>
						<div class="clearBoth"></div>
					</div>
					<c:set var="superName" value="" />
					<c:forEach var="altRef" items="${cPkg.projAltRefs}" varStatus="loop">
						<div class="listRow row">
							<h4 class="subHeading">${(superName == altRef.projectRef.project.name) ? "" : altRef.projectRef.project.name}</h4>
							<c:set var="superName" value="${altRef.projectRef.project.name}" />
							<div class="clearBoth"></div>
						</div>
						<div class="listRow row">
							<div class="projCol1 floatLeft">
								<div class="floatLeft">${altRef.alternative.name}</div>
							</div>
							<div class="projCol2 floatRight">$<fmt:formatNumber type="number">${altRef.alternative.cost}</fmt:formatNumber> Million</div>
							<div class="clearBoth"></div>
						</div>
					</c:forEach>
					<!--End project list -->
				</div>
				<!-- end obj-left -->
				<!-- begin obj-right -->
				<div class="floatRight obj-right">
					<!-- Begin funding sources table -->
					<div class="listRow row heading">
						<div class="fundingCol1 floatLeft" style="margin-left:.2em">
							<div class="floatLeft">
								<h4>Funding sources included in this package</h4>
							</div>
						</div>
						<div class="fundingCol2 floatRight">
							<h4>Money raised</h4>
						</div>
						<div class="clearBoth"></div>
					</div>
					<c:set var="superName" value="" />
					<c:forEach var="altRef" items="${cPkg.fundAltRefs}" varStatus="loop">
						<div class="listRow row">
							<h4 class="subHeading">${(superName == altRef.sourceRef.source.name) ? "" : altRef.sourceRef.source.name}</h4>
							<c:set var="superName" value="${altRef.sourceRef.source.name}" />
							<div class="clearBoth"></div>
						</div>
						<div class="listRow row">
							<div class="fundingCol1 floatLeft">
								<div class="floatLeft">${altRef.alternative.name}</div>
							</div>
							<div class="fundingCol2 floatRight">$<fmt:formatNumber type="number">${altRef.alternative.revenue}</fmt:formatNumber> Million</div>
							<div class="clearBoth"></div>
						</div>
					</c:forEach>
					<!-- end funding sources table -->
					<br />
				</div>
				<!-- end obj-right -->

			<br />
			<br />
		</div>
		</c:forEach>
		
	<script type="text/javascript">
	checkvoted();
	</script>
	<!-- End Appendix C -->
</body>
</html:html>