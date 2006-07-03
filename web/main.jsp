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
  <p><html:link page="/cctlist.do">Concerns Collector Tool</html:link>
</pg:show>

<pg:show roles="moderator">
  <p><html:link page="/stopword.do">StopWords Management Tool</html:link>
</pg:show>

<pg:show roles="moderator">
  <p><html:link page="/cstlist.do">Concerns Synthesis Tool</html:link>
</pg:show>

<pg:show roles="moderator">
  <p><html:link page="/sdclist.do">Structured Discussion for Concerns</html:link>
</pg:show>

<pg:show roles="moderator">
  <p><html:link page="/glossaryManage.do?count=20">Glossary Management Tool</html:link>
</pg:show>

<br>
<br>
<br>

<p><html:link page="/test.jsp">Test</html:link>

<p><html:link page="/concernManagement.do">Concern Management</html:link>

<p><html:link page="/search.do">Search</html:link>

<p><html:link page="/situationList.do">Situation List</html:link>

</body>
</html:html>

