<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!--####
	Project: Let's Improve Transportation!
	Page: Recent Posts
	Description: This page serves as a partial to userHome.  This page serves up all recent posts for a given user.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Static HTML/CSS (Adam)
		[ ] Initial Skeleton Code (Jordan)
		[ ] Test and Refine (Jordan)

#### -->

<!-- get DATE object -->
<jsp:useBean id="now" class="java.util.Date"/>

<h3 class="headerColor">My recent discussions</h3>
<div class="clearBoth" style="margin-bottom:2em">
	<!-- pagination -->
	<div class="floatLeft clearfix">
		<c:choose>
			<c:when test="${setting.page == 1}">
				<img src="images/btn_prev_fade.gif" alt="No Previous Pages" />
			</c:when>
			<c:otherwise>
				<a href="javascript:getRecentPosts(${setting.page}-1);"><img src="images/btn_prev_a.gif" alt="Prev" name="prev" class="button" id="prev" onMouseOver="MM_swapImage('prev','','images/btn_prev_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
			</c:otherwise>
		</c:choose>
	</div>

	<div class="floatRight clearfix">
		<c:choose>
			<c:when test="${setting.page == setting.pageSize}">
				<img src="images/btn_next_fade.gif" alt="No Additional Pages" />
			</c:when>
			<c:otherwise>
				<a href="javascript:getRecentPosts(${setting.page}+1);"><img src="images/btn_next_a.gif" alt="Next" name="next" class="button" id="next" onMouseOver="MM_swapImage('next','','images/btn_next_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
			</c:otherwise>
		</c:choose>
	</div>
	<!-- end pagination-->
</div>
<div id="recent-discussions" class="box3">
	<!-- begin RECENT DISCUSSIONS HEADER -->
	<div class="listRow headingColor heading clearfix">
		<div class="home-col1 floatLeft" style="margin-left:.2em">
			<div class="floatLeft">
				<h4>Topic</h4>
			</div>
		</div>
		<div class="home-col2 floatRight">
			<h4>Last Post</h4>
		</div>
	</div>
	<!-- end RECENT DISCUSSIONS HEADER -->
	<!-- begin A RECENT DISCUSSION -->
	<c:forEach var="post" items="${user.recentPosts}" varStatus="loop">
		<div class="list clearfix">
			<div class="home-col1 floatLeft ${((loop.index % 2) == 0) ? '' : 'odd'}">
				<div class="floatLeft">
					<a href="sdRoom.do?isid=${post.isid}&ioid=${post.ioid}">${post.title}</a><br />
					<span>${post.step}</span>
				</div>
			</div>
			<div class="home-col2 floatRight"><fmt:formatDate value="${now}" type="DATE" pattern="MM/dd"/></div>
		</div>
	</c:forEach>
	<!-- end A RECENT DISCUSSION -->
</div>
