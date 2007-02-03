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
	     Front End: Jordan Isip, Adam Hindman, Issac Yang
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] Add userAgent for vehicles
		[ ] Ensure get and set estimates are working correctly and pulling the right data (Jordan)
		[ ] Barebones JavaScript (Issac)
		[ ] Integrate Layout (Adam)
	Notes:
		- The action on this page will give: CCT, User, Tolls, UserCommute Objects
#### -->
<html:html>
<head>
<title>Tax Calculator</title>
<!-- Site Wide JavaScript -->
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->

<!-- Specific  Libraries-->
<script type='text/javascript' src='/dwr/interface/FundingAgent.js'></script>

	<script type="text/javascript" charset="utf-8">
		//Global Vars
			var cctId = "${cct.id}";
		//End Global Vars
		
		/* *************** based off of the user inputs, get the calculated estimates for the given user *************** */
		function getEstimates(){
			//Grab input variables from the form
			tolls = document.getElementsByName("tolls");
			tollsChecked = [];
			
			//Find all checked checkboxes and put all checkbox ids into tollsChecked Array
			for(i=0; i<tolls.length;i++){
				if(tolls[i].checked){
					tollsChecked.push(tolls[i].id);
				}
			}
			
			//Strip out fluff
			for(i=0; i<tollsChecked.length;i++){
				start = tollsChecked[i].indexOf('-') + 1;
				tollsChecked[i] = tollsChecked[i].substring(start,tollsChecked[i].length);
			}
			
			var tollIds = tollsChecked.toString();
			var zip = $F('zip');
			var driveDays = $F('daysAlone');
			var carpoolDays = $F('daysCarpool');
			var busDays = $F('daysBus');
			var walkDays = $F('daysWalk');
			var bikeDays = $F('daysBike');

			//alert("cctId: " + cctId + " tollIds: " + tollIds + " zip: " + zip + " driveDays: " + driveDays + " carpoolDays: " + carpoolDays + "busDays: "+ busDays + " walkDays: " + walkDays + " bikeDays: "+ bikeDays);
			FundingAgent.getEstimates({cctId:cctId,tollIds:tollIds,zip:zip,driveDays:driveDays,carpoolDays:carpoolDays,busDays:busDays,walkDays:walkDays,bikeDays,bikeDays}, {
				callback:function(data){
					if (data.successful){
						$('estimates').innerHTML = data.html; //data.source.html: fundingCalc_estimates.jsp
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("FundingAgent.getEstimates( error:" + errorString + exception);
				}
			});
		}
		
		function setEstimates(){
			//Grab estimated tolls
			eTolls = document.getElementsByName("eTolls");
			eTollsFormated = [];
			
			//Strip out fluff
			for(i=0; i<eTolls.length;i++){
				start = eTolls[i].id.indexOf('-') + 1;
				eTollId = eTolls[i].id.substring(start,eTolls[i].id.length);
				eTollValue = eTolls[i].value;
				eTollsFormated.push(eTollId +":"+eTollValue);
			}
			
			//alert(eTollsFormated.toString());
			//Grab input variables from the form
			var tolls = eTollsFormated.toString(); //string, comma separated id:value paris of tolls - get from estimates
			var zip = $F('zip');
			var driveDays = $F('daysAlone');
			var carpoolDays = $F('daysCarpool');
			var busDays = $F('daysBus');
			var walkDays = $F('daysWalk');
			var bikeDays = $F('daysBike');
			var annualConsume = $F('annualConsume');
			
			//alert("cctId: " + cctId + " tollIds: " + tollIds + " zip: " + zip + " driveDays: " + driveDays + " carpoolDays: " + carpoolDays + "busDays: "+ busDays + " walkDays: " + walkDays + " bikeDays: "+ bikeDays + " annualConsume: "+ annualConsume); 
			FundingAgent.setEstimates({cctId:cctId,tolls:tolls,zip:zip,driveDays:driveDays,carpoolDays:carpoolDays,busDays:busDays,walkDays:walkDays,bikeDays,bikeDays,annualConsume:annualConsume}, {
				callback:function(data){
					if (data.successful){
						$('report').innerHTML = data.source.html; //data.source.html: fundingCalc_estimates.jsp
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("FundingAgent.getEstimates( error:" + errorString + exception);
				}
			});
		}
	</script>


	<style type="text/css">

	</style>
	</head>
	<body>
		<a href="main.do">Back Home</a>
	
		<div id="income">
			<h3>My Income</h3>
			Annual Income $<input type="text" id="income" name="profile" value="${user.income}"/>.00
		</div>
		
		<div id="vehicles">
			<h3>My Vehicles</h3>
			<c:forEach var="vehicle" items="${user.vehicles}" varStatus="loop">
				Vehicle ${loop.index} [ edit ] [ delete ]
				<ul>
					<li>Miles Per Gallon: ${vehicle.milesPerGallon} </li>
					<li>Approximate Value: ${vehicle.approxValue}</li>
					<li>Miles Driven Per Year ${vehicle.milesPerYear}</li>
				</ul>
			</c:forEach>
			[ add vehicle ]
		</div>
		
		<div id="myCommute">
			<h3>My Commute</h3>
			<p>Home Zip Code: <input type="text" id="zip" name="profile" value="${user.zipcode}" /> days to work, every week</p>
			<p>I drive alone <input type="text" id="daysAlone" name="profile" value="${user.daysAlone}" /> days to work, every week</p>
			<p>I carpool <input type="text" id="daysCarpool" name="profile" value="${user.daysCarpool}" /> days to work, every week</p>
			<p>I ride the bus <input type="text" id="daysBus" name="profile" value="${user.daysBus}" /> days to work, every week</p>
			<p>I walk <input type="text" id="daysWalk" name="profile" value="${user.daysWalk}" /> days to work, every week</p>
			<p>I bike <input type="text" id="daysBike" name="profile" value="${user.daysBike}" /> days to work, every week</p>
			<p>My daily commute includes:</p>
			<ul>
				<c:forEach var="toll" items="${tolls}" varStatus="loop">
					<li><label><input type="checkbox" name="tolls" id="toll-${toll.id}"/>${toll.name}</label></li>
				</c:forEach>
			</ul>
			<p><input type="button" value="getEstimates();"/></p>
		</div>
		
		<div id="estimates">
			<!-- load estimates here via AJAX getEstimates(); fundingCalc_estimates.jsp-->
		</div>
		
		<div id="report">
			<!-- load report here via AJAX setEstimates(); fundingCalc_report.jsp-->
		</div>
	</body>
</html:html>

