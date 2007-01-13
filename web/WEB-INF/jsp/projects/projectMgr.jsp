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
		[ ] BareBones JavaScript (Isaac)
		[ ] Test form actions (Isaac)
		
#### -->
<html:html> 
<head>
<title>Manage Criteria</title>
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
					alert(data.html) //returns projectMgr_projects.jsp
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
		var name = '';
		var description = '';
		var transmode = 1; //1 or 2
		ProjectAgent.createProject({id:id,name:name,description:description,type:type}, {
			callback:function(data){
				if (data.successful){
					alert("successful!");
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
		var name = '';
		var description = '';
		var cost = 1100.00; 
		var sponsor = '';
		var links = '';
		var statementFor = '';
		var statementAgainst = '';

		//alert("id: " + id + " name: " + name + " description: " + description + " cost: " + cost + " sponsor: " + sponsor + " links: " + links + " statementFor: " + statementFor + " statementAgainst: " + statementAgainst); 
		ProjectAgent.createProjectAlternative({id:id,name:name,description:description,cost:cost, sponsor:sponsor, links:links, statementFor:statementFor, statementAgainst:statementAgainst}, {
			callback:function(data){
				if (data.successful){
					
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.createProjectAlternative( error:" + errorString + exception);
			}
		});
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
		//alert("param1: " + param1 + " param2: " + param2 + " param3: " + param3 + " param4: " + param4); 
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
					alert("Project " + id + " deleted")
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
					alert("Project Alternative " + id + " deleted")
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
	<form name="publishProjects" action="projectDefine.do">
		<input type="hidden" name="activity" value="save" />
		<h4>All Projects</h4>
		<ul id="projectsList">
			<c:forEach var="project" items="${projects}">
				<li><input type="checkbox" name="projectId" value="${project.id}"/>${project.name} [ <a href="javascript:Effect.toggle('editProject${project.id}','blind');">edit</a> ] [ <html:link action="/projectMgr.do?action=delete" paramId="id" paramName="project" paramProperty="id">delete</html:link> ]
					<ul>
						<li id="editProject${project.id}" style="display: none;"><input name="txtProjectEdit${project.id}" type="text" value="${project.name}" size="25"> <input type="submit" value="submit"></li>
						<li>[ <a href="javascript:addAlternative(${project.id});">Add an Alternative</a> ]</li>
						<c:forEach var="alternative" items="${project.alternatives}">
							<li>${alternative.name} [ <a href="javascript: editAlternative(${alternative.id});">edit</a> ] [ <a href="javascript:deleteAlternative(${alternative.id});">delete</a> ]</li>
						</c:forEach>
					</ul>
				</li>
			</c:forEach>

			<li>[ <a href="javascript:Effect.toggle('newProjectForm', 'blind');">New Project</a> ]
				<div id="newProjectForm" style="display: none;">
					<h4>Create New Project</h4>
					<form>
						<label>Name:</label>
						<input name="txtNewCriterion" type="text" value="" size="25">

						<label>Description:</label>
						<textarea name="txtLowDesc" cols="100" rows="5"></textarea>

						<p><input type="submit" value="submit"></p>
					</form>
				</div>
			</li>
		</ul>
	</form>
</body>
</html:html>

