<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<logic:notEqual name="showTitle" value="true">
		<logic:notEqual name="showIcon" value="true">
			<span class="title_section">Other Participant's Concerns</span>
			<br>
			<span class="highlight">To help you create your concerns</span>, below are examples of other participant concerns in random order.  Use the buttons below to view more pages of random concerns.
			<p>
				<div id="prevNext_container">
					
					<div id="previous"><span class="prevNext">
						<logic:equal name="setting" property="page" value="1">
							<a href="javascript:goPage(${setting.pageSize});">
						</logic:equal>
						
						<logic:notEqual name="setting" property="page" value="1">	
							<a href="javascript:goPage(${setting.page}-1);">
						</logic:notEqual>
						&#171; prev page</a></span>
					</div>

					<div id="next"><span class="prevNext">
						<logic:equal name="setting" property="page" value="${setting.pageSize}">
							<a href="javascript:goPage(1);">
						</logic:equal>
						
						<logic:notEqual name="setting" property="page" value="${setting.pageSize}">	
							<a href="javascript:goPage(${setting.page}+1);">
						</logic:notEqual>
						next page &#187; </a></span>
					</div>
				</div>
			</p>
		</logic:notEqual>
</logic:notEqual>

<logic:equal name="showTitle" value="true">
	<p align="right"><a href="javascript:getRandomConcerns();">Back to Random Concerns</a></p>
	<span class="title_section">Tag: <bean:write name="tagRef" property="tag.name" /></span>
	<p>Displaying all concerns with the tag: <bean:write name="tagRef" property="tag.name" /></p>
</logic:equal>

<logic:iterate id="concern" name="concerns">
			<div id="concernId${concern.id}">
						<logic:notEqual name="type" value="0">
							<span class="participantName"><b><bean:write name="concern" property="author.firstname" /></b>&nbsp;said:</span>
							<br>
						</logic:notEqual>
						<logic:equal name="type" value="0">
								<span class="myConcerns">
						</logic:equal>
						<logic:notEqual name="type" value="0">
							<span class="concerns">
						</logic:notEqual>
						"<bean:write name="concern" property="content" />"</span><br>
						<span class="tags"><strong>Tags</strong>:
						<logic:iterate id="tagref" property="tags" name="concern">
							<a href="javascript:getConcernsByTag(${tagref.id});">${tagref.tag.name}</a>&nbsp;
							<logic:equal name="showIcon" value="true">
								<a href="javascript:getConcernsByTag(${tagref.id});"><img src="/images/switchToBar.gif" title="View concerns that use this tag" border="0"></a>
							</logic:equal>|&nbsp;
						</logic:iterate></span>
						<logic:equal name="type" value="0">
						<br
						<div id=actionMenu>Actions: <a href="edit">edit concern</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="edit">edit tags</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="edit">delete entire concern</a></div>
						<br>
						</logic:equal>
			</div>
	<p></p>
</logic:iterate>

<logic:equal name="showTitle" value="true">
	<p align="right"><a href="javascript:getRandomConcerns();">Back to Random Concerns</a></p>
</logic:equal>

<logic:notEqual name="showTitle" value="true">
		<logic:notEqual name="showIcon" value="true">
			<p>
				<div id="prevNext_container">
					
					<div id="previous"><span class="prevNext">
						<logic:equal name="setting" property="page" value="1">
							<a href="javascript:goPage(${setting.pageSize});">
						</logic:equal>
						
						<logic:notEqual name="setting" property="page" value="1">	
							<a href="javascript:goPage(${setting.page}-1);">
						</logic:notEqual>
						&#171; prev page</a></span>
					</div>

					<div id="next"><span class="prevNext">
						<logic:equal name="setting" property="page" value="${setting.pageSize}">
							<a href="javascript:goPage(1);">
						</logic:equal>
						
						<logic:notEqual name="setting" property="page" value="${setting.pageSize}">	
							<a href="javascript:goPage(${setting.page}+1);">
						</logic:notEqual>
						next page &#187; </a></span>
					</div>
				</div>
			</p>
		</logic:notEqual>
</logic:notEqual>