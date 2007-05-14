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
<link rel="stylesheet" type="text/css" href="/styles/pgist.css">
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/GlossaryPublicAgent.js'></script>

<script type="text/javascript">
		window.onload = doOnLoad();
		
		function doOnLoad(){
			getComments(${term.id});
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
		
	  function createComment(termId, comment){
				GlossaryPublicAgent.createComment({id:termId, comment:comment, quote:null}, {
				callback:function(data){

					if (data.successful){ 
							getComments(${term.id});
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
		
</script>
<event:pageunload />
</head>

<body>
<!-- add if moderator options -->
<div id="container">
	<div id="slate" class="leftBox">
		<p><a href="glossaryPublic.do">Back to All Glossary Terms</a></p>
		<h1>${term.name}</h1> | Created on ${term.createTime} by ${term.creator}
		<p></p>
		<p>${term.shortDefinition}</p>
		<p>${term.extDefinition}</p>
		<p><a href="javascript:flagDef();">Flag definition for revision by moderator</a></p>

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
				<li>${oneterm}</li>
			</logic:iterate>
		</ul>
		</c:if>
		
		
		<div id="comments"><!--comments load here onLoad --></div>
		<div id="newCommentForm">
			
		<h2>Comment on this Term</h2><br>
		  <form id="commentForm" name="commentForm" method="post" action="" style="padding-top: 20px;">
				<textarea style="height: 50px; width: 100%;" name="newComment" id="newComment" cols="50" rows="5"></textarea></p>
				<input type="button" id="createComment" value="Create Comment!" onClick="createComment(${term.id},'this is my test');">
			</form>
		</div>
		<a href="javascript:createComment(${term.id}, $('newComment').value);">test</a>
		
		<p><a href="glossaryPublic.do">Back to All Glossary Terms</a></p>
	</div>
</div>
</body>
</html>

