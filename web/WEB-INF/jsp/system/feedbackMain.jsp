<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
<html><head><title>Let's Improve Transportation - Feedback from Users</title><!-- Site Wide CSS -->


<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/position.css";</style>
<style type="text/css" media="screen">@import "styles/styles.css";</style>
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
		
</script>
</head><body onload="getFeedbacks();">
<div id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>



<!-- Header -->

<jsp:include page="/header.jsp" />
<!-- Sub Title -->
<div id="subheader">
<h1>Moderator:</h1> <h2>Feedback</h2>
</div>
<div id="footprints">
<span class="smalltext">Moderator Tools >>Feedback</span>
</div>
<!-- End Sub Title -->


<!-- Overview SpiffyBox -->
<div class="cssbox">
<div class="cssbox_head">
<h3>Overview and Instructions</h3>
</div>
<div class="cssbox_body">
<p>blah blah blah</p>
</div>
</div>
<!-- End Overview -->

</div> <!-- End cont-top -->

	<div id="cont-main">
		<h3>Listing of User Feedback</h3>
		<div id="feedbacks" style="padding: 10px;"><!-- load collection of feedback from feedbacks.jsp here -->
		</div>
	</div>
	<!-- End cont-main -->



<div id="footerspacing">
<!-- Start Footer -->
<jsp:include page="/footer.jsp" />

<!-- End Footer -->
</div>
</body></html>