<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<!--####
	Project: Let's Improve Transportation!
	Page: Help Me 
	Description: Users will use this component to re-weigh their criteria to have the system assist in the creation of a package.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Matt Paulin, Zhong Wang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] myLimit validations (Jordan)
		[x] Polish it a bit (Adam)

#### -->
<html:html>
<head>
<title>Package Tuner</title>
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->

<script type='text/javascript' src='/dwr/interface/PackageAgent.js'></script>
<script type='text/javascript' src='/dwr/interface/CriteriaAgent.js'></script>

<style type="text/css">
	@import "styles/lit.css";
	@import "styles/table.css";
	@import "styles/step3c.css";
	@import "styles/weighCriteria.css";
#top{padding:0px;}
#left{margin-left:0px;}
.dollarSign {display:inline;margin:auto 5px auto 10px}
</style>


	<script type="text/javascript" charset="utf-8">
		
		//START Global vars
		var usrPkgId = "${usrPkgId}";
		var fundSuiteId = "${fundSuiteId}";
		var projSuiteId = "${projSuiteId}";
		
		//Load the critSuiteId first for weighCriteria.js (external so we can reuse the functions)
		var critSuiteId = "${critSuiteId}";
		
		function getTunerConfig(){
			//alert("usrPkgId: " + usrPkgId + " projSuiteId: " + projSuiteId + " fundSuiteId: " + fundSuiteId + " critSuiteId: " + critSuiteId); 
			PackageAgent.getTunerConfig({usrPkgId:usrPkgId,projSuiteId:projSuiteId,fundSuiteId:fundSuiteId,critSuiteId:critSuiteId}, {
				callback:function(data){
					if (data.successful){
						//alert("config:" + data.config)
					
						var fcs = {};
						var pcs = {};

						//create the hash for funding sources
						//key = altRefId, value = 0-2

						var fundingSelects = document.getElementsByName('fundingChoices');
						var projectSelects = document.getElementsByName('projectChoices');

						fcs = convertSelectsToHash(fundingSelects,fcs)
						pcs = convertSelectsToHash(projectSelects,pcs)

						//alert("data: " + data.config + " fcs: " + fcs + " pcs: " + pcs)
						createMyConfiguredPackage(data.config,fcs,pcs);
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("PackageAgent.getTunerConfig( error:" + errorString + exception);
				}
			});
		}
		
		function convertSelectsToHash(selects, hash){
			for (var i=0; i < selects.length; i++) {
				start = selects[i].id.indexOf('-') + 1;
				stop = selects[i].id.length;
				selectKey = selects[i].id.substring(start,stop);
				selectValue = selects[i].value;
				//alert("key: " +  selectKey + " value: " + selectValue);
				
				hash[selectKey] = selectValue;
			};
			
			return hash;
		}
		
		function createMyConfiguredPackage(config,fcs,pcs){
			//alert("Funding Choices Hash: " + config.fundingChoices.inspect());
			//alert("Project Choices Hash: " + config.projectChoices.inspect());

			//alert("createmyconfiguredpackage config:" + config )			
			var mylimit = $F('mylimit');
			var avglimit = 0;
			if(mylimit.length > 0){
				var mylimitInt = parseInt(mylimit);
				PackageAgent.createMyConfiguredPackage(config, fcs, pcs, mylimitInt, avglimit, usrPkgId, {
					callback:function(data){
						if (data.successful){
							//alert("createmyconfiguredpackage worked");
							location.href="closeWindowAndReload.jsp";
						}else{
							alert(data.reason);
						}
					},
					errorHandler:function(errorString, exception){ 
					alert("PackageAgent.createMyConfiguredPackage( error:" + errorString + exception);
					}
				});
			}else{
				alert("Please enter how much you are willing to pay per year.")
			}
		}

	</script>
	
	<script type='text/javascript' src='/scripts/weighCriteria.js'></script>
<event:pageunload />
</head>
<body>
	<div id="container">
		<div style="display: none;" id="loading-indicator"> Loading... 
			<img src="/images/indicator_arrows.gif"> 
		</div>
		<div style="display:none;" id="saving-indicator"> Saving...
			<img src="/images/indicator_arrows.gif">
		</div>
	<div id="top">
			<h2 class="headerColor">Help me create a package</h2>
			<div id="overview" class="box2">
			<h3 class="headerColor">Instructions</h3>
				<p>Complete this form and click the button at the bottom of this page to create a package.  You are able to adjust the package before you submit it on the main page.</p>
			</div>
			<div class="box6 padding5">
				<h3 class="headerColor">1. How much are you willing to pay per year?</h3>
				<label>What is the total annual cost <em><strong>you</strong></em> are willing to pay to fund your preferred transportation package?</label> <span class="dollarSign">$</span><input type="text" id="mylimit" size="5"/><br/>
				<div class="clearBoth"></div>
			</div>
		</div>
	<div id="object" class="stripWS">
				<h3 class="headerColor">2. What is the relative importance of each improvement factor?</h3>
        <label>You can keep your weights from Step 2b or you can change them here.</label>
				<div id="criteria">
					<!--load the criteria partial here -->
				</div>
				<!-- begin NewTable-->
				<br />
				<div id="newTable" class="stripWS">
					<h3 class="headerColor">3. Which choices are you sure about?</h3>
         <label>Mark the projects and funding sources that you want to include or exclude from the package that will be created. Feel free to leave any or all the choices as "No opinion".</label><br/><br/>
					<div id="left" class="floatLeft">
						<table cellpadding=0 cellspacing=0>
							<!-- begin CATEGORY LABEL -->
							<tr class="tableHeading">
								<th colspan="2" class="first">Projects</th>
								<th>Include or Exclude</th>
							</tr>
					
							<c:forEach var="category" begin="1" end="2">
								<!-- start road projects -->
								<tr>
									<c:choose>
										<c:when test="${category == 1}">
											<td class="category" colspan="3"><strong>Road projects</strong></td>
										</c:when>
										<c:otherwise>
											<td class="category" colspan="3"><strong>Transit projects</strong></td>
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
											<td colspan="2">&nbsp;
												
											</td>
										</tr>
										<!-- end PROJECT -->
										<tr class="objectives" id="objective${projectRef.id}">
											<td colspan="3">
												<table>
													<c:forEach var="altRef" items="${projectRef.altRefs}" varStatus="loop">
														<tr>
															<td>
																<label>
																	<span style="font-size:9pt">${altRef.alternative.name}</span>
																</label>
															</td>
															<td class="cost">
																<select name="projectChoices" id="projAltSelect-${altRef.alternative.id}">
																	<option value="2">Include it</option>
																	<option value="1" SELECTED>No opinion</option>
																	<option value="0">Exclude it</option>
																</select>
															</td>
														</tr>
													</c:forEach>
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
				
					<div id="right">
						<table cellpadding=0 cellspacing=0>
							<tr class="tableHeading">
								<th class="first">Funding source</th>
								<th>Include or Exclude</th>
							</tr>
							<!-- begin FUNDING source -->
							<c:forEach var="fundingRef" items="${fundingRefs}" varStatus="loop">
								<tr class="fundingType">
									<td class="fundingSourceItem">${fundingRef.source.name}</td>
									<td colspan="3">&nbsp;</td>
								</tr>
								<!-- end FUNDING source -->
								<!-- begin OPTIONS -->
								<c:set var="doNothing"value="true"/>
								<c:forEach var="altRef" items="${fundingRef.altRefs}" varStatus="loop">
									<tr>
										<td class="fundingSourceItem">
											<label style="font-size:9pt">${altRef.alternative.name}</label>
										</td>
		
										<td class="right-col2">
											<select name="fundingChoices" id="fundAltSelect-${altRef.alternative.id}">
												<option value="2">Include it</option>
												<option value="1" SELECTED>No opinion</option>
												<option value="0">Exclude it</option>
											</select>
										</td>
									</tr>
								</c:forEach>
							</c:forEach>
							<!-- end OPTIONS -->
						</table>
					</div>
					
				</div>
			</div>
			<div class="clearBoth">
			</div>
			<div id="finished" class="box7">
				<h3>Are you ready to create a package?</h3>
				<input type="button" id="goBtn" onClick="getTunerConfig();" value="I have completed this form. Show me a package."/>
				<div class="clearBoth"></div>
			</div>
		</div>
		
		<script type="text/javascript" charset="utf-8">
			//getWeights();
			getCriteriaSuiteById();

			//setTimeout(function() {initWeights();}, 350);
		</script>
</body>
</html:html>