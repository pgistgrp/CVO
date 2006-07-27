<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
Uncategorized Tags:
<ul>
<logic:iterate id="tag" name="tags">
	<li style="list-style: none;" id="tag${tag.id}">[ ? ] <a href="javascript:getConcerns(${tag.id});">${tag.tag.name}</a></li>
</logic:iterate>
</ul>