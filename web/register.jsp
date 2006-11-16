<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>Register</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/position.css";</style>
<style type="text/css" media="screen">@import "styles/styles.css";</style>
<!-- End Site Wide CSS -->
<style type="text/css">
#register {
	margin: auto;
	margin-right: auto;
	margin-left: auto;
	width: 320px;
	border: 10px solid #CCCCCC;
	padding: 15px;
}
#pgistlogo {
	margin: auto;
	margin-right: auto;
	margin-left: auto;
	text-align: center;
}
.red {color: #FF0000}
</style>
</head>

<body>
<br /><br />
<div id="pgistlogo">
<img src="images/mainlogo.png" alt="pgistlogo" >
</div>
<html:form action="/register.do" method="POST">
<html:hidden property="save" value="true"/>
<html:hidden property="user.homeAddr" value="12345 Home Address"/>
<html:hidden property="user.city" value="default city"/>
<html:hidden property="user.state" value="wa default"/>
<html:hidden property="user.zipcode" value="55555"/>
<html:hidden property="user.ethnicity" value="Purple Default"/>	
<html:hidden property="user.isOfficial" value="yes"/>	
<html:hidden property="user.isTransportationAgency" value="yes"/>	
<input name="role" type="hidden" class="inputRadio" value="DecisionMaker">
<div align="center">
<h3>Registration Form</h3><br />
<table id="register">
	<tr>
		<td>First Name</td>
		<td><html:text property="user.firstname"/></td>
	</tr>
	<tr> 		
		<td>Last Name</td>
		<td><html:text property="user.lastname"/></td>
	</tr>
	<tr>
		<td>E-mail Address</td>
		<td><html:text property="user.email"/></td>
	</tr>
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
	<!--
	<tr>
 		<td width="200" colspan="2" class="smallText"><html:checkbox property="user.enabled" value="${user.enabled}"/>I would like to receive e-mail notifications when other participants reply to my discussion posts.</td>
	</tr>
	-->
	<tr>
		<td></td>
		<td align="right"><html:submit property="submit" value="Submit"/><input type="reset" name="Reset" value="Cancel"></td>
	</tr>
 <table>
 <span class="red">${userForm.reason}</span>
</div>	 


</html:form>

</body>
</html:html>

