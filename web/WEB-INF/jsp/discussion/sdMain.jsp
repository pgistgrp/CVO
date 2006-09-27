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
<!--
<c:if test="${structure.type == 'sdmap'}">

	<script type='text/javascript' src='scripts/map.js'></script>
	<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAADmWGO07Q7ZeAHCvFNooqIxTwM0brOpm-All5BF6PoaKBxRWWERTgXzfGnh96tes2zXXrBXrWwWigIQ" type="text/javascript"></script>
	<script type='text/javascript' src='/dwr/interface/ProjectAgent.js'></script>
	<style type="text/css">
		v\:* {
		  behavior:url(#default#VML);
		}
	</style>
</c:if>
-->
<!--End SDX Specific  Libraries-->
<script type="text/javascript">
	 function InfoStructure(){
	 	displayIndicator(true);
		this.isDivElement = 'object';
	 	 this.getTargets = function(){
				SDAgent.getTargets({isid:${structure.id}}, {
				callback:function(data){
						if (data.successful){
							
							$(infoStructure.isDivElement).innerHTML = data.source.html;
              				eval(data.source.script);
              				 
		              		 if(data.voting == null || data.voting == undefined){
						           $('structure_question').innerHTML = '<span class="smalltext">Does this summary adequately reflect concerns expressed by participants? <a href="javascript:infoStructure.setVote(\'true\');"><img src="images/btn_yes_a.gif" alt="Yes" name="yes" class="button" id="yes"><a href="javascript:infoStructure.setVote(\'false\');"><img src="images/btn_no_a.gif" alt="No" name="no" class="button" id="no"></a></span>';

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
						alert("get targets error: " + errorString +" "+ exception);
				}
				});
			};

	 	 this.setVote = function(agree){
	 	 			displayIndicator(true);
					SDAgent.setVoting({isid: ${structure.id}, agree:agree}, {
					callback:function(data){
							if (data.successful){
	              				 //alert("thank you for your vote");
	              				 new Effect.Fade('structure_question', {afterFinish: function(){infoStructure.getTargets(); new Effect.Appear('structure_question');}});
	              				 displayIndicator(false);
							}else{
								alert(data.reason);
								 displayIndicator(false);
							}
						},
					errorHandler:function(errorString, exception){ 
							alert("setVote:" + errorString + exception);
					}
					});
				};
			};
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
		
		
		
		//end sidebar global vars
		function getConcerns(page){
			//alert("cctid: ${cct.id}");
				displayIndicator(true);
				//pagination
				var sidebarPage = 1
				if (page != undefined){
					sidebarPage = page;
				}

 	 			//sidebarFilter
 	 			var filters = "";
 	 			var currentFilter = new Array();
 	 			filters += '<ul class="filter">';
 	 			for(i=0; i<currentFilterArr.length; i++){
 	 				
 	 				filters += '<li><input type="checkbox" id="filtercheck'+currentFilterArr[i].tagRefId+'" onclick="checkFilter('+i+')"  '+ currentFilterArr[i].status +' /> '+getTagByTagRef(currentFilterArr[i].tagRefId)+'<ul class="filter">';
 	 				if(currentFilterArr[i].status == "checked"){
 	 					currentFilter.push(currentFilterArr[i].tagRefId);
 	 				}
 	 			}
 	 			filters += '</ul>';
 	 			$('ulfilters').innerHTML = filters;
 	 			
 	 			//show all concerns link
 	 				if(currentFilter.length == 0){
 	 					$('showAllLink').style.display = 'none';
 	 				}else{
 	 					$('showAllLink').style.display = 'inline';
 	 				}
 	 				
 	 			//show title
 	 				if(currentFilterArr.length == 0){
 	 					$('filterheader').style.display = 'none';
 	 				}else{
 	 					$('filterheader').style.display = 'inline';
 	 				}
 	 			
				var currentFilterString = currentFilter.toString();
				SDAgent.getConcerns({isid: ${structure.id},tags: currentFilterString, count: "5", page: sidebarPage}, {
				callback:function(data){
						if (data.successful){
              				 $('sidebar_content').innerHTML = data.source.html;//using partial sidebar-concerns.jsp
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
		}
		
		function checkFilter(index){
			if(currentFilterArr[index].status == "unchecked"){
				currentFilterArr[index].status = "checked";
			}else{
				currentFilterArr[index].status = "unchecked";
			}
			getConcerns();
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
		
		function Filter(tagRefId, status){
			this.tagRefId = tagRefId;
			this.status = status;
		}
		
		function addFilter(tagRefId){
				tagRefId.toString();

				var filterInstance = new Filter(tagRefId, "checked");
				currentFilterArr.push(filterInstance);
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
				currentFilterArr.pop();
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
</script>

</head>
<!--
<c:choose>
  <c:when test="${structure.type == 'sdmap'}">
 		<body onUnload="GUnload()">
  </c:when>

  <c:otherwise>
  		<body>
 </c:otherwise>
</c:choose>-->

<body>
<div id="container">
		 <div id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>
		<jsp:include page="/header.jsp" />
		<!-- Header -->
		<div id="cont-top">
		<!-- Sub Title -->
		<div id="subheader">
		<h1>Step 1b:</h1> 
		<h2>Review Summaries</h2>
		</div>
		<div id="footprints">
		<span class="smalltext"><a href="#">Participate</a> &raquo; <a href="#">Step 1 Brainstorm Concerns</a> &raquo; Step 1b Review Summaries</span>
		</div>
		<!-- End Sub Title -->
		<!-- Overview SpiffyBox -->
		<div class="cssbox">
			<div class="cssbox_head">
				<h3>Overview</h3>
			</div>
			<div class="cssbox_body">
				<p>Several participants have submitted their concerns about the transportation system and the moderator has taken these concerns and clustered them into themes, listed below. Each theme has concerns and their tags associated with it, and the moderator has composed a summary description of the theme. Please review these themes and discuss how you think they approprirately or inappropriately articulate the concerns submitted by participants. Use the right coumn to explore concerns. </p>
				
				<p>[ <a href="/readmore.jsp">Read more about how this step fits into the bigger picture.</a> ]</p>
			</div>
		</div>
		<!-- End Overview -->
		
		</div> <!-- End cont-top -->
		
		<div id="cont-main">
		
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
		<td id="maintop"><img src="#" alt="" height="1" width="1"/></td>
		<td><img src="images/sidebar_top.gif" alt="sidebartop" /></td>
		</tr>
		<tr>
		<td valign="top" id="maincontent">
		<!-- Main Content starts Here-->
		<!--
		<c:if test="${structure.type == 'sdmap'}">
			<div id="map" style="width: 100%; height: 400px;"></div>
		</c:if>
		-->
		
		<h4>Select a Theme to Review and Discuss Concern Summary </h4>
		<div id="object">
			<!-- load discussion rooms -->
		</div><!-- End Object -->
				
		<br />
				<div>
				  <table width="100%" class="tabledisc">
				          <tr class="disc_row_b">
							<jsp:useBean id="today" class="java.util.Date"/>
							<c:set var="fmtLastPostDate"><fmt:formatDate value="${structure.lastPost.createTime}" pattern="yyyy/MM/dd"/></c:set>
    						<c:set var="fmtToday"><fmt:formatDate value="${today}" pattern="yyyy/MM/dd"/></c:set>
							
							  <c:choose>
							  <c:when test="${fmtToday == fmtLastPostDate}">
							  	 <td width="40" class="textcenter"><img src="/images/balloonactive2.gif" alt="Posts within the last 24 hours" /></td>
							  </c:when>
							  <c:otherwise>
							  	 <td width="40" class="textcenter"><img src="/images/ballooninactive2.gif" alt="No posts within the last 24 hours" /></td>
							  </c:otherwise>
							  </c:choose>
							  <td><div class="padding-sides"><a href="/sdRoom.do?isid=${structure.id}">Discussion about all concern themes</a><br />
							    <span class="smalltext">Do you feel like a theme is missing  from the above list? Have a question about the summary process? Discuss here</span></div></td>
				 		    <td><span class="smalltext" style="font-size: 80%;">
				 		    <c:choose>
						      <c:when test="${structure.lastPost.id != null}">
						     		<div class="padding-sides">
									<a href="/sdThread.do?isid=${structure.id}&pid=${structure.lastPost.id}">${structure.lastPost.title}</a><br />
						      		Posted on: <fmt:formatDate value="${structure.lastPost.createTime}" pattern="MM/dd/yy, hh:mm aaa"/> by: ${structure.lastPost.owner.loginname}
									</div>
						      </c:when>
						      <c:otherwise>
						      		<span class="padding-sides">No current discussions</span>
						      </c:otherwise>
						    </c:choose>          
							</td>
				            <td width="100" class="textcenter"><a href="/sdRoom.do?isid=${structure.id}&ioid=">${structure.numDiscussion}</a></td>
				          </tr>		    
		        </table><br>
				
				<div id="legend" class="smalltext">
					<img src="/images/balloonactive2.gif" alt="active" class="button">
				 Recent Post  <img src="/images/ballooninactive2.gif" alt="active" width="20" height="21" class="button"> No Recent Post </div>
			</div>
				
			<div id="finished" class="greenBB">
				<h4>Ready for the next step?</h4><br />
				<p>Click on the continue button to go on to step 2 where you will review and weigh criteria to evaluate proposed transportation projects.  Go back to your <a href="main.do">home page</a> or  <a href="/sdcWaiting.jsp"><img src="images/btn_gcontinue_a.gif" border="0" alt="Continue" name="continue" class="button" id="continue" onMouseOver="MM_swapImage('continue','','images/btn_gcontinue_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a></p>
			</div>
		
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
		<h6 id="filterheader">Filter(s):</h6><span id="ulfilters"></span>
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
		<!--end sidebar-->
	
		
		
		</div><!-- End cont-main -->
</div> <!-- End container -->

<!-- start feedback form -->
		
		<pg:feedback id="feedbackDiv" action="sdMain.do" />
	
<!-- end feedback form -->

<!-- Start Footer -->
<jsp:include page="/footer.jsp" />

<!-- End Footer -->
<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
<script type="text/javascript">
	var infoStructure = new InfoStructure(); 
	infoStructure.getTargets();
	getConcerns();
/*
	<c:if test="${structure.type == 'sdmap'}">
		var pgistmap = new PGISTMap('map');
		var idList = new Array();
		<c:forEach var="infoObject" items="${structure.infoObjects}">
			idList[idList.length] = '${infoObject.id}';
		</c:forEach>
		pgistmap.setProjectList(idList);
		pgistmap.projectClickHandler = function(projId){
			//this is a callback function to load the sidebar_bottom
			
		}
	</c:if>
	*/
</script>
</body>

</html:html>