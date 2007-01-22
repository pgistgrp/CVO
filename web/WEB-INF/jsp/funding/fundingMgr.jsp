<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<!--####
	source: Let's Improve Transportation!
	Page: Funding Manager
	Description: CRUD Events on All Funding and their Alternatives
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman, Isaac Yang
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] BareBones JavaScript (Isaac)
		
#### -->
<html:html> 
<head>
<title>Manage Criteria</title>
<!-- Site Wide JavaScript -->
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->

<!--Criteria Specific  Libraries-->
<script type='text/javascript' src='/dwr/interface/FundingAgent.js'></script>

<script>
	//on-load
	getFundingSources();

	/* *************** get all funding sources in the system *************** */
	function getFundingSources(){
		FundingAgent.getFundingSources({}, {
			callback:function(data){
				if (data.successful){
					$('sourcesList').innerHTML = data.html // gets fundingMgr_sources.jsp
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("FundingAgent.getFundingSources( error:" + errorString + exception+")"+);
			}
		});
	}

	/* *************** create a new funding source *************** */
	function createFundingSource(){
		var name = "";
		FundingAgent.createFundingSource({name:name}, {
			callback:function(data){
				if (data.successful){
					alert("successful");
					getFundingSources();
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("FundingAgent.createFundingSource( error:" + errorString + exception+")"+);
			}
		});
	}
	
	/* *************** create a new funding source alternative *************** */
	function addAlternative(fid,fname,frev,ftax){
		//alert("param1: " + param1 + " param2: " + param2 + " param3: " + param3 + " param4: " + param4); 
		FundingAgent.createFundingSourceAlt({id:fid,name:fname,revenue:frev,taxRate:ftax}, {
			callback:function(data){
				if (data.successful){
					alert("successful");
					getFundingSources();
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
				alert("FundingAgent.createFundingSourceAlt( error:" + errorString + exception+")"+);
			}
		});
	}
	
	/* *************** delete a given funding source *************** */
	function deleteFundingSource(fid){
		FundingAgent.deleteFundingSource({id:fid}, {
			callback:function(data){
				if(data.successful){
					alert("Funding source deleted");
					getFundingSources();
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){
				alert("FundingAgent.deleteFundingSource( error:" + errorString + exception+")"+);
			}
		});
		
	}
	
	
	/* *************** delete a given funding source alternative *************** */
	function deleteAlternative(fid){
		FundingAgent.deleteAlternative({id:fid}, {
			callback:function(data){
				if(data.successful){
					alert("Alt funding source deleted");
					getFundingSources();
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){
				alert("FundingAgent.deleteAlternative( error:" + errorString + exception+")"+);
			}
		});
		
	}

	/* *************** create a form (via javascript) to edit a given funding source *************** */
	//prepareEditFundingSource
		//getFundingSourceById
	
	function prepareEditFundingSource(id){
		var name='editName'+id;
		var val="";
		FundingAgent.getFundingSourceById({id:fid}, {
			callback:function(data){
				if(data.successful){
					alert(data.source);
					val=data.source.name;
				}else{
					alert(data.reason);
					val="";
				}
			},
			errorHandler:function(errorString, exception){
				alert("FundingAgent.getFundingSourceById( error:" + errorString + exception+")"+);
			}
		});
		
		filler="<input name='txtsourceEdit"+id+"' type='text' value='"+val+"' size='25'> <input type='submit' value='submit' onclick='editFundingSource("+id+",$(\'txtsourceEdit"+id+"\').value); Effect.toggle(\'editsource"+id+"\',\'blind\');'>";
	
	$('editsource'+id).innerHTML=filler;
	}
	
	function getFundingSourceById(fid){
		FundingAgent.getFundingSourceById({id:fid}, {
			callback:function(data){
				if(data.successful){
					alert(data.source);
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){
				alert("FundingAgent.getFundingSourceById( error:" + errorString + exception+")"+);
			}
		});
		
	}
	/* *************** edit a given funding source *************** */	
	//editFundingSource
	
	function editFundingSource(fid, fname){
		FundingAgent.editFundingSource({id:fid, name:fname}, {
			callback:function(data){
				if(data.successful){
					alert("Funding source edited");
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){
				alert("FundingAgent.editFundingSource( error:" + errorString + exception+")"+);
			}
		});
		
	}
	
	/* *************** create a form (via javascript) to edit a given funding source alternative*************** */
	//prepareEditFundingSourceAlt
		//getFundingSourceAltById
		
	function prepareEditFundingSourceAlt(id){
	var name='editNameAlt'+id;
	var rev='editRevenueAlt'+id;
	var tax='editTaxAlt'+id;
			filler="";
			filler+="<fieldset style='float:left;'>";
			filler+="<form id='editFundingSourceAlt"+id+"' name='editFundingSourceAlt"+id+"'>";
			filler+="<h3>Edit Funding Source Alternative</h3>";
			filler+="<br />";
			filler+="<label for='editNameAlt"+id+"' class='niceFormElement'>Funding Source Alternative Name</label>";
			filler+="<input id='editNameAlt"+id+"' name='editNameAlt"+id+"' type='text' class='niceFormElement' /><br />";
			
			filler+="<label for='editRevenueAlt"+id+"' class='niceFormElement'>Funding Source Alternative Revenue</label>";
			filler+="<input id='editRevenueAlt"+id+"' name='editRevenueAlt"+id+"' type='text' class='niceFormElement' /><br />";
			
			filler+="<label for='editTaxAlt"+id+"' class='niceFormElement'>Funding Source Alternative Tax Rate</label>";
			filler+="<input id='editTaxAlt"+id+"' name='editTaxAlt"+id+"' type='text' class='niceFormElement' /><br />";
			
			filler+="<br />";
			
			filler+="<input type='button' value='Save Funding Source Alternative Edits' onClick='editFundingSourceAlt("+id+","+$(name).value+","+$(rev).value+","+$(tax).value+";'/>";
			filler+="<input type='reset' value='Clear Form' />";
			filler+="<br />";
			filler+="</form>";
		filler+="</fieldset>";
		$('editsourceAlt'+id).innerHTML=filler;
		
		FundingAgent.getFundingSourceAltById({id:fid}, {
			callback:function(data){
				if(data.successful){
					alert(data.alternative);
					$(name).value=data.alternative.name;
					$(rev).value=data.alternative.revenue;
					$(tax).value=data.alternative.taxRate;
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){
				alert("FundingAgent.getFundingSourceAltById( error:" + errorString + exception+")"+);
			}
		});
	}
	
	function getFundingSourceAltById(fid){
		FundingAgent.getFundingSourceAltById({id:fid}, {
			callback:function(data){
				if(data.successful){
					alert(data.alternative);
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){
				alert("FundingAgent.getFundingSourceAltById( error:" + errorString + exception+")"+);
			}
		});
		
	}
	
	/* *************** edit a given funding source alternative *************** */	
	//editFundingSourceAlt
	
	function editFundingSourceAlt(fid,fname,frev,ftax){
		FundingAgent.editFundingSourceAlt({id:fid, name:fname, revenue:frev, taxRate:ftax}, {
			callback:function(data){
				if(data.successful){
					alert("Alt funding Source edited");
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){
				alert("FundingAgent.editFundingSourceAlt( error:" + errorString + exception+")"+);
			}
		});
		
	}
	
	function prepareAddAlternative(id){
		var name="txtNewFundingAltName"+id;
		var rev="txtNewFundingAltRevenue"+id;
		var tax="txtNewFundingAltTax"+id;
		filler="";
		filler+="<h4>Create New Funding Source Alternative</h4>";
		filler+="<form onsubmit='return false;'>";
		filler+="<label>Name:</label>";
		filler+="<input id='txtNewFundingAltName"+id+"' name='txtNewFundingAltName"+id+"' type='text' value='' size='25'/>";
		filler+="<br/>";
		filler+="<label>Revenue:</label>";
		filler+="<input id='txtNewFundingAltRevenue"+id+"' name='txtNewFundingAltRevenue"+id+"' type='text' value=''size='25'/>";
		filler+="<br/>";
		filler+="<label>Tax Rate:</label>";
		filler+="<input id='txtNewFundingAltTax"+id+"' name='txtNewFundingAltTax"+id+"' type='text' value='' size='25'/>";
		filler+="<br/>";
		filler+="<input type='submit' value='Add New Funding Source Alternative' onclick='addAlternative("+id+","+$(name).value+","+$(rev).value+","+$(tax).value+")'";
		filler+="</form>";
		$('addsourceAlt'+id).innerHTML=filler;
	
	
	}
	
</script>
<style type="text/css">

</style>
</head>


<body>
	<h3>Moderator Tools &raquo; Manage Funding</h3> 
	<form name="publishsources" action="sourceDefine.do">
		<input type="hidden" name="activity" value="save" />
		<h4>Funding Sources in ${cct.name}</h4>
		<ul id="sourcesList">
			<!-- load sources here-->
		</ul>
	</form>
</body>
</html:html>

