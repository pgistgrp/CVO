<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

  <!-- The discussionHeader sits on top of the discussion and contains the title of the
			discussion area, and the sorting menu -->
  <div id="discussionHeader">
    <div class="sectionTitle">
      <h3 class="headerColor floatLeft">Discussion about ${post.title}</h3> 
	  <div class="floatRight" id="toggleNotification">

	  </div>
    </div>
	
	<!--
    <div id="sortingMenu" class="box4" style="right:-270px;"> sort discussion by:
      <select>
        <option>Option</option>
        <option>Option Option Option Option Option</option>
        <option>Option</option>
        <option>Option</option>
        <option>Option</option>
      </select>
      <br />
      filter discussion by:
      <input type="text">
      or <a href="#">browse all tags</A> </div>-->
</div>
<!-- Begin Discussion Area -->
				<!-- Begin hidden "New topic" DIV -->
				<div style="width:680px;">
				<div id="newDiscussion" style="display: none">
					<div id="newdisc_title" >
						<div class="textright">
						</div>
						<h3 style="display: inline">New discussion topic</h3>
					</div> <!-- End newdisc_title -->
					<div id="newdisc_content" class="greenBB">
						<div id="newdisc_inner">
							<form>
								<p><label>Title</label><br><input maxlength=100 size=100 type="text" id="txtNewPostTitle"/></p>
								<p><br><textarea style="width:100%; height: 200px;" id="txtNewPost"></textarea></p>
								<p><label>Keywords (comma separated)</label><br><input style="width:100%" id="txtNewPostTags" type="text" /></p>
								<input type="button" onClick="io.createPost();" value="Create Discussion">
						
							</form>
						</div>
					</div>
				</div>
				</div>
					<!-- End hidden "new topic" DIV -->	
<!-- START Discussion Topic (Discussion Post) -->
<div id="discussion-topic">
				<div id="discussion${post.id}" class="discussionRow">
						<c:choose>
								<c:when test="${baseuser.id == post.owner.id}">
									<div class="discussionRowHeader box6">			
								</c:when>
								<c:otherwise>
									<div class="discussionRowHeader box1">
								</c:otherwise>
						</c:choose>
						<div id="voting-post${post.id}" class="discussionVoting">
							Do you agree with this comment? ${post.numAgree} of ${post.numVote} agree so far.
							<c:choose>
								<c:when test="${post.object == null}">
									<a href="javascript:io.setVote('post',${post.id}, 'false');"><img src="/images/btn_thumbsdown.png" alt="I disagree!" border="0"/></a> 
									<a href="javascript:io.setVote('post',${post.id}, 'true');"><img src="/images/btn_thumbsup.png" alt="I agree!" border="0"/></a>
								</c:when>
								<c:otherwise>
									<img src="images/btn_thumbsdown_off.png" alt="Disabled Button"/> <img src="images/btn_thumbsup_off.png" alt="Disabled Button"/>
								</c:otherwise>
							</c:choose>
						</div>
						<span class="discussionTitle">
							${post.title}
					</div>
					
						<c:choose>
								<c:when test="${baseuser.id == post.owner.id}">
									<div class="discussionBody box7">		
								</c:when>
								<c:otherwise>
									<div class="discussionBody">
								</c:otherwise>
						</c:choose>
						<div class="discussionText peekaboobugfix">
							<p>${post.content}</p>
							<h3>- <pg:url page="/publicprofile.do" target="_blank" params="user=${post.owner.loginname}">${post.owner.loginname}</pg:url></h3>
						</div>
						<div class="discussionComments">
						    <input type="button" alt="Reply" onclick="location.href='#replyAnchor'" name="Reply" class="button" value="Reply" id="Reply">
						</div>
						<c:if test="${fn:length(post.tags) > 0}">
							<ul class="tagsInline">
								<li class="tagsInline"><strong>Keywords:</strong> </li>
								<c:forEach var="tag" items="${post.tags}">
									<c:choose>
										<c:when test="${baseuser.id == post.owner.id}">
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
						<c:if test="${baseuser.id == post.owner.id}">		
							<div class="smallText" style="text-align:right;">
							<c:choose>
								<c:when test="${post.emailNotify}">
									<a href="javascript:io.setupEmailNotify(${post.id}, 'post', false)">Turn off e-mail notification for this discussion</a>
								</c:when>
								<c:otherwise>
									<a href="javascript:io.setupEmailNotify(${post.id}, 'post', true)">Turn on e-mail notification for this discussion</a>
								</c:otherwise>
							</c:choose>
							</div>
						</c:if>
					</div>
			</div>
