<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!--####
	Project: Let's Improve Transportation!
	Page: Project Alternative Description
	Description: This page serves as an information page for a project alternative. 
	Author: Jordan Isip, Adam Hindman, Issac Yang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] Integrate Layout (Adam)
		[ ] Integrate Project Map or static image (Guirong/Issac)
		[ ] Integrate Criteria Tree (Issac)
#### -->

<div id="prjAlternativeDescription">
	<h1>${alerternative.name}</h1>
	<p><b>Money needed to complete this project: </b> ${alerternative.cost}</p>
	<p><b>Sponsoring Agency:</b> ${alerternative.sponsor}</p>

	<p><b>Short Description:</b><br />${alerternative.shortDesc}</p>
	<p><b>Detailed Description:</b><br />${alerternative.detailedDesc}</p>

	<p><b>Links to Additional Information about this Project:</b>${alerternative.links}</p>

	<p><b>Statement For:</b><br />${alerternative.statementFor}</p>
	<p><b>Statement Against:</b><br />${alerternative.statementAgainst}</p>

	<h1>Planning Factor Grades for ${alerternative.name}</h1>
	<c:forEach var="criterion" items="${criteria}" varStatus="loop">
		<hr>
			<p>Name: ${criterion.name}</p>
			<p>Description: ${criterion.description}</p>
			<p>Grade: ${criterion.grade}</p>
			<p>Objectives (${fn:length(criterion.objectives)}):</p>		
			<ul>
				<c:forEach var="objective" items="${criterion.objectives}" varStatus="loop">
					<li>${objective.name} - ${objective.grade}</li>	
				</c:forEach>
			</ul>
			<p>Average grade based on equal weighting of all planning factors: ${alternative.criteria.equalWeights}</p>
			<p>Average grade based on all participants' weighting of planning factors: ${alternative.criteria.allAverage}</p>
			<p>Average grade based on your preferred weighting of planning factors: ${alternative.criteria.userWeight}</p>
		<hr>
		<br />
	</c:forEach>
</div>
<div id="map">
	<!-- map goes here gMan -->
</div>