<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<!--####
	Project: Let's Improve Transportation!
	Page: Main index page - http://www.letsimprovetransportation.org
	Description: This page serves as the main index.  Here the user can read info 
	about the project, click to learn more, and/or log into the system.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] Txt and layout changes (Jordan and Adam)
		[x] Make it look all web 2.0 (Adam)
#### -->

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Let's Improve Transportation!</title>
<script src="scripts/search.js" type="text/javascript"></script>
<style type="text/css">
body {
		background:url('images/home-background2.png') repeat-x #E8F7CC;
		text-align:center;
		margin:0px;
		font-size:12pt;
		font-family:Arial, Verdana, sans-serif;
	}
	
	img.splash {
		margin:auto;
		position:relative;
		top:1px;
	}
	
	div#text {
		width:611px;
		margin:20px auto 0px auto;
		text-align:left;
		font-size:1em;
	}
	
	h1 {
		font-family:"Trebuchet MS",arial,verdana,sans-serif;
		font-size:1.3em;
		color:#000;
font-weight:normal;
	}
	
	ul {color:#355800;font-size:1.3em;line-height:1.2em;}
	ul span {color:#333;}
	
	a{color:#316786;}

	#login * {margin:0px;padding:0px;}
	
	#login {
		background:#D5E7A1;
		padding:15px;
	}
	
	#login div.cell {float:left;margin-right:20px;}
	#login input {width:180px;font-size:1.2em;padding:2px;}
	
	input#sub {
	padding:10px;
	font-size:1.2em;
  height:80%;
	width:100px;     
	}

	#facts{
		background:#B4D579;
		padding:10px;
		text-align:center;
		color:#32480B;
		font-size:13pt;
	}

	#facts p{padding:0px;margin:7px 0px;}

	h4{font-weight:normal;font-style:italic;font-size:13pt}

small {display:block;margin-top:20px;}


</style>
</head>
<body>
	<div><img class="splash" src="images/home-splashimage.png" alt="Let's Improve Transportation!"></div>
	
	<div id="text">
		<div id="facts"><p>During the next 25 years the central Puget Sound population is expected to grow by <strong>1.2 million people</strong>.</p><p>How will this growth impact our already congested <strong>transportation system?</strong></p><p>What <strong>improvements</strong> are necessary to keep our region moving?</p><p><strong>Who</strong> gets to have a voice in this decision?</p><div class="clearBoth"></div></div>
		<p>
		<h1>Let's Improve Transportation is an experiment in participatory democracy. <a href="lmMenu.do">Learn more</a> about this research study or <a href="register.do">register now</a>.</h1>
		<h4>Qualified participants may be eligible for compensation.</h4>
		
		<div id="login">
			<form action="/login.do" method="POST">
				<div class="cell"><strong>User Name</strong><br /><input type="text" name="loginname" value="${user.loginname}" /></div>
				<div class="cell"><strong>Password</strong>
					<br /><input type="password" name="password" value=""/>
				</div>
				<div style="float:right;"><input type="submit" id="sub" value="Login"/></div>
				<div style="clear:both"></div>
				<span id="errors">${reason}</span>
			</form>
			<div class="clearBoth"></div>
		</div>
	<small>This research is funded by National Science Foundation, Division of Experimental and Integrative Activities, Information Technology Research (ITR) Program, Project Number EIA 0325916, funds managed within the Digital Government Program.</small>
	</div>
</body>
</html>