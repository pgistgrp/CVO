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
			<td width="45%"><a href="javascript:discussion.getPost(${post.id});">${post.title}</a></td>
			<td width="10%" class="textcenter">1</td>
			<td width="20%" class="textcenter"><a href="#">${post.owner.loginname}</a></td>
			<td width="25%"><small><fmt:formatDate value="${post.createTime}" pattern="MM/dd/yy, hh:mm aaa"/> by: ${post.owner.loginname}</small></td>
		</tr>		
	</logic:iterate>
</table>