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
		<p>If you choose to be in this study, we would like to participate in the <em>Let’s Improve Transportation</em> website.  The website will be active for 4 weeks, and we would like you to participate in the various activities within the website during that time period.  You may refuse to participate in any portion of this website.  With your permission, we would like to collect information about your participation within the website (including discussions posts and other comments you make within the website).  Beyond participating in the various activities within the website, we also ask that you complete 5 questionnaires, which shall be administered electronically during the course of the 4-week activity.  These 5 questionnaires will ask you to reflect on your experiences and opinions of the website, and will be used to evaluate the website.  You may refuse to answer any question within any of the 5 questionnaires.</p>
		
		<h2 class="centerAlign">RISKS, STRESS, OR DISCOMFORT</h2>
		<p>Some people feel that providing information for research is an invasion of privacy.  We have addressed concerns for your privacy in the section below. </p>
		
		<h2 class="centerAlign">ALTERNATIVES TO TAKING PART IN THIS STUDY</h2>
		<p>The alternative to taking part in this study is to not take part in this study.</p>
		
		<h2 class="centerAlign">BENEFITS OF THE STUDY</h2>
		<p>For participating in the 12 activities within the website you will receive payment of $40.00.  Should you not be able to participate in all 12 activities over the course of 4 weeks, you will be paid a pro-rated amount for the activities you have completed:</p>
		<ul>
		    <li>Map your travel paths;</li>
		    <li>Brainstorm your concerns;</li>
		    <li>Discuss the concerns of your fellow participants;</li>
		    <li>Discuss the planning factors used to fund transportation projects;</li>
		    <li>Weigh the planning factors;</li>
		    <li>Discuss the proposed transportation projects;</li>
		    <li>Discuss the funding options;</li>
		    <li>Create your preferred package of transportation projects and funding options;</li>
		    <li>Discuss the proposed packages;</li>
		    <li>Vote to endorse a proposed package;</li>
		    <li>Discuss the final report of the website; and</li>
		    <li>Vote on the final report.</li>
		</ul>
		<p>You will also receive payment of $20.00 for providing responses to the 5 questionnaires, administered electronically.  Each questionnaire varies in length, but should last no longer than 30 minutes:</p>
		<ul>
		    <li>Entrance questionnaire ($10.00);</li>
		    <li>Three short mid-website questionnaires ($5.00); and</li>
		    <li>Exit questionnaire ($5.00).</li>
		</ul>
		<p>At the conclusion of the 4-week activity, your payment will be calculated based on your participation.  This payment will be mailed to an address you specify, following the conclusion of the website activity.</p>
		
		<h2 class="centerAlign">OTHER INFORMATION</h2>
		<p>Taking part in this study is voluntary.  You can stop at any time. Information about you is confidential.  We will code the study information and keep the link between your identifying information and the code in a separate, secured location until October 2009.  Then we will destroy the link.  The following groups may need to review study records about you: institutional oversight review offices at the research site, the University of Washington, and federal regulators.  If the results of this study are published or presented, we will not use your name.  </p>
		<h3>Subject's Statement</h3>
		<p>This study has been explained to me.  I volunteer to take part in this research.  If I have questions later on about the research I can ask the investigator listed above.  If I have questions about my rights as a research subject, I can call the University of Washington Human Subjects Division at (206) 543-0098.</p>
		<p>By clicking "Agree" below, I give my permission for the researcher to collect information about my use of the website, and to use information collection from my response to the 5 questionnaires. </p>
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
