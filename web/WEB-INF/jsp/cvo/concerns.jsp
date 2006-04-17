<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<logic:iterate id="concern" name="concerns">
	
	<logic:equal name="type" value="0">
		<span class="participantName">You wrote:</span>
	</logic:equal>
	<logic:notEqual name="type" value="0">
		<span class="participantName"><bean:write name="concern" property="author.firstname" />&nbsp;said:</span>
	</logic:notEqual>
	<br>
	<span class="concerns">"<bean:write name="concern" property="content" />"</span><br>
	<span class="tags"><strong>Tags</strong>:
	<logic:iterate id="tagref" property="tags" name="concern">
		<a href="javascript:getConcernsByTag(${tagref.id});">${tagref.tag.name}</a>&nbsp;|&nbsp;
	</logic:iterate></span>
	<logic:equal name="type" value="0">
	<p class=actionMenu>Actions: <a href="edit">edit concern</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="edit">edit tags</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="edit">delete concern</a></p>
	</logic:equal>
	<p></p>
</logic:iterate>

