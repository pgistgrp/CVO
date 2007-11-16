<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<span class="title_section">Tags within: </span>
<ul>
<logic:iterate id="tag" name="tags">
	<li id="tag${tag.id}"><a href="javascript:getConcerns(${tag.id});">${tag.tag.name}</a>&nbsp;[ <a href="javascript:derelateTag(${catRef.id}, ${tag.id});">derelate</a> ]</li>
</logic:iterate>
</ul>