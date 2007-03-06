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
			
			<c:forEach var="step" items="${workflow.steps}" varStatus="loop">
				<h3 class="headerColor clearBoth step-header">${step.name}</h3>
				<c:forEach var="substep" items="${step.substeps}" varStatus="loop">
					<div id="step-${substep.id}" class="padding5 home-row clearfix">
						<div class="step"><a href="#">${substep.name}</a><br />
							<small>${substep.description}</small>
						</div>
						<div class="date">${substep.startDate} - ${substep.endDate}</div>
					</div>
				</c:forEach>
			</c:forEach>	
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

