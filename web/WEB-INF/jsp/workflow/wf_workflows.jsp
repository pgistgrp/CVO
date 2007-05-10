<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="workflows">
	<table id="newTable" cellspacing="0" cellpadding="0">
		<tr>
			<th>Running Workflows</th>
			<th>Description</th>
			<th>Begin Time</th>
			<th>Operation</th>
		</tr>
		<c:forEach var="workflow" items="${runningWorkflows}">
				<tr>
					<td>${workflow.situation.name}</td>
					<td>${workflow.situation.description}</td>
					<td>${workflow.beginTime}</td>
					<td><input type="button" value="Participate" onclick="javascript: workflow.getWorkflow(${workflow.id});"></td>
				</tr>
		</c:forEach>
	</table>
	
	<table id="newTable" cellspacing="0" cellpadding="0">
		<tr>
			<th>Finished Workflows</th>
			<th>Description</th>
			<th>Begin Time</th>
			<th>End Time</th>
			<th>Operation</th>
		</tr>
		<c:forEach var="workflow" items="${finishedWorkflows}">
				<tr>
					<td>${workflow.situation.name}</td>
					<td>${workflow.situation.description}</td>
					<td>${workflow.beginTime}</td>
					<td>${workflow.endTime}</td>
					<td><input type="button" value="Review" onclick="javascript: workflow.getWorkflow(${workflow.id});"></td>
				</tr>
		</c:forEach>
	</table>
	
	<pg:show roles="moderator">
		<table id="newTable" cellspacing="0" cellpadding="0">
			<tr>
				<th>New Workflows</th>
				<th>Description</th>
				<th>Operation</th>
			</tr>
			<c:forEach var="workflow" items="${newWorkflows}">
					<tr>
						<td>${workflow.situation.name}</td>
						<td>${workflow.situation.description}</td>
						<td><input type="button" value="Start" onclick="javascript: workflow.startWorkflow(${workflow.id});"></td>
					</tr>
			</c:forEach>
		</table>
	</pg:show>
</div>

