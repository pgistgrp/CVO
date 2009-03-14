<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
  <textarea style="width:100%; height: 850px;" id="reportContent"><jsp:include page="report_${infoObject.target.workflowId}.html" /></textarea>
</div>
<input type="button" value="Save" onclick="infoObject.saveReport();">
