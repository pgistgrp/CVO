<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h2 class="headerColor">Welcome, ${baseuser.loginname}</h2>

<div id="workflows">
	<pg:show roles="moderator">
	<h3 class="headerColor">New experiments</h3>
		
			<table id="newTable" cellspacing="0" cellpadding="0">
				<c:if test="${fn:length(newWorkflows) > 0}">
					<tr>
						<th>Name</th>
						<th>Description</th>
						<th class="last">Operation</th>
					</tr>
				</c:if>
				<c:forEach var="workflow" items="${newWorkflows}">
						<tr>
							<td>${workflow.name}</td>
							<td>${workflow.description}</td>
							<td style="text-align:right"><input type="button" value="Start" onclick="javascript: workflow.startWorkflow(${workflow.id});"></td>
						</tr>
				</c:forEach>
			</table>
		
		<button onclick="javascript:Element.toggle('newExpiriment');void(0);" class="padding5">
			<img src="images/addItem.gif"/> Create a new experiment
		</button>
		<div id="newExpiriment" style="display:none; border: 1px solid #ccc">
			<!-- load templates here -->
		</div>
		
	</pg:show>
	
	
	<h3 class="headerColor">Running experiments</h3>
	<c:choose>
		<c:when test="${fn:length(runningWorkflows) > 0}">
			<table id="newTable" class="running" cellspacing="0" cellpadding="0">
				<tr>
					<th>Name</th>
					<th>Description</th>
					<th>Begin time</th>
					<th class="last">Operation</th>
				</tr>
				<c:forEach var="workflow" items="${runningWorkflows}">
					<tr>
						<td>${workflow.name}</td>
						<td>${workflow.description}</td>
						<td><fmt:formatDate dateStyle="full" value="${workflow.beginTime}" /> at <fmt:formatDate type="time" value="${workflow.beginTime}" /></td>
						<td>
						    <input type="button" value="Participate" onclick="javascript:location.href='userhome.do?workflowId=${workflow.id}';" />
						    <pg:show roles="moderator"><input type="button" value="Manage Agenda" onclick="javascript:location.href='agendaManager.do?workflowId=${workflow.id}';" /></pg:show>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
		<table id="newTable" class="noActive" cellspacing="0" cellpadding="0">
			<tr>
				<th>
					<h3>Sorry, there are currently no active experiments.</h3>The moderator will email you when the experiment is ready.</h3>
				</th>
			</tr>
		</table>
		</c:otherwise>
	</c:choose>

	
	
	<pg:show roles="moderator">
		<c:if test="${fn:length(finishedWorkflows) > 0}">
			<br />
			<h3 class="headerColor">Completed experiments</h3>
			<table id="newTable" class="finished" cellspacing="0" cellpadding="0">
				<tr>
					<th>Name</th>
					<th>Description</th>
					<th>Begin time</th>
					<th>End time</th>
					<th class="last">Operation</th>
				</tr>
				<c:forEach var="workflow" items="${finishedWorkflows}">
						<tr style="color:#999;">
							<td>${workflow.situation.name}</td>
							<td>${workflow.situation.description}</td>
							<td>${workflow.beginTime}</td>
							<td>${workflow.endTime}</td>
							<td><input type="button" value="Review" onclick="javascript:location.href='userhome.do?workflowId=${workflow.id}'"></td>
						</tr>
				</c:forEach>
			</table>
		</c:if>
	</pg:show>
</div>

<pg:show roles="moderator">
<br />
<h3 class="headerColor">Manage global components</h3>
<div id="global-components">
	<ul>
		<li><a href="usermgr.do"><img src="images/user.png" title="Manage users"/>Users</a></li>
		<li><a href="glossaryManage.do"><img src="images/book_open.png" title="Manage Glossary"/>Glossary</a></li>
		<li><a href="tagging.do"><img src="images/stop.png" title="Manage stopwords"/>Stopwords</a></li>
		<li><a href="projectManage.do"><img src="images/project.png" title="Manage projects"/>Projects</a></li>
		<li><a href="fundingManage.do"><img src="images/money_dollar.png" title="Manage funding sources"/>Funding</a></li>
		<li><a href="feedback.do"><img src="images/user_comment.png" title="Manage users"/>Feedbacks</a></li>
		<li id="bugtrack"><a target="_blank" href="http://jordanisip.wufoo.com/forms/lit-bug-tracker/"><img src="images/bug.png" title="Manage users"/>Bug Tracker</a></li>
		<br class="clearBoth" />
	</ul>
</div>
</pg:show>
