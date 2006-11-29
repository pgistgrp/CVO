<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html:html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Step 1a: Brainstorm</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">
@import "styles/lit.css";
</style>
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
<!--CCT Specific  Libraries-->
<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>
<!--End CCT Specific  Libraries-->

<script type="text/javascript">
//START Global Variables
	var concernId = ${concern.id}
	alert(concernId)
//END Global variables
/*
	function getComments(params){
		//alert("isid: " + sd.isid + " ioid: " + sd.ioid + " tags: " + tags + " page: " + page + " count: " + sd.concernCount); 
		CCTAgent.getComments({dwrParams}, {
			callback:function(data){
				if (data.successful){
					alert(data.html)
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("CCTAgent.getComments( error:" + errorString + exception);
			}
		});
	}

	function createComment(params){
		//alert("isid: " + sd.isid + " ioid: " + sd.ioid + " tags: " + tags + " page: " + page + " count: " + sd.concernCount); 
		CCTAgent.createComment({dwrParams}, {
			callback:function(data){
				if (data.successful){
					alert(data.html)
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("CCTAgent.createComment( error:" + errorString + exception);
			}
		});
	}
	
	function editComment(params){
		//alert("isid: " + sd.isid + " ioid: " + sd.ioid + " tags: " + tags + " page: " + page + " count: " + sd.concernCount); 
		CCTAgent.editComment({dwrParams}, {
			callback:function(data){
				if (data.successful){
					alert(data.html)
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("CCTAgent.editComment( error:" + errorString + exception);
			}
		});
	}
	
	function deleteComment(params){
		//alert("isid: " + sd.isid + " ioid: " + sd.ioid + " tags: " + tags + " page: " + page + " count: " + sd.concernCount); 
		CCTAgent.deleteComment({dwrParams}, {
			callback:function(data){
				if (data.successful){
					alert(data.html)
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("CCTAgent.deleteComment( error:" + errorString + exception);
			}
		});
	}
*/
</script>


  
</head><body>
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
	<!-- start concern tally -->
	
	
	
	<!-- end concern tally -->
	<!-- start single concern and voting here getConcernByID()-->
	<div id="concern${concern.id}" class="discussionRow">
		<c:choose>
			<c:when test="${baseuser.id == concern.author.id}">
				<div class="discussion-left box7">			
			</c:when>
			<c:otherwise>
				<div class="discussion-left ${((loop.index % 2) == 0) ? 'box8' : ''}">	
			</c:otherwise>
		</c:choose>
			<div class="discussionRowHeader">
				<div id="concernVote${concern.id}" class="discussionVoting">
					Do you agree with this concern?  ${concern.numAgree} of ${concern.numVote} people agree so far.
					
				 	<c:choose>
				 		<c:when test="${concern.object == null}">
							<a href="javascript:setVote(${concern.id}, 'false');"><img src="images/btn_thumbsdown.png" alt="Disagree" /></a>&nbsp;
							<a href="javascript:setVote(${concern.id}, 'true');"><img src="images/btn_thumbsup.png"  alt="Agree" /></a>
						</c:when>
						<c:otherwise>
							<img src="images/btn_thumbsdown_off.png" alt="Disabled Button"/> <img src="images/btn_thumbsup_off.png" alt="Disabled Button"/>
						</c:otherwise>
					</c:choose>
				</div><!-- end discussionVoting -->
			</div><!-- end discussionRowHeader -->
			<div class="discussionBody">
				<div id="editingArea${concern.id}" style="display:none"></div>
				<div class="discussionText" id="discussionText${concern.id}"><p>"${concern.content}"</p></div>
				<h3 id="discussionAuthor">- <bean:write name="concern" property="author.loginname" /></h3>
					<div class="discussionComments" id="discussionComments"><h3><a href="concern.do?id=${concern.id}">${concern.replies} Comments</a></h3></div>
					<div class="discussionTagsList">
						<!-- iterate through concern tags here -->	
						<div id="tagsUL${concern.id}"><ul class="tagsInline">
							<li class="tagsInline"><strong>Tags:</strong> </li>
							<c:forEach items="${concern.tags}" var="tagref">
								<c:choose>
									<c:when test="${baseuser.id == concern.author.id}">
										<li class="box6 tagsInline">		
									</c:when>
									<c:otherwise>
										<li class="box8 tagsInline">
									</c:otherwise>
								</c:choose>

								<a href="javascript:changeCurrentFilter(${tagref.id});">${tagref.tag.name}</a></li>
							</c:forEach>
						</ul>
						</div>
					
					<div id="tagEditingArea${concern.id}" style="display:none"></div>
					<div style="clear: left;"></div>
					
					<!-- end tag iteration -->

					</div><!--end discussionTagsList -->
					<c:if test="${baseuser.id == concern.author.id}">
							<div class="box6">
								<strong>Author Actions:</strong> <a href="javascript:editConcernPopup(${concern.id});">Edit Concern</a> &nbsp; <a href="javascript:editTagsPopup(${concern.id});">Edit Tags</a> &nbsp; <a href="javascript:deleteConcern(${concern.id});">Delete Concern</a> 
							</div>
					</c:if>
			</div><!-- end discussion body -->	
		</div><!-- end discussion-left -->
	</div><!-- end discussion row -->
	<!-- END single concern -->
	<!-- load comments for the given concern getComments()-->
	
	
	<!-- end loading comments -->
	<!-- start comment form -->
	
	
	<!-- end comment form -->
	<!-- start feedback form -->
	<pg:feedback id="feedbackDiv" action="sdRoom.do" />
	<!-- end feedback form -->

	<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
	<script type="text/javascript">

	</script>
</div><!-- end container -->
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

</html:html>
