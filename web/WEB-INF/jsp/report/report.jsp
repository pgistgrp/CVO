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
						<div id="pollresults" class="clearfix" style="display:none;">
							
								
						</div>
						<br>

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
	and ${counties[size-1].name} counties 	
	</c:otherwise>
</c:choose>


 worked together over the course of four weeks to learn about transportation problems, discuss their concerns, and collectively recommend a package of improvement projects and funding sources to address regional transportation needs. On ${finalReportDate} they released the results of their efforts. 

The recommended package contains ${statsES.totalProjects} road and transit projects across the three-county region. 
<c:choose>
	<c:when test="${statsES.totalCost == null}">
		*** Error Total Cost for project was null ***
	</c:when>
	<c:otherwise>
It is funded by a combination of bridge tolls, parking taxes, and vehicle excise fees. The total cost of the package is 	

							<c:if test="${statsES.totalCost > 999999 && statsES.totalCost < 1000000000}">
							$00000 Million.
							</c:if>
							<c:if test="${statsES.totalCost > 999999999 && statsES.totalCost < 1000000000000}">
							$11111 Billion.
							</c:if>
							
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
	222222
	of the participants (${numEndorsed} out of ${totalVotes} participating).		
	</c:otherwise>
</c:choose>
</p>
		<p>${executiveSummary}</p>
		<div class="floatLeft" style="margin:0em 2em"><strong>This report includes 4 sections:</strong>
			<ol>
				<li><a href="#participants">The participants and their concerns about transportation</a></li>
				<li><a href="#planningFactors">Improvement factors used in project evaluation</a></li>
				<li><a href="#projects">Individual project review and  package creation</a></li>
				<li><a href="#packages">The participants' recommended transportation package </a></li>
			</ol>
		</div>
		<div class="floatLeft" style="margin-right:1em"> <strong>Report appendices</strong><p>
			<a href="#appendixA">Appendix A: About <em>Let's Improve Transportation</em></a><br />
			<a href="#appendixB">Appendix B: Project scoring methodology</a><br />
			<a href="#appendixC">Appendix C: The candidate packages</a></p>
		</div>
		<div class="clearBoth"></div>
	</div>
	<!-- Begin participants + concerns -->
	<div id="participants" class="box3 padding5 section" style="font-size:10pt">
		<h3 class="headingColor padding5 centerAlign">1. The participants and their concerns
			about transportation </h3>
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
						<c:when test="${temp == stats1size && stats1size > 1}">
						and ${county.name}
						</c:when>
						<c:otherwise>
						${county.name}<c:if test="${stats1size > 1}">, </c:if>
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
					
					44444 Male<br/>
					55555 Female
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr>
				<td><strong>Area of residence:</strong></td>
				<td>
				<!-- display counties -->
				<c:choose>	
					<c:when test="${fn:length(statsPart1.counties) < 1}">
						<p>No Counties Available</p>
					</c:when>
					<c:otherwise>

					<c:forEach var="county" items="${statsPart1.counties}" varStatus="loop">
						<fmt:formatNumber type="percent">${statsPart1.countyStats[county]/statsPart1.totalUsers} </fmt:formatNumber>
						: ${county.name}<br/>				
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
						<fmt:formatNumber type="percent">${statsPart1.transportStats[transport]/statsPart1.totalUsers}</fmt:formatNumber>
						: 
						  ${transport.value}<br/>
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
						<c:if test="${statsPart1.incomeStats[income] > 0}">
						<fmt:formatNumber type="percent">${statsPart1.incomeStats[income]/statsPart1.totalUsers}</fmt:formatNumber>
						: ${income.value}<br/>
						</c:if>
					</c:forEach>
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
			<c:forEach var="reportThemeStat" items="${statsPart1.reportThemeStats}" varStatus="loop">
				<h4>${reportThemeStat.theme.title}</h4>
				<p>${reportThemeStat.theme.summary}</p>
				<p><em>${reportThemeStat.yesVotes} out of ${reportThemeStat.totalVotes} agree that this summary reflects the concerns of all participants.</em><br />
					<!--<a href="#">Read participant concerns related to ${theme.title}</a>--></p>
			</c:forEach>
		</blockquote>
  </div>
	<!-- End participants + concerns -->
	<!-- Begin planning factors -->
	<div id="planningFactors" class="box3 padding5 section">
		<h3 class="headingColor padding5 centerAlign">2. Planning factors used in project
			evaluation</h3>
      <p>
		The second step included a review, discussion, and weighting of ${statsPart2.quanity} "improvement factors" used to evaluate the proposed transportation projects. They were used by a panel of transportation specialists to qualitatively "score" proposed projects. (Appendix B includes a detailed discussion of the factors and scoring process). 

