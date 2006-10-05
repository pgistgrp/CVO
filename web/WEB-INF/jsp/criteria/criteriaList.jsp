<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>Criteria List</title>
<link rel="stylesheet" type="text/css" href="/styles/default.css'/>">
<script>
</script>
</head>

<body>
  <h2>Criteria List</h2>
  <html:link page="/criteriaMgr.do?action=manage">Manage Criteria</html:link>
   
  <table id="sdListTable" class="listtable" cellspacing="1" frame="box" rules="all" width="100%">
    <logic:iterate id="cct" property="${ccts}">
    <tr>
      <td>
        <html:link action="/criteriaMgr.do?action=assoc" paramId="cctId" paramName="cct" paramProperty="id">
          <bean:write name="cct" property="name"/>
        </html:link>
      </td>
    </tr>
    </logic:iterate>
  </table>

</body>
</html:html>

