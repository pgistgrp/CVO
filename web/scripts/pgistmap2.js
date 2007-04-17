// JavaScript Document
var PGISTMapper_Global_Accessor = null;
var PGISTGeometryType = {4:"POINT", 5:"LINE", 6:"POLYGON"};


//===========================
//class 2: PGISTMapEditor

/* 
 * Class PGISTMapEditor: used to handle map interactions when digitizing on a GMap
 * The constructor needs a GMap component
 * @params
 * mapcontainer, width, height: the DIV element that is used to contain the map, and width and height of the map
 * enableEdit: boolean, whether or not to ebable editing
*/
var PGISTMapEditor_Global_Accessor = null;
var PGISTMapEditor = function(mapcontainer, width, height, enableEdit) {
	if (!GBrowserIsCompatible()) {
		alert("Sorry, this browser is not compatible with Google Maps.");
		return null;
	}
	
	if(typeof(mapcontainer)!='object')
		mapcontainer = document.getElementById(mapcontainer);

	this._mapdiv = document.createElement("div");
	width = width || 600;
	height = height || 400;
	
	this.map = new GMap2(this._mapdiv,{size:new GSize(width, height),mapTypes: [G_SATELLITE_MAP,G_NORMAL_MAP]});
	this.map.addControl(new GLargeMapControl());
	this.map.addControl(new GMapTypeControl());
	this.map.setCenter(new GLatLng(47.65985278,-122.3224667),12,G_NORMAL_MAP);
	this.map.enableContinuousZoom();
	this.map.enableDoubleClickZoom();
	this.map.mouseWheel = true;
	this.map.setMapType(G_NORMAL_MAP);
	
	if(enableEdit){
		this.map.addControl( new PGISTMapEditToolsControl() );
	}
	
	this.allowMultiplePoints = false;
	this.mapListener = null;
	this.editGeomType = "POINT";	//default to point

	//these variables are only useful when editing projects  
	this.editProject = null;
	this.editProjectId = -1;
	this.inputpoints = new Array();
	this.inputline = null;

	this.coords = [];
	this.coords[0] = [];	//initialize with one point series

	this.editicon = new GIcon(G_DEFAULT_ICON); 
	this.editicon.image = "images/_pointicon.png";
	this.editicon.iconSize = new GSize(16,16);
	this.editicon.iconAnchor = new GPoint(8,8);
	this.editicon.shadow = "";
	this.editicon.infoWindowAnchor = new GPoint(8,8);
	
	if(saveFootprint)
		this.saveHandler = saveFootprint;
	else
		this.saveHandler = null;
	
	mapcontainer.appendChild(this._mapdiv);
	this._container = mapcontainer;

	PGISTMapEditor_Global_Accessor = this;
};

/**
 * Change the container of the map to the new target
 */
PGISTMapEditor.prototype.changeToContainer = function(newcontainer){
	if(typeof(newcontainer)!='object')
		newcontainer = document.getElementById(newcontainer);

	this._container.removeChild(this._mapdiv);
	newcontainer.appendChild(this._mapdiv);
	this._container = newcontainer;
};

/**
 * Hide/show the map
 */
PGISTMapEditor.prototype.swithchDisplay = function(){
	var vis = this._container.style.display;
	vis = (vis=="none")?"":"none";
	this._container.style.display = vis;
};

/*
  Todo:
 [] showProjectAlt(long altId, boolean include): add alternative to display; adjust map extent to maximum
 [] prepareOverlays(): have all G overlays in a dictionary to be fetched by fpid
 [] highlightAlt(long altId): remove regular diaplay of an alternative, add highlight display of that alternative
 [] unhighlightAlt(long altId): reverse the highlight process
 [] -->> for "map all": symbols; mouse over/out; mouse click;
 [] -->> for "map single": symbol; mouse over/out; mouse click;
 [] -->> for "map package": symbol; mouse over/out; mouse click;
*/


/*-----------------Editing interactions-----------------------*/  
/**
 * Make the map ready to accept clicks as input for points
 * @return
 * <b>when to use: </b> begin to click and input points
 */
