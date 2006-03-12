<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<html:html>
<head>
<title>Running Activities</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page='/styles/default.css'/>">
</head>

<body>

<h1>Running activities of decision situation: ${workflowForm.situation.title}</h1>

<table id="listTable" class="listtable" cellspacing="1" frame="box" rules="all" width="800">
<logic:iterate id="meeting" property="meetings" name="workflowForm">
  <tr>
    <td colspan="2"><bean:write name="meeting" property="name"/></td>
  </tr>
  <logic:iterate id="pmethod" property="pmethods" name="meeting">
    <tr>
      <td colspan="2" style="padding-left:20px;"><bean:write name="pmethod" property="name"/></td>
    </tr>
    <logic:iterate id="pgame" property="pgames" name="pmethod">
    <tr>
      <td style="padding-left:40px;">
        <a href="participate.do?situationId=${workflowForm.situation.id}&meetingId=${meeting.id}&pmethodId=${pmethod.id}&pgameId=${pgame.id}"><bean:write name="pgame" property="name"/></a>
      </td>
      <td style="text-align:center;" width="40">
        <a href="complete.do?situationId=${workflowForm.situation.id}&meetingId=${meeting.id}&pmethodId=${pmethod.id}&pgameId=${pgame.id}">Finish</a>
      </td>
    </tr>
    </logic:iterate>
  </logic:iterate>
</logic:iterate>
</table>

</body>
</html:html>

