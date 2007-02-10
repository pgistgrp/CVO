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

<h3>Your package summary</h3>
<p>Total Cost: ${userPackage.totalCost}</p>
<p>Total Funding: ${userPackage.totalFunding}</p>
<p>Cost to you: ${userPackage.yourCost}</p>
<p>Cost to the average resident: ${userPackage.avgCost}</p>
<p>Number of projects in your package: ${fn:length(userPackage.projects)}</p>

<h3 id="balance" class="${(userPackage.balance > 0) ? 'negative':'positive'}">Balance: ${userPackage.balance}</h3>
<!--Adam - Add negative and positive CSS classes-->