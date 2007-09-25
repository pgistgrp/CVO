//##########################################################
//### Individual Travel Path Application Javascript Code ###
//###																										 ###
//### Author:																						 ###
//### Martin Swobodzinski																 ###
//### San Diego State University												 ###
//### September, 25 2007																 ###
//##########################################################

var map; //the google map
var gdir; //the google directions object

var userId; //has to be initialized through some context; assuming here that i know the userId when the page is loaded

//geocode variables
var global_geocodePoint = null;
var geocode_success;
var geocode_counter = 1;
var geocode_success_origin = false;
var geocode_success_destination = false;
var geocode_hadErrors = false;

//variables that relate somehow to the three different marker types
var origin_marker = null;
var destination_marker = null;
var waypoint_marker = null;

var global_originAddressString;
var global_destinationAddressString;
var global_waypointAddressString;

var global_openOriginInfoWindowHtml = false;
var global_openDestinationInfoWindowHtml = false;
var global_openWaypointInfoWindowHtml = false;
//end marker type variables 

//helper variables
var global_markerType;
var global_polyline;
var global_ownLabel;
var global_radioObject;
var is_formOK = false;
var global_itIsMe;
var is_msie;
var any_changes = false;
var is_init = true;

//dhtml window variables      
var placemarkPopup;
var labelLocationPopup;
var newLabelPopup;
var tripFreqModePopup; 

//event handler variables
var global_placemarkIndex = 0;
var global_labelIndex = 0;

//current route variables
var global_waypointMarkers = []; // array with the waypoint markers of the current route
var global_routeMarkers = []; // array with the markers of the current route
var global_routePoints = []; // array with the marker points for the calculation of current route
var global_routeVertices = []; // array with vertices for current route
var global_routeFrequency = "Less than once"; // travel frequency information for current route NEW
var global_routeMode = "Car"; // travel mode information for current route NEW

//variables of the trip collector
var global_tripsLabelArray = [];
var global_tripsVertices = []; // the array for the vertices of the polyline after calculation of the trip
var global_tripsMarkers = []; // the array for the markers of the polyline after calculation of the trip
var global_tripsFrequency = []; //stores the frequency information of all trips
var global_tripsMode = []; //stores the travel mode information of all trips

//current temp route variables
var global_tmp_routeMarkers = [];
var global_tmp_polyline = null;

//variable that stores the ids of saved trips on page load
var savedTripIds = [];
var tripDelete_OK = true;

//#########################################################################################
//#########################################################################################

function init()  
  {
 	console.log("Processing load().");
 	
	if (GBrowserIsCompatible()) 
 		{      
	  map = new GMap2(document.getElementById("map"));
	  map.addControl(new GLargeMapControl());
	  map.setCenter(new GLatLng(47.658345,-122.303017), 10); //UW Seattle
		console.log("GMap loaded after setCenter? Answer: " + map.isLoaded());
	
	  gdir = new GDirections(null, null);
	        
	  GEvent.addListener(gdir, "load", onGDirectionsLoad);
	  GEvent.addListener(gdir, "error", handleDirErrors);
	        
	  GEvent.addListener(map, "click", function(marker, point)
	  	{
	  	
	  	//this is the case when a new marker is created (after a click on the map)	
	  	if (marker === null && point !== null)	
    		{
    		createClickMarker(marker, point);
    		}
    	
    	//ths case happens when an object in the map was clicked
			else if (marker !== null && typeof point == "undefined")
				{
				
				//using the try part to check if the object was a marker or something else (which is then caught by the catch case)
				try 
					{	
  				console.info("What the heck is the marker that the map hands to createClickMarker(marker, point)? Answer: " + marker.getName());
  				global_itIsMe = marker.getId();
  				
  				//check also if the user clicked an already stored waypoint marker; if so, ignore the click (one cannot edit already stored trips)
    				
  				if (marker.getName() !== "origin" && marker.getName() !== "destination" && marker.getName().indexOf("trip#") == -1)
						{
  					handleWaypointMarkerClick(map.getMarkerById(global_itIsMe));
  					}
  				}
  			
  			catch(err)
					{
					//This 'error' occurs when a non-marker object (e.g., the info window of a marker) is clicked in the map  	
					console.log("I caught an 'error'. Something of an unknown object type was clicked. typeof marker gives: " + typeof marker);		
					}	
    		}
    		
	   	});
	        
		//execute a function to populate all controls with the JSON 
	  //global_tripsJSON = TripsAgent.getTrips({userId:userId, trips:userTripList}]
	  
	  //check if running MSIE
	  check_msie();
	  
	  console.log("Client using MSIE?\nAnswer: " + is_msie);
	  
	  try
	  	{
	  	userId = getUserId();
	  	console.log("The userId before calling retrieveUserTrips: " + userId);	
	  	//call retrieveUserTrips to see if the user continues an earlier session
			retrieveUserTrips(userId);
			}
			
		catch(e)
			{
			}	
	  
	 	}
	 	
	 	else
	 		{
	 		alert("Sorry, based on you settings and/or type of browser, your browser is not compatible with this application.");
	 		}
	}

//#########################################################################################
//#########################################################################################	

function check_msie()
// return Microsoft Internet Explorer (major) version number, or 0 for others
// This function works by finding the "MSIE " string and extracting the version number
// following the space, up to the decimal point for the minor version which is ignored.
	{
	var ua = window.navigator.userAgent;
	var msie = ua.indexOf ( "MSIE " );
	
	if ( msie > 0 )		// is Microsoft Internet Explorer; return version number
		{
		//return parseInt ( ua.substring ( msie+5, ua.indexOf ( ".", msie ) ) )
		is_msie = true;
		}
		
	else
		{
		//return 0; // is other browser
		is_msie = false;
		}
	}

//#########################################################################################
//#########################################################################################

function storeTripFreqMode()
	{
		
	if (document.getElementById("addTripButton").className == "dis_fill_btn")
		{
		alert("This button is currently disabled. Please complete your input first and then map your trip before adding it to the trip list.");	
		}
	
	else
		{
		
		if (geocode_success)
			{	
			
			//inform the user that trips can only be looked at after they have been added to the trip list	
			var response = confirm("Please note: Once you add the trip to the list you won't be able to make any changes to it. Do you want to proceed?");
		
			if (response)
				{	
				map.closeInfoWindow();
				tripFreqModePopup = dhtmlwindow.open('tripFreqModeWindow', 'div', 'tripFreqModeDiv', 'Save Trip', 'width=430px,height=425px,center=1,resize=0,scrolling=1');
	
				tripFreqModePopup.onclose = function()
					{
		
					saveCurrentTrip(false);		

					return true;	
					};
				}
			}
		
		else 
			{
			alert("There was a problem with adding your trip to the trip list. Please make sure that you trip has been mapped successfully. If so, please try again.");
			}
		}
		
	}

//#########################################################################################
//#########################################################################################

function saveCurrentTrip(is_init)
	{

	var myTripIndex = global_tripsLabelArray.length;
	
	global_tripsVertices[myTripIndex] = global_routeVertices;
	
	for (var i = 0; i < global_routeMarkers.length; i++)
		{
		
		var marker = global_routeMarkers[i];
			
		//change the name of the waypoint to contain a reference to the trip index if not there yet (which is the case in for a new trip)
		//disable draggable option and click event handler on all new markers	
		if (marker.getName().indexOf("trip#") == -1)
			{
			var newMarkerName = "trip#" + myTripIndex + "_" + global_routeMarkers[i].getName();
			global_routeMarkers[i].setName(newMarkerName);
			global_routeMarkers[i].disableDragging();
			GEvent.clearListeners(global_routeMarkers[i], "click");
			}
				
		} 
	
	global_tripsMarkers[myTripIndex] = global_routeMarkers;
	global_tripsFrequency[myTripIndex] = global_routeFrequency;
	global_tripsMode[myTripIndex] = global_routeMode;
	global_tripsLabelArray[myTripIndex] = "By " + global_tripsMode[myTripIndex].toLowerCase() + " " + global_tripsFrequency[myTripIndex].toLowerCase() + " per week.";
	
	updateTripList(is_init);
	
	//calls clearAllControls with the boolean signalizing that no alert message box should be opened
	clearAllControls(false);
		
	}

//#########################################################################################
//#########################################################################################

function closeTripFreqModeWindow()
	{
	tripFreqModePopup.hide();
	}


//#########################################################################################
//#########################################################################################

// retrieves the trips of a user; should be called during onLoad() at the beginning of the session
function retrieveUserTrips(userId)
	{
		
	RegisterAgent.getUserTrips(userId, function(data)
		{
		//data.trips is now an array of trips
		if (data.successful)
			{
	
			//if there are any trips, save the trips in the trips list; otherwise don't do anything
			if (data.trips.length !== 0)
				{
					
				//turn on both the 'Delete trip' and the 'Save my trips!' button
				var myTmpDelButton = document.getElementById("tripDeleteButton");
				myTmpDelButton.className = "fill_btn";
								
				myTmpDelButton.onmouseover = function()
					{
					myTmpDelButton.className = "btnhov_fbtn";	
					};
					
				myTmpDelButton.onmouseout = function()
					{
					myTmpDelButton.className = "fill_btn";	
					};
					
				myTmpDelButton.onclick = function()
					{
					deleteSelectedTrip();	
					};
				
				var myTmpSaveButton = document.getElementById("saveTripsButton");
				myTmpSaveButton.className = "fill_btn";
								
				myTmpSaveButton.onmouseover = function()
					{
					myTmpSaveButton.className = "btnhov_fbtn";	
					};
					
				myTmpSaveButton.onmouseout = function()
					{
					myTmpSaveButton.className = "fill_btn";	
					};
					
				myTmpSaveButton.onclick = function()
					{
					saveTripList();	
					};					
					
				//iterate through the returned trips and save each trip one by one through saveCurrentTrip()
				for (var i = 0; i < data.trips.length; i++)
					{
					
					//first save the id of the returned trip (so it can be deleted later on
					savedTripIds[i] = data.trips[i].id;
					
					//convert the array of x/y coordinates to an array of GLatLng	and save it in global_routeVertices
					global_routeVertices = getVerticesFromXY(data.trips[i].coords);	
					
					//convert the numerical values coming from the DB into the respective string values of travel mode and frequency
					global_routeFrequency = getFrequencyFromInt(data.trips[i].frequency);
					global_routeMode = getModeFromInt(data.trips[i].mode);
										
					//now get the markers data from the trip and create new PdMArkers and add them to global_routeMarkers array
					for (var j = 0; j < data.trips[i].markers.length; j++)
						{
							
						var pnt;
						
 						var icon;
  			  	var tooltipString;
					
						var baseIcon = new GIcon();
						baseIcon.shadow = "images/markers/shadow.png";
						baseIcon.iconSize = new GSize(20, 34);
						baseIcon.shadowSize = new GSize(37, 34);
						baseIcon.iconAnchor = new GPoint(9, 34);
						baseIcon.infoWindowAnchor = new GPoint(9, 2);
						baseIcon.infoShadowAnchor = new GPoint(18, 25);
						
						icon = new GIcon(baseIcon);
					
						//stores the data of the current DWR marker as coming from the DB
						var currentMarker = data.trips[i].markers[j];					
						
						//origin
						if (currentMarker.type === 0)
							{
	 						icon.image = "images/markers/start2.png";	
							}
						
						//waypoint(s)
						else if (currentMarker.type == 1)
							{
							if (currentMarker.index > 98)
  							{
  							icon.image = "images/markers/marker0.png";	
  							}
  				
  						else
  							{	
  							icon.image = "images/markers/marker" + (currentMarker.index) + ".png";
  			  			}
							}
						
						//destination	
						else if (currentMarker.type == 2)
							{
							icon.image = "images/markers/end2.png";	
							}
						
						pnt = new GLatLng(currentMarker.lat, currentMarker.lng);	
 						
 						
 						//stores the DWR marker data in form of an instance of PdMarker
 						var tmpMarker = new PdMarker(pnt, {icon: icon});
 						
						tmpMarker.setOpacity(100);
							  						
						tmpMarker.setName(data.trips[i].markers[j].name);
  		
  					//numbering of marker ids starts with 0
  					tmpMarker.setId(data.trips[i].markers[j].index);
  						  		
  					tmpMarker.setUserData(data.trips[i].markers[j].data1);	  		
  					tmpMarker.setUserData2(data.trips[i].markers[j].data2);	
  		
  					tmpMarker.setTooltip(tmpMarker.getUserData());

						global_routeMarkers[tmpMarker.getId()] = tmpMarker;		
							
						}
						
					//now call saveCurrentTrip() to save the data in the respective storage arrays; flag indicates that it is a method invocation during page load
					saveCurrentTrip(true);	
					} 
				}
			}
			
		else
			console.log("There seems to be a problem with the retieval of the user trips. data.successful returns: " + data.successful +". data.reason returns: " + data.reason + ".");	
		});
	}

//#########################################################################################		
//#########################################################################################

function saveTripList()
	{
	
	if (any_changes)
		{
		var myHelpMeDiv = document.getElementById("helpMeDiv");
		myHelpMeDiv.innerHTML = "Sending your trips to the server.<br/>&nbsp;<br/>This should only take a few seconds. Please wait...";
		
		var tripsJSON = prepareTripList();
	
		deleteAndSaveTrips(tripsJSON);
		}
		
	else
		{
		alert("The list on the server is already up to date.");	
		}

	}

//#########################################################################################
//#########################################################################################

