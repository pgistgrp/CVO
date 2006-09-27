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
<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>
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
	
		/////////////////////sidebar functionality////////////////////////////
	
	
		//sidebar global vars
		var currentFilterArr = new Array();
		var cctId = 1171; 
		var filterPID = false;
		
		//end sidebar global vars
		function getContextPosts(page){
			//alert("cctid: ${cct.id}");
				displayIndicator(true);
				//pagination
				var sidebarPage = 1
				if (page != undefined){
					sidebarPage = page;
				}

 	 			//sidebarFilter
 	 			var currentFilter = new Array();
 	 			for(i=0; i<currentFilterArr.length; i++){
 	 				if(currentFilterArr[i].removeable){
	 	 				if(currentFilterArr[i].status == "checked"){
	 	 					currentFilter.push(currentFilterArr[i].tagRefId);
	 	 				}
 	 				}else{ //if ioid
	 	 				if(currentFilterArr[i].status == "checked"){
	 	 					filterPID = true;
	 	 				}else{
	 	 					filterPID = false;	
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
				if(filterPID){ //check if filtering by pid or not
					var pid = ${post.id};
				}else{
					var pid = "";
				}
				
				SDAgent.getContextPosts({isid: ${structure.id},pid: pid, tags: currentFilterString, count: "5", page: sidebarPage}, {
				callback:function(data){
						if (data.successful){
              				 $('sidebar_content').innerHTML = data.source.html;//using partial sidebar-concerns.jsp
	              				 
	              			//sidebarFilter
			 	 			var filters = "";
			 	 			var currentFilter = new Array();
			 	 			filters += '<ul class="filter">';
			 	 			for(i=0; i<currentFilterArr.length; i++){
				 	 			if(currentFilterArr[i].removeable){
					 	 				filters += '<li><input type="checkbox" id="filtercheck'+i+'" onclick="checkFilter('+i+')"  '+ currentFilterArr[i].status +' /> '+(currentFilterArr[i].tagRefId);
					 	 				filters += '&nbsp;<a href="javascript: removeUlFilter('+i+');"><img src="/images/trash.gif" alt="remove filter" border="0" /></a>';
					 	 				filters +='<ul class="filter">';
				 	 				}else{ //if ioid
				 	 					filters += '<li><input type="checkbox" id="filtercheck'+i+'" onclick="checkPIDFilter('+i+')"  '+ currentFilterArr[i].status +' />current post\'s '+ data.num +' tags';
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
			getContextPosts();
		}
		
		function checkPIDFilter(index){
			if(currentFilterArr[index].status == "unchecked"){
				currentFilterArr[index].status = "checked";
			}else{
				currentFilterArr[index].status = "unchecked";
			}
			filterPID = true;
			getContextPosts();
		}
		
		function getTagByTagRef(tagRefId){
			var tag = "tag" + tagRefId;
			/*
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
			});*/
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
				getContextPosts();
		}
		
		function addPIDFilter(){
			var filterInstance = new Filter(${object.id}, "checked", false);
			currentFilterArr.push(filterInstance)
			getContextPosts();	
		}
		
		function removeFilter(){
				currentFilterArr.pop();
				getContextPosts();
		}
		
		function removeUlFilter(index){
				currentFilterArr.splice(index, 1);
				getContextPosts();
		}
		
		function changeCurrentFilter(tagRefId){
				
				if (!filterPID || currentFilterArr.length > 1) {//if filtering by pid, add a new filter, not change it
					currentFilterArr.pop()
				};
				addFilter(tagRefId);
		}
		
		function clearFilter(){
			for(i=0; i<currentFilterArr.length; i++){
				currentFilterArr[i].status = "unchecked";	
			}
			getContextPosts()	;
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
		<c:if test="${fn:length(post.tags) != 0}">
		<ul class="tagsList"><strong>Tags: </strong>
			<c:forEach items="${post.tags}" var="tag">
					<li class="tagsList">${tag.name}</li>
			</c:forEach>
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
					<div id="sidebarHeader" style="padding: 5px;">
						<h4>Other Discussions</h4>
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

 </td><!-- End Right Col -->
</tr>

</table>
<div id="sidebarbottom" style="text-align:right;"><img src="images/sidebar_bottom.gif" alt="sidebarbottom" /></div>
<div class="backToDiscussion"><a href="sdRoom.do?isid=${structure.id}&ioid=${object.id}"><img src="images/btn_back_a.gif" alt="back2" name="back" class="button" id="back2" onMouseOver="MM_swapImage('back2','','images/btn_back_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a></div>

<div id="finished" class="greenBB">
	<h4>Ready for the next step?</h4><br />
	<p>Click on the continue button to go on to step 2 where you will review and weigh criteria to evaluate proposed transportation projects.  Go back to your <a href="main.do">home page</a> or  <a href="sdcWaiting.jsp"><img border="0" src="images/btn_gcontinue_a.gif" alt="Continue" name="continue" class="button" id="continue" onMouseOver="MM_swapImage('continue','','images/btn_gcontinue_b.gif',1)" onMouseOut="MM_swapImgRestore()"></p>
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
	addPIDFilter();
</script>
</html:html>


