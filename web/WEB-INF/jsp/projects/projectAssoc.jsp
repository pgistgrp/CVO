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
	Page: Define Projects
	Description: Form to associate selected projects to a workflow instance.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman, Issac Yang
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] BareBones JavaScript (Jordan)
		[ ] Test form actions (Jordan)
		[ ] Load Projects into Action (Matt)
#### -->
<html:html> 
<head>
<title>Define Projects</title>
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

<!--Project Specific  Libraries-->
<script type='text/javascript' src='/dwr/interface/ProjectAgent.js'></script>
<script src="scripts/treeul.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8">
	
</script>
<style type="text/css">

</style>
</head>


<body>
	<h3>Moderator Tools &raquo; Define Projects</h3> 
	<p>Select the projects that you would like to use for this expiriment.</p>
	<form method="POST" name="publishProjects" action="projectDefine.do">
		<input type="hidden" name="cctId" value="${cct.id}" /
		<input type="hidden" name="activity" value="save" />
		<h4>All Projects</h4>
		<ul id="projectsList">
			<c:forEach var="project" items="${projects}">
				<li><input type="checkbox" name="projectId" value="${project.id}"/>${project.name}
					<ul>
						<c:forEach var="alternative" items="${project.alternatives}">
							<li>${alternative.name}</li>
						</c:forEach>
					</ul>
				</li>
			</c:forEach>
		</ul>
		<input type="submit" value="submit">
	</form>
	<script type="text/javascript" charset="utf-8">
		initiate("projectsList"); //initiate the treeul script
	</script>
</body>
</html:html>

