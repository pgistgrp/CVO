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
<title>Discuss and Review Tool</title>
<style type="text/css" media="screen">@import "styles/lit.css";</style>
<style type="text/css" media="screen">
.blueBB {
	border-color: #C0D7F6 !important;
	border-width: 1px;
}
</style>

<script src="/scripts/prototype.js" type="text/javascript"></script>
<script src="/scripts/scriptaculous.js?load=effects,controls" type="text/javascript"></script>
<pg:property name="javascript" />

<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/DRTAgent.js'></script>

<pg:property name="style" />

<script type="text/javascript">
  if (typeof(infoObject)=='undefined') {
    infoObject = {};
  }
  infoObject.oid = ${infoObject.id};
  infoObject.targetId = ${infoObject.target.id};
  infoObject.wfinfo = <pg:wfinfo/>;
  infoObject.oDivElement = 'infoObjectBox';
  infoObject.discussionDivElement = 'discussionBox';
  infoObject.page = 1;
  infoObject.getAnnouncements = function(){
    displayIndicator(true);
    DRTAgent.getAnnouncements({oid:${infoObject.id}}, <pg:wfinfo/>,{
        callback:function(data){
            if (data.successful){
                displayIndicator(false);
                $('announcements').innerHTML = data.html;
            }else{
                displayIndicator(false);
                alert(data.reason);
            }
        },
        errorHandler:function(errorString, exception){ 
            alert("get targets error: " + errorString +" "+ exception);
        }
    });
  };
</script>

<script type="text/javascript">
  function onPageLoaded() {
    if (typeof(tabberAutomatic)=='function') tabberAutomatic();
    if (typeof(infoObject.loadTarget)=='function') infoObject.loadTarget();
  }
</script>

<event:pageunload />

</head>

<body onLoad="onPageLoaded();">
  <wf:nav />
  <wf:subNav />

  <h3><pg:property name="title" /></h3>
  
  <div style="display: none;" id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>
  <div id="container">
    <div id="overview" class="box2">
      <div id="col-right2" style="overflow:hidden;clear:both;height:20px;">
        <div style="float:left;overflow:hidden;">
          Moderator Announcements:
        </div>
      </div>
      <div id="col-right3" style="overflow:auto;clear:both;height:250px;">
        <center>
          <div id="announcements" style="clear:both;padding:2px;overflow:auto;width:90%;height:80%;">
            <jsp:include page="drtAnnouncements.jsp" />
          </div>
        </center>
      </div>
    </div>
    <div id="infoObjectBox" class="infoObjectBox">
        <pg:include property="page" />
    </div>
  </div>
  
  <wf:subNav />
  
  <pg:feedback id="feedbackDiv" action="drtmod.do" />

  <div id="footer">
      <jsp:include page="/footer.jsp" />
  </div>
</body>

</html>

