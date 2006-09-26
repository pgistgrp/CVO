<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="/scripts/prototype.js" type="text/javascript"></script>
<script src="/scripts/scriptaculous.js?load=effects" type="text/javascript"></script>
<br>
<c:if test="${fn:length(comments) != 0}">
	<h3>${fn:length(comments)} Comment(s) on this Term</h3>

</c:if>

<logic:iterate id="comment" name="comments">
	<div id="aCommentCont" style="width:100%;">
		<div id="comment-attributes${comment.id}" style="background-color:#E9EFD3"><span class="participantName"><a href="#">${comment.owner.loginname}</a></span> on ${comment.createTime} added the following comment:</div>
		<div id="comment-content${comment.id}">${comment.content}</div>
		<div id="quote${comment.id}" style="text-align:right;"><a href="javascript: location.href='#newComment'; new Effect.Highlight('newComment'); $('newComment').value += '<blockquote><b>Quoting ${comment.owner.loginname} on ${comment.createTime}</b><br>'+ $('comment-content${comment.id}').innerHTML +'</blockquote>'; void(0);">Quote</a></div>
	</div>
	<br>
</logic:iterate>
