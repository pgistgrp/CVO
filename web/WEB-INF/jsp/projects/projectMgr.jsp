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
		[ ] Add mapping tool (Guirong)
		[x] Fix add/edit alts (Jordan)
		[x] Add county to project alt (Zhong)
		[x] EditProject inclusive not saving? (Matt)
		[x] Fix county and inclusive on forms (Jordan)
		[x] Sort projects and project alts by name (Matt)
		[x] getProjectAltByID() (Matt)
		[x] Alts A-Z (Matt)
		[ ] Short description and detailed description for project alts (Matt)
		[ ] Add descriptions to create/edit alt form(Jordan)
#### -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml"> 
<head>
<title>Manage Projects</title>
<!-- Site Wide JavaScript -->
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<!-- mapping JavaScript -->
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAADmWGO07Q7ZeAHCvFNooqIxTwM0brOpm-All5BF6PoaKBxRWWERTgXzfGnh96tes2zXXrBXrWwWigIQ"
      type="text/javascript"></script>
<script src="scripts/pgistmap2.js"></script>
<!-- End of mapping JavaScript -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->

<!--Project Specific  Libraries-->
<script type='text/javascript' src='/dwr/interface/ProjectAgent.js'></script>
<style type="text/css" media="screen">
	li{margin: 10px 0; list-style: none;}
	.project{font-size: 1.3em;}
</style>
<style type="text/css">
    v\:* {
      behavior:url(#default#VML);
    }
</style>
<script>
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
		var description = $F('txtAltDesc' + id);
		var cost = $F('txtAltCost' + id); 
		var county = $F('txtAltCounty'+ id);
		var sponsor = $F('txtAltAgency'+ id);
		var shortDesc = $F('txtAltDesc' + id);
		var links = $F('txtAltLinks' + id);
		var statementFor = $F('txtAltFor'+ id);
		var statementAgainst = $F('txtAltAgainst'+ id);

		//alert("id: " + id + " name: " + name + " description: " + description + " cost: " + cost + " sponsor: " + sponsor + " links: " + links + " statementFor: " + statementFor + " statementAgainst: " + statementAgainst + " county: " + county); 
		ProjectAgent.createProjectAlt({id:id, name:name,description:description,cost:cost, sponsor:sponsor, links:links, statementFor:statementFor, statementAgainst:statementAgainst, county:county}, {
			callback:function(data, id){
				if (data.successful){
					getProjects();
					setTimeout(function() {new Effect.Highlight('alt-'+ data.id, {duration:5});}, 100);
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
		
		f = '<label>Project Name:</label>\
			<input id="txtProjName' + id +'" type="text" value="'+name+'" size="25"><br />\
			<label>Project Description:</label><br />\
			<input id="txtProjDesc' + id +'" type="text" value="'+description+'" size="25"><br />\
			<label>Type:</label>\
			<select id="selProjType' + id +'">';
			for(i=1; i<transModes.length; i++){
				modeSelected = (i==transMode) ? "SELECTED" : "";
				f += '<option value="'+ i +'" '+ modeSelected +'>'+transModes[i]+'</option>';
			}
			
		f +='</select><br />\
			<label><input type="checkbox" id="inclusive' + id +'" '+inclusiveChecked+' /> The user can only select one option in this group.</label>\
			<p><input type="submit" value="Submit"></p>';
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
					renderProjectAltForm(data.alternative)
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
		description = (alt.detailedDesc) ? alt.detailedDesc : "";
		links = (alt.links) ? alt.links : "";
		statementFor = (alt.statementFor) ? alt.statementFor : "";
		statementAgainst = (alt.statementAgainst) ? alt.statementAgainst : "";
		
		f = '<h4>Editing Project Alternative</h4>\
			<label>Project Alternative Name:</label>\
			<input id="txtAltName'+ altId +'" type="text" value="'+ name +'" size="25"><br />\
			<label>Agency:</label>\
			<input id="txtAltAgency'+ altId +'" type="text" value="'+ sponsor +'" size="25"><br />\
			<label>Cost:</label>\
			<input id="txtAltCost'+ altId +'" type="text" value="'+ cost +'" size="25"><br />\
			<label>County:</label>\
			<input id="txtAltCounty'+ altId +'" type="text" value="'+ county +'" size="25"><br />\
			<label>Short Description:</label>\
			<input id="txtAltDesc'+ altId +'" type="text" value="'+ description +'" size="25"><br />\
			<label>Links:</label>\
			<input id="txtAltLinks'+ altId +'" type="text" value="'+ links +'" size="25"><br />\
			<label>Statement For:</label>\
			<input id="txtAltFor'+ altId +'" type="text" value="'+ statementFor +'" size="25"><br />\
			<label>Statement Against:</label>\
			<input id="txtAltAgainst'+ altId +'" type="text" value="'+ statementAgainst +'" size="25"><br />\
			<p><input type="submit" value="Submit"></p>';
		

		$("frmProjectAlt"+altId).innerHTML = f;

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
		var description = $F('txtAltDesc' + id);
		var links = $F('txtAltLinks' + id);
		var statementFor = $F('txtAltFor'+ id);
		var statementAgainst = $F('txtAltAgainst'+ id);

		//{id: 3545, name:"This is from DWR EDIT", description: "This is a description", cost: 60.00, links: "http://www.google.com", sponsor: "PSRC", statementFor: "COOL", statementAgainst: "BAD"}
		//alert("id: " + id + " name: " + name + " description: " + description + " cost: " + cost + " sponsor: " + sponsor + " links: " + links + " statementFor: " + statementFor + " statementAgainst: " + statementAgainst + " county: " + county); 
		ProjectAgent.editProjectAlt({id:id,name:name,description:description,cost:cost,sponsor:sponsor,links:links,statementFor:statementFor,statementAgainst:statementAgainst, county:county},[], {
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
			//alert("new map");
			mapeditor = new PGISTMapEditor('alternativeMap'+id, 600, 400);
		}else{
			mapeditor.changeToContainer('alternativeMap'+id);
			mapeditor.clearInput();
		}
			
	}
	
	/* *************** Saves the coordinates of the project alternative *************** */
	function saveFootprint(altId, shape){
		ProjectAgent.saveFootprint({altId:altId, shape:shape}, {
			callback:function(data){
				if (data.successful){
					alert("footprint saved")
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
					alert("successful"); //coordinates - 3d array returned
				}else{
					alert(data.reason);
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
<style type="text/css">

</style>
</head>


<body>
	<p><a href="main.do">Back to Moderator Control Panel</a></p>
	<h1>Manage Projects</h1>
	<h3>Manage all projects and their associated alternatives.</h3>
	
	<ul id="projectsList">
		<!--load projects here -->
	</ul>
		
	<script type="text/javascript" charset="utf-8">
		getProjects();
	</script>

</body>
</html>

