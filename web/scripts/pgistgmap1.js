// JavaScript Document
var PGISTMapper_Global_Accessor = null;
var PGISTMapEditor_Global_Accessor = null;
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
	this.map.mouseWheel = true;
	this.map.setMapType(G_SATELLITE_MAP);
	
	GEvent.addDomListener(mapdiv, "DOMMouseScroll",wheelZoom); // Firefox
	GEvent.addDomListener(mapdiv, "mousewheel",	wheelZoom); // IE
	
	this.mapListener = null;

	this.projectList  = null;
	this.featureOverlays = new Array(); //line overlays to be drawn
	
	//predefine some icons for default visualization
	this.pgiconsize = 22;
	this.bubbleicons = new Array();
	var pgicon = new GIcon(G_DEFAULT_ICON); //must use this argument to be able to fire event
	pgicon.image = "/images/ltr_1.png";
	pgicon.iconSize = new GSize(this.pgiconsize, this.pgiconsize);
	pgicon.iconAnchor = new GPoint(this.pgiconsize/2, this.pgiconsize/2);
	pgicon.shadow = "";
	pgicon.infoWindowAnchor = new GPoint(this.pgiconsize/2, this.pgiconsize/2);
	this.bubbleicons['ltr_0'] = pgicon;
	
	for(var i=1; i<39; i++){ //39 for 40 icons
		pgicon = new GIcon(pgicon); 
		pgicon.image = "/images/ltr_" + (i+1) + ".png";
		this.bubbleicons['ltr_' + i] = pgicon;
	}
	
	
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
 * Class PGISTMapEditor: used to handle map interactions when digitizing on a GMap
 * The constructor needs a GMap component
 *
 *
*/
var PGISTMapEditor = function(mapdiv) {
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
	this.map.mouseWheel = true;
	this.map.setMapType(G_SATELLITE_MAP);
	
	GEvent.addDomListener(mapdiv, "DOMMouseScroll",wheelZoom); // Firefox
	GEvent.addDomListener(mapdiv, "mousewheel",	wheelZoom); // IE
	
	this.mapListener = null;

	//these variables are only useful when editing projects  
	this.editProject = null;
	this.editProjectId = -1;
	this.inputpoints = new Array();
	this.inputline = null;
	this.multiplepoints = false;
	this.coords = new Array();
		
	PGISTMapEditor_Global_Accessor = this;
};

/*
 * Call this function before use the project map.<br>
 * Since we want to be able to use only a portion of the project pool,
 * some session ID (cctID), in which we select projects, should be provided.<br>
 * There is also a after init handler, to do something once all data is loaded.
 */
PGISTMapper.prototype.initProjects = function (cctId, afterInitHandler){
	//first call DWR to get projects data
	ProjectAgent.getProjects(cctId,{callback:function(data){
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
	
	//then prepare feature overlays
	
	//then take care of the after-init handler
	//example 
	
};