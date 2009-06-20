		 //Creates a new Filter Class (called upon by SideBar Class)
		function Filter(tagRefId, status, bool, tagName){
				this.tagRefId = tagRefId;
				this.tagName = tagName;
				this.status = status;
				this.removeable = bool
		}
	
		 //Creates a new SideBar Class
		 function SideBar(struc,obj,cctID,objTitle,title,itemscount,allLink,filterText,tagCount, postId){
		 		/*----Global vars - these are the variables needed for each SideBar instance ---- 
		 		EXAMPLE - Pull real data from separate page
			 	 this.structureId = "${structure.id}"; //isid
			 	 this.objectId = "${object.id}"; //ioid
			 	 this.cctId = "${structure.cctId}";  //converted cctId
			 	 this.objectTitle =" ${object.object}"; //object  title
			 	 this.sidebarTitle = "Participant Concerns"; // title of entire info structure
			 	 this.itemsCount = 5 //number of items in the sidebar on each page
			 	 this.showAllLinkText = "Show All Concerns"; //text for the show all 'tab'
			 	 this.filterHeaderText = "Filter All Concerns By:"; //text for the filter header
			 	 this.tagCloudCount = 50; //number of tags in the tag cloud
			 	 this.postId = '' //post ID*/
			 	 
			 	 this.structureId = struc; //isid
			 	 this.objectId = obj; //ioid
			 	 this.cctId = cctID;  //converted cctId
			 	 this.objectTitle = objTitle; //object  title
			 	 this.sidebarTitle = title; // title of entire info structure
			 	 this.itemsCount = itemscount; //number of items in the sidebar on each page
			 	 this.showAllLinkText = allLink; //text for the show all 'tab'
			 	 this.filterHeaderText = filterText; //text for the filter header
			 	 this.tagCloudCount = tagCount; //number of tags in the tag cloud
			 	 this.postId = postId;
			 	 
			 	 this.filterObject = false; //change this to filterSlate
			 	 
			 	 /*----Input ID's - these id's of input elements have changing content or gets read by the javascript ---- */
				 this.txtManualFilter = 'txtmanualFilter'; //textbox id to add a manual tag filter
				 this.btnClearSearch = 'btnClearSearch'; //link id that clears the search
				 this.txtSearchTagCloud = 'txtSearch';  //textbox id that searches tags in the tag cloud
	 	 
	 	 		  /*----Divs - these divs have changing content or gets read by the javascript ---- */
	 	 		 this.divSidebarTitle = 'sidebarTitle';  //Header ID for the sidebarTitle
			 	 this.divShowAllLink = 'showAllLink'; //Div ID that holds the "show all" tab
			 	 this.divFilterHeader = "filterheader"; //Header ID that holds the filter header in the sidebar
			 	 this.divContent = "sidebar_content"; //Div ID that contains the content of the sidebar
			 	 this.divUlFilters = 'ulfilters'; //Div id that contains the rendered list of filters
			 	 this.divSearchResults = 'sidebarSearchResults'; //Div that contains the tag search results
			 	 this.divAllTags = 'allTags'; //Div that contains all the tags in the tag cloud
			 	 this.divSearchTagCloudResults = 'tagSearchResults'; //Div Id that contains the results for the tag search in the tag cloud.
				 this.divTagCloud = 'topTags'; //Div id that contains the tags in the tag cloud
				 this.divAddFilter = 'addFilter'; //Div that contains the manual add tag textbox
			 	 this.closeLightBoxDiv = 'closeBox'; //Div that closes that lightbox
			 	 this.lightBoxDiv = 'lightbox'; //Div that contains the lightbox
			 	 
			 	 var currentFilterArr = new Array();
				 
				 
				 
				 /***************Get Sidebar Items************** */
	 	 		this.assignTitle = function(){
	 	 			$(this.divSidebarTitle).innerHTML =this.sidebarTitle;
	 	 			
	 	 		};
				/***************Get Sidebar Items************** */
	 	 		this.getSidebarItems = function(page){
	 	 			displayIndicator(true);
					
					var sidebarPage = 1 //pagination
					if (page != undefined){
						sidebarPage = page;
					}
					
					if(currentFilterArr.length == 0){
	 					$(this.divFilterHeader).style.display = 'none';
	 				}else{
	 					$(this.divFilterHeader).innerHTML = this.filterHeaderText+':';
	 					$(this.divFilterHeader).style.display = 'inline';
	 				}
	 				
		 			var currentFilter = new Array(); //array to hold all items that are checked
		 			for(i=0; i<currentFilterArr.length; i++){
		 				if(currentFilterArr[i].removeable){
		 	 				if(currentFilterArr[i].status == "checked"){
		 	 					currentFilter.push(currentFilterArr[i].tagRefId);
		 	 				}
		 				}else{ //if filtering by object
		 	 				if(currentFilterArr[i].status == "checked"){
		 	 					this.filterObject = true;
		 	 				}else{
		 	 					this.filterObject = false;	
		 	 				}
		 				}
		 			}
		
		 			
		 			//show all items link and assign show all link text to div
		 				if(currentFilter.length > 0 || this.filterObject == true){
		 					$(this.divShowAllLink).style.display = 'inline';
		 					$(this.divShowAllLink).innerHTML = '<a href="javascript:sideBar.clearFilter();">'+ this.showAllLinkText +'</a>';
		 				}else{
		 					$(this.divShowAllLink).style.display = 'none';
		 				}
		 				
		 			//show title
		 			/*
		 				if(currentFilterArr.length == 0){
		 					$(this.divFilterHeader).style.display = 'none';
		 				}else{
		 					$(this.divFilterHeader).innerHTML = this.filterHeaderText+':';
		 					$(this.divFilterHeader).style.display = 'inline';
		 				}*/
		 			
						var currentFilterString = currentFilter.toString();
						if(this.filterObject){ //check if filtering by ioid or not
							var ioid = this.objectId;
						}else{
							var ioid = "";
						}
						this.getAbstractItems(currentFilterString, sidebarPage);
						
			 	};
			 	
			 	
			 	
				/***************Render Item in Lightbox************** */
					this.renderItemDetails = function(data){
						var browser = navigator.appName;
						var currentItem = data.content;
						
							os = "";
							os += '<div id="'+ sideBar.closeLightBoxDiv +'" style="text-align: right;"><a href="javascript: lightboxDisplay();"><img src="/images/closelabel.gif" border="0"></a></div>';
							os += '<h4>'+ sideBar.lightBoxTitle +'</h4><br>';
							if(browser=="Microsoft Internet Explorer"){
								os+='<div style="margin:2%;"><div style="width:390px; height:215px; overflow:auto;" name="viewSidebarConcern" id="viewSidebarConcern">'+currentItem;
							}else{
								os += '<div style=" margin:2%; overflow:hidden;"><div style="width:385px; max-height:215px; overflow:auto;" name="viewSidebarConcern" id="viewSidebarConcern">' + currentItem;
							}
							os +='<br />';
							for(i=0; i < data.tags.length; i++){
								os +='<span class="tags"><a href="javascript:lightboxDisplay(); sideBar.changeCurrentFilter('+data.tags[i].id+ ');">'+ data.tags[i].tag.name + '</a></span> \n';
							}
							os+='</div></div>';
							lightboxDisplay(true);
							$('lightbox').innerHTML = os;	
						
					}


			 	/***************Render Sidebar Filters.************** */
			 	this.renderFilters = function(){
				    //render sidebar filters
 	 				var filters = "";
 	 				filters += '<ul class="filter">';
				
	 	 			for(i=0; i<currentFilterArr.length; i++){
	 	 				if(currentFilterArr[i].removeable){
		 	 				filters += '<li><input type="checkbox" id="filtercheck'+i+'" onclick="sideBar.checkFilter('+i+')"  '+ currentFilterArr[i].status +' /> '+ currentFilterArr[i].tagName;
		 	 				filters += '&nbsp;<a href="javascript: sideBar.removeUlFilter('+i+');"><img src="/images/trash.gif" alt="remove filter" border="0" /></a>';
		 	 				filters +='<ul class="filter">';
	 	 				}else if(currentFilterArr[i].removeable != true && sideBar.postId != ""){ //if PID
	 	 					filters += '<li><input type="checkbox" id="filtercheck'+i+'" onclick="sideBar.checkIOIDFilter('+i+')"  '+ currentFilterArr[i].status +' />Current Post Filter';
		 	 				filters +='<ul class="filter">';
	 	 				}else{ //if ioid
	 	 					filters += '<li><input type="checkbox" id="filtercheck'+i+'" onclick="sideBar.checkIOIDFilter('+i+')"  '+ currentFilterArr[i].status +' />' + currentFilterArr[i].tagName;
		 	 				filters +='<ul class="filter">';
	 	 				}
	 	 			}
	 	 			filters += '</ul>';
	 	 			$(sideBar.divUlFilters).innerHTML = filters;			
				}
				/***************Changes the checked status of a currentFilterArr object.  This is triggered when a rendered filter checkbox is clicked on. Gets new sidebar items after.************** */
			    this.checkFilter = function(index){
					if(currentFilterArr[index].status == "unchecked"){
						currentFilterArr[index].status = "checked";
					}else{
						currentFilterArr[index].status = "unchecked";
					}
					this.getSidebarItems();
				};
				
				/***************Checks the checkbox of an IO Filter************* */
				this.checkIOIDFilter = function(index){
					this.checkFilter(index);
					this.filterObject = true;
				};
				
				/***************Get Tag by TagID - From Separate File************* */
				this.addFilter = function(tagRefId){
						var tagRef = tagRefId.toString();
						this.convertAbstractFilter(tagRef);
				};
				/***************Add FilterInstance to Filter************* */
				this.addFilterToArr = function(tagRefId, tagName){
					var filterInstance = new Filter(tagRefId, "checked", true, tagName);
					currentFilterArr.push(filterInstance);
					this.closeSearchResults();
					shrinkTagSelector();
					this.getSidebarItems();	
				}
				/***************Add FilterInstance to Filter************* */
				this.addObjectFilter = function(filterInstance){
					currentFilterArr.push(filterInstance)
					this.getSidebarItems();	
				};
				
				/***************Unchecks Filter from current filter array and get new sidebar items************* */
				this.removeFilter = function(){
						currentFilterArr.pop();
						this.getSidebarItems();
				};
				
				/***************Removes Filter from current filter array, removes rendered UL filter, and get new sidebar items************* */
				this.removeUlFilter = function(index){
						currentFilterArr.splice(index, 1);
						this.getSidebarItems();
				};
				
				/***************Changes current filter unless filtering by IO************* */
				this.changeCurrentFilter = function(tagRefId){
						if (!this.filterObject || currentFilterArr.length > 1) {//if filtering by ioid, add a new filter, not change it
							currentFilterArr.pop()
						};
						this.addFilter(tagRefId);
				};
				
				/***************Show All SideBar Items unfiltered************* */
				this.clearFilter = function(){
					for(i=0; i<currentFilterArr.length; i++){
						currentFilterArr[i].status = "unchecked";	
					}
					this.getSidebarItems()	;
					shrinkTagSelector();
				};
				
				/***************Clear Search Text Box************ */
				this.clearSearch = function(){
					$(this.txtManualFilter).value = "";	
					$(this.txtManualFilter).focus();
					$(this.btnClearSearch).style.display = 'none';
				};
					
				/***************Close Search Results Pull Down************* */
				this.closeSearchResults = function(){
					 new Effect.Fade(this.divSearchResults, {duration: 0.3});
					 new Effect.Fade(this.divAddFilter, {duration: 0.3});
				};
				
				/***************Validations prior to sidebar tag search action************* */
				this.sidebarTagSearch = function(theTag,key){
					if (theTag != ""){
						$(this.btnClearSearch).style.display = 'inline';
					}else{
						$(this.btnClearSearch).style.display = 'none';

					}
					//hack to disable backspace on input box when length < 1 - "19 tags hack"
					if (key.keyCode == 8 && theTag.length < 1){
						return false;	
					}
					
					//if the query is greater than 2 chars do the action if not keep it hidden
					if($(this.txtManualFilter).value.length > 2){
						this.sidebarSearchTagsAction(theTag);
					}else{
						$(this.divSearchResults).style.display == 'none'
					}
				};

				/***************Render the tag search results************* */
				this.renderSearchTagResults = function(data){
					//show results if hidden
					if($(sideBar.divSearchResults).style.display == 'none'){
						new Effect.Appear(sideBar.divSearchResults, {duration: 0.5});		
					}		
					
					$(sideBar.divSearchResults).innerHTML = $(sideBar.divSearchResults).innerHTML = data.html;
					if (data.count == 0){
						$(sideBar.divSearchResults).innerHTML = '<span class="closeBox"><a href="javascript:Effect.Fade('+sideBar.divSearchResults+', {duration: 0.5}); void(0);">Close</a></span><p>No keyphrase matches found.</p> ';
					}
				};
				/***************May be not neccessary************* 
				this.getSidebarItemsByTag = function(tagRefId){
						addFilter(tagRefId);	
						$('addFilter').style.display = 'none';
						if($('sidebarSearchResults').style.display != 'none'){
							closeSearchResults();
						}
						clearSearch();
						shrinkTagSelector();
				};*/
		
				/***************Get Tag Cloud when "Browsing all tags"************* */
				this.getTagCloud = function(goToPage){
					if(goToPage == undefined){
						var page = 1	
					}else{
						var page = goToPage;	
					}
					this.getAbStractTagCloud(page);
				};
	
				/***************Search Tags within the Tag Cloud************* */
				this.tagSearch = function(theTag){
					if ($(sideBar.txtSearchTagCloud).value == ""){
						$(sideBar.divTagCloud).innerHTML = "";
						$(sideBar.divSearchTagCloudResults).innerHTML = '<span class="highlight">Please type in your query or <a href="javascript:sideBar.getTagCloud();">clear query</a>&nbsp;to view top tags again.</span>';
					}else{
						this.getAbstractTagCloudResults(theTag);
					}
				};
				
				this.renderTagCloudSearchResults = function(data){
						$(sideBar.divSearchTagCloudResults).innerHTML = '<span class="highlight">' + data.count +' tags match your query&nbsp;&nbsp;(<a href="javascript:sideBar.getTagCloud();">clear query</a>)</span>';
						$(sideBar.divTagCloud).innerHTML = data.html;
				}
				

				
		 };//end SideBar Function this.getSidebarItems = function(page){
	
	
		///////////////////////////////////////// END SIDEBAR //////////////////////////////////////