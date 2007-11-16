<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!--####
	Project: Let's Improve Transportation!
	Page: Edit Project Alternative scores
	Description: Partial to scoring project
	Author(s): Jordan Isip, Adam Hindman, Issac Yang, Guirong Zhou
	Todo Items:
		[] 
#### -->

<ul>
<c:forEach var="critGrade" items="${altRef.gradedCriteria}" varStatus="loop">
	<li>Factor: ${critGrade.criteria.name} (Grade: <b id="critGrade-${altRef.id}-${critGrade.criteria.id}">${critGrade.grade}</b>):
		<ul>
			<c:forEach var="gradedObjective" items="${critGrade.objectives}" varStatus="loop">
				<li>${gradedObjective.objective.description} - Grade:
					<select id="objGrade-${gradedObjective.objective.id}" onchange="setGrading(${altRef.id},${critGrade.criteria.id},${gradedObjective.objective.id}, this.value);">
						<c:forEach var="grade" items="-3,-2.5,-2,-1.5,-1,-0.5,0.0,0.5,1,1.5,2,2.5,3">
						
							<c:choose>
								<c:when test="${grade == 0.0}">
									<option <c:if test="${gradedObjective.grade == null || gradedObjective.grade == 0.0}">selected = "true"</c:if> value="${grade}">0</option>
								</c:when>
								<c:otherwise>
									<option <c:if test="${gradedObjective.grade == (grade)}"> selected = "true"</c:if> value="${grade}">${grade}</option>
								</c:otherwise>
							</c:choose>
				
						</c:forEach>
					</select>
				</li>	
			</c:forEach>
		</ul>
	</li>
</c:forEach>
</ul>
