<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>

<!--####
	Project: Let's Improve Transportation!
	Page: Project Grading
	Description: This page is to grade projects on criteria in the given decision situation.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman, Issac Yang
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] Todo1

#### -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8">
		<title>index</title>
		<style type="text/css" media="screen">
			@import "styles/lit.css";
		</style>
		<script type="text/javascript" charset="utf-8">
			function setGrading(altId, value){
				//alert("altId: " + altId + " critId: " + critId + " value: " +value ); 
				ProjectAgent.setGrading({altId:altId,critId:critId,value:value {
					callback:function(data){
						if (data.successful){
							alert("grade set!")  //testing
						}else{
							alert(data.reason);
						}
					},
					errorHandler:function(errorString, exception){ 
					alert("ProjectAgent.setGrading( error:" + errorString + exception);
					}
				});
			}
			
			/*
			*  GET:
		      projectGrading.do?cctId=1234
		    * POST:
		      <form action="projectGrading.do">
		      <input type="hidden" name="cctId" value="1234">
		      <input type="hidden" name="activity" value="save">
		      </form>
		    
			*/
		</script>
	</head>
	<body>
		<h1>Planning Factor Grades for ${project.name}</h1>
		<c:forEach var="project" items="${projects}" varStatus="loop">
			${project.name}
				<c:forEach var="alternative" items="${project.alternatives}" varStatus="loop">
				${alternative.name}
					<c:forEach var="criterion" items="${alternative.object}" varStatus="loop">
						<hr>
							<p>Name: ${criterion.name}</p>
							<p>Description: ${criterion.description}</p>
							<p>Grade: ${criterion.grade}</p>
							<p>Objectives (${fn:length(criterion.objectives)}):</p>		
							<ul>
								<c:forEach var="objective" items="${criterion.objectives}" varStatus="loop">
									<li>${objective.name}</li>	
								</c:forEach>
							</ul>
						<hr>
						<br />
					</c:forEach>
				</c:forEach>
		</c:forEach>

	</body>
</html>

