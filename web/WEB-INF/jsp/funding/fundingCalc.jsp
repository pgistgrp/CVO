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
		[x] Integrate Layout (Adam)
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

<!-- Site Wide CSS -->
<style type="text/css" media="screen">
@import "styles/lit.css";

#income,#vehicles,#myCommute,#estimates
{
padding:.5em;
padding-left:2em;
border:1px solid #89A3AF;
}

#income
{
background:#E6EDEF;
}

#vehicles
{
border-top:0px;
background:#D6E7EF;
}

#vehicles input {width:5em;}
.vehiclesRow {margin:.5em 0em;}

#myCommute
{
border-top:0px;
background:#E6EDEF;
}

#myCommute-left,#myCommute-center,#myCommute-right{margin-right:1em;}

#myCommute-left 
{
width:12em;
}

#myCommute-left input {width:3em}

#myCommute-center 
{
width:25em;
}

#myCommute-center input {width:1em;}

#myCommute-right 
{
}

#estimates
{
border-top:0px;
background:#D6E7EF;
}

#estimates-left
{

}

table#tollRoads {font-size:.9em;width:90%;}
#tollRoads td {padding:.1em .3em;}
#tollRoads input {width:2em;text-align:center;}

#estimates-center
{

}

#estimates-right
{

}

#costReport table 
{
width:100%;
margin-bottom:1.5em;
border:1px solid #D6E7EF;
padding:.2em;
}

#costReport th{font-weight:bold;padding:.2em;background:inherit;width:200px;text-align:left;}
#costReport th.fundingSourceItem{font-size:.8em;text-align:left;padding-left:.5em;}
#costReport tr.fundingType{font-size:1.2em;text-align:left;}

#costReport td
{
font-size:.8em;
padding:5px;
font-weight:normal;
}

#costReport tr{} 

.odd {background:#D6E7EF;}
.tableHeading {background:#ADCFDE;}
</style>
  <!-- End Site Wide CSS -->
  </head>
  <body>
  <!-- #container is the container that wraps around all the main page content -->
  <div id="container">
  	<h3 class="headerColor">Calculating the annual cost to you</h3>
	  <p>Feel free to change the information you previously provided in the text boxes below</p>
	  
<!-- Begin calculator options -->
	  <div id="income">
	  	<h3 class="headerColor">My income</h3>
			<span id="annualIncome">
				Annual Income $<input type="text" id="income" name="profile" value="${user.income}"/>.00
			</span>
	  </div>
	  <div id="vehicles">
		<h3 class="headerColor">My Vehicles</h3>
			<c:forEach var="vehicle" items="${user.vehicles}" varStatus="loop">
				<div class="vehiclesRow">
					<strong>Vehicle ${loop.index}</strong>[ edit ] [ delete ]
					Miles per gallon: <strong>${vehicle.milesPerGallon}</strong>
					Approximate value: <strong>${vehicle.approxValue}</strong>
					Miles driven per year: <strong>${vehicle.milesPerYear}</strong>
					<a href="#">Remove vehicle</a>
				</div>
			</c:forEach>
			
			<p><a href="#">Add vehicle</a>

	  </div>
	  <div id="myCommute">
	  	<h3 class="headerColor">My Commute</h3>
		<div id="myCommute-left" class="floatLeft">
			Home zip code <input name="profile" type="text" size="5" maxlength="5" value="${user.zipcode}"> 
		</div>
		<div id="myCommute-center" class="floatLeft">
			I drive alone <input id="daysAlone" size="1" maxlength="1" name="profile" type="text" value="${user.daysAlone}"> 
			days to work each week<br/>
			I carpool <input type="text" id="daysCarpool" name="profile" value="${user.daysCarpool}" size="1" maxlength="1"/> 
			days to work each week with <input size="1" maxlength="1" name="profile" type="text" value="${user.daysCarpoolPeople}"> people<br/>
			I ride the bus <input size="1" maxlength="1"type="text" id="daysBus" name="profile" value="${user.daysBus}" />
			 days to work each week<br/>
         I bike <input size="1" maxlength="1" type="text" id="daysBike" name="profile" value="${user.daysBike}" />
				  days to work each week<br/>
		</div>
		<div id="myCommute-right" class="floatLeft">
			My daily commute includes:<br />
				<c:forEach var="toll" items="${tolls}" varStatus="loop">
					<label><input type="checkbox" name="tolls" id="toll-${toll.id}"/>${toll.name}</label><br />
				</c:forEach>
		</div>
			<div class="clearboth">
				<input type="button" value="getEstimates();" 
					style="clear:both;margin:1em;" class="floatRight">
			</div>
		<div class="clearBoth"></div>
	  </div> 	
		
		<div id="estimates">
			<!-- load estimates here via AJAX getEstimates(); fundingCalc_estimates.jsp-->
		</div>
		
		<div id="report">
			<!-- load report here via AJAX setEstimates(); fundingCalc_report.jsp-->
		</div>
	</div>
	<!-- end container -->
	</body>
</html:html>

