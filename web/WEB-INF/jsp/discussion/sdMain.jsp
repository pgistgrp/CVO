<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>Structured Discussion Main</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/position.css";</style>
<style type="text/css" media="screen">@import "styles/styles.css";</style>
<style type="text/css" media="screen">@import "styles/tabs.css";</style>
<style type="text/css" media="screen">@import "styles/headertabs.css";</style>
<!-- Temporary Borders used for testing <style type="text/css" media="screen">@import "styles/tempborders.css";</style>-->
<!-- End Site Wide CSS -->


<!-- Site Wide JavaScript -->
<script src="scripts/headercookies.js" type="text/javascript"></script>
<script src="scripts/headertabs.js" type="text/javascript"></script>
<script src="scripts/tabcookies.js" type="text/javascript"></script>
<script src="scripts/tabs.js" type="text/javascript"></script>
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/findxy.js" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->

<!--SDX Specific  Libraries-->
<script src="scripts/resize.js" type="text/javascript"></script>
<script src="scripts/expand.js" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/interface/SDAgent.js'></script>
<style type="text/css" media="screen">@import "styles/toggle_sdc.css";</style>
<!--End SDX Specific  Libraries-->


<script type="text/javascript">
	//Start Global Variables
     //var isid = ${structure.id};
	 var targetObject = null;
	 var infoStructure = {
	 	 isid: ${structure.id},
	 	 type: "${structure.type}",
	 	 instructions: "These are the instructions for blah blah",
	 	 data: null,
	 	 
	 	 
	 	 getTargets: function(){
				SDAgent.getTargets({isid:${structure.id}}, {
				callback:function(data){
						if (data.successful){

							$('object_column').innerHTML = data.source.html;
             				alert(data.source.html);
              				alert(data.source.script);
              				eval(data.source.script);
						}else{
							//alert(data.reason);
						}
					},
				errorHandler:function(errorString, exception){ 
						//alert("get targets error:" + errorString + exception);
				}
				});
			},
	 	};
	//End Global Variables
	
			
		function getPosts(ioid){
			
				SDAgent.getPosts({isid:isid, ioid:ioid}, {
				callback:function(data){
						if (data.successful){
							$('discussion').innerHTML = data.html;
						}
						if (data.successful != true){
							alert("error:" + data.reason);
						}
					},
				errorHandler:function(errorString, exception){ 
						alert(errorString + exception);
				}
				});
		}
		//$('object_column').innerHTML = '<ul>';

</script>
</head>


<body onResize="dosize()">

<div id="container">
	<!-- START LIGHTBOX -->
	<div id="overlay"></div>
	<div id="lightbox" class="blueBB"></div>
	<!-- END LIGHTBOX -->

	
	<!-- Sub Title -->
	<div id="subheader">
	<h1>Step 1:</h1> <h2>Brainstorm Concerns</h2>
	</div>
	<div id="footprints">
	<p>LIT Process >> Step 1: Brainstorm >> Concerns</p>
	</div>
	<!-- End Sub Title -->
	
	<!-- Overview SpiffyBox -->
	<div class="cssbox">
	<div class="cssbox_head">
	<h3>Overview and Instructions</h3>
	</div>
	<div class="cssbox_body">
		<p>Before we can determine how to best improve the transportation system, we need to know what the problems are. Our first task is to brainstorm concerns about the transportation system.</p><p>[ Read more about how this step fits into the bigger picture. ]</p>
	</div>
	</div>
	<!-- End Overview -->
	


<div id="cont-resize">
<div>
</div>
<h4>Summary of Participant Concerns</h4>
	<div id="col-left">
	
	<div id="cont-toggle">
		<div id="header_object" class="allBlue">
		<div id="header_title">
		<h5>All Concern Themes</h5>
		</div>
		<div id="themeSelector">
		<form id="Tselector" name="ThemeSelector" method="post" action="">
		  <label>
		  Jump To:
		  <select name="selecttheme" id="selecttheme" onChange="getTargetPanes(this.value);">		  
		    <option value = "-1">Select a Theme</option>
	      </select>
		  </label>
		  </form>
		  </div>
		  <div class="clear">
		  <span class="smalltext">Last modified: June 2, 2006 by the Moderator</span>
