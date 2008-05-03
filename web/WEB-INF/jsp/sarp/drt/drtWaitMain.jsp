<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Wait Screen</title>
<style type="text/css" media="screen">@import "styles/lit.css";</style>
<style type="text/css" media="screen">
.blueBB {
	border-color: #C0D7F6 !important;
	border-width: 1px;
}
</style>

<event:pageunload />

</head>

<body>
    <wf:nav />
    <wf:subNav />
    
    <pg:property var="announcement" name="announcement" />
    <center>
    <div style="width:50%; height:600px; font-size:24pt; text-align:center; vertical-align:middle; border: 1px solid #C0D7F6;">
      <div style="margin-top:250px;">
      ${announcement}
      </div>
    </div>
    </center>
    
    <br>
    <wf:subNav />
    
    <pg:feedback id="feedbackDiv" action="sdMain.do" />

    <div id="footer">
        <jsp:include page="/footer.jsp" />
    </div>
    
</body>

</html>

