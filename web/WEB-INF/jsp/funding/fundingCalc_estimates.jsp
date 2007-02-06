<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!--####
	Project: Let's Improve Transportation!
	Page: Funding Source Estimates for a Given User
	Description: This page serves as an information page for a project alternative. 
	Author: Jordan Isip, Adam Hindman, Issac Yang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] Integrate Layout (Adam)
		[ ] Integrate Project Map or static image (Guirong/Issac)
		[ ] Integrate Criteria Tree (Issac)
#### -->
		<div id="estimates">
			<div id="tolls">
				<h3 class="headerColor peekaboobugfix">Estimated use of toll roads, taxed parking facilities, and annual taxable consumption</h3>
			<p class="peekaboobugfix">Your estimates number of tolls has been estimated based on your home zip code, your usual commute mode of travel, and your commute route. You may change these estimates.</p>
			<table id="tollRoads" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="25%"><strong>Estimated number of trips on potential toll roads per year</strong></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<c:forEach var="toll" items="${userCommute.tolls}" varStatus="loop">
						${toll.name} <input type="text" id="eToll-${toll.id}" name="eTolls" value="${toll.value}" /><br />
						</c:forEach>
					</td>
					<td>
					<c:forEach var="toll" items="${tolls}" varStatus="loop">
						${toll.name} <input type="text" id="eToll-${toll.id}" name="eTolls" value="${toll.value}" /><br />
						</c:forEach>
					</td>
					<td colspan="2">Annual Consumption Rate (sales tax) <input type="text" id="annualConsume" value="${annualConsume}" /></td>
				</tr>
			</table>
				<div class="clearboth">
					<input type="button" name="calcEstimates" value="Update Annual Cost Report" 
						style="clear:both;margin:1em;" class="floatRight">
				</div>
			<div class="clearBoth"></div>
			</div>
		</div>