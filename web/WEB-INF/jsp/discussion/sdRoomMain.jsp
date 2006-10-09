<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en"><head>
<title>Structured Discussion Main</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/position.css";</style>
<style type="text/css" media="screen">@import "styles/styles.css";</style>
<!-- Temporary Borders used for testing <style type="text/css" media="screen">@import "styles/tempborders.css";</style>-->
<!-- End Site Wide CSS -->


<!-- Site Wide JavaScript -->
<script src="scripts/search.js" type="text/javascript"></script>
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
<script src="scripts/SideBar.js" type="text/javascript"></script>
<script src="scripts/InfoObject.js" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/interface/SDAgent.js'></script>
<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>
<!--End SDX Specific  Libraries-->


<script type="text/javascript">
<!--
	///////////////////////////////////////// START INFO OBJECT //////////////////////////////////////
	/*  Requires: InfoObject.js ***************************  See file for element ID needs
	Create a new Instance of InfoObject
		params
			- Structure id
			- Object id
			- Object title
			- Number of Object Discussions
			- Number of Structure Discussions
			- Title of Structure
			- Current Discussion Page
		
	Methods to define for this Instance
		infoObject.getTargets();
	*/

	var infoObject = new InfoObject("${structure.id}","${object.id}","${object.object}", "${object.numDiscussion}", "${structure.numDiscussion}", "All Concern Themes", '<%= request.getParameter("page") %>');
	 
	/*************** Get Targets - If IOID is ommitted, return sdcSummary.jsp::else, returns sdcStructureSummary.jsp************** */
	 infoObject.getTargets = function(){
	 		displayIndicator(true);
	 		SDAgent.getSummary({isid: this.structureId, ioid: this.objectId  }, {
					callback:function(data){
							if (data.successful){
								
	              			  $(infoObject.objectDiv).innerHTML = data.source.html; 
	              			  if(data.voting == null || data.voting == undefined){
						           $(infoObject.votingQuestionDiv).innerHTML = '<span class="smalltext">Do you feel this summary adequately reflects concerns expressed by participants? <a href="javascript:infoObject.setVote(\'true\');"><img src="/images/btn_yes_a.gif" alt="YES" class="button"><a href="javascript:infoObject.setVote(\'false\');"><img src="/images/btn_no_a.gif" alt="NO" class="button"></a></span>';
					          }else{ //user has already voted
						           $(infoObject.votingQuestionDiv).innerHTML = '<span class="smalltext">Your vote has been recorded. Thank you for your participation.</span>';
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
	 	};
	 	
	 ///////////////////////////////////////// START SIDEBAR //////////////////////////////////////
	 
	 /*  Requires: SideBar.js *************************** See file for element ID needs
	Create a new Instance of SideBar
		params
			- Structure id
			- Object id
			- CCT Id
			- Object title
			- Title of Entire Info structure
			- Count of items in sideBar
			- Show all link text
			- Filter header text
			- Tag cloud count
			- PostID
		
	Methods to define for this Instance
		sidebar.getAbstractItems(tags, page);
		sideBar.convertAbstractFilter(tagRefId);
		sideBar.sidebarSearchTagsAction(theTag)
		sideBar.getAbStractTagCloud(page)
		sideBar.getAbstractTagCloudResults(theTag)
	*/
	var sideBar = new SideBar("${structure.id}", "${object.id}","${structure.cctId}","${object.object}", "Participant Concerns", 5, "Show All Concerns", "Filter All Concerns By", 50, "" ); 
	sideBar.getAbstractItems = function(tags, page){
		SDAgent.getConcerns({isid: this.structureId,ioid: this.objectId, tags: tags, count: this.itemsCount, page: page}, {
			callback:function(data){
					if (data.successful){
	          			$(sideBar.divContent).innerHTML = data.source.html;//using partial sidebar-concerns.jsp
						sideBar.renderFilters();		 	 						            				 
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
	};
	
	sideBar.convertAbstractFilter = function(tagRefId){
		CCTAgent.getTagByTagRefId(tagRefId, {
		callback:function(data){
		if (data.successful){
          			var tagName = data.tag.name;
					sideBar.addFilterToArr(tagRefId, tagName)
				}else{
					alert(data.reason);
				}
		},
		errorHandler:function(errorString, exception){ 
				alert("get tagbytagref error:" + errorString + exception);
		}
		});
	};
	
		sideBar.sidebarSearchTagsAction = function(theTag){
			CCTAgent.searchTags({cctId:this.cctId,tag:theTag},{
				callback:function(data){
						if (data.successful){
							sideBar.renderSearchTagResults(data);
						}
				},
				errorHandler:function(errorString, exception){ 
							alert("sidebarSearchTagsAction: "+errorString+" "+exception);
							//showTheError();
				}		
			});	
		};
		
		sideBar.getAbStractTagCloud = function(page){
			CCTAgent.getTagCloud({cctId:this.cctId,type:2, page: page, count:this.tagCloudCount}, {
					callback:function(data){
						if (data.successful){
							$(sideBar.divAllTags).innerHTML = data.html;	
						}
					},
					errorHandler:function(errorString, exception){ 
					alert("getTagCloud: "+errorString+" "+exception);
					}
			});
		}
		
		sideBar.getAbstractTagCloudResults = function(theTag){
			CCTAgent.searchTags({cctId:this.cctId,tag:theTag},{
				callback:function(data){
						if (data.successful){
							sideBar.renderTagCloudSearchResults(data);
						}
				},
				errorHandler:function(errorString, exception){ 
							alert("tagSearch: "+errorString+" "+exception);
				}		
			});
		}
	///////////////////////////////////////// END SIDEBAR //////////////////////////////////////
			 	 



//-->


</script>

<style type="text/css">

#loading-indicator{
	
	background-color: red;
	color: white;
	position:absolute;
	top: 0;
	left:0;
	padding: 3px;
	z-index: 500;
}



div > div#loading-indicator{
position:fixed;
}
</style>
<!--[if gte IE 5.5]><![if lt IE 7]>
		<style type="text/css">
#loading-indicator {
left: expression( ( 0 + ( ignoreMe2 = document.documentElement.scrollLeft ? document.documentElement.scrollLeft : document.body.scrollLeft ) ) + 'px' );
top: expression( ( 0 + ( ignoreMe = document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop ) ) + 'px' );
}
		</style>
		<![endif]><![endif]-->
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
	<span class="smalltext"><a href="http://128.95.212.210:8080/main.do">Participate</a> &raquo; <a href="http://128.95.212.210:8080/cctview.do?cctId=1171">Step 1a Brainstorm Concerns</a> &raquo; <a href="sd.do?isid=${structure.id}">Step 1b Review Summaries</a> &raquo; ${object.object}</span>
	</div>
	<!-- End Sub Title -->
	
	<!-- Overview SpiffyBox -->
	<div class="cssbox">
		<div class="cssbox_head">
			<h3>Overview and Instructions</h3>
		</div>
		<div class="cssbox_body">
			<p>Several participants have brainstormed concerns about the
				transportation system. The moderator has reviewed these concerns and
				clustered them into themes. The moderator also composed a summary
				description of the theme. Each theme is associated with a set of tags
				and concerns. In this step you can review the concern theme summaries and discuss
				how well you feel they "fit" your own concerns and those expressed by
				other participants. You can use the right column (the sidebar) to
				review all concerns. </p>
							
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
		<table border="0" cellpadding="0" cellspacing="0">
				<tr>
						<td id="maintop"><img src="" alt="" height="1" width="1"/></td>
						<td><img src="images/sidebar_top.gif" alt="sidebartop" /></td>
				</tr>
				<tr>
						<td valign="top" id="maincontent">
							<!-- Main Content starts Here-->
							<script type="text/javascript">
								<c:choose>
									<c:when test="${object.id==null}">
										document.write("<h4>Summarization of Participant Concerns</h4>");
									</c:when>
									<c:otherwise>
										document.write("<h4>Summarization of Participant Concerns about ${object.object}</h4>");
									</c:otherwise>
								</c:choose>
							</script>
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
						<h4 id="sidebarTitle"></h4>
						<p>
						 <!-- optional context sidebar paragraph -->
						 
						 <!-- end optional context sidebar paragraph -->
						</p>
					</div>
						<!-- start tagselector -->
							<div id="tagSelector">
								<div id="showAllLink"></div>
								<div id="tagform">
								<h6 id="filterheader"></h6><span id="ulfilters"></span>
								<!-- insert filter list here -->
								<p><a href="javascript: Effect.toggle('addFilter', 'blind', {duration: 0.2}); void(0);">Add a Tag Filter</a></p>
								
								<div id="addFilter" style="display: none;">
									<span class="textright"><a href="javascript: Effect.toggle('addFilter', 'blind', {duration: 0.2}); void(0);"><img src="images/close1.gif" alt="Close" name="closeresults" class="button" id="closeresults" onMouseOver="MM_swapImage('closeresults','','images/close.gif',1)" onMouseOut="MM_swapImgRestore()"></a></a></span>
									<b>Add a Tag Filter:</b> 
									<form id="frmSidebarTagSearch" onSubmit="sideBar.sidebarSearchTagsAction($('txtmanualFilter').value); return false;">
										<input name="txtmanualFilter" id="txtmanualFilter" type="text" onKeyDown="sideBar.sidebarTagSearch(this.value, event)" onKeyUp="sideBar.sidebarTagSearch(this.value, event)" /><span id="btnClearSearch" style="display: none;"><a href="javascript:sideBar.clearSearch(); sideBar.closeSearchResults();"><img src="/images/clearText.gif" border="0" alt="clear textbox" /></a></span>
									</form>
									<p>or <a href="javascript: expandTagSelector();">Browse All Tags</a></p>
										
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
					<div id="tempvars" style="display: none;"></div>
					</div><!-- sidebar container-->
					</td>
				<!-- End Right Col -->
				</tr>
		</table>
		<!--changed  display:block;-->
		<div id="sidebarbottom_disc" style="text-align:right; display:block;"><img src="/images/sidebar_bottom.gif" alt="sidebarbottom" /></div>
		<div id="sidebarbottom_newdisc" style="text-align:right; display: none;"><img src="/images/sidebar_bottom.gif" alt="sidebarbottom" /></div>
	</div><!-- End cont-main -->
	<div id="newDiscussion" style="display: none">
		<div id="newdisc_title" >
			<div class="textright"><span id="closeNewDiscussion" class="closeBox"><a href="javascript:infoObject.toggleNewDiscussion();"><img src="/images/btn_close.gif" class="button"></a></span></div>
			<h3 style="display: inline">New Topic</h3>
		</div> <!-- End newdisc_title -->
		<div id="newdisc_content" class="greenBB">
			<div id="newdisc_inner">
			<p>SDC New Topic Paragraph</p>
			<form>
				<p><label>Post Title</label><br><input maxlength=100 size=100 type="text" id="txtNewPostTitle"/></p>
				<p><label>Your Thoughts</label><br><textarea style="width:100%; height: 200px;" id="txtNewPost"></textarea></p>
				<p><label>Tag your post (comma separated)</label><br><input style="width:100%" id="txtNewPostTags" type="text" /></p>
				<input type="button" onClick="infoObject.createPost();" value="Create Discussion">
			</form>
			</div>
		</div>
	</div>
		
	<div id="discussion-cont">
		<div id="discussion-inner">
			<span class="padding"><h4 id="targetDiscussionTitle"></h4></span><span id="closeNewDiscussion" class="closeBox"><a href="javascript:infoObject.toggleNewDiscussion();"><img src="images/btn_gnewtopic_a.gif" alt="New Topic" name="newtopic" class="button" id="newtopic" onMouseOver="MM_swapImage('newtopic','','images/btn_gnewtopic_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a></span>
			<div id="discussion"><!-- load discussion posts --></div>
		</div><!-- end discussion-inner -->
	</div><!-- end discussion-cont -->
	<div id="legend" class="smalltext">
	<img src="/images/balloonactive2.gif" alt="active" class="button">
	Recent Post  <img src="/images/ballooninactive2.gif" alt="active" width="20" height="21" class="button"> No Recent Post </div>
	</div>
	
	<div id="backdisc"><a href="sd.do?isid=${structure.id}"><img src="images/btn_back_a.gif" alt="Back" name="back" class="button" id="next" onMouseOver="MM_swapImage('back','','images/btn_back_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a></div>	  

<div class="padding-finished">
<div id="finished" class="greenBB">
	<h4>Ready for the next step?</h4><br />
	<p>Click continue to go on to step 2 where we will review and weigh criteria to evaluate proposed transportation projects.  Go back to your <a href="main.do">home page</a> or  <a href="/sdcWaiting.jsp"><img border="0" src="images/btn_gcontinue_a.gif" alt="Continue" name="continue" class="button" id="continue" onMouseOver="MM_swapImage('continue','','images/btn_gcontinue_b.gif',1)" onMouseOut="MM_swapImgRestore()"></p>
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
	infoObject.getPosts();
	infoObject.assignTargetHeaders();
	infoObject.getTargets();
	
	sideBar.assignTitle();
	if(sideBar.objectId != ""){
		sideBar.addObjectFilter();
	}else{
		sideBar.getSidebarItems();
	}

</script>

</body>

<html:html>
<style type="text/css">
	<!--[if IE]>
	#tablediscwidth {width:90%}
	<![endif]-->

	#tablediscwidth {width:100%}
</style>
</html:html>

