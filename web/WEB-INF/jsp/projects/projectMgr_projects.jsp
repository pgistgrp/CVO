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
	Description: Partial page to get all projects
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman, Issac Yang
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] loop through all projects

		
#### -->

<c:forEach var="project" items="${projects}">
	<li>${project.name} [ <a href="javascript:Effect.toggle('editProject${project.id}','blind');">edit</a> ] [ <a href="javascript:deleteProject(${project.id}">delete</a> ]
		<div id="editProject${project.id}"></div>
		<ul>
			<c:forEach var="alternative" items="${project.alternatives}">
				<li>${alternative.name} [ <a href="javascript: editAlternative(${alternative.id});">edit</a> ] [ <a href="javascript:deleteProjectAlt(${alternative.id});">delete</a> ]</li>
			</c:forEach>
			<li>[ <a href="javascript:prepareCreateProjectAlt(${project.id});">Add an Alternative</a> ]</li>
		</ul>
		<div id="newAlternativeForm${project.id}"></div>
	</li>
</c:forEach>

<li>[ <a href="javascript:Element.toggle('newProjectForm');">Add a Project</a> ]</li>
