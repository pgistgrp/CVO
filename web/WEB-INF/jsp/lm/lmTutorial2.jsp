<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
	<title>Learn More Tutorial - Page 2</title>
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
			<div class="floatLeft headerButton"> <pg:url page="lmAbout.do">About LIT</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmFaq.do">FAQ</pg:url> </div>
			<div class="floatLeft headerButton currentBox"> <pg:url page="lmTutorial1.do">Tutorial</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmGallery.do">Project Map</pg:url> </div>
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
		<h2>Tutorial - Page 2 of 2</h2>
		<h3>The map is yours too!</h3>
			<p>During the discussions on Let's Improve Transportation, you will find the map useful for exploring proposed transportation projects and proposals for funded transportation packages.  You will also find that you and your co-contributors can add to our maps, by adding keywords to specific places.  Refer to this diagram below, which identifies the various controls on the map.</p>
		<br>
		
		<div id="map"  class="floatLeft peekaboobugfix">
			<div class="floatLeft" id="mapLegend">
				<p><div class="icon"><img src="images/lm-mapIcon1.png"></div>
				<div class="value">Select the Keyword Cloud to choose which keywords to display on the map.</div></p>

				<p><div class="icon"><img src="images/lm-mapIcon2.png"></div>
				<div class="value">Turn on or turn off the satellite imagery on the map. </div></p>

				<p><div class="icon"><img src="images/lm-mapIcon3.png"></div>
				<div class="value">Use the scale bar to zoom in or out of the map.</div></p>
				
				<p><div class="icon"><img src="images/lm-mapIcon4.png"></div>
				<div class="value">Hold down [ctrl] while clicking to add your keywords to the map.</div></p>
				
				<p><div class="icon"><img src="images/lm-mapIcon5.png"></div>
				<div class="value">Click and hold to move around the map, just like Google Maps!</div></p>

				<p><div class="icon"><img src="images/lm-mapIcon6.png"></div>
				<div class="value">Click on a project to display its information.</div></p>

			</div>
			<div id="mapCell" style="position: relative;" class="floatRight">
				<img src="images/lm-map.png">
				<img src="images/lm-mapPointer1.png" style="position:absolute;top:11px;_top:21px;left:250px;">
				<img src="images/lm-mapPointer2.png" style="position:absolute;top:11px;_top:21px;left:310px;">
				<img src="images/lm-mapPointer3.png" style="position:absolute;top:141px;_top:151px;left:32px;">
				<img src="images/lm-mapPointer4.png" style="position:absolute;top:200px;_top:210px;left:300px;">
				<img src="images/lm-mapPointer5.png" style="position:absolute;top:300px;_top:310px;left:100px;">
				<img src="images/lm-mapPointer6.png" style="position:absolute;top:400px;_top:360px;left:300px;">		
			</div>
				
			<br class="clearBoth">
		</div>		
		
		<br>

		<h3>Find conversations to join using our discussion search bar!</h3>
		<p>In order to really find the conversations that interest you, you'll probably want to become familiar with the discussion search bar, located on each discussion page in Let's Improve Transportation.</p>


		<div id="map"  class="floatLeft peekaboobugfix">
			<div class="floatLeft" id="filterLegend">
				<p><div class="icon"><img src="images/lm-filtericon1.png"></div>
				<div class="value">Enter text to search for discussions.</div></p>

				<p><div class="icon"><img src="images/lm-filtericon2.png"></div>
				<div class="value">Browse by keywords using the Keyword Cloud.</div></p>

				<p><div class="icon"><img src="images/lm-filtericon3.png"></div>
				<div class="value">Find recent posts, or dust off some older posts.</div></p>
				
				<p><div class="icon"><img src="images/lm-filtericon4.png"></div>
				<div class="value">Didn't find what you're looking for? Start a new discussion!</div></p>
				

			</div>
			<div id="filterCell" style="position: relative;" class="floatRight">
				<img src="images/lm-filterbar.png" style="margin-top:80px">
				<img src="images/lm-filterpointer1.png" style="position:absolute;top:61px;left:120px;">
				<img src="images/lm-filterpointer2.png" style="position:absolute;top:135px;left:290px;">
				<img src="images/lm-filterpointer3.png" style="position:absolute;top:135px;left:480px;">
				<img src="images/lm-filterpointer4.png" style="position:absolute;top:30px;left:505px;">
			</div>

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
			<div class="floatLeft headerButton"> <pg:url page="lmAbout.do">About LIT</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmFaq.do">FAQ</pg:url> </div>
			<div class="floatLeft headerButton currentBox"> <pg:url page="lmTutorial1.do">Tutorial</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmGallery.do">Project Map</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="glossaryPublic.do">Glossary</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmResources.do">More Resources</pg:url> </div>
		</div>
	</div>
	<!-- End header menu -->
	<!-- Begin footer -->
	<!-- End footer -->
	</body>
</html>
