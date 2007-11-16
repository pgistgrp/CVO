// JavaScript Document for the Tag Selector
		function expandTagSelector(){
				$('tagSelector').style.position = "absolute";		
				$('tagSelector').style.border = "1px solid #CCCCCC";
				
				$('tagSelector').style.width = (windowWidth * .75) + "px";
				new Effect.Grow('allTags', {duration: 0.6, direction: 'top-right'}); 
				$('pullDown').innerHTML ='<a href="javascript: shrinkTagSelector();">Uh I take it back</a>';
				}
		function shrinkTagSelector(){
				new Effect.Shrink('allTags', {duration: 0.6, direction: 'top-right', afterFinish: function(){$('tagSelector').style.width = "";$('tagSelector').style.position = "";$('tagSelector').style.borderwidth = "";}}); 
				$('pullDown').innerHTML = $('pullDown').innerHTML = '<a href="javascript: expandTagSelector();">Pull My Finger</a>';
				
				
		}
	