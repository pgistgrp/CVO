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

				
					<div>
						<div class="floatLeft clearfix"><a href="#">Prev</a></div>
						<div class="floatRight clearfix"><a href="#">Next</a></div>
					</div>
					
					<div class="clearBoth"></div>
				
					<div class="listRow headingColor heading clearfix">
						<div class="profile-col1 floatLeft" style="margin-left:.2em">
							<div class="floatLeft">
								<h4>Topic</h4>
							</div>
						</div>
						<div class="profile-col2 floatRight">
							<h4>Last Post</h4>
	
						</div>
					</div>
					<!-- end RECENT DISCUSSIONS HEADER -->
					<c:choose>	
					<c:when test="${fn:length(discussions) == 0}">
						<p>This user has no dicussions at this time.</p>
					</c:when>
					<c:otherwise>
						<c:set var="rowcount" value="0"/>
						<c:forEach var="discussion" items="${discussions}" varStatus="loop">
						
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
										<a href="#">${discussion.title}</a><br />
			
										<span>Some how get the step</span>
									</div>
								</div>
								<div class="profile-col2 floatRight"><fmt:formatDate value="${discussion.createTime}" pattern="MM/dd" var="discussionDate" />${discussionDate}</div>
							</div>
							<!-- end A RECENT DISCUSSION -->
					
						</c:forEach>
					</c:otherwise>
					</c:choose>
					<!-- begin A RECENT DISCUSSION -->
					
				</div>
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
					<div class="listRow clearfix">
						<div class="profile-col1 floatLeft">
							<div class="floatLeft">
								Economic Vitality
							</div>
						</div>
						<div class="profile-col2 floatRight">10</div>
					</div>
					<!-- end A RECENT DISCUSSION -->
					<div class="listRow odd clearfix">
						<div class="profile-col1 floatLeft">
	
							<div class="floatLeft">
								Security
							</div>
						</div>
						<div class="profile-col2 floatRight">13</div>
					</div>
	
					<div class="listRow  clearfix">
						<div class="profile-col1 floatLeft">
							<div class="floatLeft">
								Accessibility and Mobility
							</div>
						</div>
						<div class="profile-col2 floatRight">23</div>
	
					</div>
					<div class="listRow odd clearfix">
						<div class="profile-col1 floatLeft">
							<div class="floatLeft">
								Environmental / Energy / QOL
							</div>
						</div>
	
						<div class="profile-col2 floatRight">3</div>
					</div>
					<div class="listRow  clearfix">
						<div class="profile-col1 floatLeft">
							<div class="floatLeft">
								Integration and Connectivity
							</div>
						</div>
						<div class="profile-col2 floatRight">8</div>
					</div>
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
</body>
</html:html>
