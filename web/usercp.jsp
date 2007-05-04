<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
	<head>
	<title>Let's Improve Transportation - Edit your Profile</title>
	<!-- Site Wide CSS -->
	<style type="text/css" media="screen">
@import "styles/lit.css";
/* Note:  Move this to user-home.css */

body {font-size:12pt;}

.settings-col1, .settings-col2 {float:left;padding-bottom:3px;_padding-bottom:0px;}

.settings-col1
{
width:200px;
clear:left;
font-weight:bold;
margin-right:2em;
}

.settings-col1 span {font-weight:normal;font-size:.9em;}

.settings-col2{clear:right;}

img {vertical-align:middle;}

.settings-col2 input, .settings-col2 select {margin-right:1em;}

.heading {margin-bottom:.5em;}

#edit-profile, #edit-settings {margin:1em 0em 1em 1em;}
.email-col1,.email-col2,.email-col3 {float:left;}

.email-col1{width:20%;clear:left;}
.email-col2{width:10%;margin-left:1em;}
.email-col3{width:65%;clear:right;}

small {font-size:.75em;font-style:italic;}
.short {margin-bottom:5px;}

fieldset
{
border:2px solid #ADCFDE;
padding:20px 10px;
margin:1em auto;
}

legend
{
font-size:1.15em;
margin:px;
padding:5px;
font-weight:bold;
color:#31496B;
}

#commute #left .settings-col1 {text-align:right;}
#commute #left .settings-col2 {}

#commute #right {width:450px;padding-left:20px;border-left:1px solid #ADCFDE;}

#errors {
border:1px solid #FFE3B9;
padding:10px;
color:D85703;
font-weight:bold;
width:420px;
}

#password #errors {width:550px;}
button {margin-bottom:1em;font-size:1.1em;}

