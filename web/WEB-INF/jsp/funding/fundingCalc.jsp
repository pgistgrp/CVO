<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<!--####
	Project: Let's Improve Transportation!
	Page: Funding Calc
	Description: Tax Calculator Game - Users will be able to calculate how much funding sources will cost to them.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] Ensure get and set estimates are working correctly and pulling the right data (Jordan)
		[ ] Barebones JavaScript (Jordan)
		[x] Integrate Layout (Adam)
#### -->

<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
	<head>
	<title>Tax Calculator</title>
	<!-- Site Wide CSS -->
	<style type="text/css" media="screen">
		@import "styles/lit.css";
		@import "styles/table.css";
		@import "styles/taxCalculator.css";
	</style>
	<!-- End Site Wide CSS -->
	<!-- Site Wide JS -->
	<script src="scripts/prototype.js" type="text/javascript"></script>

	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<script src="scripts/search.js" type="text/javascript"></script>
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/util.js'></script>
	<script type='text/javascript' src='/dwr/interface/FundingAgent.js'></script>
	<script type="text/javascript" charset="utf-8">
		var suiteId = "${suiteId}"
		
		function getVehicles(){
			FundingAgent.getVehicles({}, {
				callback:function(data){
					if (data.successful){
						$('vehicles').innerHTML = data.html;
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("FundingAgent.getVehicles( error:" + errorString + exception);
				}
			});
		}
		
		function addVehicle(){
			var userId = "${user.userId}"
			var mpg = $F('vehicleMpg');
			var value = $F('vehicleValue');
			var mpy = $F('vehicleMpy');
			
			//alert("userId: " + userId + "milesPerGallon: " + milesPerGallon + " value: " + value + " milesPerYear: " + milesPerYear); 
			FundingAgent.addVehicle({userId:userId,milesPerGallon:mpg,value:value,milesPerYear:mpy}, {
				callback:function(data){
					if (data.successful){
						getVehicles();
						Form.reset('addVehicle');
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("FundingAgent.addVehicle( error:" + errorString + exception);
				}
			});
		}

		function updateVehicle(vehicleId){
			var mpg = $F('vehicleMpg');
			var value = $F('vehicleValue');
			var mpy = $F('vehicleMpy');
			
			//alert("vehicleId: " + vehicleId + " milesPerGallon: " + milesPerGallon + " value: " + value + " milesPerYear: " + milesPerYear); 
			FundingAgent.updateVehicle({vehicleId:vehicleId,milesPerGallon:mpg,value:value,milesPerYear:mpy}, {
				callback:function(data){
					if (data.successful){
						alert("update worked!")
						new Insertion.Bottom("vehicles", "test test");
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("FundingAgent.updateVehicle( error:" + errorString + exception);
				}
			});
		}

		function deleteVehicle(vehicleId){
			userId = "${user.userId}"
			//alert("userId: " + userId + " vehicleId: " + vehicleId); 
			FundingAgent.deleteVehicle({userId:userId,vehicleId:vehicleId}, {
				callback:function(data){
					if (data.successful){
						new Effect.DropOut('vehicle' + vehicleId)
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("ClassName.methodName( error:" + errorString + exception);
				}
			});
		}
		
		function renderProjectForm(project){
			//using ternery operators so it won't complain about the values when creating a new project :)
			id = (project) ? project.id : "";
			name = (project) ? project.name : "";
			description = (project) ? project.description : "";
			inclusive = (project) ? project.inclusive : "";
			inclusiveChecked = (inclusive == true) ? "CHECKED" : "";
			transMode = (project) ? project.transMode : "";
			transModes = ["null","road", "transit"];

			f = '';
			$("frmProject"+id).innerHTML = f;

		}
		
		function getUserById(userId){
			FundingAgent.lookupUserById({userId:userId}, {
				callback:function(data){
					if (data.successful){
						calcCommute(data.user);
					}else{
						return data.reason;
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("FundingAgent.getUserById( error:" + errorString + exception);
				}
			});
		}
		
		function calcCommute(user){
			//alert("user object: " + user)
			user.income = $F('annual-income');
			user.familyCount = $F('household-size');
			user.zipcode = $F('hZipcode');
			user.workZipcode = $F('wZipcode');
			user.driveDays = $F('drive-alone');
			user.carpoolDays = $F('carpool');
			user.carpoolPeople = $F('carpool-with');
			user.busDays = $F('bus');
			user.walkDays = $F('walk');
			user.bikeDays = $F('bike');
			
			//Grabbing checkboxes for toll.used
			selectedTolls = [];			
			myTolls = document.getElementsByName('myCommute');
			for(i=0; i<myTolls.length; i++){
				if(myTolls[i].checked){
					//grab just the toll id
					var start = myTolls[i].id.indexOf("-");
					var finish = myTolls[i].id.length;
					
					var tollId = myTolls[i].id.substring(start+1,finish);
					selectedTolls.push(tollId);
				}
			}
			
			for(i=0; i<user.tolls.length;i++){
				if(selectedTolls.include(user.tolls[i].id)){
					user.tolls[i].used = true;
				}
			}

			//for(i=0;i<user.tolls.length;i++){
			//	alert(user.tolls[i].used);
			//}


			//alert('income: ' +user.income+ ' familyCount: ' +user.familyCount+ ' zipcode: ' + user.zipcode + ' workzip: '+ user.workZipcode + ' driveDays: ' + user.driveDays + ' carpoolDays:' +user.carpoolDays+ ' carpoolPeeps: ' + user.carpoolPeople + ' bus days ' +user.carpoolPeople+' busDays:' + user.busDays+ ' walkDays: ' + user.walkDays + ' bikeDays: ' + user.bikeDays + ' userTolls: ' + user.tolls);
			FundingAgent.calcCommute(user, {
				callback:function(data){
					if (data.successful){
						//alert("data:" + data.user);
						
						//LOAD ESTIMATES
						$('gasCost').value = data.user.costPerGallon
						$('annualConsume').value = data.user.annualConsume
						
						//RENDER TOLL ESTIMATES
						//var tollTr = "<tr><td>111</td><td>222</td><td>333</td><td></td></tr>"
						//new Insertion.Bottom("tollRoads", tollTr)
						
						
						Element.show('estimates');
						Element.show('newTable')
						
						//RENDER REPORT
						calcCostReport(data.user);
					}else{
						alert("reason: " + data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("FundingAgent.setEstimates( error:" + errorString + exception);
				}
			});
		}
		
		function calcCostReport(user){
			//alert("suiteId: " + suiteId + " userCommute: " + user); 
			FundingAgent.calcCostReport(user,suiteId, {
				callback:function(data){
					if (data.successful){
						$('newTable').innerHTML = data.html;
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("FundingAgent.calcCostReport( error:" + errorString + exception);
				}
			});
		}
	</script>
	</head>
	<body>
	<!-- #container is the container that wraps around all the main page content -->

	<div id="container">
		<h3 class="headerColor">Calculating the annual cost to you</h3>
		<p>Feel free to change the information you previously provided in the text boxes
			below</p>
		<!-- Begin calculator options -->
		<div id="myIncome">
			<h3 class="headerColor">My income</h3>
			<span id="annualIncome"> Annual Income
			<input name="annual-income" id="annual-income" type="text" value="${user.income}"></span>
			<span id="householdSize"> Household size
				<select name="household-size" id="household-size" >
					<c:forEach var="i" begin="1" end="11">
						<option ${(user.familyCount == (i-1)) ? "SELECTED" : ""} value="${i-1}">${i-1}</option>
					</c:forEach>
				</select>
			</span>
			</div>
		<div id="myVehicles">
			<h3 class="headerColor">My Vehicle(s)</h3>
			<div id="vehicles">
				<!-- vehicles rendered by separate jsp page -->
			</div>
			<p><a href="javascript:Element.toggle('newVehicle');">Add vehicle</a> 
				<div id="newVehicle" class="myVehiclesRow" style="display:none;"> 
					<form id="addVehicle" action="javascript:addVehicle();">
					<strong>New Vehicle: </strong> Miles per gallon
					<input name="mpg" id="vehicleMpg" type="text" >
					Approximate value
					<input name="value" id="vehicleValue" type="text">
					Miles driven per year
					<input name="mpy" id="vehicleMpy" type="text">

					<input type="submit" value="Submit" /><small><a href="javascript:Element.toggle('newVehicle');">Cancel</a></small></div>
					</form>
				
		</div>
		<div id="myCommute">
			<h3 class="headerColor">My Commute</h3>
			<div id="myCommute-left" class="floatLeft">
			<p>Home zip code <input id="hZipcode" name="hZipcode" type="text" size="5" maxlength="5" value="${user.zipcode}"></span></p>
			<p>Work zip code <input id="wZipcode" name="wZipcode" type="text" size="5" maxlength="5" value="${user.workZipcode}"></span></p>
			</div>
			<div id="myCommute-center" class="floatLeft"> 
				I <strong>drive alone</strong>
				<select name="drive-alone" id="drive-alone">
					<c:forEach var="i" begin="1" end="8">
						<option ${(user.driveDays == (i-1)) ? "SELECTED" : ""} value="${i-1}">${i-1}</option>
					</c:forEach>
				</select>
				days to work each week<br/>
				
				I <strong>carpool</strong> to work
				<select name="carpool" id="carpool">
					<c:forEach var="i" begin="1" end="8">
						<option ${(user.carpoolDays == (i-1)) ? "SELECTED" : ""} value="${i-1}">${i-1}</option>
					</c:forEach>
				</select>
				days each week with
				<select name="carpool-with" id="carpool-with">
					<c:forEach var="i" begin="1" end="8">
						<option ${(user.carpoolPeople == (i-1)) ? "SELECTED" : ""} value="${i-1}">${i-1}</option>
					</c:forEach>
				</select>
				people<br/>

				I <strong>ride the bus</strong> to work
				<select name="bus" id="bus">
					<c:forEach var="i" begin="1" end="8">
						<option ${(user.busDays == (i-1)) ? "SELECTED" : ""} value="${i-1}">${i-1}</option>
					</c:forEach>
				</select>
				days each week<br/>
				
				I <strong>walk</strong> to work
				<select name="walk" id="walk">
					<c:forEach var="i" begin="1" end="8">
						<option ${(user.walkDays == (i-1)) ? "SELECTED" : ""} value="${i-1}">${i-1}</option>
					</c:forEach>
				</select>
				days each week<br/>
				
				I <strong>bike</strong>
				<select name="bike" id="bike">
					<c:forEach var="i" begin="1" end="8">
						<option ${(user.bikeDays == (i-1)) ? "SELECTED" : ""} value="${i-1}">${i-1}</option>
					</c:forEach>
				</select>
				days to work each week<br/>
			</div>
			<div id="myCommute-right" class="floatLeft"> My daily commute includes:<br />
				<c:forEach var="toll" items="${user.tolls}" varStatus="loop">
					<input name="myCommute" id="myCommute-${toll.id}" type="checkbox" />
					${toll.name}<br />
				</c:forEach>
			</div>
			
			<div class="clearboth">
				<input type="button" name="calcEstimates" value="Calculate my estimates" 
					style="clear:both;margin:1em;" class="floatRight" onClick="getUserById(${user.userId});">
			</div>
			<div class="clearBoth"></div>
		</div>

		<div id="estimates" style="display:none;">
			<h3 class="headerColor peekaboobugfix">Estimated use of toll roads, taxed parking
				facilities, and annual taxable consumption</h3>
			<p class="peekaboobugfix">Your estimates number of tolls has been estimated based
				on your home zip code, your usual commute mode of travel, and your commute route.
				You may change these estimates.</p>
		<div id="estimates-left">
			<table border="0" cellpadding="1" cellspacing="0" id="tollRoads">
				<tr>
					<th>&nbsp;</th>
					<th>Peak Hour Trips</th>
					<th>Off-peak trips</th>
				</tr>
				<tr>
					<td class="fundingSourceItem">Parking downtown</td>
					<td><input size="3" maxlength="3" type="text" ></td>
					<td><input size="3" maxlength="3" type="text" ></td>
				</tr>
				<tr>
					<td class="fundingSourceItem">Alaskan Way Viaduct</td>
					<td><input size="3" maxlength="3" type="text" ></td>
					<td><input size="3" maxlength="3" type="text" ></td>
				</tr>
				<tr>
					<td class="fundingSourceItem">I-405 North</td>
					<td><input size="3" maxlength="3" type="text" ></td>
					<td><input size="3" maxlength="3" type="text" ></td>
				</tr>
				<tr>
					<td class="fundingSourceItem">I-405 South</td>
					<td><input size="3" maxlength="3" type="text" ></td>
					<td><input size="3" maxlength="3" type="text" ></td>
				</tr>
				<tr>
					<td class="fundingSourceItem">SR 520 Floating Bridge</td>
					<td><input size="3" maxlength="3" type="text" ></td>
					<td><input size="3" maxlength="3" type="text" ></td>
				</tr>
				<tr>
					<td class="fundingSourceItem">I-90</td>
					<td><input size="3" maxlength="3" type="text" ></td>
					<td><input size="3" maxlength="3" type="text" ></td>
				</tr>
				<tr>
					<td class="fundingSourceItem">SR 167</td>
					<td><input size="3" maxlength="3" type="text" ></td>
					<td><input size="3" maxlength="3" type="text" ></td>
				</tr>
				<tr>
			</table>
		</div>
		<div id="estimates-right">
			<table>
				<tr>
					<td>Gas cost per gallon</td>
					<td><input name="gasCost" id="gasCost" size="5" type="text" value="${user.costPerGallon}"></td>
				</tr>
				<tr>
					<td>Annual consumption (sales tax)</td>
					<td><input name="annualConsume" id="annualConsume" size="5" type="text" value="${user.annualConsume}"></td>
				</tr>
			</table>
		</div>
		
			<div class="clearboth">

				<!--<input type="button" name="calcEstimates" value="Update Annual Cost Report" 
					style="clear:both;margin:1em;" class="floatRight">-->
			</div>
			<div class="clearBoth"></div>
		</div>
		<!-- End calculator options -->
		<!-- Note on zebra-striping by funding source: When creating rows of funding items (such as each alternative gas tax increase) wrap all rows in a TBODY tag, then do zebra-striping on those TBODYs, not on the individual TRs. --><br>
		
		<div id="newTable" style="display:none;">
			<!-- load via calcReport in JS -->
		</div>
	</div>

	<script type="text/javascript" charset="utf-8">
		getVehicles();
	</script>
	<!-- end container -->
	<!-- start feedback form -->
	<pg:feedback id="feedbackDiv" action="cctView.do"/>
	<!-- end feedback form -->
	</body>
</html:html>


