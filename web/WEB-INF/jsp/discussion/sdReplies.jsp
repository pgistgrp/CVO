<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="postReplies">
<c:if test="${fn:length(reply.tags) != 0}">
	<h4>Replies to ${post.title}</h4>
</c:if>
<logic:iterate id="reply" name="replies">
	<div id="reply${reply.id}" class="replies">
		 <div id="replies_title" class="bluetitle">
		 	<span class="padding-sides"><strong>${reply.title}</strong> - <small>Posted on <fmt:formatDate value="${reply.createTime}" pattern="MM/dd/yy, hh:mm aaa"/> by: ${reply.owner.loginname}</small></span>
		 </div>
		<div class="padding">
		${reply.content}<br />
		
	 <c:if test="${fn:length(reply.tags) != 0}">
	 	<br />
		<ul class="tagsList"><strong>tags: </strong>
			<logic:iterate id="tag" name="reply" property="tags">
				<li class="tagsList"><small>${tag.name}</small></li>
			</logic:iterate>
		<small>- click on a tag to view discussions with the same tag.</small>
	</c:if>
		</div>
	</div>
	<br />
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
<div id="newReply" class="greenBB" style="padding: 5px 10px; margin-top: 20px; border-top: 2px solid #C0D7F6">
	<h4>Post a Reply</h4>
	<form>
		<p><label>Post Title</label><br><input style="width:100%" type="text" value="Re: ${post.title} " id="txtnewReplyTitle"/></p>
		<p><label>Your Thoughts</label><br><textarea style="width:100%; height: 150px;" id="txtnewReply"></textarea></p>
		<p><label>Tag your post (comma separated)</label><br><input style="width:100%" id="newReplyTags" type="text" /></p>
		<input type="button" onClick="createReply(${setting.pageSize});" value="Submit Reply">
	</form>
</div>