<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<c:choose>
	<c:when test="${fn:length(posts) <= 0}">
		<div class="box4 padding5 centerAlign"><h3 class="headerColor">There aren't related discussions yet</h3><p>Discussions are related through tags, so tagging your posts helps you find out what's being talked about elsewhere on the site</p></div>
	</c:when>
	<c:otherwise>
		
		
		<c:forEach var="post" items="${posts}" varStatus="loop">
		
			<div id="discussion${post.id}" class="discussionRow" style="margin-top: 3px;">
					<div class="discussionRowHeader box4">			
							<div id="voting-post${post.id}" class="discussionVoting">
								${post.numAgree} of ${post.numVote} participants agree with this post | <a href="sdThread.do?isid=${structure.id}&pid=${post.id}&ioid=${post.object.id}">${post.replies} replies</a>
							</div>
							<c:choose>
								<c:when test="${post.object.level == 'object'}">
                  <span class="discussionTitle"><a href="sdThread.do?isid=${structure.id}&pid=${post.id}&ioid=${post.object.id}">${post.title}</a> - Posted by: ${post.owner.loginname} in ${post.object.object}</span>
                </c:when>
								<c:otherwise>
                  <span class="discussionTitle"><a href="sdThread.do?isid=${structure.id}&pid=${post.id}&ioid=${post.object.id}">${post.title}</a> - Posted by: ${post.owner.loginname} in "Discussion about all concern themes"</span>
								</c:otherwise>
							</c:choose>
					</div>
			</div>

		</c:forEach>
		
				<div class="pagination2 padding5">
				  		You are currently viewing page: ${setting.page} of ${setting.pageSize} &nbsp;
						<logic:equal name="setting" property="page" value="1">
							<img src="images/btn_prev_fade.gif" alt="No Previous Pages" />
						</logic:equal>
						<logic:notEqual name="setting" property="page" value="1">	
							<a href="javascript:goToPage('contextPosts',${setting.page}-1);"><img src="images/btn_prev_a.gif" alt="Prev" name="prev" class="button" id="prev" onMouseOver="MM_swapImage('prev','','images/btn_prev_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
						</logic:notEqual>
						
						
						<logic:equal name="setting" property="page" value="${setting.pageSize}">
							<img src="images/btn_next_fade.gif" alt="No Additional Pages" />
						</logic:equal>
						<logic:notEqual name="setting" property="page" value="${setting.pageSize}">	
							<a href="javsascript:goToPage('contextPosts',${setting.page}+1)"><img src="images/btn_next_a.gif" alt="Next" name="next" class="button" id="next" onMouseOver="MM_swapImage('next','','images/btn_next_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
						</logic:notEqual>
		  		</div>


	</c:otherwise>

		
</c:choose>