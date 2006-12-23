<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

	<c:choose>
		<c:when test="${fn:length(concerns) == 0}">
				<div class="discussion-left box8">
					<p>No concerns with the current filters could be found.  Try removing a filter.</p>
				</div>
		</c:when>
		<c:otherwise>
			<c:forEach var="concern" items="${concerns}" varStatus="loop">
						<!-- START Discussion Row -->	
						<div class="discussionRow">
							<c:choose>
								<c:when test="${baseuser.id == concern.author.id}">
									<div id="concern${concern.id}" class="discussion-left box7">			
								</c:when>
								<c:otherwise>
									<div id="concern${concern.id}" class="discussion-left ${((loop.index % 2) == 0) ? 'box8' : ''}">	
								</c:otherwise>
							</c:choose>
								<div class="discussionRowHeader">
									<div id="concernVote${concern.id}" class="discussionVoting">
										Do you agree with this concern?  ${concern.numAgree} of ${concern.numVote} people agree so far.
										
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
									<div id="editingArea${concern.id}" style="display:none"></div>
									<div class="discussionText" id="discussionText${concern.id}"><p>"${concern.content}"</p></div>
									<h3 id="discussionAuthor">- <bean:write name="concern" property="author.loginname" /></h3>
										<div class="discussionComments" id="discussionComments"><h3><a href="concern.do?id=${concern.id}">${concern.replies} Comments</a></h3> (${concern.views} views)</div>
										<div class="discussionTagsList">
											<!-- iterate through concern tags here -->	
											<div id="tagsUL${concern.id}"><ul class="tagsInline">
												<li class="tagsInline"><strong>Tags:</strong> </li>
												<c:forEach items="${concern.tags}" var="tagref">
													<c:choose>
														<c:when test="${baseuser.id == concern.author.id}">
															<li class="box6 tagsInline">		
														</c:when>
														<c:otherwise>
															<li class="box8 tagsInline">
														</c:otherwise>
													</c:choose>

													<a href="javascript:changeCurrentFilter(${tagref.id});">${tagref.tag.name}</a></li>
												</c:forEach>
											</ul>
											</div>
										
										<div id="tagEditingArea${concern.id}" style="display:none"></div>
										<div style="clear: left;"></div>
										
										<!-- end tag iteration -->
	
										</div><!--end discussionTagsList -->
										<c:if test="${baseuser.id == concern.author.id}">
											<c:if test="${concern.numVote == 1}">
												<c:if test="${concern.replies == 0}">
													<div class="box6">
														<strong>Author Actions:</strong> <a href="javascript:editConcernPopup(${concern.id});">Edit Concern</a> &nbsp; <a href="javascript:editTagsPopup(${concern.id});">Edit Tags</a> &nbsp; <a href="javascript:deleteConcern(${concern.id});">Delete Concern</a> 
													</div>
												</c:if>
											</c:if>
										</c:if>
								</div><!-- end discussion body -->	
							</div><!-- end discussion-left -->
						</div><!-- end discussion row -->
						<!-- END Discussion Row -->
			</c:forEach>

		  <div class="pagination">
		  				You are currently viewing page: ${setting.page} of ${setting.pageSize} &nbsp;
						<logic:equal name="setting" property="page" value="1">
							<img src="images/btn_prev_fade.gif" alt="No Previous Pages" />
						</logic:equal>
						<logic:notEqual name="setting" property="page" value="1">	
							<a href="javascript:goToPage(${setting.page}-1, 'concerns');"><img src="images/btn_prev_a.gif" alt="Prev" name="prev" class="button" id="prev" onMouseOver="MM_swapImage('prev','','images/btn_prev_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
						</logic:notEqual>
						
						
						<logic:equal name="setting" property="page" value="${setting.pageSize}">
							<img src="images/btn_next_fade.gif" alt="No Additional Pages" />
						</logic:equal>
						<logic:notEqual name="setting" property="page" value="${setting.pageSize}">	
							<a href="javascript:goToPage(${setting.page}+1, 'concerns')"><img src="images/btn_next_a.gif" alt="Next" name="next" class="button" id="next" onMouseOver="MM_swapImage('next','','images/btn_next_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
						</logic:notEqual>
						

		  </div>
		</c:otherwise>
	</c:choose>





