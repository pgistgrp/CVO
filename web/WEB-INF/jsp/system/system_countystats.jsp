<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


	<br/>
	<h3 class="headerColor">Counties</h3>
	<table id="newTable" border="0" cellpadding="3" cellspacing="0" class="center">
		<tr>
			<th class="col1">County</th>
			<th class="col2">Quota Statistics</th>
			<th class="col3">Zip Codes</th>
			<th class="col4">Actions</th>
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
					<td><span id="name${count}"><strong>${county.name}</strong>
							<input id="countyid${count}" type="hidden" value="${county.id}" /><input name="edit" type="button" 
							onclick="Element.toggle('editName${count}'),Element.toggle('name${count}')" 
							value="Edit" />
							</span>
							<form class="inline" action="javascript:saveCountyName('${count}');Element.toggle('editName${count}');Element.toggle('name${count}'); void(0);">
								<span id="editName${count}" style="display:none">
								<input type="text" id="namevalue${count}" value="${county.name}" size="3" />
								<input name="Save" type="submit" value="Save" />
								</span>
							</form>
						</td>
						<td>${county.tempQuotaNumber} of <span id="limit${count}">${county.quotaLimit}
								participants registered
							<input name="Edit" type="button" 
							onclick="Element.toggle('editlimit${count}'),Element.toggle('limit${count}')" 
							value="Edit" />
							</span>
							<form class="inline" action="javascript:saveQuotaLimit('${count}');Element.toggle('editlimit${count}');Element.toggle('limit${count}'); void(0);">
								<span id="editlimit${count}" style="display:none">
								<input type="text" id="limitvalue${count}" value="${county.quotaLimit}" size="3" />
								<input name="Save" type="submit" value="Save" />
								</span>
							</form>
						</td>
						<td>
							<c:forEach var="zip" items="${county.zipCodes}" varStatus="loop">
								<div align="left">&nbsp;${zip}<a href="javascript:deleteZip('${county.id}','${zip}')"> <img class="trash" src="images/btn_trash.gif" alt="Remove this ZIP code"/></a><br />
								</div>
							</c:forEach>
						</td>
						<td> <span id="actions${count}">
							<input name="addzip" type="button" value="Add Zip" onclick="Element.toggle('addzipcodebox${count}'),Element.toggle('actions${count}')" />
							<input name="delete" type="button" value="Delete County" onclick="deleteCounty('${county.id}')" />
							</span> <span id="addzipcodebox${count}" style="width:500px;border=1px solid #00000; display:none;">&nbsp;<img style="vertical-align:bottom;" alt="Close button" onclick="javascript:Element.toggle('addzipcodebox${count}'),Element.toggle('actions${count}');" title="Cancel" src="images/close.gif" />
							<form class="inline" action="javascript:addZipCodes('${county.id}','${count}');Element.toggle('addzipcodebox${count}');Element.toggle('actions${count}'); void(0);">
								<input id="zipcodesinput${count}" type="text" size="6" />
								<input name="addzip" type="submit" value="Add Zip" />
							</form>
							</span> </td>
					</tr>
					<c:set var="count" value="${count + 1}"/>
				</c:forEach>
			</c:otherwise>
		</c:choose>
				<tr>
					
					<td colspan="4" class="addNewBar">
						<form action="javascript:addCounty();">
							<h4 style="margin:3px 0px;display:inline;">Add a new county</h4>
							<input style="margin-left:20px;" id="countyname" type="text" size="20" />
							<input type="submit" value="Add" />
						</form>
					</td>
				</tr>
	</table>

<p>&nbsp;</p>
