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
	     Front End: Jordan Isip, Adam Hindman, Isaac Yang
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] Ensure connection with criteriaMgr.jsp (Jordan)
		[x] Loop through ${criteria} (Jordan)
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
<c:if test="${fn:length(criteria) == 0}">
  <p>You have not created any planning factors yet! Use the form
    below to add a new planning factor.</p>
</c:if>
<c:forEach var="criterion" items="${criteria}" varStatus="loop">
  <div id="criteria-${criterion.id}" class="criteriaListRow row ${((loop.index % 2) == 0) ? 'even' : ''}">
    <div class="criteriaCol1 floatLeft"><a href="#">
      <div class="floatLeft"><a href="javascript:io.expandList('objectives${criterion.id}','icon${criterion.id}');"> <img src="/images/plus.gif" id="icon${criterion.id}"></a></div>
      <div class="floatLeft"> ${criterion.name}
        <c:if test="${structure.id == null}">
          <!-- needs another variable to differentiate -->
          <pg:show roles="moderator">
            <!-- show editing only to moderator -->
            <small><br />[ <a href="javascript:editCriterionPopup(${criterion.id});">edit</a> ]
            [ <a href="javascript:deleteCriterion(${criterion.id});">delete</a> ]</small> </pg:show>
        </c:if>
      </div>
    </div>
    <div class="criteriaCol2 floatLeft">${criterion.na}</div>
    <div class="criteriaCol3 floatLeft">
      <!--themes-->
      <c:if test="${fn:length(criterion.themes) == 0}"> None
        Selected </c:if>
      <c:forEach var="theme" items="${criterion.themes}" varStatus="loop">
        ${theme.title}<br />
      </c:forEach>
    </div>
    <div class="clearBoth"></div>
    <div class="objectives" id="criteriaEdit${criterion.id}">
      <!--javascript will load edit form here -->
    </div>
    <div class="objectives" id="objectives${criterion.id}" style="display:none;"><br /><strong>Objectives:</strong>
      <ul class="smallText">
        <c:if test="${fn:length(criterion.objectives) == 0}">
          <li>None Selected</li>
        </c:if>
        <c:forEach var="objective" items="${criterion.objectives}" varStatus="loop">
          <li>${objective.description}</li>
        </c:forEach>
      </ul>
    </div>
  </div>
</c:forEach>
<div class="clearBoth"></div>
