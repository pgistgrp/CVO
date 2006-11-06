<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="postReplies">
<c:if test="${fn:length(reply.tags) != 0}">
	<h4 style="text-transform:capitalize;">Replies to ${post.title}</h4>
</c:if>
<logic:iterate id="reply" name="replies">
			<div id="discussion${post.id}" class="discussionRow" style="margin-top: 5px;">
						<c:choose>
								<c:when test="${baseuser.id == reply.owner.id}">
									<div class="discussionRowHeader box6">			
								</c:when>
								<c:otherwise>
									<div class="discussionRowHeader box1">
								</c:otherwise>
						</c:choose>
						<div id="voting-post${reply.id}" class="discussionVoting">
							${reply.numAgree} of ${reply.numVote} participants agree with this post
							<c:choose>
								<c:when test="${reply.object == null}">
									<a href="javascript:io.setVote('post',${reply.id}, 'false');"><img src="/images/btn_thumbsdown.png" alt="I disagree!" border="0"/></a> 
									<a href="javascript:io.setVote('post',${reply.id}, 'true');"><img src="/images/btn_thumbsup.png" alt="I agree!" border="0"/></a>
								</c:when>
								<c:otherwise>
									<img src="images/btn_thumbsdown_off.png" alt="Disabled Button"/> <img src="images/btn_thumbsup_off.png" alt="Disabled Button"/>
								</c:otherwise>
							</c:choose>
						</div>
						<span class="discussionTitle">
							${reply.title}
					</div>
					
						<c:choose>
								<c:when test="${baseuser.id == reply.owner.id}">
									<div class="discussionBody box7 padding5">		
								</c:when>
								<c:otherwise>
									<div class="discussionBody padding5">
								</c:otherwise>
						</c:choose>
						<div class="discussionText">
							<pg:show roles="moderator"><p align="right">Moderator Options: <input type="button" onClick="deleteReply(reply.id);" value="Delete" /></p></pg:show>
							<div id="replyContent${reply.id}"><p>${reply.content}</p></div>
							<div id="replyOwner${reply.id}"><h3>- ${reply.owner.loginname}</h3></div>
						</div>
						<div class="discussionComments">
							 <a href="javascript:io.setQuote(${reply.id});">Quote</a>
						</div>
						<c:if test="${fn:length(reply.tags) > 0}">
							<ul class="tagsInline">
								<li class="tagsInline"><strong>Tags:</strong> </li>
								<c:forEach var="tag" items="${reply.tags}">
									<c:choose>
										<c:when test="${baseuser.id == reply.owner.id}">
											<li class="box6 tagsInline">		
										</c:when>
										<c:otherwise>
											<li class="box8 tagsInline">
										</c:otherwise>
									</c:choose>
									<a href="javascript:io.getReplies(${tag.id},0,true);">${tag.name}</a></li>
								</c:forEach>
							</ul>
							<div style="clear: left;"></div>
						</c:if>
					</div>
			</div>
</div>
<div class="clearBoth"></div>
	
</logic:iterate>

		  <div class="pagination">
		  				You are currently viewing page: ${setting.page} of ${setting.pageSize} &nbsp;
						<logic:equal name="setting" property="page" value="1">
							<img src="images/btn_prev_fade.gif" alt="No Previous Pages" />
						</logic:equal>
						<logic:notEqual name="setting" property="page" value="1">	
							<a href="javascript:goToPage(${setting.page}-1);"><img src="images/btn_prev_a.gif" alt="Prev" name="prev" class="button" id="prev" onMouseOver="MM_swapImage('prev','','images/btn_prev_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
						</logic:notEqual>
						
						
						<logic:equal name="setting" property="page" value="${setting.pageSize}">
							<img src="images/btn_next_fade.gif" alt="No Additional Pages" />
						</logic:equal>
						<logic:notEqual name="setting" property="page" value="${setting.pageSize}">	
							<a href="javascript:goToPage(${setting.page}+1)"><img src="images/btn_next_a.gif" alt="Next" name="next" class="button" id="next" onMouseOver="MM_swapImage('next','','images/btn_next_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
						</logic:notEqual>
						

		  </div>

</div>
<a name="replyAnchor"></a>
<div id="newReply" class="box8 padding5" style="margin-top: 10px;">
	<h3 class="headerColor">Post a Reply</h3>
	<form>
		<p><label>Post Title</label><br><input style="text-transform:capitalize;" maxlength=100 size=100 type="text" value="Re: ${post.title}" id="txtNewReplyTitle"/></p>
		<p><label>Your Thoughts</label><br><textarea style="width:100%; height: 150px;" id="txtnewReply"></textarea></p>
		<p><label>Tag your post (comma separated)</label><br><input style="width:100%" id="txtNewReplyTags" type="text" /></p>
		<input type="button" onClick="io.createReply();" value="Submit Reply">
	</form>
</div>
