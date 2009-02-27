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
			<div class="floatLeft headerButton"> <pg:url page="lmGallery.do">Maps</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="glossaryPublic.do">Glossary</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmResources.do">More Resources</pg:url> </div>
		</div>
	</div>
	<!-- End header menu -->
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
		<div id="step-bar" class="box10 padding6 clearfix">
				<p class="floatLeft" id="submit-button"><input type="button" value="Next Page" style="font-size:1.2em;" onclick="location.href='lmTutorial2.do'"/></p>
				<p class="floatRight" id="step-progress">Page 1 of 2</p>
		</div>
		<h2>The "Tutorial" section is not being supported. Please register or return "Home."</h2>
		
		
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
