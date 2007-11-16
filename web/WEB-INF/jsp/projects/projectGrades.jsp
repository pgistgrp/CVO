<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>



<h1>Planning Factor Grades for ${project.name}</h1>
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