function prepareTripList()
	{
	
	var dwrOneTripMarkers;
	var dwrMarkers = [];
	var dwrTrips = [];
	
	//loop through all saved trips
	for (var i = 0; i < global_tripsLabelArray.length; i++)
		{
		
		//first handle all the markers of the trip i
		var pdMarkerArray = global_tripsMarkers[i];
		
		var typeInt;
		dwrOneTripMarkers = [];
		
		//loop through the markers of the trip i; these markers are of type pdMarker and have to be converted into DWR/JSON objects	
		for (var j = 0; j <	pdMarkerArray.length; j++)
			{
						
			var pdMarker = pdMarkerArray[j];
			
			//check what type the marker is; could probably be avoided if the stored indexes are right
			if (pdMarker.getName().indexOf("origin") !== -1)
				{
				typeInt = 0;	
				}
				
			else if (pdMarker.getName().indexOf("waypoint") !== -1)	
				{
				typeInt = 1;	
				}
				
			else if (pdMarker.getName().indexOf("destination") !== -1)
				{
				typeInt = 2;	
				}
			
			//add marker data in form of DWR/JSON objects to the dwrMarkers array
			dwrOneTripMarkers[j] = {type:typeInt, index:pdMarker.getId(), name:pdMarker.getName(), data1:pdMarker.getUserData(), data2:pdMarker.getUserData2(), lat:pdMarker.getPoint().lat(), lng:pdMarker.getPoint().lng()};
				
			}
			
		//next handle the other properties of trip i (mode, travel frequency and the geometry of the trip)
		var modeInt = getIntFromMode(global_tripsMode[i]);
		var freqInt = getIntFromFrequency(global_tripsFrequency[i]);
		var tripCoords = getXYFromVertices(global_tripsVertices[i]);
		
		dwrTrips[i] = {mode:modeInt, frequency:freqInt, coords:tripCoords};
		dwrMarkers[i] = dwrOneTripMarkers;
		}
	
	return {trips:dwrTrips, markers:dwrMarkers};		

	}


//#########################################################################################
//#########################################################################################

function deleteAndSaveTrips(tripsJSON_in)
	{
	
	//savedTripIds is a global variable that contains the ids of previously stored trips
	if (savedTripIds.length !== 0 && tripDelete_OK)
		{
		var lastId = savedTripIds[savedTripIds.length - 1];
			
		RegisterAgent.removeTravelTrip(lastId, function(data)	
			{
			if(data.successful)
				{
				console.info("Trip #" + lastId + " has been deleted successfully.");
				
				//pop the last element in savedTripIds and call deleteTrip again with shortened array
				savedTripIds.pop();
				tripDelete_OK = true;
				deleteAndSaveTrips(tripsJSON_in);
				}
			else
				{
				console.info("RegisterAgent.removeTravelTrip(trip# " + lastId + ") was not successful. data.reason: " + data.reason);
				tripDelete_OK = false;
				}
			});
		}
		
	else if (savedTripIds.length === 0 && tripDelete_OK)
		{
		//call RegisterAgent and save all the trips that are stored client-side
		RegisterAgent.saveUserTrip(userId, tripsJSON_in.markers, tripsJSON_in.trips, function(data)
			{
			
			if (data.successful) 
				{
				var myHelpMeDiv = document.getElementById("helpMeDiv");
				myHelpMeDiv.innerHTML = "Your trips were saved successfully!<br/>You can now return to the agenda and come back to 'Map your daily travel' at a later time without loosing your trips. You can also view your trips together with the footprints of the proposed projects in Step 3.<br/>&nbsp;<br/>If you continue to add new trips to the trip list don't forget to press the 'Save my Trips!' button again!";	
					
				alert("Your trips were saved successfully! If you continue to add new trips to the trip list don't forget to press the 'Save my Trips!' button again.");
				savedTripIds = data.tripIds;
				any_changes = false;
				}
		
			else
				{
				alert("There was a problem during the communication with the database. Trips could not be saved. Please try again.");	
				}	
			});
		}
		
	else
		{
		console.log("This is deleteAndSaveTrips(). At least one trip has not been deleted successfully.");	
		}		
	}	
		
//#########################################################################################
//#########################################################################################

function handleMultiplePlacemarks(placemarks_in)
	{
	
	console.log(placemarks_in[0]);
	
	map.closeInfoWindow();	
	placemarkPopup = dhtmlwindow.open('placemarkWindow', 'div', 'placemarkWindowDiv', 'Address Selection', 'width=430px,height=150px,center=1,resize=0,scrolling=1');
	
	placemarkPopup.onclose=function()
		{
		//var selectionFlag;	
			
		//store the point of the selected placemark (as determined above in the while loop) in global_geocodePoint
		//store also the address of the selected placemark so that information can be saved as userData2 in the marker
    console.log("Here we go with the global_placemarkIndex: " + global_placemarkIndex);
		var pointCoordArray = placemarks_in[global_placemarkIndex].Point.coordinates;
		console.log("first element in pointCoordArray[]: " + pointCoordArray[0]);
			
		//arrangements of coordinates in the JSON array is opposite to GLatLng (i.e., lng, lat, z-value)
		var lng = pointCoordArray[0];
		var lat = pointCoordArray[1];
			
		console.log("lng: " + lng);
		console.log("lng: " + lat);
			
		console.log("The global_markerType before the test for calling createMarker is: " + global_markerType);
			
		//Store the coordinates in the global variable global_geocodePoint
		//GLatLng takes arguments in the form of (lat, lng)
		global_geocodePoint = new GLatLng(lat, lng);
			
		if (global_markerType == "origin")
			{
			//call createMarker with the current version of (global) origin_marker
			global_originAddressString = placemarks_in[global_placemarkIndex].address;
			createMarker(origin_marker, global_geocodePoint, "origin");
			global_placemarkIndex = 0;	
			}
	
		else if (global_markerType == "destination")
			{
			//call createMarker with the current version of (global) destination_marker	
			global_destinationAddressString = placemarks_in[global_placemarkIndex].address;		
			createMarker(destination_marker, global_geocodePoint, "destination");
			global_placemarkIndex = 0;
			}
			
		else if (global_markerType == "waypoint")
			{
			//call createMarker with the current version of (global) destination_marker			
			console.log("Yes, I am executing createMarker(null, global_geocodePoint, 'waypoint') next.");
			global_waypointAddressString = placemarks_in[global_placemarkIndex].address;
			createMarker(null, global_geocodePoint, "waypoint");
			global_placemarkIndex = 0;
			}		
					
		//return true so that close() does not abort	
		return true;	
		};
	}

//#########################################################################################
//#########################################################################################

//Function to run when buttons within modal window is clicked on. Directly embedded inside hidden DIV, bypassing "onclose" event:
function closePlacemarkWindow()
	{
	placemarkPopup.hide();
	}

//#########################################################################################
//#########################################################################################	  	

//Function to run when buttons within modal window is clicked on. Directly embedded inside hidden DIV, bypassing "onclose" event:
function closeLabelWindow()
	{
	map.closeInfoWindow();
	}

//#########################################################################################
//#########################################################################################	 

function onGDirectionsLoad()
	{
					
	var gstat = gdir.getStatus().code;
	    	
	if (gstat == G_GEO_SUCCESS)
		{
		console.log("Route loaded successfully: code " + gstat);
		turnOnOffAddTripButton("on");
	  }
	  
	else
		{
	  console.log("Route could not be loaded: error code " + gstat); //should not occur since errors are handled in handleDirErrors()
		turnOnOffAddTripButton("off"); //the button should already be disabled at this point
		}
		  	
	var gdirPolyTemp = gdir.getPolyline();
	console.log("Number of vertices in gdirResult: " + gdirPolyTemp.getVertexCount());
		  
	var ind;
	var myCoordString = "LAT,LONG<br\>";
		  	
	var points = [];
	global_routeVertices = []; 
 				 				
	for (ind = 0; ind < gdirPolyTemp.getVertexCount(); ind++)
		{
	  points[ind] = gdirPolyTemp.getVertex(ind);
	  
	  global_routeVertices[ind] = gdirPolyTemp.getVertex(ind);
	   		    	
	  //myCoordString = myCoordString + points[ind].lat() + "," + points[ind].lng() + "<br\>";
	  
	  myCoordString = myCoordString + global_routeVertices[ind].lat() + "," + global_routeVertices[ind].lng() + "<br\>";
	  }
		    	
	//call the function that sets the appearance of the routes based on travel purpose
	var myPolyline = setPolylineAppearance(points, "default"); 
	//var myPolyline = setPolylineAppearance(global_routeVertices, "default"); 
	console.log(myCoordString);
		  	
	console.log("Number of vertices in myPolyline: " + myPolyline.getVertexCount());
	
	if (global_polyline !== null && typeof global_polyline !== 'undefined')
		{
		map.removeOverlay(global_polyline);
		}

	global_polyline = myPolyline;
	
	map.closeInfoWindow();
	map.addOverlay(global_polyline);
	//map.setCenter(gdir.getBounds().getCenter());
	//map.setZoom(map.getBoundsZoomLevel(gdir.getBounds()));
	
	map.setCenter(global_polyline.getBounds().getCenter());
	map.setZoom(map.getBoundsZoomLevel(global_polyline.getBounds()));
	
	console.log("The number of geocodes in gdir is: " + gdir.getNumGeocodes());
	}
	
//#########################################################################################
//#########################################################################################	

function checkInputField(type_in)
	{
		
	if (type_in == 'origin')
		{
			
		if (document.getElementById("fromAddressField").value === "" || document.getElementById("fromAddressField").value == "Street, City, State, Place Name or Intersection") 
			{
			is_formOK = false;
			alert("Please provide an address for the origin before clicking the 'ADD' button."); 	
			}
		
		else
			{
			is_formOK = true; 
			}				
		}
		
	else if (type_in == 'destination')
		{
			
		if (document.getElementById("toAddressField").value === "" || document.getElementById("toAddressField").value == "Street, City, State, Place Name or Intersection") 
			{
			is_formOK = false;
			alert("Please provide an address for the destination before clicking the 'ADD' button."); 	
			}
		
		else
			{
			is_formOK = true; 
			}				
		}
		
	else if (type_in == 'waypoint')
		{
			
		if (document.getElementById("viaAddressField").value === "" || document.getElementById("viaAddressField").value == "Street, City, State, Place Name or Intersection")  
			{
			is_formOK = false;
			alert("Please provide an address for the waypoint before clicking the 'ADD' button."); 	
			}
		
		else
			{
			is_formOK = true; 
			}				
		}

	}

//#########################################################################################
//#########################################################################################
	    
function geocodeAddress(address, marker_type)
	{
	
	//Check if the address input field is empty
	checkInputField(marker_type);	
	
	if (!is_formOK)
		{
		return;	
		}
			
	var geocoder = new GClientGeocoder();
	
	console.log("The address going into the geocoder is: " + address);
	
	//store the marker_type for that request in global_markerType so that the callback function can access it 
	console.log("Yes, I am executing the rest of geocodeAddress before the getLocations callback function is done");
	if (marker_type == "origin")
		{
		global_markerType = "origin";
		console.log("global_markerType just assigned 'origin': " + global_markerType);
		}
	
	else if (marker_type == "destination")
		{
		global_markerType = "destination";
		console.log("global_markerType just assigned 'destinaton': " + global_markerType);
		}
		
	else if (marker_type == "waypoint")
		{
		global_markerType = "waypoint";
		console.log("global_markerType just assigned 'waypoint': " + global_markerType);
		}	
	
	//callback function handleGeocodeResponse handles quite some functionality	
	geocoder.getLocations(address, handleGeocodeResponse);
	
	}

//#########################################################################################
//#########################################################################################
				
function setDirections(fromAdd, toAdd) 
	{
	
	//if the origin and the destination geocode flags indicate that the geocoding was successful
	//then create the array of coordinates and send it to gdir
	if (geocode_success_origin && geocode_success_destination)
		{

		//call createRouteArray() in order to put the point array together for the GDirections object
		console.log("global_routePoints.length BEFORE createRouteArray(): " + global_routePoints.length);	
		createRouteArray();
	
		for (var ind = 0; ind < global_routePoints.length; ind++)
			{
			console.log("global_routePoints.length after createRouteArray(): " + global_routePoints.length);
		
			console.log("global_routePoints lat coord of point #" + ind + " is: " + global_routePoints[ind].lat());	
			console.log("global_routePoints lng coord of point #" + ind + " is: " + global_routePoints[ind].lng());
			
			}
	
		gdir.loadFromWaypoints(global_routePoints, { "locale": "en_US", "getPolyline":"true", "preserveViewport":"true" });
		console.log("I just sent off the routePoints array through gdir.loadFromWaypoints()");
		}
		
	else if (!geocode_success_origin && geocode_success_destination)
		{
		alert("There was a problem with the start address. It has not been defined yet. Please enter a start location and try again.");	
		}
		
	else if (geocode_success_origin && !geocode_success_destination)
		{
		alert("There was a problem with the end address. It has not been defined yet. Please enter an end location and try again.");	
		}
		
	else if (!geocode_success_origin && !geocode_success_destination)
		{
		alert("There was a problem with both the start address and the end address. Neither has been defined yet. Please enter a start and end location and try again.");		
		}
	
	}

//#########################################################################################
//#########################################################################################
						
