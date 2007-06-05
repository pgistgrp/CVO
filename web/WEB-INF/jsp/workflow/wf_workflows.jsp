<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h2 class="headerColor">Welcome, ${baseuser.loginname}</h2>

<pg:show roles="moderator">
<h3 class="headerColor">Manage Global Components</h3>
	<div class="home-row clearfix">
		<div class="step"><a href="usermgr.do">Manage Users</a><br />
			<small>Information about this step</small></div>
	</div>
	<div class="home-row clearfix">
		<div class="step"><a href="glossaryManage.do">Manage Glossary</a><br />
			<small>Information about this step</small></div>
	</div>
	<div class="home-row clearfix">
		<div class="step"><a href="tagging.do">Manage Tags/Stopwords</a><br />
			<small>Information about this step</small></div>
	</div>

	<div class="home-row clearfix">
		<div class="step"><a href="projectManage.do">Manage Projects</a><br />
			<small>Information about this step</small></div>
	</div>
	<div class="home-row clearfix">
		<div class="step"><a href="fundingManage.do">Manage Funding</a><br />
			<small>Information about this step</small></div>
	</div>
	<div class="home-row clearfix">
		<div class="step"><a href="feedback.do">Manage Feedbacks</a><br />
			<small>Information about this step</small></div>
	</div>
	<div class="home-row clearfix">
		<div class="step"><a target="_blank" href="http://jordanisip.wufoo.com/forms/lit-bug-tracker/">Bug Tracker</a><br />
			<small>Information about this step</small></div>
	</div>
</pg:show>

<div id="workflows">
	<pg:show roles="moderator">
	<h3 class="headerColor">New Experiments</h3>
		
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
							<td>${workflow.situation.name}</td>
							<td>${workflow.situation.description}</td>
							<td><input type="button" value="Start" onclick="javascript: workflow.startWorkflow(${workflow.id});"></td>
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
	
	
	<h3 class="headerColor">Running Experiments</h3>
	<c:choose>
		<c:when test="${fn:length(runningWorkflows) > 0}">
			<table id="newTable" class="running" cellspacing="0" cellpadding="0">
				<tr>
					<th>Name</th>
					<th>Description</th>
					<th>Begin Time</th>
					<th class="last">Operation</th>
				</tr>
				<c:forEach var="workflow" items="${runningWorkflows}">
					<tr>
						<td>${workflow.situation.name}</td>
						<td>${workflow.situation.description}</td>
						<td>${workflow.beginTime}</td>
						<td><input type="button" value="Participate" onclick="javascript:location.href='userhome.do?wf=${workflow.id}';"></td>
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
		<br />
		<h3 class="headerColor">Completed Experiments</h3>
		<table id="newTable" class="finished" cellspacing="0" cellpadding="0">
			<tr>
				<th>Name</th>
				<th>Description</th>
				<th>Begin Time</th>
				<th>End Time</th>
				<th class="last">Operation</th>
			</tr>
			<c:forEach var="workflow" items="${finishedWorkflows}">
					<tr style="color:#999;">
						<td>${workflow.situation.name}</td>
						<td>${workflow.situation.description}</td>
						<td>${workflow.beginTime}</td>
						<td>${workflow.endTime}</td>
						<td><input type="button" value="Review" onclick="javascript:location.href='userhome.do?wf=${workflow.id}'"></td>
					</tr>
			</c:forEach>
		</table>

	</pg:show>
</div>


