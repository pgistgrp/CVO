<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<table id="newTable" border="0"cellpadding="3" cellspacing="0">
  <tr>
    <th class="col1">User Name </th>
    <th>Email Address</th>
    <th>Quota?</th>
    <th>Interview</th>
    <th>Recording</th>
    <th>Consented</th>
    <th>Actions</th>
  </tr>
<c:choose>	
	<c:when test="${fn:length(users) == 0}">
	<tr>
		<td class="col1">No Users</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	</c:when>
	<c:otherwise>
	<c:forEach var="user" items="${users}" varStatus="loop">
	  <tr>
		<td><pg:url page="/publicprofile.do" target="_blank" params="user=${user.loginname}">${user.loginname}</pg:url></td>
		<td>${user.email}</td>
		<td>
		<c:choose> 
			<c:when test='${user.quota == "true"}'>
				<input name="" type="checkbox" onchange="javascript:quota(${user.id}, false)" checked="checked" />
			</c:when>
			<c:otherwise>
				<input name="" type="checkbox"  onchange="javascript:quota(${user.id}, true)"/>
			</c:otherwise>
		</c:choose>
		</td>
		<td>${user.interview}</td>
		<td>${user.recording}</td>
		<td>${user.consented}</td>
		<td>
		<c:choose> 
			<c:when test='${user.enabled == "true"}'>
				<input name="Disable" type="button" value="Enable" onclick="javascript:enableUser(${user.id})" />
			</c:when>
			<c:otherwise>
				<input name="Disable" type="button" value="Disable" onclick="javascript:disableUser(${user.id})" />
			</c:otherwise>
		</c:choose>
		<input name="Reset Password" type="button" value="Reset Password" onclick="javascript:resetPassword(${user.id})" />
		</td>
	  </tr>
  	</c:forEach>
	</c:otherwise>
</table>
