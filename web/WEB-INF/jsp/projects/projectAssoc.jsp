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
	     Back End: Matt Paulin, Zhong Wang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] BareBones JavaScript (Jordan)
		[x] Add JS to set alts (Jordan)
		[x] Load Projects into Action (Matt)
		[ ] Order Alts A-Z (Matt)
		[x] test setProjectDefine (Matt)
		[ ] How do I check if the alternative is defined or not onLoad? pg:contains
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
<script type="text/javascript" charset="utf-8">
	var suiteId = ${suite.id}; //hardcoded until workflow manager is available

	function checkAltsInProject(projId,checked){
		var alts = document.getElementsByName("projectAlts" + projId);
		for(i=0;i<alts.length;i++){
			alts[i].checked = checked;
			
			//Get the AltID
			start = alts[i].id.indexOf('-') + 1;
			end = alts[i].id.length
			altId = alts[i].id.substring(start,end)
			
			//Inoke AJAX to set the project Alt operation
			setProjectDefine(altId, checked)
		}
	}

	function setProjectDefine(altId,checked){
		operation = (checked) ? "add" : "remove";
		
		alert("suiteId: " + suiteId + " altId: " + altId + " operation: " + operation); 
		ProjectAgent.setProjectDefine({suiteId:suiteId,altId:altId,operation:operation}, {
			callback:function(data){
				if (data.successful){
					alert("alternative operation saved!");
					//add loading indicator if time permits
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.setProjectDefine( error:" + errorString + exception);
			}
		});
	}
</script>
<style type="text/css">
	li{margin: 10px 0; list-style: none;}
	.project{font-size: 1.3em;}
</style>
</head>


<body>
	<p><a href="main.do">Back to Moderator Control Panel</a></p>
	<h1>Define Projects Alternatives</h1>
	<p>Select all project alternatives that you would like to include for this expiriment.  Your selection is saved when you click on the checkbox.</p>
	<form method="POST" name="publishProjects" action="projectDefine.do">
		<input type="hidden" name="cctId" value="${cct.id}" /
		<input type="hidden" name="activity" value="save" />
		<h3>All Projects</h3>
		<ul id="projectsList">
			<c:forEach var="project" items="${projects}">
				<li><span class="project">${project.name}</span> 
					<small>
						<a href="javascript:checkAltsInProject(${project.id}, true)">check all</a> | 
						<a href="javascript:checkAltsInProject(${project.id}, false)">uncheck all</a>
					</small>
					<ul>
						<c:forEach var="alt" items="${project.alternatives}">
							<li>
								<label><input type="checkbox" name="projectAlts${project.id}" id="projectAlt-${alt.id}" 
								<c:if test="${pg:containsRef(suite,project,alt)}">CHECKED</c:if> value="${alt.id}" onClick="setProjectDefine(this.value, this.checked);"/>
								${alt.name}</label>
							</li>
						</c:forEach>
					</ul>
				</li>
			</c:forEach>
		</ul>

	</form>
	<h3>Finished selecting project alternatives?</h3>
	<p>Go back to the <a href="main.do">Back to Moderator Control Panel</a> to publish!</p>
</body>
</html:html>

