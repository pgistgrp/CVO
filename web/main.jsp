<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>Let's Improve Transportation!</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/position.css";</style>
<style type="text/css" media="screen">
@import "styles/styles.css";
.style1 {color: #808080}
</style>

<!-- End Site Wide CSS -->
<!-- Site Wide JS -->
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
</head>

<body>
<!-- Header -->
<jsp:include page="/header.jsp" />
<!-- End Header -->

<h3>Welcome, ${baseuser.loginname}</h3>
<ul>
	<pg:show roles="participant">
<li>
  <html:link page="/cctlist.do">Step 1a: Brainstorm Concerns </html:link> [This step has finished.  New concerns will not be included in summaries]
</li>

<li>
  <html:link page="/sd.do?isid=2951">Step 1b: Discuss Summaries</html:link>
</li>
</pg:show> 
<li>
  <html:link page="/glossaryPublic.do">Learn More: Public Glossary</html:link> 
</li>
</ul>

<pg:show roles="moderator">
<h3>Public Components</h3>
<ul>
	<li><html:link page="#">Public Home</html:link></li>
	<li>Step 1</li>
		<ul>
			<li><html:link page="cctlist.do">Brainstorm Concerns</html:link></li>
			<li><html:link page="sdList.do">Discuss Summaries</html:link></li>
		</ul>
	<li>Step 2</li>
		<ul>
			<li><html:link page="#">Review Criteria</html:link></li>
			<li><html:link page="#">Weigh Criteria</html:link></li>
		</ul>
	<li>Step 3</li>
		<ul>
			<li><html:link page="#">Review Projects</html:link></li>
			<li><html:link page="#">Express Funding Packages</html:link></li>
			<li><html:link page="#">Create Personal Package</html:link></li>
		</ul>
	<li>Step 4</li>
		<ul>
			<li><html:link page="#">Review Packages</html:link></li>
		</ul>
	<li>Step 5</li>
		<ul>
			<li><html:link page="#">Review Report</html:link></li>
		</ul>
</ul>
<h3>Moderator Tools</h3>
<ul>
	<li><html:link page="#">Reviewing Feedbacks</html:link></li>
	<li><html:link page="#">Contact Participants</html:link></li>
	<li><html:link page="#">Create a Voting Poll</html:link></li>
	<li><html:link page="#">Manage Workflow</html:link></li>
	<li>Step 0</li>
		<ul>
			<pg:show users="admin"><li><a href="/userlist.do">User Management</a></li></pg:show>
			<li><html:link page="/tagging.do">Tags/StopWords Management Tool</html:link></li>
			<li><html:link page="/glossaryManage.do">Glossary Management Tool</html:link></li>
		</ul>
	<li>Step 1</li>
		<ul>
			<li><html:link page="/cstlist.do">Concerns Synthesis Tool</html:link></li>
			<li><html:link page="/sdList.do">Step 1b: Discuss Summaries</html:link></li>
		</ul>
	<li>Step 2</li>
		<ul>
			<li><html:link page="/criteriaMgr.do">Criteria Management Tool</html:link></li>
			<li><html:link page="/criteriaPublish.do">Criteria Publish Tool</html:link></li>
		</ul>
	<li>Step 3</li>
		<ul>
			<li><html:link page="/editProject.jsp">Test Edit Projects</html:link></li>
			<li><html:link page="/projectMgr.do">Projects Management Tool</html:link></li>
			<li><html:link page="/projectPublish.do">Projects Publish Tool</html:link></li>
			<li><html:link page="/fundingMgr.do">Funding Source Management Tool</html:link></li>
			<li><html:link page="/fundingPublish.do">Funding Source Publish Tool</html:link></li>
		</ul>
	<li>Step 4</li>
		<ul>
			<li>Packages Management Tool</li>
			<li>Packages Publish Tool</li>
		</ul>
</ul>

<h3>Development Tools</h3>
<ul>
	<li><html:link page="/thematic_map.htm">Test Thematic Map</html:link></li>
	<li><html:link page="/concernManagement.do">Concern Management</html:link></li>
	<li><html:link page="/search.do">Search</html:link></li>
	<li><html:link page="/situationList.do">Situation List</html:link></li>
	<li><html:link page="/pgist-docs/index.html">Javadoc</html:link></li>
	<li><html:link page="/dwr/index.html">DWR Test</html:link></li>
</ul>
</pg:show>
</div>

	<pg:feedback id="feedbackDiv" action="login.do" />
<script src="http://www.google-analytics.com/urchin.js" type="text/javascript">
</script>
<script type="text/javascript">
_uacct = "UA-797433-1";
urchinTracker();
</script>
</body>
</html:html>

