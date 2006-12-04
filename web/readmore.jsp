<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>Let's Improve Transportation - About the Process</title>
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
 <div id="container" style="width:600px;">
<p>There are five steps in the Let's Improve Transportation activity. Here  you can read about each of the steps. (Note, only Step 1 is ready for  use at this time)</p>
    <h3><a name="step1" class="contrast2">Step 1: Discuss Concerns</a></h3>

    <p>Before we can determine how to best improve our transportation system, we need to know what the problems are.</p>
    <ul>
      <li><strong>1a: Brainstorm</strong>
        <ul>
          <li>All participants brainstorm and "tag" concerns about the  transportation system.</li>
        </ul>
      </li>
    </ul>
    <ul>
      <li><strong>1b: Review Summaries </strong>
        <ul>
          <li>The  moderator identifies themes among these concerns and writes a preliminary  summary  of each theme. (<a href="litfaq.jsp">Read more</a>)</li>
          <li> Participants  review and discuss the theme summaries, and suggest revisions.</li>

          <li>The moderator revises theme summaries to reflect participant suggestions, as appropriate.</li>
        </ul>
      </li>
    </ul>
    <h3><a name="step2" class="contrast2">Step 2: Review Criteria</a></h3>
    <p>Criteria are used to evaluate which proposed transportation improvement  projects are best suited to address problems with our current  transportation system. Researchers identified a list of criteria before  this activity began so that information relevant to these criteria  could be gathered.</p>
    <ul>

      <li><strong>2a: Review Criteria </strong>
        <ul>
          <li> Participants review and discuss criteria in order to evaluate  how adequately they reflect each concern theme.</li>
        </ul>
      </li>
    </ul>
    <ul>

      <li><strong>2b: Weigh Criteria </strong>
        <ul>
          <li>Participants individually choose their favorite criteria and weigh the  relative importance of these criteria for project evaluation.</li>
        </ul>
      </li>
    </ul>
    <h3><a name="step3" class="contrast2">Step 3: Create Packages</a></h3>

    <p>A &quot;transportation package&quot; is simply a set of transportation projects  and sources of funding to pay for those projects. Decision makers often  create packages as options to simplify regional transportation  improvement financing decisions.</p>
    <ul>
      <li><strong>Step 3a: Review Projects</strong><br />
        <ul>
          <li>Participants review road and transit projects proposed as improvements  to the central Puget Sound regional transportation system.</li>

          <li>Participants discuss the project information and  criteria scores to evaluate the relative benefits and drawbacks.</li>
        </ul>
      </li>
    </ul>
    <ul>
      <li><strong>Step 3b: Review Funding Options</strong><br />
        <ul>
          <li>Participants review a variety of taxes available to raise funds to pay  for regional transportation projects. They also learn about the likely  personal costs of these taxes, as well as the costs to other residents.</li>
          <li>Participants discuss the funding options to evaluate the relative benefits and drawbacks.</li>
        </ul>
      </li>
    </ul>

    <ul>
      <li><strong>Step 3c: Create a Personal Transportation Package</strong>
        <ul>
          <li>Participants create their own transportation package, selecting from a  menu of project and funding options. A help tool is available to  suggest items which may fit the participant's preferences.<br />
            </li>
        </ul>
      </li>
    </ul>
    <h3><a name="step4" class="contrast2">Step 4: Evaluate Packages</a></h3>
    <p>In order to simplify the decision process, we need to narrow down the  number of different packages considered. Therefore the system  automatically creates five new packages that collectively represent the  diversity of packages created by participants in Step 3. In this step  participants evaluate the five new packages and determine which one (or  ones) they wish to collectively endorse.</p>
    <ul>

      <li><strong>4a: Compare Packages </strong>
        <ul>
          <li>Participants review information about each  package.</li>
          <li>Participants compare the overall criteria scores of each package, as well as costs and other package details.</li>
          <li>Participants discuss the relative benefits and drawbacks of the packages.</li>
          <li>The moderator periodically polls participants about their willingness  to endorse different packages in order to help them identify areas of  agreement and disagreement.</li>
        </ul>
      </li>

      <li><strong>4b: Final Vote </strong>
        <ul><li>At a designated time, participants are asked whether or not they are  willing to endorse the most popular package. If the majority of  participants say yes, the package is designated as the majority  endorsement.</li>
          <li>Dissenting voters have the opportunity to vote on a minority endorsement.</li>
        </ul>
      </li>
    </ul>

    <h3><a name="step5" class="contrast2">Step 5: Prepare Group Report</a></h3>
    <p>The final step is to compose a report about the results of this  activity which will be delivered to transportation decision makers.</p>
    <ul><li>The moderator creates a  draft report and notifies participants.</li>
      <li> Participants  review and discuss this draft report and submit their comments and suggested  revisions.</li>
      <li> The  moderator makes revisions and includes participant comments when appropriate.</li>

      <li> Participants  review the final report.</li>
      <li>All participants vote on whether to approve or disapprove the report.  If a majority approves the report, then it is forwarded on to decision  makers along with the results of this final vote.</li>
    </ul>
  </div>
</div>
  <!-- end container -->
  
  
<!-- start feedback form -->
  <pg:feedback id="feedbackDiv" action="cctView.do"/>
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

