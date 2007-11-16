<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<!--####
	Project: Let's Improve Transportation!
	Page: Package Manager
	Description: View all auto generated packages and access to CRUD Events on All Manual Packages.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Zhong Wang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] BareBones JavaScript/JSTL (Jordan)
		[ ] Test and Refine (Jordan)
#### -->
<html:html> 
<head>
<title>Manage Packages</title>
<!-- Site Wide JavaScript -->
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->

<script>
// Global Variables
// End global Variables
</script>
<style type="text/css">

</style>
</head>


<body>
	<h3>Moderator Tools &raquo; Manage Packages</h3>
	<!-- begin "overview and instructions" area -->
	<div id="overview" class="box2">
		<h3 class="headerColor">Overview</h3>
		<p>
			These are the packages that will be discussed by participants in step 4a. 
			Nam interdum. Donec accumsan, purus ut viverra pharetra, augue tellus vehicula 
			orci, eget consectetuer neque tortor id ante. Proin vehicula imperdiet ante. 
		</p>
	</div>
	<!-- end overview -->
	 
	<div id="auto-packages">
		<h3 class="headerColor">Automatically Created Packages</h3>
		<c:choose>
			<c:when test="${testExpression}"> <!-- eventually will test if current date is less than workflow date for step 4a-->
				<p>Participant packaes will be automatically clustered on <!-- workflow date --> (beginning of step4a)</p>
			</c:when>
			<c:otherwise>
				<p>On <!--workflow date--> all participant packages were clustered into the ${fn:length(clusteredPackages.length)} below.</p> 
				<table>
					<tr>
						<th>Package</th>
						<th>Total</th>
						<th>Total Cost to Average Resident</th>
					</tr>
					<c:forEach var="package" items="${clusteredPackages}" varStatus="loop">
						<tr>
							<td>${package.id}</td>
							<td>${package.totalCost} Billion</td>
							<td>${package.avgCost}/year</td>
						</tr>
					</c:forEach>
				<table>
			</c:otherwise>
		</c:choose>
	</div>
	
	<div id="manual-packages">
		<h3 class="headerColor">Manually Created Packages</h3>
		<c:forEach var="package" items="${manualPackages}" varStatus="loop">
			<p>${package.description} [ <a href="packageMgr.do?activity=edit&pkgId=${package.id}">edit</a> ] [ <a href="packageMgr.do?activity=delete&pkgId=${package.id}">delete</a> ]</p>
		</c:forEach>
	</div>
	
	<div id="finished">
		<h3 class="headerColor">Finished?</h3>
		<p>When you are ready to allow participants begin discussing these packages, click “publish” below to create the discussion rooms.</p>
		<form method="POST" action="packageMgr.do?activity=publish">
			<input type="button" value="Publish Packages!" />
		</form>
	</div>

</body>
</html:html>

