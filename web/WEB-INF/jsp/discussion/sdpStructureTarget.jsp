<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>


<pg:fragment type="html">
	<!--####
		Project: Let's Improve Transportation!
		Page: Object for Structured Discussion for Projects
		Description: This page will list and map out all of the projects in the given decision situation.
					 The javascript on this page will allow for map manipulation.  Clicking on a project
					 will 
		Author(s): Jordan Isip, Adam Hindman, John Le
		Todo Items:
			[x] Initial Skeleton Code (Jordan)
			[x] Integrate Structured Discussion (Jordan)
			[x] Integrate Project Tree (Jordan)
			[x] Integrate Layout (Adam) 
			[x] Ensure loop is working with backend (Jordan)
			[x] Ensure tree menu is working correctly (Jordan)
			[x] Test with backend contractor code (Jordan)
	#### -->
<!-- begin "overview and instructions" area -->

  <div id="overview" class="box2">
		<h3>Overview and instructions</h3>
		<c:set var="current" value="${requestScope['org.pgist.wfengine.CURRENT']}" />
		
		Learn about proposed projects for improving or expanding our regional transportation system.
		<ul>
			<li>Click on a project to view it on the map and read more about it.</li>
			<li>Discuss the benefits and drawbacks of any project in the discussion area below.</li>
		</ul>
			<p>When you are ready, feel free to Review funding options (Step 3b) and Create your own package (Step 3c)</a>.</p>
				<a href="#" onclick="Effect.toggle('hiddenRM','blind'); return false">Read more about this step</a>
		<p id="hiddenRM" style="display:none">The proposed projects have been examined by a panel of transportation specialists who assigned scores for the improvement factors we reviewed in Step 2. (<pg:url page="lmFaq.do" target="_blank" anchor="step3-assigned">Read more about the scoring process</pg:url>). After reviewing the proposed transportation projects (Step 3a) and funding options (Step 3b) you will have an opportunity to create your own transportation package by selecting the projects and funding options you like best. </p>
  </div>
	<!-- end overview -->
	<!-- begin Object -->
	<div id="object">
		<div id="rp3a-left" class="floatLeft">
			<!-- begin collapsible list of projects -->
			<div id="newtable">
				<table cellpadding=0 cellspacing=0>
					<tr class="tableHeading">
						<th colspan="2" class="first">Proposed improvement projects</th>
						<th class="right"><span id="hiddenLabel" style="visibility:hidden">Money needed</span></th>
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
						<c:forEach var="infoObject" items="${infoStructure.infoObjects}" varStatus="loop">
							<c:if test="${infoObject.object.project.transMode == category}">
								
								
								<!-- begin PROJECT -->
								<tr class="${(infoObject.object.project.inclusive) ? 'fundingType' : 'fundingType2'}">
									<td class="fundingSourceItem">${infoObject.object.project.name}</td>
									<td class="col2">&nbsp;</td>
									<td class="col3">&nbsp;</td>
								</tr>
								<!-- end PROJECT -->
								
								<!-- begin objectives -->
								<tr class="objectives" id="objective${loop.index}">
									<td colspan="3">
										<table>
											<c:set var="doNothing"value="true"/>
											
											<c:forEach var="altRef" items="${infoObject.object.altRefs}" varStatus="loop">
												<tr>
													<td>
													    <a href="javascript:io.goToProjectAlt(${altRef.id});">${altRef.alternative.name}</a>
													</td>
													<td class="cost">
														$<fmt:formatNumber maxFractionDigits="0" value="${altRef.alternative.cost/1000000}" /> million
												  </td>
												</tr>
												<c:if test="${pg:contains(userPkg.projAltRefs,altRef)}">
													<c:set var="doNothing"value="false"/>
												</c:if>
											</c:forEach>
										</table>
									</td>
								</tr>
								<!-- end objectives -->
							</c:if>
						</c:forEach>
						<!-- ******* END LOOP ENTIRE PROJECT ******** -->
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

</pg:fragment>

