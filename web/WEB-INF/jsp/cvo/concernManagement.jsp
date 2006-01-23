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
  function myClick1Handler(itemId) {
    CVOAgent.getCategoryTags(
      function(data) {
        alert(data);
      },
      itemId
    );
  }
  function myDrag1Handler(sourceId, targetId, siblingId, sourceTree, targetTree) {
    if (sourceTree.rootId==targetTree.rootId) return true;
    return false;
  }
  function myDrag2Handler(sourceId, targetId, siblingId, sourceTree, targetTree) {
    if (tree1.getSelectedItemId()==null || tree1.getSelectedItemId()=='') return false;
    if (sourceTree.rootId==tree3.rootId) return true;
    return false;
  }
  function myDrag3Handler(sourceId, targetId, siblingId, sourceTree, targetTree) {
    if (sourceTree.rootId==tree2.rootId) return true;
    return false;
  }
  function doOnLoad(){
    <pg:tree id="treebox1" var="tree1" node="${cvoForm.category}"
        imagePath="/images/dhtmlXTree/" dragable="true"
        dragHandler="myDrag1Handler" clickHandler="myClick1Handler"
        stdImage="book.gif, books_open.gif, books_close.gif">
    </pg:tree>
    <pg:list id="treebox2" var="tree2" list="" title="name" identity="id"
        imagePath="/images/dhtmlXTree/" dragable="true"
        dragHandler="myDrag2Handler"
        stdImage="book.gif, books_open.gif, books_close.gif">
    </pg:list>
    <pg:list id="treebox3" var="tree3" list="${cvoForm.tags}" title="name" identity="id"
        imagePath="/images/dhtmlXTree/" dragable="true"
        dragHandler="myDrag3Handler"
        stdImage="book.gif, books_open.gif, books_close.gif">
    </pg:list>
  }
  function createCategory() {
    CVOAgent.createCategory(
      function(data) {
        tree1.insertNewItem(tree1.rootId,data['category']['id'],data['category']['name'],0,0,0,0,'');
      },
      tree1.getSelectedItemId()==''?null:tree1.getSelectedItemId(),
      $('catName').value
    );
  }
</script>
</head>

<body bgcolor="white" onload="doOnLoad();">

<table height="100%">
  <tr>
    <td valign="top">
      <div id="toolbar" style="background-color:#f5f5f5; margin-left:2px; border :1px solid Silver;">
        <input type="text" id="catName" value="New Category">
        <span style="margin-left:2px;" class="link" onclick="javascript:createCategory();">Create</span>
      </div>
    </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr height="95%">
    <td valign="top">
      <div id="treebox1" style="width:250; height:90%; background-color:#f5f5f5; border :1px solid Silver; overflow:auto;"></div>
    </td>
    <td valign="top">
      <div id="treebox2" style="width:250; height:90%; background-color:#f5f5f5; border :1px solid Silver; overflow:auto;"></div>
    </td>
    <td valign="top">
      <div id="treebox3" style="width:250; height:90%; background-color:#f5f5f5; border :1px solid Silver; overflow:auto;"></div>
    </td>
  </tr>

</body>
</html:html>

