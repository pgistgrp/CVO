<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Glossary Public View</title>

<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/GlossaryPublicAgent.js'></script>
<script type='text/javascript' src='/dwr/interface/GlossaryManageAgent.js'></script>



<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/position.css";</style>
<style type="text/css" media="screen">@import "styles/styles.css";</style>
<!-- Temporary Borders used for testing <style type="text/css" media="screen">@import "styles/tempborders.css";</style>-->
<!-- End Site Wide CSS -->

<style type="text/css">
	#term-text {width:60%;float:left;margin-right:20px;}
	#right-col 
	{
		width:30%;
		float:left;
		padding:10px;
		background:#DEE3E7;
		border:2px solid #9CB6C6; }
</style>

<!-- Site Wide JavaScript -->

<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/findxy.js" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<script type="text/javascript">
		
		
		function doOnLoad(){
			getComments(${term.id});
			isCommentsLoaded();
			//modApprove("${term.name}",${term.id});
			
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
	  	comment = keepBreaks(comment);
	  	if (numComment == 4){
	  		setFlag(0);
	  	}
	  	
	  	if ($('newComment').value != ''){
				GlossaryPublicAgent.createComment({id:termId, comment:comment}, {
				callback:function(data){
					if (data.successful){ 
							getComments(${term.id});
							numComment = numComment + 1;
							$('feedback').innerHTML = 'Your comment has been saved. Thank you for your participation.';
							Effect.Appear('feedback', {duration: 0.8, afterFinish:function(){Effect.Fade('feedback', {duration: 5.0});}});
							$('newComment').value = '';
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
						$('feedback').innerHTML = 'Your comment is blank.  Please write your comment in the textbox below and click on "Create Comment!"';
						Effect.Appear('feedback', {duration: 0.8, afterFinish:function(){Effect.Fade('feedback', {duration: 10.0});}});
		}
		}

	function keepBreaks(string){

		return string.replace(/\n/g,"<br>");
	}
		
</script>

</head>
<body>


<div id="container">
	<!-- Header -->
	<jsp:include page="/header.jsp" />
	<!-- End Header -->
	<!-- Sub Title -->
	<div id="subheader">



	
	<logic:equal name="term" property="status" value="0">
		<pg:show roles="admin, moderator">
		
			<div style='background-color:gray; font-size:x-large;' id="modApproval"><strong>The term ${term.name} is awaiting moderator approval</strong><br /><br />Moderator Options: 
			<button type='button' onclick='acceptTerm(${term.id});'>Accept</button>&nbsp;<button type='button' onclick='giveReason(${term.id});'>Decline</button></div>
			<br />
			<div style='display:none; border:thick solid #666666; font-size:large;' id='modReasondiv'><div><strong>Reason for Declining Term</strong></div>
			<div>This reason will be emailed to the participant that proposed this glossary term.</div>
			<div style='margin:1%;'><textarea style='height:100%; width:100%;' id='modReasontext'></textarea></div><button type='button' onclick='javascript:rejectTerm(${term.id},$("modReasontext").value);'>Delete Term and Send Reason</button></div>
			<br />
			
		</pg:show>
	</logic:equal>


	<h1>Learn More: </h1> <h2>Listing of All Glossary Terms</h2>
	</div>
	<div id="footprints">
	<p>Learn More >> <a href="glossaryPublic.do">Glossary</a> >> ${term.name}</p>
	</div>
	<!-- End Sub Title -->
	
<!-- add if moderator options -->

<div id="slate"  class="blueBB" width="80%;" >
		<div id="term-text">

		<h1>${term.name}</h1>
		<p></p>
		<p>${term.shortDefinition}</p>
		<p>${term.extDefinition}<br />
	<pg:show roles="participant">
		<a href="glossaryPublic.do">Back to All Glossary Terms</a>
</pg:show></p>
		<p><a href="javascript:setFlag(1);"><img src="/images/icon_flag3.gif" border="0"></a>&nbsp;<a href="javascript:setFlag(1);">Flag moderator to review this term's definition and/or comments</a></p>

		<div id="commentStats" style="padding-top: 10px;">
			<h3>Statistics</h3>
			<table cellpadding="5" cellspacing="5">
			<th>Page Views</th><th>Unique Participant Views</th><th>Avg. Views Per Participant</th><th>Appearances in Pages</th>
			<tr style="text-align: center">
				<td>${term.viewCount}</td>
				<td>${term.participantCount}</td>
				<td>${term.averageCount}</td>
				<td>${term.highlightCount}</td>		
			</tr>	
		  </table>
		</div>
		
	
		<div id="comments" style="width:100%"><!--comments load here onLoad --></div> <br> 
		<div id="feedback" style="display: none; background-color:#ffc; color: #333; width:100%;"></div>
		<div id="newCommentForm"> <h3>Add a Comment</h3> (html OK)<br><div id="reply-to"></div> 
		<form name="commentForm" action="" onsubmit="createComment1(${term.id}, $('newComment').value); return false;">
			<textarea style="height: 50px; width: 80%;" name="newComment" id="newComment" cols="50" rows="5"></textarea></p> 
			<br><input type="button" id="createComment" value="Submit Comment" onclick="createComment1(${term.id}, $('newComment').value);">
		</form> 

		</div> 

		
		<br>
		<a href="glossaryPublic.do">Back to All Glossary Terms</a>

	</div>
</div>

	<div id="right-col">
		<pg:show roles="admin, moderator">
	<p><a href="glossaryManage.do">Back to Glossary Management</a></p>
</pg:show>

	<c:if test="${fn:length(term.links) != 0}">
		<h3>Term Links</h3>
		<ul>
			<logic:iterate id="onelink" property="links" name="term">
				<li><a href="${onelink}">${onelink}</a></li>
			</logic:iterate>
		
		</ul>
		</c:if>
		<c:if test="${fn:length(term.relatedTerms) != 0}">
		<h3>Related Terms</h3>
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

<!-- Start Footer -->
<div style="clear:both"></div>
<jsp:include page="/footer.jsp" />

<!-- End Footer -->
<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
<script type="text/javascript">
	doOnLoad();
	
</script>
</body>
</html>

