<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<head>
<title>Step 1b: Discuss Summaries - Rooms</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">
	@import "styles/lit.css";
</style>
<link rel="stylesheet" href="styles/nicetitle.css">
<!-- Temporary Borders used for testing <style type="text/css" media="screen">@import "styles/tempborders.css";</style>-->
<!-- End Site Wide CSS -->
<script language="javascript" type="text/javascript" src="/scripts/tinymce/jscripts/tiny_mce/tiny_mce.js"></script>
<!-- Site Wide JavaScript -->
<script src="scripts/search.js" type="text/javascript"></script>
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
<script src="scripts/qTip.js" type="text/javascript"></script>
<!--End SDX Specific  Libraries-->

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
		io.currentFilter = '';
		io.currentPage = 1;
		io.postCount = 5;
		io.tagCloudCount = 50;
		io.currentTagCloudPage = 1;
		
		/*----Input ID's - these id's of input elements have changing content or gets read by the javascript ---- */
	 	 io.newPostTitleInput = "txtNewPostTitle";   //new post title input box
	 	 io.newPostTagsInput = "txtNewPostTags"; //new post tags input box
	 	 
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

		/*************** Set Vote************** */
	 	 io.setVote = function(target, id, agree){
					//alert("structure" + infoObject.structureId + "object " + infoObject.objectId + "vote " + agree);
					SDAgent.setVoting({target: target, id: id, agree:agree}, {
					callback:function(data){
							if (data.successful){ 
								//alert("successful");
								var votingDiv = 'voting-'+target+id;
								if($(votingDiv) != undefined){
	              				 	new Effect.Fade(votingDiv, {afterFinish:function(){io.getPosts(io.currentFilter, io.currentPage, false); io.getTargets(); new Effect.Appear(votingDiv);}});
	              				 	
	              				}else{
	              					io.getPosts(io.currentFilter, io.currentPage, false);	
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
		
		io.getPosts = function(tag, page, jump){
				if(jump){
					location.href = io.filterAnchor;
				}
				//alert("structure: " + io.structureId + " tag filter: " + tag + " page: " + page);
				//SDAgent.getContextPosts({isid:io.structureId, tag: tag,  type:"tagRef", page: page, count: io.postCount}, {
			    SDAgent.getPosts({isid:io.structureId,ioid:io.objectId, filter: tag, page: page, count: io.postCount}, {
			      callback:function(data){
			          if (data.successful){
			          $(io.discussionDiv).innerHTML = data.html;
			          }else{
			            alert(data.reason);
			          }
			      },
			      errorHandler:function(errorString, exception){
			          alert("get posts error:" + errorString + exception);
			      }
			    });
			  };
		
	io.goToPage = function(page, component){
		switch (component){
			case "tagCloud":
				io.getTagCloud(page);
				break;	
			case "posts":
				io.getPosts(io.currentFilter,page,true); 
				break;
			}
	}
	 	 
			
		/*************** New Discussion Post: if successful, reload discussion posts************** */
	 	 io.createPost = function(){
	 	 		var newPostTitle = $(io.newPostTitleInput).value;
				var newPost= tinyMCE.getContent();
	 	 		var newPostTags = $(io.newPostTagsInput).value;
	 	 		
	 	 		//alert("ISID: " + this.structureId + "IOID: " + this.objectId + "Title: " + newPostTitle + "Content: " + newPost + "Tags: " + newPostTags);
				SDAgent.createPost({isid:io.structureId, ioid: io.objectId, title: newPostTitle, content: newPost, tags:newPostTags}, {
				callback:function(data){
						if (data.successful){
						     io.setVote("post", data.id, "true"); //set initial vote
							 io.clearNewDiscussionInputs();
							 io.toggleNewDiscussion();
							 if(io.currentDiscPage != 1){
							 	io.currentDiscPage = 1
							 }
							 io.getPosts();
						}else{
							alert(data.reason);
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
								   		 new Effect.Puff('discussion' + pid, {afterFinish: function(){io.getPosts();}});
										
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
			new Effect.toggle(io.newDiscussionDiv, 'slide', {duration: 0.5});	
		}
			

	//START Filters and tags
	io.changeCurrentFilter = function(tagId){
		io.currentFilter = tagId;
		if (tagId != ''){
				SDAgent.getTagById(tagId, {
				callback:function(data){
				if (data.successful){
		          			var tagName = data.tag.name;
		          			io.getPosts(tagName, 0, true);
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
			io.getPosts('', 1, true);
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
			io.currentTagCloudPage = page;
			SDAgent.getTagCloud({isid:io.structureId,count:io.tagCloudCount, page: io.currentTagCloudPage},{
				callback:function(data){
						if (data.successful){			
							$(io.divTagCloud).innerHTML = data.html;
							if (data.count == 0){
								$(io.divTagCloud).innerHTML = '<a href="javascript:Effect.Fade(\''+io.divTagCloud+'\', {duration: 0.5}); void(0);"><img src="images/close1.gif" border=0 class="floatRight"></a><p>No tag matches found! Please try a different search.</p> ';
							}
							if($(io.divTagCloud).style.display == 'none'){
								new Effect.BlindDown(io.divTagCloud, {duration: 0.5});		
							}		
						}
				},
				errorHandler:function(errorString, exception){ 
							alert("sidebarSearchTagsAction: "+errorString+" "+exception);
							//showTheError();
				}		
			});	
	};
			//-->
		</script>
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
<div id="container">
  <!-- Begin Breadcrumbs -->
  <div id="breadCrumbs" class="floatLeft"> <a href="sd.do?isid=${structure.id}">Select a Theme</a> &rarr; ${object.object}</div>
  <!-- End Breadcrumbs -->
  <!-- jump to other room selection menu -->
  <div class="floatRight"> Jump To:
    <select name="selecttheme" id="selecttheme" onChange="javascript: location.href='sdRoom.do?isid=${structure.id}&ioid=' + this.value;">
      <option value = "${object.id}">Select a Theme</option>
      <option value = "">All Concern Themes</option>
      <c:forEach var="infoObject" items="${structure.infoObjects}">
        <option value="${infoObject.id}">${infoObject.object}</option>
      </c:forEach>
    </select>
  </div>
  <!-- end jump to other room selection menu -->
  <script type="text/javascript">
				<c:choose>
				<c:when test="${object.id==null}">
				document.write("<h3 class=\"headerColor clearBoth\">Summarization of Participant Concerns</h3>");
				</c:when>
				<c:otherwise>
				document.write("<h3 class=\"headerColor clearBoth\">Summarization of Participant Concerns about ${object.object}</h3>");
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
  <div class="clearBoth"></div>
  <a name="filterJump"></a>
  <!-- The discussionHeader sits on top of the discussion and contains the title of the
			discussion area, and the sorting menu -->
  
  <div id="discussionHeader">
    <div class="sectionTitle">
      <h3 class="headerColor"><!--${object.discussion.numPosts} -->Discussions about ${object.object}</h3>
      <div id="filteredBy"></div>
      <div class="button smallText box5 floatLeft"> <a href="javascript:Effect.toggle('newDiscussion','slide',{duration:1.5});">Start a New Topic</a></div>
    </div>

<!--[if IE]>
	<style type="text/css">
	#sortingMenu {right:0px;}
	</style>
<![endif]-->

    <div id="sortingMenu" class="box4">sort discussion by:

          <select>
          	 <option>--Sorting is not currently functional.--</option>
	        <option>Newest to Oldest</option>
	        <option>Oldest to Newest</option>
	        <option>Most Agreement</option>
	        <option>Least Agreement</option>
	        <option>Most Comments</option>
	        <option>Most Views</option>
	        <option>Most Votes</option>
          </select>
          <br />
          <div class="floatLeft">filter discussion by:</div>
          <form action="javascript: io.customFilterAction($('txtCustomFilter').value);" class="floatLeft">
            <input type="text" id="txtCustomFilter" value="Add a filter" onKeyUp="io.customFilter(this.value, event);"  onKeyUp="io.customFilter(this.value, event);" onClick="javascript:if(this.value==this.defaultValue){this.value = ''}"/>
            or <a href="javascript:io.getTagCloud();">Browse All Tags</a>
          </form>
          <div id="searchResults" style="display: none;"></div>
  
  </div>
</div><!-- end discussion header-->
  
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
              <label>Post Title</label>
              <br>
              <input maxlength=100 size=100 type="text" id="txtNewPostTitle"/>
            </p>
            <p>
              <label>Your Thoughts</label>
              <br>
              <textarea style="width:100%; height: 200px;" id="txtNewPost"></textarea>
            </p>
            <p>
              <label>Tag your post (comma separated)</label>
              <br>
              <input style="width:100%" id="txtNewPostTags" type="text" />
            </p>
            <input type="button" onClick="io.createPost();" value="Create Discussion">
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
			io.getPosts('', 1, false);
			//infoObject.assignTargetHeaders();
			io.getTargets();

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
