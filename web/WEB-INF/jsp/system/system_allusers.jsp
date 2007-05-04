<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<table id="newTable" border="0" cellpadding="3" cellspacing="0" class="center">
  <tr>
    <th>User Name </th>
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
		<td>No Users</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	</c:when>
	<c:otherwise>
	<c:set var="rowcount" value="0"/>
	<c:forEach var="user" items="${users}" varStatus="loop">
	  <c:choose>
	  	<c:when test='${rowcount==0}'>
	  	<tr class="rowfont">
		<c:set var="rowcount" value="1"/>
	  	</c:when>
		<c:otherwise>
		<tr class="rowcolor rowfont">
		<c:set var="rowcount" value="0"/>
		</c:otherwise>
		</c:choose>
		<td>${user.loginname}</td>
		<td>${user.email}</td>
		<td>
		<c:choose> 
			<c:when test='${user.quota == "true"}'>
				<input name="" type="checkbox" onchange="quota('${user.id}', 'false')" checked="checked" />
			</c:when>
			<c:otherwise>
				<input name="" type="checkbox"  onchange="quota('${user.id}', 'true')"/>
			</c:otherwise>
		</c:choose>
		</td>
		<td>${user.interview}</td>
		<td>${user.recording}</td>
		<td>${user.consented}</td>
		<td>
		<c:choose> 
			<c:when test='${user.enabled == "true"}'>
				<input name="Disable" id="disable" type="button" value="Disable" onclick="javascript:disableUsers('${user.id}');setTimeout('getAllUsers();',100);" />
			</c:when>
			<c:otherwise>
				<input name="Enable" id="enable" type="button" value="Enable" onclick="javascript:enableUsers('${user.id}');setTimeout('getAllUsers();',100)" />	
			</c:otherwise>
		</c:choose>
		<input name="Reset Password" type="button" value="Reset Password" onclick="javascript:resetPassword('${user.id}')" />

		</td>
	  </tr>
  	</c:forEach>
	</c:otherwise>
	</c:choose>
</table>
