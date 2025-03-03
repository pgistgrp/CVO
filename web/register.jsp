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
		var email1 = form.email1.value;
		var email2 = form.email2.value;
		var address1 = form.address1.value;
		var address2 = form.address2.value;
		var city = form.city.value;
		var state = form.state.value;
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
		if(email1.length==0 || email2.length==0) {
			errormsg = errormsg + "Email cannot be blank<br />";
			highlightErrors('email1');
 		}
		if(email1!=email2) {
			errormsg = errormsg + "Email fields must match<br />";
			highlightErrors('email2');
 		}
		if(address1.length==0) {
			errormsg = errormsg + "Address Line 1 cannot be blank<br />";
			highlightErrors('address1');
 		}
		if(city.length==0) {
			errormsg = errormsg + "City cannot be blank<br />";
			highlightErrors('city');
 		}
		if(state.length==0) {
			errormsg = errormsg + "State cannot be blank<br />";
			highlightErrors('state');
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
							RegisterAgent.addUser({firstname:firstname, lastname:lastname, email1:email1, email2:email2, address1:address1, address2:address2, city:city, state:state, zipcode:zip, username:username, password1:password1, password2:password2}, {
								callback:function(data){
									if (data.successful){
										
										if(data.q == true) {
											window.location = "register2a.do";		
										} else {
											window.location = "register2b.do";
										}
													
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
		<p>Already have an account at Let's Improve Transportation? <a href="login.do">Sign in</a>.</p
		<h3>Register for Let's Improve Transportation</h3>
	</div>
	<!-- end  SIGN-IN -->
	
	<!-- begin OVERVIEW -->
	<div id="overview" class="box2">
		<p>Let's Improve Transportation (LIT) is part of a research study at the University of Washington. To participate fill out the information below. Qualified participants may be eligible for payment. Complete the information below to find out if you are eligible.</p>
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
				<span class="label">Email address:</span>
				<span class="value"><input id="email1" type="text" /></span><span id="erroremail" style="color:#D85703;font-weight:bold;font-size: .8em;"></span>
			</p><br />
			<p>
				<span class="label">Re-type email address:</span>
				<span class="value"><input id="email2" type="text" /></span>
			</p><br />
			<p>
				<span class="label">Home address line 1: </span>
				<span class="value"><input id="address1" type="text" /></span>
			</p><br />
			<p>
				<span class="label">Home address line 2:</span>
				<span class="value"><input id="address2" type="text" /></span>
			</p><br />
			<p>
				<span class="label">City:</span>
				<span class="value"><input id="city" type="text" /></span>
			</p><br />
			<p>
				<span class="label">State:</span>
				<span class="value">
						<select name="Target" id="state">
							<option value="Washington" selected="selected">Washington</option>
						 	<option value="Alabama">Alabama</option>
							<option value="Alaska">Alaska</option>
							<option value="Arizona">Arizona</option>

							<option value="Arkansas">Arkansas</option>
							<option value="California">California</option>
							<option value="Colorado">Colorado</option>
							<option value="Connecticut">Connecticut</option>
							<option value="Delaware">Delaware</option>
							<option value="District of Columbia">District of Columbia</option>

							<option value="Florida">Florida</option>
							<option value="Georgia">Georgia</option>
							<option value="Guam">Guam</option>
							<option value="Hawaii">Hawaii</option>
							<option value="Idaho">Idaho</option>
							<option value="Illinois">Illinois</option>

							<option value="Indiana">Indiana</option>
							<option value="Iowa">Iowa</option>
							<option value="Kansas">Kansas</option>
							<option value="Kentucky">Kentucky</option>
							<option value="Louisiana">Louisiana</option>
							<option value="Maine">Maine</option>

							<option value="Maryland">Maryland</option>
							<option value="Massachusetts">Massachusetts</option>
							<option value="Michigan">Michigan</option>
							<option value="Minnesota">Minnesota</option>
							<option value="Mississippi">Mississippi</option>
							<option value="Missouri">Missouri</option>

							<option value="Montana">Montana</option>
							<option value="Nebraska">Nebraska</option>
							<option value="Nevada">Nevada</option>
							<option value="New Hampshire">New Hampshire</option>
							<option value="New Jersey">New Jersey</option>
							<option value="New Mexico">New Mexico</option>

							<option value="New York">New York</option>
							<option value="North Carolina">North Carolina</option>
							<option value="North Dakota">North Dakota</option>
							<option value="Ohio">Ohio</option>
							<option value="Oklahoma">Oklahoma</option>

							<option value="Oregon">Oregon</option>
							<option value="Pennsylvania">Pennsylvania</option>
							<option value="Rhode Island">Rhode Island</option>
							<option value="South Carolina">South Carolina</option>
							<option value="South Dakota">South Dakota</option>

							<option value="Tennessee">Tennessee</option>
							<option value="Texas">Texas</option>
							<option value="Utah">Utah</option>
							<option value="Vermont">Vermont</option>

							<option value="Virginia">Virginia</option>
							<option value="West Virginia">West Virginia</option>
							<option value="Wisconsin">Wisconsin</option>
							<option value="Wyoming">Wyoming</option>
						</select>		
				</span>
			</p><br />
			<p>
				<span class="label">ZIP Code:</span>
				<span class="value"><input id="zip" type="text" size="5" maxlength="5"/></span>
			</p><br />
		</div>
		<div class="form-right">
			<div id="errors" style="color:#D85703;font-weight:bold;"></div>
			<p><strong>Privacy of your information:</strong>You personal information is confidential. It will not be shared with anyone. Your email will be used to send you updates of the study progress. If you qualify for payment, your mailing address will only be used to mail you your payment once the study is complete.</p>
		</div>
	</fieldset>
	<fieldset>
		<legend>Create your Let's Improve Transportation account</legend>
		
		<div class="form-left">
			<p>
				<span class="label">User name:</span>
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
			<p>You user name is how you will be identified by other participants on the Lets Improve Transportation website. Feel free to choose a creative name. Your user name should not be the same as your real name. It may consist of a-z, 0-9, and underscores.</p>
		</div>
	</fieldset>
	<div id="step-bar" class="box5 padding5 clearfix">
		<p class="floatLeft" id="step-progress">Step 1 of 3</p>
		<p class="floatLeft" id="submit-description">Click the Submit button, to submit your registration and see if you qualify for our study. You will be asked to fill out a consent form and a questionnaire, before we process your account.</p>
		<p class="floatRight" id="submit-button"><input type="button" value="Next" onclick="submitForm(this.form)" id="submitBtn" style="font-size:14pt;"/>
		</p>
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
