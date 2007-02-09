<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!--####
	Project: Let's Improve Transportation!
	Page: Create My Transportation Package
	Description: This page serves as a form for a user to create her/her own transportation package. 
	Author: Jordan Isip, Adam Hindman, Issac Yang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] Integrate Layout (Adam)
		[ ] Test and Refine (Jordan)
#### -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<title>Create your own package!</title>
<style type="text/css" media="screen">
@import "styles/lit.css";
</style>

<style type="text/css">

.box3{padding:5px}

#balanceRow, #balanceRow2, #balance2, #balance {padding:.5em;margin:.5em 0em;}

.positive{background-color:#B6EEB6;}
.negative{background-color:#FCAEAE;}

#projects
{
width:45%;
margin:.5em .5em .5em 0em;
}

#funding
{
width:52%;
margin:.5em 0em .5em .5em;
}

.odd {background: #D6E7EF}
.even {background: #ffffff}


.listRow
{
padding:.3em 0em;
}

#allListHeader
{
text-align:left;
height:2em;
}

#projects .col1
{
width:250px;
margin-right:.5em;
padding-left:.5em;
}

#projects .col2
{
width:80px;
}

#projects .col3
{
margin-left:.5em;
width:70px;
text-align:center;
}		

#funding .col1
{
width:200px;
margin-right:.5em;
padding-left:.5em;
}

#funding .col2
{
width:80px;
}

#funding .col3
{
margin-left:.5em;
width:100px;
}	

#funding .col4
{
margin-left:.5em;
width:80px;
}	

.packageCol1
{
width:160px;
font-weight:bold;
}

.packageCol2
{
width:250px;
}

.packageCol3{
margin-left:1em;
width:400px;
font-weight:bold;
}

.packageCol4{
width:100px;
}

h4
{
font-size:1em;
clear:both;
}

.listHeaderTitles
{
font-size:.8em;
font-weight:bold;
}

#summary{width:auto;}

</style>
<script type='text/javascript' src='/dwr/interface/PackageAgent.js'></script>
<script type="text/javascript" charset="utf-8">
			//Global Vars
			var pkgId = "${package.id}";
			//End Global Vars
			
			function setFundingToPkg(altId,deleting){
				//alert("id: " + id + " deleting: " + deleting); 
				PackageAgent.setFundingtoPkg({pkgId:pkgId,altId,altId,deleting:deleting}, {
					callback:function(data){
						if (data.successful){
							alert("Project" + id + " was successfully set to " + deleting); //replace with saving indicator later
							updateSummary();
						}else{
							alert(data.reason);
						}
					},
					errorHandler:function(errorString, exception){ 
					alert("PackageAgent.setProjectToPkg( error:" + errorString + exception);
					}
				});
			}
			
			function setProjectToPkg(altId,deleting){
				//alert("pkgId: " + pkgId + " altId: "+ altId +" deleting: " + deleting); 
				PackageAgent.setFundingToPkg({pkgId:pkgId,altId,altId,deleting:deleting}, {
					callback:function(data){
						if (data.successful){
							updateSummary();
							alert("Funding" + id + " was successfully set to " + deleting); //replace with saving indicator later
						}else{
							alert(data.reason);
						}
					},
					errorHandler:function(errorString, exception){ 
					alert("PackageAgent.setFundingToPkg( error:" + errorString + exception);
					}
				});
			}
			
			function updateSummary(){
				var balance = $('balance').innerHTML;
				balance = parseInt(balance);
				$('summary').innerHTML = data.html;
				$('summaryRepeat').innerHTML = data.html;
				if(balance < 0){
					$('submitPackage').disable(); //disable submit button
				}
			}
		</script>

</head>
<body>
<!-- Begin header -->
<div id="header">
	<jsp:include page="/header.jsp" />
</div>
<!-- End header -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu">
	<div id="headerContainer">
		<div id="headerTitle" class="floatLeft">
			<h3 class="headerColor">Step 3: Create Packages</h3>
		</div>
		<div class="headerButton floatLeft"> <a href="step3a.html">3a: Review projects</a> </div>
		<div class="headerButton floatLeft"> <a href="step3b.html">3b: Review funding options</a> </div>
		<div class="headerButton floatLeft currentBox"> <a href="step3c.html">3c: Create
				your own package</a> </div>
		<div id="headerNext" class="floatRight box5"> <a href="step3c.html">Next Step</a> </div>
	</div>
</div>
<!-- End header menu -->
<!-- #container is the container that wraps around all the main page content -->
<div id="container">
	<!-- begin "overview and instructions" area -->
	<div id="overview" class="box2">
		<h3>Overview and Instructions</h3>
		<p>How are we going to pay for improvements to the regional transportation system?
			Some of the money for improvements comes from state and federal government. However,
			all of the projects you reviewed in Step 3A are not yet fully funded by these
			sources, and many are not funded at all. The purpose of the regional transportation
			ballot measure is to fund some of these projects with new regional tax increases.</p>
		<p>Below you can review and discuss five different kinds of regional tax increases
			that can be used to pay for transportation improvements.</p>
	</div>
	<!-- end overview -->
	<FORM action="packageAction.do" method="post">
		<div id="createPackage">
			<!-- begin summary -->
			<div id="summary" class="box3">
			<!-- load summary via DWR -->
			</div>
			<!-- end summary -->
			
			<!--Project list CONTAINER -->
			<div class="floatLeft" id="projects">
				<h3 class="headerColor">Create your personal transportation package</h3>
				<div id="allListHeader">
					<!--begin HEADER of Project list-->
					<div class="listHeaderHeader box4">
						<div class="col1 floatLeft"> <span class="listHeaderTitles">Project</span> </div>
						<div class="col2 floatLeft"> <span class="listHeaderTitles">Money Needed</span> </div>
						<div class="col3 floatLeft"> <span class="listHeaderTitles">County</span> </div>
					</div>
				</div>
				<!--end HEADER of Project list-->
				<!-- begin all PROJECTS -->
				<div>
					<!--begin PROJECT LOOP-->
					<c:forEach var="project" items="${projects}" varStatus="loop">
						<h4 class="headerColor">${project.name}</h4>
						<c:choose>
							<c:when test="${project.inclusive}"><!-- radio buttons -->
								<div class="listRow">
									<div class="col1 floatLeft">
										<label><input name="proj-${project.id}" type="radio" CHECKED /> Do Nothing</label>
									</div>
									<div class="clearBoth"></div>
								</div>
								<c:forEach var="alternative" items="${project.alternatives}" varStatus="loop">
									<div class="listRow">
										<div class="col1 floatLeft">
										<label><input type="radio" name="proj-${project.id}" onchange="setProjectToPkg('${alternative.id}', this.checked)"
													<c:if test="${pg:contains(alternative, package.projAlts)}">
														checked = "checked"
													</c:if>/>$ {alternative.name}
										</label>
										</div>
										<div class="col2 floatLeft">${alternative.value}</div>
										<div class="col3 floatLeft">${alternative.county}</div>
										<div class="clearBoth"></div>
									</div>
								</c:foreach>
							</c:when>
							<c:otherwise><!-- checkboxes -->
								<c:forEach var="alternative" items="${project.alternatives}" varStatus="loop">
									<div class="listRow row">
										<div class="col1 floatLeft">
										<label><input type="checkbox" name="proj-${project.id}" onchange="setProjectToPkg('${alternative.id}', this.checked)"
													<c:if test="${pg:contains(alternative, package.projAlts)}">
														checked = "checked"
													</c:if>
												/><!-- end input --> ${alternative.name}
										</label>
										</div>
										<div class="col2 floatLeft">${alternative.value}</div>
										<div class="col3 floatLeft">${alternative.county}</div>
										<div class="clearBoth"></div>
									</div>
								</c:foreach>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<!-- end PROJECT LOOP -->
				</div>
				<!-- end all PROJECTS -->
			</div>
			<!-- End of Project list CONTAINER-->
			
			
			<div class="floatLeft" id="funding">
				<div id="map">
					<!-- load the map here gMan! -->
				</div>
				<h3 class="headerColor">Decide how to pay for it</h3>
				<div id="allListHeader">
					<!--Funding list-->
					<div class="listHeaderHeader box4">
						<div class="col1 floatLeft"> <span class="listHeaderTitles">Funding Source</span> </div>
						<div class="col2 floatLeft"> <span class="listHeaderTitles">Money raised</span> </div>
						<div class="col3 floatLeft"> <span class="listHeaderTitles">Annual cost to
								you</span> </div>
						<div class="col4 floatLeft"> <span class="listHeaderTitles">Annual cost to
								avg. resident</span> </div>
					</div>
				</div>
				
				<div>
				<!-- begin FUNDING LOOP -->
					<c:forEach var="source" items="${sources}" varStatus="loop">
						<h4 id="source-${source.id}" class="headerColor">${source.name}</h4>
						<div class="listRow">
							<div class="col1 floatLeft">
								<label><input name="proj-${project.id}" type="radio" CHECKED /> Do Nothing</label>
							</div>
							<div class="clearBoth"></div>
						</div>						
						<c:forEach var="alternative" items="${source.alternatives}" varStatus="loop">
							<div class="listRow row">
								<div class="col1 floatLeft">
									<label><input type="radio" name="fund-${alternative.id}" onchange="setFundingToPkg('${alternative.id}', this.checked)"
										<c:if test="${pg:contains(alternative, package.fundAlts)}">
											checked="checked"
										</c:if>
									/><!-- end input --> ${alternative.name}	
									</label>							
								</div>
								<div class="col2 floatLeft">${alternative.revenue}</div>
								<div class="col3 floatLeft">${alternative.userCost}</div>
								<div class="col4 floatLeft">${alternative.averageCost}</div>
								<div class="clearBoth"></div>
							</div>
						</c:forEach>
					</c:forEach>
				<!-- end FUNDING LOOP -->
				</div>
				<!-- end ALL FUNDING -->
			</div>
			<a href="#packSummary">
			<h3 class="centerAlign">Finished?</h3>
			</a> </div>
		<!--End of Funding list-->
		<!-- Finished Link-->
		<div class="clearBoth"></div>
		<div id="object">
			<div class="box3" class="padding5" id="summaryRepeat">
				<!-- load summary via DWR -->
			</div>
		<div class="padding5">
			<!--Finished submit buttons-->
			<h3 class="centerAlign">Finished?</h3>
			<p class="centerAlign">
				<input type="button" id="submitPackage" value="Yes - Submit My Package!"> 
				<!-- this should only be enabled if funding exceeds cost -->
				<input type="cancel" value="No - Start Over!">
			</p>
			<div class="clearBoth"></div>
		</div>
	</form>
</div>
</body>
</html>