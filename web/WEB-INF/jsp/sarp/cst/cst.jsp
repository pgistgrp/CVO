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
<title>Concerns Synthesis Tool</title>

  <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
  <!--CSS Libraries -->

  <style type="text/css" media="screen">@import "/styles/dhtmlXTree/dhtmlXTree.css";</style>
  <style type="text/css" media="screen">@import "/styles/tabs.css";</style>
  <style type="text/css" media="screen">@import "/styles/lit.css";</style>

  <pg:show condition="${!cst.closed}">
  <script language="javascript" type="text/javascript" src="scripts/tinymce/jscripts/tiny_mce/tiny_mce.js"></script>
  </pg:show>
  <script src="/scripts/prototype.js" type="text/javascript"></script>
  <script src="/scripts/rico_simple.js" type="text/javascript"></script>
  <script src="/scripts/tabcookies.js" type="text/javascript"></script>
  <script src="/scripts/tabs.js" type="text/javascript"></script>
  <script src="/scripts/editor_simple.js" type="text/javascript"></script>
  <script src="/scripts/scriptaculous.js?load=effects,controls" type="text/javascript"></script>
  <script src="/scripts/tags.js" type="text/javascript"></script>
  <script src="scripts/search.js" type="text/javascript"></script>
  
  <!--DHTML XTree Libraries -->
  <script src="/scripts/dhtmlXTree/dhtmlXTree.js" type="text/javascript"></script>  
  <script src="/scripts/dhtmlXTree/dhtmlXCommon.js" type="text/javascript"></script>  
  
  <!--DWR and Component Interfaces -->
  <script type='text/javascript' src='/dwr/engine.js'></script>
  <script type='text/javascript' src='/dwr/util.js'></script>
  <script type='text/javascript' src='/dwr/interface/BCTAgent.js'></script>
  <script type='text/javascript' src='/dwr/interface/CSTAgent.js'></script>
  <script type='text/javascript' src='/dwr/interface/DRTAgent.js'></script>
  
  <!-- Template 5 Specific -->
  <style type="text/css" media="screen">@import "/styles/template5.css";</style>
  <script src="/scripts/util.js" type="text/javascript"></script>
  <!-- End Template 5 Specific -->
  
