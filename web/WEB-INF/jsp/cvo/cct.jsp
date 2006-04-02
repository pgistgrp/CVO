<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>PGIST Portal - Let's Improve Transportation</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<style type="text/css" media="screen">@import "/styles/tabs.css";</style>
<style type="text/css" media="screen">@import "/styles/pgist.css";</style>
<script src="/scripts/ajax.js" type="text/javascript"></script>
<script src="/scripts/tabs.js" type="text/javascript"></script>
<script src="/scripts/prototype.js" type="text/javascript"></script>
<script src="/scripts/effects.js" type="text/javascript"></script>
<script src="/scripts/combo.js" type="text/javascript"></script>

<script type="text/javascript">
	function doOnLoad() {
		OpenTab("tab_page2", "Tags", "tags.html", false, '');
		OpenTab("tab_page1", "Concerns", "concerns.html", false, '');
	}
	
	function validateForm()
	{
		if(""==document.forms.brainstorm.addConcern.value)
		{
			document.getElementById('validation').innerHTML = 'Please fill in your concern above.';
			Effect.OpenUp('validation');
			Effect.CloseDown('tagConcerns');
			Effect.Yellow('validation', {duration: 4, endcolor:'#EEEEEE'});
		}else{
			Effect.CloseDown('validation');
			Effect.OpenUp('tagConcerns');
			Effect.Yellow('tagConcerns', {duration: 4, endcolor:'#EEEEEE'});
		}
	}
	
	function resetForm()
	{
		Effect.CloseDown('tagConcerns');
		Effect.CloseDown('validation');
	}
</script>
</head>
<body onload="doOnLoad()">

<div id="decorBar"></div>
<div id="header"><img src="/images/logo.jpg"></div>

<div id="navigation">
	<div id="bread">
	<ul>
		<li class="first">LIT Process
			<ul>
				<li>&#187; Brainstorm Concerns</li>
			</ul>
		</li>
	</ul>
	</div>
</div>
<br>
<span class="title_page">Brainstorm Concerns</span>
<div id="container"><br>
  <div id="overview"><span class="title_overview">Overview and Instructions</span> 
  	<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Fusce eu ante vitae risus varius scelerisque. Vivamus fermentum. Maecenas posuere arcu in eros. Sed tortor. Praesent quis purus. Ut accumsan metus non nisl. Nullam consequat, ligula a bibendum porta, nisl justo volutpat pede, vitae commodo felis nisl malesuada est. Fusce pellentesque. Sed dolor pede, lobortis vel, pellentesque ut, sodales vel, augue. Vestibulum molestie fringilla nibh. Nulla condimentum. In quis justo. Integer vel est. Sed ligula sapien, scelerisque a, pulvinar eu, molestie vitae, urna. Morbi vestibulum. Vestibulum vestibulum.</p>
  </div>
  <br>
  
 <div id="slate">
    <span class="title_section">Add your concern</span>
    <form name="brainstorm" method="post" action="javascript:validateForm();">
      <p><textarea style="width:99%;" name="addConcern" cols="50" rows="5" id="addConcern"></textarea></p>
      <p><input type="reset" name="Submit2" value="Reset" onClick="resetForm();"> <input type="submit" name="Continue" value="Continue"><div style="display: none;" id="validation"></div>
      </p>
    
	    <div id="tagConcerns" style="display: none;"><span class="title_section">Tag Your Concern</span>
		    <div id="tags">The tags below are suggested tags for your concern.  Please delete those that do not apply to your concern and use the textbox below to add more tags (if needed).
			<ul id="tagsList">
				<li><span class="tagsList_tag"></span>Safety <span class="tagsList_controls"><img src="/images/trash.gif" alt="Delete this Tag!" ></span></li>
				<li><span class="tagsList_tag"></span>Traffic <span class="tagsList_controls"><img src="/images/trash.gif" alt="Delete this Tag!" ></span></li>
			</ul>	    
			Add more tags (Add tags that were not suggested - View Examples)<br>
			<input type="text" name="tags" size="15"><input type="submit" name="AddTag" value="Add Tag!">
			<p>Finished Tagging? <input type="submit" name="SubmitConcern" name="AddConcern" value="Add Concern to List!"</p>
		    </div>
	    </div><hr>
	      <span class="title_section">List of Created Concerns</span><br>
	<p><span class="explaination">None created yet.  Please add a concern above.  Please refer to other participant's concerns on the right column for examples.</span></p>
	
	<span class="title_section">Finished Brainstorming Concerns?</span><br>
	<p><span class="explaination">Continue to the next step!</span></p>
    </form>
  </div>
 
  <div id="tabContainer">
	<div id="tabs">
		<ul id="tabList">
		</ul>
	</div>
	<div id="tabPanels"></div>
  </div>
<div id="footerContainer">
<div id="footer"><a href="http://www.pgist.org" target="_blank"><img src="/images/footer_pgist.jpg" alt="Powered by the PGIST Portal" border="0" align="right"></a></div>
<div id="nsf">This research is funded by National Science Foundation, Division of Experimental and Integrative Activities, Information Technology Research (ITR) Program, Project Number EIA 0325916, funds managed within the Digital Government Program.</div>
</div>
</body>
</html>
