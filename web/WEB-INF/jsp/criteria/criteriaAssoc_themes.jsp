<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!--####
	Project: Let's Improve Transportation!
	Page: Themes List
	Description: This is a partial page that is included via getThemes in criteriaMgr.
				 This page will loop through all of the themes in a given workflow instance.
	Author(s): Jordan Isip, Adam Hindman, Isaac Yang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] Test if page is pulled by getThemes (Jordan)

#### -->
<c:choose>
	<c:when test="${fn:length(themes) == 0}">
		No themes could be found!
	</c:when>
	<c:otherwise>
		<ul style="float:left;" class="assocList">
		<c:forEach var="theme" items="${themes}" varStatus="loop">
				<li class="assocList"><input type="checkbox" name="themesGroup" id="theme-${theme.id}"> ${theme.title}</li>
		</c:forEach>
		</ul>
	</c:otherwise>
</c:choose>
