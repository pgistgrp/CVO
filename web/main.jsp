<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>PGIST main page</title>
</head>

<body bgcolor="white">

<h2><html:link action="/logout.do">Logout</html:link></h2>

<pg:show users="admin">
  <p><a href="/userlist.do">User Management</a>
</pg:show>

<pg:show roles="member, moderator">
  <p><html:link action="/cvolist.do">CVO participation</html:link>
</pg:show>

<p><html:link page="/test.jsp">Test</html:link>

<p><html:link page="/concernManagement.do">Concern Management</html:link>

<p><html:link page="/search.do">Search</html:link>

</body>
</html:html>

