<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table border="1" width="100%">
	<tr>
		<td>Running Workflows</td>
		<td>Description</td>
		<td>Begin Time</td>
    <td>Operation</td>
	</tr>
  <c:forEach var="workflow" items="${runningWorkflows}">
      <tr>
        <td>${workflow.situation.name}</td>
        <td>${workflow.situation.description}</td>
        <td>${workflow.beginTime}</td>
        <td><a href="javascript: workflow.getWorkflow(${workflow.id});">Participate</a></td>
      </tr>
  </c:forEach>
</table>

<table border="1" width="100%">
	<tr>
		<td>Finished Workflows</td>
		<td>Description</td>
		<td>Begin Time</td>
		<td>End Time</td>
    <td>Operation</td>
	</tr>
  <c:forEach var="workflow" items="${finishedWorkflows}">
      <tr>
        <td>${workflow.situation.name}</td>
        <td>${workflow.situation.description}</td>
        <td>${workflow.beginTime}</td>
        <td>${workflow.endTime}</td>
        <td><a href="javascript: workflow.getWorkflow(${workflow.id});">Review</a></td>
      </tr>
  </c:forEach>
</table>

<pg:show roles="moderator">
  <table border="1" width="100%">
    <tr>
      <td>New Workflows</td>
      <td>Description</td>
      <td>Operation</td>
    </tr>
    <c:forEach var="workflow" items="${newWorkflows}">
        <tr>
          <td>${workflow.situation.name}</td>
          <td>${workflow.situation.description}</td>
          <td><a href="javascript: workflow.startWorkflow(${workflow.id});">Start</a></td>
        </tr>
    </c:forEach>
  </table>
</pg:show>

