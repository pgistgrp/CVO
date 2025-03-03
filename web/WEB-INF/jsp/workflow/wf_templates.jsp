<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h4>Create a New Experiment</h4>
<form id="newExp" action="javascript:workflow.createInstance('newExp');">
	<p>
		<label for="name">Name</label>
		<input name="name" id="expName" type="text">
	</p>
	<p>
		<label for="description">Description</label>
		<input name="description" id="expDesc" type="text">
	</p>
	<p><br/>Please select the workflow template you would like to use for this experiment</p>
	<ul>
		<c:forEach var="template" items="${templates}" varStatus="loop">
			<li><label><input type="radio" ${(loop.index == 0) ? "CHECKED" : ""} name="expTemplates" value="${template.id}"> ${template.name} - ${template.description}</label></li>
		</c:forEach>
	</ul>
	<input type="submit" class="padding5" value="Submit" /> 
	<input type="button" class="padding5" value="Cancel"
	onclick="javascript:Element.toggle('newExpiriment');void(0);" />
</form>