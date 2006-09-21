<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<pg:fragment type="html">
    <table width="100%" class="tabledisc">
          <tr class="objectblue">
          	<td width="40" class="textcenter">Status</td>
            <td>Concern Themes</td>
			<td width="150">Last Post</td>
            <td width="100" class="textcenter">Discussions</td> 
          </tr>		 
	<jsp:useBean id="today" class="java.util.Date"/>
    <c:forEach var="infoObject" items="${structure.infoObjects}" varStatus="loop">
    	<c:set var="fmtLastPostDate"><fmt:formatDate value="${infoObject.lastPost.createTime}" pattern="yyyy/MM/dd"/></c:set>
    	<c:set var="fmtToday"><fmt:formatDate value="${today}" pattern="yyyy/MM/dd"/></c:set>
    	
    	
          <tr class="${((loop.index % 2) == 0) ? 'disc_row_a' : 'disc_row_b'}">
		  <!--<tr>-->    
		  <c:choose>
		  <c:when test="${fmtToday == fmtLastPostDate}">
		  	 <td width="40" class="textcenter"><img src="/images/balloonactive2.gif" alt="Posts within the last 24 hours" /></td>
		  </c:when>
		  <c:otherwise>
		  	 <td width="40" class="textcenter"><img src="/images/ballooninactive2.gif" alt="No posts within the last 24 hours" /></td>
		  </c:otherwise>
		  </c:choose>
			

            <td><a href="/sdRoom.do?isid=${structure.id}&ioid=${infoObject.id}">${infoObject.object.theme.title}</a><br /><span class="smalltext">Discuss concerns related to ${infoObject.object.theme.title}</span></td>
			<td ><span class="smalltext" style="font-size: 80%;">
			
 		    <c:choose>
		      <c:when test="${infoObject.lastPost.id != null}">
		     		 <a href="/sdThread.do?isid=${structure.id}&pid=${infoObject.lastPost.id}&ioid=${infoObject.id}">${infoObject.lastPost.title}</a><br />
		     		Posted on: <fmt:formatDate value="${structure.lastPost.createTime}" pattern="MM/dd/yy, hh:mm aaa"/> by: ${infoObject.lastPost.owner.loginname}
		      </c:when>
		      <c:otherwise>
		      	No current discussions
		      </c:otherwise>
		    </c:choose>          
			</span></td>
            <td class="textcenter"><a href="/sdRoom.do?isid=${structure.id}&ioid=${infoObject.id}">${infoObject.numDiscussion}</a></td>
          </tr>		  
    </c:forEach>
	  
  </table>

<div id="structure_question_status">
	<span class="smalltext">${structure.numAgree} of ${structure.numVote} participants have said that this list of concern themes adequately reflects concerns expressed by participants.</span><br />
</div>
<div id="structure_question"></div>
</pg:fragment>

<pg:fragment type="script">
	
</pg:fragment>
