<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<!-- begin RECENT DISCUSSIONS HEADER -->

<div>
	<div class="floatLeft clearfix"><a href="javascript:prevDiscussion();">Prev</a></div>
	<div class="floatRight clearfix"><a href="javascript:nextDiscussion();">Next</a></div>
</div>

<div class="clearBoth"></div>

<div class="listRow headingColor heading clearfix">
	<div class="profile-col1 floatLeft" style="margin-left:.2em">
		<div class="floatLeft">
			<h4>Topic</h4>
		</div>
	</div>
	<div class="profile-col2 floatRight">
		<h4>Last Post</h4>

	</div>
</div>
<!-- end RECENT DISCUSSIONS HEADER -->
<c:choose>	
<c:when test="${fn:length(discussions) == 0}">
	<p>This user has no discussions at this time.</p>
</c:when>
<c:otherwise>
	<c:set var="rowcount" value="0"/>
	<c:forEach var="discussion" items="${discussions}" varStatus="loop">
	
		<!-- begin A RECENT DISCUSSION -->
			<c:choose>
			<c:when test='${rowcount==0}'>
			<div class="listRow clearfix">
			<c:set var="rowcount" value="1"/>
			</c:when>
			<c:otherwise>
			<div class="listRow odd clearfix">
			<c:set var="rowcount" value="0"/>
			</c:otherwise>
			</c:choose>
		
			<div class="profile-col1 floatLeft">
				<div class="floatLeft">
					 <pg:url page="/sdThread.do" params="pid=${discussion.id}">${discussion.title}</pg:url><br />

					<span>Some how get the step</span>
				</div>
			</div>
			<div class="profile-col2 floatRight"><fmt:formatDate value="${discussion.createTime}" pattern="MM/dd" var="discussionDate" />${discussionDate}</div>
		</div>
		<!-- end A RECENT DISCUSSION -->

	</c:forEach>
</c:otherwise>
</c:choose>
<!-- begin A RECENT DISCUSSION -->

</div>