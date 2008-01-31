InfoObject.prototype.getConcerns = function(page, filter) {
  displayIndicator(true);
  BCTAgent.getConcerns({bctId: this.targetId, filter: this.currentFilter, page: page, sorting: this.currentSort, type:'all', contextAware:'true'}, this.wfinfo,{
      callback:function(data){
          displayIndicator(false);
          if (data.successful){
              $('concernsPanel').innerHTML = data.html;
          }else{
              alert(data.reason);
          }
      },
      errorHandler:function(errorString, exception){ 
          alert("get concerns error: " + errorString +" "+ exception);
      }
  });
};

InfoObject.prototype.getTags = function(page) {
  $('tagsPanel').innerHTML = 'bbbbb';
};

InfoObject.prototype.loadTarget = function() {
  this.getConcerns(1);
  this.getTags(1);
};