<script type="text/javascript">
    ///////////////////////////////////////////////////new change/////////////////////////
    var bctId = ${bct.id};
    var cstId = ${cst.id};
    var tree1 = null;
    var currentCategory = null;
    var previousCategory = null;
    var currentUserId = ${user.id};
    var page = 1;
    
    <pg:show condition="${!cst.closed}">
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
      if (typeof(tabberAutomatic)=='function') tabberAutomatic();
      resetCols();
      <pg:show condition="${!modtool}">
      getComments(1);
      </pg:show>
      <pg:show condition="${!cst.closed}">
      tinyMCE.idCounter=0;
      tinyMCE.execCommand('mceAddControl',false,'txtNewComment');
      </pg:show>
      getOrphanTags();
      tree1=new dhtmlXTreeObject("cats","100%","100%",0);
      tree1.setImagePath("/images/dhtmlXTree/");
      tree1.enableCheckBoxes(false);
      tree1.enableThreeStateCheckboxes(true);
      tree1.loadXML("/catsTree.do?oid=${infoObject.id}&cstId=${cst.id}&userId=${user.id}");
      tree1.cstId = cstId;
      tree1.setOnClickHandler(treeClickHandler);
    }
    
    function keepBreaks(string){
      return string.replace(/\n/g,"<br>");
    }
 
    function getOrphanTags(){
      <pg:show condition="${modtool}">
        CSTAgent.getOrphanTags({cstId:cstId, count: 1000000000, modtool:"true"}, {
      </pg:show>
      <pg:hide condition="${modtool}">
        CSTAgent.getOrphanTags({cstId:cstId, count: 1000000000}, {
      </pg:hide>
        callback:function(data){
            if (data.successful){
              $('sidebar_tags').innerHTML += data.html;
            }
          }
        });
    }

    var relatedTagsArr = [];
    function getTags(categoryId, page, type, orphanpage){
      Util.loading(true,"Working")
      CSTAgent.getTags({<pg:show condition="${modtool}">modtool:true,</pg:show>userId: ${user.id}, cstId:${cst.id}, categoryId:categoryId, page:page, count: 1000000000, orphanCount: 1000000000, type: type, orphanPage:orphanpage}, {
      callback:function(data){
        if (data.successful){
          if (type == 0){      
            
            relatedTagsArr = [];
            for(i=0; i<data.tags.length; i++){
              relatedTagsArr.push(data.tags[i].id);
            }
            
            document.getElementById('col').innerHTML = '<h4>Tags within "' + currentCategory.label + '"</h4>';
            document.getElementById('col').innerHTML += data.html;
            
            if (data.tags.length > 0){
              getConcernsByTags(0);
              $('col').innerHTML += '<a href="javascript:getConcernsByTags(1); new Effect.Highlight(\'sidebar_concerns\'); void(0);">Show concerns with the above tags</a>';
            }
            
          }
          if (type == 1){
            $('sidebar_tags').innerHTML = '<h4>Tags not in "' + currentCategory.label + '"</h4>' + data.html;
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

    <pg:show condition="${(user.id==baseuser.id && !cst.closed) || modtool}">
    function relateTag(tagId){
      if(currentCategory == null)return;
      Util.loading(true,"Working");
      CSTAgent.relateTag({bctId:bctId, categoryId:currentCategory.dataId, tagId:tagId}, {
      callback:function(data){
          if (data.successful){
            //new Effect.Fade('tag' + tagId, {duration: 0.5, afterFinish: function(){getTags(currentCategory.dataId, 0, 1, tagId);getTags(currentCategory.dataId, 0, 0, tagId);}});
            getTags(currentCategory.dataId, 0, 1, tagId);
            getTags(currentCategory.dataId, 0, 0, tagId);
          }
          if (data.successful != true){
            alert(data.reason);
          }
        Util.loading(false);
        },
      errorHandler:function(errorString, exception){ 
      alert("relateTag: "+errorString+ " "+exception);
          //showTheError();
      }
      });
    }
    
    function derelateTag(categoryId, tagId){

        Util.loading(true,"Working");
        CSTAgent.derelateTag({bctId:bctId, categoryId:categoryId, tagId:tagId}, {
        callback:function(data){
            if (data.successful){
                  //new Effect.SwitchOff('tag' + tagId);
                  //new Effect.Fade('tag'+tagId, {duration: 0.5, afterFinish: function(){getTags(categoryId, 0, 1, tagId);getTags(categoryId, 0, 0, tagId);}});
                  getTags(categoryId, 0, 1, tagId);
                  getTags(categoryId, 0, 0, tagId);
            }
            if (data.successful != true){
              alert(data.reason);
            }
          Util.loading(false)
          },
        errorHandler:function(errorString, exception){ 
            alert("derelateTag: "+errorString+" "+exception);
        }
        });
    }
    
    function addcategory(){
      if(document.getElementById("newcatetext").value != ""){
        var catname = document.getElementById("newcatetext").value;
        var parentId = 0;
        Util.loading(true,"Working9");
        CSTAgent.addCategory({cstId:${cst.id}, parentId:parentId, name:catname},{
          callback:function(data){
            if (data.successful){
              tree1.insertNewItemAtBottom(document.getElementById("newcatetext").value, data.newId);
              document.getElementById("newcatetext").value = "";
              //top.location.reload();
            } else {
              alert(data.reason);
            }
            Util.loading(false);
          },
          errorHandler:function(errorString, exception){alert("addcategory: "+errorString+" "+exception);}
        });      
      }else{
      alert("Category can't be blank.");
      }
    }
    
    function checkaddcategory(e){
      if(e.keyCode == 13)addcategory();
    }
    
    function globalKeyHandler(e){
      if(e.keyCode==46)
        deleteSelectedCategory();
    }
      
    function deleteSelectedCategory(){
      if(tree1.lastSelected!=null){
        if(confirm("Are you sure you want to delete category \"" + tree1.lastSelected.parentObject.label + "\"")){
          if(tree1.lastSelected.parentObject.parentObject.id == 0)
            var params = {cstId: ${cst.id}, categoryId: tree1.lastSelected.parentObject.dataId};
          else
            var params = {cstId: ${cst.id}, categoryId: tree1.lastSelected.parentObject.dataId,parentId: tree1.lastSelected.parentObject.parentObject.dataId}
            
          CSTAgent.deleteCategory(params, {
                callback:function(data){
                  if (data.successful){
                    tree1.deleteSelectedItem();
                    new Effect.SlideUp('col-crud-options',{duration: .5});
                  }
                },
                errorHandler:function(errorString, exception){
                alert("deleteSelectedCategory: "+errorString+" "+exception);
                }
          });
        }
      }
    }
  
    function modifySelectedCategory(){
      var newtext = document.getElementById("selcatetext").value;
      if(tree1.lastSelected!=null && newtext!=""){
        var params = {bctId: bctId, categoryId: tree1.lastSelected.parentObject.dataId, name:newtext};
        CSTAgent.editCategory(params, {
              callback:function(data){
                if (data.successful){
                  tree1.modifyItemName(tree1.lastSelected.parentObject.dataId, newtext);
                  new Effect.Fade('col-option'); 
                }else{
                  alert("modifySelectedCategory failure reason: "+data.reason);
                }
              },
              errorHandler:function(errorString, exception){
                alert("modifySelectedCategory: "+errorString+" "+exception);
                }
        });
      }else{
        alert("Category can't be blank.");  
      }
    }
    
    function saveTheme() {
      CSTAgent.saveSummary(
        {catRefId:currentCategory.dataId, summary:$('theme').value}, {
          callback:function(data){
            if (data.successful){
              alert('Your description is saved.');
            }else{
              alert("getTheme failure reason: "+data.reason);
            }
          },
          errorHandler:function(errorString, exception){
            alert("getTheme: "+errorString+" "+exception);
          }
        });
    }
    </pg:show>
    
    function getConcernsByTags(visibility){
      //eventually make  paginated
        Util.loading(true,"Working");
        CSTAgent.getConcernsByTags({bctId:bctId, page: 1, count: 100000000}, relatedTagsArr, {
        callback:function(data){
            if (data.successful){
              if (visibility == 1){  
                $('myTab').tabber.tabShow(1);
              }else{
                $('myTab').tabber.tabShow(0);
              }
              $('sidebar_concerns').innerHTML = data.html;
            }
            if (data.successful != true){
              alert(data.reason);
            }
          Util.loading(false)
          },
        errorHandler:function(errorString, exception){ 
            alert("getConcernsByTags: "+errorString+" "+exception);
        }
        });
    }
    
    function getConcerns(tagId, page){
        Util.loading(true,"Working");
        CSTAgent.getConcerns({bctId:bctId, tagId: tagId, page: page}, {
        callback:function(data){
            if (data.successful){
              $('myTab').tabber.tabShow(1);
              $('sidebar_concerns').innerHTML = data.html;
            }
            if (data.successful != true){
              alert(data.reason);
            }
          Util.loading(false);
          },
        errorHandler:function(errorString, exception){ 
            alert("getConcerns: "+errorString+" "+exception);
        }
        });
    }
    
  function getTheme(clickid) {
    CSTAgent.getSummary(
      {catRefId:clickid}, {
        callback:function(data){
          if (data.successful){
            $('theme').value = data.summary;
            <pg:show condition="${user.id==baseuser.id && !cst.closed}">
            $('theme').disabled = false;
            $('themeDiv').style.display = 'block';
            </pg:show>
          }else{
            alert("getTheme failure reason: "+data.reason);
          }
        },
        errorHandler:function(errorString, exception){
          alert("getTheme: "+errorString+" "+exception);
        }
      });
  }
  
  function treeClickHandler(clickid, lastid, labeltext) {
    <pg:show condition="${(user.id==baseuser.id && !cst.closed) || modtool}">
    document.getElementById("selcatetext").value = labeltext;
    </pg:show>
    currentCategory = tree1.lastSelected.parentObject;
    tempcate = tree1.getTopLevelNode(currentCategory);
    
    <pg:hide condition="${modtool}">
    getTheme(clickid);
    </pg:hide>
    getTags(clickid, 0, 0, 1);
    getTags(clickid, 0, 1, 1);
    
    <pg:show condition="${(user.id==baseuser.id && !cst.closed) || modtool}">
    if ($('col-crud-options').style.display == 'none'){
      new Effect.SlideDown('col-crud-options',{duration: .5}); 
    }else{
      new Effect.Highlight('col-crud-options');
    }
    new Effect.Fade('col-option');
    </pg:show>
  }
  
  function unselectall(mode){
    if(mode){
      tree1.unSelectAll();
      <pg:show condition="${(user.id==baseuser.id && !cst.closed) || !modtool}">
      $('selcatetext').value = '';
      $('theme').value = '';
      $('themeDiv').style.display = 'none';
      </pg:show>
      currentCategory=null;
      resetCols();
      getOrphanTags();
      <pg:show condition="${(user.id==baseuser.id && !cst.closed) || modtool}">
      $('col-crud-options').style.display = "none"; 
      $('col-option').style.display = "none";
      </pg:show>
    }
    tree1.clickedOn = false;
  }
  
  function resetCols(){
    $('col').innerHTML = '<h4>Select a category in the left to see tags assciated with it.</h4>';
    $('sidebar_tags').innerHTML = '<h4>Orphan Tags</h4>';
  }
  
  function onSelectChanged() {
    location.href = '/workflow.do?workflowId='+${requestScope['org.pgist.wfengine.WORKFLOW_ID']}+'&contextId='+${requestScope['org.pgist.wfengine.CONTEXT_ID']}+'&activityId='+${requestScope['org.pgist.wfengine.ACTIVITY_ID']}+'&userId='+$('otherCategory').value;
  }
  
  <pg:show condition="${!modtool}">
  function getComments(page) {
      CSTAgent.getComments({catRefId:${root.id}, page:page}, <pg:wfinfo/>,{
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
  
  function cancelCSTComment() {
    $('txtNewCommentTitle').value = '';
    tinyMCE.setContent('');
  }
  
  function createCSTComment() {
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
    CSTAgent.createComment({catRefId:${root.id}, title:title, content:content}, <pg:wfinfo/>,{
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
  
  function deleteCSTComment(cid) {
    if (!confirm('Are you sure to delete this comment? There\'s not way to undo it.')) return;
    displayIndicator(true);
    CSTAgent.deleteComment({cid:cid}, <pg:wfinfo/>,{
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
      CSTAgent.setVotingOnComment({cid: cid, agree:agree}, {
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
  };
  
  <c:if test="${!published && !cst.closed}">
    function publish(){
      if (!confirm('Are you sure to publish your categories?')) return;
      CSTAgent.publish({cstId:cstId}, {
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
    };
  </c:if>
  </pg:show>
  <pg:show condition="${modtool}">
    function getAnnouncements(){
      displayIndicator(true);
      DRTAgent.getAnnouncements({oid:${infoObject.id}, modtool:'true'}, <pg:wfinfo/>,{
          callback:function(data){
              if (data.successful){
                  displayIndicator(false);
                  $('announcements').innerHTML = data.html;
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
    
    function setAnnouncementDone(aid) {
      displayIndicator(true);
      DRTAgent.setAnnouncementDone({aid:aid},{
        callback:function(data){
          if (data.successful){
              displayIndicator(false);
              getAnnouncements();
          }else{
              displayIndicator(false);
              alert(data.reason);
          }
        },
        errorHandler:function(errorString, exception){ 
            alert("error:" + errorString + exception);
        }
      });
    }
  </pg:show>
  </script>
  
<style type="text/css"> 

   .accordionTabTitleBar { 
      font-size: 12px; 
      padding : 2px 6px 2px 6px;
      border-style : solid none solid none;
      border-top-color: #DDDDDD; 
      border-bottom-color : #DDDDDD;
      border-width: 1px 0px 1px 0px; 
      text-transform: capitalize;
      cursor:pointer;
   }
   
   .accordionTabContentBox { 
      font-size : 11px;
      border : 1px solid #1f669b; 
      border-top-width : 0px;
      padding : 0px 8px 0px 8px;
   }
   
   .inplaceeditor-form textarea { 
       width: 95%;
       height: 100px;
   }

    #topMenu {
    padding:5px;
    background:#E1F1C5;
    border-bottom:1px solid #C6D78C;
    margin-bottom:5px;
    margin-left:-3px;
    }
    
    #topMenu input[type="text"]{width:110px;}
    
    #topMenu2 {
    padding:5px;
    padding-left:0px;
    width:97.75%;
    }
    
    #col-crud-options{
    background:#EEF7DD;
    padding:5px;
    margin-top:10px;
    border:1px solid #B4D579;
    }
    
    #col-left, #col {border:1px solid #B4D579;}
    
    .closeBox{float:right;}
    
    #summaryEditorTitle{display:block;margin-top:10px;}
    
    button#ss{font-size:12pt;padding:5px;}
    
    #sidebar_tags_header{border:1px solid #B4D579;}
    #col-left{width:31%;height:450px;}
    #col {width:29%;padding-left:10px;height:450px;}
    #col-right{width:37%;height:418px;}

</style>
<event:pageunload />
</head>

<pg:show condition="${(user.id==baseuser.id && !cst.closed) || modtool}">
<body onkeydown="globalKeyHandler(event);" onLoad="doOnLoad();">
</pg:show>
<pg:hide condition="${(user.id==baseuser.id && !cst.closed) || modtool}">
<body onLoad="doOnLoad();">
</pg:hide>
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
    <p><a href="userhome.do?workflowId=${requestScope['org.pgist.wfengine.WORKFLOW_ID']}">
      Back to Moderator Control Panel</a></p>
    <pg:show condition="${!modtool}">
    <h2 class="headerColor">Concerns Synthesis Tool for participant
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
    </pg:show>
    <pg:show condition="${modtool}">
      <h2 class="headerColor">Moderator Tool for TCT</h2>
    </pg:show>
    
    <pg:show condition="${modtool}">
    <div id="overview" class="box2">
      <div id="col-right2" style="overflow:hidden;clear:both;height:20px;">
        <div style="float:left;overflow:hidden;">
          Moderator Announcements:
        </div>
      </div>
      <div id="col-right3" style="overflow:auto;clear:both;height:250px;">
        <center>
          <div id="announcements" style="clear:both;padding:2px;overflow:auto;width:90%;height:80%;">
            <jsp:include page="../drt/drtAnnouncements.jsp" />
          </div>
        </center>
      </div>
    </div>
    </pg:show>

    <div id="col-left">
      <div id="topMenu" style="clear:both;">
        <pg:show condition="${!modtool}">
          <pg:show condition="${user.id==baseuser.id && !cst.closed}">
          <input type="text" id="newcatetext" onkeydown="checkaddcategory(event)">
          <input type="button" id="addCat" value="Add Category" onclick="addcategory();">
          </pg:show>
          <pg:hide condition="${user.id==baseuser.id && !cst.closed}">
          ${user.loginname}'s categories
          </pg:hide>
        </pg:show>
        <pg:show condition="${modtool}">
          <input type="text" id="newcatetext" onkeydown="checkaddcategory(event)">
          <input type="button" id="addCat" value="Add Category" onclick="addcategory();">
        </pg:show>        
      </div>
      <div id="cats" style="height:300px;overflow:auto;" onclick="unselectall(!tree1.clickedOn);"></div>
      <pg:hide condition="${modtool}">
      <div style="width:100%;">
        <textarea id="theme" disabled="true" style="border-top:1px solid black; border-left:thin dotted #800080; border-right:thin dotted #800080;  border-bottom:thin dotted #800080; width:98%; height:108px; background-color:#FFFAF0;"></textarea>
      </div>
      <div id="themeDiv" style="width:100%; display:none;">
        <input type="button" value="save description" onclick="saveTheme();">
      </div>
      </pg:hide>
    </div>
    
    <div id="col"></div>
    
    <div id="col-right">
        <!--START Tabs -->
        <div id="bar">
          <a id="SideConcernsTop" name="SideConcernsTop"></a>
            <div class="tabber" id="myTab">
                <!-- AB 1 -->
                <div id="sidebar_tags_header" class="tabbertab">
                  <H2>Tags</H2>
                  <div id="sidebar_tags">
                    <!-- load tags into this div -->
                  </div>
                </div>
                <!-- END TAB 1 -->
                <!-- START TAB 2 -->
                <div id="sidebar_concerns_header" class="tabbertab">
                  <h2>Concerns</h2>
                  <div id="sidebar_concerns"><h4>Concerns</h4>
                    <!-- load concerns into this div -->
                  </div>
                </div>
               <!-- END TAB 2 -->
            </div>
        </div>
        <!--END Tabs -->
    </div>
    
    <div style="clear:both"></div>
    <pg:show condition="${(user.id==baseuser.id && !cst.closed) || modtool}">
    <div id="col-crud-options" style="display:none;margin-top:50px">
        <span class="closeBox">
          <a href="javascript: new Effect.SlideUp('col-crud-options',{duration: .5}); void(0);">
            <img src="images/close.gif" border="0" alt="Hide" name="Hide" class="button" style="margin-bottom:3px;" id="hide" onMouseOver="MM_swapImage('hide','','images/close1.gif',1)" onMouseOut="MM_swapImgRestore()">
            </a>
        </span>
      <strong>Editing Options: </strong>
        <input type="button" onclick="deleteSelectedCategory();" value="Delete" />
        <input type="button" onclick="Element.toggle('col-option')" value="Rename"/>
    </div>
    <div id="col-option" style="display:none;"><span class="closeBox"><a href="javascript: new Effect.SlideDown('col-crud-options',{duration: .5}); void(0); new Effect.Fade('col-option'); void(0);">back to all options</a></span>
      <h4>Editing Options</h4>
      Rename to: <form name="modifyCategory" action="" method="GET" onsubmit="javascript: modifySelectedCategory(); return false;"><input type="text" style="width: 50%;" id="selcatetext" onkeydown="checkaddcategory(event)"><input type="button" id="btnNewName" value="Modify" onclick="modifySelectedCategory();"></form><br>
    </div>
    </pg:show>
    <div id="spacer">
    </div>
    <div>
      <c:if test="${!published && !cst.closed}">
        <input id="publishBtn" type="button" value="Publish" onclick="publish();">
      </c:if>
    </div>
    
    <br>
    
    <pg:show condition="${!modtool}">
      <p><b>Discussion about the categories:</b>
      <div id="discussionBox" class="discussionBox"></div>
      
      <pg:show condition="${!cst.closed}">
        <a id="newCommentAnchor" name="newCommentAnchor"></a>
        <div id="newComment" class="box8 padding5">
          <h3 class="headerColor">Post a comment</h3>
          <form>
            <p><label>Title</label><br><input maxlength="100" style="width:90%;" type="text" value="" id="txtNewCommentTitle"/></p>
            <p><label>Your Thoughts</label><br><textarea style="width:100%; height: 150px;" id="txtNewComment"></textarea></p>
            <input type="button" onClick="createCSTComment();" value="Submit">
            <input type="button" onClick="cancelCSTComment();" value="Cancel" />
            <input type="checkbox" id="newCommentNotifier" />E-mail me when someone responds to this comment
          </form>
        </div>
      </pg:show>
    </pg:show>
    
    <div class="clearBoth"></div>

</div>
</div>


<!--feedback form-->

<div style="margin-top:130px;margin-left: 10px;">
<pg:feedback id="feedbackDiv" action="cstview.do" />
</div>

<!--end feedback form-->

<wf:subNav />

<div id="footer">
  <jsp:include page="/footer.jsp" />
</div>

</body>
</html>
