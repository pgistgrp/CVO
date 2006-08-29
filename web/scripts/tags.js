// JavaScript Document for the Tag Selector
		function expandTagSelector(){
				$('tagSelector').style.position = "absolute";		
				$('tagSelector').style.border = "1px solid #CCCCCC";
				$('tagSelector').style.width = (500) + "px";
				$('tagSelector').style.margin = "0";
				$('pullDown').style.display = 'none';
				$('tagSelector_spacer').style.display = 'inline';
				$('tagSelector_spacer').style.visibility = 'hidden';
				$('tagform').style.display = 'none';
			
				new Effect.Grow('allTags', {duration: 0.6, direction: 'top-left', beforeUpdate: function(){getTagCloud();}}); 
				
				}
		function shrinkTagSelector(){
				new Effect.Shrink('allTags', {duration: 0.6, direction: 'top-right', afterFinish: function(){$('tagSelector').style.width = "";$('tagSelector').style.position = "";$('tagSelector').style.borderwidth = "";$('pullDown').style.display = 'inline';$('tagSelector_spacer').style.visibility = 'visible'; $('tagSelector_spacer').style.display = 'none';$('tagform').style.display = 'inline';}}); 			
				
		}
	