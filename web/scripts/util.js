Util = new Object;

Util.loading = function(show, message){
	i = $('loading-indicator');
	el = document.body;
	mg = (message) ? message : "Loading";
	html = "<img src='/images/indicator_arrows.gif' alt='please wait...'/>"+mg+"..."
	if(show){
		if(i){
			i.show();
			i.innerHTML=html;
		}else{
			new Insertion.Top(el,"<div id='loading-indicator'>"+html+"</div>")
		}
	}else{
		if(i){
			setTimeout(function() {Element.hide(i)}, 200);
		}
	}
}

//Toggling functions use naming conventions.  The toggling row must have the id 'rowXX' and the icon must be 'iconXX'
//If there is a hidden label that appears, call it 'hiddenLabel' or whatever is specified in Util.hiddenLabel
Util.plusIcon = "/images/plus.gif";
Util.minusIcon = "/images/minus.gif";
Util.hiddenLabel = "hiddenLabel";

Util.toggleRow = function(index){
	row = 'row' + index;
	icon =  'icon' + index;
	Effect.toggle(row, 'blind', {duration:.1, afterFinish:
		function(){
			if ($(row).visible()){
					$(icon).src = Util.plusIcon;
					if($(Util.hiddenLabel)){ $(Util.hiddenLabel).show(); }
				}else{
					$(icon).src = Util.minusIcon;
					Util.testOpenRows(row);
				}
			}
	});
}

Util.testOpenRows = function(row){
	var rows = document.getElementsByClassName($(row).className);
	for (var i=0;i<rows.length; i++){
		($(row).visible()) ? $(Util.hiddenLabel).show() : $(Util.hiddenLabel).hide();
	}
}

Util.expandAll = function(rowClass){
	var rows = document.getElementsByClassName(rowClass);
	$(Util.hiddenLabel).show();
	for (var i=0;i<rows.length; i++){
		var row = 'row' + i;
		var icon = 'icon' + i;
		Effect.toggle(row, 'blind', {duration:.1});
		$(icon).src = Util.minusIcon;
	}
}

Util.collapseAll = function(rowClass){
	var rows = document.getElementsByClassName(rowClass);
	$(Util.hiddenLabel).hide();
	for (var i = 0;i < rows.length; i++){
		var row = 'row' + i;
		var icon = 'icon' + i;
		$(row).hide();
		$(icon).src = Util.plusIcon;
	}
}

