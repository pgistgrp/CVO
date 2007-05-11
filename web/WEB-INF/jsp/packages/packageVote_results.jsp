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
<title>Step 4b: Vote</title>

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
</head>
<body>
<!-- Begin the header - loaded from a separate file -->
<div id="header">
	<!-- Begin header -->
	<jsp:include page="/header.jsp" />
	<p>[Load header from separate file]</p>
	<!-- End header -->
</div>
<!-- End header -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu">
	<div id="headerContainer">
		<div id="headerTitle" class="floatLeft">
			<h3 class="headerColor">Step 4b: Evaluate Candidate Packages</h3>
		</div>
		<div class="headerButton floatLeft"> <a href="step4.html">4a: Review packages</a> </div>
		<div class="headerButton floatLeft currentBox"> <a href="step4b.html">4b: Vote</a> </div>
		<div id="headerNext" class="floatRight box5"> <a href="step4b.html">Next Step</a> </div>
	</div>
</div>
<!-- End header menu -->
<!-- #container is the container that wraps around all the main page content -->
<div id="container">
	<!-- begin "overview and instructions" area -->
	<div id="overview" class="box2">
		<h3>Overview and Instructions</h3>
		<p>During this step, the moderator asks participants to determine which packages
			have the greatest level of collective support. This polling information will help
			us to decide which package to collectively recommend. The final vote will be held
			on Thursday Oct. 19.</p>
	</div>
	<!-- end overview -->
	<!-- begin Object -->
	<div id="object">
		<!-- begin obj-left -->
		<h2 class="headerColor">Active polls</h2>
		<!-- begin one voting box -->
		<jsp:useBean id="now" class="java.util.Date" />
		<fmt:formatDate value="${now}" dateStyle="full" var="formattedDate" />

		<h3 class="headerColor">Package Poll Results as of ${formattedDate}</h3>
		<c:set var="voters" value="${(fn:length(voteSuite.userVotes) / fn:length(voteSuite.stats))}" />
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
		<!-- begin one voting box -->
		<h3 class="headerColor">Package Poll Results October 15, 2007</h3>
		<p>On August 5 Moderator_Joanna initiated a poll. 214 participants voted in the
			poll (89% of all participants). Poll results are displayed below.</p>
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
			<div class="VoteListRow row">
				<div class="voteCol1 floatLeft">
					<div class="floatLeft">Package A </div>
				</div>
				<div class="voteCol2 floatLeft">50%</div>
				<div class="voteCol3 floatLeft">44%</div>
				<div class="voteCol4 floatLeft">6%</div>
				<div class="clearBoth"></div>
			</div>
			<div class="VoteListRow row">
				<div class="voteCol1 floatLeft">Package B </div>
				<div class="voteCol2 floatLeft">43%</div>
				<div class="voteCol3 floatLeft">39%</div>
				<div class="voteCol4 floatLeft">18%</div>
				<div class="clearBoth"></div>
			</div>
			<div class="VoteListRow row">
				<div class="voteCol1 floatLeft"> Package C </div>
				<div class="voteCol2 floatLeft">40%</div>
				<div class="voteCol3 floatLeft">23%</div>
				<div class="voteCol4 floatLeft">37%</div>
				<div class="clearBoth"></div>
			</div>
			<div class="VoteListRow row">
				<div class="voteCol1 floatLeft">
					<div class="floatLeft">Package D </div>
				</div>
				<div class="voteCol2 floatLeft">33%</div>
				<div class="voteCol3 floatLeft">23%</div>
				<div class="voteCol4 floatLeft">44%</div>
				<div class="clearBoth"></div>
			</div>
			<div class="VoteListRow row">
				<div class="voteCol1 floatLeft">
					<div class="floatLeft">Package E </div>
				</div>
				<div class="voteCol2 floatLeft">12%</div>
				<div class="voteCol3 floatLeft">44%</div>
				<div class="voteCol4 floatLeft">44%</div>
				<div class="clearBoth"></div>
			</div>
			<div class="VoteListRow row">
				<div class="voteCol1 floatLeft">
					<div class="floatLeft">RTID Package</div>
				</div>
				<div class="voteCol2 floatLeft">12%</div>
				<div class="voteCol3 floatLeft">44%</div>
				<div class="voteCol4 floatLeft">44%</div>
				<div class="clearBoth"></div>
			</div>
		</div>
		<!-- end one voting box -->
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
<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu">
	<div id="headerContainer">
		<div id="headerTitle" class="floatLeft">
			<h3 class="headerColor">Step 4b: Evaluate Candidate Packages</h3>
		</div>
		<div class="headerButton floatLeft"> <a href="step4.html">4a: Review
				packages</a> </div>
		<div class="headerButton floatLeft currentBox"> <a href="step4b.html">4b: Vote</a> </div>
		<div id="headerNext" class="floatRight box5"> <a href="step4b.html">Next Step</a> </div>
	</div>
</div>
<!-- End header menu -->
<!-- Begin footer -->
<div id="footer">
	<jsp:include page="/footer.jsp" />
</div>
<!-- End footer -->
</body>
</html>
