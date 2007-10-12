
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
	    Util.loading(true, "Creating new expiriment");
		WorkflowAgent.createInstance(
	    { situationId : templateId, name:name, description:description },
	    function(data) {
	      if (data.successful) {
			workflow.getWorkflows('mod');
	      } else {
	        alert(data.reason);
	      }
	      Util.loading(false);
	    }
	  );
	}else{
		alert("Please do not leave any fields blank");
	}
};


Workflow.prototype.getWorkflows = function(mod) {
	Util.loading(true, "Loading Overview");
	var thePanel = this.panel;
	WorkflowAgent.getWorkflows({type:"all"}, {
		callback:function(data){
			if (data.successful){
				if(!mod && data.openRunningTotal == 1){
					location.href="userhome.do?workflowId="+ data.instanceId;
				}else{
					$(thePanel).innerHTML = data.html;
					if(mod){workflow.getTemplates();}
				}
				Util.loading(false);
			}else{
				alert(data.reason);
			}
		},
		errorHandler:function(errorString, exception){ 
		alert("WorkflowAgent.getWorkflows( error:" + errorString + exception);
		}
	});
};


Workflow.prototype.startWorkflow = function(workflowId) {
  var workflow = this;
  Util.loading(true, "Starting Expiriment");
  WorkflowAgent.startWorkflow(
    { workflowId : workflowId },
    function(data) {
      if (data.successful) {
        workflow.getWorkflows('mod');
      } else {
        alert(data.reason);
      }
      Util.loading(false);
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

Workflow.prototype.setOpenAccess = function(workflowId, setting) {
  WorkflowAgent.setOpenAccess(
    { workflowId : workflowId, openAccess : setting },
    function(data) {
      if (data.successful) {
        workflow.getWorkflows('mod');
      } else {
        alert(data.reason);
      }
    }
  );
};

Workflow.prototype.nextStep = function(workflowId, contextId, activityId) {
  Util.loading(true, "Starting next step");
  var buttons = document.getElementsByName("completedButton")
  for (var i=0; i < buttons.length; i++) {
    buttons[i].disabled = true;
  };
  //alert("workflowId: " + workflowId + " contextId : " + contextId + " activityId: " + activityId)
  WorkflowAgent.nextStep(
    { workflowId : workflowId, contextId: contextId, activityId: activityId },
    function(data) {
      if (data.successful) {
        workflow.getWorkflow(workflowId);
        Util.loading(false);
      } else {
        alert("REASON: " + data.reason);
      }
    }
  );
};
