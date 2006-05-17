<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>

<logic:iterate id="stopword" property="stopWords" name="cctForm" indexId="index">
	  	
	<pg:modulo parameter="index" folding="5" value="0"><li class="col0"><span class="includeExclude"><bean:write name="stopword" property="name"/>&nbsp;<a href="javascript:deleteStopWord(${stopword.id});"><img src="/images/trash.gif" alt="Delete this Stop Word!" border="0"></a></span></li></pg:modulo>
	
	<pg:modulo parameter="index" folding="5" value="1"><li class="col1"><span class="includeExclude"><bean:write name="stopword" property="name"/>&nbsp;<a href="javascript:deleteStopWord(${stopword.id});"><img src="/images/trash.gif" alt="Delete this Stop Word!" border="0"></a></span></li></pg:modulo>
	
	<pg:modulo parameter="index" folding="5" value="2"><li class="col2"><span class="includeExclude"><bean:write name="stopword" property="name"/>&nbsp;<a href="javascript:deleteStopWord(${stopword.id});"><img src="/images/trash.gif" alt="Delete this Stop Word!" border="0"></a></span></li></pg:modulo>
	
	<pg:modulo parameter="index" folding="5" value="4"><li class="col3"><span class="includeExclude"><bean:write name="stopword" property="name"/>&nbsp;<a href="javascript:deleteStopWord(${stopword.id});"><img src="/images/trash.gif" alt="Delete this Stop Word!" border="0"></a></span></li></pg:modulo>
	
	<pg:modulo parameter="index" folding="5" value="5"><li class="col4"><span class="includeExclude"><bean:write name="stopword" property="name"/>&nbsp;<a href="javascript:deleteStopWord(${stopword.id});"><img src="/images/trash.gif" alt="Delete this Stop Word!" border="0"></a></span></li></pg:modulo>

</logic:iterate>