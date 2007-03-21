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
		[ ] Add STATIC calcs to form (Jordan)
#### -->
<html:html> 
<head>
<title>Manage Funding Sources</title>
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
<style type="text/css" media="screen">
	li{margin: 10px 0; list-style: none;}
	.source{font-size: 1.3em;}
</style>
<script>
	/* *************** get all funding sources in the system *************** */
	function getFundingSources(){
		FundingAgent.getFundingSources({page:1, count:-1}, {
			callback:function(data){
				if (data.successful){
					$('sourcesList').innerHTML = data.html // gets fundingMgr_sources.jsp
					alert(data.fundings)
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("FundingAgent.getFundingSources( error:" + errorString + exception);
			}
		});
	}
	
	function prepareSource(id){
		formId = (id) ? id : ""
		Element.toggle('sourceForm'+ formId);
		(id) ? getSourceById(id) : renderSourceForm();		
	}

	function getFundingSourceById(id){
		FundingAgent.getFundingSourceById({id:id}, {
			callback:function(data){
				if (data.successful){
					renderSourcForm(data.source)
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("FundingAgent.getFundingSourceById( error:" + errorString + exception);
			}
		});
	}

	function renderSourceForm(source){
		//using ternery operators so it won't complain about the values when creating a new project :)
		id = (source) ? source.id : "";
		name = (source) ? source.name : "";
		type = (source) ? source.type : "";	
		types = ["null","road", "transit"];
		
		f = '<label>Funding Source Name:</label>\
			<input id="txtSourceName' + id +'" type="text" value="'+name+'" size="25"><br />\
			<select id="selSourceType' + id +'">';
			for(i=1; i<types.length; i++){
				typeSelected = (i==type) ? "SELECTED" : "";
				f += '<option value="'+ i +'" '+ typeSelected +'>'+types[i]+'</option>';
			}
		f +='</select><br />\
			<p><input type="submit" value="Submit"></p>';
		$("frmSource"+id).innerHTML = f;

	}

	function createSource(){
		var name = $F('txtSourceName');
		var type = $F('selSourceType');
		alert('name: ' + name + ' type: ' + type);
		FundingAgent.createFundingSource({name:name, type:type}, {
			callback:function(data){
				if (data.successful){
					getFundingSources();
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
				alert("FundingAgent.createFundingSource( error:" + errorString + exception);
			}
		});
	}
	


	
	/* *************** create a new funding source alternative *************** */
	function addAlternative(){
		var id = "";
		var name = "";
		var revenue = "";
		var sourceURL = "";
		var taxRate = "";
		var toll = "";
		var offPeakTripsRate = "";
		var peakHourTripsRate = "";
		//alert("id: " + id + " name: " + name + " revenue: " + revenue + " taxRate: " + taxRate + " toll: " + toll + " offPeakTripsRate: " + offPeakTripsRate + " peakHourTripsRate: " + peakHourTripsRate); 
		FundingAgent.createFundingSourceAlt({id:id,name:name,revenue:revenue,sourceURL:sourceURL, taxRate:taxRate, toll:toll,offPeakTripsRate:offPeakTripsRate,peakHourTripsRate:peakHourTripsRate}, {
			callback:function(data){
				if (data.successful){
					alert("successful");
					getFundingSources();
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
				alert("FundingAgent.addAlternative( error:" + errorString + exception);
			}
		});
	}
	
	/* *************** delete a given funding source *************** */
	function deleteFundingSource(id){
		FundingAgent.deleteFundingSource({id:id}, {
			callback:function(data){
				if(data.successful){
					alert("Funding source deleted");
					getFundingSources();
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){
				alert("FundingAgent.deleteFundingSource( error:" + errorString + exception);
			}
		});
		
	}
	
	
	/* *************** delete a given funding source alternative *************** */
	function deleteAlternative(id){
		FundingAgent.deleteAlternative({id:id}, {
			callback:function(data){
				if(data.successful){
					alert("Alt funding source deleted");
					getFundingSources();
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){
				alert("FundingAgent.deleteAltnerative( error:" + errorString + exception);
			}
		});
		
	}

	/* *************** create a form (via javascript) to edit a given funding source *************** */
	
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
				alert("FundingAgent.prepareEditFundingSource( error:" + errorString + exception);
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
				alert("FundingAgent.getFundingSourceById( error:" + errorString + exception);
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
				alert("FundingAgent.editFundingSource( error:" + errorString + exception);
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
				alert("FundingAgent.prepareEditFundingSourceAlt( error:" + errorString + exception);
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
				alert("FundingAgent.getFundingSourceById( error:" + errorString + exception);
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
				alert("FundingAgent.editFundingSourceAlt( error:" + errorString + exception);
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
	<p><a href="main.do">Back to Moderator Control Panel</a></p>
	<h1>Manage Funding Sources</h1>
	<h3>Manage all funding sources and their associated alternatives.</h3>

	<h4>All Funding Sources </h4>
	<ul id="sourcesList">
		<!-- load sources here-->
	</ul>

	<script type="text/javascript" charset="utf-8">
		getFundingSources();
	</script>
</body>
</html:html>

