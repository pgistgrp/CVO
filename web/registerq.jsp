<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
	<head>
	<title>Let's Improve Transportation - Registration</title>
	<!-- Site Wide CSS -->
<style type="text/css" media="screen">
@import "styles/lit.css";
@import "styles/registration-questionnaire.css";
</style>
<!-- End Site Wide CSS -->
<!-- Site Wide JS -->
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/SystemAgent.js'></script>
<script type='text/javascript' src='/dwr/interface/RegisterAgent.js'></script>

<script type='text/javascript'> 

function addVehicle() {
	
	var mpg = $F('vehicleMpg');
	var value = $F('vehicleValue');
	var mpy = $F('vehicleMpy');
	
	alert("milesPerGallon: " + mpg + " value: " + value + " milesPerYear: " + mpy); 
	RegisterAgent.addVehicle({milesPerGallon:mpg,value:value,milesPerYear:mpy}, {
		callback:function(data){
			if (data.successful){
				getVehicles();
				Form.reset('addVehicle');
			}else{
				alert(data.reason);
			}
		},
		errorHandler:function(errorString, exception){ 
		alert("RegisterAgent.addVehicle( error:" + errorString + exception);
		}
	});
}

function getVehicles(){
	RegisterAgent.createGetVehicles({}, {
		callback:function(data){
			if (data.successful){
				$('vehicles').innerHTML = data.html;
			}else{
				alert(data.reason);
			}
		},
		errorHandler:function(errorString, exception){ 
		alert("RegisterAgent.createGetVehicles( error:" + errorString + exception);
		}
	});
}
	
function toggleEditField(item, id) {
	Element.toggle(item + "Edit" + id);
	Element.toggle(item + id);
}

