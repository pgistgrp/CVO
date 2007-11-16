<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<logic:iterate id="post" property="root.children" name="cvoForm">
  <pg:discourse id="post" post="post"/><br>
</logic:iterate>

