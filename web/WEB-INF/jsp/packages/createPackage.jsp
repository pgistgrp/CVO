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
		[ ] Integrate Layout (Issac)
		[ ] JavaScript for Calculations (Issac)

#### -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8">
		<title>Create your own Package!</title>
		<style type="text/css" media="screen">
			@import "styles/lit.css";
		</style>
		<script type='text/javascript' src='/dwr/interface/PackageAgent.js'></script>		
		<script type="text/javascript" charset="utf-8">
			//Global Vars
			
			//End Global Vars
			
			function setFundingToPkg(id, deleting){
				//alert("id: " + id + " deleting: " + deleting); 
				PackageAgent.setProjectToPkg({id:id,deleting:deleting}, {
					callback:function(data){
						if (data.successful){
							alert("Project" + id + " was successfully set to " + deleting); //replace with saving indicator later
						}else{
							alert(data.reason);
						}
					},
					errorHandler:function(errorString, exception){ 
					alert("PackageAgent.setProjectToPkg( error:" + errorString + exception);
					}
				});
			}
			
			function setProjectToPkg(id, deleting){
				//alert("id: " + id + " deleting: " + deleting); 
				PackageAgent.setFundingToPkg({id:id,deleting:deleting}, {
					callback:function(data){
						if (data.successful){
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
		</script>
	</head>
	<body>
		<h1>Overview and Instructions</h1>
		<p>This is the overview blah blah blah...</p>
		
		<FORM action="packageAction.do" method="post">	
			<div id="createPackage">
				<div id="summary">
					<h3>Your package summary</h3>
					<p>Total Cost: </p>
					<p>Total Funding: </p>
					<p>Cost to you: </p>
					<p>Cost to the average resident: </p>
					<p>Number of projects in your package: </p>
					<p>Balance: </p>
				</div>
				<div id="projects">
					<h3>Create your Transportation Package</h3>
					<ul>
						<c:forEach var="project" items="${projects}" varStatus="loop">
							<li>	
								<label><input name="proj-${project.id}" type="radio" CHECKED /> Do Nothing</label>
								<p>Name: ${project.name}</p>
								<c:forEach var="alternative" items="${project.alternatives}" varStatus="loop">
									<input type="radio" name="proj-${project.id}" 
									<c:if test="${pg:contains(alternative, package.projAlts)}">
										CHECKED
									</c:if>
									<!-- end input -->
									Name: ${alternative.name}
								</c:forEach>
							</li>
						</c:forEach>
					</ul>
				</div>
				
				<div id="map">
					<!-- load the map here gMan! -->
				</div>
				
				<div id="funding">
					<h3>Decide how to pay for it</h3>
					<!-- begin list of funding options -->
					<div id="sdf-allListHeader headingColor">
						<div class="listHeaderHeader headingColor">
							<div class="sdf-col1 floatLeft"> <span class="sdf-listHeaderTitles">Funding
									Source</span> </div>
							<div class="sdf-col2 floatLeft"> <span class="sdf-listHeaderTitles">Money
									raised</span> </div>
							<div class="sdf-col3 floatLeft"> <span class="sdf-listHeaderTitles">Annual
									cost to you</span> </div>
							<div class="sdf-col4 floatLeft"> <span class="sdf-listHeaderTitles">Annual
									cost to average resident</span> </div>
							<div class="clearBoth"></div>
						</div>

						<c:forEach var="source" items="${sources}" varStatus="loop">
							<div id="source-${source.id}">
								<h4 class="headerColor">${source.name}</h4>
								<div class="sdf-listRow row">
									<c:forEach var="alternative" items="${source.alternatives}" varStatus="loop">
										<div class="sdf-col1 floatLeft">
											<div class="floatLeft">
												<label><input name="proj-${project.id}" type="radio" CHECKED /> Do Nothing</label>
												<input type="radio" name="fund-${alternative.id}" onchange="setFundingToPkg('${alternative.id}', this.checked)"
													<c:if test="${pg:contains(alternative, package.fundAlts)}">
														checked="checked"
													</c:if>
												/> <!-- end input -->
												${alternative.name}
											</div>
										</div>
										<div class="sdf-col2 floatLeft smallText">${alternative.revenue}</div>
										<div class="sdf-col3 floatLeft smallText">${alternative.userCost}</div>
										<div class="sdf-col4 floatLeft smallText">${alternative.averageCost}</div>
										<div class="clearBoth"></div>
									</c:forEach>
								</div>
							</div>
						</c:forEach>
					</div>
					<!-- end list of funding options -->
					<h3>Finished?</h3>
				</div>
				<div id="summaryRepeat">
					<!-- repeat from above -->
				</div>
				<input type="button" value="Yes - Submit My Package!"> <!-- this should only be enabled if funding exceeds cost -->
				<input type="cancel" value="No - Start Over!">
			</div>
		

		</FORM>
	</body>
</html>
