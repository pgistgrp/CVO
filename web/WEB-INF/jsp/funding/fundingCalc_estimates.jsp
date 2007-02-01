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
		[ ] Integrate Layout (Adam)
		[ ] Integrate Project Map or static image (Guirong/Issac)
		[ ] Integrate Criteria Tree (Issac)
#### -->

<ul id="tolls">
	<c:forEach var="toll" items="${userCommute.tolls}" varStatus="loop">
		<li>${toll.name} <input type="text" id="eToll-${toll.id}" name="eTolls" value="${toll.value}" /></li>
	</c:forEach>
	<c:forEach var="toll" items="${tolls}" varStatus="loop">
		<li>${toll.name} <input type="text" id="eToll-${toll.id}" name="eTolls" value="${toll.value}" /></li>
	</c:forEach>
</ul>
<p>Annual Consumption Rate (sales tax) <input type="text" id="annualConsume" value="${annualConsume}" /></p>