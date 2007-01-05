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
	     Front End: Jordan Isip, Adam Hindman, Issac Yang
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] Ensure connection with criteriaMgr.jsp (Jordan)
		[ ] Loop through ${criteria} (Jordan)
		[ ] Add conditional statements to allow sdcritStructureTarget.jsp to use the same partial.
#### -->
	<!-- begin criteria headers -->
	<div class="criteriaListHeader">
		<div class="criteriaCol1 floatLeft">
			<h4 class="headerColor">Planning factor</h4>

		</div>
		<div class="criteriaCol2 floatLeft">
			<h4 class="headerColor">Description</h4>
		</div>
		<div class="criteriaCol3 floatLeft">
			<h4 class="headerColor">Related concern theme</h4>
		</div>
		<div class="clearBoth"></div>

	</div>
	<!-- end criteria headers -->
	
	<c:forEach var="criterion" items="${criteria}" varStatus="loop">
		<div id="criteria-${criterion.id}" class="criteriaListRow row ${((loop.index % 2) == 0) ? 'even' : ''}">
			<div class="criteriaCol1 floatLeft"><a href="#">
				<div class="floatLeft"><a href="javascript:expandList('objectives1','icon1');">
					<img src="/images/plus.gif" id="icon1"></a></div>
				<div class="floatLeft">${criterion.name} [ <a href="javascript:editCriterionPopup(${criterion.id);">Edit</a> ] [ <a href="javascript:deleteCriterion(${criterion.id});">delete</a> ]</div>
			</div>
			<div class="criteriaCol2 floatLeft smallText">${criterion.na}</div>

			<div class="criteriaCol3 floatLeft smallText"><!--themes-->
				<c:forEach var="theme" items="${themes}" varStatus="loop">
					<p>${theme.name}</p>
				</c:forEach>
			</div>
			
			<div class="clearBoth"></div>
			<div class="objectives" id="criteriaEdit${criterion.id}"><!--javascript will load edit form here --></div>
			<div class="objectives" id="objectives1" style="display:none;">
				<c:forEach var="objective" items="${objectives}" varStatus="loop">
					<p>${objective.name}</p>
				</c:forEach>
			</div>

		</div>	
	</c:forEach>