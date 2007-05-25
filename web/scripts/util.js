Util = new Object;

Util.loading = function(show, message){
	i = $('loading-indicator');
	el = $('container');
	mg = (message) ? message : "Loading";
	html = "<img src='/images/indicator_arrows.gif' alt='please wait...'/>"+mg+"..."
	if(show){
		if(i){
			i.show();
			i.innerHTML=html;
		}else{
			new Insertion.Before(el,"<div id='loading-indicator'>"+html+"</div>")
		}
	}else{
		if(i){
			i.hide();
		}
	}
}