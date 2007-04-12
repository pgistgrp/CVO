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
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Matt Paulin, Zhong Wang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] BareBones JavaScript (Jordan)
		[x] Add JS to set alts (Jordan)
		[x] Load Projects into Action (Matt)
		[x] Order Alts A-Z (Matt)
		[x] test setProjectDefine (Matt)
		[x] How do I check if the alternative is defined or not onLoad? pg:contains (Jordan)
		[ ] Loading indicator (Jordan)
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
	var suiteId = ${suite.id};

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
					//alert("alternative operation saved!");
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
	body{font-size:11pt;font-family:arial,sans-serif;width:800px;}
	li{margin: 10px 0; list-style: none;}
	.project{font-size: 1.3em;}
	li ul li:hover {background:#E4F1F5;}
	li ul li{width:600px;padding:5px;}
	span#projRow:hover{background:#BDE2F1;}
	span#projRow{padding:3px;}
</style>
</head>


<body>
	<p><a href="main.do">Back to Moderator Control Panel</a></p>
	<h1>Define Projects for this Experiment</h1>
	<p>Which projects are available for the "Let's Improve Transportation Challenge" experiment? Go to <a href="projectManage.do">manage projects</a> to add/remove projects.</p>
	<form method="POST" name="publishProjects" action="projectDefine.do">
		<input type="hidden" name="cctId" value="${cct.id}" /
		<input type="hidden" name="activity" value="save" />
		<h3>All Projects</h3>
		<ul id="projectsList">
			<c:forEach var="project" items="${projects}">
				<li><span id="projRow"><label><input type="checkbox" style="margin-right:10px;"><span class="project">${project.name}</span></label> 
					<small>
						<a href="javascript:checkAltsInProject(${project.id}, true)">check all</a> | 
						<a href="javascript:checkAltsInProject(${project.id}, false)">uncheck all</a>
					</small></span>
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
	<div style="width:700px">
	<h3 align="right">Finished selecting project alternatives?</h3>
	<!-- this button just redirects - saves are occuring on check. -->
	<p align="right"><input type="button" style="padding:5px;" onClick="location.href='main.do'" value="Finished!"/></p></div>
</body>
</html:html>

