<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>PGIST main page</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/position.css";</style>
<style type="text/css" media="screen">@import "styles/styles.css";</style>
<!-- End Site Wide CSS -->
</head>

<body>
<!-- Header -->

<jsp:include page="/header.jsp" />

<!-- End Header -->

<h3>Public View</h3>
<ul>
<li><pg:show roles="member, moderator"><html:link page="/cctlist.do">Concerns Collector Tool</html:link></pg:show></li>
<li><html:link page="/glossaryPublic.do">Glossary Public View</html:link></li>
<li><pg:show roles="member, moderator"><html:link page="/sdlist.do">Structured Discussion List</html:link></pg:show></li>
</ul>

<h3>Moderator Tools</h3>
<ul>
<li><pg:show users="admin"><a href="/userlist.do">User Management</a></pg:show></li>
<li><pg:show roles="moderator"><html:link page="/stopword.do">StopWords Management Tool</html:link></pg:show></li>
<li><pg:show roles="moderator"><html:link page="/cstlist.do">Concerns Synthesis Tool</html:link></pg:show></li>
<li><pg:show roles="moderator"><html:link page="/glossaryManage.do">Glossary Management Tool</html:link></pg:show></li>
</ul>

<h3>Developement Tools</h3>
<ul>
<li><html:link page="/test.jsp">Test</html:link></li>
<li><html:link page="/concernManagement.do">Concern Management</html:link></li>
<li><html:link page="/search.do">Search</html:link></li>
<li><html:link page="/situationList.do">Situation List</html:link></li>
</ul>

<!-- Sorry Zhong
<pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">
  This is a test for glossary terms: Advocacy organization, Air quality, Short Range Transit Plan,
  Transportation Improvement Program, Auto-restricted zone. All these terms should be linked.
</pg:termHighlight>
-->
</div>
</body>
</html:html>

