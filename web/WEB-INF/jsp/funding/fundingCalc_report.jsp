<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!--####
	Project: Let's Improve Transportation!
	Page: Funding Source Report
	Description: This page serves report
	Author: Jordan Isip, Adam Hindman, Issac Yang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] Integrate Layout (Adam)
		[ ] Integrate Project Map or static image (Guirong/Issac)
		[ ] Integrate Criteria Tree (Issac)
#### -->
<h3 class="headerColor">My annual costs report</h3>

<table cellpadding="0" cellspacing="0">
	<tr class="tableHeading">
		<th class="first">Funding Source</th>
		<th>Estimated annual cost to you</th>

		<th>&nbsp;</th>
		<th>&nbsp;</th>
		<th colspan="7">Calculation</th>
	</tr>
	<c:forEach var="cost" items="${user.costs}" varStatus="loop">
		<tr class="fundingType">
			<c:forEach var="title" items="${cost.headers}" varStatus="loop">
				<td class="fundingSourceItem">${title}</td>
			</c:forEach>	
		</tr>
		<c:forEach var="alt" items="${cost.alternatives}" varStatus="loop">
			<tr class="fundingSourceItem">
				<c:forEach var="data" items="${alt.data}" varStatus="loop">
					<td class="fundingSourceItem">${data}</td>
				</c:forEach>
			</tr>
		</c:forEach>
	</c:forEach>
</table>