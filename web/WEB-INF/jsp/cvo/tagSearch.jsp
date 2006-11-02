<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:choose>
	<c:when test='${fn:length(tags) != 0}'>
		<h3>Search Results: ${fn:length(tags)} tag(s)</h3>match your query.  Click on the tag below to set a new filter.<br>
		<c:forEach items="${tags}" var="tagRef">
			  <span class="tagSize${tagRef.fontSize}"><a href="javascript:changeCurrentFilter(${tagRef.id});">${tagRef.tag.name}</a></span>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<span class="closeBox"><a href="javascript:Effect.Fade('sidebarSearchResults', {duration: 0.5}); void(0);">Close</a></span><p>No tag matches found! Please try a different search.</p>
	</c:otherwise>
</c:choose>


