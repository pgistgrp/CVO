<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
	<title>Tutorial Page 1</title>
	<!-- Site Wide CSS -->
	<style type="text/css" media="screen">
@import "styles/lit.css";

#container h3 {font-size:1.5em;margin:2em auto 10px auto;}
#container {font-size:12pt;}
.arrow,.arrow2 {
position:absolute;
font-size:12pt;
}

#keywordIt .arrow {
top:130px;
_top:110px; /* IE value */
right:270px;
}

#kw-icon .arrow {
bottom:10px;
right:50px;
background:red;
}

#kw-cloud .arrow {
bottom:-20px;
_bottom:-10px; /* IE value */
right:50px;
}

#kw-cloud {margin:1em auto}

#kw-discussion .arrow {
top:-5px;
_top:5px; /* IE value */
left:300px;
}

#kw-discussion .arrow span {margin-left:0px;margin-right:20px;}

#kw-browse .arrow {
top:100px;
_top:110px; /* IE value */
left:520px;
}

#kw-tagsize .arrow {
position:absolute;
top:-5px;
_top:5px; /* IE value */
left:530px;
}

#kw-tagsize .arrow2 {
position:absolute;
top:160px;
_top:170px; /* IE value */
left:530px;
}

.arrow span, .arrow1 span, .arrow2 span {font-size:2em;margin-left:20px;}

/* Step progress bar */

#step-bar {margin-bottom:1em;padding:10px;}
#step-progress {width:150px;margin:0px 5px;font-size:1.5em;text-align:center;}
#submit-description {width:550px;margin:0px;}
#submit-button {padding:5px;margin:0px;}
#submit-button input {font-size:1.5em}
#step-bar p{margin:0px;padding:0px;}

</style>
	<!-- End Site Wide CSS -->
	<!-- Site Wide JS -->
	<script src="scripts/prototype.js" type="text/javascript"></script>
	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<script src="scripts/search.js" type="text/javascript"></script>
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/util.js'></script>
	<script type='text/javascript' src='/scripts/flowplayer-3.1.4.min.js'></script>
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
			<div class="floatLeft headerButton"> <pg:url page="lmMenu.do">Menu</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmAbout.do">About VCC</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmFaq.do">FAQ</pg:url> </div>
			<div class="floatLeft headerButton currentBox"> <pg:url page="lmTutorial1.do">Tutorial</pg:url> </div>
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
		<h2> How to register for Voicing Climate Concerns</h2>
		<p>
			<a href="images/vcc0Registration.flv" style="display:block;width:480px;height:360px"  id="step0"> </a> 
			<script>
				flowplayer("step0", "scripts/flowplayer-3.1.5.swf", { clip: { autoPlay: false, autoBuffering: true} } );
			</script>
		</p>
		<h2>Step 1a: Brainstorm Keyphrases</h2>
		<p>
			<a href="images/vcc1aBrainstorm.flv" style="display:block;width:480px;height:360px"  id="step1a"> </a> 
			<script>
				flowplayer("step1a", "scripts/flowplayer-3.1.5.swf", { clip: { autoPlay: false, autoBuffering: true} } );
			</script>
		</p>
		<h2>Step 1b: Assess Keyphrases</h2>
		<p>
			<a href="images/vcc1bAssessKeyphrases.flv" style="display:block;width:480px;height:360px"  id="step1b"> </a> 
			<script>
				flowplayer("step1b", "scripts/flowplayer-3.1.5.swf", { clip: { autoPlay: false, autoBuffering: true} } );
			</script>
		</p>
		<h2>Step 2a: Create Indicator Labels</h2>
		<p>
			<a href="images/vcc2aIndicatorLabels.flv" style="display:block;width:480px;height:360px"  id="step2a"> </a> 
			<script>
				flowplayer("step2a", "scripts/flowplayer-3.1.5.swf", { clip: { autoPlay: false, autoBuffering: true} } );
			</script>
		</p>
		<h2>Step 2b: Assess Indicator Labels</h2>
		<p>
			<a href="images/vcc2bAssessIndicator.flv" style="display:block;width:480px;height:360px"  id="step2b"> </a> 
			<script>
				flowplayer("step2b", "scripts/flowplayer-3.1.5.swf", { clip: { autoPlay: false, autoBuffering: true} } );
			</script>
		</p>
		<h2>Step 3a: Endorse Labels and Build Index(es) as Needed</h2>
		<p>
			<a href="images/vcc3aEndorseLabels.flv" style="display:block;width:480px;height:360px"  id="step3a"> </a> 
			<script>
				flowplayer("step3a", "scripts/flowplayer-3.1.5.swf", { clip: { autoPlay: false, autoBuffering: true} } );
			</script>
		</p>
		<h2>Step 3b: Assess Indicator Labels and Index(es)</h2>
		<p>
			<a href="images/vcc3bAssessIndicatorIndexes.flv" style="display:block;width:480px;height:360px"  id="step3b"> </a> 
			<script>
				flowplayer("step3b", "scripts/flowplayer-3.1.5.swf", { clip: { autoPlay: false, autoBuffering: true} } );
			</script>
		</p>
		<h2>Step 4a: Develop Indicators</h2>
		<p>
			<a href="images/vcc4aDevelopIndicators.flv" style="display:block;width:480px;height:360px"  id="step4a"> </a> 
			<script>
				flowplayer("step4a", "scripts/flowplayer-3.1.5.swf", { clip: { autoPlay: false, autoBuffering: true} } );
			</script>
		</p>
		<h2>Step 4b: Assess Indicators</h2>
		<p>
			<a href="images/vcc4bAssessIndicators.flv" style="display:block;width:480px;height:360px"  id="step4b"> </a> 
			<script>
				flowplayer("step4b", "scripts/flowplayer-3.1.5.swf", { clip: { autoPlay: false, autoBuffering: true} } );
			</script>
		</p>
		
		
	</div>
	<!-- end container -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu">
		<div id="headerContainer">
			<div id="headerTitle" class="floatLeft">
				<h3 class="headerColor">Learn More</h3>
			</div>
			<div class="floatLeft headerButton"> <pg:url page="lmMenu.do">Menu</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmAbout.do">About VCC</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmFaq.do">FAQ</pg:url> </div>
			<div class="floatLeft headerButton currentBox"> <pg:url page="lmTutorial1.do">Tutorial</pg:url> </div>
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
</html>
