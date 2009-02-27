<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<c:set var="errorusers" value="0"/>
<c:set var="totalusers" value="0"/>
<c:set var="quotausers" value="0"/>
<c:set var="nonquotausers" value="0"/>

<table id="newTable" border="0" cellpadding="3" cellspacing="0" class="center">
  <tr>
    <th title="The login name">Login  Name </th>
    <th title="The e-mail address given by the user">Name</th>
    <th title="The e-mail address given by the user">Email Address</th>
    <th title="User qualifies for payment">Quota?</th>
    <th title="Agreed to an interview">Interview</th>
    <th title="Agreed to undergo recorded (screencast) session(s)">Recording</th>
    <th title="Agreed to consent form">Consented</th>
    <th>WebQ Id </th>
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
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	</c:when>
	<c:otherwise>
		
	<c:set var="rowcount" value="0"/>
	<c:forEach var="user" items="${users}" varStatus="loop">
		<!-- user stats -->
		<c:if test='${fn:length(user.roles) < 2}'>
		<c:set var="totalusers" value="${totalusers+1}"/>
		</c:if>
		
		<c:if test='${user.quota == "true" && fn:length(user.roles) < 2}'>
		<c:set var="quotausers" value="${quotausers+1}"/>
		</c:if>
		
		<c:if test='${user.quota == "false" && fn:length(user.roles) < 2}'>
		<c:set var="nonquotausers" value="${nonquotausers+1}"/>
		</c:if>
		
	  <c:choose>

	  	<c:when test='${rowcount==0}'>
		
		<c:choose>	
		<c:when test='${user.quota == "true" && user.consented == "Non-Quota"}'>
			<tr bgcolor="#FFBA53">
			<c:set var="errorusers" value="${errorusers+1}"/>
		</c:when>
		<c:otherwise>
			<tr class="rowfont">		</c:otherwise>
		</c:choose>	
		
		<c:set var="rowcount" value="1"/>
	  	</c:when>
		<c:otherwise>
		
		<c:choose>	
		<c:when test='${user.quota == "true" && user.consented == "Non-Quota"}'>
			<tr bgcolor="#FFBA53">
			<c:set var="errorusers" value="${errorusers+1}"/>
		</c:when>
		<c:otherwise>
			<tr class="rowcolor rowfont">		</c:otherwise>
		</c:choose>	
		
		<c:set var="rowcount" value="0"/>
		</c:otherwise>
		</c:choose>
		<td><pg:url page="/publicprofile.do" target="_blank" params="userId=${user.id}">${user.loginname}</pg:url></td>
		  <td>${user.firstname} ${user.lastname} </td>
		  <td>${user.email}</td>
		<td>
		<c:choose> 
			<c:when test='${user.quota == "true"}'>
				<input name="" type="checkbox" onchange="quota('${user.id}', 'false')" checked="checked" />
			</c:when>
			<c:otherwise>
				<input name="" type="checkbox"  onchange="quota('${user.id}', 'true')"/>
			</c:otherwise>
		</c:choose>		</td>
		<td>${user.interview}</td>
		<td>${user.recording}</td>
		<td>${user.consented}</td>
		<td>${user.webQ.value}</td>
		<td>
		<c:choose> 
			<c:when test='${user.enabled == "true"}'>
				<input name="Disable" id="disable" type="button" value="Disable" onclick="javascript:disableUsers('${user.id}');setTimeout('getAllUsers();',100);" />
			</c:when>
			<c:otherwise>
				<input name="Enable" id="enable" class="disabled" type="button" value="Enable" onclick="javascript:enableUsers('${user.id}');setTimeout('getAllUsers();',100);" />	
			</c:otherwise>
		</c:choose>
		<input name="Reset Password" type="button" value="Reset Password" onclick="javascript:resetPassword('${user.id}')" />
		<input name="Delete" type="button" value="Delete" onclick="javascript:deleteUser('${user.id}');setTimeout('getAllUsers();',100);" />		  </td>
	  </tr>
  	</c:forEach>
	</c:otherwise>
  </c:choose>
</table>
<p align="center">
<c:if test="${totalusers > 0}">
<strong>Total Users:</strong> ${totalusers}  &nbsp;
<strong>Quota Users:</strong> ${quotausers} (<fmt:formatNumber type="percent">${quotausers / totalusers}</fmt:formatNumber>)  &nbsp;
<strong>Non-Quota Users:</strong> ${nonquotausers} (<fmt:formatNumber type="percent">${nonquotausers / totalusers}</fmt:formatNumber>)&nbsp;
<strong>Error Users:</strong> ${errorusers} (<fmt:formatNumber type="percent">${errorusers / totalusers}</fmt:formatNumber>)
</c:if>
</p>
