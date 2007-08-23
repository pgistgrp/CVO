<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
	<title>Learn More About LIT</title>
	<!-- Site Wide CSS -->
	<style type="text/css" media="screen">
@import "styles/lit.css";

#container h3 {font-size:1.5em;}
#container {font-size:12pt;}
</style>
	<!-- End Site Wide CSS -->
	<!-- Site Wide JS -->
	<script language="JavaScript" src="scripts/qtip.js" type="text/JavaScript"></script>
	<script src="scripts/prototype.js" type="text/javascript"></script>
	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<script src="scripts/search.js" type="text/javascript"></script>
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/util.js'></script>
	</head>
	<body>
	<wf:nav />
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu">
		<div id="headerContainer">
			<div id="headerTitle" class="floatLeft">
				<h3 class="headerColor">Learn More</h3>
			</div>
			<div class="floatLeft headerButton"> <pg:url page="lmMenu.do">Menu</pg:url> </div>
			<div class="floatLeft headerButton currentBox"> <pg:url page="lmAbout.do">About LIT</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmFaq.do">FAQ</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmTutorial1.do">Tutorial</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmGallery.do">Project Map</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="glossaryPublic.do">Glossary</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmResources.do">More Resources</pg:url> </div>
		</div>
	</div>
	<!-- End header menu -->
	<!-- #container is the container that wraps around all the main page content -->
	 <div id="container">
	 <h2 class='headerColor'>About Let's Improve Transportation</h2>
	 <ul>
	 	<li><pg:url page="lmAbout.do" anchor="whatis">What is Let's Improve Transportation (LIT)?</pg:url></li>
		<li><pg:url page="lmAbout.do" anchor="realworld">How is LIT related to the "Roads and Transit" ballot measure?</pg:url></li>
		<li><pg:url page="lmAbout.do" anchor="purpose">What is the purpose of the research effort behind LIT?</pg:url></li>
		<li><pg:url page="lmAbout.do" anchor="support">How can the LIT website support transportation improvement decision making processes?</pg:url></li></ul>

 <h3 class="headerColor" style="text-transform:none"><a name="whatis" class="headerColor">What is Let's Improve Transportation?</a></h3>
<pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">

<p>Let's Improve Transportation (LIT) is an experiment in participatory democracy. A research team based at the University of Washington<sub>(1)</sub> has developed an innovative new website where you can:</p>
<ul>
  <li>learn about proposed improvements to the central Puget Sound regional transportation system,</li>
  <li>consider the potential impacts of these changes on our communities, and</li>
  <li>work collaboratively with other residents to determine which changes the region should support.</li>
</ul>
<p>This October, residents of King, Pierce, and Snohomish counties are invited to participate in the LIT Challenge, a 4-week long online experiment in which 300 or more individuals will participate via the Internet at their own convenience. The purpose of this experiment is to evaluate a new and potentially more meaningful way to involve citizens in the process of regional transportation decision making.</p>
<p>Participants in the LIT Challenge are asked to imagine they are a member of a large citizen advisory committee charged with providing policy makers with their recommendations regarding the make-up of a regional transportation ballot measure. The hypothetical measure will ask voters in the region if they wish to raise taxes to pay for a package of road and transit improvement projects. The task of LIT Challenge participants is to determine which proposed transportation improvement projects to build and which funding mechanisms (such as taxes or tolls) should be used to pay for these improvements. The challenging part is to get as many participants as possible to collectively recommend a single "package" of projects and funding options. Fortunately, the LIT website was designed to support exactly this kind of challenge. The website, as well as a small team of online moderators, will facilitate a process in which participants:</p>
<ul>
	<li>Brainstorm concerns,</li>
	<li>Review improvement factors</li>
	<li>Discuss proposed transportation improvements and funding options</li>
	<li>Create personal transportation packages</li>
	<li>Select a recommended transportation package</li>
	<li>Create a final report</li>
</ul>
<p>While this may sound complicated, the website is designed to lead any interested citizen through the process. No prior experience with transportation issues is necessary. It is estimated that full participation in the LIT Challenge will take as little as 8-10 hours over the four week period. Qualified participants who complete the LIT Challenge will receive compensation for their efforts.</p>
 <h3 class="headerColor" style="text-transform:none"><a name="realworld" class="headerColor">How is LIT related to the "Roads and Transit" ballot measure?</a></h3>
