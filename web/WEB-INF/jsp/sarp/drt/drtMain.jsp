<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Discuss and Review Tool</title>
<style type="text/css" media="screen">@import "styles/lit.css";</style>
<style type="text/css" media="screen">
.blueBB {
	border-color: #C0D7F6 !important;
	border-width: 1px;
}
</style>

<pg:show condition="${!infoObject.closed}">
<script language="javascript" type="text/javascript" src="scripts/tinymce/jscripts/tiny_mce/tiny_mce.js"></script>
</pg:show>
<script language="JavaScript" src="scripts/qtip.js" type="text/JavaScript"></script>
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/globalSnippits.js" type="text/javascript"></script>

<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/DRTAgent.js'></script>

<script type="text/javascript">
    <pg:show condition="${!infoObject.closed}">
    tinyMCE.init({
        mode : "exact",
        theme : "advanced",
        theme_advanced_buttons1 : "bold, italic, bullist, numlist,undo, redo,link",
        theme_advanced_buttons2 : "",
        theme_advanced_buttons3 : "",
        content_css : "/scripts/tinymce/jscripts/tiny_mce/themes/simple/css/bigmce.css",
        extended_valid_elements : "blockquote[style='']"
    });
    </pg:show>
    
    var infoObject = null;
    
    function InfoObject() {
        this.oid = ${infoObject.id};
        this.targetId = ${infoObject.target.id};
        this.currentSort = 1;
        this.currentFilter = '';
        this.wfinfo = <pg:wfinfo/>;
        displayIndicator(true);
        this.oDivElement = 'infoObjectBox';
        this.discussionDivElement = 'discussionBox';
        this.page = 1;
        this.getComments = function(page){
            DRTAgent.getComments({oid:${infoObject.id}, page:page}, <pg:wfinfo/>,{
                callback:function(data){
                    if (data.successful){
                        displayIndicator(false);
                        $(infoObject.discussionDivElement).innerHTML = data.html;
                        infoObject.page = data.page;
                    }else{
                        displayIndicator(false);
                        alert(data.reason);
                    }
                },
                errorHandler:function(errorString, exception){ 
                    alert("get targets error: " + errorString +" "+ exception);
                }
            });
        };
        this.cancelComment = function() {
            $('txtNewCommentTitle').value = '';
            tinyMCE.setContent('');
        };
        this.createComment = function() {
          var title = $('txtNewCommentTitle').value;
          var content = tinyMCE.getContent();
          if (title.length<1) {
              alert('please input title');
              return;
          }
          if (content.length<1) {
              alert('please input content');
              return;
          }
          DRTAgent.createComment({oid:${infoObject.id}, title:title, content:content}, <pg:wfinfo/>,{
                callback:function(data){
                    if (data.successful){
                        displayIndicator(false);
                        $('txtNewCommentTitle').value = '';
                        tinyMCE.setContent('');
                        infoObject.getComments(1);
                    }else{
                        displayIndicator(false);
                        alert(data.reason);
                    }
                },
                errorHandler:function(errorString, exception){ 
                    alert("get targets error: " + errorString +" "+ exception);
                }
            });
        };
        this.setVoteOnInfoObject = function(agree){
            DRTAgent.setVotingOnInfoObject({oid: ${infoObject.id}, agree:agree}, {
            callback:function(data){
              if (data.successful){
                var votingDiv = 'voting';
                if($(votingDiv) != undefined){
                  new Effect.Fade(votingDiv, {
                    afterFinish:function(){
                        $('structure_question_status').innerHTML = '<h2>'+data.numAgree+' of '+data.numVote+'</h2> agree to move forward';
                        if (data.voted) {
                          $('structure_question').innerHTML = 'Your vote has been recorded. Thank you for your participation.';
                        }
                        new Effect.Appear(votingDiv);
                    }
                  });
                }
              }else{
                if (data.voted) {
                  $('structure_question').innerHTML = 'Your vote has been recorded. Thank you for your participation.';
                } else {
                  alert(data.reason);
                }
              }
            },
            errorHandler:function(errorString, exception){ 
                alert("setVote error:" + errorString + exception);
            }
            });
        
        };
    }
</script>

<pg:outputProperty property="javascript" />

