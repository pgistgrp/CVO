<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>PGIST main page</title>
</head>

<body bgcolor="white">

<html:form action="/login.do" method="POST" focus="user.loginname">
  <h2>Welcome to PGIST.
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
      <td colspan="2"><html:submit property="submit" value="Login"/> (<a href="register.do">Register</a>)</td>
    </tr>
  </table>
</html:form>

</body>
</html:html>

