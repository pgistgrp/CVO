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
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Zhong Wang, John Le, Guirong Zhou


#### -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8">
		<title>Projects Scoring</title>
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
            @import "styles/lit.css";
            @import "styles/step4-start.css";
            @import "styles/loading-indicator.css";
                    
            #projects-list li{
                padding: 5px;
            }
            
            #obj-left, #obj-right{
                overflow:auto;
                height:500px;
                border: 5px solid #DDD;
                width: 45%;
                padding:5px;
            }
        </style>			


		<script type="text/javascript" charseut="utf-8">
		    highlightOn = "#BDD5FA";
		    highlightOff = "#FFFFFF";
		    

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
			
			function getAltRefScores(altRefId){
			    $('scores').innerHTML = "<img src='/images/indicator_arrows.gif' /> Loading scores...";
			    Util.loading(true,"Loading scores");
			    ProjectAgent.getProjectAltRefById({id:altRefId}, {
			         callback:function(data){
			             if (data.successful){
			                 var score = new Object;
			                 score.gCrits = data.altRef.gradedCriteria;
			                 score.gCritIds = [];
			                 score.gObjIds = [];
			                 score.grades = [-3,-2.5,-2,-1.5,-1,-0.5,0.0,0.5,1,1.5,2,2.5,3]
			                 score.html = "<h4>"+ data.altRef.alternative.name +"</h4> <a id='info"+data.altRef.id+"' target='blank' href='projectAlt.do?altrefId="+data.altRef.id+"'>View project info</a><ul>";
			                 score.counter = 0;
			                 
			                 //Get and render graded crit and objectives array
			                 score.gCrits.each(function(gCrit, i) {
			                   score.html += "<li>"+ gCrit.criteria.name +"  \
			                   <b id='critGrade-"+ altRefId +"-"+ gCrit.criteria.id+"'>"+ gCrit.grade +"</b></li><ul>"                  
                               gCrit.objectives.each(function(gObj,j){
                                   score.counter += 1;
                                   score.html += "<li>\
                                   <select id='objGrade-"+ data.altRef.id +"-"+ score.counter +"' onchange='setGrading("+altRefId+","+gCrit.criteria.id+","+gObj.objective.id+", this.value);'>";
                                      score.grades.each(function(grade){
                                          if(grade == 0.0){
                                            selected = (gObj.grade == null || gObj.grade == 0.0) ? "selected = true" : "";
                                            score.html += "<option "+ selected +" value="+ grade +">0</option>";
                                          }else{
                                            selected = (gObj.grade == grade) ? "selected = true" : "";
                                            score.html += "<option "+ selected +" value="+ grade +">"+ grade +"</option>";
                                          }
                                      })  

                                   score.html +="</select> "+ gObj.objective.description +"\
                                   </li>";
                                   
                                 
                               }) //end gCrit.objectives.each
                               
                               score.html += "</ul>"
                             });
                             score.html += "</ul>"
                             $('scores').innerHTML = score.html;
                             
                             
             			    //focus to first objective in scores
             			    $("objGrade-" + altRefId + "-1").focus();
             			    
                           //Add event observer if it is the last objective - on keydown of "tab" key switch to next alternative
             			    Event.observe('objGrade-'+ altRefId +'-'+ score.counter, 'keypress', function(e){
                                   var code;
                                   if (!e) var e = window.event;
                                   if (e.keyCode) code = e.keyCode;
                                   else if (e.which) code = e.which;
                                   
                                   if (e.keyCode == 9){
                                      nextRow();
                                   }
                   			
                               });
                           
                           //Add event observer if it is the first objective - on keydown of "shift + tab" key switch to next alternative
                            Event.observe('objGrade-'+ altRefId +'-'+ 1, 'keypress', function(e){
                                  var code;
                                  if (!e) var e = window.event;
                                  if (e.keyCode) code = e.keyCode;
                                  else if (e.which) code = e.which;

                                  if (e.keyCode == 9 && e.keyCode == 16){
                                     prevRow();
                                  }

                              });

			             }else{
			                 alert(data.reason);
			             }
			             Util.loading(false,"Loading scores");
			         },
			         errorHandler:function(errorString, exception){ 
			         alert("ProjectAgent.getProjectALtRefById( error:" + errorString + exception);
			         }
			    });
			}

			function highlightList(){
			    $$('.projAlt').each(function(alt){
			        Event.observe(alt,'click',function(){
			            //highlighter(alt);
			            switchItem(alt);
			        });
			    })
			}
			
			var currentAlt = null;
			function highlighter(choosen){
			    currentAlt = choosen;
			    $$('.projAlt').each(function(projAlt){
			        if(choosen == projAlt){
			            projAlt.style.background = highlightOn;
			        }else{
			            projAlt.style.background = highlightOff;
		            }
			    })
			    
			}

            //adjust columns to full screen
			function colsTofullScreen(){
			    height = (document.documentElement.clientHeight || document.body.clientHeight);
			    buffer = 150;
			    hpx = (height - buffer) + "px";
                $('obj-left').style.height = hpx;
                $('obj-right').style.height = hpx;
			}
			

			function observeKeys(){
			    Event.observe('obj-left', 'keypress', function(e){
                    var code;
                    if (!e) var e = window.event;
                    if (e.keyCode) code = e.keyCode;
                    else if (e.which) code = e.which;

                    switch (e.keyCode){
    					// Key up.
    					case 38:
    						previousRow();					
    					    break;
    					    
    					// Key down.
    					case 40:
                            nextRow();
    					    break;
    				}
					
                });
			}
			
			function nextRow(){
			    if(!currentAlt){return false}
			    if(currentAlt.next()){
                    item = currentAlt.next();
                }else{
				    //check to see if it is the top row
                    if (currentAlt.up(1).next()) {
                        item = currentAlt.up(1).next().down(2);
                    }else{
                        return false;
                    }
                }
                switchItem(item);
			}
			
			function previousRow(){
		        if(!currentAlt){return false}
			    if(currentAlt.previous()){
                    item = currentAlt.previous();
                }else{
				    //check to see if it is the top row
                    if (currentAlt.up(1).previous()) {
                        items = currentAlt.up(1).previous().descendants();
                        //Get the last item in the previous list then go back up to the LI element
                        item = items[items.length - 1].up();
                    }else{
                        return false;
                    }
                }
                switchItem(item);
			}
			
			function switchItem(item){
			    altRefId = getAltIdFromItem(item)
			    highlighter(item)
			    getAltRefScores(altRefId);
			    
			}
			
			function getAltIdFromItem(item){
			    tag = "liProjAlt";
			    altRefId = item.id.substr(tag.length, item.id.length);
			    return altRefId;
			}
			
            Event.observe(window,'load',function(){
                colsTofullScreen();
                highlightList();
               // observeKeys();  
            });
            Event.observe(window,'resize',function(){colsTofullScreen();});
		</script>
	<event:pageunload />
	</head>
	<body>
	    
	    <div id="container">
    		<!-- begin Object -->
    		<div id="object">
		        <p><a href="userhome.do?wf=${requestScope['org.pgist.wfengine.WORKFLOW_ID']}">Back to Moderator Control Panel</a></p>
		        <h1 class="headerColor">Project Scoring</h1>
    			<!-- begin obj-left -->
    			<div id="obj-left" class="floatLeft clearBoth">
        		    <h3 class="headerColor">Projects and Project Alternatives</h3>
    				<!-- begin list of funding options -->
    				<div id="allListHeader">
    					<ul id="projects-list">
                			<c:forEach var="projectRef" items="${projSuite.references}" varStatus="loop">
                				<li id="liProj${projectRef.id}"><span class="project">Project: ${projectRef.project.name}</span>
                				    <ul id="ulProjAlts${projectRef.id}">
                					<c:forEach var="altRef" items="${projectRef.altRefs}" varStatus="loop">
                						<li id="liProjAlt${altRef.id}" class="projAlt">
                						    <a id="projAlt${altRef.id}" href="#">${altRef.alternative.name}</a> 
                						    <!--(<a href="projectAlt.do?altrefId=${altRef.id}">project info</a>)-->
                					    </li>
                					</c:forEach>
                				    </ul>
                		        </li>
                			</c:forEach>
                		</ul>
                    </div>
                    
                    
    			</div>
    			<!-- end obj-left -->
    			<!-- begin cell containing Google Map object -->
    			<div id="obj-right" class="floatRight">
                     <h3 class="headerColor">Scores</h3>
    			     <div id="scores" class="scores">
    			         <p>Click on a project alternative to load it's scores.</p>
    			     </div>
    		    </div>
    			<!-- end cell containing Google Map object -->
    			<!-- begin firefox height hack -->

    			<div class="clearBoth"></div>
    			<!-- end firefox height hack -->
    		</div>
    		<!-- end Object-->
    		<h3 class="headerColor">Finished scoring projects?</h3>
    		<p><input type="button" style="padding:5px;" onClick="location.href='userhome.do?wf=${requestScope['org.pgist.wfengine.WORKFLOW_ID']}'" value="Finished!"/></p>
    		<p>TEMP (For Developers) - <a href="javascript:Element.toggle('XMLwrapper');updateXML();">View XML for Data Template</a></p>
    		
    		<div id="XMLwrapper" style="display:none">
        		<textarea id="xmlDataTemplate" style="width:100%; height: 500px">
                    <!--update via DWR-->
        	    </textarea>
    	    </div>
    	    <br />
    	</div>
    	<!-- end container -->

	</body>
</html>

