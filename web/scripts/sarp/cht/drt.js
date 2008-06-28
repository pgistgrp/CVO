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
            $("pathnum-"+pathId).innerHTML = data.numAgree+'/'+data.numVote;
            $("pathvot-"+pathId).innerHTML = '<img src="images/btn_thumbsdown_off.png" alt="Disabled Button"/> <img src="images/btn_thumbsup_off.png" alt="Disabled Button"/>';
          }else{
            alert(data.reason);
          }
      },
      errorHandler:function(errorString, exception){ 
          alert("get winner error: " + errorString +" "+ exception);
      }
  });
};

