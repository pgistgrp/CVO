<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Let's Improve Transportation: Glossary</title>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/GlossaryPublicAgent.js'></script>
<script type='text/javascript' src='/dwr/interface/GlossaryManageAgent.js'></script>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">
@import "styles/lit.css";
</style>

<script language="javascript" type="text/javascript" src="scripts/tinymce/jscripts/tiny_mce/tiny_mce.js"></script>


<!-- Temporary Borders used for testing <style type="text/css" media="screen">@import "styles/tempborders.css";</style>-->
<!-- End Site Wide CSS -->
<style type="text/css">
	#term-text {width:60%;float:left;margin-right:20px;}
	#right-col 
	{
		width:30%;
		float:left;
		padding:10px;
	}
	
</style>
<!-- Site Wide JavaScript -->
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/findxy.js" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->
<script type="text/javascript">

tinyMCE.init({
		mode : "exact",
		theme : "advanced",
		theme_advanced_buttons1 : "bold, italic, bullist, numlist,undo, redo,link",
		theme_advanced_buttons2 : "",
		theme_advanced_buttons3 : "",
		content_css : "/scripts/tinymce/jscripts/tiny_mce/themes/simple/css/bigmce.css",
		extended_valid_elements : "blockquote[style='']"
	});
	

		
		
		function doOnLoad(){
			getComments(${term.id});
			isCommentsLoaded();
			//modApprove("${term.name}",${term.id});
			tinyMCE.execCommand('mceAddControl',false,'newComment');
			
		}
		
		function isCommentsLoaded(){
		
			if(gotComments){
				gotComments=false;
			}else{
				var t=setTimeout('isCommentsLoaded();',500);
			}
		}
		
		
		
		function modApprove(){
	/*$('modApproval').innerHTML="<strong>The term "+tname+" is awaiting moderator approval</strong><br /><br />Moderator Options: "+
	"<button type='button' onclick=\"acceptTerm("+tid+");\">Accept</button>&nbsp;<button type='button' onclick=\"giveReason("+tid+");\">Decline</button>";
	*/
	$('modApproval').style.display='block';
}

		function giveReason(tid){
		
			/*$('modReasondiv').innerHTML="<strong>Reason for Declining Term</strong><br />This reason will be emailed to the participant that proposed this glossary term."+
			"<br /><textarea id='modReasontext' rows=5 cols=20 value=''></textarea><br /><button type='button' onclick='rejectTerm("+tid+",\""+$("modReasontext").value+"\");'>Delete Term and Send Reason</button>";
		*/
		$('modApproval').style.display='none';
		$('modReasondiv').style.display='block';
		
		}

		function acceptTerm(tid){
			GlossaryManageAgent.acceptTerm({id:tid},{
			callback:function(data){
				if(data.successful){
					$('modApproval').style.display='none';
					alert('The term has been accepted');
				}else{
					alert("acceptTerm failure reason: "+data.reason);
				
				}
			},
			errorHandler:function(errorString, exception){
				alert("acceptTerm: "+errorString+" "+exception);
			
			}
		});
		
		
		
		}
		
		function rejectTerm(tid,reasonString){
			GlossaryManageAgent.rejectTerm({id:tid, reason:reasonString},{
			callback:function(data){
				if(data.successful){
				$('modReasondiv').style.display='none';
					alert('The term has been rejected');
				}else{
				$('modReasondiv').style.display='none';
					alert("rejectTerm failure reason: "+data.reason);
				
				}
			},
			errorHandler:function(errorString, exception){
				alert("rejectTerm: "+errorString+" "+exception);
			
			}
		});
		
		
		
		}
		
		
		function setFlag(type){
				GlossaryPublicAgent.setFlag({id:${term.id}}, {
				callback:function(data){

					if (data.successful){ 
							if(type==1){
							alert("This term has been flagged for revision.  The moderator has been notified.  Thank you for your help");
							}
					}

					else{
						alert("setFlag failure reason: "+data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("setFlag: "+errorString+" "+exception);
						//showTheError();
				}
			});
		}	
		
		var gotComments=false;
		function getComments(termId){
				GlossaryPublicAgent.getComments({id:termId}, {
				callback:function(data){

					if (data.successful){ 
							$('comments').innerHTML = data.html;
							gotComments=true;
					}

					else{
						alert("getComments failure reason: "+data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("getComments: "+errorString+" "+exception);
						//showTheError();
				}
			});
		}		
		var numComment = ${term.commentCount}
		
	  function createComment1(termId, comment){
	  	//comment = validateInput(comment);
	  	if (numComment == 4){
	  		setFlag(0);
	  	}
	  	
	  	if (tinyMCE.getContent() != ''){
				GlossaryPublicAgent.createComment({id:termId, comment:comment}, {
				callback:function(data){
					if (data.successful){ 
							getComments(${term.id});
							numComment = numComment + 1;
							$('feedback').innerHTML = 'Your comment has been saved. Thank you for your participation.';
							Effect.Appear('feedback', {duration: 0.8, afterFinish:function(){Effect.Fade('feedback', {duration: 5.0});}});
							//$('newComment').value = '';
							tinyMCE.setContent('');
							//alert(numComment);
							
					}
					else{
						alert("createComment failure reason: "+data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("createComment1: "+errorString+" "+exception);
						//showTheError();
				}
			});
		}else{
						$('feedback').innerHTML = 'Your comment is blank. Please write your comment in the textbox below and click on "Create Comment!"';
						Effect.Appear('feedback', {duration: 0.8, afterFinish:function(){Effect.Fade('feedback', {duration: 10.0});}});
		}
		}

	function keepBreaks(string){

		return string.replace(/\n/g,"<br>");
	}
	
		function validateInput(string){
			string=string.replace(/>/g,"//>//");
			string=string.replace(/</g,"//<//");
			string=string.replace(/\n/g,"<br>");

			return string;
	}
		
</script>
<event:pageunload />
</head>
<body>
  <!-- Start Global Headers  -->
  	<wf:nav />
  <!-- End Global Headers -->
  <!-- Begin header menu - The wide ribbon underneath the logo -->
  <div id="headerMenu">
  	<div id="headerContainer">
  		<div id="headerTitle" class="floatLeft">
  			<h3 class="headerColor">Learn More</h3>
  		</div>
  		<div class="floatLeft headerButton"> <a href="lmMenu.do">Menu</a> </div>
  		<div class="floatLeft headerButton"> <a href="lmAbout.do">About LIT</a> </div>
  		<div class="floatLeft headerButton"> <a href="lmFaq.do">FAQ</a> </div>
  		<div class="floatLeft headerButton"> <a href="lmTutorial1.do">Tutorial</a> </div>
  		<div class="floatLeft headerButton"> <a href="lmGallery.do">Project gallery</a> </div>
  		<div class="floatLeft headerButton currentBox"> <a href="glossaryPublic.do">Glossary</a> </div>
  		<div class="floatLeft headerButton"> <a href="lmResources.do">More resources</a> </div>
  	</div>
  </div>
  <!-- End header menu -->
  <!-- #container is the container that wraps around all the main page content -->
  <div id="container">
	  <logic:equal name="term" property="status" value="0">
      <pg:show roles="admin, moderator">
        <div style='background-color:gray; font-size:x-large;' id="modApproval"><strong>The term ${term.name} is awaiting moderator approval</strong><br /><br />
	          Moderator options:
	          <button type='button' onclick='acceptTerm(${term.id});'>Accept</button>
	          <button type='button' onclick='giveReason(${term.id});'>Decline</button>
        </div>
        <br />
        <div style='display:none; border:thick solid #666666; font-size:large;' id='modReasondiv'>
          <div><strong>Reason for declining term</strong></div>
          <div>This reason will be emailed to the participant that proposed this glossary term.</div>
          <div style='margin:1%;'>
            <textarea style='height:100%; width:100%;' id='modReasontext'></textarea>
          </div>
          <button type='button' onclick='javascript:rejectTerm(${term.id},$("modReasontext").value);'>Delete term and send reason</button>
        </div>
        <br />
      </pg:show>
    </logic:equal>
      <a href="glossaryPublic.do">Back to all glossary terms</a>
    <div id="term-text" style="margin-top: 10px;">
      <h3>${term.name}</h3>
      <p class="largeText">${term.shortDefinition}</p>
      <p class="largeText">${term.extDefinition}<br />
    
      </p>
      <div id="commentStats" style="padding-top: 10px;">
        <h3>Statistics</h3>
        <table cellpadding="5" cellspacing="5">
          <th>Page views</th>
            <th>Unique participant views</th>
            <th>Avg. views per participant</th>
            <th>Appearances in pages</th>
          <tr style="text-align: center">
            <td>${term.viewCount}</td>
            <td>${term.participantCount}</td>
            <td>${term.averageCount}</td>
            <td>${term.highlightCount}</td>
          </tr>
        </table>
      </div>
      <div id="comments" style="width:100%">
        <!--comments load here onLoad -->
      </div>
      <br>
            <p><a href="javascript:setFlag(1);"><img src="/images/icon_flag3.gif" border="0"></a>&nbsp;<a href="javascript:setFlag(1);">Flag moderator to review this term's definition and/or comments</a></p>
      <div id="feedback" style="display: none; background-color:#ffc; color: #333; width:100%;"></div>
      <div id="newCommentForm">
        <h3>Add a comment</h3>
        (html OK)<br>
        <div id="reply-to"></div>
        <form name="commentForm" action="" onsubmit="createComment1(${term.id}, tinyMCE.getContent()); return false;">
          <textarea style="height: 150px; width: 150%;" name="newComment" id="newComment" cols="50" rows="5"></textarea>
          </p>
          <br>
          <input type="button" id="createComment" value="Submit Comment" onclick="createComment1(${term.id}, tinyMCE.getContent());">
        </form>
      </div>
      <br>
      <a href="glossaryPublic.do">Back to all glossary terms</a> </div>
	  
	    <div id="right-col" class="box3">
    <pg:show roles="admin, moderator">
      <p><a href="glossaryManage.do">Back to glossary management</a></p>
    </pg:show>
    <c:if test="${fn:length(term.links) != 0}">
      <h3>Term links</h3>
      <ul>
        <logic:iterate id="onelink" property="links" name="term">
          <li><a href="${onelink}">${onelink}</a></li>
        </logic:iterate>
      </ul>
    </c:if>
    <c:if test="${fn:length(term.relatedTerms) != 0}">
      <h3>Related terms</h3>
      <ul>
        <logic:iterate id="oneterm" property="relatedTerms" name="term">
          <li><a href="glossaryView.do?id=${oneterm.id}">${oneterm}</a></li>
        </logic:iterate>
      </ul>
    </c:if>
    <c:if test="${term.acronym != null }">
      <h3>Abbreviation</h3>
      <ul>
        <li>${term.acronym}</li>
      </ul>
    </c:if>
    <c:if test="${fn:length(term.variations) != 0}">
      <h3>Variations</h3>
      <ul>
        <logic:iterate id="onevariation" property="variations" name="term">
          <li>${onevariation.name}</li>
        </logic:iterate>
      </ul>
    </c:if>
    <c:if test="${fn:length(term.sources) != 0}">
      <h3>Sources</h3>
      <ul>
        <logic:iterate id="onesource" property="sources" name="term">
          <li><a href="${onesource.url}">${onesource}</a></li>
        </logic:iterate>
      </ul>
    </c:if>
  </div>
  </div>

</div>
<!-- Start Footer -->
<div style="clear:both"></div>
  <!-- end container -->
  
<!-- start feedback form -->
  <pg:feedback id="feedbackDiv" action="cctView.do"/>
<!-- end feedback form -->

  <!-- Begin header menu - The wide ribbon underneath the logo -->
  <div id="headerMenu">

  </div>

	<!-- Begin footer -->
	<div id="footer">
		<jsp:include page="/footer.jsp" />
	</div>
	<!-- End footer -->
	<script type="text/javascript">
	doOnLoad();
	
</script>
</body>
</html>
