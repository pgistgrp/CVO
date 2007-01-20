<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!--####
	Project: Let's Improve Transportation!
	Page: Criteria List
	Description: This is a temporary page to list out all the cct instances of criteria manager. 
				 This page will be replaced by the workflow manager.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman, Issac Yang
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] Loop through all ccts to provide a link to a criteriaMgr instance
#### -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8">
		<title>Criteria List</title>
	</head>
	<body>
		<h2>CCT List for Criteria</h2>
		<table id="cctListTable" class="listtable" cellspacing="1" frame="box" rules="all" width="100%">
		  <c:forEach var="cct" items="${ccts}" varStatus="loop">
		  	  <tr>
			    <td><html:link action="/criteriaMgr.do" paramId="cctId" paramName="cct" paramProperty="id"><bean:write name="cct" property="name"/>: Review Criteria</html:link></td>
				<td><html:link action="/criteriaWeigh.do" paramId="cctId" paramName="cct" paramProperty="id"><bean:write name="cct" property="name"/>: Weigh Criteria</html:link></td>
			  </tr>
		  </c:forEach>
		</table>
	</body>
</html>

