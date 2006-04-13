<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<logic:iterate id="concern" name="concerns">
	<span class="participantName"><bean:write name="concern" property="author.firstname" />&nbsp;said:</span><br>
	"<bean:write name="concern" property="content" />"<br>
	<span class="tags"><strong>Tags</strong>:
	<logic:iterate id="tagref" property="tags" name="concern">
		<a href="null"><bean:write name="tagref" property="tag.name" /></a>&nbsp;|&nbsp;
	</logic:iterate></span></p>
	<logic:equal name="type" value="0">
	<p class=actionMenu><a href="edit">edit concern</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="edit">edit tags</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="edit">delete concern</a>&nbsp;&nbsp;|&nbsp;&nbsp;</p>
	</logic:equal>
	<p></p>
</logic:iterate>

