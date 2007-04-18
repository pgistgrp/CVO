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
	Author: Jordan Isip, Adam Hindman
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] Integrate Adam's Layout (Jordan)
		[x] setFundingtoPkg and setProjecttoPkg (Jordan and Matt)
		[x] SuiteIds (Jordan and Matt)
		[x] Pull Summary Partials (Jordan and Matt)
		[ ] Balance color (Jordan)
		[ ] Clear selection prior to setting (Jordan)
		[ ] pg:contains for initialization(Jordan)
		[ ] What happends when user clicks on "finished"? (Jordan)
		[ ] Ordering (Matt)
#### -->
<html>
<head>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<title>Create your own package!</title>
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->
<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/interface/PackageAgent.js'></script>
<!-- mapping JavaScript -->
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAADmWGO07Q7ZeAHCvFNooqIxTwM0brOpm-All5BF6PoaKBxRWWERTgXzfGnh96tes2zXXrBXrWwWigIQ"
      type="text/javascript"></script>
<script src="scripts/pgistmap2.js"></script>
<!-- End of mapping JavaScript -->
<style type="text/css">
	@import "styles/lit.css";
	@import "styles/table.css";
	@import "styles/step3c.css";	
</style>
<script type="text/javascript" charset="utf-8">
			//Global Vars
			var pkgId = "${userPkg.id}";

			
			//End Global Vars

			function setFundingToUserPkg(altRefId,checked){
				//alert("checked = " +checked)
				//alert("pkgSuiteId: " + pkgId + "altRefId: " + altRefId + " deleting: " + deleting); 
				var deleting = !Boolean(checked); //dealing with checkboxes
				//alert("deleting = " + deleting)
				PackageAgent.setFundingToUserPkg({pkgId:pkgId,altId:altRefId,deleting:deleting}, {
					callback:function(data){
						if (data.successful){
							//alert("Funding alt " + altRefId + " was successfully set to " + deleting); //replace with saving indicator later
							updateSummary(data);
						}else{
							alert(data.reason);
						}
					},
					errorHandler:function(errorString, exception){ 
					alert("PackageAgent.setFundingToUserPkg( error:" + errorString + exception);
					}
				});
			}
			
			function clearSelectionThenDefine(groupId,type) {
				alts = document.getElementsByName(type + "-"+ groupId)
				for (var i=0; i < alts.length; i++) {
					start = alts[i].id.indexOf('-') + 1;
					stop = alts[i].id.length;
					id = alts[i].id.substring(start,stop);
					
					if(id != ""){
						if(type=="project"){
							//alert("setting project "+id +" to false...");
							setProjectToUserPkg(id, false);
							if(alts[i].checked == true){
								//alert("adding" +type+id)
								setProjectToUserPkg(id, true);
							}
						}else{ //source
							//alert("setting funding to user package...");
							setFundingToUserPkg(id, false);
							if(alts[i].checked == true){
								//alert(type+id)
								setFundingToUserPkg(id, true);
							}
						}
					}
					
				};
			}
			
			function setProjectToUserPkg(altRefId,checked){
				//alert("checked = " + checked)
				var deleting = !Boolean(checked);
				//alert(deleting)
				//alert("pkgId: " + pkgId + " altRefId: "+ altRefId +" deleting: " + deleting); 
				PackageAgent.setProjectToUserPkg({pkgId:pkgId,altId:altRefId,deleting:deleting}, {
					callback:function(data){
						if (data.successful){
				//			alert("Project alt " + altId + " was successfully set to " + deleting); //replace with saving indicator later
							updateSummary(data);
						}else{
							alert(data.reason);
						}
					},
					errorHandler:function(errorString, exception){ 
					alert("PackageAgent.setProjectToUserPkg( error:" + errorString + exception);
					}
				});
			}
			
			function getSummary(){
				//alert("pkgId: " + pkgId); 
				PackageAgent.getSummary({pkgId:pkgId}, {
					callback:function(data){
						if (data.successful){
							updateSummary(data);
						}else{
							alert(data.reason);
						}
					},
					errorHandler:function(errorString, exception){ 
					alert("PackageAgent.getSummary( error:" + errorString + exception);
					}
				});
			}
			
			function updateSummary(data){
				//Render Summaries
				$('yourSummary').innerHTML = data.html;
				$('yourSummaryRepeat').innerHTML = data.html;

				if($('yourSummary').style.backgroundColor == "transparent"){
					new Effect.Highlight('yourSummary',{duration:0.3});
					new Effect.Highlight('yourSummaryRepeat',{duration:0.3});
				}
				

				//Check balance - if negative balance then disable submit - maybe do this via JSP?
				/*var balance = $('balance').innerHTML;
				balance = parseInt(balance);
				if(balance < 0){
					$('submitPackage').disable(); //disable submit button
				}*/
			}
			
			function createMyPackage(){
				var limit= $F('avgPersonLimit')
				
				//alert("usrPkgId: " + usrPkgId + " limit: " + limit); 
				PackageAgent.createMyPackage({usrPkgId:pkgId,limit:limit}, {
					callback:function(data){
						if (data.successful){
							alert("it worked");
							updateSummary(data);
						}else{
							alert(data.reason);
						}
					},
					errorHandler:function(errorString, exception){ 
					alert("PackageAgent.createMyPackage( error:" + errorString + exception);
					}
				});
			}
			
			
			/* *************** START MAPPING FUNCTIONS *************** */
			
			
			/* *************** END MAPPING FUNCTIONS *************** */
		</script>
