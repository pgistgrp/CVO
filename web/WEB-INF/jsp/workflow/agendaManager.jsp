<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:html>
<head>
<title>Let's Improve Transportation - Agenda Manager</title>

<!-- Site Wide JS -->
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/util.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>

<style type="text/css">
    @import "styles/lit.css";
</style>

</head>
<body>
<div id="container">
    <h1>Agenda Manager</h1>
    <form action="/agendaManager.do" method="POST">
    <input type="hidden" name="workflowId" value="${param.workflowId}" />
    <input type="hidden" name="save" value="true" />
    <c:forEach var="step" items="${activities}" varStatus="loop">
        <h2>Step: ${loop.index}</h2>
        <c:forEach var="activity" items="${step}" varStatus="loop2">
            <pg:narrow name="activity" />
            <input type="hidden" name="activity_id" value="${activity.id}" />
            <h3 class="headerColor">${activity.title}</h3>
            <p><label>Begin Time:</label><input type="text" name="${activity.id}_begin" value="${activity.beginTime}" /></p>
            <p><label>End Time:</label><input type="text" name="${activity.id}_end" value="${activity.endTime}" /></p>
        </c:forEach>
    </c:forEach>
    <input type="submit" value="submit"/>
    </form>
</div>
</body>
</html:html>