PGISTMapEditor.prototype.startInputPoints=function(){
	if(this.editGeomType != "POINT"){
		for(i=0; i<this.coords.lenth; i++){
			this.coords[i]	= null;
		}
		this.map.clearOverlays();
	}

	this.editGeomType = "POINT";
	this.clearInput();
	
	this.coords[0] = [];
	
	if(this.mapListener)GEvent.removeListener(this.mapListener);

	this.mapListener = GEvent.addListener(this.map, "click", function(marker, point) {
		if (marker == null) {		
			if(!PGISTMapEditor_Global_Accessor.allowMultiplePoints){
				PGISTMapEditor_Global_Accessor.map.clearOverlays();
				PGISTMapEditor_Global_Accessor.coords[0] = null;
				PGISTMapEditor_Global_Accessor.coords[0] = [];	//clear it every time
			}
			
			PGISTMapEditor_Global_Accessor.map.addOverlay(new GMarker(point , PGISTMapEditor_Global_Accessor.editicon));
			PGISTMapEditor_Global_Accessor.coords[0].push(point);
		}
	});
};


/**
 * Make the map ready to accept clicks as input for a line or a part of multi-part line 
 * @return
 * <b>when to use: </b> when "line" is selected as the geometry type
 */
PGISTMapEditor.prototype.startInputLine=function(){
	if(this.editGeomType != "LINE"){
		this.coords[0]	= null;  this.coords[0] = [];
		this.map.clearOverlays();
	}
	
	this.editGeomType = "LINE";
	
	var currentpart = this.coords.length-1;
	if(this.coords[currentpart].length > 1){ //previous is OK, make a new part
		//fix the display of the last part, avoid multiple overlay of it
		if(this.inputline != null){
			this.map.removeOverlay(this.inputline);
			this.map.addOverlay(new GPolyline(this.coords[currentpart], "#000000", 6, 0.6) );
		}
		
		this.coords[++currentpart] = [];
	}

	if(this.mapListener)
		GEvent.removeListener(this.mapListener);
	
	this.mapListener = GEvent.addListener(this.map, "click", function(marker, point) {
			if (marker == null) {
				PGISTMapEditor_Global_Accessor.map.addOverlay(new GMarker(point,PGISTMapEditor_Global_Accessor.editicon));			
				PGISTMapEditor_Global_Accessor.coords[PGISTMapEditor_Global_Accessor.coords.length-1].push(point);
				PGISTMapEditor_Global_Accessor.showInputLine();
			}
	});
};//startInputLine

/**
 * Make the map ignore any clicks affecting the input line
 * @return
 * <b>when to use: </b> when the stop editting button is clicked
 */
PGISTMapEditor.prototype.stopInput=function(){
	GEvent.clearListeners(this.map, "click");
	this.map.clearOverlays();
	
	if(this.editGeomType == "POINT"){
		this.drawPoints();
	}else if (this.editGeomType == "LINE"){
		this.drawLines();
	}
	
	//clear out buffered input
	this.inputline = null;
};

/**
 * Redraw all lines being edited
 * @return
 * <b>when to use: </b> 
 */
PGISTMapEditor.prototype.drawLines = function(){
	//this.map.clearOverlays();
	if(!this.coords)return;
	for(i=0; i<this.coords.length; i++)
		this.map.addOverlay(new GPolyline(this.coords[i], "#000000", 6, 0.6) );
		
};

/**
 * Redraw all polygons as defined by points in coords
 * @return
 * <b>when to use: </b> 
 */
PGISTMapEditor.prototype.drawPolygons = function(){
	//this.map.clearOverlays();
	if(!this.coords)return;
	for(i=0; i<this.coords.length; i++)
		this.map.addOverlay( new GPolygon(this.coords[i],"#FF0088",2,0.4,"#0000FF",0.2) );
};

/**
 * Redraw all lines being edited
 * @return
 * <b>when to use: </b> 
 */
PGISTMapEditor.prototype.drawPoints = function(){
	//this.map.clearOverlays();
	for(j=0; j<this.coords.length; j++){
		for(i=0; i<this.coords[j].length; i++){
			this.map.addOverlay(new GMarker(this.coords[j][i], this.editicon) );
		}
	}
};

