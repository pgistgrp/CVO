<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<c:if test="${fn:length(workflow.situation.context.pendingActivities) == 0}">
		<h2>This expiriment has ended.</h2>
	</c:if>
	<h3 class="headerColor">${workflow.situation.name}</h3>
	<p><strong>124</strong> participants have been active in the ${workflow.situation.name} in the past 12 hours</p>
	<fmt:formatDate value="${workflow.beginTime}" dateStyle="full" var="beginTime" />
	<p>This expiriment began on: ${beginTime} </p>

  
	<h3 class="headerColor">Overview of all Steps</h3>
	<h5>** Limitations to our current workflow: Only shows "active" components and it doesn't differentiate between showing what a moderator can see vs a participant.  These will be changed in the near future. **</h5>

    <c:forEach var="meeting" items="${workflow.situation.context.pendingActivities}">
		<!--<pg:narrow name="meeting"/><strong>Meeting:</strong> "${meeting.description}"-->

		<c:forEach var="pmethod" items="${meeting.context.pendingActivities}">
			<pg:narrow name="pmethod"/></h4>
			<h4 class="headerColor clearBoth step-header">${pmethod.description}</h4>
				<c:forEach var="pgame" items="${pmethod.context.runningActivities}">
					<div class="home-row clearfix">
						<div class="step">
							<a href="${pgame.link}">${pgame.description}</a><br />
							<small>Information about this step</small>
						</div>
						<div class="date">11/15 - 11/25</div>
					</div>
				</c:forEach>		
		</c:forEach>
	</c:forEach>

	
	<h4 class="headerColor clearBoth step-header">Global Components</h4>
	<div class="home-row clearfix">
		<div class="step"><a href="lmMenu.do">Learn More: Home</a><br />
			<small>Information about this step</small></div>

	</div>
	<div class="home-row clearfix">
		<div class="step"><a href="glossaryPublic.do">Learn More: Public Glossary</a><br />
			<small>Information about this step</small></div>

	</div>
	
	



