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
	var currentPage = 1;
	var commentCount = 15;
	var divDiscussion = "container-include";
	var filterAnchor = "#filterAnchor";
	
	//Inputs
	var txtNewCommentTitle = 'txtNewCommentTitle';
	var txtNewComment = 'txtNewComment';
	var txtNewCommentTags = 'txtNewCommentTags'
//END Global variables

	function getComments(page, jump){
		currentPage = page;
		if(jump){
			location.href = filterAnchor;
		}
		//alert("concernId: " + concernId + " page: " + currentPage + " count: " + commentCount); 
		CCTAgent.getComments({concernId: concernId, page: currentPage, count: commentCount}, {
			callback:function(data){
				if (data.successful){
					$(divDiscussion).innerHTML = data.html; //uses commentsMain.jsp
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("CCTAgent.getComments( error:" + errorString + exception);
			}
		});
	}

	function createComment(){
		var title = $(txtNewCommentTitle).value
		var content = $(txtNewComment).value
		var tags = $(txtNewCommentTags).value
						
		//alert("concernId: " + concernId + " title: " + title + " content: " + content + " tags: " + tags); 
		CCTAgent.createComment({concernId: concernId, title: title, content: content, tags: tags}, {
			callback:function(data){
				if (data.successful){
					getComments(1, true);
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("CCTAgent.createComment( error:" + errorString + exception);
			}
		});
	}
	
	function goToPage(page){
		getComments(page);
	}
	
	
	function setVote(id, agree){
		CCTAgent.setVoting({id: id, agree:agree}, {
		callback:function(data){
				if (data.successful){ 
					if($('concernVote'+id) != undefined){
           				 new Effect.Fade('concernVote'+id, {afterFinish: function(){getContextConcerns(cct.currentFilter,cct.currentPage, false, cct.showOnlyMyConcerns, cct.currentSort); new Effect.Appear('concernVote'+id);}});
           			}else{ //newly created concern
           				getContextConcerns(cct.currentFilter, cct.currentPage, false, cct.showOnlyMyConcerns, cct.currentSort); 	
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
	
	function setCommentVoting(id, agree){
		//alert("id: " + id + " agree: " + agree); 
		CCTAgent.setCommentVoting({id:id,agree:agree}, {
			callback:function(data){
				if (data.successful){
					getComments(currentPage, false);
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("CCTAgent.setCommentVoting( error:" + errorString + exception);
			}
		});
	}
	/*
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
  <div id="headerMenu">
    <div id="headerContainer">
      <div id="headerTitle" class="floatLeft">
        <h3 class="headerColor">Step 1: Discuss Concerns</h3>
      </div>
    <div class="headerButton box4 floatLeft currentBox"><a href="cctlist.do">1a: Brainstorm</a></div>
    <div class="headerButtonCurrent floatLeft"><a href="sdlist.do">1b: Review Summaries</A></div>
      <div id="headerNext" class="box5 floatRight"><a href="sdlist.do">Next Step</A></div>
    </div>
  </div>
<!-- End header menu -->
<div style="display: none;" id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>
<div id="container">

	<!-- end voting tally menu -->
	<div id="container-include">
		<!-- load commentsMain.jsp via AJAX-->
	</div>
	<!-- start feedback form -->
	<pg:feedback id="feedbackDiv" action="concern.do" />
	<!-- end feedback form -->

	<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
	<script type="text/javascript">
		getComments(currentPage, false);
	</script>
</div><!-- end container -->
<!-- start the bottom header menu -->
  <!-- Begin header menu - The wide ribbon underneath the logo -->
  <div id="headerMenu">
    <div id="headerContainer">
      <div id="headerTitle" class="floatLeft">
        <h3 class="headerColor">Step 1: Discuss Concerns</h3>
      </div>
    <div class="headerButton box4 floatLeft currentBox"><a href="cctlist.do">1a: Brainstorm</a></div>
    <div class="headerButtonCurrent floatLeft"><a href="sdlist.do">1b: Review Summaries</A></div>
      <div id="headerNext" class="box5 floatRight"><a href="sdlist.do">Next Step</A></div>
    </div>
  </div>
  <!-- End header menu -->
<!-- end the bottom header menu -->
<!-- Begin footer -->
<div id="footer">
  <jsp:include page="/footer.jsp" />
</div>
<!-- End footer -->
</body>

</html:html>
