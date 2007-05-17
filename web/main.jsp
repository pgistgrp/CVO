<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
	<head>
	<title>Let's Improve Transportation - Home</title>
	<!-- Site Wide CSS -->
	<style type="text/css" media="screen">
@import "styles/lit.css";
@import "styles/user-home.css";
@import "styles/workflow.css";
</style>
	<!-- End Site Wide CSS -->
	<!-- Site Wide JS -->
	<script type="text/javascript" src="scripts/tinymce/jscripts/tiny_mce/tiny_mce.js"></script>
	<script src="scripts/prototype.js" type="text/javascript"></script>
	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<script src="scripts/search.js" type="text/javascript"></script>
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/util.js'></script>
	<script type='text/javascript' src='/dwr/interface/WorkflowAgent.js'></script>
	<script type='text/javascript' src='/scripts/workflow.js'></script>
	<script>
	  var workflow = new Workflow('workflow-panel');
	</script>
	<script type="text/javascript">
	

	</script>
	<event:pageunload />
	</head>
	
	<body onload="workflow.getWorkflows();">
	<!-- Begin the header - loaded from a separate file -->
	<div id="header">
		<!-- Begin header -->
		<jsp:include page="/header.jsp" />
		<!-- End header -->
	</div>
	<!-- End header -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu"></div>
	<!-- End header menu -->
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
		<h2 class="headerColor">Welcome, ${baseuser.loginname}</h2>
		<h3 class="headerColor">Please select an expiriment</h3>
		<div id="manager">
			<pg:show roles="moderator">
				<div><a class="wfblue" href="javascript: workflow.getTemplates();">Templates</a><a class="wfgreen" href="javascript: workflow.getWorkflows();">Workflows</a></div>
			</pg:show>
			<div id="workflow-panel"></div>
			
		</div>

		
	</div>
	<!-- end container -->
	<!-- start feedback form -->
	<pg:feedback id="feedbackDiv" action="cctView.do"/>
	<!-- end feedback form -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu"></div>
	<!-- Begin footer -->
	<div id="footer">
		<jsp:include page="/footer.jsp" />
	</div>
	<!-- End footer -->
	</body>
</html:html>
