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
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman, Issac Yang
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] Ensure connection with criteriaMgr.jsp (Jordan)
		[ ] Loop through ${criteria} (Jordan)
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
	
	<div class="criteriaListRow row">
		<div class="criteriaCol1 floatLeft"><a href="#">
			<div class="floatLeft"><a href="javascript:expandList('objectives1','icon1');">
				<img src="/images/plus.gif" id="icon1"></a></div>
			<div class="floatLeft"><a href="#">Economic Vitality</a></div>
		</div>
		<div class="criteriaCol2 floatLeft smallText">Support the
			economic vitality of the metropolitan area especially
			by enabling global competitiveness, productivity, and
			efficiency</div>

		<div class="criteriaCol3 floatLeft smallText">None</div>
		<div class="clearBoth"></div>
		<div class="objectives" id="objectives1" style="display:none;"> Lorem
			Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
			Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
			Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
			Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
			Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
			Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
			Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
			Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
			Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
			Lorem Ipsum Lorem Ipsum Lorem Ipsum </div>
	</div>
	<div class="criteriaListRow row even">
		<div class="criteriaCol1 floatLeft"><a href="#">
			<div class="floatLeft"><a href="javascript:expandList('objectives2','icon2');">

				<img src="/images/plus.gif" id="icon2"></a></div>
			<div class="floatLeft"><a href="#">Security</a></div>
		</div>
		<div class="criteriaCol2 floatLeft smallText">Increase
			security of the transportation system for motorized and
			nonmotorized uses.</div>
		<div class="criteriaCol3 floatLeft smallText">None</div>
		<div class="clearBoth"></div>
		<div class="objectives" id="objectives2" style="display:none;"> Lorem
			Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
			Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
			Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
			Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
			Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
			Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
			Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
			Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
			Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
			Lorem Ipsum Lorem Ipsum Lorem Ipsum </div>

	</div>
	<div class="criteriaListRow row">
		<div class="criteriaCol1 floatLeft">
			<div class="floatLeft"><a href="javascript:expandList('objectives3','icon3');">
				<img src="/images/plus.gif" id="icon3"></a></div>
			<div class="floatLeft"><a href="#">Accessibility and Mobility</a></div>
		</div>
		<div class="criteriaCol2 floatLeft smallText">Increase
			the accessibility and mobility options to people and freight</div>

		<div class="criteriaCol3 floatLeft smallText"> <a href="#">Traffic
				congestion is a problem</a><br />
			<a href="#">Making the region more bicycle friendly</a><br />
			<a href="#">Need to improve public transit</a><br />
		</div>
		<div class="clearBoth"></div>
		<div class="objectives" id="objectives3" style="display:none;"> Lorem
			Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
			Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
			Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
			Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
			Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
			Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
			Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
			Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
			Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
			Lorem Ipsum Lorem Ipsum Lorem Ipsum </div>

	</div>
	<div class="criteriaListRow row even">
		<div class="criteriaCol1 floatLeft">
			<div class="floatLeft"><a href="javascript:expandList('objectives4','icon4');">
				<img src="/images/plus.gif" id="icon4"></a></div>
			<div class="floatLeft"><a href="#">Lorem Ipsum Dolor Sit
					Amet Adapiscing Elit</a></div>
		</div>
		<div class="criteriaCol2 floatLeft smallText"> Protect
			and enhance the environment, promote energy conservation,
			and improve quality of life. </div>

		<div class="criteriaCol3 floatLeft smallText"> <a href="#">Environmental
				impacts of transportation</a><br />
		</div>
		<div class="clearBoth"></div>
		<div class="objectives" id="objectives4" style="display:none;"> Lorem
			Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
			Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
			Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
			Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
			Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
			Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
			Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
			Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
			Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
			Lorem Ipsum Lorem Ipsum Lorem Ipsum </div>
	</div>
