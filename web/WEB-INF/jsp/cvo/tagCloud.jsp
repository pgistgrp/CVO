<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<h2>Tags</h2>
<span class="title_section">Tag Cloud</span><br>
<p>Click on any of these tags to view the concerns attached to each tag.  <span class="highlight">Use these tags to help you navigate and find other participant's concerns</span> to help you create your own!</p>
<form name="searchTags" method="post" onSubmit="searchTags();">
	<input type="text" class="textbox"><input type="button" id="btnSearchTag" name="btnSearchTag" value="Search" onclick="SearchTags();">
</form>
<logic:iterate id="tagRef" name="tags">
  	<span class="tagSize${tagRef.fontSize}">${tagRef.tag.name}</span>
</logic:iterate>


