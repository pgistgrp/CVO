<div id="col-left">
  <div id="topMenu" style="clear:both;">
    <pg:show condition="${user.id==baseuser.id && !cst.closed}">
    <input type="text" id="newcatetext" onkeydown="checkaddcategory(event)">
    <input type="button" id="addCat" value="Add Category" onclick="addcategory();">
    </pg:show>
  </div>
  <div id="cats" style="height:450px;overflow:auto;" onclick="unselectall(!tree1.clickedOn);"></div>
</div>

<div id="col"></div>

<div style="clear:both"></div>
<pg:show condition="${user.id==baseuser.id && !cst.closed}">
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

