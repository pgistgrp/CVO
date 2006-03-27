<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<html:html>
<head>
<title>Test</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page='/styles/default.css'/>">
<link rel="stylesheet" type="text/css" href="<html:rewrite page='/styles/dhtmlXTree/dhtmlXTree.css'/>">
<script type='text/javascript' src='<html:rewrite page="/scripts/dhtmlXTree/dhtmlXCommon.js"/>'></script>
<script type='text/javascript' src='<html:rewrite page="/scripts/dhtmlXTree/dhtmlXTree.js"/>'></script>
<script type='text/javascript' src='<html:rewrite page="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<html:rewrite page="/dwr/util.js"/>'></script>
<script type='text/javascript' src='<html:rewrite page="/dwr/interface/CVOAgent.js"/>'></script>
<script>
  var tree1 = null;
  var tree2 = null;
  var tree3 = null;
  var bottomTree = null;
  function resetTags(data) {
    tree2.deleteChildItems(tree2.rootId);
    for(var i=0;i<data['tags'].length;i++) {
      var one = data['tags'][i];
      tree2.insertNewItem(tree2.rootId,one['id'],one['name'],0,0,0,0,'');
    }
    tree3.deleteChildItems(tree3.rootId);
    for(var i=0;i<data['all'].length;i++) {
      var one = data['all'][i];
      tree3.insertNewItem(tree3.rootId,one['id'],one['name'],0,0,0,0,'');
    }
  }
  function myClick1Handler(itemId) {
    CVOAgent.getCategoryTags(
      function(data) {
        resetTags(data);
      },
      itemId
    );
  }
  function myDrag1Handler(sourceId, targetId, siblingId, sourceTree, targetTree) {
    if (sourceTree.rootId==targetTree.rootId) {
      var result = 'false';
      DWREngine.setAsync(false);
      CVOAgent.moveCategory(
        targetId,
        sourceId,
        {
          callback: function(data) {
            result = data['result'];
          },
          async:false
        }
      );
      return result=='true';
    }
    return false;
  }
  function myDrag2Handler(sourceId, targetId, siblingId, sourceTree, targetTree) {
    if (sourceTree.rootId==tree3.rootId) {
      CVOAgent.addTagToCategory(
        function(data) {
          resetTags(data);
        },
        tree1.getSelectedItemId(),
        sourceId
      );
    }
    return false;
  }
  function myDrag3Handler(sourceId, targetId, siblingId, sourceTree, targetTree) {
    if (sourceTree.rootId==tree2.rootId) {
      CVOAgent.deleteTagFromCategory(
        function(data) {
          resetTags(data);
        },
        tree1.getSelectedItemId(),
        sourceId
      );
    }
    return false;
  }
  function doOnLoad(){
    <pg:tree id="treebox1" var="tree1" node="${cvoForm.category}"
        imagePath="/images/dhtmlXTree/" dragable="true"
        dragHandler="myDrag1Handler" clickHandler="myClick1Handler"
        stdImage="category.gif, category.gif, category.gif">
    </pg:tree>
    <pg:list id="treebox2" var="tree2" list="" title="name" identity="id"
        imagePath="/images/dhtmlXTree/" dragable="true"
        dragHandler="myDrag2Handler"
        stdImage="tag.gif, tag.gif, tag.gif">
    </pg:list>
    <pg:list id="treebox3" var="tree3" list="" title="name" identity="id"
        imagePath="/images/dhtmlXTree/" dragable="true"
        dragHandler="myDrag3Handler"
        stdImage="tag.gif, tag.gif, tag.gif">
    </pg:list>
    bottomTree = new dhtmlXTreeObject($('bottomTree'),"100%","100%",0);
    bottomTree.setImagePath("<html:rewrite page='/images/dhtmlXTree/'/>");
    bottomTree.loadXML("<html:rewrite page='/concerns.xml'/>");
  }
  function createCategory() {
    CVOAgent.createCategory(
      function(data) {
        if (data['result']=='true') {
          if (data['parent']) {
            tree1.insertNewItem(data['parent']['id'],data['category']['id'],data['category']['name'],0,0,0,0,'');
          } else {
            tree1.insertNewItem(tree1.rootId,data['category']['id'],data['category']['name'],0,0,0,0,'');
          }
        } else {
          alert(data['alert']);
        }
      },
      tree1.getSelectedItemId()==''?null:tree1.getSelectedItemId(),
      $('catName').value
    );
  }
  function createTag() {
    CVOAgent.createTag(
      function(data) {
        if (data['result']=='true') {
          tree3.insertNewItem(tree3.rootId,data['tag']['id'],data['tag']['name'],0,0,0,0,'');
        } else {
          alert(data['alert']);
        }
      },
      $('tagName').value
    );
  }
</script>
</head>

<body bgcolor="white" onload="doOnLoad();">
<center>

<table width="800">
  <tr>
    <td colspan="3">
      <div>Synthesize concerns: As a moderator, you can use this tool to sort out all the concerns the participants expressed in the previous step. All concerns are given "tags" which roughly captures the "ontology" of a participant as expressed in the concern. By adjusting the relationship among categories and the relationship among categories and tags, concerns will be grouped into clusters.</div>
    </td>
  </tr>
  <tr>
    <td valign="top">
      <div id="categorybar" style="background-color:#f5f5f5; margin-left:2px; border :1px solid Silver;">
        <input type="text" id="catName" value="New Category">
        <span style="margin-left:2px;" class="link" onclick="javascript:createCategory();">Create</span>
      </div>
    </td>
    <td>&nbsp;</td>
    <td valign="top">
      <div id="tagbar" style="background-color:#f5f5f5; margin-left:2px; border :1px solid Silver;">
        <input type="text" id="tagName" value="New Tag">
        <span style="margin-left:2px;" class="link" onclick="javascript:createTag();">Create</span>
      </div>
    </td>
  </tr>
  <tr>
    <td valign="top" width="33%">
      <div id="treebox1" style="width:100%; height:250; background-color:#f5f5f5; border :1px solid Silver; overflow:auto;"></div>
    </td>
    <td valign="top" width="33%">
      <div id="treebox2" style="width:100%; height:250; background-color:#f5f5f5; border :1px solid Silver; overflow:auto;"></div>
    </td>
    <td valign="top">
      <div id="treebox3" style="width:100%; height:250; background-color:#f5f5f5; border :1px solid Silver; overflow:auto;"></div>
    </td>
  </tr>
  <tr>
    <td colspan="3" valign="top">
      <div id="bottomTree" style="width:100%; height:500; background-color:#f5f5f5; border :1px solid Silver; overflow:auto;"></div>
    </td>
  </tr>
</table>

</center>
</body>
</html:html>

