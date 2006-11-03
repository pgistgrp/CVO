<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<head>
<title>Step 1b: Discuss Summaries - Main</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/lit.css";</style>
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
<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>
<!--
<c:if test="${structure.type == 'sdmap'}">

	<script type='text/javascript' src='scripts/map.js'></script>
	<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAADmWGO07Q7ZeAHCvFNooqIxTwM0brOpm-All5BF6PoaKBxRWWERTgXzfGnh96tes2zXXrBXrWwWigIQ" type="text/javascript"></script>
	<script type='text/javascript' src='/dwr/interface/ProjectAgent.js'></script>
	<style type="text/css">
		v\:* {
		  behavior:url(#default#VML);
		}
	</style>
</c:if>
-->
<!--End SDX Specific  Libraries-->
<script type="text/javascript">
	 function InfoStructure(){
		this.isDivElement = 'object';
	 	 this.getTargets = function(){
				SDAgent.getTargets({isid:${structure.id}}, {
				callback:function(data){
						if (data.successful){
							
							$(infoStructure.isDivElement).innerHTML = data.source.html;
              				eval(data.source.script);
						}else{
							alert(data.reason);
						}
					},
				errorHandler:function(errorString, exception){ 
						alert("get targets error: " + errorString +" "+ exception);
				}
				});
			};
	};

</script>

<html:html>
</head>

<body>

  <!-- Begin the header - loaded from a separate file -->
  <div id="header">
	<!-- Begin header -->
	<jsp:include page="/header.jsp" />
	<!-- End header -->
  </div>
  <!-- End header -->

	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu">
		<div id="headerContainer">
			<div id="headerTitle" class="floatLeft">
				<h3 class="headerColor">Step 1: Brainstorm Concerns</h3>
			</div>
    <div class="headerButton box4 floatLeft"><a href="cctlist.do">1a: Brainstorm Concerns</a></div>
    <div class="headerButtonCurrent floatLeft currentBox"><a href="sdlist.do">2b: Discuss Summaries</A></div>
			<div id="headerNext" class=" floatRight box5"><a href="#">Next Step</A></div>
		</div>
	</div>
	<!-- End header menu -->

	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">

		<!-- begin "overview and instructions" area -->
		<div id="overview" class="box2">
			<h3 class="headerColor">Instructions</h3>
			<p>Several participants have submitted their concerns about the transportation system and the moderator has taken these concerns and clustered them into themes, listed below. Each theme has concerns and their tags associated with it, and the moderator has composed a summary description of the theme. Please review these themes and discuss how you think they approprirately or inappropriately articulate the concerns submitted by participants. Use the right coumn to explore concerns.</p>
			<p><a href="/readmore.jsp">Read more about how this step fits into the bigger picture.</a></p>
		</div>
		<!-- end overview -->

<h3 class="headerColor">Select a Theme to Review and Discuss Concern Summary</h3>


<!-- Here's the div where the contents get placed -->
<div id="object">

</div>
<!-- End contents div -->

<!-- Begin all discussions box -->


				<div class="themeBox floatLeft box6" id="allDiscussionsBox"> <h3 
			class="headerColor"><a href="/sdRoom.do?isid=${structure.id}">Discussion about all concern themes</a></h3> <p>This room is for discussing whether any themes the moderator has identified should be removed, or changed, or if new themes should be added.<span class="smallText"><a href="/sdRoom.do?isid=${structure.id}">more</a></span></p> 
			
			
			
			<span class="smallText">
			<p><strong>Latest post</strong><br /> 

				<c:choose>
					<c:when test="${structure.discussion.lastPost.id != null}">
						"<a href="/sdThread.do?isid=${structure.id}&pid=${structure.discussion.lastPost.id}">${structure.discussion.lastPost.title}</a>"<br />
						by ${structure.discussion.lastPost.owner.loginname}
					</c:when>
					<c:otherwise>
						No current discussions
					</c:otherwise>
				</c:choose>
				
				
			</div>

<div class="clearBoth"></div>
</div>

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

<!-- start feedback form -->
		
		<pg:feedback id="feedbackDiv" action="sdMain.do" />
	
<!-- end feedback form -->

	<!-- Begin footer -->
	<div id="footer">
		<jsp:include page="/footer.jsp" />
	</div>
	<!-- End footer -->
<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
<script type="text/javascript">
	var infoStructure = new InfoStructure(); 
	infoStructure.getTargets();
	//sideBar.assignTitle();
	//if(sideBar.objectId != ""){
	//	sideBar.addIOIDFilter();
	//}else{
	//	sideBar.getSidebarItems();
//	}

</script>
</body>

</html:html>