After a period of discussion about the relevance of the improvement factors to the concerns discussed in Step 1, participants weighted the relative importance of each factor based on their individual preferences. A total weight of 100 was distributed by each participant across the ${statsPart2.quanity} factors. The following table displays information about the improvement factors and participant weight preferences.		</p>
		<p>${part2a}</p>
		<div id="criteria" class="box3 floatLeft">
			<!-- START All Criteria List -->
			<div id="allCriteriaList">
				<div class="criteriaListHeader">
					<div class="weighCriteriaCol1 floatLeft">
						<h4 class="headerColor">Planning factor</h4>
					</div>
					<div class="clearBoth"></div>
				</div>
				
				<c:forEach var="ref" items="${cr}" varStatus="loop">
					<div class="criteriaListRow row">
						<div class="weighCriteriaCol1 floatLeft">
							<div class="floatLeft">${ref.criterion.name}</div>
						</div>
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
		<h3 class="headingColor padding5 centerAlign"><a name="projects">3. Project selection, funding selection, 
				and personal package creation</a></h3>
		
		<p>
		In the third step participants first reviewed and discussed a list of proposed transportation improvement projects in the 3 county region. This was followed by a review and discussion of available options for funding these transportation improvements. Finally, participants were each given the opportunity to create their own personal transportation package including both a set of projects and a set of funding sources which provide enough money to pay for the projects. The results of participant project and funding source selection are described below. Note: only ${statsPart3.userCompleted} of the total ${statsES.totalUsers} participants completed this step.
		</p>
			
			<p>${part3a}</p>
		<table cellpadding=0 cellspacing=0 style="font-size:10pt">
			<tr class="tableHeading">
				<th class="col1">Project</th>
				<th class="col2">Money needed</th>
				<th class="col3">County</th>
				<th class="col4">Number of participants who selected</th>
				<th class="col5">% of participants who selected</th>
			</tr>
			<c:if test="${fn:length(projRefs) == 0}">
				<tr colspan="5">
					<td colspan="5"><p>You did not select any projects when you created your package.</p></td>
				</tr>
			</c:if>
			
			<c:set var="projectRefs" value="${projRefs}" />
			<c:forEach var="projectRef" items="${projectRefs}" varStatus="loop">
				
					<tr class="project" colspan="5">
						<td>${projectRef.project.name}</td>
					</tr>
				
				<c:set var="altRefs" value="${projectRef.altRefs}" />
				<c:forEach var="altRef" items="${altRefs}" varStatus="loop">
				<c:if test="${altRef.alternative.numVotes > 0}">
				<tr class="project-options">
					<td class="col1">${altRef.alternative.name}</td>
					<td class="col2">
					
					<c:if test="${altRef.alternative.cost > 999999 && altRef.alternative.cost < 1000000000}">
					$<fmt:formatNumber type="number" maxFractionDigits="1">${altRef.alternative.cost/1000000}</fmt:formatNumber> million
					</c:if>
					<c:if test="${altRef.alternative.cost > 999999999 && altRef.alternative.cost < 1000000000000}">
					$<fmt:formatNumber type="number" maxFractionDigits="1">${altRef.alternative.cost/1000000000}</fmt:formatNumber> billion
					</c:if>
					
					</td>
					<td class="col3">${altRef.alternative.county}</td>
					<c:set var="yesVotes" value="${altRef.alternative.yesVotes}" />
					<c:set var="numPack" value="${fn:length(pkgSuite.userPkgs)}" />
					<td class="col4">${yesVotes} of ${numPack}</td>
					<c:choose>
						<c:when test="${numPack > 0}">
						<td class="col5"><fmt:formatNumber type="percent">${yesVotes/numPack}</fmt:formatNumber>
						</c:when>
						<c:otherwise>
						<td class="col5">N/A</td>
						</c:otherwise>
					</c:choose>					

				</tr>
				</c:if>
				</c:forEach>
			</c:forEach>
		</table>
		
		<table cellpadding=0 cellspacing=0 style="font-size:10pt">
			<tr class="tableHeading">
				<th class="col1">Funding source</th>
				<th class="col2">Money raised</th>
				<th class="col3">Annual cost to average resident</th>
				<th class="col4">Number of participants who selected</th>
				<th class="col5">% of participants who selected</th>
			</tr>
			<c:if test="${fn:length(fundRefs) == 0}">
				<tr colspan="5">
					<td colspan="5"><p>You did not select any funding sources when you created your package.</p></td>
				</tr>
			</c:if>
			
			
			<c:set var="fundingRefs" value="${fundRefs}" />
			<c:forEach var="fundingRef" items="${fundingRefs}" varStatus="loop">
				
					<tr class="project" colspan="5">
						<td>${fundingRef.source.name}</td>
					</tr>
				
				<c:set var="altRefs" value="${fundingRef.altRefs}" />
				<c:forEach var="altRef" items="${altRefs}" varStatus="loop">
				<c:if test="${altRef.alternative.numVotes > 0}">
				<tr class="project-options">
					<td class="col1">${altRef.alternative.name}</td>
				  <td class="col2">
				  
				  	<c:if test="${altRef.alternative.revenue > 999999 && altRef.alternative.revenue < 1000000000}">
					$<fmt:formatNumber type="number" maxFractionDigits="1">${altRef.alternative.revenue/1000000}</fmt:formatNumber> Million
					</c:if>
					<c:if test="${altRef.alternative.revenue > 999999999 && altRef.alternative.revenue < 1000000000000}">
					$<fmt:formatNumber type="number" maxFractionDigits="1">${altRef.alternative.revenue/1000000000}</fmt:formatNumber> Billion
					</c:if>
				  
				  
				  </td>
					<td class="col3">$<fmt:formatNumber maxFractionDigits="2" value="${altRef.alternative.avgCost}" /></td>
					<c:set var="yesVotes" value="${altRef.alternative.yesVotes}" />
					<c:set var="numPack" value="${fn:length(pkgSuite.userPkgs)}" />
					
					<td class="col4">${yesVotes} of ${numPack}</td>
					<c:choose>
						<c:when test="${numPack > 0}">
						<td class="col5"><fmt:formatNumber type="percent">${yesVotes/numPack}</fmt:formatNumber>
						</c:when>
						<c:otherwise>
						<td class="col5">N/A</td>
						</c:otherwise>
					</c:choose>	
				</tr>
				</c:if>
				</c:forEach>
			</c:forEach>
		</table>
			
	</div>
	<!-- End projects -->
	
	<!-- Begin packages -->
	<div id="packages" class="box3 padding5 section">
		<h3 class="headingColor padding5 centerAlign"><a name="packages">4. Evaluation
				of candidate packages and package selection</a></h3>
				
		<p>Participants created ${statsPart4.totalPackages} unique packages. In order to narrow the field of packages under consideration for recommendation, a small set of new packages were computationally generated. These new candidate packages collectively represent the diversity of packages created by participants in Step 3. Details about each of these packages, as well as the methodology used to create them is available in Appendix C. </p>
		<p>In the fourth step, participants reviewed and evaluated these candidate packages. A preliminary poll regarding participants' degree of support for each of the packages was used to inform the discussion. This was followed by a final package recommendation vote.</p>
		<p>${part4a}</p>
		<h3 class="headingColor padding5">Participants in the package recommendation vote</h3>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="font-size:10pt">
			<tr class="odd">
				<td><strong>Gender:</strong></td>
				<td>
				<c:choose>
					<c:when test="${statsPart4.totalUsers == 0}">
						N/A no users submitted concerns
					</c:when>
					<c:otherwise>
					
					<fmt:formatNumber type="percent">${statsPart4.males / statsPart4.totalUsers}</fmt:formatNumber> Male<br/>
					<fmt:formatNumber type="percent">${statsPart4.females / statsPart4.totalUsers}</fmt:formatNumber> Female
					</c:otherwise>
				</c:choose>
			</tr>
			<tr>
				<td><strong>County of residence:</strong></td>
				<td><c:choose>	
					<c:when test="${fn:length(statsPart4.counties) == 0}">
						<p>No Counties Available</p>
					</c:when>
					<c:otherwise>

					<c:forEach var="county" items="${statsPart1.counties}" varStatus="loop">
						${statsPart4.countyStats[county]/statsPart4.totalUsers} ${county.name},
					</c:forEach>
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
						<fmt:formatNumber type="percent">${statsPart4.transportStats[transport]/statsPart4.totalUsers}
						</fmt:formatNumber>
						 ${transport.value}<br>
