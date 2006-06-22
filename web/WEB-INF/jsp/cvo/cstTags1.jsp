<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>


<span class="title_section">Tags not in:</span>
<ul>
	<logic:iterate id="tag" name="tags">
		<li id="tag${tag.id}" style="list-style: none;">[<a href="javascript:relateTag(${tag.id});"><strong>&larr;</strong></a>]&nbsp;<a href="javascript:getConcerns(${tag.id});">${tag.tag.name}</a></li>
	</logic:iterate>
</ul>
