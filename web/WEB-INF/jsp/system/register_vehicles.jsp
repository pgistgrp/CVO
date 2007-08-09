<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:forEach var="vehicle" items="${vehicles}" varStatus="loop">
	<div id="vehicle${vehicle.id}" class="myVehiclesRow"> 
		<strong>Vehicle ${loop.index + 1}: </strong>
		Miles per	gallon:	<strong>${vehicle.milesPerGallon}</strong>&nbsp;&nbsp;&nbsp;
		Approximate value: $<strong>${vehicle.approxValue}</strong>&nbsp;&nbsp;&nbsp;
		Miles driven per year: <strong>${vehicle.milesPerYear}</strong>
		<small><a href="javascript:toggleEditField('vehicle',${vehicle.id});">Edit</a> | <a href="javascript:deleteVehicle(${vehicle.id})">Remove</a></small> 
	</div>
	<div id="vehicleEdit${vehicle.id}" class="myVehiclesRow" style="display: none;"> 
		<strong>Vehicle ${loop.index + 1}: </strong> 
		Miles per gallon <input name="mpg" onBlur="this.value=filterNum(this.value);" id="vehicleMpg${vehicle.id}" type="text" value="${vehicle.milesPerGallon}" />
		Approximate value <input name="value" onBlur="this.value=filterNum(this.value);" class="moneyInput" id="vehicleValue${vehicle.id}" type="text" value="${vehicle.approxValue}" />
		Miles driven per year <input name="mpy" onBlur="this.value=filterNum(this.value);" id="vehicleMpy${vehicle.id}" type="text" value="${vehicle.milesPerYear}" />

		<input type="submit" onclick="editVehicle(${vehicle.id});" value="Update" /><small><a href="javascript:toggleEditField('vehicle',${vehicle.id});">Cancel</a></small>
	</div>
</c:forEach>

<p><a href="javascript:Element.toggle('newVehicle');void(0);">Add vehicle</a> 
	<div id="newVehicle" class="myVehiclesRow" style="display:none;"> 

		<strong>New Vehicle: </strong> Miles per gallon
		<input name="mpg" onBlur="this.value=filterNum(this.value);" id="vehicleMpg" type="text" />
		Approximate value
		<input name="value" class="moneyInput" onBlur="this.value=filterNum(this.value);" id="vehicleValue" type="text" />
		Miles driven per year
		<input name="mpy" onBlur="this.value=filterNum(this.value);" id="vehicleMpy" type="text" />

		<input type="submit" onClick="addVehicle();" value="Add" /><small> <a href="javascript:Element.toggle('newVehicle');">Cancel</a></small>

	</div>		
</p>