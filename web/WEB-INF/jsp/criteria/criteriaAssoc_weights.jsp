<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<p id="remainingWeightP">Remaining Weight: <b id="remainingWeight">
<!--load remaining weight here -->
</b></p><br class="clearBoth" />

<div class="criteriaListHeader">
	<div class="weighCriteriaCol1 floatLeft">
		<h4 class="headerColor">Planning factor</h4>
	</div>
	<div class="weighCriteriaCol2 floatLeft">
		<h4 class="headerColor">Description</h4>
	</div>
	<div class="weighCriteriaCol3 floatLeft">
		<h4 class="headerColor">Weight</h4>
	</div>
	<div class="clearBoth"></div>
</div>
<!-- end criteria headers -->
<c:forEach var="ref" items="${critSuite.references}" varStatus="loop">
	<div id="criteria-${ref.criterion.id}" class="criteriaListRow row ${((loop.index % 2) == 0) ? 'even' : ''}">
		<div class="weighCriteriaCol1 floatLeft"><a href="#">
			<div class="floatLeft"> 
				<a href="javascript:expandList('objectives${ref.criterion.id}','icon${ref.criterion.id}');"> <img src="/images/plus.gif" id="icon${ref.criterion.id}"></a> 
			</div>
			<div class="floatLeft"> ${ref.criterion.name}</div>
		</div>
		<div class="weighCriteriaCol2 floatLeft">${ref.criterion.na}</div>

		<div class="weighCriteriaCol3 floatLeft">
			<!-- start slider bar -->
			<div id="track${ref.criterion.id}" class="track floatLeft" style="width:200px; height:9px;">
				<div id="track${ref.criterion.id}-left" class="track-left"></div>
				<div id="handle${ref.criterion.id}" style="cursor: col-resize; width:19px; height:20px;"> 
				<img src="images/slider-handle.png" alt="" style="float: left;"/> </div>
			</div>
			<div id="inputField" class="floatRight">
				<input type="text" tabIndex="${loop.index + 1}" size="3" 
				maxlength="3" id="input${ref.criterion.id}" 
				onchange="manualSliderChange(${ref.criterion.id}, this.value)" value = "0" />
			</div>
			<!-- end input -->
			<!-- end slider bar -->
			<!--weights-->
			<!-- end weights -->
			<div class="clearBoth"></div>
		</div>
		
		<div class="objectives" id="objectives${ref.criterion.id}" style="display:none;"><br />
			<strong>Objectives:</strong>
			<ul class="smallText">
				<c:if test="${fn:length(ref.criterion.objectives) == 0}">
					<li>None Selected</li>
				</c:if>
				<c:forEach var="objective" items="${ref.criterion.objectives}" varStatus="loop">
					<li>${objective.description}</li>
				</c:forEach>
			</ul>
		</div>
		<div class="clearBoth"></div>
	</div>
</c:forEach>
</div>
</div>
<p id="remainingWeightP">Remaining Weight: <b id="remainingWeight">
<!--load remaining weight here -->
</b></p><br class="clearBoth" />