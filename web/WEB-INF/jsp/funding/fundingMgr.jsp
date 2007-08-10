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
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] Add STATIC calcs to form (Jordan)
		[x] Order Sources and Alts by Name (Matt)
		[x] Create ALts - Peak rate and off peak rate shouldn't be required if Toll is unchecked (Matt)
		[ ] Polish it up a bit (Adam)
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
<script type='text/javascript' src='/scripts/util.js'></script>
<!-- End DWR JavaScript Libraries -->

<!--Criteria Specific  Libraries-->
<script type='text/javascript' src='/dwr/interface/FundingAgent.js'></script>
<style type="text/css" media="screen">
	@import "styles/loading-indicator.css";
	li{margin: 10px 0; list-style: none;border-bottom:2px solid #ccc;}
	li ul li {border:0px;}
	.source{font-size: 1.3em;}
	body{font:11pt arial;}
</style>
<script>
	/* *************** get all funding sources in the system *************** */
	function getFundingSources(){
		Util.loading(true,"Working");
		FundingAgent.getFundingSources({page:1, count:-1}, {
			callback:function(data){
				if (data.successful){
					$('sourcesList').innerHTML = data.html // gets fundingMgr_sources.jsp
				}else{
					alert(data.reason);
				}
			Util.loading(false)
			},
			errorHandler:function(errorString, exception){ 
			alert("FundingAgent.getFundingSources( error:" + errorString + exception);
			}
		});
	}
	
	function getSourceById(id){
		Util.loading(true,"Loading funding source");
		FundingAgent.getFundingSourceById({id:id}, {
			callback:function(data){
				if(data.successful){
					renderSourceForm(data.source)
				}else{
					alert(data.reason);
				}
			Util.loading(false)
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
		Util.loading(true,"Loading funding source");
		FundingAgent.getFundingSourceById({id:id}, {
			callback:function(data){
				if (data.successful){
					renderSourcForm(data.source)
				}else{
					alert(data.reason);
				}
			Util.loading(false)
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
		
		f = '<p style="width:500px;"><span style="width:150px;float:left;"><label>Funding Source Name:</label></span>\
			<span style="float:right;width:300px"><input id="txtSourceName' + id +'" type="text" value="'+name+'" size="25"></p><br />\
			<p style="width:500px;"><span style="width:150px;float:left;"><label>Calculation Type:</label></span>\
			<span style="float:right;width:300px"><select id="selSourceType' + id +'">';
			for(i=1; i<types.length; i++){
				typeSelected = (i==type) ? "SELECTED" : "";
				f += '<option value="'+ i +'" '+ typeSelected +'>'+types[i]+'</option>';
			}
		f +='</select></span></p><br />\
			<p><input type="submit" value="Add Funding Source"></p>';
		$("frmSource"+id).innerHTML = f;

	}

	function createSource(){
		var name = $F('txtSourceName');
		var type = $F('selSourceType');
		//alert('name: ' + name + ' type: ' + type);
		Util.loading(true,"Working");
		FundingAgent.createFundingSource({name:name, type:type}, {
			callback:function(data){
				if (data.successful){
					getFundingSources();
				}else{
					alert(data.reason);
				}
			Util.loading(false)
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
		Util.loading(true,"Working");
		FundingAgent.editFundingSource({id:id, name:name, type:type}, {
			callback:function(data){
				if(data.successful){
					getFundingSources();
					setTimeout(function() {new Effect.Highlight('source-'+ id, {duration:5});}, 200);
				}else{
					alert(data.reason);
				}
			Util.loading(false)
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
			Util.loading(true,"Deleting funding source..");
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
		}Util.loading(false);
	}
	
	/* *************** STARTING ALTERNATIVE FUNCTIONS *************** */
	/* *************** STARTING ALTERNATIVE FUNCTIONS *************** */
	
	function prepareSourceAlt(alt, idType){
		(idType=="altId") ? getSourceAltById(alt) : renderSourceAltForm(alt);
		Element.toggle('alternativeForm'+ alt);
	}
	
	function getSourceAltById(id){
		Util.loading(true,"Loading funding source");
		FundingAgent.getFundingSourceAltById({id:id}, {
			callback:function(data){
				if(data.successful){
					renderSourceAltForm(data.alternative);
				}else{
					alert(data.reason);
				}
			Util.loading(false)
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
		peakHourTripsRate = (alt) ? alt.peakHourTripsRate : "";
		offPeakTripsRate = (alt) ? alt.offPeakTripsRate : "";

		
		f = '<h4>Editing Funding Source Alternative</h4>\
			<div style="clear:both">\
			</div>\
			<div>\
			<p style="float:left;margin-right:20px;"><label>Funding Source Alternative Name</label><br />\
			<input id="txtAltName'+ altId +'" type="text" value="'+ name +'" style="width:312px;"></p>\
			<p style="float:left;margin-right:20px;"><label>Revenue (<em>ex. 45000000</em>)</label><br />\
			$ <input id="txtAltRevenue'+ altId +'" type="text" value="'+ revenue +'" style="width:150px;"></p>\
			<p style="float:left;"><label>Tax Rate</label><br />\
			<input id="txtAltTaxRate'+ altId +'" type="text" value="'+ taxRate +'" style="width:110px;"> %</p><br />\
			<div style="clear:both">\
			</div>\
			<p style="float:left;margin-right:20px;"><label>Source URL</label><br />\
			<input id="txtAltSourceURL'+ altId +'" type="text" value="'+ sourceURL +'" style="width:312px;"></p>\
			<p style="float:left;margin-right:20px;"><label>Annual Cost to Average Resident</label><br />\
			$ <input id="txtAltAvgCost'+ altId +'" type="text" value="'+ avgCost +'" style="width:50px;"></p>\
			<div style="clear:both">\
			</div>\
			<label>Is this a toll? </label>\
			<input id="ckbxToll'+ altId +'" type="checkbox" onClick="Element.toggle(\'ifTolls'+ altId +'\')" value="'+ toll +'" '+tollChecked+'><br />\
			<div id="ifTolls'+altId+'" style="display:none;">\
				<div><p style="float:left;margin-right:50px;"><label>Peak Rate</label><br />\
				$ <input id="txtAltPeakHourTripsRate'+ altId +'" type="text" value="'+ peakHourTripsRate +'" size="5"></p>\
				<p style="float:left"><label>Off Peak Rate</label><br />\
				$ <input id="txtAltOffPeakTripsRate'+ altId +'" type="text" value="'+ offPeakTripsRate +'" size="5"></p>\
			<div style="clear:both"></div></div></div>\
			<p><input style="padding:5px" type="submit" value="Submit"></p>\
			</div>\
			</div>';
		

		$("frmSourceAlt"+altId).innerHTML = f;
		(tollChecked) ? Element.toggle('ifTolls'+altId) : "";
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
		Util.loading(true,"Working");
		FundingAgent.createFundingSourceAlt({id:id,name:name,revenue:revenue,taxRate:taxRate,source:sourceURL,avgCost:avgCost,toll:tollFormatted,peakHourTrips:peakHourTripsRate,offPeakTrips:offPeakTripsRate}, {
			callback:function(data){
				if (data.successful){
					getFundingSources();
				}else{
					alert(data.reason);
				}
			Util.loading(false)
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
			Util.loading(true,"Deleting funding source");
			FundingAgent.deleteFundingSourceAlt({id:id}, {
				callback:function(data){
					if(data.successful){
						new Effect.Puff("alt-" + id);
					}else{
						alert(data.reason);
					}
				Util.loading(false)
				},
				errorHandler:function(errorString, exception){
					alert("FundingAgent.deleteAltnerative( error:" + errorString + exception);
				}
			});
		}
		Util.loading(false)
		
	}

	/* *************** edit a given funding source alternative *************** */	
	function editSourceAlt(id){
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
		
		alert("id: " + id + " name: " + name + " revenue: " + revenue + " taxRate: " + taxRate + " source: " + sourceURL + " avgCost: " + avgCost + " toll: " + tollFormatted + " peakHourTrips: " + peakHourTripsRate + " offPeakTrips: " + offPeakTripsRate)
		Util.loading(true,"Working");
		FundingAgent.editFundingSourceAlt({id:id,name:name,revenue:revenue,taxRate:taxRate,source:sourceURL,avgCost:avgCost,toll:tollFormatted,peakHourTrips:peakHourTripsRate,offPeakTrips:offPeakTripsRate}, {
			callback:function(data){
				if(data.successful){
					getFundingSources();
					setTimeout(function() {new Effect.Highlight('alt-'+ id, {duration:5});}, 200);
				}else{
					alert(data.reason);
				}
			Util.loading(false)
			},
			errorHandler:function(errorString, exception){
				alert("FundingAgent.editFundingSourceAlt( error:" + errorString + exception);
			}
		});
		
	}
	
</script>
<event:pageunload />
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
	
	<h3>Finished managing funding sources?</h3>
	<!-- this button just redirects - saves are occuring on check. -->
	<p><input type="button" style="padding:5px" onClick="location.href='userhome.do'" value="Finished!"/></p>
</body>
</html:html>

