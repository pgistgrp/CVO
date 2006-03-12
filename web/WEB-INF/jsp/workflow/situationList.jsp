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
  function openCreateSituation() {
    WorkflowAgent.getTemplates(function(data) {
      $('templateList').innerHTML = data;
      createSituationDialog.popup();
    });
  }
  function createSituation() {
    if ($('selectedTemplate').value=='') return;
    WorkflowAgent.createSituation($('selectedTemplate').value, $('createSituationDialog_title').value,
      function(data) {
        createSituationDialog.close();
      }
    );
  }
</script>
</head>

<body>

<pg:dialog id="createSituationDialog" width="300" height="200">
  <table width="100%" height="100%" cellpadding="0" cellspacing="0">
    <tr>
      <td>Please create a decision situation from following template:</td>
    </tr>
    <tr>
      <td>Title:</td>
    </tr>
    <tr>
      <td><input type="text" id="createSituationDialog_title" value="" style="width:100%;"></td>
    </tr>
    <tr>
      <td id="templateList"></td>
    </tr>
    <tr>
      <td align="center"><input type="button" value="Submit" onclick="createSituation();"/></td>
    </tr>
  </table>
</pg:dialog>

<html:form action="/situationList.do" method="POST">
  <h2>Decision Situation List</h2>
  <pg:show roles="moderator">
    <pg:hide roles="admin">
    <p><a href="#" onclick="openCreateSituation();">Create Situation</a>
    </pg:hide>
  </pg:show>
  <table id="situationListTable" class="listtable" cellspacing="1" frame="box" rules="all" width="600">
    <tr>
      <th>Decision Situation</th>
      <th>Status</th>
      <th width="50">Participate</th>
    </tr>
    <logic:iterate id="situation" property="situationList" name="workflowForm">
    <tr>
      <td><bean:write name="situation" property="title"/></td>
      <td style="text-align:center;">Running</td>
      <td style="text-align:center;"><html:link action="/agendaDisplay.do" paramId="situationId" paramName="situation" paramProperty="id">Participate</html:link></td>
    </tr>
    </logic:iterate>
  </table>
</html:form>

</body>
</html:html>

