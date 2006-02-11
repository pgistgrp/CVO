<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<html:html>
<head>
<title>Meeting List</title>
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
  <h2>Meeting List</h2>
  <table id="meetingListTable" class="listtable" cellspacing="1" frame="box" rules="all" width="600">
    <tr>
      <th>Meeting List</th>
      <th width="50">Participate</th>
    </tr>
    <logic:iterate id="meeting" property="agenda.runningActivities" name="workflowForm">
    <tr>
      <td><bean:write name="meeting" property="name"/></td>
      <td style="text-align:center;">
        <a href="<html:rewrite page='/workflow.do'/>?situationId=${workflowForm.situationId}&meetingId=${meeting.id}">Participate</a>
      </td>
    </tr>
    </logic:iterate>
  </table>
</html:form>

</body>
</html:html>

