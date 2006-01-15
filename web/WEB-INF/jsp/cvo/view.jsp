<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>CVO</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page='/styles/default.css'/>">
<script type='text/javascript' src='<html:rewrite page="/scripts/util.js"/>'></script>
<script type='text/javascript' src='<html:rewrite page="/scripts/ajaxCaller.js"/>'></script>
<script type='text/javascript' src='<html:rewrite page="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<html:rewrite page="/dwr/util.js"/>'></script>
<script type='text/javascript' src='<html:rewrite page="/dwr/interface/CVOAgent.js"/>'></script>
<script>
  function extractConcern() {
    var reply = $('reply');
    if (reply.value=='') {
      alert('Please give your reply.');
      return;
    }
    reply.disabled = true;
    CVOAgent.extractConcern(extractConcern_callback, reply.value);
  }
  function extractConcern_callback(data) {
    $('concern').value = data['concern'];
    $('step2').style.display='inline';
  }
  function sendReply() {
    CVOAgent.createConcern($('cvoId').value, $('reply').value, $('concern').value, createConcern_callback);
  }
  function createConcern_callback(data) {
    if (data['result']=='true') {
      $('reply').value = '';
      $('concern').value = '';
      $('step2').style.display='none';
      reply.disabled = false;
      //reload concerns
      url = '<html:rewrite page="/postGroups.do?id="/>'+$('cvoId').value;
      ajaxCaller.getPlainText(url, reloadPostGroups);
    } else {
      alert(data['alert']);
    }
  }
  function reloadPostGroups(text, headers, callingContext) {
    $('postGroups').innerHTML = text;
  }
  function reloadMyPostGroups(text, headers, callingContext) {
    $('myPostGroups').innerHTML = text;
  }
  function postReply(id) {
    CVOAgent.getPost(id, getPost_callback);
  }
  function getPost_callback(data) {
    var post = data['post'];
    var div = $('createComment');
    $('createComment_id').value = post['id'];
    $('createComment_title').innerHTML = 'User '+post.owner.loginname+' said:';
    $('createComment_replyto').value = post.content;
    $('createComment_reply').value = '';
    leftPos = (window.innerWidth-400)/2;
    topPos = (window.innerHeight-500)/2;
    div.style.left = leftPos+"px";
    div.style.top = topPos+"px";
    div.style.display = "block";
  }
  function createReply() {
    var content = $('createComment_reply').value;
    if (content.length<1) {
      alert('Please input your comment.');
      return;
    }
    CVOAgent.createPost($('createComment_id').value, content, createPost_callback);
  }
  function createPost_callback(data) {
    if (data['result']=='true') {
      var div = $('createComment');
      div.style.display = 'none';
      url = '<html:rewrite page="/postGroups.do?id="/>'+$('cvoId').value+'&myPost=0';
      ajaxCaller.getPlainText(url, reloadMyPostGroups);
      url = '<html:rewrite page="/postGroups.do?id="/>'+$('cvoId').value+'&myPost=1';
      ajaxCaller.getPlainText(url, reloadPostGroups);
    } else {
      alert(data['alert']);
    }
  }
</script>
</head>
<body bgcolor="white">

<pg:dialog id="createComment" width="400" height="500">
  <input type="hidden" id="createComment_id" value="">
  <table width="100%" cellpadding="0" cellspacing="0">
    <tr>
      <td id="createComment_title"></td>
    </tr>
    <tr>
      <td><textarea id="createComment_replyto" style="width:100%;height:150px;color:red;" class="inputbox" readonly value=""></textarea></td>
    </tr>
    <tr>
      <td>Please input your comment:</td>
    </tr>
    <tr>
      <td><textarea id="createComment_reply" style="width:100%;height:200px;color:blue;" class="inputbox" value=""></textarea></td>
    </tr>
    <tr>
      <td align="center"><input type="button" value="Submit" onclick="createReply();"/></td>
    </tr>
  </table>
</pg:dialog>

<form>
<input type="hidden" id="cvoId" value="${cvoForm.cvo.id}">
<div style="background-color:#c9c9ff;padding:3px;">CVO: <bean:write name="cvoForm" property="cvo.name"/></div>
<table width="100%">
  <tr>
    <td width="50%" valign="top">
      <table width="100%">
        <tr>
          <td width="100%">
            <div>
              <span>Q: <bean:write name="cvoForm" property="root.content"/></span>
              <textarea id="reply" style="width:100%;height:80px;" class="inputbox"></textarea>
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
                <input type="button" value="Submit" onclick="sendReply();">
              </td>
            </tr>
            </table>
          </td>
        </tr>
      </table>
      <table>
        <tr>
          <td width="100%">
            <div style="border-top:1px solid blue; padding-top:10px;">
              Your concerns:
            </div>
          </td>
        </tr>
        <tr>
          <td id="myPostGroups">
            <jsp:include page="myPostGroups.jsp"/>
          </td>
        </tr>
      </table>
    </td>
    <td valign="top">
      <table width="100%">
      <tr>
        <td style="font-size:small; padding-bottom:10px;">Hottest First | Latest First | Last commented</td>
      </tr>
      <tr>
        <td id="postGroups">
        <jsp:include page="postGroups.jsp"/>
        </td>
      </tr>
    </td>
  </tr>
</table>
</form>

</body>
</html:html>

