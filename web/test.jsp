<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<html:html>
<head>
<title>Test</title>
<script type='text/javascript' src='<html:rewrite page="/scripts/prototype.js"/>'></script>
<script type='text/javascript' src='<html:rewrite page="/scripts/rico.js"/>'></script>
<style>
div.panel {
   width : 200px;
   height : 80px;
   padding : 2px;
   border : 1px solid #5b5b5b;
}
div.box {
   font-family : Arial;
   font-size : 14px;
   width : 100px;
   height : 40px;
   cursor : hand; cursor:pointer;
   text-align : center;
   border-bottom : 1px solid #6b6b6b;
   border-right : 1px solid #6b6b6b;
}
div.title {
   font-family : Arial;
   font-size : 10px;
   background-color : #797979;
   color : #ffffff;
   width : 200px;
   margin : 1px;
}
div.simpleDropPanel {
   width : 200;
   height : 80px;
   padding : 2px;
   border : 1px solid #5b5b5b;
}
</style>
</head>

<body bgcolor="white">

<div style="padding:5px;border:1px solid #5b5b5b;height:50px">
<div class="box" style="background:#f7a673" id="dragme">Drag Me</div>
</div>

<br/>

<table style="margin-bottom:8px" cellspacing="3" cellpadding="3">
<tr>
   <td>
      <div id="droponme" class="simpleDropPanel" style="background:#ffd773">
         <div class="title">Drop On Me</div>
      </div>
    </td>
    <td>
      <div id="droponme2" class="simpleDropPanel" style="background:#c6c3de">
         <div class="title">Drop On Me 2</div>
      </div>
	 </td>
</tr>
</table>

<script>
  dndMgr.registerDraggable( new Rico.Draggable('test-rico-dnd','dragme') );
  dndMgr.registerDropZone( new Rico.Dropzone('droponme') );
  dndMgr.registerDropZone( new Rico.Dropzone('droponme2') );
</script>

</body>
</html:html>

