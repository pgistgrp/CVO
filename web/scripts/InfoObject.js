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
	 	 this.currentDiscPage = 1;
	 	
	 	 //Make sure ID's of Inputs and Divs match when putting the following in a separate file.
	 	 /*----Input ID's - these id's of input elements have changing content or gets read by the javascript ---- */
	 	 this.newPostTitleInput = "txtNewPostTitle";   //new post title input box
	 	 this.newPostBodyInput = "txtNewPost"; //new post input text area --- now uses tinyMCE.getContent() to get input
	 	 this.newPostTagsInput = "txtNewPostTags"; //new post tags input box
	 	 
	 	 /*----Divs - these divs have changing content or gets read by the javascript ---- */
	 	 this.sidebarDiv = 'sidebar_object'; //div that contains the sidebar
	 	 this.objectDiv =  'object-content'; //div that contains the object
	 	 this.discussionDiv = 'discussion'; //div that contains the discussion
	 	 this.votingQuestionDiv = 'structure_question' //div that contains the voting question
	 	 this.newDiscussionDiv = 'newDiscussion'; //the new discussion pull down
	 	 this.IOTitleDiv = 'targetTitle';
	 	 this.discTitleDiv = 'targetDiscussionTitle';
	 	 


	 

			
		/*************** New Discussion Post: if successful, reload discussion posts************** */
	 	 this.createPost = function(){
	
	 	 		var newPostTitle = $(this.newPostTitleInput).value;
	 	 		//var newPost = validateInput($(this.newPostBodyInput).value);
				var newPost= tinyMCE.getContent();
				tinyMCE.setContent('');
	 	 		var newPostTags = $(this.newPostTagsInput).value;
	 	 		
	 	 		//alert("ISID: " + this.structureId + "IOID: " + this.objectId + "Title: " + newPostTitle + "Content: " + newPost + "Tags: " + newPostTags);
				SDAgent.createPost({isid:this.structureId, ioid: this.objectId, title: newPostTitle, content: newPost, tags:newPostTags}, {
				callback:function(data){
						if (data.successful){
						     infoObject.setVote("post", data.id, "true"); //set initial vote
							 infoObject.clearNewDiscussionInputs();
							 infoObject.toggleNewDiscussion();
							 if(infoObject.currentDiscPage != 1){
							 	infoObject.currentDiscPage = 1
							 }
							 infoObject.getPosts();
						}else{
							alert(data.reason);
							 infoObject.toggleNewDiscussion();
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
			

			

	}; //end InfoObject
	
		 
	function validateInput(string){
			string=string.replace(/>/g,"//>//");
			string=string.replace(/</g,"//<//");
			string=string.replace(/\n/g,"<br>");

			return string;
	}
