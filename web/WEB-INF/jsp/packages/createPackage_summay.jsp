<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!--####
	Project: Let's Improve Transportation!
	Page: Create Package Summary Report
	Description: This page serves report for the summary.
	Author: Jordan Isip, Adam Hindman, Issac Yang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] Integrate Layout (Adam)
		[x] Add "negative" and "positive" CSS classes - to indicate balance color (Adam)
		[ ] Test and Refine (Jordan)
#### -->


<!--Package Summary-->
<h3 class="headerColor">Your package summary</h3>
<!--Title-->
<div class="floatLeft packageCol1">
	<div>
		<!--Total Cost-->
		<div>Total cost</div>
	</div>
	<div>
		<!--Total funding-->
		<div>Total funding</div>
	</div>
	<div>
		<!--Balance-->
		<div id="balanceRow" class="${(userPackage.balance > 0) ? 'negative':'positive'}">Balance</div>
	</div>
</div>
<div class="floatLeft packageCol2">
	<div>
		<div id="totalCost">${package.totalCost}</div>
		<div id="totalFunding">${package.totalFunding}</div>
		<div class="${(userPackage.balance > 0) ? 'negative':'positive'}" id="balance">${package.balance}</div>
	</div>
</div>
<div class="floatLeft packageCol3">
	<div>
		<!--Cost to you-->
		<div>Cost to you</div>
	</div>
	<div>
		<!--Cost to the average resident-->
		<div>Cost to the average resident</div>
	</div>
	<div>
		<!--Number of projects in your package-->
		<div>Number of projects in your package</div>
	</div>
</div>
<div class="floatLeft packageCol4">
	<div id="costToYou">${package.yourCost}</div>
	<div id="costToAvg">${stat.avgCost}</div>
	<div id="projNum">${fn:length(package.projects)}</div>
</div>
<div class="clearBoth"></div>
<!--End of package summary-->