function handleDirErrors()
	{
	
	var num_args = handleDirErrors.arguments.length;
	      
	console.log("Number of args handed to handleDirErrors(): " + num_args);
	      
	var gstat = gdir.getStatus().code;
        
  //console.error("Case 0 here. We have an error! Error code: " + gstat);	
  
  if (gstat == G_GEO_BAD_REQUEST) //400
  	{
		alert("A directions request could not be successfully parsed.\n Error code: " + gstat);
		turnOnOffAddTripButton("off");
		}
	
	else if (gstat == G_GEO_SERVER_ERROR) //500
		{
		alert("A geocoding or directions request could not be successfully processed, yet the exact reason for the failure is not known.\n Error code: " + gstat);
		turnOnOffAddTripButton("off");
		}
	
	else if (gstat == G_GEO_MISSING_QUERY) //601
		{
		alert("The HTTP q parameter was either missing or had no value. For geocoder requests, this means that an empty address was specified as input. For directions requests, this means that no query was specified in the input.\n Error code: " + gstat);
		turnOnOffAddTripButton("off");
		}
    
  else if (gstat == G_GEO_MISSING_ADDRESS) //601
  	{
		alert("The HTTP q parameter was either missing or had no value. For geocoder requests, this means that an empty address was specified as input. For directions requests, this means that no query was specified in the input.\n Error code: " + gstat);
		turnOnOffAddTripButton("off");
		}
  
  else if (gstat == G_GEO_UNKNOWN_ADDRESS) //602
  	{
		alert("No corresponding geographic location could be found for the specified address. This may be due to the fact that the address is relatively new, or it may be incorrect.\nError code: " + gstat);
		turnOnOffAddTripButton("off");
		}
	
	else if (gstat == G_GEO_UNAVAILABLE_ADDRESS)  //603. Possible doc bug. This might be either not defined, or Doc is wrong.
		{
		alert("No corresponding geographic location could be found for the specified address. This may be due to the fact that the address is relatively new, or it may be incorrect.\n Error code: " + gstat);
		turnOnOffAddTripButton("off");
		}
	
	else if (gstat == G_GEO_UNKNOWN_DIRECTIONS) //604
		{
		alert("The GDirections object could not compute directions between the points mentioned in the query. This is usually because there is no route available between the two points, or because we do not have data for routing in that region.\n Error code: " + gstat);
		turnOnOffAddTripButton("off");
		}
	
	else if (gstat == G_GEO_BAD_KEY) //610
		{
		alert("The given key is either invalid or does not match the domain for which it was given. \n Error code: " + gstat);
		turnOnOffAddTripButton("off");
		}
	
	else 
		{
		alert("An unknown error occurred (due to a spelling error?). Please check your input and try again.\n Error code: " + gstat);
		turnOnOffAddTripButton("off");
		}
        	
  }//end handleDirErrors

//#########################################################################################
//#########################################################################################
      	
function handleGeocodeResponse() 
	{
	
	try
		{
		var num_args = handleGeocodeResponse.arguments.length;
	      
		console.log("Number of args handed to handleGeocodeResponse(): " + num_args);
		console.log("What placemark address is handed to handleGeocodeResponse()?\n It is: " + handleGeocodeResponse.arguments[0].Placemark[0].address);
	      
		var gstat = handleGeocodeResponse.arguments[0].Status.code;
		}
		
	catch(err)
		{
		console.log("Problems with the address input fields caused an error (it was probably empty).");	
		}	
        
  console.log("Case 1 here: " + gstat);
                 
	if (gstat == G_GEO_SUCCESS) //200
  	{
    geocode_success = true;
    
    if (global_markerType == "origin")
    	{
    	geocode_success_origin = true;	
    	}
    	
    else if (global_markerType == "destination")
    	{
    	geocode_success_destination = true;	
    	}
    	
    else if (global_markerType == "waypoint")
    	{
    	geocode_success_waypoint = true;	
    	}	
        
    //successful so check if there is more than one placemark
    
    var placemarks_array = handleGeocodeResponse.arguments[0].Placemark;
    var placemarks_count = placemarks_array.length;
    
    console.log("Length of the returned placemarks array in the geocoder JSON response: " + placemarks_count);
        
    if (placemarks_count > 0) //write placemark addresses to div, open dhtml window, store the point of the selected placemark and add it to the map
    	{
    		
    	var myNewFormString = "";
    	
    	//var i;	
				
			for(var i = 0; i < placemarks_count; i++) //placeIndex should start with 1 later on i think, not 0--we will see
    		{
    		
     		if (i === 0)
    			{
    			myNewFormString += "<tr><td><input type='radio' onClick='radioPlacemarkClick(event)' name='placemarkChoice' CHECKED value='" + i + "' id='placemark" + i + "'></td><td><label class='radio_btn_labels'>" + placemarks_array[i].address + "</label></td></tr>";
    			}
    			
    		else
    			{
    			myNewFormString += "<tr><td><input type='radio' onClick='radioPlacemarkClick(event)' name='placemarkChoice' value='" + i + "' id='placemark" + i + "'></td><td><label class='radio_btn_labels'>" + placemarks_array[i].address + "</label></td></tr>";
    			}	
    		
    		}
    	    	
    	//write new content to the placemarkWindowDiv
    	var myPlacemarkDiv = document.getElementById("placemarkWindowDiv");
    	
    	myPlacemarkDiv.innerHTML = "<label class='hd_labels'>Please select the correct address:</label><p><form><table>" + myNewFormString + "</table></form></p><br/>&nbsp;&nbsp;<input type='button' class='large_btns' value='That is the address!' style='margin-right: 20px' onClick='closePlacemarkWindow()' />"; 
    	    	
    	console.log("myPlacemarkDiv.innerHTML: " + myPlacemarkDiv.innerHTML);
    	
    	//you should leave the other processing to handleMultiplePlacemarks	so hand it the placemarks_array as argument   	    	
    	handleMultiplePlacemarks(placemarks_array); //sets value of selected placemark in global_placemarkIndex
    	}

		console.log("Geocoding of address was successful and no errors occured.");	
		}
  
  else if (gstat == G_GEO_BAD_REQUEST) //400
  	{
    geocode_success = false;
    geocode_hadErrors = true;
		alert("A directions request could not be successfully parsed.\n Error code: " + gstat);
		}
	
	else if (gstat == G_GEO_SERVER_ERROR) //500
		{
		geocode_success = false;
		alert("A geocoding or directions request could not be successfully processed, yet the exact reason for the failure is not known.\n Error code: " + gstat);
		}
	
	else if (gstat == G_GEO_MISSING_QUERY) //601
		{
		geocode_success = false;
		alert("The HTTP q parameter was either missing or had no value. For geocoder requests, this means that an empty address was specified as input. For directions requests, this means that no query was specified in the input.\n Error code: " + gstat);
		}
    
  else if (gstat == G_GEO_MISSING_ADDRESS) //601
  	{
  	geocode_success = false;
		alert("The HTTP q parameter was either missing or had no value. For geocoder requests, this means that an empty address was specified as input. For directions requests, this means that no query was specified in the input.\n Error code: " + gstat);
		}
  
  else if (gstat == G_GEO_UNKNOWN_ADDRESS) //602
  	{
    geocode_success = false;
		alert("No corresponding geographic location could be found for the specified address. This may be due to the fact that the address is relatively new, or it may be incorrect.\nError code: " + gstat);
		}
	
	else if (gstat == G_GEO_UNAVAILABLE_ADDRESS)  //603. Possible doc bug. This might be either not defined, or Doc is wrong.
		{
		geocode_success = false;
		alert("No corresponding geographic location could be found for the specified address. This may be due to the fact that the address is relatively new, or it may be incorrect.\n Error code: " + gstat);
		}
	
	else if (gstat == G_GEO_UNKNOWN_DIRECTIONS) //604
		{
		geocode_success = false;
		alert("The GDirections object could not compute directions between the points mentioned in the query. This is usually because there is no route available between the two points, or because we do not have data for routing in that region.\n Error code: " + gstat);
		}
	
	else if (gstat == G_GEO_BAD_KEY) //610
		{
		geocode_success = false;
		alert("The given key is either invalid or does not match the domain for which it was given. \n Error code: " + gstat);
		}
	
	else 
		{
		alert("An unknown error occurred probably due to a spelling spelling error. Please check your input and try again.\n Error code: " + gstat);
		geocode_success = false;
		}
	
	//This uses geocode_success to set a flag for the respective address field that failed the geocode request	
	if (!geocode_success)
		{
		geocode_hadErrors = true;
				
		if (global_markerType == "origin")
    	{
    	geocode_success_origin = false;	
    	}
    	
    else if (global_markerType == "destination")
    	{
    	geocode_success_destination = false;	
    	}
    	
    else if (global_markerType == "waypoint")
    	{
    	geocode_success_waypoint = false;	
    	}	
		}
        	
	}//end handleGeocodeResponse()

//#########################################################################################
//#########################################################################################
				
function setPolylineAppearance(points_in, route_type)
	{
				
	var myPoly;
				
	if (route_type == "Car")
		{
		myPoly = new GPolyline(points_in, "#FF0000", 3, 1); //Red
		}
	
	else if (route_type == "Bus")
		{
		myPoly = new GPolyline(points_in, "#FF00CC", 3, 1); //Pink
		}
	
	else if (route_type == "Bike")
		{
		myPoly = new GPolyline(points_in, "#007700", 3, 1); //Dark green
		}
		
	else if (route_type == "Walk")
		{
		myPoly = new GPolyline(points_in, "#999999", 3, 1); //Dark grey
		}
	
	else if (route_type == "Ferry")
		{
		myPoly = new GPolyline(points_in, "#0000AA", 3, 1); //Dark blue 
		}
	
	else if (route_type == "Train")
		{
		myPoly = new GPolyline(points_in, "#0000AA", 3, 1); //Dark blue 
		}	
	
	else if (route_type == "Other")
		{
		myPoly = new GPolyline(points_in, "#3399FF", 3, 1); //Light blue
		}
	
	else
		{
		myPoly = new GPolyline(points_in, "#000000", 3, 1); //Black
		}					
					
	return myPoly; 	
	}

//#########################################################################################
//#########################################################################################

function updateWaypointMarkerProperties(splice_index)
	{
		
	var tempMarker;
	var myImageString;
	var oldImageValue;
	var firstDigit;
	var secondDigit;
		
	//updates the icon properites, name, and id of the markers in the global_waypointMarkers array when a marker is deleted
	for (var i = splice_index; i < global_waypointMarkers.length; i++)
  	{
  		  		  	
  	tempMarker = global_waypointMarkers[i];
  	
  	myImageString = tempMarker.getIcon().image;
  	
  	if (myImageString.length == 26) //image number has to be one digit
  		{
  		oldImageValue = parseInt(myImageString.charAt(myImageString.length - 5), 10);	
  		}
  	
  	else if (myImageString.length == 27) //image number has to be two digits
  		{
  		firstDigit = parseInt(myImageString.charAt(myImageString.length - 6), 10);
  		secondDigit = parseInt(myImageString.charAt(myImageString.length - 5), 10);
  		oldImageValue = firstDigit * 10 + secondDigit;	
  		}
  	
		console.log("" + i + ": tempMarker.getIcon().image BEFORE update: " + tempMarker.getIcon().image);	
		console.log("" + i + ": tempMarker.getName BEFORE update: " + tempMarker.getName());	
		console.log("" + i + ": tempMarker.getId BEFORE update: " + tempMarker.getId());	
 
    tempMarker.getIcon().image = "images/markers/marker" + (oldImageValue - 1) +	".png";
		tempMarker.setName("waypoint#" + (oldImageValue - 1));
		tempMarker.setId(oldImageValue - 1);
		
		console.log("" + i + ": tempMarker.getIcon().image AFTER update: " + tempMarker.getIcon().image);	
		console.log("" + i + ": tempMarker.getName AFTER update: " + tempMarker.getName());	
		console.log("" + i + ": tempMarker.getId AFTER update: " + tempMarker.getId());	
        
    }	
	
	//updates the properites of the markers on the map	
	tempMarker = map.getFirstMarker();
	var count = 0;
		
	while (tempMarker !== null)
		{
			
		if (tempMarker.getName() !== "origin")
			{
		
			if (tempMarker.getName() == "destination")
				{
				console.log("found the destination marker!");
				console.log("id of destination marker BEFORE the update: " + tempMarker.getId());
				//var newId = tempMarker.getId() + global_waypointMarkers.length;
				tempMarker.setId(global_waypointMarkers.length + 1);
				console.log("id of destination marker AFTER the update: " + tempMarker.getId());		
				}
		
			else //seems that (1) setImage() fires a change event when setting the image so that the markers on the map
					 //are being redrawn and (2) the map maintains the uniqueness of markers across all variables that reference
					 //the respective marker (the marker on the map and the marker in the markers array are the same)
				{
				tempMarker.setImage(tempMarker.getIcon().image);
				}
			}	
		
		tempMarker = map.getNextMarker();
		count++;
		}
		
	}

//#########################################################################################
//#########################################################################################

