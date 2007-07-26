<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
<script src="scripts/calendar_date_select.js" type="text/javascript"></script>
<style type="text/css">
    @import "styles/calendar_date_select.css";
    @import "styles/lit.css";
</style>

</head>
<body>
<div id="container">
    <p><a href="userhome.do?workflowId=${param.workflowId}">Back to Moderator Control Panel</a></p>
    <h1>Agenda Manager</h1>
    <p>
       These dates will serve only as a guideline for participants.  They do not affect the flow of the system. 
    <p>
    <form action="/agendaManager.do" method="POST">
    <input type="hidden" name="workflowId" value="${param.workflowId}" />
    <input type="hidden" name="save" value="true" />
    <h2>Initialize Data</h2>
    <c:set var="stepCounter" value="1" />
    <c:forEach var="step" items="${activities}" varStatus="loop">
        <c:if test="${loop.index > 1}">
            <h2>Step: ${stepCounter}</h2>
            <c:set var="stepCounter" value="${stepCounter + 1}" />
        </c:if>
        <c:forEach var="activity" items="${step}" varStatus="loop2">
            <pg:narrow name="activity" />
            <input type="hidden" name="activity_id" value="${activity.id}" />
            <h3 class="headerColor">${activity.title}</h3>
            <p>
                <label>Begin Time:</label><input type="text"  id="${activity.id}_begin" name="${activity.id}_begin" value='<fmt:formatDate value="${activity.beginTime}" pattern="MM-dd-yyyy" />' /> 
                <img alt="Calendar" onclick="new CalendarDateSelect('${activity.id}_begin');" id="_${activity.id}_begin_link" src="/images/calendar.gif" style="cursor: pointer;" />
            </p>
            <p>
                <label>End Time:</label><input type="text"  name="${activity.id}_end" id="${activity.id}_end" value='<fmt:formatDate value="${activity.endTime}" pattern="MM-dd-yyyy" />' /> 
                <img alt="Calendar" onclick="new CalendarDateSelect('${activity.id}_end');" id="_${activity.id}_end_link" src="/images/calendar.gif" style="cursor: pointer;" />
            </p>
        </c:forEach>
    </c:forEach>
    <input type="submit" value="submit"/>
    </form>
</div>
</body>
</html:html>
