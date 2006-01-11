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
  function createCVO() {
    $('createCVODialog').style.display='block';
    //CVOAgent.createCVO(createCVO_callback, 'test');
    return false;
  }
  function createCVO_callback(data) {
    alert(data);
  }
</script>
</head>

<body>

<div id="createCVODialog" class="dialog" style="width:300;height:200;">
</div>

<html:form action="/cvolist.do" method="POST">
  <h2>CVO List</h2>
  <pg:show roles="admin">
    <p><a href="#" onclick="createCVO();">Create CVO</a>
  </pg:show>
  <table class="listtable" width="100%">
    <tr>
      <td>CVO List</td>
    </tr>
    <tr>
      <td>CVO List</td>
    </tr>
    <tr>
      <td>CVO List</td>
    </tr>
    <logic:iterate id="cvo" property="cvoList" name="cvoForm">
    <tr>
      <td><bean:write name="cvo" property="name"/></td>
    </tr>
    </logic:iterate>
  </table>
</html:form>

</body>
</html:html>

