<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<c:if test="${fn:length(workflow.situation.context.pendingActivities) == 0}">
		<h2>This experiment has ended.</h2>
	</c:if>
	<h3 class="headerColor">${workflow.situation.name}</h3>
	<p><strong>124</strong> participants have been active in the ${workflow.situation.name} in the past 12 hours</p>
	<fmt:formatDate value="${workflow.beginTime}" dateStyle="full" var="beginTime" />
	<p><small>This experiment began on: ${beginTime}</small></p>

  
	<h3 class="headerColor">Moderator Control Panel</h3>
	
	<pg:show roles="moderator">
		<h4 class="headerColor clearBoth step-header">Global Components</h4>
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
			<div class="step"><a href="criteriaMgr.do">Manage Planning Factors</a><br />
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

	<h3 class="headerColor">Overview of all Steps</h3>
	
	<!--History-->
	<c:forEach var="sHistory" items="${workflow.situation.context.histories}" varStatus="loop">
		<pg:narrow name="sHistory"/>
		<c:set var="sHistoryActivity" value="${sHistory.activity}" />
		<pg:narrow name="sHistoryActivity"/>
		<!--${sHistoryActivity.description}-->
			<c:forEach var="mHistory" items="${sHistoryActivity.context.histories}" varStatus="loop">
				<pg:narrow name="mHistory"/>
				<c:set var="mHistoryActivity" value="${mHistory.activity}" />
				<pg:narrow name="mHistoryActivity"/>
				<h4 class="headerColor clearBoth step-header">${mHistoryActivity.description}</h4>
				<c:forEach var="gHistory" items="${mHistoryActivity.context.histories}" varStatus="loop">		
					<pg:narrow name="gHistory"/>
					<c:set var="gHistoryActivity" value="${gHistory.activity}" />
					<pg:narrow name="gHistoryActivity"/>
					<div class="home-row clearfix">
						<div class="step">
							<a href="/workflow.do?workflowId=${workflow.id}&contextId=${mHistory.id}&historyId=${gHistory.id}">${gHistoryActivity.description}</a><br />
							<small>Information about this step</small>
						</div>
						<div class="date">00/00</div>
					</div>
				</c:forEach>
			</c:forEach>
				
	</c:forEach>
	
	
	<!--Active-->
	<c:forEach var="sActive" items="${workflow.situation.context.pendingActivities}" varStatus="loop">
		<pg:narrow name="sActive"/>
		<!--${sActive.description}-->
			<c:forEach var="mActive" items="${sActive.context.pendingActivities}" varStatus="loop">
				<pg:narrow name="mActive"/>
				<h4 class="headerColor clearBoth step-header">${mActive.description}</h4>
				
				<!-- history sub-steps in active step -->
				<c:forEach var="gActiveHistory" items="${mActive.context.histories}">
					<pg:narrow name="gActiveHistory"/>
					<c:set var="gActiveHistoryActivity" value="${gActiveHistory.activity}" />
					<pg:narrow name="gActiveHistoryActivity"/>
					<div class="home-row clearfix">
						<div class="step">
							<a href="/workflow.do?workflowId=${workflow.id}&contextId=${mActive.context.id}&historyId=${gActiveHistory.id}">${gActiveHistoryActivity.description}</a><br />
							<small>Information about this step</small>
						</div>
						<div class="date">00/00</div>
					</div>
				</c:forEach>
				
				<!-- active sub-steps in active step-->
				<div class="box4">
				<c:forEach var="gActive" items="${mActive.context.runningActivities}" varStatus="loop">
					<pg:narrow name="gActive"/>
					<div class="home-row clearfix">
						<div class="step">
							<a href="/workflow.do?workflowId=${workflow.id}&contextId=${mActive.context.id}&activityId=${gActive.id}">${gActive.description}</a><br />
							<small>Information about this step</small>
						</div>
						<div class="date"><input type="button" onclick="workflow.nextStep(${workflow.id},${mActive.context.id},${gActive.id});" value="Completed"/>	</div
					</div>
				</c:forEach>
				</div>
				
				<!--future sub-steps in active step -->
				<!--
				<c:forEach var="gActiveFuture" items="${mActive.context.futureActivities}">
					<pg:narrow name="gActiveFuture"/>
					<div class="home-row clearfix">
						<div class="step disabled">${gActiveFuture.description}<br />
							<small>Information about this step</small>
						</div>
						<div class="date disabled">00/00</div>
					</div>
				</c:forEach>
			-->
			</c:forEach>
	</c:forEach>
	
	<!--Future
	<c:forEach var="sFuture" items="${workflow.situation.context.futureActivities}" varStatus="loop">
		<pg:narrow name="sFuture"/>
			<c:forEach var="mFuture" items="${sFuture.context.futureActivities}" varStatus="loop">
				<pg:narrow name="mFuture"/>

				<h4 class="headerColor clearBoth step-header">${mFuture.description}</h4>
				<c:forEach var="gFuture" items="${mFuture.context.futureActivities}" varStatus="loop">
					<pg:narrow name="gFuture"/>
					<div class="home-row clearfix">
						<div class="step disabled">${gFuture.description}<br />
							<small>Information about this step</small>
						</div>
						<div class="date disabled">00/00</div>
					</div>
				</c:forEach>
			</c:forEach>
	</c:forEach>
	-->
   


	

	<pg:show roles="moderator">
		<h4 class="headerColor clearBoth step-header">Development Tools</h4>
		<div class="home-row clearfix ">
			<div class="step"><a href="/pgist-docs/index.html">Javadoc</a><br />
				<small>Information about this step</small></div>
		</div>
		<div class="home-row clearfix ">
			<div class="step"><a href="/dwr/index.html">DWR Test</a><br />
				<small>Information about this step</small></div>
		</div>
	</pg:show>

	



