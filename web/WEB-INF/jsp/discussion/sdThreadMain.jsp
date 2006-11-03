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
<title style="text-transform:capitalize;">Step 1b: ${post.title}</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/lit.css";</style>
<!-- Temporary Borders used for testing <style type="text/css" media="screen">@import "styles/tempborders.css";</style>-->
<!-- End Site Wide CSS -->

<script language="javascript" type="text/javascript" src="scripts/tinymce/jscripts/tiny_mce/tiny_mce.js"></script>

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
<!--End SDX Specific  Libraries-->



<script type="text/javascript">
	
	tinyMCE.init({
	mode : "exact",
	theme : "advanced",
	theme_advanced_buttons1 : "bold, italic, bullist, numlist,undo, redo,link",
	theme_advanced_buttons2 : "",
	theme_advanced_buttons3 : "",
	content_css : "/scripts/tinymce/jscripts/tiny_mce/themes/simple/css/bigmce.css"
});

	
			///////////////////////////////////////// START INFO OBJECT //////////////////////////////////////

		var io = new Object;
		//Global Var Settings
		io.structureId = "${structure.id}";
		io.objectId = "${object.id}";
		io.currentFilter = '';
		io.currentPage = 1;
		io.replyCount = 15;
		
		/*----Input ID's - these id's of input elements have changing content or gets read by the javascript ---- */
	 	 io.newReplyTagsInput = "txtNewReplyTags"; //new post tags input box
	 	 io.newReplyTitleInput = "txtNewReplyTitle";
	 	 
	 	 /*----Divs - these divs have changing content or gets read by the javascript ---- */
	 	 io.objectDiv =  'object-content'; //div that contains the object
	 	 io.discussionDiv = 'discussion'; //div that contains the discussion
	 	 io.votingQuestionDiv = 'structure_question' //div that contains the voting question
	 	 io.filterAnchor = '#filterJump';
		
		/*************** Get Targets - If IOID is ommitted, return sdcSummary.jsp::else, returns sdcStructureSummary.jsp************** */
		io.getTargets = function(){
			SDAgent.getSummary({isid: io.structureId, ioid: io.objectId  }, {
				callback:function(data){
					if (data.successful){
						$(io.objectDiv).innerHTML = data.source.html;
						}else{
							alert(data.reason);
						}
					},
					errorHandler:function(errorString, exception){
						alert("get targets error:" + errorString + exception);
					}
				});
			};
			
		io.getReplies = function(){
		      SDAgent.getReplies({isid:${structure.id}, ioid:io.objectId, postid:${post.id}, page: io.currentPage, count: io.replyCount}, {
		      callback:function(data){
		          if (data.successful){
		          			$(io.discussionDiv).innerHTML = data.html;         
		          }else{
		            alert("get replies error " + data.reason);
		          }
		      },
			  async:false,
		      errorHandler:function(errorString, exception){
		          alert("getReplies Error" + errorString + exception);
		      }
		    });
			tinyMCE.idCounter=0;
			tinyMCE.execCommand('mceAddControl',false,'txtnewReply');
		  }
			
			
		/*
		function removeDoubleQuotes(str){
		charToRemove = '"';
		regExp = new RegExp("["+charToRemove+"]","g");
		return str.replace(regExp,"");
		
		}*/

		io.createReply = function(){
			var title = $(io.newReplyTitleInput).value;
			var newReply = tinyMCE.getContent();
			var tags = $(io.newReplyTagsInput).value;
		    SDAgent.createReply({isid:${structure.id}, pid:${post.id}, title:title, content:newReply, tags: tags}, {
		      callback:function(data){
		          if (data.successful){     
						resetNewReplyForm();
						io.setVote('reply', data.id, 'true');	
		          }else{
		            alert(data.reason);
		          }
		      },
		      errorHandler:function(errorString, exception){
		          alert("createReply Error" + errorString + exception);
		      }
		    });
		  }
		
		io.setQuote = function(replyId){
			alert($('replyContent'+replyId).innerHTML);	
		}
		
		function resetNewReplyForm(){
			$(io.newReplyTitleInput).value = '';
			tinyMCE.setContent('');
			$(io.newReplyTagsInput).value = '';	
		}
		
		/*
		function validateInput(string){
			string=string.replace(/>/g,"//>//");
			string=string.replace(/</g,"//<//");
			string=string.replace(/\n/g,"<br>");

			return string;
		}*/
	
		/*************** Set Vote************** */
	 	 io.setVote = function(target, id, agree){
					//alert("structure" + infoObject.structureId + "object " + infoObject.objectId + "vote " + agree);
					SDAgent.setVoting({target: target, id: id, agree:agree}, {
					callback:function(data){
							if (data.successful){ 
								var votingDiv = 'voting-'+target+id;
								if($(votingDiv) != undefined){
	              				 	new Effect.Fade(votingDiv, {afterFinish: function(){io.getReplies(); new Effect.Appear(votingDiv);}});
	              				}else{
	              					io.getReplies();	
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
	
-->
</script>
</head>
<body> 
	
	
<!--
<div class="backToDiscussion">
			<div id="tselector">>
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
			<!--  </form> 
			</div>	
			
-->
	<!--
	
	<div id="post${post.id}">
		 <h5>
		 	<a href="sd.do?isid=${structure.id}">All concern themes</a> &raquo;  
		 	<c:choose>
		 		<c:when test="${object != null}">
		 			<a href="sdRoom.do?isid=${structure.id}&ioid=${object.id}" style="text-transform:capitalize;">${object.object.theme.title}</a> &raquo;   
		 		</c:when>
		 		<c:otherwise>
		 			<a href="sdRoom.do?isid=${structure.id}">All Concern Themes List</a> &raquo;   
		 		</c:otherwise>
		 	</c:choose>
		 	<span style="text-transform:capitalize;">${post.title}</span>
		 </h5>
		 
		 <div class="bluetitle">

		 	<div id="voting-post${post.id}" class="votingDisc">
			 	${post.numAgree} of ${post.numVote} participants agree with ${post.owner.loginname} 

			 	<c:choose>
			 		<c:when test="${post.object == null}">
						<a href="javascript:setVote('post',${post.id}, 'false');"><img src="/images/btn_thumbsdown.png" alt="I disagree!" border="0"/></a> 
			 			<a href="javascript:setVote('post',${post.id}, 'true');"><img src="/images/btn_thumbsup.png" alt="I agree!" border="0"/></a>
					</c:when>
					<c:otherwise>
						<img src="images/btn_thumbsdown_off.png" alt="Disabled Button"/> <img src="images/btn_thumbsup_off.png" alt="Disabled Button"/>
					</c:otherwise>
				</c:choose>

			</div>
		 	
		 	<span class="padding-sides"><strong style="text-transform:capitalize;">${post.title}</strong> - <small>Posted on <fmt:formatDate value="${post.createTime}" pattern="MM/dd/yy, hh:mm aaa"/> by: ${post.owner.loginname}</small></span>

		 </div>
		<div class="padding">
		${post.content}
		
		<c:if test="${fn:length(post.tags) != 0}">
		<p>
		<ul class="tagsList"><strong>Tags: </strong>
			<c:forEach items="${post.tags}" var="tag">
					<li class="tagsList"><a href="javascript:sideBar.changeCurrentFilter(${tag.id});">${tag.name}</a></li>
			</c:forEach>
		</ul>
		<small>- click on a tag to view other discussions with the same tag.</small>
		</p>
		</c:if>
		</div>
		<div id="replyTo${post.id}" style="text-align: right;"><a href="javascript:location.href='#replyAnchor';  new Effect.Pulsate('newReply', {duration: .8, from: 0.5}); void(0);"><img src="images/btn_postreply_a.gif" alt="Post Reply" name="postreplya" width="69" height="19" class="button" id="postreplya" onMouseOver="MM_swapImage('postreplya','','images/btn_postreply_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a></div>
	</div><!--end post-->


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
  <div class="clearBoth"></div><a name="filterJump"></a>
  <!-- The discussionHeader sits on top of the discussion and contains the title of the
			discussion area, and the sorting menu -->
  <div id="discussionHeader">
    <div class="sectionTitle">
      <h3 class="headerColor">${object.discussion.numPosts} Discussion about ${post.title}</h3>
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
								<input type="button" onClick="io.createPost();" value="Create Discussion">
							</form>
						</div>
					</div>
				</div>
				</div>
					<!-- End hidden "new topic" DIV -->	
<!-- START Discussion Topic (Discussion Post) -->
<div id="discussion-topic">
				<div id="discussion${post.id}" class="discussionRow">
						<c:choose>
								<c:when test="${baseuser.id == post.owner.id}">
									<div class="discussionRowHeader box6">			
								</c:when>
								<c:otherwise>
									<div class="discussionRowHeader box1">
								</c:otherwise>
						</c:choose>
						<div id="voting-post${post.id}" class="discussionVoting">
							${post.numAgree} of ${post.numVote} participants agree with this post
							<c:choose>
								<c:when test="${post.object == null}">
									<a href="javascript:infoObject.setVote('post',${post.id}, 'false');"><img src="/images/btn_thumbsdown.png" alt="I disagree!" border="0"/></a> 
									<a href="javascript:infoObject.setVote('post',${post.id}, 'true');"><img src="/images/btn_thumbsup.png" alt="I agree!" border="0"/></a>
								</c:when>
								<c:otherwise>
									<img src="images/btn_thumbsdown_off.png" alt="Disabled Button"/> <img src="images/btn_thumbsup_off.png" alt="Disabled Button"/>
								</c:otherwise>
							</c:choose>
						</div>
						<span class="discussionTitle">
							${post.title}
					</div>
					
						<c:choose>
								<c:when test="${baseuser.id == post.owner.id}">
									<div class="discussionBody box7">		
								</c:when>
								<c:otherwise>
									<div class="discussionBody">
								</c:otherwise>
						</c:choose>
						<div class="discussionText">
							<p>${post.content}</p>
							<h3>- ${post.owner.loginname}</h3>
						</div>
						<div class="discussionComments">
						<a href="#replyAnchor">Reply to this post</a>
						</div>
						<c:if test="${fn:length(post.tags) > 0}">
							<ul class="tagsInline">
								<li class="tagsInline"><strong>Tags:</strong> </li>
								<c:forEach var="tag" items="${post.tags}">
									<c:choose>
										<c:when test="${baseuser.id == post.owner.id}">
											<li class="box6 tagsInline">		
										</c:when>
										<c:otherwise>
											<li class="box8 tagsInline">
										</c:otherwise>
									</c:choose>
									<a href="javascript:io.getPosts(${tag.id},0,true);">${tag.name}</a></li>
								</c:forEach>
							</ul>
							<div style="clear: left;"></div>
						</c:if>
					</div>
			</div>
</div>
<div class="clearBoth"></div>
<!-- END Discussion Topic (Discussion Post) -->
<div id="discussion" style="margin-left: 135px;">
  <!-- load discussion Replies -->
</div>

<!-- start feedback form -->
<pg:feedback id="feedbackDiv" action="sdRoom.do" />
<!-- end feedback form --><!-- end container -->


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

<script type="text/javascript">
	io.getReplies();
	io.getTargets();
	</script>


</html:html>


