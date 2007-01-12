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
	Description: Form to define which projects should be in the given decsision situation.
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
<title>Define Funding Sources</title>
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
	<h3>Moderator Tools &raquo; Define Funding Sources</h3> 
	<form method="POST" name="publishFunding" action="fundingDefine.do">
		<input type="hidden" name="cctId" value="${cct.id}" /
		<input type="hidden" name="activity" value="save" />
		<h4>All Projects</h4>
		<ul id="projectsList">
			<c:forEach var="source" items="${sources}">
				<li><input type="checkbox" name="sourceId" value="${source.id}"/>${project.name}
					<ul>
						<c:forEach var="alternative" items="${source.alternatives}">
							<li>${alternative.name}</li>
						</c:forEach>
					</ul>
				</li>
			</c:forEach>
		</ul>
		<input type="submit" value="submit">
	</form>
</body>
</html:html>