<script type="text/javascript">
    function onPageLoaded() {
        infoObject = new InfoObject();
        window.setTimeout('tooltip.init()',1000);
	
        infoObject.getComments(1);
        <pg:show condition="${!infoObject.closed}">
        tinyMCE.idCounter=0;
        tinyMCE.execCommand('mceAddControl',false,'txtNewComment');
        </pg:show>
        
        infoObject.loadTarget();
    }
</script>

<event:pageunload />

</head>

<body onLoad="onPageLoaded();">
    <wf:nav />
    <wf:subNav />

    <div style="display: none;" id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>
    <div id="container">

        <div id="overview" class="box2">
            <table width="100%"><tr><td width="650px;">
            <pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">
                <h3 class="headerColor">Overview and instructions</h3>
                <p>The moderators have reviewed the concerns and summarized them according to themes. Now you can:</p>
                <ul>
                    <li>Review the concern themes</li>
                    <li>Discuss how well these summaries represent participants' concerns</li>
                    <li>Suggest revisions to the summaries in your discussion comments</li>
                </ul>
                <a href="#" onclick="Effect.toggle('hiddenRM','blind'); return false">Read more about this step</a>
                <p id="hiddenRM" style="display:none">After the brainstorm concluded, the moderators synthesized and summarized the concerns offered by participants. (<pg:url page="lmFaq.do" target="_blank" anchor="step1-created">Read more about the summarization process</pg:url>). Each concern theme is associated with a group of keywords. As you review summaries let us know if you think these summaries are accurate and if you feel any important themes were left out. The moderator will make revisions based on participant comments. The final version of these summaries will be included in the final report of the <em>LIT Challenge</em>. The summaries will also be used in Step 2 when we we assess different "factors" used to evaluate proposed transportation improvement projects.</p>
            </pg:termHighlight>
            </td><td></td><td style="padding-left:10px;width:180px;">
                <div id="votingMenu" class="floatLeft">
                <div id="voting">
                  <div id="votingMenuTally" class="box1">
                    <span id="structure_question_status"><h2>${infoObject.numAgree} of ${infoObject.numVote}</h2> agree to move forward</span>
                  </div>
                  <div id="structure_question">
                      <pg:show condition="${!infoObject.closed}">
                        <c:choose>
                          <c:when test="${voting == null}">
                            <p>Do you think we can move forward?</p>
                            <span>
                            <a href="javascript:infoObject.setVoteOnInfoObject('true');"><img src="images/btn_thumbsup_large.png" alt="YES" class="floatRight" style="margin-right:5px;"><a href="javascript:infoObject.setVoteOnInfoObject('false');"><img src="images/btn_thumbsdown_large.png" alt="NO" class="floatLeft" style="margin-left:5px;"></a></span>
                            </span>
                          </c:when>
                          <c:otherwise>
                            <span>
                            Your vote has been recorded. Thank you for your participation.
                            </span>
                          </c:otherwise>
                        </c:choose>
                      </pg:show>
                  </div>
                </div>
            </td></tr></table>
        </div>

        <div id="infoObjectBox" class="infoObjectBox">
            <pg:include property="page" />
        </div>
        
        <a id="firstCommentAnchor" name="firstCommentAnchor"></a>
        <div id="discussionBox" class="discussionBox"></div>
        
        <pg:show condition="${!infoObject.closed}">
          <a id="newCommentAnchor" name="newCommentAnchor"></a>
          <div id="newComment" class="box8 padding5">
            <h3 class="headerColor">Post a comment</h3>
            <form>
              <p><label>Title</label><br><input maxlength="100" style="width:90%;" type="text" value="" id="txtNewCommentTitle"/></p>
              <p><label>Your Thoughts</label><br><textarea style="width:100%; height: 150px;" id="txtNewComment"></textarea></p>
              <input type="button" onClick="infoObject.createComment();" value="Submit">
              <input type="button" onClick="infoObject.cancelComment();" value="Cancel" />
              <input type="checkbox" id="newCommentNotifier" />E-mail me when someone responds to this comment
            </form>
          </div>
        </pg:show>
        
        <div class="clearBoth"></div>
    
    </div>
    
    <wf:subNav />
    
    <pg:feedback id="feedbackDiv" action="sdMain.do" />

    <div id="footer">
        <jsp:include page="/footer.jsp" />
    </div>
    
</body>

</html>

