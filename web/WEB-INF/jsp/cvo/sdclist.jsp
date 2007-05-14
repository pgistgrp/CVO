<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>SDC List</title>
<link rel="stylesheet" type="text/css" href="/styles/default.css">
<script type='text/javascript' src='/scripts/base.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>
<event:pageunload />
</head>

<body>

<pg:show roles="moderator">
  <html:form action="/sdclist.do" method="POST">
    <h2>SDC List</h2>
    <table id="sdcListTable" class="listtable" cellspacing="1" frame="box" rules="all" width="100%">
      <logic:iterate id="cct" property="ccts" name="cctForm">
      <tr>
        <td><html:link action="/sdcview.do" paramId="cctId" paramName="cct" paramProperty="id"><bean:write name="cct" property="name"/></html:link></td>
      </tr>
      </logic:iterate>
    </table>
  </html:form>
</pg:show>

</body>
</html:html>

