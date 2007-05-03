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
		[x] Test and Refine (Jordan)
#### -->

<table>
	<tr>
		<td><h3>Total Cost</h3></td>
		<td><fmt:formatNumber type="currency">${package.totalCost}</fmt:formatNumber> million</td>
	</tr>
	<tr>
		<td><h3>Total funding</h3></td>
		<td><fmt:formatNumber type="currency">${package.totalFunding}</fmt:formatNumber> million</td>
	</tr>
	<tr>
		<td><strong>Cost to you:</strong></td>
		<td><fmt:formatNumber type="currency">${package.yourCost}</fmt:formatNumber> per year</td>
	</tr>
	<tr>
		<td><strong>Cost to the average resident:</strong></td>
		<td><fmt:formatNumber type="currency">${package.avgResidentCost}</fmt:formatNumber> per year</td>
	</tr>
	<tr>
		<td><strong>Number of projects in your package:</strong></td>
		<td>${fn:length(package.projAltRefs)}</td>
	</tr>
</table>

	<c:choose>
		<c:when test="${(package.totalFunding - package.totalCost) > 0}">
			<div id="balance" class="balance">
				<h3>Revenues Exceed Costs</h3>
			</div>
		</c:when>
		<c:when test="${(package.totalFunding - package.totalCost) == 0}">
			<div id="balance" class="balance">
				<h3>Revenues Equal Costs</h3>
			</div>
		</c:when>
		<c:otherwise>
			<div id="balance" class="exceed">
				<h3>Costs Exceed Revenue!</h3>
			</div>
		</c:otherwise>
	</c:choose>

	<input class="finishedButton" type="button" onclick="location.href='waiting.jsp'" value="Finished? Submit your package" 
	${((package.totalFunding - package.totalCost) < 0) ? "disabled='true'" : ""} />