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
		[ ] Integrate Layout (Adam)
		[ ] Integrate Project Map or static image (Guirong/Issac)
		[ ] Integrate Criteria Tree (Issac)
#### -->

<h3 class="headerColor clearBoth">My Annual Costs Report</h3>
<ul>
<c:forEach var="source" items="${sources}" varStatus="loop">
	
	<!-- sigh this is soo ugly -->
	
	<!-- sales tax -->
	<c:if test="${alternative.type == 1}"> 
		<li id="source-${source.id}"><a href="#" title="$*{source.description}">${source.name}
			<ul>
				<c:forEach var="alternative" items="${source.alternatives}" varStatus="loop">
						<li>Funding Source: ${alternative.name}
							<ul>
								<li>Annual Cost: </li>
								<li>Tax Rate: ${alternative.taxRate}</li>
								<li>Estimated Annual Consumption: ${userCommute.annualConsume}
							</ul>
						</li>
					<!-- loops for other funding source alt types -->
				</c:forEach>
			</ul>
		</li>
	</c:if>
	
	<!-- Annual Vehicle License Fee -->
	<c:if test="${alternative.type == 2}">
		<li id="source-${source.id}"><a href="#" title="$*{source.description}">${source.name}
			<ul>
				<c:forEach var="alternative" items="${source.alternatives}" varStatus="loop">
						<li>Funding Source: ${alternative.name}
							<ul>
								<li>Annual Cost: </li>
								<li>Tax Rate: ${alternative.taxRate}</li>
								<li>Number of Vehicles:${fn:length(user.vehicles)}</li>
							</ul>
						</li>
					<!-- loops for other funding source alt types -->
				</c:forEach>
			</ul>
		</li>
	</c:if>
	
	<!-- Annual Motor Vehicle Excise Tax -->
	<c:if test="${alternative.type == 3}">
		<li id="source-${source.id}"><a href="#" title="$*{source.description}">${source.name}
			<ul>
				<c:forEach var="alternative" items="${source.alternatives}" varStatus="loop">
						<li>Funding Source: ${alternative.name}
							<ul>
								<c:forEach var="vehicle" items="${user.vehicles}" varStatus="loop">
									<li>Annual Cost: </li>
									<li>Tax Rate: ${alternative.taxRate}</li>
									<li>Vehicle Value:${vehicle.value}</li>
								</c:forEach>
							</ul>
						</li>
					<!-- loops for other funding source alt types -->
				</c:forEach>
			</ul>
		</li>
	</c:if>
	
	<!-- Gas Tax -->
	<c:if test="${alternative.type == 4}">
		<li id="source-${source.id}"><a href="#" title="$*{source.description}">${source.name}
			<ul>
				<c:forEach var="alternative" items="${source.alternatives}" varStatus="loop">
						<li>Funding Source: ${alternative.name}
							<ul>
								<c:forEach var="vehicle" items="${user.vehicles}" varStatus="loop">
									<li>Annual Cost: </li>
									<li>Tax Rate: ${alternative.taxRate}</li>
									<li>Miles Per Gallon:${vehicle.milesPerGallon}</li>
									<li>Miles Driven Per Year:${vehicle.milesPerYear}</li>
								</c:forEach>
							</ul>
						</li>
					<!-- loops for other funding source alt types -->
				</c:forEach>
			</ul>
		</li>
	</c:if>
	
	<!-- Sales Tax on Gas -->
	<c:if test="${alternative.type == 5}">
		<li id="source-${source.id}"><a href="#" title="$*{source.description}">${source.name}
			<ul>
				<c:forEach var="alternative" items="${source.alternatives}" varStatus="loop">
						<li>Funding Source: ${alternative.name}
							<ul>
								<c:forEach var="vehicle" items="${user.vehicles}" varStatus="loop">
									<li>Annual Cost: </li>
									<li>Tax Rate: ${alternative.taxRate}</li>
									<li>Miles Per Gallon:${vehicle.milesPerGallon}</li>
									<li>Miles Driven Per Year:${vehicle.milesPerYear}</li>
								</c:forEach>
							</ul>
						</li>
					<!-- loops for other funding source alt types -->
				</c:forEach>
			</ul>
		</li>
	</c:if>
	
	<!-- Employer Excise Tax -->
	<c:if test="${alternative.type == 6}">
		<li id="source-${source.id}"><a href="#" title="$*{source.description}">${source.name}
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
	
	<!-- Commercial Parking Tax -->
	<c:if test="${alternative.type == 7}">
		<li id="source-${source.id}"><a href="#" title="$*{source.description}">${source.name}
			<ul>
				<c:forEach var="alternative" items="${source.alternatives}" varStatus="loop">
						<li>Funding Source: ${alternative.name}
							<ul>
								<li>Annual Cost: </li>
								<li>Tax Rate: ${alternative.taxRate}</li>
								<li>Parkings Per Year: ???</li>
							</ul>
						</li>
					<!-- loops for other funding source alt types -->
				</c:forEach>
			</ul>
		</li>
	</c:if>
	
	<!-- Tolls -->
	<c:if test="${alternative.type == 7}">
		<li id="source-${source.id}"><a href="#" title="$*{source.description}">${source.name}
			<ul>
				<c:forEach var="alternative" items="${source.alternatives}" varStatus="loop">
						<li>Funding Source: ${alternative.name}
							<ul>
								<c:forEach var="toll" items="${tolls}" varStatus="loop">
									<li>Annual Cost: </li>
									<li>Toll Rate: ${alternative.taxRate}</li>
									<li>Trips Per Year: ???</li>
								</c:forEach>
							</ul>
						</li>
					<!-- loops for other funding source alt types -->
				</c:forEach>
			</ul>
		</li>
	</c:if>
</c:forEach>
</ul>
<p><input type="button" value="Upate Annual Cost Report"/></p>