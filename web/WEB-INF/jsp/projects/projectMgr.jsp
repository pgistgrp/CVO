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
		[x] test with backend contractor code (jordan)
	Issues:
		[ ] Fix add/edit alts (Jordan)
		[ ] EditProject inclusive not saving? (Matt)
#### -->
<html:html> 
<head>
<title>Manage Projects</title>
<!-- Site Wide JavaScript -->
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->

<!--Criteria Specific  Libraries-->
<script type='text/javascript' src='/dwr/interface/ProjectAgent.js'></script>

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
		var name = $F('txtAltName');
		var description = $F('txtAltDesc');
		var cost = $F('txtAltCost'); 
		var county = $F('txtCounty');
		var sponsor = $F('txtAltAgency');
		var links = $F('txtAltLinks');
		var statementFor = $F('txtAltFor');
		var statementAgainst = $F('txtAltAgainst');

		//alert("id: " + id + " name: " + name + " description: " + description + " cost: " + cost + " sponsor: " + sponsor + " links: " + links + " statementFor: " + statementFor + " statementAgainst: " + statementAgainst); 
		ProjectAgent.createProjectAlt({id:id, name:name,description:description,cost:cost, sponsor:sponsor, links:links, statementFor:statementFor, statementAgainst:statementAgainst}, {
			callback:function(data, id){
				if (data.successful){
					getProjects();
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
	
	function prepareProjectAlt(id){
		(id) ? getProjectAltById(id) : renderProjectAltForm();
		formId = (id) ? id : ""
		Element.toggle('projectAltForm'+ formId);
	}
	
	
	function renderProjectAltForm(alt){
		//using ternery operators so it won't complain about the values when creating a new project :)
		alert(alt);
		
		f = '<label>Project Alternative Name:</label>\
			<input id="txtAltName" type="text" value="" size="25"><br />\
			<label>Agency:</label>\
			<input id="txtAltAgency" type="text" value="" size="25"><br />\
			<label>Cost:</label>\
			<input id="txtAltCost" type="text" value="" size="25"><br />\
			<label>County:</label>\
			<input id="txtCounty" type="text" value="" size="25"><br />\
			<label>Short Description:</label>\
			<input id="txtAltDesc" type="text" value="" size="25"><br />\
			<label>Links:</label>\
			<input id="txtAltLinks" type="text" value="" size="25"><br />\
			<label>Statement For:</label>\
			<input id="txtAltFor" type="text" value="" size="25"><br />\
			<label>Statement Against:</label>\
			<input id="txtAltAgainst" type="text" value="" size="25"><br />\
			<p><input type="button" value="Submit"></p>';
		
			$("frmProject"+id).innerHTML = f;
	}

	
	
	function prepareEditProjectAlt(id){
		//alert("id: " + id); 
		ProjectAgent.getProjectAlternativeById({id:id}, {
			callback:function(data){
				if (data.successful){
					//javascript object "alternative" returned
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.EditProjectAlt( error:" + errorString + exception);
			}
		});
	}
	
	function editProject(id){
		var name = $F('txtProjName'+ id);
		var description = $F('txtProjDesc'+ id);
		var transMode = $F('selProjType'+ id); //1 or 2
		var inclusive = $('inclusive'+ id).checked
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
		var name = '';
		var description = '';
		var cost = 1100.00; 
		var sponsor = '';
		var links = '';
		var statementFor = '';
		var statementAgainst = '';

		//alert("id: " + id + " name: " + name + " description: " + description + " cost: " + cost + " sponsor: " + sponsor + " links: " + links + " statementFor: " + statementFor + " statementAgainst: " + statementAgainst); 
		ProjectAgent.editProjectAlternative({id:id,name:name,description:description,cost:cost, sponsor:sponsor, links:links, statementFor:statementFor, statementAgainst:statementAgainst}, {
			callback:function(data){
				if (data.successful){
					
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.editProjectAlternative( error:" + errorString + exception);
			}
		});
	}
	
	function deleteProject(id){
		ProjectAgent.deleteProject({id:id}, {
			callback:function(data){
				if (data.successful){
					new Effect.Puff("project-" + id, {afterFinish:function(){getProjects();}});
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.deleteProject( error:" + errorString + exception);
			}
		});
	}
	
	function deleteProjectAlt(id){
		//alert("id: " + id); 
		ProjectAgent.deleteProjectAlt({id:id}, {
			callback:function(data){
				if (data.successful){
					alert("Project Alternative " + id + " deleted");
					getProjects();
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.deleteProjectAlternative( error:" + errorString + exception);
			}
		});
	}
	
	////////// START Mapping Functions ////////////
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
	
	/* *************** ISSAC's CODE *************** */
	/*
	function prepareEditProject(id){
		//alert("id: " + id); 
		ProjectAgent.getProjectById({id:id}, {
			callback:function(data){
				if (data.successful){
				var eph='';
				eph+="<div><label>Project Name: </label><input type='text' id='editProjectName"+id+"' value="+data.project.name+"/></div>";
				eph+="<div><label>Project Description:</label><textarea id='editProjectDesc"+id+"'>"+data.project.description+"</textarea></div>";
				eph+="<div><label>Transportation Mode</label><br/>";
				eph+='<select id="editProjectTransmode'+id+'">';
				eph+='<option value="1" ';
				//eph+="<input type='radio' id='editProjectTransmode'"+id+"' value=1 name='editProjectTransmode"+id+"'";
				if(data.project.transMode==1){
				eph+='checked="checked">Road</option>';
				//eph+=" checked='checked'/>Road &nbsp;&nbsp;&nbsp;&nbsp;";
				//eph+="<input type='radio' value=2 name='editProjectTransmode"+id+"'/>Transit</div>";
				}else{
				eph+='>Road</option>';
				eph+='<option value="2">Transit</option>';
				}
				eph+='</select>';
				
				
				eph+="<p><input type='submit' onclick='editProject("+id+");' value='Submit'></p>";
				$('editProject'+id).innerHTML=eph;
					//javascript object "project" returned
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.getProjectById( error:" + errorString + exception);
			}
		});
	}
	
	function prepareEditProjectAlt(id){
		//alert("id: " + id); 
		ProjectAgent.getProjectAlternativeById({id:id}, {
			callback:function(data){
				if (data.successful){
				var epa='';
				//epa+='<div id="editAlternativeForm'+id+'" style="display: none;">';
		epa+='<h4>Edit Project Alternative</h4>';
		//epa+='<form onsubmit="editProjectAlt('+id+'); return false;" id="frmeditAlternative'+id+'">';		
		epa+='<label>Project Alternative Name:</label>';
			epa+='<input id="edtxtAltName'+id+'" type="text" value='+data.alternative.name+' size="25">';

			//epa+='<label>Agency:</label>';
			//epa+='<input id="edtxtAltAgency'+id+'" type="text" value='+data.alternative.agency+' size="25">';
			
			epa+='<label>Cost:</label>';
			epa+='<input id="edtxtAltCost'+id+'" type="text" value='+data.alternative.cost+' size="25">';
			
			epa+='<label>Short Description:</label>';
			epa+='<input id="edtxtAltDesc'+id+'" type="text" value='+data.alternative.shortDesc+' size="25">';//or detailedDesc?
			
			epa+='<label>Sponsor</label>';
			epa+='<input id="edtxtAltSponsor'+id+'" type="text" value='+data.altnerative.sponsor+' size="25">';
			
			epa+='<label>Links:</label>'; //<!--this will be converted to a rich text box editor -->
			epa+='<input id="edtxtAltLinks'+id+'" type="text" value='+data.alternative.links+' size="25">';
			
			epa+='<label>Statement For:</label>';
			epa+='<input id="edtxtAltFor'+id+'" type="text" value='+data.alternative.statementFor+' size="25">';
			
			epa+='<label>Statement Against:</label>';
			epa+='<input id="edtxtAltAgainst'+id+'" type="text" value='+data.alternative.statementAgainst+' size="25">';


			epa+='<p><input type="submit" onclick="editProjectAlt('+id+');" value="Submit"></p>';
		//epa+='</form>';
	//epa+='</div>';
	$('editAlternativeForm'+id).innerHTML=epa;
					//javascript object "alternative" returned
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.getProjectById( error:" + errorString + exception);
			}
		});
	}
	
	function editProject(id){
		var name = $F('editProjectName'+id);
		var description = $F('editProjectDesc'+id)
		var transmode = $F('editProjectTransmode'+id); //1 or 2
		
		ProjectAgent.editProject({id:id,name:name,description:description,transMode:transmode}, {
			callback:function(data){
				if (data.successful){
					alert("successful!");
					Effect.toggle('editProject'+id,'blind');
					getProjects();
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
		var name = $F('edtxtAltName'+id);
		var description = $F('edtxtAltDesc'+id);
		var cost = $F('edtxtAltCost'+id);//1100.00; 
		var sponsor = $F('edtxtAltSponsor'+id);
		var links = $F('edtxtAltLinks'+id);
		var statementFor = $F('edtxtAltFor'+id);
		var statementAgainst = $F('edtxtAltAgainst'+id);

		//alert("id: " + id + " name: " + name + " description: " + description + " cost: " + cost + " sponsor: " + sponsor + " links: " + links + " statementFor: " + statementFor + " statementAgainst: " + statementAgainst); 
		ProjectAgent.editProjectAlt({id:id,name:name,description:description,cost:cost, links:links, sponsor:sponsor,  statementFor:statementFor, statementAgainst:statementAgainst}, {
			callback:function(data){
				if (data.successful){
					alert("successful!");
					Effect.toggle('editAlternativeForm'+id,'blind');
					getProjects();
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.editProjectAlt( error:" + errorString + exception);
			}
		});
	}
	
	*/
	////////// END Mapping Functions ////////////	
</script>
<style type="text/css">

</style>
</head>


<body>
	<h3><a href="main.do">Moderator Tools</a> &raquo; Manage Projects</h3> 
	<ul id="projectsList">
		<!--load projects here -->
	</ul>
		
	<script type="text/javascript" charset="utf-8">
		getProjects();
	</script>

</body>
</html:html>

