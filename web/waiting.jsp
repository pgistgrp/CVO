<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>Let's Improve Transportation - Done with Step 1a</title>
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
    <div class="headerButton floatLeft currentBox"><a href="cctlist.do">1a: Brainstorm</a></div>
    <div class="headerButtonCurrent floatLeft"><a href="/waiting.jsp">1b: Review Summaries</A></div>
      <div id="headerNext" class="box5 floatRight"><a href="main.do">Next Step</A></div>
    </div>
  </div>
  <!-- End header menu -->
  <!-- End header menu -->
  <!-- #container is the container that wraps around all the main page content -->
  <div id="container">
  <div style="width:500px;">
<h3 class="headerColor">Thank you for submitting your concerns</h3>

<p>We will continue to collect concerns from participants until <strong>11:59pm on Thursday, December 7th</strong>. Feel free to return to the <a href="main.do">Home Page</A> or the <a href="/cctlist.do">Brainstorm Concerns</a> page to review other participant's concerns, edit your own concerns, or add some more.</p>
<p>On <strong>Friday, December 8th</strong>, the moderator will review concerns and summarize them in the form of concern themes. When the concern themes are ready for participant review and discussion, the moderator will send you an email. At that point you will be able evaluate how well the summaries represents your concerns and others', and discuss whether revisions are necessary.</p>

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
    <div class="headerButton floatLeft currentBox"><a href="cctlist.do">1a: Brainstorm</a></div>
    <div class="headerButtonCurrent floatLeft"><a href="/waiting.jsp">1b: Review Summaries</A></div>
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





