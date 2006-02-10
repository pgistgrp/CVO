<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>Situation List</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page='/styles/default.css'/>">
<script type='text/javascript' src='<html:rewrite page="/scripts/base.js"/>'></script>
<script type='text/javascript' src='<html:rewrite page="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<html:rewrite page="/dwr/util.js"/>'></script>
<script type='text/javascript' src='<html:rewrite page="/dwr/interface/WorkflowAgent.js"/>'></script>
<script>
</script>
</head>

<body>

<html:form action="/situationList.do" method="POST">
  <h2>Situation List</h2>
  <pg:show roles="moderator">
    <pg:hide roles="admin">
    <p><a href="#" onclick="openCreateSituation();">Create Situation</a>
    </pg:hide>
  </pg:show>
  <table id="situationListTable" class="listtable" cellspacing="1" frame="box" rules="all" width="100%">
    <logic:iterate id="situation" property="situationList" name="workflowForm">
    <tr>
      <td><html:link action="/view.do" paramId="id" paramName="situation" paramProperty="id"><bean:write name="situation" property="name"/></html:link></td>
    </tr>
    </logic:iterate>
  </table>
</html:form>

</body>
</html:html>

