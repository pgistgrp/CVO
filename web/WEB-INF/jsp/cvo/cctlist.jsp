<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>CCT List</title>
<link rel="stylesheet" type="text/css" href="/styles/default.css">
<script type='text/javascript' src='/scripts/base.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>
<script>
  function openCreateCCT() {
    $('createCCTDialog_name').value = '';
    $('createCCTDialog_purpose').value = '';
    $('createCCTDialog_instruction').value = '';
    createCCTDialog.popup();
    return false;
  }
  function createCCT() {
    var params = {
      name : $('createCCTDialog_name').value,
      purpose : $('createCCTDialog_purpose').value,
      instruction : $('createCCTDialog_instruction').value,
    };
    CCTAgent.createCCT(params, function(data) {
      if (data.successful) {
        createCCTDialog.close();
        var table = $('cctListTable');
        n = table.rows.length;
        for (var i=0; i<n; i++) table.deleteRow(0);
        var row = table.insertRow(table.rows.length);
        var column = row.insertCell(0);
        column.innerHTML = '<span style="color:red;">Loading......</span>';
        CCTAgent.getCCTs(function(data1) {
          if (data1.successful) {
            var table = $('cctListTable');
            table.deleteRow(0);
            var list = data1.ccts;
            for (var i=0; i<list.length; i++) {
              var cct = list[i];
              var row = table.insertRow(table.rows.length);
              var column = row.insertCell(0);
              column.innerHTML = '<a href="<html:rewrite page="/cctview.do"/>?cctId='+cct['id']+'">'+cct['name']+'</a>';
            }
          } else {
            alert(data.reason);
          }
        });
      } else {
        alert(data.reason);
      }
    });
  }
</script>
</head>

<body>

<pg:dialog id="createCCTDialog" width="500" height="400">
  <table width="100%" cellpadding="0" cellspacing="0">
    <tr>
      <td>Please input the display name of this CCT:</td>
    </tr>
    <tr>
      <td><input type="text" id="createCCTDialog_name" value="" class="inputbox" style="width:100%;"/></td>
    </tr>
    <tr>
      <td>Please input the purpose of this CCT:</td>
    </tr>
    <tr>
      <td><textarea id="createCCTDialog_purpose" style="width:100%;height:100px;" class="inputbox" value=""></textarea></td>
    </tr>
    <tr>
      <td>Please input the instruction of this CCT:</td>
    </tr>
    <tr>
      <td><textarea id="createCCTDialog_instruction" style="width:100%;height:100px;" class="inputbox" value=""></textarea></td>
    </tr>
    <tr>
      <td align="center"><input type="button" value="Submit" onclick="createCCT();"/></td>
    </tr>
  </table>
</pg:dialog>

<html:form action="/cctlist.do" method="POST">
  <h2>CCT List</h2>
  <pg:show roles="moderator">
    <pg:hide roles="admin">
    <p><pg:button onclick="openCreateCCT();" title="Create CCT"/>
    </pg:hide>
  </pg:show>
  <table id="cctListTable" class="listtable" cellspacing="1" frame="box" rules="all" width="100%">
    <logic:iterate id="cct" property="ccts" name="cctForm">
    <tr>
      <td><html:link action="/cctview.do" paramId="cctId" paramName="cct" paramProperty="id"><bean:write name="cct" property="name"/></html:link></td>
    </tr>
    </logic:iterate>
  </table>
</html:form>

</body>
</html:html>

