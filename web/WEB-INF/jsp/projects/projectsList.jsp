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
	Page: Projects List
	Description: List All projects from all instances.  This page is to manage the list of projects and select
				 projects to create a new instance.
	Author(s): Jordan Isip, Adam Hindman, Issac Yang
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


// END Global Variables
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

