<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<table border="0" cellspacing="0" width="100%" class="box12">
	<tr>
		<th>Package</th>
		<th>Total</th>
		<th>Total Cost to Average Resident</th>
	</tr>
	<c:forEach var="package" items="${packages}" varStatus="loop">
		<tr>
			<td class="col1"><a href="package.do?id=${package.id}">${loop.index +1}</a></td>
			<td>$${package.totalCost} Billion</td>
			<td>$${package.totalCostForAvgResident}/year</td>
		</tr>
	</c:forEach>
</table>