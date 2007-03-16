// JavaScript Document
var PGISTMapper_Global_Accessor = null;
var PGISTGeometryType = {4:"POINT", 5:"LINE", 6:"POLYGON"};

/**
 *
 * constructor, need the id of the DIV element, reuturns an PGISTMap object, with "map" being the GMap2 in the DIV element.
 * Main instance variables defined in this object are:
 * <li>map</li>
 * <li>mapListener</li>
 * <li>projectList</li>
 * <li>featureOverlays</li>
 * <li>bubbleIcons[]</li>
*/
var PGISTMapper = function(mapdiv) {
	if (!GBrowserIsCompatible()) {
		alert("Sorry, this browser is not compatible with Google Maps.");
		return null;
	}

	if(typeof(mapdiv)!='object')
		mapdiv = document.getElementById(mapdiv);

	this.map = new GMap2(mapdiv,{mapTypes: [G_SATELLITE_MAP,G_NORMAL_MAP]});
	this.map.addControl(new GLargeMapControl());
	this.map.addControl(new GMapTypeControl());
	this.map.setCenter(new GLatLng(47.65985278,-122.3224667),12,G_NORMAL_MAP);
	this.map.enableContinuousZoom();
	this.map.enableDoubleClickZoom();
	//this.map.mouseWheel = true;
	this.map.setMapType(G_SATELLITE_MAP);
	
	//GEvent.addDomListener(mapdiv, "DOMMouseScroll",wheelZoom); // Firefox
	//GEvent.addDomListener(mapdiv, "mousewheel",	wheelZoom); // IE
	
	this.mapListener = null;

	this.projectList  = null;
	this.featureOverlays = new Array(); //line overlays to be drawn
		
	this.projectClickHandler = null;	//what happens if the link in the info windows is clicked
	this.afterDataLoadHandler = null;
	this.infoStructureId = -1;
	this.discussionActionURL = "/sdRoom.do";
	this.idMapping = [];
	PGISTMapper_Global_Accessor = this;
	
};

/**
 *
 * function <b>makeIdInString(prjlist)</b>: given a list of projects, grab the foorprint IDs from its "fpids" attribute
 * @return The ids of all footprints used by the projects, as a string, in the form that is good for the IN ()
 * expression of a WHERE clause in a SELECT statement, i.e., comma-delimited ids. For example, "1", or "1,2,3"
 */
function makeIdInString(prjlist){	//PGISTMapper.prototype.
	if(!prjlist)return "-1";	//by "id in (-1)" nothing will be selected
	
	var fpids = "";
	var temp = new Array();
	for(i=0;i<prjlist.length; i++){
		if(prjlist[i].fpids!=null && prjlist[i].fpids!=""){				
			alert(prjlist[i].fpids);
			prjlist[i].fpids = prjlist[i].fpids.replace(/\s/g, '');
			alert(prjlist[i].fpids);
			temp = prjlist[i].fpids.split(",");
			for(j=0; j<temp.length; j++){
				if(fpids.indexOf(temp[j]+",") < 0){
					fpids += temp[j] +"," ;
				}
			}
		}
	}
	
	fpids = fpids.replace(/,$/g, ''); //get rid of the last comma
	return fpids;
}


/*
 * Call this function before use the project map.<br>
 * Since we want to be able to use only a portion of the project pool,
 * some session ID (cctID), in which we select projects, should be provided.<br>
 * There is also a after init handler, to do something once all data is loaded.
 */
PGISTMapper.prototype.initProjects = function (cctId, afterInitHandler){
	//first call DWR to get projects data
/*	ProjectAgent.getProjects(cctId,{callback:function(data){
		if(data.successful){
			//keep this project list
			PGISTMapper_Global_Accessor.projectList = data.projects;
			
			// use a black string to get all footprints.
			fpids = PGISTMapper_Global_Accessor.makeIdInString(PGISTMapper_Global_Accessor.projectList);
			
			ProjectAgent.getFootprints(fpids,{callback:function(data1){
				if(data1.successful){
					PGISTMapper_Global_Accessor.prepareOverlays(data1.footprints);
					if(PGISTMapper_Global_Accessor.afterInitHandler)PGISTMapper_Global_Accessor.afterInitHandler();
				}else{
					alert("Get footprints not successful. Reason: " + data1.reason);
				}
		}else{
			alert(data.reason);
		}
	}});
*/	
	//then prepare feature overlays
	
	//then take care of the after-init handler
	//example 
	
};