</head>
<body>
<div id="header">
	<!-- Begin header -->
	<jsp:include page="/header.jsp" />
	<!-- End header -->
</div>
<!-- End header -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu">
	<div id="headerContainer">
		<div id="headerTitle" class="floatLeft">
			<h3 class="headerColor">Step 3: Create Packages</h3>
		</div>
		<div class="headerButton floatLeft "> <a href="step3a.html">3a: Review projects</a> </div>
		<div class="headerButton floatLeft "> <a href="step3b.html">3b: Review funding
				options</a> </div>
		<div class="headerButton floatLeft currentBox "> <a href="step3c.html">3c: Create
				your own package</a> </div>
		<div id="headerNext" class="floatRight box5"> <a href="step3b.html">Next Step</a> </div>
	</div>
</div>
<!-- End header menu -->
<!-- #container is the container that wraps around all the main page content -->
<div id="container">
	<!-- begin "overview and instructions" area -->
	<div id="overview" class="box2">
		<h3>Overview and Instructions</h3>
		<p>Criteria are used to help Evaluate which proposed transportation projects are
			best suited to address problems with our transportation system. Below, these criteria
			have been associated with the concern themes discussed in the previous step. Please
			review these criteria and the associated themes. Do these criteria adequately
			reflect your concerns and the summaries? What criteria might be useful in evaluating
			proposed transportation projects?</p>
		<p><a href="readmore.jsp">Read more about how this step fits into the bigger picture</a>.</p>
	</div>
	<!-- end overview -->
	<!-- begin Object -->
	<div id="object">
		<!-- begin NewTable-->
		<div id="newTable">
			<div id="left" class="floatLeft">
				<!-- begin TOP SUMMARY -->
				<div id="yourSummary" class="summary">
					<!-- summary goes here -->
				</div>
				<!-- end TOP SUMMARY -->
				<div class="clearBoth"></div>
				<br />
				<h3>Select Projects to Include in your Package</h3>
				<input type="button" class="helpMeButton" onClick="new Effect.toggle('helpMe', 'blind', {duration:0.3})" value="Help me create a package" />
				<div id="helpMe" style="display:none;">
					<p>Using the information you provided during registration, we can put together
						a package for you automatically. Any projects and funding sources you've already
						selected will be included in this package.</p>
					<form action="javascript:createMyPackage();">
					<h4>Cost per year</h4>
						<div class="floatLeft" style="width:60%">
							<label> What's the most the average person should be expected to pay to fund this package? </label>
						</div>
						<div class="floatRight"> <span style="font-size:1.3em;">$
							<input type="text" size="3" id="avgPersonLimit" /></span>
						</div>
						<div class="clearBoth"></div>
						<div style="margin-top:15px;" class="floatLeft">
						<input type="submit" class="floatLeft padding5" value="Create a package"/>
						<div class="floatRight"><a href="javascript:window.open('tuner.do?usrPkgId=${userPkg.id}&projSuiteId=${projSuiteId}&fundSuiteId=${fundSuiteId}','helpMe','width=1000,height=500,resizable=yes,scrollbars=yes'); void(0);"> <img src="images/tuneup.gif">Fine
								tune a package</a></div>
					</form>
				</div>
				<div class="clearBoth"></div>
			</div>
			<!-- begin collapsible list of projects -->
			<table cellpadding=0 cellspacing=0>
				<!-- begin CATEGORY LABEL -->
				<tr class="tableHeading">
					<th colspan="2" class="first">All Proposed Projects</th>
					<th>Money Needed</th>
				</tr>
				<c:forEach var="category" begin="1" end="2">
					<!-- start road projects -->
					<tr>
						<c:choose>
							<c:when test="${category == 1}">
								<td class="category" colspan="3"><strong>Road Projects</strong></td>
							</c:when>
							<c:otherwise>
								<td class="category" colspan="3"><strong>Transit Projects</strong></td>
							</c:otherwise>
						</c:choose>
					</tr>
					<!-- end CATEGORY LABEL -->
					<!-- ******* LOOP ENTIRE PROJECT ******** -->
					<c:forEach var="projectRef" items="${projectRefs}" varStatus="loop">
						<c:if test="${projectRef.project.transMode == category}">
							<!-- begin PROJECT -->
							<tr class="${(projectRef.project.inclusive) ? 'fundingType' : 'fundingType2'}">
								<td class="fundingSourceItem">${projectRef.project.name} Options</td>
								<td colspan="2"> ${(projectRef.project.inclusive) ? 'Select at most one'
									: 'Select any number'} </td>
							</tr>
							<!-- end PROJECT -->
							<tr class="objectives" id="objective${projectRef.id}">
								<td colspan="3">
									<table>
										<c:set var="doNothing"value="true"/>
										<c:forEach var="altRef" items="${projectRef.altRefs}" varStatus="loop">
											<tr>
												<td>
													<label>
													<c:choose>
														<c:when test="${projectRef.project.inclusive}">
															<input type="radio" ${(pg:contains(userPkg.projAltRefs,altRef)) ? "checked='CHECKED'" : ""} name="project-${projectRef.project.id}" id="alt-${altRef.id}" onChange="clearSelectionThenDefine('${projectRef.project.id}', 'project')" />
														</c:when>
														<c:otherwise>
															<input type="checkbox" ${(pg:contains(userPkg.projAltRefs,altRef)) ? "checked='CHECKED'" : ""} name="proj-${projectRef.project.id}" onChange="setProjectToUserPkg('${altRef.id}', this.checked);" />
														</c:otherwise>
													</c:choose>
													${altRef.alternative.name} </label>
												</td>
												<td class="cost">$${altRef.alternative.cost} million</td>
											</tr>
											<c:if test="${pg:contains(userPkg.projAltRefs,altRef)}">
												<c:set var="doNothing"value="false"/>
											</c:if>
										</c:forEach>
										<c:if test="${projectRef.project.inclusive}">
											<tr>
												<td>
													<label>
													<input type="radio" ${(doNothing) ? "checked" : ""}  onchange="clearSelectionThenDefine('${projectRef.project.id}', 'project')" name="project-${projectRef.project.id}"  />
													Do nothing</label>
												</td>
												<td class="cost">&nbsp;</td>
											</tr>
										</c:if>
									</table>
								</td>
							</tr>
						</c:if>
					</c:forEach>
					<!-- ******* END LOOP ENTIRE PROJECT ******** -->
				</c:forEach>
			</table>
			<!-- end collapsible project list -->
		</div>
		<!-- end left -->
		<!-- begin cell containing #right -->
		<div id="right" class="floatRight">
			<!-- begin GOOGLE MAP -->
			<div id="map"> <img src="/images/gmaps.gif" width="520"> </div>
			<!-- end GOOGLE MAP -->
			<table cellpadding=0 cellspacing=0>
				<tr class="tableHeading">
					<th class="first">Funding Source</th>
					<th>Money Raised</th>
					<th>Cost to the avg. taxpayer</th>
					<th>Cost to you</th>
				</tr>
				<!-- begin FUNDING source -->
				<c:forEach var="fundingRef" items="${fundingRefs}" varStatus="loop">
					<tr class="fundingType">
						<td class="fundingSourceItem">${fundingRef.source.name}</td>
						<td colspan="3">One option will be chosen</td>
					</tr>
					<!-- end FUNDING source -->
					<!-- begin OPTIONS -->
					<c:set var="doNothing"value="true"/>
					<c:forEach var="altRef" items="${fundingRef.altRefs}" varStatus="loop">
						<tr>
							<td class="fundingSourceItem">
								<label>
								<input type="radio" ${(pg:contains(userPkg.fundAltRefs,altRef)) ? "CHECKED" : ""}  name="source-${fundingRef.source.id}" id="alt-${altRef.id}" onChange="clearSelectionThenDefine('${fundingRef.source.id}', 'source')" />
								${altRef.alternative.name}</label>
							</td>
							<td>${altRef.alternative.revenue}</td>
							<td>$${altRef.alternative.avgCost}</td>
							<td>???</td>
						</tr>
						<c:if test="${pg:contains(userPkg.fundAltRefs,altRef)}">
							<c:set var="doNothing"value="false"/>
						</c:if>
					</c:forEach>
					<tr>
						<td class="fundingSourceItem">
							<label>
							<input type="radio" ${(doNothing) ? "CHECKED" : ""} name="source-${fundingRef.source.id}" onChange="clearSelectionThenDefine('${fundingRef.source.id}', 'source')" />
							Do nothing</label>
						</td>
						<td class="cost">&nbsp;</td>
						<td class="cost">&nbsp;</td>
						<td class="cost">&nbsp;</td>
					</tr>
				</c:forEach>
				<!-- end OPTIONS -->
			</table>
		</div>
	</div>
	<!-- end NewTable-->
