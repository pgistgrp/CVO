<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en"><head>
<title>Step 1b: Discuss Summaries - Rooms</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">
@import "styles/lit.css";
</style>
<!-- Temporary Borders used for testing <style type="text/css" media="screen">@import "styles/tempborders.css";</style>-->
<!-- End Site Wide CSS -->
<script language="javascript" type="text/javascript" src="/scripts/tinymce/jscripts/tiny_mce/tiny_mce.js"></script>
<!-- Site Wide JavaScript -->
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->
<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->
<!--SDX Specific  Libraries-->
<script src="scripts/InfoObject.js" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/interface/SDAgent.js'></script>
<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>
<!--End SDX Specific  Libraries-->
<script language="javascript" type="text/javascript">
		tinyMCE.init({
			mode : "exact",
			elements: "txtNewPost",
			theme : "advanced",
			theme_advanced_buttons1 : "bold, italic, bullist, numlist,undo, redo,link",
			theme_advanced_buttons2 : "",
			theme_advanced_buttons3 : "",
			content_css : "/scripts/tinymce/jscripts/tiny_mce/themes/simple/css/bigmce.css"
		});

	</script>
<script type="text/javascript">
		<!--
		///////////////////////////////////////// START INFO OBJECT //////////////////////////////////////
		/*  Requires: InfoObject.js ***************************  See file for element ID needs
		Create a new Instance of InfoObject
		params
		- Structure id
		- Object id
		- Object title
		- Number of Object Discussions
		- Number of Structure Discussions
		- Title of Structure
		- Current Discussion Page

		Methods to define for this Instance
		infoObject.getTargets();
		*/

		var infoObject = new InfoObject("${structure.id}","${object.id}","${object.object}", "${object.discussion.numPosts}", "${structure.discussion.numPosts}", "All Concern Themes", '<%= request.getParameter("page") %>');

		/*************** Get Targets - If IOID is ommitted, return sdcSummary.jsp::else, returns sdcStructureSummary.jsp************** */
		infoObject.getTargets = function(){
			SDAgent.getSummary({isid: this.structureId, ioid: this.objectId  }, {
				callback:function(data){
					if (data.successful){
						$(infoObject.objectDiv).innerHTML = data.source.html;
						}else{
							alert(data.reason);
						}
					},
					errorHandler:function(errorString, exception){
						alert("get targets error:" + errorString + exception);
					}
				});
			};

		/*************** Set Vote************** */
	 	 infoObject.setVote = function(target, id, agree){
					//alert("structure" + infoObject.structureId + "object " + infoObject.objectId + "vote " + agree);
					SDAgent.setVoting({target: target, id: id, agree:agree}, {
					callback:function(data){
							if (data.successful){ 
								//alert("successful");
								var votingDiv = 'voting-'+target+id;
								if($(votingDiv) != undefined){
	              				 	new Effect.Fade(votingDiv, {afterFinish: function(){infoObject.getPosts(); new Effect.Appear(votingDiv);}});
	              				}else{
	              					infoObject.getPosts();	
	              				}
							}else{
								alert(data.reason);
							}
						},
					errorHandler:function(errorString, exception){ 
							alert("setVote error:" + errorString + exception);
					}
					});

		};
	

			//-->
		</script>
<!--[if gte IE 5.5]><![if lt IE 7]>
		<style type="text/css">
		#loading-indicator {
		left: expression( ( 0 + ( ignoreMe2 = document.documentElement.scrollLeft ? document.documentElement.scrollLeft : document.body.scrollLeft ) ) + 'px' );
		top: expression( ( 0 + ( ignoreMe = document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop ) ) + 'px' );
		}
		</style>
		<![endif]><![endif]-->
</head>
<body>

<!-- Begin the header - loaded from a separate file -->
<div id="header">
  <p>Load separate file here</p>
