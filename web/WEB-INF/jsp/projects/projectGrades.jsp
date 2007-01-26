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
		[x] Javascript (Jordan)

#### -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8">
		<title>Grade Projects</title>
		<style type="text/css" media="screen">
			@import "styles/lit.css";
		</style>
		<script type="text/javascript" charseut="utf-8">
			function setGrading(altId, critId, objId, value){
				alert("altId: " + altId + " critId: " + critId + " objId: " + objId +" value: " +value ); 
				ProjectAgent.setGrading({altId:altId,critId:critId,objId:objId,value:value {
					callback:function(data){
						if (data.successful){
							alert("grade set!")  //testing
							$('critGrade-' + critId).innerHTML = data.grade; //returned grade
							new Effect.Highlight('critGrade-' + critId); //highlight reflecting change
						}else{
							alert(data.reason);
						}
					},
					errorHandler:function(errorString, exception){ 
					alert("ProjectAgent.setGrading( error:" + errorString + exception);
					}
				});
			}
			
		</script>
	</head>
	<body>
		<h1>Grade Projects on Criteria Objectives</h1>
		<form action="projectGrading.do">
			<input type="hidden" name="cctId" value="${cct.id}">
			<input type="hidden" name="activity" value="save">	      
			<c:forEach var="project" items="${projects}" varStatus="loop">
				${project.name}
					<c:forEach var="alternative" items="${project.alternatives}" varStatus="loop">
					${alternative.name}
						<c:forEach var="criterion" items="${alternative.object}" varStatus="loop">
							<p>Name: ${criterion.name}</p>
							<p>Description: ${criterion.description}</p>
							<p>Grade: <b id="critGrade-${criterion.id}">${criterion.grade}</b></p>
							<p>Objectives (${fn:length(criterion.objectives)}):</p>		
							<ul>
								<c:forEach var="objective" items="${criterion.objectives}" varStatus="loop">
									<li>${objective.name} - Grade: 
										<select id="objGrade-${objective.id}" onchange="setVoting(${alternative.id},${criterion.id}, ${objective.id}, this.value);">
											<option>3</option>
											<option>2</option>
											<option>1</option>
											<option>0</option>
											<option>-1</option>
											<option>-2</option>
											<option>-3</option>
										</select>
									</li>	
								</c:forEach>
							</ul>
						</c:forEach>
					</c:forEach>
			</c:forEach>
		</form>
	</body>
</html>

