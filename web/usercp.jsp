<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
	<head>
	<title>Profile</title>
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

#errors1, #errors2 {
border:1px solid #F2AF27;
background:#FFF1DC;
padding:10px;
color:#D85703;
font-weight:bold;
width:650px;
}

#errors1 li span, #errors2 li span {font-size:.9em;color:#000;font-weight:normal;margin-left:5px;}

#errors1 h3, #errors2 h3 {color:#000; }
#password #errors {width:550px;}
.submit {margin-bottom:1em;font-size:1.1em;}

    </style>
	<!-- End Site Wide CSS -->
	<!-- Site Wide JS -->
	<script src="scripts/prototype.js" type="text/javascript"></script>
	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<script src="scripts/search.js" type="text/javascript"></script>
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/util.js'></script>
	<script type='text/javascript'>

function highlightErrors(inputDiv) {
		$(inputDiv).style.background = '#FFF1DC';
}

function validateForm(form,formId){
	
	var errordiv1 = document.getElementById("errors1");
	var errormsg1 = "";
	var errordiv2 = document.getElementById("errors2");
	var errormsg2 = "";

	var address1 = $F('address1');
	var hcity = $F('hcity');	
	var wcity = $F('wcity');
	var state = "WA";//$F('state');
	var hzip = $F('hzip');	
	var wzip = $F('wzip');	
	
	var mail = $F('mail');
	var password1 = $F('password1');
	var password2 = $F('password2');
	
	//var oldpw = "${user.password}"; // This gets the encrypted password, so it doesn't work.
	var currentpw = $F('currentpw');
	
	if (formId == 1){
		
		if(address1.length==0) {
			errormsg1 = errormsg1 + "<li>Address line 1 cannot be blank</li>";
			highlightErrors('address1');
		}
		
		if(hcity.length==0) {
			errormsg1 = errormsg1 + "<li>Home city cannot be blank<br/><span>This helps us put your concerns and discussion posts into context.</span></li>";
			highlightErrors('hcity');
		}
	
		if(wcity.length==0) {
			errormsg1 = errormsg1 + "<li>Work city cannot be blank<br/><span>This helps us put your concerns and discussion posts into context.</span></li>";
			highlightErrors('wcity');
		}
	
		if(state.length==0) {
			errormsg1 = errormsg1 + "<li>State cannot be blank</li>";
			highlightErrors('state');
		}
		if(hzip.length < 5) {
			errormsg1 = errormsg1 + "<li>Home ZIP code must be 5 digits long. <br/><span>We use this information to calculate the length of your commute.</span></li>";
			highlightErrors('hzip');
		}
	
		if(wzip.length < 5) {
			errormsg1 = errormsg1 + "<li>Work ZIP code must be 5 digits long. <br /><span>We use this information to calculate the length of your commute.</span></li>";
			highlightErrors('wzip');
		}
		
		if(errormsg1.length != 0){
			errordiv1.style.display = "";
			errordiv1.innerHTML = '<h3>Please change the following:</h3><ul>' + errormsg1 + '</ul>';
		}	else {
		$('profileForm').submit();
		$('settingsForm').submit();
		}

		
	} else if (formId == 2) {
		if(mail.length == 0) {
			errormsg2 = errormsg2 + "<li>Your e-mail address cannot be blank.<br/><span>  This information is kept confidential, but we need to know how to contact you during the study.</span></li>";
			highlightErrors('mail');
		}

		/*if(currentpw.length != 0 && currentpw != oldpw) {
		alert(oldpw);
			errormsg2 = errormsg2 + "<li>Check that your Current Password is correct.</li>";
			highlightErrors('currentpw');
 		}*/

		/*if(password1.length > 0 && currentpw.length == 0) {
			errormsg2 = errormsg2 + "<li>You must enter your Current Password before creating a New Password</li>";
			highlightErrors('password1');
 		}*/

		if(password1.length > 0 && password1.length < 6) {
			errormsg2 = errormsg2 + "<li>Your new password must be at least six characters.</li>";
			highlightErrors('password1');
 		}
		
		if(password1.length > 0 && password2.length == 0) {
			errormsg2 = errormsg2 + "<li>Don't forget to retype your password!</li>";
			highlightErrors('password2');
 		}
		
		if(password1.length > 0 && password1!=password2) {
			errormsg2 = errormsg2 + "<li>Both password fields must match. <br/><span>Remember, passwords are case sensitive!</span></li>";
			highlightErrors('password1');
			highlightErrors('password2');
 		}

		if(errormsg2.length != 0){
			errordiv2.style.display = "";
			errordiv2.innerHTML = '<h3>Please change the following:</h3><ul>' + errormsg2 + '</ul>';
		}	else {$('settingsForm').submit();}
	
	} else {}
	
} 

