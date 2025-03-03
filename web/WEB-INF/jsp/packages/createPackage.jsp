<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<!--####
	Project: Let's Improve Transportation!
	Page: Create My Transportation Package
	Description: This page serves as a form for a user to create her/her own transportation package. 
	Authors:
		Front End: Jordan Isip, Adam Hindman
    	Back End: Matt Paulin, Zhong Wang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] Integrate Adam's Layout (Jordan)
		[x] setFundingtoPkg and setProjecttoPkg (Jordan and Matt)
		[x] SuiteIds (Jordan and Matt)
		[x] Pull Summary Partials (Jordan)
		[x] Balance color (Jordan)
		[x] Clear selection prior to setting (Jordan)
		[x] pg:contains for initialization(Jordan)
		[x] What happens when user clicks on "finished"? (Jordan)
		[x] Ordering (Matt)
		[x] Cost to you (Matt)
		[x] Limit Decimal Points on Floats (Jordan)
		[x] myLimit validations via JS (Jordan)

#### -->
<html xmlns:v="urn:schemas-microsoft-com:vml">
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
<script type='text/javascript' src='/dwr/interface/ProjectAgent.js'></script>
<script type='text/javascript' src='/dwr/interface/PESAgent.js'></script>
<wf:gmapjs />
<script src="scripts/pgistmap2.js"></script>

<!-- End of mapping JavaScript -->
<style type="text/css">
	@import "styles/lit.css";
	@import "styles/table.css";
	@import "styles/step3c.css";	
