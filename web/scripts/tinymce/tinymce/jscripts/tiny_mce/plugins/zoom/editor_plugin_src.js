/**
 * $RCSfile$
 * $Revision$
 * $Date$
 *
 * @author Moxiecode
 * @copyright Copyright � 2004-2006, Moxiecode Systems AB, All rights reserved.
 */

var TinyMCE_ZoomPlugin = {
	getInfo : function() {
		return {
			longname : 'Zoom',
			author : 'Moxiecode Systems',
			authorurl : 'http://tinymce.moxiecode.com',
			infourl : 'http://tinymce.moxiecode.com/tinymce/docs/plugin_zoom.html',
			version : tinyMCE.majorVersion + "." + tinyMCE.minorVersion
		};
	},

	/**
	 * Returns the HTML contents of the zoom control.
	 */
	getControlHTML : function(control_name) {
		if (!tinyMCE.isMSIE || tinyMCE.isMSIE5_0 || tinyMCE.isOpera)
			return "";

		switch (control_name) {
			case "zoom":
				return '<select id="{$editor_id}_zoomSelect" name="{$editor_id}_zoomSelect" onfocus="tinyMCE.addSelectAccessibility(event, this, window);" onchange="tinyMCE.execInstanceCommand(\'{$editor_id}\',\'mceZoom\',false,this.options[this.selectedIndex].value);" class="mceSelectList">' + 
						'<option value="100%">+ 100%</option>' + 
						'<option value="150%">+ 150%</option>' + 
						'<option value="200%">+ 200%</option>' + 
						'<option value="250%">+ 250%</option>' + 
						'</select>';
		}

		return "";
	},

	/**
	 * Executes the mceZoom command.
	 */
	execCommand : function(editor_id, element, command, user_interface, value) {
		// Handle commands
		switch (command) {
			case "mceZoom":
				tinyMCE.getInstanceById(editor_id).contentDocument.body.style.zoom = value;
				tinyMCE.getInstanceById(editor_id).contentDocument.body.style.mozZoom = value;
				return true;
		}

		// Pass to next handler in chain
		return false;
	}
};

tinyMCE.addPlugin("zoom", TinyMCE_ZoomPlugin);
