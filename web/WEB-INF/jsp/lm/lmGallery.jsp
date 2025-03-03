<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="javascript" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<html xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<title>Let's Improve Transportation - Learnmore: Review Projects</title>
<!-- Site Wide JS -->
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/util.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<!--Mapping  Libraries-->
<script type='text/javascript' src='/dwr/interface/ProjectAgent.js'></script>
<script type='text/javascript' src='/dwr/interface/PESAgent.js'></script>
<wf:gmapjs />
<script type='text/javascript' src='scripts/pgistmap2.js'></script>
<!--END Mapping  Libraries-->

<script type="text/javascript">
var prjaltlist = [];
var fpidlist = "";
var overlaypoints = [];
var pgistmap = null;

function load(){
    pgistmap = new PGISTMapEditor('map', 420, 520, false);
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
                overlaypoints[geomkey]["geotype"], transcolor, 3, 0.9, "", transicon));
            
            for(var j=0; j<p["overlays"].length; j++){
                pgistmap.map.addOverlay( p["overlays"][j] );
            }
        }
    }
     pgistmap.addLegend([{"img":"/images/leg_road.gif", "descp":"Road projects"},
     			{"img":"/images/leg_transit.gif", "descp":"Transit projects"}], true);
}

</script>
<style type="text/css">
@import "styles/lit.css";
@import "styles/table.css";
@import "styles/step3a-reviewprojects.css";
</style>
<style type="text/css"> v\:* {behavior:url(#default#VML);}</style>
</head>
<body onload="load()" onunload="GUnload()">
<!-- Begin the header - loaded from a separate file -->
<!-- Start Global Headers  -->
	<wf:nav />
<!-- End Global Headers -->
<!-- End header -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu">
		<div id="headerContainer">
			<div id="headerTitle" class="floatLeft">
				<h3 class="headerColor">Learn More</h3>
			</div>
			<div class="floatLeft headerButton"> <pg:url page="lmMenu.do">Menu</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmAbout.do">About LIT</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmFaq.do">FAQ</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmTutorial1.do">Tutorial</pg:url> </div>
			<div class="floatLeft headerButton currentBox"> <pg:url page="lmGallery.do">Project Map</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="glossaryPublic.do">Glossary</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmResources.do">More Resources</pg:url> </div>
		</div>
	</div>
<!-- End header menu -->
<!-- #container is the container that wraps around all the main page content -->
<div id="container">
	<!-- begin Object --> 
	<h2 class="headerColor">Project Map</h2>
	<p>Explore the projects being considered in Let's Improve Transportation.
	  The participants in this website will actually discuss these projects.  
		<pg:url page="index.do">Click here to login</pg:url>.</p>
	<div id="object">
		<a href="javascript:Util.expandAll('objectives');">Expand all</a>
		<a href="javascript:Util.collapseAll('objectives');">Collapse all</a>
		<div id="rp3a-left" class="floatLeft">
			<!-- begin collapsible list of projects -->
			<div id="newtable">
				<table cellpadding=0 cellspacing=0>
					<tr class="tableHeading">
						<th colspan="2" class="first">Proposed Projects</th>
						<th class="right"><span class="hiddenLabel" style="display:none">Money Needed</span></th>
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
						<c:forEach var="project" items="${projects}" varStatus="loop">
							
							<c:if test="${project.transMode == category}">
								<!-- begin PROJECT -->
								<tr class="fundingType">
									<td class="fundingSourceItem">
											<a href="javascript:Util.toggleRow(${loop.index});">
											<img src="images/plus.gif" id="icon${loop.index}" class="icon">${project.name}</a>
									</td>
									<td class="col2" colspan="2" align="right"> <!--${(project.inclusive) ? 'Select at most one' : 'Select any number'}-->&nbsp; </td>
								</tr>
								<!-- end PROJECT -->

								<!-- begin HIDDEN ROW of OPTIONS -->
								<tr style="display:none;" class="objectives" id="row${loop.index}">
									<td colspan="3">
										<table>
											<!-- start project alt -->
											<c:forEach var="alt" items="${project.alternatives}" varStatus="loop">
												<tr>
													<td class="col1"><pg:url page="lmAlt.do" target="_blank" params="altId=${alt.id}">${alt.name}</pg:url></td>
													
													<td class="cost">$<fmt:formatNumber maxFractionDigits="0" value="${alt.cost/1000000}" /> million </td>
                                                    <script type="text/javascript">
                                                                prjaltlist.push({"id":"${alt.id}", "fpids":"${alt.fpids}", "mode":"${alt.project.transMode}"}); 
                                                                altfpids = "${alt.fpids}";
                                                                if(altfpids.length > 0) fpidlist += "," + "${alt.fpids}";
                                                    </script>
												</tr>
											</c:forEach>
											<!-- end project alt-->
										</table>
									</td>
								</tr>
								<!-- end HIDDEN ROW -->
							</c:if>
						</c:forEach>
					</c:forEach>

				</table>
			</div>

			<!-- end collapsible project list -->
		</div>
		<!-- end rp3a-left -->
	</div>
	<!-- begin cell containing Google Map object -->
	<!-- GUIRONG: This can be up to 420px wide -->
	<div id="map" class="floatRight"></div>
	<!-- end cell containing Google Map object -->
	<!-- begin firefox height hack -->
	<div class="clearBoth"></div>
	<!-- end firefox height hack -->
</div>
<!-- end Object-->
</div>
<!-- end container -->
<!-- start feedback form -->
<pg:feedback id="feedbackDiv" action="cctView.do"/>
<!-- end feedback form -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu">
		<div id="headerContainer">
			<div id="headerTitle" class="floatLeft">
				<h3 class="headerColor">Learn More</h3>
			</div>
			<div class="floatLeft headerButton"> <pg:url page="lmMenu.do">Menu</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmAbout.do">About LIT</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmFaq.do">FAQ</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmTutorial1.do">Tutorial</pg:url> </div>
			<div class="floatLeft headerButton currentBox"> <pg:url page="lmGallery.do">Project Map</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="glossaryPublic.do">Glossary</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmResources.do">More Resources</pg:url> </div>
		</div>
	</div>
<!-- End header menu -->
<!-- Begin footer -->
<div id="footer">
	<jsp:include page="/footer.jsp" />
</div>
<!-- End footer -->

</body>
</html>
