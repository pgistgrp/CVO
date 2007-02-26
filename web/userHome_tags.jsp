<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!--####
	Project: Let's Improve Transportation!
	Page: User Tags
	Description: This page serves as a partial to userHome.  This page serves up all user tags in a cloud form.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Static HTML/CSS (Adam)
		[x] Initial Skeleton Code (Jordan)
		[ ] Test and Refine (Jordan)
#### -->


<h3 class="headerColor">My Keywords</h3>
<div class="clearBoth" style="margin-bottom:2em">
	<!-- pagination -->
	<div class="floatLeft clearfix">
		<c:choose>
			<c:when test="${setting.page == 1}">
				<img src="images/btn_prev_fade.gif" alt="No Previous Pages" />
			</c:when>
			<c:otherwise>
				<a href="javascript:getTags(${setting.page}-1);"><img src="images/btn_prev_a.gif" alt="Prev" name="prev" class="button" id="prev" onMouseOver="MM_swapImage('prev','','images/btn_prev_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
			</c:otherwise>
		</c:choose>
	</div>

	<div class="floatRight clearfix">
		<c:choose>
			<c:when test="${setting.page == setting.pageSize}">
				<img src="images/btn_next_fade.gif" alt="No Additional Pages" />
			</c:when>
			<c:otherwise>
				<a href="javascript:getTags(${setting.page}+1);"><img src="images/btn_next_a.gif" alt="Next" name="next" class="button" id="next" onMouseOver="MM_swapImage('next','','images/btn_next_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
			</c:otherwise>
		</c:choose>
	</div>
	<!-- end pagination-->
</div>		
<div id="keywords" class="clearfix">
	<ul>
		<c:forEach var="tag" items="${user.tags}" varStatus="loop">
			<li class="tagSize${tag.fontSize}">${tag.tag}</li>
		</c:forEach>
	</ul>
</div>
