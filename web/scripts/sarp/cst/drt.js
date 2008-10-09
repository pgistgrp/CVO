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
              $('catTop').innerHTML = data.catRef.catRef.category.name;
              var s = '<table>';
              
              // alias
              var size = 0;
              var ss = '';
              if (data.catRef.alias) {
                for (var a in data.catRef.alias) {
                  ss += a+' ('+data.catRef.alias[a]+')<br>';
                  size++;
                }
              }
              if (size>0) {
                s += '<tr><td align="right" valign="top"><b>aliases:</b></td><td align="left" valign="top">'+ss+'</td></tr>';
              } else {
                s += '<tr><td align="right"><b>aliases:</b></td><td align="left">none</td></tr>';
              }
              
              s += '<tr><td align="right"><b>freq set:</b></td><td align="left">'+data.catRef.freqSet+'</td></tr>';
              s += '<tr><td align="right"><b>freq name:</b></td><td align="left">'+data.catRef.freqName+'</td></tr>';
              s += '<tr><td align="right"><b>freq name and set:</b></td><td align="left">'+data.catRef.freqNameAndSet+'</td></tr>';
              
              //tags
              size = 0;
              ss = '';
              if (data.tags) {
                for (var i=0; i<data.tags.length; i++) {
                  ss += data.tags[i].tag.name+'<br>';
                  size++;
                }
              }
              if (size>0) {
                s += '<tr><td align="right" valign="top"><b>tags:</b></td><td align="left" valign="top">'+ss+'</td></tr>';
              } else {
                s += '<tr><td align="right"><b>tags:</b></td><td align="left">none</td></tr>';
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
              $('catBottom').innerHTML = data.catRef.catRef.category.name;
              var s = '<table>';
              
              // alias
              var size = 0;
              var ss = '';
              if (data.catRef.alias) {
                for (var a in data.catRef.alias) {
                  ss += a+' ('+data.catRef.alias[a]+')<br>';
                  size++;
                }
              }
              if (size>0) {
                s += '<tr><td align="right" valign="top"><b>aliases:</b></td><td align="left" valign="top">'+ss+'</td></tr>';
              } else {
                s += '<tr><td align="right"><b>aliases:</b></td><td align="left">none</td></tr>';
              }
              
              s += '<tr><td align="right"><b>freq set:</b></td><td align="left">'+data.catRef.freqSet+'</td></tr>';
              s += '<tr><td align="right"><b>freq name:</b></td><td align="left">'+data.catRef.freqName+'</td></tr>';
              s += '<tr><td align="right"><b>freq name and set:</b></td><td align="left">'+data.catRef.freqNameAndSet+'</td></tr>';
              
              //tags
              size = 0;
              ss = '';
              if (data.tags) {
                for (var i=0; i<data.tags.length; i++) {
                  ss += data.tags[i].tag.name+'<br>';
                  size++;
                }
              }
              if (size>0) {
                s += '<tr><td align="right" valign="top"><b>tags:</b></td><td align="left" valign="top">'+ss+'</td></tr>';
              } else {
                s += '<tr><td align="right"><b>tags:</b></td><td align="left">none</td></tr>';
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

