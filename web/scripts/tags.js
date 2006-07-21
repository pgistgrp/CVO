// JavaScript Document for the Tag Selector
		function expandTagSelector(){
				$('tagSelector').style.position = "absolute";		
				$('tagSelector').style.border = "1px solid #CCCCCC";
				$('tagSelector').style.width = (windowWidth * .75) + "px";
				$('manualFilter').style.display = 'none';
				
				new Effect.Grow('allTags', {duration: 0.6, direction: 'top-right', beforeUpdate: function(){getTagCloud();}}); 
				
				}
		function shrinkTagSelector(){
				new Effect.Shrink('allTags', {duration: 0.6, direction: 'top-right', afterFinish: function(){$('tagSelector').style.width = "";$('tagSelector').style.position = "";$('tagSelector').style.borderwidth = "";$('manualFilter').style.display = 'inline';}}); 			
				
		}
	