<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<table id="termListTable" class="listtable" cellspacing="1" frame="box" rules="all" width="100%">
  <logic:iterate id="term" name="terms">
    <tr>
      <td>${term.name}</td>
    </tr>
  </logic:iterate>
</table>

