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
<title>Concern Indicator Tool</title>

  <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
  <!--CSS Libraries -->

  <style type="text/css" media="screen">@import "/styles/lit.css";</style>

  <pg:show condition="${!vtt.closed}">
  <script language="javascript" type="text/javascript" src="scripts/tinymce/jscripts/tiny_mce/tiny_mce.js"></script>
  </pg:show>
  <script src="/scripts/prototype.js" type="text/javascript"></script>
  <script src="/scripts/rico_simple.js" type="text/javascript"></script>
  <script src="/scripts/editor_simple.js" type="text/javascript"></script>
  <script src="/scripts/scriptaculous.js?load=effects,controls" type="text/javascript"></script>
  <script src="scripts/search.js" type="text/javascript"></script>
  
  <!--DWR and Component Interfaces -->
  <script type='text/javascript' src='/dwr/engine.js'></script>
  <script type='text/javascript' src='/dwr/util.js'></script>
  <script type='text/javascript' src='/dwr/interface/CHTAgent.js'></script>
  <script type='text/javascript' src='/dwr/interface/VTTAgent.js'></script>
  
<script type="text/javascript">
    ///////////////////////////////////////////////////new change/////////////////////////
    var chtId = ${cht.id};
    var vttId = ${vtt.id};
    var currentCategory = null;
    var previousCategory = null;
    var currentUserId = ${user.id};
    var ownerId = "${param.userId}";
    if (ownerId=='') ownerId = currentUserId;
    var page = 1;
    
    var tree1 = {
      selectedId : null,
      select : function(id) {
        displayIndicator(true);
        var current = this.selectedId;
        <c:choose>
          <c:when test="${currentUser==user && !vtt.closed}">
            VTTAgent.getCategoryPathValue({userId:currentUserId, pathId:id}, <pg:wfinfo/>,{
              callback:function(data){
                  if (data.successful){
                      displayIndicator(false);
                      $("col").innerHTML = data.html;
                      
                      if (current!=null) {
                        $('row-'+current).className = "catUnSelected";
                        $('vtrow-'+current).className = "catUnSelected";
                      }
                      tree1.selectedId = id;
                      currentCategory = $('col-'+tree1.selectedId).innerHTML;
                      $('row-'+tree1.selectedId).className = "catSelected";
                      $('vtrow-'+tree1.selectedId).className = "catSelected";
                  }else{
                      displayIndicator(false);
                      alert(data.reason);
                  }
              },
              errorHandler:function(errorString, exception){ 
                  alert("get comments error: " + errorString +" "+ exception);
              }
            });
          </c:when>
          <c:otherwise>
            displayIndicator(false);
            if (current!=null) {
              $('row-'+current).className = "catUnSelected";
              $('vtrow-'+current).className = "catUnSelected";
            }
            tree1.selectedId = id;
            currentCategory = $('col-'+tree1.selectedId).innerHTML;
            $('row-'+tree1.selectedId).className = "catSelected";
            $('vtrow-'+tree1.selectedId).className = "catSelected";
          </c:otherwise>
        </c:choose>
      }
    };
    
    <pg:show condition="${!vtt.closed}">
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
    
    function doOnLoad(){
      getComments(1);
      <pg:show condition="${!vtt.closed}">
      tinyMCE.idCounter=0;
      tinyMCE.execCommand('mceAddControl',false,'txtNewComment');
      </pg:show>
    }
    
    function saveValue(id, name, unit, isTag) {
      displayIndicator(true);
      VTTAgent.saveCategoryPathValue({vttId:${vtt.id}, pathId:id, name:name, unit:unit, isTag:isTag}, <pg:wfinfo/>,{
        callback:function(data){
            if (data.successful){
                displayIndicator(false);
                $('btnAdd').value="Save";
                $('col-right').innerHTML = data.html;
                $('vtrow-'+tree1.selectedId).className = "catSelected";
		// brandon added next line		
		alert('Your indicator has been saved.');
            }else{
                displayIndicator(false);
                alert(data.reason);
            }
        },
        errorHandler:function(errorString, exception){ 
            alert("save category value error: " + errorString +" "+ exception);
        }
      });
    }
    
    function keepBreaks(string){
      return string.replace(/\n/g,"<br>");
    }
 
    var relatedTagsArr = [];
    function getTags(categoryId, page, type, orphanpage){
      Util.loading(true,"Working");
      VTTAgent.getTags({userId: ${user.id}, vttId:${vtt.id}, categoryId:categoryId, page:page, count: 1000000000, orphanCount: 1000000000, type: type, orphanPage:orphanpage}, {
      callback:function(data){
        if (data.successful){
          if (type == 0){      
            relatedTagsArr = [];
            for(i=0; i<data.tags.length; i++){
              relatedTagsArr.push(data.tags[i].id);
            }
            
            document.getElementById('col').innerHTML = '<h4>Tags within "' + tree1.currentCategory + '"</h4>';
            document.getElementById('col').innerHTML += data.html;
          }
        }else{
          alert("Retrieving keywords not successful: " + data.reason);
        }
        Util.loading(false);
      },
      errorHandler:function(errorString, exception){ 
          alert("getTags: "+errorString+" "+exception);
      }
      });
    }

  function onSelectChanged() {
    location.href = '/workflow.do?workflowId='+${requestScope['org.pgist.wfengine.WORKFLOW_ID']}+'&contextId='+${requestScope['org.pgist.wfengine.CONTEXT_ID']}+'&activityId='+${requestScope['org.pgist.wfengine.ACTIVITY_ID']}+'&userId='+$('otherCategory').value;
  }
  
  function getComments(page) {
      VTTAgent.getComments({vttId:${vtt.id}, ownerId:ownerId, page:page}, <pg:wfinfo/>,{
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
    VTTAgent.createComment({vttId:${vtt.id}, ownerId:ownerId, title:title, content:content}, <pg:wfinfo/>,{
          callback:function(data){
              if (data.successful){
                  displayIndicator(false);
                  $('txtNewCommentTitle').value = '';
                  tinyMCE.setContent('');
                  getComments(1);
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
    VTTAgent.deleteComment({cid:cid}, <pg:wfinfo/>,{
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
      VTTAgent.setVotingOnComment({cid: cid, agree:agree}, {
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
  
  function publish(){
      if (!confirm('Publish concern-indicator paths?')) return;
      $('publishBtn').disabled=true;
      VTTAgent.publish({vttId:vttId}, {
      callback:function(data){
        if (data.successful){
          window.location.reload();
        }else{
          alert(data.reason);
          $('publishBtn').disabled=false;
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
  #col{width:30%;height:450px;}
  #col-right{width:38%;height:450px;}
  
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
      <!-- Instruction and overview -->
	<div id="overview" class="box2">
      <h3>Overview and instructions</h3>
      <c:set var="current" value="${requestScope['org.pgist.wfengine.CURRENT']}" />
      <pg:narrow name="current"/>
      <pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">In this step, participants specify indicators and measurement units for the hierarchy paths constructed previousky. Click on a path in the left column. In the middle column, enter an indicator label for that path by selecting a keyword/phrase from the pull down box or enter your own indicator label into the text box. Then, suggest a unit of measurement suitable for that climate concern indicator. These indicators and measurement units will be used to create climate change/variability maps using data as it becomes available from the Oregon Coastal Atlas data archive. Repeat the above instructions as many times as is appropriate to identify indicators and units of measurements to be used in creating climate change maps. When finished to share with others, click the "Publish" button at the bottom of left column.</pg:termHighlight>
      <p>
        <a href="#" onclick="Effect.toggle('hiddenRM','blind'); return false"><pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">Read more about this step</pg:termHighlight></a>
      </p>
      <p id="hiddenRM" style="display:none">
        <pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">The Oregon Coastal Atlas has thousands of data files. Many of them pretain to climate change and variability. However, it is possible that many of the indicator variables created during this process do not at this time have data readily available. We will find out in future steps of the project based on participant concerns.</pg:termHighlight>
      </p>
    </div>
<!-- end of overview and instructions -->

    <h2 class="headerColor">Specify indicators and measurement units
    <select id="otherCategory" onChange="onSelectChanged();">
        <option value="${baseuser.id}">My Category Paths</option>
        <logic:iterate id="other" name="others">
          <logic:equal name="other" property="id" value="${user.id}">
          <option value="${other.id}" SELECTED>${other.loginname}</option>
          </logic:equal>
          <logic:notEqual name="other" property="id" value="${user.id}">
          <option value="${other.id}">${other.loginname}</option>
          </logic:notEqual>
        </logic:iterate>
      </select></h2>
    <div id="col-left" style="height:430px;overflow:auto;">
      <div id="cats">
        <jsp:include page="vttCatsTable.jsp"/>
      </div>
    </div>
    
    <div id="col" style="height:430px;overflow:auto;"></div>
    <div id="col-right" style="height:430px;overflow:auto;"><jsp:include page="vttValueTree.jsp"/></div>
    
    <div style="clear:both"></div>
    <div id="spacer">
    </div>
    <div>
      <c:if test="${currentUser==user && !published}">
        <input id="publishBtn" type="button" value="Publish" onclick="publish();">
      </c:if>
    </div>
    
    <br>
    
    <p><b>Discussion about categories:</b>
    <div id="discussionBox" class="discussionBox"></div>
    
    <pg:show condition="${!vtt.closed}">
      <a id="newCommentAnchor" name="newCommentAnchor"></a>
      <div id="newComment" class="box8 padding5">
        <h3 class="headerColor">Post a comment</h3>
        <form>
          <p><label>Title</label><br><input maxlength="100" style="width:90%;" type="text" value="" id="txtNewCommentTitle"/></p>
          <p><label>Your Thoughts</label><br><textarea style="width:100%; height: 150px;" id="txtNewComment"></textarea></p>
          <input type="button" onClick="createVTTComment();" value="Submit">
          <input type="button" onClick="cancelVTTComment();" value="Cancel" />
          <!--
          <input type="checkbox" id="newCommentNotifier" />E-mail me when someone responds to this comment
          -->
        </form>
      </div>
    </pg:show>
    
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
