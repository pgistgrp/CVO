<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>

<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu">
  <div id="headerContainer">
    <div id="headerTitle" class="floatLeft">
      <c:set var="current" value="${requestScope['org.pgist.wfengine.CURRENT']}" />
      <pg:narrow name="current"/>
      <h3 class="headerColor floatLeft" style="margin-top:0px;">${current.description}</h3>
    </div>

    <!-- HISTORIES -->
    <c:forEach var="history" items="${requestScope['org.pgist.wfengine.HISTORIES']}" varStatus="loop">
    <pg:narrow name="history"/>
    <c:if test="${history.access != 'moderator' && requestScope['org.pgist.wfengine.CURRENT'] != history}">
      <div class="headerButton floatLeft">
        <a href="/workflow.do?workflowId=${param.workflowId}&contextId=${param.contextId}&activityId=${history.id}">${history.description}</a>
      </div>
    </c:if>
    </c:forEach>
    <!-- END HISTORIES -->

    <!-- CURRENT -->
    <div class="headerButton floatLeft currentBox">
        <a href="/workflow.do?workflowId=${param.workflowId}&contextId=${param.contextId}&activityId=${current.id}">${current.description}</a>
    </div>
    <!-- END CURRENT -->
    
    <!-- FUTURES -->
    <c:forEach var="future" items="${requestScope['org.pgist.wfengine.FUTURES']}" varStatus="loop">
    <pg:narrow name="future"/>
    <c:if test="${future.access != 'moderator' && requestScope['org.pgist.wfengine.CURRENT'] != future}">
      <div class="headerButton floatLeft">
        ${future.description}
      </div>
    </c:if>
    </c:forEach>
    <!-- END FUTURES -->
    
    <div id="headerNext" class="floatRight box5"><a href="/sdcWaiting.jsp">Next step</A></div>
  </div>
</div>
<!-- End header menu -->