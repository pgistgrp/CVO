<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Alphabetic Glossary</title>
<link rel="stylesheet" type="text/css" href="/styles/default.css">
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/GlossaryPublicAgent.js'></script>

<script type="text/javascript">

  
		function doOnLoad(){
			getTerms();
		}
		
		function getTerms(){
				GlossaryPublicAgent.getTerms({filter: '_'}, {
				callback:function(data){
					if (data.successful){ 
							$('slate').innerHTML = data.html;
					}

					if (data.successful != true){
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
						//showTheError();
				}
			});
		}
		
</script><wf:pageunload />
</head>
<body onload='doOnLoad();'>
	
	<div id="glossaryCont">
		<div id="alpha_a"><h2>A</h2></div>
		<div id="alpha_b"><h2>B</h2></div>
	</div>
	<div id="slate"></div>
	
</body>