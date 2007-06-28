<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>Select a situation template</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/OtherAgent.js'></script>
<script>
  function selectTemplate() {
    OtherAgent.setSituationTemplate(
      {
        templateId:$('templateId').value,
        workflowId:${requestScope['org.pgist.wfengine.WORKFLOW_ID']},
        contextId:${requestScope['org.pgist.wfengine.CONTEXT_ID']},
        activityId:${requestScope['org.pgist.wfengine.ACTIVITY_ID']},
      },
      function(data) {
        if (data.successful){
			alert("Successful! Redirecting to overview...")
			location.href="userhome.do?wf=${requestScope['org.pgist.wfengine.WORKFLOW_ID']}"
		}else{
		 	alert("Uh oh! Notify Zhong!");
		}
      }
    );
  }//selectTemplate()
  
  function checkit(value) {
    $('templateId').value = value;
  }
</script>
<style type="text/css" media="screen">
#container{width:950px;margin:auto}

body {
font-family:arial;}

#selectTemplate{
border:1px solid #C6D78C;
padding:5px;
background:#FDFFF7;
}

h1{
margin:0px;
font-size:14pt;
background:#E1F1C5;
padding:5px;
}

form p{margin:5px;}
label {font-size:13pt}
input[type=radio]{padding:5px;}
input[type=button]{padding:5px;margin-top:15px;}

</style>
</head>
<body>
<div id="container">
	<p><a href="userhome.do?wf=${requestScope['org.pgist.wfengine.WORKFLOW_ID']}">Back to Moderator Control Panel</a></p>
	<div id="selectTemplate">
	  <h1 class="headerColor">Select a Data Template</h1>
	  <p>Please select the data template that you would like to start this experiment with.
	      This will automatically select projects, project alternatives, funding sources, funding alternatives,
	      planning factors, objectives, and project grades.  Don't worry, you can modify the options throughout the 
	      experiment during each of the "define" tools.
	  <form name="mainForm">
	    <p><label><input type="radio" name="tSelect" value="-1" checked="true" onclick="checkit(this.value)">None</label>
	    <c:forEach var="template" items="${templates}">
	        <p><label><input type="radio" name="tSelect" value="${template.id}" onclick="checkit(this.value)">${template.name}</label>
	    </c:forEach>
	    </p>
  </div>
	<input type="hidden" id="templateId" size="20">
	</p><input type="button" value ="Done" onclick="selectTemplate();"></p>
	</form>
</div>
</body>
</html>

