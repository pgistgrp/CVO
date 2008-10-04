var tree1 = {
  selectedId : null,
  select : function(id) {
    displayIndicator(true);
    var current = this.selectedId;
    VTTAgent.getExpertUnitSet({targetUserId:targetUserId, pathId:id}, <pg:wfinfo/>,{
      callback:function(data){
          if (data.successful){
              displayIndicator(false);
              $("col-right").innerHTML = data.html;
              
              if (current!=null) {
                $('row-'+current).className = "catUnSelected";
              }
              tree1.selectedId = id;
              currentCategory = $('col-'+tree1.selectedId).innerHTML;
              $('row-'+tree1.selectedId).className = "catSelected";
          }else{
              displayIndicator(false);
              alert(data.reason);
          }
      },
      errorHandler:function(errorString, exception){ 
          alert("get comments error: " + errorString +" "+ exception);
      }
    });
  }
};

