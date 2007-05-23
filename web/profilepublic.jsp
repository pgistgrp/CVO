<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
<head>
<title>Let's Improve Transportation: Eros Profile</title>

<style type="text/css" media="screen">
@import "styles/lit.css";
@import "styles/public-profile.css";
</style>
<!-- Site Wide JS -->
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/interface/ProfileAgent.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>



<script type="text/javascript" charset="utf-8">
var start = 0;
var end = 5;
function nextDiscussion() {
	start = start + 5;
	end = end + 5;
	getDiscussion(start, end);
}

function prevDiscussion() {
	if(start >= 5) {
		start = start - 5;
		end = end - 5;
	}
	getDiscussion(start, end);
}

function getDiscussion(start, end) {
	var user = "${param.user}"; //Make this works, then do the next buttons
	ProfileAgent.getUserDiscussion({username:user, start:start, end:end}, {
		callback:function(data){
			if (data.successful){
				$('profile-recent').innerHTML = data.html;
			}else{
				$('profile-recent').innerHTML = "<b>Error in ProfileAgent.getUserDiscussion Method: </b>" + data.reason; 
			}
		},
		errorHandler:function(errorString, exception){ 
		alert("ProfileAgent.getUserDiscussion( error:" + errorString + exception);
		}
	});
}
</script>

<event:pageunload />
</head>
<body>
	<!--[if IE]>
		<style type="text/css">
			fieldset p {padding-bottom:1px;}
		</style>
	<![endif]-->
<!-- Begin header -->
<div id="header"><jsp:include page="/header.jsp" /></div>
<!-- End header -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu">
	<div id="headerContainer">
		<div id="headerTitle" class="floatLeft">
			<h3 class="headerColor">Learn More</h3>
		</div>
	</div>
</div>
<!-- End header menu -->
<!-- Begin container - Main page content begins here -->
<div id="container" class="clearfix">
	<p><h3 class="headerColor" style="display:inline">LIT Participant Profile</h3>
	<a href="usercp.do">Edit my profile and settings</a></p>
	<!-- start PROFILE-FIELDS section -->
	<div id="profile-fields">
		<div id="statistics" class="box9">
			<h4 class="headerColor">My statistics</h4>
			<p>
				<span class="label">Last visit to LIT website:</span>
				<span class="value">${lastlogin}</span>
			</p><br />
			<p>
				<span class="label">Total Visits to LIT website:</span>
				<span class="value">${visits}</span>
			</p><br />
			<p>
				<span class="label"># of discussion posts:</span>
				<span class="value">${post}</span>
			</p><br />
		</div>
		<p>
			<span class="label">Username</span>
			<span class="value">${user.loginname}</span>
		</p><br />
		<p>
			<span class="label">Home location</span>
			<span class="value">${user.city}, ${user.zipcode} [map]</span>
		</p><br />
		<p>
			<span class="label">Work Location</span>
			<span class="value">${user.workCity}, ${user.workZipcode}[map]</span>
		</p><br />
		<p>
			<span class="label">Vocation</span>
			<span class="value">${user.vocation}</span>
		</p><br />
		<p>
			<span class="label">Methods of transportation</span>
			<span class="value"><span>${user.primaryTransport}</span>
		</p><br />
		<div class="clearBoth"></div>
		<p>
			<span class="label">Why I'm here</span> 
			<span class="value" style="width:700px;">${user.profileDesc}</span> 
		</p>
		<br />
	
		<div class="clearBoth"></div>

		<p>
			<span class="label">My tags</span>
	  <span class="value" id="keywords" style="width:600px;">
	  			<c:choose>	
					<c:when test="${fn:length(tags) == 0}">
						<p>This user has no concerns at this time.</p>
					</c:when>
					<c:otherwise>
					<ul>
						<c:forEach var="tag" items="${tags}" varStatus="loop">

							<li class="tagSize3">${tag}</li>

						</c:forEach>
					</ul>
					</c:otherwise>
				</c:choose>

	  </span>
		</p><br />
		
		<div class="clearBoth"></div>
		<p>
			<span class="label">My concerns</span> 
	  <span class="value">
				<div id="concerns">
				<!-- start Concerns -->
				
				<c:choose>	
					<c:when test="${fn:length(concerns) == 0}">
						<p>This user has no concerns at this time.</p>
					</c:when>
					<c:otherwise>
						<c:forEach var="concern" items="${concerns}" varStatus="loop">
							<div class="concern box7">
							<p id="concern${concern.id}"></p>
							<span class="concerns">
							${concern.content}
							</span>
							<br />
							
							
							<div id="concerns-keywords">
							<span>Keywords:</span>
							<ul>
							<c:forEach items="${concern.tags}" var="tagref">
										<li>${tagref.tag.name}</li>
							</c:forEach>
							</ul>
							</div>
							</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
				
				<!-- end concerns-->
				</div>
	  </span> 
		</p><br />
		
		<div class="clearBoth"></div>
		<p>
			<span class="label">My recent discussion activity</span>
	  <span class="value">
	<div id="profile-recent" style="width:600px;">
	<!-- begin RECENT DISCUSSIONS HEADER -->
				
	<!-- end RECENT DISCUSSIONS -->
	</div>
			</span>
		</p><br />				
		<div class="clearBoth"></div>
		
		<p>
			<span class="label">My planning factor weights</span>
	<span class="value">
				<div id="weights" style="width:600px;">

					<!-- begin RECENT DISCUSSIONS HEADER -->				
					<div class="listRow headingColor heading clearfix">
						<div class="profile-col1 floatLeft" style="margin-left:.2em">
							<div class="floatLeft">
								<h4>Planning Factor</h4>
							</div>
						</div>
						<div class="profile-col2 floatRight">
							<h4>Weights</h4>
						</div>
					</div>
					<!-- end RECENT DISCUSSIONS HEADER -->
					
					<!-- begin A RECENT DISCUSSION -->
					<c:choose>	
					<c:when test="${fn:length(criterias) == 0}">
						<p>This user has no planning factors at this time.</p>
					</c:when>
					<c:otherwise>
						<c:set var="rowcount" value="0"/>
						<c:forEach var="criteria" items="${criterias}" varStatus="loop">
						
							<!-- begin A RECENT DISCUSSION -->
								<c:choose>
								<c:when test='${rowcount==0}'>
								<div class="listRow clearfix">
								<c:set var="rowcount" value="1"/>
								</c:when>
								<c:otherwise>
								<div class="listRow odd clearfix">
								<c:set var="rowcount" value="0"/>
								</c:otherwise>
								</c:choose>
							
									<div class="profile-col1 floatLeft">
										<div class="floatLeft">
											${criteria.name}
										</div>
									</div>
									<div class="profile-col2 floatRight">${criteria.tempWeight}</div>
									
								</div>
							<!-- end A RECENT DISCUSSION -->
						</c:forEach>
					</c:otherwise>
					</c:choose>
					
				</div>
				<!-- end RECENT DISCUSSIONS -->

  </div>
			</span>
	</div>
	</p><br />
	<div class="clearBoth"></div>
	<!-- end PROFILE-FIELDS section -->
</div>
<!-- End Container -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu">
	<div id="headerContainer">
		<div id="headerTitle" class="floatLeft">
			<h3 class="headerColor">Learn More</h3>
		</div>
	</div>
</div>
<!-- End header menu -->
<!-- end the bottom header menu -->
<!-- Begin footer -->
<div id="footer"><jsp:include page="/footer.jsp" /></div>
<!-- End footer -->
<script type="text/javascript">
getDiscussion('0','5');
</script>
</body>
</html:html>
