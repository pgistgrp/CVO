<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<c:choose>
	<c:when test="${fn:length(posts) <= 0}">
		<div class="box4 padding5 centerAlign"><h3 class="headerColor">There aren't related discussions yet!</h3><p>Discussions are related through tags...blah blah</p></div>
	</c:when>
	<c:otherwise>
		
		
		<c:forEach var="post" items="${posts}" varStatus="loop">
		
			<div id="discussion${post.id}" class="discussionRow">
					<div class="discussionRowHeader box6">			
						<div id="voting-post${post.id}" class="discussionVoting">
							${post.numAgree} of ${post.numVote} participants agree with this post
							<c:choose>
								<c:when test="${post.object == null}">
									<a href="javascript:infoObject.setVote('post',${post.id}, 'false');"><img src="/images/btn_thumbsdown.png" alt="I disagree!" border="0"/></a> 
									<a href="javascript:infoObject.setVote('post',${post.id}, 'true');"><img src="/images/btn_thumbsup.png" alt="I agree!" border="0"/></a>
								</c:when>
								<c:otherwise>
									<img src="images/btn_thumbsdown_off.png" alt="Disabled Button"/> <img src="images/btn_thumbsup_off.png" alt="Disabled Button"/>
								</c:otherwise>
							</c:choose>
						</div>
						<span class="discussionTitle">
							${post.title}
					</div>
					

					<div class="discussionBody">
						<div class="discussionText">
							<p>${post.content}</p>
							<h3>- ${post.owner.loginname}</h3>
						</div>
						<div class="discussionComments">

							<a href="sdThread.do?isid=${structure.id}&pid=${post.id}&ioid=${object.id}"><h3 style="display:inline;">${post.replies} Replies</h3></a> (${post.views} views)
						</div>
					</div>
			</div>
		
		
		<!-- End New Style -->
		</c:forEach>
		
				  <div class="pagination discussion-left">
				  		You are currently viewing page: ${setting.page} of ${setting.pageSize} &nbsp;
						<logic:equal name="setting" property="page" value="1">
							<img src="images/btn_prev_fade.gif" alt="No Previous Pages" />
						</logic:equal>
						<logic:notEqual name="setting" property="page" value="1">	
							<a href="javascript:io.goToPage(${setting.page}-1);"><img src="images/btn_prev_a.gif" alt="Prev" name="prev" class="button" id="prev" onMouseOver="MM_swapImage('prev','','images/btn_prev_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
						</logic:notEqual>
						
						
						<logic:equal name="setting" property="page" value="${setting.pageSize}">
							<img src="images/btn_next_fade.gif" alt="No Additional Pages" />
						</logic:equal>
						<logic:notEqual name="setting" property="page" value="${setting.pageSize}">	
							<a href="javascript:io.goToPage(${setting.page}+1)"><img src="images/btn_next_a.gif" alt="Next" name="next" class="button" id="next" onMouseOver="MM_swapImage('next','','images/btn_next_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
						</logic:notEqual>
		  		</div>


	</c:otherwise>

		
</c:choose>