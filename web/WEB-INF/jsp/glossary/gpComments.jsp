<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<<<<<<< gpComments.jsp
<h2>Comments on this Term</h2>
<logic:iterate id="comment" name="comments">

	<p>${comment.owner.loginname} on ${comment.time} added the following comment:<br> ${comment.content}</p>

</logic:iterate>
