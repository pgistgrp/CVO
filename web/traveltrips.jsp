<%@ taglib prefix="gmap" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  xmlns:v="urn:schemas-microsoft-com:vml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    
    <title>Individual Travel Path</title>
    
		<!-- Site Wide CSS -->
		<style type="text/css" media="screen">@import "styles/lit.css";</style>
		<!-- End Site Wide CSS -->
		<!-- Site Wide JavaScript -->
		<script language="JavaScript" src="scripts/qtip.js" type="text/JavaScript"></script>
		<script src="scripts/tags.js" type="text/javascript"></script>
		<script src="scripts/prototype.js" type="text/javascript"></script>
		<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
		<script src="scripts/globalSnippits.js" type="text/javascript"></script>
		<!-- End Site Wide JavaScript -->
		
		<!-- DWR JavaScript Libraries -->
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
		<!-- End DWR JavaScript Libraries -->
		
		<!--SDX Specific  Libraries-->
		<script type='text/javascript' src='/dwr/interface/SDAgent.js'></script>
		<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>

    <!-- following line contains the google maps key for http://localhost:8080/test -->
	<gmap:gmapjs />
    <script language="javascript" type="text/javascript" src="scripts/pdmarker.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/travelpath.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/firebug/firebug.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/json.js"></script>
    <script type='text/javascript' src='/dwr/interface/RegisterAgent.js'></script>
	<script type='text/javascript' src='/dwr/engine.js'></script>
   
    <link rel="stylesheet" href="/styles/travelpath.css" type="text/css" />
        
    <!-- for dhtml window with veil -->
    <link rel="stylesheet" href="windowfiles/dhtmlwindow.css" type="text/css" />

		<script type="text/javascript" src="windowfiles/dhtmlwindow.js">

		/***********************************************
		* DHTML Window Widget- © Dynamic Drive (www.dynamicdrive.com)
		* This notice must stay intact for legal use.
		* Visit http://www.dynamicdrive.com/ for full source code
		***********************************************/

		</script>

		<!--<link rel="stylesheet" href="modalfiles/modal.css" type="text/css" />
		<script type="text/javascript" src="modalfiles/modal.js"></script>-->
		<!-- for dhtml window with veil -->
		<script type="text/javascript"">
			/* this is a utility function to convert a GPolyline to a series of coords, in the form on x,y,x,y,.... */
			function getXYFromGPolyline(gpolyline){
				var coords = [];
				try{
					for(var i=0; i<gpolyline.getVertexCount()*2; i=i+2){
						var v = gpolyline.getVertex(i/2);
						coords[i] = v.lng();
						coords[i+1] = v.lat();
					}
				}catch (e){}
				
				return coords;
			}
			
			/* a test function to show the format of the call */
			function testSave(){
				//the coordinates of the polyline
				var coords = getXYFromGPolyline( gdir.getPolyline() );
				
				//a list of markers, you can make your own list
				var markers=[{type:1, index:1, name:"m1", data1:"data 1.1", data2:"data 1.2", lat:46.11, lng:-111.12}, {type:1, index:2, name:"m2", data1:"data 2.1", data2:"data 2.2", lat:46.11, lng:-121.12}];
				
				//a trip object
				var trip = {mode:2, frequency:1, coords:coords};
				
				//the first argument of this call is the user ID, which we can either set from this page, or get from the context
				RegisterAgent.saveUserTrip(-1, [markers], [trip], function(data){
							if(data.successful) alert("path and markers saved successfully, with tripid=" + data.tripIds);
						});
			}
			
			/* a test function to load the trips of a particular user, and display the last trip
			 * of course you can create your own code to display all trips, with certain symbology, and adjust map extent
			 * again, first parameter is the user ID.
			 */
			function testLoad(){
				RegisterAgent.getUserTrips(-1, function(data){
					//data.trips is now an array of trips
					if(data.successful){
						if(data.trips.length>0)
							testDrawTrip(data.trips[data.trips.length-1].coords, data.trips[data.trips.length-1].markers);
						lastTripId = data.trips[data.trips.length-1].id;
						alert("Total number of trips: " + data.trips.length + "; last tripid=" + lastTripId);
					}
					
				});
			}
			
			function testDrawTrip(coords, markers){
				var points= [];
				for(i=0;i<coords.length; i=i+2){
					points[i/2] = new GPoint(coords[i], coords[i+1]);
				}
				map.addOverlay( new GPolyline(points, "#0000FF", 6, 0.6) );
				for(i=0; i<markers.length; i++){
					map.addOverlay( new GMarker(new GLatLng(markers[i].lat, markers[i].lng)) );
				}
			}
			
			var lastTripId;
			function testDelete(){
				if(lastTripId){
					RegisterAgent.removeTravelTrip(lastTripId, function(){
						if(data.successful){
							testLoad();
						}else{
							alert(data.reason);
						}
					});
				}
			}
		</script>   
	</head>

	<body onload="load()" onunload="GUnload()">
    <!-- Start Global Headers  -->
    <wf:nav />
    <wf:subNav />
    <!-- End Global Headers -->
  <div id="container">
	  
