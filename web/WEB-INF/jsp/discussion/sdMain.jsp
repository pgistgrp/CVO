<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
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
	//Start Global Variables
     //var isid = ${structure.id};
	 var targetObject = null;
	 function InfoStructure(){
	 	 this.isid = ${structure.id};
	 	 this.type = "${structure.type}";
	 	 this.instructions = "These are the instructions for blah blah";
	 	 this.data = null;
	 	 this.isDivTargetNavText = 'targetNavText';
	 	 this.isDivTargetTitle = 'targetTitle';
	 	 this.isDivTargetDiscussionTitle = "targetDiscussionTitle";
	 	 this.isDivTargetSideBarTitle = "targetSideBarTitle";
	 	 this.isDivElement =  'object_column';
	 	 this.isDivDiscussion = 'discussion';
	 	 this.targetType = 'structure';
	 	 this.targetId = null;
	 	 
	 	 this.getTargetPanes = function(ioid){
	 	 	this.getPosts(ioid);
	 	 	this.getDetails(ioid);
	 		
	 	};
	 	
	 	this.assignTargetHeaders = function(targetTitle){
	 		if(targetTitle){
				$(this.isDivTargetTitle).innerHTML = targetTitle;
				$(this.isDivTargetDiscussionTitle).innerHTML = "Discussion about " + targetTitle;
				$(this.isDivTargetSideBarTitle).innerHTML = this.sideBarTheme+ ' ' + targetTitle;
			}else{
				$(this.isDivTargetTitle).innerHTML = this.defaultObjectTitle; 
				$(this.isDivTargetDiscussionTitle).innerHTML = "Discussion about " + this.defaultDiscussionTitle;
				$(this.isDivTargetSideBarTitle).innerHTML = this.sideBarTheme + ' ' +  this.defaultSidebarTitle;
			}
		};
	 	
	 	 this.getTargets = function(){
	 	 		displayIndicator(true);
				SDAgent.getTargets({isid:${structure.id}}, {
				callback:function(data){
						if (data.successful){
							$(infoStructure.isDivElement).innerHTML = data.source.html;
              				eval(data.source.script);
              				 displayIndicator(false);
              				 infoStructure.assignTargetHeaders();
              				 $(infoStructure.isDivTargetNavText).innerHTML =  infoStructure.defaultTargetNavText;  //reset navText
              				 infoStructure.targetId = null; //reset targetId
              				 
              				 if(data.voting) { 
		              				 if(data.voting == null){
						           		$('structure_question').innerHTML = '<span class="smalltext">Does this summary adequately reflect concerns expressed by participants? <a href="javascript:infoStructure.setVote(true,${structure.id},\'isid\');"><img src="images/btn_yes_s.gif" alt="YES" border="0"><a href="javascript:infoStructure.setVote(false, ${structure.id},\'isid\');"><img src="images/btn_no_s.gif" alt="NO" border="0"></a></span>';
						           }
					          }else{
						           		$('structure_question').innerHTML = '<span class="smalltext">Your vote has been recorded. Thank you for your participation.</span>';
						      }
					        
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
	 	 this.setVote = function(agree, ioid, type){
	 	 		displayIndicator(true);
	 	 		if (type == 'isid'){
					SDAgent.setVoting({isid: ${structure.id}, agree:agree}, {
					callback:function(data){
							if (data.successful){
	              				 displayIndicator(false);
	              				 //alert("thank you for your vote");
	              				 infoStructure.getTargets();
							}else{
								alert(data.reason);
								 displayIndicator(false);
							}
						},
					errorHandler:function(errorString, exception){ 
							alert("get targets error:" + errorString + exception);
					}
					});
				}
	 	 		
	 	 		if (type == 'ioid'){
					SDAgent.setVoting({ioid: ioid, agree:agree}, {
					callback:function(data){
							if (data.successful){
	              				 displayIndicator(false);
	              				 //alert("thank you for your vote");
	              				 infoStructure.getDetails(ioid);
							}else{
								alert(data.reason);
								 displayIndicator(false);
							}
						},
					errorHandler:function(errorString, exception){ 
							alert("get targets error:" + errorString + exception);
					}
					});
				}
			};
	 	 this.createPost = function(){
	 	 		var newPostTitle = $('txtNewPostTitle').value;
	 	 		var newPost = $('txtNewPost').value;
	 	 		var newPostTags = $('txtNewPostTags').value;
	 	 		//validation
	 	 		if(newPostTitle == '' || newPost == ''){
	 	 			alert("Either your title or post was left blank.  Please fill it in.");
	 	 			return
	 	 		}//end validation
	 	 		//alert("target" + infoStructure.targetType);
	 	 		//alert("targetId" + infoStructure.targetId);
				SDAgent.createPost({isid:${structure.id}, target: infoStructure.targetType, targetId: infoStructure.targetId, title: newPostTitle, content: newPost, tags:newPostTags}, {
				callback:function(data){
						if (data.successful){
							 infoStructure.getPosts(infoStructure.targetId);
							 //clear new discussion textfields
							 $('txtNewPostTitle').value = '';
							 $('txtNewPost').value = '';
							 $('txtNewPostTags').value = '';
							 toggleNewDiscussion();
						}else{
							alert(data.reason);
							 toggleNewDiscussion();
						}
					},
				errorHandler:function(errorString, exception){ 
						alert("create post error:" + errorString + exception);
				}
				});
			};
			
		this.getPosts = function(ioid){

			if(ioid != undefined){
				infoStructure.targetType = 'object';
				infoStructure.targetId = ioid;
		  	}
		    displayIndicator(true);
		    SDAgent.getPosts({isid:${structure.id}, ioid:ioid}, {
		      callback:function(data){
		          if (data.successful){
		          $(infoStructure.isDivDiscussion).innerHTML = data.html;
		           displayIndicator(false);
		          }else{
		          	 displayIndicator(false);
		            alert(data.reason);
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
		
		this.deletePost = function(postId){
		var destroy = confirm ("Are you sure you want to delete this post? Note: there is no undo.")
		if (destroy){
			    SDAgent.deletePost({pid: postId}, {
			      callback:function(data){
			          if (data.successful){
								displayIndicator(true);
			          			$('discussion-post'+postId).innerHTML = "deleting...";
			          			setTimeout("new Effect.DropOut('discussion-post-cont"+postId+"', {afterFinish: function(){infoStructure.getPosts(infoStructure.targetId)}});", 1000);
			          			displayIndicator(false);
								
			          }else{
			           	 alert(data.reason);
			          }
			      },
			      errorHandler:function(errorString, exception){
			          alert("get post error:" + errorString + exception);
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
	          		
	          		$(infoStructure.isDivDiscussion).innerHTML = '<div id="discussion-post-cont'+data.post.id+'"><p><a href="javascript:infoStructure.getPosts('+infoStructure.targetId+')">Back to list of discussions</a></p><div id="discussion-post'+data.post.id+'" class="whiteBlueBB"><h5>'+data.post.title+'</h5><p>Posted by: '+data.post.owner.loginname+' on '+data.post.createTime+' <br /><strong>Author Actions: </strong> <a href="javascript:discussion.deletePost('+data.post.id+')">delete discussion</a> | edit discussion (actions available until commented on)</p><p>'+data.post.content+'</p><p><strong>Tags:</strong> <ul class="tagsList">'+tags+'</ul></p></div><p><a href="javascript:infoStructure.getPosts('+infoStructure.targetId+')">Back to list of discussions</a></p></div>';
	          		//$(infoStructure.isDivDiscussion).innerHTML += '
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
	
	function toggleNewDiscussion(){
		if ($('newDiscussion').style.display == 'none'){
			new Effect.toggle('newDiscussion', 'blind', {duration: 0.5});
			$('sidebarbottom_disc').style.display = 'none';	
			$('sidebarbottom_newdisc').style.display = 'block';	
		}else{
			new Effect.toggle('newDiscussion', 'blind', {duration: 0.5, afterFinish: function(){
			$('sidebarbottom_disc').style.display = 'block';	
			$('sidebarbottom_newdisc').style.display = 'none';		
			}});		
		}
	}
 
</script>
</head>


<body>

<div id="container">
	<jsp:include page="/header.jsp" />
	<!-- START LIGHTBOX -->
		<div id="overlay" style="display: none;"></div>
		<div id="lightbox" style="display: none;" class="blueBB"></div>
	<!-- END LIGHTBOX -->
   <div id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>
	
	<!-- Sub Title -->
	<div id="subheader">
	<h1>Step 1:</h1> <h2>Brainstorm Concerns</h2>
	</div>
	<div id="footprints">
	<p>LIT Process >> Step 1: Brainstorm >> Concerns</p>
	</div>
	<!-- End Sub Title -->
	
	<div id="container">

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

<div id="cont-main">
<html:link action="/sdRoom.do" paramId="isid" paramName="structure" paramProperty="id">Back to Discussion Room List</html:link><span class="smalltext">Jump To: Put a select here</span>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td id="maintop"><img src="" alt="" height="1" width="1"/></td>
<td><img src="images/sidebar_top.gif" alt="sidebartop" /></td>
</tr>
<tr>
<td valign="top" id="maincontent">
<!-- Main Content starts Here-->
<h4>Step 1. Summary of Participant Concerns </h4>
<div id="object">
<div class="padding">
<h4>All Concern Themes &raquo;</h4>
<h5>Safety</h5>
	<p>
		Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nam interdum. Donec accumsan,
	purus ut viverra pharetra, augue tellus vehicula orci, eget consectetuer neque tortor id ante.
	Proin vehicula imperdiet ante. Mauris vehicula velit sed arcu. Ut aliquam pede ac arcu.
	Phasellus dictum condimentum nisl. Quisque elementum dictum nibh. Curabitur auctor
	faucibus libero. Suspendisse eu dui ut sem nonummy egestas. Praesent luctus lorem a
	magna.
	Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nam interdum. Donec accumsan,
	purus ut viverra pharetra, augue tellus vehicula orci, eget consectetuer neque tortor id ante.
	Proin vehicula imperdiet ante. Mauris vehicula velit sed arcu. Ut aliquam pede ac arcu.
	Phasellus dictum condimentum nisl. Quisque elementum dictum nibh. Curabitur auctor
	faucibus libero. Suspendisse eu dui ut sem nonummy egestas. Praesent luctus lorem a
	magna.
	</p>
	<p class="textalignright">
		<span class="smalltext">4 of 15 participants have said that this list of concern themes adequately reflects concerns expressed by participants. <br />
		Does this list of concern themes adequately reflect concerns expressed by participants? Yes No
	</span>
	</p>
	</div>
</div>
<!-- End Object -->
<p class="textalignright">&nbsp;</p>
		</td>

<td width="280" valign="top" id="sidebarmiddle"><!-- This is the Right Col -->
  <div id="sidebar_content">
    <h4>Other Discussions filtered by:</h4>
<h5>Accidents</h5>

<div class="sidebardisc">
<a href="#">What I am Concerned With</a><br /><span class="smalltext">What I am mainly concerned with is something that I have been [more...]</span><br /><span class="smalltext">[Tags] [Tags] [Tags]</span>
</div>
<div class="sidebardisc">
<a href="#">What I am Concerned With</a><br /><span class="smalltext">What I am mainly concerned with is something that I have been [more...]</span><br /><span class="smalltext">[Tags] [Tags] [Tags]</span>
</div>
<div class="sidebardisc">
<a href="#">What I am Concerned With</a><br /><span class="smalltext">What I am mainly concerned with is something that I have been [more...]</span><br /><span class="smalltext">[Tags] [Tags] [Tags]</span>
</div>
<div class="sidebardisc">
<a href="#">What I am Concerned With</a><br /><span class="smalltext">What I am mainly concerned with is something that I have been [more...]</span><br /><span class="smalltext">[Tags] [Tags] [Tags]</span>
</div>
<!-- End sidebarcontents-->
</td>
<!-- End Right Col -->
</tr>

</table>
<div id="sidebarbottom_disc" style="text-align:right; display: block;"><img src="/images/sidebar_bottom.gif" alt="sidebarbottom" /></div>
<div id="sidebarbottom_newdisc" style="text-align:right; display: none;"><img src="/images/sidebar_bottom.gif" alt="sidebarbottom" /></div>


</div>
<!-- End cont-main -->
<div id="newDiscussion" style="display: none">
<div id="newdisc_title" >
	New Discussion
	<span id="closeNewDiscussion" class="closeBox"><a href="javascript:toggleNewDiscussion();">Close</a></span>
</div> <!-- End newdisc_title -->
<div id="newdisc_content" class="greenBB">
	<p>SDC New Discussion Paragraph</p>
	<form>
		<p><label>Post Title</label><br><input style="width:100%" type="text" id="txtNewPostTitle"/></p>
		<p><label>Your Thoughts</label><br><textarea style="width:100%; height: 200px;" id="txtNewPost"></textarea></p>
		<p><label>Tag your post (comma separated)</label><br><input style="width:100%" id="txtNewPostTags" type="text" /></p>
		<input type="button" onClick="infoStructure.createPost();" value="Create Discussion">
	</form>
</div>
</div>


<div id="discussion-cont">
	<span class="padding"><h4>Step 3. Discussions about Safety (11 Discussions)</h4></span><span id="closeNewDiscussion" class="closeBox"><a href="javascript:toggleNewDiscussion();">New Discussion</a></span>
	  <div id="discussion">
		  <tr class="disc_row_a">
			<td><a href="#">Pot Holes</a></td>
			<td class="textcenter"><a href="#">John Le</a><br /></td>
			<td class="textcenter">May 20, 2006 by: Jim Jack</td>
			<td class="textcenter"><a href="#">20</a></td>
			<td class="textcenter"><a href="#">68</a></td>
		  </tr>
	  </div>
</div>


<div class="pages">
	<span class="pages_prev">&#171; PREV</span>
	<span class="pages_current">1</span>
	<a href="#" title="Page 2">2</a> 
	<a href="#" title="Page 3">3</a> 
	<a href="#" title="Page 4">4</a>
	<a href="#" title="Page 5">5</a>  
	...
	<a href="#" title="Page 99">99</a>
	<a href="#" title="Page 100">100</a>
	<a href="#" class="pages_nextprev" title="Next Page">NEXT &#187;</a>
</div>
	
<div id="finished" class="borderblue">
	<h4>Step 4. Finished?</h4><br />
	Go back or continue... [add buttons] [Cancel]
</div>

</div> <!-- End container -->
<!-- Start Footer -->
<jsp:include page="/footer.jsp" />

<!-- End Footer -->
<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
<script type="text/javascript">
	var infoStructure = new InfoStructure(); 
	var discussion = new Discussion();
	//infoStructure.getTargets();
	infoStructure.getPosts();
	

</script>

</body>

</html:html>

