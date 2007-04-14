<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div style="width:100%; text-align: left;">
  Decision Situation: ${workflow.situation.name}<br>
  Description: ${workflow.situation.description}<br>
  Begin Time: ${workflow.beginTime}<br>
  
  <table width="100%">
    <tr>
      <td>Running Activities:</td>
    </tr>
    <c:forEach var="meeting" items="${workflow.situation.context.pendingActivities}">
      <tr>
        <pg:narrow name="meeting"/>
        <td style="padding-left:20px;">Meeting: "${meeting.description}"</td>
      </tr>
      <c:forEach var="pmethod" items="${meeting.context.pendingActivities}">
        <pg:narrow name="pmethod"/>
        <tr>
          <td style="padding-left:40px;">PMethod: "${pmethod.description}"</td>
        </tr>
        <c:forEach var="pgame" items="${pmethod.context.runningActivities}">
          <tr>
            <td style="padding-left:60px;">PGame: "<a href="${pgame.link}">${pgame.description}</a>"</td>
          </tr>
        </c:forEach>
      </c:forEach>
    </c:forEach>
    
  </table>
  
</div>

