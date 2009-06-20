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
<title>Build Hierarchies</title>

  <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
  <!--CSS Libraries -->

  <style type="text/css" media="screen">@import "/styles/lit.css";</style>

  <pg:show condition="${!cht.closed}">
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
  <script type='text/javascript' src='/dwr/interface/BCTAgent.js'></script>
  <script type='text/javascript' src='/dwr/interface/CHTAgent.js'></script>
  
  <!-- Template 5 Specific -->
  <style type="text/css" media="screen">@import "/styles/template5.css";</style>
  <script src="/scripts/util.js" type="text/javascript"></script>
  <!-- End Template 5 Specific -->
  
<script type="text/javascript">
    ///////////////////////////////////////////////////new change/////////////////////////
    var cstId = ${cst.id};
    var chtId = ${cht.id};
    var currentCategory = null;
    var previousCategory = null;
    var currentUserId = ${user.id};
    var page = 1;
    var navigation = [0, 0, 0, 0, 1, 0];
    
    var tree1 = {
      selectedId : null,
      select : function(id) {
        if (this.selectedId!=null) {
          $('row-'+this.selectedId).className = "catUnSelected";
        }
        this.selectedId = id;
        this.currentCategory = $('col-'+this.selectedId).innerHTML;
        var catRefId = this.selectedId;
        getTags(catRefId, 0, 0, 1);
        <pg:show condition="${user.id==baseuser.id && !cht.closed}">
        CHTAgent.getNavigation({catRefId:catRefId},{
          callback:function(data){
            if (data.successful){
              navigation = data.navigation;
              for (var i=0; i<6; i++) {
                var element = $('navigator-'+i);
                if (navigation[i]==0) {
                  element.src = '/images/gray-go-'+i+'.png';
                } else {
                  element.src = '/images/go-'+i+'.png';
                }
              }
              $('row-'+catRefId).className = "catSelected";
            } else {
              alert(data.reason);
            }
          },
          errorHandler:function(errorString, exception){
            alert("getNavigation: "+errorString+" "+exception);
          }
        });
        </pg:show>
        <pg:hide condition="${user.id==baseuser.id && !cht.closed}">
        $('row-'+this.selectedId).className = "catSelected";
        </pg:hide>
      }
      <pg:show condition="${user.id==baseuser.id && !cht.closed}">
      ,
      navigate : function(n) {
        if (navigation[n]==0) return;
        if (this.selectedId==null) return;
        var catRefId = this.selectedId;
        CHTAgent.moveCategoryReference({chtId:chtId, catRefId:catRefId, direction:n},{
          callback:function(data){
            if (data.successful){
              $('cats').innerHTML = data.html;
              tree1.select(catRefId);
            } else { 
              alert(data.reason);
            }
          },
          errorHandler:function(errorcatsTableString, exception){
            alert("moveCategoryReference: "+errorcatsTableString+" "+exception);
          }
        });
      }
      </pg:show>
    };
    
    <pg:show condition="${!cht.closed}">
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
      <pg:show condition="${!cht.closed}">
      tinyMCE.idCounter=0;
      tinyMCE.execCommand('mceAddControl',false,'txtNewComment');
      </pg:show>
    }
    
    function keepBreaks(string){
      return string.replace(/\n/g,"<br>");
    }
 
    var relatedTagsArr = [];
    function getTags(categoryId, page, type, orphanpage){
      Util.loading(true,"Working");
      CHTAgent.getTags({userId: ${user.id}, chtId:${cht.id}, categoryId:categoryId, page:page, count: 1000000000, orphanCount: 1000000000, type: type, orphanPage:orphanpage}, {
      callback:function(data){
        if (data.successful){
          if (type == 0){      
            relatedTagsArr = [];
            for(i=0; i<data.tags.length; i++){
              relatedTagsArr.push(data.tags[i].id);
            }
            
            document.getElementById('tags').innerHTML = '<h4>Keywords/phrases within "' + tree1.currentCategory + '"</h4>';
            document.getElementById('tags').innerHTML += data.html;
          }
        }else{
          alert("Getting tags not successful: " + data.reason);
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
      CHTAgent.getComments({catRefId:${root.id}, page:page}, <pg:wfinfo/>,{
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
  
  function cancelCHTComment() {
    $('txtNewCommentTitle').value = '';
    tinyMCE.setContent('');
  }
  
  function createCHTComment() {
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
    CHTAgent.createComment({catRefId:${root.id}, title:title, content:content}, <pg:wfinfo/>,{
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
              alert("create comment error: " + errorString +" "+ exception);
          }
      });
  }
  
  function deleteCHTComment(cid) {
    if (!confirm('Are you sure you want to delete this comment? There is no way to undo this action.')) return;
    displayIndicator(true);
    CHTAgent.deleteComment({cid:cid}, <pg:wfinfo/>,{
          callback:function(data){
              displayIndicator(false);
              if (data.successful){
                getComments(page);
              }else{
                  alert(data.reason);
              }
          },
          errorHandler:function(errorString, exception){ 
              alert("delete comment error: " + errorString +" "+ exception);
          }
      });
  }
  
  function setVoteOnComment(cid, agree){
      CHTAgent.setVotingOnComment({cid: cid, agree:agree}, {
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
            $('structure_question').innerHTML = 'Your vote has been recorded. Thank you for participating.';
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
  
  function publish(){
      if (!confirm('Confirm?')) return;
          $('publishBtn').disabled=true;
      CHTAgent.publish({chtId:chtId}, {
      callback:function(data){
        if (data.successful){
          window.location.reload();
        }else{
          $('publishBtn').disabled=false;
          alert(data.reason);
        }
      },
      errorHandler:function(errorString, exception){ 
          alert("publish error:" + errorString + exception);
      }
      });
  };
  </script>
  
<style type="text/css"> 
   .inplaceeditor-form textarea { 
       width: 95%;
       height: 100px;
   }
   
   #col-left, #col {border:1px solid #B4D579;}
   
   .closeBox{float:right;}
   
   button#ss{font-size:12pt;padding:5px;}
   
   #col-left{width:60%;height:450px;}
   #col{width:38%;height:450px;}

   .topMenu {
     padding:5px;
     background:#E1F1C5;
     border-bottom:1px solid #C6D78C;
     margin-bottom:5px;
     margin-left:-3px;
     height:20px;
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
      <h3>Instruction overview</h3>
      <c:set var="current" value="${requestScope['org.pgist.wfengine.CURRENT']}" />
      <pg:narrow name="current"/>
      <p><pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">This sub-step allows users to build hierarchies, further refining relationships between group categories, from general to specific levels.<br>
      The hierarchies users create will be separated into paths (see diagram at right), contextualizing specific categories within general categories, to be used in the next step.</pg:termHighlight></p>
      <p>
        <a href="#" onclick="Effect.toggle('hiddenRM','blind'); return false"><pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">Detailed instructions</pg:termHighlight></a>
      </p>
      <ol id="hiddenRM" style="display:none">
        <pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">  <li>Choose categories to be <b>included</b> in your hierarchies by highlighting them and clicking the &quot;<b>+</b>&quot; button.</li>
	<li>Using the arrow icons organize categories into hierarchies from general (at the left) to specific (at the right).  Any number of hierarchies is OK.  To remove a category, click the &quot;<em><b>-</b></em>&quot; button; all categories beneath and to the right will be removed.</li>
	<li>Click &quot;<b>Publish</b>&quot; to share your hierarchies with other users.</li>
	<li>Use the drop down menu to explore hierarchies created by other users.  Discuss the contributions of each user by submitting comments at the bottom of the screen.</li>
	<li>Re-evaluate your own contributions and alter them if necessary.</li></pg:termHighlight>
      </ol>
    </div>
<!-- end of overview and instructions -->

	<h2 class="headerColor">Create category hierarchies
    <select id="otherCategory" onChange="onSelectChanged();">
        <option value="${baseuser.id}">My Categories</option>
        <logic:iterate id="other" name="others">
          <logic:equal name="other" property="id" value="${user.id}">
          <option value="${other.id}" SELECTED>${other.loginname}</option>
          </logic:equal>
          <logic:notEqual name="other" property="id" value="${user.id}">
          <option value="${other.id}">${other.loginname}</option>
          </logic:notEqual>
        </logic:iterate>
      </select></h2>
    <div id="col-left">
      <div class="topMenu" style="clear:both;">
        <pg:show condition="${user.id==baseuser.id && !cht.closed}">
        <img id="navigator-0" src="/images/gray-go-0.png" style="cursor:pointer;" onclick="tree1.navigate(0);">
        <img id="navigator-1" src="/images/gray-go-1.png" style="cursor:pointer;" onclick="tree1.navigate(1);">
        <img id="navigator-2" src="/images/gray-go-2.png" style="cursor:pointer;" onclick="tree1.navigate(2);">
        <img id="navigator-3" src="/images/gray-go-3.png" style="cursor:pointer;" onclick="tree1.navigate(3);">
        <img id="navigator-4" src="/images/gray-go-4.png" style="cursor:pointer;" onclick="tree1.navigate(4);">
        <img id="navigator-5" src="/images/gray-go-5.png" style="cursor:pointer;" onclick="tree1.navigate(5);">
        </pg:show>
        <pg:hide condition="${user.id==baseuser.id && !cht.closed}">
        ${user.loginname}'s categories
        </pg:hide>
      </div>
      <div id="cats" style="height:410px;overflow:auto;">
        <jsp:include page="chtCatsTable.jsp"/>
      </div>
    </div>
    
    <div id="col" style="overflow:auto;">
      <div class="topMenu" style="clear:both;">Keywords/phrases related to concern cateogory </div>
      <div id="tags"></div>
    </div>
    
    <div style="clear:both"></div>
    <div id="spacer">
    </div>
    <div>
      <c:if test="${!published}">
        <input id="publishBtn" type="button" value="Publish" onclick="publish();">
      </c:if>
    </div>
    
    <br>
    
    <p><b>Discussion about categories:</b>
    <div id="discussionBox" class="discussionBox"></div>
    
    <pg:show condition="${!cht.closed}">
      <a id="newCommentAnchor" name="newCommentAnchor"></a>
      <div id="newComment" class="box8 padding5">
        <h3 class="headerColor">Post a comment</h3>
        <form>
          <p><label>Title</label><br><input maxlength="100" style="width:90%;" type="text" value="" id="txtNewCommentTitle"/></p>
          <p><label>Your Thoughts</label><br><textarea style="width:100%; height: 150px;" id="txtNewComment"></textarea></p>
          <input type="button" onClick="createCHTComment();" value="Submit">
          <input type="button" onClick="cancelCHTComment();" value="Cancel" />
        </form>
      </div>
    </pg:show>
    
    <div class="clearBoth"></div>

</div>
</div>


<!--feedback form-->

<div style="margin-top:130px;margin-left: 10px;">
<pg:feedback id="feedbackDiv" action="chtview.do" />
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
