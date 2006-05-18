<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>

<ul class="tagsList" id="excludeList">

<logic:iterate id="tag" name="tags" indexId="index">
	  	
	<pg:modulo parameter="index" folding="5" value="0"><li class="col0"><span class="includeExclude"><bean:write name="tag" property="name"/>&nbsp;<a href="javascript:deleteTag('${tag.name}');"><img src="/images/trash.gif" alt="Delete this Tag!" border="0"></a></span></li></pg:modulo>
	
	<pg:modulo parameter="index" folding="5" value="1"><li class="col1"><span class="includeExclude"><bean:write name="tag" property="name"/>&nbsp;<a href="javascript:deleteTag('${tag.name}');"><img src="/images/trash.gif" alt="Delete this Tag!" border="0"></a></span></li></pg:modulo>
	
	<pg:modulo parameter="index" folding="5" value="2"><li class="col2"><span class="includeExclude"><bean:write name="tag" property="name"/>&nbsp;<a href="javascript:deleteTag('${tag.name}');"><img src="/images/trash.gif" alt="Delete this Tag!" border="0"></a></span></li></pg:modulo>
	
	<pg:modulo parameter="index" folding="5" value="4"><li class="col3"><span class="includeExclude"><bean:write name="tag" property="name"/>&nbsp;<a href="javascript:deleteTag('${tag.name}');"><img src="/images/trash.gif" alt="Delete this Tag!" border="0"></a></span></li></pg:modulo>
	
	<pg:modulo parameter="index" folding="5" value="5"><li class="col4"><span class="includeExclude"><bean:write name="tag" property="name"/>&nbsp;<a href="javascript:deleteTag('${tag.name}');"><img src="/images/trash.gif" alt="Delete this Tag!" border="0"></a></span></li></pg:modulo>

</logic:iterate>
</ul>
			<div id="prevNext_container" style="clear:both; padding-top:10px;">
					
					<div id="previous"><span class="prevNext">
						<logic:equal name="setting" property="page" value="1">
							<a href="javascript:goToTagPage(${setting.pageSize});">
						</logic:equal>
						
						<logic:notEqual name="setting" property="page" value="1">	
							<a href="javascript:goToTagPage(${setting.page}-1);">
						</logic:notEqual>
						&#171; prev page</a></span>
					</div>

					<div id="next"><span class="prevNext">
						<logic:equal name="setting" property="page" value="${setting.pageSize}">
							<a href="javascript:goToTagPage(1);">
						</logic:equal>
						
						<logic:notEqual name="setting" property="page" value="${setting.pageSize}">	
							<a href="javascript:goToTagPage(${setting.page}+1);">
						</logic:notEqual>
						next page &#187; </a></span>
					</div>
				</div>
				</div>