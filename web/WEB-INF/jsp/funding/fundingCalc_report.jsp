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

<p>==========================================</p>
<p>==========================================</p>
<p>==========================================</p>

	<table border="1" cellspacing="0" cellpadding="0">
		<c:forEach var="cost" items="${user.costs}" varStatus="loop">
			<tr>
				<c:forEach var="header" items="${cost.headers}" varStatus="loop">
					<th>${header}</th>
				</c:forEach>	
			</tr>
			<c:forEach var="alt" items="${cost.alternatives}" varStatus="loop">
				<tr>
					<c:forEach var="data" items="${alt.data}" varStatus="loop">
						<td>${data}</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</c:forEach>
	</table>


<p>==========================================</p>
<p>==========================================</p>
<p>==========================================</p>



<table cellpadding="0" cellspacing="0">
	<tr class="tableHeading">
		<th class="first">Funding Source</th>
		<th>Estimated annual cost to you</th>

		<th>&nbsp;</th>
		<th>&nbsp;</th>
		<th colspan="7">Calculation</th>
	</tr>
	<tr class="fundingType">
		<td class="fundingSourceItem">Toll on Alaskan Way Viaduct</td>
		<td>Cost to you</td>

		<td>=</td>
		<td>&nbsp;</td>
		<td>Peak tax rate</td>
		<td>&times;</td>
		<td># of Peak trips</td>
		<td>+</td>
		<td>off-peak tax rate</td>

		<td>&times;</td>
		<td># off-peak trips</td>
	</tr>
	<tr>
		<td class="fundingSourceItem">Fixed rate $1.00 per trip</td>
		<td>$25</td>
		<td>=</td>

		<td>&nbsp;</td>
		<td>$1.00</td>
		<td>&times;</td>
		<td>5</td>
		<td>+</td>
		<td>$1.00</td>
		<td>&times;</td>

		<td>20</td>
	</tr>
	<tr>
		<td class="fundingSourceItem">Fixed rate $1.00 per trip</td>
		<td>$25</td>
		<td>=</td>
		<td>&nbsp;</td>

		<td>$1.00</td>
		<td>&times;</td>
		<td>5</td>
		<td>+</td>
		<td>$1.00</td>
		<td>&times;</td>
		<td>20</td>

	</tr>
	<tr class="fundingType">
		<td class="fundingSourceItem">Toll on Alaskan Way Viaduct</td>
		<td>Cost to you</td>
		<td>=</td>
		<td>&nbsp;</td>
		<td>Peak tax rate</td>

		<td>&times;</td>
		<td># of Peak trips</td>
		<td>+</td>
		<td>off-peak tax rate</td>
		<td>&times;</td>
		<td># off-peak trips</td>
	</tr>

	<tr>
		<td class="fundingSourceItem">Fixed rate $1.00 per trip</td>
		<td>$25</td>
		<td>=</td>
		<td>&nbsp;</td>
		<td>$1.00</td>
		<td>&times;</td>

		<td>5</td>
		<td>+</td>
		<td>$1.00</td>
		<td>&times;</td>
		<td>20</td>
	</tr>
	<tr>

		<td class="fundingSourceItem">Fixed rate $1.00 per trip</td>
		<td>$25</td>
		<td>=</td>
		<td>&nbsp;</td>
		<td>$1.00</td>
		<td>&times;</td>
		<td>5</td>

		<td>+</td>
		<td>$1.00</td>
		<td>&times;</td>
		<td>20</td>
	</tr>

	<tr class="fundingType">
		<td class="fundingSourceItem">Sales tax increase (Rate now 8.8%)</td>

		<td>Cost to you</td>
		<td>=</td>
		<td>&nbsp;</td>
		<td>tax rate</td>
		<td>&times;</td>
		<td>consumption</td>
		<td>&nbsp;</td>

		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td class="fundingSourceItem">0.1% increase</td>
		<td>$16</td>
		<td>=</td>

		<td>&nbsp;</td>
		<td>0.1</td>
		<td>&times;</td>
		<td>$15,879</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>

	</tr>
	<tr>
		<td class="fundingSourceItem">0.3% increase</td>
		<td>$48</td>
		<td>=</td>
		<td>&nbsp;</td>
		<td>0.3</td>

		<td>&times;</td>
		<td>$15,879</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>

		<td class="fundingSourceItem">0.5% increase</td>
		<td>$79</td>
		<td>=</td>
		<td>&nbsp;</td>
		<td>0.5</td>
		<td>&times;</td>
		<td>$15,879</td>

		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
</table>