</style>
<style type="text/css">v\:* {behavior:url(#default#VML);}</style>
<script type="text/javascript" charset="utf-8">
			//Global Vars
			var userPkg = Boolean("${userPkg.id}");
			var pkgId = (userPkg) ? "${userPkg.id}" : "${package.id}";
			var critSuiteId = "${critSuiteId}";
			var fundSuiteId = "${fundSuiteId}";
			var projSuiteId = "${projSuiteId}";
			var pkgSuiteId = "${pkgSuiteId}";
			var pkgValid = null;
			//End Global Vars

			function setFundingToPkg(altRefId,checked){
				//alert("checked = " +checked)
				var deleting = !Boolean(checked); //dealing with checkboxes
				//alert("pkgSuiteId: " + pkgId + "altRefId: " + altRefId + " deleting: " + deleting); 
				PackageAgent.setFundingToPkg({fundingSuiteId:fundSuiteId, pkgId:pkgId,altId:altRefId,deleting:deleting,userPkg:userPkg}, {
					callback:function(data){
						if (data.successful){
							//alert("Funding alt " + altRefId + " was successfully set to " + deleting); //replace with saving indicator later
							if(userPkg){
								updateSummary(data);
							}else{
								getClusteredSummary();
							}
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
							setProjectToPkg(id, false);
							if(alts[i].checked == true){
								//alert("adding" +type+id)
								setProjectToPkg(id, true);
							}
						}else{ //source
							//alert("setting funding to user package...");
							setFundingToPkg(id, false);
							if(alts[i].checked == true){
								//alert(type+id)
								setFundingToPkg(id, true);
							}
						}
					}
					
				};
			}
			
			function setProjectToPkg(altRefId,checked,altId){
				var deleting = !Boolean(checked);
				//alert("pkgId: " + pkgId + " altRefId: "+ altRefId +" deleting: " + deleting); 
				PackageAgent.setProjectToPkg({fundingSuiteId:fundSuiteId,pkgId:pkgId,altId:altRefId,deleting:deleting,userPkg:userPkg}, {
					callback:function(data){
						if (data.successful){
							//alert("Project alt " + altRefId + " was successfully set to " + deleting); //replace with saving indicator later
							if(userPkg){
								updateSummary(data);
							}else{
								getClusteredSummary();
							}
							
							if(!deleting){
								highlightProject(altIdLookUp["" + altRefId]);
								//alert("adding: " + p["name"]);
							}else{
								unHighlightProject(altIdLookUp["" + altRefId]);
								//alert("removing: " + p["name"]);
							}
						}else{
							alert(data.reason);
						}
					},
					errorHandler:function(errorString, exception){ 
					alert("PackageAgent.setProjectToPkg( error:" + errorString + exception);
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
			
			function getClusteredSummary(){
				//alert("pkgId: " + pkgId); 
				PackageAgent.getClusteredSummary({pkgId:pkgId}, {
					callback:function(data){
						if (data.successful){
							updateSummary(data);
						}else{
							alert(data.reason);
						}
					},
					errorHandler:function(errorString, exception){ 
					alert("PackageAgent.getClusteredSummary( error:" + errorString + exception);
					}
				});
			}
			
			function updateSummary(data){
				//Render Summaries
				$('yourSummary').innerHTML = data.html;
				$('yourSummaryRepeat').innerHTML = data.html;
				updateMapSum();
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
				var avglimit = 0;
				var mylimit = $F('mylimit')
				if(mylimit.length > 0){
					//alert("usrPkgId: " + usrPkgId + " limit: " + mylimit); 
					PackageAgent.createMyPackage({usrPkgId:pkgId,avglimit:avglimit,mylimit:mylimit,critSuiteId:critSuiteId,projSuiteId:projSuiteId,fundSuiteId:fundSuiteId}, {
						callback:function(data){
							if (data.successful){
								//if mylimit > 0 and not null
								location.reload(true);
							}else{
								alert(data.reason);
							}
						},
						errorHandler:function(errorString, exception){ 
						alert("PackageAgent.createMyPackage( error:" + errorString + exception);
						}
					});
				}else{
					alert("Please enter how much you are willing to pay per year.")
				}
			}
			
			function editPackageDescription(){
				var desc = $F('txtPkgDesc');
				PackageAgent.setManualPkgDesc({pkgId:pkgId,desc:desc}, {
					callback:function(data){
						if (data.successful){
							$('pkgDesc').innerHTML = "Package " + desc;
							new Effect.toggle('editDesc','blind',{duration:0.2});
						}else{
							alert(data.reason);
						}
					},
					errorHandler:function(errorString, exception){ 
					alert("PackageAgent.editPackageDescription( error:" + errorString + exception);
					}
				});
			}
			
			
			function finished() {
				if(userPkg){
					location.href='waiting.jsp';
				}else{
					location.href='packageMgr.do?pkgSuiteId='+pkgSuiteId+'&projSuiteId='+projSuiteId+'&fundSuiteId='+fundSuiteId+'&critSuiteId='+critSuiteId;
				}
			}
			


			/* *************** START MAPPING FUNCTIONS *************** */
			var fpidlist = "";
			var prjaltlist = [];
			var pgistmap = null;
			var overlaypoints = [];
			var mapPositionTop = 0;
            var altIdLookUp = [];
			
			function load(){
				pgistmap = new PGISTMapEditor(document.getElementById('themap'), 520, 580, false);
				
				if(fpidlist.length > 0){
					fpidlist = fpidlist.substring(1, fpidlist.length);  //get rid of the first comma
					ProjectAgent.getFootprints({fpids:fpidlist}, {
						callback:function(data){
							if (data.successful){
								for(fpid in data.footprints){
									//if(overlaypoints['_'+fpid]!=null)alert("exists");
									overlaypoints['_'+fpid] = [];
									overlaypoints['_'+fpid]["geotype"] = data.footprints[fpid].geotype;
									overlaypoints['_'+fpid]["coords"] = pgistmap.makeGPoints(data.footprints[fpid].coords);
								}
								//render projects
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
				mapPositionTop = getYCoord(document.getElementById('themap'));
                pgistmap.addLegend([{"img":"/images/leg_road.gif", "descp":"Road projects"},
                    {"img":"/images/leg_transit.gif", "descp":"Transit projects"}], true);
				updateMapSum();
			}
			var all_overlays = {};
			function renderProjects(){
                pgistmap.disableMapLogger();
				for(var i=0;i<prjaltlist.length;i++){
					//var fpid = parseInt(p["fpids"]);
					var p = prjaltlist[i];
					p["overlays"] = []; 
					if(p["fpids"] == "") continue;
					var geomkeys = p["fpids"].split(',');
					for(var k=0; k<geomkeys.length; k++){
						var geomkey = '_'+geomkeys[k];
						if(overlaypoints[geomkey] == null){continue;}
						
						var transcolor = (p["mode"]==2)?"#0bc00f":"#FF0000";
						var transicon = (p["mode"]==2)?pgistmap.transiticon:pgistmap.roadicon;
                        
                        if(!all_overlays[geomkey])
                            all_overlays[geomkey] = pgistmap.createOverlays(overlaypoints[geomkey]["coords"], 
                                overlaypoints[geomkey]["geotype"], transcolor, 4, 0.9, "", transicon);
                        createListener(p.id, all_overlays[geomkey]);
                        p["overlays"] = p["overlays"].concat(all_overlays[geomkey]);
						
						for(var j=0; j<p["overlays"].length; j++){
							pgistmap.map.addOverlay( p["overlays"][j] );
                            //createListener(p["overlays"][j], p);
						}
					}
					//if project alternative selected highlight it
					if(p["selected"]){
						highlightProject(p);
					}
				}
                pgistmap.enableMapLogger();
			}
            
            function createListener(id, overlays){
                for(var i=0; i<overlays.length; i++){
                    overlay = overlays[i];
                    if(overlay.idlist){
                        overlay.idlist.push(id);
                    }
                    else{
                        overlay.idlist = [id];
                        GEvent.addListener(overlay, 'click', handleClick);
                    }
                }             
            }
            
            function handleClick(point){
                if(this.idlist == null)return;
                var html = '';
                for(var i=0; i<this.idlist.length; i++){
                  p = findProjectById(this.idlist[i]);
                  if(p){
                    html += '<div style="width:300px;padding-top:10px">' + p.name + '</div>';
                    if(point)
                        pgistmap.map.openInfoWindowHtml(point,  html);
                    else
                        this.openInfoWindowHtml(html);
                    
                    var logstr = 'clicked_project=' + p.name + '(id=' + p.id + ')';
                    PESAgent.saveAct({mapaction:logstr}, {callback:function(data){}, errorHandler:function(errorString, exception){}});
                  }
                }
            }
            
            function findProjectById(id){
                for(var i=0; i<prjaltlist.length; i++){
                    if(prjaltlist[i].id==id)return prjaltlist[i];
                }
                return null;
            }
			
			function highlightProject(project){
			
				if(typeof(project) != 'object') project = getProjectById(project);
				if(project==null)return;
				var transcolor = (project["mode"]==2)?"#0bc00f":"#FF0000";	//1=road; 2=transit
				if(project["fpids"] == "") return;

				var geomkeys = project["fpids"].split(',');
				project["hioverlays"]=[];
				for(var k=0; k<geomkeys.length; k++){
					var geomkey = '_'+geomkeys[k];

					//handle points differently
					if(overlaypoints[geomkey]["geotype"]==1 || overlaypoints[geomkey]["geotype"]==4){
						var img = (project["mode"]==2)?"images/grnpin2.png":"images/redpin2.png";
						for (var i=0;i<project["overlays"].length;i++){	//add back original overlays
							project["overlays"][i].setImage(img);
						}
						continue;
					}else{
						for (var i=0;i<project["overlays"].length;i++){
							pgistmap.map.removeOverlay(project["overlays"][i]);
						}
					}
					
					project["hioverlays"] = project["hioverlays"].concat(pgistmap.createOverlays(overlaypoints[geomkey]["coords"], 
						overlaypoints[geomkey]["geotype"], "#FFFFCC", 8, 0.9, "NONE"));
					
					pgistmap.scaleToCoords(overlaypoints[geomkey]["coords"], true);
				}
				
				for (var i=0;i<project["hioverlays"].length;i++){//add highlight overlays
					pgistmap.map.addOverlay(project["hioverlays"][i]);
                    
				}
                createListener(project.id, project["hioverlays"]);
                
				for (var i=0;i<project["overlays"].length;i++){	//add back original overlays
					pgistmap.map.addOverlay(project["overlays"][i]);
				}				
				
			}
			function unHighlightProject(projectaltid){
				var project = getProjectById(projectaltid);
				if(project==null)return;
				
				if(project["overlays"] == null)return;
				var icon = (project["mode"]==2)?pgistmap.transiticon:pgistmap.roadicon;
				for (var i=0;i<project["overlays"].length;i++){	//add back original overlays
					if( project["overlays"][i].getIcon ){
						pgistmap.map.removeOverlay(project["overlays"][i]);
						project["overlays"][i] = new GMarker(project["overlays"][i].getPoint(), icon);
						pgistmap.map.addOverlay(project["overlays"][i]);
					}
				}

				var transcolor = (project["mode"]==0)?"#FF0000":"#0bc00f";
				if(project["fpids"] == "") return;
				var geomkey = '_'+project["fpids"];
                if(!project["hioverlays"])return;
				for (var i=0;i<project["hioverlays"].length;i++){
					pgistmap.map.removeOverlay(project["hioverlays"][i]);
				}
			}
			function getProjectById(id){
				for(var i=0;i<prjaltlist.length;i++){
					if(prjaltlist[i]['id'] == id)return prjaltlist[i];
					if(prjaltlist[i]['id'] == (id+""))return prjaltlist[i];
				}
				return null;
			}
			function clearMemory(){
				prjaltlist = null;
				overlaypoints = null;
				pgistmap=null;
				GUnload();
			}
			
			function getYCoord(el) {
				var y=0;
				while(el){
					y+=el.offsetTop;
					el=el.offsetParent;
				}
				return y;
			}
		function adjustMapPosition() {
				mapPositionBottom = getYCoord($('themap')) + $('themap').clientHeight;
				fundingPositionBottom = getYCoord($('fundingTable')) + $('fundingTable').clientHeight;
				leftPositionTop = getYCoord($('left'));
				mapPositionTop = getYCoord($('themap'));
				
				if(document.body.scrollTop < leftPositionTop){
					$('themap').style.top = leftPositionTop; 
				}
				else{ 
					newMapPosition = mapPositionBottom + 5;
					if (newMapPosition >= fundingPositionBottom)
					{
						if (document.body.scrollTop < mapPositionTop)
						{
							$('themap').style.top = (document.body.scrollTop + 5) + 'px';
						}else{}
					}
					else{$('themap').style.top = (document.body.scrollTop + 5) + 'px';}
				}
				clearBottom();
			}
			
		function clearBottom(){ // Move the map so that it's not below the funding table
			if(mapPositionBottom >= fundingPositionBottom){
					$('themap').style.top = (fundingPositionBottom - 629) + 'px';
				}
		}

        function updateMapSum(){
        	var theSum = $('sum').firstChild.nodeValue;
        	$('mapSum').innerHTML = theSum;
        	var balanceBG = $('balance').style.backgroundColor;
        	$('mapSumContainer').style.backgroundColor = balanceBG;
        }			
            


	/* *************** END MAPPING FUNCTIONS *************** */
</script>
</head>

<body onresize="adjustMapPosition();" onscroll="adjustMapPosition();" onload="load()" onunload="clearMemory();">
	<div id="header">
  <!-- Start Global Headers  -->
	  <wf:nav />
	  <wf:subNav />
  <!-- End Global Headers -->
	
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
		<!-- begin "overview and instructions" area -->
		<div id="overview" class="box2">
			<h3>Overview and instructions</h3>
				<ul>Create your own transportation package.
					<li>Select projects and funding options to include in your package.</li>
					<li>Be sure to provide enough funding to pay for your projects!</li>
					<li>Click "Help me create a package" if you’d like help selecting projects and funding options which match your preferences.</li>
				</ul>
			<p>You can only submit one package.</p>
			<p><a href="#" onclick="Effect.toggle('hiddenRM','blind',{duration:0});">Read more about this step</a></p>
			<p id="hiddenRM" style="display:none">The goal of the <em>LIT Challenge</em> is for participants to develop a transportation package to recommend to regional decision makers. <br/>Once all participants have submitted their own packages, new packages will be generated through a statistical process. These new packages will represent the diversity of the different packages submitted by individual participants. In Step 4 we will evaluate these new packages and decide whether there is a significant majority of support for a single package. </p>
		</div> <!-- end overview -->
		
		<!-- begin Object -->
		<div id="object">
			<!-- begin NewTable-->
			<div id="newTable">
				<div id="left" class="floatLeft">
					<div id="yourSummary" class="summary">
						<h3 class="HeaderColor">Your package summary</h3>
						<!-- summary goes here -->
					</div> <!-- end yoursummary -->
					<div class="clearBoth"></div>
					<br />
					<h3 id="pkgDesc" class="headerColor">${(package.description == null) ? "Create Your Package" : package.description}</h3>
					<c:choose>
						<c:when test="${userPkg.id != null}">
  							<input type="button" class="helpMeButton" onClick="new Effect.toggle('helpMe', 'blind', {duration:0.3})" value="Help me create a package" />
						</c:when>
						<c:otherwise>
							[ <small><a href="javascript:new Effect.toggle('editDesc','blind',{duration:0.2});void(0);">Edit Package Description</a> ]</small>
							<div id="editDesc" style="display:none" class="box12">
								<h3>Editing Description</h3>
								<form action="javascript:editPackageDescription();">
									<input type="text" id="txtPkgDesc" style="width:250px" value="${package.description}" />
									<input type="submit" value="Edit Description!" />
								</form>
							</div>
						</c:otherwise>
					</c:choose>

					<div id="helpMe" style="display:none;">
						<p>Using the information you provided during registration, we can put together
							a package for you automatically.  Enter the amount of money that you would be willing to pay annually to fund a package. Then click the button below. </p>
						<form action="javascript:createMyPackage();">
							<h4>Cost per year that you are willing to pay</h4>
			
              <div class="floatRight"> <span style="font-size:1.3em;">$<input type="text" size="3" id="mylimit" /></span></div>
							<div class="clearBoth"></div>
							<div style="margin-top:15px;" class="floatLeft"><input type="submit" class="floatLeft padding5" value="Create a package"/></div>
							<div class="floatRight"><a href="javascript:window.open('tuner.do?usrPkgId=${userPkg.id}&projSuiteId=${projSuiteId}&fundSuiteId=${fundSuiteId}&critSuiteId=${critSuiteId}','helpMe','width=1000,height=500,resizable=yes,scrollbars=yes'); void(0);"> <img src="images/tuneup.gif">More tool options</a></div>
						</form>
					</div><!--end help me-->
					<div class="clearBoth"></div>

					<!-- begin collapsible list of projects -->
          <p> </p>
          <h3 class="headerColor" style="margin-bottom:10px">Select projects to include in your package</h3>
					<table cellpadding=0 cellspacing=0 id="projectsTable">
						<tr class="tableHeading">
							<th colspan="2" class="first">Proposed improvement projects</th>
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
										<td  colspan="3" class="fundingSourceItem">${projectRef.project.name} Options</td>
									</tr>
									<!-- end PROJECT -->
									<tr class="objectives" id="objective${projectRef.id}">
										<td colspan="3">
											<table>
												<c:set var="doNothing"value="true"/>
												<c:forEach var="altRef" items="${projectRef.altRefs}" varStatus="loop">
													<script type="text/javascript" charset="utf-8">
													altfpids = "${altRef.alternative.fpids}";
                                                    altIdLookUp["${altRef.id}"] = ${altRef.alternative.id};
													if(altfpids.length > 0) fpidlist += "," + altfpids;
													prjaltlist.push({"name":"${altRef.alternative.name}", 
															"id":"${altRef.alternative.id}",
															"cost":"${altRef.alternative.cost}", 
															"mode":"${altRef.alternative.project.transMode}",
															"fpids":"${altRef.alternative.fpids}",
															<c:choose>
																<c:when test="${userPkg != null}">
																	"selected":${(pg:containsProjAltRef(userPkg.projAltRefs,altRef.id)) ? "true" : "false"}
																</c:when>
																<c:otherwise>
																	"selected":${(pg:containsProjAltRef(package.projAltRefs,altRef.id)) ? "true" : "false"}
																</c:otherwise>
															</c:choose>
															
															});
													</script>
													<tr>
														<td>
															<label>
															<c:choose>
																<c:when test="${projectRef.project.inclusive}">
																	<c:choose>
																		<c:when test="${userPkg != null}">
																			<input type="radio" ${(pg:containsProjAltRef(userPkg.projAltRefs,altRef.id)) ? "checked='CHECKED'" : ""} name="project-${projectRef.project.id}" id="alt-${altRef.id}" onclick="clearSelectionThenDefine('${projectRef.project.id}', 'project');" />
																		</c:when>
																		<c:otherwise>
																			<input type="radio" ${(pg:containsProjAltRef(package.projAltRefs,altRef.id)) ? "checked='CHECKED'" : ""} name="project-${projectRef.project.id}" id="alt-${altRef.id}" onclick="clearSelectionThenDefine('${projectRef.project.id}', 'project');" />
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<c:choose>
																		<c:when test="${userPkg != null}">
																			<input type="checkbox" ${(pg:containsProjAltRef(userPkg.projAltRefs,altRef.id)) ? "checked='CHECKED'" : ""} name="proj-${projectRef.project.id}" onclick="setProjectToPkg('${altRef.id}', this.checked, '${altRef.alternative.id}');" />
																		</c:when>
																		<c:otherwise>
																			<input type="checkbox" ${(pg:containsProjAltRef(package.projAltRefs,altRef.id)) ? "checked='CHECKED'" : ""} name="proj-${projectRef.project.id}" onclick="setProjectToPkg('${altRef.id}', this.checked,'${altRef.alternative.id}');" />
																		</c:otherwise>
																	</c:choose>
																</c:otherwise>
															</c:choose>
															${altRef.alternative.name}</label>
														</td>
														<td class="cost">
															$<fmt:formatNumber maxFractionDigits="0" value="${altRef.alternative.cost/1000000}" /> million</td>
													</tr>
													<c:if test="${pg:contains(userPkg.projAltRefs,altRef) && userPkg != null}">
														<c:set var="doNothing"value="false"/>
													</c:if>
												</c:forEach>
												<c:if test="${projectRef.project.inclusive}">
													<tr>
														<td>
															<label>
															<input type="radio" ${(doNothing) ? "checked" : ""}  onclick="clearSelectionThenDefine('${projectRef.project.id}', 'project');" name="project-${projectRef.project.id}"  />
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
					
					<br />
					
					<!-- end collapsible project list -->
					<h3 class="headerColor" style="margin-bottom:10px">Decide how to pay for your package</h3>
					<table cellpadding="0" cellspacing="0" id="fundingTable">
						<tr class="tableHeading">
							<th class="first">Funding Source</th>
							<th>Money Raised</th>
							<th class="thcol2">Cost to the avg. taxpayer</th>
							<c:if test="${userPkg != null}">
								<th>Cost to you</th>
							</c:if>
						</tr>
						<!-- begin FUNDING source -->
						<c:forEach var="fundingRef" items="${fundingRefs}" varStatus="loop1">
							<tr class="fundingType">
								<td class="fundingSourceItem">${fundingRef.source.name}</td>
								<td colspan="3"></td>
							</tr>
							<!-- end FUNDING source -->
							<!-- begin OPTIONS -->
							<c:set var="doNothing"value="true"/>
							<c:forEach var="altRef" items="${fundingRef.altRefs}" varStatus="loop2">
								<tr>
                                
                                
									<td class="fundingSourceItem">
										<label>
										<c:choose>
											<c:when test="${userPkg !=null}">
												<input type="radio" ${(pg:containsFundAltRef(userPkg.fundAltRefs,altRef.id)) ? "CHECKED" : ""}  name="source-${fundingRef.source.id}" id="alt-${altRef.id}" onclick="clearSelectionThenDefine('${fundingRef.source.id}', 'source');" />
											</c:when>
											<c:otherwise>
												<input type="radio" ${(pg:containsFundAltRef(package.fundAltRefs,altRef.id)) ? "CHECKED" : ""}  name="source-${fundingRef.source.id}" id="alt-${altRef.id}" onclick="clearSelectionThenDefine('${fundingRef.source.id}', 'source');" />
											</c:otherwise>
										</c:choose>

										${altRef.alternative.name}</label>
									</td>
                                    
                                    
									<td class="col2">
										$<fmt:formatNumber maxFractionDigits="0" value="${altRef.alternative.revenue/1000000}" /> million</td>
									<td class="col3">$<fmt:formatNumber type="number">${altRef.alternative.avgCost}</fmt:formatNumber></td>
									
                                    
                                    <c:if test="${userPkg != null}">
										<td class="col4"><!--need formating-->
												$<fmt:formatNumber maxFractionDigits="0" value="${userPkg.personalCost[altRef.alternative.id]}" />
                    </td>
									</c:if>
								</tr>
                
								<c:choose>
									<c:when test="${userPkg != null}">
										<c:if test="${pg:contains(userPkg.fundAltRefs,altRef)}">
											<c:set var="doNothing"value="false"/>
										</c:if>
									</c:when>
									<c:otherwise>
										<c:if test="${pg:contains(package.fundAltRefs,altRef)}">
											<c:set var="doNothing"value="false"/>
										</c:if>
									</c:otherwise>
								</c:choose>
							</c:forEach>

							<tr>
								<td class="fundingSourceItem">
									<label>
									<input type="radio" ${(doNothing) ? "CHECKED" : ""} name="source-${fundingRef.source.id}" onclick="clearSelectionThenDefine('${fundingRef.source.id}', 'source');" />
									None</label>
								</td>
								<td class="cost">&nbsp;</td>
								<td class="cost">&nbsp;</td>
								<c:if test="${userPkg != null}">
								<td class="cost">&nbsp;</td>
								</c:if>
							</tr>
						</c:forEach>
						<!-- end OPTIONS -->
					</table>
			</div><!-- end left -->
			<!-- begin cell containing #right -->
			<div id="right" class="floatRight">
				<!-- begin GOOGLE MAP -->
				<div id="themap" style="position:absolute">
					<!-- load the map here -->
					<div id="mapSumContainer" style="height:25px;width:500px;_width:510px;padding:5px 10px 0px 10px">
						<h3 style="width:250px;float:left;">Balance:</h3>
						<h3 id="mapSum" style="width:250px;float:right;text-align:right;"></h3><div class="clearBoth"></div>
					</div>
				</div>
				<!-- end GOOGLE MAP -->
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
  <!-- Start Global Headers  -->
  <wf:subNav />
  <!-- End Global Headers -->
	<!-- Begin footer -->
	<div id="footer">
		<jsp:include page="/footer.jsp" />
	</div>
	<!-- End footer -->
	<script type="text/javascript" charset="utf-8">
		if (userPkg) {
			getSummary();	
		} else{
			getClusteredSummary();
		}
	</script>
</body>
</html>

