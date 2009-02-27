<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>Let's Improve Transportation - Event Log</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/lit.css";</style>

<!-- End Site Wide CSS -->
<link type="text/css" rel="StyleSheet" href="styles/sortabletable.css" />

<style type="text/css">

body {
	font-family:	Verdana, Helvetica, Arial, Sans-Serif;
	font:			Message-Box;
}

table { width:950px; }

/* background color for odd numbered rows */
.odd {
	background:#F6F7F2;
}


</style>
<!-- Site Wide JS -->
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type="text/javascript" src="scripts/sortabletable.js"></script>
<script type="text/javascript">
function paintRows()
{
	rows = document.getElementsByTagName('tr')
	for (var i = 0; i <= rows.length;i++) 
	{
		if (i%2==0)
		{
			rows[i].setAttribute("class","even");
		}else{
			rows[i].setAttribute("class","odd");
		}
	}
}
</script>
</head>

<body onLoad="javascript:paintRows();">
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
	<table class="sort-table" id="table-1" cellspacing="0" cellpadding="2">
		<col />
		<col />
		<col style="text-align: right" />
		<col />
		<thead onClick="javascript:paintRows();">
			<tr>
				<td>User</td>
				<td>Step</td>
				<td>Method</td>
				<td>Date</td>
			</tr> 
		</thead>
		<tbody>
			<tr>
				<td>Adam</td>
				<td>Step 1a</td>
				<td>getBananas</td>
				<td>2006-11-15 1400 </td>
			</tr>
			<tr>
				<td>Joe</td>
				<td>Step 1a</td>
				<td>peelBananas</td>
				<td>2006-11-15 1412 </td>
			</tr>
			<tr>
				<td>Zed</td>
				<td>Step 1b</td>
				<td>throwBananas</td>
				<td>1900-01-01 0000 </td>
			</tr>
			<tr>
				<td>Jordan</td>
				<td>Step 1c</td>
				<td>thinkAboutBananas</td>
				<td>2003-09-29 1100 </td>
			</tr>
			<tr>
				<td>Adam</td>
				<td>Step 2b</td>
				<td>eatBananas</td>
				<td>2006-11-15 1500 </td>
			</tr>
			<tr>
				<td>Adam</td>
				<td>Step 3a</td>
				<td>getBananas</td>
				<td>2006-11-17</td>
			</tr>
			<tr>
				<td>Brian</td>
				<td>Step 2a</td>
				<td>appreciateBananas</td>
				<td>2006-11-15 1400 </td>
			</tr>
			<tr>
				<td>Jordan</td>
				<td>Step 4b</td>
				<td>eatBananas</td>
				<td>2006-11-15 1430 </td>
			</tr>
			<tr>
				<td>Jordan</td>
				<td>Step 3b</td>
				<td>getBananas</td>
				<td>2006-11-13</td>
			</tr>
			<tr>
				<td>Matt</td>
				<td>Step 0</td>
				<td>getBananas</td>
				<td>2004-01-24</td>
			</tr>
			<tr>
				<td>Matt</td>
				<td>Step 3c</td>
				<td>makeBanana</td>
				<td>2006-03-03</td>
			</tr>
		</tbody>
	</table>

<script type="text/javascript">

var st1 = new SortableTable(document.getElementById("table-1"),
	["String", "String", "String", "Date"]);

</script>

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

