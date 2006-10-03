<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<table width="100%" border="0" cellspacing="0" class="tabledisc_green" >
	  <tr class="objectgreen">
	  
	  	<td width="40" class="textcenter">Status</td>
		<td>Topic Title</td>
		<td width="150" class="textcenter">Author</td>
		<td width="200">Last Post</td>
		<td width="100" class="textcenter">Replies</td>
		<td width="100" class="textcenter">Views</td>
	  </tr>

	<c:if test="${fn:length(posts) == 0}">
		<tr>
			<td colspan="6">
				<p>Currently there are no topics for this concern theme.  If you would like to create a topic, please click on the '<a href="javascript:new Effect.toggle('newDiscussion', 'blind', {duration: 0.5}); void(0);">new Topic </a>' above.</p>
				<br />
			</td>
		</tr>
	</c:if>
	
<jsp:useBean id="today" class="java.util.Date"/>
	<c:forEach var="post" items="${posts}" varStatus="loop">
		<c:set var="fmtLastPostDate"><fmt:formatDate value="${post.createTime}" pattern="yyyy/MM/dd"/></c:set>
    	<c:set var="fmtLastReplyDate"><fmt:formatDate value="${post.lastReply.createTime}" pattern="yyyy/MM/dd"/></c:set>
    	<c:set var="fmtToday"><fmt:formatDate value="${today}" pattern="yyyy/MM/dd"/></c:set>
		<tr class="${((loop.index % 2) == 0) ? 'disc_row_c' : 'disc_row_d'}">
		  
		 
		  <td width="40" class="textcenter">
		  <c:choose>
		  <c:when test="${fmtToday == fmtLastPostDate || fmtToday == fmtLastReplyDate }"><img src="/images/balloonactive2.gif" alt="Replies within the last 24 hours" /></c:when>
		  <c:otherwise><img src="/images/ballooninactive2.gif" alt="No replies within the last 24 hours" /></c:otherwise>
		  </c:choose>
		  </td>
		  
		  
			<td><a href="sdThread.do?isid=${structure.id}&pid=${post.id}&ioid=${object.id}&page=1">${post.title}</a><br /><span class="smalltext"  style="font-size: 80%; color: #98A072;">${fn:substring(post.content, 0, 125)}... </span></td>
			<td width="150" class="textcenter">${post.owner.loginname}</td>
			<td width="200">
			<span class="smalltext" style="font-size: 80%; color: #98A072;">

		    <c:choose>
		      <c:when test="${post.lastReply == null }">
		     		<a href="sdThread.do?isid=${structure.id}&pid=${post.id}&ioid=${object.id}&page=1">${post.title}</a><br />
		     		Posted on: <fmt:formatDate value="${post.createTime}" pattern="MM/dd/yy, hh:mm aaa"/> by: ${post.owner.loginname}
		      </c:when>
		
		      <c:otherwise>
		      		<a href="sdThread.do?isid=${structure.id}&pid=${post.id}&ioid=${object.id}&page=1#${post.lastReply.id}">${post.lastReply.title}</a><br />
		      		Posted on:  <fmt:formatDate value="${post.lastReply.createTime}" pattern="MM/dd/yy, hh:mm aaa"/> by: ${post.lastReply.owner.loginname}
		      </c:otherwise>
		    </c:choose>
				
			</span></td>
			<td width="100" class="textcenter">${post.replies}</td>	
			<td width="100" class="textcenter"><a href="sdThread.do?isid=${structure.id}&pid=${post.id}&ioid=${object.id}&page=1">${post.views}</a></td>
			<!--<tr class="${((loop.index % 2) == 0) ? 'disc_row_a' : 'disc_row_b'}">
				<td colspan="5">
					<small>${fn:substring(post.content, 0, 250)}... </small>
					
					Quick Preview Toggle Mode: Call with: javascript:closeAllContentsExcept(${post.id}, 'quickPostContents')  -- Removed for better interaction.
					<div id="quickPostContents${post.id}" class="quickPostContents" style="display: none;">
					<p><b>Preview: </b>${fn:substring(post.content, 0, 250)} [ <a href="sdThread.do?isid=${structure.id}&pid=${post.id}&ioid=${object.id}">more</a>... ]</p>
					<ul class="tagsList">
						<logic:iterate id="tag" name="post" property="tags">
							<li class="tagsList"><small>${tag.name}</small></li>
						</logic:iterate>
						<small>- click on a tag to view concerns with the same tag.</small>
					</ul>
					<p><small><a href="sdThread.do?isid=${structure.id}&pid=${post.id}&ioid=${object.id}">${post.replies} Replies - Participate in this Discussion</a></small></p>
					</div>
					
				</td>
			</tr>-->
		</tr>		
	</c:forEach>
</table>


	<c:if test="${setting.pageSize > 1}">
		<div class="pages">
				More Pages: 
				<c:if test="${setting.page > 1}">
					<span class="pages_nextprev"><a href="sdRoom.do?isid=${structure.id}&pid=${post.id}&ioid=${object.id}&page=${setting.page - 1}">&#171; prev page</a></span>
				</c:if>
								<ul>
									<c:forEach var="i" begin="1" end="${setting.pageSize}" step="1">
										    <c:choose>
										      <c:when test="${setting.page == i }">
										     		<li class="pages_current">${i}</li>
										      </c:when>
										      <c:otherwise>
										      		<li><a href="sdRoom.do?isid=${structure.id}&pid=${post.id}&ioid=${object.id}&page=${i}">${i}</a></li>
										      </c:otherwise>
										    </c:choose>
									</c:forEach>
								</ul>
				
				<logic:notEqual name="setting" property="page" value="${setting.pageSize}">	
					<span class="pages_nextprev"><a href="sdRoom.do?isid=${structure.id}&pid=${post.id}&ioid=${object.id}&page=${setting.page + 1}">next page &#187; </a></span>
				</logic:notEqual>
		</div>
	</c:if>
