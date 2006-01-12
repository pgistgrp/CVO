<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>CVO List</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page='/styles/default.css'/>">
<script type='text/javascript' src='<html:rewrite page="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<html:rewrite page="/dwr/util.js"/>'></script>
<script type='text/javascript' src='<html:rewrite page="/dwr/interface/CVOAgent.js"/>'></script>
<script>
  function openCreateCVO() {
    var div = $('createCVODialog');
    $('createCVODialog_name').value = '';
    $('createCVODialog_question').value = '';
    leftPos = (window.innerWidth-300)/2;
    topPos = (window.innerHeight-200)/2;
    div.style.left = leftPos+"px";
    div.style.top = topPos+"px";
    div.style.display = "block";
    return false;
  }
  function createCVO() {
    CVOAgent.createCVO($('createCVODialog_name').value, $('createCVODialog_question').value, createCVO_callback);
  }
  function createCVO_callback(data) {
    if (data) {
      $('createCVODialog').style.display = "none";
    }
  }
</script>
</head>

<body>

<pg:dialog id="createCVODialog" width="300" height="200">
  <table width="100%" height="100%" cellpadding="0" cellspacing="0">
    <tr>
      <td>Please input the display name of this CVO:</td>
    </tr>
    <tr>
      <td><input type="text" id="createCVODialog_name" value="" style="width:100%;"/></td>
    </tr>
    <tr>
      <td>Please input the QUESTION:</td>
    </tr>
    <tr>
      <td><textarea id="createCVODialog_question" style="width:100%;height:100%;" value=""></textarea></td>
    </tr>
    <tr>
      <td><input type="button" value="Submit" onclick="createCVO();"/></td>
    </tr>
  </table>
</pg:dialog>

<html:form action="/cvolist.do" method="POST">
  <h2>CVO List</h2>
  <pg:show roles="moderator">
    <p><a href="#" onclick="openCreateCVO();">Create CVO</a>
  </pg:show>
  <table class="listtable" width="100%">
    <logic:iterate id="cvo" property="cvoList" name="cvoForm">
    <tr>
      <td><html:link action="/cvoview.do" paramId="id" paramName="cvo" paramProperty="id"><bean:write name="cvo" property="name"/></html:link></td>
    </tr>
    </logic:iterate>
  </table>
</html:form>

</body>
</html:html>

