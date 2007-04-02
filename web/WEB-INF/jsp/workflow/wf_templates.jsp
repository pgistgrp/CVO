<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table border="1" width="100%">
	<tr>
		<td>Decision Situation Templates</td>
		<td>Description</td>
    <td>Operation</td>
	</tr>
  <c:forEach var="template" items="${templates}">
      <tr>
        <td>${template.name}</td>
        <td>${template.description}</td>
        <td><a href="javascript: workflow.createInstance(${template.id});">Create Instance</a></td>
      </tr>
  </c:forEach>
</table>