</div>
<div class="clearBoth"></div>
<!-- END Discussion Topic (Discussion Post) -->
<div id="discussion" style="margin-left: 135px;">
  <!-- load discussion Replies -->

<div id="postReplies">
<c:if test="${fn:length(replies) != 0}">
	<h3 style="text-transform:none">${fn:length(replies)}
	<c:choose>
		<c:when test="${fn:length(replies) == 1}">
			reply
		</c:when>
		<c:otherwise>
			replies
		</c:otherwise>
	</c:choose>			
	 to this discussion topic</h3>
</c:if>
<div id="filteredBy"></div>
<logic:iterate id="reply" name="replies">
			<a name="replyAnchor${reply.id}" id="replyAnchor${reply.id}"></a>
			<div id="reply${reply.id}" class="discussionRow" style="margin-top: 5px;">
						<c:choose>
								<c:when test="${baseuser.id == reply.owner.id}">
									<div class="discussionRowHeader box6">			
								</c:when>
								<c:otherwise>
									<div class="discussionRowHeader box1">
								</c:otherwise>
						</c:choose>
						<div id="voting-reply${reply.id}" class="discussionVoting">
							${reply.numAgree} of ${reply.numVote} participants agree with this post
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
						<span class="discussionTitle">
							<a href="#replyAnchor${reply.id}">${reply.title}</a>
					</div>
					
						<c:choose>
								<c:when test="${baseuser.id == reply.owner.id}">
									<div class="discussionBody box7 padding5 peekaboobugfix">		
								</c:when>
								<c:otherwise>
									<div class="discussionBody padding5 peekaboobugfix">
								</c:otherwise>
						</c:choose>
						<div id="discussionText${reply.id}" class="discussionText peekaboobugfix">
							
							<div id="replyContent${reply.id}"><p>${reply.content}</p></div>
							<div id="replyOwner${reply.id}">
								<h3>- <pg:url page="/publicprofile.do" target="_blank" params="user=${post.owner.loginname}">${post.owner.loginname}</pg:url></h3>    
							</div>
						</div>
						<div class="discussionComments peekaboobugfix">
							 <a href="javascript:io.setQuote(${reply.id});">Quote</a>
						</div>
						<c:if test="${fn:length(reply.tags) > 0}">
							<ul class="tagsInline">
								<li class="tagsInline"><strong>Keywords:</strong> </li>
								<c:forEach var="tag" items="${reply.tags}">
									<c:choose>
										<c:when test="${baseuser.id == reply.owner.id}">
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
						
						
						<div class="smallText" style="text-align:right;">	
							<pg:show roles="moderator"><p>Moderator options: <input type="button" onClick="io.deleteReply(${reply.id});" value="Delete" /></p></pg:show>
						</div>
					</div>
		
</div>
<div class="clearBoth"></div>
	
</logic:iterate>
<!--
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
</c:if>-->
</div>
<a id="replyAnchor" name="replyAnchor"></a>
<div id="newReply" class="box8 padding5" style="margin-top: 10px;">
	<h3 class="headerColor">Your reply</h3>
	<form>
		<p><label>Title</label><br><input style="maxlength=100 size=50 type="text" value="Re: ${post.title}" id="txtNewReplyTitle"/></p>
		<p><label>Your Thoughts</label><br><textarea style="width:85%; height: 150px;" id="txtnewReply"></textarea></p>
		<p><label>Tag your post (comma separated)</label><br><input style="width:85%" id="txtNewReplyTags" type="text" /></p>
		<input type="button" onClick="io.createReply();" value="Submit Reply">
		<input type="button" onClick="io.cancelReply();" value="Cancel" />
		<input type="checkbox" id="newReplyNotifier" />E-mail me when someone responds to this discussion
	</form>
</div>

