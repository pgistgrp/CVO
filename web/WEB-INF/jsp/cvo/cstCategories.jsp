<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
 <ol id="root">
	<logic:iterate id="category" name="categories">
		<li id="cat${category.id}"><a id="theme${category.id}" href="#default.impl" class="folder"  onclick="toggle(this)"></a><a href="javascript:showTags(${category.id}, 0);">${category.category.name}</a>&nbsp;[ <a href="javascript:editCatsPopup(${category.id}, '${category.category.name}');">edit</a> ]&nbsp;[ <a href="javascript:deleteCategory(${category.id});">x</a> ]
				<ol>
					<logic:iterate id="category" name="categories">
							<li id="cat${category.id}"><a href="javascript:showTags(${category.id}, 0);" class="doc"></a><a href="javascript:showTags(${category.id}, 0);">${category.category.name}</a>&nbsp;[ <a href="javascript:editCatsPopup(${category.id}, '${category.category.name}');">edit</a> ]&nbsp;[ <a href="javascript:deleteCategory(${category.id});">x</a> ]</li>
					</logic:iterate>
				</ol>
		</li>
	</logic:iterate>
</ol>

<!--<li id="cat${category.id}"><a href="javascript:showTags(${category.id}, 0);">${category.category.name}</a>&nbsp;[ <a href="javascript:editCatsPopup(${category.id}, '${category.category.name}');">edit</a> ]&nbsp;[ <a href="javascript:deleteCategory(${category.id});">x</a> ]</li>-->