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
<script>
  var tree1 = null;
  function myDrag1Handler(sourceId, targetId, siblingId, sourceTree, targetTree) {
    if (sourceTree.rootId==targetTree.rootId) return true;
    return false;
  }
  function myDrag2Handler(sourceId, targetId, siblingId, sourceTree, targetTree) {
    if (sourceTree.rootId==3) return true;
    return false;
  }
  function myDrag3Handler(sourceId, targetId, siblingId, sourceTree, targetTree) {
    if (sourceTree.rootId==2) return true;
    return false;
  }
  function doOnLoad(){
    tree1=new dhtmlXTreeObject('treebox1',"100%","100%",1);
    tree1.setImagePath("<html:rewrite page='/images/dhtmlXTree/'/>");
    tree1.enableDragAndDrop(true);
    tree1.setDragHandler(myDrag1Handler);
    tree1.setStdImages('book.gif','books_open.gif','books_close.gif');
    <%
      org.pgist.cvo.CVOForm cvoForm = (org.pgist.cvo.CVOForm) request.getAttribute("cvoForm");
      out.print(cvoForm.getCategory().getJavascript("tree1"));
    %>
    tree2=new dhtmlXTreeObject('treebox2',"100%","100%",2);
    tree2.setImagePath("<html:rewrite page='/images/dhtmlXTree/'/>");
    tree2.enableDragAndDrop(true);
    tree2.setDragHandler(myDrag2Handler);
    tree2.setStdImages('book.gif','books_open.gif','books_close.gif');
    tree2.insertNewItem(2,-1,'New Tag',0,0,0,0,'');
    tree3=new dhtmlXTreeObject('treebox3',"100%","100%",3);
    tree3.setImagePath("<html:rewrite page='/images/dhtmlXTree/'/>");
    tree3.enableDragAndDrop(true);
    tree3.setDragHandler(myDrag3Handler);
    tree3.setStdImages('book.gif','books_open.gif','books_close.gif');
    tree3.insertNewItem(3,1,'Tag1',0,0,0,0,'');
    tree3.insertNewItem(3,2,'Tag2',0,0,0,0,'');
    tree3.insertNewItem(3,3,'Tag3',0,0,0,0,'');
    tree3.insertNewItem(3,4,'Tag4',0,0,0,0,'');
  }
  function createCategory() {
    tree1.insertNewItem(1,100,$('catName').value,0,0,0,0,'');
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

