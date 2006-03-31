<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ attribute name="js_id" %>
<%@ attribute name="discussible" required="true" %>
<%@ attribute name="title" %><!--default to discussion-->
<%@ attribute name="count" %><!--default 5-->
<%@ attribute name="hint" %><!--Post -->
<%@ attribute name="target" %>
<%@ attribute name="url" %>

<c:if test="${js_id==null}"><c:set var="js_id" value="discussion"/></c:if>
<c:if test="${title==null}"><c:set var="title" value="Discussion"/></c:if>
<c:if test="${hint==null}"><c:set var="hint" value="Post your comment here:"/></c:if>
<c:if test="${url==null}"><c:set var="url" value="/discussion.do?id=${discussible}"/></c:if>
<c:if test="${count==null}"><c:set var="count" value="5"/></c:if>

<script>
  var obj = {
    id : 61,
  };
  
  var ${js_id} = {
    getComments : function(){
      DiscussionAgent.getDiscussionBrief(obj,
        function(data){
          //display comments
        }
      );
    },
    submitComments : function(){
      //prepare data
      dataObject = {
        id : 61,
        content : "this is my comment.",
      };
      DiscussionAgent.postComment(dataObject, 
        function(data){
          if(data.successful)
            getComments();
        }
      );
    },
  }
</script>

<div>

<div>${title}</div>
<hr>
<div id="discarea">The latest ${count} comments</div>
<div align="right"><a href="<html:rewrite page='${url}'/>" <c:if test="${target}">target="${title}"</c:if>>more...</a></div>

<div>${hint}</div>

<div>
  <textarea id="cmnt_cont" style="width:100%" rows="5">
  </textarea>
</div>

<div align="right" style="padding-top:8px;">
  <input type="button" value="Submit" onclick="${js_id}.submitComments();">
</div>

</div>