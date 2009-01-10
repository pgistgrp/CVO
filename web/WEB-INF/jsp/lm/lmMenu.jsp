<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
<head>
	<title>Learn More About</title>
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
			<h3 class="headerColor">Learn More About</h3>
		</div>
		<div class="floatLeft headerButton currentBox"> <pg:url page="lmMenu.do">VCC</pg:url> </div>
		<div class="floatLeft headerButton"> <pg:url page="lmAbout.do">Climate Concerns</pg:url> </div>
		<div class="floatLeft headerButton"> <pg:url page="lmFaq.do">FAQ</pg:url> </div>
        <!--
		<div class="floatLeft headerButton"> <pg:url page="lmTutorial1.do">Tutorial</pg:url> </div>
		<div class="floatLeft headerButton"> <pg:url page="lmGallery.do">Maps</pg:url> </div>
		<div class="floatLeft headerButton"> <pg:url page="glossaryPublic.do">Glossary</pg:url> </div>
		<div class="floatLeft headerButton"> <pg:url page="lmResources.do">More Resources</pg:url> </div>
        -->
	</div>
</div>
	<!-- End header menu -->
	<!-- #container is the container that wraps around all the main page content -->
<div id="container">
	<h2 class="headerColor">Learn More</h2> 
	<p>In this section of the website any interested user can link to the <a href="http://www.coastalatlas.net"title="Oregon Coastal Atlas">Oregon Coastal Atlas</a> or learn more about the Voicing Climate Concerns experiment in participatory decision making. The entire website will be available to registered participants. <a href="register.do">Register now</a>.</p>
		<!-- Begin explanation -->

	<h2 class="headerColor">About</h2>
		
    <p>Voicing Climate Concerns is an online activity in which participants brainstorm concerns about 
    climate change and variability along the Oregon Coast and analysts produce maps depicting those 
    concerns contigent on availabity of data.</p>
    
    <p>
    There are 4 steps to complete this experiment.
    </p>
    
    <p>
    Images to show how it works.
    </p>
    
    <p>
    etc..
    </p>
		
	<br />
</div>
	<!-- end container -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu">
	<div id="headerContainer">
		<div id="headerTitle" class="floatLeft">
			<h3 class="headerColor">Learn More About</h3>
		</div>
		<div class="floatLeft headerButton currentBox"> <pg:url page="lmMenu.do">VCC</pg:url> </div>
		<div class="floatLeft headerButton"> <pg:url page="lmAbout.do">Climate Concerns</pg:url> </div>
		<div class="floatLeft headerButton"> <pg:url page="lmFaq.do">FAQ</pg:url> </div>
        <!--
		<div class="floatLeft headerButton"> <pg:url page="lmTutorial1.do">Tutorial</pg:url> </div>
		<div class="floatLeft headerButton"> <pg:url page="lmGallery.do">Maps</pg:url> </div>
		<div class="floatLeft headerButton"> <pg:url page="glossaryPublic.do">Glossary</pg:url> </div>
		<div class="floatLeft headerButton"> <pg:url page="lmResources.do">More Resources</pg:url> </div>
        -->
	</div>
</div>
	<!-- End header menu -->
</body>
</html:html>
