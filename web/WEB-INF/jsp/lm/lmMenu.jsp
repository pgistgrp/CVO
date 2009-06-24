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
	<title>Learn More</title>
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
		<div class="floatLeft headerButton currentBox"> <pg:url page="lmMenu.do">Overview</pg:url> </div>
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

	<h2 class="headerColor">Overview</h2>
		
    <p>Voicing Climate Concerns is an online activity in which participants brainstorm concerns about 
    climate change and variability along the Oregon Coast and analysts produce maps depicting those 
    concerns contingent on availabity of data. Voicing Climate Concerns facilitates citizen involvement, 
    <a href="http://www.oregon.gov/LCD/docs/goals/goal1.pdf">Goal 1</a> of 
    <a href="http://www.oregon.gov/LCD/goals.shtml">Oregon’s Statewide Planning Goals and Guidelines</a></p>
    
    <p>
    There are 4 steps to complete this experiment.  Following the execution of each step, participants
    have the opportunity to review and comment about the group process.  As part of the review and 
    comment process, participants vote on whether the group is ready to proceed to the next step
    </p>
    
    <p>
    In Step 1, participants voice their concerns about climate change and attach key words or phrases
    of their choosing to each of their concerns
    </p>
    
    <p>
    In Step 2, participants create categories by clustering the keywords and phrases they and the other
    participants articulated in Step 1.
    </p>
    
    <p>
    In Step 3, participants create hierarchies out of the climate concern categories they and other 
    participants created in Step 2.
    </p>
    
    <p>
    In Step 4, participants develop indicators to address the concerns organized in step 3, drawing on 
    information provided by data analysts.
    </p>
    
    <p>
    In Step 5, a report on the outcome of the experiment will be generated, and participants will have 
    the opportunity to review and comment on the report.
    </p>
		
	<br />
</div>
	<!-- end container -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu">
	<div id="headerContainer">
		<div id="headerTitle" class="floatLeft">
			<h3 class="headerColor">Learn More</h3>
		</div>
		<div class="floatLeft headerButton currentBox"> <pg:url page="lmMenu.do">Overview</pg:url> </div>
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
