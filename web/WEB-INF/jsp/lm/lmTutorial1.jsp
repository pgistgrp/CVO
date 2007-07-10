<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
	<head>
	<title>Let's Improve Transportation - Learn More Tutorial</title>
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
			<div class="floatLeft headerButton"> <a href="lmMenu.do">Menu</a> </div>
			<div class="floatLeft headerButton"> <a href="lmAbout.do">About LIT</a> </div>
			<div class="floatLeft headerButton"> <a href="lmFaq.do">FAQ</a> </div>
			<div class="floatLeft headerButton currentBox"> <a href="lmTutorial1.do">Tutorial</a> </div>
			<div class="floatLeft headerButton"> <a href="lmGallery.do">Project Gallery</a> </div>
			<div class="floatLeft headerButton"> <a href="glossaryPublic.do">Glossary</a> </div>
			<div class="floatLeft headerButton"> <a href="lmResources.do">More Resources</a> </div>
		</div>
	</div>
	<!-- End header menu -->
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
		<div id="step-bar" class="box10 padding6 clearfix">
				<p class="floatLeft" id="submit-button"><input type="button" value="Next Page" style="font-size:1.2em;" onclick="location.href='lmTutorial2.do'"/></p>
				<p class="floatRight" id="step-progress">Page 1 of 2</p>
		</div>
		<h2>Tutorial</h2>
		<p>There are some cool features on this website we’d like you to know about, so
			you can better make use of them!</p>
		<h2>Keyword your concerns, discussion posts, and the map!</h2>
		<p>By adding keywords to things you care about, you can direct other users to specific
			concerns, discussion posts, and the points on the map. There are two things to
			keep in mind throughout your use of Let’s Improve Transportation.</p>

		<br />

		<div id="keywordit" style="position:relative;"> 
		<img src="images/lm-keywordit.png" class="floatRight" />
			<h3>1. Care about it? Keyword it!</h3>
			<p>After every concern you enter, and after every post to our discussion forums,
				you may choose to add keywords to categorize your post. Even places on the map
				can be keyworded.</p>
			<p class="arrow">Add keywords to your concerns for safe keeping. <span>&rarr;</span></p>
			<br class="clearBoth" />
		</div>
		
		<br />

		<div id="kw-discussion" style="position:relative;" class="peekaboobugfix">
			<p class="arrow"><span>&larr;</span> Add keywords to your discussion posts too. </p>
			<img src="images/lm-kwdiscussion.gif" class="floatLeft"/>
			<br class="clearBoth" />
		</div>		
		
		<br />
		
		<div id="kw-cloud" style="position:relative;" class="peekaboobugfix">
		<h3>2. Need to find it? Use the Keyword Cloud?</h3>
		<p>The Keyword Cloud allows you to review the keywords that your co-contributors
			are using. Select a keyword in the cloud to find discussion posts or concerns
			that use that keyword. It can also be used to find keywords on a map. It’s a great
			way to browse what’s out there! You can also use Search.</p>
			<p class="arrow">Click this button to use the Keyword Cloud <span>&rarr;</span></p>
			<img src="images/keyword-cloud.gif" class="floatRight"/>
			<br class="clearBoth" /> 
		</div>

		<br />

		<div id="kw-browse" style="position:relative;" class="peekaboobugfix">
			<p class="arrow"><span>&larr;</span> Add keywords to your discussion posts too. </p>
			<img src="images/lm-browsekw.gif" class="floatLeft"/>
			<br class="clearBoth" />
		</div>			
		
		<br />

		<div id="kw-tagsize" style="position:relative;" class="peekaboobugfix">
			<p class="arrow">Smaller keywords are less popular<span>&rarr;</span></p>
			<p class="arrow2">Larger keywords are more popular<span>&rarr;</span></p>
			<img src="images/lm-kwsize.gif" class="floatRight"/>
			<br class="clearBoth" />
		</div>			

		<br />
		<div id="step-bar" class="box10 padding6 clearfix">
				<p class="floatLeft" id="submit-button"><input type="button" value="Next Page" style="font-size:1.2em;" onclick="location.href='lmTutorial2.do'"/></p>
				<p class="floatRight" id="step-progress">Page 1 of 2</p>
		</div>
		
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
			<div class="floatLeft headerButton currentBox"> <a href="lmTutorial1.do">Tutorial</a> </div>
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
