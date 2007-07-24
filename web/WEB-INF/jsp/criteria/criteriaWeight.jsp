<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8">
		<title>Weigh Improvement Factors</title>
		<!-- Site Wide JavaScript -->
		<script src="/scripts/globalSnippits.js" type="text/javascript"></script>
		<script src="/scripts/tags.js" type="text/javascript"></script>
		<script src="/scripts/prototype.js" type="text/javascript"></script>
		<script src="/scripts/scriptaculous.js?control,effect" type="text/javascript"></script>
		<!-- End Site Wide JavaScript -->
		<!-- DWR JavaScript Libraries -->
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
		<!-- End DWR JavaScript Libraries -->
		<!--Criteria Specific  Libraries-->
		<script type='text/javascript' src='/dwr/interface/CriteriaAgent.js'></script>
		

		<script type="text/javascript" charset="utf-8">
			//Load the critSuiteId for weighCriteria.js (external so we can reuse the functions)
			var critSuiteId = '<%=request.getParameter("suiteId")%>';	
		</script>
		<script type='text/javascript' src='/scripts/weighCriteria.js'></script>
		<script type="text/javascript" src="/scripts/util.js"></script>
		<style type="text/css" media="screen">
			@import "styles/lit.css";
			@import "styles/weighCriteria.css";
		</style>

	<event:pageunload />
	</head>
	<body>
		<!-- Begin conditional styles for the benefit of IE -->
		<!--[if IE]>
		                          <style type="text/css">
		                          #sortingMenu {right:0px;}
								  #criteriaRuler {width:745px;}
								  
								  


div > div#saving-indicator{
position:fixed;
}

div > div#loading-indicator{
position:fixed;
}
</style>

		                          
		          <![endif]-->
		<!-- End conditional styles -->
		
        <!-- Start Global Headers  -->
        <wf:nav />
        <wf:subNav />
        <!-- End Global Headers -->
		<!-- Begin loading indicator -->
		<div style="display: none;" id="loading-indicator"> Loading... 
			<img src="/images/indicator_arrows.gif"> 
		</div>
		<div style="display:none;" id="saving-indicator"> Saving...
			<img src="/images/indicator_arrows.gif">
		</div>
		<!-- End loading indicator -->
		<!-- Begin container - Main page content begins here -->
		<div id="container">
			<!-- begin "overview and instructions" area -->
			<div id="overview" class="box2">
				<h3>Overview and instructions</h3>
				<p>In this step you can weigh the relative importance of the improvement factors.
					<ul>
						<li>You have a total of 100 points to distribute among the nine factors.</li>
						<li>Drag the bar next to an improvement factor to assign a weight.</li>
						<li>Assign a higher weight to factors you feel are most important.</li>
					</ul>
				</p>
				<p>Assigning weights will help you to identify transportation projects in Step 3 which most closely reflect your own priorities for transportation system improvement.</p>
			</div>
			<!-- end overview -->
			<!-- begin object -->
			<div id="object">
				<!-- begin object content -->
				<div id="object-content">
					<div id="criteria">
						<!--load the criteria partial here -->
					</div>
				</div><!--end object content -->
					
					
				<div class="clearBoth"></div>
				<div class="floatRight padding5">
					<input type="button" value="Continue" class="padding5" onclick="location.href='waiting.jsp'" />
				</div>
				<div class="clearBoth">&nbsp;</div>
			</div><!-- end object -->
		</div><!--end container -->
				
		<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
		<script type="text/javascript" charset="utf-8">
			//getWeights();
			getCriteriaSuiteById();

		//	setTimeout(function() {initWeights();}, 350);
		</script>
		
        <!-- Start Global Headers  -->
        <wf:subNav />
        <!-- End Global Headers -->
		<!-- Begin footer -->
		<div id="footer"> 		<jsp:include page="/footer.jsp" /></div>
		<!-- End footer -->

	</body>
</html>