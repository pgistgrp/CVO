<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="running" style="width:100%; text-align: left;margin-bottom:10px;">
	<strong>Decision Situation:</strong> ${workflow.situation.name}<br>
	<strong>Description:</strong> ${workflow.situation.description}<br>
	<strong>Begin Time:</strong> ${workflow.beginTime}<br>

  
  <h4>Running Activities</h4>
	<ol>
    <c:forEach var="meeting" items="${workflow.situation.context.pendingActivities}">
      <li>
				<pg:narrow name="meeting"/><strong>Meeting:</strong> "${meeting.description}"
				<ul>
					<c:forEach var="pmethod" items="${meeting.context.pendingActivities}">
					<pg:narrow name="pmethod"/>
					<li>
						<strong>PMethod:</strong> "${pmethod.description}"
						<ul>
							<c:forEach var="pgame" items="${pmethod.context.runningActivities}">
							<li><strong>PGame:</strong> "<a href="${pgame.link}">${pgame.description}</a>"</li>
							</c:forEach>
						</ul>
					</li>
					</c:forEach>
				</ul>
			</li>
		</c:forEach>
  </ol>
	
</div>

