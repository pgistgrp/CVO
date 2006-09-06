var PGISTMap_Global_Accessor = null;
var PGISTGeometryType = {4:"POINT", 5:"LINE", 6:"POLYGON"};


/**
 *
 * constructor, need the id of the DIV element, reuturns an PGISTMap object, with "map" being the GMap2 in the DIV element 
 * @return The ids of all footprints used by the projects, as a string, in the form that is good for the IN ()
 * expression of a WHERE clause in a SELECT statement, i.e., comma-delimited ids. For example, "1", or "1,2,3"
 */
var PGISTMap = function(mapdiv) {
	if(typeof(mapdiv)!='object')
		mapdiv = document.getElementById(mapdiv);

	if (GBrowserIsCompatible()) {
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
		this.featureOverlays = new Array();
		
		//these variables are only useful when editing projects  
		this.editProject = null;
		this.editProjectId = -1;
		this.inputpoints = new Array();
		this.inputline = null;
		this.multiplepoints = false;
		this.coords = new Array();
		
		//predefine some constants
		this.pgiconsize = 22;
		this.bubbleicons = new Array();
		var pgicon = new GIcon(G_DEFAULT_ICON); //must use this argument to be able to fire event
		pgicon.image = "http://localhost:8080/images/ltr_1.png";
		pgicon.iconSize = new GSize(this.pgiconsize, this.pgiconsize);
		pgicon.iconAnchor = new GPoint(this.pgiconsize/2, this.pgiconsize/2);
		pgicon.shadow = "";
		pgicon.infoWindowAnchor = new GPoint(this.pgiconsize/2, this.pgiconsize/2);
		this.bubbleicons['ltr_0'] = pgicon;
		
		for(var i=1; i<39; i++){ //39 for 40 icons
			pgicon = new GIcon(pgicon); 
			pgicon.image = "http://localhost:8080/images/ltr_" + (i+1) + ".png";
			this.bubbleicons['ltr_' + i] = pgicon;
		}
	

	}
	
	this.projectClickHandler = null;	//what happens if the link in the info windows is clicked
	this.afterDataLoadHandler = null;
	this.infoStructureId = -1;
	this.discussionActionURL = "/sdRoom.do";
	this.idMapping = [];
	PGISTMap_Global_Accessor = this;
};



/**
 *
 * function <b>makeIdInString(prjlist)</b>: given a list of projects, grab the foorprint IDs from its "fpids" attribute
 * @return The ids of all footprints used by the projects, as a string, in the form that is good for the IN ()
 * expression of a WHERE clause in a SELECT statement, i.e., comma-delimited ids. For example, "1", or "1,2,3"
 */
function makeIdInString(prjlist){
	if(!prjlist)return "-1";	//by "id in (-1)" nothing will be selected
	
	var fpids = "";
	for(i=0;i<prjlist.length; i++){
		if(prjlist[i].fpids!=null && prjlist[i].fpids!=""){				
				fpids += prjlist[i].fpids +"," ;
		}
	}
	fpids = fpids.replace(/,$/g, ''); //get rid of the last comma
	return fpids;
}


/**
 * Create a marker at the given point, pop out given info, and use the given icon.
 * @params a point, info as HTML snippet, and (optional) <a href="http://www.google.com/apis/maps/documentation/reference.html#GIcon">GIcon</a>, which can possibly be grabed from variable bubbleicons
 * @return a marker 
 * @type <a hreg="http://www.google.com/apis/maps/documentation/reference.html#GMarker">GMarker</a>
 * <b>when to use: </b> page load or equavelent
 */
function createMarker(point, info, icon, dataid)
{
	if(icon)var marker = new GMarker(point,icon);
	else var marker = new GMarker(point);
	
	GEvent.addListener(marker, "click", function() 
			{PGISTMap_Global_Accessor.zoomToProject(dataid);
			marker.openInfoWindowHtml(info,{maxWidth:300});
			 });
	return marker;
}
	
/**
 * Compute a best extent for a given list of overlays (lines or points), and do an animated pan to the new extent
 * @return
 * <b>when to use: </b> for example, when a project is selected. The overlays are saved in project.footprints
 */
