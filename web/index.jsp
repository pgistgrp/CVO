<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">

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

<html:html>
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
		font-size:1.7em;
		color:#355800;
		letter-spacing: -1px;
		word-spacing:-1px;
		line-height:1.0em;
		font-weight:400;
	}
	
	ul {color:#355800;font-size:1.3em;line-height:1.2em;}
	ul span {color:#333;}
	
	.right {text-align:right;}
	a{color:#355800;}

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
</style>
</head>
<body>
	<div><img class="splash" src="images/home-splashimage.png" alt="Let's Improve Transportation!"></div>
	
	<div id="text">
		<h1>If you bus, bike, drive, or walk in the Puget
					Sound region, this site will help you:</h1>
		
		<p>
			<ul>
				<li><span>Voice your concerns with the transportation system</span></li>
				<li><span>Design your own improvement plan and discuss it with others</span></li>
				<li><span>Vote on which improvements are most important</span></li>
			</ul>
			<h3 style="text-align:right">Want to <a href="lmMenu.do">Learn More</a>?</h3>
		</p>
		
		<div id="login">
			<html:form action="/login.do" method="POST" focus="user.loginname">
				<input type="hidden" name="PG_INIT_URL" value="${param['PG_INIT_URL']}">
				<div class="cell"><strong>User Name</strong><br /><html:text property="user.loginname"/></div>
				<div class="cell"><strong>Password</strong>
					<br /><html:password property="user.password" redisplay="false"/>
				</div>
				<div style="float:right;"><html:submit styleId="sub" property="submit" value="Login"/></div>
				<div style="clear:both"></div>
				<span id="errors">${userForm.reason}</span>
			</html:form>
			<div class="clearBoth"></div>
		</div>
		
		<h1 class="right">Need to <a href="register.do">Create an Account</a>? <a href="recoverpassword.do">Forgot your Password?</a></h1>
	</div>
</body>
</html:html>