<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
	<span class="title_section">Tags not in:</span>
<ul>
	<logic:iterate id="tag" name="tags">
		<li id="tag${tag.id}" class="dragtags"><a href="javascript:getConcerns(${tag.id});">${tag.tag.name}</a>&nbsp;[ <a href="javascript:deleteTag(${tag.id});">x</a> ]</li>
	</logic:iterate>
</ul>
    <script type="text/javascript" language="javascript" charset="utf-8">
    // <![CDATA[
      new Draggable('tag105',{revert:true});
      new Draggable('tag344',{revert:true});
    </script>