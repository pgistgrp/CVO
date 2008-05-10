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
  showCompare : function(selectedId) {
    var table = $('catTable');
    for (var i=0; i<table.rows.length; i++) {
      var row = table.rows[i];
      if (row.id=='catRow-'+selectedId) {
        $('catcmp-'+selectedId).style.display = 'none';
      } else {
        $('catcmp-'+row.id.substr(7)).style.display = 'inline';
      }
    }
  },
  category0Clicked : function (catId) {
    //if (catId==this.selectedId1) return;
    if (catId==this.selectedId1) {
      this.selectedId1 = null;
      $('catBottom').innerHTML = '';
      $('tagsBottom').innerHTML = '';
    }
    var selectedId0 = this.selectedId0;
    //load tags
    CSTAgent.getRTags(
      {cstId:infoObject.targetId, categoryId:catId},
      {
        callback:function(data){
            if (data.successful){
              $('catTop').innerHTML = data.catRef.category.name;
              var s = '<table>';
              for (var i=0; i<data.tags.length; i++) {
                if (s!='') s += '<tr><td>';
                s += data.tags[i].tag.name;
                s += '</td></tr>';
              }
              $('tagsTop').innerHTML = s;
              if (selectedId0!=null) {
                $('catRow-'+selectedId0).style.backgroundColor='';
              }
              $('catRow-'+catId).style.backgroundColor='#D6E7EF';
              catTree.selectedId0 = catId;
              catTree.showCompare(catId);
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
    var selectedId1 = this.selectedId1;
    //load tags
    CSTAgent.getRTags(
      {cstId:infoObject.targetId, categoryId:catId},
      {
        callback:function(data){
            if (data.successful){
              $('catBottom').innerHTML = data.catRef.category.name;
              var s = '<table>';
              for (var i=0; i<data.tags.length; i++) {
                if (s!='') s += '<tr><td>';
                s += data.tags[i].tag.name;
                s += '</td></tr>';
              }
              $('tagsBottom').innerHTML = s;
              if (selectedId1!=null) {
                $('catRow-'+selectedId1).style.backgroundColor='';
              }
              $('catRow-'+catId).style.backgroundColor='#FFF1DC';
              catTree.selectedId1 = catId;
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

