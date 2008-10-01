<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
	<head>
	<title>ClimateConcerns - Registration</title>
	<!-- Site Wide CSS -->
<style type="text/css" media="screen">
@import "styles/lit.css";
@import "styles/registration-1.css";
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

	
	<script type='text/javascript'> 
	
	function highlightErrors(inputDiv) {
			$(inputDiv).style.background = '#FFF1DC';
			$(inputDiv).ancestors()[0].siblings()[0].style.color = '#D85703';
			$(inputDiv).ancestors()[0].siblings()[0].style.fontWeight = 'bold';
	}
	
	function submitForm(form) {

		var labels = document.getElementsByClassName('label');
		var values = document.getElementsByTagName('input');
		
		for (var i = 0; i < labels.length; i++){
				labels[i].style.color = "";
				labels[i].style.fontWeight = "";
		};

		for (var i = 0; i < values.length; i++){
				if (values[i].id != 'submitBtn'){ // Ignore the Submit button
					values[i].style.background = "";
				} else {}
		};
		
		var errordiv = document.getElementById("errors");
		var errorusernamediv = document.getElementById("errorusername");
		var erroremaildiv = document.getElementById("erroremail");
		var errormsg = "";
 
		var firstname = form.fname.value;	
		var lastname = form.lname.value;
		var age = $('age').value;
		var gender = $('gender').value;
		var income = $('income').value;
		var education = $('education').value;
		var email1 = form.email1.value;
		var email2 = form.email2.value;
    var zip = form.zip.value;
		var username = form.username.value;
		var password1 = form.password1.value;
		var password2 = form.password2.value;
		
		if(firstname.length==0) {
			errormsg = errormsg + "Name cannot be blank<br />";
			highlightErrors('fname');
 		}
		if(lastname.length==0) {
			errormsg = errormsg + "Last name cannot be blank<br />";
			highlightErrors('lname');
 		}
		if($('age').value=='0') {
			errormsg = errormsg + "Please select age range<br />";
			highlightErrors('age');
 		}
		if($('gender').value=='') {
			errormsg = errormsg + "Please select gender<br />";
			highlightErrors('gender');
 		}
		if($('income').value=='0') {
			errormsg = errormsg + "Please select income range<br />";
			highlightErrors('income');
 		}
		if($('education').value=='0') {
			errormsg = errormsg + "Please select education range<br />";
			highlightErrors('education');
 		}
		if(email1.length==0 || email2.length==0) {
			errormsg = errormsg + "Email cannot be blank<br />";
			highlightErrors('email1');
 		}
		if(email1!=email2) {
			errormsg = errormsg + "Email fields must match<br />";
			highlightErrors('email2');
 		}
    if(zip.length < 5) {
      errormsg = errormsg + "You must enter in a 5-digit ZIP code<br/>";
      highlightErrors('zip');
    }
		if(username.length==0) {
			errormsg = errormsg + "You must choose a user name<br />";
			highlightErrors('username');
 		}
		if(password1.length<6) {
			errormsg = errormsg + "Your password must be at least six characters<br />";
			highlightErrors('password1');
 		}
		if(password2.length==0) {
			errormsg = errormsg + "Don't forget to retype your password<br />";
			highlightErrors('password2');
 		}
		if(password1!=password2) {
			errormsg = errormsg + "Both password fields must match<br />";
			highlightErrors('password1');
			highlightErrors('password2');
 		}
		/*
		if(email1.indexOf(' ')==-1 && 0<email1.indexOf('@') && email1.indexOf('@')+1 < email1.length) {     
			errormsg = errormsg + "Invalid email address!<br />" + email1;
		}
		*/
		if(errormsg.length == 0) {
			RegisterAgent.checkUsername({username:username}, {
				callback:function(data){
					if (data.available){		
					//if Username is available
						//check email
						RegisterAgent.checkEmail({email:email1}, {
						callback:function(data){
							if (data.available){
						//if email is not used already
							RegisterAgent.addSarpUser({firstname:firstname, lastname:lastname, email1:email1, email2:email2, age:age, income:income, gender:gender, education:education, zipcode:zip, username:username, password1:password1, password2:password2}, {
								callback:function(data){
                  alert(1);
									if (data.successful){
											window.location = "/";		
									} else {
                      alert(data.reason);
                  }
								},
								errorHandler:function(errorString, exception){ 
								alert("SystemAgent.createQuotaStats( error:" + errorString + exception);
								}
							});
							// else if email is taken
							} else {
							errormsg = errormsg + "Email Address is already being used in this system.";
							erroremaildiv.innerHTML = "&nbsp;" + errormsg;
							highlightErrors('email1');
							highlightErrors('email2');
							}
							},
							errorHandler:function(errorString, exception){ 
							alert("SystemAgent.checkEmail( error:" + errorString + exception);
							}
						});	
				// else if user name is taken
				} else {
					errormsg = errormsg + "Username Not Available";
					errorusernamediv.innerHTML = "&nbsp;" + errormsg;
					highlightErrors('username');
				}
				},
				errorHandler:function(errorString, exception){ 
				alert("SystemAgent.checkUsername( error:" + errorString + exception);
				}
			});	
		} else {
			errordiv.innerHTML = '<h3 class=\"headerColor\">Please change the following:</h3>' + errormsg;
		}
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
	<!-- #register -->
	<div id="register">
	<!-- begin SIGN-IN -->
	<div>
		<p>Already have an account at ClimateConcerns.org? <a href="login.do">Sign in</a>.</p
		<h3>Register for Voicing Climate Concerns</h3>
	</div>
	<!-- end  SIGN-IN -->
	
	<!-- begin OVERVIEW -->
	<div id="overview" class="box2">
		<p>Voicing Climate Concerns (VCC) is part of a research study at the University of Washington and Oregon State University. Before participating, please help us by filling in the information below. </p>
	</div>
	<!-- end OVERVIEW -->
	
	<!-- begin CONTACT INFO -->
	<form action="" method="get" name="register">
	<fieldset>
		<legend>Provide your personal contact information</legend>
		<div class="form-left">
			<p>
				<span class="label">First Name:</span>
				<span class="value"><input id="fname" type="text" /></span>
			</p><br />
			<p>
				<span class="label">Last Name:</span>
				<span class="value"><input id="lname" type="text" /></span>
			</p><br />
			<p>
				<span class="label">Age:</span>
				<span class="value">
          <select id="age">
            <option value="0">Please select</option>
            <option value="1">18 - 24</option>
            <option value="2">25 - 34</option>
            <option value="3">35 - 44</option>
            <option value="4">45 - 54</option>
            <option value="5">55 - 64</option>
            <option value="6">65 and over</option>
          </select>
        </span>
			</p><br />
			<p>
				<span class="label">Gender:</span>
				<span class="value">
          <select id="gender">
            <option value="">Please select</option>
            <option value="male">Male</option>
            <option value="female">Female</option>
          </select>
        </span>
			</p><br />
			<p>
				<span class="label">Income Level:</span>
				<span class="value">
          <select id="income">
            <option value="0">Please select</option>
            <option value="1">Less than $25,000</option>
            <option value="2">$25,000 to $49,999</option>
            <option value="3">$50,000 to $74,999</option>
            <option value="4">$75,000 to $99,999</option>
            <option value="5">$100,000 to $149,999</option>
            <option value="6">$150,000 or more</option>
          </select>
        </span>
			</p><br />
			<p>
				<span class="label">Education Level:</span>
				<span class="value">
          <select id="education">
            <option value="0">Please select</option>
            <option value="1">Some Highschool</option>
            <option value="2">Highschool Graduate</option>
            <option value="3">Some College</option>
            <option value="4">College Graduate</option>
            <option value="5">Some Graduate School</option>
            <option value="6">Graduate School Graduate</option>
          </select>
        </span>
			</p><br />
			<p>
        <span class="label">ZIP Code:</span>
        <span class="value"><input id="zip" type="text" size="5" maxlength="5"/></span>
			</p><br />
			<p>
				<span class="label">Email address:</span>
				<span class="value"><input id="email1" type="text" /></span><span id="erroremail" style="color:#D85703;font-weight:bold;font-size: .8em;"></span>
			</p><br />
			<p>
				<span class="label">Re-type email address:</span>
				<span class="value"><input id="email2" type="text" /></span>
			</p><br />
		</div>
		<div class="form-right">
			<div id="errors" style="color:#D85703;font-weight:bold;"></div>
			<p><strong>Privacy of your information:</strong>Your personal information is confidential. It will not be shared with anyone. Your email will be used to send you updates of the study progress. </p>
		</div>
	</fieldset>
	<fieldset>
		<legend>Create your Voicing Climate Concerns account</legend>
		
		<div class="form-left">
			<p>
				<span class="label">Login name:</span>
				<span class="value"><input id="username" type="text" /></span><span id="errorusername" style="color:#D85703;font-weight:bold;font-size: .8em;"></span>
			</p><br />
			<p>
				<span class="label">Password:</span>
				<span class="value">
					<input id="password1" type="password" />
					<br />
					<small style="display:block;margin:.5em 0em;color:#777;">
						Six characters or more. Capitalization matters!
					</small>
				</span>
			</p><br />
			<p>
			<span class="label">Re-type password:</span><span class="value">
			<input id="password2" type="password" />
			</span></p>
			<br />

		</div>
		<div class="form-right">
			<p>You user name is how you will be identified by other participants on the Voicing Climate Concerns website. Feel free to choose a creative name. Your user name should not be the same as your real name. It may consist of a-z, 0-9, and underscores.</p>
		</div>
	</fieldset>
	<div id="step-bar" class="box5 padding5 clearfix" style="text-align:center;">
    <input type="button" value="Register" onclick="submitForm(this.form)" id="submitBtn" style="font-size:14pt;"/>
	</div>
	</form>
	<!-- end CONTACT INFO -->
	<div class="clearBoth"></div>
	</div>
	<!-- end register -->
	
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
