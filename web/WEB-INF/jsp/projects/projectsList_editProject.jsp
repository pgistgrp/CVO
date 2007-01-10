<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!--####
	Project: Let's Improve Transportation!
	Page: Edit Project Form
	Description: Partial to edit project
	Author(s): Jordan Isip, Adam Hindman, Issac Yang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] saveProject - for edit transfer real id for save transfer -1.
#### -->
<c:choose>
	<c:when test="${project.id == -1}">
		<h2>Add Project</h2>
	</c:when>
	<c:otherwise>
		<h2>Edit Project</h2>
	</c:otherwise>
</c:choose>
<p>Project Name: <input name="project-${project.id}" type="text" value="${project.name}" /></p>
<p><input type="button" onclick="saveProject(${project.id}, $('project-${project.id}').value);" value="Submit!"></p>

