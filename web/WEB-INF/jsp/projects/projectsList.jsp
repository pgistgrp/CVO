<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>Projects List</title>
<link rel="stylesheet" type="text/css" href="/styles/default.css'/>">
<script>
</script>
</head>

<body>
  <h2>Projects List</h2>
   <table id="prjListTable" class="listtable" cellspacing="1" frame="box" rules="all" width="100%">
    <c:forEach var="project" items="${projects}">
    <tr>
      <td>
      	 <bean:write name="project" property="name"/>
        [ <html:link action="/projectMgr.do?action=edit" paramId="id" paramName="project" paramProperty="id">edit</html:link> ]
        [ <html:link action="/projectMgr.do?action=delete" paramId="id" paramName="project" paramProperty="id">delete</html:link> ]
      </td>
    </tr>
    </c:forEach>
  </table>
		[ <html:link action="/projectMgr.do?id=-1">new project</html:link> ]
</body>
</html:html>