function moveWaypointUp()
	{
		
	var selectedWaypointIndex = document.getElementById("waypointList").selectedIndex;	
	
	if (selectedWaypointIndex !== -1) //an option was selected and its index was returned
		{
		
		var myHelpMeDiv = document.getElementById("helpMeDiv"); 
		myHelpMeDiv.innerHTML = "When you map your trip, waypoints will be traversed in a sequential order. That means that waypoint #1 will be traversed right after the start location, then the remaining waypoints (in ascending order), and finally the end location.<br/>&nbsp;<br/>Waypoints have two purposes: (1) You can use waypoints to route the trip through these specific locations and (2) you can capture actual stops (e.g., a grocery store, your gym, your place of work) that are part of a chain of trips (such as home-work-gym-home). The labeling functionality lets you assign proper labels to these types of waypoints.";
		
		if (selectedWaypointIndex !==  0) //if the selected waypoint is the first in the array then there is nothing to do
			{
				
			//remove the polyline from the map if one was calculated before the waypoint is moved
			if (global_polyline !== null && typeof global_polyline !== 'undefined')
				{
				map.removeOverlay(global_polyline);
				turnOnOffAddTripButton("off");
				}	
			
			//switch the selected marker in the waypoint array with the one that comes after it	
			//get the two markers
			var firstMarker = global_waypointMarkers[selectedWaypointIndex];	
			var secondMarker = global_waypointMarkers[selectedWaypointIndex - 1];
			
			//and swap the properties (id, name, image source, userData, and userData2) of the two markers
			var firstId = firstMarker.getId();
			var firstName = firstMarker.getName();
			var firstImage = firstMarker.getIcon().image;
			var firstPoint = firstMarker.getPoint();
			var firstUserData = firstMarker.getUserData();
			var firstUserData2 = firstMarker.getUserData2();
			
			var secondId = secondMarker.getId();
			var secondName = secondMarker.getName();
			var secondImage = secondMarker.getIcon().image;
			var secondPoint = secondMarker.getPoint();
			var secondUserData = secondMarker.getUserData();
			var secondUserData2 = secondMarker.getUserData2();
													
			//update the other properties of the first marker
			firstMarker.setId(secondId);
			firstMarker.setName(secondName);
			firstMarker.getIcon().image = secondImage;
			firstMarker.setUserData(secondUserData);
			firstMarker.setUserData2(secondUserData2);
			
			//update the other properties of the second marker
			secondMarker.setId(firstId);
			secondMarker.setName(firstName);
			secondMarker.getIcon().image = firstImage;
			secondMarker.setUserData(firstUserData);
			secondMarker.setUserData2(firstUserData2);
					
			//trigger redrawing of the markers on the map
			firstMarker.setImage(firstMarker.getIcon().image);
			secondMarker.setImage(secondMarker.getIcon().image);
			
			//update the global waypoint array			
			global_waypointMarkers[selectedWaypointIndex] = secondMarker;
			global_waypointMarkers[selectedWaypointIndex - 1] = firstMarker;
			
			//call updateWaypointList() so that the changes reflect themselves in the waypoint list
			updateWaypointList();
			
			//set the selectedIndex of the waypoint list to the moved waypoint's index
			document.getElementById("waypointList").selectedIndex = selectedWaypointIndex - 1;
			
			}
		}
		
	else
		{
		alert("You first have to select a waypoint from the list in order to change the position of the waypoint in the succession of waypoints."); 	
		}
				
	}

//#########################################################################################
//#########################################################################################

function moveWaypointDown()
	{
		
	var selectedWaypointIndex = document.getElementById("waypointList").selectedIndex;	
	
	if (selectedWaypointIndex !== -1) //an option was selected and its index was returned
		{
		
		var myHelpMeDiv = document.getElementById("helpMeDiv"); 
		myHelpMeDiv.innerHTML = "When you map your trip, waypoints will be traversed in a sequential order. That means that waypoint #1 will be traversed right after the start location, then the remaining waypoints (in ascending order), and finally the end location.<br/>&nbsp;<br/>Waypoints have two purposes: (1) You can use waypoints to route the trip through these specific locations and (2) you can capture actual stops (e.g., a grocery store, your gym, your place of work) that are part of a chain of trips (such as home-work-gym-home). The labeling functionality lets you assign proper labels to these types of waypoints.";
		
		if (selectedWaypointIndex !==  global_waypointMarkers.length - 1) //if the selected waypoint is the last in the array then there is nothing to do  
			{
				
			//remove the polyline from the map if one was calculated before the waypoint is moved
			if (global_polyline !== null && typeof global_polyline !== 'undefined')
				{
				map.removeOverlay(global_polyline);
				turnOnOffAddTripButton("off");
				}	
			
			//switch the selected marker in the waypoint array with the one that comes after it	
			//get the two markers
			var firstMarker = global_waypointMarkers[selectedWaypointIndex];	
			var secondMarker = global_waypointMarkers[selectedWaypointIndex + 1];
			
			//and swap the properties (id, name, and image source) of the two markers
			var firstId = firstMarker.getId();
			var firstName = firstMarker.getName();
			var firstImage = firstMarker.getIcon().image;
			var firstPoint = firstMarker.getPoint();
			var firstUserData = firstMarker.getUserData();
			var firstUserData2 = firstMarker.getUserData2();

			var secondId = secondMarker.getId();
			var secondName = secondMarker.getName();
			var secondImage = secondMarker.getIcon().image;
			var secondPoint = secondMarker.getPoint();
			var secondUserData = secondMarker.getUserData();
			var secondUserData2 = secondMarker.getUserData2();
			
			//update the other properties of the first marker
			firstMarker.setId(secondId);
			firstMarker.setName(secondName);
			firstMarker.getIcon().image = secondImage;
			firstMarker.setUserData(secondUserData);
			firstMarker.setUserData2(secondUserData2);
			
			//update the other properties of the second marker
			secondMarker.setId(firstId);
			secondMarker.setName(firstName);
			secondMarker.getIcon().image = firstImage;
			secondMarker.setUserData(firstUserData);
			secondMarker.setUserData2(firstUserData2);
					
			//trigger redrawing of the markers on the map
			firstMarker.setImage(firstMarker.getIcon().image);
			secondMarker.setImage(secondMarker.getIcon().image);
			
			//update the global waypoint array			
			global_waypointMarkers[selectedWaypointIndex] = secondMarker;
			global_waypointMarkers[selectedWaypointIndex + 1] = firstMarker;
			
			//call updateWaypointList() so that the changes reflect themselves in the waypoint list
			updateWaypointList();
			
			//set the selectedIndex of the waypoint list to the moved waypoint's index
			document.getElementById("waypointList").selectedIndex = selectedWaypointIndex + 1;
			
			}
		}
		
	else
		{
		alert("You first have to select a waypoint from the list in order to change the position of the waypoint in the succession of waypoints."); 	
		}		
	}

//#########################################################################################
//#########################################################################################

function labelSelectedWaypoint()
	{
	
	var selectedWaypointIndex = document.getElementById("waypointList").selectedIndex;	
	
	if (selectedWaypointIndex !== -1) //an option was selected and its index was returned
		{
		
		assignNewLabel(selectedWaypointIndex + 1);
		}
		
	else //no waypoint selected in the list so don't do anything
		{
		alert("Please select a waypoint in the waypoint list first.");	
		console.log("no waypoint selected so refusing to do anything!");	
		}	
	
	}
	
//#########################################################################################
//#########################################################################################

function clearAllControls(flag)
	{
		
	var response;	
	
	if (flag) 
		{
		response = confirm("This will clear all controls. Are you sure?");
		}
	
	else
		{
		response = true;	
		}	

	if (response)
		{
		
		clearStartStop("origin",'function');	
		clearStartStop('destination','function');
		global_waypointMarkers = [];
				
		map.clearOverlays();
					
		//var txtField = document.getElementById("fromAddressField");
		//txtField.value = "Street, City, State, Place Name or Intersection";
		//txtField.readOnly = false;
		
		//txtField = document.getElementById("toAddressField");
		//txtField.value = "Street, City, State, Place Name or Intersection";
		//txtField.readOnly = false;
		
		var txtField = document.getElementById("viaAddressField");
		txtField.value = "Street, City, State, Place Name or Intersection";
		txtField.readOnly = false;
		txtField.className = "fieldsInitial";

		waypoint_marker = null;
		
		geocode_counter = 1;
		global_placemarkIndex = 0;
		global_labelIndex = 0;

		global_waypointMarkers = [];
		global_routeMarkers = [];
		global_routePoints = []; // array with the marker points for the calculation of current route
		global_routeVertices = []; // array with vertices for current route
		global_routeFrequency = "Less than once";
		global_routeMode = "Car";
		
		is_formOK = false;

		global_openOriginInfoWindowHtml = false;
		global_openDestinationInfoWindowHtml = false;
		global_openWaypointInfoWindowHtml = false;
		
		geocode_success_origin = false;
		geocode_success_destination = false;
		geocode_hadErrors = false;
		
		updateWaypointList();
			
		}
	}
 	
//#########################################################################################
//#########################################################################################

function deleteSelectedWaypoint()
	{
	
	var selectedWaypointIndex = document.getElementById("waypointList").selectedIndex;	
	
	if (selectedWaypointIndex !== -1) //an option was selected and its index was returned
		{
		
		var tempMarker = global_waypointMarkers[selectedWaypointIndex];
		console.info("selectedWaypointIndex: " + selectedWaypointIndex);
		
		//remove the polyline from the map if one was calculated before the waypoint is supposed to be deleted
		if (global_polyline !== null && typeof global_polyline !== 'undefined')
			{
			map.removeOverlay(global_polyline);
			turnOnOffAddTripButton("off");
			}
		
		map.closeInfoWindow();	
		map.removeOverlay(tempMarker);
			
		console.log("global_waypointMarkers.length BEFORE splice: " + global_waypointMarkers.length);
		console.log("waypoint to be removed: (" + tempMarker.getPoint().lat() + "/" + tempMarker.getPoint().lng() + ")");
			
		global_waypointMarkers.splice(selectedWaypointIndex, 1);
		console.log("global_waypointMarkers.length AFTER splice: " + global_waypointMarkers.length);
		
		//now that the waypoint is deleted, the images of the other waypoint markers have to be adjusted so that the marker images are not 'out of sync'	
		updateWaypointMarkerProperties(selectedWaypointIndex);
		
		//call updateWaypointList
		updateWaypointList();
		
		//call function to adjust the id of the destination marker
		adjustDestinationMarkerId();
		}
		
	else //no waypoint selected in the list so don't do anything
		{
		alert("Please select a waypoint in the waypoint list first.");
		console.log("no waypoint selected so refusing to do anything!");	
		}	
	
	//once the waypoint is deleted, the images of the other waypoint markers have to be adjusted so that the marker images are not 'out of sync'
		
	}
	
//#########################################################################################
//#########################################################################################

function deleteSelectedTrip()
	{
	
	var response;
		
	var selectedTripIndex = document.getElementById("triplist").selectedIndex;	
	
	if (selectedTripIndex !== -1) //an option was selected and its index was returned
		{
		
		response = confirm("This will delete the selected trip. Are you sure?"); 
		
		if (response)
			{
			global_tripsLabelArray.splice(selectedTripIndex, 1);
			global_tripsVertices.splice(selectedTripIndex, 1);
			global_tripsMarkers.splice(selectedTripIndex, 1);
			global_tripsFrequency.splice(selectedTripIndex, 1);
			global_tripsMode.splice(selectedTripIndex, 1);
		
			map.clearOverlays();
			map.closeInfoWindow();	
			
			updateTripList();
			}

		}
		
	else //no waypoint selected in the list so don't do anything
		{
		console.log("no trip selected so refusing to do anything!");
		alert("Please select a trip in the trip list in order to delete the selected trip from the list.");	
		}	
	
	}
	
//#########################################################################################
//#########################################################################################

function eventTrigger (e) 
	{
  if (! e)
  	{
  	e = event;
  	}
  	
  return e.target || e.srcElement;
	}

//#########################################################################################
//#########################################################################################

function radioLabelClick(e) 
	{
  var obj = eventTrigger (e);
  
  if (obj)
  	{
  	console.info('radioLabelClick; You clicked on ' + obj.id);
  	global_labelIndex = obj.id;
		}
		
  return true;
	}

//#########################################################################################
//#########################################################################################

function radioTripFrequencyClick(e) 
	{
  var obj = eventTrigger (e);
  
  if (obj)
  	{
  	console.info('radioTripFrequencyClick; You clicked on ' + obj.value);
  	global_routeFrequency = obj.value;
		}
		
  return true;
	}
	
//#########################################################################################
//#########################################################################################

function radioTripModeClick(e) 
	{
  var obj = eventTrigger (e);
  
  if (obj)
  	{
  	console.info('radioTripModeClick; You clicked on ' + obj.value);
  	global_routeMode = obj.value;
		}
		
  return true;
	}	

//#########################################################################################
//#########################################################################################

function radioPlacemarkClick(e) 
	{
  var obj = eventTrigger (e);
    
  if (obj)
  	{
  	console.info('radioPlacemarkClick; You clicked on ' + obj.value);
  	global_placemarkIndex = obj.value;
		}
		
  return true;
	}


//#########################################################################################
//#########################################################################################

function storeOwnLabelInput(e) 
	{
  var obj = eventTrigger (e);
    
  if (obj)
  	{
  	console.info('storeLabelInput; You blurred the textfield:' + obj.value);
  	
  	global_ownLabel = obj.value;
  	global_labelIndex = "5";

		}
		
  return true;
	}


//#########################################################################################
//#########################################################################################

function clearOwnLabelField(e)
	{

	var obj = eventTrigger (e);

	if (obj)
  	{
  	  		
  	console.info('clearOwnLabelField; You clicked the textfield:' + obj.value);
  	obj.value = "";
  	
  	global_radioIndex = 5;
  	  	
  	obj.focus();
  	document.getElementById("5").checked = true;
  	//document.getElementById("5").click();
  	
		}
		
  return true;
  	
	}

//#########################################################################################
//#########################################################################################

function adjustDestinationMarkerId()
	{
		
	var tempMarker = map.getFirstMarker();
	
	while (tempMarker !== null)
		{
		
		if (tempMarker.getName() == "destination")
			{
			console.log("found the destination marker!");
			console.log("id of destination marker BEFORE the update: " + tempMarker.getId());
			//var newId = tempMarker.getId() + global_waypointMarkers.length;
			tempMarker.setId(global_waypointMarkers.length + 1);
			console.log("id of destination marker AFTER the update: " + tempMarker.getId());	
			}	
					
		tempMarker = map.getNextMarker();
		}
			
	}
	
//#########################################################################################
//#########################################################################################	

