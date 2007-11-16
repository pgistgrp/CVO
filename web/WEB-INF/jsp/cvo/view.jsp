<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>CVO</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page='/styles/default.css'/>">
<script type='text/javascript' src='<html:rewrite page="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<html:rewrite page="/dwr/util.js"/>'></script>
<script>
  function extractConcern() {
    $('step2').style.display='inline';
  }
</script>
</head>
<body bgcolor="white">

<table width="100%">
  <tr>
    <td width="50%">
      <table width="100%">
        <tr>
          <td width="100%">
            <div style="background-color:#c9c9ff;">CVO: <bean:write name="cvoForm" property="cvo.name"/></div>
            <div>
              <span style="background-color:#deffc1;">Q: <bean:write name="cvoForm" property="root.content"/></span>
              <textarea id="reply" style="width:100%;height:80px;"></textarea>
            </div>
            <center>
              <input type="button" value="OK" onclick="extractConcern();">
            </center>
          </td>
        </tr>
      </table>
      <table id="step2" style="display:none;">
        <tr>
          <td>
            <table width="100%">
            <tr>
              <td>Do you mean your concern can be summarized as follows?</td>
            </tr>
            <tr>
              <td nowrap>
                <input type="text" id="concern" style="width:400px;" value="">
                <input type="button" value="Submit" onclick="$('step2').style.display='none';">
              </td>
            </tr>
            </table>
          </td>
        </tr>
      </table>
      <table>
        <tr>
          <td width="100%">
            <div>
              Your concerns:
            </div>
          </td>
        </tr>
      </table>
    </td>
    <td valign="top">
      <logic:iterate id="post" property="root.children" name="cvoForm">
        <pg:discourse id="post" post="post"/><br>
      </logic:iterate>
    </td>
  </tr>
</table>

</body>
</html:html>

