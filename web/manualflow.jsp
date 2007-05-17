<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
	<head>
	<title>Let's Improve Transportation - Home</title>
	
	<!-- End Site Wide CSS -->
	<!-- Site Wide JS -->
	<script src="scripts/prototype.js" type="text/javascript"></script>
	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<script src="scripts/search.js" type="text/javascript"></script>
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/util.js'></script>
    <script type='text/javascript' src='/dwr/interface/CriteriaAgent.js'></script>
	<script type='text/javascript' src='/dwr/interface/ProfileAgent.js'></script>
	<script type='text/javascript' src='/dwr/interface/FundingAgent.js'></script>
	<script type='text/javascript' src='/dwr/interface/ProjectAgent.js'></script>
	<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>
	<script type='text/javascript' src='/dwr/interface/CSTAgent.js'></script>	
	
	<script type="text/javascript">
	var cctId= 0;
	var projectSuiteId = 0;
	var fundingSuiteId = 0;
	var critSuiteId = 0;
	var theme_isid = 0;
	var crit_isid = 0;
	
	function createProjectSuite() {
		ProjectAgent.createProjectSuite({}, {
			callback:function(data){
				if (data.successful){
					alert(data.id);
					projectSuiteId = data.id;
					$('defineproject').innerHTML = "<a href=\"projectDefine.do?suiteId=" + data.id + "\">Define Project</a>";
					$('gradeproject').innerHTML = "<a href=\"projectGrading.do?suiteId=" + data.id + "\">Grade Project</a>";
				}else{
					$('error').innerHTML = "<b>Error in ProjectAgent.createProjectSuite Method: </b>" + data.reason; 
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ProjectAgent.createProjectSuite( error:" + errorString + exception);
			}
		});
	}

	function createFundingSuite() {
		FundingAgent.createFundingSourceSuite({}, {
			callback:function(data){
				if (data.successful){
					alert(data.id);
					$('definefunding').innerHTML = "<a href=\"fundingDefine.do?suiteId=" + data.id + "\">Define Funding</a>";
				}else{
					$('error').innerHTML = "<b>Error in FundingAgent.createFundingSourceSuite Method: </b>" + data.reason; 
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("FundingAgent.createFundingSourceSuite( error:" + errorString + exception);
			}
		});
	}
	
	function publishFundingSuite() {
		FundingAgent.publish({}, {
			callback:function(data){
				if (data.successful){
					alert(data.isid);
					$('').innerHTML = "<a href=\"sd.do?isid=" + data.isid + "\">Structured Discussion Funding Sources</a>";
				}else{
					$('error').innerHTML = "<b>Error in FundingAgent.publish Method: </b>" + data.reason; 
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("FundingAgent.publish( error:" + errorString + exception);
			}
		});
	}
	
	function publishProjects() {
		FundingAgent.publish({}, {
			callback:function(data){
				if (data.successful){
					alert(data.isid);
					$('').innerHTML = "<a href=\"sd.do?isid=" + data.isid + "\">Structured Discussion Funding Sources</a>";
				}else{
					$('error').innerHTML = "<b>Error in FundingAgent.publish Method: </b>" + data.reason; 
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("FundingAgent.publish( error:" + errorString + exception);
			}
		});
	}
	
	function createCCT(form) {
		var name = form.name.value;
		var purpose = form.purpose.value;
		var instruction = form.instruction.value;
		CCTAgent.createCCT({name:name, purpose:purpose, instruction:instruction}, {
			callback:function(data){
				if (data.successful){
					alert(data.cctId);
					cctId=data.cctId;
					$('cct').innerHTML = "<a href=\"cctview.do?cctId=" + data.cctId + "\">Concern Collection Tool</a>";
					$('cst').innerHTML = "<a href=\"cstview.do?cctId=" + data.cctId + "\">Concern Synthesis Tool</a>";
				}else{
					$('error').innerHTML = "<b>Error in CCTAgent.createCCT Method: </b>" + data.reason; 
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("CCTAgent.createCCT( error:" + errorString + exception);
			}
		});
	}
	
	function publishThemes(form) {
		var title = form.title.value;
		CSTAgent.publish({cctId:cctId, title:title}, {
			callback:function(data){
				if (data.successful){
					alert(data.isid);
					theme_isid = data.isid;
					$('sdc').innerHTML = "<a href=\"sd.do?isid=" + theme_isid + "\">Structured Discussion For CCT</a>";
				}else{
					$('error').innerHTML = "<b>Error in CSTAgent.publish Method: </b>" + data.reason; 
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("CSTAgent.publish( error:" + errorString + exception);
			}
		});
	}
	
	function createCriteriaSuite() {
		CriteriaAgent.createCriteriaSuite({}, {
			callback:function(data){
				if (data.successful){
					alert(data.critSuiteId);
					critSuiteId = data.critSuiteId;
					$('definecriteria').innerHTML = "<a href=\"criteriaDefine.do?suiteId=" + data.critSuiteId + "&cctId=" + cctId + "\">Define Criteria</a>";
				}else{
					$('error').innerHTML = "<b>Error in CriteriaAgent.createCriteriaSuite Method: </b>" + data.reason; 
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("CriteriaAgent.createCriteriaSuite( error:" + errorString + exception);
			}
		});
	}
	
	function publishCriteria(form) {
		var title = form.ctitle.value;
		CriteriaAgent.publish({cctId:cctId, suiteId:critSuiteId, title:title}, {
			callback:function(data){
				if (data.successful){
					alert(data.isid);
					crit_isid = data.isid;
					$('sdcrit').innerHTML = "<a href=\"sd.do?isid=" + crit_isid + "\">Structured Discussion For Criteria</a>";
					$('critweight').innerHTML = "<a href=\"criteriaWeigh.do?suiteId=" + critSuiteId + "\">Criteria Weight</a>";
				}else{
					$('error').innerHTML = "<b>Error in CriteriaAgent.publish Method: </b>" + data.reason; 
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("CriteriaAgent.publish( error:" + errorString + exception);
			}
		});
	}
	</script>
	</head>
	<body>
	<pg:show roles="moderator">
	<h2 align="center">Manual Flow Control Panel - PoC </h2>
	
	<p align="center"><strong>Initializing Step</strong></p>
	<div id="error"></div>
	<div align="center">
	  <table width="500" border="0" cellspacing="5">
        <tr>
          <td><div align="center">
            <input name="Create Project Suite" type="button" value="Create Project Suite" onclick="createProjectSuite()" />
          </div></td>
          <td><div align="center">
            <input name="Create Funding Suite" type="button" value="Create Funding Suite" onclick="createFundingSuite()"/>
          </div></td>
        </tr>
        <tr>
          <td><div id="defineproject" align="center">Define Project</div></td>
          <td><div id="definefunding" align="center">Define Funding</div></td>
        </tr>
          </table>
	  </div>
	<p align="center">&nbsp;</p>
	<p align="center"><strong>CVO Step</strong></p>
	<p align="center">
	  <form id="formCCT">
	  
	    <div align="center">Name:
	      <input type="text" id="name" />
	  Purpose:
	  <input type="text" id="purpose" />
	  Instruction:
	  <input type="text" id="instruction" />
	  <input name="CreateCCT" type="button" value="CreateCCT" onclick="createCCT(this.form)"/>
	    </div>
	  </form>
	  
	</p>
	<div align="center">
	  <table width="500" border="0" cellspacing="5">
        <tr>
          <td><div id="cct" align="center">Concern Collection Tool </div></td>
          <td><div id="cst" align="center">Concern Synthesis Tool</div></td>
        </tr>
          </table>
	  </div>
	<p align="center">
	 <form id="publishthemesform">
	   <div align="center">title:
	     <input type="text" id="title" />
	     <input name="Publish Themes" type="button" value="Publish Themes" onclick="publishThemes(this.form)" />
	   </div>
	 </form>
</p>
	<div id="sdc" align="center">Structured Discussion For CCT
	  </div>
	<p align="center">&nbsp;</p>
	<p align="center"><strong>Criteria and Objective  Step</strong></p>
	<p align="center">
	  <input name="Create Criteria Suite" type="button" value="Create Criteria Suite" onclick="createCriteriaSuite()"/>
	</p>
	<div id="definecriteria" align="center">Define Criteria</div>
	<p align="center">
  	  <form id="publishcriteriaform">
	   <div align="center">title:
	     <input type="text" id="ctitle" />
	     <input name="Publish Criteria" type="button" value="Publish Criteria" onclick="publishCriteria(this.form)" />
	   </div>
	 </form>
	</p>
	<div align="center">
	  <table width="500" border="0" cellspacing="5">
        <tr>
          <td><div id="sdcrit" align="center">Structured Discussion Criteria</div></td>
          <td><div id="critweight" align="center">Weight Criteria</div></td>
        </tr>
          </table>
	  </div>
	<p align="center">&nbsp;</p>
	<p align="center"><strong>Project/Funding Sources and Viewing   Step</strong>  </p>
	<div align="center">
	  <table width="700" border="0" cellspacing="5">
        <tr>
          <td><div id="gradeproject" align="center">Grade Project </div></td>
          <td><div align="center">
            <input name="Publish Funding Sources" type="button" value="Publish Funding Sources" onclick="publishFundingSources()"/>
          </div></td>
        </tr>
        <tr>
          <td><div align="center">
            <input name="Publish Projects" type="button" value="Publish Projects" onclick="publishProjects()"/>
          </div></td>
          <td><div id="sdfunding" align="center">Structured Discussion Funding Sources </div></td>
        </tr>
        <tr>
          <td><div id="sdproject" align="center">Structured Discussion Project </div></td>
          <td><div align="center"></div></td>
        </tr>
          </table>
	  </div>
	<p align="center">&nbsp;</p>
	<p align="center"><strong>Packages   Step</strong></p>
	<p align="center"><strong>Coming soon... </strong></p>
	</pg:show>
	</body>
</html:html>