function assignNewLabel(marker_type)
	{
	
	if (marker_type == "origin")
  	{
  	
  	if (origin_marker !== null)
  		{
  	
  		origin_marker.openInfoWindowHtml(document.getElementById("labelLocationDiv").innerHTML);
	  	global_openOriginInfoWindowHtml = true;  
	  	  		
  		}
  		
  	else
  		{
  		alert("Please enter a start location first.");	
  		}
  			  				
  	}
  	
  else if (marker_type == "destination")
  	{
  		
  	if (destination_marker !== null)	
  		{
  		  		
  		destination_marker.openInfoWindowHtml(document.getElementById("labelLocationDiv").innerHTML); 
  		global_openDestinationInfoWindowHtml = true; 		  		  			

  		}
  		
  	else
  		{
  		alert("Please enter an end location first.");	
  		}
  			
   	}
   	
  else
  	{
  		
  	if (marker_type !== null)	
  		{
  		
  		console.log("The ID of the waypoint marker in assignNewLabel is: " + map.getMarkerById(marker_type).getId());
  		var tmpWPMarker = map.getMarkerById(marker_type);
  		console.log("tmpWPMarker.getId() is: " + tmpWPMarker.getId());
  		//global_openWaypointInfoWindowHtml = true;

  		tmpWPMarker.openInfoWindowHtml(document.getElementById("labelLocationDiv").innerHTML);
  		global_openWaypointInfoWindowHtml = true;  		  		  			

  		}
   	} 	
   				
	}

//#########################################################################################
//#########################################################################################
			
// A function to create the marker and set up the event window
function createClickMarker(marker, point) 
	{
	
	var myHelpMeDiv = document.getElementById("helpMeDiv");
		
	if (global_tmp_polyline !== null)
		{
		map.removeOverlay(global_tmp_polyline);
		global_tmp_polyline = null;
		turnOnOffAddTripButton("off");	
		
		var i;
		
		for (i = 0; i < global_tmp_routeMarkers.length; i++)
  		{
     	map.removeOverlay(global_tmp_routeMarkers[i]);
     	}
    
    global_tmp_routeMarkers = [];     	
		}	
	
	var icon;
	
	//console.log("createClickMarker marker; what is it?: " + marker);
  
  // Create a base icon for the (in this case) waypoint markers
	var baseIcon = new GIcon();
	baseIcon.shadow = "images/markers/shadow.png";
	baseIcon.iconSize = new GSize(20, 34);
	baseIcon.shadowSize = new GSize(37, 34);
	baseIcon.iconAnchor = new GPoint(9, 34);
	baseIcon.infoWindowAnchor = new GPoint(9, 2);
	baseIcon.infoShadowAnchor = new GPoint(18, 25);
		
	var num_waypoints = global_waypointMarkers.length;
	
	console.log("num_waypoints and global_waypointMarkers.length at the BEGINNING of createClickMarker are: " + num_waypoints + "/" + global_waypointMarkers.length);

	console.log("createClickMarker; what is !marker?: " + !marker);
  	
	if (!marker) //returns true if marker is null
		{
		
  	icon = new GIcon(baseIcon);
  	
  	//if the number of waypoints is > 99 then assign it the 0 marker (there are only 99 png files for indexed waypoint markers)
  	//all waypoints beyond 99 will use the 0 marker; the alternative would be to restrict the number of waypoints (<= 99)
  	if (num_waypoints > 98)
  		{
  		icon.image = "images/markers/marker0.png";	
  		}
  				
  	else
  		{	
  		icon.image = "images/markers/marker" + (num_waypoints + 1) + ".png";
  		}
  			
  	waypoint_marker = new PdMarker(new GLatLng(point.lat(),point.lng()), {icon: icon, draggable: true});
  			
  	GEvent.addListener(waypoint_marker, "dragstart", function() 
  		{
  		if (global_polyline !== null && typeof global_polyline !== 'undefined')
				{
				map.removeOverlay(global_polyline);
				turnOnOffAddTripButton("off");
				}
				
				map.closeInfoWindow();
  		});

		GEvent.addListener(waypoint_marker, "dragend", function()
  		{
  		updateWaypointList();
  		});
  	
 		GEvent.addListener(waypoint_marker, "infowindowclose", function() 
				{
	
				if (global_openWaypointInfoWindowHtml)
  				{
					var labelString;
				
					if (global_labelIndex == 5)
						{
						labelString = global_ownLabel;//document.getElementById("ownLabel").value;
						}
				
					else
						{
						labelString = document.getElementById(global_labelIndex).value;	
						}
			 				
 					this.setTooltip(labelString);
  				this.setUserData(labelString);
  				this.setOpacity(100);
  				
  				//waypoint_marker.setTooltip(labelString);
  				//waypoint_marker.setUserData(labelString);
  				//waypoint_marker.setOpacity(100);
  							
  				global_labelIndex = 0;
  			 			
  				var htmlInfoCloud = "<p>This location is:<br/><b>";
  				//htmlInfoCloud += waypoint_marker.getUserData();
  				htmlInfoCloud += this.getUserData();
  				//htmlInfoCloud += "</b></p><div align='right'><input type='button' class='menu_btns' value='Relabel' id='htmlCloudRelabelButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='assignNewLabel(\"" + waypoint_marker.getId() +"\");' /></div>";
  				htmlInfoCloud += "</b></p><div align='left'><input type='button' class='menu_btns' value='Relabel' id='htmlCloudRelabelButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='assignNewLabel(\"" + this.getId() +"\");' />&nbsp;<input type='button' class='menu_btns' value='Close' id='htmlCloudCloseButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='map.closeInfoWindow();' /></div>";
  			  
  			  global_openWaypointInfoWindowHtml = false;	  			
    			//waypoint_marker.openInfoWindowHtml(htmlInfoCloud);
    			this.openInfoWindowHtml(htmlInfoCloud);
    			}
  			
  			//return true so that close() does not abort
				return true;	
  			});
	  
	  if (global_polyline !== null && typeof global_polyline !== 'undefined')
			{
			map.removeOverlay(global_polyline);
			turnOnOffAddTripButton("off");
			}
	  			
  	map.addOverlay(waypoint_marker);
  	
  	myHelpMeDiv.innerHTML = "You have just created a waypoint marker. You can drag and drop the marker to another location if you are not satisfied with the placement of the marker. You can also change the label of the marker by clicking on the marker itself or on the 'Relabel' button underneath the respective text field.<br/>&nbsp;<br/>The trip will traverse the waypoints sequentially (starting at the start location, proceeding to waypoint #1, #2, and so forth, and ending at the end location). You can change the sequence of waypoints by using the up and down arrow next to the waypoint list.";
  	
  	var j = num_waypoints + 1;
  	
  	waypoint_marker.setTooltip("No Label");
  	waypoint_marker.setOpacity(100);
  			
  	//set name and id of the waypoint (starting with 1 as id)
  	waypoint_marker.setName("waypoint#" + j);
  	waypoint_marker.setId(j);
  	console.info("id of the waypoint: " + waypoint_marker.getId());
  	  			
  	//waypoint markers have to be stored in the global_waypoints array; it will append a new marker to the end of the array
  	global_waypointMarkers[num_waypoints] = waypoint_marker;
  	
  	console.log("num_waypoints and global_waypointMarkers.length at the END of createClickMarker are: " + num_waypoints + "/" + global_waypointMarkers.length);
  	
  	//call adjustDestinationMarkerId() to update the id of the destination marker
  	adjustDestinationMarkerId();
  			
  	//the next step is to update the selectbox list with the new item
  	updateWaypointList();
  	
  	htmlInfoCloud = "";
  		
  	htmlInfoCloud += "<p><label class='hd_labels'>This waypoint has currently no label.</label><br/></p>";
  	htmlInfoCloud += "<div align='left'><input type='button' class='huge_btns' value='Click here to assign a label to this waypoint!' id='waypointLabelYesButton' onmouseover='this.className=\"btnhov_hb\"' onmouseout='this.className=\"huge_btns\"' onClick='assignNewLabel(\"" + waypoint_marker.getId() + "\");' /></div>";
  	
  	global_openWaypointInfoWindowHtml = false;		  	  			
    waypoint_marker.openInfoWindowHtml(htmlInfoCloud);
   	
	  }
	}

//#########################################################################################
//#########################################################################################	

function handleWaypointMarkerClick(marker_in)
	{
	
	var myTmpMarker = map.getMarkerById(global_itIsMe);
	
	console.log("handleWaypointMarkerClick; myTmpMarker.getId()/marker_in.getId()? Answer: " + myTmpMarker.getId() + "/" + marker_in.getId());
	
	if (myTmpMarker.getName() !== "origin" && myTmpMarker.getName() !== "destination")
		{
			
		var htmlInfoCloud = "";
  		
  	if (myTmpMarker.getUserData() === "")
  		{
  		htmlInfoCloud += "<p>This waypoint has currently no label.<br/></p>";
  		htmlInfoCloud += "<div align='left'><input type='button' class='huge_btns' value='Click here to assign a label to this waypoint!' id='waypointLabelYesButton' onmouseover='this.className=\"btnhov_hb\"' onmouseout='this.className=\"huge_btns\"' onClick='assignNewLabel(\"" + marker_in.getId() + "\");' /></div>";
  		
  		global_openWaypointInfoWindowHtml = false;		  	  			
    	myTmpMarker.openInfoWindowHtml(htmlInfoCloud);
   		}
  			
  	else
  		{		
  		htmlInfoCloud += "<p>This location is:<br/><b>";
  		htmlInfoCloud += myTmpMarker.getUserData();
  		htmlInfoCloud += "</b></p><div align='right'><input type='button' class='menu_btns' value='Relabel' id='htmlCloudRelabelButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='assignNewLabel(\"" + marker_in.getId() + "\");' /></div>";
  		
  		global_openWaypointInfoWindowHtml = false;	  	  			
    	myTmpMarker.openInfoWindowHtml(htmlInfoCloud);
     	}		
			
		}

	}

//#########################################################################################
//#########################################################################################	

