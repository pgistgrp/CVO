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
		[ ] Look into map for planning factor grades (Jordan)
#### -->

<h1>${reference.alternative.name}</h1>
<p><b>Money needed to complete this project: </b> ${reference.alternative.cost}</p>
<p><b>County: </b> ${reference.alternative.county}</p>
<p><b>Sponsoring Agency:</b> ${reference.alternative.sponsor}</p>

<p><b>Short Description:</b><br />${reference.alternative.shortDesc}</p>
<p><b>Detailed Description:</b><br />${reference.alternative.detailedDesc}</p>

<p><b>Links to Additional Information about this Project:</b>${reference.alternative.links}</p>

<p><b>Statement For:</b>${reference.alternative.statementFor}</p>
<p><b>Statement Against:</b>${reference.alternative.statementAgainst}</p>

<h3>Planning Factor Grades for ${alternative.name}</h3>
<c:forEach var="criterion" items="${criteria}" varStatus="loop">
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