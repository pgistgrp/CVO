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
	     Front End: Jordan Isip, Adam Hindman, Issac Yang
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] BareBones JavaScript (Jordan)
		[ ] editProject and editProjectAlt (issac)
		[ ] test with backend contractor code (jordan)
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
		ProjectAgent.getProjectsforMgr({}, {
			callback:function(data){
				if (data.successful){
					$('projectsList').innerHTML = data.source.html; //returns projectMgr_projects.jsp
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
		var transmode = $F('selProjType'); //1 or 2
		ProjectAgent.createProject({name:name,description:description,transmode:transmode}, {
			callback:function(data){
				if (data.successful){
					alert("successful!");
					getProjects();
					$('frmNewProject').reset();
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.createProject( error:" + errorString + exception);
			}
		});
	}
	
	function createProjectAlt(){
		var name = $F('txtAltName');
		var description = $F('txtAltDesc');
		var cost = $F('txtAltCost'); 
		var county = $F('txtCounty');
		var sponsor = $F('txtAltAgency');
		var links = $F('txtAltLinks');
		var statementFor = $F('txtAltFor');
		var statementAgainst = $F('txtAltAgainst');
		var inclusive = $('inclusive').checked; // "true" or "false"

		//alert("id: " + id + " name: " + name + " description: " + description + " cost: " + cost + " sponsor: " + sponsor + " links: " + links + " statementFor: " + statementFor + " statementAgainst: " + statementAgainst + " inclusive: " + inclusive); 
		ProjectAgent.createProjectAlternative({name:name,description:description,cost:cost, sponsor:sponsor, links:links, statementFor:statementFor, statementAgainst:statementAgainst, inclusive: inclusive}, {
			callback:function(data){
				if (data.successful){
					getProjects();
					$('frmNewAlternative').reset();
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.createProjectAlternative( error:" + errorString + exception);
			}
		});
	}
		
	/* *************** Generate a form to add a new project alternative (inline) *************** */
	function prepareCreateProjectAlt(projId){
		var formDivId = 'newAlternativeForm' + projId;
		$(formDivId).innerHTML = $('newAlternativeForm').innerHTML; //insert form
		Element.toggle('newAlternativeForm');
	}	
	
	function prepareEditProject(id){
		//alert("id: " + id); 
		ProjectAgent.getProjectById({id:id}, {
			callback:function(data){
				if (data.successful){
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
		var name = '';
		var description = '';
		var transmode = 1; //1 or 2
		ProjectAgent.({id:id,name:name,description:description,type:type}, {
			callback:function(data){
				if (data.successful){
					alert("successful!");
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
		//alert("id: " + id); 
		ProjectAgent.deleteProject({id:id}, {
			callback:function(data){
				if (data.successful){
					alert("Project " + id + " deleted");
					getProjects();
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
		ProjectAgent.deleteProjectAlternative({id:id}, {
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
	
	////////// END Mapping Functions ////////////	
</script>
<style type="text/css">

</style>
</head>


<body>
	<h3>Moderator Tools &raquo; Manage Projects</h3> 
	<ul id="projectsList">
		<!--load projects here -->
	</ul>
	
	<div id="newProjectForm" style="display: none;">
		<h4>Add a New Project</h4>
		<form id="frmNewProject">
			<label>Project Name:</label>
			<input id="txtProjName" type="text" value="" size="25">
			<br />
			<label>Project Description:</label>
			<input id="txtProjDesc" type="text" value="" size="25">
			<br />
			<label>Type:</label>
			<select id="selProjType">
				<option value="1">Road</option>
				<option value="2">Transit</option>
			</select>
			<br />
			<label><input type="checkbox" id="inclusive" /> The user can only select one option in this group.</label>
			<p><input type="submit" value="submit"></p>
		</form>
	</div>
	
	<div id="newAlternativeForm" style="display: none;">
		<h4>Add a New Project Alterqnative</h4>
		<form id="frmNewAlternative">
			<label>Project Alternative Name:</label>
			<input id="txtAltName" type="text" value="" size="25">
			<br />
			<label>Agency:</label>
			<input id="txtAltAgency" type="text" value="" size="25">
			<br />
			<label>Cost:</label>
			<input id="txtAltCost" type="text" value="" size="25">
			<br />
			<label>County:</label>
			<input id="txtCounty" type="text" value="" size="25">
			<br />
			<label>Short Description:</label>
			<input id="txtAltDesc" type="text" value="" size="25">
			<br />
			<label>Links:</label> <!--this will be converted to a rich text box editor -->
			<input id="txtAltLinks" type="text" value="" size="25">
			<br />
			<label>Statement For:</label>
			<input id="txtAltFor" type="text" value="" size="25">
			<br />
			<label>Statement Against:</label>
			<input id="txtAltAgainst" type="text" value="" size="25">
			<br />

			<p><input type="submit" value="submit"></p>
		</form>
	</div>
	

</body>
</html:html>