</c:forEach>
					</c:otherwise>
				</c:choose></td>
			</tr>
						<tr>
				<td><strong>Yearly household income</strong></td>
				<td>
				<c:choose>	
					<c:when test="${fn:length(statsPart4.incomeRanges) == 0}">
						<p>No Transportation Types Available</p>
					</c:when>
					<c:otherwise>

					<c:forEach var="income" items="${statsPart4.incomeRanges}" varStatus="loop">
						<c:if test="${statsPart4.incomeStats[income] > 0}">
						<fmt:formatNumber type="percent">${statsPart4.incomeStats[income]/statsPart4.totalUsers}</fmt:formatNumber>
						: ${income.value}<br/>
						</c:if>
					</c:forEach>
					</c:otherwise>
				</c:choose>
				</td>
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

			<c:choose>
				
				<c:when test="${fn:length(voteSuite.stats) == 0}">
					<p>No Clustered Packages Available</p>
				</c:when>
				<c:otherwise>
				
				<c:set var="firstcp" value="true" />
 				<c:forEach var="stat" items="${voteSuite.stats}" varStatus="loop">
					 <c:if test="${firstcp == false}">
						<div class="VoteListRow row ">
					 </c:if>
					 <c:if test="${firstcp == true}">
						<div class="VoteListRow row highlight">
						<c:set var="firstcp" value="false" />
					 </c:if>
						<div class="voteCol1 floatLeft">
							<div class="floatLeft">${stat.clusteredPackage.description}</div>
						</div>
						<div class="voteCol2 floatLeft"><fmt:formatNumber type="percent">${stat.highVotes / stat.totalVotes}</fmt:formatNumber></div>
						<div class="voteCol3 floatLeft"><fmt:formatNumber type="percent">${stat.mediumVotes / stat.totalVotes}</fmt:formatNumber></div>
						<div class="voteCol4 floatLeft"><fmt:formatNumber type="percent">${stat.lowVotes / stat.totalVotes}</fmt:formatNumber></div>
						<div class="clearBoth"></div>
					</div>
				</c:forEach>
				</c:otherwise>
			</c:choose>
			
			
		<!-- end PACKAGE ENDORSEMENT VOTE RESULTS -->

		<div class="projSummary clearfix">
			<h3 class="headingColor padding5 clearfix">
				<span class="packageNum" style="background:#ADCFDE">${pp.description} (The winning/preferred package)</span>
				<span class="totalCost">Total cost: $<fmt:formatNumber type="number" maxFractionDigits="1" value="${pp.totalCost/1000000000}" /> 
				billion</span>
				<span class="yearlyCostToAvg">Yearly cost to the average resident: <fmt:formatNumber type="currency">${pp.avgResidentCost}</fmt:formatNumber>/year</span>
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
					<div class="clearBoth"></div>
				</div>

			 <c:forEach var="alt" items="${pp.projAltRefs}" varStatus="loop">

				  <div class="listRow row">
					  <h4 class="subHeading">${alt.projectRef.project.name}</h4>
					  <div class="clearBoth"></div>
				  </div>

				  <div class="listRow row">
					  <div class="projCol1 floatLeft">
							<div class="floatLeft">${alt.alternative.name}</div>	  
					  </div>
					  <div class="projCol2 floatLeft">
					  
					 <c:if test="${alt.alternative.cost > 999999 && alt.alternative.cost < 1000000000}">
					$<fmt:formatNumber type="number" maxFractionDigits="1">${alt.alternative.cost/1000000}</fmt:formatNumber> Million
					</c:if>
					<c:if test="${alt.alternative.cost > 999999999 && alt.alternative.cost < 1000000000000}">
					$<fmt:formatNumber type="number" maxFractionDigits="1">${alt.alternative.cost/1000000000}</fmt:formatNumber> Billion
					</c:if>
					  
					  </div>
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
							<h4>Projects included in this package</h4>
						</div>
					</div>
					<div class="fundingCol2 floatRight">
						<h4>Money raised</h4>
					</div>
					<div class="clearBoth"></div>
				</div>
				
				
				<c:forEach var="fundAlt" items="${pp.fundAltRefs}" varStatus="loop">
					<div class="listRow row">
						<h4 class="subHeading">${fundAlt.alternative.source.name}</h4>
						<div class="clearBoth"></div>
					</div>
					<div class="listRow row">
						<div class="fundingCol1 floatLeft">
							<div class="floatLeft">${fundAlt.alternative.name}</div>
						</div>
					  <div class="fundingCol2 floatRight">
					  
					  <c:if test="${fundAlt.alternative.revenue > 999999 && fundAlt.alternative.revenue < 1000000000}">
					$<fmt:formatNumber type="number" maxFractionDigits="1">${fundAlt.alternative.revenue/1000000}</fmt:formatNumber> Million
					</c:if>
					<c:if test="${fundAlt.alternative.revenue > 999999999 && fundAlt.alternative.revenue < 1000000000000}">
					$<fmt:formatNumber type="number" maxFractionDigits="1">${fundAlt.alternative.revenue/1000000000}</fmt:formatNumber> Billion
					</c:if>
					  
					  </div>
						<div class="clearBoth"></div>
					</div>
				</c:forEach>
				
				
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
		<h3 class="headingColor padding5 centerAlign"><a name="appendixA">Appendix A: About <em>Let's Improve Transportation</em></a></h3>
		<p>Residents of King, Pierce, and Snohomish counties were invited to participate in the 
