<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
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
  <!-- Start Global Headers  -->
  <wf:nav />
  <wf:subNav />
  <!-- End Global Headers -->
	</div>
	<!-- End header -->
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
		<h2 class="headerColor">Waiting for next step to begin</h2>
		<p>Thanks for completing this step in the LIT Challenge. Check the <a href="/main.do">agenda</a> to see when the next step begins. You will also receive an email reminder.</p>
		<p>Still looking for something to do?</p> 
		<p>Remember that "review projects" and "review funding options" are always available on the agenda. Go there to read about and discuss proposed transportation improvement projects and funding sources.</p> 
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
</html:html>
