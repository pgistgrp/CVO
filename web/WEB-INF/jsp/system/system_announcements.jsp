<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:choose>	
	<c:when test="${fn:length(announcements) == 0}">
		<p>No announcements at this time.</p>
	</c:when>
	<c:otherwise>
		<c:forEach var="announcement" items="${announcements}" varStatus="loop">
			<p id="announcement${announcement.id}">
			<fmt:formatDate value="${announcement.date}" dateStyle="full" var="annouceDate" />
			<strong>${annouceDate}</strong> 
			&nbsp;<span id="message${announcement.id}">${announcement.message}</span>
			<pg:show roles="moderator">
				<small>
					[ <a href="javascript:editAnnouncementPrep(${announcement.id});">Edit</a> ] 
					[ <a href="javascript:deleteAnnouncement(${announcement.id});">Delete</a> ]	
				</small>
			</pg:show>
			</p>
		</c:forEach>
	</c:otherwise>
</c:choose>