function adjustMapExtent (map, overlays, geometryType){
	if(!overlays)return;
	
	var minLong = 200, minLat = 100, maxLong = -200, maxLat = 0;
	
	for(var i=0; i<overlays.length; i++){
		if(geometryType == "LINE" || geometryType == "POLYGON"){
			for(var j=0;j <overlays[i].getVertexCount();j++){
				if(overlays[i].getVertex(j).lng()<minLong) minLong = overlays[i].getVertex(j).lng();
				if(overlays[i].getVertex(j).lng()> maxLong) maxLong = overlays[i].getVertex(j).lng();
				if(overlays[i].getVertex(j).lat()<minLat) minLat = overlays[i].getVertex(j).lat();
				if(overlays[i].getVertex(j).lat()>maxLat) maxLat = overlays[i].getVertex(j).lat();		
			}
		}else{
			for(var j=0;j<overlays[0].length;j++){
				if(overlays[0][j].getPoint().lng()<minLong) minLong = overlays[0][j].getPoint().lng();
				if(overlays[0][j].getPoint().lng()> maxLong) maxLong = overlays[0][j].getPoint().lng();
				if(overlays[0][j].getPoint().lat()<minLat) minLat = overlays[0][j].getPoint().lat();
				if(overlays[0][j].getPoint().lat()>maxLat) maxLat = overlays[0][j].getPoint().lat();		
			}
		}
	}
	
	if(minLat==maxLat || minLong==maxLong)
		var zoomlevel = map.getZoom();
	else{
		var bounds = new GLatLngBounds( new GLatLng(minLat, minLong), new GLatLng(maxLat,maxLong) );
		var zoomlevel = map.getBoundsZoomLevel(bounds);
		bounds = null;
	}
	var center = new GLatLng( (maxLat+minLat)/2, (maxLong+minLong)/2 );
	
	if(zoomlevel != map.getZoom())map.setZoom(zoomlevel);
	map.panTo(center);
}

/**
 * Make a line that can be overlayed on Google map
 * @return <a href="http://www.google.com/apis/maps/documentation/reference.html#GPolyline">GPolyline</a>
 * <b>when to use: </b> when a bunch of coordinates are know and we want a line
 */
function makeline(coordinates, color, width, opacity){
	var points = [];
	for(var i=0; i<coordinates.length; i=i+2){
		points.push ( new GPoint(coordinates[i],coordinates[i+1]) );
	}
	return(new GPolyline(points, color, width, opacity));
};

/**
 * Make an array of GMarkers
 * @return an array of <a href="http://www.google.com/apis/maps/documentation/reference.html#GMarker">GMarkers</a>
 * <b>when to use: </b>  when a bunch of coordinates are know and we want a series of point markers 
 */
function makepoints(coordinates, icon){
	var pointmarkers = [];
	for(var i=0; i<coordinates.length; i=i+2){
		pointmarkers.push ( new GMarker(new GPoint(coordinates[i],coordinates[i+1]), icon) );
	}
	return pointmarkers;
};

PGISTMap.prototype.zoomToProject=function(projectid){
	var project = this.findProjectById(projectid);
	if(project)
		adjustMapExtent(this.map, project.footprints, PGISTGeometryType[project.geoType]);
};

/*-----------------Editing interactions-----------------------*/  
/**
 * Make the map ready to accept clicks as input for points
 * @return
 * <b>when to use: </b> begin to click and input points
 */
PGISTMap.prototype.startInputPoint=function(){
	this.clearInput();
	
	coords = [];
	
	if(this.mapListener)GEvent.removeListener(this.mapListener);

	this.mapListener = GEvent.addListener(this.map, "click", function(marker, point) {
		if (marker == null) {		
			if(!multiplepoints){
				this.map.clearOverlays();
				this.inputpoints = [];
			}
			
			this.map.addOverlay(new GMarker(point));
			this.inputpoints.push(point);
			this.coords[0] = this.inputpoints;
		}
	});
};

/**
 * Provide a handler for the map to do when "view more" is clicked 
 * @return
 * <b>when to use: </b>
 */
PGISTMap.prototype.setProjectClickHandler=function(func){
	if(typeof(func)=="function")this.projectClickHandler=func;
	else this.projectClickHandler=eval(func);
};

/**
 * Provide a handler for the map to do after all footprints are loaded 
 * @return
 * <b>when to use: </b>
 */
