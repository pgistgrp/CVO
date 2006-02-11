<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<pg:show roles="moderator">
  <table id="templateListTable" class="listtable" cellspacing="1" frame="box" rules="all" width="100%">
    <input type="hidden" id="selectedTemplate" value="">
    <logic:iterate id="template" property="templateList" name="workflowForm">
    <tr>
      <td><bean:write name="template" property="name"/></td>
      <td><input type="radio" onclick="if (this.checked) $('selectedTemplate').value='${template.id}';"></td>
    </tr>
    </logic:iterate>
  </table>
</pg:show>

