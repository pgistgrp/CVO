<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<logic:iterate id="post" property="root.children" name="cvoForm">
  <pg:hide owner="${post.owner.loginname}">
    <pg:discourse id="post" post="post" depth="0" callback="postReply"/><div style="height:10px;width:100%"></div>
  </pg:hide>
</logic:iterate>

