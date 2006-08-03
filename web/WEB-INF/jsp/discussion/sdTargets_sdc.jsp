<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>


<logic:iterate id="infoObject" property="infoObjects" name="structure">
		<p>${infoObject.id}</p>
</logic:iterate>

<!--${object.object.theme.content}-->