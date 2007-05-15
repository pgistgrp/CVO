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
	Page: Projects Manager
	Description: CRUD Events on All Projects
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Zhong Wang, Matt Paulin, Guirong Zhou,
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] BareBones JavaScript (Jordan)
		[x] test with backend contractor code (Jordan)
		[x] Add mapping tool (Guirong)
		[x] Fix add/edit alts (Jordan)
		[x] Add county to project alt (Zhong)
		[x] EditProject inclusive not saving? (Matt)
		[x] Fix county and inclusive on forms (Jordan)
		[x] Sort projects and project alts by name (Matt)
		[x] getProjectAltByID() (Matt)
		[x] Alts A-Z (Matt)
		[x] Short description and detailed description for project alts (Matt)
		[x] Add descriptions to create/edit alt form(Jordan)
		[x] JavaScript Tree menu (Jordan)
		[x] Persist map edits (Guirong)
		[x] Links and detailed description as a rich text box editor (Jordan)
		[ ] Play with tree script to only link the first link (Jordan)
		[x] Polish it up a little (Adam)
#### -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml"> 
<head>
<title>Manage Projects</title>
<!-- Site Wide JavaScript -->
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<!-- TinyMCE libraries -->


<!-- mapping JavaScript -->
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAADmWGO07Q7ZeAHCvFNooqIxSrR7p1nyD8TH138ULjTOjQOW5fjxTrHGj2RyW-631yBK63wnZBIuC6BA"
      type="text/javascript"></script>
<script src="scripts/pgistmap2.js"></script>
<!-- End of mapping JavaScript -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->

<!--Project Specific  Libraries-->
<script type='text/javascript' src='/dwr/interface/ProjectAgent.js'></script>
<script src="scripts/simpletreemenu.js" type="text/javascript"></script>

<script language="javascript" type="text/javascript" src="/scripts/tinymce/jscripts/tiny_mce/tiny_mce.js"></script>
<style type="text/css" media="screen">
	@import "styles/simpletree.css";