function formOther(){
	if ($F('primaryTrans') == "Other"){
		$('transOther').style.display = "";	
	} else {$('transOther').style.display = "none";} 
}
	</script>
<event:pageunload />
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
  <wf:nav />
		<!-- End header -->
	</div>
	<!-- End header -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu"> </div>
	<!-- End header menu -->
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container" class="clearfix">
	<html:form action="/profileedit.do" styleId="profileForm" method="POST">
		 <html:hidden property="save" value="true"/>
		<h3 class="headerColor" style="display:inline">Edit your participant profile information&nbsp;&nbsp;&nbsp;</h3>
		<pg:url page="publicprofile.do" params="userId=${baseuser.id}">View your profile</pg:url>
		<p>Here you can edit your Voicing Climate Concerns user profile. Some information
			in your user profile will appear to all other participants,
			but if a field is marked by this icon ( <img src="images/icon_private.png" /> )
			it will be kept private. You can return to this page to edit your profile at any
			time. Simply click "user settings" at the top of any page.</p>
		<!-- begin EDIT-PROFILE -->
		
		<div id="edit-profile" >
		<span style="color:#FF0000;">${userForm.reason}</span>
			<fieldset>
			<legend>About me</legend>
			<div class="settings-col1">Username</div>
			<div class="settings-col2">${user.loginname}</div>
			<div class="clearBoth"></div>
			</p>
			<fieldset id="commute">
			<legend>My Commute</legend>
			<div id="left" class="floatLeft">
				<h3>Home Location</h3>
				<br />
				<div class="settings-col1"><small>Address line 1</small></div>
				<div class="settings-col2">
					<html:text property="address1" styleId="address1" value="${user.homeAddr}"/>
					<img title="This will not be shown to other participants" src="images/icon_private.png" /> </div>
				<div class="clearBoth"></div>
				<div class="settings-col1"><small>Address line 2</small></div>
				<div class="settings-col2">
					<html:text property="address2" value="${user.homeAddr2}"/>
					<img title="This will not be shown to other participants" src="images/icon_private.png" /> </div>
				<div class="clearBoth"></div>
				<div class="settings-col1"><small>City</small></div>
				<div class="settings-col2">
					<html:text property="city" styleId="hcity" value="${user.city}"/>
				</div>
				<div class="clearBoth"></div>
				<div class="settings-col1"><small>State</small></div>
				<div class="settings-col2">
					<html:select property="state">
						<html:option value="WA">Washington</html:option>
						<html:option value="AL">Alabama</html:option>
						<html:option value="AK">Alaska</html:option>
						<html:option value="AZ">Arizona</html:option>
						<html:option value="AR">Arkansas</html:option>
						<html:option value="CA">California</html:option>
						<html:option value="CO">Colorado</html:option>
						<html:option value="CT">Connecticut</html:option>
						<html:option value="DE">Delaware</html:option>
						<html:option value="DC">District Of Columbia</html:option>
						<html:option value="FL">Florida</html:option>
						<html:option value="GA">Georgia</html:option>
						<html:option value="HI">Hawaii</html:option>
						<html:option value="ID">Idaho</html:option>
						<html:option value="IL">Illinois</html:option>
						<html:option value="IN">Indiana</html:option>
						<html:option value="IA">Iowa</html:option>
						<html:option value="KS">Kansas</html:option>
						<html:option value="KY">Kentucky</html:option>
						<html:option value="LA">Louisiana</html:option>
						<html:option value="ME">Maine</html:option>
						<html:option value="MD">Maryland</html:option>
						<html:option value="MA">Massachusetts</html:option>
						<html:option value="MI">Michigan</html:option>
						<html:option value="MN">Minnesota</html:option>
						<html:option value="MS">Mississippi</html:option>
						<html:option value="MO">Missouri</html:option>
						<html:option value="MT">Montana</html:option>
						<html:option value="NE">Nebraska</html:option>
						<html:option value="NV">Nevada</html:option>
						<html:option value="NH">New Hampshire</html:option>
						<html:option value="NJ">New Jersey</html:option>
						<html:option value="NM">New Mexico</html:option>
						<html:option value="NY">New York</html:option>
						<html:option value="NC">North Carolina</html:option>
						<html:option value="ND">North Dakota</html:option>
						<html:option value="OH">Ohio</html:option>
						<html:option value="OK">Oklahoma</html:option>
						<html:option value="OR">Oregon</html:option>
						<html:option value="PA">Pennsylvania</html:option>
						<html:option value="RI">Rhode Island</html:option>
						<html:option value="SC">South Carolin</html:option>
						<html:option value="SD">South Dakota</html:option>
						<html:option value="TN">Tennessee</html:option>
						<html:option value="TX">Texas</html:option>
            <html:option value="UT">Utah</html:option>
						<html:option value="VT">Vermont</html:option>
						<html:option value="VA">Virginia</html:option>
						<html:option value="WA">Washington</html:option>
						<html:option value="WV">West Virginia</html:option>
						<html:option value="WI">Wisconsin</html:option>
						<html:option value="WY">Wyoming</html:option>
					</html:select>
				</div>
				<div class="clearBoth"></div>
				<div class="settings-col1"><small>ZIP code</small></div>
				<div class="settings-col2">
					<html:text property="zipcode" styleId="hzip" value="${user.zipcode}"/>
				</div>
				<div class="clearBoth"></div>
				<h3>Work location</h3>
				<div class="settings-col1"><small>City</small></div>
				<div class="settings-col2">
					<html:text property="workCity" styleId="wcity" value="${user.workCity}"/>
				</div>
				<div class="clearBoth"></div>
				<div class="settings-col1"><small>ZIP code</small></div>
				<div class="settings-col2">
					<html:text property="workZipcode" styleId="wzip" value="${user.workZipcode}"/>
				</div>
				<div class="clearBoth"></div>
			

				</p>
				<div class="clearBoth"></div>
			</div>
			</fieldset>
			<div id="errors1" class="floatLeft" style="display:none"> Errors will go here. </div>
			
			<br class="clearBoth" />
		</div>

		</html:form>
		<html:form action="/usercp.do" styleId="settingsForm" method="POST">
		<html:hidden property="save" value="true"/>
		<!-- end EDIT-PROFILE -->
		<h3 class="headerColor">View and edit your private user settings</h3>
		<p>This information is private, and not shared with other participants. You can always return to this page to edit it in the future. </p>
		<!-- begin EDIT-SETTINGS -->
		<div id="edit-settings">
		 
			<fieldset id="email">
			<legend>My e-mail settings</legend>
			<div class="settings-col1">E-mail address</div>
			<div class="settings-col2">
				<html:text property="email" styleId="mail" value="${user.email}" />
			</div>
			<div class="clearBoth"></div>
			<br />
			<div class="email-col1">I wish to receive e-mails notifying me of new activity in my discussions.</div>
			<div class="email-col2">
				<label>
				<html:radio property="emailNotifyDisc" value="true" />
				Yes</label>
				<br />
				<label>
				<html:radio property="emailNotifyDisc" value="false" />
				No</label>
			</div>
			<div class="email-col3"> <strong>About e-mail notification for discussions:</strong><br />
				When you post a new comment in the discussion you always have the option
					of selecting to receive e-mail notifications when there is new activity in that
					discussion. Select no here if you wish to stop receiving e-mails notifying you
					about discussion activity. </div>
			<div class="clearBoth"></div>
			<br />
			<div class="email-col1">I wish to receive e-mails from the moderator announcing
				progress in the experiment.</div>
			<div class="email-col2">
				<label>
				<html:radio property="emailNotify" value="true" />
				Yes</label>
				<br />
				<label>
				<html:radio property="emailNotify" value="false" />
				No</label>
			</div>
			<div class="email-col3"> <strong>Note to research study participants:</strong><br />
				We highly recommend that all research study participants choose to receive
					e-mails from the moderator. Not receiving these e-mails may result in missing
					important steps in the process. </div>
			<div class="clearBoth"></div>
			<br />
			</fieldset>
			<fieldset id="password">
			<legend>Change my password</legend>
			<div class="floatLeft">
				<div class="settings-col1"></div>
				<div class="clearBoth"></div>
				<div class="settings-col1">Current password</div>
				<div class="settings-col2">
					<html:password property="currentpassword" styleId="currentpw" redisplay="false"/>
				</div>
				<div class="clearBoth"></div>
				<div class="settings-col1">New password</div>
				<div class="settings-col2">
					<html:password property="password1" styleId="password1" redisplay="false"/>
				</div>
				<div class="clearBoth"></div>
				<div class="settings-col1">Retype new password</div>
				<div class="settings-col2">
					<html:password property="password2" styleId="password2" redisplay="false"/>
				</div>
			</div>
			<div class="clearBoth"></div>
			</fieldset>
			<div id="errors2" class="floatLeft" style="display:none"> Errors will go here. </div>
			<input type="button" class="floatRight padding5 submit" onclick="validateForm(this.form,2)" value="Update My Settings"/>
		</div>
		<!-- end EDIT-SETTINGS -->
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
