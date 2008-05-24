if (typeof(infoObject)=='undefined') {
  infoObject = {};
}

infoObject.loadTarget = function() {
  tree1=new dhtmlXTreeObject("cats","100%","100%",0);
  tree1.setImagePath("/images/dhtmlXTree/");
  tree1.enableCheckBoxes(false);
  tree1.enableThreeStateCheckboxes(true);
  tree1.loadXML("/tctTree.do?oId="+this.oid);
  tree1.setOnClickHandler(treeClickHandler);
};

function treeClickHandler(clickid, lastid, labeltext) {
  document.getElementById("selcatetext").value = labeltext;
  currentCategory = tree1.lastSelected.parentObject;
  tempcate = tree1.getTopLevelNode(currentCategory);
  
  getTags(clickid);
  
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
    $('col-option').style.display = "none";
  }
  tree1.clickedOn = false;
}

var relatedTagsArr = [];
function getTags(categoryId){
  Util.loading(true,"Working");
  DRTAgent.getCategoryTags({categoryId:categoryId}, {
  callback:function(data){
    if (data.successful){
      document.getElementById('col').innerHTML = '<h4>Tags within "' + currentCategory.label + '"</h4>';
      document.getElementById('col').innerHTML += data.html;
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

