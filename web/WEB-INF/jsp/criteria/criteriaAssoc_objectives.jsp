<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!--####
	Project: Let's Improve Transportation!
	Page: Objectives List
	Description: This is a partial page that is included via getObjectives in criteriaMgr.
				 This page will loop through all of the objectives in a given workflow instance.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman, Isaac Yang
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] Test if page is pulled by getObjectives (Jordan)

#### -->
<c:choose>
	<c:when test="${fn:length(objectives) == 0}">
		No objectives could be found!
	</c:when>
	<c:otherwise>
		<ul id="objectivesList" class="assocList">
			<c:forEach var="objective" items="${objectives}" varStatus="loop">
				<li id="objectiveCont-${objective.id}" class="assocList"><input type="checkbox" name="objectivesGroup" id="objective-${objective.id}" /> ${objective.description} [ <a href="javascript:deleteObjective(${objective.id})">delete</a> ]</li>
			</c:forEach>
		</ul>
	</c:otherwise>
</c:choose>

