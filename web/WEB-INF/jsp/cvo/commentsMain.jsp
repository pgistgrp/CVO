<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- Description: This page is the template for a single comment for concern comments -->
<html:html>
<div id="postReplies">
<c:if test="${fn:length(replies) != 0}">
	<h4 style="text-transform:capitalize;">${fn:length(replies)}
	<c:choose>
		<c:when test="${fn:length(replies) == 1}">
			comment
		</c:when>
		<c:otherwise>
			comments
		</c:otherwise>
	</c:choose>			
	 </h4>
</c:if>
<div id="filteredBy"></div>
<logic:iterate id="comment" name="replies">
			<div id="comment${comment.id}" class="discussionRow" style="margin-top: 5px;">
						<c:choose>
								<c:when test="${baseuser.id == comment.owner.id}">
									<div class="discussionRowHeader box6">			
								</c:when>
								<c:otherwise>
									<div class="discussionRowHeader box1">
								</c:otherwise>
						</c:choose>
						<div id="voting-post${comment.id}" class="discussionVoting">
							${comment.numAgree} of ${comment.numVote} participants agree with this post
							<c:choose>
								<c:when test="${comment.object == null}">
									<a href="javascript:io.setVote('comment',${comment.id}, 'false');"><img src="/images/btn_thumbsdown.png" alt="I disagree!" border="0"/></a> 
									<a href="javascript:io.setVote('comment',${comment.id}, 'true');"><img src="/images/btn_thumbsup.png" alt="I agree!" border="0"/></a>
								</c:when>
								<c:otherwise>
									<img src="images/btn_thumbsdown_off.png" alt="Disabled Button"/> <img src="images/btn_thumbsup_off.png" alt="Disabled Button"/>
								</c:otherwise>
							</c:choose>
						</div>
						<span class="discussionTitle">
							${comment.title}
					</div>
					
						<c:choose>
								<c:when test="${baseuser.id == comment.owner.id}">
									<div class="discussionBody box7 padding5">		
								</c:when>
								<c:otherwise>
									<div class="discussionBody padding5">
								</c:otherwise>
						</c:choose>
						<div id="discussionText${comment.id}" class="discussionText peekaboobugfix">
							
							<div id="commentContent${comment.id}"><p>${comment.content}</p></div>
							<div id="commentOwner${comment.id}"><h3>- ${comment.owner.loginname}</h3></div>
						</div>
						<div class="discussionComments peekaboobugfix">
							 <a href="javascript:io.setQuote(${comment.id});">Quote</a>
						</div>
						<c:if test="${fn:length(comment.tags) > 0}">
							<ul class="tagsInline">
								<li class="tagsInline"><strong>Tags:</strong> </li>
								<c:forEach var="tag" items="${comment.tags}">
									<c:choose>
										<c:when test="${baseuser.id == comment.owner.id}">
											<li class="box6 tagsInline">		
										</c:when>
										<c:otherwise>
											<li class="box8 tagsInline">
										</c:otherwise>
									</c:choose>
									<a href="javascript:io.changeCurrentFilter('${tag.name}');">${tag.name}</a></li>
								</c:forEach>
							</ul>
							<div style="clear: left;"></div>
						</c:if>
						<pg:show roles="moderator">
						<div class="smallText" style="text-align:right;">	
							Moderator Options: <input type="button" onClick="io.deletecomment(${comment.id});" value="Delete" />
						</div>
						</pg:show>
					</div>
			</div>
</div>
<div class="clearBoth"></div>
	
</logic:iterate>
<c:if test="${fn:length(replies) != 0}">
		  <div class="pagination">
		  				You are currently viewing page: ${setting.page} of ${setting.pageSize} &nbsp;
						<logic:equal name="setting" property="page" value="1">
							<img src="images/btn_prev_fade.gif" alt="No Previous Pages" />
						</logic:equal>
						<logic:notEqual name="setting" property="page" value="1">	
							<a href="javascript:goToPage('replies',${setting.page}-1);"><img src="images/btn_prev_a.gif" alt="Prev" name="prev" class="button" id="prev" onMouseOver="MM_swapImage('prev','','images/btn_prev_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
						</logic:notEqual>
						
						
						<logic:equal name="setting" property="page" value="${setting.pageSize}">
							<img src="images/btn_next_fade.gif" alt="No Additional Pages" />
						</logic:equal>
						<logic:notEqual name="setting" property="page" value="${setting.pageSize}">	
							<a href="javascript:goToPage('replies',${setting.page}+1)"><img src="images/btn_next_a.gif" alt="Next" name="next" class="button" id="next" onMouseOver="MM_swapImage('next','','images/btn_next_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
						</logic:notEqual>
		  </div>
</c:if>
</div>
<a name="commentAnchor"></a>
<div id="newcomment" class="box8 padding5" style="margin-top: 10px;">
	<h3 class="headerColor">Post a comment</h3>
	<form>
		<p><label>Post Title</label><br><input style="text-transform:capitalize;" maxlength=100 size=100 type="text" value="Re: ${post.title}" id="txtNewcommentTitle"/></p>
		<p><label>Your Thoughts</label><br><textarea style="width:100%; height: 150px;" id="txtnewcomment"></textarea></p>
		<p><label>Tag your post (comma separated)</label><br><input style="width:100%" id="txtNewcommentTags" type="text" /></p>
		<input type="button" onClick="io.createcomment();" value="Submit comment">
		<input type="button" onClick="javascript:resetNewcommentForm();Effect.toggle('newcomment','slide',{duration:1.5});" value="Cancel" />
	</form>
</div>
</html:html>
