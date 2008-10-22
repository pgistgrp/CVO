<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<h4>Unrelated Keywords/phrases</h4>
<ul>
	<logic:iterate id="tag" name="tags">
		<li id="tag${tag.id}" style="list-style: none;">[<a href="javascript:relateTag(${tag.id});"><strong>&larr;</strong></a>]&nbsp;<a href="javascript:getConcerns(${tag.id});">${tag.tag.name}</a></li>
	</logic:iterate>
</ul>
<h4>Orphan Keywords/phrases</h4>
<ul>
	<logic:iterate id="tag" name="orphanTags">
		<li id="tag${tag.id}" style="list-style: none;">[<a href="javascript:relateTag(${tag.id});"><strong>&larr;</strong></a>]&nbsp;[ ? ]&nbsp;<a href="javascript:getConcerns(${tag.id});">${tag.tag.name}</a></li>
	</logic:iterate>
</ul>