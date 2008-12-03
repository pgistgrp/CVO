<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>

<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu">
  <div id="headerContainer">
    <div id="headerTitle" class="floatLeft">
      <c:set var="current" value="${requestScope['org.pgist.wfengine.CURRENT']}" />
      <pg:narrow name="current"/>
      
      <c:set var="meeting" value="${requestScope['org.pgist.wfengine.MEETING']}" />
      <pg:narrow name="meeting" />
      <h3 class="headerColor floatLeft" style="margin-top:0px;">${meeting.description}</h3>
    </div>

    <!-- PARALLEL -->

    <c:forEach var="serial" items="${requestScope['org.pgist.wfengine.SERIAL']}" varStatus="loop">
    <pg:narrow name="SERIAL"/>
    <c:set var="running" value="${pg:contains(requestScope['org.pgist.wfengine.CONTEXT'].runningActivities, serial)}" />
    <c:set var="histories" value="${pg:contains(requestScope['org.pgist.wfengine.HISTORIES'], serial)}" />
    
    <c:if test="${serial.access != 'moderator' && serial.access != 'expert'}">
      <div class="headerButton floatLeft ${(serial.id == current.id) ? 'currentBox' : ''}">
        <c:choose>
            <c:when test="${running || histories}">
                <a href="/workflow.do?workflowId=${param.workflowId}&contextId=${param.contextId}&activityId=${serial.id}">${serial.title}</a>
            </c:when>
            <c:otherwise>
                ${serial.title}
            </c:otherwise>
        </c:choose>
      </div>
    </c:if>
    </c:forEach>
    <!-- END PARALLEL -->

  <div id="headerNext" class="floatRight box5"> <pg:url page="/userhome.do">Back to Agenda</pg:url> </div>
  </div>
  


</div>
<!-- End header menu -->