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
@import "styles/registration-2a.css";
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
function submit2A() {
	document.formreg2a.agree.disabled=true;
	document.formreg2a.disagree.disabled=true;
	var errordiv = document.getElementById("errors");
	var errormsg = "";

	var interview1 = document.formreg2a.interview[0].checked;
	var interview2 = document.formreg2a.interview[1].checked;
	//var observation = form.observation.value;
	var observation1 = document.formreg2a.observation[0].checked;
	var observation2 = document.formreg2a.observation[1].checked;

	RegisterAgent.addQuotaInfo({interview:interview1, observation:observation1});
	setTimeout("redirect()",100);
}
function redirect() {
	window.location = "registerq.do";
}
function cancel() {
	RegisterAgent.createCancel({});
	setTimeout("redirectcancel()",100);
}
function redirectcancel() {
	window.location = "main.do";
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

	
<!-- start #reg2a -->
<div id="reg2a">
	<div class="">
		<h3>Welcome ${baseuser.loginname}. You qualify to participate in Let's Improve Transportation!</h3>
		<p>Congratulations! You are eligible to receive $40 if you participate in each stage of the Let’s Improve Transportation experiment (AKA the “<em>LIT Challenge</em>”), and an additional $20 for completing three questionnaires during the experiment. You can participate at your own convenience wherever you have access to the Internet. The LIT Challenge will take place over a 4 week period, starting <strong>October 1, 2007</strong> and concluding <strong>October 29, 2007</strong>. We estimate that full participation will take about 8-10 hours of your time, spread out over the 4-week period. However you are encouraged to spend as much time on the LIT website as you like. Details about the requirements for payment can be found in the informed consent agreement at the bottom of this page. If you prefer, you can also choose to participate as an <a href="#">unpaid volunteer</a>, with no requirements for your participation.</p>
	</div>
	<!-- begin OPPORTUNITIES -->
	<form name="formreg2a">
	<fieldset>
	<legend>Additional opportunities for paid participation in our research study</legend>
	<p>In addition to your participation in the <em>LIT challenge</em> you are invited to also participate in these other parts of our research study. Payment would be made upon completion of both the <em>LIT Challenge</em> and the additional activities selected.  Not all participants will be selected to participate in these activities. Please indicate your interest in these additional research study opportunities below. </p>
	<p>
		<span class="consent-label">1-hour, face-to-face interview:</span> 
		<span class="consent-value">
			<label>
				<input type="radio" name="interview" value="yes" checked="checked" /> Yes, I'm interested
			</label>
			<label>
				<input type="radio" name="interview" value="no" /> No, I'm not interested
			</label><br/>
			<small>Must travel to the University of Washington Seattle campus for a 1- hour, audio-recorded interview. A researcher will contact you to schedule an appointment. Payment = $40</small>
		</span>
	</p><br />
	
	<div class="clearBoth"></div>
			
	<p>
		<span class="consent-label">Two 30-minute observations of you using the website: </span> 
		<span class="consent-value">
			<label>
				<input type="radio" name="observation" value="yes" checked="checked" /> Yes, I'm interested
			</label>
			<label>
				<input type="radio" name="observation" value="no" /> No, I'm not interested
			</label><br/>
			<small>Must travel to the University of Washington Seattle campus for two 30- minute, video/audio-recorded interviews. A researcher will contact you to schedule an appointment. Payment = $60</small>
		</span>
	</p><br/>
	</fieldset>
	
	<fieldset>
		<legend>Review the Informed Consent Agreement</legend>
		<p>If you wish to be a participant in our research study, you must review and accept this information statement.</p>
		<div id="agreement" class="box3">
		<h2 class="centerAlign">UNIVERSITY OF WASHINGTON INFORMATION STATEMENT<br/>TRANSPORTATION STUDY</h2>
		<h3>Investigators</h3>
		<p style="margin-left:20px">
			Timothy L. Nyerges, Principal Investigator, Professor, Department of Geography, 206-543-5296<br />
			Terrence Brooks, Associate Prof., Information School, 206-543-2646<br />
			Piotr Jankowski, Professor, Department of Geography, San Diego State University, 619-594-0640<br />
			G. Scott Rutherford, Professor, Civil & Environmental Engr., 206-685-2481<br/>
			Rhonda Young, Assistant Professor, Civil & Architectural Engr., University of Wyoming, 307-766-2184<br/>
			Kevin Ramsey, Research Assistant, Department of Geography, 206-616-9018<br/>
			Martin Swobodzinski, Research Assistant, Geography, San Diego State University, 619-594-5437<br/>
			Guirong Zhou, Research Assistant, Department of Geography, 206-616-9018<br/>
			Matthew W. Wilson, Research Assistant, Department of Geography, 206-616-9018 <br/>
		</p>
		<h3>Researcher's statement</h3>
		<p>We are asking you to be in a research study. The purpose of this information statement is to give you the information you will need to help you decide whether to be in the study or not. Please read the form carefully.</p>
		<h2 class="centerAlign">PURPOSE OF THE STUDY</h2>
		<p>The purpose of this study is to evaluate a new website which is designed to enhance public participation in regional transportation improvement decision making. Researchers will examine how well the website and facilitated online moderation process enables participants to learn about transportation problems and proposed solutions, voice their concerns, construct solutions, and collectively prepare recommendations for decision makers.</p>
		<h2 class="centerAlign">STUDY PROCEDURES</h2>
		<p>The Let’s Improve Transportation Challenge experiment will last for 4 weeks, the beginning of which you will be notified via email (expected during the last quarter of 2007). During that time, you will be asked to participate in several activities on the Let’s Improve Transportation website.  You may choose not to participate in any of these activities.  The website includes five main steps: (1) brainstorm concerns, (2) assess transportation improvement factors, (3) create transportation packages, (4) evaluate candidate packages, (5) prepare group report. Each step includes 2-3 short sub-steps, totally 12 activities in all. Instructions are provided to guide you along the way. As you use the website, information about your participation will automatically be collected. This includes comments you post in a discussion forum and the ways in which you use various online tools. (Note: We will not collect any information about your activities which do not occur on the Let’s Improve Transportation website.)  You will receive periodic emails announcing the beginning of each new activity to remind you of when your participation is requested. We estimate that completion of these activities will take a minimum of 6-8 hours over the course of 4 weeks. You will have a great deal of flexibility in choosing the dates and timing of your participation. </p>
		
		<p>You will also be asked to complete 5 online questionnaires during the course of the 4-week study.  These 5 questionnaires will ask you to reflect on your personal background as well as your opinions of the Let’s Improve Transportation website.  Each questionnaire varies in length. The longest questionnaire will take approximately 30 minutes to complete.  Others will be significantly shorter.  You may choose not to answer any question within any of the 5 questionnaires.</p>
		
		<h2 class="centerAlign">RISKS, STRESS, OR DISCOMFORT</h2>
		<p>Some people worry about sharing personal information on a website.  We discuss how we address privacy concerns in the section below.</p>
		
		<h2 class="centerAlign">BENEFITS OF THE STUDY</h2>
		<p>Current transportation improvement decision tools do not address community quality of life, economic benefits, social equity, etc. in regards to transportation improvement projects. They do not integrate data across projects and scales, and they do not make information readily accessible to those who need it. The tools we are developing and evaluating in this project will better represent the complexity of transportation problems, integrate information via the Internet, and contribute to the transparency of the decision process by making information more accessible and understandable to interested parties.  Moreover, the software tools that we develop to integrate transportation programming data are likely to be useful in environmental, social and economic decision situations, as well.  There are no individual benefits. </p>
		
		<h2 class="centerAlign">OTHER INFORMATION</h2>
		<p>Taking part in this study is voluntary.  You can skip an activity or stop at any time. For participating in the 12 activities within the website you will receive payment of $40.00.  Should you not be able to participate in all the activities you will be paid a pro-rated amount for the activities you have completed. A list of each activity, as well as a calendar of when they will occur, is available when you log in to the website: <a href="http://www.letsimprovetransportation.org">http://www.letsimprovetransportation.org</a>. We reserve the right to ban participation for abuse of, or misconduct within, the system.  The email address provided for your user account must be a working email address. </p>
		<p>You will also receive payment of $20.00 for providing responses to the 5 online questionnaires. If you only complete some of the questionnaires, your pay will be pro-rated as follows:</p>
		<ul>
			<li>Beginning-of-study questionnaire ($10.00);</li>
			<li>Three short mid-study questionnaires ($5.00);</li>
			<li>End-of-study questionnaire ($5.00).</li>
		</ul>
		
		<p>At the conclusion of the 4-week activity, your payment will be calculated based on your participation.  This payment will be mailed to an address you specify.  All of your personal information is confidential.  Other participants in the study will not have access to your personal information.  Your personal information will be stored separately from the study data we collect, and it will be deleted at the completion of this study (October 2009).  Direct subject identifiers will be sent and retrieved through Secure Socket Layer (SSL) technology, an approach used throughout the University of Washington computing environment. The identifiers will be stored separately from regular subject study data.  The following groups may need to review study records about you: institutional oversight review offices at the research site, the University of Washington, and federal regulators.  If the results of this study are published or presented, we will not use your name.</p>
		
		<h3>Subject's Statement</h3>
		<p>This study has been explained to me.  I volunteer to take part in this research.  If I have questions later on about the research I can ask the investigator listed above.  If I have questions about my rights as a research subject, I can call the University of Washington Human Subjects Division at (206) 543-0098.</p>
		<p>By clicking “Agree” below, I give my permission for the researcher to collect information about my use of the website, to collect information from my responses to the 5 questionnaires, and to send me emails about the study progress.</p>
		</div>
	</fieldset>
	<!-- end OPPORTUNITIES -->
	<div class="clearBoth"></div>
	
	<div id="step-bar" class="box5 padding5 clearfix">
			<p class="floatLeft" id="step-progress">Step 2 of 3</p>
			<p class="floatLeft" id="submit-description" style="width:420px;">By clicking “I Agree” you (a) agree to the “informed consent” above and (b) agree to receive required notices from Let’s Improve Transportation electronically.</p>
			<p class="floatRight" id="submit-button"><input type="button" name="agree" value="I Agree" style="font-size:14pt;" onClick="submit2A()" /> <input type="button" name="disagree" value="I Do Not Agree" style="font-size:14pt;" onClick="cancel()"/></p>
		</div>
	
</div>
<!-- end #reg2a -->	
	
	
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
