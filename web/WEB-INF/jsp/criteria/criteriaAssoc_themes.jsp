<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!--####
	Project: Let's Improve Transportation!
	Page: Themes List
	Description: This is a partial page that is included via getThemes in criteriaMgr.
				 This page will loop through all of the themes in a given workflow instance.
	Author(s): Jordan Isip, Adam Hindman, Issac Yang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] Test if page is pulled by getThemes (Jordan)

#### -->

<c:forEach var="theme" items="${themes}" varStatus="loop">
	<input type="checkbox" name="theme-${theme.id}">${theme.name} [ <a href="javascript:deleteObjective(${objective.id})">delete</a> ]
</c:forEach>