<em>Let's Improve Transportation Challenge</em>, an experiment in online participatory democracy. The purpose of this experiment was to evaluate a new and potentially more meaningful way to involve citizens in the process of regional transportation improvement decision making.</p>
<p>Participants were asked to imagine they are a member of a large citizen advisory committee, charged with providing policy makers their recommendations regarding a regional transportation ballot measure. The measure would ask voters in the region if they wish to raise taxes to pay for a package of road and transit improvement projects. The participants' task was to determine which projects to build and which funding mechanisms (such as taxes or tolls) should be used to pay for them. The challenge for participants was to identify which package of projects and funding options they could collectively recommend. An innovative new website called Let's Improve Transportation was developed to support this collaborative process, which included five steps:</p>
<ol>
	<li>Brainstorm concerns</li>
	<li>Review improvement factors</li>
	<li>Create personal transportation package</li>
	<li>Select a recommended transportation package</li>
	<li>Create a final report</li>
</ol>
<p>No prior experience with transportation issues was necessary for participation. Qualified participants who completed the LIT Challenge received a small stipend for their efforts.</p>

<h3 class="headerColor">About the research effort behind Let's Improve Transportation</h3>

<p>The Participatory GIS for Transportation (PGIST) research study at the University of Washington<sub>(1)</sub> was founded to develop web and mapping technologies for supporting public participation in transportation improvement programming decisions. A demonstration website, www.LetsImproveTransportation.org employs state-of-the-art Web 2.0 technology which provides a simple web interface to support facilitators or agency planners in several stages of a transportation improvement programming process.</p>

