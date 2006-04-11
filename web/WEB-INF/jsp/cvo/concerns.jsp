<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<span class="title_section">Other Participant's Concerns</span>
<p>To help you create your concerns, below are examples of other participant concerns in random order. To sort, use the drop down box above.</p>
<logic:iterate id="concern" name="concerns">
	<p><span class="participantName"><bean:write name="concern" property="author.firstname" />&nbsp;said:</span><br>

	"<bean:write name="concern" property="content" />"<br>
	<span class="tags"><strong>Tags</strong>:
	<logic:iterate id="tagref" property="tags" name="concern">
		<a href="null"><bean:write name="tagref" property="tag.name" /></a>&nbsp;|&nbsp;
	</logic:iterate></span></p>

</p>
</logic:iterate>
