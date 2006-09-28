<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
<html><head><title>Let's Improve Transportation</title><!-- Site Wide CSS -->


<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/position.css";</style>
<style type="text/css" media="screen">@import "styles/styles.css";</style>
<!-- End Site Wide CSS -->

<!-- Site Wide JS -->
<script src="scripts/search.js" type="text/javascript"></script>
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<!-- End Site Wide JS -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>
<!-- End DWR JavaScript Libraries -->
</head><body>
<!-- Header -->

<!-- Header -->

<jsp:include page="/header.jsp" />
<!-- Sub Title -->
<div id="subheader">
<h1>Let's Improve Transportation:</h1> <h2>Decision Process</h2>
</div>
<div id="footprints">
<span class="smalltext">LIT Process >>Read More</span>
</div>
<!-- End Sub Title -->


<!-- Overview SpiffyBox -->
<div class="cssbox">
<div class="cssbox_head">
<h3>Thank you for submitting your concerns</h3>
</div>
<div class="cssbox_body">

<p>We will continue to collect&nbsp;concerns from participants until<font color="#ff0000"><b> 11:59pm on Saturday, September 30th</b></font>. Feel free to return to the <a href="main.do">Home Page</A> or the <a href="http://128.95.212.210:8080/cctview.do?cctId=1171">Brainstorm Concerns</a> page to review other participant's concerns, edit your own concerns, or add some more.</p>
<p>On <strong><font color="#cc0000">Monday, October 2nd</font></strong>, the moderator will review concerns and summarize them in the form of concern themes. When the concern themes are ready for participant review and discussion, the moderator will send you an email. At that point you will be able evaluate how well the summaries represents your concerns and others', and discuss whether revisions are necessary.</p>
</div>
</div>
<!-- End Overview -->

</div> <!-- End cont-top -->

	<div id="cont-main">
	
	<!-- start feedback form -->
			<pg:feedback id="feedbackDiv" action="waiting.do" />
	<!-- end feedback form -->
	</div>
	<!-- End cont-main -->



<div id="footerspacing" style="padding-top: 200px;">
<!-- Start Footer -->
<jsp:include page="/footer.jsp" />

<!-- End Footer -->
</div>
</body></html>