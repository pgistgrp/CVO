<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>Register</title>
<link rel="stylesheet" type="text/css" href="/styles/pgist.css">

<style>
	label{display: block; margin: 10px;}
	</style>
</head>

<body bgcolor="white">
<div id="container">
<div id="slate" class="leftBox">

<h1 style="display:block;">Registration Form</h1>
<br>
<html:form action="/register.do" method="POST" focus="loginname">
  <html:hidden property="save" value="true"/>
		  <h2>Login Information</h2>
			
						<table>
							<tr>
					  		<td>E-mail Address</td>
					  		<td><html:text property="user.email"/></td>
							</tr>
							<tr>
								<td>First Name</td>
					  		<td><html:text property="user.firstname"/></td>
					  	</tr>
					  	<tr> 		
					 			<td>Last Name</td>
					  		<td><html:text property="user.lastname"/></td>
							<tr>
					  		<td>Preferred ID</td>
					  		<td><html:text property="user.loginname"/></td>
							</tr>
							<tr>
								<td>Password</td>
					  		<td><html:password property="user.password" redisplay="false"/></td>
							</tr>
							<tr>
								<td>Confirm Password</td>
					  		<td><html:password property="password1" redisplay="false"/></td>
					  	</tr>
					 <table>

		<br />
		<h2>Your Profile</h2>
					  
						<table>
							<tr>
					  		<td>ZIP/Postal Code</td>
					  		<td><html:text property="user.zipcode"/></td>
							</tr>

					 <table>
					 	<br>
					  Are you an elected official serving a city, town regional office in King, Pierce, or Snohomish Counties?
					
							<option value="no" property="user.isOfficial" />
							<option value="yes" property="user.isOfficial" />
					
						Do you work for a transportation agency (e.g., Washington State Department of Transportation, Puget Sound Regional Council, Sound Transit,  King County Department of Transportation)?
							<option value="no" property="user.isTransportationAgency" />
							<option value="yes" property="user.isTransportationAgency" />

						If you answered &quot;yes&quot; to the previous question, please check the box that best describes your role.
					  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
					  <tr>
						  <td width="6%" class="registrationform">
							<input name="role" type="radio" class="inputRadio" value="DecisionMaker">
						  </td>
						  <td width="94%"><p class="registrationform">I am a decision maker; I have responsibility for making and implementing decisions about transportation.</p></td>
					  </tr>
					  <tr>

						  <td class="registrationform">
							<input name="role" type="radio" class="inputRadio" value="TechSpecialist">
						  </td>
						  <td>
							<p class="registrationform"> I am a transportation technical specialist; I have specialized training in transportation planning and advise decision makers on transportation issues. </p></td>
					  </tr>
					   <tr>
						  <td class="registrationform">

							<input name="role" type="radio" class="inputRadio" value="Role_Other">
						  </td>
						  <td><p class="registrationform">Other</p></td>
					  </tr>
					  </table>
					  <p class="title_form">Terms of Agreement </p>
						  <p> Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Mauris faucibus lacinia neque. Donec diam. Curabitur a orci eu elit tincidunt consectetuer. Vestibulum mattis facilisis nunc. Etiam a augue. Aenean ligula. Suspendisse potenti. In sed velit. Proin posuere. Nunc eu pede. Integer imperdiet. Aenean dolor neque, sollicitudin sed, consectetuer non, molestie id, nisi. Nunc ultricies, diam aliquam varius porta, orci quam tristique sem, commodo sagittis nibh erat id quam. In vel velit. Donec non est. Nunc convallis odio. </p>

						  <p>Curabitur faucibus. Sed accumsan enim ac eros. Etiam ante sem, volutpat ac, interdum sed, commodo eu, neque. Phasellus tempus leo vel nisl. Vestibulum vel nulla. Phasellus ut odio. Praesent quam tortor, ultrices et, scelerisque non, nonummy sit amet, nibh. Maecenas aliquam aliquet ante. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Praesent lacinia. Suspendisse potenti.</p>
						  <p class="title_form">&nbsp;</p>
						
					
							<html:submit property="submit" value="I Agree!"/>
							<input type="reset" name="Reset" value="I Do Not Agree">
						  </p>
						
			</html:form>
					  <!--

				   		<label for="ethnicityVal">Ethnicity</label>
				   				<p></p>
								<select  class="inputBox"  name="ethnicityVal" size=1>
										  <option value=0></option>

										  <option  value="African-American">African-American</option>
										  <option  value="Asian-Pacific Islander">Asian/Pacific Islander</option>
										  <option  value="Native-American">Native American</option>
										  <option  value="Latino/Hispanic">Latino/Hispanic</option>
										  <option  value="Caucasian-White">Caucasian/White</option>
										  <option  value="Other">Other (Please Specify Below)</option>

								</select> 
								<p></p>
					  
							<label for="other_ethnicity">If other, please specify</label>
							<input  class="inputBox" align="left" maxlength="30" name="other_ethnicity" size="50" value=""> 

					
					<label for="household">How many people live in your household?</label>
					<p></p>
								 <select class="inputBox" name="household">
									<option value="household_1">1</option>

									<option value="household_2">2</option>
									<option value="household_3">3</option>
									<option value="household_4">4</option>
								    <option value="household_5">5</option>
									<option value="household_6">6 or more</option>
								</select>

					<p></p>
					<label for="income">Household Income</label>
					</span>
					<p></p>
								<select  class="inputBox"  name="income">
									<option  value="25000orless">less than $25,000</option>
									<option  value="25001_35000">$25,001 - $35,000</option>

									<option  value="35001_50000">$35,001 - $50,000</option>
									<option  value="50001_75000">$50,001 - $75,000</option>
									<option  value="75001_100000">$75,001 - $100,000</option>
									<option  value="100000Plus">more than $100,000</option>
								</select>
								
				    <p></p>
				    
				    -->
</div>
</div>
</body>
</html:html>

