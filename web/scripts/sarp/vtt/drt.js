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
  
  VTTAgent.saveSelection({pathId:pathId, unit:selection}, {
    callback:function(data){
        if (data.successful){
            tree1.select(pathId);
        }else{
            alert(data.reason);
            $('btnSave').disabled = false;
        }
        displayIndicator(false);
    },
    errorHandler:function(errorString, exception){ 
        alert("get comments error: " + errorString +" "+ exception);
    }
  });
}
