<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<pg:fragment type="html">
<!--
<div id="themeSelector">
		<form id="Tselector" name="ThemeSelector" method="post" action="">
		  <label>
		  Jump To:
		  <select name="selecttheme" id="selecttheme" onChange="javascript: infoStructure.getTargetPanes(this.value); infoStructure.assignTargetHeaders(this.innerValue);">		  
		    <option value = "-1">Select a Theme</option>
		    <logic:iterate id="infoObject" property="infoObjects" name="structure">
		        <option value ="${infoObject.id}">${infoObject.object.theme.title}</option>
		    </logic:iterate>		
	      </select>
		  </label>
		  </form>
 </div>
 -->

    <table width="100%" class="tabledisc">
          <tr class="objectblue">
            <td><a href="#">Concern Theme</a></td>
			<td width="150"><a href="#">Last Post</a></td>
            <td width="100" class="textcenter"><a href="#">Discussions</a></td> 
          </tr>		 

    <c:forEach var="infoObject" items="${structure.infoObjects}" varStatus="loop">
          <tr class="${((loop.index % 2) == 0) ? 'disc_row_a' : 'disc_row_b'}">
		  <!--<tr>-->
            <td  ><a href="/sdRoom.do?isid=${structure.id}&ioid=${infoObject.id}">${infoObject.object.theme.title}</a><br /><span class="smalltext">Discuss concerns related to ${infoObject.object.theme.title}</span></td>
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

<div id="structure_question_status" style="text-align: right;">
	<span class="smalltext">${structure.numAgree} of ${structure.numVote} participants have said that this list of concern themes adequately reflects concerns expressed by participants.</span><br />
</div>
<div id="structure_question" style="text-align: right;"></div>
</pg:fragment>

<pg:fragment type="script">
	
</pg:fragment>
