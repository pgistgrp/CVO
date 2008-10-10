<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
	<title>Tutorial Page 2</title>
	<!-- Site Wide CSS -->
<style type="text/css" media="screen">
@import "styles/lit.css";

#container h3 {font-size:1.5em;margin:2em auto 10px auto;}
#container {font-size:12pt;}
.arrow {
position:absolute;
font-size:12pt;
}

.arrow span {font-size:2em;margin-left:20px;}

#mapLegend, #mapCell {margin-bottom:20px;}
#mapLegend{width:500px;}

#mapLegend .icon {float:left;margin:0px 0px 15px 0px}
#mapLegend p {clear:both;}
#mapLegend .icon img {vertical-align:middle;}

#mapLegend .value {float:right;width:440px;margin:0px 0px 15px 0px}

#mapCell{width:432px;}

#filterLegend{width:250px}
#filterCell{}

#filterLegend, #filterCell {margin-bottom:20px;}
#filterLegend .icon {float:left;margin:0px 0px 15px 0px}
#filterLegend p {clear:both;}
#filterLegend .value {float:right;width:195px;margin:0px 0px 15px 0px}

/* Step progress bar */

#step-bar {margin-bottom:1em;padding:10px;}
#step-progress {width:150px;margin:0px 5px;font-size:1.5em;text-align:center;}
#submit-description {width:550px;margin:0px;}
#submit-button {padding:5px;margin:0px;}
#submit-button input {font-size:1.5em}
#step-bar p{margin:0px;padding:0px;}


</style>

</style>
	<!-- End Site Wide CSS -->
	<!-- Site Wide JS -->
	<script src="scripts/prototype.js" type="text/javascript"></script>
	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<script src="scripts/search.js" type="text/javascript"></script>
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/util.js'></script>

<!--[if lt IE 7.]>
	<script defer type="text/javascript" src="scripts/pngfix.js"></script>
<![endif]-->

	</head>
	<body>
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
			<div class="floatLeft headerButton"> <pg:url page="lmGallery.do">Maps</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="glossaryPublic.do">Glossary</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmResources.do">More Resources</pg:url> </div>
		</div>
	</div>
	<!-- End header menu -->
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
		<div id="step-bar" class="box10 padding5 clearfix">
			<p class="floatLeft" id="submit-button"><input type="button" value="Previous Page" style="font-size:1.2em;" onclick="location.href='lmTutorial1.do'"/></p>
			<p class="floatRight" id="step-progress">Page 2 of 2</p>

		</div>	
		<h2>The "Tutorial" section is not being supported. Please register or return "Home."</h2>

		<br />
	</div>

		<div class="clearBoth"></div>
		<div id="step-bar" class="box10 padding5 clearfix">
			<p class="floatLeft" id="submit-button"><input type="button" value="Previous Page" style="font-size:1.2em;" onclick="location.href='lmTutorial1.do'"/></p>
			<p class="floatRight" id="step-progress">Page 2 of 2</p>

		</div>		
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
			<div class="floatLeft headerButton"> <pg:url page="lmGallery.do">Maps</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="glossaryPublic.do">Glossary</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmResources.do">More Resources</pg:url> </div>
		</div>
	</div>
	<!-- End header menu -->
	<!-- Begin footer -->
	<!-- End footer -->
	</body>
</html>
