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
<title>Criteria List</title>
<link rel="stylesheet" type="text/css" href="/styles/default.css'/>">
<script>
</script>
</head>

<body>
  <h2>Criteria List</h2>

  <table id="sdListTable" class="listtable" cellspacing="1" frame="box" rules="all" width="100%">
    <c:forEach var="cct" items="${ccts}">
    <tr>
      <td>
        <html:link action="/criteriaPublish.do?action=assoc" paramId="cctId" paramName="cct" paramProperty="id">
          <bean:write name="cct" property="name"/>
        </html:link>
      </td>
    </tr>
    </c:forEach>
  </table>

</body>
</html:html>

