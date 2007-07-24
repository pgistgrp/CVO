<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
	<head>
	<title>Let's Improve Transportation - Resources</title>
	<!-- Site Wide CSS -->
	<style type="text/css" media="screen">
@import "styles/lit.css";

#resources p {margin-left:1em;}
#container {font-size:12pt;}
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
	<wf:nav />
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu">
		<div id="headerContainer">
			<div id="headerTitle" class="floatLeft">
				<h3 class="headerColor">Learn More</h3>
			</div>
			<div class="floatLeft headerButton"> <a href="lmMenu.do">Menu</a> </div>
			<div class="floatLeft headerButton"> <a href="lmAbout.do">About LIT</a> </div>
			<div class="floatLeft headerButton"> <a href="lmFaq.do">FAQ</a> </div>
			<div class="floatLeft headerButton"> <a href="lmTutorial1.do">Tutorial</a> </div>
			<div class="floatLeft headerButton"> <a href="lmGallery.do">Project Map</a> </div>
			<div class="floatLeft headerButton"> <a href="glossaryPublic.do">Glossary</a> </div>
			<div class="floatLeft headerButton currentBox"> <a href="lmResources.do">More Resources</a> </div>
		</div>
	</div>
	<!-- End header menu -->
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
		<h2 class="headerColor">Resources</h2>
		<p>Are you inspired to learn more about transportation planning in the central Puget Sound region? Here we recommend several websites to get your started. If you find resources that you feel should be listed here, please submit them to us by <a href="mailto:pgistfeedback@gmail.com">sending us an email</a>.</p>
		<!--<div id="resources">
			<h3 class="headerColor">Transportation agencies and decision-making bodies in
				our region</h3>
			<p><a href="#">Sample web site name</a><br />
				This is the description of that site, written by moderators and added by
					Adam. Lorem ipsum dolor sit amet, consectetuer adapiscing elit. </p>
			<p><a href="#">Sample web site name</a><br />
				This is the description of that site, written by moderators and added by
					Adam. Lorem ipsum dolor sit amet, consectetuer adapiscing elit. </p>
			<h3 class="headerColor">Transportation advocacy organization in our region</h3>
			<p> <a href="#">Sample web site name</a><br />
				This is the description of that site, written by moderators and added by
					Adam. Lorem ipsum dolor sit amet, consectetuer adapiscing elit. </p>
			<p> <a href="#">Sample web site name</a><br />
				This is the description of that site, written by moderators and added by
					Adam. Lorem ipsum dolor sit amet, consectetuer adapiscing elit. </p>
		</div>-->
	</div>
	<!-- end container -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu">
		<div id="headerContainer">
			<div id="headerTitle" class="floatLeft">
				<h3 class="headerColor">Learn More</h3>
			</div>
			<div class="floatLeft headerButton"> <a href="lmMenu.do">Menu</a> </div>
			<div class="floatLeft headerButton"> <a href="lmAbout.do">About LIT</a> </div>
			<div class="floatLeft headerButton"> <a href="lmFaq.do">FAQ</a> </div>
			<div class="floatLeft headerButton"> <a href="lmTutorial1.do">Tutorial</a> </div>
			<div class="floatLeft headerButton"> <a href="lmGallery.do">Project Gallery</a> </div>
			<div class="floatLeft headerButton"> <a href="glossaryPublic.do">Glossary</a> </div>
			<div class="floatLeft headerButton currentBox"> <a href="lmResources.do">More Resources</a> </div>
		</div>
	</div>
	<!-- End header menu -->
	<!-- Begin footer -->
	<!-- End footer -->
	</body>
</html:html>
