<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<p align="right"><a href="javascript:Effect.BlindUp('tagCloud'); void(0);"><img src="/images/closelabel.gif" border="0"></a></p>
<h3>Browse All Discussion Tags</h3>
<p>"Tags" are an easy way to explore the wide variety of discussion posts contributed by other participants. Simply click on a tag to read concerns related to that idea. Click on a tag below to browse concerns.</p>

<!-- iterate through concern tags here -->	
<ul class="tagsInline">
	<li class="tagsInline"><strong>Tags:</strong> </li>
	<c:forEach items="${tags}" var="tagRef">
		<li class="box1 tagsInline"><a href="javascript:changeCurrentFilter(${tagRef.id}); setTimeout('Effect.BlindUp(\'tagCloud\')',1000); void(0);">${tagRef.tag.name}</a></li>
	</c:forEach>
</ul>
<div style="clear: left;"></div>

<!-- end tag iteration -->
<div class="pagination">
				You are currently viewing page: ${setting.page} of ${setting.pageSize} &nbsp;
			<logic:equal name="setting" property="page" value="1">
				<img src="images/btn_prev_fade.gif" alt="No Previous Pages" />
			</logic:equal>
			<logic:notEqual name="setting" property="page" value="1">	
				<a href="javascript:goToPage(${setting.page}-1, 'tagCloud');"><img src="images/btn_prev_a.gif" alt="Prev" name="prev" class="button" id="prev" onMouseOver="MM_swapImage('prev','','images/btn_prev_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
			</logic:notEqual>
			
			
			<logic:equal name="setting" property="page" value="${setting.pageSize}">
				<img src="images/btn_next_fade.gif" alt="No Additional Pages" />
			</logic:equal>
			<logic:notEqual name="setting" property="page" value="${setting.pageSize}">	
				<a href="javascript:goToPage(${setting.page}+1, 'tagCloud')"><img src="images/btn_next_a.gif" alt="Next" name="next" class="button" id="next" onMouseOver="MM_swapImage('next','','images/btn_next_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
			</logic:notEqual>
			

</div>