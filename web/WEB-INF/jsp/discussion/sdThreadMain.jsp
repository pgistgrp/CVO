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
<title>LIT Discussion: ${post.title}</title>
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
		/* global vars */
			var countPerPage = 10; //numer of posts per page
			var lastPage = 1
		/* end global vars */
		 function createReply(page){
		 	lastPage = page;
		 	displayIndicator(true);
			var newReplyTitle = $('txtnewReplyTitle').value;
			var newReply = validateInput($('txtnewReply').value);
			var newReplyTags = $('newReplyTags').value;
		    SDAgent.createReply({isid:${structure.id}, pid:${post.id}, title:newReplyTitle, content:newReply, tags: newReplyTags}, {
		      callback:function(data){
		          if (data.successful){     
          				$('txtnewReplyTitle').value = 'Re: ${post.title} ';
						$('txtnewReply').value = '';
						$('newReplyTags').value = '';
						if (${setting.page} != lastPage){
							location.href='sdThread.do?isid=${structure.id}&pid=${post.id}&ioid=${object.id}&page=' + lastPage;
						}else{
							getReplies();	
						}
						displayIndicator(false);
						
		          }else{
		            alert(data.reason);
		            displayIndicator(false);
		          }
		      },
		      errorHandler:function(errorString, exception){
		          alert("createReply Error" + errorString + exception);
		      }
		    });
		  }
		
		function validateInput(string){
			string=string.replace(/>/g,"//>//");
			string=string.replace(/</g,"//<//");
			string=string.replace(/\n/g,"<br>");

			return string;
	}
	
		 function getReplies(){
		 		displayIndicator(true);
		 		var ioid = '${object.id}';
		 		if(ioid == ''){
		 			ioid = null;
		 		}else{
		 			ioid = '${object.id}';
		 		}
		 	
		 		var page = 1;
		 		if (<%= request.getParameter("page") %> != null){
		 			page = <%= request.getParameter("page") %>;	
		 		}
		 		

		      SDAgent.getReplies({isid:${structure.id}, ioid:ioid, postid:${post.id}, page: page, count: countPerPage}, {
		      callback:function(data){
		          if (data.successful){
		          			
		          			$('replies-cont').innerHTML = data.html;         
		          			displayIndicator(false);
		          }else{
		            alert("data.successful != true: " + data.reason);
		            displayIndicator(false);
		          }
		      },
		      errorHandler:function(errorString, exception){
		          alert("getReplies Error" + errorString + exception);
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

</head>
<body onLoad="MM_preloadImages('images/btn_postreply_b.gif')"> 
<div id="container">
<jsp:include page="/header.jsp" />
<!-- Header -->
  <div id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>

<div id="cont-top">


<!-- Sub Title -->
<div id="subheader">
<h1>Step 1 Brainstorm Concerns:</h1> <h2>Discuss Concerns Summary</h2>
</div>
<div id="footprints">
<span class="smalltext"><a href="#">Participate</a> &raquo; <a href="#">Step 1 Brainstorm Concerns</a> &raquo; <a href="sd.do?isid=${structure.id}">Step 1b Review Summaries</a> &raquo; <a href="sdRoom.do?isid=${structure.id}&ioid=${object.id}">${object.object}</a> &raquo; ${post.title}</span>
</div>
<!-- End Sub Title -->


<!-- Overview SpiffyBox -->
<div class="cssbox">
<div class="cssbox_head">
<h3>Overview</h3>
</div>
<div class="cssbox_body">
<p>Several participants have submitted their concerns about the transportation system and the moderator has taken these concerns and clustered them into themes, listed below. Each theme has concerns and their tags associated with it, and the moderator has composed a summary description of the theme. Please review these themes and discuss how you think they appropriately or innapropriately articulate the concerns submitted by participants. Use the right column (the sidebar) to explore concerns. </p>
				
				<p>[ <a href="/readmore.jsp">Read more about how this step fits into the bigger picture.</a> ]</p>
</div>
</div>
<!-- End Overview -->

</div> <!-- End cont-top -->

<div class="backToDiscussion">
			<div id="tselector">
			<!--<form id="Tselector" name="ThemeSelector" method="post" action="">-->
			  <label>
			  Jump To:
			  <select name="selecttheme" id="selecttheme" onChange="javascript: location.href='sdRoom.do?isid=${structure.id}&ioid=' + this.value;">		  
			    <option value = "${object.id}">Select a Theme</option>
			    <option value = "">All Concern Themes</option>
			   <c:forEach var="infoObject" items="${structure.infoObjects}">
			       <option value="${infoObject.id}">${infoObject.object}</option>
			    </c:forEach>	
		      </select>
			  </label>
			<!--  </form> -->
			</div>	
			  <div id="backdisc"><a href="sdRoom.do?isid=${structure.id}&ioid=${object.id}"><img src="images/btn_back_a.gif" alt="back" name="back" class="button" id="back" onMouseOver="MM_swapImage('back','','images/btn_back_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a></div>	  
</div>

<div id="cont-main">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td id="maintop"><img src="" alt="" height="1" width="1"/></td>
<td><img src="images/sidebar_top.gif" alt="sidebartop" /></td>
</tr>
<tr>
<td valign="top" id="maincontent">
<!-- Main Content starts Here-->

<div id="object">
	
	<div id="post${post.id}">
		 <h3>
		 	<a href="sd.do?isid=${structure.id}">All concern themes</a> &raquo;  
		 	<c:choose>
		 		<c:when test="${object != null}">
		 			<a href="sdRoom.do?isid=${structure.id}&ioid=${object.id}">${object.object.theme.title}</a> &raquo;   
		 		</c:when>
		 		<c:otherwise>
		 			<a href="sdRoom.do?isid=${structure.id}">All Concern Themes List</a> &raquo;   
		 		</c:otherwise>
		 	</c:choose>
		 	${post.title}
		 </h3>
		 
		 <div class="bluetitle">
		 	<span class="padding-sides"><strong>${post.title}</strong> - <small>Posted on <fmt:formatDate value="${post.createTime}" pattern="MM/dd/yy, hh:mm aaa"/> by: ${post.owner.loginname}</small></span>
		 </div>
		<div class="padding">
		${post.content}
		<c:if test="${fn:length(reply.tags) != 0}">
		<ul class="tagsList"><strong>tags: </strong>
			<logic:iterate id="tag" name="post" property="tags">
				<li class="tagsList">${tag.name}</li>
			</logic:iterate>
		<small>- click on a tag to view other discussions with the same tag.</small>
		</ul>
		</c:if>
		</div>
		<div id="replyTo${post.id}" style="text-align: right;"><a href="javascript:location.href='#replyAnchor';  new Effect.Pulsate('newReply', {duration: .8, from: 0.5}); void(0);"><img src="images/btn_postreply_a.gif" alt="Post Reply" name="postreplya" width="74" height="20" class="button" id="postreplya" onMouseOver="MM_swapImage('postreplya','','images/btn_postreply_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a></div>
	</div><!--end post-->

   <div id="extrapadding" class="padding-sides">
	<div id="replies-cont" class="greyscheme">
		<!--load replies and post reply form here -->
	</div><!-- End replies-cont -->
	<br />
	</div><!-- End extrapadding -->

	  
	</div>


</td>
<td width="280" valign="top" id="sidebarmiddle"><!-- This is the Right Col -->
<div id="sidebar_container">
<div id="tagSelector">
	<div id="tagform">
	<h6>Sidebar filtered by:</h6>
	[Tags ] [Tags] [Tags]<br />
	<form action="" method="get">
	Sidebar Filter: 
	  <input name="tagSearch" id="txtmanualFilter" type="text" onKeyDown="sidebarTagSearch(this.value)" />
	</form>
	</div>
	<div id="pullDown" class="textright"><a href="javascript: expandTagSelector();">Expand</a></div>
	<div id="allTags" style="display: none;"></div>
	<div class="clear"></div>
	
</div>
<div id="tagSelector_spacer" style="display: none;"><!-- Duplicate tagSelector to work as a spacer during expand effect -->
	<h6>Sidebar filtered by:</h6>
	[Tags ] [Tags] [Tags]<br />
	<form action="" method="get">
	Sidebar Filter: 
	  <input name="tagSearch" id="tagSearch_spacer" type="text" style="visibility: hidden;"/>
	</form>
	<div id="pullDown_spacer" class="textright" style="visibility: hidden;">Expand</div>
	<div id="allTags_spacer" style="visibility: hidden;"></div>
	<div class="clear"></div>
</div>
<div id="sidebarSearchResults" style="display: none;"></div>
  <div id="sidebar_content">
 <h3>Related Discussions</h3>
<!-- start mock disc -->
<div id="reply2152" class="replies">
		 <div id="replies_title" class="bluetitle">
		 	<span class="padding-sides"><strong><a href="#">Test</a></strong> - <small>Posted on 09/25/06, 09:51 AM by: <strong>admin</strong> in discussion room <strong>Safety</strong></small></span>
		 </div>
		<div class="padding">
		<small>This text will be limited to 100 characters... </small>
		<br />
		<ul class="tagsList"><span class="smalltext"><strong>tags:</strong></small>
			<li class="tagsList"><small><a href="#">asdf</a></small></li>
		</ul>
		</div>
</div>

<!-- end mock disc -->
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
<div class="backToDiscussion"><a href="sdRoom.do?isid=${structure.id}&ioid=${object.id}"><img src="images/btn_back_a.gif" alt="back2" name="back" class="button" id="back2" onMouseOver="MM_swapImage('back2','','images/btn_back_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a></div>

<div id="finished" class="greenBB">
	<h4>Ready for the next step?</h4><br />
	<p>Click on the continue button to go on to step 2 where you will review and weigh criteria to evaluate proposed transportation projects.  Go back to your <a href="main.do">home page</a> or  <img src="images/btn_gcontinue_a.gif" alt="Continue" name="continue" class="button" id="continue" onMouseOver="MM_swapImage('continue','','images/btn_gcontinue_b.gif',1)" onMouseOut="MM_swapImgRestore()"></p>
</div>
<!-- start feedback form -->

		<pg:feedback id="feedbackDiv" action="sdThread.do" />
		
<!-- end feedback form -->
</div>
<!-- End cont-main -->
</div> <!-- End container -->
<!-- Start Footer -->
<jsp:include page="/footer.jsp" />


<!-- End Footer -->
<script type="text/javascript">
	getReplies();
</script>
</html:html>


