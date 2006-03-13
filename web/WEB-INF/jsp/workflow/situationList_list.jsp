<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
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

