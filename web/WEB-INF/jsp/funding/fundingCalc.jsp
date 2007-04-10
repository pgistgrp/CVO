<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<!--####
	Project: Let's Improve Transportation!
	Page: Funding Calc
	Description: Tax Calculator Game - Users will be able to calculate how much funding sources will cost to them.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman, Issac Yang
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] Add userAgent for vehicles
		[ ] Ensure get and set estimates are working correctly and pulling the right data (Jordan)
		[ ] Barebones JavaScript (Issac)
		[x] Integrate Layout (Adam)
	Notes:
		- The action on this page will give: CCT, User, Tolls, UserCommute Objects
#### -->

<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
	<head>
	<title>Tax Calculator</title>
	<!-- Site Wide CSS -->
	<style type="text/css" media="screen">
		@import "styles/lit.css";
		@import "styles/table.css";
		@import "styles/taxCalculator.css";
	</style>
	<!-- End Site Wide CSS -->
	<!-- Site Wide JS -->
	<script src="scripts/prototype.js" type="text/javascript"></script>

	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<script src="scripts/search.js" type="text/javascript"></script>
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/util.js'></script>
	<script type='text/javascript' src='/dwr/interface/FundingAgent.js'></script>
	<script type="text/javascript" charset="utf-8">
		var suiteId = "${suiteId}"
		

		function getUserById(userId){
			FundingAgent.getUserById({userId:userId}, {
				callback:function(data){
					if (data.successful){
						calcCommute(data.user);
					}else{
						return data.reason;
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("FundingAgent.getUserById( error:" + errorString + exception);
				}
			});
		}
		
		function calcCommute(user){
			alert("user object: " + user)
			FundingAgent.calcCommute({user:user}, {
				callback:function(data){
					if (data.successful){
						alert("data:" + data);
						alert("html: " + data.html);
					}else{
						alert("reason: " + data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("FundingAgent.setEstimates( error:" + errorString + exception);
				}
			});
		}
	</script>
	</head>
	<body>
	<!-- #container is the container that wraps around all the main page content -->

	<div id="container">
		<h3 class="headerColor">Calculating the annual cost to you</h3>
		<p>Feel free to change the information you previously provided in the text boxes
			below</p>
		<!-- Begin calculator options -->
		<div id="myIncome">
			<h3 class="headerColor">My income</h3>
			<span id="annualIncome"> Annual Income
			<input name="annual-income" type="text" value="${user.income}"></span>
			<span id="householdSize"> Household size
			<input name="household-size" type="text" value="${user.familyCount}"></span>
			</div>
		<div id="myVehicles">
			<h3 class="headerColor">My Vehicle(s)</h3>
			<!-- put back vehicle loop here -->
			<p><a href="javascript:Element.toggle('newVehicle');">Add vehicle</a> 
				<div id="newVehicle" class="myVehiclesRow" style="display:none;"> 
					
					<strong>New Vehicle: </strong> Miles per gallon
					<input name="mpg" type="text" >
					Approximate value
					<input name="value" type="text">
					Miles driven per year
					<input name="mpy" type="text">

					<input type="submit" value="Submit" /><small><a href="javascript:Element.toggle('newVehicle');">Cancel</a></small></div>
				
				
		</div>
		<div id="myCommute">
			<h3 class="headerColor">My Commute</h3>
			<div id="myCommute-left" class="floatLeft">
			<p>Home zip code <input name="hZipcode" type="text" size="5" maxlength="5"></span></p>
			<p>Work zip code <input name="wZipcode" type="text" size="5" maxlength="5"></span></p>
			</div>
			<div id="myCommute-center" class="floatLeft"> 
				I <strong>drive alone</strong>
				<select name="drive-alone">
					<c:forEach var="i" begin="1" end="8">
						<option value="${i-1}">${i-1}</option>
					</c:forEach>
				</select>
				days to work each week<br/>
				
				I <strong>carpool</strong> to work
				<select name="carpool">
					<c:forEach var="i" begin="1" end="8">
						<option value="${i-1}">${i-1}</option>
					</c:forEach>
				</select>
				days each week with
				<select name="carpool-with">
					<c:forEach var="i" begin="1" end="8">
						<option value="${i-1}">${i-1}</option>
					</c:forEach>
				</select>
				people<br/>

				I <strong>ride the bus</strong> to work
				<select name="bus">
					<c:forEach var="i" begin="1" end="8">
						<option value="${i-1}">${i-1}</option>
					</c:forEach>
				</select>
				days each week<br/>
				
				I <strong>walk</strong> to work
				<select name="walk">
					<c:forEach var="i" begin="1" end="8">
						<option value="${i-1}">${i-1}</option>
					</c:forEach>
				</select>
				days each week<br/>
				
				I <strong>bike</strong>
				<select name="bike">
					<c:forEach var="i" begin="1" end="8">
						<option value="${i-1}">${i-1}</option>
					</c:forEach>
				</select>
				days to work each week<br/>
			</div>
			<div id="myCommute-right" class="floatLeft"> My daily commute includes:<br />
				<input name="myCommute-check1" type="checkbox">
				Parking downtown<br />
				<input name="myCommute-check2" type="checkbox">
				Alaskan Way viaduct<br />
				<input name="myCommute-check3" type="checkbox">
				I-405 North<br />
				<input name="myCommute-check4" type="checkbox">

				I-405 South<br />
				<input name="myCommute-check5" type="checkbox">
				SR 520 Floating Bridge<br />
				<input name="myCommute-check6" type="checkbox">
				I-90<br />
				<input name="myCommute-check7" type="checkbox">
				SR 167<br />
			</div>
			<div class="clearboth">
				<input type="button" name="calcEstimates" value="Calculate my estimates" 
					style="clear:both;margin:1em;" class="floatRight" onClick="getUserById(${user.userId});">
			</div>
			<div class="clearBoth"></div>
		</div>

		<div id="estimates">
			<h3 class="headerColor peekaboobugfix">Estimated use of toll roads, taxed parking
				facilities, and annual taxable consumption</h3>
			<p class="peekaboobugfix">Your estimates number of tolls has been estimated based
				on your home zip code, your usual commute mode of travel, and your commute route.
				You may change these estimates.</p>
		<div id="estimates-left">
			<table border="0" cellpadding="1" cellspacing="0" id="tollRoads">
				<tr>
					<th>&nbsp;</th>
					<th>Peak Hour Trips</th>
					<th>Off-peak trips</th>
				</tr>
				<tr>
					<td class="fundingSourceItem">Parking downtown</td>
					<td><input size="3" maxlength="3" type="text" ></td>
					<td><input size="3" maxlength="3" type="text" ></td>
				</tr>
				<tr>
					<td class="fundingSourceItem">Alaskan Way Viaduct</td>
					<td><input size="3" maxlength="3" type="text" ></td>
					<td><input size="3" maxlength="3" type="text" ></td>
				</tr>
				<tr>
					<td class="fundingSourceItem">I-405 North</td>
					<td><input size="3" maxlength="3" type="text" ></td>
					<td><input size="3" maxlength="3" type="text" ></td>
				</tr>
				<tr>
					<td class="fundingSourceItem">I-405 South</td>
					<td><input size="3" maxlength="3" type="text" ></td>
					<td><input size="3" maxlength="3" type="text" ></td>
				</tr>
				<tr>
					<td class="fundingSourceItem">SR 520 Floating Bridge</td>
					<td><input size="3" maxlength="3" type="text" ></td>
					<td><input size="3" maxlength="3" type="text" ></td>
				</tr>
				<tr>
					<td class="fundingSourceItem">I-90</td>
					<td><input size="3" maxlength="3" type="text" ></td>
					<td><input size="3" maxlength="3" type="text" ></td>
				</tr>
				<tr>
					<td class="fundingSourceItem">SR 167</td>
					<td><input size="3" maxlength="3" type="text" ></td>
					<td><input size="3" maxlength="3" type="text" ></td>
				</tr>
				<tr>
			</table>
		</div>
		<div id="estimates-right">
			<table>
				<tr>
					<td>Gas cost per gallon</td>
					<td><input name="" size="5" type="text"></td>
				</tr>
				<tr>
					<td>Annual consumption (sales tax)</td>
					<td><input name="" size="5" type="text"></td>
				</tr>
			</table>
		</div>
		
			<div class="clearboth">

				<input type="button" name="calcEstimates" value="Update Annual Cost Report" 
					style="clear:both;margin:1em;" class="floatRight">
			</div>
			<div class="clearBoth"></div>
		</div>
		<!-- End calculator options -->
		<!-- Note on zebra-striping by funding source: When creating rows of funding items (such as each alternative gas tax increase) wrap all rows in a TBODY tag, then do zebra-striping on those TBODYs, not on the individual TRs. --><br>
		<h3 class="headerColor">My annual costs report</h4>
		<div id="newTable">
		<table cellpadding=0 cellspacing=0>
			<tr class="tableHeading">
				<th class="first">Funding Source</th>
				<th>Estimated annual cost to you</th>

				<th>&nbsp;</th>
				<th>&nbsp;</th>
				<th colspan="7">Calculation</th>
			</tr>
			<tr class="fundingType">
				<td class="fundingSourceItem">Toll on Alaskan Way Viaduct</td>
				<td>Cost to you</td>

				<td>=</td>
				<td>&nbsp;</td>
				<td>Peak tax rate</td>
				<td>&times;</td>
				<td># of Peak trips</td>
				<td>+</td>
				<td>off-peak tax rate</td>

				<td>&times;</td>
				<td># off-peak trips</td>
			</tr>
			<tr>
				<td class="fundingSourceItem">Fixed rate $1.00 per trip</td>
				<td>$25</td>
				<td>=</td>

				<td>&nbsp;</td>
				<td>$1.00</td>
				<td>&times;</td>
				<td>5</td>
				<td>+</td>
				<td>$1.00</td>
				<td>&times;</td>

				<td>20</td>
			</tr>
			<tr>
				<td class="fundingSourceItem">Fixed rate $1.00 per trip</td>
				<td>$25</td>
				<td>=</td>
				<td>&nbsp;</td>

				<td>$1.00</td>
				<td>&times;</td>
				<td>5</td>
				<td>+</td>
				<td>$1.00</td>
				<td>&times;</td>
				<td>20</td>

			</tr>
			<tr class="fundingType">
				<td class="fundingSourceItem">Toll on Alaskan Way Viaduct</td>
				<td>Cost to you</td>
				<td>=</td>
				<td>&nbsp;</td>
				<td>Peak tax rate</td>

				<td>&times;</td>
				<td># of Peak trips</td>
				<td>+</td>
				<td>off-peak tax rate</td>
				<td>&times;</td>
				<td># off-peak trips</td>
			</tr>

			<tr>
				<td class="fundingSourceItem">Fixed rate $1.00 per trip</td>
				<td>$25</td>
				<td>=</td>
				<td>&nbsp;</td>
				<td>$1.00</td>
				<td>&times;</td>

				<td>5</td>
				<td>+</td>
				<td>$1.00</td>
				<td>&times;</td>
				<td>20</td>
			</tr>
			<tr>

				<td class="fundingSourceItem">Fixed rate $1.00 per trip</td>
				<td>$25</td>
				<td>=</td>
				<td>&nbsp;</td>
				<td>$1.00</td>
				<td>&times;</td>
				<td>5</td>

				<td>+</td>
				<td>$1.00</td>
				<td>&times;</td>
				<td>20</td>
			</tr>

			<tr class="fundingType">
				<td class="fundingSourceItem">Sales tax increase (Rate now 8.8%)</td>

				<td>Cost to you</td>
				<td>=</td>
				<td>&nbsp;</td>
				<td>tax rate</td>
				<td>&times;</td>
				<td>consumption</td>
				<td>&nbsp;</td>

				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="fundingSourceItem">0.1% increase</td>
				<td>$16</td>
				<td>=</td>

				<td>&nbsp;</td>
				<td>0.1</td>
				<td>&times;</td>
				<td>$15,879</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>

			</tr>
			<tr>
				<td class="fundingSourceItem">0.3% increase</td>
				<td>$48</td>
				<td>=</td>
				<td>&nbsp;</td>
				<td>0.3</td>

				<td>&times;</td>
				<td>$15,879</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>

				<td class="fundingSourceItem">0.5% increase</td>
				<td>$79</td>
				<td>=</td>
				<td>&nbsp;</td>
				<td>0.5</td>
				<td>&times;</td>
				<td>$15,879</td>

				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		</div>
	</div>

	<!-- end container -->
	<!-- start feedback form -->
	<pg:feedback id="feedbackDiv" action="cctView.do"/>
	<!-- end feedback form -->
	</body>
</html:html>


