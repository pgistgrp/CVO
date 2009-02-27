<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="javascript" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!--####
	Project: Let's Improve Transportation!
	Page: Project Alternative Description for Learn more.  Should be identical to projectAlt.jsp except for criteria section.
	Description: This page serves as an information page for a given project alternative in learn more. 
	Author: Jordan Isip, John Le, Adam Hindman
#### -->

<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
	<head>
	<title>Let's Improve Transportation: ${alt.project.name} - ${alt.name}</title>
	<!-- Site Wide CSS -->
	<style type="text/css" media="screen">
	@import "styles/lit.css";
	@import "styles/table.css";
	@import "styles/step3a-singleproject.css";
	@import "styles/table-grades.css";
	</style>
	<style type="text/css">
	    v\:* {
	      behavior:url(#default#VML);
	    }
	</style>
	<!-- Site Wide JS -->
	<script src="scripts/prototype.js" type="text/javascript"></script>
	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<script src="scripts/search.js" type="text/javascript"></script>
	<script src="scripts/qTip.js" type="text/javascript"></script>
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/util.js'></script>

    <javascript:gmapjs />
    <script type='text/javascript' src='/dwr/interface/ProjectAgent.js'></script>
    <script type='text/javascript' src='/dwr/interface/PESAgent.js'></script>
	<script src="scripts/pgistmap2.js"></script>	
    <script type="text/javascript">

	
	function load(){
		pgistmap = new PGISTMapEditor('obj-right', 435, 485, false);
		getFootprintsByAltId(${alt.id});
	}
	var pgistmap = null;
	
	/* *************** Get footprints for a given project alternative id *************** */
	var transmode = "${alt.project.transMode}";
	var transcolor = (transmode==2)?"#00FF00":"#FF0000";
	function getFootprintsByAltId(id){
		ProjectAgent.getFootprintsByAltId({altid:id}, {
			callback:function(data){
				if (data.successful){
					for(fpid in data.footprints){
						var transicon = (transmode==2)?pgistmap.transiticon:pgistmap.roadicon;
						var points = pgistmap.makeGPoints(data.footprints[fpid].coords);
						var overlays = pgistmap.createOverlays(
							points, 
							data.footprints[fpid].geotype,
							transcolor, 4, 0.9, "", transicon);
						//make lines look better by adding thicker background lines
						if(data.footprints[fpid].geotype==0 ||
							data.footprints[fpid].geotype==2 || 
							data.footprints[fpid].geotype==5){
								var bgoverlays = pgistmap.createOverlays(
									points, 
									data.footprints[fpid].geotype,
									"FFFFFF", 8, 0.9, "");
								for(var j=0; j<overlays.length; j++){
									pgistmap.map.addOverlay( bgoverlays[j] );
								}
						}
						for(var j=0; j<overlays.length; j++){
							pgistmap.map.addOverlay( overlays[j] );
						}
						pgistmap.scaleToCoords(points);
					}
				}else{
					alert("Something wrong happened, reason: " + data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.getFootprint( error:" + errorString + exception);
			}
		});
        
	}
	
	</script>
	<event:pageunload />
	</head>
	<body onLoad="load()" onUnload="pgistmap=null;GUnload();">
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
	<!-- begin Object -->
	<div id="object">
		<h3 class="headerColor" style="text-transform:none" id="project-title">${alt.project.name} - ${alt.name}</h3>
		<!-- begin cell containing Google Map object -->
		
	
		<!--GOOGLE MAPS GO HERE GMAN! -->
		<div id="obj-right" class="floatRight"> 
			<!--img src="/images/gmaps.gif"--> 
		</div>
		<!-- end GMAN -->
		
		
		<!-- end cell containing Google Map object -->
		
	
		<!--begin project description -->
		<p>
		<h4 style="display:inline">Money needed to complete this project: </h4>
		<span id="project-moneyNeeded"> 
			$<fmt:formatNumber maxFractionDigits="0" value="${alt.cost/1000000}" /> million</span>
		</p>
		<p>
		<h4 style="display:inline">Sponsoring agency: </h4>
		<span id="project-sponsoringAgency">${alt.sponsor}</span>
		</p>
		<h4 style="display:inline">County: </h4>
		<span id="project-county">${alt.county}</span>
		</p>
		
		<p>
		<h4>Short description</h4>
		<span id="project-shortDescription">${alt.shortDesc}</span>
		</p>
		<p>
		<h4>Detailed description</h4>
		<span id="project-detailedDescription">
			<pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">
				${alt.detailedDesc}
			</pg:termHighlight>
		</span>
		<p>
		<h4>Links to additional information about this project</h4>
		<span id="project-links">
			${alt.links}
		</span>
		</p>
		<p>
		<h4>Statement for</h4>
		<span id="project-statementFor">${alt.statementFor}</span>
		</p>
		<p>
		<h4>Statement against</h4>
		<span id="project-statementAgainst">${alt.statementAgainst}</span>
		</p>
		<!-- end project description -->
	</div>
	<!-- end obj-left -->
	<!-- begin firefox height hack -->
	<div class="clearBoth"></div>
	<!-- end firefox height hack -->
	<!-- Load separate file content starting here -->

	</div>
	<!-- end container -->
	<!-- start feedback form -->
	<pg:feedback id="feedbackDiv" action="cctView.do"/>
	<!-- end feedback form -->
	</body>
</html>
