<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
							
							$(infoStructure.isDivElement).innerHTML = data.source.html;
              				eval(data.source.script);
              				 
		              		 if(data.voting == null || data.voting == undefined){
						           $('structure_question').innerHTML = '<span class="smalltext">Does this summary adequately reflect concerns expressed by participants? <a href="javascript:infoStructure.setVote(\'true\');"><img src="images/btn_yes_a.gif" alt="Yes" name="yes" class="button" id="yes"><a href="javascript:infoStructure.setVote(\'false\');"><img src="images/btn_no_a.gif" alt="No" name="no" class="button" id="no"></a></span>';

					          }else{
						           $('structure_question').innerHTML = '<span class="smalltext">Your vote has been recorded. Thank you for your participation.</span>';
						      }
					        displayIndicator(false);
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

	 	 this.setVote = function(agree){
	 	 			displayIndicator(true);
					SDAgent.setVoting({isid: ${structure.id}, agree:agree}, {
					callback:function(data){
							if (data.successful){
	              				 //alert("thank you for your vote");
	              				 new Effect.Fade('structure_question', {afterFinish: function(){infoStructure.getTargets(); new Effect.Appear('structure_question');}});
	              				 displayIndicator(false);
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
			
		function displayIndicator(show){
		if (show){
			$('loading-indicator').style.display = "inline";	
		}else{
			$('loading-indicator').style.display = "none";	
		}
	}
			
</script>

</head>
<!--
<c:choose>
  <c:when test="${structure.type == 'sdmap'}">
 		<body onUnload="GUnload()">
  </c:when>

  <c:otherwise>
  		<body>
 </c:otherwise>
</c:choose>-->

<body>
<div id="container">
		 <div id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>
		<jsp:include page="/header.jsp" />
		<!-- Header -->
		<div id="cont-top">
		<!-- Sub Title -->
		<div id="subheader">
		<h1>Step 1b</h1> <h2>Review Summaries</h2>
		</div>
		<div id="footprints">
		<span class="smalltext"><a href="#">Participate</a> &raquo; <a href="#">Step 1 Brainstorm Concerns</a> &raquo; Step 1b Review Summaries</span>
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
		<!--
		<c:if test="${structure.type == 'sdmap'}">
			<div id="map" style="width: 100%; height: 400px;"></div>
		</c:if>
		-->
		
		<h4>Concern Theme Rooms</h4>
		<div id="object">
			<!-- load discussion rooms -->
		</div><!-- End Object -->
				
		<br />
				<h4>Talk to the Moderator (needs a better name) </h4>
				<div>
				<table width="100%" class="tabledisc">
				          <tr class="disc_row_b">
							<jsp:useBean id="today" class="java.util.Date"/>
							<c:set var="fmtLastPostDate"><fmt:formatDate value="${structure.lastPost.createTime}" pattern="yyyy/MM/dd"/></c:set>
    						<c:set var="fmtToday"><fmt:formatDate value="${today}" pattern="yyyy/MM/dd"/></c:set>
							
							  <c:choose>
							  <c:when test="${fmtToday == fmtLastPostDate}">
							  	 <td width="40" class="textcenter"><img src="/images/balloonactive2.gif" alt="Posts within the last 24 hours" /></td>
							  </c:when>
							  <c:otherwise>
							  	 <td width="40" class="textcenter"><img src="/images/ballooninactive2.gif" alt="No posts within the last 24 hours" /></td>
							  </c:otherwise>
							  </c:choose>
							  <td><div class="padding-sides"><a href="/sdRoom.do?isid=${structure.id}">Discussion about all concern themes</a><br /><span class="smalltext">Do you feel like a corner theme is missing or unnecessary from the above list? Discuss here</span></div></td>
				 		    <td><span class="smalltext" style="font-size: 80%;">
				 		    <c:choose>
						      <c:when test="${structure.lastPost.id != null}">
						     		<div class="padding-sides">
									<a href="/sdThread.do?isid=${structure.id}&pid=${structure.lastPost.id}">${structure.lastPost.title}</a><br />
						      		Posted on: <fmt:formatDate value="${structure.lastPost.createTime}" pattern="MM/dd/yy, hh:mm aaa"/> by: ${structure.lastPost.owner.loginname}
									</div>
						      </c:when>
						      <c:otherwise>
						      		<span class="padding-sides">No current discussions</span>
						      </c:otherwise>
						    </c:choose>          
							</td>
				            <td width="100" class="textcenter"><a href="/sdRoom.do?isid=${structure.id}&ioid=">${structure.numDiscussion}</a></td>
				          </tr>		    
		        </table><br>
				
				<div id="legend" class="smalltext">
					<img src="/images/balloonactive2.gif" alt="active" class="button">
				 Recent Post  <img src="/images/ballooninactive2.gif" alt="active" width="20" height="21" class="button"> No Recent Post </div>
				</div>
		
		
		<td width="280" valign="top" id="sidebarmiddle"><!-- This is the Right Col -->
		<div id="sidebar_container">
		<div id="tagSelector">
			<div id="tagform">
			<h6> Sidebar filtered by:</h6>
			No Selected Tags<br />
			<form action="" method="get">
			Sidebar Filter: 
			  <input name="tagSearch" id="txtmanualFilter" type="text" onKeyDown="sidebarTagSearch(this.value)" />
			</form>
			</div>
			<div id="pullDown" class="textright"><a href="javascript: expandTagSelector();">Expand</a></div>
			<div id="allTags" style="display: none;"></div>
			<div class="clear"></div>
			
		</div>
		<!-- Duplicate tagSelector to work as a spacer during expand effect -->
		<div id="tagSelector_spacer" style="display: none;">
			<h6>Sidebar filtered by:</h6>
			No Selected Tags<br />
			<form action="" method="get">
			Sidebar Filter: 
			  <input name="tagSearch" id="tagSearch_spacer" type="text" style="visibility: hidden;"/>
			</form>
			<div id="pullDown_spacer" class="textright" style="visibility: hidden;">Expand</div>
			<div id="allTags_spacer" style="visibility: hidden;"></div>
			<div class="clear"></div>
		</div>
		<!-- End Duplicate tagSelector to work as a spacer during expand effect -->
		<div id="sidebarSearchResults" style="display: none;"></div>
		  <div id="sidebar_content">
		  <h4>Hot Discussions</h4>
			<!-- mock discussion -->
			<div><span class="participantName"><a
href="">This doesn't reflect my concerns at all</a></span><br />
	  <small>"First of all, only one of my concerns is even touched on in
the summary for safety, even though I<a href="href://">...</a>"</small>
(<span class="participantName"><a href=""
style="color:#0061A8">ChiCummins</a></span> in
<strong>Safety)</strong><br />
	  <span class="tags"><a
href="javascript:getConcernsByTag(902);">commuting</a></span>
	  <span class="tags"><a
href="javascript:getConcernsByTag(885);">congestion</a></span>
	  <span class="tags"><a href="javascript:getConcernsByTag(894);">hov
lanes</a></span>
  </div>
	<br />
	<div><span class="participantName"><a href="">Works for me
</a></span><br />
	  <small>"Looks good... I like what PetePeterson said about
encouraging pedestrian traffic downtown during the<a
href="href://">...</a>"</small> (<a href="href://"
style="color:#0061A8"><span class="participantName">Ida
Fitzgerald</span></a> in<strong> Congestion) </strong><br />
	  <span class="tags"><a
href="javascript:getConcernsByTag(902);">pollution</a></span>
	  <span class="tags"><a
href="javascript:getConcernsByTag(885);">bicycles</a></span>
  </div>
	<br />
	<div>
	  <span class="participantName"><a href="">You're missing the
point </a></span><br />
	  <small>"It's not about how many cars are on the road, it's about how
congested the roads are.  It's more<a href="href://">...</a>"</small>
<span class="participantName">(<a href="href://"
style="color:#0061A8">Roy Thompson</a> </span>in
<strong>Congestion</strong>)<br />
	  <span class="tags"><a
href="javascript:getConcernsByTag(902);">commuting</a></span>
	  <span class="tags"><a
href="javascript:getConcernsByTag(885);">congestion</a></span>
	  <span class="tags"><a
href="javascript:getConcernsByTag(894);">capacity</a></span>
  </div>
<br />
	<div><span class="participantName"><a href="">Focus on
providing service to everybody </a></span><br />
	  <small>"The summary is decent, but you need to rewrite it so that
people get the message that the bus system<a
href="href://">...</a>"</small> (<span class="participantName"
style="text-align:right"><a href="href://"
style="color:#0061A8">SGould48</a></span> in
<strong>Accessibility</strong>)<br />
	  <span class="tags"><a
href="javascript:getConcernsByTag(902);">accessibility</a></span>
	  <span class="tags"><a
href="javascript:getConcernsByTag(885);">metro</a></span>
	  <span class="tags"><a
href="javascript:getConcernsByTag(894);">access</a></span>
  </div>
	<br />
	<div><span class="participantName"><a href="">Let's move on
</a></span><br />
	  <small>"I don't think we're going to get a consensus on the exact
wording of the summary, but don't we all<a
href="href://">...</a>"</small> (<span class="participantName"
style="text-align:right"><a href="href://"
style="color:#0061A8">XueCrain</a></span> in
<strong>Accessibility</strong>) <br />
	  <span class="tags"><a
href="javascript:getConcernsByTag(902);">disabled</a></span>
	  <span class="tags"><a
href="javascript:getConcernsByTag(885);">access</a></span>
	  <span class="tags"><a
href="javascript:getConcernsByTag(894);">commuting</a></span>
  </div>
  <!-- end mock discussion-->
<!-- PREV and NEXT Buttons -->
<span class="textright"><img src="images/btn_next_a.gif" alt="Next" name="next" class="button" id="next" onMouseOver="MM_swapImage('next','','images/btn_next_b.gif',1)" onMouseOut="MM_swapImgRestore()"></span><img src="images/btn_prev_a.gif" alt="Prev" name="prev" class="button" id="prev" onMouseOver="MM_swapImage('prev','','images/btn_prev_b.gif',1)" onMouseOut="MM_swapImgRestore()">
<!-- End PREV and NEXT Buttons -->
		<div id="caughtException"><h4>A Problem has Occured</h4><br>We are sorry but there was a problem accessing the server to complete your request.  <b>Please try refreshing the page.</b></div>
		
		</div><!-- End sidebarcontents-->
		</div><!-- sidebar container-->
		 </td><!-- End Right Col -->
		</tr>
		
		</table>
		<div id="sidebarbottom" style="text-align:right;"><img src="images/sidebar_bottom.gif" alt="sidebarbottom" /></div>
		<!--end sidebar-->
		</div><!-- End cont-main -->
</div> <!-- End container -->

<!-- Start Footer -->
<jsp:include page="/footer.jsp" />

<!-- End Footer -->
<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
<script type="text/javascript">
	var infoStructure = new InfoStructure(); 
	infoStructure.getTargets();
/*
	<c:if test="${structure.type == 'sdmap'}">
		var pgistmap = new PGISTMap('map');
		var idList = new Array();
		<c:forEach var="infoObject" items="${structure.infoObjects}">
			idList[idList.length] = '${infoObject.id}';
		</c:forEach>
		pgistmap.setProjectList(idList);
		pgistmap.projectClickHandler = function(projId){
			//this is a callback function to load the sidebar_bottom
			
		}
	</c:if>
	*/
</script>
</body>

</html:html>