<p>Many readers may notice the similarity between the hypothetical problem posed to LIT Challenge participants and the real world "<a href="http://www.roadsandtransit.org" target="_blank">Roads and Transit</a>" ballot measure. In November 2007, voters in King, Pierce, and Snohomish counties will be asked whether or not a package of road and transit improvements proposed by <a href="http://www.rtid.org" target="_blank">RTID</a> and <a href="http://www.soundtransit.org" target="_blank">Sound Transit</a> should become law. The "Roads and Transit" package is the result of years of negotiations between county elected officials as well as input from businesses, transportation advocacy organizations, and concerned members of the public. Transportation funding ballot measures such as this one have become increasingly common in metropolitan regions across the U.S.</p>

<p>The LIT Challenge is designed to be a more meaningful way to involve members of the public in regional transportation decision making before a single package is placed on the ballot. Researchers hope that the LIT Challenge will serve as a model for how to involve the public in future regional transportation improvement decision making.</p>


<h3 class="headerColor" style="text-transform:none"><a name="purpose" class="headerColor">What is the purpose of the research effort behind LIT?</a></h3>

<p>The <a href="http://www.pgist.org" target="_blank">Participatory GIS for Transportation</a> (PGIST) research study at the University of Washington<sub>(1)</sub> was founded to develop web and mapping technologies for supporting public participation in transportation improvement programming decisions. As part of the PGIST study, a demonstration website was developed, called Let's Improve Transportation (LIT). In October 2007 an <a href="#whatis" target="_blank">experiment</a> will be conducted to evaluate the LIT website.</p>

<p>There are three main goals of the "LIT Challenge" experiment:</p>
<ol>
	<li>To serve as a public demonstration of one way to enhance the involvement of citizens in transportation improvement programming decision processes. </li>
	<li>To allow PGIST researchers to evaluate the effectiveness of this experimental model of public involvement.</li>
	<li>Allow agency collaborators to evaluate the capabilities of the web technology and provide feedback regarding its appropriateness to their transportation improvement programming and public involvement needs.</li>
</ol>

<p>PGIST researchers hope that web technology developed by PGIST can be refined, improved, and eventually put to use by transportation agencies around U.S. and the world.</p>

<h3 class="headerColor" style="text-transform:none"><a name="support" class="headerColor">How can the LIT website support transportation improvement decision making processes?</a></h3>

<p>The technology developed by PGIST researchers is designed to support transportation agency staff in several stages of a transportation improvement programming process. Specifically, the website enables facilitators or transportation agency staff to:</p>
<ul>
	<li>Define "improvement factors", or criteria for project evaluation. </li>
	<li>Enter information about proposed transportation improvement projects into an online database. </li>
	<li>Input scores for individual proposed projects based on the defined improvement factors. </li>
	<li>Define available funding mechanisms (in the case of a ballot measure financing plan such as RTID).</li>
	<li>Display project information, project scores, project location (footprints), and funding options on a website for public review. </li>
	<li>Support a structured public consultation process (see description above)</li>
	<li>Support the automated clustering of individual transportation packages created by participants into a short list of representative "candidate" packages for public review and evaluation.</li>
	<li>Poll participants regarding their willingness to support different "candidate" packages.</li>
	<li>Automatically generate a report of the results of the public consultation process.</li>
</ul>

<p /><br/><p />
<h3 class="headerColor">Notes</h3>
<p><strong>1</strong> Research institutions associated with this study include University of Washington Departments of Geography, Civil and Environmental Engineering, and Information Science, San Diego State University Department of Geography, and University of Wyoming Department of Civil and Architectural Engineering.</p>
<p>To learn more about this research project, go to <a href="http://www.pgist.org" target="_blank">www.pgist.org</a>.</p>
			</pg:termHighlight>

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
			<div class="floatLeft headerButton currentBox"> <pg:url page="lmAbout.do">About LIT</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmFaq.do">FAQ</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmTutorial1.do">Tutorial</pg:url> </div>
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
