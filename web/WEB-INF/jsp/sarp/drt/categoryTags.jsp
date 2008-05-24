<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>

<ul>
<c:forEach var="tag" items="${catRef.tags}" varStatus="loop">
  <pg:show condition="${user.id==baseuser.id && !cst.closed}">
	<li id="tag${tag.id}" style="list-style: none;">${tag.tag.name}&nbsp;[<a href="javascript:derelateTag(${catRef.id}, ${tag.id});">x</a>]</li>
  </pg:show>
  <pg:hide condition="${user.id==baseuser.id && !cst.closed}">
	<li id="tag${tag.id}" style="list-style: none;">${tag.tag.name}</li>
  </pg:hide>
</c:forEach>
</ul>
