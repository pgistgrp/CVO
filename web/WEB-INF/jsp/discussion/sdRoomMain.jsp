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
<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>
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
							alert("setVote error:" + errorString + exception);
					}
					});
			</c:if>

			};
	 	 this.createPost = function(){
	 	 		displayIndicator(true);
	 	 		var newPostTitle = $('txtNewPostTitle').value;
	 	 		var newPost = validateInput($('txtNewPost').value);
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
	
	function validateInput(string){
			string=string.replace(/>/g,"//>//");
			string=string.replace(/</g,"//<//");
			string=string.replace(/\n/g,"<br>");

			return string;
	}
	
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

	/////////////////////sidebar functionality////////////////////////////
	
	
		//sidebar global vars
		var currentFilterArr = new Array();
		var cctId = 1171; 
		var filterIOID = false;
		
		//end sidebar global vars
		function getConcerns(page){
			//alert("cctid: ${cct.id}");
				displayIndicator(true);
				//pagination
				var sidebarPage = 1
				if (page != undefined){
					sidebarPage = page;
				}

 	 			var currentFilter = new Array();
 	 			for(i=0; i<currentFilterArr.length; i++){
 	 				if(currentFilterArr[i].removeable){
	 	 				if(currentFilterArr[i].status == "checked"){
	 	 					currentFilter.push(currentFilterArr[i].tagRefId);
	 	 				}
 	 				}else{ //if ioid
	 	 				if(currentFilterArr[i].status == "checked"){
	 	 					filterIOID = true;
	 	 				}else{
	 	 					filterIOID = false;	
	 	 				}
 	 				}
 	 			}

 	 			
 	 			//show all concerns link
 	 				if(currentFilter.length == 0){
 	 					$('showAllLink').style.display = 'none';
 	 				}else{
 	 					$('showAllLink').style.display = 'inline';
 	 				}
 	 			
				var currentFilterString = currentFilter.toString();
				if(filterIOID){ //check if filtering by ioid or not
					var ioid = ${object.id};
				}else{
					var ioid = "";
				}
				
				SDAgent.getConcerns({isid: ${structure.id},ioid: ioid, tags: currentFilterString, count: "5", page: sidebarPage}, {
				callback:function(data){
						if (data.successful){
              				 $('sidebar_content').innerHTML = data.source.html;//using partial sidebar-concerns.jsp
              			//sidebarFilter
		 	 			var filters = "";
		 	 			//alert(${object.object.theme.title});
		 	 			filters += '<ul class="filter">';
						
		 	 			for(i=0; i<currentFilterArr.length; i++){
		 	 				if(currentFilterArr[i].removeable){
			 	 				filters += '<li><input type="checkbox" id="filtercheck'+i+'" onclick="checkFilter('+i+')"  '+ currentFilterArr[i].status +' /> '+getTagByTagRef(currentFilterArr[i].tagRefId);
			 	 				filters += '&nbsp;<a href="javascript: removeUlFilter('+i+');"><img src="/images/trash.gif" alt="remove filter" border="0" /></a>';
			 	 				filters +='<ul class="filter">';
		 	 				}else{ //if ioid
		 	 					filters += '<li><input type="checkbox" id="filtercheck'+i+'" onclick="checkIOIDFilter('+i+')"  '+ currentFilterArr[i].status +' />Theme Filter ('+ data.num +' tags)';
			 	 				filters +='<ul class="filter">';
		 	 				}
		 	 			}
		 	 			filters += '</ul>';
		 	 			$('ulfilters').innerHTML = filters;
		 	 			
              				 
							displayIndicator(false);
						}else{
							alert(data.reason);
							displayIndicator(false);
						}
					},
				errorHandler:function(errorString, exception){ 
						alert("get concerns error:" + errorString + exception);
				}
				});
				
		}
		
		function checkFilter(index){
			if(currentFilterArr[index].status == "unchecked"){
				currentFilterArr[index].status = "checked";
			}else{
				currentFilterArr[index].status = "unchecked";
			}
			getConcerns();
		}
		
		function checkIOIDFilter(index){
			if(currentFilterArr[index].status == "unchecked"){
				currentFilterArr[index].status = "checked";
			}else{
				currentFilterArr[index].status = "unchecked";
			}
			filterIOID = true;
			getConcerns();
		}
		
		function getTagByTagRef(tagRefId){
			var tag = "tag" + tagRefId;
			
			CCTAgent.getTagByTagRefID({id: tagRefId}, {
			callback:function(data){
			if (data.successful){
            			alert(data);
					}else{
						alert(data.reason);
					}
				},
			errorHandler:function(errorString, exception){ 
					alert("get targets error:" + errorString + exception);
			}
			});
			return tag	
		}
		
		function Filter(tagRefId, status, bool){
			this.tagRefId = tagRefId;
			this.status = status;
			this.removeable = bool
		}
		
		function addFilter(tagRefId){
				tagRefId.toString();
				var filterInstance = new Filter(tagRefId, "checked", true);
				currentFilterArr.push(filterInstance);
				getConcerns();
		}
		
		function addIOIDFilter(){
			var filterInstance = new Filter(${object.id}, "checked", false);
			currentFilterArr.push(filterInstance)
			getConcerns();	
		}
		
		function removeFilter(){
				currentFilterArr.pop();
				getConcerns();
		}
		
		function removeUlFilter(index){
				currentFilterArr.splice(index, 1);
				getConcerns();
		}
		
		function changeCurrentFilter(tagRefId){
				
				if (!filterIOID || currentFilterArr.length > 1) {//if filtering by ioid, add a new filter, not change it
					currentFilterArr.pop()
				};
				addFilter(tagRefId);
		}
		
		function clearFilter(){
			for(i=0; i<currentFilterArr.length; i++){
				currentFilterArr[i].status = "unchecked";	
			}
			getConcerns()	;
		}
		
		function clearSearch(){
			$('txtmanualFilter').value = "";	
			$('txtmanualFilter').focus();
			$('btnClearSearch').style.display = 'none';
		}
			
		function closeSearchResults(){
			 new Effect.Fade('sidebarSearchResults', {duration: 0.3});
		}
		
		//functions lifted from CCT
		function sidebarTagSearch(theTag,key){
				//showing and hiding clear search action
				if (theTag != ""){
					$('btnClearSearch').style.display = 'inline';
				}else{
					closeSearchResults();
					clearSearch();
			}
				//hack to disable backspace on input box when length < 1 - "19 tags hack"
				if (key.keyCode == 8 && theTag.length < 1){
					return false;	
				}
				
				//if the query is greater than 2 chars do the action if not keep it hidden
				if($('txtmanualFilter').value.length > 2){
					sidebarSearchTagsAction(theTag);
				}else{
					$('sidebarSearchResults').style.display == 'none'
				}
			}
	
		function sidebarSearchTagsAction(theTag){
				CCTAgent.searchTags({cctId:cctId,tag:theTag},{
					callback:function(data){
							if (data.successful){
								//show results if hidden
								if($('sidebarSearchResults').style.display == 'none'){
									new Effect.Appear('sidebarSearchResults', {duration: 0.5});		
								}		
								
								$('sidebarSearchResults').innerHTML = $('sidebarSearchResults').innerHTML = data.html;
								
								if (data.count == 0){
									$('sidebarSearchResults').innerHTML = '<span class="closeBox"><a href="javascript:Effect.Fade(\'sidebarSearchResults\', {duration: 0.5}); void(0);">Close</a></span><p>No tag matches found! Please try a different search.</p> ';
								}
							}
					},
					errorHandler:function(errorString, exception){ 
								alert("sidebarSearchTagsAction: "+errorString+" "+exception);
								//showTheError();
					}		
				});	
		}
		
	function getConcernsByTag(tagRefId){
			addFilter(tagRefId);	
			$('addFilter').style.display = 'none';
			if($('sidebarSearchResults').style.display != 'none'){
				closeSearchResults();
			}
			clearSearch();
			shrinkTagSelector();
	}
	
	function getTagCloud(){
			CCTAgent.getTagCloud({cctId:cctId,type:0,count:1000}, {
				callback:function(data){
					if (data.successful){
						$('allTags').innerHTML = data.html;
						
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("getTagCloud: "+errorString+" "+exception);
					//showTheError();
				}
		});
		}

		function tagSearch(theTag){
		CCTAgent.searchTags({cctId:cctId,tag:theTag},{
			callback:function(data){
				  $('tagIndicator').style.visibility = 'visible';
					if (data.successful){
						if ($('txtSearch').value == ""){
							$('topTags').innerHTML = "";
							$('tagSearchResults').innerHTML = '<span class="highlight">Please type in your query or <a href="javascript:getTagCloud();">clear query</a>&nbsp;to view top tags again.</span>';
						  	$('tagIndicator').style.visibility = 'hidden';
						}
						if ($('txtSearch').value != ""){
							$('tagSearchResults').innerHTML = '<span class="highlight">' + data.count +' tags match your query&nbsp;&nbsp;(<a href="javascript:getTagCloud();">clear query</a>)</span>';
							$('topTags').innerHTML = data.html;
							$('tagIndicator').style.visibility = 'hidden';
						}
						if (data.count == 0 || $('txtSearch').value == "_"){
							$('tagSearchResults').innerHTML = '<span class=\"highlight\">No tag matches found! Please try a different search or <a href="javascript:getTagCloud();">clear the query</a>&nbsp;to view top tags again.</span>';
							$('topTags').innerHTML = "";
							$('tagIndicator').style.visibility = 'hidden';
							
						}
					}
			},
			errorHandler:function(errorString, exception){ 
						alert("tagSearch: "+errorString+" "+exception);
						//showTheError();
			}		
		});
		}
	/////////////////////end sidebar functionality////////////////////////////
//-->


</script>
</head>


<body onLoad="MM_preloadImages('images/btn_continue_b.gif')">

<div id="container">
	<jsp:include page="/header.jsp" />
		
   <div id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>
	
	<!-- Sub Title -->
	<div id="subheader">
	<h1>Step 1b:</h1> 
	<h2>Review Summaries</h2>
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
			<p>Several participants have submitted their concerns about the transportation system and the moderator has taken these concerns and clustered them into themes, listed below. Each theme has concerns and their tags associated with it, and the moderator has composed a summary description of the theme. Please review these themes and discuss how you think they appropriately or innapropriately articulate the concerns submitted by participants. Use the right column (the sidebar) to explore concerns. </p>
							
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
					<div id="sidebarHeader" style="padding: 5px;">
						<h4>Participant Concerns</h4>
						<p>
						 <!-- optional context sidebar paragraph -->
						 
						 <!-- end optional context sidebar paragraph -->
						</p>
					</div>
					<!-- start tagselector -->
						<div id="tagSelector">
							<div id="tagform">
							<div id="showAllLink" class="textright"><a href="javascript:clearFilter();">Show All Concerns</a></div>
							<h6>Filter(s):</h6><span id="ulfilters"></span>
							<!-- insert filter list here -->
							<p><a href="javascript: Effect.toggle('addFilter', 'blind', {duration: 0.2}); void(0);">Add a Tag Filter</a></p>
							
							<div id="addFilter" style="display: none;">
								<span class="textright"><a href="javascript: Effect.toggle('addFilter', 'blind', {duration: 0.2}); void(0);"><img src="images/close1.gif" alt="Close" name="closeresults" class="button" id="closeresults" onMouseOver="MM_swapImage('closeresults','','images/close.gif',1)" onMouseOut="MM_swapImgRestore()"></a></a></span>
								<b>Add a Tag Filter:</b> 
								<form id="frmSidebarTagSearch" onSubmit="sidebarSearchTagsAction($('txtmanualFilter').value); return false;">
									<input name="txtmanualFilter" id="txtmanualFilter" type="text" onKeyDown="sidebarTagSearch(this.value, event)" onkeyup="sidebarTagSearch(this.value, event)" /><span id="btnClearSearch" style="display: none;"><a href="javascript:clearSearch(); closeSearchResults();"><img src="/images/clearText.gif" border="0" alt="clear textbox" /></a></span>
								</form>
								<p>or <a href="javascript: expandTagSelector();">Browse All Tags</a>
									
								<div id="sidebarSearchResults" style="display: none;"><!-- tag search results are loaded here --></div>
							</div>
							
						</div>
						<div id="pullDown" class="textright"></div>
						<div id="allTags" style="display: none;"></div>
						<div class="clear"></div>
						
					</div>
					<!-- end tag selector -->
					
					 <div id="sidebar_content">
					
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

<div class="padding-finished">
<div id="finished" class="greenBB">
	<h4>Ready for the next step?</h4><br />
	<p>Click on the continue button to go on to step 2 where you will review and weigh criteria to evaluate proposed transportation projects.  Go back to your <a href="main.do">home page</a> or  <a href="/sdcWaiting.jsp"><img border="0" src="images/btn_gcontinue_a.gif" alt="Continue" name="continue" class="button" id="continue" onMouseOver="MM_swapImage('continue','','images/btn_gcontinue_b.gif',1)" onMouseOut="MM_swapImgRestore()"></p>
</div>
</span>

<!-- start feedback form -->
		<pg:feedback id="feedbackDiv" action="sdRoom.do" />
	
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
	addIOIDFilter();
</script>

</body>

</html:html>

