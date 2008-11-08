<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- Begin Breadcrumbs -->
<div id="breadCrumbs" class="clearBoth"> 
    <pg:url page="/bctview.do" params="bctId=${bct.id}">Step 1a: Brainstorm</pg:url> &rarr; Comments about a concern
</div>
<!-- End Breadcrumbs -->

<!-- Begin voting tally menu -->

<div id="votingMenu" class="floatLeft">
  <div id="voting-object${infoObject.id}">
    <div id="votingMenuTally" class="box1"> <span id="structure_question_status">
      <h2>${concern.numAgree} of ${concern.numVote}</h2>
      people agree so far</div>
    </span>
    <p>Do you agree with this concern?</p>
    <span id="structure_question">
    <c:choose>
    <c:when test="${concern.object == null}">
    <a href="javascript:setVote(${concern.id}, 'true');"><img src="images/btn_thumbsup_large.png" alt="YES" class="floatRight" style="margin-right:5px;"><a href="javascript:setVote(${concern.id}, 'false');"><img src="images/btn_thumbsdown_large.png" alt="NO" class="floatLeft" margin-left:5px;></a></span>
    </c:when>
    <c:otherwise>
      <div class="box3" style="text-align:left;padding:2px;">Your vote has been recorded. Thank you for your participation. </div>
    </c:otherwise>
    </c:choose>
    </span></div>
</div>
<!-- end voting tally menu -->
<!-- BEGIN CONCERN -->
<div id="summary" class="box3 floatLeft">
  <div class="discussionBody" id="discussionBody">
    <div id="editingArea${concern.id}" style="display:none"></div>
    <div id="discussionText${concern.id}">
      <p>"${concern.content}"</p>
    </div>
    <h3 id="discussionAuthor">-
        <pg:url page="/publicprofile.do" target="_blank" params="userId=${concern.author.id}">${concern.author.loginname}</pg:url>
    </h3>
    <!--<div class="discussionComments" id="discussionComments"><h3><a href="concern.do?id=${concern.id}">${concern.replies} Comments</a></h3> (${concern.views} views)</div>-->
    <div class="discussionTagsList">
      <!-- iterate through concern tags here -->
      <div id="tagsUL${concern.id}">
        <ul class="tagsInline">
          <li class="tagsInline"><strong>Keywords:</strong> </li>
          <c:forEach items="${concern.tags}" var="tagref">
            <c:choose>
              <c:when test="${baseuser.id == concern.author.id}">
                <li class="box6 tagsInline"> 
              </c:when>
              <c:otherwise>
                <li class="box8 tagsInline"> 
              </c:otherwise>
            </c:choose>
            <!--<a href="javascript:changeCurrentFilter(${tagref.id});">-->
            ${tagref.tag.name}
            </li>
          </c:forEach>
        </ul>
      </div>
      <div id="tagEditingArea${concern.id}" style="display:none"></div>
      <div style="clear: left;"></div>
      <!-- end tag iteration -->
    </div>
    <!--end discussionTagsList -->
    <pg:show condition="${!bct.closed}">
    <c:if test="${baseuser.id == concern.author.id}">
        <div class="box6"> <strong>Author Actions:</strong> <a href="javascript:editConcernPopup(${concern.id});">Edit Concern</a> &nbsp; <a href="javascript:editTagsPopup(${concern.id});">Edit Tags</a></div>
    </c:if>
    </pg:show>
  </div>
  <!-- end discussion body -->
</div>
<!-- end discussion-left -->
</div>
<!-- END CONCERN -->
<div id="commentCount" class="clearBoth">
  <p />
  <c:if test="${fn:length(comments) != 0}">
    <p>
    <h3 class="headerColor">${fn:length(comments)}
      <c:choose>
        <c:when test="${fn:length(comments) == 1}"> Comment about this concern </c:when>
        <c:otherwise> Comments about this concern</c:otherwise>
      </c:choose>
    </h3>
    </p>
  </c:if>
</div>
<div id="postcomments" class="clearBoth">
<div id="filteredBy"></div>
<c:forEach var="comment" items="${comments}" varStatus="loop">