/**
 * Clear whatever is on the map, and the editing buffer. Leave the map listener as is.
 * @return
 * <b>when to use: </b> 
 */
PGISTMapEditor.prototype.clearInput=function(){
	this.map.clearOverlays();
	this.inputline = null;
	this.inputpoints = null;
	this.inputpoints = [];
	this.coords = null;
	this.coords = [];
	this.coords[0] = [];
};

/**
 * Set the handler for the "save" button, and pass in a target Id
 * @return
 * <b>when to use: </b> 
 */
PGISTMapEditor.prototype.setSaveHandler=function(saveOp){
	this.saveHandler = saveOp;
};

/**
 * Put the coordinates from DWR back into the coords array.
 * @return
 * <b>when to use: </b> 
 */
PGISTMapEditor.prototype.recoverCoords=function(serialCoords){
	this.coords = [];
	for(var i=0; i<serialCoords[0].length; i++){
		this.coords[i] = [];
		for(var j=0; j<serialCoords[0][i].length; j=j+2){
			this.coords[i].push(new GPoint(serialCoords[0][i][j],serialCoords[0][i][j+1]));
		}
	}
};

/**
 * Make GPoints out of serialized coord array. This GPoint array can then be used to make GMarkers, GPolylines
 * or GPolygon overlays.
 * @return
 * <b>when to use: </b> 
 */
PGISTMapEditor.prototype.makeGPoints=function(serialCoords){
	var gpoints = [];
	for(var i=0; i<serialCoords[0].length; i++){
		gpoints[i] = [];
		for(var j=0; j<serialCoords[0][i].length; j=j+2){
			gpoints[i].push(new GPoint(serialCoords[0][i][j],serialCoords[0][i][j+1]));
		}
	}
	return gpoints;
};


/**
 * Zoom to coordinats.
 * @return
 * <b>when to use: </b> 
 */
PGISTMapEditor.prototype.scaleToCoords=function(){
	if(this.coords.length < 1) return;
	
	var minx = 180;
	var maxx = -180;
	var miny = 90;
	var maxy=-90;
	for(var i=0; i<this.coords.length; i++){
		for(var j=0; j<this.coords[i].length; j++){
			if(this.coords[i][j].x < minx) minx = this.coords[i][j].x;
			if(this.coords[i][j].x > maxx) maxx = this.coords[i][j].x;
			if(this.coords[i][j].y < miny) miny = this.coords[i][j].y;
			if(this.coords[i][j].y > maxy) maxy = this.coords[i][j].y;
		}
	}
	
	var bounds = new GLatLngBounds( new GLatLng(miny, minx), new GLatLng(maxy,maxx) );
	var zoomlevel = this.map.getBoundsZoomLevel(bounds);
	
	var center = new GLatLng( (maxy+miny)/2, (maxx+minx)/2 );
	
	if(zoomlevel != this.map.getZoom())this.map.setZoom(zoomlevel);
	this.map.panTo(center);

	bounds = null;
};

/**
 * Show the current input line, or the editing part of a multi-part line
 * @return
 * <b>when to use: </b> when the current line being edited needs to be displayed/updated
 */
PGISTMapEditor.prototype.showInputLine=function(){
		if(this.inputline != null){
			this.map.removeOverlay(this.inputline);
			this.inputline = null;
		}
		
		if(this.coords[this.coords.length-1].length > 1){
			this.inputline = new GPolyline(this.coords[this.coords.length-1]);
			this.map.addOverlay(this.inputline);	
		}
};

/**
 * Delete the last point
 * @return
 * <b>when to use: </b> when "delete last point" button is clicked
 */
PGISTMapEditor.prototype.deleteLastPoint=function(){

	if(this.coords[this.coords.length-1].length>0){
		this.coords[this.coords.length-1].pop();
		
		this.map.clearOverlays();
		if(this.editGeomType == "LINE"){
			//this.showInputLine();
			this.drawLines();
		}else if(this.editGeomType == "POINT"){
			
		}
		
		this.drawPoints();
	}else if(this.coords.length>1){
		this.coords.pop();
		this.deleteLastPoint();
	}
};

