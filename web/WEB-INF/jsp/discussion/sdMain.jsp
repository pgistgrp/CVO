<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<head>
<title>Step 1b: Review Summaries - Main</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/lit.css";</style>
<!-- End Site Wide CSS -->
<!-- Site Wide JavaScript -->
<script language="JavaScript" src="scripts/qtip.js" type="text/JavaScript"></script>
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/globalSnippits.js" type="text/javascript"></script>
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
	 	displayIndicator(true);
		this.isDivElement = 'object';
	 	 this.getTargets = function(){
				SDAgent.getTargets({isid:${structure.id}}, {
				callback:function(data){
						if (data.successful){
							displayIndicator(false);
							$(infoStructure.isDivElement).innerHTML = data.source.html;
              				eval(data.source.script);
						}else{
							displayIndicator(false);
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
	<jsp:include page="sdcHeader.jsp" />
<!-- End header menu -->


	<!-- #container is the container that wraps around all the main page content -->
	<div style="display: none;" id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>
	<div id="container">

		<!-- begin "overview and instructions" area -->
		<div id="overview" class="box2">
			<h3 class="headerColor">Instructions</h3>
			<p>Thanks to everyone who submitted their concerns about our regional transportation system. The moderator has reviewed the concerns and identified themes, listed below. Each theme is associated with a group of concern tags.</p>
			There are two main ways to participate in Step 1b.<ul>
			<li>Read the summary of each theme and discuss how well you feel it describes the diversity of concerns submitted by participants.</li>
			<li>Indicate whether you feel the current summary adequately reflects participants' concerns by clicking the thumb's up or thumb's down button.</li>
			</ul>
			<p><a href="/readmore.jsp">Read more about how this step fits into the bigger picture.</a></p>
			<p>[<a href="#"  class="glossHighlight" title="As participants add concerns, the moderator will review a subset of these concerns by sampling the tags participants used to label their concerns.  While reading the concerns, the moderator will place these concerns into topical themes, by creating themes and adding tags to these themes.  Due to the many concerns added by participants, it is possible that the moderator will not be able to review every concern, or every group of concerns tagged with the same tag.  Therefore, your responsibility as a participant is to review these summaries and discuss how these summaries might be re-written differently.">How are these summaries created?</a>] [<a href="#"  class="glossHighlight" title="The summaries are important for three main reasons.  First, summaries represent a synthesis of the various ideas and concerns offered by participants. Therefore they are useful to help us all to understand.  Also, summaries will be used by participants in Step 2 to assess the relevance and importance of different criteria planners use to evalute proposed transportation projects. Finally, summaries will be shared with decision makers makers to concisely show what is of concern to participants.">Why are the summaries important?</a>] [<a href="#"  class="glossHighlight" title="When a significant number of participants indicate that they disagree with a summary, the moderator will review discussions and suggested revisions by participants. If it appears that a revision is necessary, the moderator will make changes and notify participants. At the conclusion of this step, the summaries will no longer be revised. Some participants may disagree with the 'final' version of a summary. These participants can express this disagreement in the poll as well as their discussion comments about the summary. These comments will always be available for review to all participants and will also be available to decision makers as an appendix to the final report.">How are summaries revised?</a>] [<a href="#"  class="glossHighlight" title="You and your fellow participants will initially read and review these summaries.  The moderator may revise these summaries based on participant feedback and discussion.  At the conclusion of this step, the summaries will cease to be updated and will be included in a final report given to decision makers.  All the comments and discussion about these summaries will also be included, to showcase the variety of opinions.">Who reads these summaries?</a>]
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
	<jsp:include page="sdcHeader.jsp" />
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