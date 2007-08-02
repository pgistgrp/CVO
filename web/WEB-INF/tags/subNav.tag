<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>

<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu">
  <div id="headerContainer">
    <div id="headerTitle" class="floatLeft">
      <c:set var="current" value="${requestScope['org.pgist.wfengine.CURRENT']}" />
      <pg:narrow name="current"/>
      <h3 class="headerColor floatLeft" style="margin-top:0px;">${current.title}</h3>
    </div>

    <!-- HISTORIES -->
    <c:forEach var="history" items="${requestScope['org.pgist.wfengine.HISTORIES']}" varStatus="loop">
    <pg:narrow name="history"/>
    <c:if test="${history.access != 'moderator'}">
      <div class="headerButton floatLeft ${(requestScope['org.pgist.wfengine.CURRENT'] == history) ? "currentBox": ""}">
        <a href="/workflow.do?workflowId=${param.workflowId}&contextId=${param.contextId}&activityId=${history.id}">${history.title}</a>
      </div>
    </c:if>
    </c:forEach>
    <!-- END HISTORIES -->

    <!-- CURRENT -->
    <c:if test="${requestScope['org.pgist.wfengine.ACTIVITY_RUNNING']}">
        <div class="headerButton floatLeft currentBox">
            <a href="/workflow.do?workflowId=${param.workflowId}&contextId=${param.contextId}&activityId=${current.id}">${current.title}</a>
        </div>
    </c:if>
    <!-- END CURRENT -->
    
    <!-- FUTURES -->
    <c:forEach var="future" items="${requestScope['org.pgist.wfengine.FUTURES']}" varStatus="loop">
    <pg:narrow name="future"/>
    <c:if test="${future.access != 'moderator' && requestScope['org.pgist.wfengine.CURRENT'] != future}">
      <div class="headerButton floatLeft">
        ${future.title}
      </div>
    </c:if>
    </c:forEach>
    <!-- END FUTURES -->

  </div>
</div>
<!-- End header menu -->