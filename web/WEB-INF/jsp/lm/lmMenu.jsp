<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
	<head>
	<title>Let's Improve Transportation - Learn More Home</title>
	<!-- Site Wide CSS -->
	<style type="text/css" media="screen">
@import "styles/lit.css";
#container {font-size:12pt;}
#container h3 {font-size:1.2em;margin-top:20px;}
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
	<div id="header">
		<!-- Begin header -->
			<jsp:include page="/header.jsp" />
		<!-- End header -->
	</div>
	<!-- End header -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu">
		<div id="headerContainer">
			<div id="headerTitle" class="floatLeft">
				<h3 class="headerColor">Learn More</h3>
			</div>
			<div class="floatLeft headerButton currentBox"> <a href="lmMenu.do">Home</a> </div>
			<div class="floatLeft headerButton"> <a href="lmAbout.do">About LIT</a> </div>
			<div class="floatLeft headerButton"> <a href="lmFaq.do">FAQ</a> </div>
			<div class="floatLeft headerButton"> <a href="lmTutorial1.do">Tutorial</a> </div>
			<div class="floatLeft headerButton"> <a href="lmGallery.do">Project Gallery</a> </div>
			<div class="floatLeft headerButton"> <a href="glossaryPublic.do">Glossary</a> </div>
			<div class="floatLeft headerButton"> <a href="lmResources.do">More Resources</a> </div>
		</div>
	</div>
	<!-- End header menu -->
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
		<h2 class="headerColor">Learn More</h2>
		<p class="nomargin">This is a small part of the larger Let’s Improve Transportation site. Most of
			Learn More is open to the public, and contains information about transportation
			decision making and transportation projects in our region. Here, you can… </p>
		<h3 class="headerColor"><a href="lmAbout.do">Read More</a> about Let's Improve Transportation</h3>
		<p>Information about this site and what we’re trying to accomplish</p>
		<h3 class="headerColor">Read our <a href="lmFaq.do">Frequently Asked Questions</a></h3>
		<p>Answers to specific questions about different steps in the process</p>
		<h3 class="headerColor">View a <a href="lmTutorial1.do">Tutorial</a> about the more advanced
			features of this site</h3>
		<p>If you’re not clear about keywords, maps, or other tools, this tutorial may
			be useful.</p>
		<h3 class="headerColor">Research proposed projects in the <a href="lmGallery.do">Project Gallery</a></h3>
		<p>View a list of every proposed transportation project, read detailed information
			on them, and view them on a dynamic map.</p>
		<h3 class="headerColor">Browse articles and other <a href="lmResources.do">Resources</a> about
			transportation planning</h3>
		<p>Don’t know much about transportation planning? That’s okay, we’ve collected
			some useful articles and links from around the Web that may be helpful.</p>
		<h3 class="headerColor">Look up a word in our interactive transportation <a href="glossaryPublic.do">Glossary</a>
		</h3>
		<p>If you run into a word you’re not familiar with, you can look it up here.</p>
		<br />
	</div>
	<!-- end container -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu">
		<div id="headerContainer">
			<div id="headerTitle" class="floatLeft">
				<h3 class="headerColor">Learn More</h3>
			</div>
			<div class="floatLeft headerButton currentBox"> <a href="lmMenu.do">Home</a> </div>
			<div class="floatLeft headerButton"> <a href="lmAbout.do">About LIT</a> </div>
			<div class="floatLeft headerButton"> <a href="lmFaq.do">FAQ</a> </div>
			<div class="floatLeft headerButton"> <a href="lmTutorial1.do">Tutorial</a> </div>
			<div class="floatLeft headerButton"> <a href="lmGallery.do">Project Gallery</a> </div>
			<div class="floatLeft headerButton"> <a href="glossaryPublic.do">Glossary</a> </div>
			<div class="floatLeft headerButton"> <a href="lmResources.do">More Resources</a> </div>
		</div>
	</div>
	<!-- End header menu -->
	<!-- Begin footer -->
	<!-- End footer -->
	</body>
</html:html>
