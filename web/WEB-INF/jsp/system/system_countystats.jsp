<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<br /><br />
<form name="quotas" action="" method="get">
<h3>Add New County</h3>
<strong>County Name:</strong> 
<input id="countyname" type="text" size="20" />
<input name="Add" type="button" onclick="addCounty()" value="Add" />
<br /><br />
<table border="0" cellpadding="3" cellspacing="0" class="center">
		<tr>
			<th>County</th>
			<th>Quota Statistics</th>
			<th>Zip Codes</th>
			<th>Actions</th>
		</tr>
<c:choose>	
	<c:when test="${fn:length(counties) == 0}">
		<tr>
			<td>No Counties Available</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>	
	</c:when>
	<c:otherwise>
		<c:set var="count" value="0"/>
		<c:forEach var="county" items="${counties}" varStatus="loop">
			<c:choose>
			<c:when test='${count%2==0}'>
				<tr class="rowfont">
			</c:when>
			<c:otherwise>
				<tr class="rowcolor rowfont">
			</c:otherwise>
			</c:choose>
			<td><strong>${county.name}</strong><input id="countyid${count}" type="hidden" value="${county.id}" /></td>
			
			<td>${county.tempQuotaNumber} of <span id="limit${count}">${county.quotaLimit}
						 participants registered <input name="Edit" type="button" onclick="Element.toggle('editlimit${count}'),Element.toggle('limit${count}')" value="Edit" /></span><span id="editlimit${count}" style="display:none"><input type="text" id="limitvalue${count}" value="${county.quotaLimit}" size="3" /> <input name="Save" type="button" onclick="saveQuotaLimit('${count}'),Element.toggle('editlimit${count}'),Element.toggle('limit${count}')" value="Save" /></span></td>
			<td>
			<c:forEach var="zip" items="${county.zipCodes}" varStatus="loop">
				<div align="left">&nbsp;<a href="javascript:deleteZip('${county.id}','${zip}')">[X]</a> ${zip}<br />
			        </div>
			</c:forEach>
			</td>
			<td>
			<span id="actions${count}">
			<input name="addzip" type="button" value="Add Zip" onclick="Element.toggle('addzipcodebox${count}'),Element.toggle('actions${count}')" /><input name="delete" type="button" value="Delete County" onclick="deleteCounty('${county.id}')" />
			</span>
			
			<span id="addzipcodebox${count}" style="width:500px;border=1px solid #00000; background-color:#CCCCCC; display:none;">&nbsp;<a href="javascript:Element.toggle('addzipcodebox${count}'),Element.toggle('actions${count}');">[X]</a> <input id="zipcodesinput${count}" type="text" size="6" /><input name="addzip" type="button" value="Add Zip" onclick="addZipCodes('${county.id}','${count}'),Element.toggle('addzipcodebox${count}'),Element.toggle('actions${count}')" />
			</span>
			
			</td>
		</tr>
		<c:set var="count" value="${count + 1}"/>
		</c:forEach>
	</c:otherwise>
</c:choose>

</table>

</form>	
<p>&nbsp;</p>