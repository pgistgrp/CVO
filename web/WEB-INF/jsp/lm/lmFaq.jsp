<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
	<head>
	<title>FAQ</title>
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
	<script language="JavaScript" src="scripts/qtip.js" type="text/JavaScript"></script>
	<script src="scripts/prototype.js" type="text/javascript"></script>
	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<!--<script src="scripts/search.js" type="text/javascript"></script>-->
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/util.js'></script>
	</head>
	<body>
	<wf:nav />
	<!-- Begin header menu - The wide ribbon underneath the logo -->
    <div id="headerMenu">
        <div id="headerContainer">
            <div id="headerTitle" class="floatLeft">
                <h3 class="headerColor">Learn More About</h3>
            </div>
            <div class="floatLeft headerButton"> <pg:url page="lmMenu.do">Overview</pg:url> </div>
            <div class="floatLeft headerButton"> <pg:url page="lmAbout.do">Climate Concerns</pg:url> </div>
            <div class="floatLeft headerButton currentBox"> <pg:url page="lmFaq.do">FAQ</pg:url> </div>
            <div class="floatLeft headerButton"> <pg:url page="lmTutorial1.do">Tutorial</pg:url> </div>
            <!--
            <div class="floatLeft headerButton"> <pg:url page="lmGallery.do">Maps</pg:url> </div>
            <div class="floatLeft headerButton"> <pg:url page="glossaryPublic.do">Glossary</pg:url> </div>
            <div class="floatLeft headerButton"> <pg:url page="lmResources.do">More Resources</pg:url> </div>
            -->
        </div>
    </div>

	<!-- End header menu -->
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
    
    <!-- <pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id="> -->
	
    	<h2 class="headerColor">Frequently Asked Questions</h2>

        <h3>Can I participate in this experiment?</h3>
        <p>Any Oregon Coastal community stakeholders can participate in the experiment.</p>
        
        <h3>How much should I contribute?</h3>
        <p>You may contribute as many concerns, indicators, etc. as you feel comfortable with. 
        We want to hear about what is important to you.</p> 

        <h3>Is this project associated with other climate change outreach project at Oregon State University? </h3>
        <p>No, Voicing Climate Concerns is a separate project.</p>

       <h2 class="headerColor"></h2>
        <p>
        </p>
        
        
        
        
		<br /></pg:termHighlight>
	</div>
	<!-- end container -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
    <div id="headerMenu">
        <div id="headerContainer">
            <div id="headerTitle" class="floatLeft">
                <h3 class="headerColor">Learn More About</h3>
            </div>
            <div class="floatLeft headerButton"> <pg:url page="lmMenu.do">Overview</pg:url> </div>
            <div class="floatLeft headerButton"> <pg:url page="lmAbout.do">Climate Concerns</pg:url> </div>
            <div class="floatLeft headerButton currentBox"> <pg:url page="lmFaq.do">FAQ</pg:url> </div>
            <div class="floatLeft headerButton"> <pg:url page="lmTutorial1.do">Tutorial</pg:url> </div>
            <!--
            <div class="floatLeft headerButton"> <pg:url page="lmGallery.do">Maps</pg:url> </div>
            <div class="floatLeft headerButton"> <pg:url page="glossaryPublic.do">Glossary</pg:url> </div>
            <div class="floatLeft headerButton"> <pg:url page="lmResources.do">More Resources</pg:url> </div>
            -->
        </div>
    </div>

	<!-- End header menu -->
	<!-- Begin footer -->
	<!-- End footer -->
	</body>
</html:html>
