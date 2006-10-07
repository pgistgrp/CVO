<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="closeTagSelector" class="textright"><a href="javascript: shrinkTagSelector();"><img src="/images/closelabel.gif" border="0"></a></div>
<h3>Browse All Discussion Tags</h3>
<p>"Tags" are an easy way to explore the wide variety of concerns contributed by other participants. Simply click on a tag to read concerns related to that idea. Click on a tag below to browse concerns.</p>

<div id="searchTag_container">
		<div id="searchTag_form">
				<form name="searchTags" method="post" onSubmit="sideBar.tagSearch($('txtSearch').value, event); return false;">
					<input type="text" id="txtSearch" name="txtSearch"  class="search" value="Search for Tags" onfocus="this.value = ( this.value == this.defaultValue ) ? '' : this.value;return true;" onkeyup="sideBar.tagSearch($('txtSearch').value, event);"><div id="tagIndicator" style="visibility:hidden;"><img src="/images/indicator.gif"></div>
				</form>		
		
		</div>
</div>
<div id="tagSearchResults"></div>
<div id="topTags">
	<c:forEach items="${tags}" var="tag">
	  	<span class="tagSize${tag.times}"><a href="javascript:sideBar.addFilter(${tag.id});">${tag.name}</a></span>&nbsp;
	</c:forEach>
</div>

<p>
  <div id="prevNext_container">
			
			<div id="next"><span class="textright">
				<logic:equal name="setting" property="page" value="${setting.pageSize}">
					<img src="images/btn_next_fade.gif" alt="No Additional Pages" />
				</logic:equal>
				
				<logic:notEqual name="setting" property="page" value="${setting.pageSize}">	
					<a href="javascript:sideBar.getTagCloud(${setting.page}+1);"><img src="images/btn_next_a.gif" alt="Next" name="next" class="button" id="next" onMouseOver="MM_swapImage('next','','images/btn_next_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
				</logic:notEqual>
				</span>
			</div>
			
			
			<div id="previous">
				<logic:equal name="setting" property="page" value="1">
					<img src="images/btn_prev_fade.gif" alt="No Previous Pages" />
				</logic:equal>
				
				<logic:notEqual name="setting" property="page" value="1">	
					<a href="javascript:sideBar.getTagCloud(${setting.page}-1);"><img src="images/btn_prev_a.gif" alt="Prev" name="prev" class="button" id="prev" onMouseOver="MM_swapImage('prev','','images/btn_prev_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
				</logic:notEqual>
				
			</div>

  </div>
</p>