// A function to create the marker and set up the event window
function createMarker(marker, point, type_in) 
	{
	
	if (global_tmp_polyline !== null)
		{
		map.removeOverlay(global_tmp_polyline);
		global_tmp_polyline = null;
		turnOnOffAddTripButton("off");	
		
		var i;
		
		for (i = 0; i < global_tmp_routeMarkers.length; i++)
  		{
     	map.removeOverlay(global_tmp_routeMarkers[i]);
     	}
    
    global_tmp_routeMarkers = [];     	
		}	
		
  var pnt;
  var icon;
  
  var tooltipString;
  
  // Create a base icon for the start, end, and waypoint markers
	var baseIcon = new GIcon();
	baseIcon.shadow = "images/markers/shadow.png";
	baseIcon.iconSize = new GSize(20, 34);
	baseIcon.shadowSize = new GSize(37, 34);
	baseIcon.iconAnchor = new GPoint(9, 34);
	baseIcon.infoWindowAnchor = new GPoint(9, 2);
	baseIcon.infoShadowAnchor = new GPoint(18, 25);
	
	var myHelpMeDiv = document.getElementById("helpMeDiv");
      		
  if (type_in == "origin")
  	{
  	
  	//this is the only part that is actually happening
  	if (marker === null) //this is the only part that is actually happening	
  		{
  		console.log("type is ORIGIN, marker (i.e., first arg) IS null");
  		icon = new GIcon(baseIcon);
  		icon.image = "images/markers/start2.png";
  		//tempMarker = new PdMarker(new GLatLng(point.lat(),point.lng()), {icon: icon, draggable: true});
  		origin_marker = new PdMarker(new GLatLng(point.lat(),point.lng()), {icon: icon, draggable: true});
  		
  		GEvent.addListener(origin_marker, "dragstart", function() 
  			{
  			if (global_polyline !== null && typeof global_polyline !== 'undefined')
					{
					map.removeOverlay(global_polyline);
					turnOnOffAddTripButton("off");
					}
				
				map.closeInfoWindow();
				origin_marker.hideTooltip();	
  			});

			GEvent.addListener(origin_marker, "dragend", function() 
				{
  			//origin_marker.openInfoWindowHtml("Just bouncing along...");
  			//origin_marker.setTooltipHiding(false);
  			//origin_marker.showTooltip();
  			});
 			
  		GEvent.addListener(origin_marker, "click", function() 
  			{
  			
  			var htmlInfoCloud = "<p>This location is:<br/><b>";
  			htmlInfoCloud += origin_marker.getUserData();
  			//htmlInfoCloud += "</b></p><div align='right'><input type='button' class='menu_btns' value='Relabel' id='htmlCloudRelabelButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='assignNewLabel(\"origin\");' /></div>";
  			htmlInfoCloud += "</b></p><div align='left'><input type='button' class='menu_btns' value='Relabel' id='htmlCloudRelabelButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='assignNewLabel(\"origin\");' />&nbsp;<input type='button' class='menu_btns' value='Close' id='htmlCloudCloseButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='map.closeInfoWindow();' /></div>";
  			
  			console.log("GEvent.addListener(origin_marker, click) is this even happening? YES!!");  	  			
    		origin_marker.openInfoWindowHtml(htmlInfoCloud);
    		global_openOriginInfoWindowHtml = false;
  			});	
 			
  		origin_marker.setName("origin");
  		
  		//numbering of marker ids starts with 0
  		origin_marker.setId("0");
  		console.info("id of origin_marker: " + origin_marker.getId());
  		
  		//store the well-formated address as userData2
  		origin_marker.setUserData2(global_originAddressString);	
  		
  		map.addOverlay(origin_marker);
  		map.setCenter(origin_marker.getPoint(), 14);
  		
  		//change helpMe text here (give feedback that the origin marker has been created)
  		myHelpMeDiv.innerHTML = "A marker showing the location of the start address has been added to the map.<br/>&nbsp;<br/>You can drag and drop the marker to another location if you are not satisfied with the placement of the marker. You can also change the label of the marker by clicking on the marker itself or on the 'Relabel' button underneath the respective text field.";
  		
  		disableAddressInput('origin');
  		
  		origin_marker.openInfoWindowHtml(document.getElementById("labelLocationDiv").innerHTML);
  		global_openOriginInfoWindowHtml = true;
  		
  		GEvent.addListener(origin_marker, "infowindowclose", function() 
				{
	
				if (global_openOriginInfoWindowHtml)
  				{
					var labelString;
				
					if (global_labelIndex == 5)
						{
						labelString = global_ownLabel;//document.getElementById("ownLabel").value;
						}
				
					else
						{
						labelString = document.getElementById(global_labelIndex).value;	
						}
			 				
 					origin_marker.setTooltip(labelString);
  				origin_marker.setUserData(labelString);
  				origin_marker.setOpacity(100);
 			
  				global_labelIndex = 0;
  			 			
  				var htmlInfoCloud = "<p>This location is:<br/><b>";
  				htmlInfoCloud += this.getUserData();
  				//htmlInfoCloud += "</b></p><div style='color: red; font-size: 45%'>Hint: You can always change the label<br/>of the location by clicking on the 'Relabel' button<br/>underneath the respective address field.</div>";
  			  //htmlInfoCloud += "</b></p><div align='right'><input type='button' class='menu_btns' value='Relabel' id='htmlCloudRelabelButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='assignNewLabel(\"origin\");' /></div>";
  			  htmlInfoCloud += "</b></p><div align='left'><input type='button' class='menu_btns' value='Relabel' id='htmlCloudRelabelButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='assignNewLabel(\"origin\");' />&nbsp;<input type='button' class='menu_btns' value='Close' id='htmlCloudCloseButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='map.closeInfoWindow();' /></div>";
  			  
  			  global_openOriginInfoWindowHtml = false;	  			
    			origin_marker.openInfoWindowHtml(htmlInfoCloud);
    			}
  			
  			//return true so that close() does not abort
				return true;	
  			});
  		
  		}
  	}
  	
  else if (type_in == "destination")		
  	{
  	
  	//this is the only part that is actually happening
  	if (marker === null) //this is the only part that is actually happening
  		{
  		console.log("type is DESTINATION, marker (i.e., first arg) IS null");
  		icon = new GIcon(baseIcon);
  		icon.image = "images/markers/end2.png";
  		//tempMarker = new PdMarker(new GLatLng(point.lat(),point.lng()), {icon: icon, draggable: true});	
	 		destination_marker = new PdMarker(new GLatLng(point.lat(),point.lng()), {icon: icon, draggable: true});	
	 		
	 		GEvent.addListener(destination_marker, "dragstart", function() 
  			{
  			
  			if (global_polyline !== null && typeof global_polyline !== 'undefined')
					{
					map.removeOverlay(global_polyline);
					turnOnOffAddTripButton("off");
					}
				
				map.closeInfoWindow();
				destination_marker.hideTooltip();	
  			});

			GEvent.addListener(destination_marker, "dragend", function() 
				{
  			//destination_marker.openInfoWindowHtml("Just bouncing along...");
  			//destination_marker.setTooltipHiding(false);
  			//destination_marker.showTooltip();
  			});
  			
  		GEvent.addListener(destination_marker, "click", function() 
  			{
    		var htmlInfoCloud = "<p>This location is:<br/><b>";
  			htmlInfoCloud += destination_marker.getUserData();
  			//htmlInfoCloud += "</b></p><div style='color: red; font-size: smaller'>Hint: You can always change the label<br/>of the location by clicking on the 'Relabel' button<br/>underneath the respective address field.</div>";
  			//htmlInfoCloud += "</b></p><div align='right'><input type='button' class='menu_btns' value='Relabel' id='htmlCloudRelabelButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='assignNewLabel(\"destination\");' /></div>";
  			htmlInfoCloud += "</b></p><div align='left'><input type='button' class='menu_btns' value='Relabel' id='htmlCloudRelabelButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='assignNewLabel(\"destination\");' />&nbsp;<input type='button' class='menu_btns' value='Close' id='htmlCloudCloseButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='map.closeInfoWindow();' /></div>";
  			  	  			
    		destination_marker.openInfoWindowHtml(htmlInfoCloud);
    		global_openDestinationInfoWindowHtml = false;
  			});	
  			
  		destination_marker.setName("destination");
  		
  		//destination_marker id is current as of the moment of creation; has to be updated when a new waypoint is added
  		destination_marker.setId(global_waypointMarkers.length + 1);
  		console.info("id of destination_marker: " + destination_marker.getId());
  		
  		//store the well-formated address as userData2
  		destination_marker.setUserData2(global_destinationAddressString);	
	 		
  		map.addOverlay(destination_marker);
  		map.setCenter(destination_marker.getPoint(), 14);
  		
  		//change helpMe text here (give feedback that the origin marker has been created)
			myHelpMeDiv.innerHTML = "A marker showing the location of the end address has been added to the map.<br/>&nbsp;<br/>You can drag and drop the marker to another location if you are not satisfied with the placement of the marker. You can also change the label of the marker by clicking on the marker itself or on the 'Relabel' button underneath the respective text field.";
  		
  		disableAddressInput('destination');
  		
  		//labelLocationPopup = dhtmlwindow.open('labelLocationWindow', 'div', 'labelLocationDiv', 'Label the Location', 'width=430px,height=250px,center=1,resize=0,scrolling=1');
  		destination_marker.openInfoWindowHtml(document.getElementById("labelLocationDiv").innerHTML);
  		global_openDestinationInfoWindowHtml = true;
  		
  		GEvent.addListener(destination_marker, "infowindowclose", function() 
				{

  			if (global_openDestinationInfoWindowHtml)
  				{			
				
					var labelString;
				
					if (global_labelIndex == 5)
						{
						labelString = global_ownLabel;//document.getElementById("ownLabel").value;
						}
				
					else
						{
						labelString = document.getElementById(global_labelIndex).value;	
						}
 				
 					destination_marker.setTooltip(labelString);
  				destination_marker.setUserData(labelString);
  				destination_marker.setOpacity(100);
  			
  				var htmlInfoCloud = "<p>This location is:<br/><b>";
  				htmlInfoCloud += destination_marker.getUserData();
  				//htmlInfoCloud += "</b></p><div style='color: red; font-size: smaller'>Hint: You can always change the label<br/>of the location by clicking on the 'Relabel' button<br/>underneath the respective address field.</div>";
  			  //htmlInfoCloud += "</b></p><div align='right'><input type='button' class='menu_btns' value='Relabel' id='htmlCloudRelabelButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='assignNewLabel(\"destination\");' /></div>";
  			  htmlInfoCloud += "</b></p><div align='left'><input type='button' class='menu_btns' value='Relabel' id='htmlCloudRelabelButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='assignNewLabel(\"destination\");' />&nbsp;<input type='button' class='menu_btns' value='Close' id='htmlCloudCloseButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='map.closeInfoWindow();' /></div>";
  			  
  			  global_openDestinationInfoWindowHtml = false;  			  	  			
    			destination_marker.openInfoWindowHtml(htmlInfoCloud);
  				}
  			
  			//return true so that close() does not abort
				return true;	
  			});  		
	 		}
					    			
  	}
  	
  else if (type_in == "waypoint")		
  	{
  	
  	var htmlInfoCloud;
  	  		
  	var num_waypoints = global_waypointMarkers.length;
  	
  	console.log("num_waypoints and global_waypointMarkers.length at the BEGINNING of createMarker are: " + num_waypoints + "/" + global_waypointMarkers.length);
  	
  	if (num_waypoints > 0)
  		{
  		
  		console.log("YES I AM HERE 1");
  		
  		var exists_same_waypoint = false;
  		
  		console.log("YES I AM HERE 2");
  			
  		for (var l = 0; l < num_waypoints; l++)
  			{
   			
   			console.log("YES I AM HERE 3");
   			
   			console.log("What is i? Answer: " + l);
   				
  			pnt = global_waypointMarkers[l].getPoint();
  			
  			console.log("Do we get here or what is wrong with pnt?");	
  			  			
  			//the test here has to go over all the waypoints in global_waypoints; if a match is found then then remember that there was a match
  			if (pnt.lat() == point.lat() && pnt.lng() == point.lng())
  				{
  				console.log("type is WAYPOINT, marker (i.e., the length of the global_waypoints array) is NOT 0, coordinates of another marker in global_waypoints and the new marker so assign true to exists_waypoint.");	
   				console.log("There was a match between the marker and global_waypoints.");
   				console.log("pnt_in (lat/lng): (" + pnt.lat() + "/" + pnt.lng() + ").");
   				console.log("point (lat/lng): (" + point.lat() + "/" + point.lng() + ").");
   				exists_same_waypoint = true;
  				}
  				
  			else
  				{
  				console.log("No match between the marker and global_waypoints.");
   				console.log("pnt_in (lat/lng): (" + pnt.lat() + "/" + pnt.lng() + ").");
   				console.log("point (lat/lng): (" + point.lat() + "/" + point.lng() + ").");
  				}	
  				
  			}
  		
  		if (!exists_same_waypoint) //if the new waypoint is unique (no other waypoint matches the new waypoint)
  			{
  			console.log("type is WAYPOINT, num_waypoints is NOT 0, and coordinates of the new marker and markers in global_waypoints are different");	

  			icon = new GIcon(baseIcon);
  			
  			//if the number of waypoints is > 99 then assign it the 0 marker (there are only 99 png files for indexed waypoint markers)
  			//all waypoints beyond 99 will use the 0 marker; the alternative would be to restrict the number of waypoints (<= 99)
  			if (num_waypoints > 98)
  				{
  				icon.image = "images/markers/marker0.png";	
  				}
  				
  			else
  				{	
  				icon.image = "images/markers/marker" + (num_waypoints + 1) + ".png";
  			  }
  			  		
  			//tempMarker = new PdMarker(new GLatLng(point.lat(),point.lng()), {icon: icon, draggable: true});
  			
  			waypoint_marker = new PdMarker(new GLatLng(point.lat(),point.lng()), {icon: icon, draggable: true});
  			
  			GEvent.addListener(waypoint_marker, "dragstart", function() 
  				{
  			
  				if (global_polyline !== null && typeof global_polyline !== 'undefined')
						{
						map.removeOverlay(global_polyline);
						turnOnOffAddTripButton("off");
						}
					
					map.closeInfoWindow();	
  				});

				GEvent.addListener(waypoint_marker, "dragend", function()
  				{
  				updateWaypointList();
  				});
  			
 		 		GEvent.addListener(waypoint_marker, "infowindowclose", function() 
					{
	
					if (global_openWaypointInfoWindowHtml)
  					{
						var labelString;
				
						if (global_labelIndex == 5)
							{
							labelString = global_ownLabel;//document.getElementById("ownLabel").value;
							}
				
						else
							{
							labelString = document.getElementById(global_labelIndex).value;	
							}
			 				
 						this.setTooltip(labelString);
  					this.setUserData(labelString);
  					this.setOpacity(100);
  							
  					global_labelIndex = 0;
  			 			
  					var htmlInfoCloud = "<p>This location is:<br/><b>";
  					//htmlInfoCloud += waypoint_marker.getUserData();
  					htmlInfoCloud += this.getUserData();
  					//htmlInfoCloud += "</b></p><div align='right'><input type='button' class='menu_btns' value='Relabel' id='htmlCloudRelabelButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='assignNewLabel(\"" + waypoint_marker.getId() +"\");' /></div>";
  					//htmlInfoCloud += "</b></p><div align='right'><input type='button' class='menu_btns' value='Relabel' id='htmlCloudRelabelButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='assignNewLabel(\"" + this.getId() +"\");' /></div>";
  			  	htmlInfoCloud += "</b></p><div align='left'><input type='button' class='menu_btns' value='Relabel' id='htmlCloudRelabelButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='assignNewLabel(\"" + this.getId() +"\");' />&nbsp;<input type='button' class='menu_btns' value='Close' id='htmlCloudCloseButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='map.closeInfoWindow();' /></div>";
  			  
  			  	global_openWaypointInfoWindowHtml = false;	  			
    				//waypoint_marker.openInfoWindowHtml(htmlInfoCloud);
    				this.openInfoWindowHtml(htmlInfoCloud);
    				}
  			
  				//return true so that close() does not abort
					return true;	
  				});
  			    		
    		waypoint_marker.setTooltip("No Label");
  			waypoint_marker.setOpacity(100);
    		
    		if (global_polyline !== null && typeof global_polyline !== 'undefined')
					{
					map.removeOverlay(global_polyline);
					turnOnOffAddTripButton("off");
					}
    			
				map.addOverlay(waypoint_marker);
  			map.setCenter(waypoint_marker.getPoint(), 14);
  			
  			//change helpMe text here (give feedback that the origin marker has been created)
				myHelpMeDiv.innerHTML = "You have just created a marker representing a stop. You can drag and drop the marker to another location if you are not satisfied with the placement of the marker. You can also change the label of the marker by clicking on the marker itself or on the 'Relabel' button underneath the respective text field.<br/>&nbsp;<br/>The trip will traverse the stops according to their number (starting at the start location, proceeding to stop #1, #2, and so forth, and ending at the end location). You can change the sequence of stops by using the up and down arrow next to the list of stops.";
  			
  			//waypoint markers have to be stored in the global_waypointMarkers array; it will append a new marker to the end of the array
  			global_waypointMarkers[num_waypoints] = waypoint_marker;
  			
  			var j = num_waypoints + 1;
  			
  			//set name and id of the waypoint (starting with 1 as id)
  			waypoint_marker.setName("waypoint#" + j);
  			waypoint_marker.setId(j);
  			console.info("id of the waypoint: " + waypoint_marker.getId());
  			
  			//store the well-formated address as userData2
  			waypoint_marker.setUserData2(global_waypointAddressString);	
  			
  			console.log("num_waypoints and global_waypointMarkers.length at the END of createMarker for waypoints > 0 are: " + num_waypoints + "/" + global_waypointMarkers.length);
  			
  			//call adjustDestinationMarkerId() to update the id of the destination marker
  			adjustDestinationMarkerId();
  			
  			//the next step is to update the selectbox list with the new item
  			updateWaypointList();
  			
  			htmlInfoCloud = "";
  		
  			htmlInfoCloud += "<p>This waypoint has currently no label.<br/></p>";
  			htmlInfoCloud += "<div align='right'><input type='button' class='huge_btns' value='Click here to assign a label to this waypoint!' id='waypointLabelYesButton' onmouseover='this.className=\"btnhov_hb\"' onmouseout='this.className=\"huge_btns\"' onClick='assignNewLabel(\"" + waypoint_marker.getId() + "\");' /></div>"; 
  			  			  	  			
    		waypoint_marker.openInfoWindowHtml(htmlInfoCloud);
    		global_openWaypointInfoWindowHtml = false;	

	 			}
	 			
	 		//in the else case (type is WAYPOINT, num_waypoints is NOT 0, coordinates of the new marker and a marker in global_waypoints are the same) adjust the center of the map only; the test for equality is above in the for loop		 			
	 		else
	 			{
	 			map.setCenter(point, 14);
	 			}
	 		
  		}	
  		
  	else if (num_waypoints === 0) //shouldn't be necessary to check for num_waypoints == 0 but doing just to make sure (for debugging purposes) 
  		{
  			
  		//var htmlInfoCloud;
  			
  		console.log("type is WAYPOINT and num_waypoints IS 0");
  		icon = new GIcon(baseIcon);
  		
  		var k = num_waypoints + 1;
  		
  		icon.image = "images/markers/marker" + k + ".png";
  		  		
  		//tempMarker = new PdMarker(new GLatLng(point.lat(),point.lng()), {icon: icon, draggable: true});
  		  		
  		waypoint_marker = new PdMarker(new GLatLng(point.lat(),point.lng()), {icon: icon, draggable: true});
  		
  		GEvent.addListener(waypoint_marker, "dragstart", function() 
  			{
  			
  			if (global_polyline !== null && typeof global_polyline !== 'undefined')
					{
					map.removeOverlay(global_polyline);
					turnOnOffAddTripButton("off");
					}
					
				map.closeInfoWindow();					
  			});

			GEvent.addListener(waypoint_marker, "dragend", function()
  			{
  			updateWaypointList();
  			});
    		
    	GEvent.addListener(waypoint_marker, "infowindowclose", function() 
				{
	
				if (global_openWaypointInfoWindowHtml)
  				{
					var labelString;
				
					if (global_labelIndex == 5)
						{
						labelString = global_ownLabel;//document.getElementById("ownLabel").value;
						}
				
					else
						{
						labelString = document.getElementById(global_labelIndex).value;	
						}
			 				
 					this.setTooltip(labelString);
  				this.setUserData(labelString);
  				this.setOpacity(100);
				
  				global_labelIndex = 0;
  			 			
  				var htmlInfoCloud = "<p>This location is:<br/><b>";
  				htmlInfoCloud += this.getUserData();
  				htmlInfoCloud += "</b></p><div align='left'><input type='button' class='menu_btns' value='Relabel' id='htmlCloudRelabelButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='assignNewLabel(\"" + waypoint_marker.getId() +"\");' />&nbsp;<input type='button' class='menu_btns' value='Close' id='htmlCloudCloseButton' onmouseover='this.className=\"btnhov\"' onmouseout='this.className=\"menu_btns\"' onClick='map.closeInfoWindow();' /></div>";
  			  
  			  global_openWaypointInfoWindowHtml = false;	  			
    			//waypoint_marker.openInfoWindowHtml(htmlInfoCloud);
    			this.openInfoWindowHtml(htmlInfoCloud);
    			}
  			
  			//return true so that close() does not abort
				return true;	
  			});	 

  		console.log("just before the addOverlay");	
  		
  		waypoint_marker.setTooltip("No Label");
  		waypoint_marker.setOpacity(100);
  		
  		if (global_polyline !== null && typeof global_polyline !== 'undefined')
				{
				map.removeOverlay(global_polyline);
				turnOnOffAddTripButton("off");
				}
  		  			
  		map.addOverlay(waypoint_marker);
  		map.setCenter(waypoint_marker.getPoint(), 14);
  		
  		//change helpMe text here (give feedback that the origin marker has been created)
			myHelpMeDiv.innerHTML = "You have just created a marker representing a stop. You can drag and drop the marker to another location if you are not satisfied with the placement of the marker. You can also change the label of the marker by clicking on the marker itself or on the 'Relabel' button underneath the respective text field.<br/>&nbsp;<br/>The trip will traverse the stops according to their number (starting at the start location, proceeding to stop #1, #2, and so forth, and ending at the end location). You can change the sequence of stops by using the up and down arrow next to the list of stops.";
  		
  		console.log("just after the addOverlay");
  		
  		//set name and id of the waypoint (starting with 1 as id)
  		waypoint_marker.setName("waypoint#" + k);
  		waypoint_marker.setId(k);
  		console.info("id of the waypoint: " + waypoint_marker.getId());
  		
  		//store the well-formated address as userData2
  		waypoint_marker.setUserData2(global_waypointAddressString);	
  		
  		//waypoint markers have to be stored in the global_waypoints array; in this case (num_waypoints = 0) new markers will be inserted at the beginning of the array
  		//global_waypoints[0] = waypoint_marker.getPoint(); //should probably only keep one of these arrays in order to minimize cross-indexing problems
  		global_waypointMarkers[0] = waypoint_marker;
  		
  		console.log("num_waypoints and global_waypointMarkers.length at the END of createMarker for waypoints = 0 are: " + num_waypoints + "/" + global_waypointMarkers.length);
  		
  		//call function to update the id of the destination marker
  		adjustDestinationMarkerId();
  		  		  		
  		//the next step is to update the selectbox list with the new item
  		updateWaypointList();
  		
  		htmlInfoCloud = "";
  		
 			htmlInfoCloud += "<p>This waypoint has currently no label.<br/></p>";
 			htmlInfoCloud += "<div align='left'><input type='button' class='huge_btns' value='Click here to assign a label to this waypoint!' id='waypointLabelYesButton' onmouseover='this.className=\"btnhov_hb\"' onmouseout='this.className=\"huge_btns\"' onClick='assignNewLabel(\"" + waypoint_marker.getId() + "\");' /></div>";
  			  	  			
   		waypoint_marker.openInfoWindowHtml(htmlInfoCloud);
   		global_openWaypointInfoWindowHtml = false;	

			}		    			
  	}	
	}

