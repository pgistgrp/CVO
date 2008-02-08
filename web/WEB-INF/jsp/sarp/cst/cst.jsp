<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
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
    
    function doOnLoad(){
      tabberAutomatic();
      resetCols();
      tree1=new dhtmlXTreeObject("cats","100%","100%",0);
      tree1.setImagePath("/images/dhtmlXTree/");
      tree1.setDragHandler(moveNodeHandler);
      tree1.enableCheckBoxes(false);
      tree1.enableThreeStateCheckboxes(true);
      tree1.enableDragAndDrop(true);
      tree1.loadXML("/catsTree.do?cstId=${cst.id}");
      tree1.cstId = cstId;
      tree1.setDragCopyHandler(copyNodeHandler);
      //tree1.setDragDuplicateHandler(duplicateNodeHandler);
      tree1.setOnClickHandler(treeClickHandler);
      getOrphanTags();
    }
    
    function keepBreaks(string){
      return string.replace(/\n/g,"<br>");
    }
 
    function getOrphanTags(){
        CSTAgent.getOrphanTags({cstId:cstId, count: 1000000000}, {
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
      CSTAgent.getTags({cstId:${cst.id}, categoryId:categoryId, page:page, count: 1000000000, orphanCount: 1000000000, type: type, orphanPage:orphanpage}, {
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
    
  //--------------------------------------
  
  function checkaddcategory(e){
    if(e.keyCode == 13)addcategory();
  }
  
  function addcategory(){
    
    if(document.getElementById("newcatetext").value != ""){
      var catname = document.getElementById("newcatetext").value;
      var parentId = (tree1.lastSelected) ? tree1.lastSelected.parentObject.dataId : 0;
      Util.loading(true,"Working9");
      CSTAgent.addCategory({cstId:${cst.id}, parentId:parentId, name:catname},{
        callback:function(data){
          if (data.successful){
            tree1.insertNewItemUnderSelected(document.getElementById("newcatetext").value, data.newId);
            document.getElementById("newcatetext").value = "";
            top.location.reload();
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

  function globalKeyHandler(e){
    if(e.keyCode==46)
      deleteSelectedCategory();
  }
    
  function deleteSelectedCategory(){
    if(tree1.lastSelected!=null){
      if(confirm("Are you sure you want to delete category \"" + tree1.lastSelected.parentObject.label + "\"")){
        if(tree1.lastSelected.parentObject.parentObject.id == 0)
          var params = {bctId: bctId, categoryId: tree1.lastSelected.parentObject.dataId};
        else
          var params = {bctId: bctId, categoryId: tree1.lastSelected.parentObject.dataId,parentId: tree1.lastSelected.parentObject.parentObject.dataId}
          
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
  
  function getTheme(clickid) {
    CSTAgent.getSummary(
      {catRefId:clickid}, {
        callback:function(data){
          if (data.successful){
            $('theme').value = data.summary;
            $('theme').disabled = false;
            $('themeDiv').style.display = 'block';
          }else{
            alert("getTheme failure reason: "+data.reason);
          }
        },
        errorHandler:function(errorString, exception){
          alert("getTheme: "+errorString+" "+exception);
        }
      });
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
  
  function treeClickHandler(clickid, lastid, labeltext) {
    document.getElementById("selcatetext").value = labeltext;
    currentCategory = tree1.lastSelected.parentObject;
    tempcate = tree1.getTopLevelNode(currentCategory);
    
    getTheme(clickid);
    getTags(clickid, 0, 0, 1);
    getTags(clickid, 0, 1, 1);
    
    if ($('col-crud-options').style.display == 'none'){
      new Effect.SlideDown('col-crud-options',{duration: .5}); 
    }else{
      new Effect.Highlight('col-crud-options');
    }
    
    new Effect.Fade('col-option');
  }
  
  function unselectall(mode){
    if(mode){
      tree1.unSelectAll();
      $('selcatetext').value = '';
      
      $('theme').value = '';
      $('themeDiv').style.display = 'none';

      currentCategory=null;
      resetCols();
      getOrphanTags();
      $('col-crud-options').style.display = "none"; 
      $('col-option').style.display = "none";;
    }
    tree1.clickedOn = false;
  }
  
  function moveNodeHandler(sourceO, targetO){
    params = {cstId: cstId, categoryId: sourceO.dataId};
    if(sourceO.parentObject.id != 0)
      params.parent0Id = sourceO.parentObject.dataId;      
    if(targetO.id != 0)
      params.parent1Id = targetO.dataId;
      
    CSTAgent.moveCategory(params,{
      callback:function(data){
        if (data.successful){
          var newID=tree1._moveNode(sourceO,targetO);
          tree1.selectItem(newID);
          return true;
        }
        else{ 
          alert(data.reason);
          return false;
        }
      },
      errorHandler:function(errorString, exception){
              alert("moveNodeHandler: "+errorString+" "+exception);
      }
    });
  }
  
  function copyNodeHandler(sourceO, targetO){
    CSTAgent.copyCategory({bctId: bctId, categoryId: sourceO.dataId, parentId: targetO.dataId},{
      callback:function(data){
        if (data.successful){
          var newID=tree1._copyNodeTo(sourceO,targetO);
          tree1.selectItem(newID);
          return true;
        }
        else return false;
      },
      errorHandler:function(errorString, exception){
              alert("copyNodeHandler: "+errorString+" "+exception);
      }
    });
  }
  
  function duplicateSelectedCategory(){
    if(tree1.lastSelected!=null){
      var obj1 = tree1.lastSelected.parentObject;
      var params = {bctId:bctId,categoryId:obj1.dataId, name:"Similar to "+ obj1.label};
      if(obj1.parentObject.Id!=0)
        params.parentId = obj1.parentObject.dataId;
      CSTAgent.duplicateCategory(params, 
      {callback:function(data){
        if(data.successful){
          var newitem = tree1.insertNewItem(obj1.parentObject.id,data.newId,"Similar to "+ obj1.label);
          if ($('col-crud-options').style.display == 'none'){
            new Effect.SlideDown('col-crud-options',{duration: .5}); 
          }else{
            new Effect.Highlight('col-crud-options');
          }
          new Effect.Fade('col-option');
          location.href="#colsTop";
        }else
          alert(data.reason);
      },
      errorHandler:function(errorString, exception){
              alert(errorString+" "+exception);
      }
    });
    }
  }
  
  function resetCols(){
    $('col').innerHTML = '<h4>Select a category in the left to see tags assciated with it.</h4>';
    $('sidebar_tags').innerHTML = '<h4>Orphan Tags</h4>';
  }
  //------------------------------------
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
    #col-left{width:31%;height:450px;_height:409px;}
    #col {width:29%;padding-left:10px;height:450px;}
    #col-right{width:37%;height:418px;}

</style>
<event:pageunload />
</head>

<body onkeydown="globalKeyHandler(event);" onLoad="doOnLoad();">
  <div id="savingIndicator" style="display: none; background-color:#FF0000;position:fixed;">&nbsp;Saving...<img src="/images/indicator.gif">&nbsp;</div>
<div id="header">
  <!-- Start Global Headers  -->
  <wf:nav />
  <wf:subNav />
  <!-- End Global Headers -->
</div>
<!-- End header -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="container">
  <div id="cont-resize">
    <p><a href="userhome.do?workflowId=${requestScope['org.pgist.wfengine.WORKFLOW_ID']}">
      Back to Moderator Control Panel</a></p>
    <h2 class="headerColor">Concerns Synthesis Tool</h2>
    <a name="colsTop"></a>
    <div id="topMenu2">
      <input type="button" value="Unselect All" onclick="unselectall(true);">
    </div>
    <div id="col-left">
      <div id="topMenu" style="clear:both;">
        <input type="text" id="newcatetext" onkeydown="checkaddcategory(event)">
        <input type="button" id="addCat" value="Add Category" onclick="addcategory();">
      </div>
      <div id="cats" style="height:300px;overflow:auto;" onclick="unselectall(!tree1.clickedOn);"></div>
      <div style="width:100%;">
        <textarea id="theme" disabled="true" style="border-top:1px solid black; border-left:thin dotted #800080; border-right:thin dotted #800080;  border-bottom:thin dotted #800080; width:98%; height:108px; background-color:#FFFAF0;"></textarea>
      </div>
      <div id="themeDiv" style="width:100%; display:none;">
        <input type="button" value="save description" onclick="saveTheme();">
      </div>
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
    <div id="spacer">
    </div>
</div>
</div>


<!--feedback form-->

<div style="margin-top:130px;margin-left: 10px;">
<pg:feedback id="feedbackDiv" action="cstview.do" />
</div>

<!--end feedback form-->

  <!-- Start Global Headers  -->
  <wf:subNav />
  <!-- End Global Headers -->

<!-- Start Footer -->
  <div id="footer">
    <jsp:include page="/footer.jsp" />
  </div>
<!-- End Footer -->
<!--script src="http://www.google-analytics.com/urchin.js" type="text/javascript">
</script-->
<!--script type="text/javascript">
_uacct = "UA-797433-1";
urchinTracker();
</script-->
</body>
</html>
