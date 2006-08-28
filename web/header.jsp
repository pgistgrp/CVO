<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<!-- Header -->
<div id="header">

</div>

<div id="login"><img src="images/btn_logout.gif" alt="logout" width="126" height="21" /></div>

<div id="cont-top">
<!--START Title Header -->
	<div id="headernav">
			<!-- Search -->
		<form id="mysearch" name="mysearch" method="post" action="">    
		<div id="searchbox">
			<input name="search" type="text" class="search" value="Search" />
		    
			<a href="javascript:;" onmouseout="MM_swapImgRestore();" onmouseover="MM_swapImage('btn_search_1','','images/btn_search_2.gif',1);" onclick="sendForm();return false;">
			<img name="btn_search_1" src="images/btn_search_1.gif" width="19" height="21" border="0" id="btn_search_1" alt="submit" />
			</a></div> 
		<!-- End searchbox -->
		<div id="submit"></div>
		<!-- End submit -->
		<div id="searchresults"></div>
		</form>
		<!-- End Search -->
		<!-- Navigation -->
			<div id="headerbuttons">
				<a href="#" class="headerbuttons_selected">Home</a> <a href="#">Current Task</a> <a href="#">Resource Library</a>
			</div>
		<!-- ENd Navi -->
	</div> <!-- End headerbar -->
	<div id="headerbar">Sub 1 &nbsp; Sub 2 &nbsp; Sub 3 &nbsp; Sub 4 &nbsp;</div><!-- End headerguide -->
<!--END Title Header -->
</html:html>

