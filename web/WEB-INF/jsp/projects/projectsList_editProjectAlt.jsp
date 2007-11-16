<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!--####
	Project: Let's Improve Transportation!
	Page: Edit Project Alternative Form
	Description: Partial to edit project alternative
	Author(s): Jordan Isip, Adam Hindman, Issac Yang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] saveAlternative - for edit transfer real id for save transfer -1.
		[ ] Finish form add the rest of the fields from JavaDoc
#### -->
<c:choose>
	<c:when test="${alternative.id == -1}">
		<h2>Add Project Alternative</h2>
	</c:when>
	<c:otherwise>
		<h2>Edit Project Alternative</h2>
	</c:otherwise>
</c:choose>
<p>Project Alternative Name: <input name="alternative-${alternative.id}" type="text" value="${alternative.name}" /></p>
<p><input type="button" onclick="saveAlternative(${alternative.id}, $('alternative-${alternative.id}').value);" value="Submit!"></p>

