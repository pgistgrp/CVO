<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>

<ul>
<logic:iterate id="tag" name="tags">
	<li id="tag${tag.id}" style="list-style: none;">${tag.tag.name}</li>
</logic:iterate>
</ul>