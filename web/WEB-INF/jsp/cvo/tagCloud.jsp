<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<h2>Tags</h2><!--Assigns Tab Name -->
<h4>Read other people's concerns</h4><br>
Click on a tag below to browse concerns.<p></p>

<div id="searchTag_container">
		<div id="searchTag_title">
				Top 25 Tags
		</div>
		<div id="searchTag_form">
				<form name="searchTags" method="post" onSubmit="tagSearch($('txtSearch').value); return false;">
					<input type="text" id="txtSearch" name="txtSearch" style="width:100px; padding-left: 1px; padding-right:20px; background: url('/images/search_light.gif') no-repeat right;"  class="searchTagTextbox" value="Search for Tags" onfocus="this.value = ( this.value == this.defaultValue ) ? '' : this.value;return true;" onkeyup="tagSearch($('txtSearch').value);"><div id="tagIndicator" style="visibility:hidden;"><img src="/images/indicator.gif"></div>
				</form>			
		</div>
</div>
<div id="tagSearchResults"></div><br>
<div id="topTags">
	<logic:iterate id="tagRef" name="tags">
	  	<span class="tagSize${tagRef.fontSize}"><a href="javascript:getConcernsByTag(${tagRef.id});">${tagRef.tag.name}</a></span>&nbsp;
	</logic:iterate>
</div>

