<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
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
<html:html>
	<head>
	<title>Let's Improve Transportation: ${alternative.name}</title>
	<!-- Site Wide CSS -->
	<style type="text/css" media="screen">
	@import "styles/lit.css";
	@import "styles/table.css";
	@import "styles/step3a-singleproject.css";
	@import "styles/table-grades.css";
	</style>
	<!-- Site Wide JS -->
	<script src="scripts/prototype.js" type="text/javascript"></script>
	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<script src="scripts/search.js" type="text/javascript"></script>
	<script src="scripts/qTip.js" type="text/javascript"></script>
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/util.js'></script>

	<!-- data accessing js -->
	<script type='text/javascript' src='/dwr/interface/ProjectAgent.js'></script>
	<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAq4HJEw-8aIG3Ew6IOzpYEBTwM0brOpm-All5BF6PoaKBxRWWERSP-RPo4689bM1xw9IvCyK4oTwAIw"
	      type="text/javascript"></script>
	<script src="scripts/pgistmap2.js"></script>	<script type="text/javascript">

	
	function load(){
		pgistmap = new PGISTMapEditor('obj-right', 435, 485, false);
		getFootprintsByAltId(${alternative.id});
	}
	var pgistmap = null;
	
	/* *************** Get footprints for a given project alternative id *************** */
	var transmode = "${alternative.project.transMode}";
	var transcolor = (transmode==0)?"#FF0000":"#00FF00";
	function getFootprintsByAltId(id){
		ProjectAgent.getFootprintsByAltId({altid:id}, {
			callback:function(data){
				if (data.successful){
					for(fpid in data.footprints){
						if(data.footprints[fpid].geotype==0 ||
							data.footprints[fpid].geotype==2 || 
							data.footprints[fpid].geotype==5){ // line
							var points = pgistmap.makeGPoints(data.footprints[fpid].coords);
							for(var j=0; j<points.length; j++){
								pgistmap.map.addOverlay(
									new GPolyline(points[j], "#FFFFFF", 8, 0.6) );
								pgistmap.map.addOverlay(
									new GPolyline(points[j], transcolor, 4, 0.9) );
							}
							pgistmap.scaleToCoords(points);
							
							//pgistmap.recoverCoords(data.footprints[fpid].coords);
							//pgistmap.scaleToCoords();
							//pgistmap.drawLines();
						}else if(data.footprints[fpid].geotype==1 || 
							data.footprints[fpid].geotype==4){ // point
							pgistmap.recoverCoords(data.footprints[fpid].coords);
							pgistmap.scaleToCoords();
							pgistmap.drawPoints();	
						}else{ //polygon
							pgistmap.recoverCoords(data.footprints[fpid].coords);
							pgistmap.scaleToCoords();
							pgistmap.drawPolygons();	
							
						}
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
		<h3 class="headerColor" id="project-title">${alternative.name}</h3>
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
		<span id="project-moneyNeeded">${alternative.cost} million</span>
		</p>
		<p>
		<h4 style="display:inline">Sponsoring Agency: </h4>
		<span id="project-sponsoringAgency">${alternative.sponsor}</span>
		</p>
		<h4 style="display:inline">County: </h4>
		<span id="project-county">${alternative.county}</span>
		</p>
		
		<p>
		<h4>Short Description</h4>
		<span id="project-shortDescription">${alternative.shortDesc}</span>
		</p>
		<p>
		<h4>Detailed Description</h4>
		<span id="project-detailedDescription">
			<pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">
				${alternative.detailedDesc}
			</pg:termHighlight>
		</span>
		<p>
		<h4>Links to additional information about this project</h4>
		<span id="project-links">
			${alternative.links}
		</span>
		</p>
		<p>
		<h4>Statement for</h4>
		<span id="project-statementFor">${alternative.statementFor}</span>
		</p>
		<p>
		<h4>Statement against</h4>
		<span id="project-statementAgainst">${alternative.statementAgainst}</span>
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
</html:html>