</style>
	<!-- End Site Wide CSS -->
	<!-- Site Wide JS -->
	<script src="scripts/prototype.js" type="text/javascript"></script>
	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<script src="scripts/search.js" type="text/javascript"></script>
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/util.js'></script>
	</head>
	<body>
	<!-- Hack to make col1 & 2 margin heights the same -->
	<!--[if IE]>
		<style type="text/css">
			.settings-col1, .settings-col2 {margin-bottom:0em;margin-top:0em;}
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
	<div id="container" class="clearfix">
	<html:form action="/profileedit.do" method="POST">
		 <html:hidden property="save" value="true"/>
		<h3 class="headerColor">Edit your participant profile information</h3>
		<p>Here you can edit your Let’s Improve Transportation user profile. Some information
			in your user profile will appear to all other participants in the LIT challenge,
			but if a field is marked by this icon ( <img src="images/icon_private.png" /> )
			it will be kept private. You can return to this page to edit your profile at any
			time. Simply click “user settings” at the top of any page.</p>
		<!-- begin EDIT-PROFILE -->
		
		<div id="edit-profile" >
		<span class="red">${userForm.reason}</span>
			<fieldset>
			<legend>About Me</legend>
			<div class="settings-col1">Username</div>
			<div class="settings-col2">${user.loginname}</div>
			<div class="clearBoth"></div>
			</p>
			<div class="settings-col1">Vocation<br />
				<span>What do you do?</span></div>
			<div class="settings-col2">
				<html:text property="vocation" value="${user.vocation}"/>
			</div>
			<div class="clearBoth"></div>
			<div class="settings-col1">Why I'm Here<br />
				<span> Tell us about your interest in transportation and why you've chosen to
				take on the LIT challenge.</span> </div>
			<div class="settings-col2">
				<html:textarea property="profileDesc" value="${user.profileDesc}"/>
			</div>
			<div class="clearBoth"></div>
			</fieldset>
			<fieldset id="commute">
			<legend>My Commute</legend>
			<div id="left" class="floatLeft">
				<h3>Home Location</h3>
				<br />
				<div class="settings-col1"><small>Address Line 1</small></div>
				<div class="settings-col2">
					<html:text property="address1" value="${user.homeAddr}"/>
					<img title="This will not be shown to other participants" src="images/icon_private.png" /> </div>
				<div class="clearBoth"></div>
				<div class="settings-col1"><small>Address Line 2</small></div>
				<div class="settings-col2">
					<html:text property="address2" value="${user.homeAddr2}"/>
					<img title="This will not be shown to other participants" src="images/icon_private.png" /> </div>
				<div class="clearBoth"></div>
				<div class="settings-col1"><small>City</small></div>
				<div class="settings-col2">
					<html:text property="city" value="${user.city}"/>
				</div>
				<div class="clearBoth"></div>
				<div class="settings-col1"><small>State (Abbreviation)</small></div>
				<div class="settings-col2">
					<html:text property="state" value="${user.state}"/>
				</div>
				<div class="clearBoth"></div>
				<div class="settings-col1"><small>ZIP Code</small></div>
				<div class="settings-col2">
					<html:text property="zipcode" value="${user.zipcode}"/>
				</div>
				<div class="clearBoth"></div>
				<h3>Work Location</h3>
				<div class="settings-col1"><small>City</small></div>
				<div class="settings-col2">
					<html:text property="workCity" value="${user.workCity}"/>
				</div>
				<div class="clearBoth"></div>
				<div class="settings-col1"><small>ZIP Code</small></div>
				<div class="settings-col2">
					<html:text property="workZipcode" value="${user.workZipcode}"/>
				</div>
				<div class="clearBoth"></div>
			</div>
			<div id="right" class="floatRight">
				<h3>Method of Transportation</h3>
				<br />
				<span>How do you usually travel from your home to work and other regular errands?</span>
				<p>
					<html:text property="primaryTransport" value="${user.primaryTransport}"/>
				</p>
				<div id="errors" class="floatLeft" style="display:none"> Errors will go here. </div>
				<div class="clearBoth"></div>
			</div>
			</fieldset>
			<button class="floatRight padding5">Update my Profile</button>
			<br class="clearBoth" />
		</div>
		<div align="right"><html:submit property="submit" value="Update My Settings"/></div>
		</html:form>
		<html:form action="/usercp.do" method="POST">
		<html:hidden property="save" value="true"/>
		<!-- end EDIT-PROFILE -->
		<h3 class="headerColor">View and Edit your private user settings</h3>
		<p>This information is private, and not shared with other participants in the LIT
			challenge. You can always return to this page to edit it in the future. </p>
		<!-- begin EDIT-SETTINGS -->
		<div id="edit-settings">
		 
			<fieldset id="email">
			<legend>My E-mail Settings</legend>
			<div class="settings-col1">E-mail address</div>
			<div class="settings-col2">
				<html:text property="email" value="${user.email}" />
			</div>
			<div class="clearBoth"></div>
			<br />
			<div class="email-col1"> Do you wish to receive e-mails notifying you of new activity
				in your discussions? </div>
			<div class="email-col2">
				<label>
				<html:radio property="user.emailNotifyDisc" value="true" />
				Yes</label>
				<br />
				<label>
				<html:radio property="user.emailNotifyDisc" value="false" />
				No</label>
			</div>
			<div class="email-col3"> <strong>About e-mail notification for discussions:</strong><br />
				When you post a new comment in a LIT discussion you always have the option
					of selecting to receive e-mail notifications when there is new activity in that
					discussion. Select no here if you wish to stop receiving any e-mails notifying
					about discussion activity. </div>
			<div class="clearBoth"></div>
			<br />
			<div class="email-col1">Do you wish to receive e-mails from the moderator announcing
				progress in the LIT challenge?</div>
			<div class="email-col2">
				<label>
				<label><html:radio property="user.emailNotify" value="true" />
				Yes</label>
				<br />
				<label>
				<label><html:radio property="user.emailNotify" value="false" />
				No</label>
			</div>
			<div class="email-col3"> <strong>Note to paid research study participants:</strong><br />
				We highly recommend that all paid research study participants select to receive
					e-mails from the moderator. Not receiving these e-mails may result in missing
					important steps in the LIT challenge, and thus impact your qualification for
					payment. </div>
			<div class="clearBoth"></div>
			<br />
			</fieldset>
			<fieldset id="password">
			<legend>Change my Password</legend>
			<div class="floatLeft">
				<div class="settings-col1"></div>
				<div class="clearBoth"></div>
				<div class="settings-col1">Current Password</div>
				<div class="settings-col2">
					<html:password property="currentpassword" redisplay="false"/>
				</div>
				<div class="clearBoth"></div>
				<div class="settings-col1">New Password</div>
				<div class="settings-col2">
					<html:password property="password1" redisplay="false"/>
				</div>
				<div class="clearBoth"></div>
				<div class="settings-col1">Retype New Password</div>
				<div class="settings-col2">
					<html:password property="password2" redisplay="false"/>
				</div>
			</div>
			<div id="errors" class="floatLeft" style="display:none"> Errors will go here. </div>
			<div class="clearBoth"></div>
			</fieldset>
			
			<button class="floatRight padding5">Update My Settings</button>
		</div>
		<!-- end EDIT-SETTINGS -->
		<div align="right"><html:submit property="submit" value="Update My Settings"/></div>
		</html:form>
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
