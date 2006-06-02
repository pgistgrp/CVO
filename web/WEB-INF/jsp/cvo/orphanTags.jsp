<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
sdfasdf
<ul>
<logic:iterate id="category" name="categories">
	<li id="cat${category.id}">${category.category.name}&nbsp;[ <a href="javascript:deleteCategory(${category.id});">x</a> ]</li>
</logic:iterate>
</ul>