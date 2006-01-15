<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<logic:iterate id="post" property="root.children" name="cvoForm">
  <pg:hide owner="${post.owner.loginname}">
    <pg:discourse id="post" post="post" callback="postReply"/><div style="height:5px;"></div>
  </pg:hide>
</logic:iterate>

