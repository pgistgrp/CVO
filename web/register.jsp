<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>Register</title>
</head>

<body bgcolor="white">

<html:form action="/register.do" method="POST" focus="loginname">
  <html:hidden property="save" value="true"/>
  <h2>Register
  <table>
    <tr>
      <td>User Name:</td>
      <td><html:text property="user.loginname"/></td>
    </tr>
    <tr>
      <td>Password:</td>
      <td><html:password property="user.password" redisplay="false"/></td>
    </tr>
    <tr>
      <td>Password Confirm:</td>
      <td><html:password property="password1" redisplay="false"/></td>
    </tr>
    <tr>
      <td>First Name:</td>
      <td><html:text property="user.firstname"/></td>
    </tr>
    <tr>
      <td>Last Name:</td>
      <td><html:text property="user.lastname"/></td>
    </tr>
    <tr>
      <td>Email Address:</td>
      <td><html:text property="user.email"/></td>
    </tr>
    <tr>
      <td colspan="2"><html:submit property="submit" value="Submit"/></td>
    </tr>
  </table>
</html:form>

</body>
</html:html>

