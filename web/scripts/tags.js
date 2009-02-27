// JavaScript Document for the Tag Selector
		var currentShowAllLink = "";
		function expandTagSelector(){
				$('tagSelector').style.position = "absolute";		
				$('tagSelector').style.border = "1px solid #CCCCCC";
				$('tagSelector').style.width = (500) + "px";
				$('tagSelector').style.margin = "0";
				$('tagform').style.display = 'none';
				
				new Effect.BlindDown('allTags', {duration: 0.2,  beforeUpdate: function(){sideBar.getTagCloud();}}); 
				if($('showAllLink') != undefined){
					currentShowAllLink = $('showAllLink').style.display;
					$('showAllLink').style.display = 'none';
				}
				}
		function shrinkTagSelector(){
			if($('allTags').style.display != 'none'){
				new Effect.BlindUp('allTags', {duration: 0.2, afterFinish: function(){$('tagSelector').style.width = "";$('tagSelector').style.position = "";$('tagSelector').style.borderwidth = ""; $('tagform').style.display = 'inline';}}); 			
				if($('showAllLink') != undefined){
				$('showAllLink').style.display = currentShowAllLink;
				}
			}
		}
	