<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${fn:length(posts) == 0}">
	<p>Currently there are no discussions for this concern theme.  If you would like to create a discussion, please click on the '<a href="javascript:new Effect.toggle('newDiscussion', 'blind', {duration: 0.5}); void(0);">new discussion</a>' above.</p>
</c:if>

<table width="95%" border="0" cellspacing="0">
	<logic:iterate id="post" name="posts">
		<tr class="disc_row_a">
			<td width="45%"><a href="javascript:closeAllContentsExcept(${post.id}, 'quickPostContents')">${post.title}</a></td>
			<td width="10%" class="textcenter">${post.replies}</td>
			<td width="10%" class="textcenter">${post.views}</td>	
			<td width="20%" class="textcenter"><a href="#">${post.owner.loginname}</a></td>
			<td width="15%"><small><fmt:formatDate value="${post.createTime}" pattern="MM/dd/yy, hh:mm aaa"/> by: ${post.owner.loginname}</small></td>
			<tr class="disc_row_a">
				<td colspan="5">
					<div id="quickPostContents${post.id}" class="quickPostContents" style="display: none;">
					<p><b>Preview: </b>${fn:substring(post.content, 0, 150)} [ <a href="sdThread.do?isid=${structure.id}&pid=${post.id}&ioid=${object.id}">more</a>... ]</p>
					<ul class="tagsList">
						<logic:iterate id="tag" name="post" property="tags">
							<li class="tagsList"><small>${tag.name}</small></li>
						</logic:iterate>
						<small>- click on a tag to view concerns with the same tag.</small>
					</ul>
					<p><small><a href="sdThread.do?isid=${structure.id}&pid=${post.id}&ioid=${object.id}">${post.replies} Replies - Participate in this Discussion</a></small></p>
					</div>
				</td>
			</tr>
		</tr>		
	</logic:iterate>
</table>