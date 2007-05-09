<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
	<head>
	<title>Let's Improve Transportation - Done with Step 1a</title>
	<!-- Site Wide CSS -->
	<style type="text/css" media="screen">
@import "styles/lit.css";
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
	<!-- Begin the header - loaded from a separate file -->
	<div id="header">
		<!-- Begin header -->
		<jsp:include page="/header.jsp" />
		<!-- End header -->
	</div>
	<!-- End header -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu">
		<div id="headerContainer">
		</div>
	</div>
	<!-- End header menu -->
	<!-- End header menu -->
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
		<div style="width:600px;">
			<h3 class="headerColor">Waiting for the next step to begin</h3>
			<p>Thanks for your input. You've completed this step in the <I>Let's
					Improve Transportation</I> challenge. Once everybody has completed this step, we will process the results and prepare the next step.</p>
				<p><strong>When the next step is ready, you will automatically receive an e-mail from the moderator</strong>, so be sure to check your inbox. You can also check the <a href="main.do">Home</a> page to see when the next step will open.</p>
		</div>
	</div>
	<!-- end container -->
	<!-- start feedback form -->
	<pg:feedback id="feedbackDiv" action="cctView.do"/>
	<!-- end feedback form -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu">
		<div id="headerContainer">
		</div>
	</div>
	<!-- End header menu -->
	<!-- Begin footer -->
	<div id="footer">
		<jsp:include page="/footer.jsp" />
	</div>
	<!-- End footer -->
	</body>
</html:html>
