<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



		<!--
		<jsp:useBean id="today" class="java.util.Date"/>

		<c:set var="fmtLastPostDate"><fmt:formatDate value="${post.createTime}" pattern="yyyy/MM/dd"/></c:set>
    	<c:set var="fmtLastReplyDate"><fmt:formatDate value="${post.lastReply.createTime}" pattern="yyyy/MM/dd"/></c:set>
    	<c:set var="fmtToday"><fmt:formatDate value="${today}" pattern="yyyy/MM/dd"/></c:set>

-->
<div id="allDiscussionRows">

<c:choose>
	<c:when test="${fn:length(posts) <= 0}">
		<div class="box2 padding5 centerAlign"><h3 class="headerColor">There aren't any topics yet!</h3><p>Why not <a href="javascript:Effect.toggle('newDiscussion','blind',{duration: 0.2});">start the first discussion</a>?</div>
	</c:when>
	<c:otherwise>
	
		
		
		
		<c:forEach var="post" items="${posts}" varStatus="loop">
		
			<div class="discussionRow">
				<div class="discussion-left floatLeft">
					<div class="discussionRowHeader box1">
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
							 <c:choose>
				  <c:when test="${fmtToday == fmtLastPostDate || fmtToday == fmtLastReplyDate }">
					<img src="/images/balloonactive2.gif" alt="Replies within the last 24 hours" /></c:when>
				  <c:otherwise>
					<img src="/images/ballooninactive2.gif" alt="No replies within the last 24 hours" /></c:otherwise>
				  </c:choose>&nbsp;
							${post.replies} Replies
						</div>
						<div class="discussionTagsList">
							<c:forEach var="tag" items="${post.tags}">
								<a href="javascript:changeCurrentFilter(${tag.id});">${tag.name}</a>,&nbsp;
							</c:forEach>
						</div>
					</div>
				</div>
				<div class="discussion-right">
					<img src="images/icon_people_1.gif">
				</div>
			</div>
		
		
		<!-- End New Style -->
		</c:forEach>

	</c:otherwise>
		
</c:choose>
