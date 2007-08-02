<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
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
<title>Step 4b: Vote Results</title>

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
<script type="text/javascript" charset="utf-8">
	function paintRows(){
		rows = document.getElementsByClassName('VoteListRow');
		for (var i = 0;i<rows.length;i++){
			if(i%2!=0){
				$(rows[i]).style.background="#E7F2F7";		
			}
			
		}	
	}
</script>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">
		@import "styles/lit.css";
		@import "styles/step4b-voteresults.css";
	</style>
<!-- End Site Wide CSS -->
<event:pageunload />
</head>
<body onload="paintRows()">
<!-- Start Global Headers  -->
    <wf:nav />
    <wf:subNav />
<!-- End Global Headers -->

<!-- #container is the container that wraps around all the main page content -->
<div id="container">
  <!-- begin "overview and instructions" area -->
  <div id="overview" class="box2">
    <h3>Overview and instructions</h3>
    <p>It is now time to vote for the package (or packages) you are willing to recommend to decision makers. You have until midnight <strong><fmt:formatDate value="${current.endTime}" dateStyle="long" /></strong> to submit your vote. You can only vote once, and you cannot change your vote. The results of this vote will be included in a report to decision makers which you can review in Step 5.</p>
    <a href="#" onclick="Effect.toggle('hiddenRM','blind'); return false;adjustMapPosition();">Read more about this step</a>
		<p id="hiddenRM" style="display:none">The purpose of this vote is to determine which package can get the greatest degree of collective support by <em>LIT Challenge</em> participants. Feel free to refer to the package poll or discussion in Step 4a before casting your vote. Keep in mind that a recommendation that is supported by a strong majority of participants is likely to carry more weight. In the event that strong majority consensus does not emerge, the moderator will identify a minority endorsement package based on an analysis of final vote results.</p>
  </div>

  <!-- end overview -->
	<!-- begin Object -->
	<div id="object">
		<!-- begin obj-left -->
		<!-- begin one voting box -->
		<jsp:useBean id="now" class="java.util.Date" />
        
		<h3 class="headerColor">Preliminary vote results</h3>
		Results as of <fmt:formatDate value="${now}" dateStyle="full" /> <fmt:formatDate value="${now}" type="time" /></h3>
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
		
		<p><a href="userhome.do?workflowId=${requestScope['org.pgist.wfengine.WORKFLOW_ID']}">Back to Overview</a></p>
		<!--		
		<h2 class="headerColor">Previous polls</h2>
		<c:if test="${fn:length(pVoteSuites) == 0}">
			<p>There have not been any previous polls.</p>
		</c:if>


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
	-->
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
<!-- Start Global Headers  -->
    <wf:subNav />
<!-- End Global Headers -->
<!-- Begin footer -->
<div id="footer">
	<jsp:include page="/footer.jsp" />
</div>
<!-- End footer -->
</body>
</html>
