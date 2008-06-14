<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<logic:iterate id="theme" name="themes">
<div id="anotherPanel${theme.id}">
	<div id="overviewheader${theme.id}"  class="accordionTabTitleBar ">Theme: ${fn:escapeXml(theme.title)}</div>
	<div id="overviewtext${theme.id}"><div id="editSummary${theme.id}" style="width: 100%; height: 100%;">
		<logic:equal name="theme" property="summary" value="">
		Summary for "${theme.title}" has not been created yet.  Click to edit summary.
		</logic:equal>
		<logic:notEqual name="theme" property="summary" value="">
		${fn:escapeXml(theme.summary)}
		</logic:notEqual>
		</div>
	</div>					
</div>	


</logic:iterate>