function editVehicle(vehicleId){
	var mpg = $F('vehicleMpg' + vehicleId);
	var value = $F('vehicleValue' + vehicleId);
	var mpy = $F('vehicleMpy'+ vehicleId);

	RegisterAgent.updateVehicle({vehicleId:vehicleId,milesPerGallon:mpg,value:value,milesPerYear:mpy}, {
		callback:function(data){
			if (data.successful){
				getVehicles();
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

	RegisterAgent.deleteVehicle({vehicleId:vehicleId}, {
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

function submitQ(form) {
	var errordiv = document.getElementById("errors");
	var errormsg = "";

	var income = form.income.value;	
	var householdsize = form.householdsize.value;
	var drive = form.drive.value;
	var carpool = form.carpool.value;
	var carpoolpeople = form.carpoolpeople.value;
	var bus = form.bus.value;
	var bike = form.bike.value;
	
	RegisterAgent.addQuestionnaire({income:income, householdsize:householdsize, drive:drive, carpool:carpool, carpoolpeople:carpoolpeople, bus:bus, bike:bike}, {
		callback:function(data){
			if (data.successful){
				window.location = "registercomplete.do";
			}else{
				alert(data.reason);
			}
		},
		errorHandler:function(errorString, exception){ 
		alert("RegisterAgent.addQuestionnaire( error:" + errorString + exception);
		}
	});
}

function getTolls() {
	RegisterAgent.getTolls({}, {
		callback:function(data){
			if (data.successful){
				var tolls = new Array();
				tolls = data.tolls;
				var tolloutput = "";
				for(var i = 0; i<tolls.length; i++) {
					tolloutput += "<input name=\"toll" + tolls[i].id + "\" id=\"" + tolls[i].id + "\" type=\"checkbox\" onchange=\"selectToll(" + tolls[i].id + ")\"/>" + tolls[i].name + "<br />";
				}
				document.getElementById('mytolls').innerHTML = tolloutput;
			}else{
				alert(data.reason);
			}
		},
		errorHandler:function(errorString, exception){ 
		alert("RegisterAgent.getTolls( error:" + errorString + exception);
		}
	});
}

function selectToll(tollid) {
	var boolvalue = new Boolean();
	boolvalue = document.getElementById(tollid).checked;
	if(boolvalue) {
		RegisterAgent.setToll({tollid:tollid, boolvalue:"true"});
	} else {
		RegisterAgent.setToll({tollid:tollid, boolvalue:"false"});
	}
}

</script>
</head>
<body>
	<!--[if IE]>
		<style type="text/css">
			fieldset p {padding-bottom:1px;}
		</style>
	<![endif]-->
	
	<div id="header">
		<!-- Begin header -->
		<jsp:include page="/header.jsp" />
		<!-- End header -->
	</div>
	<!-- End header -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu"> </div>
	<!-- End header menu -->
	
	<!-- #container is the container that wraps around all the main page content -->

	<div id="container">
		<h4 class="headerColor">Please answer a few questions about yourself</h4><p>The answers to these questions will help us later on in the process to estimate the costs of transportation improvements to you. All of this information will remain confidential. <em>You may choose not to respond to any of the questions on this page.</em></p>
		<!-- Begin calculator options -->
		<form name="questionnaire">
		<div id="myIncome">
			What is your approximate annual household income?
			<span id="annualIncome">
			<select id="income" style="margin-left:1em;">
				<option>$999,999 - $1,000,000</option>
				<option>Category 2</option>
				<option>Category 3</option>
				<option>Category 4</option>
				<option>Category 5</option>
			</select>
			</span>
			 </div>
		<div id="myHousehold">
			How many people live in your household?
			<span id="household"><select id="householdsize" style="margin-left:1em;">
				<option>1</option>
				<option>2</option>
				<option>3</option>
				<option>4</option>
				<option>5</option>
				<option>6 or more</option>				
			</select>
			</span>
			 </div>
		<div id="myVehicles">
			<p>How many vehicles are registered to your household?</p>
			<p><small>For each vehicle, please provide the following information:</small></p>
				<div id="vehicles">
					<img src="/images/indicator_arrows.gif" /> Loading your vehicles...
					<!-- vehicles rendered by separate jsp page -->
				</div>		
			</div>
		<div id="myCommute" class="peekaboobugfix">
			<p>Please provide the following information about your commute to and from work</p>
			<div id="myCommute-center" class="floatLeft"> I <strong>drive alone</strong>
				<select id="drive">
				  <option value="0">0</option>
				  <option value="1">1</option>
				  <option value="2">2</option>
				  <option value="3">3</option>
				  <option value="4">4</option>
				  <option value="5">5</option>
				  <option value="6">6</option>
				  <option value="7">7</option>
			    </select>

				days to work each week<br/>
				I <strong>carpool</strong>
				<select id="carpool">
				  <option value="0">0</option>
				  <option value="1">1</option>
				  <option value="2">2</option>
				  <option value="3">3</option>
				  <option value="4">4</option>
				  <option value="5">5</option>
				  <option value="6">6</option>
				  <option value="7">7</option>
			    </select>
				days to work each week with
				<select id="carpoolpeople">
				  <option value="0" selected="selected">0</option>
				  <option value="1">1</option>
				  <option value="2">2</option>
				  <option value="3">3</option>
				  <option value="4">4</option>
				  <option value="5">5</option>
				  <option value="6">6</option>
				  <option value="7">7+</option>
			                                    </select>
				people<br/>

				I <strong>ride the bus</strong>
				<select id="bus">
				  <option value="0">0</option>
				  <option value="1">1</option>
				  <option value="2">2</option>
				  <option value="3">3</option>
				  <option value="4">4</option>
				  <option value="5">5</option>
				  <option value="6">6</option>
				  <option value="7">7</option>
			    </select>
				days to work each week<br/>
				I <strong>bike</strong>
				<select id="bike">
				  <option value="0">0</option>
				  <option value="1">1</option>
				  <option value="2">2</option>
				  <option value="3">3</option>
				  <option value="4">4</option>
				  <option value="5">5</option>
				  <option value="6">6</option>
				  <option value="7">7</option>
			    </select>
				days to work each week<br/>
			</div>
			<div id="myCommute-right" class="floatLeft"> My daily commute includes:<br />
				<div id="mytolls">
				Loading
				</div>
			</div>
			<div class="clearBoth"></div>
		</div>
		<!-- End calculator options -->
			<fieldset>
				<legend>Please complete our detailed participant questionnaire</legend>
				<p>Please take some time to complete this questionnaire.  Throughout the Let’s Improve Transportation challenge, we will be asking you from time to time to let us know how the website is performing.  To access these questionnaires, you will be asked for your participant ID number.  You participant ID number is displayed below.  Please write this number down and keep until you have finished the Let’s Improve Transportation challenge.</p>
				<p>Your participant ID is: <code>3091</code></p>
				<input type="button" value="Go to participant questionnaire">
			</fieldset>
			
		<div id="step-bar" class="box5 padding5 clearfix">
				<p class="floatLeft" id="step-progress">Step 3 of 3</p>
				<p class="floatLeft" id="submit-description" style="width:450px;">When you are finished with the questionnaire Click “complete registration” get started on the Let’s Improve Transportation challenge!</p>
				<p class="floatRight" id="submit-button"><input type="button" value="Complete registration" style="font-size:1.2em;" onclick="submitQ(this.form)"/></p>
		</div>
		</form>
		</div>
	<script type="text/javascript">
		getVehicles();
		getTolls();
	</script>
	<!-- end container -->
	<!-- start feedback form -->
	<pg:feedback id="feedbackDiv" action="cctView.do"/>
	<!-- end feedback form -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu"> </div>
	<!-- End header menu -->
</body>
</html:html>
