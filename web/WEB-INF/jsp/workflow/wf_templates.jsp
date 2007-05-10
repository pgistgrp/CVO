<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="templates">
	<table id="newTable" cellspacing="0" cellpadding="0">
		<tr>
			<th>Decision Situation Templates</th>
			<th>Description</th>
			<th>Operation</th>
		</tr>
		<c:forEach var="template" items="${templates}">
				<tr>
					<td>${template.name}</td>
					<td>${template.description}</td>
					<td><input type="button" value="Create Instance" onclick="javascript: workflow.createInstance(${template.id});"></td>
				</tr>
		</c:forEach>
	</table>
</div>
