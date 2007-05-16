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
					
						var fcs = $H({});
						var pcs = $H({});

						//create the hash for funding sources
						//key = altRefId, value = 0-2

						var fundingSelects = document.getElementsByName('fundingChoices');
						var projectSelects = document.getElementsByName('projectChoices');

						fcs = convertSelectsToHash(fundingSelects,fcs)
						pcs = convertSelectsToHash(projectSelects,pcs)
			
						data.config.fundingChoices = fcs;
						data.config.projectChoices = pcs;
						
						//alert(data.config.fundingChoices.inspect());
						createMyConfiguredPackage(data.config);
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
		
		function createMyConfiguredPackage(config){
			//alert("Funding Choices Hash: " + config.fundingChoices.inspect());
			//alert("Project Choices Hash: " + config.projectChoices.inspect());

			//alert("createmyconfiguredpackage config:" + config )			
			var mylimit = $F('mylimit');
			var avglimit = 0;
			if(mylimit.length > 0){
				PackageAgent.createMyConfiguredPackage(config, mylimit, avglimit, usrPkgId, {
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
	<div style="display: none;" id="loading-indicator"> Loading... 
		<img src="/images/indicator_arrows.gif"> 
	</div>
	<div style="display:none;" id="saving-indicator"> Saving...
		<img src="/images/indicator_arrows.gif">
	</div>
<div id="top">
		<h2 class="headerColor">Fine Tune a Package</h2>
		<div id="overview" class="box2">
		<h3 class="headerColor">Overview and Instructions</h3>
			<p>Answer the following questions so that we can suggest a package that matches
				your general preferences. you will be able to adjust your suggested package before
				moving on.</p>
		</div>
		<p><label>How much would you be willing to pay each year for this package?</label> <span class="dollarSign">$</span><input type="text" id="mylimit" size="5"/></p>
	</div>
<div id="object" class="stripWS">
			<h3 class="headerColor">Re-Weigh Your Criteria</h3>
			<div id="criteria">
				<!--load the criteria partial here -->
			</div>
			<!-- begin NewTable-->
			<br />
			<div id="newTable" class="stripWS">
				<h3 class="headerColor">Fine Tune Projects and Funding Sources</h3>
				<div id="left" class="floatLeft">
					<table cellpadding=0 cellspacing=0>
						<!-- begin CATEGORY LABEL -->
						<tr class="tableHeading">
							<th colspan="2" class="first">Projects</th>
							<th>Priority</th>
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
															<select name="projectChoices" id="projAltSelect-${altRef.id}">
																<option value="2">Include it</option>
																<option value="1" SELECTED>Don't care</option>
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
							<th class="first">Funding Source</th>
							<th>Priority</th>
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
										<select name="fundingChoices" id="fundAltSelect-${altRef.id}">
											<option value="2">Include it</option>
											<option value="1" SELECTED>Don't care</option>
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
			<h3>Finished fine tuning your package?</h3>
			<input type="button" id="goBtn" onClick="getTunerConfig();" value="Create My Package"/>
			<div class="clearBoth"></div>
		</div>
		
		<script type="text/javascript" charset="utf-8">
			//getWeights();
			getCriteriaSuiteById();

			//setTimeout(function() {initWeights();}, 350);
		</script>
</body>
</html:html>