<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

	<c:choose>
		<c:when test="${fn:length(concerns) == 0}">
				<p>No concerns with the current filters could be found.  Try removing a filter.</p>
		</c:when>
		<c:otherwise>
			<c:forEach var="concern" items="${concerns}" varStatus="loop">
					<div id="concernId${concern.id}" class="theConcern"><!--${((loop.index % 2) == 0) ? 'disc_row_a' : 'disc_row_a'}-->
									<small><strong><bean:write name="concern" property="author.loginname" /></strong>&nbsp;said:</small>
									<br>
									<span class="concerns">
										
										"${fn:substring(concern.content, 0, 150)}"
										<c:if test="${fn:length(concern.content) > 150}">
											 [...] 
										</c:if>
										
									</span><br>
					
									<c:forEach items="${concern.tags}" var="tagref">
											<span class="tags"><a href="javascript:changeCurrentFilter(${tagref.id});">${tagref.tag.name}</a></span>
									</c:forEach>
					</div>
			<p></p>
		</c:forEach>

		  <div id="prevNext_container">

					<div id="next"><span class="textright">
						<logic:equal name="setting" property="page" value="${setting.pageSize}">
							<img src="images/btn_next_fade.gif" alt="No Additional Pages" />
						</logic:equal>
						
						<logic:notEqual name="setting" property="page" value="${setting.pageSize}">	
							<a href="javascript:getContextConcerns(${setting.page}+1);"><img src="images/btn_next_a.gif" alt="Next" name="next" class="button" id="next" onMouseOver="MM_swapImage('next','','images/btn_next_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
						</logic:notEqual>
						</span>
					</div>
					
					
					<div id="previous">
						<logic:equal name="setting" property="page" value="1">
							<img src="images/btn_prev_fade.gif" alt="No Previous Pages" />
						</logic:equal>
						
						<logic:notEqual name="setting" property="page" value="1">	
							<a href="javascript:getContextConcerns(${setting.page}-1);"><img src="images/btn_prev_a.gif" alt="Prev" name="prev" class="button" id="prev" onMouseOver="MM_swapImage('prev','','images/btn_prev_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
						</logic:notEqual>
						
					</div>

		  </div>
		</c:otherwise>
	</c:choose>





