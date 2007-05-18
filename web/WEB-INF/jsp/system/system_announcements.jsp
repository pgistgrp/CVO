<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:choose>	
	<c:when test="${fn:length(announcements) == 0}">
		<p>No Announcements at this time.</p>
	</c:when>
	<c:otherwise>
		<c:forEach var="announcement" items="${announcements}" varStatus="loop">
			<p id="announcement${announcement.id}">
			<fmt:formatDate value="${announcement.date}" pattern="MM/dd" var="annouceDate" />
			<strong>${annouceDate}</strong> 
			&nbsp;<span id="message${announcement.id}">${announcement.message}</span>
			<pg:show roles="moderator">
				<small>
					[ <a href="javascript:editAnnoucementPrep(${announcement.id});">edit</a> ] 
					[ <a href="javascript:deleteAnnouncement(${announcement.id});">delete</a> ]	
				</small>
			</pg:show>
			</p>
		</c:forEach>
	</c:otherwise>
</c:choose>

