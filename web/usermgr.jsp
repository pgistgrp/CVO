<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>Let's Improve Transportation - User Management</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/lit.css";</style>

<!-- End Site Wide CSS -->
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
	getEnabledUsers();
	getLockedUsers();
	getEmailList();
}

function getEnabledUsers() {
	SystemAgent.getEnabledUsers({}, {
		callback:function(data){
			if (data.successful){
				$('enabledUsersList').innerHTML = data.html;
				
			}else{
				$('enabledUsersList').innerHTML = "<b>Error in SystemAgent.getEnabledUsers Method: </b>" + data.reason; 
			}
		},
		errorHandler:function(errorString, exception){ 
		alert("SystemAgent.getEnabledUsers( error:" + errorString + exception);
		}
	});
}

function getLockedUsers() {
	SystemAgent.getDisabledUsers({}, {
		callback:function(data){
			if (data.successful){
				$('lockedUsersList').innerHTML = data.html;
				
			}else{
				$('lockedUsersList').innerHTML = "<b>Error in SystemAgent.getDisabledUsers Method: </b>" + data.reason; 
			}
		},
		errorHandler:function(errorString, exception){ 
		alert("SystemAgent.getDisabledUsers( error:" + errorString + exception);
		}
	});
}

function lockUsers() {
	var size = document.enabledUsersList.activelist.options.length;
	var list = "";
	for(var i=0; i<size; i++) {
		if(document.enabledUsersList.activelist.options[i].selected == true) {
			var name=document.enabledUsersList.activelist.options[i].value;
			list += name + ",";
		}
	}
	SystemAgent.disableUsers({ids:list});
	
}

function unlockUsers() {
	var size = document.disabledUsersList.lockedlist.options.length;
	var list = "";
	for(var i=0; i<size; i++) {
		if(document.disabledUsersList.lockedlist.options[i].selected == true) {
			var name=document.disabledUsersList.lockedlist.options[i].value;
			list += name + ",";
		}
	}
	SystemAgent.enableUsers({ids:list});
	
}

function getEmailList() {
	var id =""
	SystemAgent.getEmailList({}, {
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

</script>


</head>

<body>
 <!-- Begin the header - loaded from a separate file -->
  <div id="header">
	<!-- Begin header -->
	<jsp:include page="/header.jsp" />
	<!-- End header -->
  </div>
  <!-- End header -->
  <!-- Begin header menu - The wide ribbon underneath the logo -->
  <div id="headerMenu">

  </div>
  <!-- End header menu -->
  <!-- #container is the container that wraps around all the main page content -->
  <div id="container">

	<table border="0" cellpadding="5">
      <tr>
        <td>Active Accounts</td>
        <td>Locked Accounts</td>
      </tr>
      <tr>
        <td><form name="enabledUsersList" method="POST" action="" onSubmit="return lockUsers(this)"><div id="enabledUsersList"></div><input name="Lock Selected User(s)" type="submit" value="Lock Selected User(s)">
        </form></td>
        <td><form name="disabledUsersList" method="POST" action="" onSubmit="return unlockUsers(this)"><div id="lockedUsersList"></div><input name="Unlock Selected User(s)" type="submit" value="Unlock Selected User(s)">
        </form></td>
      </tr>
    </table>
	<form name="emaillist">
	  <p>Email List: (Ctrl + A to select All, Ctrl + C to copy to clipboard) <br>
	    <textarea id="EmailList" cols="75" rows="8"></textarea>
      </p>
    </form>
  </div>
  <!-- end container -->
  
<!-- start feedback form -->
  <pg:feedback id="feedbackDiv" action="cctView.do"/>
<!-- end feedback form -->

  <!-- Begin header menu - The wide ribbon underneath the logo -->
  <div id="headerMenu">

  </div>

	<!-- Begin footer -->
	<div id="footer">
		<jsp:include page="/footer.jsp" />
	</div>
	<!-- End footer -->
</body>
</html:html>

