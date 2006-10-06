<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<c:forEach items="${feedbacks}" var="feedback">

	<div id="feedbackInstance${feedback.id}" class="bluescheme">
		<div class="bluetitle">
			<span class="textright"><fmt:formatDate value="${feedback.createTime}" pattern="MM/dd/yy, hh:mm aaa"/></span>
			<strong>${feedback.user.loginname}</strong> (${feedback.user.email}) said:
		</div>
		<div class="indentBox">
			<p>RE: <b>${feedback.action}</b></p>
			<p>${feedback.content}</p> 
		</div>
	</div>
		<br />
</c:forEach>

