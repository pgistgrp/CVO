<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<h2>Tags</h2>
<span class="title_section">Tag Cloud</span><br>
Click on any of these tags to view the concerns attached to each tag.  Use these tags to help you navigate and find other participant's concerns to help you create your own!<p></p>

<div id="searchTag_container">
		<div id="searchTag_title">
				<span class="title_section2">Top 25 Tags</span>
		</div>
		<div id="searchTag_form">
				<form name="searchTags" method="post" onSubmit="tagSearch($('txtSearch').value); return false;">
					<input type="text" id="txtSearch" name="txtSearch" class="searchTagTextbox" value=" tag search" onfocus="clear_textbox(this);" onkeyup="tagSearch($('txtSearch').value);"><span id="tagIndicator" style="visibility:hidden;"><img src="/images/indicator.gif"></span><div id="clearQuery" style="visibility: hidden;"><img src="/images/clearText.gif"></div>
				</form>			
		</div>
</div>
<div id="tagSearchResults"></div><br>
<div id="topTags">
	<logic:iterate id="tagRef" name="tags">
	  	<span class="tagSize${tagRef.fontSize}"><a href="javascript:getConcernsByTag(${tagRef.id});">${tagRef.tag.name}</a></span>&nbsp;
	</logic:iterate>
</div>

