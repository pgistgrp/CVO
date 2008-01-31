<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<logic:equal name="showTitle" value="true">
	<span class="closeBox">[ <a href="javascript:goPage(${setting.page});">Clear Filter</a> ]</span>
	<br><span class="title_section">Concerns about: </span>
		<span class="tagSize${tagRef.fontSize}"><a href="javascript:sideBar.changeCurrentFilter(${tagRef.id});">${tagRef.tag.name}</a></span>&nbsp;
	<p></p>
</logic:equal>

	<c:forEach var="concern" items="${concerns}" varStatus="loop">
			<div id="concernId${concern.id}" class="theConcern"><!--${((loop.index % 2) == 0) ? 'disc_row_a' : 'disc_row_a'}-->
						<logic:notEqual name="type" value="0">
							<small><strong>
		    					<pg:url page="/publicprofile.do" target="_blank" params="user=${concern.author.loginname}">${concern.author.loginname}</pg:url></strong>&nbsp;said:</small>
							<br>
						</logic:notEqual>
						<logic:equal name="type" value="0">
								<span class="concerns">
						</logic:equal>
						<logic:notEqual name="type" value="0">
							<span class="concerns">
						</logic:notEqual>
						"<bean:write name="concern" property="content" />"</span><br>
						

								<c:forEach items="${concern.tags}" var="tagref">
										<span class="tags"><a href="javascript:sideBar.changeCurrentFilter(${tagref.id});">${tagref.tag.name}</a></span>
								</c:forEach>

						<logic:equal name="type" value="0">
						<div id=actionMenu class="actionMenu"><strong>Actions:</strong> <a href="javascript:editConcernPopup(${concern.id});" rel="lightbox" class="lbOn">edit concern</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="javascript:editTagsPopup('${concern.id}');">edit tags</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="javascript:delConcern(${concern.id});">delete entire concern</a></div>
						<br>
						<div id="editConcernArea${concern.id}" style="display:none">Test</div>
						</logic:equal>
			</div>
	<p></p>
</c:forEach>

<logic:notEqual name="showTitle" value="true">
		<logic:notEqual name="showIcon" value="true">
			<p>
		  <div id="prevNext_container">
					
					<div id="next"><span class="textright">
						<logic:equal name="setting" property="page" value="${setting.pageSize}">
							<img src="images/btn_next_fade.gif" alt="No Additional Pages" />
						</logic:equal>
						
						<logic:notEqual name="setting" property="page" value="${setting.pageSize}">	
							<a href="javascript:sideBar.getSidebarItems(${setting.page}+1);"><img src="images/btn_next_a.gif" alt="Next" name="next" class="button" id="next" onMouseOver="MM_swapImage('next','','images/btn_next_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
						</logic:notEqual>
						</span>
					</div>
					
					
					<div id="previous">
						<logic:equal name="setting" property="page" value="1">
							<img src="images/btn_prev_fade.gif" alt="No Previous Pages" />
						</logic:equal>
						
						<logic:notEqual name="setting" property="page" value="1">	
							<a href="javascript:sideBar.getSidebarItems(${setting.page}-1);"><img src="images/btn_prev_a.gif" alt="Prev" name="prev" class="button" id="prev" onMouseOver="MM_swapImage('prev','','images/btn_prev_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
						</logic:notEqual>
						
					</div>

		  </div>
			</p>
		</logic:notEqual>
</logic:notEqual>

