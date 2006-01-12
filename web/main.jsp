<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>PGIST main page</title>
</head>

<body bgcolor="white">

<h2><html:link action="/logout.do">Logout</html:link></h2>

<pg:show users="admin">
  <p><a href="/userlist.do">User Management</a>
</pg:show>

<pg:show roles="member">
  <p><html:link action="/cvolist.do">CVO participation</html:link>
</pg:show>

</body>
</html>

