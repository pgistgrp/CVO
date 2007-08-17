<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
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
  <!-- Start Global Headers  -->
  <wf:nav />
  <!-- End Global Headers -->
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
		<p>Due to the large number of participants who have responded to our call to join Let’s Improve Transportation, we currently cannot offer any small payment in appreciation of your efforts.  We hope you will still consider participating as an unpaid volunteer, and you will be placed on a waiting list to be among the participants who can receive a small payment.</p>
	</div>
	
	<fieldset>
		<legend>Review the Informed Consent Agreement</legend>
		<p>If you wish to be a paid participant in our research study, you must review and accept this Informed Consent Agreement.</p>
		<div id="agreement" class="box3">
		<h2 class="centerAlign">UNIVERSITY OF WASHINGTON INFORMATION STATEMENT<br/>TRANSPORTATION STUDY</h2>
		<h3>Investigators</h3>
		<p style="margin-left:20px">Timothy L. Nyerges, Principal Investigator, Professor, Department of Geography, 206-543-5296<br/>
				Terrence Brooks, Associate Prof., Information School, 206-543-2646<br/>
				Piotr Jankowski, Professor, Department of Geography, San Diego State University, 619-594-0640<br/>
				G. Scott Rutherford, Professor, Civil & Environmental Engr., 206-685-2481<br/>
				Rhonda Young, Assistant Professor, Civil & Architectural Engr., University of Wyoming, 307-766-2184<br/>
				Kevin Ramsey, Research Assistant, Department of Geography, 206-616-9018<br/>
				Martin Swobodzinski, Research Assistant, Geography, San Diego State University, 619-594-5437<br/>
				Guirong Zhou, Research Assistant, Department of Geography, 206-616-9018<br/>
				Matthew W. Wilson, Research Assistant, Department of Geography, 206-616-9018<br/>
		</p>
		<h3>Researcher's statement</h3>
			<p>We are asking you to be in a research study. The purpose of this information statement is to give you the information you will need to help you decide whether to be in the study or not. Please read the form carefully.</p>
			<h2 class="centerAlign">PURPOSE OF THE STUDY</h2>
			<p>This research project has developed an Internet website, including mapping technologies, to support public participation in regional transportation improvement decision making. The name of this website is <em>Let’s Improve Transportation</em>.  This research study seeks to evaluate this website by recruiting residents from the Puget Sound area to participate by voicing their concerns about transportation issues in the region (Step 1), reviewing criteria used to evaluate proposed transportation projects (Step 2), creating their own "package" of transportation projects and funding mechanisms (Step 3),  working with other participants to endorse a collectively preferred "package" (Step 4), and by creating a report describing the outcomes (Step 5). In many ways, this study can be likened to an online focus group, as it is an online discussion moderated by researchers.</p>
			<h2 class="centerAlign">STUDY PROCEDURES</h2>
			<p>If you choose to be in this study, we would like to participate in the Let’s Improve Transportation website.  The website will be active for 4 weeks, and we would like you to participate in the various activities within the website during that time period.  You may refuse to participate in any portion of this website.  With your permission, we would like to collect information about your participation within the website (including discussions posts and other comments you make within the website).  Beyond participating in the various activities within the website, we also ask that you complete 5 questionnaires, which shall be administered electronically during the course of the 4-week activity.  These 5 questionnaires will ask you to reflect on your experiences and opinions of the website, and will be used to evaluate the website.  You may refuse to answer any question within any of the 5 questionnaires.</p>
			<h2 class="centerAlign">RISK, STRESS OR DISCOMFORT</h2>
			<p>Some people feel that providing information for research is an invasion of privacy.  We have addressed concerns for your privacy in the section below. </p>
			<h2 class="centerAlign">ALTERNATIVES TO TAKING PART IN THIS STUDY</h2>
			<p>The alternative to taking part in this study is to not take part in this study.</p>
			<h2 class="centerAlign">BENEFITS OF THE STUDY</h2>
			<p>Although we think this research is valuable to society as a whole, you may not directly benefit from taking part in this study.</p>
			<h2 class="centerAlign">OTHER INFORMATION</h2>
			<p>Taking part in this study is voluntary.  You can stop at any time. Information about you is confidential.  We will code the study information and keep the link between your identifying information and the code in a separate, secured location until October 2009.  Then we will destroy the link.  The following groups may need to review study records about you: institutional oversight review offices at the research site, the University of Washington, and federal regulators.  If the results of this study are published or presented, we will not use your name.  </p>
			<h3>Subject's statement</h3>
			<p>This study has been explained to me.  I volunteer to take part in this research.  If I have questions later on about the research I can ask the investigator listed above.  If I have questions about my rights as a research subject, I can call the University of Washington Human Subjects Division at (206) 543-0098.</p>
			<p>By clicking "Agree" below, I give my permission for the researcher to collect information about my use of the website, and to use information collection from my response to the 5 questionnaires. </p>
		</div>
	</fieldset>
	<!-- end OPPORTUNITIES -->
	<div class="clearBoth"></div>
	
	<div id="step-bar" class="box5 padding5 clearfix">
			<p class="floatLeft" id="step-progress">Step 2 of 3</p>
			<p class="floatLeft" id="submit-description" style="width:420px;">By clicking “I Agree” you (a) agree to the “informed consent” above and (b) agree to receive required notices from Let’s Improve Transportation electronically.</p>
			<p class="floatRight" id="submit-button"><input type="button" value="I Agree" style="font-size:14pt;" onClick="submit2B()" /> <input type="button" value="I Do Not Agree" style="font-size:14pt;" onClick="cancel()" /></p>
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