</div>
		</div>
		<div id="object" class="blueBB">
		<span class="smalltext">4 of 15 participants have said that this list of concern themes adequately reflects concerns expressed by participants.</span><br />
		<p>To view the summary of a concern theme, click on the theme name.</p>
		<div id="object_column">
			<div id="object_column_left">
				<ul
				><li><a href="#">Argue</a> <span class="smalltext">(5 Disscussions)</span></li
				><li><a href="#">Donec</a> <span class="smalltext">(5 Disscussions)</span></li
				><li><a href="#">Dictum</a> <span class="smalltext">(7 Disscussions)</span></li
				></ul>
			</div>
			<div id="object_column_center">
				<ul
				><li><a href="#">Pollution</a> <span class="smalltext">(0 Disscussions)</span></li
				><li><a href="#">Public Transportation</a> <span class="smalltext">(0)</span></li
				><li><a href="#">Safety</a> <span class="smalltext">(17 Disscussions)</span></li
				></ul>
			</div>
			<div id="object_column_right">
				<ul
				><li><a href="#">Utellus</a> <span class="smalltext">(0 Disscussions)</span></li
				><li><a href="#">Viverra</a> <span class="smalltext">(0 Disscussions)</span></li
				><li><a href="#">Zip</a> <span class="smalltext">(17 Disscussions)</span></li
				></ul>
			</div>
			<div class="clear">
			</div>
		</div>
		<div id="object_question" class="smalltext">
			Does this list of concern themes adequately reflect concerns expressed by participants? <img src="images/btn_yes_s.gif" alt="YES"> <img src="images/btn_no_s.gif" alt="NO">
		</div>
		</div>
		<div id="toggle"><a href="javascript:moreObject();"><img src="images/slideDown.gif" alt="More Discussion Space!" width="82" height="9" border="0"></a></div>
		<div id="header_discussion" class="allBlue">
			<div id="newDiscussion" class="greenBB" style="display: none;">
				<div id="header_newDiscussion" class="allGreen">New Discussion<span id="closeNewDiscussion" class="closeBox"><a href="javascript:new Effect.toggle('newDiscussion', 'blind', {duration: 0.5}); void(0);">Close</a></span></div>
				<p><strong>SDC New Discussion Title</strong><p>SDC New Discussion Paragraph</p></p>
				<p><label>Post Title</label><br><input type="text" /></p>
				<p><label>Your Thoughts</label><br><textarea></textarea></p>
				<p><label>Tag your post (comma separated)</label><br><input type="text" /></p>
				<input type="button" value="Create Discussion">
			</div>
			<div class="sidepadding">
			
		  <div id="disc_title"><h5>Discussion about All Concern Themes</h5></div>
		  <div id="btnNewDiscussion"><a href="javascript:new Effect.toggle('newDiscussion', 'blind', {duration: 0.5}); void(0);"><img src="images/btn_newdiscussion.gif" border="0" alt="New Discussion"></a>&nbsp;</div>
		  <br />
		  <span class="smalltext">Feel like a theme is missing from the above list? Have a question about the summary process? Discuss here.</span>
		  	</div>
		</div>
		<div id="header_cat">
			<div class="sidepadding">
			<div class="header_cat_title" ><a href="#">Title</a></div><div class="header_cat_replies"><a href="#">Replies</a></div><div class="header_cat_author"><a href="#">Author</a></div><div class="header_cat_lastpost"><a href="#">Last Post</a></div>
			<div class="clear">
			</div>
			</div>
		</div>
		<div id="discussion" class="blueBB">
			<div class="disc_row_a">
			<div class="sidepadding">
			<div class="header_cat_title" ><a href="#">This doesn't reflect my concerns at all</a></div><div class="header_cat_replies">5</div><div class="header_cat_author"><a href="#">John</a></div><div class="header_cat_lastpost"><a href="#">May 18, 2006</a></div><div class="clear"></div>
			</div>
			</div>
			
			<div class="disc_row_b"><div class="sidepadding">
			<div class="header_cat_title" ><a href="#">This doesn't reflect my concerns at all</a></div><div class="header_cat_replies">5</div><div class="header_cat_author"><a href="#">John</a></div><div class="header_cat_lastpost"><a href="#">May 18, 2006</a></div><div class="clear"></div>
			</div>
			</div>
						<div class="disc_row_a">
			<div class="sidepadding">
			<div class="header_cat_title" ><a href="#">This doesn't reflect my concerns at all</a></div><div class="header_cat_replies">5</div><div class="header_cat_author"><a href="#">John</a></div><div class="header_cat_lastpost"><a href="#">May 18, 2006</a></div><div class="clear"></div>
			</div>
			</div>
			
			<div class="disc_row_b"><div class="sidepadding">
			<div class="header_cat_title" ><a href="#">This doesn't reflect my concerns at all</a></div><div class="header_cat_replies">5</div><div class="header_cat_author"><a href="#">John</a></div><div class="header_cat_lastpost"><a href="#">May 18, 2006</a></div><div class="clear"></div>
			</div>
			</div>
						<div class="disc_row_a">
			<div class="sidepadding">
			<div class="header_cat_title" ><a href="#">This doesn't reflect my concerns at all</a></div><div class="header_cat_replies">5</div><div class="header_cat_author"><a href="#">John</a></div><div class="header_cat_lastpost"><a href="#">May 18, 2006</a></div><div class="clear"></div>
			</div>
			</div>
			
			<div class="disc_row_b"><div class="sidepadding">
			<div class="header_cat_title" ><a href="#">This doesn't reflect my concerns at all</a></div><div class="header_cat_replies">5</div><div class="header_cat_author"><a href="#">John</a></div><div class="header_cat_lastpost"><a href="#">May 18, 2006</a></div><div class="clear"></div>
			</div>
			</div>
						<div class="disc_row_a">
			<div class="sidepadding">
			<div class="header_cat_title" ><a href="#">This doesn't reflect my concerns at all</a></div><div class="header_cat_replies">5</div><div class="header_cat_author"><a href="#">John</a></div><div class="header_cat_lastpost"><a href="#">May 18, 2006</a></div><div class="clear"></div>
			</div>
			</div>
			
			<div class="disc_row_b"><div class="sidepadding">
			<div class="header_cat_title" ><a href="#">This doesn't reflect my concerns at all</a></div><div class="header_cat_replies">5</div><div class="header_cat_author"><a href="#">John</a></div><div class="header_cat_lastpost"><a href="#">May 18, 2006</a></div><div class="clear"></div>
			</div>
			</div>
						<div class="disc_row_a">
			<div class="sidepadding">
			<div class="header_cat_title" ><a href="#">This doesn't reflect my concerns at all</a></div><div class="header_cat_replies">5</div><div class="header_cat_author"><a href="#">John</a></div><div class="header_cat_lastpost"><a href="#">May 18, 2006</a></div><div class="clear"></div>
			</div>
			</div>
			
			<div class="disc_row_b"><div class="sidepadding">
			<div class="header_cat_title" ><a href="#">This doesn't reflect my concerns at all</a></div><div class="header_cat_replies">5</div><div class="header_cat_author"><a href="#">John</a></div><div class="header_cat_lastpost"><a href="#">May 18, 2006</a></div><div class="clear"></div>
			</div>
			</div>
		</div>
		<br />
		<div id="finished">Finished? (We need some style for this)</div>
	  </div>
	  
	  
	</div>
	
	<div id="col-right">
	
		<!--START SIDEBAR -->