<!--	  <h2>Individual Travel Path App Demo</h2>
	  
	  <br/>-->
	  
	  <table class="grandTable" cellpadding="0" cellspacing="0" border="1" style="background-color:#F5F5F5;"> <!-- start of the table that enclosed the trip creator controls and the map (two columns) -->
	    <tr> <!-- total width of the two columns of the grandtable is restricted to 950px -->
	    	<td align="center" width="260px" height="430px">
					<table cellpadding="0" cellspacing="0" border="0"> <!-- start of the input fields table; has one column -->
	   				<tr>
  						<th id="fromAddressLabel" align="left">
  							<label class="std_labels">Starting Location</label>
  						</th>
  					</tr>
  					<tr>
    					<td align="left">
    						<label class="small_labels">(Street Adress, City, and State or Intersection)</label>
  							<br/>
  							<input type="text" class="fields" id="fromAddressField" name="from" value="4620 4th Street, La Mesa, CA"/>
    					</td>
  					</tr>
  					<tr>
  						<td align="left">
    						<input type="button" class="menu_btns" value="Add" id="originAddButton" onmouseover="this.className='btnhov'" onmouseout="this.className='menu_btns'" onClick="geocodeAddress(document.getElementById('fromAddressField').value, 'origin');" />
    						<input type="button" class="menu_btns" value="Clear" id="originClearButton" onmouseover="this.className='btnhov'" onmouseout="this.className='menu_btns'" onClick="clearStartStop('origin');" />
    						<input type="button" class="menu_btns" value="Relabel" id="originEditButton" onmouseover="this.className='btnhov'" onmouseout="this.className='menu_btns'" onClick="assignNewLabel('origin');" />
    						<input type="button" class="menu_btns" id="originZoomButton" value="Zoom To" onmouseover="this.className='btnhov'" onmouseout="this.className='menu_btns'" onClick="map.setCenter(origin_marker.getPoint(), map.getZoom());" />
    					</td>
    				</tr>
    				<tr>
    					<td align="center">
    						<hr class="hrline"/>
    					</td>
						</tr>	
  					<tr>	
    					<th id="toAdressLabel" align="left">
    						<label class="std_labels">Ending Location</label>
    					</th>
  					</tr>
  					<tr>
    					<td align="left">
    						<label class="small_labels">(Street Adress, City, and State or Intersection)</label>
  							<br/>
    						<input type="text" class="fields" id="toAddressField" name="to" value="San Diego State University, San Diego, CA" />
    					</td>
    				</tr>
    				<tr>
  						<td align="left">
    						<input type="button" class="menu_btns" id="destinationAddButton" value="Add" onmouseover="this.className='btnhov'" onmouseout="this.className='menu_btns'" onClick="geocodeAddress(document.getElementById('toAddressField').value, 'destination')" />
    						<input type="button" class="menu_btns" id="destinationClearButton" value="Clear" onmouseover="this.className='btnhov'" onmouseout="this.className='menu_btns'" onClick="clearStartStop('destination');" />
    						<input type="button" class="menu_btns" id="destinationEditButton" value="Relabel" onmouseover="this.className='btnhov'" onmouseout="this.className='menu_btns'" onClick="assignNewLabel('destination');" />
    						<input type="button" class="menu_btns" id="destinationZoomButton" value="Zoom To" onmouseover="this.className='btnhov'" onmouseout="this.className='menu_btns'" onClick="map.setCenter(destination_marker.getPoint(), map.getZoom());" />
    					</td>
  					</tr>
  					<tr>
    					<td align="center">
    						<hr class="hrline"/>
    					</td>
						</tr>
		    		<tr>
		    			<th id="viaAddressLabel" align="left">
		    				<label class="std_labels">Waypoints</label>
    					</th>
  					</tr>
  					<tr>
    					<td align="left">
    						<label class="small_labels">(Street Adress, City, and State or Intersection)</label>
  							<br/>
    						<input type="text" class="fields" id="viaAddressField" name="via" value="College Ave and El Cajon, San Diego, CA" />
    					</td>
    				</tr>
  					<tr>
    					<td align="left">
								<div id="waypointListDiv">
									<select class="lists" onChange="waypointSelectionChanged();" id="waypointList" size="4" />
  									<!-- placeholder -->
									</select>  
								</div>  								
    					</td>
    					<td>
    						<table cellpadding="0" cellspacing="0" border="1" style="background-color:#F5F5F5;">
    							<tr>
    								<td>
    									<input type="button" class="updown_btns" name="upArrow" value="Up" onmouseover="this.className='btnhov_udb'" onmouseout="this.className='updown_btns'" onClick="moveWaypointUp()" />
    								<td>
    							</tr>
    							<tr>
    								<td>
    									<input type="button" class="updown_btns" name="downArrow" value="Down" onmouseover="this.className='btnhov_udb'" onmouseout="this.className='updown_btns'" onClick="moveWaypointDown()" />
    								</td>
    							</tr>		    									
    						</table>		
							</td>	
    				</tr>	
    				<tr>
    					<td align="left">
     						<input type="button" class="menu_btns" id="waypointAddButton" value="Add" onmouseover="this.className='btnhov'" onmouseout="this.className='menu_btns'" onClick="geocodeAddress(document.getElementById('viaAddressField').value, 'waypoint')" />
    						<input type="button" class="menu_btns" id="waypointClearButton" value="Delete" onmouseover="this.className='btnhov'" onmouseout="this.className='menu_btns'" onClick="deleteSelectedWaypoint();" />
    						<input type="button" class="menu_btns" id="waypointEditButton" value="Relabel" onmouseover="this.className='btnhov'" onmouseout="this.className='menu_btns'" onClick="labelSelectedWaypoint();" />
    					</td>
						</tr>
						<tr>
    					<td align="center">
    						<hr class="hrline"/>
    					</td>
						</tr>   
    				<tr>
    					<td align="center">
								<input type="button" class="show_btn" id="calcDirectionsButton" value="SHOW TRIP" onmouseover="this.className='btnhov_sb'" onmouseout="this.className='show_btn'" onClick="setDirections(document.getElementById('fromAddressField').value, document.getElementById('toAddressField').value); return false" />&nbsp;<input type="button" class="show_btn" id="clearAllControlsButton" value="CLEAR ALL" onmouseover="this.className='btnhov_sb'" onmouseout="this.className='show_btn'" onClick="clearAllControls(true);" />
    					</td>
    				</tr>
    				<tr>
    					<td align="center">
    						<input type="button" class="fill_btn" id="saveTripButton" value="&darr;&nbsp;ADD TO COLLECTION&nbsp;&darr;" onmouseover="this.className='btnhov_fbtn'" onmouseout="this.className='fill_btn'" onClick="storeTripFreqMode();" />
    					</td>
    				</tr>
					</table> <!-- end of the left-hand table with the trip creator controls-->
				</td>
	    	<td width="620px" height="430px"> <!-- total width of the two columns of the grandtable is restricted to 950px -->
	    		<div id="map" style="width: 100%; height: 100%;"></div>
	    	</td>
	  	</tr>
	  </table> <!-- end of the table for the enclosing table -->
	  
	  <table cellpadding="0" cellspacing="0" border="1" style="background-color:#F5F5F5;"> <!-- start of the table that encloses the trip collection and the help me frame -->
	    <tr> <!-- total width of the this table should be the same as above -->
	    	<td align="center" width="260px">
	  			<table cellpadding="0" cellspacing="0" border="0"> <!-- table for placing stuff underneath the enclosing table -->
	  				<tr>		
		    			<th align="left">
		    				<label class="std_labels">Trip Collection</label>
    					</th>
  					</tr>
    				<tr>
    					<td align="left">	
	  						<div id="triplistDiv">
									<select class="tripList" onChange="tripSelectionChanged();" id="triplist" size="4" />
    								<!-- placeholder -->
									</select>  
								</div>
								<input type="button" class="midsize_btns" value="Delete Trip" id="tripDeleteButton" onmouseover="this.className='btnhov_msb'" onmouseout="this.className='midsize_btns'" onClick="deleteSelectedTrip();" />&nbsp;<input type="button" class="huge_btns2" value="Save collection and EXIT" id="tripDeleteButton" onmouseover="this.className='btnhov_hb2'" onmouseout="this.className='huge_btns2'" onClick="saveTripsAndExit();" /> <input type="button" value="test save" onclick="testSave()"/><input type="button" value="test load" onclick="testLoad()"/><input type="button" value="test delete" onclick="testDelete()"/>
							</td>
						</tr>
					</table>	
	  		</td>
				<td width="620px">
	    		<div id="helpMeDiv" class="helpme">
						This text should be underneath the map. It will contain some short textual guidance/help me information to the user and will be triggered by user-interface interaction events.
	    		</div>	
	    	</td>
			</tr>
		</table> <!-- end of the table for placing stuff underneath the enclosing table -->

		<!--<div id="placemarkWindowDiv" style="background: #F3F3F3; height: 100%; padding: 5px; display: none;">-->
		<div id="placemarkWindowDiv" style="display: none;">
			<!-- placeholder -->
		</div>
		
		<div id='labelLocationDiv' style="display: none;">
			<label class="hd_labels">Please choose a pre-defined name for the new location or enter your own:</label>
			<p>
				<form>
					<table cellpadding='0' cellspacing='0'>
						<tr>
							<td>
								<input type='radio' name='labelChoice' checked id='0' value='Home' onClick='return radioLabelClick(event)'/>
							</td>
							<td>
								<label class="radio_btn_labels">Home</label>
							</td>
    				</tr>
						<tr>
							<td>
								<input type='radio' name='labelChoice' id='1' value='Work' onClick='return radioLabelClick(event)'/>
							</td>
							<td>
								<label class="radio_btn_labels">Work</label>
							</td>
						</tr>		
						<tr>
							<td>
								<input type='radio' name='labelChoice' id='2' value='School' onClick='return radioLabelClick(event)'/>
							</td>
							<td>
								<label class="radio_btn_labels">School</label>
							</td>	
						</tr>
						<tr>
							<td>		
    						<input type='radio' name='labelChoice' id='3' value='Shopping' onClick='return radioLabelClick(event)'/>
    					</td>
    					<td>
    						<label class="radio_btn_labels">Shopping</label>
    					</td>
    				</tr>
    				<tr>
    					<td>
    						<input type='radio' name='labelChoice' id='4' value='Gym' onClick='return radioLabelClick(event)'/>
    					</td>
    					<td>	
    						<label class="radio_btn_labels">Gym</label>
							</td>
						</tr>
						<tr>
							<td>
								<input type='radio' name='labelChoice' id='5' value='Own' onClick='return radioLabelClick(event)'>
							</td>
							<td>
								<input type='text' class="dhtml_fields" id='ownLabel' name='ownLabel' onClick='return clearOwnLabelField(event)' onBlur='return storeOwnLabelInput(event)' value='Enter your own label...'/>
							</td>
						</tr>
					</table>	
				</form>
			</p>
			<br/>
			&nbsp;&nbsp;<input type='button' value='Got it!' class="menu_btns" onmouseover="this.className='btnhov'" onmouseout="this.className='menu_btns'" onClick='closeLabelWindow()' />
		</div>
		
		<div id='tripFreqModeDiv' style="display: none;">
			<label class="hd_labels">Which type of transportation do you primarily use on this trip?</label>
			<p>
				<form>
					<table cellpadding='0' cellspacing='0'>
						<tr>
							<td>
								<input type='radio' name='modeChoice' checked id='car' value='Car' onClick='return radioTripModeClick(event)'/>
							</td>
							<td>
								<label class="radio_btn_labels">I travel by car.</label>
							</td>
    				</tr>
						<tr>
							<td>
								<input type='radio' name='modeChoice' id='bus' value='Bus' onClick='return radioTripModeClick(event)'/>
							</td>
							<td>
								<label class="radio_btn_labels">I take the bus.</label>
							</td>
						</tr>		
						<tr>
							<td>
								<input type='radio' name='modeChoice' id='bicycle' value='Bike' onClick='return radioTripModeClick(event)'/>
							</td>
							<td>
								<label class="radio_btn_labels">I ride my bicycle.</label>
							</td>	
						</tr>
						<tr>
							<td>		
    						<input type='radio' name='modeChoice' id='walk' value='Walk' onClick='return radioTripModeClick(event)'/>
    					</td>
    					<td>
    						<label class="radio_btn_labels">I walk.</label>
    					</td>
    				</tr>
    				<tr>
							<td>		
    						<input type='radio' name='modeChoice' id='ferry' value='Ferry' onClick='return radioTripModeClick(event)'/>
    					</td>
    					<td>
    						<label class="radio_btn_labels">I take the ferry.</label>
    					</td>
    				</tr>
    				<tr>
							<td>		
    						<input type='radio' name='modeChoice' id='train' value='Train' onClick='return radioTripModeClick(event)'/>
    					</td>
    					<td>
    						<label class="radio_btn_labels">I take the train.</label>
    					</td>
    				</tr>
    				<tr>
							<td>		
    						<input type='radio' name='modeChoice' id='other' value='Other' onClick='return radioTripModeClick(event)'/>
    					</td>
    					<td>
    						<label class="radio_btn_labels">I use other transportation.</label>
    					</td>
    				</tr>
    			</table>	
				</form>
			</p>
			<br/>
			<label class="hd_labels">How often do you take this trip per week?</label>
			<p>
				<form>
					<table cellpadding='0' cellspacing='0'>
						<tr>
							<td>
								<input type='radio' name='freqChoice' checked id='lessOnce' value='Less than once' onClick='return radioTripFrequencyClick(event)'/>
							</td>
							<td>
								<label class="radio_btn_labels">Less than once</label>
							</td>
    				</tr>
						<tr>
							<td>
								<input type='radio' name='freqChoice' id='once' value='Once' onClick='return radioTripFrequencyClick(event)'/>
							</td>
							<td>
								<label class="radio_btn_labels">Once</label>
							</td>
    				</tr>
						<tr>
							<td>
								<input type='radio' name='freqChoice' id='twiceThrice' value='Twice to thrice' onClick='return radioTripFrequencyClick(event)'/>
							</td>
							<td>
								<label class="radio_btn_labels">Twice to three times</label>
							</td>
						</tr>		
						<tr>
							<td>
								<input type='radio' name='freqChoice' id='fourFive' value='Four to five times' onClick='return radioTripFrequencyClick(event)'/>
							</td>
							<td>
								<label class="radio_btn_labels">Four to five times</label>
							</td>	
						</tr>
						<tr>
							<td>		
    						<input type='radio' name='freqChoice' id='fivePlus' value='Six or more times' onClick='return radioTripFrequencyClick(event)'/>
    					</td>
    					<td>
    						<label class="radio_btn_labels">Six or more times</label>
    					</td>
    				</tr>
    			</table>	
				</form>
			</p>
			<br/>
			&nbsp;&nbsp;<input type='button' value='Save it!' class="menu_btns" onmouseover="this.className='btnhov'" onmouseout="this.className='menu_btns'" onClick='closeTripFreqModeWindow()' />
		</div>
</div>
<!-- END CONTAINER-->
	</body>
	
</html>
