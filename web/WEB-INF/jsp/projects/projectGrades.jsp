<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
		[ ] So the next thing was that in Task3 you are going to need to be working off of the ProjectAltRef, not the project Alternative
		[ ] You can get the ProjectAlternative out of the ProjectAltRef, but the ProjectAltRef will also have all the grading and whatnot
		So anyway, I need to go back to task 3 and look at how this would work exactally, but when you tell me a grade in the ProjectAgent.setGrading, before you were sending me the AltID, now you will need to send me the AltRefId
		3:17
		I think that means that you will have a list of ProjectRef's, in the ProjectGradingPage
		3:18
		Then you will cycle through those pulling the Projects out for the top level nodes, and then use the project alt refs and their corrisponding project alternatives to form the next level
		3:18
		To be honest, this is not solidified at all in my head
		3:19
		So I'm hoping this makes sense to you on some level

#### -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8">
		<title>Grade Projects</title>

		<script type="text/javascript" charseut="utf-8">
			function setGrading(altId, critId, objId, value){
				//alert("altId: " + altId + " critId: " + critId + " objId: " + objId +" value: " +value ); 
				ProjectAgent.setGrading({altId:altId,critId:critId,objId:objId,value:value},{
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
		<h1>Grade Projects on Criteria Objectives ${critSuite}</h1>
		<ul>
		<c:forEach var="projectRef" items="${projSuite.references}" varStatus="loop">
			<li>${projectRef.project.name}<ul>
				<c:forEach var="altRef" items="${projectRef.altRefs}" varStatus="loop">
					<li>${altRef.alternative.name}<ul>
					<c:forEach var="critRef" items="${critSuite.references}" varStatus="loop">
						<li>Name: ${critRef.criterion.name}</li>
						<li>Description: ${critRef.criterion.description}</li>
						<li>Grade: <b id="critGrade-${critRef.criterion.id}">${critRef.criterion.grade}</b></li>
						<li>Objectives (${fn:length(critRef.criterion.objectives)}):</li>
						<ul>
							<c:forEach var="objective" items="${critRef.criterion.objectives}" varStatus="loop">
								<li>${objective.name} - Grade: 
									<select id="objGrade-${objective.id}" onchange="setVoting(${alternative.id},${criterion.id}, ${objective.id}, this.value);">
										<option>3</option>
										<option>2</option>
										<option>1</option>
										<option selected="true"></option>
										<option>-1</option>
										<option>-2</option>
										<option>-3</option>
									</select>
								</li>	
							</c:forEach>
						</ul>
					</c:forEach>
				</ul></li>
				</c:forEach>
				</ul></li>
		</c:forEach>
		</ul>
	</body>
</html>

