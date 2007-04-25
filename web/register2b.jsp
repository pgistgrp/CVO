<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
	<head>
	<title>Let's Improve Transportation - Registration</title>
	<!-- Site Wide CSS -->
<style type="text/css" media="screen">
@import "styles/lit.css";
@import "styles/registration-2b.css";

</style>
<!-- End Site Wide CSS -->
<!-- Site Wide JS -->
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/SystemAgent.js'></script>
<script type='text/javascript' src='/dwr/interface/RegisterAgent.js'></script>

<script type="text/javascript">
function submit2B() {
	RegisterAgent.addConsent({});
	window.location = "registerq.do";
}
</script>
</head>
<body>

<!--[if IE]>
	<style type="text/css">
		fieldset p {padding-bottom:1px;}
	</style>
<![endif]-->

<!-- Begin the header - loaded from a separate file -->
<div id="header">
	<!-- Begin header -->
	<jsp:include page="/header.jsp" />
	<!-- End header -->
</div>
<!-- End header -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu"> </div>
<!-- End header menu -->
<!-- #container is the container that wraps around all the main page content -->
<div id="container">

	
<!-- start #reg2b -->
<div id="reg2b">
	<div>
		<h3>Welcome ${baseuser.loginname}. You qualify to participate in Let's Improve Transportation!</h3>
		<p>Due to the large number of participants who have responded to our call to join Let’s Improve Transportation, we currently can not offer any small payment in appreciation of your efforts.  We hope you’ll still consider participating as a volunteer, and you will be placed on a waiting list to be among the participants who will receive a small payment.</p>
	</div>
	
	<fieldset>
		<legend>Review the Informed Consent Agreement</legend>
		<p>If you wish to be a paid participant in our research study, you must review and accept this Informed Consent Agreement.</p>
		<div id="agreement" class="box3">
		<h3>1. INVESTIGATORS STATEMENT</h3>
		
		<p>We are asking you to be in a research study. The purpose of this consent form
			is to give you the information you will need to help you decide whether or not
			to be in the study. Please read the form carefully. When you are finished reading
			this form, you can decide if you want to be in the study or not. This process
			is called “informed consent”.</p>
		<h3>2. PURPOSE OF THE STUDY</h3>
		<p>We want to better understand the opinions and feelings of the public regarding
			transportation improvement decision-making in the Seattle metropolitan area.
			We would like to ask that you participate fully in this decision-making situation,
			and could ask you to.</p>
		<h3>3. MORE STUFF</h3>
		<p>We are asking you to be in a research study. The purpose of this consent form
			is to give you the information you will need to help you decide whether or not
			to be in the study. Please read the form carefully. When you are finished reading
			this form, you can decide if you want to be in the study or not. This process
			is called “informed consent”.</p>
		</div>
	</fieldset>
	<!-- end OPPORTUNITIES -->
	<div class="clearBoth"></div>
	
	<div id="step-bar" class="box5 padding5 clearfix">
			<p class="floatLeft" id="step-progress">Step 2 of 3</p>
			<p class="floatLeft" id="submit-description" style="width:420px;">By clicking “I Agree” you (a) agree to the “informed consent” above and (b) agree to receive required notices from Let’s Improve Transportation electronically.</p>
			<p class="floatRight" id="submit-button"><input type="button" value="I Agree" style="font-size:1.5em;" onClick="submit2B()" /> <input type="button" value="I Do Not Agree" style="font-size:1.5em;" onClick="cancel()" /></p>
		</div>
</div>
</form>
<!-- end #reg2b -->	
	
	
</div>
<!-- end container -->
<!-- start feedback form -->
<pg:feedback id="feedbackDiv" action="cctView.do"/>
<!-- end feedback form -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu"> </div>
<!-- Begin footer -->
<div id="footer">
	<jsp:include page="/footer.jsp" />
</div>
<!-- End footer -->

</body>
</html:html>
