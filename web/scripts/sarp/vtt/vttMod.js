var tree1 = {
  selectedId : null,
  select : function(id) {
    displayIndicator(true);
    var current = this.selectedId;
    
    VTTAgent.getPathInfo({pathId:id}, {
      callback:function(data){
        if (data.successful){
          displayIndicator(false);
          $('pathInfo').innerHTML = data.html;
          if (current!=null) {
            $('row-'+current).className = "catUnSelected";
          }
          tree1.selectedId = id;
          currentCategory = $('col-'+tree1.selectedId).innerHTML;
          $('row-'+tree1.selectedId).className = "catSelected";
        } else {
          alert(data.reason);
        }
      },
      errorHandler:function(errorString, exception){
        alert("getPaths error: "+errorString+" "+exception);
      }
    });
  },
  deletePath : function(vttId, pathId) {
    if (!confirm('Are your sure to delete this path? Action can not be undone.')) return;
    VTTAgent.deletePath({vttId:vttId, pathId: pathId},{
      callback:function(data){
        if (data.successful){
          tree1.selectedId = null;
          $('pathInfo').innerHTML = 'No path selected.';
          VTTAgent.getPaths({vttId:infoObject.targetId}, {
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
  },
  saveUnit : function() {
    var indicator = $('indicator').value;
    if (indicator=='') {
      alert('please input indicator');
      return;
    }
    var measurement = $('measurement').value;
    if (measurement=='') {
      alert('please input suggested unit of measurement');
      return;
    }
    VTTAgent.saveUnit({pathId: tree1.selectedId, indicator:indicator, measurement:measurement, appr:$('appr').checked, avail:$('avail').checked, dup:$('dup').checked, reco:$('reco').checked},{
      callback:function(data){
        if (data.successful){
          alert('Saved!');
        } else {
          alert(data.reason);
        }
      },
      errorHandler:function(errorString, exception){
        alert("addPath error: "+errorString+" "+exception);
      }
    });
  }
};

function toggleNewPathPanel() {
  $('newPathPanel').toggle();
}

infoObject = {};

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
  VTTAgent.addPath({vttId:infoObject.targetId, ids:this.pathIds.join()},{
  callback:function(data){
    if (data.successful){
      infoObject.clearPath();
      VTTAgent.getPaths({vttId:infoObject.targetId}, {
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

infoObject.addIndicator = function(pathId) {
  s = prompt('indicator name');
  if (s=='') return;
  
  VTTAgent.addIndicator({pathId:pathId, indicator:s}, {
    callback:function(data){
      if (data.successful){
        tree1.select(pathId);
      } else {
        alert(data.reason);
      }
    },
    errorHandler:function(errorString, exception){
      alert("addIndicator error: "+errorString+" "+exception);
    }
  });
}

infoObject.deleteIndicator = function(pathId, musetId) {
  if (!confirm('Are your sure to delete this indicator?')) return;
  
  VTTAgent.deleteIndicator({musetId:musetId}, {
    callback:function(data){
      if (data.successful){
        tree1.select(pathId);
      } else {
        alert(data.reason);
      }
    },
    errorHandler:function(errorString, exception){
      alert("deleteIndicator error: "+errorString+" "+exception);
    }
  });
}

infoObject.addUnit = function(pathId, musetId) {
  s = prompt('unit name');
  if (s=='') return;
  
  VTTAgent.addUnit({musetId:musetId, unit:s}, {
    callback:function(data){
      if (data.successful){
        tree1.select(pathId);
      } else {
        alert(data.reason);
      }
    },
    errorHandler:function(errorString, exception){
      alert("addUnit error: "+errorString+" "+exception);
    }
  });
}

infoObject.deleteUnit = function(pathId, musetId, unit) {
  if (!confirm('Are your sure to delete this unit?')) return;
  
  VTTAgent.deleteUnit({musetId:musetId, unit:unit}, {
    callback:function(data){
      if (data.successful){
        tree1.select(pathId);
      } else {
        alert(data.reason);
      }
    },
    errorHandler:function(errorString, exception){
      alert("deleteUnit error: "+errorString+" "+exception);
    }
  });
}

