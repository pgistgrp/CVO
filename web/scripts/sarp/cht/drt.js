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

infoObject.setVotingOnPath = function(pathId, agree) {
  CHTAgent.setVotingOnPath({pathId:pathId, agree:agree}, {
      callback:function(data){
          if (data.successful){
            $('catRow-'+pathId).innerHTML = data.pathLine;
          }else{
            alert(data.reason);
          }
      },
      errorHandler:function(errorString, exception){ 
          alert("get winner error: " + errorString +" "+ exception);
      }
  });
};

