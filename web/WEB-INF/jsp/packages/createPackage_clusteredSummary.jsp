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
		[ ] Integrate Layout (Adam)
		[ ] Add "negative" and "positive" CSS classes - to indicate balance color (Adam)
		[ ] Test and Refine (Jordan)
#### -->

<table>
	<tr>
		<td><h3>Total Cost</h3></td>
		<td>${userPackage.totalCost} million</td>
	</tr>
	<tr>
		<td><h3>Total funding</h3></td>
		<td>${userPackage.totalFunding} million</td>
	</tr>
	<tr>
		<td><strong>Cost to you:</strong></td>
		<td>${userPackage.yourCost} per year</td>
	</tr>
	<tr>
		<td><strong>Cost to the average resident:</strong></td>
		<td>${userPackage.avgCost} per year</td>
	</tr>
	<tr>
		<td><strong>Number of projects in your package:</strong></td>
		<td>${fn:length(userPackage.projects)}</td>
	</tr>
</table>
	<div class="${(userPackage.balance > 0) ? 'balance':'negative'}">
		<h3>Revenues Equal Costs</h3>
	</div>
