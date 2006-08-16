<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<title>LIT - Explore candidate projects</title>
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAADmWGO07Q7ZeAHCvFNooqIxTwM0brOpm-All5BF6PoaKBxRWWERTgXzfGnh96tes2zXXrBXrWwWigIQ" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/interface/ProjectAgent.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
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
  var editProjectId = -1;
  var inputpoints = new Array();
  var inputline = null;
  var updateonthefly = false;
  var multiplepoints = false;
  var coords = new Array();

  var mapListener = null;
    
  function createMarker(point, info)
	{
		var marker = new GMarker(point);
		var html =  info;
		GEvent.addListener(marker, "click", function() {marker.openInfoWindowHtml(info);});
		return marker;
	};
    
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
var mapListerner = null;
function startInputLine(){
	//stopInputLine();
	//clearInputLine();
	if(coords.length>0 && coords[coords.length-1].length<2){
		alert("Can't create another part until the last segment has at least two points.");
		return;
	}

	//alert("now working on part #" + (coords.length+1));
	if(inputline != null){
		map.removeOverlay(inputline);
		map.addOverlay(new GPolyline(coords[coords.length-1], "#000000", 6, 0.6) );
	}
	
	coords[coords.length]=new Array();
	inputpoints = coords[coords.length - 1];
	
	if(mapListerner)GEvent.removeListener(mapListerner);
	mapListerner = GEvent.addListener(map, "click", function(marker, point) {
	  if (marker == null) {
		map.addOverlay(new GMarker(point));
		
		inputpoints.push(point);
		
		showInputLine();
	  }
	});	
}//startInputLine

function showInputLine(){
		if(inputline != null){
			map.removeOverlay(inputline);
			inputline = null;
		}
		inputline = new GPolyline(inputpoints);
		if(inputpoints.length > 1)
			map.addOverlay(inputline);	
}

function stopInputLine(){
	GEvent.clearListeners(map, "click");
	map.clearOverlays();
	redrawAllLines(coords);
	inputline = null;
	inputpoints = null;
}

function saveFootprint(){
	if(editProjectId==-1)return;
	
	var fptype = "LINE";
	for(i=0; i<document.forms['fpparams'].elements['fptype'].length; i++){
		if(document.forms['fpparams'].elements['fptype'][i].checked){
			fptype = document.forms['fpparams'].elements['fptype'][i].value;
			break;
		}
	}
	
	if(!inputpoints)inputpoints = coords[coords.length-1];
	if(inputpoints.length < 2){
		alert("Please make at least 2 points are needed to make a line.");
		return;
	}
	
	var serialcoords = new Array(coords.length);
	for(i=0; i<coords.length; i++){
		serialcoords[i] = new Array(coords[i].length*2);
		for(j=0; j<coords[i].length*2;j=j+2){
			serialcoords[i][j] = coords[i][j/2].x;
			serialcoords[i][j+1] = coords[i][j/2].y;
		}
	}
	$('errormessage').innerHTML = serialcoords;
	
	ProjectAgent.saveFootprint({pid:editProjectId,
								coords:serialcoords},
								{callback:function(data){
											if(data.successful){
												alert("footprint saved..");
											}else{
												alert(data.reason);
											}
											}
	});
}

function deleteLastPoint(){
	if(inputpoints){
		inputpoints.pop();
		showInputLine();
	}
}

function redrawAllLines(lines){
	map.clearOverlays();
	if(!lines)return;
	for(i=0; i<lines.length; i++)
		map.addOverlay(new GPolyline(coords[i], "#000000", 6, 0.6) );
		
}

