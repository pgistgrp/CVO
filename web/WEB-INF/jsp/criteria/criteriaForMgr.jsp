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
#### -->

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
		
		<ul id="${criterion.id}themes"}>
			<li class="liHeader">Which concern themes are related to this planning factor?</li>
			<c:if test="${fn:length(infoObjects) == 0}">
				<li class="mleft15">Concern themes have not been created yet.  Please use the <a href="cstview.do?cctId=${cctId}">Concern Synthesis Tool</a> to create concern themes after participants have expressed their concerns.</li>
			</c:if>
			<c:forEach var="infoObject" items="${infoObjects}" varStatus="loop">
				<li class="mleft15">
					<label><input type="checkbox" id="theme${infoObject.id}" ${(pg:containsInfoObject(criterion,infoObject)) ? "checked='CHECKED'" : ""} name="${criterion.id}checkboxes" onClick="setThemes('${criterion.id}checkboxes', ${criterion.id});"/>
					${infoObject.object}</label>
				</li>
			</c:forEach>

			<li class="liHeader">What are the objectives for this planning factor?</li>
			<c:if test="${fn:length(criterion.objectives) == 0}">
				<li class="mleft15">No objectives have been created for this planning factor yet</li>
			</c:if>
			<c:forEach var="objective" items="${criterion.objectives}" varStatus="loop">
				<li class="mleft15">
					<div id="objective${objective.id}">
						${objective.description}
						<small>[ <a href="javascript:toggleEditField('objective',${objective.id});">edit</a> ]
				        [ <a href="javascript:deleteObjective(${objective.id});">delete</a> ]</small>
					</div>
					<div id="objectiveEdit${objective.id}" style="display: none;"> 
						<form id="editObjective" action="javascript:editObjective(${objective.id});">
							<input name="description" id="objDesc${objective.id}" type="text" value="${objective.description}" />
							<input type="submit" value="Update" /><small> <a href="javascript:toggleEditField('objective',${objective.id});">Cancel</a></small>
						</form>
					</div>
				</li>
			</c:forEach>
			<li class="mleft30"><a href="javascript:Element.toggle('addObjective${criterion.id}');void(0);">Add an Objective</a>
				<div id="addObjective${criterion.id}" style="display:none;">
					<form action="javascript:addObjective(${criterion.id});">
						Objective Description: <input type="text" id="objDesc${criterion.id}" size="30"/><input type="submit" value="Add">
					</form>
				</div>
				
			</li>
		</ul>

  </div>
</c:forEach>
