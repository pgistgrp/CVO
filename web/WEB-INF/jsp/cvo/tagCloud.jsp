<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<div id="closeTagSelector" class="closeBox"><a href="javascript: shrinkTagSelector();"><img src="/images/closelabel.gif" border="0"></a></div>
<h3>Browse All Tags</h3>
<p>"Tags" are an easy way to explore the wide variety of concerns contributed by other participants. Simply click on a tag to read concerns related to that idea. Click on a tag below to browse concerns.</p>

<div id="searchTag_container">
		<div id="searchTag_form">
				<form name="searchTags" method="post" onSubmit="tagSearch($('txtSearch').value); return false;">
					<input type="text" id="txtSearch" name="txtSearch"  class="search" value="Search for Tags" onfocus="this.value = ( this.value == this.defaultValue ) ? '' : this.value;return true;" onkeyup="tagSearch($('txtSearch').value);"><div id="tagIndicator" style="visibility:hidden;"><img src="/images/indicator.gif"></div>
				</form>		
		
		</div>
</div>
<div id="tagSearchResults"></div>
<div id="topTags">
	<logic:iterate id="tagRef" name="tags">
	  	<span class="tagSize${tagRef.fontSize}"><a href="javascript:getConcernsByTag(${tagRef.id});">${tagRef.tag.name}</a></span>&nbsp;
	</logic:iterate>
</div>

