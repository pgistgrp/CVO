<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<!--####
	Project: Voicing Climate Concerns
	Page: Main index page - http://www.climateconcerns.org
	Description: This page serves as the main index.  Here the user can read info 
	about the project, click to learn more, and/or log into the system.
	Author(s): 
	     Front End: Michalis Avraam, Robert Aguirre, Tim Nyerges
	     Back End: Zhong Wang, Michalis Avraam
#### -->

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Voicing Climate Concerns</title>
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
		top:2px;
	}
	
	div#text {
		width:611px;
		margin:20px auto 0px auto;
		text-align:left;
		font-size:1em;
	}
	
	h1 {
		font-size:13pt;
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
.rightAlign{text-align:right}

</style>
</head>
<body>
	<div><img class="splash" src="images/home-splashimage.png" alt="Voicing Climate Concerns"></div>
	
	<div id="text">
		<div id="facts">
		<p>During the coming decades coastal Oregon communities, like coastal areas in general, will be impacted by climate change and variability.</p>
		<p>What concerns do you have?</p>
		<p>How might these <strong>concerns</strong> be <strong>measured</strong>?</p>
		<p> <strong>Make your voice heard!</strong></p>
		
		<div class="clearBoth"></div></div>
		<p>
    
		<h1><strong>Voicing Climate Concerns</strong> is an experiment in participatory democracy. <a href="/lmMenu.do?workflowId=&contextId=&activityId=&" target="_blank" title="Read an overview about this study">Read an overview</a> about this research study or <b><a href="register.do">register now</a></b>. Your browser must use Firefox to access the experiment. Download Firefox <a href="http://www.mozilla.com/en-US/firefox/all.html"title="Download Firefox">here</a></h1>
    		
		<p>Voicing Climate Concerns is an online activity in which participants brainstorm concerns about climate change and variability along the Oregon Coast and analysts produce maps depicting those concerns contingent on availabity of data. If you would like to participate in the experiment, please log in (or register above):</p>
    		
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
					<p class="rightAlign"><a href="recoverpassword.jsp">Forgot your password?</a></p>
	<small>This research is funded by the National Oceanic and Atmospheric Administration, Climate Program Office, Sectoral Applications Research Program NA07OAR4310410.</small>
	</div>
</body>
</html>