/**
 * prepare coordinates in an acceptable format ([part][path or ring][x y,x y,...])
 * @return
 * <b>when to use: </b> when "delete last point" button is clicked
 */
PGISTMapEditor.prototype.prepareCoords=function(){
	if(this.coords[0].length<=0)return null;
	
	var fptype = this.editGeomType;
	var coords = this.coords;
	
	if(fptype == "LINE" || fptype == "POINT"){
		var inputpoints = coords[coords.length-1];
		if(inputpoints.length < 2 && fptype == "LINE"){
			alert("Please make at least 2 points are needed to make a line.");
			return null;
		}
		
		var serialcoords = new Array();
		serialcoords[0] = new Array();
		for(i=0; i<coords.length; i++){
			serialcoords[0][i] = new Array(coords[i].length*2);
			for(j=0; j<coords[i].length*2;j=j+2){
				serialcoords[0][i][j] = coords[i][j/2].x;
				serialcoords[0][i][j+1] = coords[i][j/2].y;
			}
		}
	}
	return serialcoords;
};

/**
 * Generate overlays that can be readily be displayed
 * @param fpDate is returned from ProjectAgent.getFootprints(), it has the format of 
 * @param boolean enableEdit: if true, vertex points will also be displayed
 * <b>when to use: </b> when "delete last point" button is clicked
 */
PGISTMapEditor.prototype.prepareOverlays=function(fpData, enableEdit){
	//clear exisitng
	
	//
	for(fpid in fpData.footprints){
		//
	}
	
	
};

//map edit toolbar control

function PGISTMapEditToolsControl() {
	PGISTMapEditToolsControl_Global = this;
}
PGISTMapEditToolsControl.prototype = new GControl();

PGISTMapEditToolsControl.prototype.selectTool = function(ele){
	//loop through all tool elements to update tool selection
	var tools = ele.parentNode.getElementsByTagName('img');
	for(var i=0; i<tools.length; i++)
		tools[i].border = 0;
	
	ele.border = 1;
	
	var regntool = ele.parentNode.getElementsByTagName("div")[0].style;
	if(ele.alt == "Region"){
		PGISTMapEditor_Global_Accessor.stopInput();
		PGISTMapEditor_Global_Accessor.clearInput();
		PGISTMapEditor_Global_Accessor.editGeomType = 'POLYGON';
		if(regntool.display == "none")
			regntool.display = "";
		else
			regntool.display = "none";
		
	}else{
		regntool.display = "none";
		if(ele.alt == "Single point"){
			PGISTMapEditor_Global_Accessor.allowMultiplePoints=false;
			PGISTMapEditor_Global_Accessor.startInputPoints();
		}else if(ele.alt == "Multiple points"){
			PGISTMapEditor_Global_Accessor.allowMultiplePoints=true;
			PGISTMapEditor_Global_Accessor.startInputPoints();
		}else if(ele.alt == "New line"){
			PGISTMapEditor_Global_Accessor.allowMultiplePoints=true;
			PGISTMapEditor_Global_Accessor.startInputLine();
		}else if(ele.alt == "Delete last point"){
			PGISTMapEditor_Global_Accessor.deleteLastPoint();
		}else if(ele.alt == "Save"){
			//alert("do save...");
			if(PGISTMapEditor_Global_Accessor.saveHandler){
				if(PGISTMapEditor_Global_Accessor.editGeomType == 'POLYGON'){
					var counties = document.forms["countylist"].county;
					
					var fpids = "";
					for (k=0; k<counties.length; k++){
						if(counties[k].checked)fpids += counties[k].value + ",";
					}
					fpids = fpids.substr(0, fpids.length-1);
					
					PGISTMapEditor_Global_Accessor.saveHandler(
								PGISTMapEditor_Global_Accessor.targetId, 
								fpids );
					return;
				}
				var goodCoords = PGISTMapEditor_Global_Accessor.prepareCoords();
				if( goodCoords == null){
					alert("Geometry is not valid, please try again");
					return;
				}
				PGISTMapEditor_Global_Accessor.saveHandler(
								PGISTMapEditor_Global_Accessor.targetId, 
								PGISTMapEditor_Global_Accessor.editGeomType,
								goodCoords );
			}
		}else{
			PGISTMapEditor_Global_Accessor.stopInput();
		}
	}

};

