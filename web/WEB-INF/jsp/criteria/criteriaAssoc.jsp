<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>Associate Planning Factors</title>
<!-- Site Wide JavaScript -->
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->

<!--Criteria Specific  Libraries-->
<script src="scripts/SideBar.js" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/interface/CriteriaAgent.js'></script>

<script>
	var cctId = <%= request.getParameter("cctId") %>;
	var critSuiteId = "${criteriasuite.id}"
	function assocCriterion(critId, checked){
		//alert("critSuiteId: " + critSuiteId + " critId: " + critId + " checked: " + checked); 
		CriteriaAgent.addAssocCriterion({critSuiteId:critSuiteId,critId:critId,checked:checked}, {
			callback:function(data){
				if (data.successful){
					//alert("successfully set crit "+critId+" to " +checked+ "!");
					new Effect.toggle(critId + 'themes','blind',{duration:0.2});
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("CriteriaAgent.assocCriterion( error:" + errorString + exception);
			}
		});
	}
	
	function switchCheckboxes(checked){
		var criteria = document.getElementsByName("planningFactor");
		for(i=0;i<criteria.length;i++){
			if(criteria[i].checked == !checked){ //only close those that are open and vise versa
				criteria[i].checked = checked;
				critId = criteria[i].id.substring(4,criteria[i].id.length); //grab just the ID
				assocCriterion(critId, checked);
			}
		}
	}
	
	/* *************** Grab all themes for the current instance *************** */
	function getThemes(){
		//alert("cctId: " + cctId); 
		CriteriaAgent.getThemes({cctId:cctId}, {
			callback:function(data){
				if (data.successful){
					alert(data.html);
					
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("CriteriaAgent.getThemes( error:" + errorString + exception + " cctId:" + cctId);
			}
		});
	}
	
	
	
		/******Puts checkmarks next to the Themes that are already part of a criterion, given its ID.****/
			/***Must have the edit form made and getThemes(cid) run before***/
			/***This is called by getThemes**/
			function getThemesToEditByCriterionId(cid){
				CriteriaAgent.getCriterionById({id:cid},{
					callback:function(data){
					if(data.successful){

						for(a=0;a<data.criterion.themes.length;a++){

							$('editTheme-'+data.criterion.themes[a].id).checked=true;
						}

					}else{
						alert("error in getThemesToEditByCriterionId");
					}

				},
				errorHandler:function(errorString, exception){ 
						alert("getThemesToEditByCriterionId error:" + errorString + exception);
					}
				});
			}
</script>
<style type="text/css">
	body{font-size:11 pt;font-family:arial;width:800px;}
	li{margin: 10px 0; list-style: none;}
	li ul li:hover {background:#D5EAEF;}
	#finished{text-align:right;}
</style>
</head>


<body>
	<p><a href="main.do">Back to Moderator Control Panel</a></p>
	<h1>Define Planning Factors and Associated Themes</h1>
	<p>Select all planning factors that you would like to include for this expiriment.</p>

	<h3>All Planning Factors</h3>
	<ul id="sourcesList">
		<small>
			<a href="javascript:switchCheckboxes(true)">check all</a> | 
			<a href="javascript:switchCheckboxes(false)">uncheck all</a>
		</small>
		<c:forEach var="criterion" items="${criteria}">
			<li><label><input type="checkbox" ${(pg:containsCriteria(criteriasuite,criterion)) ? "CHECKED" : ""} name="planningFactor" id="crit${criterion.id}" onClick="assocCriterion('${criterion.id}', this.checked)"/> ${criterion.name}</label>
				<ul id="${criterion.id}themes" ${(pg:containsCriteria(criteriasuite,criterion)) ? "" : "style='display:none'"}>
					<li><small>Which concern themes are related to this criterion?</small> **** ${themes}</li>
					<li><label><input type="checkbox" name="${criterion.id}checkboxes" onClick="setTheme('themeId','${criterion.id}',this.checked);"/><small> loop</small></label></li>
				</ul>
			</li>
		
		</c:forEach>
	</ul>

	<div id="finished">
		<h3>Finished selecting planning factors?</h3>
		<p>The system will automatically publish these planning factors on --date--</p>
		<p><input type="button" style="padding:5px;" onClick="location.href='main.do'" value="Finished!"/></p>
	</div>
</body>
</html:html>