PGISTMap.prototype.setAfterDataLoadHandler=function(func){
	if(typeof(func)=="function"){this.afterDataLoadHandler=func;}
	else{ this.afterDataLoadHandler=eval(func);}
};


/**
 * Make the map ready to accept clicks as input for a line or a part of multi-part line 
 * @return
 * <b>when to use: </b> when "line" is selected as the geometry type
 */
PGISTMap.prototype.startInputLine=function(){
	if(this.coords.length>0 && this.coords[this.coords.length-1].length<2){
		alert("Can't create another part until the last segment has at least two points.");
		return;
	}

	//this is to avoid multiple overlay of the current input line
	if(this.inputline != null){
		this.map.removeOverlay(this.inputline);
		this.map.addOverlay(new GPolyline(coords[coords.length-1], "#000000", 6, 0.6) );
	}
	
	this.coords[this.coords.length]=new Array();	//make a new array to hold new clicks
	this.inputpoints = this.coords[this.coords.length - 1];
	
	if(this.mapListener)GEvent.removeListener(this.mapListener);
	this.mapListener = GEvent.addListener(this.map, "click", function(marker, point) {
	  if (marker == null) {
		this.map.addOverlay(new GMarker(point));
		
		this.inputpoints.push(point);
		
		this.showInputLine();
	  }
	});	
};//startInputLine

/**
 * Show the current input line, or the editing part of a multi-part line
 * @return
 * <b>when to use: </b> when the current line being edited needs to be displayed/updated
 */
PGISTMap.prototype.showInputLine=function(){
		if(inputline != null){
			this.map.removeOverlay(this.inputline);
			this.inputline = null;
		}
		
		if(inputpoints.length > 1){
			this.inputline = new GPolyline(this.inputpoints);
			this.map.addOverlay(this.inputline);	
		}
};

/**
 * Make the map ignore any clicks affecting the input line
 * @return
 * <b>when to use: </b> when the stop editting button is clicked
 */
PGISTMap.prototype.stopInputLine=function(){
	GEvent.clearListeners(this.map, "click");
	this.map.clearOverlays();
	drawLines(this.map, this.coords);
	this.inputline = null;
	this.inputpoints = null;
};

/**
 * Delete the last point
 * @return
 * <b>when to use: </b> when "delete last point" button is clicked
 */
PGISTMap.prototype.deleteLastPoint=function(){
	if(this.inputpoints){
		this.inputpoints.pop();
		this.showInputLine();
	}
};

/**
 * Redraw all lines being edited
 * @return
 * <b>when to use: </b> 
 */
function drawLines(map,lines){
	map.clearOverlays();
	if(!lines)return;
	for(i=0; i<lines.length; i++)
		map.addOverlay(new GPolyline(this.coords[i], "#000000", 6, 0.6) );
		
};

/**
 * Clear whatever is on the map, and the editing buffer. Leave the map listener as is.
 * @return
 * <b>when to use: </b> 
 */
PGISTMap.prototype.clearInput=function(){
	this.map.clearOverlays();
	this.inputline = null;
	this.inputpoints = null;
	this.inputpoints = [];
	this.coords = null;
	this.coords = [];
};
/*---------------------------------------------------------*/  


/**
 * Save the input coordinats into the backend database using AJAX call ProjectAgent.saveFootprint
 * @return
 * <b>when to use: </b> when "save footprint" button is clicked
 */
