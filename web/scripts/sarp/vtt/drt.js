var tree1 = {
  selectedId : null,
  select : function(id) {
    displayIndicator(true);
    var current = this.selectedId;
    VTTAgent.getPathStats({pathId:id}, {
      callback:function(data){
          if (data.successful){
              $("col-right").innerHTML = data.html;
              
              if (current!=null) {
                $('row-'+current).className = "catUnSelected";
              }
              tree1.selectedId = id;
              currentCategory = $('col-'+tree1.selectedId).innerHTML;
              $('row-'+tree1.selectedId).className = "catSelected";
          }else{
              alert(data.reason);
          }
          displayIndicator(false);
      },
      errorHandler:function(errorString, exception){ 
          alert("get comments error: " + errorString +" "+ exception);
      }
    });
  }
};

infoObject.loadTarget = function() {
};

function saveUnitSelection(pathId) {
  $('btnSave').disabled = true;
  
  var userSels = document.mainForm.userSel;
  var selection = '';
  for (var i=0; i<userSels.length; i++) {
    if (userSels[i].checked) {
      selection = userSels[i].value;
      break;
    }
  }
  
  musetId = 0;
  unit = '';
  if (selection.length>0) {
    var index = selection.indexOf('-');
    musetId = selection.substr(0, index);
    unit = selection.substr(index+1);
  }
  
  VTTAgent.saveSelection({pathId:pathId, musetId:musetId, unit:unit}, {
    callback:function(data){
        if (data.successful){
            tree1.select(pathId);
            alert("saved.");
        }else{
            alert(data.reason);
            $('btnSave').disabled = false;
        }
        displayIndicator(false);
    },
    errorHandler:function(errorString, exception){ 
        alert("save selection error: " + errorString +" "+ exception);
    }
  });
}

function changeSorting(order) {
  VTTAgent.getRawPaths({vttId:infoObject.targetId, sorting:order}, {
  callback:function(data){
    if (data.successful){
      $('col-left').innerHTML = data.html;
    }else{
      alert(data.reason);
      $('publishBtn').disabled=false;
    }
  },
  errorHandler:function(errorString, exception){ 
      alert("publish error:" + errorString + exception);
  }
  });
}

