<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<!--####
	Project: Let's Improve Transportation!
	Page: Help Me 
	Description: Users will use this component to re-weigh their criteria to have the system assist in the creation of a package.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Matt Paulin, Zhong Wang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] myLimit validations (Jordan)
		[x] Polish it a bit (Adam)

#### -->
<html:html>
<head>
<title>Package Tuner</title>
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
		
		//START Global vars
		var usrPkgId = "${usrPkgId}";
		var fundSuiteId = "${fundSuiteId}";
		var projSuiteId = "${projSuiteId}";
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
			alert("Funding Choices Hash: " + config.fundingChoices.inspect());
			alert("Project Choices Hash: " + config.projectChoices.inspect());

			//alert("createmyconfiguredpackage config:" + config )			
			var mylimit = $F('mylimit');
			var avglimit = 0;
			
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
		}
	</script>
	
	<style type="text/css" media="screen">
	#left {margin:0px 15px}
	
	</style>

<body>
	<h1>Help Me!</h1>
	<p>Answer the following questions so that we can suggest a package that matches your general preferences.
		you will be able to adjust your suggested package before moving on.</p>

		<div id="object">
			<!-- begin NewTable-->
			<div id="newTable">
				<p><label>Average Person Limit</label> <input type="text" id="mylimit" /></p>
				<div id="left" class="floatLeft">
					<table cellpadding=0 cellspacing=0>
						<!-- begin CATEGORY LABEL -->
						<tr class="tableHeading">
							<th colspan="2" class="first">Projects</th>
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
																${altRef.alternative.name}
															</label>
														</td>
														<td class="cost">
															<select name="projectChoices" id="projAltSelect-${altRef.id}">
																<option value="2">Must Have</option>
																<option value="1">Maybe</option>
																<option value="0">Never</option>
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

							<th>Cost to you</th>
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
										<label>${altRef.alternative.name}</label>
									</td>
	
									<td>
										<select name="fundingChoices" id="fundAltSelect-${altRef.id}">
											<option value="2">Must Have</option>
											<option value="1" SELECTED>Maybe</option>
											<option value="0">Never</option>
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
		<p><input type="button" onClick="getTunerConfig();" value="Go!"/></p>

		</div>
	

</body>
</html:html>