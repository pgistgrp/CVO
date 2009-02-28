<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Indicator Creation Tool</title>

  <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
  <!--CSS Libraries -->

  <style type="text/css" media="screen">@import "/styles/lit.css";</style>

  <script language="javascript" type="text/javascript" src="scripts/tinymce/jscripts/tiny_mce/tiny_mce.js"></script>
  <script src="/scripts/prototype.js" type="text/javascript"></script>
  <script src="/scripts/rico_simple.js" type="text/javascript"></script>
  <script src="/scripts/editor_simple.js" type="text/javascript"></script>
  <script src="/scripts/scriptaculous.js?load=effects,controls" type="text/javascript"></script>
  <script src="scripts/search.js" type="text/javascript"></script>
  
  <!--DWR and Component Interfaces -->
  <script type='text/javascript' src='/dwr/engine.js'></script>
  <script type='text/javascript' src='/dwr/util.js'></script>
  <script type='text/javascript' src='/dwr/interface/VTTAgent.js'></script>
  
<script type="text/javascript">
    ///////////////////////////////////////////////////new change/////////////////////////
    var vttId = ${vtt.id};
    var currentCategory = null;
    var previousCategory = null;
    var targetUserId = ${targetUser.id};
    var page = 1;
    var navigation = [0, 0, 0, 0];
    
    var tree1 = {
      selectedId : null,
      select : function(id) {
        displayIndicator(true);
        var current = this.selectedId;
        VTTAgent.getExpertUnitSet({targetUserId:targetUserId, pathId:id}, <pg:wfinfo/>,{
          callback:function(data){
              if (data.successful){
                  displayIndicator(false);
                  $("col-right").innerHTML = data.html;
                  
                  if (current!=null) {
                    $('row-'+current).className = "catUnSelected";
                  }
                  tree1.selectedId = id;
                  currentCategory = $('col-'+tree1.selectedId).innerHTML;
                  $('row-'+tree1.selectedId).className = "catSelected";
              }else{
                  displayIndicator(false);
                  alert(data.reason);
              }
          },
          errorHandler:function(errorString, exception){ 
              alert("get comments error: " + errorString +" "+ exception);
          }
        });
      }
    };
    
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
      getComments(1);
      tinyMCE.idCounter=0;
      tinyMCE.execCommand('mceAddControl',false,'txtNewComment');
    }
    
    function keepBreaks(string){
      return string.replace(/\n/g,"<br>");
    }
 
    function onSelectChanged() {
      location.href = '/workflow.do?workflowId='+${requestScope['org.pgist.wfengine.WORKFLOW_ID']}+'&contextId='+${requestScope['org.pgist.wfengine.CONTEXT_ID']}+'&activityId='+${requestScope['org.pgist.wfengine.ACTIVITY_ID']}+'&targetUserId='+$('otherCategory').value;
    }
    
    function getComments(page) {
      VTTAgent.getSpecialistComments({vttId:${vtt.id}, targetUserId:targetUserId, page:page}, <pg:wfinfo/>,{
          callback:function(data){
              if (data.successful){
                  displayIndicator(false);
                  $("discussionBox").innerHTML = data.html;
                  page = data.page;
              }else{
                  displayIndicator(false);
                  alert(data.reason);
              }
          },
          errorHandler:function(errorString, exception){ 
              alert("get comments error: " + errorString +" "+ exception);
          }
      });
  }
  
  function cancelVTTComment() {
    $('txtNewCommentTitle').value = '';
    tinyMCE.setContent('');
  }
  
  function createVTTComment() {
    displayIndicator(true);
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
    VTTAgent.createSpecialistComment({targetUserId:targetUserId, vttId:${vtt.id}, title:title, content:content}, <pg:wfinfo/>,{
          callback:function(data){
              if (data.successful){
                  displayIndicator(false);
                  $('txtNewCommentTitle').value = '';
                  tinyMCE.setContent('');
                  getComments(1);
                  alert('Saved.');
              }else{
                  displayIndicator(false);
                  alert(data.reason);
              }
          },
          errorHandler:function(errorString, exception){ 
              alert("get targets error: " + errorString +" "+ exception);
          }
      });
  }
  
  function deleteVTTComment(cid) {
    if (!confirm('Delete this comment? No way to undo.')) return;
    displayIndicator(true);
    VTTAgent.deleteSpecialistComment({cid:cid}, <pg:wfinfo/>,{
          callback:function(data){
              displayIndicator(false);
              if (data.successful){
                getComments(page);
              }else{
                  alert(data.reason);
              }
          },
          errorHandler:function(errorString, exception){ 
              alert("get targets error: " + errorString +" "+ exception);
          }
      });
  }
  
  function setVoteOnComment(cid, agree){
      VTTAgent.setVotingOnSpecialistComment({cid: cid, agree:agree}, {
      callback:function(data){
        if (data.successful){
          var votingDiv = 'voting-comment'+cid;
          if($(votingDiv) != undefined){
            new Effect.Fade(votingDiv, {
              afterFinish:function(){
                $(votingDiv).innerHTML = "Do you agree with this comment? "+data.numAgree+" of "+data.numVote+" agree so far."
                  + "<img src='images/btn_thumbsdown_off.png' alt='Disabled Button'/> <img src='images/btn_thumbsup_off.png' alt='Disabled Button'/>";
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
  }
  
  function toggleSelection(pathId, name, type, criterion, checked) {
    VTTAgent.toggleSelection({pathId:pathId, name:name, type:type, criterion:criterion, checked:checked}, {
      callback:function(data){
        if (data.successful){
          return true;
        }else{
          alert(data.reason);
          return false;
        }
      },
      errorHandler:function(errorString, exception){ 
        alert("setVote error:" + errorString + exception);
      }
    });
  }
  
  function publishExpertUnits(){
      if (!confirm('Are you sure to publish your decision?')) return;
      VTTAgent.publishExpertUnits({vttId:vttId}, {
      callback:function(data){
        if (data.successful){
          $('publishBtn').disabled=true;
        }else{
          alert(data.reason);
        }
      },
      errorHandler:function(errorString, exception){ 
          alert("publish error:" + errorString + exception);
      }
      });
  }
  
  function saveUnitComment(pathId){
      VTTAgent.setUnitComment({pathId:pathId, content:$('unitComment').value}, {
      callback:function(data){
        if (data.successful){
          alert('saved');
        }else{
          alert(data.reason);
        }
      },
      errorHandler:function(errorString, exception){ 
          alert("publish error:" + errorString + exception);
      }
      });
  }
  </script>

  <!-- Template 5 Specific -->
  <style type="text/css" media="screen">@import "/styles/template5.css";</style>
  <script src="/scripts/util.js" type="text/javascript"></script>
  <!-- End Template 5 Specific -->
<style type="text/css"> 
  .inplaceeditor-form textarea {
    width: 95%;
    height: 100px;
  }
  
  #col-left, #col, #col-right {border:1px solid #B4D579;}
  
  .closeBox{float:right;}
  
  button#ss{font-size:12pt;padding:5px;}
  
  #col-left{width:28%;height:450px;}
  #col-right{width:68%;height:450px;}
  
  #topMenu {
    padding:5px;
    background:#E1F1C5;
    border-bottom:1px solid #C6D78C;
    margin-bottom:5px;
    margin-left:-3px;
  }
  
  .catSelected {
    background-color:#D6E7EF;
    cursor:pointer;
    font-weight:bold;
  }
  .catUnSelected {
    background-color:white;
    cursor:pointer;
    font-weight:normal;
  }
</style>
<event:pageunload />
</head>

<body onLoad="doOnLoad();">
<div id="savingIndicator" style="display: none; background-color:#FF0000;position:fixed;">&nbsp;Saving...<img src="/images/indicator.gif">&nbsp;</div>
<div id="header">
  <wf:nav />
  <wf:subNav />
</div>
<!-- End header -->
<div style="display: none;" id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>
<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="container">
  <div id="cont-resize">
    <h2 class="headerColor">Concerns Indicator Recommendations from Specialists
    <select id="otherCategory" onChange="onSelectChanged();">
      <option value="${baseuser.id}">My Categories</option>
      <logic:iterate id="other" name="others">
        <logic:equal name="other" property="id" value="${targetUser.id}">
        <option value="${other.id}" SELECTED>${other.loginname}</option>
        </logic:equal>
        <logic:notEqual name="other" property="id" value="${targetUser.id}">
        <option value="${other.id}">${other.loginname}</option>
        </logic:notEqual>
      </logic:iterate>
    </select></h2>
    
    <div id="col-left" style="height:450px;overflow:auto;">
      <div id="cats">
        <jsp:include page="vttCatsTable.jsp"/>
      </div>
    </div>
    
    <div id="col-right" style="height:450px;overflow:none;"></div>
    
    <div style="clear:both">
      <c:if test="${isOwner && !published}">
        <input id="publishBtn" type="button" value="Publish" onclick="publishExpertUnits();">
      </c:if>
    </div>
    <div id="spacer">
    </div>
    
    <br>
    
    <p><b>Discussion about the categories:</b>
    <div id="discussionBox" class="discussionBox"></div>
    
      <a id="newCommentAnchor" name="newCommentAnchor"></a>
      <div id="newComment" class="box8 padding5">
        <h3 class="headerColor">Post a comment</h3>
        <form>
          <p><label>Title</label><br><input maxlength="100" style="width:90%;" type="text" value="" id="txtNewCommentTitle"/></p>
          <p><label>Your Thoughts</label><br><textarea style="width:100%; height: 150px;" id="txtNewComment"></textarea></p>
          <input type="button" onClick="createVTTComment();" value="Submit comment">
          <input type="button" onClick="cancelVTTComment();" value="Cancel" />
        </form>
      </div>
    
    <div class="clearBoth"></div>

</div>
</div>

<!--feedback form-->

<div style="margin-top:130px;margin-left: 10px;">
<pg:feedback id="feedbackDiv" action="vttview.do" />
</div>

<!--end feedback form-->

  <wf:subNav />

  <div id="footer">
    <jsp:include page="/footer.jsp" />
  </div>
<!--script src="http://www.google-analytics.com/urchin.js" type="text/javascript">
</script-->
<!--script type="text/javascript">
_uacct = "UA-797433-1";
urchinTracker();
</script-->
</body>
</html>
