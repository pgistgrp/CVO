<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
	<head>
	<title>Let's Improve Transportation - Learn More FAQ</title>
	<!-- Site Wide CSS -->
	<style type="text/css" media="screen">
@import "styles/lit.css";

#container {font-size:12pt;}
#container h3 {font-size:1.5em;}

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
			<div class="floatLeft headerButton"> <a href="lmMenu.do">Home</a> </div>
			<div class="floatLeft headerButton"> <a href="lmAbout.do">About LIT</a> </div>
			<div class="floatLeft headerButton currentBox"> <a href="lmFaq.do">FAQ</a> </div>
			<div class="floatLeft headerButton"> <a href="lmTutorial1.do">Tutorial</a> </div>
			<div class="floatLeft headerButton"> <a href="lmGallery.do">Project Gallery</a> </div>
			<div class="floatLeft headerButton"> <a href="glossaryPublic.do">Glossary</a> </div>
			<div class="floatLeft headerButton"> <a href="lmResources.do">More Resources</a> </div>
		</div>
	</div>
	<!-- End header menu -->
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
		<h2 class="headerColor">Frequently Asked Questions</h2>
		<p>Here are answers to some of the most common questions participants ask.</p>
		
		<a name="step1"><h3 class="headerColor">Step 1: Discuss Concerns</h3></a>
		
		<a name="step1-keyword"><h4 class="headerColor">What is a keyword?</h4></a>
		<p>Think of keywords as labels. These labels help make your concerns easier to find among the many other concerns 	provided by other participants. Keywords are important for this first step in our process, as it assists the moderator in 	finding and clustering concerns so that summaries can be written. It is important, therefore, to provide keywords for your 	concerns with words or word phrases that you feel appropriately convey the meaning of your concern.</p>
		
		<a name="step1-created"><h4 class="headerColor">How are these summaries created?</h4></a>
		<p>	As participants add concerns, the moderator will review a subset of these concerns by sampling the keywords 	participants used to label their concerns. While reading the concerns, the moderator will place these concerns into 	topical themes, by creating themes and adding keywords to these themes. Due to the many concerns added by 	participants, it is possible that the moderator will not be able to review every concern, or every group of concerns 	keyworded with the same keyword. Therefore, your responsibility as a participant is to review these summaries and 	discuss how these summaries might be re-written differently.</p>
		
		<a name="step1-reads"><h4 class="headerColor">Who reads these summaries?</h4></a>
		<p>	You and your fellow participants will initially read and review these summaries. The moderator may revise these	 	summaries based on participant feedback and discussion. At the conclusion of this step, the summaries will cease to be 	updated and will be included in a final report given to decision makers. All the comments and discussion about these 	summaries will also be included, to showcase the variety of opinions.</p>
		
		<a name="step1-important"><h4 class="headerColor">Why are these summaries important?</h4></a>
		<p>	When a significant number of participants indicate that they disagree with a summary, the moderator will review 
	discussions and suggested revisions by participants. If it appears that a revision is necessary, the moderator will make 	changes and notify participants. At the conclusion of this step, the summaries will no longer be revised. Some 	participants may disagree with the 'final' version of a summary. These participants can express this disagreement in the 
	poll as well as their discussion comments about the summary. These comments will always be available for review to all 
	participants and will also be available to decision makers as an appendix to the final report.</p>
		
		<a name="step1-revised"><h4 class="headerColor">How are summaries revised?</h4></a>
		<p>	When a significant number of participants indicate that they disagree with a summary, the moderator will review 	discussions and suggested revisions by participants. If it appears that a revision is necessary, the moderator will make 	changes and notify participants. At the conclusion of this step, the summaries will no longer be revised. Some 	participants may disagree with the 'final' version of a summary. These participants can express this disagreement in the 	poll as well as their discussion comments about the summary. These comments will always be available for review to all 	participants and will also be available to decision makers as an appendix to the final report.</p>
		<br />
	</div>
	<!-- end container -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu">
		<div id="headerContainer">
			<div id="headerTitle" class="floatLeft">
				<h3 class="headerColor">Learn More</h3>
			</div>
			<div class="floatLeft headerButton"> <a href="lmMenu.do">Home</a> </div>
			<div class="floatLeft headerButton"> <a href="lmAbout.do">About LIT</a> </div>
			<div class="floatLeft headerButton currentBox"> <a href="lmFaq.do">FAQ</a> </div>
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
