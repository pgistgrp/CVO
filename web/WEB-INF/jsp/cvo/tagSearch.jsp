<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


	<c:if test='${fn:length(tags) != 0}'>
	    <div class="closeBox floatRight clearBoth">
			<a href="javascript:Effect.Fade('searchResults', {duration: 0.5}); void(0);"><img src="images/close1.gif" /></a>
		</div>
		<h3>Search Results: ${fn:length(tags)} tag(s) match your query.</h3>Click on the tag below to set a new filter.<br /><p>
		<c:forEach items="${tags}" var="tagRef">
			  <span class="tagSize${tagRef.fontSize} box3" style="padding:2px;margin:2px 2px 0px 0px;"><a href="javascript:changeCurrentFilter(${tagRef.id}); setTimeout('Effect.BlindUp(\'searchResults\')',1000); void(0);">${tagRef.tag.name}</a></span>
		</c:forEach></p>
	</c:if>


