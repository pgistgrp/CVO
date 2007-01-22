<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<!--####
	Project: Let's Improve Transportation!
	Page: Projects Partial
	Description: Partial page to get all funding sources
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman, Isaac Yang
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] loop through all funding sources

		
#### -->

<c:forEach var="source" items="${fundings}" varStatus="loop">
	<li><input type="checkbox" name="sourceId" value="${source.id}"/>${source.name} [ <a href="javascript:Effect.toggle('editsource${source.id}','blind');">edit</a> ] [ <html:link action="/sourceMgr.do?action=delete" paramId="id" paramName="source" paramProperty="id">delete</html:link> ]
		<ul>
			<li id="editsource${source.id}" style="display: none;"></li>
			<li>[ <a href="javascript:prepareAddAlternative(${source.id});Effect.toggle('addsourceAlt${source.id}','blind');">Add an Alternative</a> ]</li>
			<li id="addsourceAlt${source.id}" style="display:none;"></li>
				
			<c:forEach var="alternative" items="${source.alternative}" varStatus="loop">
				<li>${alternative.name} [ <a href="javascript: prepareEditFundingSourceAlt(${alternative.id}); Effect.toggle('editsourceAlt${alternative.id}');">edit</a> ] [ <a href="javascript:deleteAlternative(${alternative.id});">delete</a> ]</li>
				<li style="display:none;" id="editsourceAlt${alternative.id}"></li>
			</c:forEach>
		</ul>
	</li>
</c:forEach>