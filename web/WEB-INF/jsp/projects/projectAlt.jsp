<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!--####
	Project: Let's Improve Transportation!
	Page: Project Alternative Description
	Description: This page serves as an information page for a project alternative. 
	Author: Jordan Isip, Adam Hindman, Issac Yang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] Look into map for planning factor grades (Jordan)
#### -->

<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
	<head>
	<title>Let's Improve Transportation: ${reference.alternative.name}</title>
	<!-- Site Wide CSS -->
<style type="text/css" media="screen">
@import "styles/lit.css";
@import "styles/table.css";
@import "styles/step3a-singleproject.css";
</style>
	<!-- Site Wide JS -->
	<script src="scripts/prototype.js" type="text/javascript"></script>
	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<script src="scripts/search.js" type="text/javascript"></script>
	<script src="scripts/qTip.js" type="text/javascript"></script>
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/util.js'></script>
	<script src="scripts/prototype.js" type="text/javascript"></script>
	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<script type="text/javascript">

// Loop through the 'objectives' divs, show them, and change the icon.
			function expandAll(){
				var rows = document.getElementsByClassName('objectives');
				var icons = document.getElementsByClassName('icon');
				for (var i = 1;i <= rows.length; i++){
					var row = 'objective' + i;
					var icon = 'icon' + i;
					$(row).show();
					$(icon).src = "/images/minus.gif";
				}
				showLabels();
			}

// Loop through all the 'objectives' divs, hide them, and change the icon.
			function collapseAll(){
				var rows = document.getElementsByClassName('objectives');
				for (var i = 1;i <= rows.length; i++){
					var row = 'objective' + i;
					var icon = 'icon' + i;
					$(row).hide();
					$(icon).src = "/images/plus.gif";
				}
				hideLabels();
			}

// Loop through all the column labels and make then visible.
			function showLabels(){
				var labels = document.getElementsByClassName('hiddenLabel')
				for (var i = 0;i < labels.length;i++){
					labels[i].style.visibility = "";
				}
			}

// Loop through all the column labels and set them invisible.
			function hideLabels(){
				var labels = document.getElementsByClassName('hiddenLabel')
				for (var i = 0;i < labels.length;i++){
					labels[i].style.visibility = "hidden";
				}
			}

// First, hide the labels. Then, for every objective that is open, show the labels.
			function testOpenRows(){
				hideLabels();
				var rows = document.getElementsByClassName('objectives');
				for (var i = 1;i <= rows.length; i++){
					var row = 'objective' + i;
					if ($(row).style.display != "none"){
						showLabels();
					}else{}
				}
			}
	
/* First, use Scriptaculous to toggle the objectives div.
Then, decide whether to show the plus or minus icon based on whether 
the div is displayed or not.  If the div is displayed, reveal
the column labels. */

			function toggleRow(project,icon){
				Effect.toggle(project, 'appear', {duration:.4, afterFinish:
					function(){
						if ($(project).style.display != ""){
							$(icon).src = "/images/plus.gif";
							testOpenRows();
							}else{
								$(icon).src = "/images/minus.gif";
								showLabels();
							}
						}
				});
			}
	</script>
	</head>
	<body>
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
	<!-- begin Object -->
	<div id="object">
		<h3 class="headerColor" id="project-title">${reference.alternative.name}</h3>
		<!-- begin cell containing Google Map object -->
		<div id="obj-right" class="floatRight"> <img src="/images/gmaps.gif"> </div>
		<!-- end cell containing Google Map object -->
		<!--begin project description -->
		<p>
		<h4 style="display:inline">Money needed to complete this project: </h4>
		<span id="project-moneyNeeded">${reference.alternative.cost}</span>
		</p>
		<p>
		<h4 style="display:inline">Sponsoring Agency: </h4>
		<span id="project-sponsoringAgency">${reference.alternative.sponsor}</span>
		</p>
		<h4 style="display:inline">County: </h4>
		<span id="project-county">${reference.alternative.county}</span>
		</p>
		
		<p>
		<h4>Short Description</h4>
		<span id="project-shortDescription">${reference.alternative.shortDesc}</span>
		</p>
		<p>
		<h4>Detailed Description</h4>
		<span id="project-detailedDescription">
			<pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">
				${reference.alternative.detailedDesc}
			</pg:termHighlight>
		</span>
		<p>
		<h4>Links to additional information about this project</h4>
		<span id="project-links">
			${reference.alternative.links}
		</span>
		</p>
		<p>
		<h4>Statement for</h4>
		<span id="project-statementFor">${reference.alternative.statementFor}</span>
		</p>
		<p>
		<h4>Statement against</h4>
		<span id="project-statementAgainst">${reference.alternative.statementAgainst}</span>
		</p>
		<!-- end project description -->
	</div>
	<!-- end obj-left -->
	<!-- begin firefox height hack -->
	<div class="clearBoth"></div>
	<!-- end firefox height hack -->
	<!-- Load separate file content starting here -->
	<!-- begin criteria  -->
			<a href="javascript:expandAll();">Expand all</a>
		<a href="javascript:collapseAll();">Collapse all</a>
			<div id="newtable">
				<table border=0 cellpadding=0 cellspacing=0>
					<tr class="tableHeading">
						<th class="first">Planning Factor</th>
						<th><span class="hiddenLabel" 
						style="visibility:hidden;">Specialist Panel Assessment</span></th>
						<th>Planning Factor Grade</th>
					</tr>
					<!--======================-->
					<c:forEach var="criterion" items="${criteria}" varStatus="loop">
						<!-- begin PROJECT -->
						<tr class="fundingType">
							<td class="fundingSourceItem">
									<a href="javascript:toggleRow('objective1','icon1');">
									<img src="/images/plus.gif" id="icon1" class="icon"></a>
									<a href="javascript:toggleRow('objective1','icon1');" title="${criterion.description}">${criterion.name}</a></td>
							<td>&nbsp;</td>
							<td class="gradeA">${criterion.grade}</td>
						</tr>
						<!-- end PROJECT -->
					
						<!-- begin HIDDEN ROW of OPTIONS -->
						<tr style="display:none;" class="objectives" id="objective1">
							<td colspan="3">
								<ul>
									<p><b>Objectives (${fn:length(criterion.objectives)}):</b></p>	
								
									<c:forEach var="objective" items="${criterion.objectives}" varStatus="loop">
										<li>Promotes General economic development
											<span>major positive impact</span>
										</li>
									</c:forEach>
								</ul>
							</td>
						</tr>
						<!-- end HIDDEN ROW -->
					</c:forEach>
					<!--======================-->
					<!-- begin AVERAGES -->
					<tr class="headingColor">
						<td class="fundingSourceItem">
								Average</td>
						<td>&nbsp;</td>
						<td class="gradeDMinus">D-</td>
					</tr>

					<tr class="headingColor">
						<td class="fundingSourceItem">
								Average weighted grade (based on your preferred factor weights)</td>
						<td>&nbsp;</td>
						<td class="gradeCPlus">C+</td>
					</tr>

					<tr class="headingColor">
						<td class="fundingSourceItem">
								Average weighted grade (based on all participants' planning factor weights)</td>
						<td>&nbsp;</td>
						<td class="gradeA">A</td>
					</tr>
					<!-- end AVERAGES -->


				</table>
			</div>
	</div>
	<!-- end container -->
	<!-- start feedback form -->
	<pg:feedback id="feedbackDiv" action="cctView.do"/>
	<!-- end feedback form -->
	</body>
</html:html>
