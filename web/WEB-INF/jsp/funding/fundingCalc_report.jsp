<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!--####
	Project: Let's Improve Transportation!
	Page: Funding Source Report
	Description: This page serves report
	Author: Jordan Isip, Adam Hindman, Issac Yang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] Integrate Layout (Adam)
		[ ] Integrate Project Map or static image (Guirong/Issac)
		[ ] Integrate Criteria Tree (Issac)
#### -->

	<div id="costReport">
		<h4 class="headerColor">My annual costs report</h4>
	<c:forEach var="source" items="${sources}" varStatus="loop">
			
	<!-- begin sales tax increase -->
	<c:if test="${alternative.type == 1}"> 
		<table id="source-${source.id}" border="0" cellpadding="0" cellspacing="0">
		  <tr class="tableHeading">
			 <th scope="col">Funding Source </th>
			 <th scope="col">Annual cost to you </th>
			 <th colspan="7" scope="col">&nbsp;</th>
		  </tr>
		  <tr class="fundingType">
			 <th scope="row" class="fundingSourceItem"><a href="#" title="${source.description}">${source.name}</a></th>
			 <td>Annual cost </td>
			 <td>=</td>
			 <td>Tax Rate </td>
			 <td>&times;</td>
			 <td>Estimated Annual Consumption</td>
		  </tr>

		  <c:forEach var="alternative" items="${source.alternatives}" varStatus="loop">
				<tr class="odd">
				 <th scope="row" class="fundingSourceItem">${alternative.name}</th>
				 <td>[Annual Cost]</td>
				 <td>=</td>
				 <td>${alternative.taxRate}</td>
				 <td>&times;</td>
				 <td>${userCommute.annualConsume}</td>
				</tr>
				<!-- loops for other funding source alt types -->
			</c:forEach>
		</table>
	</c:if>
	<!-- end sales tax increase -->
	
	<!-- Begin annual vehicle license fee -->
		<c:if test="${alternative.type == 2}">
			<table id="source-${source.id}" border="0" cellpadding="0" cellspacing="0">
				<tr class="tableHeading">
					<th scope="col">Funding Source </th>
					<th scope="col">Annual cost to you </th>
					<th colspan="7" scope="col">&nbsp;</th>
				</tr>
				<tr class="fundingType">
					<th width="200" scope="row" class="fundingSourceItem"><a href="#" title="${source.description}">${source.name}</a></th>
					<td>Annual cost </td>
					<td>=</td>
					<td>Tax rate </td>
					<td>&times;</td>
					<td>Number of Vehicles </td>
				</tr>
				<c:forEach var="alternative" items="${source.alternatives}" varStatus="loop">
					<tr>
						<th scope="row" class="fundingSourceItem">${alternative.name}</th>
						<td>[Annual Cost]</td>
						<td>=</td>
						<td>${alternative.taxRate}</td>
						<td>&times;</td>
						<td>${fn:length(user.vehicles)}</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	<!-- End annual vehicle license fee -->

	<!-- Begin annual motor vehicle Excise Tax -->
	<c:if test="${alternative.type == 3}">
		<table id="source-${source.id}" border="0" cellpadding="0" cellspacing="0">
			<tr class="tableHeading">
				<th scope="col">Funding Source </th>
				<th scope="col">Annual cost to you </th>
				<th colspan="7" scope="col">&nbsp;</th>
			</tr>
			<tr class="fundingType">
				<th width="200" scope="row" class="fundingSourceItem"><a href="#" title="${source.description}">${source.name}</a> </th>
				<td>Annual cost </td>
				<td>=</td>
				<td>&nbsp;</td>
				<td>Tax rate </td>
				<td>&times;</td>
				<td>Vehicle Value </td>
			</tr>
			<c:forEach var="alternative" items="${source.alternatives}" varStatus="loop">
				<tbody>
				<c:forEach var="vehicle" items="${user.vehicles}" varStatus="loop">
					<c:choose>
						<c:when test="${loop.index == 0}">
						<tr class="odd">
							<th scope="row" class="fundingSourceItem">${alternative.name} </th>
							<td>[Annual Cost]</td>
							<td>=</td>
							<td>&nbsp;</td>
							<td>${alternative.taxRate}</td>
							<td>&times;</td>
							<td>${vehicle.value}</td>
						</tr>
						</c:when>
						<c:otherwise>
						<tr>
							<th scope="row" class="fundingSourceItem">&nbsp;</th>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>+</td>
							<td>${alternative.taxRate}</td>
							<td>&times;</td>
							<td>${vehicle.value}</td>
						</tr>
						</c:otherwise>
					</c:choose>
				</c:foreach>
				</tbody>
			</c:foreach>
		</table>
	</c:if>
	<!-- End annual motor vehicle excise tax -->
	
	<!-- Begin gas tax -->
	<c:if test="${alternative.type == 4}">
		<table id="source-${source.id}" border="0" cellpadding="0" cellspacing="0">
		  <tr class="tableHeading">
			 <th scope="col">Funding Source </th>
			 <th scope="col">Annual cost to you </th>
			 <th colspan="7" scope="col">&nbsp;</th>
		  </tr>
		  <tr class="fundingType">
			 <th scope="row" class="fundingSourceItem"><a href="#" title="${source.description}">${source.name}</a> </th>
			 <td>Annual cost </td>
			 <td>=</td>
			 <td>&nbsp;</td>
			 <td>Tax Rate </td>
			 <td>&divide;</td>
			 <td>Miles per gallon </td>
			 <td>&times;</td>
			 <td>Miles driven per year </td>
		  </tr>
		<c:forEach var="alternative" items="${source.alternatives}" varStatus="loop">
			<tbody>
			<c:forEach var="vehicle" items="${user.vehicles}" varStatus="loop">
				<c:choose>
					<c:when test="${loop.index == 0}">
						<tr>
						 <th scope="row" class="fundingSourceItem">{alternative.name} </th>
						 <td>[Annual Cost]</td>
						 <td>=</td>
						 <td>&nbsp;</td>
						 <td><p>${alternative.taxRate} </p>      </td>
						 <td>&divide;</td>
						 <td>${vehicle.milesPerGallon} mpg </td>
						 <td>&times;</td>
						 <td>${vehicle.milesPerYear} miles/yr </td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
						 <th scope="row" class="fundingSourceItem">&nbsp;</th>
						 <td>&nbsp;</td>
						 <td>&nbsp;</td>
						 <td>+</td>
						 <td>${alternative.taxRate} </td>
						 <td>&divide;</td>
						 <td>${vehicle.milesPerGallon} mpg </td>
						 <td>&times;</td>
						 <td>${vehicle.milesPerYear} miles/yr </td>
						</tr>
					</c:otherwise>
				</c:choose>
			</c:foreach>
			</tbody>
		</c:foreach>
		</table>
	</c:if>
	<!-- End gas tax -->
	
	<!-- Begin SALES tax on GAS -->
	<c:if test="${alternative.type == 5}">
		<table id="source-${source.id}" border="0" cellpadding="0" cellspacing="0">
		  <tr class="tableHeading">
			 <th scope="col">Funding Source </th>
			 <th scope="col">Annual cost to you </th>
			 <th colspan="7" scope="col">&nbsp;</th>
		  </tr>
		  <tr class="fundingType">
			 <th scope="row" class="fundingSourceItem"><a href="#" title="${source.description}">${source.name}</a> </th>
			 <td>Annual cost </td>
			 <td>=</td>
			 <td>&nbsp;</td>
			 <td>Tax Rate </td>
			 <td>&divide;</td>
			 <td>Miles per gallon </td>
			 <td>&times;</td>
			 <td>Miles driven per year </td>
		  </tr>
		<c:forEach var="alternative" items="${source.alternatives}" varStatus="loop">
			<tbody>
			<c:forEach var="vehicle" items="${user.vehicles}" varStatus="loop">
				<c:choose>
					<c:when test="${loop.index == 0}">
						<tr>
						 <th scope="row" class="fundingSourceItem">{alternative.name} </th>
						 <td>[Annual Cost]</td>
						 <td>=</td>
						 <td>&nbsp;</td>
						 <td><p>${alternative.taxRate} </p>      </td>
						 <td>&divide;</td>
						 <td>${vehicle.milesPerGallon} mpg </td>
						 <td>&times;</td>
						 <td>${vehicle.milesPerYear} miles/yr </td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
						 <th scope="row" class="fundingSourceItem">&nbsp;</th>
						 <td>&nbsp;</td>
						 <td>&nbsp;</td>
						 <td>+</td>
						 <td>${alternative.taxRate} </td>
						 <td>&divide;</td>
						 <td>${vehicle.milesPerGallon} mpg </td>
						 <td>&times;</td>
						 <td>${vehicle.milesPerYear} miles/yr </td>
						</tr>
					</c:otherwise>
				</c:choose>
			</c:foreach>
			</tbody>
		</c:foreach>
		</table>
	</c:if>
	<!-- End SALES tax on GAS -->
		
	<!-- begin EMPLOYER EXCISE TAX -->
	<c:if test="${alternative.type == 6}">
		<li id="source-${source.id}"><a href="#" title="${source.description}">${source.name}</a>
			<ul>
				<c:forEach var="alternative" items="${source.alternatives}" varStatus="loop">
						<li>Funding Source: ${alternative.name}
							<ul>
								<li>Cost is not defined (maybe we can remove this one)</li>
							</ul>
						</li>
					<!-- loops for other funding source alt types -->
				</c:forEach>
			</ul>
		</li>
	</c:if>	
	<!-- end EMPLOYER EXCISE TAX -->
	
	<!-- begin COMMERCIAL PARKING TAX -->
	<c:if test="${alternative.type == 7}">
		<table id="source-${source.id}" border="0" cellpadding="0" cellspacing="0">
	
				<tr class="tableHeading">
				 <th scope="col">Funding Source </th>
				 <th scope="col">Annual cost to you </th>
				 <th colspan="7" scope="col">&nbsp;</th>
				</tr>
	 	
				<tr class="fundingType">
				 <th width="200" scope="row" class="fundingSourceItem"><a href="#" title="${source.description}">${source.name}</a> </th>
				 <td>Annual cost </td>
				 <td>=</td>
				 <td>Tax Rate </td>
				 <td>&times;</td>
				 <td>Parkings per year </td>
				</tr>
	
				<c:forEach var="alternative" items="${source.alternatives}" varStatus="loop">
					<tr class="odd">
					 <th scope="row" class="fundingSourceItem">${alternative.name} </th>
					 <td>[Annual Cost]</td>
					 <td>=</td>
					 <td>${alternative.taxRate} </td>
					 <td>&times;</td>
					 <td>[Parking/year]</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	<!-- end COMMERCIAL PARKING TAX -->
	
	<!-- begin TOLLS -->
	<c:if test="${alternative.type == 8}">
	<table id="source-${source.id}" border="0" cellpadding="0" cellspacing="0">

			<tr class="tableHeading">
			 <th scope="col">Funding Source </th>
			 <th scope="col">Annual cost to you </th>
			 <th colspan="7" scope="col">&nbsp;</th>
			</tr>
	
			<tr class="fundingType">
			 <th width="200" scope="row" class="fundingSourceItem"><a href="#" title="${source.description}">${source.name}</a> </th>
			 <td>Annual cost </td>
			 <td>=</td>
			 <td>Toll Rate </td>
			 <td>&times;</td>
			 <td>Trips Per Year </td>
			</tr>

			<c:forEach var="toll" items="${tolls}" varStatus="loop">
				<tr class="odd">
				 <th scope="row" class="fundingSourceItem">${toll.name} </th>
				 <td>[Annual Cost]</td>
				 <td>=</td>
				 <td>${toll.taxRate} </td>
				 <td>&times;</td>
				 <td>[Trips/year]</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<!-- end TOLLS -->
	</c:forEach>	
	<p><input type="button" value="Upate Annual Cost Report"/></p>
	</div>