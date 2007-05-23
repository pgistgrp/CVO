
function Workflow(div_panel, div_panel2) {
  this.panel = div_panel;
  this.panel_templates = div_panel2;
  return this;
}

Workflow.prototype.getTemplates = function() {
  var thePanel = this.panel_templates;
  WorkflowAgent.getTemplates(
    function(data) {
      if (data.successful) {
        $(thePanel).innerHTML = data.html;
      } else {
        alert(data.reason);
      }
    }
  );
};


Workflow.prototype.createInstance = function() {
	var name = $F('expName');
	var description = $F('expDesc');
	var templates = document.getElementsByName("expTemplates");
	for(i=0;i<templates.length;i++){
		if(templates[i].checked){
			var templateId = templates[i].value;
			break;
		}
	}
	if(templateId && description != "" && name != ""){
		WorkflowAgent.createInstance(
	    { situationId : templateId },
	    function(data) {
	      if (data.successful) {
			workflow.getWorkflows();
	      } else {
	        alert(data.reason);
	      }
	    }
	  );
	}else{
		alert("Please do not leave any fields blank");
	}
};


Workflow.prototype.getWorkflows = function(user) {
  var thePanel = this.panel;
  WorkflowAgent.getWorkflows(
    { type : "all" },
    function(data) {
      if (data.successful) {
		if(!user && data.runningTotal == 1){
			location.href="userhome.do?wf="+ data.instanceId;
		}else{
			$(thePanel).innerHTML = data.html;
			if(!user==null){workflow.getTemplates();}
		}
      } else {
        alert(data.reason);
      }
    }
  );
};


Workflow.prototype.startWorkflow = function(workflowId) {
  var workflow = this;
  WorkflowAgent.startWorkflow(
    { workflowId : workflowId },
    function(data) {
      if (data.successful) {
        workflow.getWorkflows();
      } else {
        alert(data.reason);
      }
    }
  );
};


Workflow.prototype.getWorkflow = function(workflowId) {
  var thePanel = this.panel;
  WorkflowAgent.getWorkflow(
    { workflowId : workflowId },
    function(data) {
      if (data.successful) {
        $(thePanel).innerHTML = data.html;
      } else {
        alert(data.reason);
      }
    }
  );
};

Workflow.prototype.nextStep = function(workflowId, contextId, activityId) {
  //alert("workflowId: " + workflowId + " contextId : " + contextId + " activityId: " + activityId)
  WorkflowAgent.nextStep(
    { workflowId : workflowId, contextId: contextId, activityId: activityId },
    function(data) {
      if (data.successful) {
        workflow.getWorkflow(workflowId);
      } else {
        alert("REASON: " + data.reason);
      }
    }
  );
};
