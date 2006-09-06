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


<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/position.css";</style>
<style type="text/css" media="screen">@import "styles/styles.css";</style>
<!-- Temporary Borders used for testing <style type="text/css" media="screen">@import "styles/tempborders.css";</style>-->
<!-- End Site Wide CSS -->


<!-- Site Wide JavaScript -->

<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/findxy.js" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<script type="text/javascript">
		
		
		function doOnLoad(){
			getComments(${term.id});
		}
		
		
		function setFlag(type){
				GlossaryPublicAgent.setFlag({id:${term.id}}, {
				callback:function(data){

					if (data.successful){ 
							if(type==1){
							alert("This term has been flagged for revision.  The moderator has been notified.  Thank you for your help");
							}
					}

					if (data.successful != true){
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
						//showTheError();
				}
			});
		}	
		
		function getComments(termId){
				GlossaryPublicAgent.getComments({id:termId}, {
				callback:function(data){

					if (data.successful){ 
							$('comments').innerHTML = data.html;
					}

					if (data.successful != true){
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
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
					if (data.successful != true){
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
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
	<h1>Learn More: </h1> <h2>Listing of All Glossary Terms</h2>
	</div>
	<div id="footprints">
	<p>Learn More >> <a href="glossaryPublic.do">Glossary</a> >> ${term.name}</p>
	</div>
	<!-- End Sub Title -->
	
<!-- add if moderator options -->

<div id="slate"  class="blueBB" width="80%;" >
		<p><a href="glossaryPublic.do">Back to All Glossary Terms</a></p>
		<h1>${term.name}</h1>
		<p></p>
		<p>${term.shortDefinition}</p>
		<p>${term.extDefinition}</p>
		<p><a href="javascript:setFlag(1);"><img src="/images/flag_icon.gif" border="0"></a>&nbsp;<a href="javascript:setFlag(1);">Flag moderator to review this term's definition and/or comments</a></p>

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
					<li><a href="${onesource}">${onesource}</a></li>
				</logic:iterate>
				</ul>
		</c:if>
		
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
	
		<div id="comments"><!--comments load here onLoad --></div> <br> 
		<div id="feedback" style="display: none; background-color:#ffc; color: #333; width:80%;"></div>
		<div id="newCommentForm"> <h3>Add a Comment</h3> (html OK)<br><div id="reply-to"></div> 
		<form name="commentForm" action="" onsubmit="createComment1(${term.id}, $('newComment').value); return false;">
			<textarea style="height: 50px; width: 80%;" name="newComment" id="newComment" cols="50" rows="5"></textarea></p> 
			<br><input type="button" id="createComment" value="Submit Comment" onclick="createComment1(${term.id}, $('newComment').value);">
		</form> 

		</div> 

		
		<br>
		<p><a href="glossaryPublic.do">Back to All Glossary Terms</a></p>

	</div>
</div>

<!-- Start Footer -->
<jsp:include page="/footer.jsp" />

<!-- End Footer -->
<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
<script type="text/javascript">
	doOnLoad();
	
</script>
</body>
</html>

