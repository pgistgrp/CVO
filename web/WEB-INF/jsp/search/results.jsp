<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>PGIST main page</title>
</head>

<body bgcolor="white">

<html:form action="/search.do" method="post">

<table width="100%">
  <tr>
    <td><html:text property="queryStr" name="searchForm" maxlength="50" size="50"/><input type="submit" value="Search"></td>
  </tr>
</table>

<table width="100%">
  <tr>
    <td>Found: ${searchForm.total} results</td>
  </tr>
  <logic:iterate name="searchForm" property="results" id="result">
  <tr>
    <td>${result}</td>
  </tr>
  </logic:iterate>
</table>

</html:form>

</body>
</html:html>

