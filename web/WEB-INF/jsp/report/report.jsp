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
		Concern Summaries: ***${concernSummary}***
		Criteria Summaries: ***${criteriaSummary}***
		Executive Summaries: ***${executiveSummary}***
		Package Summaries: ***${packageSummary}***
		Participants Summary: ***${participantsSummary}***
		Project Summary: ***${projectSummary}***
		finalized: ***${finalized}***
	

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
		<p>${statsES.totalUsers} residents of King, Pierce, and Snohomish county worked together online over
			the course of 5 weeks to learn about transportation problems in our region, discuss
			their own concerns, and create a package of transportation projects and funding
			sources to address our transportation needs. On ${finalReportDate} they released
			the results of their efforts.</p>
		<p>The package contains ${statsES.totalProjects} road and transit projects across the 3 county region.
			It is funded by a combination of bridge tolls, parking taxes, and vehicle excise
			fees. The total cost of the package is $${statsES.totalCost} billion. The package was endorsed by
			<c:set var="totalVotes" value="${statsES.totalVotes}"/>
			<c:set var="percentyes" value="${statsES.numEndorsed/totalVotes*100}"/>
			<fmt:formatNumber maxFractionDigits="0" value="${percentyes}"/>
			% of the participants (${statsES.numEndorsed} our of ${statsES.totalVotes} participating).</p>
		<p>${executiveSummary}</p>
		<div class="floatLeft" style="margin:0em 2em"><strong>This report includes 4 sections:</strong>
			<ol>
				<li><a href="#participants">The participants and their concerns about transportation</a></li>
				<li><a href="#planningFactors">ìPlanning factorsî used in project evaluation</a></li>
				<li><a href="#projects">Project selection and personal package creation</a></li>
				<li><a href="#packages">Evaluation of packages</a></li>
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
		<p>298 residents of King, Pierce, and Snohomish counties participated in the Let's
			Improve Transportation challenge. Here is some more information about the participants:</p>
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr class="odd">
				<td><strong>Gender:</strong></td>
				<td>${concernStats.males}% male, ${concernStats.females}% female</td>
			</tr>
			<tr>
				<td><strong>County of residence:</strong></td>
				<td>
				<!-- display counties -->
				<c:choose>	
					<c:when test="${fn:length(concernStats.counties) == 0}">
						<p>No Counties Available</p>
					</c:when>
					<c:otherwise>

					<c:forEach var="county" items="${concernStats.counties}" varStatus="loop">
						${concernStats.countyStats[county].name} ${county.name},
					</c:forEach>
					</c:otherwise>
				</c:choose>
	
		</td>
			</tr>
			<tr class="odd">
				<td><strong>Primary mode of transportation (daily commute):</strong></td>
				<td>23% drive alone, 29% bus or transit, 12% carpool or vanpool, 7% walk or bike,
					3% work at home or are retired</td>
			</tr> 
			<tr>
				<td><strong>Yearly household income</strong></td>
				<td>22% 150k or more, 15% 100K-150K, 29% $75K-$100K, 11% 50K-75K, 19% $25K-$50K</td>
			</tr>
			<tr class="odd">
				<td><strong>Age</strong></td>
				<td>15% 18-25 years old, 24% 26-35 years old, 33% 35-45 years old, 28% 46-55
					years old, 13% 55 years or older</td>
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
		<p>The second step included a review, discussion, and weighting of "planning factors" used
			to evaluate the proposed transportation projects. These planning factors were
			adapted from .... They were used by a panel of transportation specialists to qualitatively "score" proposed
			projects. <a href="#appendixA">Appendix A</a> includes a detailed discussion of
			the scoring process. These scores, in turn, were used by participants in their
			effort to evaluate the benefits or drawbacks of the proposed transportation projects
			(see <a href="#projects">following section</a> for details).</p>
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
		<p>The third step began with a review and discussion of a list of 44 proposed transportation
			improvement projects. This was followed by a review and discussion of available
			funding options. Finally, participants were each given the opportunity to create
			their own personal transportation package including both a set of projects and
			a set of revenue sources which provide enough money to pay for the projects. We
			tabulated the results of participant project and funding source selection below.
			Note: only 212 of the total 298 participants completed this step. The percentages
			below refer to the percent of participants who completed this step.</p>
			
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
		<p>Participants created 212 unique packages. In order to narrow down the field
			of packages under consideration the system automatically created five new packages
			that collectively represent the diversity of packages created by participants
			in Step 3 (this process is described in Appendix B). Participants reviewed these
			five packages as well as the ìRTIDî package that will appear on the ballot on
			November 8, 2007.</p>
		<p>${part4a}</p>
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr class="odd">
				<td><strong>Gender:</strong></td>
				<td>55% male, 45% female</td>
			</tr>
			<tr>
				<td><strong>County of residence:</strong></td>
				<td>44% King, 23% Pierce, 23% Snohomish</td>
			</tr>
			<tr class="odd">
				<td><strong>Primary mode of transportation (daily commute):</strong></td>
				<td>23% drive alone, 29% bus or transit, 12% carpool or vanpool, 7% walk or bike,
					3% work at home or are retired</td>
			</tr>
			<tr>
				<td><strong>Yearly household income</strong></td>
				<td>22% 150k or more, 15% 100K-150K, 29% $75K-$100K, 11% 50K-75K, 19% $25K-$50K</td>
			</tr>
			<tr class="odd">
				<td><strong>Age</strong></td>
				<td>15% 18-25 years old, 24% 26-35 years old, 33% 35-45 years old, 28% 46-55
					years old, 13% 55 years or older</td>
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