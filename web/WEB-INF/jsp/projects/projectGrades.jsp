<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!--####
	Project: Let's Improve Transportation!
	Page: Project Grading
	Description: This page is to grade projects on criteria in the given decision situation.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman, Issac Yang
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] Tree with Unobtrusive JS
		[ ] Project Alt sorting (Matt)
		[x] reason:"Error: this objective could not be assigned the specified grade",successful:false} (Matt)
		[x] contains to get the objective value
		[ ] Polishing (Adam)

#### -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8">
		<title>Grade Projects</title>
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

		<!--Project Grades Specific  Libraries-->
		<script type='text/javascript' src='/dwr/interface/ProjectAgent.js'></script>
		<script src="scripts/simpletreemenu.js" type="text/javascript"></script>
		<script src="scripts/util.js" type="text/javascript"></script>
		<style type="text/css" media="screen">
			@import "styles/simpletree.css";
			@import "styles/loading-indicator.css";
		</style>
		
		<style type="text/css" media="screen">
			body {font-family:arial;font-size:12pt;}
			
			li{margin: 10px 0;}
			.project{font-size: 1.3em;}
			
		</style>

		<script type="text/javascript" charseut="utf-8">
            projSuiteId = "${projSuite.id}";
            
            function updateXML(){
                ProjectAgent.getProjectSuite({id:projSuiteId}, {
                    callback:function(data){
                        if (data.successful){
                            
                            
xml = '<?xml version="1.0" encoding="UTF-8"?>\r\n\
<template>\r\n\
    <projects>\r\n'
    data.projSuite.references.each(function(pRef){
        xml += '\t<project name="'+pRef.project.name +'">\r\n'; 
        pRef.altRefs.each(function(aRef){
            xml += '\t\t<alternative name="'+aRef.alternative.name+'">\r\n'
            aRef.gradedCriteria.each(function(cRef){
                xml += '\t\t\t<criterion name="'+cRef.criteria.name+'">\r\n'
                cRef.objectives.each(function(oRef){
                    xml += '\t\t\t\t<objective description="'+oRef.objective.description+'">'+ oRef.grade +'</objective>\r\n'
                })
                xml += '\t\t\t</criterion>\r\n'
            })
            xml += '\t\t</alternative>\r\n'
        })
        xml += '\t</project>\r\n'
    });
xml+='\
    </projects>\r\n\
    <fundings>\r\n'
    /*
    data.fundSuite.references.each(function(fRef){
        xml += '\t<funding name="'+fRef.source.name +'">\r\n'; 
        fRef.altRefs.each(function(faRef){
            xml+= '\t\t<alternative>'+faRef.alternative.name+'</alternative\r\n'
        })
        xml += '\t</funding>\r\n'
    })*/
    xml += '</fundings>\r\n\
</template>';
                            
                            $('xmlDataTemplate').value = xml;
                        }else{
                            alert(data.reason);
                        }
                    },
                    errorHandler:function(errorString, exception){ 
                    alert("ProjectAgent.getProjectSuiteById( error:" + errorString + exception);
                    }
                });
            }
            
			function setGrading(altRefId, critId, objId, value){
				if(value != ""){
					//alert("altRefId: " + altRefId + " critId: " + critId + " objId: " + objId +" value: " +value ); 
					Util.loading(true,"Saving grades");
					ProjectAgent.setGrading({altRefId:altRefId,critId:critId,objId:objId,value:value},{
						callback:function(data){
							if (data.successful){
								//alert("grade set! Setting Criteria Grade to: " + data.critGrade)  //testing
								$('critGrade-' + altRefId + '-' + critId).innerHTML = data.critGrade; //returned grade
								//new Effect.Highlight('critGrade-' + critId); //highlight reflecting change
							    if($('XMLwrapper').visible()){
							        updateXML();
							    }
							}else{
								alert(data.reason);
							}
						Util.loading(false)
						},
						errorHandler:function(errorString, exception){ 
						alert("ProjectAgent.setGrading( error:" + errorString + exception);
						}
					});
				}
			}
			
            function getGrades(altRefId){
	    				$('scorePane').innerHTML = "loading scores, please wait ...";
					ProjectAgent.getGradesByAltRefId({id:altRefId},{
						callback:function(data){
							if (data.successful){
								$('scorePane').innerHTML = data.html;
                                
                                /*if($('XMLwrapper').visible()){
							        updateXML();
							    }*/
							}else{
								alert(data.reason);
							}
						Util.loading(false)
						},
						errorHandler:function(errorString, exception){ 
						alert("ProjectAgent.setGrading( error:" + errorString + exception);
						}
					});                
            }
            
		</script>
	<event:pageunload />
	</head>
	<body>
		<p><a href="userhome.do?wf=${requestScope['org.pgist.wfengine.WORKFLOW_ID']}">Back to Moderator Control Panel</a></p>
		<h1>Grade Projects on Criteria Objectives</h1>
        <table border="0" width="95%">
            <tr>
            <td width="450" valign="top">
                <div style="width: 450px;height: 800px;overflow: auto;">
                <ul id="treemenu1" class="treeview">
                    <c:forEach var="projectRef"  items="${projSuite.references}" varStatus="loop">
                    <li><span class="project">Project: ${projectRef.project.name}</span><ul>
                        <c:forEach var="altRef" items="${projectRef.altRefs}" varStatus="loop">
                            <li><a href="javascript: getGrades(${altRef.id})">${altRef.alternative.name}</a></li>
                        </c:forEach>
                    </ul></li>
                    </c:forEach>
                </ul>
                </div>
            </td>
            <td valign="top"><div id="scorePane">Click on a project alternative name to view/change the scores</div>
            </td>
            </tr>
        </table>
        
		<!-->
		<a href="javascript:ddtreemenu.flatten('treemenu1', 'expand')">Expand All</a> | 
		<a href="javascript:ddtreemenu.flatten('treemenu1', 'contact')">Collapse All</a>
        projectAlt.do?altrefId=${altRef.id}" target="_blank
	-->
		
		<h3>Finished grading projects?</h3>
		<!-- this button just redirects - saves are occuring on check. -->
		<p>
		<input type="button" style="padding:5px;" 
		onClick="location.href='userhome.do?wf=${requestScope['org.pgist.wfengine.WORKFLOW_ID']}'" value="Finished!"/></p></div>
		
		<p>TEMP (For Developers) - <a href="javascript:Element.toggle('XMLwrapper');updateXML();">View XML for Data Template</a></p>
		<div id="XMLwrapper" style="display:none">
    		<textarea id="xmlDataTemplate" style="width:100%; height: 500px">
                <!--update via DWR-->
    	    </textarea>
	    </div>
	    <br />
		
		<script type="text/javascript">
		    //updateXML();
			//ddtreemenu.createTree(treeid, enablepersist, opt_persist_in_days (default is 1))
			//ddtreemenu.createTree("treemenu1", false);
			//ddtreemenu.flatten('treemenu1', 'contact');
			//ddtreemenu.flatten('treemenu1', 'expand');
		</script>
	</body>
</html>

