if (infoObject) {
  if (infoObject.setExitCondition) {
    var old_setExitCondition = infoObject.setExitCondition;
    infoObject.setExitCondition = function(element, exitNow) {
      if (exitNow=='true') {
        CHTAgent.getWinner({chtId:infoObject.targetId}, {
            callback:function(data){
                if (data.successful){
                  if (data.winnerUser) {
                    old_setExitCondition(element, exitNow);
                  } else {
                    alert('You have to select one final version of cateogry before going forward!');
                  }
                }else{
                  alert(data.reason);
                }
            },
            errorHandler:function(errorString, exception){ 
                alert("get winner error: " + errorString +" "+ exception);
            }
        });
      } else {
        old_setExitCondition(element, exitNow);
      }
    }
  }
}

infoObject.loadTarget = function() {
};

var catTree = {
  selectedId0 : null,
  category0Clicked : function (catId) {
    if (catId==this.selectedId0) return;
    var catTree = this;
    //load tags
    CHTAgent.getRTags(
      {chtId:infoObject.targetId, categoryId:catId},
      {
        callback:function(data){
            if (data.successful){
              $('catName').innerHTML = data.catRef.category.name;
              var s = '<table>';
              for (var i=0; i<data.tags.length; i++) {
                if (s!='') s += '<tr><td>';
                s += data.tags[i].tag.name;
                s += '</td></tr>';
              }
              $('tags').innerHTML = s;
              if (catTree.selectedId0!=null) {
                $('catRow-'+catTree.selectedId0).style.backgroundColor='';
              }
              $('catRow-'+catId).style.backgroundColor='#D6E7EF';
              catTree.selectedId0 = catId;
            }else{
              alert(data.reason);
            }
        },
        errorHandler:function(errorString, exception){ 
            alert("get tags error: " + errorString +" "+ exception);
        }
      }
    );
  }
}

