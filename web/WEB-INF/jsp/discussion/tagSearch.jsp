<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<ul class="tagsList">
	<c:forEach items="${tags}" var="tag">
			<li class="tagsList"><a href="javascript:changeCurrentFilter(${tag.id});">${tag.name}</a></li>
	</c:forEach>
</ul>