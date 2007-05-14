<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>Structured Discussion List</title>
<link rel="stylesheet" type="text/css" href="/styles/default.css'/>">
<script>
</script>
<event:pageunload />
</head>

<body>
  <h2>Structured Discussion List</h2>
  <table id="sdListTable" class="listtable" cellspacing="1" frame="box" rules="all" width="100%">
    <logic:iterate id="structure" name="structures">
    <tr>
      <td>
        <html:link action="/sd.do" paramId="isid" paramName="structure" paramProperty="id">
          <bean:write name="structure" property="type"/>
        </html:link>
      </td>
    </tr>
    </logic:iterate>
  </table>

</body>
</html:html>

