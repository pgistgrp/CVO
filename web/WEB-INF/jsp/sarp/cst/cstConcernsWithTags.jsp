<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<p align="right">[ <a href="javascript:$('myTab').tabber.tabShow(0); void(0);">Back to all keywords</a> ]</p>
<br><span class="title_section">Viewing Keywords:</span>
<logic:iterate id="concern" name="concerns">
			<div id="concernId${concern.id}" class="theConcern">
						<logic:notEqual name="type" value="0">
							<span class="participantName"><b><a href="userProfile${concern.id}.jsp"><bean:write name="concern" property="author.loginname" /></a></b>&nbsp;said:</span>
							<br>
						</logic:notEqual>
						<logic:equal name="type" value="0">
								<span class="myConcerns">
						</logic:equal>
						<logic:notEqual name="type" value="0">
							<span class="concerns">
						</logic:notEqual>
						"<bean:write name="concern" property="content" />"</span><br>
						
						<logic:iterate id="tagref" property="tags" name="concern">
							<span class="tags">
							<a href="javascript:getConcerns(${tagref.id});">${tagref.tag.name}</a>
							</span>
						</logic:iterate>
						<logic:equal name="type" value="0">
						<div id=actionMenu class="actionMenu">Actions: <a href="javascript:editConcernPopup(${concern.id});">edit concern</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="javascript:editTagsPopup('${concern.id}');">edit keywords/phrases</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="javascript:delConcern(${concern.id});">delete entire concern</a></div>
						<br>
						</logic:equal>
			</div>
	<p></p>
</logic:iterate>

