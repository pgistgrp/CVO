<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

	<c:choose>
		<c:when test="${fn:length(concerns) == 0}">
				<p>No concerns with the current filters could be found.  Try removing a filter.</p>
		</c:when>
		<c:otherwise>
			<c:forEach var="concern" items="${concerns}" varStatus="loop">
						<!-- START Discussion Row -->	
						<div id="concern${concern.id}" class="discussionRow">
							<c:choose>
								<c:when test="${baseuser.id == concern.author.id}">
									<div class="discussion-left box7">			
								</c:when>
								<c:otherwise>
									<div class="discussion-left ${((loop.index % 2) == 0) ? 'box8' : ''}">	
								</c:otherwise>
							</c:choose>
								<div class="discussionRowHeader">
									<div id="concernVote${concern.id}" class="discussionVoting">
										${concern.numAgree} of ${concern.numVote} participants agree with this concern
										
									 	<c:choose>
									 		<c:when test="${concern.object == null}">
												<a href="javascript:setVote(${concern.id}, 'false');"><img src="images/btn_thumbsdown.png" alt="Disagree" /></a>&nbsp;
												<a href="javascript:setVote(${concern.id}, 'true');"><img src="images/btn_thumbsup.png"  alt="Agree" /></a>
											</c:when>
											<c:otherwise>
												<img src="images/btn_thumbsdown_off.png" alt="Disabled Button"/> <img src="images/btn_thumbsup_off.png" alt="Disabled Button"/>
											</c:otherwise>
										</c:choose>
	
									</div><!-- end discussionVoting -->
								</div><!-- end discussionRowHeader -->
								<div class="discussionBody">
									<div class="discussionText"><p>"${concern.content}"</p></div>
									<h3>- <bean:write name="concern" property="author.loginname" /></h3>
										<div class="discussionComments">0 Comments</div>
										<div class="discussionTagsList">
											<!-- iterate through concern tags here -->	
											<ul class="tagsInline">
												<li class="tagsInline"><strong>Tags:</strong> </li>
												<c:forEach items="${concern.tags}" var="tagref">
													<c:choose>
														<c:when test="${baseuser.id == concern.author.id}">
															<li class="box7 tagsInline">		
														</c:when>
														<c:otherwise>
															<li class="box8 tagsInline">
														</c:otherwise>
													</c:choose>

													<a href="javascript:changeCurrentFilter(${tagref.id});">${tagref.tag.name}</a></li>
												</c:forEach>
											</ul>
										<div style="clear: left;"></div>
										
										<!-- end tag iteration -->
	
										</div><!--end discussionTagsList -->
										<c:if test="${baseuser.id == concern.author.id}">
												<div class="box6">
													<strong>Author Actions:</strong> <a href="javascript:editConcern(${concern.id});">Edit Concern</a> &nbsp; <a href="javascript:editTags(${concern.id});">Edit Tags</a> &nbsp; <a href="javascript:deleteConcern(${concern.id});">Delete Concern</a> 
												</div>
										</c:if>
								</div><!-- end discussion body -->	
							</div><!-- end discussion-left -->
						</div><!-- end discussion row -->
						<!-- END Discussion Row -->
			</c:forEach>

		  <div class="pagination">
						<logic:equal name="setting" property="page" value="1">
							<img src="images/btn_prev_fade.gif" alt="No Previous Pages" />
						</logic:equal>
						<logic:notEqual name="setting" property="page" value="1">	
							<a href="javascript:getContextConcerns('',${setting.page}-1, true); void(0);"><img src="images/btn_prev_a.gif" alt="Prev" name="prev" class="button" id="prev" onMouseOver="MM_swapImage('prev','','images/btn_prev_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
						</logic:notEqual>
						
						
						<logic:equal name="setting" property="page" value="${setting.pageSize}">
							<img src="images/btn_next_fade.gif" alt="No Additional Pages" />
						</logic:equal>
						<logic:notEqual name="setting" property="page" value="${setting.pageSize}">	
							<a href="javascript:getContextConcerns('',${setting.page}+1,true); void(0);"><img src="images/btn_next_a.gif" alt="Next" name="next" class="button" id="next" onMouseOver="MM_swapImage('next','','images/btn_next_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
						</logic:notEqual>
						

		  </div>
		</c:otherwise>
	</c:choose>





