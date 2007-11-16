<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<!--####
	Project: Let's Improve Transportation!
	Page: User Home
	Description: This page serves as a dashboard for all activity in the LIT process for the logged in user.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Zhong Wang, Guirong Zhou
	Todo Items:
		[x] Static HTML and CSS (Adam)
		[ ] JavaScript/JSTL (Jordan)
#### -->
<html:html> 
<head>
<title>${baseuser.loginname}'s Home</title>
<!-- Site Wide JavaScript -->
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->

<!--Criteria Specific  Libraries-->
<script type='text/javascript' src='/dwr/interface/ProjectAgent.js'></script>

<script>
// Global Variables


// End global vars

	function getRecentPosts(page){
		//alert("page: " + page); 
		User.getRecentPosts({page:page}, {
			callback:function(data){
				if (data.successful){
					$('recent-discussions-cont').innerHTML = data.html;
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("User.getRecentPosts( error:" + errorString + exception);
			}
		});
	}
	
	function getPopularPosts(page){
		//alert("page: " + page);
		System.getPopularPosts({page:page,:,:,:}, {
			callback:function(data){
				if (data.successful){
					$('popular-discussions-cont').innerHTML = data.html;
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("System.getPopularPosts( error:" + errorString + exception);
			}
		});
	}
	
	function getTags(page){
		//alert("page: " + page); 
		User.getTags({page:page}, {
			callback:function(data){
				if (data.successful){
					$('keywords-cont').innerHTML = data.html;
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("User.getTags( error:" + errorString + exception);
			}
		});
	}
</script>
<style type="text/css" media="screen">
@import "styles/lit.css";
@import "styles/user-home.css";

/* Note:  Move this to user-home.css */

body{font-size:12pt}

div#container h2.headerColor 
{
display:inline;
margin-right:1em;
}

#keywords ul{margin:0px;padding:0px;}
#keywords li 
{
list-style-type:none;
display:inline;
padding:.2em;
margin:.2em;
line-height:2em;
border:1px solid #FFE3B9;
background:#FFF1DC;
}

li.s{font-size:.9em;}
li.m{font-size:1.2em;}
li.l{font-size:1.4em;}
li.xl{font-size:1.6em;}

#keywords, #mod-announcements, #recent-discussions, #popular-discussions
{
margin:0em auto 2em auto;
}

#left-col
{
width:57%;
float:left;
padding:5px;
}

#right-col
{
width:40%;
float:right;
}

#right-col > div {padding:5px;margin-bottom:1em;}

.highlighted {background:#FFE3B9}
.step{float:left;width:350px;margin-bottom:.5em;margin-left:1em;}

.date{float:right;clear:right;margin-bottom:.5em;}
.disabled{color:#bbb;}

div.disabled a{color:#bbb;text-decoration:none;}

#announcements
{
background:#FCFFF5;
border:1px solid #B4D579;
height:300px;
padding:10px;
overflow:auto
}

#mod-announcements
{
padding:5px;
margin-top:.5em;
}

.clearfix:after {
content: "."; 
display: block; 
height: 0; 
clear: both; 
visibility: hidden;
}

/* Hides from IE-mac \*/
* html .clearfix {height: 1%;}
/* End hide from IE-mac */

div.heading h4 {margin:0px;}

.home-col1
{
width:240px;
margin-left:1em;
}

.home-col2
{
width:100px;
text-align:center;
}


.listRow
{
padding:.3em 0em;
}

.listRow h4 {margin:.5em 0em;}

#allListHeader
{
text-align:left;
}

.heading {margin-bottom:.5em;}

.odd {background: #E7F2F7}
.even {background: #ffffff}

div.home-col1 > div > span {font-size:.9em;margin-left:.5em}

</style>
</head>


<body>
	<!-- Begin the header - loaded from a separate file -->
	<div id="header">
		<!-- Begin header -->
		<jsp:include page="/header.jsp" />
		<!-- End header -->
	</div>
	<!-- End header -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu"> </div>
	<!-- End header menu -->
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
		<h2 class="headerColor">Welcome, ${baseuser.loginname}</h2>
		<a href="profile.do">View / Edit your profile</a>
		<div id="left-col">
			<h3 class="headerColor">Overview of all Steps</h3>
			<p><strong>${system.activeParticipants}</strong> participants have been active in the LIT Challenge in the past 12 hours</p>
				
				
				<!-- this section will need to be done after the workflow manager has been completed 
				<h3 class="headerColor clearBoth step-header">Step 1: Discuss Concerns</h3>
				
				<div id="step1a" class="padding5 home-row clearfix">
					<div class="step"><a href="#">1a: Brainstorm</a><br />
						<small>A list of my concerns about transportation</small>
					</div>
					<div class="date">11/15 - 11/25</div>
				</div>
				
				<div id="step1b" class="padding5 home-row clearfix">				
					<div class="step">
						<a href="#">1b: Review Summaries</a><br />
						<small>Discuss summaries of everyone's concerns</small>
					</div>
					<div class="date">11/15 - 11/25</div>
				</div>

				<h3 class="headerColor clearBoth step-header">Step 2: Evaluate Planning Factors</h3>
				
				<div id="step2a" class="padding5 home-row clearfix">
					<div class="step"><a href="#">2a: Review Planning Factors</a><br />
						<small>Talk about what factors are important in choosing projects</small>
					</div>
					<div class="date">11/15 - 11/25</div>
				</div>

				<div id="step2b" class="padding5 home-row clearfix">
					<div class="step"><a href="#">2b: Weigh Planning Factors</a><br />
						<small>Decide which factors are most important to me</small>
					</div>
					<div class="date">11/15 - 11/25</div>
				</div>
							
				<h3 class="headerColor clearBoth step-header">Step 3: Create Packages</h3>
			
				<div id="step3a" class="padding5 home-row clearfix">
					<div class="step"><a href="#">3a: Review Projects</a><br />
						<small>Discuss the projects that ahve been proposed</small>
					</div>
					<div class="date">11/15 - 11/25</div>
				</div>
				<div id="step3b" class="padding5 home-row clearfix">
					<div class="step"><a href="#">3b: Review Funding Options</a><br />
						<small>Discuss the different ways to pay for the proposed projects</small>
					</div>
					<div class="date">11/15 - 11/25</div>
				</div>
				<div id="step3c" class="padding5 home-row clearfix">
					<div class="step"><a href="#">3c: Create Your Own Package</a><br />
						<small>Select projects and decide how to pay for them</small>
					</div>
					<div class="date">11/15 - 11/25</div>
				</div>
				
				<h3 class="headerColor clearBoth step-header">Step 4: Evaluate Candidate Packages</h3>
				
				<div id="step4a" class="highlighted padding5 home-row clearfix">
					<div class="step"><a href="#">4a: Review and Discuss Packages</a><br />
						<small>Talk about the different packages before voting</small>
					</div>
					<div class="date">11/15 - 11/25</div>
				</div>
				<div id="step4b" class="highlighted padding5 home-row clearfix">
					<div class="step"><a href="#">4b: Vote</a><br />
						<small>Vote on each of the packages</small>
					</div>
					<div class="date">11/15 - 11/25</div>
				</div>
				<h3 class="headerColor clearBoth disabled step-header">Step 5: Prepare Group Report</h3>
				
				<div id="step5a" class="padding5 home-row disabled clearfix">
					<div class="step"><a href="#">5a: Review Draft Report</a><br />
						<small>Discuss a summary of what will be shown to decision makers</small>
					</div>
					<div class="date">11/15 - 11/25</div>
				</div>
				<div id="step5b" class="padding5 home-row disabled clearfixf">
					<div class="step"><a href="#">5b: Vote to Endorse Report</a><br />
						<small>Vote on the summary</small>
					</div>
					<div class="date">11/15 - 11/25</div>
				</div>
			-->
		</div>
		<div id="right-col">
			<h3 class="headerColor">Moderator announcements</h3>
			<div id="mod-announcements" class="box9">
				<div id="announcements">
					${system.moderatorAnnoucements}
				</div>
			</div>
			
			<!--begin RECENT DISCUSSIONS -->
			<div id="recent-discussions-cont">
				<!-- load recent discussions via javascript dwr -->
			</div>
			<!--end RECENT DISCUSSIONS -->
			
			
			<!--begin POPULAR DISCUSSIONS -->
			<div id="popular-discussions-cont">
				<!-- load popular discussions via javascript dwr -->
			</div>
		</div>
		<div class="clearBoth"></div>
		
		<!--begin USER KEYWORDS -->
		<div id="keywords-cont">
			<!-- load keywords via javascript dwr -->
		</div>
		<!--end USER KEYWORDS -->
	</div>
	<!-- end container -->
	<!-- start feedback form -->
	<pg:feedback id="feedbackDiv" action="cctView.do"/>
	<!-- end feedback form -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu"> </div>
	<!-- Begin footer -->
	<div id="footer">
		<jsp:include page="/footer.jsp" />
	</div>
	<!-- End footer -->

</body>
</html:html>

