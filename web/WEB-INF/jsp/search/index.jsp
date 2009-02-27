<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>PGIST Search</title>
<event:pageunload />
</head>
<body>

<html:form action="/search.do" method="post">

<html:hidden property="workflowId" name="searchForm" value="${param['workflowId']}"/>

<table width="100%">
  <tr>
    <td><html:text property="queryStr" name="searchForm" maxlength="50" size="50"/><input type="submit" value="Search"></td>
  </tr>
</table>

</html:form>

</body>
</html:html>

