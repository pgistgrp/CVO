<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<!-- Header -->
<div id="header">

</div>
<div id="login"><img src="/images/btn_logout.gif" /></div>

<div id="cont-top">
<!--START Title Header -->
	<div id="headernav">
			<!-- Search -->
		<form id="mysearch" name="mysearch" method="post" action="">    
		<div id="searchbox">
			<input name="search" type="text" class="search" value="Search" />
		</div> <!-- End searchbox -->
		<div id="submit">
			<img src="images/btn_search_1.png" name="Image1" width="19" height="19" border="0" id="Image1" onClick="sendForm();return false;" onMouseDown="MM_swapImage('Image1','','images/btn_search_3.png',1)" onMouseOver="MM_swapImage('Image1','','images/btn_search_2.png',1)" onMouseOut="MM_swapImgRestore()">
		</div><!-- End submit -->
		<div id="searchresults"></div>
		</form>
		<!-- End Search -->
		<!-- Navigation -->
			<div id="headerbuttons">
				<a href="#" class="headerbuttons_selected">Home</a> <a href="#">Current Task</a> <a href="#">Resource Library</a>			</div>
		<!-- ENd Navi -->
	</div> <!-- End headerbar -->
	<div id="headerbar">Sub 1 &nbsp; Sub 2 &nbsp; Sub 3 &nbsp; Sub 4 &nbsp;</div><!-- End headerguide -->
<!--END Title Header -->
</html:html>

