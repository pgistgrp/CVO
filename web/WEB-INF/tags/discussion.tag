<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<%@ attribute name="discussible" required="true" %>
<%@ attribute name="title" %><!--default to discussion-->
<%@ attribute name="count" %><!--default 5-->
<%@ attribute name="hint" %><!--Post -->
<%@ attribute name="target" %>
<%@ attribute name="url" %>

<c:if test="${url}==null"><c:set var="url" value="/discussion.do?id=${discussible.id}"/></c:if>

<script>
var obj = {
	id : 61,
};

function getComments(){
	DiscussionAgent.getDiscussionBrief(obj,
		function(data){
			//display comments
		}
	);
}

function submitComments(){
	//prepare data
	dataObject = {
		id : 61,
		content : "this is my comment.",
	};
	DiscussionAgent.postComment(dataObject, 
		function(data){
			if(data.successful)
				getComments();
		});
}

</script>

<div>

<div>
<c:if test="${title}">${title}</c:if>
<c:else>Discussion</c:else>
</div>
<hr>
<div id="discarea">The latest 5 comments</div>
<div align="right"><a href="" <c:if test="${target}">target="${title}"</c:if> >more...</a></div>

<div>
<c:if test="${hint}">${title}</c:if>
<c:else>Post your comment here:</c:else>
</div>

<div>
<textarea id="cmnt_cont" style="width:100%" rows="5">
</textarea>
</div>

<div align="right" style="padding-top:8px;">
<input type="button" value="Submit">
</div>

</div>