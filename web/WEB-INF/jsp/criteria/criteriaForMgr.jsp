<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<!--####
	Project: Let's Improve Transportation!
	Page: Criteria
	Description: This is a partial page that defines the view of a single criteria in criteriaMgr.jsp.
				 This is also used on sdcritStructureTarget.jsp
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] Ensure connection with criteriaMgr.jsp (Jordan)
		[x] Loop through ${criteria} (Jordan)
		
#### -->
<!-- begin criteria headers -->


<h3 class="headerColor">Planning factors</h3>

<!-- end criteria headers -->
<c:if test="${fn:length(criteria) == 0}">
  <p>You have not created any planning factors yet! Use the form
    below to add a new planning factor.</p>
</c:if>
<c:forEach var="criterion" items="${criteria}" varStatus="loop">
  <div id="criteria-${criterion.id}" class="criteriaListRow row ${((loop.index % 2) == 0) ? 'even' : ''}">

		<div id="criterion${criterion.id}">
			${criterion.name}
	        <small>[ <a href="javascript:toggleEditField('criterion',${criterion.id});">edit</a> ]
	        [ <a href="javascript:deleteCriterion(${criterion.id});">delete</a> ]</small> 
		</div>
		
		<div id="criterionEdit${criterion.id}" style="display: none;"> 
			<form id="editCriterion" action="javascript:editCriterion(${criterion.id});">
				<input name="name" id="criterionName${criterion.id}" type="text" value="${criterion.name}" />
				<input type="submit" value="Update" /><small> <a href="javascript:toggleEditField('criterion',${criterion.id});">Cancel</a></small>
			</form>
		</div>

  </div>
</c:forEach>
