<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
	<head>
	<title>Let's Improve Transportation - Learn More Menu</title>
	<!-- Site Wide CSS -->
	<style type="text/css" media="screen">
@import "styles/lit.css";
#container {font-size:12pt;}
#container h3 {font-size:1.2em;margin:30px auto;}
p {margin-left:10px;margin-top:5px;}
p.nomargin {margin-left:0px;}

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
    <wf:nav />
	<!-- End header -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu">
		<div id="headerContainer">
			<div id="headerTitle" class="floatLeft">
				<h3 class="headerColor">Learn More</h3>
			</div>
			<div class="floatLeft headerButton currentBox"> <a href="lmMenu.do">Menu</a> </div>
			<div class="floatLeft headerButton"> <a href="lmAbout.do">About LIT</a> </div>
			<div class="floatLeft headerButton"> <a href="lmFaq.do">FAQ</a> </div>
			<div class="floatLeft headerButton"> <a href="lmTutorial1.do">Tutorial</a> </div>
			<div class="floatLeft headerButton"> <a href="lmGallery.do">Project Map</a> </div>
			<div class="floatLeft headerButton"> <a href="glossaryPublic.do">Glossary</a> </div>
			<div class="floatLeft headerButton"> <a href="lmResources.do">More Resources</a> </div>
		</div>
	</div>
	<!-- End header menu -->
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
		<h2 class="headerColor">Learn More</h2>
		<h3 class="headerColor"><a href="lmAbout.do">Read More about <em>Let's Improve Transportation</em></a></h3>
		<h3 class="headerColor"><a href="lmFaq.do">Read our Frequently Asked Questions</a></h3>
		<h3 class="headerColor"><a href="lmTutorial1.do">Learn about the more advanced features of this site</a></h3>
		<h3 class="headerColor"><a href="lmGallery.do">View a map of proposed transportation improvements</a></h3>
		<h3 class="headerColor"><a href="lmResources.do">Browse articles and other resources about transportation planning</a></h3>
		<h3 class="headerColor"><a href="glossaryPublic.do">Look up a word in our interactive transportation glossary</a></h3>
		<br />
	</div>
	<!-- end container -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu">
		<div id="headerContainer">
			<div id="headerTitle" class="floatLeft">
				<h3 class="headerColor">Learn More</h3>
			</div>
			<div class="floatLeft headerButton currentBox"> <a href="lmMenu.do">Menu</a> </div>
			<div class="floatLeft headerButton"> <a href="lmAbout.do">About LIT</a> </div>
			<div class="floatLeft headerButton"> <a href="lmFaq.do">FAQ</a> </div>
			<div class="floatLeft headerButton"> <a href="lmTutorial1.do">Tutorial</a> </div>
			<div class="floatLeft headerButton"> <a href="lmGallery.do">Project Map</a> </div>
			<div class="floatLeft headerButton"> <a href="glossaryPublic.do">Glossary</a> </div>
			<div class="floatLeft headerButton"> <a href="lmResources.do">More Resources</a> </div>
		</div>
	</div>
	<!-- End header menu -->
	<!-- Begin footer -->
	<!-- End footer -->
	</body>
</html:html>
