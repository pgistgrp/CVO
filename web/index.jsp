<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<html:html>
<head>
<title>PGIST main page</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/position.css";</style>
<style type="text/css" media="screen">@import "styles/styles.css";</style>
<!-- End Site Wide CSS -->
<!-- Site Wide JS -->
<script src="scripts/search.js" type="text/javascript"></script>
<style type="text/css">
#login {
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
<br />
<div id="pgistlogo">
<img src="images/mainlogo.png" alt="pgistlogo" >
</div>
<center><h3>Note: User name and password are case sensitive</h3></center> 
<br />

<html:form action="/login.do" method="POST" focus="user.loginname">
  <div align="center">
    <table id="login">
      <tr>
        <td>User Name:</td>
          <td><html:text property="user.loginname"/></td>
        </tr>
      <tr>
        <td>Password:</td>
          <td><html:password property="user.password" redisplay="false"/></td>
        </tr>
      <tr>
        <td>&nbsp;</td>
	      <td align="right">(<a href="register.do">Register</a>) <html:submit property="submit" value="Login"/> </td>
        </tr>
    </table>
	 <span class="red">${userForm.reason}</span>
  </div>
</html:form>

<div align="center"><a href="/pgist-docs/index.html">Javadoc</a>
  
  
    <!--tags:discussion discussible="null" url="/test.do" count="10"/-->
  
</div>
</body>
</html:html>

