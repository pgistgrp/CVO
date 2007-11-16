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
@import "styles/registration-1.css";
@import "styles/registration-2a.css";
@import "styles/registration-2b.css";
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
		alert("FundingAgent.getVehicles( error:" + errorString + exception);
		}
	});
}

function addVehicle(){
	var mpg = $F('vehicleMpg');
	var value = $F('vehicleValue');
	var mpy = $F('vehicleMpy');
	
	//alert("userId: " + userId + "milesPerGallon: " + milesPerGallon + " value: " + value + " milesPerYear: " + milesPerYear); 
	RegisterAgent.addVehicle({milesPerGallon:mpg,value:value,milesPerYear:mpy}, {
		callback:function(data){
			if (data.successful){
				getVehicles();
				//Form.reset('addVehicle');
			}else{
				alert(data.reason);
			}
		},
		errorHandler:function(errorString, exception){ 
		alert("FundingAgent.addVehicle( error:" + errorString + exception);
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

	var firstname = form.fname.value;	
	var lastname = form.lname.value;
	var email1 = form.email1.value;
	var email2 = form.email2.value;
	var address1 = form.address1.value;
	var address2 = form.address2.value;
	var city = form.city.value;
	var state = form.state.value;
	var zip = form.zip.value;
	var username = form.username.value;
	var password1 = form.password1.value;
	var password2 = form.password2.value;
}
</script>
</head>
<body>

<!--[if IE]>
	<style type="text/css">
		fieldset p {padding-bottom:1px;}
	</style>
<![endif]-->

<!-- Begin the header - loaded from a separate file -->
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

	
<!-- start #regq -->
<div id="regq">

<h4 class="headerColor">Please answer a few questions about yourself</h4><p>The answers to these questions will help us later on in the process to estimate the costs of transportation improvements to you. All of this information will remain confidential. <em>You may choose not to respond to any of the questions on this page.</em></p>
	<!-- Begin calculator options -->
	<div id="errorsq" style="color:#FF0000;"></div>
	<form name="q">
	<div id="myIncome">
		What is your approximate annual household income?
		<span id="annualIncome">
		<select id="myAnnualIncome" style="margin-left:1em;">
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
		<span id="annualIncome">
		<select id="familysize" style="margin-left:1em;">
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
		<h3 class="headerColor">My Vehicle(s)</h3>
		<div id="vehicles">
			<img src="/images/indicator_arrows.gif" /> Loading your vehicles...
			<!-- vehicles rendered by separate jsp page -->
		</div>		
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
				<input name="myCommute" id="myCommute-${toll.id}" type="checkbox" ${(toll.used) ? "CHECKED": ""}/>
				${toll.name}<br />
			</c:forEach>
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
			<p class="floatRight" id="submit-button"><input type="button" value="Complete registration" style="font-size:1.2em;" onClick="submitQ()"/></p>
			</form>
	</div>
	
</div>
<!-- end #regq -->	
	
	
</div>
<!-- end container -->
<!-- start feedback form -->
<pg:feedback id="feedbackDiv" action="cctView.do"/>
<!-- end feedback form -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu"> </div>
<!-- Begin footer -->
<div id="footer">
	<jsp:include page="/footer.jsp" />
</div>
<!-- End footer -->

</body>
</html:html>
