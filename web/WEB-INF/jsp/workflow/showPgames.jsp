<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<html:html>
<head>
<title>PGame List</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page='/styles/default.css'/>">
<script type='text/javascript' src='<html:rewrite page="/scripts/base.js"/>'></script>
<script type='text/javascript' src='<html:rewrite page="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<html:rewrite page="/dwr/util.js"/>'></script>
<script type='text/javascript' src='<html:rewrite page="/dwr/interface/WorkflowAgent.js"/>'></script>
<script>
</script>
</head>

<body>

<html:form action="/workflow.do" method="POST">
  <h2>PGame List</h2>
  <table id="pgameListTable" class="listtable" cellspacing="1" frame="box" rules="all" width="600">
    <tr>
      <th>PGame List</th>
      <th width="50">Participate</th>
    </tr>
    <logic:iterate id="pgame" property="runningActivities" name="workflowForm">
    <tr>
      <td><bean:write name="pgame" property="name"/></td>
      <td style="text-align:center;">
        <a href="<html:rewrite page='/workflow.do'/>?situationId=${workflowForm.situationId}&meetingId=${workflowForm.meetingId}&pmethodId=${workflowForm.pmethodId}&pgameId=${pgame.id}">Participate</a>
      </td>
    </tr>
    </logic:iterate>
  </table>
</html:form>

</body>
</html:html>