//#########################################################################################
//#########################################################################################

//two helper functions to round the lat/lng values of the waypoint markers for better display
function roundToPrecision(inputNum, desiredPrecision)
	{
  var precisionGuide = Math.pow(10, desiredPrecision);
  return( Math.round(inputNum * precisionGuide) / precisionGuide );
	}

//converts the input number into a string and adds zeroes
//until the desired precision is reached and then
//returns the new string
function addZeroesToPrecision(inputNum, desiredPrecision)
	{
  var numString = inputNum + "";
  var afterDecimalString = numString.substring(numString.search(/\./) + 1);
  while (afterDecimalString.length < desiredPrecision) 
  	{
    afterDecimalString += "0";
    numString += "0";
 		}
 		
  return(numString);
	}

//#########################################################################################
//#########################################################################################		

function updateWaypointList()
	{
		
	var myOptionsHtmlString = "";
	    	
	for (var i = 0; i < global_waypointMarkers.length; i++)
  	{
  	
		var roundedLat = roundToPrecision(global_waypointMarkers[i].getPoint().lat(), 6);
		var roundedLng = roundToPrecision(global_waypointMarkers[i].getPoint().lng(), 6);
		
		var roundedLatString = addZeroesToPrecision(roundedLat, 6);
		var roundedLngString = addZeroesToPrecision(roundedLng, 6);		
 
    //global_optionsHtmlArray[i] = "<option name='waypoint" + i + "'>#" + global_waypointMarkers[i].getId() + ": (" + global_waypointMarkers[i].getPoint().lat() + "/" + global_waypointMarkers[i].getPoint().lng() + ")</option>";
    myOptionsHtmlString += "<option name='waypoint" + i + "'>#" + global_waypointMarkers[i].getId() + ": (" + roundedLatString + "/" + roundedLngString + ")</option>";
        
    }
    
  console.log("myOptionsHtmlString: " + myOptionsHtmlString);	 
    	    	
	//write new options to the myWaypointListDiv
  var myWaypointListDiv = document.getElementById("waypointListDiv");

	var myWaypoinytListHtmlLeftPart = "<select class='lists' onClick='waypointSelectionChanged();' id='waypointList' size='4' />";
	var myWaypoinytListHtmlRigthPart = "</select>";
    	
  myWaypointListDiv.innerHTML = myWaypoinytListHtmlLeftPart + myOptionsHtmlString + myWaypoinytListHtmlRigthPart;
      	
  console.log("myWaypointListDiv.innerHTML: " + myWaypointListDiv.innerHTML);	
		
	}

//#########################################################################################
//#########################################################################################

function updateTripList(is_init)
	{
		
	var myOptionsHtmlString = "";
	    	
	for (var i = 0; i < global_tripsLabelArray.length; i++)
  	{
 
    myOptionsHtmlString += "<option name='trip" + i + "'>" + global_tripsLabelArray[i] + "</option>";
        
    }
    
  console.log("myOptionsHtmlString in updateTripList(): " + myOptionsHtmlString);	 
    	    	
	//write new options to the myTripListDiv
  var myTripListDiv = document.getElementById("triplistDiv");

	var myTripListHtmlLeftPart = "<select class='tripList' onClick='tripSelectionChanged();' id='triplist' size='4' />";
	var myTripListHtmlRigthPart = "</select>";
    	
  myTripListDiv.innerHTML = myTripListHtmlLeftPart + myOptionsHtmlString + myTripListHtmlRigthPart;
      	
  console.log("myTripListDiv.innerHTML: " + myTripListDiv.innerHTML);
  
  //set flag that indicates changes to the trip list
  if (!is_init)
  	{
		any_changes = true;
		}	
	}

//#########################################################################################
//#########################################################################################

function waypointSelectionChanged()
	{
	
	var mySelectedIndex = document.getElementById("waypointList").selectedIndex;
	
	if (mySelectedIndex !== -1)
		{
		map.setCenter(global_waypointMarkers[mySelectedIndex].getPoint(), map.getZoom());	
		}
		
	}
	
//#########################################################################################
//#########################################################################################

function tripSelectionChanged()
	{
		
	var mySelectedIndex = document.getElementById("triplist").selectedIndex;
	
	if (mySelectedIndex !== -1)
		{
		
		var myHelpMeDiv = document.getElementById("helpMeDiv"); 
		myHelpMeDiv.innerHTML = "You just clicked on one of your stored trips in your trip list. The respective trip is being shown on the map. You can view one trip at a time.<br/>&nbsp;<br/>Please note that you can not make any changes to the trip. As for now, the only way to modify a trip is to create a new trip that incorporates the desired changes and delete the trip that is no longer necessary.";
		
		if (global_tmp_polyline !== null)
			{
			map.removeOverlay(global_tmp_polyline);
			global_tmp_polyline = null;	
		
			var i;
		
			for (i = 0; i < global_tmp_routeMarkers.length; i++)
  			{
     		map.removeOverlay(global_tmp_routeMarkers[i]);
     		}
    
 	 		global_tmp_routeMarkers = [];     	
			}
	
		map.clearOverlays(); //clear all overlays when a trip in the list is clicked
		turnOnOffAddTripButton("off"); //disable the 'add your trip' button
		clearAllControls(false); //clear all controls
	
		global_tmp_routeMarkers = global_tripsMarkers[mySelectedIndex];
	
		var myMarkerCount = global_tmp_routeMarkers.length;
	
		for (i = 0; i < myMarkerCount; i++)
  		{
		
			if (i === 0)
				{
				map.addOverlay(global_tmp_routeMarkers[i]);
				}
			
			else if (i == (myMarkerCount - 1))
				{
				map.addOverlay(global_tmp_routeMarkers[i]);
				}	
			
			else
				{	
				map.addOverlay(global_tmp_routeMarkers[i]);
				}	
		
			}
	
		global_tmp_polyline = setPolylineAppearance(global_tripsVertices[mySelectedIndex], global_tripsMode[mySelectedIndex]);
	
		map.addOverlay(global_tmp_polyline);
		map.setZoom(map.getBoundsZoomLevel(global_tmp_polyline.getBounds()));
		map.setCenter(global_tmp_polyline.getBounds().getCenter());
		}
	}

//#########################################################################################
//#########################################################################################	

function populateControls(json_in)
	{
	console.log("Here is populateControls()!!");	
	}

