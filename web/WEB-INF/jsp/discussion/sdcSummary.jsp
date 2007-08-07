<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<pg:fragment type="html">
	<pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">
	  <p>${infoObject.object.theme.summary}</p>
	</pg:termHighlight>

	<ul class="tagsInline">
		<li class="tagsInline"><strong>Tags highlighted by the moderator:</strong> </li>
		<c:forEach var="tag" items="${infoObject.object.theme.tags}">
			<li class="box8 tagsInline padding5">
			    <a href="javascript:io.getThemeConcerns(${tag.id});">${tag.name} <img src="images/external.png" alt="(new window)" /></a>
			</li>
		</c:forEach>
	</ul>
	<div style="clear: left;"></div>
	<p align="right">
	    <a href="javascript:io.getThemeConcerns();">View all Concerns for this Summary <img src="images/external.png" alt="(new window)" /></a>
	</p>
	
</pg:fragment>