<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<h2>Tags</h2>
<logic:iterate id="tagRef" name="tags">
  	<span class="tagSize${tagRef.fontSize}">${tagRef.tag.name}</span>
</logic:iterate>