/**
 * <b>Purpose:</b> 
 * @return
 * <b>when to use: </b> when 
 */
PGISTMapper.prototype.addTitle = function (title, subtitle){
	var html;
	if(title){
		html = "<h3>" + title + "</h3>";
		if(subtitle){
			html += "<br>" + subtitle;
		}
		
	}
	
};//end of PGISTMapper.prototype.addTitle 

//===========================
//class 2: PGISTMapEditor

/* 
 * Class PGISTMapEditor: used to handle map interactions when digitizing on a GMap
 * The constructor needs a GMap component
 *
 *
*/
var PGISTMapEditor_Global_Accessor = null;
var PGISTMapEditor = function(mapcontainer, width, height) {
	if (!GBrowserIsCompatible()) {
		alert("Sorry, this browser is not compatible with Google Maps.");
		return null;
	}
	
	if(typeof(mapcontainer)!='object')
		mapcontainer = document.getElementById(mapcontainer);

	this._mapdiv = document.createElement("div");
	width = width || "600";
	height = height || "400";
	this._mapdiv.style.width = width + "px";
	this._mapdiv.style.height = height + "px";
	
	this.map = new GMap2(this._mapdiv,{mapTypes: [G_SATELLITE_MAP,G_NORMAL_MAP]});
	this.map.addControl(new GLargeMapControl());
	this.map.addControl(new GMapTypeControl());
	this.map.setCenter(new GLatLng(47.65985278,-122.3224667),12,G_NORMAL_MAP);
	this.map.enableContinuousZoom();
	this.map.enableDoubleClickZoom();
	this.map.mouseWheel = true;
	this.map.setMapType(G_NORMAL_MAP);
	
	this.map.addControl( new PGISTMapEditToolsControl() );
	
	//GEvent.addDomListener(mapdiv, "DOMMouseScroll",wheelZoom); // Firefox
	//GEvent.addDomListener(mapdiv, "mousewheel",	wheelZoom); // IE
	
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
	this.editicon.image = "images/pointicon.png";
	this.editicon.iconSize = new GSize(16,16);
	this.editicon.iconAnchor = new GPoint(8,8);
	this.editicon.shadow = "";
	this.editicon.infoWindowAnchor = new GPoint(8,8);
	
	mapcontainer.appendChild(this._mapdiv);
	this._container = mapcontainer;

	PGISTMapEditor_Global_Accessor = this;
};

PGISTMapEditor.prototype.changeToContainer(newcontainer) = function(){
	this._container.removeChild(this._mapdiv);
	newcontainer.appendChild(this._mapdiv);
	this._container = newcontainer;
};

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
		}else{
			PGISTMapEditor_Global_Accessor.stopInput();
		}
	}

};

PGISTMapEditToolsControl.prototype.initialize = function(map) {
  var container = document.createElement("div");

  var toolbarDiv = document.createElement("div");
  container.appendChild(toolbarDiv);
  var html = '<div style="background-color: #FFFFCE;height:24px;width:150px">';
  html += '<img src="images/_dot1.gif" alt="Single point" width="23" height="23" onclick="PGISTMapEditToolsControl_Global.selectTool(this);" />';
  html += '<img src="images/_dotm.gif" alt="Multiple points" width="23" height="23" onclick="PGISTMapEditToolsControl_Global.selectTool(this);" />';
  html += '<img src="images/_linem.gif" alt="New line" width="23" height="23" onclick="PGISTMapEditToolsControl_Global.selectTool(this);" />';
  html += '<img src="images/_poly.gif" alt="Region" width="23" height="23" onclick="PGISTMapEditToolsControl_Global.selectTool(this);" />';
  html += '<img src="images/_delete.png" alt="Delete last point" width="23" height="23" onclick="PGISTMapEditToolsControl_Global.selectTool(this);" />';
  html += '<img src="images/_stop.png" alt="Stop edit" width="23" height="23" onclick="PGISTMapEditToolsControl_Global.selectTool(this);" />';
  html += '<div style="background-color: #FFFFCE;display:none"><form name="counties">';
  html += '  <label><input type="checkbox" name="county" value="12" />Snohomish</label>';
  html += '  <label><input type="checkbox" name="county" value="13" />King</label>';
  html += '  <label><input type="checkbox" name="county" value="14" />Pierce</label>';
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
