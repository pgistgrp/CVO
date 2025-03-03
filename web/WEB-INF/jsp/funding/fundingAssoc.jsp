<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<!--####
	Project: Let's Improve Transportation!
	Page: Define Funding Sources
	Description: Form to associate selected funding sources to a workflow instance.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Matt Paulin, Zhong Wang
#### -->
<html:html> 
<head>
<title>Define Funding Sources</title>
<!-- Site Wide JavaScript -->
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/scripts/util.js'></script>
<!-- End DWR JavaScript Libraries -->

<!--Funding Specific  Libraries-->
<script type='text/javascript' src='/dwr/interface/FundingAgent.js'></script>
<script type="text/javascript" charset="utf-8">
	var suiteId = ${suite.id}; //hardcoded until workflow manager is available

	function checkAltsInSource(sourceId,checked){
		var alts = document.getElementsByName("sourceAlts" + sourceId);
		for(i=0;i<alts.length;i++){
			alts[i].checked = checked;
			
			//Get the AltID
			start = alts[i].id.indexOf('-') + 1;
			end = alts[i].id.length
			altId = alts[i].id.substring(start,end)
			
			//Inoke AJAX to set the soure Alt operation
			setSourceDef(altId, checked)
		}
	}

	function setSourceDef(altId,checked){
		Util.loading(true,"Saving funding sources..");
		operation = (checked) ? "add" : "remove";
		
		//alert("suiteId: " + suiteId + " altId: " + altId + " operation: " + operation); 
		FundingAgent.setFundingDef({suiteId:suiteId,altId:altId,operation:operation}, {
			callback:function(data){
				if (data.successful){
					//alert("alternative operation saved!");
					//updateXML();
				}else{
					alert(data.reason);
				}
			Util.loading(false);
			},
			errorHandler:function(errorString, exception){ 
			alert("FundingAgent.setFundingDef( error:" + errorString + exception);
			}
		});
	}
	
	function updateXML(){
        FundingAgent.getFundingSuiteById({id:suiteId}, {
            callback:function(data){
                if (data.successful){
                    xml=data.fundSuite.references
                    /*
xml = '<?xml version="1.0" encoding="UTF-8"?>\r\n\
<template>\r\n\
    <fundings>\r\n'
    data.fundSuite.references.each(function(fRef){
    xml += '\t<funding name="'+fRef.project.name +'">\r\n'; 
    fRef.altRefs.each(function(aRef){
        xml += '\t\t<alternative name="'+aRef.alternative.name+'"></alternative>\r\n'
    })
    xml += '\t</funding>\r\n'
    });
    xml+='\
    </fundings>\r\n\
</template>';
*/
                    $('xmlDataTemplate').value = xml;
                }else{
                    alert(data.reason);
                }
            },
            errorHandler:function(errorString, exception){ 
            alert("FundingAgent.getFundingSuiteById( error:" + errorString + exception);
            }
        });
    }
</script>
<style type="text/css">
	@import "styles/loading-indicator.css";
	body{font-size:11 pt;font-family:arial;width:800px;}
	li{margin: 10px 0; list-style: none;}
	.source{font-size: 1.3em;}
	li ul li:hover {background:#D5EAEF;}
</style>
<event:pageunload />
</head>


<body>
	<p><a href="userhome.do?workflowId=${requestScope['org.pgist.wfengine.WORKFLOW_ID']}">Back to Moderator Control Panel</a></p>
	<h1>Define Funding Source Alternatives</h1>
	<p>Select all funding source alternatives that you would like to include for this expiriment.</p>

	<h3>All Funding Sources</h3>
	<ul id="sourcesList">
		<c:forEach var="source" items="${sources}">
			<li><span class="source">${source.name}</span> 
				<!--
				<small>
					<a href="javascript:checkAltsInSource(${source.id}, true)">check all</a> | 
					<a href="javascript:checkAltsInSource(${source.id}, false)">uncheck all</a>
				</small>-->
				<ul>
					<c:forEach var="alt" items="${source.alternatives}">
						<li>
							<label><input type="checkbox" name="sourceAlts${source.id}" id="sourceAlt-${alt.id}" 
							<c:if test="${pg:containsFundingRef(suite,source,alt)}">CHECKED</c:if> value="${alt.id}" onClick="setSourceDef(this.value, this.checked);"/>
							${alt.name}</label>
						</li>
					</c:forEach>
				</ul>
			</li>
		</c:forEach>
	</ul>

	<h3 align="right">Finished selecting funding source alternatives?</h3>
	
	<p>TEMP (For Developers) - <a href="javascript:Element.toggle('XMLwrapper');updateXML();">View XML for Data Template</a></p>
	
	<div id="XMLwrapper" style="display:none">
		<textarea id="xmlDataTemplate" style="width:100%; height: 500px">
            <!--update via DWR-->
	    </textarea>
    </div>
	
	<p align="right"><input type="button" style="padding:5px;" onClick="location.href='userhome.do?workflowId=${requestScope['org.pgist.wfengine.WORKFLOW_ID']}'" value="Finished!"/></p>
</body>
</html:html>

