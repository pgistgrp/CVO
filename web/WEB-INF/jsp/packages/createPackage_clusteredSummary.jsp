<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

<table cellpadding="0" cellspacing="0">
	<tr>
		<td><h3>Total money needed</h3></td>
		<td>$<fmt:formatNumber maxFractionDigits="1" value="${package.totalCost/1000000000}" /> billion</td>
	</tr>
	<tr>
		<td><h3>Total money raised</h3></td>
		<td>$<fmt:formatNumber maxFractionDigits="1" value="${package.totalFunding/1000000000}" /> billion</td>
	</tr>
	
	<tr>
		<td colspan="2">
		<c:choose>
		<c:when test="${(package.totalFunding - package.totalCost) > 0}">
			<div id="balance" class="balance" style="background-color:#C9E1BD;">
				<h3 style="width:50%;">Balance:</h3>
				<h3 id="sum" style="width:49%;text-align:right;">$<fmt:formatNumber type="number" maxFractionDigits="0" value="${(package.totalFunding - package.totalCost)/1000000}" /> million </h3><div class="clearBoth"></div>
			</div>
		</c:when>
		<c:when test="${(package.totalFunding - package.totalCost) == 0}">
			<div id="balance" class="balance" style="background-color:#C9E1BD;">
				<h3 style="width:50%">Balance:</h3>
				<h3 id="sum" style="width:49%;text-align:right;">$<fmt:formatNumber type="number" maxFractionDigits="0" value="${(package.totalFunding - package.totalCost)/1000000}" /> million </h3><div class="clearBoth"></div>
			</div>
		</c:when>
		<c:otherwise>
			<div id="balance" class="exceed" style="background-color:#E5A9A6">
				<h3 style="width:50%;">Balance:</h3>
				<h3 id="sum" style="width:49%;text-align:right;">$<fmt:formatNumber type="number" maxFractionDigits="0" value="${(package.totalFunding - package.totalCost)/1000000}" /> million </h3><div class="clearBoth"></div>
			</div>
		</c:otherwise>
		</c:choose>
		</td>
	</tr>
	
	<tr>
		<td><strong>Total cost to the average resident:</strong></td>
		<td><fmt:formatNumber type="currency">${package.avgResidentCost}</fmt:formatNumber> per year</td>
	</tr>
	<tr>
		<td><strong>Number of projects in your package:</strong></td>
		<td>${fn:length(package.projAltRefs)}</td>
	</tr>
</table>

	<input class="finishedButton" type="button" onclick="finished();" value="Finished? Submit your package" 
	${((package.totalFunding - package.totalCost) < 0) ? "disabled='true'" : ""} />
