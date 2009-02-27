<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<ul>
<logic:iterate id="tag" name="tags">
	<li id="tag${tag.id}" style="list-style: none;"><a href="javascript:getConcerns(${tag.id});">${tag.tag.name}</a>&nbsp;[<a href="javascript:derelateTag(${catRef.id}, ${tag.id});">&rarr;</a>]</li>
</logic:iterate>
</ul>