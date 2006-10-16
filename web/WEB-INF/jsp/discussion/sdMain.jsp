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
<title>Step 1b: Discuss Summaries - Main</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/position.css";</style>
<style type="text/css" media="screen">@import "styles/styles.css";</style>
<!-- Temporary Borders used for testing <style type="text/css" media="screen">@import "styles/tempborders.css";</style>-->
<!-- End Site Wide CSS -->

<style type="text/css">
	<!--[if IE]>
	#sidebarbottom {
		right:19px;}
	<![endif]-->
	
	
	#sidebarbottom {
	  position:absolute;
	  right:0px;
	}
</style>

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
<script src="scripts/lightbox.js" type="text/javascript"></script>
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
	var sideBar = new SideBar("${structure.id}", "${object.id}","${structure.cctId}","${object.object}", "Participant Concerns", 3, "Show All Concerns", "Filter All Concerns By", 50, ""); 
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
	
			/***************View item details.************** */
	sideBar.viewItemDetails = function(conId){				
		var conIdStr = conId.toString();		
		
		SDAgent.getConcernById(conIdStr, { // requires lightbox script
				callback:function(data){
					if (data.successful){
							//alert(conIdStr);	
							sideBar.lightBoxTitle = "View Entire Concern"; //Title of lightbox
							sideBar.renderItemDetails(data.concern); //add contents to lightbox 
					}
				},
				errorHandler:function(errorString, exception){ 
						alert("viewSidebarConcern: "+errorString+" "+exception);
						//showTheError();
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

		function displayIndicator(show){
		if(show){
			$('loading-indicator').style.display="inline";
			}else{
			$('loading-indicator').style.display="none";
			}
		}
		
		
	function setSort(thisSort, term){
			headings = document.getElementsByTagName("td"); 
			for (var i = 0; i < headings.length; i++) { 
				if (headings[i].id != 'def' && headings[i].id!='actions'){
			    if (headings[i].id == thisSort){
			    	if(direction == 'asc'){
			    		headings[i].innerHTML = thisSort + '&nbsp;&nbsp;<a href="javascript:sortDir(\'desc\',\'' + thisSort + '\', \''+ term +'\');"><img src="/images/sort_' + direction +'.gif" border="0"></a>';
			   		}else{
			   			headings[i].innerHTML = thisSort + '&nbsp;&nbsp;<a href="javascript:sortDir(\'asc\',\'' + thisSort + '\', \''+ term +'\');"><img src="/images/sort_' + direction +'.gif" border="0"></a>';
			   		}
			    }else{
			    	headings[i].innerHTML = '<a href="javascript: getTerms(\''+ term +'\',\''+ headings[i].id +'\'); isTermsGotten();">'+ headings[i].innerHTML +'</a>';
			    }
			  }  
			}
		}	
		
		function sortDir(switchTo, thisSort, term){
				direction = switchTo;
				getTerms(term, thisSort); 
				isTermsGotten();
		}
		
		///////////////////////////////////////// START LIGHTBOX //////////////////////////////////////
	
		function lightboxDisplay(show){
			if (show){
				$('overlay').style.display = 'block';
				$('lightcontainer').style.display = 'inline';
				centerDisable();
			}else{
				$('overlay').style.display = 'none';
				$('lightcontainer').style.display = 'none';
				centerReenable();
			}
		}
			function displayIndicator(show){
				if (show){
					$('loading-indicator').style.display = "inline";	
				}else{
					$('loading-indicator').style.display = "none";	
				}
			}

</script>
<style type="text/css" />

.leightpadding{

padding:0em 1em 1em 1em;


}
#loading-indicator{
	
	background-color: red;
	color: white;
	position:absolute;
	top: 0;
	left:0;
	padding: 3px;
	z-index: 500;
}
.leightcontainer{
 	display: none;
 	position: absolute;
 	top: 50%;
 	left: 50%;
 	margin-left: -200px;
 	margin-top: -150px;
 	width:400px;
 	height: 300px;
 	background-color: white;
 	text-align: left;
 	z-index:1002;
 	overflow:hidden;
	border: 5px solid #E1E1E1;
}

.leightbox {
 	color: #333;
 	position:absolute;
 	top:30px;
 	height:100%;
 	width:100%;
 	background-color: white;
 	text-align: left;
 	z-index:1002;
 	overflow:auto;	


}

.leightbar{
	position:absolute;
	top:0%;
	height:30px;
	width:100%;
	background-color:#0066FF;
	z-index:1002;
	overflow:hidden;
}

#overlay{
 	display:none;
 	position:absolute;
 	top:0;
 	left:0;
 	width:100%;
 	height:100%;
 	z-index:1000;
 	background-color:#333;
 	-moz-opacity: 0.8;
 	opacity:.80;
 	filter: alpha(opacity=80);
}



.lbclose{
float:right;

}



.lightbox[id]{ /* IE6 and below Can't See This */    position:fixed;    
}#overlay[id]{ /* IE6 and below Can't See This */    position:fixed;    
}
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
	<div id="overlay"></div>
	<div id="lightcontainer" class="leightcontainer">
 	<div id="lightbox" class="leightbox">
 		<div id="lightboxpadding" class="leightpadding">

 		</div>
 	</div>
</div>
<div id="container">
		 <div id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>
		<jsp:include page="/header.jsp" />
		<!-- Header -->
		</div>
		<div id="cont-top">
		<!-- Sub Title -->
		<div id="subheader">
		<h1>Step 1b:</h1> 
		<h2>Review Summaries</h2>
		</div>
		<div id="footprints">
		<span class="smalltext"><a href="/main.do">Participate</a> &gt;&gt; <a href="/cctview.do?cctId=1171">Step 1a Brainstorm Concerns</a> >> <a href="/sd.do?isid=2951">Step 1b: Review Summaries</A></span>
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
		
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
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
				  <table class="tabledisc" width="100%">
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
							</span><!--Added-->        
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
		 </td><!--Added-->
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
	sideBar.assignTitle();
	if(sideBar.objectId != ""){
		sideBar.addIOIDFilter();
	}else{
		sideBar.getSidebarItems();
	}

</script>
</body>

</html:html>