<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<title>LIT - Explore candidate projects</title>
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAADmWGO07Q7ZeAHCvFNooqIxTwM0brOpm-All5BF6PoaKBxRWWERTgXzfGnh96tes2zXXrBXrWwWigIQ" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/interface/ProjectAgent.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>

<script type='text/javascript' src='/scripts/maps.js'></script>


<style type="text/css">
v\:* {
  behavior:url(#default#VML);
}
</style>

<script type="text/javascript">
//<![CDATA[

/**
 * function <b>load()</b>: do 3 things:
 * <ul>
 * <li> 1 Initiate the map, with name 'map';
 * <li> 2 Configure the map so the scrool wheel works for zoom in/out
 * <li> 3 Load all projects data.
 * </ul>
 * <b>when to use: </b> page load or equavelent
 */
function load() {
  if (GBrowserIsCompatible()) {
	map = new GMap2(document.getElementById("map"),{mapTypes: [G_SATELLITE_MAP,G_NORMAL_MAP]});
	map.addControl(new GLargeMapControl());
	map.addControl(new GMapTypeControl());
	map.setCenter(new GLatLng(47.65985278,-122.3224667),12,G_NORMAL_MAP);
	map.enableContinuousZoom();
	map.enableDoubleClickZoom();
	map.mouseWheel = true;
	map.setMapType(G_SATELLITE_MAP);

	
	GEvent.addDomListener(document.getElementById("map"), "DOMMouseScroll",wheelZoom); // Firefox
	GEvent.addDomListener(document.getElementById("map"), "mousewheel",	wheelZoom); // IE
	
//	GEvent.addListener(map, "zoomend", function(oldlevel, newlevel){
//		redrawBubbles(editProject);
//	});
  }

   document.afterDataLoad = function(){
		alert('after load');
	  };
  
  getProjects();
}

function showAllProjects(){
	map.clearOverlays();
	for(i=0; i<projectList.length; i++){
		if(projectList[i].fpids && projectList[i].fpids!=""){	
			redrawProjectFootprint(projectList[i]);
			for(var j=0; j<projectList[i].bubblemarkers.length; j++){
				map.removeOverlay(projectList[i].bubblemarkers[j]);
				map.addOverlay(projectList[i].bubblemarkers[j]);
			}			
		}
	}
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
   	  <td width="40%" class="portlettitle"> <strong>Create project attributes</strong>
		<select id="prjlist" onchange="showProjectInfo(this.value,true);">
		</select>		</td>
    	<td width="60%" class="portlettitle"> <strong>Create project footprint</strong>&nbsp;<span id="fpeditmsg">no project selected (created)</span><input type="button" value="Edit footprint" onclick="alert(editProjectId);$('editfpoptions').style.display ='inline';" />
		<input type="button" value="Show all projects" onclick="showAllProjects()" />
		</td>
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
			<td><textarea id="prjdescp" cols="35" rows="6"></textarea></td>
		  </tr>
		  <tr>
			<th valign="top" scope="row"><div align="right">Cost:</div></th>
			<td><input type="text" id="prjcost" /></td>
		  </tr>
		  <tr>
			<td colspan="2"><div align="center">
			  <input type="button" value="Save project attributes" onclick="saveProject();" />
			  </div></td>
		  </tr>
	</table>
	  <hr />
	  <div style="background-color:#FDFDFD" id="prjalttitle">This project has no alternative(s) (yet).
	  </div>
	  <table width="100%" border="0" id="projaltern">
		  <tr>
			<th valign="top" scope="row"><div align="right">Name:</div></th>
			<td><input type="text" id="altname" /></td>
		  </tr>
		  <tr>
			<th valign="top" scope="row"> <div align="right">Description:&nbsp;</div></th>
			<td><textarea name="textarea" id="altdescp" cols="35" rows="6"></textarea></td>
		  </tr>
		  <tr>
			<th valign="top" scope="row"><div align="right">Cost:</div></th>
			<td><input type="text" id="altcost" /></td>
		  </tr>
		  <tr>
			<td colspan="2"><div align="center">
			  <input type="button" value="Save alternative" id="enablealtedit" onclick="saveAlternative();" disabled="disabled" />
			  </div></td>
		  </tr>
	</table>

	</td>
  <td>
  <table width="100%" border="1" id="editfpoptions" style="display:none">
  <tr>
    <td width="100"><form name="fpparams" id="fpparams">
	<input type="radio" id="fptype" name="fptype" value="POINT" onclick="startInputPoint();" />&nbsp;Point(s)</td>
    <td><input type="checkbox" name="chkMultiPnt" value="checkbox"  onclick="multiplepoints=!multiplepoints;"/>
Allow multiple points
	</td>
    <td width="100" rowspan="3">
	  <div align="center">
	    <input name="button54" type="button" onclick="clearInput()" value="Clear" />
	    <input type="button" id="btnsavefp" value="Save footprint" onclick="saveFootprint();"/>	
	    </div></td>
  </tr>
  <tr>
    <td><input id="fptype" name="fptype" type="radio" value="LINE" checked="checked" onclick="startInputLine()"/>
    &nbsp;Line(s)</td>
    <td>  
  <input type="button" value="Start a new part" onclick="startInputLine()" />
  <input type="button" onclick="stopInputLine()" value="Stop" />
  <input type="button" onclick="deleteLastPoint()" value="Delete last point" /></td>
    </tr>
  <tr>
    <td><input type="radio" id="fptype" name="fptype" value="POLYGON" />
      &nbsp;Region(s)</td>
    <td>
		<input type="checkbox" name="county" value="126" />&nbsp;King County
		<input type="checkbox" name="county" value="93" />&nbsp;Snohomish County
		<input type="checkbox" name="county" value="153" />&nbsp;Pierce County
		<input type="checkbox" name="county" value="117" />&nbsp;Kitsap County	
		</form>		
	</td>
    </tr>

</table>

      <div id="map" style="width: 900px; height: 700px;">
      </div>
     </td>
    </tr>
  </table>

<script language="JavaScript">
  load();
  
  pollHash();
</script>
</body>
</html>
