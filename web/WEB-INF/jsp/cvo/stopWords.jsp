<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<logic:iterate id="stopword" property="stopWords" name="cctForm">
	  	<li class="tags"><bean:write name="stopword" property="name"/></li>
</logic:iterate>