<h3 class="headerColor">Project solicitation and evaluation</h3>
<ul>
	<li>Define "improvement factors", or criteria for project evaluation. 
	<li>Enter information about proposed transportation improvement projects into an online database. 
	<li>Input scores for individual proposed projects based on the defined improvement factors. 
	<li>Define available funding mechanisms (in the case of a ballot measure financing plan such as RTID).
</ul>

<h3 class="headerColor">Public review and participation</h3>
<ul>
	<li>Display project information, project scores, project location (footprints), and funding options on a website for public review. 
	<li>Support a structured public consultation process (see description above)
	<li>Support the automated clustering of individual transportation packages created by participants into a short list of representative "candidate" packages for public review and evaluation.
</ul>

<h3 class="headerColor">There were three main goals for the LIT Challenge experiment:</h3>
<ol>
	<li>To serve as a public demonstration of one way to enhance the involvement of citizens in transportation improvement programming decision processes. 
	<li>To allow PGIST researchers to evaluate the effectiveness of this experimental model of public involvement.
	<li>Allow agency collaborators to evaluate the capabilities of the web technology and provide feedback regarding its appropriateness to their transportation improvement programming and public involvement needs.
</ol>
<p>Researchers hope that web technology developed by PGIST can be refined, improved, and eventually put to use by transportation agencies around U.S. and the world.</p>

<h3 class="headerColor">Notes</h3>
<strong>1</strong> Research institutions associated with this study include University of Washington departments of Geography, Civil Engineering, and Information Science, San Diego State University department of Geography, and University of Wyoming department of Civil Engineering.

		
		
	</div>
	<!-- End Appendix A -->
	<!-- Begin Appendix B -->
	<div id="appendixB" class="box3 padding5 section"><a name="appendixB"></a>
		<h3 class="headingColor padding5 centerAlign">Appendix B: Project scoring methodology</h3>
		<p>All proposed transportation projects appearing on the LIT website have a letter grade for each improvement factor. The factors were not scored directly. Rather, a panel of six researcher team members with a diversity of perspectives in transportation planning assigned a numeric score to each objective associated with the improvement factors. The scores, ranging from negative three to positive three, correspond to a subjective judgment regarding the predicted impacts of the completed project on the region. Positive scores mean the project is predicted to have a positive impact on the region for the objective under consideration, while negative scores mean the predicted impact is negative.</p>

