
function Workflow(div_panel) {
  this.panel = div_panel;
  return this;
}

Workflow.prototype.getTemplates = function() {
  var thePanel = this.panel;
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


Workflow.prototype.createInstance = function(situationId) {
  WorkflowAgent.createInstance(
    { situationId : situationId },
    function(data) {
      if (data.successful) {
        //alert('workflowId: '+data.workflowId);
		workflow.getWorkflows();
      } else {
        alert(data.reason);
      }
    }
  );
};


Workflow.prototype.getWorkflows = function() {
  var thePanel = this.panel;
  WorkflowAgent.getWorkflows(
    { type : "all" },
    function(data) {
      if (data.successful) {
        $(thePanel).innerHTML = data.html;
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

