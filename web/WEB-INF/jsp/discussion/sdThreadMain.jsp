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
<title>Step 1b: ${post.title}</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/position.css";</style>
<style type="text/css" media="screen">@import "styles/styles.css";</style>
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
<script src="scripts/SideBar.js" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/interface/SDAgent.js'></script>
<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>
<!--End SDX Specific  Libraries-->



<script type="text/javascript">
		/* global vars */
			var countPerPage = 10; //numer of posts per page
			var lastPage = 1
		/* end global vars */
		
		function removeDoubleQuotes(str){
		charToRemove = '"';
		regExp = new RegExp("["+charToRemove+"]","g");
		return str.replace(regExp,"");
		
		}

		function createReply(page){
		 	lastPage = page;
		 	displayIndicator(true);
			var newReplyTitle = $('txtnewReplyTitle').value;
			//var newReply = validateInput($('txtnewReply').value);
			var newReply = tinyMCE.getContent();
			
			var newReplyTags = $('newReplyTags').value;
		    SDAgent.createReply({isid:${structure.id}, pid:${post.id}, title:newReplyTitle, content:newReply, tags: newReplyTags}, {
		      callback:function(data){
		          if (data.successful){     
						$('txtnewReply').value = '';
						tinyMCE.setContent('');
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
			  async:false,
		      errorHandler:function(errorString, exception){
		          alert("getReplies Error" + errorString + exception);
		      }
		    });
			tinyMCE.idCounter=0;
			tinyMCE.execCommand('mceAddControl',false,'txtnewReply');
		  }

	
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
		
	Methods to define for this Instance
		sidebar.getAbstractItems(tags, page);
		sideBar.convertAbstractFilter(tagRefId);
		sideBar.sidebarSearchTagsAction(theTag)
		sideBar.getAbStractTagCloud(page)
		sideBar.getAbstractTagCloudResults(theTag)
	*/
	
	
	var sideBar = new SideBar("${structure.id}", "${object.id}","${structure.cctId}","${object.object}", "Other Discussion Posts", 3, "Show All Discussions", "Filter All Discussions By", 50, "${post.id}" ); 
	
	sideBar.addPIDFilter = function(){
		var filterInstance = new Filter(this.objectId, "checked", false, "Current Post");
		this.addObjectFilter(filterInstance);
	};
	
	
	sideBar.getAbstractItems = function(tags, page){
		//alert(sideBar.filterObject);
		if(sideBar.filterObject){
			var pid = ${post.id};
		}else{
			var pid = "";	//clear pid to find all posts
		}
		//alert("isid:"  +this.structureId+"pid: "+ pid + "tags: " + tags + "count: " +this.itemsCount+ " page: " +page);
		SDAgent.getContextPosts({isid: this.structureId,pid: pid, tags: tags, count: this.itemsCount, page: page}, {
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
		SDAgent.getTagById(tagRefId, {
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
			SDAgent.searchTags({isid:this.structureId,tag:theTag},{
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
			SDAgent.getTagCloud({isid:this.structureId, page: page, count:this.tagCloudCount}, {
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
			SDAgent.searchTags({isid:this.structureId,tag:theTag},{
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
<body onLoad="MM_preloadImages('images/btn_postreply_b.gif')"> 
<div id="container">
<jsp:include page="/header.jsp" />
<!-- Header -->
  <div id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>

<div id="cont-top" style="margin-left:0px; margin-right:0px; padding-left:0px; padding-right:0px;">


<!-- Sub Title -->
<div id="subheader">
<h1>Step 1 Brainstorm Concerns:</h1> <h2>Review Summaries</h2>
</div>
<div id="footprints">
<span class="smalltext"><a href="/main.do">Participate</a> &raquo; <a href="/cctview.do?cctId=1171">Step 1a Brainstorm Concerns</a> &raquo; <a href="sd.do?isid=${structure.id}">Step 1b Review Summaries</a> &raquo; <a href="sdRoom.do?isid=${structure.id}&ioid=${object.id}">${object.object}</a> &raquo; ${post.title}</span>
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

<div id="cont-main" style="margin-left:0px; margin-right:0px;">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td id="maintop"><img src="" alt="" height="1" width="1"/></td>
<td><img src="images/sidebar_top.gif" alt="sidebartop" /></td>
</tr>
<tr>
<td valign="top" id="maincontent">
<!-- Main Content starts Here-->
<h4>View Discussion</h4>
<div id="object">
	
	<div id="post${post.id}">
		 <h5>
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
		 </h5>
		 
		 <div class="bluetitle">
		 	<span style="float:right; font-weight: bold; font-size: 0.8em;">0 of 0 participants agree with ${post.owner.loginname} <a href="#"><img src="/images/btn_thumbsdown.png" alt="I disagree!" border="0"/></a> <a href="#"><img src="/images/btn_thumbsup.png" alt="I agree!" border="0"/></a></span>
		 	<span class="padding-sides"><strong>${post.title}</strong> - <small>Posted on <fmt:formatDate value="${post.createTime}" pattern="MM/dd/yy, hh:mm aaa"/> by: ${post.owner.loginname}</small></span>
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

 </td><!-- End Right Col -->
</tr>

</table>
<div id="sidebarbottom" style="text-align:right;"><img src="images/sidebar_bottom.gif" alt="sidebarbottom" /></div>
<div class="backToDiscussion"><a href="sdRoom.do?isid=${structure.id}&ioid=${object.id}"><img src="images/btn_back_a.gif" alt="back2" name="back" class="button" id="back2" onMouseOver="MM_swapImage('back2','','images/btn_back_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a></div>

<div id="finished" class="greenBB">
	<h4>Ready for the next step?</h4><br />
	<p>Click on the continue button to go on to step 2 where you will review and weigh criteria to evaluate proposed transportation projects.  Go back to your <a href="main.do">home page</a> or  <a href="sdcWaiting.jsp"><img border="0" src="images/btn_gcontinue_a.gif" alt="Continue" name="continue" class="button" id="continue" onMouseOver="MM_swapImage('continue','','images/btn_gcontinue_b.gif',1)" onMouseOut="MM_swapImgRestore()"></p>
</div>

</div>
<!-- End cont-main -->
</div> <!-- End container -->

<!-- start feedback form -->

		<pg:feedback id="feedbackDiv" action="sdThread.do" />
		
<!-- end feedback form -->
<!-- Start Footer -->
<jsp:include page="/footer.jsp" />


<!-- End Footer -->
<script type="text/javascript">
tinyMCE.init({
	mode : "exact",
	theme : "simple",
	content_css : "/scripts/tinymce/jscripts/tiny_mce/themes/simple/css/bigmce.css"
});
</script>
<script type="text/javascript">
	getReplies();
	sideBar.assignTitle();
	sideBar.addPIDFilter();
$('container').style.width="1659";
$('container').style.paddingLeft="0";
</script>


</html:html>


