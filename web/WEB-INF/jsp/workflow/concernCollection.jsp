<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<html:html>
<head>
<title>Concern Collection</title>
</head>

<body>

<h1>Concern Collection</h1>

<html:form action="/concernCollection.do">
  <input type="hidden" name="situationId" value="${workflowForm.situationId}">
  <input type="hidden" name="meetingId" value="${workflowForm.meetingId}">
  <input type="hidden" name="pmethodId" value="${workflowForm.pmethodId}">
  <input type="hidden" name="pgameId" value="${workflowForm.pgameId}">
  <input type="hidden" name="pactId" value="${workflowForm.pact.id}">
  <input type="hidden" name="goAhead" value="true">
  <input type="submit" name="next" value="Next">
</html:form>

</body>
</html:html>

