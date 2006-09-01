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
		/* end global vars */
		 function createReply(){
			var newReplyTitle = $('txtnewReplyTitle').value;
			var newReply = $('txtnewReply').value;
			var newReplyTags = $('newReplyTags').value;
		    SDAgent.createReply({isid:${structure.id}, pid:${post.id}, title:newReplyTitle, content:newReply, tags: newReplyTags}, {
		      callback:function(data){
		          if (data.successful){
		          		displayIndicator(true);
		          		getReplies();           
          				$('txtnewReplyTitle').value = 'Re: ${post.title} ';
						$('txtnewReply').value = '';
						$('newReplyTags').value = '';
						displayIndicator(false);
		          }else{
		            alert(data.reason);
		          }
		      },
		      errorHandler:function(errorString, exception){
		          alert("createReply Error" + errorString + exception);
		      }
		    });
		  }
		
		 function getReplies(){
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
		          			displayIndicator(true);
		          			$('postReplies').innerHTML = data.html;         
		          			displayIndicator(false);
		          }else{
		            alert("data.successful != true: " + data.reason);
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

<div class="backToDiscussion">
			<div id="tselector">
			<!--<form id="Tselector" name="ThemeSelector" method="post" action="">-->
			  <label>
			  Jump To:
			  <select name="selecttheme" id="selecttheme" onChange="javascript: location.href='sdRoom.do?isid=${structure.id}&ioid=' + this.value;">		  
			    <option value = "${object.id}">Select a Theme</option>
			   <c:forEach var="infoObject" items="${structure.infoObjects}">
			       <option value="${infoObject.id}">${infoObject.object}</option>
			    </c:forEach>	
		      </select>
			  </label>
			<!--  </form> -->
			</div>	
			  <div id="backdisc"><a href="sdRoom.do?isid=${structure.id}&ioid=${object.id}">Back to Discussion</a></div>	  
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
	
	<div id="post">
		 <h3><a href="sd.do?isid=${structure.id}">All concern themes</a> &raquo;  <a href="sdRoom.do?isid=${structure.id}&ioid=${object.id}">${object.object.theme.title}</a> &raquo;   ${post.title}</h3>
		<small>Posted on <fmt:formatDate value="${post.createTime}" pattern="MM/dd/yy, hh:mm aaa"/> by: ${post.owner.loginname}</small>
		<p>${post.content}</p>
		<c:if test="${fn:length(reply.tags) != 0}">
		<ul class="tagsList"><strong>tags: </strong>
			<logic:iterate id="tag" name="post" property="tags">
				<li class="tagsList">${tag.name}</li>
			</logic:iterate>
		<small>- click on a tag to view other discussions with the same tag.</small>
		</ul>
		</c:if>
		<div id="replyTo${post.id}" style="text-align: right;"><a href="javascript:location.href='#replyAnchor';  new Effect.Pulsate('newReply', {duration: .8, from: 0.5}); void(0);">Reply to this Post</a></div>
	</div><!--end post-->
   <div id="extrapadding" class="padding-sides">
	<div id="replies-cont" class="greyscheme">
			<div id="postReplies">
				<!-- replies will be loaded here via DWR -->
			</div>
			<a name="replyAnchor"></a>
			<div id="newReply" class="greenBB" style="padding: 5px 10px; margin-top: 20px; border-top: 2px solid #C0D7F6">
				<h3>Post a Reply</h3>
				<form>
					<p><label>Post Title</label><br><input style="width:100%" type="text" value="Re: ${post.title} " id="txtnewReplyTitle"/></p>
					<p><label>Your Thoughts</label><br><textarea style="width:100%; height: 150px;" id="txtnewReply"></textarea></p>
					<p><label>Tag your post (comma separated)</label><br><input style="width:100%" id="newReplyTags" type="text" /></p>
					<input type="button" onClick="createReply();" value="Submit Reply">
				</form>
			</div>
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


<div id="caughtException"><h4>A Problem has Occured</h4><br>We are sorry but there was a problem accessing the server to complete your request.  <b>Please try refreshing the page.</b></div>

</div><!-- End sidebarcontents-->
</div><!-- sidebar container-->

 </td><!-- End Right Col -->
</tr>

</table>
<div id="sidebarbottom" style="text-align:right;"><img src="images/sidebar_bottom.gif" alt="sidebarbottom" /></div>


</div>
<!-- End cont-main -->
<div class="backToDiscussion"><a href="sdRoom.do?isid=${structure.id}&ioid=${object.id}">Back to Discussion</a></div>
</div> <!-- End container -->
<!-- Start Footer -->
<jsp:include page="/footer.jsp" />

<!-- End Footer -->
<script type="text/javascript">
	getReplies();
</script>
</html:html>


