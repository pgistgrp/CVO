<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
<!--####
	Project: Let's Improve Transportation!
	Page: User Home
	Description: This page serves as a single instance of a user home portal.  This will have
					links to all available components along with a user's discussion and tags.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Zhong Wang, John Le
#### -->  
	<head> 
	<title>Let's Improve Transportation - Home</title>
	<!-- Site Wide CSS -->
	<style type="text/css" media="screen">
		@import "styles/lit.css";
		@import "styles/user-home.css";
		@import "styles/workflow.css";
	</style>
	<!-- End Site Wide CSS -->
	<!-- Site Wide JS -->
	<script type="text/javascript" src="scripts/tinymce/jscripts/tiny_mce/tiny_mce.js"></script>
	<script src="scripts/prototype.js" type="text/javascript"></script>
	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<script src="scripts/search.js" type="text/javascript"></script>
	<script src="scripts/util.js" type="text/javascript"></script>
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/util.js'></script>
	<script type='text/javascript' src='/dwr/interface/WorkflowAgent.js'></script>
	<script type='text/javascript' src='/scripts/workflow.js'></script>
	<script>
	  var workflow = new Workflow('workflow-panel');
	</script>
	<script type="text/javascript">
		var wfId = "${param.workflowId}";
		
		if(!wfId){location.href="main.do"}
		function getAnnouncements(){
			Util.loading(true, "Loading Announcements");
			SystemAgent.getAnnouncements({workflowId:wfId}, {
				callback:function(data){
					if (data.successful){
						$('announcements').innerHTML = data.html;
					}else{
						alert(data.reason);
					}
					Util.loading(false);
				},
				errorHandler:function(errorString, exception){ 
				alert("SystemAgent.getAnnouncements( error:" + errorString + exception);
				}
			});
		}
		
		function addAnnouncement(){
			var email = $F("email");
			if(email == "" || email == null) {
				email = "false";
			} else {
				email = email.toString();
			}
			Util.loading(true, "Saving Annoucement");
			var message = tinyMCE.getContent();
			SystemAgent.addAnnouncement({workflowId:wfId,message:message,email:email}, {
				callback:function(data){
					if (data.successful){
					    new Effect.ScrollTo("right-col");
						getAnnouncements();
					}else{
						alert(data.reason);
					}
					Util.loading(false);
				},
				errorHandler:function(errorString, exception){ 
				alert("SystemAgent.getAnnouncements( error:" + errorString + exception);
				}
			});
		}
		
		function deleteAnnouncement(id){
			Util.loading(true, "Deleting Annoucement");
			SystemAgent.deleteAnnouncement({id:id}, {
				callback:function(data){
					if (data.successful){
						new Effect.DropOut('announcement' + id)
					}else{
						alert(data.reason);
					}
					Util.loading(false);
				},
				errorHandler:function(errorString, exception){ 
				alert("SystemAgent.deleteAnnouncement( error:" + errorString + exception);
				}
			});
		}
		
		function addAnnouncementPrep(){
			Element.toggle($('announce-editor'));
			showPubBtn();
			// Remove the existing control before adding a new one (kludgey!)
			tinyMCE.execCommand('mceRemoveControl',true,'modAnnounce');
			// Have to add the control before setting content
			tinyMCE.execCommand('mceAddControl',true,'modAnnounce');
			tinyMCE.setContent('');
		}
		
		/* Have to use mceAddControl to instantiate editor onclick or else
		   tinyMCE can't find it */
		function editAnnouncementPrep(id){
		    new Effect.ScrollTo("btnEdit");
			$('announce-editor').style.display="";
			tinyMCE.execCommand('mceRemoveControl',true,'modAnnounce');
			tinyMCE.execCommand('mceAddControl',true,'modAnnounce');
			var old = $('message' + id).innerHTML;
			tinyMCE.setContent(old);
			$('editBtn').name = id;
			showEditBtn();
		}
		
		function editAnnouncement(id){
			Util.loading(true, "Saving Annoucement");
			var message="default text";
			var message = tinyMCE.getContent();
			SystemAgent.editAnnouncement({id:id, message:message}, {
				callback:function(data){
					if (data.successful){
					    new Effect.ScrollTo("right-col");
						tinyMCE.setContent("");
						getAnnouncements();
						setTimeout(function() {new Effect.Highlight("announcement"+id,{duration:4.0});}, 100);
					}else{
						alert(data.reason);
					}
					Util.loading(false);
				},
				errorHandler:function(errorString, exception){ 
				alert("SystemAgent.deleteAnnouncement( error:" + errorString + exception);
				}
			});
		}	
		
		function showPubBtn(){
			$('pubBtn').style.display="";
			$('editBtn').style.display="none";
		}
		
		function showEditBtn(){
			$('editBtn').style.display="";
			$('pubBtn').style.display="none";
		}
		
   function initialise() {
   		var editor = tinyMCE.selectedInstance.editorId;
      tinyMCE.execCommand('mceFocus', false, editor);
   }
   
		tinyMCE.init({
		theme : "advanced",
		theme_advanced_buttons1 : "bold, italic, bullist, numlist,undo, redo,link",
		theme_advanced_buttons2 : "",
		theme_advanced_buttons3 : "",
		content_css : "/scripts/tinymce/jscripts/tiny_mce/themes/simple/css/bigmce.css",
		extended_valid_elements : "blockquote[style='']",
    setupcontent_callback : "initialise",
		//mode : "textareas",
		height: "100",
		width: "430"
		});
	
	//tinyMCE.execCommand('mceFocus',false,'content');
	
	</script>
	<event:pageunload />
	</head>
	<body onload="workflow.getWorkflow(${param.workflowId});">
        <!-- Start Global Headers  -->
        <wf:nav />
        <div id="headerMenu"></div>
        <!-- End Global Headers -->
        
	<!-- End header menu -->
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
		<h2 class="headerColor">Welcome, ${baseuser.loginname}</h2>
    <pg:hide roles="guest">
		  <pg:url page="publicprofile.do" params="user=${baseuser.loginname}">View</pg:url> / <pg:url page="/usercp.do">Edit your profile</pg:url>
    </pg:hide>
    <h3 style="display:inline;margin:0px;padding:0px;margin-left:20px">Your participant ID is <strong>${webq}</strong></h3>
		<div id="left-col">
			<h3 class="headerColor">Agenda</h3>
			<div class="box12 clearfix"><h4 style="font-weight:normal">Current steps are highlighted</h4>
				<div id="workflow-panel"><img src="/images/indicator_arrows.gif" alt="Please wait..."/> Loading overview...</div>
			</div>
		</div>
		<div id="right-col">
			<h3 class="headerColor">Moderator announcements</h3>
			<div id="mod-announcements" class="box9">
				<div id="announcements">
					<img src="/images/indicator_arrows.gif" alt="Please wait..."/> Loading Moderator Announcements...
					<!--load via DWR -->
				</div>
			</div>
			<pg:show roles="moderator">
				<input type="button" id="btnEdit" class="padding5" value="Add Announcement" 
				onclick="addAnnouncementPrep()" />
				<div id="announce-editor" style="display:none">
					<textarea name="content" id="modAnnounce"></textarea>
					<br/>
					<div class="floatRight"><input id="email" type="checkbox" value="true" /><label for="email">Email Participants</div>
					<input type="button" id="pubBtn" 
						onclick="addAnnouncement();Element.toggle($('announce-editor'));" 
						class="padding5" value="Publish" />
					<input type="button" id="editBtn" 
						onclick="editAnnouncement(name);Element.toggle($('announce-editor'));" 
						class="padding5" style="display:none" value="Save" />
					<a href="javascript:Element.toggle('announce-editor');new Effect.ScrollTo('right-col');void(0);">Cancel</a> </div>
			</pg:show>
		</div>
		<div class="clearBoth"></div>
	</div>
	<!-- end container -->
	<!-- start feedback form -->
	<pg:feedback id="feedbackDiv" action="cctView.do"/>
	<!-- end feedback form -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
    <!-- Start Global Headers  -->
<div id="headerMenu"></div>
    <!-- End Global Headers -->
	<!-- Begin footer -->
	<div id="footer"> 
		<jsp:include page="/footer.jsp" />
	</div>
	<!-- End footer -->
	<script type="text/javascript" charset="utf-8"> 
		getAnnouncements();
	</script>
	</body>
</html:html>
