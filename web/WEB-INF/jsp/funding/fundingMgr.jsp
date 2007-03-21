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
		[ ] Create ALts - Peak rate and off peak rate shouldn't be required if Toll is unchecked
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
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("FundingAgent.getFundingSources( error:" + errorString + exception);
			}
		});
	}
	
	function getSourceById(id){
		FundingAgent.getFundingSourceById({id:id}, {
			callback:function(data){
				if(data.successful){
					renderSourceForm(data.source)
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){
				alert("FundingAgent.getFundingSourceById( error:" + errorString + exception);
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
		types = ["null",
				"annual cost = (tax rate) * (estimated annual consumption)",
				"annual cost = (tax rate) * (number of vehicles)",
				"annual cost = sum( (tax rate) * (vehicle value) )",
				"annual cost = sum( (tax rate) / (miles per galon) * (miles driven per year) )",
				"annual cost = sum( (tax rate) / (miles per galon) * (miles driven per year) )",
				"No direct cost calculated",
				"annual cost = (tax rate) * (parkings per year)",
				"annual cost = (tax rate) * (trips per year)"];
		
		f = '<label>Funding Source Name:</label>\
			<input id="txtSourceName' + id +'" type="text" value="'+name+'" size="25"><br />\
			<label>Calculation Type:</label>\
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
		//alert('name: ' + name + ' type: ' + type);
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
	
	/* *************** edit a given funding source *************** */	
	function editSource(id){
		var name = $F('txtSourceName' + id);
		var type = $F('selSourceType' + id);
		FundingAgent.editFundingSource({id:id, name:name, type:type}, {
			callback:function(data){
				if(data.successful){
					getFundingSources();
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){
				alert("FundingAgent.editFundingSource( error:" + errorString + exception);
			}
		});
		
	}


	/* *************** delete a given funding source *************** */
	function deleteSource(id){
		var destroy = confirm ("Are you sure you want to delete this funding source? Note: there is no undo.")
		if(destroy){
			FundingAgent.deleteFundingSource({id:id}, {
				callback:function(data){
					if(data.successful){
						new Effect.Puff('source-' + id);
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){
					alert("FundingAgent.deleteFundingSource( error:" + errorString + exception);
				}
			});
		}
	}
	
	/* *************** STARTING ALTERNATIVE FUNCTIONS *************** */
	/* *************** STARTING ALTERNATIVE FUNCTIONS *************** */
	
	function prepareSourceAlt(alt, idType){
		(idType=="altId") ? getSourceAltById(alt) : renderSourceAltForm(alt);
		Element.toggle('alternativeForm'+ alt);
	}
	
	function getSourceAltById(id){
		FundingAgent.getFundingSourceAltById({id:id}, {
			callback:function(data){
				if(data.successful){
					renderSourceAltForm(data.alternative);
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){
				alert("FundingAgent.getFundingSourceById( error:" + errorString + exception);
			}
		});
		
	}
	
	function renderSourceAltForm(alt){
		//using ternery operators so it won't complain about the values when creating a new alt :)
		altId = (alt.id) ? alt.id : alt;
		name = (alt.name) ? alt.name : "";
		revenue = (alt.revenue) ? alt.revenue : "";
		taxRate = (alt.taxRate) ? alt.taxRate : "";
		sourceURL = (alt.sourceURL) ? alt.sourceURL : "";
		avgCost = (alt.avgCost) ? alt.avgCost : "";
		toll = (alt) ? alt.toll : "";
		tollChecked = (toll == true) ? "CHECKED" : "";
		peakHourTripsRate = (alt.peakHourTripsRate) ? alt.peakHourTripsRate : "";
		offPeakTripsRate = (alt.offPeakTripsRate) ? alt.offPeakTripsRate : "";

		
		f = '<h4>Editing Funding Source Alternative</h4>\
			<label>Funding Source Alternative Name:</label>\
			<input id="txtAltName'+ altId +'" type="text" value="'+ name +'" size="25"><br />\
			<label>Revenue:</label>\
			<input id="txtAltRevenue'+ altId +'" type="text" value="'+ revenue +'" size="25"><br />\
			<label>Tax Rate:</label>\
			<input id="txtAltTaxRate'+ altId +'" type="text" value="'+ taxRate +'" size="25"><br />\
			<label>Source URL:</label>\
			<input id="txtAltSourceURL'+ altId +'" type="text" value="'+ sourceURL +'" size="25"><br />\
			<label>Average Cost to Average Resident:</label>\
			<input id="txtAltAvgCost'+ altId +'" type="text" value="'+ avgCost +'" size="25"><br />\
			<label>Toll:</label>\
			<input id="ckbxToll'+ altId +'" type="checkbox" onClick="Element.toggle(\'ifTolls\')" value="'+ toll +'" '+tollChecked+'><br />\
			<div id="ifTolls" style="display:none;">\
				<label>Peak Rate:</label>\
				<input id="txtAltPeakHourTripsRate'+ altId +'" type="text" value="'+ peakHourTripsRate +'" size="25"><br />\
				<label>Off Peak Rate:</label>\
				<input id="txtAltOffPeakTripsRate'+ altId +'" type="text" value="'+ offPeakTripsRate +'" size="25"><br />\
			</div>\
			<p><input type="submit" value="Submit"></p>';
		

		$("frmSourceAlt"+altId).innerHTML = f;
		(tollChecked) ? Element.toggle('ifTolls') : ""
	}
	
	function createSourceAlt(id){
		name = $F('txtAltName' + id);
		revenue = $F('txtAltRevenue' + id);
		taxRate = $F('txtAltTaxRate' + id);
		sourceURL = $F('txtAltSourceURL' + id);
		avgCost = $F('txtAltAvgCost' + id);
		toll = $('ckbxToll' + id).checked;
		tollChecked = (toll == true) ? "CHECKED" : "";
		peakHourTripsRate = $F('txtAltPeakHourTripsRate' + id);
		offPeakTripsRate = $F('txtAltOffPeakTripsRate' + id);
		tollFormatted = toll.toString();
		//DWR TEST: {id:"2495", name:"DWRTEST",revenue:"111",taxRate:"222",source:"Source",avgCost:"333",toll:"true",peakHourTrips:"444",offPeakTrips:"555"}
		//alert('id:' + id + ' name: ' + name + ' revenue: ' + revenue + ' taxRate: ' + taxRate + ' sourceURL: ' + sourceURL + ' avgCost: ' + avgCost + ' toll:' + tollFormatted + ' tollChecked: ' + tollChecked + ' peakHourTripsRate:' + peakHourTripsRate + ' offPeakTripsRate:' + offPeakTripsRate);
		FundingAgent.createFundingSourceAlt({id:id,name:name,revenue:revenue,taxRate:taxRate,source:sourceURL,avgCost:avgCost,toll:tollFormatted,peakHourTrips:peakHourTripsRate,offPeakTrips:offPeakTripsRate}, {
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
	
	/* *************** delete a given funding source alternative *************** */
	function deleteSourceAlt(id){
		var destroy = confirm ("Are you sure you want to delete this Funding Source Alternative? Note: there is no undo.")
		if(destroy){
			FundingAgent.deleteFundingSourceAlt({id:id}, {
				callback:function(data){
					if(data.successful){
						new Effect.Puff("alt-" + id);
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){
					alert("FundingAgent.deleteAltnerative( error:" + errorString + exception);
				}
			});
		}
		
	}

	/* *************** edit a given funding source alternative *************** */	
	function editFundingSourceAlt(fid,fname,frev,ftax){
		name = $F('txtAltName' + id);
		revenue = $F('txtAltRevenue' + id);
		taxRate = $F('txtAltTaxRate' + id);
		sourceURL = $F('txtAltSourceURL' + id);
		avgCost = $F('txtAltAvgCost' + id);
		toll = $('ckbxToll' + id).checked;
		tollChecked = (toll == true) ? "CHECKED" : "";
		peakHourTripsRate = $F('txtAltPeakHourTripsRate' + id);
		offPeakTripsRate = $F('txtAltOffPeakTripsRate' + id);
		tollFormatted = toll.toString();
		FundingAgent.editFundingSourceAlt({id:id,name:name,revenue:revenue,taxRate:taxRate,source:sourceURL,avgCost:avgCost,toll:tollFormatted,peakHourTrips:peakHourTripsRate,offPeakTrips:offPeakTripsRate}, {
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

