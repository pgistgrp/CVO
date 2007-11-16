<!DOCTYPE html PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html><head><title>PGIST main page</title><!-- Site Wide CSS -->






<style type="text/css" media="screen">@import "styles/position.css";</style>
<style type="text/css" media="screen">@import "styles/styles.css";</style><!-- End Site Wide CSS --><!-- Site Wide JS -->


<script src="login.do_files/search.js" type="text/javascript"></script>
<script type="text/javascript" src="login.do_files/engine.js"></script>
<script type="text/javascript" src="login.do_files/util.js"></script></head><body>
<!-- Header -->
<div id="header">

</div>

<div id="login"><a href="http://128.95.212.210:8080/logout.do"><img src="login.do_files/btn_logout.gif" alt="logout" id="btn_logout" height="21" width="126"></a></div>

<div id="cont-top">
<!--START Title Header -->
	<div id="headernav">
			<!-- Search -->
		<form id="mysearch" name="mysearch" method="post" action="">    
		<div id="searchbox">
			<input name="search" class="search" value="Search" type="text">
		    
			<a href="javascript:;" onMouseOut="MM_swapImgRestore();" onMouseOver="MM_swapImage('btn_search_1','','images/btn_search_2.gif',1);" onClick="sendForm();return false;">
			<img name="btn_search_1" src="login.do_files/btn_search_1.gif" id="btn_search_1" alt="submit" border="0" height="21" width="19"></a></div> 
		<!-- End searchbox -->
		<div id="submit"></div>
		<!-- End submit -->
		<div id="searchresults"></div>
		</form>
		<!-- End Search -->
		<!-- Navigation -->
			<div id="headerbuttons">
				<a href="http://128.95.212.210:8080/main.do" class="headerbuttons_selected">Home</a> <a href="#">Current Task</a> <a href="#">Resource Library</a>
			</div>
		<!-- ENd Navi -->
	</div> <!-- End headerbar -->
	<div id="headerbar">Sub 1 &nbsp; Sub 2 &nbsp; Sub 3 &nbsp; Sub 4 &nbsp;</div><!-- End headerguide -->




<!-- End Header -->

<h1>That's it!</h1>
<p>You've finished the Step 1 functionality we wanted to test. You may continue discussing the concern theme summaries until midnight on October 4th, when discussion in this step ends. If any additional revisions to the summaries are made before October 4, the moderator will notify you by email.</p>
<p>Remember, these summaries do not represent the end of the conversation about participant concerns. They are merely a snapshot in time that we can use in Step 2 to help evaluate the criteria. (However, Step 2 is not ready for testing, so this is the end of the road for now!)</p>

<p>After the text concludes we will analyze the feedback you left us and make improvements in preparation for the next test.</p>

<p>Is there anything else you'd like to tell us?  You can use the feedback form below, or feel free to e-mail Adam at <a href="mailto:adamh@u.washington.edu">adamh@u.washington.edu</a></p> 

<!-- start feedback form -->
	<p>Found a bug?  Problem accessing a part on the page?  <a href="javascript:Effect.toggle('feedbackForm','blind');">Send us feedback.</a></p>
	<div id="feedbackForm" style="display: none;">

		<script src="/dwr/interface/SystemAgent.js"></script><script>function createFeedback() {  s = $('feedback_input').value;  if (s.length==0) { alert('Please input your feedback.'); return; }  action = 'sdcWaiting.do';  SystemAgent.createFeedback(    {feedback:s, action:action}, function(data) {      if (data.successful) { alert('Your feedback is submitted. Thank you.'); $('feedback_input').value=''; }      else { alert(data.reason); }    }  );}</script><div id="feedbackDiv" >Feedback:<br><textarea id="feedback_input"></textarea><br><input type="button" value="Submit" onclick="createFeedback();"></div>
	</div>
<!-- end feedback form -->

</body></html>