function saveFootprint(projectid, coordinates, aftersavecall){
	if(projectid<=0)return;
	
	var fptype = "LINE";
	for(i=0; i<document.forms['fpparams'].elements['fptype'].length; i++){
		if(document.forms['fpparams'].elements['fptype'][i].checked){
			fptype = document.forms['fpparams'].elements['fptype'][i].value;
			break;
		}
	}
	
	var fpids = "";
	if(fptype == "POLYGON"){
		for(i=0; i<document.forms['fpparams'].elements['county'].length; i++){
			if(document.forms['fpparams'].elements['county'][i].checked){
				fpids += document.forms['fpparams'].elements['county'][i].value + ",";
			}
		}
		var serialcoords = [[[0]]];
	}else{
		
		if(!this.inputpoints)this.inputpoints = this.coords[this.coords.length-1];
		if(this.inputpoints.length < 2 && fptype == "LINE"){
			alert("Please make at least 2 points are needed to make a line.");
			return;
		}
		
		var serialcoords = new Array();
		serialcoords[0] = new Array();
		for(i=0; i<coordinates.length; i++){
			serialcoords[0][i] = new Array(coordinates[i].length*2);
			for(j=0; j<coordinates[i].length*2;j=j+2){
				serialcoords[0][i][j] = coordinates[i][j/2].x;
				serialcoords[0][i][j+1] = coordinates[i][j/2].y;
			}
		}
	}
	
	if(fpids.charAt(fpids.length-1) == ',')fpids=fpids.substr(0,fpids.length-1);
	ProjectAgent.saveFootprint(projectid,serialcoords,fptype, fpids,
								{callback:function(data){
											if(data.successful){
												alert("footprint saved..");
												//aftersavecall( makeIdInString(projectList) ); //refresh footprint list
											}else{
												alert("Not successful: " + data.reason);
											}
										}
								});
};

/**
 * Save the project attributes into the backend database using AJAX call ProjectAgent.saveProject
 * @return
 * <b>when to use: </b> when "save project attribute" button is clicked
 */
PGISTMap.prototype.saveProject=function(fpids){
	if(!parseInt($('prjcost').value)>0){
		alert("Cost should be a valid number.");
		$('prjcost').focus();
		return;
	}
	var projectinfo = {name:$('prjname').value,
				   	   description: $('prjdescp').value,
				       cost: $('prjcost').value
					  };
	
	//in case (e.g., use county boundaries) we want to given know geometry ids to this project as its footprint
	if(fpids) projectinfo.fpids = fpids;	
	
	ProjectAgent.saveProject(projectinfo,{callback:function(data){
								if(data.successful){
									this.editProjectId = data.pid;
									alert("Project saved with id = " + this.editProjectId);
									this.getProjects();
									$('editfpoptions').style.display = "inline";
									$('enablealtedit').disabled = null;
								}
							}}
	);
};

/**
 * Save the alternative attributes into the backend database using AJAX call ProjectAgent.saveAlternative
 * @return
 * <b>when to use: </b> when "save alternative attribute" button is clicked
 */
PGISTMap.prototype.saveAlternative=function(){
	if(this.editProjectId==-1)return;

	if(!parseInt($('altcost').value)>0){
		alert("Cost should be a valid number.");
		$('altcost').focus();
		return;
	}
	var alternativeinfo = {name:$('altname').value,
				   	   description: $('altdescp').value,
				       cost: $('altcost').value
					  };
	
	ProjectAgent.saveAlternative(this.editProjectId, alternativeinfo,{callback:function(data){
								if(data.successful){	
									alert("Alternative saved");
									this.getProjects();
									this.showProjectInfo(editProjectId, true);
								}else{
									alert("Save alternative not successful: " + data.reason);
								}
								
							}}
	);
};

/**
 * Get all the foorprints that are needed by the current selection of projects.
 * @params String pids, @see #makeIdInString
 * @return
 * <b>when to use: </b> page load
 */
function getFootprints(fpids){
	ProjectAgent.getFootprints(fpids,{callback:function(data){
		if(data.successful){
			PGISTMap_Global_Accessor.prepareOverlays(data.footprints);
			if(PGISTMap_Global_Accessor.afterDataLoadHandler)PGISTMap_Global_Accessor.afterDataLoadHandler();
		}else{
			alert("Get footprints not successful. Reason: " + data.reason);
		}
	}});
};

/**
 * This is the most important function to call. This function will prepare overlays (polylines or point markers) for each project.
 * @return
 * <b>when to use: </b> page load or equavelent
 */
