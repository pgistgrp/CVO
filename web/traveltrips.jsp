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
    
    <!-- START Personal Travel App Specific Libraries -->
		<gmap:gmapjs />
    <script language="javascript" type="text/javascript" src="scripts/pdmarker.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/travelpath.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/firebug/firebug.js"></script>
    <script type='text/javascript' src='/dwr/interface/RegisterAgent.js'></script>
		<script type='text/javascript' src='/dwr/engine.js'></script>
   
    <link rel="stylesheet" href="styles/travelpath.css" type="text/css" />
        
    <link rel="stylesheet" href="windowfiles/dhtmlwindow.css" type="text/css" />
		<script type="text/javascript" src="windowfiles/dhtmlwindow.js">

		/***********************************************
		* DHTML Window Widget- © Dynamic Drive (www.dynamicdrive.com)
		* This notice must stay intact for legal use.
		* Visit http://www.dynamicdrive.com/ for full source code
		***********************************************/
		
		/* END Personal Travel App Specific Libraries */

		</script>
    
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

		<script type="text/javascript">
			
			window.onload = function()
				{
				init();
				}
						
			function getUserId()
				{
				
				try 
					{
					var theUserId=${baseuser.id};
					console.log("Got the user id " + theUserId + " from baseuser.");
					return theUserId;
					}
				
				catch(e)
					{
					return -99;	
					}
				}
		</script>
		   
	</head>

	<body onunload="GUnload()">
    <!-- Start Global Headers  -->
    <wf:nav />
    <wf:subNav />
    <!-- End Global Headers -->
  
  <div id="container">
	<!-- START OF AREA FOR THE PERSONAL TRAVEL APP HTML CODE -->	  
	  
		<h2 class="heading">Map your daily travel</h2>
	  
	  <table cellpadding="0" cellspacing="0" border="0">
	  	<tr>
	  		<td width="862px">
	  			<div class="helpme" id="helpMeDiv">
	  				<label><b>Welcome to Map your daily travel!</b><br/>&nbsp;<br/>Here you can easily input and visualize your daily trips.<br/>A good way to start your trip creation would be to enter the address of the origin in the address field in Step 1.<br>Subsequently, you can proceed to Step 2 and 3 and input the destination and, respectively, specific waypoints. 
	  			</div>
	  		<td>
	  	<tr>
	  </table>
	  
	  <br/>
	  	  	  
	  <table class="grandTable" cellpadding="0" cellspacing="0" border="0" style="background-color:white;"> <!-- START of the table that encloses the trip creator controls and the map (two columns) -->
	   	<tr>  	
	    	<td valign="bottom" align="left">
					<table class="inputTable" cellpadding="0" cellspacing="0" border="0" style="width: 330px"> <!-- START of the left-hand table with all elements (including 'Map a trip'-->
	   				<tr>
							<td>
								<label class="heading2">Map a trip</label>
							</td>
	   				</tr>	
	   				<tr>
	   					<td>
	   						<table cellpadding="0" cellspacing="0" style="border:2px solid;border-color:#3366FF;background-color:#F5F5F5;width:330px;padding-left:3px;padding-bottom:3px;"> <!-- START of the left-hand table with the trip creator controls -->
	   							<tr>	
  									<th id="fromAddressLabel" align="left">
  										<label class="number_labels">1&nbsp;&nbsp;</label><label class="std_labels">Start&nbsp;-&nbsp;</label><label class="description_labels">Wherever you start your trip</label>
  									</th>
  								</tr>
  								<tr>
    								<td align="left">
  										<input type="text" class="fieldsInitial" id="fromAddressField" onClick='return helpInstruction(event)' name="from" value="Street, City, State, Placename or Intersection"/>&nbsp;<input type="button" class="menu_btns" value="Add" id="originAddButton" onmouseover="this.className='btnhov'" onmouseout="this.className='menu_btns'" onClick="geocodeAddress(document.getElementById('fromAddressField').value, 'origin');" />
    								</td>
  								</tr>
  								<tr>
  									<td align="left">
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
    									<label class="number_labels">2&nbsp;&nbsp;</label><label class="std_labels">End&nbsp;-&nbsp;</label><label class="description_labels">Wherever you end your trip</label>
    								</th>
  								</tr>
  								<tr>
    								<td align="left">
    									<input type="text" class="fieldsInitial" id="toAddressField" onClick='return helpInstruction(event)' name="to" value="Street, City, State, Placename or Intersection" />&nbsp;<input type="button" class="menu_btns" id="destinationAddButton" value="Add" onmouseover="this.className='btnhov'" onmouseout="this.className='menu_btns'" onClick="geocodeAddress(document.getElementById('toAddressField').value, 'destination')" />
    								</td>
    							</tr>
    							<tr>
  									<td align="left">
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
		    							<label class="number_labels">3&nbsp;&nbsp;</label><label class="std_labels">Waypoints&nbsp;-&nbsp;</label><label class="description_labels">Any stops along the way?</label>
    								</th>
  								</tr>
  								<tr>
    								<td align="left">
    									<input type="text" class="fieldsInitial" id="viaAddressField" onClick='return helpInstruction(event)' name="via" value="Street, City, State, Placename or Intersection" />&nbsp;<input type="button" class="menu_btns" id="waypointAddButton" value="Add" onmouseover="this.className='btnhov'" onmouseout="this.className='menu_btns'" onClick="geocodeAddress(document.getElementById('viaAddressField').value, 'waypoint')" />
    								</td>
    							</tr>
    							<tr height="5px">
    								<td height="5px" width="5px">
     								</td>
    							</tr>
  								<tr>
    								<td align="left">
    									<table cellpadding="0" cellspacing="0" border="0" style="background-color:#F5F5F5;">
    										<tr>
    											<td>
														<div id="waypointListDiv">
															<select class="lists" id="waypointList" size="4" />
  															<!-- placeholder -->
															</select>  
														</div>  								
 													</td>
 													<td>
 														&nbsp;
 													</td>   						
    											<td align="center">
    												<table cellpadding="0" cellspacing="0" border="0" style="background-color:#F5F5F5;">
    													<tr>
    														<td>
    															<!--<input type="button" class="updown_btns" name="upArrow" value="Up" onmouseover="this.className='btnhov_udb'" onmouseout="this.className='updown_btns'" onClick="moveWaypointUp()" />-->
    															<input type="image" width="30px" height="24px" src="images/black_up.gif" name="upArrow" value="Up" onClick="moveWaypointUp()" />
    														</td>
    													</tr>
    													<tr>
    														<td>
    															<!--<input type="button" class="updown_btns" name="downArrow" value="Down" onmouseover="this.className='btnhov_udb'" onmouseout="this.className='updown_btns'" onClick="moveWaypointDown()" />-->
    															<input type="image" width="30px" height="24px" src="images/black_down.gif" name="downArrow" value="Down" onClick="moveWaypointDown()" />
    														</td>
    													</tr>	
    												</table>
    											</td>
    										</tr>		 									
    									</table>		
										</td>
    							</tr>	
    							<tr>
    								<td align="left">
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
    								<td>
    									<table cellpadding="0" cellspacing="0" border="0" width="100%">
    										<tr>
    											<td align="left">
    												<input type="button" class="fill_btn" id="saveTripButton" value="Add your Trip&nbsp;&nbsp;&nbsp;&darr;" onmouseover="this.className='btnhov_fbtn'" onmouseout="this.className='fill_btn'" onClick="storeTripFreqMode();" />
    											</td>
    											<td align="right">
														<input type="button" class="fill_btn" id="calcDirectionsButton" value="Map your Trip&nbsp;&nbsp;&nbsp;&rarr;" onmouseover="this.className='btnhov_fbtn'" onmouseout="this.className='fill_btn'" onClick="setDirections(document.getElementById('fromAddressField').value, document.getElementById('toAddressField').value); return false" />&nbsp;
													</td>
												</tr>
											</table>
										</td>		
									</tr>
    						</table> <!-- END of the left-hand table with the trip creator controls -->
    					</td>
    				</tr>
					</table> <!-- END of the left-hand table with all elements (including 'Map a trip'-->
				</td>
				<td width="5px">
					&nbsp;
				</td>
	    	<td align="left" valign="bottom"> <!-- total width of the two columns of the grandtable is restricted to 950px -->
	    		<div id="map" style="width: 525px; height: 400px; border:1px solid;"></div>
	    	</td>
	  	</tr>
	  </table> <!-- END of the table for the enclosing table -->
	 	  
	  <!-- START of the table underneath the map/input controls table -->
	  <table cellpadding="0" cellspacing="5" border="0" style="background-color:white;">
	    <tr> <!-- total width of the this table should be the same as above -->
	    	<td valign="bottom" align="left">
	  			<table cellpadding="0" cellspacing="0" border="0" style="width: 330px"> <!-- table for placing stuff underneath the enclosing table -->
	  				<tr>		
		    			<th align="left">
		    				<label class="heading2">Your Trips</label>
    					</th>
  					</tr>
    				<tr>
    					<td align="left">	
	  						<div id="triplistDiv">
									<select class="tripList" id="triplist" size="4" />
    								<!-- placeholder -->
									</select>  
								</div>
								<!--<input type="button" class="midsize_btns" value="Delete Trip" id="tripDeleteButton" onmouseover="this.className='btnhov_msb'" onmouseout="this.className='midsize_btns'" onClick="deleteSelectedTrip();" />&nbsp;<input type="button" class="huge_btns2" value="Save Trip List!" id="tripDeleteButton" onmouseover="this.className='btnhov_hb2'" onmouseout="this.className='huge_btns2'" onClick="saveTripList();" />-->
								<input type="button" class="fill_btn" value="Delete Trip" id="tripDeleteButton" onmouseover="this.className='btnhov_fbtn'" onmouseout="this.className='fill_btn'" onClick="deleteSelectedTrip();" />&nbsp;<input type="button" class="fill_btn" value="Save my Trips!" id="tripDeleteButton" onmouseover="this.className='btnhov_fbtn'" onmouseout="this.className='fill_btn'" onClick="saveTripList();" />
							</td>
						</tr>
					</table>	
	  		</td>
			</tr>
		</table>  <!-- END of the table underneath the map/input controls table -->

		<!-- START of the placeholder div for the dhtml window that displays the placemark alternatives -->
		<div id="placemarkWindowDiv" style="display: none;background-color:navy;">
			<!-- placeholder -->
		</div>
		<!-- END of the placeholder div for the dhtml window that displays the placemark alternatives -->
		
		<!-- START of the div for the labeling of locations -->
		<div id='labelLocationDiv' style="display: none;">
			<label class="hd_labels">Please choose a pre-defined name for the<br>new location or enter your own:</label>
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
		<!-- END of the div for the labeling of locations -->
		
		<!-- START of the div for the trip frequency and travel mode dhtml window -->
		<div id='tripFreqModeDiv' style="display: none;">
			<label class="hd_labels">Which type of transportation do you primarily use on this trip?</label>
			<p>
				<form>
					<table cellpadding='0' cellspacing='0'>
						<tr>
							<td>
								<input type='radio' name='modeChoice' checked id='car' value='Car' onClick='return radioTripModeClick(event)'/>
							</td>
							<td align="left">
								<label class="radio_btn_labels">I travel by car.</label>
							</td>
    				</tr>
						<tr>
							<td>
								<input type='radio' name='modeChoice' id='bus' value='Bus' onClick='return radioTripModeClick(event)'/>
							</td>
							<td align="left">
								<label class="radio_btn_labels">I take the bus.</label>
							</td>
						</tr>		
						<tr>
							<td>
								<input type='radio' name='modeChoice' id='bicycle' value='Bike' onClick='return radioTripModeClick(event)'/>
							</td>
							<td align="left">
								<label class="radio_btn_labels">I ride my bicycle.</label>
							</td>	
						</tr>
						<tr>
							<td>		
    						<input type='radio' name='modeChoice' id='walk' value='Walk' onClick='return radioTripModeClick(event)'/>
    					</td>
    					<td align="left">
    						<label class="radio_btn_labels">I walk.</label>
    					</td>
    				</tr>
    				<tr>
							<td>		
    						<input type='radio' name='modeChoice' id='ferry' value='Ferry' onClick='return radioTripModeClick(event)'/>
    					</td>
    					<td align="left">
    						<label class="radio_btn_labels">I take the ferry.</label>
    					</td>
    				</tr>
    				<tr>
							<td>		
    						<input type='radio' name='modeChoice' id='train' value='Train' onClick='return radioTripModeClick(event)'/>
    					</td>
    					<td align="left">
    						<label class="radio_btn_labels">I take the train.</label>
    					</td>
    				</tr>
    				<tr>
							<td>		
    						<input type='radio' name='modeChoice' id='other' value='Other' onClick='return radioTripModeClick(event)'/>
    					</td>
    					<td align="left">
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
							<td align="left">
								<label class="radio_btn_labels">Less than once</label>
							</td>
    				</tr>
						<tr>
							<td>
								<input type='radio' name='freqChoice' id='once' value='Once' onClick='return radioTripFrequencyClick(event)'/>
							</td>
							<td align="left">
								<label class="radio_btn_labels">Once</label>
							</td>
    				</tr>
						<tr>
							<td>
								<input type='radio' name='freqChoice' id='twiceThrice' value='Two to three times' onClick='return radioTripFrequencyClick(event)'/>
							</td>
							<td align="left">
								<label class="radio_btn_labels">Two to three times</label>
							</td>
						</tr>		
						<tr>
							<td>
								<input type='radio' name='freqChoice' id='fourFive' value='Four to five times' onClick='return radioTripFrequencyClick(event)'/>
							</td>
							<td align="left">
								<label class="radio_btn_labels">Four to five times</label>
							</td>	
						</tr>
						<tr>
							<td>		
    						<input type='radio' name='freqChoice' id='fivePlus' value='Six or more times' onClick='return radioTripFrequencyClick(event)'/>
    					</td>
    					<td align="left">
    						<label class="radio_btn_labels">Six or more times</label>
    					</td>
    				</tr>
    			</table>	
				</form>
			</p>
			<br/>
			&nbsp;&nbsp;<input type='button' value='Save it!' class="menu_btns" onmouseover="this.className='btnhov'" onmouseout="this.className='menu_btns'" onClick='closeTripFreqModeWindow()' />
		</div>
		<!-- END of the div for the trip frequency and travel mode dhtml window -->
	<!-- END OF AREA FOR THE PERSONAL TRAVEL APP HTML CODE -->		
</div>

</body>
	
</html>
