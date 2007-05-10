<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<p class="floatRight" id="remainingWeightP2">Points Left: <b id="remainingWeight2">
<!--load remaining weight here -->
</b></p><br class="clearBoth" />
<div id="critRowWrapper">
<div class="criteriaListHeader">
	<div class="weighCriteriaCol1 floatLeft">
		<h4>Planning factor</h4>
	</div>
	<div class="weighCriteriaCol2 floatLeft">
		<h4>Description</h4>
	</div>
	<div class="weighCriteriaCol3 floatLeft">
		<h4>Weight</h4>
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
				<div class="floatLeft"><label for="icon${ref.criterion.id}" onclick="javascript:expandList('objectives${ref.criterion.id}','icon${ref.criterion.id}');">${ref.criterion.name}</label></div>
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
			<div class="clearBoth"></div>
			<div class="objectives" id="objectives${ref.criterion.id}" style="display:none;">
				<p>To get a good grade in <strong>${ref.criterion.name}</strong>, a transportation project must meet the following objectives:</p>
				<ul><c:if test="${fn:length(ref.criterion.objectives) == 0}">
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
</div>
<p id="remainingWeightP">Points Left: <b id="remainingWeight">
<!--load remaining weight here -->
</b></p><br class="clearBoth" />