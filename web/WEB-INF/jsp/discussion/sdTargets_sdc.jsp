<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<pg:fragment type="html">
	<jsp:useBean id="today" class="java.util.Date"/>
    <c:forEach var="infoObject" items="${structure.infoObjects}" varStatus="loop">
    	<c:set var="fmtLastPostDate"><fmt:formatDate value="${infoObject.discussion.lastPost.createTime}" pattern="yyyy/MM/dd"/></c:set>
    	<c:set var="fmtToday"><fmt:formatDate value="${today}" pattern="yyyy/MM/dd"/></c:set>

<div class="themeBox floatLeft">
			<h3 class="headerColor">
			    <pg:url page="/sdRoom.do" params="isid=${structure.id}&ioid=${infoObject.id}">${infoObject.object.theme.title}</pg:url>
			</h3>
			<!-- We set the substring to begin at the 5th character to remove an extra paragraph tag. It was 0-->
			<p>"${fn:substring(infoObject.object.theme.summary,5, 200)}"
				
			<span class="smallText"> ... <pg:url page="/sdRoom.do" params="isid=${structure.id}&ioid=${infoObject.id}">more</pg:url></span></p>
			<span class="smallText"><span id="topicCount">
			<c:choose>
			  <c:when test="${fmtToday == fmtLastPostDate}">
			  	<img src="/images/balloonactive2.gif" alt="Posts within the last 24 hours" class="floatLeft"/>
			  </c:when>
			  <c:otherwise>
			  	 <img src="/images/ballooninactive2.gif" alt="No posts within the last 24 hours" class="floatLeft"/>
			  </c:otherwise>
		  </c:choose>
				&nbsp;There are <strong>${infoObject.discussion.numPosts}</strong> topics in this theme
				<p><strong>Latest post</strong><br />
					<c:choose>
		      <c:when test="${infoObject.discussion.lastPost.id != null}">
		          "<pg:url page="/sdThread.do" params="isid=${structure.id}&pid=${infoObject.discussion.lastPost.id}&ioid=${infoObject.id}">${infoObject.discussion.lastPost.title}</pg:url>"
		      </c:when>
		      <c:otherwise>
		      	No current discussions
		      </c:otherwise>
		    </c:choose>     
				</p>
			</span>
		</span>
	</div>

<!-- End a theme concern box -->
			
						
         
    </c:forEach>


</pg:fragment>

<pg:fragment type="script">
	
</pg:fragment>


