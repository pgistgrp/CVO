<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>Structured Discussion Main</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/position.css";</style>
<style type="text/css" media="screen">@import "styles/styles.css";</style>
<!-- Temporary Borders used for testing <style type="text/css" media="screen">@import "styles/tempborders.css";</style>-->
<!-- End Site Wide CSS -->


<!-- Site Wide JavaScript -->
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->

<!--SDX Specific  Libraries-->
<script type='text/javascript' src='/dwr/interface/SDAgent.js'></script>
<!--End SDX Specific  Libraries-->


<script type="text/javascript">
	 function InfoStructure(){
		this.isDivElement = 'object';

	 	 this.getTargets = function(){
	 	 		//displayIndicator(true);
				SDAgent.getTargets({isid:${structure.id}}, {
				callback:function(data){
						if (data.successful){
							$(infoStructure.isDivElement).innerHTML = data.source.html;
              				eval(data.source.script);
              				//displayIndicator(false);
              				 alert(data.voting);
              				 if(data.voting) { 
		              				 if(data.voting == null){
						           		$('structure_question').innerHTML = '<span class="smalltext">Does this summary adequately reflect concerns expressed by participants? <a href="javascript:infoStructure.setVote(true,${structure.id},\'isid\');"><img src="images/btn_yes_s.gif" alt="YES" border="0"><a href="javascript:infoStructure.setVote(false, ${structure.id},\'isid\');"><img src="images/btn_no_s.gif" alt="NO" border="0"></a></span>';
						           }
					          }else{
						           		$('structure_question').innerHTML = '<span class="smalltext">Your vote has been recorded. Thank you for your participation.</span>';
						      }
					        
						}else{
							alert(data.reason);
							 displayIndicator(false);
						}
					},
				errorHandler:function(errorString, exception){ 
						alert("get targets error:" + errorString + exception);
				}
				});
			};

	};
</script>

</head>

<body>
<div id="container">
<jsp:include page="/header.jsp" />
<!-- Header -->


<div id="cont-top">


<!-- Sub Title -->
<div id="subheader">
<h1>Step 1 Brainstorm Concerns:</h1> <h2>Discuss Concerns Summary</h2>
</div>
<div id="footprints">
<span class="smalltext">LIT Process >> Step 1 Brainstorm Concerns >> Discuss Concerns Summary</span>
</div>
<!-- End Sub Title -->


<!-- Overview SpiffyBox -->
<div class="cssbox">
<div class="cssbox_head">
<h3>Overview and Instructions</h3>
</div>
<div class="cssbox_body">
<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nam interdum. Donec accumsan, purus ut viverra pharetra, augue tellus vehicula orci, eget consectetuer neque tortor id
ante. Proin vehicula imperdiet ante. Mauris vehicula velit sed arcu. Ut aliquam pede ac arcu. Phasellus dictum condimentum nisl. Quisque elementum dictum nibh. Curabitur
auctor faucibus libero. Suspendisse eu dui ut sem nonummy egestas. Praesent luctus lorem a magna.</p>
</div>
</div>
<!-- End Overview -->

</div> <!-- End cont-top -->

<div id="cont-main">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td id="maintop"><img src="#" alt="" height="1" width="1"/></td>
<td><img src="images/sidebar_top.gif" alt="sidebartop" /></td>
</tr>
<tr>
<td valign="top" id="maincontent">
<!-- Main Content starts Here-->
<span class="textright"><span class="smalltext">Jump To: Put a select here</span></span>
<h4>Concern Theme Rooms</h4>
<div id="object" class="borderblue">
	<!-- load discussion rooms -->
</div><!-- End Object -->
		
		<p class="textalignright">
			<span class="smalltext">${structure.numAgree} of ${structure.numVote} participants have said that this list of concern themes adequately reflects concerns expressed by participants.</span>
		</p>
		<h4>Talk to the Morderator (needs a better name) </h4>
		<div class="borderblue">
		<table width="100%" border="0" cellspacing="0">
          <tr class="disc_row_b">
            <td width="50%"><a href="#">Discussion about all concern Themes </a><br />
              <span class="smalltext">Do you feel like a corner theme is missing or unnecessary from the above list? Discuss here. </span></td>
            <td width="40%"><a href="#">Singing Hampsters</a><br />
                <span class="smalltext"><span class="textright">6-03-2006</span> By Adam </span></td>
            <td width="10%" class="textcenter"><a href="#">12</a></td>
          </tr>	    
        </table>
		</div>


<td width="280" valign="top" id="sidebarmiddle"><!-- This is the Right Col -->
  <div id="sidebar_content">
    <h4>Other Discussions filtered by:</h4>
  
	<h5>Accidents</h5>

	<div class="sidebardisc">
	<a href="#">What I am Concerned With</a><br /><span class="smalltext">What I am mainly concerned with is something that I have been [more...]</span><br /><span class="smalltext">[Tags] [Tags] [Tags]</span>
	</div>
	<div class="sidebardisc">
	<a href="#">What I am Concerned With</a><br /><span class="smalltext">What I am mainly concerned with is something that I have been [more...]</span><br /><span class="smalltext">[Tags] [Tags] [Tags]</span>
	</div>
	<div class="sidebardisc">
	<a href="#">What I am Concerned With</a><br /><span class="smalltext">What I am mainly concerned with is something that I have been [more...]</span><br /><span class="smalltext">[Tags] [Tags] [Tags]</span>
	</div>
	<div class="sidebardisc">
	<a href="#">What I am Concerned With</a><br /><span class="smalltext">What I am mainly concerned with is something that I have been [more...]</span><br /><span class="smalltext">[Tags] [Tags] [Tags]</span>
	</div>
</div>
<!-- End sidebarcontents-->
 </td><!-- End Right Col -->
</tr>

</table>
<div id="sidebarbottom" style="text-align:right;"><img src="images/sidebar_bottom.gif" alt="sidebarbottom" /></div>


</div>
<!-- End cont-main -->

</div> <!-- End container -->
<!-- Start Footer -->
<jsp:include page="/footer.jsp" />

<!-- End Footer -->
<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
<script type="text/javascript">
	var infoStructure = new InfoStructure(); 

	infoStructure.getTargets();

	

</script>
</body>

</html:html>