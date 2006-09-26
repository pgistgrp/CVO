<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<pg:fragment type="html">
			<c:if test="${fn:length(concerns) == 0}">
				<p>No concerns with your current filters could be found.  Try removing one of your filters.</p>
			</c:if>
			<logic:iterate id="concern" name="concerns">
				<p>${concern.content}</p>
				<c:forEach items="${concern.tags}" var="tagref">
					<span class="tags"><a href="javascript:changeCurrentFilter(${tagref.id});">${tagref.tag.name}</a> ${tagref.id}</span>
				</c:forEach>
			</logic:iterate>
</pg:fragment>

<pg:fragment type="script">
</pg:fragment>

