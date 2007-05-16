<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8">
		<title>Weigh Planning Factors</title>
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
		
		<!-- Begin header -->
		<div id="header">		<jsp:include page="/header.jsp" /></div>
		<!-- End header -->
		<!-- Begin header menu - The wide ribbon underneath the logo -->
		<div id="headerMenu">
			<div id="headerContainer">
				<div id="headerTitle" class="floatLeft">
					<h3 class="headerColor">Step 2: Evaluate Planning Factors</h3>
				</div>
				<div class="headerButton floatLeft"> <a href="step2a.html">2a:
						Review and discuss factors</a> </div>
				<div class="headerButtonCurrent floatLeft currentBox"> <a href="step2b.html">2b:
						Weigh factors</a> </div>
				<div id="headerNext" class="floatRight box5"> <a href="/sdcWaiting.jsp">Next
						Step</a> </div>
			</div>
		</div>
		<!-- End header menu -->
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
				<h3>Overview and Instructions</h3>
<p>Which planning factors do you feel are most important when determining which transportation improvement projects to fund and build? On this page you can assign weights to the nine different planning factors. This will help you to identify projects in Step 3 which most closely reflect your own priorities for transportation system improvement.</p>

<p>To assign a weight to a planning factor, drag the slider bar next to that factor. You have <strong>100 points</strong> to distribute between the nine factors.</p>

<strong>Hint:</strong> Click the "plus" (<img src="images/plus.gif" valign="bottom"> ) icon to see what objectives go into grading each project.<br />
				<p><a href="readmore.jsp">Read more about how this step fits
						into the bigger picture</a>.</p>
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
		
		<!-- start the bottom header menu -->
		<!-- Begin header menu - The wide ribbon underneath the logo -->
		<div id="headerMenu">
			<div id="headerContainer">
				<div id="headerTitle" class="floatLeft">
					<h3 class="headerColor">Step 2: Evaluate Planning Factors</h3>
				</div>
				<div class="headerButton floatLeft"> <a href="step2a.html">2a:
						Review and discuss factors</a> </div>
				<div class="headerButtonCurrent floatLeft currentBox"> <a href="step2b.html">2b:
						Weigh factors</a> </div>
				<div id="headerNext" class="floatRight box5"> <a href="/sdcWaiting.jsp">Next
						Step</a> </div>
			</div>
		</div>

		<!-- End header menu -->
		<!-- end the bottom header menu -->
		<!-- Begin footer -->
		<div id="footer"> 		<jsp:include page="/footer.jsp" /></div>
		<!-- End footer -->

	</body>
</html>