<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<style type="text/css">

</style>
<h2>Feedback</h2>
<c:if test="${fn:length(feedbacks) == 0}">
	<p>No feedback yet!</p>
</c:if>
<c:forEach items="${feedbacks}" var="feedback">

	<div id="feedbackInstance${feedback.id}" style="border:1px solid #BDD7E7;background:#F7FBFF;padding:5px;">
		<div style="background:#E7F2F7;padding:5px;">
			<span class="floatRight"><fmt:formatDate value="${feedback.createTime}" pattern="MM/dd/yy, hh:mm aaa"/></span>
			<strong>${feedback.user.loginname}</strong> (<a href="mailto:${feedback.user.email}">${feedback.user.email}</a>) said:
		</div>
		<div class="indentBox">
			<p>RE: <b>${feedback.action}</b></p>
			<p>${feedback.content}</p> 
		</div>
	</div>
		<br />
</c:forEach>
