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
		 function createReply(){
			var newReplyTitle = $('txtnewReplyTitle').value;
			var newReply = $('txtnewReply').value;
			var newReplyTags = $('newReplyTags').value;
		    SDAgent.createReply({isid:${structure.id}, pid:${post.id}, title:newReplyTitle, content:newReply, tags: newReplyTags}, {
		      callback:function(data){
		          if (data.successful){
		          		getReplies();           
          				$('txtnewReplyTitle').value = '';
						$('txtnewReply').value = '';
						$('newReplyTags').value = '';
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
		      SDAgent.getReplies({isid:${structure.id}, ioid:${object.id}, postid:${post.id}}, {
		      callback:function(data){
		          if (data.successful){
		          			$('postReplies').innerHTML = data.html;         
		          }else{
		            alert("data.successful != true: " + data.reason);
		          }
		      },
		      errorHandler:function(errorString, exception){
		          alert("getReplies Error" + errorString + exception);
		      }
		    });
		  }
  
</script>

</head>
<body> 
<div id="container">
  <div id="col-left">
	
	<div id="backToDiscussion" style="text-align: right;"><a href="sd.do?isid=${structure.id}">Back to Discussion</a></div>
	<div id="post" class="blueBB">
		 <h3><a href="sd.do?isid=${structure.id}">${object.object.theme.title}</a> >> ${post.title}</h3>
		<small>Posted on <fmt:formatDate value="${post.createTime}" pattern="MM/dd/yy, hh:mm aaa"/> by: ${post.owner.loginname}</small>
		<p>${post.content}</p>
		<ul class="tagsList"><strong>tags: </strong>
			<logic:iterate id="tag" name="post" property="tags">
				<li class="tagsList">${tag.name}</li>
			</logic:iterate>
		<small>- click on a tag to view other discussions with the same tag.</small>
		<div id="replyTo${post.id}" style="text-align: right;"><a href="javascript:location.href='#replyAnchor';  new Effect.Pulsate('newReply', {duration: .8, from: 0.5}); void(0);">Reply to this Post</a></div>
	</div>
	<div id="replies" class="blueBB">
			<div id="postReplies">
				<!-- replies will be loaded here via DWR -->
			</div>
			<a name="replyAnchor"></a>
			<div id="newReply" class="greenBB" style="padding: 5px 10px; margin-top: 20px; border-top: 2px solid #C0D7F6">
				<h3>Post a Reply</h3>
				<form>
					<p><label>Post Title</label><br><input style="width:100%" type="text" value="Re: ${post.title} " id="txtnewReplyTitle"/></p>
					<p><label>Your Thoughts</label><br><textarea style="width:100%" id="txtnewReply"></textarea></p>
					<p><label>Tag your post (comma separated)</label><br><input style="width:100%" id="newReplyTags" type="text" /></p>
					<input type="button" onclick="createReply();" value="Submit Reply">
				</form>
			</div>
	</div>
	</div>
  <div id="col-right">
	<jsp:include page="sdTabber.jsp" />

</div>
<!-- Start Footer -->
<div id="footer_clouds">

	<div id="footer_text">
	<img src="images/footerlogo.png" alt="PGIST Logo" width="156" height="51" class="imgright"/><br />This research is funded by National Science Foundation, Division of Experimental and Integrative Activities, Information Technology Research (ITR) Program, Project Number EIA 0325916, funds managed within the Digital Government Program.    </div>

</div>
<!-- End Footer -->
<script type="text/javascript">
	getReplies();
</script>
</html:html>