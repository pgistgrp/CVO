<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
	<head>
	<title>Let's Improve Transportation - Learn More About LIT</title>
	<!-- Site Wide CSS -->
	<style type="text/css" media="screen">
@import "styles/lit.css";

#container h3 {font-size:1.5em;}
#container {font-size:12pt;}
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
			<div class="floatLeft headerButton currentBox"> <a href="lmAbout.do">About LIT</a> </div>
			<div class="floatLeft headerButton"> <a href="lmFaq.do">FAQ</a> </div>
			<div class="floatLeft headerButton"> <a href="lmTutorial1.do">Tutorial</a> </div>
			<div class="floatLeft headerButton"> <a href="lmGallery.do">Project Map</a> </div>
			<div class="floatLeft headerButton"> <a href="glossaryPublic.do">Glossary</a> </div>
			<div class="floatLeft headerButton"> <a href="lmResources.do">More Resources</a> </div>
		</div>
	</div>
	<!-- End header menu -->
	<!-- #container is the container that wraps around all the main page content -->
	 <div id="container">
 <h2 class="headerColor" style="text-transform:none"><a name="purpose" class="headerColor">What is <em>Let's Improve Transportation</em> and the <em>LIT Challenge</em>?</a></h2>
<p>Let's Improve Transportation (LIT) is an experiment in participatory democracy. A research team based at the University of Washington has developed an innovative new website where you can:</p>
    <ul>
      <li>learn about proposed improvements to the central Puget Sound regional transportation system,</li>
      <li>consider the potential impacts of these changes on our communities, and</li>
      <li>work collaboratively with other residents to determine which changes the region should support.</li>
    </ul>

<p>This October, residents of King, Pierce, and Snohomish counties are invited to participate in the LIT Challenge, a 4-week long online experiment in which 300 or more individuals will participate via the Internet at their own convenience. The purpose of this experiment is to evaluate a new and potentially more meaningful way to involve citizens in the process of regional transportation decision making.</p>
<p>Participants in the LIT Challenge are asked to imagine they are a member of a large citizen advisory committee charged with providing policy makers with their recommendations regarding the make-up of a regional transportation ballot measure. The (hypothetical) measure will ask voters in the region if they wish to raise taxes to pay for a package of road and transit improvement projects. The task of LIT Challenge participants is to determine which proposed transportation improvement projects to build and which funding mechanisms (such as taxes or bridge tolls) should be used to pay for these improvements. The challenging part is to get as many participants as possible to collectively recommend a single "package" of projects and funding options. Fortunately, the LIT website was designed to support exactly this kind of challenge. The website, as well as a small team of online moderators, will facilitate a process in which participants:</p>
<ul>
	<li>brainstorm concerns,</li>
	<li>assess different goals for transportation improvement,</li>
	<li>create personal "packages" of transportation projects and funding,</li>
	<li>discuss and evaluate 5 representative packages in order to determine which ones can gain the greatest degree of participant support,</li>
	<li>and compose a report to communicate the final recommendations to the public and policy makers.</li>
</ul>
<p>While this may sound complicated, the website is designed to lead any interested citizen through the process. No prior experience with transportation issues is necessary. It is estimated that full participation in the LIT Challenge will take as little as 8-10 hours over the four week period. Qualified participants who complete the LIT Challenge will receive compensation for their efforts.</p>
 <h3 class="headerColor" style="text-transform:none"><a name="realWorld" class="headerColor">How is LIT related to the (real world) "Roads and Transit" ballot measure in November 2007?</a></h3>
<p>In the real world, regional policy makers have already created a single "Roads and Transit" package and will be asking voters in King, Pierce, and Snohomish counties whether or not it should become law. The "Roads and Transit" package is the result of years of negotiations between county elected officials as well as pressure from businesses, transportation advocacy organizations, and concerned members of the public. Transportation funding ballot measures such as this one have become increasingly common in metropolitan regions across the U.S.</p>

<p>LIT is an attempt to design a more meaningful way to involve members of the public in regional transportation decision making before a single package is placed on the ballot. Therefore the outcome of the LIT Challenge will no effect on the make-up of the "Roads and Transit" package. However participants will have the opportunity to carefully examine the "Roads and Transit" package and compare it to alternative packages created based on participant input. Researchers also hope that the LIT Challenge will serve as a model for how to involve the public in future regional transportation decisions.</p>

<p>To learn more about this research project, go to <a href="http://www.pgist.org" target="_blank">www.pgist.org</a>.</p>

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
			<div class="floatLeft headerButton currentBox"> <a href="lmAbout.do">About LIT</a> </div>
			<div class="floatLeft headerButton"> <a href="lmFaq.do">FAQ</a> </div>
			<div class="floatLeft headerButton"> <a href="lmTutorial1.do">Tutorial</a> </div>
			<div class="floatLeft headerButton"> <a href="lmGallery.do">Project Map</a> </div>
			<div class="floatLeft headerButton"> <a href="glossaryPublic.do">Glossary</a> </div>
			<div class="floatLeft headerButton"> <a href="lmResources.do">More Resources</a> </div>
		</div>
	</div>
	<!-- End header menu -->
	<!-- Begin footer -->
	<!-- End footer -->
	</body>
</html:html>
