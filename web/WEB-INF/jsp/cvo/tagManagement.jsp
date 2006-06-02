<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>PGIST Portal - Let's Improve Transportation</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<style type="text/css" media="screen">@import "/styles/pgist.css";</style>

<script type="text/javascript">
<script src="/scripts/prototype.js" type="text/javascript"></script>
<script src="/scripts/effects.js" type="text/javascript"></script>
<script src="/scripts/combo.js" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>

function ifEnter(field,event) {
	var theCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
	if (theCode == 13){
	prepareConcern();
	$('theTag').focus();
	return false;
	} 
	else
	return true;
}   

function showTheError(errorString, exception){
					$('overview').style.display = 'none';
				$('slate').style.display = 'none';
				$('bar').style.display = 'none';
				$('caughtException').style.display = 'block';
				$('caughtException').innerHTML +='<p>If this problem persists, please <A HREF="mailto:webmaster@pgist.org?subject=LIT Website Problem>contact our webmaster</a></p>';
}
</script>
</Head>
<body>

<div id="decorBar"></div>
<div id="container">

<div id="searchNavContiner">
	<div id="logo"><img src="/images/logo.png"></div>
	<div id="authentication">Welcome, ${baseuser.firstname} [&nbsp;<a href="/logout.do">logout</a>&nbsp;]</div>
	<div id="mainSearch">
			<form name="mainSearch" method="post" onSubmit="search();">
				<input type="text" ID="tbx1" class="searchBox" style="padding-left: 5px; padding-right:20px; background: url('/images/search.gif') no-repeat right;" value="Search" onfocus="this.value = ( this.value == this.defaultValue ) ? '' : this.value;return true;"/>

			</form>	
	</div>
	<div id="mainNav" class="navigation">Home&nbsp;&nbsp;&nbsp;&nbsp;<span class="active">Current Task</span>&nbsp;&nbsp;&nbsp;&nbsp;Discussion&nbsp;&nbsp;&nbsp;&nbsp;Maps&nbsp;&nbsp;&nbsp;&nbsp;Library&nbsp;&nbsp;</div>
</div>
<!-- LIGHTBOX -->
<div id="overlay"></div>
<div id="lightbox"></div>
<!-- LIGHTBOX -->
<div id="pageTitle">
<span class="title_page">Moderator Dashboard: </span><h1>Tag Management</h1>
	<div id="bread">
	<ul>
		<li class="first"><a href="null">Moderator Dashboard</a>
			<ul>
				<li>&#187; <a href="null">Databases</a></li>
					<ul>
							<li>&#187; <a href="null">Tag Management</a></li>
					</ul>
			</ul>
		</li>
	</ul>
	</div>
</div>	

 <div id="overview">
	  	<h3>Overview and Instructions</h3> 
	  	<p class="indent"><strong>Overview: </strong>${cctForm.cct.purpose}</p>
	  	<p class="indent"><strong>Instructions: </strong>${cctForm.cct.instruction}</p>
 </div>

 <div id="caughtException"><h2>A Problem has Occured</h2><br>We are sorry but there was a problem accessing the server to complete your request.  <b>Please try refreshing the page.</b></div>
 <div id="fullSlate">
  		<h2>Add your concern</h2><br>What is one of your concerns about the Central Puget Sound Transportation System?  View examples of concerns in the right column (<a href="javascript:tabFocus(1);">concerns tab</a>).


  </div>
<!--START SIDEBAR -->

</div>
<!--END SIDEBAR -->
<div id="footerContainer">
	<div id="footer"><a href="http://www.pgist.org" target="_blank"><img src="/images/footer_pgist.jpg" alt="Powered by the PGIST Portal" border="0" align="right"></a></div>
	<div id="nsf">This research is funded by National Science Foundation, Division of Experimental and Integrative Activities, Information Technology Research (ITR) Program, Project Number EIA 0325916, funds managed within the Digital Government Program.</div>
</div>
</body>
</html>
