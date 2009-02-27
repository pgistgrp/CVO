if (typeof(infoObject)=='undefined') {
  infoObject = {};
}

infoObject.pathIds = [];

infoObject.addToPath = function(refId, refName) {
  this.pathIds[this.pathIds.length] = refId;
  if ($('newPath').value.length>0) {
    $('newPath').value += '/';
  }
  $('newPath').value += refName;
  $('li-'+refId).hide();
  $('lihide-'+refId).show();
};

infoObject.clearPath = function() {
  $('newPath').value = '';
  var elements = document.getElementsByTagName('li');
  for (var i=0; i<elements.length; i++) {
    if (elements[i].id.substr(0, 3)=='li-') {
      elements[i].show();
    } else if (elements[i].id.substr(0, 7)=='lihide-') {
      elements[i].hide();
    }
  }
  infoObject.pathIds = [];
};

infoObject.addPath = function() {
  if (infoObject.pathIds.length==0) return;
  CHTAgent.addPath({chtId:infoObject.targetId, ids:this.pathIds.join()},{
  callback:function(data){
    if (data.successful){
      infoObject.clearPath();
      CHTAgent.getPaths({chtId:infoObject.targetId}, {
        callback:function(data){
          if (data.successful){
            $('pathsPanel').innerHTML = data.html;
          } else {
            alert(data.reason);
          }
        },
        errorHandler:function(errorString, exception){
          alert("getPaths error: "+errorString+" "+exception);
        }
      });
    } else {
      alert(data.reason);
    }
  },
  errorHandler:function(errorString, exception){
    alert("addPath error: "+errorString+" "+exception);
  }
});
};