<pg:fragment type="script">
	//All Javascript that is internal to this page must go here - not sdRoomMain.
	/* *************** load a dynamic javascript or css file ****************/

	io.loadDynamicFile = function(file){
		var start = file.indexOf('.') + 1
		var finish = file.length
		var type = file.substring(start,finish)
		
		var headElem = document.getElementsByTagName('head')[0];
		if(type == "css"){
			var cssLinkElem = document.createElement('link');
			cssLinkElem.setAttribute('href', file);
			cssLinkElem.setAttribute('type', 'text/css');
			cssLinkElem.setAttribute('rel', 'stylesheet');
			headElem.appendChild(cssLinkElem);
		}else{ //javascript
			var jsLinkElem = document.createElement('script');
			jsLinkElem.setAttribute('src', file);
			jsLinkElem.setAttribute('type', 'text/javascript');
			headElem.appendChild(jsLinkElem);
		}
		
	}
	
	io.expandAll = function(){
		var rows = document.getElementsByClassName('objectives');
		var icons = document.getElementsByClassName('icon');
		//alert('icons: ' + icons.length + ' rows: ' + rows.length);
		for (var i = 1;i <= rows.length; i++){
			var row = 'objective' + i;
			var icon = 'icon' + i;
			$(row).show();
			$(icon).src = "images/minus.gif";
		}
		$('hiddenLabel').style.visibility = "";
	}
	
	io.collapseAll = function(){
		var rows = document.getElementsByClassName('objectives');
		for (var i = 1;i <= rows.length; i++){
			var row = 'objective' + i;
			var icon = 'icon' + i;
			$(row).hide();
			$(icon).src = "images/plus.gif";
		}
		$('hiddenLabel').style.visibility = "hidden";
	}

	io.testOpenRows = function(){
		var rows = document.getElementsByClassName('objectives');
		hideLabels();
		for (var i = 1;i <= rows.length; i++){
			var row = 'objective' + i;
			if ($(row).style.display != "none"){
				io.showLabels();
			}else{}
		}
	}

	io.toggleRow = function(project,icon){
		Effect.toggle(project, 'appear', {duration: .4, afterFinish:
			function(){
				if ($(project).style.display != ""){
					$(icon).src = "images/plus.gif";
					io.testOpenRows();
					$('hiddenLabel').style.visibility = "hidden";
					}else{
						$(icon).src = "images/minus.gif";
						$('hiddenLabel').style.visibility = "";
					}
				}
		});
	}
    

	
    function loadFootprints(){
        if(fpidlist.length > 0){
            fpidlist = fpidlist.substring(1, fpidlist.length);  //get rid of the first comma
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
                    overlaypoints[geomkey]["geotype"], transcolor, 2, 0.9, "", transicon));
                
                for(var j=0; j<p["overlays"].length; j++){
                    pgistmap.map.addOverlay( p["overlays"][j] );
                }
            }
        }
	 //pgistmap.map.setCenter(new GLatLng(47.651500,-122.165222),9);
     pgistmap.addLegend([{"img":"/images/leg_road.gif", "descp":"Road projects"},
                         {"img":"/images/leg_transit.gif", "descp":"Transit projects"}], true);
     pgistmap.addLegendItem("<label><input type='checkbox'>&nbsp;My trips</label>", loadTravelPath);
     //loadTravelPath();
    }
    
    var travelPath = [];
    function loadTravelPath(){
        if(travelPath.length == 0){
            RegisterAgent.getUserTrips(-1, function(data){//use -1 for user id for the current user
                //data.trips is now an array of trips
                pgistmap.disableMapLogger();
                if(data.successful){
                    for(k=0; k<data.trips.length; k++){
                        travelPath.push( drawTrip(pgistmap, data.trips[k].coords) );
                    }
                }
                PESAgent.saveAct({mapaction:"show personal travel trip"}, {callback:function(data){},
                    errorHandler:function(errorString, exception){} });
                
                pgistmap.enableMapLogger();
            });
        }else{
            pgistmap.disableMapLogger();
            while(travelPath.length>0)pgistmap.map.removeOverlay( travelPath.pop() );
            PESAgent.saveAct({mapaction:"hide personal travel trip"}, {callback:function(data){},
                errorHandler:function(errorString, exception){} });
            pgistmap.enableMapLogger();
        }
    }
    
    function drawTrip(pgmap, coords){
        var points= [];
        for(i=0;i<coords.length; i=i+2){
            points[i/2] = new GPoint(coords[i], coords[i+1]);
        }
        var tripline = new GPolyline(points, "#0000FF", 6, 0.5);
        pgmap.map.addOverlay( tripline );
        pgmap.scaleToCoords(points, true);
        return(tripline);
    }
    
	/* *************** loading on getTargets() in SDRoomMain *************** */
	io.loadDynamicFile('/styles/step3a-reviewprojects.css');
	io.loadDynamicFile('/styles/table.css');
	//io.loadDynamicFile('/dwr/interface/ProjectAgent.js');
    
		io.goToProjectAlt = function(altRefid){
		window.open("projectAlt.do?"+io.wfInfo+"&altrefId="+altRefid+"&critSuiteId="+io.critSuiteId,'_blank');
	}
	
    var pgistmap = new PGISTMapEditor('map', 420, 600, false);
    var prjaltlist = [];
    var fpidlist = "";
    var overlaypoints = [];
    <c:forEach var="infoObject" items="${infoStructure.infoObjects}" varStatus="loop">
        <c:forEach var="altRef" items="${infoObject.object.altRefs}" varStatus="loop">
            prjaltlist.push({"id":"${altRef.id}", "fpids":"${altRef.alternative.fpids}", "mode":"${altRef.alternative.project.transMode}",
                             "name_p":"${infoObject.object.project.name}", "name_a":"${altRef.alternative.name}"}); 
            fpids = "${altRef.alternative.fpids}";
	    if(fpids.length > 0)fpidlist += "," + fpids;
        </c:forEach>
    </c:forEach>
    loadFootprints();
    
</pg:fragment>
