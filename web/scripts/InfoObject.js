	 function InfoObject(struc,obj,objtitle,objNum,strucNum,strucTitle,currentPage){
	 	/*----Global vars and Methods- to be defined in a separate file ---- 
	 		SDC - getTargets(); sdcSummary.jsp, sdcStructureSummary.jsp
	 		SDCr - 
	 		sdP -
	 		sdPr - 
	 		sdRp -
	 	with examples:
	 	
	 	
	 	*/
	 	
	 	/*----Global vars - these are the variables needed for each infoObject instance ---- 
	 	 this.structureId = "${structure.id}"; //isid
	 	 this.objectId = "${object.id}"; //ioid
	 	 this.objectTitle =" ${object.object}"; //object  title
	 	 this.numObjectDiscussions = ${object.numDiscussion} //number of object discussions
	 	 this.numStructureDiscussions = ${structure.numDiscussion}	 	
	 	 this.ISTitle = "All Concern Themes"; // title of entire info structure
	 	 this.currentPage = '<%= request.getParameter("page") %>';
	 	 
	 	 Example when called from a separate page:
	 	 var infoObject = new InfoObject("${structure.id}","${object.id}","${object.object}", "${object.numDiscussion}", "${structure.numDiscussion}", "All Concern Themes", '<%= request.getParameter("page") %>');
	 	 */
	 	 
	 	 
	 	 
	 	 this.structureId = struc; //isid
	 	 this.objectId = obj; //ioid
	 	 this.objectTitle = objtitle; //object  title
	 	 this.numObjectDiscussions = objNum; //number of object discussions
	 	 this.numStructureDiscussions = strucNum	;
	 	 this.ISTitle = strucTitle;
	 	 this.currentDiscPage = currentPage;
	 	
	 	 //Make sure ID's of Inputs and Divs match when putting the following in a separate file.
	 	 /*----Input ID's - these id's of input elements have changing content or gets read by the javascript ---- */
	 	 this.newPostTitleInput = "txtNewPostTitle";   //new post title input box
	 	 this.newPostBodyInput = "txtNewPost"; //new post input text area
	 	 this.newPostTagsInput = "txtNewPostTags"; //new post tags input box
	 	 
	 	 /*----Divs - these divs have changing content or gets read by the javascript ---- */
	 	 this.sidebarDiv = 'sidebar_object'; //div that contains the sidebar
	 	 this.objectDiv =  'object-content'; //div that contains the object
	 	 this.discussionDiv = 'discussion'; //div that contains the discussion
	 	 this.votingQuestionDiv = 'structure_question' //div that contains the voting question
	 	 this.newDiscussionDiv = 'newDiscussion'; //the new discussion pull down
	 	 this.IOTitleDiv = 'targetTitle';
	 	 this.discTitleDiv = 'targetDiscussionTitle';
	 	 this.sidebarBottomDiv1 = 'sidebarbottom_disc' //the bottom of the sidebar that changes when a new discussion is toggled
	 	 this.sidebarBottomDiv2 = 'sidebarbottom_newdisc' //the bottom of the sidebar that changes when a new discussion is toggled
	 	 

		/***************Assign Target Headers************** */
	 	this.assignTargetHeaders = function(){
	 		if(this.objectId != ""){ //for info object
				$(this.discTitleDiv).innerHTML = '<html:link action="/sd.do" paramId="isid" paramName="structure" paramProperty="id">'+ this.ISTitle +'</html:link>  &raquo; ' + this.objectTitle; //object title div id
				$(this.discTitleDiv).innerHTML = this.objectTitle;//discussion title div id
				if (this.numObjectDiscussions == 1){
				 	$(this.discTitleDiv).innerHTML += ' - ' +this.numObjectDiscussions+' Topic';
				}else{
					$(this.discTitleDiv).innerHTML += ' - ' +this.numObjectDiscussions+' Topics';
				}
			}else{//for entire info structure
				$(this.discTitleDiv).innerHTML = '<html:link action="/sd.do" paramId="isid" paramName="structure" paramProperty="id">'+ this.ISTitle +'</html:link>  &raquo; ' + this.ISTitle + ' List'; //object title div id
				$(this.discTitleDiv).innerHTML = this.ISTitle;//discussion title div id
					if (this.numStructureDiscussions == 1){
					 	$(this.discTitleDiv).innerHTML += ' - ' +this.numStructureDiscussions+' Topic';
					}else{
						$(this.discTitleDiv).innerHTML +=  ' - ' +this.numStructureDiscussions+' Topics';
					}
			}
		};
		
	 
		/*************** Set Vote************** */
	 	 this.setVote = function(agree){
	 	 			displayIndicator(true);
	 	 			alert("structure" + this.structureId + "object " + this.objectId + "vote " + agree);
					SDAgent.setVoting({isid: this.structureId, ioid: this.objectId, agree:agree}, {
					callback:function(data){
							if (data.successful){ 
								alert("structure" + this.structureId + "object " + this.objectId + "vote " + agree);
	              				 new Effect.Fade(infoObject.votingQuestionDiv, {afterFinish: function(){infoObject.getTargets(); new Effect.Appear(infoObject.votingQuestionDiv);}});
	              				 displayIndicator(false);
							}else{
								alert(data.reason);
								displayIndicator(false);
							}
						},
					errorHandler:function(errorString, exception){ 
							alert("setVote error:" + errorString + exception);
					}
					});

		};
			
		/*************** New Discussion Post: if successful, reload discussion posts************** */
	 	 this.createPost = function(){
	 	 		displayIndicator(true);
	
	 	 		var newPostTitle = $(this.newPostTitleInput).value;
	 	 		var newPost = validateInput($(this.newPostBodyInput).value);
	 	 		var newPostTags = $(this.newPostTagsInput).value;
	 	 		
				SDAgent.createPost({isid:this.structureId, ioid: this.objectId, title: newPostTitle, content: newPost, tags:newPostTags}, {
				callback:function(data){
						if (data.successful){
							 infoObject.getPosts();
							 infoObject.clearNewDiscussionInputs();
							 infoObject.toggleNewDiscussion();
							 displayIndicator(false);
						}else{
							alert(data.reason);
							 infoObject.toggleNewDiscussion();
							 displayIndicator(false);
						}
					},
				errorHandler:function(errorString, exception){ 
						alert("create post error:" + errorString + exception);
				}
				});
			};
			
		/*************** Clear all discussion input boxes - triggered after a new post is created ************** */
		this.clearNewDiscussionInputs =  function(){
	 	 		$(this.newPostTitleInput).value = "";
	 	 		$(this.newPostBodyInput).value = "";
	 	 		$(this.newPostTagsInput).value = "";
		};
			
		/*************** Scriptaculous toggle effect for the new discussion div************* */
	  	this.toggleNewDiscussion =  function(){
			if ($(this.newDiscussionDiv).style.display == 'none'){
				displayIndicator(true);
				new Effect.toggle(this.newDiscussionDiv, 'blind', {duration: 0.5});
				$(this.sidebarBottomDiv1).style.display = 'none';	
				$(this.sidebarBottomDiv2).style.display = 'block';	
				displayIndicator(false);
			}else{
				displayIndicator(true);
				new Effect.toggle(this.newDiscussionDiv, 'blind', {duration: 0.5, afterFinish: function(){
					$(infoObject.sidebarBottomDiv1).style.display = 'block';	
					$(infoObject.sidebarBottomDiv2).style.display = 'none';	
					displayIndicator(false);	
				}});		
			}
		};
			
		/***************Get all posts for the given infoObject.  If infoObject is omitted, get infoStructure discussions. Partial: SDPosts.jsp************** */
		this.getPosts = function(){
			    displayIndicator(true);
		    	var page = 1;
		 		if (this.currentDiscPage != null){
		 			page = this.currentDiscPage;	
		 		}
			   
				SDAgent.getPosts({isid:this.structureId, ioid:this.objectId, page: page, count: 10}, {
			      callback:function(data){
			          if (data.successful){
			          $(infoObject.discussionDiv).innerHTML = data.html;
			           displayIndicator(false);
			          }else{
			            alert(data.reason);
			            displayIndicator(false);
			          }
			      },
			      errorHandler:function(errorString, exception){
			          alert("get posts error:" + errorString + exception);
			      }
			    });
			  };
	}; //end InfoObject
	
		 
	function validateInput(string){
			string=string.replace(/>/g,"//>//");
			string=string.replace(/</g,"//<//");
			string=string.replace(/\n/g,"<br>");

			return string;
	}
