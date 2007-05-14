<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<title>LIT - Explore candidate projects</title>
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAADmWGO07Q7ZeAHCvFNooqIxT8LAKGhtP9maYhJLTjx4IxqGpsgRTey8zEXlF355TIxu1rYbhBNl_RdQ"
            type="text/javascript"></script>
<script type='text/javascript' src='/pgistws/dwr/interface/ProjectServlet.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<style type="text/css">
v\:* {
  behavior:url(#default#VML);
}
</style>

<script type="text/javascript">
//<![CDATA[

function load() {
  if (GBrowserIsCompatible()) {
	map = new GMap2(document.getElementById("map"));
	map.addControl(new GLargeMapControl());
	map.addControl(new GMapTypeControl());
	map.setCenter(new GLatLng(47.65985278,-122.3224667), 13);
  }
}

//]]>
  var projectList  = null;
  var currentpage = 0;
  var projects = new Array();
  var map;
  
  var editProject;
  var inputpoints = [];
  var inputline = null;
  var updateonthefly = false;
  var multiplepoints = false;
  
  function getProjects(){//startRow
  	startrow = currentpage * parseInt($("selnumperpage").value);	
	
	$("prjlist").innerHTML = "<a style='background-color:#FF3300'>Loading data from PGIST databases ...</a>";

	ProjectServlet.getProjects(displayProjects, $("selsort").value, $("selfilter").value, parseInt($("selnumperpage").value), startrow);  //startRow
  }
  
  function displayProjects(data){
	var content = "<table border=0 cellspacing=0 class=prjtable>";
	for(i=0; i<data.length; i++){
		content += "<tr><td class=prjindex><input type='checkbox' name='prjsel' value='" + data[i]['id'] + "' /> </td>";
		content += "<td class=prjtitle><a class='voida' href='javascript:void(0)' onclick='displayProjectInfo(";
		content += data[i]['id'] + ")'><b>" + data[i]['projectTitle'] + ":&nbsp;</b>" + data[i]['projectDesc'] + " (" + data[i]['sponsorLead'] + ")";
		content += "[project ID: " + data[i]['id'] + "]";
		if($("selsort").value == "status")
			content += "(" + data[i]['approvalStatus'] + ") </a></td></tr>" ;
		else
			content += "</a></td></tr>" ;
	}
		
	content += "</table>";
	DWRUtil.setValue("prjlist", content);
	projectList = data;
  }
  
  function setPage(n0){	
	var pagination = "Page";
	currentpage = n0;
	n = n0 +1;
	totalp = Math.round( totalnumproj/parseInt($("selnumperpage").value) );// + 1;
	if( n > totalp )n = totalp;  //in case total page number changs - use n not n0!!!
	
	p = 1;
	if(totalp == 0)
		pagination += '<b>' + p + '</b>&nbsp;';
	else
		while(p <= totalp){
			if(p == n)
				pagination += '<b>' + p + '</b>&nbsp;';		
			else if( (p < 5) || (n-2 <= p && p <= n+2 ) || (p > totalp-5) )
				pagination += '<a href="javascript:void();" onclick="setPage(' + (p-1) + ')">' + p + '</a>&nbsp;';
			else if(4<p && p<n-2){
				pagination += '<a href="javascript:void();" onclick="setPage(' + (n-6) + ')"> ... </a>&nbsp;';
				p = n-3;
			}else if(n+2<p && p<totalp-5){
				pagination += '<a href="javascript:void();" onclick="setPage(' + (n+4) + ')"> ... </a>&nbsp;';
				p = totalp-5;
			}
				
			p++;
		}
	
	DWRUtil.setValue("pagination", pagination);
	getProjects();
  }
  
	function createMarker(point, info)
	{
		var marker = new GMarker(point);
		var html =  info;
		GEvent.addListener(marker, "click", function() {marker.openInfoWindowHtml(info);});
		return marker;
	};
  
  
  function clearProjectData(){
  	for(i=0; i<projects.length;i++){
		projects[i][0] = null;	//marker
		projects[i][1] = null; //project data
	}
  }
  
  function mapProject(data){
	map.clearOverlays();
	if(data != null){
		if(data.length > 0){
			var points = [];
			var minLong = 200, minLat = 100, maxLong = -200, maxLat = 0;
			for(i=0; i<data.length; i=i+2){
				points.push ( new GPoint(data[i],data[i+1]) );
				$('errormessage').innerHTML += "" + data[i+1] +"," + data[i] + ",<br>";
				if(data[i]<minLong) minLong = data[i];
				if(data[i]> maxLong) maxLong = data[i];
				if(data[i+1]<minLat) minLat = data[i+1];
				if(data[i+1]>maxLat) maxLat = data[i+1];		
			}
			
			if(editProject.spatial_type == 1){
				for(i=0;i<points.length;i++)
					map.addOverlay(new GMarker(points[i]));
			}else{
				map.addOverlay(new GPolyline(points), "#0000ff", 8, 0.5 );
			}
			
			adjustMapExtent(minLong, maxLong, minLat,maxLat);
		}
	}
  }

  function adjustMapExtent(minLong, maxLong, minLat,maxLat){
	var center = new GLatLng( (maxLat+minLat)/2, (maxLong+minLong)/2 );
	//var delta = new GSize( maxLong-minLong, maxLat-minLat);
	//var minZoom = map.spec.getLowestZoomLevel(center, delta, map.viewSize);
	if((maxLong-minLong) > 0.5)
		zoomlevel = 8;
	else
		zoomlevel = 12;
		
	map.setCenter(center, zoomlevel); 
  }
  
  function showMarkerInfo(i){
  	projects[i][0].openInfoWindowHtml(projects[i][1]['project_title']);
  }
  
  function displayProjectInfo(id){
	if(id == 0)alert("project id is 0!!!"); //scen = projectList[id];
	
	for(i=0; i<projectList.length; i++)
		if(projectList[i]['id'] == id){
			editProject = projectList[i];
			break;
		}

	//make the scenario content to be displayed
	projcontent = '<table width="100%" border="0" style="padding-top:10px;">';
    projcontent += '<tr><td>';
    
	projcontent += '<br><b>Project title: </b>' + editProject['projectTitle'] + "<br><br>";
	projcontent += '<b>Project description: </b>' + editProject['projectDesc'] + "<br><br>";
	projcontent += '<b>Current fund total: </b>' + editProject['curFundTotal'] + "<br><br>";
	projcontent += '<b>Sponsor lead: </b>' + editProject['sponsorLead'] + "<br><br>";
	projcontent += '<b>Completion year: </b>' + editProject['compDate'] + "<br><br>";
	projcontent += '<b>Approval status: </b>' + editProject['approvalStatus'] + "<br>";

    projcontent += '</td></tr></table>';
	
	DWRUtil.setValue("prjdatasgeet", projcontent);
	if(editProject.spatial_oid != null){
		ProjectServlet.getGeometry(mapProject, editProject.spatial_type, editProject.spatial_oid);
	}else
		if(map!=null)map.clearOverlays();
  }

var mapListener = null;

function startInputPoint(){
	stopInputLine();
	clearInputLine();
	
	mapListener = GEvent.addListener(map, "click", function(marker, point) {
	  if (marker == null) {
		
		if(!multiplepoints){
			map.clearOverlays();
			inputpoints = [];
		}
		
		map.addOverlay(new GMarker(point));
		inputpoints.push(point);
	  }
	});
}
function saveInputPoint(){
	if(inputpoints.length == 0){
		alert("Please define at least one point.");
		return;
	}
	
	var coords = new Array();
	for(i=0; i<inputpoints.length; i++){
		coords[i*2] = inputpoints[i].x;
		coords[i*2 + 1] = inputpoints[i].y;
	}

	ProjectServlet.createFootprint(coords, 1, editProject.id, function(data){
		if(data.successful){
			alert("Successful!");
			editProject.spatial_oid = data.spatial_oid;
			editProject.spatial_type = data.spatial_type;
			ProjectServlet.getGeometry(mapProject, editProject.spatial_type, editProject.spatial_oid);
		}else{
			alert("Oops, there is an error: " + data.reason);
			$('errormessage').innerHTML = data.reason;
		}
	});

}

function startInputLine(){
	stopInputLine();
	clearInputLine();
	
	GEvent.addListener(map, "click", function(marker, point) {
	  if (marker == null) {
		map.addOverlay(new GMarker(point));
		
		inputpoints.push(point);
		
		if(updateonthefly)
			showInputLine();
	  }
	});	
}
function showInputLine(){
		if(inputline != null){
			map.removeOverlay(inputline);
			inputline = null;
		}
		inputline = new GPolyline(inputpoints);
		if(inputpoints.length > 1)
			map.addOverlay(inputline, "#00ffff", 8, 0.5 );	
}

function stopInputLine(){
	GEvent.clearListeners(map, "click");
}

function saveInputLine(){
	if(inputpoints.length < 2){
		alert("Please make at least 2 points are needed to make a line.");
		return;
	}
	
	var coords = new Array();
	for(i=0; i<inputpoints.length; i++){
		coords[i*2] = inputpoints[i].x;
		coords[i*2 + 1] = inputpoints[i].y;
	}

	ProjectServlet.createFootprint(coords, 2, editProject.id, function(data){
		if(data.successful){
			alert("Successful!");
			editProject.spatial_oid = data.spatial_oid;
			editProject.spatial_type = data.spatial_type;
			ProjectServlet.getGeometry(mapProject, editProject.spatial_type, editProject.spatial_oid);
		}else{
			alert("Oops, there is an error: " + data.reason);
			$('errormessage').innerHTML = data.reason;
		}
	});
	
}
function clearInputLine(){
	map.clearOverlays();
	inputline = null;
	inputpoints = null;
	inputpoints = [];
}

function borrowFootprint(){
	var fromProject = null;
	var borrowId = parseInt($('borrowId').value);
	if(borrowId <= 0)return;
	
	for(i=0; i<projectList.length; i++)
		if(projectList[i]['id'] == borrowId){
			fromProject = projectList[i];
			break;
		}
	
	if(fromProject.spatial_oid == null || fromProject.spatial_oid < 0){
		alert("No footprint if defined for the project to use.");
		return;
	}
	
	//alert(fromProject.spatial_type + "; " + fromProject.spatial_oid);
	ProjectServlet.useFootprint(editProject.id, fromProject.spatial_type, fromProject.spatial_oid, function(data){
		if(data.successful){
			alert("Successful!");
			editProject.spatial_type = fromProject.spatial_type;
			editProject.spatial_oid = fromProject.spatial_oid;
			ProjectServlet.getGeometry(mapProject, editProject.spatial_type, editProject.spatial_oid);
		}
	});
}

function savePolygon(){
	spatial_oid = parseInt($('selCounties').value);

	ProjectServlet.useFootprint(editProject.id, 3, spatial_oid, function(data){
		if(data.successful){
			alert("Successful!");
			editProject.spatial_type = 3;
			editProject.spatial_oid = spatial_oid;
			ProjectServlet.getGeometry(mapProject, editProject.spatial_type, editProject.spatial_oid);
		}
	});
	
}
    
</script>
<style type="text/css">
<!--
.portlettitle {
	background-color: #FFDCA8;
	margin: auto;
	border-top-width: 1px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
	border-top-color: #FF9853;
	padding-left: 5px;
}
body {
	font-family: "Arial", "Helvetica", "sans-serif";
	font-size: small;
}
.prjindex {
	text-align: center;
	vertical-align: top;
	background-color: #EEEEEE;
	color: #330066;
	border-top: 1px solid #CCCCCC;
	border-left: 1px solid #CCCCCC;
	padding-top: 2px;
	padding-bottom: 2px;
}
.prjtitle {
	text-align: left;
	vertical-align: top;
	border-top: 1px solid #CCCCCC;
	border-right: 1px solid #CCCCCC;
	border-left: 1px solid #CCCCCC;
	padding-top: 2px;
	padding-bottom: 2px;
	padding-left: 5px;
}

-->
</style>
<style type="text/css">
<!--
.prjlist {
	padding-top: 5px;
	padding-right: 5px;
	padding-bottom: 5px;
}
.prjtable {
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #CCCCCC;
}
.voida {
	color: #000000;
	text-decoration: none;
}
-->
</style>
<event:pageunload />
</head>
<body onunload="GUnload()">
<p>PGIST user experiment - prepare project data</p>
  <table width="100%" border="0">
    <tr> 
      
    <td colspan="2" class="portlettitle"> <strong>Create project footprint</strong></td>
    </tr>
    <tr> 
      
    <td width="400" valign="top"><table width="100%" border="0">
        <tr>
          <td>Sort by 
            <select id="selsort">
              <option value="title" selected="selected">Project title</option>
              <option value="fund">Total fund</option>
              <option value="status">Approval status</option>
              <option value="lead">Sponsor lead</option>
              <option value="year">Completion year</option>
            </select>
            Filter 
            <select id="selfilter" onchange="applyFilter();">
              <option value="all">All projects</option>
              <option value="interested">Interested ones only</option>
              <option value="YBI" selected="selected">You build it</option>
              <option value="YBI-MEGA">You build it: mega</option>
              <option value="YBI-ROAD">You build it: road</option>
              <option value="YBI-TRANSIT">You build it: transit</option>
            </select>
            Number per page
            <select id="selnumperpage">
              <option value="10" selected="selected">10</option>
              <option value="25">25</option>
              <option value="50">50</option>
            </select>
            <input type="button" name="refreshprjlist" value="Refresh" onclick="setPage(currentpage)" /></td>
        </tr>
        <tr>
          <td><div id="prjlist"> </div></td>
        </tr>
        <tr>
          <td align="right">
<div id="pagination"></div></td>
        </tr>
        <tr>
          <td><div id="prjdatasgeet"></div><br>
		  &nbsp; </td>
        </tr>
      </table> 
    <td width="400" valign="top">
      <div id="map" style="width: 900px; height: 700px;"></div>
	  <div><hr>
	  <input type="button" value="Start new line" onclick="startInputLine()"> &nbsp;
	  <input type="button" value="Show line" onclick="showInputLine()">&nbsp;
	  <input type="button" value="Stop editing" onclick="stopInputLine()">&nbsp;
        <input type="button" value="Clear" onclick="clearInputLine()">
	  <input type="checkbox" id="updatefly" onclick="updateonthefly=!updateonthefly;">
        Show line on the fly (this might slow the display) 
        <input name="button" type="button" onclick="saveInputLine()" value="Save edits" />
        <hr>
		<input type="button" value="Select a point" onclick="startInputPoint()">
        &nbsp; 
        <input type="checkbox" name="chkMultiPnt" value="checkbox"  onclick="multiplepoints=!multiplepoints;"/>
        Allow multiple points &nbsp;
        <input name="button3" type="button" onclick="saveInputPoint()" value="Save point" />
        <hr />
        This project applies to (select one) 
        <select id="selCounties" size="4">
          <option value="3" selected="selected">King County</option>
          <option value="1">Snohomish County</option>
          <option value="4">Pierce County</option>
          <option value="1">Kitsap County</option>
        </select>
        <input name="button22" type="button" value="Set" onclick="savePolygon()" />
        , or use the foorprint of project (ID) 
        <input name="text" type="text" id="borrowId" size="6" />
        &nbsp;
        <input name="button2" type="button" value="Set" onclick="borrowFootprint()" />
        <hr>
      </div>
	<div id="errormessage"></div>
     </td>
    </tr>
  </table>

<script language="JavaScript">
  var totalnumproj;
  var currentFilter = "YBI";
  ProjectServlet.getProjectsTotal(setTotalNum, currentFilter);
  function setTotalNum(data){
  	totalnumproj = parseInt(data);
	setPage(currentpage);	
  }
  
  function applyFilter(){
  	 currentFilter = $("selfilter").value;
	 ProjectServlet.getProjectsTotal(setTotalNum, currentFilter);
  }
  
  load();
</script>
</body>
</html>
