<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<logic:iterate id="post" property="discourseObject.root.children" name="brainStorming">
  <pg:hide owner="${post.owner.loginname}">
    <pg:discourse id="post" post="post" depth="-1" commentCallback="postReply"/><div style="height:10px;width:100%"></div>
  </pg:hide>
</logic:iterate>

