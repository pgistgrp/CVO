<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<p align="right"><a href="javascript:Effect.BlindUp('tagCloud'); void(0);"><img src="/images/close1.gif" class="marginRight" border="0"></a></p>
<h3>Browse All Discussion Tags</h3>
<p>"Tags" are an easy way to explore the wide variety of discussion posts contributed by other participants. Simply click on a tag to read concerns related to that idea. Click on a tag below to browse concerns.</p>

<!-- iterate through concern tags here -->	
<ul class="tagsInline">
	<li class="tagsInline"><strong>Keywords:</strong> </li>
	<c:forEach items="${tags}" var="tag">
		<li class="box1 tagsInline"><a class="tagSize${tag.fontSize}" href="javascript:io.changeCurrentFilter(${tag.id}); setTimeout('Effect.BlindUp(\'tagCloud\')',1000); void(0);">${tag.name}</a></li>
	</c:forEach>
</ul>
<div style="clear: left;"></div>
<!-- end tag iteration -->
<div class="pagination" class="marginRight">
    You are currently viewing page: ${setting.page} of ${setting.pageSize} &nbsp;
    <logic:equal name="setting" property="page" value="1">
    	<input type="button" class="prevBtn" disabled="true" value="Prev" title="No previosu pages"/>
    </logic:equal>
    <logic:notEqual name="setting" property="page" value="1">	
    	<input type="button" class="prevBtn" 
    		onClick="javascript:io.goToPage(${setting.page}-1, 'tagCloud');" value="Prev" />
    </logic:notEqual>


    <logic:equal name="setting" property="page" value="${setting.pageSize}">
    	<input type="button" class="nextBtn" value="Next" disabled="true" title="No additional pages" />
    </logic:equal>
    <logic:notEqual name="setting" property="page" value="${setting.pageSize}">	
    	<input type="button" class="nextBtn" value="Next" 
    		onclick="javascript:io.goToPage(${setting.page}+1, 'tagCloud')"/>
    </logic:notEqual>
</div>