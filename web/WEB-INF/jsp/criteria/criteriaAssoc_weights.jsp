<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


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
	
<c:forEach var="criterion" items="${criteria}" varStatus="loop">
	  <div id="criteria-${criterion.id}" class="criteriaListRow row ${((loop.index % 2) == 0) ? 'even' : ''}">
	    <div class="weighCriteriaCol1 floatLeft"><a href="#">
	      <div class="floatLeft"><a href="javascript:expandList('objectives${criterion.id}','icon${criterion.id}');"> <img src="/images/plus.gif" id="icon${criterion.id}"></a></div>
	      <div class="floatLeft"> ${criterion.name}</div>
	    </div>
	    <div class="weighCriteriaCol2 floatLeft">${criterion.na}</div>
	    <div class="weighCriteriaCol3 floatLeft">
	    	<!-- start slider bar -->

				<div onmouseover="currentSliderValue=sliderArray[${loop.index}].value;" id="track${criterion.id}" class="track" style="width:200px; height:9px;">
					<div id="track${criterion.id}-left" class="track-left"></div><div id="handle${criterion.id}" style="cursor: col-resize; width:19px; height:20px;"><img src="images/slider-handle.png" alt="" style="float: left;"/></div>
			
				<input type="text" style="float:right;" tabIndex="${loop.index + 1}" size="3" maxlength="3" id="input${criterion.id}" onchange="manualSliderChange(${loop.index}, this.value)" value = 
				<c:choose>
					<c:when test="${criterion.object.weight == null}">
						"0"
						
					</c:when>
					<c:otherwise>
						"${criterion.object.weight}"
					</c:otherwise>
				</c:choose>
				
				 /> <!-- end input -->
				
			<!-- end slider bar -->
			<!--weights-->
			
						
			<!-- end weights -->
	    </div>
	    <div class="clearBoth"></div>
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

	<div class="clearBoth"></div>
</c:forEach>
<p>Remaining Weight: <b id="remainingWeight"><!--load remaining weight here --></b></p>