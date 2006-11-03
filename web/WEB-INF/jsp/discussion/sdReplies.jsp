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
									<div class="discussionBody box7">		
								</c:when>
								<c:otherwise>
									<div class="discussionBody">
								</c:otherwise>
						</c:choose>
						<div class="discussionText">
							<div id="replyContent${reply.id}"><p>${reply.content}</p></div>
							<h3>- ${reply.owner.loginname}</h3>
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
	
	<!--
	<div id="reply${reply.id}" class="replies">
		 <div id="replies_title" class="bluetitle">

		 	<div id="voting-reply${reply.id}" class="votingDisc">
			 	${reply.numAgree} of ${reply.numVote} participants agree with ${reply.owner.loginname} 

			 	<c:choose>
			 		<c:when test="${reply.object == null}">
						<a href="javascript:io.setVote('reply',${reply.id}, 'false');"><img src="/images/btn_thumbsdown.png" alt="I disagree!" border="0"/></a> 
			 			<a href="javascript:io.setVote('reply',${reply.id}, 'true');"><img src="/images/btn_thumbsup.png" alt="I agree!" border="0"/></a>
					</c:when>
					<c:otherwise>
						<img src="images/btn_thumbsdown_off.png" alt="Disabled Button"/> <img src="images/btn_thumbsup_off.png" alt="Disabled Button"/>
					</c:otherwise>
				</c:choose>

			</div>
		 	<span class="padding-sides"><strong style="text-transform:capitalize;">${reply.title}</strong> - <small>Posted on <fmt:formatDate value="${reply.createTime}" pattern="MM/dd/yy, hh:mm aaa"/> by: ${reply.owner.loginname}</small></span>

		 </div>
		<div class="padding">
		${reply.content}
		<br />
		
	 <c:if test="${fn:length(reply.tags) != 0}">
	 	<br />
		<ul class="tagsList"><strong>tags: </strong>
			<logic:iterate id="tag" name="reply" property="tags">
				<li class="tagsList"><small><a href="javascript:sideBar.changeCurrentFilter(${tag.id});">${tag.name}</a></small></li>
			</logic:iterate>
		<small>- click on a tag to view discussions with the same tag.</small>
	</c:if>
		</div>
	</div>
	<br />-->
</logic:iterate>


	
	
	<c:if test="${setting.pageSize > 1}">
		<div class="pages">
				More Pages: 
				<c:if test="${setting.page > 1}">
					<span class="pages_nextprev"><a href="sdThread.do?isid=${structure.id}&pid=${post.id}&ioid=${object.id}&page=${setting.page - 1}">&#171; prev page</a></span>
				</c:if>
				<ul>
					<c:forEach var="i" begin="1" end="${setting.pageSize}" step="1">
						    <c:choose>
						      <c:when test="${setting.page == i }">
						     		<li class="pages_current">${i}</li>
						      </c:when>
						      <c:otherwise>
						      		<li><a href="sdThread.do?isid=${structure.id}&pid=${post.id}&ioid=${object.id}&page=${i}">${i}</a></li>
						      </c:otherwise>
						    </c:choose>
					</c:forEach>
				</ul>
								
				<logic:notEqual name="setting" property="page" value="${setting.pageSize}">	
					<span class="pages_nextprev"><a href="sdThread.do?isid=${structure.id}&pid=${post.id}&ioid=${object.id}&page=${setting.page + 1}">next page &#187; </a></span>
				</logic:notEqual>
		</div>
	</c:if>

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