function clearInputLine(){
	alert(coords);
	map.clearOverlays();
	inputline = null;
	inputpoints = null;
	inputpoints = [];
	coords = null;
	coords = [];
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

function saveProject(){
	ProjectAgent.saveProject({name:$('prjname').value, 
							description: $('prjdescp').value,
							cost: $('prjcost').value},{callback:function(data){
								if(data.successful){
									editProjectId = data.pid;
									alert("Project saved with id = " + editProjectId);
								}
							}}
	);
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
</head>
<body onunload="GUnload()">
<p>PGIST user experiment - reatet/edit projcet data</p>
  <table width="100%" border="0">
    <tr> 
    	<td width="40%" class="portlettitle"> <strong>Create project attributes</strong></td>
    	<td width="60%" class="portlettitle"> <strong>Create project footprint</strong></td>
    </tr>
    <tr> 
      <td valign="top">
	  <table width="100%" border="0">
		  <tr>
			<th valign="top" scope="row"><div align="right">Name:</div></th>
			<td><input type="text" id="prjname" /></td>
		  </tr>
		  <tr>
			<th valign="top" scope="row"> <div align="right">Description:&nbsp;</div></th>
			<td><textarea id="prjdescp" cols="35" rows="8"></textarea></td>
		  </tr>
		  <tr>
			<th valign="top" scope="row"><div align="right">Cost:</div></th>
			<td><input type="text" id="prjcost" /></td>
		  </tr>
		  <tr>
			<td colspan="2"><div align="center">
			  <input type="button" value="Save project attributes" onclick="saveProject();" />
			  <input type="button" value="Create an alternative for this project" onclick="document.getElementById('projaltern').style.display='inline';" />
			  </div></td>
		  </tr>
	</table>
	  <hr />
	  <div style="background-color:#FDFDFD">This project has no alternative(s) (yet).</div>
	  <table width="100%" border="0" id="projaltern" style="display:none">
		  <tr>
			<th valign="top" scope="row"><div align="right">Name:</div></th>
			<td><input type="text" name="prjname" /></td>
		  </tr>
		  <tr>
			<th valign="top" scope="row"> <div align="right">Description:&nbsp;</div></th>
			<td><textarea name="textarea" cols="50" rows="8"></textarea></td>
		  </tr>
		  <tr>
			<th valign="top" scope="row"><div align="right">Cost:</div></th>
			<td><input type="text" name="prjcost" /></td>
		  </tr>
		  <tr>
			<td colspan="2"><div align="center">
			  <input type="button" value="Save project alternative" />
			  </div></td>
		  </tr>
	</table>

	</td>
  <td>
  <table width="100%" border="1"><form name="fpparams" id="fpparams">
  <tr>
    <td width="100"><input type="radio" id="fptype" name="fptype" value="POINT" />&nbsp;Point(s)</td>
    <td><input type="checkbox" name="chkMultiPnt" value="checkbox"  onclick="multiplepoints=!multiplepoints;"/>
Allow multiple points
  <input name="button4" type="button" onclick="startInputPoint()" value="Start" />
  <input name="button42" type="button" onclick="void();" value="Stop" /></td>
  <span id="pointsmsg"></span>
    <td width="100" rowspan="3">
	  <div align="center">
	    <input name="button54" type="button" onclick="clearInputLine()" value="Clear" />
	    <input type="button" name="btnsave" value="Save footprint" onclick="saveFootprint();" />	
	    </div></td>
  </tr>
  <tr>
    <td><input id="fptype" name="fptype" type="radio" value="LINE" checked="checked" />
    &nbsp;Line(s)</td>
    <td><input type="checkbox" name="chkMultiPnt2" value="checkbox"  onclick="multiplepoints=!multiplepoints;"/>
Allow multiple parts 
  
  <input type="button" value="Start a new part" onclick="startInputLine()" />
  <input type="button" onclick="stopInputLine()" value="Stop" />
  <input type="button" onclick="deleteLastPoint()" value="Delete last point" /></td>
    </tr>
  <tr>
    <td><input type="radio" id="fptype" name="fptype" value="POLYGON" />
      &nbsp;Region(s)</td>
    <td>
		<input type="checkbox" name="county" value="1" />&nbsp;King County
		<input type="checkbox" name="county" value="1" />&nbsp;Snohomish County
		<input type="checkbox" name="county" value="1" />&nbsp;Pierce County
		<input type="checkbox" name="county" value="1" />&nbsp;Kitsap County			
	</td>
    </tr>
</form>
</table>

      <div id="map" style="width: 900px; height: 700px;">
      </div>
	  <div id="errormessage"></div>
     </td>
    </tr>
  </table>

<script language="JavaScript">
  var totalnumproj;
  var currentFilter = "YBI";

  load();
</script>
</body>
</html>
