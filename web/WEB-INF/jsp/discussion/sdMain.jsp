<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<head>
<title>Step 1c: Review Summaries - Main</title>
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
				SDAgent.getTargets({isid:${structure.id}}, <pg:wfinfo/>,{
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
<event:pageunload />
</head>

<body>
    <!-- Start Global Headers  -->
    <wf:nav />
    <wf:subNav />
    <!-- End Global Headers -->

	<!-- #container is the container that wraps around all the main page content -->
	<div style="display: none;" id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>
	<div id="container">

		<!-- begin "overview and instructions" area -->
		<div id="overview" class="box2">
			<pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">
			<h3 class="headerColor">Overview and instructions</h3>
			<p>The moderators have reviewed the concerns and summarized them according to themes. Now you can:</p>
			<ul>
				<li>Review the concern themes</li>
				<li>Discuss how well these summaries represent participants' concerns</li>
				<li>Suggest revisions to the summaries in your discussion comments</li>
			</ul>
			<a href="#" onclick="Effect.toggle('hiddenRM','blind'); return false">Read more about this step</a>
			<p id="hiddenRM" style="display:none">After the brainstorm concluded, the moderators synthesized and summarized the concerns offered by participants. (<pg:url page="lmFaq.do" target="_blank" anchor="step1-created">Read more about the summarization process</pg:url>). Each concern theme is associated with a group of keywords. As you review summaries let us know if you think these summaries are accurate and if you feel any important themes were left out. The moderator will make revisions based on participant comments. The final version of these summaries will be included in the final report of the <em>LIT Challenge</em>. The summaries will also be used in Step 2 when we we assess different "factors" used to evaluate proposed transportation improvement projects.</p>
		</pg:termHighlight>
		</div>
		<!-- end overview -->

<h3 class="headerColor">Select a concern theme to review and discuss</h3>


<!-- Here's the div where the contents get placed -->
<div id="object">

</div>
<!-- End contents div -->

<!-- Begin all discussions box -->


				<div class="themeBox floatLeft box6" id="allDiscussionsBox">
				    <h3 class="headerColor">
				    <pg:url page="/sdRoom.do" params="isid=${structure.id}">Other discussions</pg:url>
                    </h3> <p>Here is a space for other discussions, including  the concerns summarization process and whether any themes should be added or removed. <span class="smallText">
                    <pg:url page="/sdRoom.do" params="isid=${structure.id}">more</pg:url>
                    </span></p> 
			
			
			
			<span class="smallText">
			<p><strong>Latest post</strong><br /> 

				<c:choose>
					<c:when test="${structure.discussion.lastPost.id != null}">
						"<pg:url page="/sdThread.do" params="isid=${structure.id}&pid=${structure.discussion.lastPost.id}">${structure.discussion.lastPost.title}</pg:url>"<br />
						by ${structure.discussion.lastPost.owner.loginname}
					</c:when>
					<c:otherwise>
						No current discussions
					</c:otherwise>
				</c:choose>
				
				
			</div>

<div class="clearBoth"></div>
</div>
<!-- Start Global Headers  -->

<wf:subNav />
<!-- End Global Headers -->

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