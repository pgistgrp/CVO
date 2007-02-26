<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!--####
	Project: Let's Improve Transportation!
	Page: Popular Posts
	Description: This page serves as a partial to userHome.  This page serves up all popular posts across all discussions
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Static HTML/CSS (Adam)
		[ ] Initial Skeleton Code (Jordan)
		[ ] Test and Refine (Jordan)

#### -->


<h3 class="headerColor">Popular discussions</h3>
<div id="popular-discussions" class="box3">
	<!-- begin POPULAR DISCUSSIONS HEADER -->
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
	<!-- end POPULAR DISCUSSIONS HEADER -->
	<!-- begin A POPULAR DISCUSSION -->
	<c:forEach var="post" items="${system.popularPosts}" varStatus="loop">
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
	<!-- end A POPULAR DISCUSSION -->
<!-- end POPULAR DISCUSSIONS -->
</div>