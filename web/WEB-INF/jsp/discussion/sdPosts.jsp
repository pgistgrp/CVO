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
<logic:iterate id="post" name="posts">
<div class="disc_row_a">
		<div class="sidepadding">
		<div class="header_cat_title" ><a href="javascript:discussion.getPost(${post.id});">${post.title}</a></div><div class="header_cat_replies">5</div><div class="header_cat_author"><a href="#">${post.owner.loginname}</a></div><div class="header_cat_lastpost"><small><fmt:formatDate value="${post.createTime}" pattern="MM/dd/yy, hh:mm aaa"/> by: ${post.owner.loginname}</small></div><div class="clear"></div>
	</div>
</div>
</logic:iterate>
