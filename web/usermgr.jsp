<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
	<pg:show roles="moderator">
		<head>
		<title>Let's Improve Transportation - User Management</title>
		<!-- Site Wide CSS -->
		<style type="text/css" media="screen">
@import "styles/lit.css";
@import "styles/table.css";

body {font-size:12pt}
.center {
	text-align:center;
}
.rowcolor {
	background-color: #ECF2F4;
} 
.rowfont {
	font-size: .9em;
	vertical-align:text-top;
}

th {padding:5px;}

.col1 {width:200px;}
table {width:100%;padding:5px;border:1px solid #ADCFDE;}
td div {text-align:center}
img.trash {width:20px;height:20px;vertical-align:middle;}

.addNewBar {
background:#D5EAEF;
text-align:left;
border-top:5px solid #ADCFDE;
padding:15px 10px 10px 10px;
}

.leftMargin {margin-left:20px;}
#newTable {margin-top:0em;}
#emailList form {margin:5px 0px;}
.leftMargin span {vertical-align:-30%;}

td.counties{width:100px;}
td.zipcodes{width:200px;}
div.zipcode{float:left;}
</style>
		<!-- Site Wide JS -->
		<script src="scripts/prototype.js" type="text/javascript"></script>
		<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
		<script src="scripts/search.js" type="text/javascript"></script>
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
		<script type='text/javascript' src='/dwr/interface/SystemAgent.js'></script>
<script type="text/javascript" charset="utf-8">
window.onLoad = doOnLoad();
		
function doOnLoad(){
	getAllUsers();
	getQuotaStats();
}

function getAllUsers() {
	SystemAgent.getAllUsers({}, {
		callback:function(data){
			if (data.successful){
				$('userList').innerHTML = data.html;
				greyDisabled();
			}else{
				$('userList').innerHTML = "<b>Error in SystemAgent.getAllUsers Method: </b>" + data.reason; 
			}
		},
		errorHandler:function(errorString, exception){ 
		alert("SystemAgent.getAllUsers( error:" + errorString + exception);
		}
	});
}

function getQuotaStats() {
	SystemAgent.createQuotaStats({}, {
		callback:function(data){
			if (data.successful){
				$('quota').innerHTML = data.html;
				
			}else{
				$('quota').innerHTML = "<b>Error in SystemAgent.createQuotaStats Method: </b>" + data.reason; 
			}
		},
		errorHandler:function(errorString, exception){ 
		alert("SystemAgent.createQuotaStats( error:" + errorString + exception);
		}
	});
}

function getAllCounties() {
		SystemAgent.getAllCounties({}, {
		callback:function(data){
			if (data.successful){
				$('allcounties').innerHTML = data.html;
				
			}else{
				$('allcounties').innerHTML = "<b>Error in SystemAgent.getAllCounties Method: </b>" + data.reason; 
			}
		},
		errorHandler:function(errorString, exception){ 
		alert("SystemAgent.getAllCounties( error:" + errorString + exception);
		}
	});
}

function disableUsers(myid) {
	SystemAgent.disableUsers({ids:myid});
}

function enableUsers(myId) {
	SystemAgent.enableUsers({ids:myId});
}

function getEmailList(mychoice) {
	var quota = "";
	var nonquota = "";
	if(mychoice=="quota") {
		quota="true";
		nonquota ="false";
	} else if (mychoice == "nonquota") {
		quota ="false";
		nonquota="true";
	}
	
	SystemAgent.getEmailList({quota:quota, nonquota:nonquota}, {
		callback:function(data){
			if (data.successful){
				$('EmailList').value = data.emaillist;
			}else{
				$('allUsersList').innerHTML = "<b>Error in SystemAgent.getEmailList Method: </b>" + data.reason; 
			}
		},
		errorHandler:function(errorString, exception){ 
		alert("SystemAgent.getEmailList( error:" + errorString + exception);
		}
	});
}
function quota(myid, mybool) {
	SystemAgent.setQuota({id:myid, quota:mybool});
}

function hide(divid){
	if(document.getElementById(divid).style.display!="none") {
		document.getElementById(divid).style.display="none";
	} else {
		document.getElementById(divid).style.display="block";
	}
}

function saveQuotaLimit(count) {
	var limitfieldname = "limitvalue" + count;
	var idfieldname = "countyid" + count;
	var limit = document.getElementById(limitfieldname).value;
	var id = document.getElementById(idfieldname).value;
	SystemAgent.setQuotaLimit({countyId:id, limit:limit});
	setTimeout("getQuotaStats()",100);
}

function saveCountyName(count) {
	var namefieldname = "namevalue" + count;
	var idfieldname = "countyid" + count;
	var name = document.getElementById(namefieldname).value;
	var id = document.getElementById(idfieldname).value;
	SystemAgent.editCountyName({countyId:id, name:name});
	setTimeout("getQuotaStats()",100);
}

function addCounty() {
	var countyname = document.getElementById('countyname').value;
	SystemAgent.addCounty({name:countyname});
	setTimeout("getQuotaStats()",100);
}
function deleteUser(myid) {
	var answer = confirm("Are you sure about that Chief?")
	if (answer){
		SystemAgent.deleteUser({id:myid});
	}
}
function resetPassword(myid) {
	//SystemAgent.resetPassword({ids:myid});
	SystemAgent.resetPassword({ids:myid}, {
		callback:function(data){
			if (data.successful){
				alert("New Password: " + data.password);
			}else{
				$('allUsersList').innerHTML = "<b>Error in SystemAgent.resetPassword Method: </b>" + data.reason; 
			}
		},
		errorHandler:function(errorString, exception){ 
		alert("SystemAgent.resetPassword( error:" + errorString + exception);
		}
	});
}

function deleteCounty(countyid) {
	SystemAgent.deleteCounty({countyid:countyid});
	setTimeout("getQuotaStats()",100);
}

function deleteZip(countyid, zipcode) {
	SystemAgent.deleteZipCodes({countyid:countyid, zips:zipcode});
	setTimeout("getQuotaStats()",100);
}

function addZipCodes(countyid, count) {
	var idfieldname = "zipcodesinput" + count;
	var zipcode = $F(idfieldname).toString()
	SystemAgent.addZipCodes({countyId:countyid, zips:zipcode});
	setTimeout("getQuotaStats()",100);
}

function greyDisabled() {
	foo = document.getElementsByClassName('disabled');
	for (var i = 0;i<foo.length;i++){
		foo[i].parentNode.parentNode.style.color='#999999';
		foo[i].style.color="#7B7B7B";
	}
}

</script>
<event:pageunload />
		</head>
		<body>
		<!-- Begin the header - loaded from a separate file -->
		<div id="header">
  <!-- Start Global Headers  -->
	  <wf:nav />
	  <wf:subNav />
  <!-- End Global Headers -->
		</div>
		<!-- End header -->
		<!-- #container is the container that wraps around all the main page content -->
		<div id="container">
			<h2 class="headerColor">Moderator Tools</h2>
				<p><a href="main.do">Back to Moderator Control Panel</a></p>
			<div id="quota"> </div>
			<h3 class="headerColor">User Management</h3><br/>
			<div id="userList"> </div>
			<br />
			<br />
			<h3 class="headerColor">Export Tool</h3>
			<div id="exportWrapper" class="box4 padding5">
				<div class="floatLeft">
					<input type="button" value="Export E-mail Addresses"	class="padding5"
					onClick="javascript:getEmailList();new Effect.toggle($('emailList'),'blind');"/>
				</div>
				<div class="floatLeft leftMargin">
					<span>This will display the e-mail address of all registered users in plain text format.</span>
				</div>
				<br/>
				<br class="clearBoth" />
				<div id="emailList" style="display:none;">
					<form name="emaillist">
						<div class="floatLeft">
							<textarea id="EmailList" cols="70" rows="10"></textarea>
						</div>
						<div class="floatLeft leftMargin">
							<h3 class="headerColor">Filter By:</h3>
							<label>
							<input name="userfilter" type="radio" value="all" 
								checked="checked" onChange="getEmailList('');">
							All Users</label>
							<br/>
							<label>
							<input type="radio" name="userfilter" value="enabled" 
							onChange="getEmailList('quota');">
							Quota Only</label>
							<br/>
							<label>
							<input type="radio" name="userfilter" value="enabled" 
							onChange="getEmailList('nonquota');">
							Non-Quota Only</label>
						</div>
						<div class="clearBoth"></div>
						</p>
					</form>
					<strong>Hint:</strong> Ctrl + A to select All, Ctrl + C to copy to clipboard
				</div>
			</div>
				<h3 class="headerColor" style="margin:20px auto 5px auto">Finished managing users?</h3>
	<!-- this button just redirects - saves are occuring on check. -->
	<p><input type="button" style="padding:5px" onClick="location.href='userhome.do'" value="Finished!"/></p>
		</div>
		<div id="allUsersList">All Users List</div>
		<!-- end container -->
		<!-- start feedback form -->
		<pg:feedback id="feedbackDiv" action="cctView.do"/>
		<!-- end feedback form -->
		<!-- Begin header menu - The wide ribbon underneath the logo -->
  <!-- Start Global Headers  -->
	  <wf:subNav />
  <!-- End Global Headers -->
		<!-- Begin footer -->
		<div id="footer">
			<jsp:include page="/footer.jsp" />
		</div>
		<!-- End footer -->

		</body> 
	</pg:show>
</html:html>