<p>For example, a score of two for the objective "impact on vehicle emissions and air pollution" means that the panel judged the project would have a moderate amount of beneficial impact on the region's vehicle emissions and air pollution. Conversely, a score of negative one for the objective "impact on travel demand" means the judges felt the project would have a minor negative impact on travel demand (or, in other words, a minor increase in regional travel demand).</p>

<p>A project's improvement factor grade is based on a numeric average of the scores assigned to each of the four objectives associated with that factor. Each objective score is the average of two individual judges' scores, one with expertise in transportation engineering and another with expertise in urban planning or urban geography. The judges assigned scores to each project over a three-week period. To evaluate the projects they used the same project information currently available to users of the LIT website.</p>

<p>It is important to note that the scores represent the <em>qualitative</em> judgments of research staff based on limited information. The transportation projects under consideration are at widely varying levels of completion. Some are nearly ready for construction, others have barely begun the planning and design process. In some cases a great deal of information is known about the projects, in others it is too early in their development to have many details. Therefore, the project grades are intended simply to begin a broader conversation among LIT participants regarding the potential merits and impacts of proposed projects.</p>

  </div>
	<!-- End Appendix B -->
	<!-- Begin Appendix C -->
	<div id="appendixC" class="box3 padding5 section"><a name="appendixC"></a>
		<h3 class="headingColor padding5 centerAlign">Appendix C: The candidate packages</h3>
		<p>After every participant submitted their package in step 3c, a statistical procedure was used to identify a small set of "candidate" packages that best represented the diversity of packages created by all participants.  The statistical procedure is called "cluster analysis".  In this procedure, all of the participant packages were separated into clusters (subgroups of packages) based on the package's project and funding selections.  Then, for each cluster, one representative package was identified as a candidate package. (Kaufman and Rousseeuw, 1990).</p>
		
			
			<c:forEach var="cPkg" items="${cp}" varStatus="loop">
			<div class="projSummary clearfix">
				<h3 class="headingColor padding5 clearfix">
					<span class="packageNum">${cPkg.description}</span>
					<span class="totalCost">Total cost:
							
							<c:if test="${cPkg.totalCost > 999999 && cPkg.totalCost < 1000000000}">
							$<fmt:formatNumber type="number" maxFractionDigits="1">${cPkg.totalCost/1000000}</fmt:formatNumber> illion
							</c:if>
							<c:if test="${cPkg.totalCost > 999999999 && cPkg.totalCost < 1000000000000}">
							$<fmt:formatNumber type="number" maxFractionDigits="1">${cPkg.totalCost/1000000000}</fmt:formatNumber> billion
							</c:if>
					
					</span>
					<span class="yearlyCostToAvg">Yearly cost to the average resident: <fmt:formatNumber type="currency">${cPkg.avgResidentCost}</fmt:formatNumber> per year</span>
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
							<div class="projCol2 floatLeft">

							<c:if test="${altRef.alternative.cost > 999999 && altRef.alternative.cost < 1000000000}">
							$<fmt:formatNumber type="number" maxFractionDigits="1">${altRef.alternative.cost/1000000}</fmt:formatNumber> million
							</c:if>
							<c:if test="${altRef.alternative.cost > 999999999 && altRef.alternative.cost < 1000000000000}">
							$<fmt:formatNumber type="number" maxFractionDigits="1">${altRef.alternative.cost/1000000000}</fmt:formatNumber> billion
							</c:if>
							
							</div>
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
							<div class="fundingCol2 floatRight">
							
							<c:if test="${altRef.alternative.revenue > 999999 && altRef.alternative.revenue < 1000000000}">
							$<fmt:formatNumber type="number" maxFractionDigits="1">${altRef.alternative.revenue/1000000}</fmt:formatNumber> Million
							</c:if>
							<c:if test="${altRef.alternative.revenue > 999999999 && altRef.alternative.revenue < 1000000000000}">
							$<fmt:formatNumber type="number" maxFractionDigits="1">${altRef.alternative.revenue/1000000000}</fmt:formatNumber> Billion
							</c:if>
							
							</div>
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