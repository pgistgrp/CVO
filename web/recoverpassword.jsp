<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
	<head>
	<title>Voicing Climate Concerns</title>
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
  <!-- Start Global Headers  -->
	  <wf:nav />
	  <wf:subNav />
  <!-- End Global Headers -->
	</div>
	<!-- End header -->
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
	
	<div id="email">
	<h2>Forgot Your  Password?</h2>
	<html:form action="/forgotpassword.do" method="POST">
		 <html:hidden property="save" value="true"/>
		 <p>You can reset your password by entering the email address you used during registration. Details on how to reset your password will be sent to this address. <br/> 
	    </p>
		 <p id="errors">${sysmsg}</p>
		 
		 <p class="prow">
		 <div class="plabel">E-mail address</div>
		 <div class="pvalue">
		 	<html:text property="email" value=""/><html:submit styleId="sub" property="submit" value="Submit"/>
		 </div>
		 <div class="clearBoth"></div>
		 </p><br />
	</html:form>
	</div>
	
	</div>
	<!-- end container -->
	<!-- start feedback form -->
	<pg:feedback id="feedbackDiv" action="cctView.do"/>
	<!-- end feedback form -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
  <!-- Start Global Headers  -->
  <wf:subNav />
  <!-- End Global Headers -->
	<!-- Begin footer -->
	<div id="footer">
		<jsp:include page="/footer.jsp" />
	</div>
	<!-- End footer -->

	</body>
</html:html>
