<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>

<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html xmlns:v="urn:schemas-microsoft-com:vml">
	<head>
	<title>Let's Improve Transportation - Step 4b: Package Info Page</title>
	<!-- Site Wide CSS -->
	<style type="text/css" media="screen">
		@import "styles/lit.css";
		@import "styles/table.css";
		@import "styles/table-grades.css";
		@import "styles/step4b-packageinfo.css";
	</style>
    <style type="text/css">v\:* {behavior:url(#default#VML);}</style>
	<!-- End Site Wide CSS -->
	<!-- Site Wide JS --> 
	<script src="scripts/prototype.js" type="text/javascript"></script>
	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<script src="scripts/search.js" type="text/javascript"></script>
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/util.js'></script>
	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
    <wf:gmapjs />
    <script type='text/javascript' src='/dwr/interface/ProjectAgent.js'></script>
    <script type='text/javascript' src='/dwr/interface/PESAgent.js'></script>
    <script src="scripts/pgistmap2.js"></script>
    <script type='text/javascript'>
        var pgistmap = null;
        var prjaltlist = [];
        var fpidlist = "";
        var overlaypoints = [];

        function loadFootprints(){
            pgistmap = new PGISTMapEditor('gmap', 500, 380, false);
            pgistmap.addLegend([{"img":"/images/leg_road.gif", "descp":"Road projects"},
                    {"img":"/images/leg_transit.gif", "descp":"Transit projects"}], true);

            if(fpidlist.length > 0){
                fpidlist = fpidlist.substring(1, fpidlist.length-1);  //get rid of the first comma
                ProjectAgent.getFootprints({fpids:fpidlist}, {
                    callback:function(data){
                        if (data.successful){
                            for(fpid in data.footprints){
                                overlaypoints['_'+fpid] = [];
                                overlaypoints['_'+fpid]["geotype"] = data.footprints[fpid].geotype;
                                overlaypoints['_'+fpid]["coords"] = pgistmap.makeGPoints(data.footprints[fpid].coords);
                            }
                            renderProjects();
                        }else{
                            alert(data.reason);
                        }
                    },
                    errorHandler:function(errorString, exception){ 
                        alert("ProjectAgent.getFootprint( error:" + errorString + exception);
                    }
                });
            }
        }
        
        function renderProjects(){
            for(var i=0;i<prjaltlist.length;i++){
                var p = prjaltlist[i];
                p["overlays"] = []; 
                if(p["fpids"] == "") continue;
                
                var geomkeys = p["fpids"].split(',');
                for(var k=0; k<geomkeys.length; k++){
                    var geomkey = '_'+geomkeys[k];
                    if(overlaypoints[geomkey] == null)continue;
                    
                    var transcolor = (p["mode"]==2)?"#0bc00f":"#FF0000";
                    var transicon = (p["mode"]==2)?pgistmap.transiticon:pgistmap.roadicon;
                    p["overlays"] = p["overlays"].concat(pgistmap.createOverlays(overlaypoints[geomkey]["coords"], 
                        overlaypoints[geomkey]["geotype"], transcolor, 4, 0.9, "", transicon));
                    
                    for(var j=0; j<p["overlays"].length; j++){
                        pgistmap.map.addOverlay( p["overlays"][j] );
                    }
                }
            }
         pgistmap.map.setCenter(new GLatLng(47.651500,-122.165222),9);
        }
    </script>
    
<event:pageunload />
	</head>
	<body onload="loadFootprints()" onunload="GUnload()">
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
		<!-- begin Object -->
		<div id="object clearfix">
			<p><h2 class="headerColor">${package.description} Details</h2></p>
			<!-- begin TOP SUMMARY -->
			<div id="gmap" class="floatRight box11"></div>			
			<div class="summary box11">
				<table>
					<tr>
						<td><h3>Total money needed</h3></td>
						<td>$<fmt:formatNumber maxFractionDigits="1" value="${package.totalCost/1000000000}" /> billion
							</td>
					</tr>
					<tr>
						<td><h3>Total money raised</h3></td>
						<td>$<fmt:formatNumber maxFractionDigits="1" value="${package.totalFunding/1000000000}" /> billion</td>
					</tr>
					<c:if test="${userPkg != null}">
						<tr>
							<td><strong>Cost to you:</strong></td>
							<td><fmt:formatNumber type="currency" value="${package.yourCost}"/> per year</td>
						</tr>
					</c:if>
					<tr>
						<td><strong>Cost to the average resident:</strong></td>
						<td><fmt:formatNumber type="currency" value="${package.avgResidentCost}" /> per year</td>
					</tr>
					<tr>
						<td><strong>Number of projects in your package:</strong></td>
						<td>${fn:length(package.projAltRefs)}</td>
					</tr>
				</table>

					<c:choose>
						<c:when test="${(package.totalFunding - package.totalCost) > 0}">
							<div id="balance" class="balance">
								<h3> </h3>
							</div>
						</c:when>
						<c:when test="${(package.totalFunding - package.totalCost) == 0}">
							<div id="balance" class="balance">
								<h3> </h3>
							</div>
						</c:when>
						<c:otherwise>
							<div id="balance" class="exceed">
								<h3>Costs exceed revenue!</h3>
							</div>
						</c:otherwise>
					</c:choose>
			</div>
			<!-- end TOP SUMMARY -->
			<div class="clearBoth"></div>

			<div id="newtable">
				<div id="top" class="box11">
					<table cellpadding=0 cellspacing=0>
						<tr class="tableHeading">
							<th class="first col1">Funding for ${package.description}</th>
							<th class="col2">Money raised</th>
							<th class="col3">Estimated annual cost to the average taxpayer</th>
							<th class="col4">Estimated annual cost to you</th>
						</tr>
						<!-- begin Funding Source -->
						<c:forEach var="source" items="${packageFunding}" varStatus="loop">
								<tr id="sourceId-${source.fundingSourceId}<" class="fundingType">
									<td class="fundingSourceItem">${source.name}</td>
									<td colspan="3">&nbsp;</td>
								</tr>
								<!-- end PROJECT -->
								<!-- begin HIDDEN ROW of OPTIONS -->
								
								<tr class="objectives" id="objective1">
									<td colspan="4">
										<table>
											<c:forEach var="alt" items="${source.fundingSourceAlternatives}" varStatus="loop">
												<tr>
													<td class="col1">${alt.name}</td>
													<td class="col2">$<fmt:formatNumber maxFractionDigits="0" value="${alt.estCost/1000000}" /> million</td>
													<td class="col3"><fmt:formatNumber type="currency" value="${alt.avgCost}"/></td>
													<td class="col4"><fmt:formatNumber type="currency" value="${alt.yourCost}"/></td>
												</tr>
											</c:forEach>
										</table>
									</td>
								</tr>
								<!-- end HIDDEN ROW -->
	
						</c:forEach>

					</table>
				</div>
				<div id="bottom" class="box11">
					<table cellpadding=0 cellspacing=0>
						<tr class="tableHeading">
							<th class="col1">Projects ${package.description}</th>
							<th class="col2">Money Needed</th>
							<th class="col3">Average Grade</th>
							<th class="col4">Average Weighted Grade (your weights)</th>
							<th class="col5">Average Weighted Grade (all participants' weights)</th>
						</tr>
						<!-- begin CATEGORY LABEL -->
						<tr >
							<td class="category" colspan="5"><strong>Road projects</strong></td>
						</tr>
						<!-- end CATEGORY LABEL -->
						<!-- begin PROJECT -->
						<c:if test="${fn:length(packageRoadProjects)==0}">
							<tr>
								<td>No road projects were selected for this package.</td>
							</tr>
						</c:if>

						<c:forEach var="project" items="${packageRoadProjects}" varStatus="loop">
							<tr id="projId" class="fundingType">
								<td colspan="5" class="fundingSourceItem">${project.name}</td>
							</tr>
							<!-- begin ROW of OPTIONS -->
							<tr  class="objectives">
								<td colspan="5">
									<table>
										<c:forEach var="alt" items="${project.projectAlternatives}" varStatus="loop">
											<tr class="option">
												<td class="col1">${alt.name}</td>
												<td class="col2">
													$<fmt:formatNumber maxFractionDigits="0" value="${alt.moneyNeeded/1000000}" /> million</td>
												<td class="col3 grade${pg:gradeSwitch(alt.projGrade)}">${alt.projGrade}</td>
												<td class="col4 grade${pg:gradeSwitch(alt.yourGrade)}">${alt.yourGrade}</td>
												<td class="col5 grade${pg:gradeSwitch(alt.avgGrade)}">${alt.avgGrade}</td>
											</tr>
											<script type="text/javascript">
												altfpids = "${alt.fpids}";
												if(altfpids.length > 0) fpidlist += "," + altfpids;
												prjaltlist.push({"name":"${alt.name}", 
														"id":"${alt.projectAlternativeID}",
														"mode":"${alt.transMode}",
														"fpids":"${alt.fpids}",
														});
											</script>
											</c:forEach>
									</table>
								</td>
							</tr>
							<!-- end ROW of OPTIONS -->
							<!-- end PROJECT -->
						</c:forEach>
						
						<tr >
							<td class="category" colspan="5"><strong>Transit projects</strong></td>
						</tr>
						<!-- end CATEGORY LABEL -->
						<!-- begin PROJECT -->
						<c:if test="${fn:length(packageTransitProjects) == 0}">
							<tr>
								<td>No transit projects were selected for this package.</td>
							</tr>
						</c:if>
						<c:forEach var="project" items="${packageTransitProjects}" varStatus="loop">
							<tr id="projId-${project.projectId}" class="fundingType">
								<td colspan="5" class="fundingSourceItem">${project.name}</td>
							</tr>
							<!-- begin ROW of OPTIONS -->
							<tr  class="objectives">
								<td colspan="5">
									<table>
										<c:forEach var="alt" items="${project.projectAlternatives}" varStatus="loop">
											<tr class="option">
												<td class="col1">${alt.name}</td>
												<td class="col2"><fmt:formatNumber type="currency" value="${alt.moneyNeeded/1000000}"/> million</td>
												<td class="col3 grade${pg:gradeSwitch(alt.projGrade)}">${alt.projGrade}</td>
												<td class="col4 grade${pg:gradeSwitch(alt.yourGrade)}">${alt.yourGrade}</td>
												<td class="col5 grade${pg:gradeSwitch(alt.avgGrade)}">${alt.avgGrade}</td>
												<script type="text/javascript">
													altfpids = "${alt.fpids}";
													if(altfpids.length > 0) fpidlist += "," + altfpids;
													prjaltlist.push({"name":"${alt.name}", 
															"id":"${alt.projectAlternativeID}",
															"mode":"${alt.transMode}",
															"fpids":"${alt.fpids}",
															});
												</script>
											</tr>
										</c:forEach>
									</table>
								</td>
							</tr>
							<!-- end ROW of OPTIONS -->
							<!-- end PROJECT -->
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
		<!-- end Object-->
	</div>
	<!-- end container -->
	</body>
</html>
<!-- 												

												<tr class="option">
													<td class="col1">Elevated Structure</td>
													<td class="col2">$217,015,384,615</td>
													<td class="col3 gradeFPlus">F+</td>
													<td class="col4 gradeDMinus">D-</td>
													<td class="col5 gradeCPlus">C+</td>
												</tr>
												<tr class="option">
													<td class="col1">Elevated Structure</td>
													<td class="col2">$217,015,384,615</td>
													<td class="col3 gradeD">D</td>
													<td class="col4 gradeA">A</td>
													<td class="col5 gradeBMinus">B-</td>
												</tr>
-->
