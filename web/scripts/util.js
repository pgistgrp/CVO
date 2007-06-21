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
//If there is a hidden label that appears, give it the class 'hiddenLabel' or whatever is specified in Util.hiddenLabel
Util.plusIcon = "/images/plus.gif";
Util.minusIcon = "/images/minus.gif";
Util.hiddenLabelClassName = "hiddenLabel";

Util.hiddenLabels = function(show){
	hiddenLabels = document.getElementsByClassName(this.hiddenLabelClassName);
	for(i=0;i<hiddenLabels.length;i++){
		(show) ? hiddenLabels[i].show() : hiddenLabels[i].hide();
	}
}

Util.toggleRow = function(index){
	row = 'row' + index;
	icon =  'icon' + index;
	Effect.toggle(row, 'blind', {duration:.1, afterFinish:
		function(){
			($(row).visible()) ? Util.hiddenLabels(true) : Util.testOpenRows(row);
			$(icon).src = ($(row).visible()) ?  Util.minusIcon :  Util.plusIcon;;
		}
	});
}

Util.testOpenRows = function(row){
	rows = document.getElementsByClassName($(row).className);

	show = false;
	for (var i=0;i<rows.length; i++){
		show = (rows[i].visible()) ? true : false;
		if(show){break;}
	}
	(show) ?  Util.hiddenLabels(true) : Util.hiddenLabels(false);
}

Util.expandAll = function(rowClass){
	var rows = document.getElementsByClassName(rowClass);
	Util.hiddenLabels(true);
	for (var i=0;i<rows.length; i++){
		var row = 'row' + i;
		var icon = 'icon' + i;
		Effect.BlindDown(row, {duration:.1});
		$(icon).src = Util.minusIcon;
	}
}

Util.collapseAll = function(rowClass){
	var rows = document.getElementsByClassName(rowClass);
	Util.hiddenLabels(false);
	for (var i = 0;i < rows.length; i++){
		var row = 'row' + i;
		var icon = 'icon' + i;
		Effect.BlindUp(row, {duration:.1});
		$(icon).src = Util.plusIcon;
	}
}

