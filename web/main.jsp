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
	<script type='text/javascript' src='/scripts/util.js'></script>
	<script>
	  var workflow = new Workflow('workflow-panel', 'newExpiriment');
	</script>
	<event:pageunload />
	</head>
	<body>
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
		<div id="workflow-panel"><!-- load workflow here --></div>
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
	<script type="text/javascript" charset="utf-8">
		var moderator = false;
	<pg:show roles="moderator">
		moderator = true;
	</pg:show>
	
	if(moderator){
		workflow.getWorkflows('mod');
	}else{
		workflow.getWorkflows();
	}


	</script>
	</body>
</html:html>
