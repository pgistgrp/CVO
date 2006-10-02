// JavaScript Document for the Tag Selector
		function expandTagSelector(){
				$('tagSelector').style.position = "absolute";		
				$('tagSelector').style.border = "1px solid #CCCCCC";
				$('tagSelector').style.width = (500) + "px";
				$('tagSelector').style.margin = "0";
				$('tagform').style.display = 'none';
			
				new Effect.Grow('allTags', {duration: 0.6, direction: 'top-left', beforeUpdate: function(){getTagCloud();}}); 
				
				}
		function shrinkTagSelector(){
			if($('allTags').style.display != 'none'){
				new Effect.Shrink('allTags', {duration: 0.2, direction: 'top-right', afterFinish: function(){$('tagSelector').style.width = "";$('tagSelector').style.position = "";$('tagSelector').style.borderwidth = ""; $('tagform').style.display = 'inline';}}); 			
			}
		}
	