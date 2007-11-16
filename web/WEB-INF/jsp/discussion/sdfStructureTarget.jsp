<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!--####
	Project: Let's Improve Transportation!
	Page: Structured Discussion for Funding Sources
	Description: This page lists out all the available funding sources for the given decision situation.
	Author(s): Jordan Isip, Adam Hindman, Issac Yang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] Integrate Structured Discussion (Jordan)
		[ ] Integrate Layout (Adam)
		[ ] Integrate static text and link to static page - see mockup (Adam)
#### -->

<pg:fragment type="html">
	<h1>Overview and Instructions</h1>
	<p>These are the overview and instructions blah blah blah</p>
	
	<h1>Available Funding Options</h1>
	<c:forEach var="source" items="${sources}" varStatus="loop">
		${source.name}
			<uL>
			<c:forEach var="alternative" items="${source.alternatives}" varStatus="loop">
				<li>${alternative.name}</li>
				<li>${alternative.revenue}</li>
			</c:forEach>
			</ul>
	</c:forEach>



</pg:fragment>

<pg:fragment type="script">
	//All Javascript that is internal to this page must go here - not sdRoomMain.
	

</pg:fragment>