<div id="comment${comment.id}" class="discussionRow">
  <c:choose>
  <c:when test="${baseuser.id == comment.author.id}">
  <div class="discussionRowHeader box6">
    </c:when>
    <c:otherwise>
    <div class="discussionRowHeader box1">
      </c:otherwise>
      </c:choose>
      <div id="voting-post${comment.id}" class="discussionVoting"> ${comment.numAgree} of ${comment.numVote} participants agree with this post
        <c:choose>
          <c:when test="${comment.object == null}"> <a href="javascript:setCommentVoting(${comment.id}, 'false');"><img src="/images/btn_thumbsdown.png" alt="I disagree!" border="0"/></a> <a href="javascript:setCommentVoting(${comment.id}, 'true');"><img src="/images/btn_thumbsup.png" alt="I agree!" border="0"/></a> </c:when>
          <c:otherwise> <img src="images/btn_thumbsdown_off.png" alt="Disabled Button"/> <img src="images/btn_thumbsup_off.png" alt="Disabled Button"/> </c:otherwise>
        </c:choose>
      </div>
      <span class="discussionTitle"><a id="commentAnchor${comment.id}" name="commentAnchor${comment.id}" href="#commentAnchor${comment.id}">${comment.title}</a></div>
    <c:choose>
    <c:when test="${baseuser.id == comment.author.id}">
    <div class="discussionBody box7 padding5">
      </c:when>
      <c:otherwise>
      <div class="discussionBody padding5">
        </c:otherwise>
        </c:choose>
        <div id="discussionText${comment.id}" class="discussionText peekaboobugfix">
          <div id="commentContent${comment.id}">
            <p>${comment.content}</p>
          </div>
          <div id="commentOwner${comment.id}">
            <h3>- <pg:url page="/publicprofile.do" target="_blank" params="userId=${comment.author.id}">${comment.author.loginname}</pg:url></h3>
          </div>
        </div>
        <pg:show condition="${!bct.closed}">
        <div class="discussionComments peekaboobugfix"> <a href="javascript:setQuote(${comment.id});">Quote</a> </div>
        <pg:show roles="moderator">
          <div class="smallText" style="text-align:right;"> Moderator options:
            <input type="button" onClick="deleteComment(${comment.id});" value="Delete" />
          </div>
        </pg:show>
        </pg:show>
      </div>
    </div>
  </div>
  <div class="clearBoth"></div>
  </c:forEach>
  <c:if test="${fn:length(comments) > 0}">
    <div class="pagination"> Viewing page: ${setting.page} of ${setting.pageSize} &nbsp;
      <logic:equal name="setting" property="page" value="1"> <img src="images/btn_prev_fade.gif" alt="No Previous Pages" /> </logic:equal>
      <logic:notEqual name="setting" property="page" value="1"> <a href="javascript:goToPage('comments',${setting.page}-1);"><img src="images/btn_prev_a.gif" alt="Prev" name="prev" class="button" id="prev" onMouseOver="MM_swapImage('prev','','images/btn_prev_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a> </logic:notEqual>
      <logic:equal name="setting" property="page" value="${setting.pageSize}"> <img src="images/btn_next_fade.gif" alt="No Additional Pages" /> </logic:equal>
      <logic:notEqual name="setting" property="page" value="${setting.pageSize}"> <a href="javascript:goToPage('comments',${setting.page}+1)"><img src="images/btn_next_a.gif" alt="Next" name="next" class="button" id="next" onMouseOver="MM_swapImage('next','','images/btn_next_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a> </logic:notEqual>
    </div>
  </c:if>
</div>
<div class="clearBoth"></div>
<a id="commentAnchor" name="commentAnchor"></a>
<pg:show condition="${!bct.closed}">
<div id="newcomment" class="box8 padding5" style="margin-top: 10px;">
  <h3 class="headerColor">Post a comment</h3>
  <form onsubmit="return false;">
    <p>
      <label>Post title</label>
      <br>
      <input maxlength=100 style="width:90%;" type="text" value="" id="txtNewCommentTitle"/>
    </p>
    <p>
      <label>Your thoughts</label>
      <br>
      <textarea style="width:100%; height: 150px;" id="txtNewComment"></textarea>
    </p>
    <!--<p>
      <label>Tag your post (comma separated)</label>
      <br>
      <input style="width:100%" id="txtNewCommentTags" type="text" />
    </p>-->
    <input type="button" onClick="createNewComment();" value="Submit Comment">
    <input type="button" onClick="resetNewCommentForm();" value="Cancel" />
  </form>
</div>
</pg:show>
<div class="clearBoth"></div>

