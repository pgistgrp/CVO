<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
	<head>
	<title>Let's Improve Transportation - Step 4b: Package Info Page</title>
	<!-- Site Wide CSS -->
	<style type="text/css" media="screen">
		@import "styles/lit.css";
		@import "styles/table.css";
		@import "styles/table-grades.css";
		@import "styles/step4b-packageinfo.css";
	</style>
	<!-- End Site Wide CSS -->
	<!-- Site Wide JS -->
	<script src="scripts/prototype.js" type="text/javascript"></script>
	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<script src="scripts/search.js" type="text/javascript"></script>
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/util.js'></script>
	<script src="scripts/prototype.js" type="text/javascript"></script>
	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	</head>
	<script type="text/javascript" charset="utf-8">

	</script>
	<body>
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
		<!-- begin Object -->
		<div id="object clearfix">
			<p><h3 class="headerColor">Package ${package.description} Details</h3></p>
			<!-- begin TOP SUMMARY -->
			<div class="summary box11">
				<table>
					<tr>
						<td><h3>Total Cost</h3></td>
						<td><fmt:formatNumber type="currency">${package.totalCost}</fmt:formatNumber> million</td>
					</tr>
					<tr>
						<td><h3>Total funding</h3></td>
						<td><fmt:formatNumber type="currency">${package.totalFunding}</fmt:formatNumber> million</td>
					</tr>
					<c:if test="${userPkg != null}">
						<tr>
							<td><strong>Cost to you:</strong></td>
							<td><fmt:formatNumber type="currency">${package.yourCost}</fmt:formatNumber> per year</td>
						</tr>
					</c:if>
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
			</div>
			<!-- end TOP SUMMARY -->
			<div class="clearBoth"></div>

			<div id="newtable">
				<div id="top" class="box11">
					<table cellpadding=0 cellspacing=0>
						<tr class="tableHeading">
							<th class="first col1">Funding</th>
							<th class="col2">Estimated Money Raised</th>
							<th class="col3">Estimated annual cost to the average taxpayer</th>
							<th class="col4">Estimated annual cost to you</th>
						</tr>
						<!-- begin Funding Source -->
						<c:forEach var="source" items="${packageFunding}" varStatus="loop">
								<tr id="sourceId-${source.fundingSourceId}" class="fundingType">
									<td class="fundingSourceItem">${source.name}</td>
									<td colspan="3">&nbsp;</td>
								</tr>
								<!-- end PROJECT -->
								<!-- begin HIDDEN ROW of OPTIONS -->
								
								<tr class="objectives" id="objective1">
									<td colspan="4">
										<table>
											<c:forEach var="alt" items="${source.fundingSourceAlternatives}" varStatus="loop">
												<tr>
													<td class="col1">${alt.name}</td>
													<td class="col2"><fmt:formatNumber type="currency">${alt.estCost}</fmt:formatNumber> million</td>
													<td class="col3"><fmt:formatNumber type="currency">${alt.avgCost}</fmt:formatNumber></td>
													<td class="col4"><fmt:formatNumber type="currency">${alt.yourCost}</fmt:formatNumber></td>
												</tr>
											</c:forEach>
										</table>
									</td>
								</tr>
								<!-- end HIDDEN ROW -->
	
						</c:forEach>

					</table>
				</div>
				<div id="bottom" class="box11">
					<table cellpadding=0 cellspacing=0>
						<tr class="tableHeading">
							<th class="col1">My Proposed Projects</th>
							<th class="col2">Money Needed</th>
							<th class="col3">Average Grade</th>
							<th class="col4">Average Weighted Grade (your weights)</th>
							<th class="col5">Average Weighted Grade (all participants grades)</th>
						</tr>
						<!-- begin CATEGORY LABEL -->
						<tr >
							<td class="category" colspan="5"><strong>Road Projects</strong></td>
						</tr>
						<!-- end CATEGORY LABEL -->
						<!-- begin PROJECT -->
						
						<c:forEach var="project" items="${packageProjects}" varStatus="loop">
							<tr id="projId-${project.projectId}" class="fundingType">
								<td colspan="5" class="fundingSourceItem">${project.name}</td>
							</tr>
							<!-- begin ROW of OPTIONS -->
							<tr  class="objectives">
								<td colspan="5">
									<table>
										<c:forEach var="alt" items="${project.projectAlternatives}" varStatus="loop">
											<tr class="option">
												<td class="col1">${alt.name}</td>
												<td class="col2"><fmt:formatNumber type="currency">${alt.moneyNeeded}</fmt:formatNumber> Million</td>
												<td class="col3 gradeBPlus">${alt.projGrade}</td>
												<td class="col4 gradeA">${alt.yourGrade}</td>
												<td class="col5 gradeC">${alt.avgGrade}</td>
											</tr>
										</c:forEach>
									</table>
								</td>
							</tr>
							<!-- end ROW of OPTIONS -->
							<!-- end PROJECT -->
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
		<!-- end Object-->
	</div>
	<!-- end container -->
	</body>
</html:html>
<!-- 												

												<tr class="option">
													<td class="col1">Elevated Structure</td>
													<td class="col2">$217,015,384,615</td>
													<td class="col3 gradeFPlus">F+</td>
													<td class="col4 gradeDMinus">D-</td>
													<td class="col5 gradeCPlus">C+</td>
												</tr>
												<tr class="option">
													<td class="col1">Elevated Structure</td>
													<td class="col2">$217,015,384,615</td>
													<td class="col3 gradeD">D</td>
													<td class="col4 gradeA">A</td>
													<td class="col5 gradeBMinus">B-</td>
												</tr>
-->