PGISTMapEditToolsControl.prototype.initialize = function(map) {
  var container = document.createElement("div");

  var toolbarDiv = document.createElement("div");
  container.appendChild(toolbarDiv);
  var html = '<div style="background-color: #FFFFCE;height:24px;width:170px">';
  html += '<img src="images/_dot1.gif" alt="Single point" width="23" height="23" onclick="PGISTMapEditToolsControl_Global.selectTool(this);" />';
  html += '<img src="images/_dotm.gif" alt="Multiple points" width="23" height="23" onclick="PGISTMapEditToolsControl_Global.selectTool(this);" />';
  html += '<img src="images/_linem.gif" alt="New line" width="23" height="23" onclick="PGISTMapEditToolsControl_Global.selectTool(this);" />';
  html += '<img src="images/_poly.gif" alt="Region" width="23" height="23" onclick="PGISTMapEditToolsControl_Global.selectTool(this);" />';
  html += '<img src="images/_delete.png" alt="Delete last point" width="23" height="23" onclick="PGISTMapEditToolsControl_Global.selectTool(this);" />';
  html += '<img src="images/_stop.png" alt="Stop edit" width="23" height="23" onclick="PGISTMapEditToolsControl_Global.selectTool(this);" />';
  html += '<img src="images/_save.gif" alt="Save" width="23" height="23" onclick="PGISTMapEditToolsControl_Global.selectTool(this);" />';
  html += '<div style="background-color: #FFFFCE;display:none"><form name="countylist">';
  html += '  <label><input type="checkbox" name="county" value="93" />Snohomish</label>';
  html += '  <label><input type="checkbox" name="county" value="126" />King</label>';
  html += '  <label><input type="checkbox" name="county" value="153" />Pierce</label>';
  html += '	</form></div></div>';

  toolbarDiv.innerHTML = html;
  
  map.getContainer().appendChild(container);
  return container;
}

// By default, the control will appear in the top left corner of the
// map with 40 pixels of padding.
PGISTMapEditToolsControl.prototype.getDefaultPosition = function() {
  return new GControlPosition(G_ANCHOR_TOP_LEFT, new GSize(100, 10));
}


//===========================
// to add some extra control for legend. more nuanced legend can be composed with 
// HTML and images, even dynamically
/**
 * This class extends GControl.
 * 
 * 
 **/
function PGISTMapTitleControl(html) {
	this.titleHTML = html;
}
PGISTMapTitleControl.prototype = new GControl();

/**
 * Creates a one DIV as the container to hold the HTML. an easier way could be just
 * set the innerHTML
 * 
 **/
PGISTMapTitleControl.prototype.initialize = function(map) {
  var container = document.createElement("div");

  var legendDiv = document.createElement("div");
  container.appendChild(legendDiv);
  legendDiv.innerHTML = this.titleHTML;
  //var legendImg = document.createElement("img");
  //legendImg.src = "imgs/prjmod_legend.png";
  //legendDiv.appendChild(legendImg);


  map.getContainer().appendChild(container);
  return container;
};

// By default, the control will appear in the top left corner of the
// map with 40 pixels of padding.
PGISTMapTitleControl.prototype.getDefaultPosition = function() {
  return new GControlPosition(G_ANCHOR_BOTTOM_LEFT, new GSize(40, 40));
};

// Sets the proper CSS for the for the legend if necessary..
PGISTMapTitleControl.prototype.setButtonStyle_ = function(button) {
  button.style.textDecoration = "underline";
  button.style.color = "#0000cc";
  button.style.backgroundColor = "white";
  button.style.font = "small Arial";
  button.style.border = "1px solid black";
  button.style.padding = "2px";
  button.style.marginBottom = "3px";
  button.style.textAlign = "center";
  button.style.width = "6em";
  button.style.cursor = "pointer";
};
