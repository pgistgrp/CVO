<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!--####
	Project: Let's Improve Transportation!
	Page: Voting Results
	Description: Participant is forwarded to this page if he/she has already voted on a package.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Matt Paulin, Zhong Wang, John Le
	Todo Items:
		[x] Setup Action (Matt)
		[x] Layout (Adam)
		[ ] Get total number of participants in expiriment (John)
		[ ] Previous Votes (Matt)
		[ ] Integrate (Jordan)
		[ ] Workflow Dates (Jordan)
#### -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Step 4b: Poll Results</title>

<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->

<!--Criteria Specific  Libraries-->
<script src="scripts/SideBar.js" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/interface/PackageAgent.js'></script>

<!-- Site Wide CSS -->
<style type="text/css" media="screen">
		@import "styles/lit.css";
		@import "styles/step4b-voteresults.css";
	</style>
<!-- End Site Wide CSS -->
<event:pageunload />
</head>
<body>
<!-- End header -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
<!-- End header menu -->
<!-- #container is the container that wraps around all the main page content -->
<div id="container">
	<!-- begin "overview and instructions" area -->
	<!-- end overview -->
	<!-- begin Object -->
	<div id="object">
		<!-- begin obj-left -->
		<!-- begin one voting box -->
		<jsp:useBean id="now" class="java.util.Date" />
		<fmt:formatDate value="${now}" dateStyle="full" var="formattedDate" />

		<h3 class="headerColor">Package Poll</h3>
		Results as of ${formattedDate}</h3>
		<c:set var="voters" value="${(fn:length(voteSuite.userVotes) / fn:length(voteSuite.stats))}" />
		<p><fmt:formatNumber type="number">${voters}</fmt:formatNumber> participants voted in this
			poll (<fmt:formatNumber type="percent">${voters / 20}</fmt:formatNumber> of all participants). Poll results are displayed below.</p>
		<div class="voteBox box3 padding5" class="clearfix">
			<div class="VoteListRow row odd">
				<div class="voteCol1 floatLeft">&nbsp;</div>
				<div class="voteCol2 floatLeft">I would <strong>enthusiastically recommend</strong> this
					package</div>
				<div class="voteCol3 floatLeft">I am <strong>willing to recommend</strong> this
					package if it receives greatest participant support</div>
				<div class="voteCol4 floatLeft">I would <strong>not recommend</strong> this package,
					regardless of its support among other participants</div>
				<div class="clearBoth"></div>
			</div>

			<c:forEach var="stat" items="${voteSuite.stats}" varStatus="loop">
				<div class="VoteListRow row ">
					<div class="voteCol1 floatLeft">
						<div class="floatLeft">${stat.clusteredPackage.description}</div>
					</div>
					<div class="voteCol2 floatLeft"><fmt:formatNumber type="percent">${stat.highVotePercent / stat.totalVotes}</fmt:formatNumber></div>
					<div class="voteCol3 floatLeft"><fmt:formatNumber type="percent">${stat.mediumVotePercent / stat.totalVotes}</fmt:formatNumber></div>
					<div class="voteCol4 floatLeft"><fmt:formatNumber type="percent">${stat.lowVotePercent / stat.totalVotes}</fmt:formatNumber></div>
					<div class="clearBoth"></div>
				</div>
			</c:forEach>
		</div>
		<!-- end one voting box -->
		<div class="clearBoth"></div>
		
		<h2 class="headerColor">Previous polls</h2>
		<c:if test="${fn:length(pVoteSuites) == 0}">
			<p>There have not been any previous polls.</p>
		</c:if>
		<!-- begin one voting box -->
		****${pVoteSuites}
		<c:forEach var="suite" items="${pVoteSuites}" varStatus="loop">
			<h3 class="headerColor">Package Poll Results --- workflow date ---</h3>
			<c:set var="voters" value="${(fn:length(suite.userVotes) / fn:length(suite.stats))}" />
			<p><fmt:formatNumber type="number">${voters}</fmt:formatNumber> participants voted in this
				poll (<fmt:formatNumber type="percent">${voters / 20}</fmt:formatNumber> of all participants). Poll results are displayed below.</p>
			<div class="voteBox box3 padding5" class="clearfix">
				<div class="VoteListRow row odd">
					<div class="voteCol1 floatLeft">&nbsp;</div>
					<div class="voteCol2 floatLeft">I would <strong>enthusiastically endorse</strong> this
						package</div>
					<div class="voteCol3 floatLeft">I am <strong>willing to endorse</strong> this
						package if it receives greatest participant support</div>
					<div class="voteCol4 floatLeft">I would <strong>not endorse</strong> this package,
						regardless of its support among other participants</div>
					<div class="clearBoth"></div>
				</div>

				<c:forEach var="stat" items="${suite.stats}" varStatus="loop">
					<div class="VoteListRow row ">
						<div class="voteCol1 floatLeft">
							<div class="floatLeft">${stat.clusteredPackage.description}</div>
						</div>
						<div class="voteCol2 floatLeft"><fmt:formatNumber type="percent">${stat.highVotePercent / stat.totalVotes}</fmt:formatNumber></div>
						<div class="voteCol3 floatLeft"><fmt:formatNumber type="percent">${stat.mediumVotePercent / stat.totalVotes}</fmt:formatNumber></div>
						<div class="voteCol4 floatLeft"><fmt:formatNumber type="percent">${stat.lowVotePercent / stat.totalVotes}</fmt:formatNumber></div>
						<div class="clearBoth"></div>
					</div>
				</c:forEach>
			</div>
		</c:forEach>
	</div>
	<!-- end obj-left -->
	<!-- begin firefox height hack -->
	<div class="clearBoth"></div>
	<!-- end firefox height hack -->
</div>
<!-- end Object-->
</div>
<!-- End foldable average grades table -->
</div>
<!-- end container -->
<!-- start feedback form -->
<pg:feedback id="feedbackDiv" action="cctView.do"/>
<!-- end feedback form -->
</body>
</html>
