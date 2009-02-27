<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>

<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<html><head><title>Voicing Climate Concerns - Feedback from Users</title><!-- Site Wide CSS -->


<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/lit.css";</style>
<!-- End Site Wide CSS -->

<!-- Site Wide JS -->
<script src="scripts/search.js" type="text/javascript"></script>
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<!-- End Site Wide JS -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/SystemAgent.js'></script>
<!-- End DWR JavaScript Libraries -->

<script type="text/javascript">
	 function getFeedbacks(){
	 		var page = 1;
	 		displayIndicator(true);
			SystemAgent.getFeedbacks({count:-1, page: page}, {
			callback:function(data){
					if (data.successful){
						$('feedbacks').innerHTML = data.html;
						displayIndicator(false);
					}
					if (data.successful != true){
						alert(data.reason);
						displayIndicator(false);
					}
				},
			errorHandler:function(errorString, exception){ 
					showTheError();
			}
			});
	}
	
	function displayIndicator(show){
		if (show){
			$('loading-indicator').style.display = "inline";	
		}else{
			$('loading-indicator').style.display = "none";	
		}
	}
		
</script><event:pageunload />
</head><body onLoad="getFeedbacks();">

  <!-- Start Global Headers  -->
  <wf:nav />
  <wf:subNav />
  <!-- End Global Headers -->
  
  <div id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>
  
  <!-- #container is the container that wraps around all the main page content -->
  <div id="container">
		<p><a href="main.do">Back to Moderator Control Panel</a></p>
		<div id="feedbacks" style="padding: 10px;">
			<!-- load collection of feedback from feedbacks.jsp here -->
		</div>
		<h3>Finished managing feedback?</h3>
	<!-- this button just redirects - saves are occuring on check. -->
	<p><input type="button" style="padding:5px" onClick="location.href='userhome.do'" value="Finished!"/></p>
  </div>
  <!-- end container -->
  
<!-- start feedback form -->
  <pg:feedback id="feedbackDiv" action="cctView.do"/>
<!-- end feedback form -->

  <wf:subNav />
  <!-- End Global Headers -->

	<!-- Begin footer -->
	<div id="footer">
		<jsp:include page="/footer.jsp" />
	</div>
	<!-- End footer -->

</body>
</html:html>