PGISTMap.prototype.prepareOverlays=function(allFootprints){
	for(var j=0; j<this.projectList.length; j++){
		var project = this.projectList[j];
		if(project.fpids && project.fpids!=""){
			fpids = project.fpids.split(',');

			processed = [];
			project.footprints = [];
			project.bubblemarkers = [];
			project.footprints_alt = [];
			
			for(var i=0;i<fpids.length;i++){
				if(!processed[fpids[i]]){
					for(var k=0;k<allFootprints[fpids[i]].length;k++){ //for each polygon parts
						for(var l=0;l<allFootprints[fpids[i]][k].length;l++){
							if(PGISTGeometryType[project.geoType] == "LINE" 
							|| PGISTGeometryType[project.geoType] == "POLYGON"){
								//featureOverlays['_'+fpids[i]+'_'+k+'_'+l] = 
								if(project.id==1830 || project.id==1831)var linecolor = '#00ff00';
								else var linecolor = '#ff0000';
								fpoverlay =	makeline(allFootprints[fpids[i]][k][l],linecolor,5,1.0);
								
								project.footprints.push(fpoverlay);// featureOverlays['_'+fpids[i]+'_'+k+'_'+l] );

								fpoverlay =	makeline(allFootprints[fpids[i]][k][l],'#00FF00',5,0.7);
								project.footprints_alt.push(fpoverlay);
							}else{
								this.featureOverlays['_'+fpids[i]+'_'+k+'_'+l] = 
									this.makepoints(allFootprints[fpids[i]][k][l]);
								
								project.footprints.push( this.featureOverlays['_'+fpids[i]+'_'+k+'_'+l] );
							}
						}
					}//end for(var k
					
					processed[fpids[i]] = true;
				}else{	//then it means these overlays are already made, just attach them to this project
					for(var k=0;k<allFootprints[fpids[i]].length;k++){ //for each polygon parts
						for(var l=0;l<allFootprints[fpids[i]][k].length;l++){
							project.footprints.push(this.featureOverlays['_'+fpids[i]+'_'+k+'_'+l]);
						}
					}
				}//else end if(!processed[fpids[i]]){}else{}
				
				//make a bubbles for each part of each footprint
				for(var k=0;k<allFootprints[fpids[i]].length;k++){ //for each polygon parts
					for(var l=0;l<allFootprints[fpids[i]][k].length;l++){
						if(PGISTGeometryType[project.geoType] == "LINE" 
						|| PGISTGeometryType[project.geoType] == "POLYGON"){
							
							var midpoint = (allFootprints[fpids[i]][k][l].length/2 - (allFootprints[fpids[i]][k][l].length/2)%2)/2;

/*							//TODO: this is intended to make bubble for each alternative
							if(project.alternatives){
								var na = project.alternatives.length;

								var altindex = 0;
								for(var n=(0-(na-na%2)/2);n<=(na-na%2)/2;n++){
									if(n==0 && na%2==0 && na!=0){continue;}
									
									var infoHTML = "Project: " + project.name;
									if(na>0) infoHTML += ' - Alternative ' + project.alternatives[altindex].name;
									var bubble = createMarker(new GLatLng(allFootprints[fpids[i]][k][l][midpoint*2 + 1]
									,allFootprints[fpids[i]][k][l][midpoint*2]), 
										infoHTML, this.bubbleicons['ltr_'+j]); //'b' + n]);////////////needs change
									project.bubblemarkers.push( bubble );
									
									altindex++;
								}
							}else{}
*/								
								var infoHTML = "<h3>Project: " + project.name + "</h3>"; 
								infoHTML += '<a href="'
									+ this.discussionActionURL + '?isid=' + this.infoStructureId
									+ '&ioid=' + this.idMapping[""+project.id] + '">Read more and discuss this project</a>';
								
								bubble = createMarker(new GLatLng(allFootprints[fpids[i]][k][l][midpoint*2 + 1]
															,allFootprints[fpids[i]][k][l][midpoint*2]), 
													  infoHTML, this.bubbleicons['ltr_'+j], project.id); //0']);	// not 'b0'
								
								project.bubblemarkers.push( bubble );
							
						}else{
							for(var m=0; m<allFootprints[fpids[i]][k][l].length; m=m+2){
								bubble = createMarker(
								new GLatLng(allFootprints[fpids[i]][k][l][m+1],
									allFootprints[fpids[i]][k][l][m]), 
									"Project: " + project.name);
								project.bubblemarkers.push( bubble );
							}
						}
					}
				}//end for(var k				
				
			}//end for
			
		}//end if
	}
};

/**
 * Load all projects using AJAX call ProjectAgent.getProjects. Use black string to get all projects in database.
 * @return
 * <b>when to use: </b> page load or equavelent
 */
