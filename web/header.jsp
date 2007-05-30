<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="header-wrap" class="clearBoth">

	<div id="header-logo">
		<a href="/main.do"><img src="/images/mainlogo.png" alt="LIT LOGO" border="0"/></a>
	</div>
	
	
	
	
	<div id="header-navigation">
		<c:choose>
			<c:when test="${baseuser != null}">
				<span><a href="userhome.do?wf=${requestScope['org.pgist.wfengine.WORKFLOW_ID']}">Home</a></span>
				<span><a href="lmMenu.do">Learn More</a></span>
				<span><a href="usercp.do">User Settings</a></span>
				<span><a href="/logout.do">Log out</a></span>
			</c:when>
			<c:otherwise>
				<span><a href="userhome.do?wf=${requestScope['org.pgist.wfengine.WORKFLOW_ID']}">Home</a></span>
				<span><a href="lmMenu.do">Learn More</a></span>
				<span><a href="/login.do">Log in</a></span>
			</c:otherwise>
		</c:choose>
	</div>

	<!-- Begin Search -->
	<div id="header-search" style="display:none">
		<form id="mysearch" name="mysearch" method="post" action="">
		  <div id="searchbox">
			<input name="search" type="text" class="search" value="Search" disabled />
			<a href="javascript:;" onmouseout="MM_swapImgRestore();" onmouseover="MM_swapImage('btn_search_1','','images/btn_search_2.gif',1);" onclick="sendForm();return false;"> <img name="btn_search_1" src="images/btn_search_1.gif" width="19" height="21" border="0" id="btn_search_1" alt="submit" /></a></div>
		  <!-- End searchbox -->
		  <div id="submit" class="floatLeft"></div>
		  <!-- End submit -->
		  <!-- <div id="searchresults"></div>-->
		</form>

	</div>
	<!-- End Search -->

</div>	
<div class="clearBoth"></div>