</div>
<!-- end Object-->
<div class="clearBoth"></div>
<!-- begin TOP SUMMARY -->
<div id="yourSummaryRepeat" class="summary">
	<!-- load summary here -->
</div>
<!-- end TOP SUMMARY -->
<div class="clearBoth"></div>
</div>
<!-- end container -->
<!-- start feedback form -->
<pg:feedback id="feedbackDiv" action="cctView.do"/>
<!-- end feedback form -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu">
	<div id="headerContainer">
		<div id="headerTitle" class="floatLeft">
			<h3 class="headerColor">Step 3: Create Packages</h3>
		</div>
		<div class="headerButton floatLeft "> <a href="step3a.html">3a: Review projects</a> </div>
		<div class="headerButton floatLeft "> <a href="step3b.html">3b: Review funding
				options</a> </div>
		<div class="headerButton floatLeft currentBox"> <a href="step3c.html">3c: Create
				your own package</a> </div>
		<div id="headerNext" class="floatRight box5"> <a href="step3b.html">Next Step</a> </div>
	</div>
</div>
<!-- End header menu -->
<!-- Begin footer -->
<div id="footer">
	<jsp:include page="/footer.jsp" />
</div>
<!-- End footer -->
<script type="text/javascript" charset="utf-8">
		getSummary();
	</script>
</body>
</html>
