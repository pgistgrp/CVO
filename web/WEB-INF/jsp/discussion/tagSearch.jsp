<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:choose>
	<c:when test='${fn:length(tags) != 0}'>
		<h3>Search Results: ${fn:length(tags)} tag(s)</h3>match your query.  Click on the tag below to set a new filter.<br>
		<c:forEach items="${tags}" var="tag">
			  <span class="tagSize1"><a href="javascript:getConcernsByTag(${tag.id});">${tag.name}</a></span>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<span class="closeBox"><a href="javascript:Effect.Fade('sidebarSearchResults', {duration: 0.5}); void(0);">Close</a></span><p>No tag matches found! Please try a different search.</p>
	</c:otherwise>
</c:choose>
