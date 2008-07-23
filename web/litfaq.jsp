<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>Voicing Climate Concerns - User Settings</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/lit.css";</style>
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

  </div>
  <!-- End header menu -->
  <!-- #container is the container that wraps around all the main page content -->
  <div id="container">
	<h2 class="headerColor">Frequently Asked Questions</h2>
	<p>Here are answers to some of the most common questions participants ask.</p>
	<h2 class="headerColor"><a name="step1" style="background-color:yellow">Step 1: Discuss Concerns</a></h2>
	<p></p>
	<h3 class="headerColor"><a name="step1a" style="background-color:yellow">Step 1a: Brainstorm</a></h3>
		<ul>
			<li><strong><a name="whattag" style="background-color:yellow">What is a tag?</a></strong>
				<p>Think of tags as labels.  These labels help make your concerns easier to find among the many other concerns provided by other participants.  Tags are important for this first step in our process, as it assists the moderator in finding and clustering concerns so that summaries can be written.  It is important, therefore, to tag your concerns with words or word phrases that you feel appropriately convey the meaning of your concern.</p>
			</li>
		</ul>
	<h3 class="headerColor"><a name="step1b" style="background-color:yellow">Step 1b: Review Summaries</a></h3>	
		<ul>
			<li><strong><a name="howsummaries" style="background-color:yellow">How are these summaries created?</a></strong>
				<p>As participants add concerns, the moderator will review a subset of these concerns by sampling the tags participants used to label their concerns.  While reading the concerns, the moderator will place these concerns into topical themes, by creating themes and adding tags to these themes.  Due to the many concerns added by participants, it is possible that the moderator will not be able to review every concern, or every group of concerns tagged with the same tag.  Therefore, your responsibility as a participant is to review these summaries and discuss how these summaries might be re-written differently.</p>
			</li>
			<li><strong><a name="whosummaries" style="background-color:yellow">Who reads these summaries?</a></strong>
				<p>You and your fellow participants will initially read and review these summaries.  The moderator may revise these summaries based on participant feedback and discussion.  At the conclusion of this step, the summaries will cease to be updated and will be included in a final report given to decision makers.  All the comments and discussion about these summaries will also be included, to showcase the variety of opinions.</p>
			</li>
			<li><strong><a name="whysummaries" style="background-color:yellow">Why are the summaries important?</a></strong>
				<p>When a significant number of participants indicate that they disagree with a summary, the moderator will review discussions and suggested revisions by participants. If it appears that a revision is necessary, the moderator will make changes and notify participants. At the conclusion of this step, the summaries will no longer be revised. Some participants may disagree with the 'final' version of a summary. These participants can express this disagreement in the poll as well as their discussion comments about the summary. These comments will always be available for review to all participants and will also be available to decision makers as an appendix to the final report.</p>
			</li>
			<li><strong><a name="howsummariesrevised" style="background-color:yellow">How are summaries revised?</a></strong>
				<p>When a significant number of participants indicate that they disagree with a summary, the moderator will review discussions and suggested revisions by participants. If it appears that a revision is necessary, the moderator will make changes and notify participants. At the conclusion of this step, the summaries will no longer be revised. Some participants may disagree with the 'final' version of a summary. These participants can express this disagreement in the poll as well as their discussion comments about the summary. These comments will always be available for review to all participants and will also be available to decision makers as an appendix to the final report.</p>
			</li>
		</ul>
  </div>
  <!-- end container -->
  
<!-- start feedback form -->
  <pg:feedback id="feedbackDiv" action="litfaq.jsp"/>
<!-- end feedback form -->

  <!-- Begin header menu - The wide ribbon underneath the logo -->
  <div id="headerMenu">

  </div>

	<!-- Begin footer -->
	<div id="footer">
		<jsp:include page="/footer.jsp" />
	</div>
	<!-- End footer -->
</body>
</html:html>