body{font-size:11pt;font-family:arial,sans-serif;}
</style>
<style type="text/css" media="screen">
	li{margin: 10px 0; list-style: none;border-bottom:2px solid #ccc;padding-bottom:10px;width:850px;}
	.project{font-size: 1.3em;}
	li ul li span:hover {background:#E4F1F5;}
	li ul li{width:720px;padding:3px;border:0px;}
</style>
<style type="text/css">
    v\:* {
      behavior:url(#default#VML);
    }
</style>


<script type="text/javascript" src="scripts/tinymce/jscripts/tiny_mce/tiny_mce.js"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script type="text/javascript">

		tinyMCE.init({
		theme : "advanced",
		theme_advanced_buttons1 : "bold, italic, bullist, numlist,undo, redo,link",
		theme_advanced_buttons2 : "",
		theme_advanced_buttons3 : "",
		content_css : "/scripts/tinymce/jscripts/tiny_mce/themes/simple/css/bigmce.css",
		extended_valid_elements : "blockquote[style='']",
		mode : "textareas",
		height: "80",
		width: "400"
		});

</script>


<script type="text/javascript">

// Global Variables
	function getProjects(){
		ProjectAgent.getProjectsForMgr({}, {
			callback:function(data){
				if(data.successful){
					$('projectsList').innerHTML = data.html; //returns projectMgr_projects.jsp
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.getProjects( error:" + errorString + exception);
			}
		});
	}
	
	function createProject(){
		var name = $F('txtProjName');
		var description = $F('txtProjDesc');
		var transMode = $F('selProjType'); //1 or 2
		var inclusive = $('inclusive').checked; // "true" or "false"
		//alert("name: " + name + " description: " + description + " transMode: " + transMode + " inclusive: " + inclusive)
		ProjectAgent.createProject({name:name,description:description,transMode:transMode, inclusive:inclusive}, {
			callback:function(data){
				if (data.successful){
					getProjects();
					setTimeout(function() {new Effect.Highlight('project-'+ data.id, {duration:5});}, 100);
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.createProject( error:" + errorString + exception);
			}
		});
	}
	
	function createProjectAlt(id){
		var name = $F('txtAltName'+ id);
		var cost = $F('txtAltCost' + id); 
		var county = $F('txtAltCounty'+ id);
		var sponsor = $F('txtAltAgency'+ id);
		var shortDescription = $F('txtAltDesc' + id);
		var detailedDescription = tinyMCE.getContent("txtAltDetailedDesc"+ id);
		var links = tinyMCE.getContent("txtAltLinks"+ id);
		var statementFor = tinyMCE.getContent("txtAltFor"+ id);
		var statementAgainst = tinyMCE.getContent("txtAltAgainst"+ id);
		
		//alert("id: " + id + " name: " + name + " shortDescription: " + shortDescription + " detailedDescription: "+ detailedDescription+" cost: " + cost + " sponsor: " + sponsor + " links: " + links + " statementFor: " + statementFor + " statementAgainst: " + statementAgainst + " county: " + county); 
		ProjectAgent.createProjectAlt({id:id, name:name,shortDescription:shortDescription,detailedDescription:detailedDescription,cost:cost, sponsor:sponsor, links:links, statementFor:statementFor, statementAgainst:statementAgainst, county:county}, {
			callback:function(data, id){
				if (data.successful){
					getProjects();
					//setTimeout(function() {new Effect.Highlight('alt-'+ data.id, {duration:5});}, 100);
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.createProjectAlt( error:" + errorString + exception);
			}
		});
	}
		
	/* *************** Generate a form to create and edit an existing project alternative (inline) *************** */	
	function renderProjectForm(project){
		//using ternery operators so it won't complain about the values when creating a new project :)
		id = (project) ? project.id : "";
		name = (project) ? project.name : "";
		description = (project) ? project.description : "";
		inclusive = (project) ? project.inclusive : "";
		inclusiveChecked = (inclusive == true) ? "CHECKED" : "";
		transMode = (project) ? project.transMode : "";
		transModes = ["null","road", "transit"];
		
		f = '<div style="width:200px;float:left;margin-bottom:5px;"><label>Project Name:</label></div>\
			<div style="width:400px;float:left;margin-bottom:5px;"><input id="txtProjName' + id +'" type="text" value="'+name+'" size="40"></div>\
			<div style="clear:both"></div>\
			<div style="width:200px;float:left;margin-bottom:5px;"><label>Project Description:</label></div>\
			<div style="width:400px;float:left;margin-bottom:5px;"><input id="txtProjDesc' + id +'" type="text" value="'+description+'" size="25"></div><br />\
			<div style="clear:both"></div>\
			<div style="width:200px;float:left;margin-bottom:5px;"><label>Type:</label></div>\
			<div style="width:400px;float:left;margin-bottom:5px;"><select id="selProjType' + id +'">';
			for(i=1; i<transModes.length; i++){
				modeSelected = (i==transMode) ? "SELECTED" : "";
				f += '<option value="'+ i +'" '+ modeSelected +'>'+transModes[i]+'</option>';
			}
			
		f +='</select></div>\
			<div style="clear:both"></div>\
			<div style="float:left;width:200px;margin-bottom:5px;"></div>\
			<div style="float:left;width:400px;margin-bottom:5px;"><label><input type="checkbox" id="inclusive' + id +'" '+inclusiveChecked+' /> Options for this project are mutually exclusive.</label></div>\
			<p><input type="submit" style="padding:5px;" value="save"></p>';
		$("frmProject"+id).innerHTML = f;
		
	}
	
	function getProjectById(id){
		ProjectAgent.getProjectById({id:id}, {
			callback:function(data){
				//if (data.successful){
					//alert(data.project);
					renderProjectForm(data.project)
				//}else{
				//	alert(data.reason);
				//}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.getProjectById( error:" + errorString + exception);
			}
		});
	}
	
	function prepareProject(id){
		(id) ? getProjectById(id) : renderProjectForm();
		formId = (id) ? id : ""
		Element.toggle('projectForm'+ formId);
	}
	
	function getProjectAltById(id){
		ProjectAgent.getProjectAltById({id:id}, {
			callback:function(data){
				if (data.successful){
					renderProjectAltForm(data.alternative);
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.getProjectById( error:" + errorString + exception);
			}
		});
	}
	
	
	function prepareProjectAlt(alt, idType){
		(idType=="altId") ? getProjectAltById(alt) : renderProjectAltForm(alt);
		Element.toggle('alternativeForm'+ alt);
	}
	
	
	function renderProjectAltForm(alt){
		//using ternery operators so it won't complain about the values when creating a new alt :)
		altId = (alt.id) ? alt.id : alt;
		name = (alt.name) ? alt.name : "";
		sponsor = (alt.sponsor) ? alt.sponsor : "";
		cost = (alt.cost) ? alt.cost : "";
		county = (alt.county) ? alt.county : "";
		shortDescription = (alt.shortDesc) ? alt.shortDesc : "";
		detailedDescription = (alt.detailedDesc) ? alt.detailedDesc : "";
		links = (alt.links) ? alt.links : "";
		statementFor = (alt.statementFor) ? alt.statementFor : "";
		statementAgainst = (alt.statementAgainst) ? alt.statementAgainst : "";
		
		f = '<h4>Editing Project Alternative</h4>\
				<div style="clear:both">\
				</div>\
			<div style="width:850px;">\
			<div style="float:left;margin-right:20px;">\
				<label>Project Alternative Name:</label><br />\
				<input id="txtAltName'+ altId +'" type="text" value="'+ name +'" size="40"><br />\
				<p><label>Agency:</label><br />\
				<input id="txtAltAgency'+ altId +'" type="text" value="'+ sponsor +'" size="40"></p>\
				<p><label>Cost (in millions):</label><br />\
				<input id="txtAltCost'+ altId +'" type="text" value="'+ cost +'" size="40"></p>\
				<p><label>County:</label><br />\
				<input id="txtAltCounty'+ altId +'" type="text" value="'+ county +'" size="40"></p>\
				<p><label>Short Description:</label><br />\
				<input id="txtAltDesc'+ altId +'" type="text" value="'+ shortDescription +'" size="40"></p>\
				<div style="clear:both">\
				</div>\
			</div>\
			<div style="float:left;border-left:1px solid #000;padding-left:20px;">\
				<label>Detailed Description:</label><br />\
				<textarea cols="40" rows="5" name="txtAltDetailedDesc'+ altId +'" id="txtAltDetailedDesc'+ altId +'">'+detailedDescription +'</textarea><br />\
				<label>Links:</label><br />\
				<textarea cols="40" rows="5" name="txtAltLinks'+ altId +'" id="txtAltLinks'+ altId +'">'+ links +'</textarea><br />\
				<label>Statement For:</label><br />\
				<textarea cols="40" rows="5" name="txtAltFor'+ altId +'" id="txtAltFor'+ altId +'">'+ statementFor +'</textarea><br />\
				<label>Statement Against:</label><br />\
				<textarea cols="40" rows="5" name="txtAltAgainst'+ altId +'" id="txtAltAgainst'+ altId +'">'+ statementAgainst +'</textarea><br />\
				<div style="clear:both">\
				</div>\
			</div>\
							<div style="clear:both">\
				</div>\
			<p><input style="padding:5px;" type="submit" value="save"></p>\
			</div>';

		

		$("frmProjectAlt"+altId).innerHTML = f;

		tinyMCE.idCounter=0;
		tinyMCE.execCommand('mceAddControl',false,'txtAltDetailedDesc' + altId);
		tinyMCE.execCommand('mceAddControl',false,'txtAltLinks' + altId);
		tinyMCE.execCommand('mceAddControl',false,'txtAltFor' + altId);
		tinyMCE.execCommand('mceAddControl',false,'txtAltAgainst' + altId);
	}
	
	function editProject(id){
		var name = $F('txtProjName'+ id);
		var description = $F('txtProjDesc'+ id);
		var transMode = $F('selProjType'+ id); //1 or 2
		var inclusive = $('inclusive'+ id).checked
		//dwr test: {id: 3067, name:"This is from DWR", description: "This is a description", cost: 60.00, links: "http://www.google.com", sponsor: "PSRC", statementFor: "COOL", statementAgainst: "BAD"}
		//alert("ID: "+id+" name: " + name + " description: " + description + " transMode: " + transMode + " inclusive: " + inclusive)
		ProjectAgent.editProject({id:id,name:name,description:description,transMode:transMode,inclusive:inclusive}, {
			callback:function(data){
				if (data.successful){
					getProjects();
					setTimeout(function() {new Effect.Highlight('project-'+ id, {duration:5});}, 100);
					
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.editProject( error:" + errorString + exception);
			}
		});
	}
	
	function editProjectAlt(id){
		var name = $F('txtAltName'+ id);
		var cost = $F('txtAltCost' + id); 
		var county = $F('txtAltCounty'+ id);
		var sponsor = $F('txtAltAgency'+ id);
		var shortDescription = $F('txtAltDesc' + id);
		var detailedDescription = tinyMCE.getContent("txtAltDetailedDesc"+ id);
		var links = tinyMCE.getContent("txtAltLinks"+ id);
		var statementFor = tinyMCE.getContent("txtAltFor"+ id);
		var statementAgainst = tinyMCE.getContent("txtAltAgainst"+ id);

		//{id: 3545, name:"This is from DWR EDIT", description: "This is a description", cost: 60.00, links: "http://www.google.com", sponsor: "PSRC", statementFor: "COOL", statementAgainst: "BAD"}
		//alert("id: " + id + " name: " + name + " description: " + description + " cost: " + cost + " sponsor: " + sponsor + " links: " + links + " statementFor: " + statementFor + " statementAgainst: " + statementAgainst + " county: " + county); 
		ProjectAgent.editProjectAlt({id:id,name:name,shortDescription:shortDescription,detailedDescription:detailedDescription,cost:cost,sponsor:sponsor,links:links,statementFor:statementFor,statementAgainst:statementAgainst, county:county},[], {
			callback:function(data){
				if (data.successful){
					getProjects();
					setTimeout(function() {new Effect.Highlight('alt-'+ id, {duration:5});}, 100);
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.editProjectAlt( error:" + errorString + exception);
			}
		});
	}
	
	function deleteProject(id){
		var destroy = confirm ("Are you sure you want to delete this project? Note: there is no undo.")
		if(destroy){
			ProjectAgent.deleteProject({id:id}, {
				callback:function(data){
					if (data.successful){
						new Effect.Puff("project-" + id);
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("ProjectAgent.deleteProject( error:" + errorString + exception);
				}
			});
		}
	}
	
	function deleteProjectAlt(id){
		var destroy = confirm ("Are you sure you want to delete this project alternative? Note: there is no undo.")
		if(destroy){
			ProjectAgent.deleteProjectAlt({id:id}, {
				callback:function(data){
					if (data.successful){
						new Effect.Puff("alt-" + id, {afterFinish:function(){getProjects();}});
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("ProjectAgent.deleteProjectAlternative( error:" + errorString + exception);
				}
			});
		}
	}
	
	////////// START Mapping Functions ////////////
	var mapeditor= null;
	function mapAlternative(id){
		if(!mapeditor){  //if it's the first time the map is initiated:
			mapeditor = new PGISTMapEditor('alternativeMap'+id, 600, 400, true);
		}else{
			if(mapeditor.targetId != id){ //decide to show or hide the map
				mapeditor.changeToContainer('alternativeMap'+id);
				mapeditor.clearInput();
			}else{
				mapeditor.swithchDisplay();
			}
		}
		mapeditor.targetId = id;
		
		getFootprintsByAltId(id);
	}
	
	/* *************** Saves the coordinates of the project alternative *************** */
	function saveFootprint(altId, shape, coords){
		if(shape != "LINE" && shape != "POINT"){
			ProjectAgent.useFootprint(altId, shape, {
				callback:function(data){
					if (data.successful){
						alert("footprint saved. Alternative id is: " + data.altId);
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("ProjectAgent.saveFootprint( error:" + errorString + exception);
				}
			});
			return;
		}
		ProjectAgent.saveFootprint(altId, shape, coords, {
			callback:function(data){
				if (data.successful){
					alert("footprint saved. Alternative id is: " + data.altId);
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.saveFootprint( error:" + errorString + exception);
			}
		});
	}
	
	/* *************** Get coordinates of a list of footprints *************** */
	function getFootprints(fpids){
		//alert("fpids: " + fpids); 
		ProjectAgent.getFootprints({fpids:fpids}, {
			callback:function(data){
				if (data.successful){
					//alert("successful"); //coordinates - 3d array returned
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.getFootprint( error:" + errorString + exception);
			}
		});
	}
	
	/* *************** Get footprints for a given project alternative id *************** */
	function getFootprintsByAltId(id){
		ProjectAgent.getFootprintsByAltId({altid:id}, {
			callback:function(data){
				if (data.successful){
					for(fpid in data.footprints){
						if(data.footprints[fpid].geotype==0 ||
							data.footprints[fpid].geotype==2 || 
							data.footprints[fpid].geotype==5){ // line
							mapeditor.recoverCoords(data.footprints[fpid].coords);
							mapeditor.editGeomType = "LINE";
							mapeditor.scaleToCoords();
							mapeditor.drawLines();
						}else if(data.footprints[fpid].geotype==1 || 
							data.footprints[fpid].geotype==4){ // point
							mapeditor.recoverCoords(data.footprints[fpid].coords);
							mapeditor.editGeomType = "POINTS";
							mapeditor.scaleToCoords();
							mapeditor.drawPoints();	
						}else{ //polygon
							mapeditor.recoverCoords(data.footprints[fpid].coords);
							mapeditor.editGeomType = "POLYGON";
							mapeditor.scaleToCoords();
							mapeditor.drawPolygons();	
							
						}
					}
					//alert("successful"); //coordinates - 3d array returned
				}else{
					alert("Something wrong happened, reason: " + data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.getFootprint( error:" + errorString + exception);
			}
		});
	}
	
	/* *************** Deletes a given footprint *************** */
	function deleteFootprint(fpid){
		//alert("fpid: " + fpid; 
		ProjectAgent.deleteFootprint({fpid:fpid}, {
			callback:function(data){
				if (data.successful){
					alert("Footprint with ID DELETED!!!") //test
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.deleteFootprint( error:" + errorString + exception);
			}
		});
	}

	////////// END Mapping Functions ////////////	
</script>
<event:pageunload />
</head>


<body onUnload="GUnload()">
	<p><a href="main.do">Back to Moderator Control Panel</a></p>
	<h1>Manage Projects</h1>
	<h3>Manage all projects and their associated alternatives.</h3>
	
	<!--
	<a href="javascript:ddtreemenu.flatten('projectsList', 'expand')">Expand All</a> | 
	<a href="javascript:ddtreemenu.flatten('projectsList', 'contact')">Collapse All</a>
-->
	<div id="projectsList">
		
	</div>
	
		<!--load projects here -->

		
	<script type="text/javascript" charset="utf-8">
		getProjects();
	</script>
	<div style="width:820px;">
	<h3 align="right">Finished managing projects?</h3>
	<!-- this button just redirects - saves are occuring on check. -->
	<p align="right"><input type="button" style="padding:5px;" onClick="location.href='main.do'" value="Finished!"/></p></div>
	
	<script type="text/javascript">
		//ddtreemenu.createTree(treeid, enablepersist, opt_persist_in_days (default is 1))
		//setTimeout(function() {ddtreemenu.createTree("projectsList", false);	ddtreemenu.flatten('projectsList', 'contact');}, 200);
		
	</script>
</body>
</html>

