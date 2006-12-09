<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>Let's Improve Transportation - Done with Step 1</title>
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
  <!-- Begin header menu - The wide ribbon underneath the logo -->
  <div id="headerMenu">
    <div id="headerContainer">
      <div id="headerTitle" class="floatLeft">
        <h3 class="headerColor">Step 1: Discuss Concerns</h3>
      </div>
    <div class="headerButton floatLeft"><a href="cctlist.do">1a: Brainstorm</a></div>
    <div class="headerButtonCurrent floatLeft currentBox"><a href="http://128.95.212.210:8080/sd.do?isid=7362">1b: Review Summaries</A></div>
      <div id="headerNext" class="box5 floatRight"><a href="main.do">Next Step</A></div>
    </div>
  </div>
  <!-- End header menu -->
  <!-- End header menu -->
  <!-- #container is the container that wraps around all the main page content -->
  <div id="container">
  <div style="width:500px;">
<h3 class="headerColor">That's it!</h3>

<p>You've finished the Step 1 functionality we wanted to test. You may continue <a href="http://128.95.212.210:8080/sd.do?isid=7362">discussing the concern theme summaries</A> until midnight on Friday, December 15th, when discussion in this step ends. If any additional revisions to the summaries are made before December 15th, the moderator will notify you by email.</p>
<p>Remember, these summaries do not represent the end of the conversation about participant concerns. They are merely a snapshot in time that we can use in Step 2 to help evaluate the criteria. (However, Step 2 is not ready for testing, so this is the end of the road for now!)</p>

<p>After the text concludes we will analyze the feedback you left us and make improvements in preparation for the next test.</p>

<p>Is there anything else you'd like to tell us?  You can use the feedback form below, or feel free to e-mail Adam at <a href="mailto:adamh@u.washington.edu">adamh@u.washington.edu</a></p> 
</div>
  </div>
  <!-- end container -->
  
<!-- start feedback form -->
  <pg:feedback id="feedbackDiv" action="cctView.do"/>
<!-- end feedback form -->

  <!-- Begin header menu - The wide ribbon underneath the logo -->
  <div id="headerMenu">
    <div id="headerContainer">
      <div id="headerTitle" class="floatLeft">
        <h3 class="headerColor">Step 1: Discuss Concerns</h3>
      </div>
    <div class="headerButton floatLeft"><a href="cctlist.do">1a: Brainstorm</a></div>
    <div class="headerButtonCurrent floatLeft currentBox"><a href="http://128.95.212.210:8080/sd.do?isid=7362">1b: Review Summaries</A></div>
      <div id="headerNext" class="box5 floatRight"><a href="main.do">Next Step</A></div>
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