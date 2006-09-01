<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>Register</title>

</head>

<body>

<h1>Registration Form</h1>
<html:form action="/register.do" method="POST" focus="loginname">
<html:hidden property="save" value="true"/>
<html:hidden property="user.homeAddr" value="12345 Home Address"/>
<html:hidden property="user.city" value="default city"/>
<html:hidden property="user.state" value="wa default"/>
<html:hidden property="user.zipcode" value="55555"/>
<html:hidden property="user.ethnicity" value="Purple Default"/>	
<html:hidden property="user.isOfficial" value="no"/>	
<html:hidden property="user.isTransportationAgency" value="no"/>	
<input name="role" type="radio" class="inputRadio" value="DecisionMaker" checked> Make this hidden
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
	<tr>
		<td></td>
		<td><html:submit property="submit" value="Submit"/><input type="reset" name="Reset" value="Cancel"></td>
	</tr>
 <table>
		 


</html:form>
</body>
</html:html>

