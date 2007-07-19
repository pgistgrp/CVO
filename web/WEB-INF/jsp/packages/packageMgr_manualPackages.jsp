<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<table border="0" cellspacing="0" width="100%" class="box12">
	<tr>
		<th>Package</th>
		<th>Total</th>
		<th>Total Cost to Average Resident</th>
		<th>Actions</th>
	</tr>
	<c:forEach var="package" items="${packages}" varStatus="loop">
		<tr>
			<td class="col1"><a target="_blank" href="package.do?pkgId=${package.id}&fundSuiteId=${fundSuiteId}&projSuiteId=${projSuiteId}&critSuiteId=${critSuiteId}&pkgSuiteId=${pkgSuiteId}">${package.description}</a></td>
			<td>
			$<fmt:formatNumber maxFractionDigits="0" value="${package.totalCost/1000000}" /> million</td>
			<td>$${package.avgResidentCost}/year</td>
			<td><small><a href="editClusteredPackage.do?pkgSuiteId=${pkgSuiteId}&projSuiteId=${projSuiteId}&fundSuiteId=${fundSuiteId}&critSuiteId=${critSuiteId}&pkgId=${package.id}">Edit</a> | <a href="javascript:deleteClusteredPackage(${package.id});">Delete</a></small></td>
		</tr>
	</c:forEach>
</table>	