//#########################################################################################
//#########################################################################################	
	
	function createRouteArray()
		{
		
		//this is not checking yet if the start or end marker are null, should happen here or in a different function!!!!!!
		if (origin_marker === null || destination_marker === null)
			{
			console.log("Either the origin or the destination was not specified. Will result in a 601 error.");
			}
		
		else
			{
		
			//reset global_routePoints and routeMarkers arrays
			global_routePoints = [];
			global_routeMarkers = [];
						
			var num_waypoints = global_waypointMarkers.length;	
		  			
  		//add start point to the routePoints array at index 0; add origin_marker to global_routeMarkers at index 0
  		global_routePoints[0] = origin_marker.getPoint();
  		global_routeMarkers[0] = origin_marker;
		
			//add start point to the routePoints/routeMarkers array at the correct index depending on the number of points in the waypoints array
  		if (num_waypoints > 0)
  			{
  		
  			//append the waypoints to the global_routePoints array 	
  			for (var ind = 0; ind < num_waypoints; ind++)
  				{
  				global_routePoints[ind + 1]	= global_waypointMarkers[ind].getPoint();
  				global_routeMarkers[ind + 1] = global_waypointMarkers[ind];
  				}
  		
  			//and add the end point/destination marker to the end of the global_routePoints/global_routeMarkers array
  			//both approaches should be equivalent
   			//global_routePoints[num_waypoints + 1] = destination_marker.getPoint();
   			console. log("Let's see if both indexes for the point array match: (1) " + (num_waypoints + 1) + ", (2) " + global_routePoints.length);
   			console. log("Let's see if both indexes for the marker array match: (1) " + (num_waypoints + 1) + ", (2) " + global_routeMarkers.length);
   			global_routePoints[global_routePoints.length] = destination_marker.getPoint();
   			global_routeMarkers[global_routeMarkers.length] = destination_marker;	
   			
  			}
  			
  		else if (num_waypoints === 0)
  			{
  			//If you get here then the waypoints are zero so the length of global_routePoints should at least two
  			console. log("So the length of global_routePoints should always be two in this case (i.e., zero waypoints): " + global_routePoints.length);
  			global_routePoints[global_routePoints.length] = destination_marker.getPoint();
  			global_routeMarkers[global_routeMarkers.length] = destination_marker;			
  			}
			
			}
		}

//#########################################################################################
//#########################################################################################

function helpInstruction(e)
	{
	
	var obj = eventTrigger (e);

	if (obj)
  	{
  	 
  	console.info('helpInstruction; You clicked the textfield: ' + obj.id);  		
  	var myHelpMeDiv = document.getElementById("helpMeDiv");
  	
 		if (obj.className == "fieldsInitial")
 			{
  			
 			obj.className = "fields";	
 			obj.value = "";
  			
 			}
  	
  	if (obj.id == "viaAddressField")
  		{
  		myHelpMeDiv.innerHTML = "Waypoints can be created by either clicking on the map or by entering an address. The format of the address can be a place name (e.g., Seattle Airport), the name of an intersection (comprised of the respective street names), or any combination of street address, city name, state, and 5-digit zip code. The more specific you are, the higher the probability that we will find the place that you meant.<br>&nbsp;<br/>A short tip: Don't forget to click the 'Add' button once you are done entering the information!"; 	
  		}
  	
  	else
  		{
  		myHelpMeDiv.innerHTML = "The start and end location of your trip can be entered as address. The format of the address can be a place name (e.g., Seattle Airport), the name of an intersection (comprised of the respective street names), or any combination of street address, city name, state, and 5-digit zip code. The more specific you are, the higher the probability that we will find the place that you meant.<br>&nbsp;<br/>A short tip: Don't forget to click the 'Add' button once you are done entering the information!"; 	
  		}		
  	
		}
		
	return true;
		
	}


//#########################################################################################
//#########################################################################################

//Called whenever a start or end marker is added to the map after a successful geocode
function disableAddressInput(type_in)
	{
		
	var myTempTxtField;	
	var myTempButton;
	
	if (type_in == 'origin')
		{
		myTempTxtField = document.getElementById("fromAddressField");
		myTempTxtField.className = "dis_fields";
		myTempTxtField.readOnly = true;
		
		myTempTxtField.onclick = function()
			{
			alert("This text field is currently disabled since you have already entered a start address. Please click the 'Clear' button if you want to enter a different start address.");			
			};	
		
		myTempButton = document.getElementById("originAddButton");	
		myTempButton.className = "dis_menu_btns";
		
		myTempButton.onmouseover = function()
			{
			//do nothing
			};
			
		myTempButton.onmouseout = function()
			{
			//do nothing
			};
		
		myTempButton.onclick = function()
			{
			alert("This button is currently disabled since you have already entered a start address. Please click the 'Clear' button if you want to enter a different start address.");		
			};		
		}
		
	else if (type_in == 'destination')
		{
		myTempTxtField = document.getElementById("toAddressField");
		myTempTxtField.className = "dis_fields";
		myTempTxtField.readOnly = true;
		
		myTempTxtField.onclick = function()
			{
			alert("This text field is currently disabled since you have already entered an end address. Please click the 'Clear' button if you want to enter a different end address.");			
			};	
		
		myTempButton = document.getElementById("destinationAddButton");	
		myTempButton.className = "dis_menu_btns";
		
		myTempButton.onmouseover = function()
			{
			//do nothing
			};
			
		myTempButton.onmouseout = function()
			{
			//do nothing
			};
		
		myTempButton.onclick = function()
			{
			alert("This button is currently disabled since you have already entered an end address. Please click the 'Clear' button if you want to enter a different end address.");		
			};		
		}	
		
	}
	
//#########################################################################################
//#########################################################################################

//Called whenever a start or end marker is added to the map after a successful geocode
function turnOnOffAddTripButton(onOrOff)
	{
		
	var myTempButton;
	
	if (onOrOff == 'on')
		{
		myTempButton = document.getElementById("addTripButton");	
		myTempButton.className = "fill_btn";
		
		myTempButton.onmouseover = function()
			{
			this.className = 'btnhov_fbtn';
			};
			
		myTempButton.onmouseout = function()
			{
			this.className = "fill_btn";
			};
		}
		
	else if (onOrOff == 'off')
		{
		myTempButton = document.getElementById("addTripButton");	
		myTempButton.className = "dis_fill_btn";
		
		myTempButton.onmouseover = function()
			{
			this.className = 'dis_fill_btn';
			};
			
		myTempButton.onmouseout = function()
			{
			this.className = "dis_fill_btn";
			};	
		}	
		
	}	

//#########################################################################################
//#########################################################################################	

//Called when the clear button underneath the address fields is clicked; clears the respective field,
//sets the respective marker = null, and enables the add button again
function clearStartStop(type_in, invoker)
	{
	
	var myButton;
	var myTxtField;
	
	if (type_in == 'origin')
		{
			
		if (origin_marker === null)
			{
				
			if (invoker == "button")
				{
				document.getElementById("fromAddressField").value = "";	
				}
			
			else
				{
				myTxtField = document.getElementById("fromAddressField");
				myTxtField.className = "fieldsInitial";	
				myTxtField.value = "Street, City, State, Place Name or Intersection";
				myTxtField.readOnly = false;
			
				myTxtField.onclick = function(e)
					{
					return helpInstruction(e);
					};	
				}
	
			}
			
		else
			{
				
			myTxtField = document.getElementById("fromAddressField");
			myTxtField.className = "fieldsInitial";	
			myTxtField.value = "Street, City, State, Place Name or Intersection";
			myTxtField.readOnly = false;
			
			myTxtField.onclick = function(e)
				{
				return helpInstruction(e);
				};

			map.closeInfoWindow();
			map.removeOverlay(origin_marker);
			
			console.log("Origin marker was removed!");
			origin_marker = null;	
			myButton = document.getElementById("originAddButton");
			myButton.className = "menu_btns";
		
			myButton.onmouseover = function()
				{
				myButton.className = "btnhov";
				};
			
			myButton.onmouseout = function()
				{
				myButton.className = "menu_btns";
				};	
				
			myButton.onclick = function()
				{
				geocodeAddress(document.getElementById('fromAddressField').value, 'origin');
				};	
			
			if (global_polyline !== null && typeof global_polyline !== 'undefined')
				{
				map.removeOverlay(global_polyline);
				}
			}	
		
		}
		
	else if (type_in == 'destination')
		{
			
		if (destination_marker === null)
			{
			
			if (invoker == "button")
				{
				document.getElementById("toAddressField").value = "";
				}
			
			else
				{
				myTxtField = document.getElementById("toAddressField");
				myTxtField.className = "fieldsInitial";	
				myTxtField.value = "Street, City, State, Place Name or Intersection";
				myTxtField.readOnly = false;
			
				myTxtField.onclick = function(e)
					{
					return helpInstruction(e);
					};	
				}

			}
		
		else
			{
				
			myTxtField = document.getElementById("toAddressField");
			myTxtField.className = "fieldsInitial";	
			myTxtField.value = "Street, City, State, Place Name or Intersection";
			myTxtField.readOnly = false;
			
			myTxtField.onclick = function(e)
				{
				return helpInstruction(e);
				};	

			map.closeInfoWindow();
			map.removeOverlay(destination_marker);
			
			console.log("Destination marker was removed!");
			destination_marker = null;
			
			myButton = document.getElementById("destinationAddButton");
			myButton.className = "menu_btns";
		
			myButton.onmouseover = function()
				{
				myButton.className = "btnhov";
				};
			
			myButton.onmouseout = function()
				{
				myButton.className = "menu_btns";
				};
								
			myButton.onclick = function()
				{
				geocodeAddress(document.getElementById('toAddressField').value, 'destination');
				};			
			
			if (global_polyline !== null && typeof global_polyline !== 'undefined')
				{
				map.removeOverlay(global_polyline);
				}
			}	
		
		}	
		
	}

//#########################################################################################
//#########################################################################################
//Server-side interaction functions

// This is a utility function that converts a GLatLng array to a series of coords in the form of x,y,x,y,....
function getXYFromVertices(gPoints_in)
	{
				
	var coords = [];
				
	try
		{
		
		var j = 0;
		
		for(var i = 0; i < gPoints_in.length; i++)
			{
				
			var gPoint = gPoints_in[i];
						
			coords[j] = gPoint.lng();
			coords[j+1] = gPoint.lat();
					
			j = j + 2;			
						
			}
		}
				
	catch (e)
		{
		}
				
	return coords;
	}

//#########################################################################################		
// This is a utility function that converts x,y coords from the database to a GLatLon array

function getVerticesFromXY(coords)
	{
		
	var points = [];
	
	try
		{
			
		var j = 0;	
			
		for (var i = 0; i < coords.length; i = i+2)
			{
			//GLatLng takes lat and then lng as parameters (should be the other way round coming from the DB	
			var vertex = new GLatLng(coords[i+1],coords[i]);
			
			points[j] = vertex;
			j++;
			}
				
		}
			
	catch (e)
		{
		}
				
	return points;
	}

//#########################################################################################
// This is a utility function that converts numeric mode values to strings

function getModeFromInt(num_mode)
	{
		
	var myModeString;	
		
	try
		{
		
		switch (num_mode)
			{
			case 0:
				myModeString = "Car";
				break;
			
			case 1:
				myModeString = "Bus";
				break;
				
			case 2:
				myModeString = "Bike";
				break;
				
			case 3:
				myModeString = "Walk";
				break;
				
			case 4:
				myModeString = "Ferry";
				break;
				
			case 5:
				myModeString = "Train";
				break;
				
			case 6:
				myModeString = "Other";
				break;								
			
			default:
				break;
			}
				
		}
			
	catch (e)
		{
		}
				
	return myModeString;
	}

//#########################################################################################
// This is a utility function that converts numeric frequency values to strings

function getFrequencyFromInt(num_freq)
	{
		
	var myFrequencyString;	
		
	try
		{
		
		switch (num_freq)
			{
			case 0:
				myFrequencyString = "Less than once";
				break;
			
			case 1:
				myFrequencyString = "Once";
				break;
				
			case 2:
				myFrequencyString = "Two to three times";
				break;
				
			case 3:
				myFrequencyString = "Four to five times";
				break;
				
			case 4:
				myFrequencyString = "Six or more times";
				break;
			
			default:
				break;
			}
				
		}
			
	catch (e)
		{
		}
				
	return myFrequencyString;
	}

//#########################################################################################
// This is a utility function that converts mode strings to integers for storage of trips in DB

function getIntFromMode(string_mode)
	{
		
	var myModeInt;	
		
	try
		{
		
		switch (string_mode)
			{
			case "Car":
				myModeInt = 0;
				break;
			
			case "Bus":
				myModeInt = 1;
				break;
				
			case "Bike":
				myModeInt = 2;
				break;
				
			case "Walk":
				myModeInt = 3;
				break;
				
			case "Ferry":
				myModeInt = 4;
				break;
				
			case "Train":
				myModeInt = 5;
				break;
				
			case "Other":
				myModeInt = 6;
				break;								
			
			default:
				break;
			}
				
		}
			
	catch (e)
		{
		}
				
	return myModeInt;
	}

//#########################################################################################
// This is a utility function that converts travel frequency strings to integers

function getIntFromFrequency(string_freq)
	{
		
	var myFreqInt;	
		
	try
		{
		
		switch (string_freq)
			{
			case "Less than once":
				myFreqInt = 0;
				break;
			
			case "Once":
				myFreqInt = 1;
				break;
				
			case "Two to three times":
				myFreqInt = 2;
				break;
				
			case "Four to five times":
				myFreqInt = 3;
				break;
				
			case "Six or more times":
				myFreqInt = 4;
				break;
			
			default:
				break;
			}
				
		}
			
	catch (e)
		{
		}
				
	return myFreqInt;
	}

//#########################################################################################
//#########################################################################################

function zoomTo(marker_type)
	{
	
	if (marker_type == "origin")
		{
		
		if (origin_marker !== null)
			{
			map.setCenter(origin_marker.getPoint(), map.getZoom());	
			global_labelIndex = 0;
			}
			
		else
			{
			alert("Can't zoom to the start address because it has not been specified yet.");
			}	
			
		}
		
	else if (marker_type == "destination")
		{
			
		if (destination_marker !== null)
			{
			map.setCenter(destination_marker.getPoint(), map.getZoom());
			global_labelIndex = 0;	
			}
			
		else
			{
			alert("Can't zoom to the end address because it has not been specified yet.");
			}
		}			
	
	}

//#########################################################################################
//#########################################################################################