<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>PGIST Portal - Let's Improve Transportation</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<style type="text/css" media="screen">@import "/styles/tabs.css";</style>
<style type="text/css" media="screen">@import "/styles/pgist.css";</style>
<script src="/scripts/prototype.js" type="text/javascript"></script>
<script src="/scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>

<script type="text/javascript">
	
	window.onresize=message;
	function message() {
	  alert("The window has been resized!");
	}
	function moreDiscussion(){
		new Effect.BlindDown('testDiscussion', {duration: 0.5, scaleFrom:100.0, scaleTo:170.0, afterFinish: function(){
				$('testDiscussion').style.height = '275px';
			}});
		
		new Effect.BlindUp('testSlate', {duration: 0.5, scaleFrom:100.0, scaleTo:54.0, afterFinish: function(){
				$('testSlate').style.height = '150px';
				$('testSlate').style.display = 'block';
			}});
		$('toggle').innerHTML = '<a href="javascript:moreObject();"><img src="/images/slideDown.gif" border="0" alt="Less Discussion Space!"></a>';
	}
	
	function moreObject(){
		new Effect.BlindDown('testSlate', {duration: 0.5, scaleFrom:100.0, scaleTo:170.0, afterFinish: function(){
				$('testSlate').style.height = '275px'; 
			}});
	
		new Effect.BlindUp('testDiscussion', {duration: 0.5, scaleFrom:100.0, scaleTo:54.0, afterFinish: function(){
				$('testDiscussion').style.height = '150px';
				$('testDiscussion').style.display = 'block';
			}});
		$('toggle').innerHTML = '<a href="javascript:moreDiscussion();"><img src="/images/slideUp.gif" border="0" alt="More Discussion Space!"></a>';
		
	}

</script>

<style type="text/css">
#testSlate{border: 1px solid #ccc; border-top: 0px solid #fff; width: 600px; margin: auto; height: 275px; overflow: auto; padding: 10px; padding-top: 0px;}
#testDiscussion{border: 1px solid #ccc; border-top: 0px solid #fff; width:550px; margin: auto; height: 150px; overflow: auto; padding: 10px; padding-top: 0px; }
#toggle{border-left: 1px solid #ccc;border-right: 1px solid #ccc; margin: auto; background: #eee; width: 570px; background: #eee; text-align: center;}
.divHeader{border-left: 1px solid #ccc;border-right: 1px solid #ccc; margin: auto; margin-left: auto; margin-right: auto; background: #fff; padding: 10px; padding-top: 5px;width: 550px;}
</style>
<event:pageunload />
</Head>
<body>
<div id="header_object" class="divHeader" style="width: 600px; border-top: 1px solid #ccc;"><h1>The Object.</h1></div>
<div id="testSlate">
	<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Morbi blandit, lectus sed semper semper, elit eros luctus metus, a euismod lacus orci quis libero. Mauris fringilla. Integer enim tellus, rhoncus eu, fringilla ac, aliquam a, sapien. Mauris tempor. In egestas lorem nec turpis. Morbi felis. Nunc hendrerit neque vitae urna. Donec bibendum. Ut non pede. In tristique, dui eget fermentum ornare, nibh ipsum molestie odio, vitae suscipit nibh lectus vel dui.</p>
	<p>Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. In nisi velit, porta id, accumsan et, mollis quis, risus. Vivamus vel orci id nulla lacinia lacinia. Duis suscipit pulvinar diam. Mauris lectus risus, scelerisque sed, varius ac, aliquam eu, metus. Nam adipiscing magna. Donec imperdiet. Nulla quam nulla, accumsan quis, varius tempus, condimentum ut, magna. Proin malesuada, felis id rhoncus interdum, augue sem convallis erat, ac vulputate nisi neque quis sem. Praesent dictum tellus sed velit. Aliquam eleifend, dolor vitae volutpat semper, nulla nunc interdum nisi, vel tempus lorem urna sed lectus. Morbi porta convallis augue. Proin posuere magna eget lectus. Nunc justo. Pellentesque vitae ante. Sed est justo, pretium eget, ultrices et, pretium eget, odio.</p>
</div>
<div id="toggle"><a href="javascript:moreDiscussion();"><img src="/images/slideUp.gif" border="0" alt="More Discussion Space!"></a></div>

<div id="header_discussion" class="divHeader"><h1>This is the discussion window.</h1></div>
<div id="testDiscussion">
	<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Morbi blandit, lectus sed semper semper, elit eros luctus metus, a euismod lacus orci quis libero. Mauris fringilla. Integer enim tellus, rhoncus eu, fringilla ac, aliquam a, sapien. Mauris tempor. In egestas lorem nec turpis. Morbi felis. Nunc hendrerit neque vitae urna. Donec bibendum. Ut non pede. In tristique, dui eget fermentum ornare, nibh ipsum molestie odio, vitae suscipit nibh lectus vel dui.</p>
	<p>Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. In nisi velit, porta id, accumsan et, mollis quis, risus. Vivamus vel orci id nulla lacinia lacinia. Duis suscipit pulvinar diam. Mauris lectus risus, scelerisque sed, varius ac, aliquam eu, metus. Nam adipiscing magna. Donec imperdiet. Nulla quam nulla, accumsan quis, varius tempus, condimentum ut, magna. Proin malesuada, felis id rhoncus interdum, augue sem convallis erat, ac vulputate nisi neque quis sem. Praesent dictum tellus sed velit. Aliquam eleifend, dolor vitae volutpat semper, nulla nunc interdum nisi, vel tempus lorem urna sed lectus. Morbi porta convallis augue. Proin posuere magna eget lectus. Nunc justo. Pellentesque vitae ante. Sed est justo, pretium eget, ultrices et, pretium eget, odio.</p>
	<p>Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. In nisi velit, porta id, accumsan et, mollis quis, risus. Vivamus vel orci id nulla lacinia lacinia. Duis suscipit pulvinar diam. Mauris lectus risus, scelerisque sed, varius ac, aliquam eu, metus. Nam adipiscing magna. Donec imperdiet.</p>
	
</div>
</body>
</html>
