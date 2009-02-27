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
	<!--<p><strong>124</strong> participants have visited in the ${workflow.situation.name} in the past 12 hours</p>-->
	<fmt:formatDate value="${workflow.beginTime}" dateStyle="full" var="beginTime" />
	<!--History-->
	<c:forEach var="sHistory" items="${workflow.situation.context.histories}" varStatus="loop">
		<pg:narrow name="sHistory"/>
		<c:set var="sHistoryActivity" value="${sHistory}" />
		<pg:narrow name="sHistoryActivity"/>
		<!--${sHistoryActivity.description}-->
			<c:forEach var="mHistory" items="${sHistoryActivity.context.histories}" varStatus="loop">
				<pg:narrow name="mHistory"/>
				<c:set var="mHistoryActivity" value="${mHistory}" />
				<pg:narrow name="mHistoryActivity"/>
				
				<h4 class="headerColor clearBoth step-header">${mHistoryActivity.description}</h4>
				<c:if test="${mHistoryActivity.description == 'Always available'}"><!--ugly!-->
					<div class="box4">
				</c:if>
				<c:forEach var="gHistory" items="${mHistoryActivity.context.histories}" varStatus="loop">		
					<pg:narrow name="gHistory"/>
					<c:set var="gHistoryActivity" value="${gHistory}" />
					<pg:narrow name="gHistoryActivity"/>
					<div class="home-row clearfix">
						<c:choose>
							<c:when test="${gHistoryActivity.access == 'moderator'}">
								<pg:show roles="moderator">
									<div class="step">
										<a href="/workflow.do?workflowId=${workflow.id}&contextId=${mHistoryActivity.context.id}&activityId=${gHistory.id}">${gHistoryActivity.title}</a><br />
										<small>${gHistoryActivity.description}</small>
									</div>
									<div class="date">
									    <fmt:formatDate value="${gHistoryActivity.beginTime}" pattern="MM/dd" /> - 
									    <fmt:formatDate value="${gHistoryActivity.endTime}" pattern="MM/dd" />
                  </div>
								</pg:show>
							</c:when>
							<c:otherwise>
								<pg:show roles="participant">
									<div class="step">
										<a href="/workflow.do?workflowId=${workflow.id}&contextId=${mHistoryActivity.context.id}&activityId=${gHistory.id}">${gHistoryActivity.title}</a><br />
										<small>${gHistoryActivity.description}</small>
									</div>
									<div class="date">
									    <fmt:formatDate value="${gHistoryActivity.beginTime}" pattern="MM/dd" /> - 
									    <fmt:formatDate value="${gHistoryActivity.endTime}" pattern="MM/dd" />
									</div>
								</pg:show>
							</c:otherwise>
						</c:choose>
					</div>
				</c:forEach>
				<c:if test="${mHistoryActivity.description == 'Always available'}">
					</div>
				</c:if>
			</c:forEach>
				
	</c:forEach>
	
	
	<!--Active-->
	<c:forEach var="sActive" items="${workflow.situation.context.pendingActivities}" varStatus="loop">
		<pg:narrow name="sActive"/>
		<!--${sActive.description}-->
			<c:forEach var="mActive" items="${sActive.context.pendingActivities}" varStatus="loop">
				<pg:narrow name="mActive"/>
				<h4 class="headerColor clearBoth step-header">${mActive.description}</h4>

				<c:forEach var="gActiveHistory" items="${mActive.context.histories}">
					<pg:narrow name="gActiveHistory"/>
					<c:set var="gActiveHistoryActivity" value="${gActiveHistory}" />
					<pg:narrow name="gActiveHistoryActivity"/>
					<c:choose>
						<c:when test="${gActiveHistoryActivity.access == 'moderator'}">
							<pg:show roles="moderator">
								<div class="home-row clearfix">
									<div class="step">
										<a href="/workflow.do?workflowId=${workflow.id}&contextId=${mActive.context.id}&activityId=${gActiveHistory.id}">${gActiveHistoryActivity.title}</a><br />
										<small>${gActiveHistoryActivity.title}</small>
									</div>
									<div class="date">
									    <fmt:formatDate value="${gActiveHistoryActivity.beginTime}" pattern="MM/dd" /> - 
									    <fmt:formatDate value="${gActiveHistoryActivity.endTime}" pattern="MM/dd" />
									</div>
								</div>
							</pg:show>
						</c:when>
						<c:otherwise>
							<pg:show roles="participant">
								<div class="home-row clearfix">
									<div class="step">
										<a href="/workflow.do?workflowId=${workflow.id}&contextId=${mActive.context.id}&activityId=${gActiveHistory.id}">${gActiveHistoryActivity.title}</a><br />
										<small>${gActiveHistoryActivity.title}</small>
									</div>
									<div class="date">
									    <fmt:formatDate value="${gActiveHistoryActivity.beginTime}" pattern="MM/dd" /> - 
									    <fmt:formatDate value="${gActiveHistoryActivity.endTime}" pattern="MM/dd" />
									</div>
								</div>
							</pg:show>
						</c:otherwise>
					</c:choose>
				</c:forEach>

				
				<div class="box4">
				<c:forEach var="gActive" items="${mActive.context.runningActivities}" varStatus="loop">
					<pg:narrow name="gActive"/>
					<c:choose>
						<c:when test="${gActive.access == 'moderator' || gActive.access == 'expert'}">
							<pg:show roles="moderator, expert">
								<div class="home-row clearfix">
									<div class="step">
                    <pg:show roles="expert">
										  <a href="/workflow.do?workflowId=${workflow.id}&contextId=${mActive.context.id}&activityId=${gActive.id}">${gActive.title}</a><br />
                    </pg:show>
                    <pg:hide roles="expert">
										  ${gActive.title}<br />
                    </pg:hide>
										<small>${gActive.description}</small>
									</div>
									<div class="date">
                    <pg:show roles="moderator">
                      <pg:declared var="declaredExitCondition" context="${mActive.context}" activity="${gActive}" name="exitCondition" />
                      <pg:envVar var="exitCondition" context="${mActive.context}" activity="${gActive}" name="exitCondition" />
                      <c:choose>
                        <c:when test="${declaredExitCondition && exitCondition == null}">
                          <input type="button" name="completedButton" disabled="true" value="Completed"/>
                        </c:when>
                        <c:otherwise>
                          <input type="button" name="completedButton" onclick="if (window.confirm('This will publish any changes you have made with this tool.  There is no undo.')){workflow.nextStep(${workflow.id},${mActive.context.id},${gActive.id});}" value="Completed"/>
                        </c:otherwise>
                      </c:choose>
                    </pg:show>
                  </div>
								</div>
							</pg:show>
						</c:when>
						<c:otherwise>
							<pg:show roles="participant">
								<div class="home-row clearfix">
									<div class="step">
										<a href="/workflow.do?workflowId=${workflow.id}&contextId=${mActive.context.id}&activityId=${gActive.id}">${gActive.title}</a><br />
										<small>${gActive.description}</small>
									</div>
									<div class="date">
                    <pg:show roles="moderator">
                      <pg:declared var="declaredExitCondition" context="${mActive.context}" activity="${gActive}" name="exitCondition" />
                      <pg:envVar var="exitCondition" context="${mActive.context}" activity="${gActive}" name="exitCondition" />
                      <c:choose>
                        <c:when test="${declaredExitCondition && exitCondition == null}">
                          <input type="button" name="completedButton" disabled="true" value="Completed"/>
                        </c:when>
                        <c:otherwise>
                          <input type="button" name="completedButton" onclick="if (window.confirm('This will publish any changes you have made with this tool.  There is no undo.')){workflow.nextStep(${workflow.id},${mActive.context.id},${gActive.id});}" value="Completed"/>
                        </c:otherwise>
                      </c:choose>
                    </pg:show>
                  </div>
								</div>
							</pg:show>
						</c:otherwise>
					</c:choose>

				</c:forEach>
				</div>
				
				<c:forEach var="gActiveFuture" items="${mActive.context.futureActivities}">
					<pg:narrow name="gActiveFuture"/>			
					<c:choose>
						<c:when test="${gActiveFuture.access == 'moderator'}">
							<pg:show roles="moderator">
								<div class="home-row clearfix">
									<div class="step disabled">${gActiveFuture.title}<br />
										<small>${gActiveFuture.description}</small>
									</div>
									<div class="date disabled">
									    <fmt:formatDate value="${gActiveFuture.beginTime}" pattern="MM/dd" /> - 
									    <fmt:formatDate value="${gActiveFuture.endTime}" pattern="MM/dd" />
									</div>
								</div>
							</pg:show>
						</c:when>
						<c:otherwise>
							<pg:show roles="participant">
								<div class="home-row clearfix">
									<div class="step disabled">${gActiveFuture.title}<br />
										<small>${gActiveFuture.description}</small>
									</div>
									<div class="date disabled">
									    <fmt:formatDate value="${gActiveFuture.beginTime}" pattern="MM/dd" /> - 
									    <fmt:formatDate value="${gActiveFuture.endTime}" pattern="MM/dd" />
									</div>
								</div>
							</pg:show>
						</c:otherwise>
					</c:choose>

				</c:forEach>
				
			</c:forEach>
	</c:forEach>
	
	<!--Future -->
	
	<c:forEach var="sFuture" items="${workflow.situation.context.futureActivities}" varStatus="loop">
		<pg:narrow name="sFuture"/>
	
			<c:forEach var="mFuture" items="${sFuture.context.futureActivities}" varStatus="loop">
				<pg:narrow name="mFuture"/>
				<h4 class="headerColor clearBoth step-header">${mFuture.description}</h4>
	
				<c:forEach var="gFuture" items="${mFuture.context.futureActivities}" varStatus="loop">
					<pg:narrow name="gFuture"/>
					<c:choose>
						<c:when test="${gFuture.access == 'moderator'}">
							<pg:show roles="moderator">
								<div class="home-row clearfix">
									<div class="step disabled">${gFuture.title}<br />
										<small>${gFuture.description}</small>
									</div>
									<div class="date disabled">
									    <fmt:formatDate value="${gFuture.beginTime}" pattern="MM/dd" /> - 
									    <fmt:formatDate value="${gFuture.endTime}" pattern="MM/dd" />
									</div>
								</div>
							</pg:show>
						</c:when>
						<c:otherwise>
							<pg:show roles="participant">
								<div class="home-row clearfix">
									<div class="step disabled">${gFuture.title}<br />
										<small>${gFuture.description}</small>
									</div>
									<div class="date disabled">
									    <fmt:formatDate value="${gFuture.beginTime}" pattern="MM/dd" /> - 
									    <fmt:formatDate value="${gFuture.endTime}" pattern="MM/dd" />
									</div>
								</div>
							</pg:show>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</c:forEach>
	</c:forEach>

	<pg:show roles="moderator">
		<h4 class="headerColor clearBoth step-header">Development Tools</h4>
		<div class="home-row clearfix ">
			<div class="step"><a href="/pgist-docs/index.html">Javadoc</a><br />
				<small>Java Documentation</small></div>
		</div>
		<div class="home-row clearfix ">
			<div class="step"><a href="/dwr/index.html">DWR Test</a><br />
				<small>Test Java DWR Methods</small></div>
		</div>
	</pg:show>

	