function getProjects(pids){
	ProjectAgent.getProjects(pids,{callback:function(data){
		if(data.successful){
			
			//DWRUtil.removeAllOptions("prjlist");
			//$("prjlist").options[0] = new Option('New project','-1'); //, '-1','Create new project');
			//DWRUtil.addOptions("prjlist", data.projects, 'id','name');
			PGISTMap_Global_Accessor.projectList = data.projects;
			getFootprints( makeIdInString(PGISTMap_Global_Accessor.projectList) ); // use a black string to get all footprints.
		}else{
			alert(data.reason);
		}
	}});
};

PGISTMap.prototype.setProjectSelection=function(projectids){
	if(!projectids)projectids= "";
	
	getProjects(projectids);
}

/**
 * Display the project's footprint, as well as other attributes and alternatives
 * @return
 * <b>when to use: </b> when a new project is selected
 */
PGISTMap.prototype.showProjectInfo=function(projectid, clearoverlay){
	
	makeHistory(projectid);
	
	if(clearoverlay)this.map.clearOverlays();
	
	var project = this.findProjectById(parseInt(projectid));
	if(!project){
		$('prjname').value = $('prjdescp').value = 	$('prjcost').value = "";
		$('enablealtedit').disabled = 'disabled';
		return;
	}else{
		$('enablealtedit').disabled = null;
	}
	this.editProject = project;
	
	$('prjname').value = project.name;
	$('prjdescp').value = project.description;
	$('prjcost').value = project.cost;
	this.editProjectId = parseInt(projectid);
	
	if(project.alternatives){
		var htmlmsg = "There are " + project.alternatives.length + " alternative(s) for this project: ";
		for(i=0;i<project.alternatives.length;i++){
			htmlmsg += "<img src='/images/ltr_" + (i+1) + ".png' onclick='this.border=1; showAlernativeInfo(" + this.editProjectId + "," + i + ")' value='" + project.alternatives[i].name + "' />&nbsp;";
			
		}
		$('prjalttitle').innerHTML = htmlmsg;
	}
	
	if(project.fpids && project.fpids!=""){	
		this.redrawProjectFootprint(project);
		this.redrawBubbles(project);
	}
};

/**
 * Redraw the footprints of a project
 * @return
 * <b>when to use: </b> when a new project is selected
 */
PGISTMap.prototype.redrawProjectFootprint=function(project){
	//adjustMapExtent(this.map, project.footprints, PGISTGeometryType[project.geoType]);
	if(PGISTGeometryType[project.geoType] == "LINE" || PGISTGeometryType[project.geoType] == "POLYGON"){
		for(var i=0; i<project.footprints.length; i++){
			this.map.removeOverlay(project.footprints[i]);
			this.map.addOverlay(project.footprints[i]);
		}
	}else{
		for(var i=0; i<project.footprints[0].length; i++){
			this.map.removeOverlay(project.footprints[0][i]);
			this.map.addOverlay(project.footprints[0][i]);
		}
	}
};

/**
 * Redraw the bubbles that represent a project
 * @return
 * <b>when to use: </b> when a new project is selected
 */
PGISTMap.prototype.redrawBubbles=function(project){
	if(project == null)return;
	
	if(project.extramarkers){
		while(project.extramarkers.length>0){
			this.map.removeOverlay(project.extramarkers.pop());
		};
	}else{
		project.extramarkers = [];
	}
	
	for(var i=0; i<project.bubblemarkers.length; i++){				
		//display the bubbles for each alternative and each part
		
		if(project.alternatives && project.alternatives.length>0){
			var na = project.alternatives.length;

			for(var n=0;n<na;n++){
				var infoHTML = "Project: " + project.name + ' - Alternative ' + project.alternatives[n].name;
				
				var loc = this.map.fromLatLngToDivPixel(project.bubblemarkers[i].getPoint());
				
				bubble = createMarker(this.map.fromDivPixelToLatLng(new GPoint(loc.x, loc.y + n* this.pgiconsize)),
										infoHTML, this.bubbleicons['ltr_' + n]);
				
				GEvent.addListener(bubble, "mouseover", function(){
					this.highlightProject(project);
				});
				GEvent.addListener(bubble, "mouseout", function(){
					this.unHighlightProject(project);
				});
				
				project.extramarkers.push( bubble );
				this.map.addOverlay(bubble);
			}
		}else{
			this.map.removeOverlay(project.bubblemarkers[i]);
			this.map.addOverlay(project.bubblemarkers[i]);
		}
	}
};

