<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<title>Step 1b: Review Summaries - Rooms</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">
	@import "styles/lit.css";	
</style>
<style type="text/css"> v\:* {behavior:url(#default#VML);}</style>
<!-- Temporary Borders used for testing <style type="text/css" media="screen">@import "styles/tempborders.css";</style>-->
<!-- End Site Wide CSS -->
<script language="javascript" type="text/javascript" src="/scripts/tinymce/jscripts/tiny_mce/tiny_mce.js"></script>
<script language="JavaScript" src="scripts/qtip.js" type="text/JavaScript"></script>
<!-- Site Wide JavaScript -->
<script src="scripts/globalSnippits.js" type="text/javascript"></script>
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->
<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->
<!--SDX Specific  Libraries-->
<script type='text/javascript' src='/dwr/interface/SDAgent.js'></script>
<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>
<script type='text/javascript' src='/scripts/util.js'></script>
<!--End SDX Specific  Libraries-->
<!--Mapping  Libraries-->
<script type='text/javascript' src='/dwr/interface/ProjectAgent.js'></script>
<script type='text/javascript' src='/dwr/interface/PESAgent.js'></script>
<script type='text/javascript' src='scripts/pgistmap2.js'></script>
<script type="text/javascript" src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAq4HJEw-8aIG3Ew6IOzpYEBTwM0brOpm-All5BF6PoaKBxRWWERSP-RPo4689bM1xw9IvCyK4oTwAIw"></script>
<!--END Mapping  Libraries-->

<script type="text/javascript">
	<!--
		tinyMCE.init({
			mode : "exact",
			elements: "txtNewPost",
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
		io.projSuiteId = "${projSuiteId}";
		io.critSuiteId = "${critSuiteId}";
		io.pkgSuiteId = "${pkgSuiteId}";
		io.fundSuiteId = "${fundSuiteId}";
		io.repoSuiteId = "${repoSuiteId}";
		io.cctId = "${structure.cctId}";
		io.currentFilter = '';
		io.currentPage = ("${param.page}" != "") ? "${param.page}" : 1;
		io.currentSort = ("${param.sort}" != "") ? "${param.sort}" : 1;
		io.postCount = 5;
		io.tagCloudCount = 50;
		io.currentTagCloudPage = 1;
		
		/*----Input ID's - these id's of input elements have changing content or gets read by the javascript ---- */
	 	 io.newPostTitleInput = "txtNewPostTitle";   //new post title input box
	 	 io.newPostTagsInput = "txtNewPostTags"; //new post tags input box
		 io.newPostNotifier = "ckboxPostNotifier"; //checkbox to determine if user gets email notifications
	 	 
	 	 /*----Divs - these divs have changing content or gets read by the javascript ---- */
	 	 io.sidebarDiv = 'sidebar_object'; //div that contains the sidebar
	 	 io.objectDiv =  'object-content'; //div that contains the object
	 	 io.discussionDiv = 'discussion'; //div that contains the discussion
	 	 io.votingQuestionDiv = 'structure_question' //div that contains the voting question
	 	 io.newDiscussionDiv = 'newDiscussion'; //the new discussion pull down
	 	 io.divSearchResults = 'searchResults';
	 	 io.filterAnchor = '#filterJump';
	 	 io.divFilteredBy = 'filteredBy';
	 	 io.divTagCloud = 'tagCloud';
		
		/*************** Get Targets - If IOID is ommitted, return sdcSummary.jsp::else, returns sdcStructureSummary.jsp************** */
		io.getTargets = function(){
			SDAgent.getTarget({isid:io.structureId, ioid:io.objectId}, {
				callback:function(data){
					if (data.successful){
						$(io.objectDiv).innerHTML = data.source.html;
						//alert(data.source.script)
						eval(data.source.script);
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("SDAgent.getTarget( error:" + errorString + "exception " +exception);
				}
			});		
			
		};

		/*************** Set Vote************** */
	 	 io.setVote = function(target, id, agree){
					//alert("structure" + infoObject.structureId + "object " + infoObject.objectId + "vote " + agree);
					SDAgent.setVoting({target: target, id: id, agree:agree}, {
					callback:function(data){
							if (data.successful){ 
								var votingDiv = 'voting-'+target+id;
								if($(votingDiv) != undefined){
	              				 	new Effect.Fade(votingDiv, {afterFinish:function(){io.getPosts(io.currentFilter, io.currentPage, false, io.currentSort); io.getTargets(); new Effect.Appear(votingDiv);}});
	              				 	
	              				}else{
	              					io.getPosts(io.currentFilter, io.currentPage, false, io.currentSort);	
	              					io.getTargets();
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
		/*************** Get Posts: posts.jsp************** */
		
		io.getPosts = function(tag, page, jump, sorting){
    			if(jump || "${param.page}" != ""){
      				setTimeout(function() {Element.scrollTo("discussionHeader");}, 300);
    			}
				displayIndicator(true);
				io.currentPage = page;
				io.currentSort = sorting;
				io.currentFilter = tag;
				//alert("structure: " + io.structureId + " tag filter: " + tag + " page: " + io.currentPage + " count:" + io.postCount + " sorting:" + io.currentSort);
				//SDAgent.getContextPosts({isid:io.structureId, tag: tag,  type:"tagRef", page: page, count: io.postCount}, {
			    SDAgent.getPosts({isid:io.structureId,ioid:io.objectId, sorting: io.currentSort, filter: tag, page: io.currentPage, count: io.postCount}, <pg:wfinfo/>,{
			      callback:function(data){
			          if (data.successful){
			          	displayIndicator(false);
			          $(io.discussionDiv).innerHTML = data.html;
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
	
	/*************** Go to page via AJAX ************** */
	io.goToPage = function(page, component){
		switch (component){
			case "tagCloud":
				io.getTagCloud(page);
				break;	
			case "posts":
				io.getPosts(io.currentFilter,page,false, io.currentSort); 
				break;
			}
	}
	
	/*************** Go to page via location.href - handles sort and page number ************** */
	io.switchPage = function(page){
	    location.href='sdRoom.do?isid='+ io.structureId +'&ioid='+ io.objectId +'&page='+page+'&sort='+ io.currentSort;
	}
	
	
	 	 
	/*************** Set Email Notificaiton************** */
 	 io.setupEmailNotify = function(id, type, status){
				//alert("id" + id + "type " + type + "turnon " + status);
				SDAgent.setupEmailNotify({id: id, type: type, turnon: status}, {
				callback:function(data){
						if (data.successful){
							if (status){
								alert("Email notification has been turned on!")
							}else{
								alert("Email notification has been turned off!")
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
			
		/*************** New Discussion Post: if successful, reload discussion posts************** */
	 	 io.createPost = function(){
	 	 		displayIndicator(true);
	 	 		var newPostTitle = $(io.newPostTitleInput).value;
				var newPost= tinyMCE.getContent();
	 	 		var newPostTags = $(io.newPostTagsInput).value;
	 	 		var notify = $(io.newPostNotifier).checked;
				var emailNotify = notify.toString();
	 	 		//alert("ISID: " + this.structureId + "IOID: " + this.objectId + "Title: " + newPostTitle + "Content: " + newPost + "Tags: " + newPostTags + " Email Notification: " + emailNotify);
				SDAgent.createPost({isid:io.structureId, ioid: io.objectId, title: newPostTitle, content: newPost, tags:newPostTags, emailNotify: "true"},<pg:wfinfo/>,{
				callback:function(data){
						if (data.successful){
							 displayIndicator(false);
						     //io.setVote("post", data.id, "true"); //set initial vote
							 io.clearNewDiscussionInputs();
							 io.toggleNewDiscussion();
							 if(io.currentDiscPage != 1){
							 	io.currentDiscPage = 1
							 }
							 io.getPosts('',1,true, 1); 
							 $('selectsort').value = 1;
							 window.setTimeout('new Effect.Highlight("discussionText'+ data.id +'", {duration: 4.0});',500);
						}else{
							alert(data.reason);
							displayIndicator(false);
						}
					},
				errorHandler:function(errorString, exception){ 
						alert("create post error:" + errorString + exception);
				}
				});
			};
			
		io.deletePost =  function(pid){
			var destroy = confirm ("Are you sure you want to delete this post? Note: there is no undo.")
			if (destroy){
					SDAgent.deletePost({pid:pid}, {
						callback:function(data){
								if (data.successful){
								   		 new Effect.Puff('discussion' + pid, {afterFinish: function(){io.getPosts(io.currentFilter,io.currentPage,true, io.currentSort);}});
										
								}else{
									alert(data.reason);
								}
							},
						errorHandler:function(errorString, exception){ 
								alert("delete post error:" + errorString + exception);
						}
						});
				}
		};	
		
		io.getSummaryConcerns =  function(){
					SDAgent.getConcerns({isid:io.structureId, ioid: io.objectId}, {
						callback:function(data){
								if (data.successful){
								   		 alert(data.source.html);										
								}else{
									alert(data.reason);
								}
							},
						errorHandler:function(errorString, exception){ 
								alert("delete post error:" + errorString + exception);
						}
						});
		};	
			
		/*************** Clear all discussion input boxes - triggered after a new post is created ************** */
		io.clearNewDiscussionInputs =  function(){
	 	 		$(io.newPostTitleInput).value = "";
	 	 		tinyMCE.setContent('');
	 	 		$(io.newPostTagsInput).value = "";
		};
			
		io.toggleNewDiscussion = function(){
			new Effect.toggle(io.newDiscussionDiv, 'blind', {duration: 0.5});	
		}
			

	//START Filters and tags
	io.changeCurrentFilter = function(tagId){
		io.currentFilter = tagId;
		if (tagId != ''){
				SDAgent.getTagById(tagId, {
				callback:function(data){
				if (data.successful){
		          			var tagName = data.tag.name;
		          			io.getPosts(tagName, 0, true, io.currentSort);
							$(io.divFilteredBy).innerHTML = '<h3 class="contrast1">Filtered By: ' + tagName + ' <a href="javascript: io.changeCurrentFilter(\'\');"><img src="images/close.gif" alt="clear filter" /></a>';
						}else{
							alert(data.reason);
						}
				},
				errorHandler:function(errorString, exception){ 
						alert("get tagbytagref error:" + errorString + exception);
				}
				});
		}else{
			io.currentFilter = '';	
			$(io.divFilteredBy).innerHTML = '';
			io.getPosts('', 1, true, io.currentSort);
		}
	}
	
	io.customFilter = function(query, key){
		if (key.keyCode == 8 && query.length < 1){
			return false;	
		}
		if(query.length > 3){
			io.customFilterAction(query);	
		}
	}
	
	io.customFilterAction = function(query){
			SDAgent.searchTags({isid:io.structureId,tag:query},{
				callback:function(data){
						if (data.successful){
							if($(io.divSearchResults).style.display == 'none'){
								new Effect.Appear(io.divSearchResults, {duration: 0.5});		
							}		
							
							$(io.divSearchResults).innerHTML = $(io.divSearchResults).innerHTML = data.html;
							if (data.count == 0){
								$(io.divSearchResults).innerHTML = '<a href="javascript:Effect.Fade(\''+io.divSearchResults+'\', {duration: 0.5}); void(0);"><img src="images/close1.gif" border=0 class="floatRight"></a><p>No tag matches found! Please try a different search.</p> ';
							}
						}
				},
				errorHandler:function(errorString, exception){ 
							alert("sidebarSearchTagsAction: "+errorString+" "+exception);
							//showTheError();
				}		
			});	
	};

		io.getTagCloud = function(page){
			displayIndicator(true);
			io.currentTagCloudPage = page;
			SDAgent.getTagCloud({isid:io.structureId,count:io.tagCloudCount, page: io.currentTagCloudPage},{
				callback:function(data){
						if (data.successful){			
							displayIndicator(false);
							$(io.divTagCloud).innerHTML = data.html;
							if (data.count == 0){
								$(io.divTagCloud).innerHTML = '<a href="javascript:Effect.Fade(\''+io.divTagCloud+'\', {duration: 0.5}); void(0);"><img src="images/close1.gif" border=0 class="floatRight"></a><p>No tag matches found! Please try a different search.</p> ';
							}
							new Effect.toggle(io.divTagCloud,'blind',{duration: 0.5});		
									
						}
				},
				errorHandler:function(errorString, exception){ 
							alert("sidebarSearchTagsAction: "+errorString+" "+exception);
							//showTheError();
				}		
			});	
	};
	
	//io.changeSort = function (sorting){
		//io.getPosts(io.currentFilter, io.currentPage, false, sorting);	
		//alert("currentFilter: " + io.currentFilter + " currentPage:" + io.currentPage + " sorting: " + sorting);
	//}
			//-->
		</script>
		<event:pageunload />
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
<div style="display: none;" id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>
<div id="container">

	<div id="object">
		<h5 id = "targetTitle"></h5>
		<div id="object-content">
			<!-- load object here -->
		</div>
		<!--end object content -->
	</div>
	<!-- end object -->
	
	
	<div class="clearBoth"></div>
	<a name="filterJump"></a>
	
	
	<!-- The discussionHeader sits on top of the discussion and contains the title of the
			discussion area, and the sorting menu -->

	<div id="discussionHeader">
		<div class="sectionTitle">
			<div class="floatLeft"><h3 class="headerColor">
				<!--${object.discussion.numPosts} -->
				Discussion</h3></div>
			<div id="filteredBy" class="floatLeft"></div>
			<div class="padding5 box5 floatRight" style="margin-bottom:2px;"> 
			<a class="orangeButton" href="javascript:Effect.toggle('newDiscussion','blind',{duration:0.5});">Start a New Topic</a>
			</div>
		</div>
		<div id="sortingMenu" class="box4 clearBoth">
			<span id="sm-left">
				Filter discussion by:
				<form style="display:inline;" action="javascript: io.customFilterAction($('txtCustomFilter').value);">
					<input type="text" id="txtCustomFilter" value="Add a filter" 
					onKeyUp="io.customFilter(this.value, event);"  
					onClick="javascript:if(this.value==this.defaultValue){this.value = ''}"/>
				</form>
			</span>
			<span id="sm-middle">
				<a href="javascript:io.getTagCloud();">Browse all keywords</a>
				<a href="javascript:io.getTagCloud();"><img src="images/keyword-cloud.gif" alt="Click here for the Keyword Cloud" /></a>
			</span>
			<span id="sm-right"> Sort concerns by:
				<select name="selectsort" id="selectsort" 
					onChange="javascript:io.getPosts(io.currentFilter, 1, true, this.value);	">
					<option value="1">Newest to oldest</option>
					<option value="2">Oldest to newest</option>
					<option value="3">Most agreement</option>
					<option value="4">Least agreement</option>
					<option value="5">Most replies</option>
					<option value="6">Most views</option>
					<option value="7">Most votes</option>
				</select>
			</span>
			<div id="searchResults" style="display: none;"></div>
			<div class="clearBoth"></div>
		</div>
	</div>
	<div class="clearBoth"></div>
	<!-- end discussion header-->
	
	<!-- Begin Discussion Area -->
	<!-- Begin hidden "New topic" DIV -->
	<div style="width:680px;">
		<div id="newDiscussion" style="display: none">
			<div id="newdisc_title" >
				<div class="textright"> </div>
				<h3 style="display: inline">New Topic</h3>
			</div>
			<!-- End newdisc_title -->
			<div id="newdisc_content" class="greenBB">
				<div id="newdisc_inner">
					<form>
						<p>
							<label>Post title</label>
							<br>
							<input maxlength=100 size=100 type="text" id="txtNewPostTitle"/>
						</p>
						<p>
							<label>Your thoughts</label>
							<br>
							<textarea style="width:100%; height: 200px;" id="txtNewPost"></textarea>
						</p>
						<p>
							<label>Keyword your post (comma separated)</label>
							<br>
							<input style="width:100%" id="txtNewPostTags" type="text" />
						</p>
						<input type="button" onClick="io.createPost();" value="Create Discussion">
						<input type="button" 
						onClick="javascript:io.clearNewDiscussionInputs();
						Effect.toggle('newDiscussion','blind',{duration:0.5});" 
						value="Cancel">
						<input type="checkbox" id="ckboxPostNotifier">
						E-mail me when someone responds to my post
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- End hidden "new topic" DIV -->
	<!-- start tag cloud -->
	<div id="tagCloud" class="discussion-left box2" style="display: none;">
		<!-- load "browse all tags" tag cloud -->
	</div>
	<!-- end tag cloud -->
	<div id="discussion">
		<!-- load discussion posts -->
	</div>
	<!-- start feedback form -->
	<pg:feedback id="feedbackDiv" action="sdRoom.do" />
	<!-- end feedback form -->
	<!-- end container -->
	<!-- Start Footer -->
	<!-- End Footer -->
	<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
	<script type="text/javascript">
			//io.getPosts('', 1, false, io.currentSort);
			//infoObject.assignTargetHeaders();
		    //checkForPage(${param.lp});
			io.goToPage(io.currentPage,'posts');
			//location.href="sdRoom.do?isid="+io.structureId+"&ioid="+ io.objectId +"&page=" + page;
			io.getTargets();
			
			
			/*checks if the url parameter lp exists, else displays page 1
			function checkForPage(page){
			    if(page){
    			    io.currentPage=page;
    			}
			}
			*/

	</script>
</div>
<!-- start the bottom header menu -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
<jsp:include page="sdcHeader.jsp" />
<!-- End header menu -->
<!-- end the bottom header menu -->
<!-- Begin footer -->
<div id="footer">
	<jsp:include page="/footer.jsp" />
</div>
<!-- End footer -->
</body>
</html>
