<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<!--####
	Project: Let's Improve Transportation!
	Page: Package Vote
	Description: Allow users to vote on packages.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Matt Paulin, Zhong Wang
	Todo Items:
		[x] Spec (Zhong) 
		[x] Initial Skeleton Code (Jordan)
		[x] Create Layout (Adam)
		[x] JavaScript/JSTL/Integrate Layout (Jordan)
		[ ] Add phase param from referring package- packageVote.jsp?phase=1 (Jordan)
		[ ] Backend form processing (Matt Paulin)
		[ ] Test and Refine (Jordan)
#### -->
<html:html> 
<head>
<title>Package Vote</title>
<!-- Site Wide JavaScript -->
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->
<!-- Site Wide CSS -->
<style type="text/css" media="screen">
		@import "styles/lit.css";
</style>
<!-- End Site Wide CSS -->
<style type="text/css">

#voteBox
{
width:100%;
border: 1px solid #ADCFDE;
margin-bottom:1.5em;
}

#voteBox p {margin:1em .5em;}

.odd {background: #D6E7EF;}
.even {background: #ffffff;}

.VoteListRow
{
padding:.3em 0em;
}

.voteCol1
{
width:10%;
margin-right:.5em;
padding-left:2em;
font-size:1.1em;
}

.voteCol2,.voteCol3,.voteCol4
{
width:28%;
text-align:center;
}

h4
{
font-size:1em;
margin:0em auto;
}

.highlight 
{
background:#FFF1DC;
}

</style>
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
      <h3 class="headerColor">Step 4b: Evaluate Candidate Packages</h3>
    </div>
    <div class="headerButton floatLeft  currentBox"> <a href="step4.html">4a: Review packages</a></div>
    <div class="headerButton floatLeft"> <a href="step4b.html">4b: Vote</a></div>
    <div id="headerNext" class="floatRight box5"> <a href="step4b.html">Next Step</a></div>
  </div>
</div>
<!-- End header menu -->

<!-- #container is the container that wraps around all the main page content -->
<div id="container">
  <!-- begin "overview and instructions" area -->
  <div id="overview" class="box2">
    <h3>Overview and Instructions</h3>
    <p>During this step, the moderator asks participants to determine
      which packages have the greatest level of collective support.
      This polling information will help us to decide which package
      to collectively recommend. The final vote will be held
      on --Thursday Oct. 19--.</p>
  </div>

  <!-- end overview -->
  <!-- begin Object -->
	<div id="object">
      <!-- begin one voting box -->
		<h3 class="headerColor">Packages Poll</h3>
		<div id="voteBox" class="floatLeft clearBoth">
		<p>Please respond to this poll by <strong> -- midnight --<!--workflow date--></strong>.</p>

		<!-- begin voting headers -->
		<p>Please indicate your current willingness to endorse each of the following package to decision makers.</p>
        <div class="VoteListRow row">
          <div class="voteCol1 floatLeft">&nbsp;</div>
          <div class="voteCol2 floatLeft">I would <strong>enthusiastically endorse</strong> this package</div>
          <div class="voteCol3 floatLeft">I am <strong>willing to endorse</strong> this package if it receives greatest participant support</div>
          <div class="voteCol4 floatLeft">I would <strong>not endorse</strong> this package, regardless of its support among other participants</div>
          <div class="clearBoth"></div>
        </div>
		<!-- end voting headers -->
		<form action="packageVote.do" method="POST">
			<input type="hidden" name="activity" value="vote" />
			<c:forEach var="clusteredPkg" items="${voteSuite.pkgSuite.clusteredPkgs}" varStatus="loop">
			       <div class="VoteListRow row ${((loop.index % 2) == 0) ? 'even' : 'odd'}">
			         <div class="voteCol1 floatLeft">
			           <div class="floatLeft"><a href="package.do?id=${clusteredPkg.id}">Package ${clusteredPkg.id}</a></div>
			         </div>
			         <div class="voteCol2 floatLeft"><input name="pkg-${clusteredPkg.id}" value="1" type="radio" /></div>
			         <div class="voteCol3 floatLeft"><input name="pkg-${clusteredPkg.id}" value="2" type="radio" /></div>
			         <div class="voteCol4 floatLeft"><input name="pkg-${clusteredPkg.id}" value="3" type="radio" /></div>
			         <div class="clearBoth"></div>
			       </div>
			</c:forEach>
		</form>
		<p class="floatRight"><input type="reset" value="Reset form and start over" /> <input type="button" value="Submit Votes" /></p>
	</div><!-- end one voting box -->
	<div class="clearBoth"></div>	
    <!-- end obj-left -->
    <!-- begin firefox height hack -->
    <div class="clearBoth"></div>
    <!-- end firefox height hack -->
  </div>
  <!-- end Object-->
</div>
<!-- End foldable average grades table -->

</div>
<!-- end container -->
<!-- start feedback form -->
<pg:feedback id="feedbackDiv" action="cctView.do"/>
<!-- end feedback form -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu">
  <div id="headerContainer">
    <div id="headerTitle" class="floatLeft">
      <h3 class="headerColor">Step 4b: Evaluate Candidate Packages</h3>
    </div>
    <div class="headerButton floatLeft  currentBox"> <a href="step4.html">4a:
        Review packages</a> </div>

    <div class="headerButton floatLeft"> <a href="step4b.html">4b:
        Vote</a> </div>
    <div id="headerNext" class="floatRight box5"> <a href="step4b.html">Next
        Step</a> </div>
  </div>
</div>
<!-- End header menu -->
<!-- Begin footer -->
<div id="footer">
  <jsp:include page="/footer.jsp" />

</div>
<!-- End footer -->
</body>
</html:html>