<div id="bar">
<a id="SideConcernsTop" name="SideConcernsTop"></a>
	<div class="tabber" id="myTab">
	<!--START Tag Selector -->
	<div id="tagSelector">
		Tag Selected: <b>Safety</b> [ <a href="javascript:goPage(${setting.page});">Clear Selected</a> ]
		<div id="pullDown" style="text-align:right;"><a href="javascript: expandTagSelector();">Pull My Finger</a></div>
		<div id="allTags" style="display: none;">
			<h1>All Currently Available Tags</h1>
				<p>Tag Selector</p>
		</div>
	</div>
		
<!--END Tag Selector -->

			<div id="sidebar_currentTaskContainer" class="tabbertab">
	    	<h2>Concerns</h2>
				<div id="sidebar_concerns"><h4>Concerns</h4>
					 Quisque lobortis placerat felis. Vivamus nisi orci, suscipit sed, semper non, nonummy quis, ligula. Etiam condimentum mauris vitae nisl. Curabitur sem. Quisque eget velit quis dolor convallis tempor. Nulla facilisis hendrerit orci. Nam laoreet enim a erat. Nullam hendrerit ligula eu eros. Suspendisse viverra magna id dui. Nulla dictum ornare velit. Duis a sem. Etiam pulvinar. Nunc at purus at diam eleifend vulputate. Maecenas ullamcorper velit ut leo. Aliquam erat volutpat. Integer leo elit, vehicula at, tempor et, ornare a, augue. Phasellus sagittis. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aenean aliquet faucibus tellus. Donec ipsum. Nullam scelerisque. Etiam lacus. Fusce enim risus, vulputate sed, egestas et, euismod vel, augue. Vestibulum eget turpis. Integer nonummy magna a massa. Sed consectetuer pharetra augue. Praesent dolor. Curabitur ullamcorper.				</div>
	    </div>
			
			<div id="sidebar_discussionContainer" class="tabbertab">
	    	<h2>Other Discussion</h2>
				<div id="sidebar_concerns"><h4>Other Discussion</h4>
					<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Morbi dictum velit eget nunc hendrerit volutpat. Aliquam egestas purus in eros. Quisque lobortis placerat felis. Vivamus nisi orci, suscipit sed, semper non, nonummy quis, ligula. Etiam condimentum mauris vitae nisl.. </p>
				</div>
	    </div>
	    
	    <div id="sidebar_resourcesContainer" class="tabbertab">
	    	<h2>Resources</h2>
				<div id="sidebar_discussion"><h4>Resources</h4>
					<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Morbi dictum velit eget nunc hendrerit volutpat. Aliquam egestas purus in eros. Quisque lobortis placerat felis. Vivamus nisi orci, suscipit sed, semper non, nonummy quis, ligula. Etiam condimentum mauris vitae nisl. Curabitur sem. Quisque eget velit quis dolor convallis tempor. Nulla facilisis hendrerit orci. Nam laoreet enim a erat. Nullam hendrerit ligula eu eros. Suspendisse viverra magna id dui. Nulla dictum ornare velit. Duis a sem. Etiam pulvinar. Nunc at purus at diam eleifend vulputate. Maecenas ullamcorper velit ut leo. Aliquam erat volutpat. Integer leo elit, vehicula at, tempor et, ornare a, augue. Phasellus sagittis. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aenean aliquet faucibus tellus. Donec ipsum. Nullam scelerisque. Etiam lacus. Fusce enim risus, vulputate sed, egestas et, euismod vel, augue. Vestibulum eget turpis. Integer nonummy magna a massa. Sed consectetuer pharetra augue. Praesent dolor. Curabitur ullamcorper. </p>
				</div>
	    </div>
	</div>
</div>
<div id="caughtException"><h4>A Problem has Occured</h4><br>We are sorry but there was a problem accessing the server to complete your request.  <b>Please try refreshing the page.</b></div>
<!--END SIDEBAR -->
	</div>
<div id="clear">
</div>

	
</div>

</div>
<!-- Start Footer -->
<div id="footer_clouds">

	<div id="footer_text">
	<img src="images/footerlogo.png" alt="PGIST Logo" width="156" height="51" class="imgright"/><br />This research is funded by National Science Foundation, Division of Experimental and Integrative Activities, Information Technology Research (ITR) Program, Project Number EIA 0325916, funds managed within the Digital Government Program.    </div>

</div>
<!-- End Footer -->
<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
<script type="text/javascript">
	infoStructure.getTargets();
	dosize();
</script>

</body>

</html:html>

