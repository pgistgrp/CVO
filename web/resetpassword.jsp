<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
	<head>
	<title>Voicing Climate Concerns - Registration</title>
	<!-- Site Wide CSS -->
<style type="text/css" media="screen">
@import "styles/lit.css";
@import "styles/registration-1.css";
@import "styles/recover-password.css";
</style>
	<!-- End Site Wide CSS -->
	<!-- Site Wide JS -->

	<script src="scripts/prototype.js" type="text/javascript"></script>
	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<script src="scripts/search.js" type="text/javascript"></script>
	
	</head>
	<body>
	
	<!--[if IE]>
		<style type="text/css">
			fieldset p {padding-bottom:1px;}
		</style>
	<![endif]-->
	
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
		<div id="password">
		<h2>Reset Your Password</h2>
		<html:form action="/resetpassword.do" method="POST">
			 <html:hidden property="editPassword" value="true"/>
			 
			 	<p id="errors">${sysmsg}</p>
			 	<div class="plabel">Recovery Code:</div>
			 	<div class="pvalue"><html:text property="code" value="<%= request.getParameter("code") %>"/></div>
				<div class="plabel">Enter your new password</div>
			 	<div class="pvalue"><html:password property="password1" value=""/></div>
				<div class="plabel">Confirm your new password</div>
				<div class="pvalue"><html:password property="password2" value=""/></div>
				<div class="clearBoth"></div>
			 <html:submit styleId="sub" property="submit" value="Submit"/>
		</html:form>
		</div>
	
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
