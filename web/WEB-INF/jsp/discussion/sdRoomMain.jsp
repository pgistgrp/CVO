<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
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
<!--End SDX Specific  Libraries-->


<script type="text/javascript">
<!--
//Start Global Variables

	 function InfoObject(){
	 	 this.objectDiv =  'object-content';
	 	 this.discussionDiv = 'discussion';
	 	 this.sidebarDiv = 'sidebar_object';
	 	 this.ISTitle = "All Concern Themes"; // title of entire info structure
	 	 
	 	 this.getISList = function(){
	 	 	var list = "";
	 	   <c:forEach var="infoObject" items="${structure.infoObjects}">
			      list += '<p>${infoObject.object}</p>';
			</c:forEach>	
	 	 	return list;
	 	};

	 	this.assignTargetHeaders = function(){
	 	<c:choose>
	 		<c:when test="${object != null}">
	 			var targetTitle = "${object.object}";
				$('targetTitle').innerHTML = '<html:link action="/sd.do" paramId="isid" paramName="structure" paramProperty="id">'+ this.ISTitle +'</html:link>  &raquo; ' + targetTitle; //object title div id
				$('targetDiscussionTitle').innerHTML = targetTitle;//discussion title div id
					if (${object.numDiscussion} == 1){
					 	$('targetDiscussionTitle').innerHTML += ' - ${object.numDiscussion} Topic';
					}else{
						$('targetDiscussionTitle').innerHTML += ' - ${object.numDiscussion} Topics';
					}
				$('targetSideBarTitle').innerHTML = 'filtered by: ' + targetTitle;//sidebar title div id
			</c:when>
			<c:otherwise> //for entire info structure
				 var targetTitle = this.ISTitle;
				$('targetTitle').innerHTML = '<html:link action="/sd.do" paramId="isid" paramName="structure" paramProperty="id">'+ this.ISTitle +'</html:link>  &raquo; ' + this.ISTitle + ' List'; //object title div id
				$('targetDiscussionTitle').innerHTML = targetTitle;//discussion title div id
					if (${structure.numDiscussion} == 1){
					 	$('targetDiscussionTitle').innerHTML += ' - ${structure.numDiscussion} Discussion';
					}else{
						$('targetDiscussionTitle').innerHTML += ' - ${structure.numDiscussion} Discussions';
					}
				$('targetSideBarTitle').innerHTML = 'filtered by: ' + this.ISTitle;//sidebar title div id
			
			</c:otherwise>
			</c:choose>
			this.getTargets();
		};
	 	this.getTargets = function(){
	 		<c:choose>
	 		<c:when test="${object != null}">
	 		displayIndicator(true);
	 		SDAgent.getSummary({ioid: ${object.id}}, {
					callback:function(data){
							if (data.successful){
	              				 $(infoObject.objectDiv).innerHTML = data.source.html; 
	              				
	              			  if(data.voting == null || data.voting == undefined){
						           $('structure_question').innerHTML = '<span class="smalltext">Does this summary adequately reflect concerns expressed by participants? <a href="javascript:infoObject.setVote(\'true\');"><img src="/images/btn_yes_a.gif" alt="YES" class="button"><a href="javascript:infoObject.setVote(\'false\');"><img src="/images/btn_no_a.gif" alt="NO" class="button"></a></span>';
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
			</c:when>
			<c:otherwise>
				$(infoObject.objectDiv).innerHTML = this.getISList();;
			</c:otherwise>
			</c:choose>
	 	};
	 

	 	 this.setVote = function(agree){

	 		<c:if test="${object != null}">
	 	 			displayIndicator(true);
					SDAgent.setVoting({ioid: ${object.id}, agree:agree}, {
					callback:function(data){
							if (data.successful){ 
	              				 new Effect.Fade('structure_question', {afterFinish: function(){infoObject.getTargets(); new Effect.Appear('structure_question');}});
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
			</c:if>

			};
	 	 this.createPost = function(){
	 	 		displayIndicator(true);
	 	 		var newPostTitle = $('txtNewPostTitle').value;
	 	 		var newPost = $('txtNewPost').value;
	 	 		var newPostTags = $('txtNewPostTags').value;
	 	 		//validation
	 	 		if(newPostTitle == '' || newPost == ''){
	 	 			alert("Either your title or post was left blank.  Please fill it in.");
	 	 			return
	 	 		}//end validation

			<c:choose>
	 		<c:when test="${object != null}">
				SDAgent.createPost({isid:${structure.id}, ioid: ${object.id}, title: newPostTitle, content: newPost, tags:newPostTags}, {
			</c:when>
			<c:otherwise>
				SDAgent.createPost({isid:${structure.id}, title: newPostTitle, content: newPost, tags:newPostTags}, {
			</c:otherwise>
			</c:choose>
				callback:function(data){
						if (data.successful){
							 infoObject.getPosts();
							 //clear new discussion textfields
							 $('txtNewPostTitle').value = '';
							 $('txtNewPost').value = '';
							 $('txtNewPostTags').value = '';
							 toggleNewDiscussion();
							 displayIndicator(false);
						}else{
							alert(data.reason);
							 toggleNewDiscussion();
							 displayIndicator(false);
						}
					},
				errorHandler:function(errorString, exception){ 
						alert("create post error:" + errorString + exception);
				}
				});
			};
			
		this.getPosts = function(){
		    displayIndicator(true);
	    	var page = 1;
	 		if (<%= request.getParameter("page") %> != null){
	 			page = <%= request.getParameter("page") %>;	
	 		}
		   
			<c:choose>
	 		<c:when test="${object != null}">
				 SDAgent.getPosts({isid:${structure.id}, ioid:${object.id}, page: page, count: 10}, {
			</c:when>
			<c:otherwise>
				 SDAgent.getPosts({isid:${structure.id},page: page, count: 10}, {
			</c:otherwise>
			</c:choose>
		      callback:function(data){
		          if (data.successful){
		          $(infoObject.discussionDiv).innerHTML = data.html;
		           displayIndicator(false);
		          }else{
		            alert(data.reason);
		            displayIndicator(false);
		          }
		      },
		      errorHandler:function(errorString, exception){
		          alert("get posts error:" + errorString + exception);
		      }
		    });
		  };
	};
	
	function Discussion(){
		this.discussionDivSort = "header_cat";
		displayIndicator(true);
		this.deletePost = function(postId){
		var destroy = confirm ("Are you sure you want to delete this post? Note: there is no undo.")
		if (destroy){
			    SDAgent.deletePost({pid: postId}, {
			      callback:function(data){
			          if (data.successful){
								
			          			$('discussion-post'+postId).innerHTML = "deleting...";
			          			setTimeout("new Effect.DropOut('discussion-post-cont"+postId+"', {afterFinish: function(){infoObject.getPosts(infoObject.targetId)}});", 1000);
			          			displayIndicator(false);
								
			          }else{
			           	 alert(data.reason);
			           	 displayIndicator(false);
			          }
			      },
			      errorHandler:function(errorString, exception){
			          alert("delete post error:" + errorString + exception);
			      }
			    });
	  	}
		}
		/*
		this.getPost = function(postId){
		if(objExpanded){
				moreDiscussion();
		}
		displayIndicator(true);
	    SDAgent.getPostById({id: postId}, {
	      callback:function(data){
	          if (data.successful){
	          		new Effect.BlindUp(discussion.discussionDivSort, {duration: 0.3});
	          		var tags = '';
	          		for(i=0; i<data.post.tags.length; i++){
	          			tags += '<li class="tagsList">' +data.post.tags[i].name+ '</li>';	
	          		}
	          		
	          		$(infoObject.discussionDiv).innerHTML = '<div id="discussion-post-cont'+data.post.id+'"><p><a href="javascript:infoObject.getPosts('+infoObject.targetId+')">Back to list of discussions</a></p><div id="discussion-post'+data.post.id+'" class="whiteBlueBB"><h5>'+data.post.title+'</h5><p>Posted by: '+data.post.owner.loginname+' on '+data.post.createTime+' <br /><strong>Author Actions: </strong> <a href="javascript:discussion.deletePost('+data.post.id+')">delete discussion</a> | edit discussion (actions available until commented on)</p><p>'+data.post.content+'</p><p><strong>Tags:</strong> <ul class="tagsList">'+tags+'</ul></p></div><p><a href="javascript:infoObject.getPosts('+infoObject.targetId+')">Back to list of discussions</a></p></div>';
	          		//$(infoObject.discussionDiv).innerHTML += '
	          		displayIndicator(false);
	          }else{
	           	 alert(data.reason);
	           	 displayIndicator(false);
	          }
	      },
	      errorHandler:function(errorString, exception){
	          alert("get post error:" + errorString + exception);
	          displayIndicator(false);
	      }
	    });
		}*/
	};
	
	//End Global Variables
	
	function displayIndicator(show){
		if (show){
			$('loading-indicator').style.display = "inline";	
		}else{
			$('loading-indicator').style.display = "none";	
		}
	}
	/*
	function closeAllContentsExcept(id, className){
		var activeRecord = className + id;
		var allRecords = document.getElementsByClassName(className);
		
		for(i = 0; i < allRecords.length; i++){
			if(allRecords[i].id == activeRecord){
				new Effect.toggle(allRecords[i].id,'blind', {duration: 0.4});
			}else{
				if(allRecords[i].style.display != 'none'){
				new Effect.BlindUp(allRecords[i].id, {duration: 0.4});
				}
			}
		}
		//new Effect.toggle('quickPostContents${post.id}', 'blind', {duration: 0.3}); void(0);"	
	}
	*/
	function toggleNewDiscussion(){
		if ($('newDiscussion').style.display == 'none'){
			displayIndicator(true);
			new Effect.toggle('newDiscussion', 'blind', {duration: 0.5});
			$('sidebarbottom_disc').style.display = 'none';	
			$('sidebarbottom_newdisc').style.display = 'block';	
			displayIndicator(false);
		}else{
			displayIndicator(true);
			new Effect.toggle('newDiscussion', 'blind', {duration: 0.5, afterFinish: function(){
			$('sidebarbottom_disc').style.display = 'block';	
			$('sidebarbottom_newdisc').style.display = 'none';	
			displayIndicator(false);	
			}});		
		}
	}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
</head>


<body onLoad="MM_preloadImages('images/btn_continue_b.gif')">

<div id="container">
	<jsp:include page="/header.jsp" />
		
   <div id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>
	
	<!-- Sub Title -->
	<div id="subheader">
	<h1>Step 1b:</h1> <h2>Review Summaries</h2>
	</div>
	<div id="footprints">
	<span class="smalltext"><a href="#">Participate</a> &raquo; <a href="#">Step 1 Brainstorm Concerns</a> &raquo; <a href="sd.do?isid=${structure.id}">Step 1b Review Summaries</a> &raquo; ${object.object}</span>
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
							
				<p>[ <a href="/readmore.jsp">Read more about how this step fits into the bigger picture.</a> ]</p>
		</div>
	</div>
	<!-- End Overview -->
	
	<div class="backToDiscussion">
		<div id="tselector">
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
		</div>	<!-- end tselector -->
		<div id="backdisc"><a href="sd.do?isid=${structure.id}"><img src="images/btn_back_a.gif" alt="Back" name="back" class="button" id="next" onMouseOver="MM_swapImage('back','','images/btn_back_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a></div>	  
	</div> <!-- end backtodiscussion -->
	<div id="cont-main">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
						<td id="maintop"><img src="" alt="" height="1" width="1"/></td>
						<td><img src="images/sidebar_top.gif" alt="sidebartop" /></td>
				</tr>
				<tr>
						<td valign="top" id="maincontent">
							<!-- Main Content starts Here-->
							<h4>Summarization of Participant Concerns </h4>
							<div id="object">
								<div class="padding">
									<h5 id = "targetTitle"></h5>
									<div id="object-content">
										<!-- load object here -->
									</div><!--end object content -->
								</div> <!-- end padding -->
							</div> <!-- end object -->
							<p class="textalignright">&nbsp;</p>
						</td> <!-- end td main content -->
						
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
										</div><!-- end tagform -->
										<div id="pullDown" class="textright"><a href="javascript: expandTagSelector();">Expand</a></div>
										<div id="allTags" style="display: none;"><!--load tags --></div>
										<div class="clear"></div>
									</div> <!-- end tag selector -->
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
									<h5 id="targetSideBarTitle"></h5>
												<!-- Fake concerns -->
												<div id="concernId887" class="theConcern">
										            <span class="participantName"><a href="userProfile887.jsp">DoeDiane</a></span>&nbsp;said:
										            <br>
										            <span class="concerns">"The transportation system should be more accessible to all citizens"</span><br>
										            <span class="tags"><a href="javascript:getConcernsByTag(864);">accessibility</a></span>            
										        </div>
										
										        <div id="concernId918" class="theConcern">
										            <span class="participantName"><a href="userProfile918.jsp">MurphyMary</a></span>&nbsp;said:
										            <br>
										            <span class="concerns">"Non-commuter vehicles shouldn't drive in commuter lanes"</span><br>
										            <span class="tags"><a href="javascript:getConcernsByTag(902);">commuting</a></span>
										            <span class="tags"><a href="javascript:getConcernsByTag(885);">congestion</a></span>
										            <span class="tags"><a href="javascript:getConcernsByTag(894);">hov lanes</a></span>
										            <span class="tags"><a href="javascript:getConcernsByTag(917);">law enforcement</a></span>
										            <span class="tags"><a href="javascript:getConcernsByTag(868);">safety</a></span>    
										        </div>
										
										        <div id="concernId878" class="theConcern">
										            <span class="participantName"><a href="userProfile878.jsp">JonesJane</a></span>&nbsp;said:
										            <br>
										            <span class="concerns">"Transportation systems should promote livability and walkability"</span><br>
										            <span class="tags"><a href="javascript:getConcernsByTag(873);">density</a></span>
										            <span class="tags"><a href="javascript:getConcernsByTag(874);">downtown</a></span>
										            <span class="tags"><a href="javascript:getConcernsByTag(877);">health</a></span>
										            <span class="tags"><a href="javascript:getConcernsByTag(871);">livability</a></span>
										            <span class="tags"><a href="javascript:getConcernsByTag(875);">sprawl</a></span>
										            <span class="tags"><a href="javascript:getConcernsByTag(855);">transportation planning</a></span>
										            <span class="tags"><a href="javascript:getConcernsByTag(872);">walkability</a></span>                    
										        </div>
										
										        <div id="concernId891" class="theConcern">
										            <span class="participantName"><a href="userProfile891.jsp">JohnsonJohn</a></span>&nbsp;said:
										            <br>
										            <span class="concerns">"We should not continue to fund a reliance on motor vehicles"</span><br>
										            <span class="tags"><a href="javascript:getConcernsByTag(890);">alternative</a></span>
										            <span class="tags"><a href="javascript:getConcernsByTag(888);">car</a></span>
										            <span class="tags"><a href="javascript:getConcernsByTag(882);">funding</a></span>
										            <span class="tags"><a href="javascript:getConcernsByTag(889);">taxes</a></span>
										            <span class="tags"><a href="javascript:getConcernsByTag(855);">transportation planning</a></span>        
										        </div>
										
										        <div id="concernId866" class="theConcern">
										            <span class="participantName"><a href="userProfile866.jsp">BrownBob</a></span>&nbsp;said:<br>
										            <span class="concerns">"Restrooms at Metro transit stations need to be handicapped accessible"</span><br>
										            <span class="tags"><a href="javascript:getConcernsByTag(860);">Metro</a></span>
										            <span class="tags"><a href="javascript:getConcernsByTag(864);">accessibility</a></span>
										        </div>
												<!-- end fake concerns -->
												<!-- PREV and NEXT Buttons -->
												<span class="textright"><img src="images/btn_next_a.gif" alt="Next" name="next" class="button" id="next" onMouseOver="MM_swapImage('next','','images/btn_next_b.gif',1)" onMouseOut="MM_swapImgRestore()"></span><img src="images/btn_prev_a.gif" alt="Prev" name="prev" class="button" id="prev" onMouseOver="MM_swapImage('prev','','images/btn_prev_b.gif',1)" onMouseOut="MM_swapImgRestore()">
												<!-- End PREV and NEXT Buttons -->
								
								<div id="caughtException"><h4>A Problem has Occured</h4><br>We are sorry but there was a problem accessing the server to complete your request.  <b>Please try refreshing the page.</b></div>
								
								</div><!-- End sidebarcontents-->
						  </div><!-- sidebar container-->
					</td>
				<!-- End Right Col -->
				</tr>
		</table>
		<div id="sidebarbottom_disc" style="text-align:right; display: block;"><img src="/images/sidebar_bottom.gif" alt="sidebarbottom" /></div>
		<div id="sidebarbottom_newdisc" style="text-align:right; display: none;"><img src="/images/sidebar_bottom.gif" alt="sidebarbottom" /></div>
	</div><!-- End cont-main -->
	<div id="newDiscussion" style="display: none">
		<div id="newdisc_title" >
			<div class="textright"><span id="closeNewDiscussion" class="closeBox"><a href="javascript:toggleNewDiscussion();"><img src="/images/btn_close.gif" class="button"></a></span></div>
			<h3 style="display: inline">New Topic</h3>
		</div> <!-- End newdisc_title -->
		<div id="newdisc_content" class="greenBB">
			<p>SDC New Topic Paragraph</p>
			<form>
				<p><label>Post Title</label><br><input style="width:100%" type="text" id="txtNewPostTitle"/></p>
				<p><label>Your Thoughts</label><br><textarea style="width:100%; height: 200px;" id="txtNewPost"></textarea></p>
				<p><label>Tag your post (comma separated)</label><br><input style="width:100%" id="txtNewPostTags" type="text" /></p>
				<input type="button" onClick="infoObject.createPost();" value="Create Discussion">
			</form>
		</div>
	</div>
		
	<div id="discussion-cont">
		<span class="padding"><h4 id="targetDiscussionTitle"></h4></span><span id="closeNewDiscussion" class="closeBox"><a href="javascript:toggleNewDiscussion();"><img src="images/btn_gnewtopic_a.gif" alt="New Topic" name="newtopic" class="button" id="newtopic" onMouseOver="MM_swapImage('newtopic','','images/btn_gnewtopic_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a></span>
		<div id="discussion"><!-- load discussion posts --></div>
	</div>
	<div id="legend" class="smalltext">
	<img src="/images/balloonactive2.gif" alt="active" class="button">
	Recent Post  <img src="/images/ballooninactive2.gif" alt="active" width="20" height="21" class="button"> No Recent Post </div>
	</div>
	
	<div id="backdisc"><a href="sd.do?isid=${structure.id}"><img src="images/btn_back_a.gif" alt="Back" name="back" class="button" id="next" onMouseOver="MM_swapImage('back','','images/btn_back_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a></div>	  

<div id="finished" class="greenBB">
	<h4>Ready for the next step?</h4><br />
	<p>Click on the continue button to go on to step 2 where you will review and weigh criteria to evaluate proposed transportation projects.  Go back to your <a href="main.do">home page</a> or  <img src="images/btn_gcontinue_a.gif" alt="Continue" name="continue" class="button" id="continue" onMouseOver="MM_swapImage('continue','','images/btn_gcontinue_b.gif',1)" onMouseOut="MM_swapImgRestore()"></p>
</div>


<!-- start feedback form -->
	<p>Found a bug?  Problem accessing a part on the page?  <a href="javascript:Effect.toggle('feedbackForm','blind');">Send us feedback.</a></p>
	<div id="feedbackForm" style="display: none;">
		<pg:feedback id="feedbackDiv" action="sdRoom.do" />
	</div>
<!-- end feedback form -->
</div> <!-- End container -->
<!-- Start Footer -->
<jsp:include page="/footer.jsp" />

<!-- End Footer -->
<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
<script type="text/javascript">
	var infoObject = new InfoObject(); 
	var discussion = new Discussion();

	infoObject.getPosts();
	infoObject.assignTargetHeaders();

</script>

</body>

</html:html>

