// JavaScript Document
function moreDiscussion(){
		new Effect.BlindDown('discussion', {duration: 0.5, scaleFrom:100.0, scaleTo:170.0, afterFinish: function(){
				$('discussion').style.height = '40%';
			}});
		
		new Effect.BlindUp('object', {duration: 0.5, scaleFrom:100.0, scaleTo:54.0, afterFinish: function(){
				$('object').style.height = '25%';
				$('object').style.display = 'block';
			}});
		$('toggle').innerHTML = '<a href="javascript:moreObject();"><img src="images/slideDown.gif" border="0" alt="Less Discussion Space!"></a>';
	}
	
	function moreObject(){
		new Effect.BlindDown('object', {duration: 0.5, scaleFrom:100.0, scaleTo:170.0, afterFinish: function(){
				$('object').style.height = '40%'; 
			}});
	
		new Effect.BlindUp('discussion', {duration: 0.5, scaleFrom:100.0, scaleTo:54.0, afterFinish: function(){
				$('discussion').style.height = '25%';
				$('discussion').style.display = 'block';
			}});
		$('toggle').innerHTML = '<a href="javascript:moreDiscussion();"><img src="images/slideUp.gif" border="0" alt="More Discussion Space!"></a>';
		
	}