</div>
<!-- End header -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu">
  <div id="headerContainer">
    <div id="headerTitle" class="floatLeft">
      <h3 class="headerColor">Step 1: Brainstorm Concerns</h3>
    </div>
    <div class="headerButton box4 floatLeft"><a href="#">1a: Brainstorm Concerns</a></div>
    <div class="headerButtonCurrent floatLeft currentBox"><a href="#">2b: Discuss Summaries</A></div>
    <div id="headerNext" class=" floatRight box5"><a href="#">Next Step</A></div>
  </div>
</div>
<!-- End header menu -->
<div id="container">
  <script type="text/javascript">
				<c:choose>
				<c:when test="${object.id==null}">
				document.write("<h3 class=\"headerColor\">Summarization of Participant Concerns</h3>");
				</c:when>
				<c:otherwise>
				document.write("<h3 class=\"headerColor\">Summarization of Participant Concerns about ${object.object}</h3>");
				</c:otherwise>
				</c:choose>
			</script>
  <div id="object">
    <h5 id = "targetTitle"></h5>
    <div id="object-content">
      <!-- load object here -->
    </div>
    <!--end object content -->
  </div>
  <!-- end object -->
  <div class="clearBoth"></div>
  <!-- The discussionHeader sits on top of the discussion and contains the title of the
			discussion area, and the sorting menu -->
  <div id="discussionHeader">
    <div class="sectionTitle">
      <h3 class="headerColor">Discussion about ${object.object}</h3>
      <div class="button smallText box5 floatLeft"> <a href="javascript:Effect.toggle('newDiscussion','slide',{duration:1.5});">Start a New Topic</a></div>
    </div>
    <div id="sortingMenu"> sort discussion by:
      <select>
        <option>Option</option>
        <option>Option Option Option Option Option</option>
        <option>Option</option>
        <option>Option</option>
        <option>Option</option>
      </select>
      <br />
      filter discussion by:
      <input type="text">
      or <a href="#">Browse All Tags</A> </div>
</div>
<!-- Begin Discussion Area -->
				<!-- Begin hidden "New topic" DIV -->
				<div style="width:680px;">
				<div id="newDiscussion" style="display: none">
					<div id="newdisc_title" >
						<div class="textright">
						</div>
						<h3 style="display: inline">New Topic</h3>
					</div> <!-- End newdisc_title -->
					<div id="newdisc_content" class="greenBB">
						<div id="newdisc_inner">
							<form>
								<p><label>Post Title</label><br><input maxlength=100 size=100 type="text" id="txtNewPostTitle"/></p>
								<p><label>Your Thoughts</label><br><textarea style="width:100%; height: 200px;" id="txtNewPost"></textarea></p>
								<p><label>Tag your post (comma separated)</label><br><input style="width:100%" id="txtNewPostTags" type="text" /></p>
								<input type="button" onClick="infoObject.createPost();" value="Create Discussion">
							</form>
						</div>
					</div>
				</div>
				</div>
					<!-- End hidden "new topic" DIV -->	
<div id="discussion">
  <!-- load discussion posts -->
</div>

<!-- start feedback form -->
<pg:feedback id="feedbackDiv" action="sdRoom.do" />
<!-- end feedback form --><!-- end container -->


<!-- Start Footer -->
<!-- End Footer -->
<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
<script type="text/javascript">
			infoObject.getPosts();
			//infoObject.assignTargetHeaders();
			infoObject.getTargets();


		</script>
</div>
<!-- start the bottom header menu -->

<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu">
  <div id="headerContainer">
    <div id="headerTitle" class="floatLeft">
      <h3 class="headerColor">Step 1: Brainstorm Concerns</h3>
    </div>
    <div class="headerButton box4 floatLeft"><a href="#">1a: Brainstorm Concerns</a></div>
    <div class="headerButtonCurrent floatLeft currentBox"><a href="#">2b: Discuss Summaries</A></div>
    <div id="headerNext" class=" floatRight box5"><a href="#">Next Step</A></div>
  </div>
</div>
<!-- End header menu -->

<!-- end the bottom header menu -->
</body>
<html:html>
  
<style type="text/css">
			<!--[if IE]>
			#tablediscwidth {width:90%}
			<![endif]-->

			#tablediscwidth {width:100%}
		</style>
</html:html>
