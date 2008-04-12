if (infoObject) {
  if (infoObject.setExitCondition) {
    var old_setExitCondition = infoObject.setExitCondition;
    infoObject.setExitCondition = function(element, exitNow) {
      if (exitNow=='true') {
        CSTAgent.getWinner({cstId:infoObject.targetId}, {
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
  selectedId1 : null,
  category0Clicked : function (catId) {
    if (catId==this.selectedId1) return;
    if (this.selectedId0!=null) {
      $('cat-'+this.selectedId0).style.backgroundColor='';
    }
    $('cat-'+catId).style.backgroundColor='#D6E7EF';
    this.selectedId0 = catId;
    //load tags
    CSTAgent.getRTags(
      {cstId:infoObject.targetId, categoryId:catId},
      {
        callback:function(data){
            if (data.successful){
              var s = '';
              for (var i=0; i<data.tags.length; i++) {
                if (s!='') s += ', ';
                s += data.tags[i].tag.name;
              }
              $('tagsTop').innerHTML = s;
            }else{
              alert(data.reason);
            }
        },
        errorHandler:function(errorString, exception){ 
            alert("get tags error: " + errorString +" "+ exception);
        }
      }
    );
  },
  category1Clicked : function (catId) {
    if (catId==this.selectedId0) return;
    if (this.selectedId1!=null) {
      $('cat-'+this.selectedId1).style.backgroundColor='';
    }
    $('cat-'+catId).style.backgroundColor='#FFF1DC';
    this.selectedId1 = catId;
    //load tags
    CSTAgent.getRTags(
      {cstId:infoObject.targetId, categoryId:catId},
      {
        callback:function(data){
            if (data.successful){
              var s = '';
              for (var i=0; i<data.tags.length; i++) {
                if (s!='') s += ', ';
                s += data.tags[i].tag.name;
              }
              $('tagsBottom').innerHTML = s;
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

