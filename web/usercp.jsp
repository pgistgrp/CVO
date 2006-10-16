<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>PGIST main page</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/position.css";</style>
<style type="text/css" media="screen">
@import "styles/styles.css";
</style>
<!-- End Site Wide CSS -->
<!-- Site Wide JS -->
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<style type="text/css">
.red {color: #FF0000}
.style6 {font-size: 10px}
.style7 {font-size: 16px}
</style>

</head>

<body>
<!-- Header -->
<jsp:include page="/header.jsp" />
<!-- End Header -->

<h3>User Control Panel: ${user.loginname}</h3>
<br>
<html:form action="/usercp.do" method="POST">
 <span class="red">${userForm.reason}</span>
 <html:hidden property="save" value="true"/>
<div style="width: 500px; text-align: center; border: 2px solid #CCCCCC; padding: 10px;">
  <span class="style7"><strong>Enter Current Password to continue:</strong> 
  <html:password property="currentpassword" redisplay="false"/>
  </span>
  <div style="width: 400px; border: 1px solid #CCCCCC; margin: 10px; margin-left:auto; margin-right: auto; padding: 19px;">
<table>
	<tr>
		<td colspan="2"><strong>Change Email Address</strong></td>
	</tr>
	<tr>
 		<td width="200">E-mail Address: </td>
		<td width="160"><div align="right">
		  <html:text property="user.email" value="${user.email}"/>
		  </div></td>
	</tr>
	<tr>
	  <td colspan="2">&nbsp;</td>
	  </tr>
	<tr>
		<td colspan="2"><strong>Change Password </strong><span class="style6">(Password won't change if left blank.)</span></td>
	</tr>
	<tr>
		<td>New Password</td>
		<td><div align="right">
		  <html:password property="user.password" redisplay="false"/>
		  </div></td>
	</tr>
	<tr>
		<td>Confirm New Password</td>
		<td><div align="right">
		  <html:password property="password1" redisplay="false"/>
		  </div></td>
	</tr>
</table>
</div>
<html:submit property="submit" value="Submit"/>
</div>
<p>&nbsp;</p>
</html:form>

</body>
</html:html>

