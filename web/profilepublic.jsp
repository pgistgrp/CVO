<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
<head>
<title>Voicing Climate Concerns: Profile</title>

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
  return;
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
  <!-- Start Global Headers  -->
  <wf:nav />
  <wf:subNav />
  <!-- End Global Headers -->
<!-- Begin container - Main page content begins here -->
<div id="container" class="clearfix">
	<p><h3 class="headerColor" style="display:inline">VCC Participant Profile</h3>
	<!-- start PROFILE-FIELDS section -->
	<div id="profile-fields">
		<div id="statistics" class="box9">
			<h4 class="headerColor">My statistics</h4>
			<p>
				<span class="label">Last visit to VCC website:</span>
				<span class="value">${lastlogin}</span>
			</p><br />
			<p>
				<span class="label">Total Visits to VCC website:</span>
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
		<div class="clearBoth"></div>

		<p>
			<span class="label">My Keywords</span>
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
		
	</div>
	</p><br />
	<div class="clearBoth"></div>
	<!-- end PROFILE-FIELDS section -->
</div>

<wf:subNav />

<div id="footer"><jsp:include page="/footer.jsp" /></div>
<!-- End footer -->
<script type="text/javascript">
getDiscussion('0','5');
</script>
</body>
</html:html>