/**
 * Redraw the footprints of a project with a new symbol
 * @return
 * <b>when to use: </b> mouse over a buuble
 */
PGISTMap.prototype.highlightProject=function(project){
	if(PGISTGeometryType[project.geoType] == "LINE" || PGISTGeometryType[project.geoType] == "POLYGON"){
		for(var i=0; i<project.footprints_alt.length; i++){
			this.map.removeOverlay(project.footprints_alt[i]);
			this.map.addOverlay(project.footprints_alt[i]);
		}
	}
//for each footprint part, make a new overlay

//remove
};

/**
 * Remove the highlight symbol of the footprints of a project
 * @return
 * <b>when to use: </b> mouse out a buuble
 */
PGISTMap.prototype.unHighlightProject=function(project){
	if(PGISTGeometryType[project.geoType] == "LINE" || PGISTGeometryType[project.geoType] == "POLYGON"){
		for(var i=0; i<project.footprints_alt.length; i++){
			this.map.removeOverlay(project.footprints_alt[i]);
		}
	}
};


/**
 * Find in the project list the project with the given id
 * @return Project
 * <b>when to use: </b> 
 */
PGISTMap.prototype.findProjectById=function(id){
	if(this.projectList)
		for(i=0; i<this.projectList.length; i++){
			if(this.projectList[i].id == id){
				return this.projectList[i];
			}
		}
	else
		return null;
};

/**
 * Display alternative info, also show the info window on the map if necessary
 * @return
 * <b>when to use: </b> an alternative title is clicked
 */
PGISTMap.prototype.showAlernativeInfo=function(prjid, altindex){
	var project = this.findProjectById(prjid);
	var alternative = project.alternatives[altindex];
	
	$('altname').value = alternative.name;
	$('altdescp').value = alternative.description;
	$('altcost').value = alternative.cost;
	
	var infoHTML = "Project: " + project.name + ' - Alternative ' + project.alternatives[altindex].name;
	if(project.extramarkers)project.extramarkers[altindex].openInfoWindowHtml(infoHTML);
};

/*-----------------History management--------------------------*/    
var expectedHash = "";

/**
 * Add a token to the history
 * @return
 * <b>when to use: </b> move to a new "page"/state but the current state needs to be remembered
 */
function makeHistory(newHash)
{
  window.location.hash = newHash;
  expectedHash = window.location.hash;
  return true;
}

/**
 * Recover state based on the value
 * @return
 * <b>when to use: </b>
 */
function setOptionValue(value)
{
  for(i=0;i<$('prjlist').options.length; i++){
  	if($('prjlist').options[i].value==value){
		$('prjlist').options[i].selected = true;
		showProjectInfo(value, true);
		break;
	}
  }
  return true;
}

/**
 * 
 * @return
 * <b>when to use: </b> page load or equavelent
 */
function handleHistory()
{
  if ( window.location.hash != expectedHash )
  {
    expectedHash = window.location.hash;
    var newoption = expectedHash.substr(1);
    setOptionValue( newoption );
	
  }
  return true;
}

/**
 * 
 * @return
 * <b>when to use: </b> page load or equavelent
 */
function pollHash() {
  handleHistory();
  window.setInterval("handleHistory()", 100);
  return true;
}


/**
 * Enable wheel zoom on Google map object "map"
 * @return
 * <b>when to use: </b> already used in load()
 */
function wheelZoom(a)
{
  if (a.detail) // Firefox
  {
	if (a.detail < 0)
	{ PGISTMap_Global_Accessor.map.zoomIn(); }
	else if (a.detail > 0)
	{ PGISTMap_Global_Accessor.map.zoomOut(); }
  }
  else if (a.wheelDelta) // IE
  {
	if (a.wheelDelta > 0)
	{ PGISTMap_Global_Accessor.map.zoomIn(); }
	else if (a.wheelDelta < 0)
	{ PGISTMap_Global_Accessor.map.zoomOut(); }
  }
} 
    
