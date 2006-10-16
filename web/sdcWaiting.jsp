
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
<html><head><title>Thanks for your help with the test!</title><!-- Site Wide CSS -->

<script type="text/javascript">

function displayFeedback()
{
	document.getElementById("feedbackForm").style.display = "block";
}

</script>
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
</head><body onLoad="displayFeedback();">
<!-- Header -->

<!-- Header -->
	
<jsp:include page="/header.jsp" />
<!-- Sub Title -->
<div id="subheader">
<h1>Let's Improve Transportation:</h1> <h2>Finished with Step 1</h2>
</div>
<div id="footprints">
<span class="smalltext"><a href="main.do">Participate</a> » <a href="/sd.do?isid=2951">Step 1b Review Summaries</a> » Finished with Step 1
</div>
<!-- End Sub Title -->


<!-- Overview SpiffyBox -->
<div class="cssbox">
<div class="cssbox_head">
<h3>That's it!</h3>
</div>
<div class="cssbox_body">

<p>You've finished the Step 1 functionality we wanted to test. You may continue <a href="/sd.do?isid=2951">discussing the concern theme summaries</A> until midnight on Thursday, October 12th, when discussion in this step ends. If any additional revisions to the summaries are made before October 12th, the moderator will notify you by email.</p>
<p>Remember, these summaries do not represent the end of the conversation about participant concerns. They are merely a snapshot in time that we can use in Step 2 to help evaluate the criteria. (However, Step 2 is not ready for testing, so this is the end of the road for now!)</p>

<p>After the text concludes we will analyze the feedback you left us and make improvements in preparation for the next test.</p>

<p>Is there anything else you'd like to tell us?  You can use the feedback form below, or feel free to e-mail Adam at <a href="mailto:adamh@u.washington.edu">adamh@u.washington.edu</a></p> 
</div>
</div>
<!-- End Overview -->

</div> <!-- End cont-top -->

	<div id="cont-main">
	
	<!-- start feedback form -->
			<pg:feedback id="feedbackDiv" action="sdcWaiting.do" />
	<!-- end feedback form -->
	</div>
	<!-- End cont-main -->



<div id="footerspacing" style="padding-top: 200px;">
<!-- Start Footer -->
<jsp:include page="/footer.jsp" />

<!-- End Footer -->
</div>
</body></html>