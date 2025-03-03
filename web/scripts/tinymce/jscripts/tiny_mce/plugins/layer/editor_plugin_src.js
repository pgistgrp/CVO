/**
 * $RCSfile$
 * $Revision$
 * $Date$
 *
 * @author Moxiecode
 * @copyright Copyright � 2004-2006, Moxiecode Systems AB, All rights reserved.
 */

/* Import plugin specific language pack */
tinyMCE.importPluginLanguagePack('layer', 'en');

var TinyMCE_LayerPlugin = {
	getInfo : function() {
		return {
			longname : 'Layer',
			author : 'Moxiecode Systems',
			authorurl : 'http://tinymce.moxiecode.com',
			infourl : 'http://tinymce.moxiecode.com/tinymce/docs/plugin_layer.html',
			version : tinyMCE.majorVersion + "." + tinyMCE.minorVersion
		};
	},

	initInstance : function(inst) {
		if (tinyMCE.isMSIE && !tinyMCE.isOpera)
			inst.getDoc().execCommand('2D-Position');
	},

	handleEvent : function(e) {
		var inst = tinyMCE.selectedInstance, self = TinyMCE_LayerPlugin;
		var w = inst.getWin(), le = inst._lastStyleElm, e;

		if (tinyMCE.isGecko) {
			e = self._getParentLayer(inst.getFocusElement());

			if (e) {
				if (!inst._lastStyleElm) {
					e.style.overflow = 'auto';
					inst._lastStyleElm = e;
				}
			} else if (le) {
				le = inst._lastStyleElm;
				le.style.width = le.scrollWidth + 'px';
				le.style.height = le.scrollHeight + 'px';
				le.style.overflow = '';
				inst._lastStyleElm = null;
			}
		}

		return true;
	},

	handleVisualAid : function(el, deep, state, inst) {
		var nl = inst.getDoc().getElementsByTagName("div"), i;

		for (i=0; i<nl.length; i++) {
			if (new RegExp('absolute|relative|static', 'gi').test(nl[i].style.position)) {
				if (state)
					tinyMCE.addCSSClass(nl[i], 'mceVisualAid');
				else
					tinyMCE.removeCSSClass(nl[i], 'mceVisualAid');					
			}
		}
	},

	getControlHTML : function(cn) {
		switch (cn) {
			case "moveforward":
				return tinyMCE.getButtonHTML(cn, 'lang_layer_forward_desc', '{$pluginurl}/images/forward.gif', 'mceMoveForward', true);

			case "movebackward":
				return tinyMCE.getButtonHTML(cn, 'lang_layer_backward_desc', '{$pluginurl}/images/backward.gif', 'mceMoveBackward', true);

			case "absolute":
				return tinyMCE.getButtonHTML(cn, 'lang_layer_absolute_desc', '{$pluginurl}/images/absolute.gif', 'mceMakeAbsolute', true);

			case "insertlayer":
				return tinyMCE.getButtonHTML(cn, 'lang_layer_insertlayer_desc', '{$pluginurl}/images/insert_layer.gif', 'mceInsertLayer', true);
		}

		return "";
	},

	execCommand : function(editor_id, element, command, user_interface, value) {
		var self = TinyMCE_LayerPlugin;

		// Handle commands
		switch (command) {
			case "mceInsertLayer":
				self._insertLayer();
				return true;

			case "mceMoveForward":
				self._move(1);
				return true;

			case "mceMoveBackward":
				self._move(-1);
				return true;

			case "mceMakeAbsolute":
				self._toggleAbsolute();
				return true;
		}

		// Pass to next handler in chain
		return false;
	},

	handleNodeChange : function(editor_id, node, undo_index, undo_levels, visual_aid, any_selection) {
		var inst = tinyMCE.getInstanceById(editor_id), self = TinyMCE_LayerPlugin;
		var le = self._getParentLayer(inst.getFocusElement());
		var p = tinyMCE.getParentElement(inst.getFocusElement(), 'div,p,img');

		tinyMCE.switchClass(editor_id + '_absolute', 'mceButtonDisabled');
		tinyMCE.switchClass(editor_id + '_moveforward', 'mceButtonDisabled');
		tinyMCE.switchClass(editor_id + '_movebackward', 'mceButtonDisabled');

		if (p)
			tinyMCE.switchClass(editor_id + '_absolute', 'mceButtonNormal');

		if (le && le.style.position.toLowerCase() == "absolute") {
			tinyMCE.switchClass(editor_id + '_absolute', 'mceButtonSelected');
			tinyMCE.switchClass(editor_id + '_moveforward', 'mceButtonNormal');
			tinyMCE.switchClass(editor_id + '_movebackward', 'mceButtonNormal');
		}
	},

	// Private plugin specific methods

	_move : function(d) {
		var inst = tinyMCE.selectedInstance, self = TinyMCE_LayerPlugin, i, z = new Array();
		var le = self._getParentLayer(inst.getFocusElement()), ci = -1, fi = -1;
		var nl = tinyMCE.selectNodes(inst.getBody(), function(n) {
			return n.nodeType == 1 && new RegExp('absolute|relative|static', 'gi').test(n.style.position);
		});

		// Find z-indexes
		for (i=0; i<nl.length; i++) {
			z[i] = nl[i].style.zIndex ? parseInt(nl[i].style.zIndex) : 0;

			if (ci < 0 && nl[i] == le)
				ci = i;
		}

		if (d < 0) {
			// Move back

			// Try find a lower one
			for (i=0; i<z.length; i++) {
				if (z[i] < z[ci]) {
					fi = i;
					break;
				}
			}

			if (fi > -1) {
				nl[ci].style.zIndex = z[fi];
				nl[fi].style.zIndex = z[ci];
			} else {
				if (z[ci] > 0)
					nl[ci].style.zIndex = z[ci] - 1;
			}
		} else {
			// Move forward

			// Try find a higher one
			for (i=0; i<z.length; i++) {
				if (z[i] > z[ci]) {
					fi = i;
					break;
				}
			}

			if (fi > -1) {
				nl[ci].style.zIndex = z[fi];
				nl[fi].style.zIndex = z[ci];
			} else
				nl[ci].style.zIndex = z[ci] + 1;
		}

		inst.repaint();
	},

	_getParentLayer : function(n) {
		return tinyMCE.getParentNode(n, function(n) {
			return n.nodeType == 1 && new RegExp('absolute|relative|static', 'gi').test(n.style.position);
		});
	},

	_insertLayer : function() {
		var inst = tinyMCE.selectedInstance;
		var e = tinyMCE.getParentElement(inst.getFocusElement());
		var p = tinyMCE.getAbsPosition(e);
		var d = inst.getDoc();
		var ne = d.createElement('div');
		var h = inst.selection.getSelectedHTML();

		// Move div
		ne.style.position = 'absolute';
		ne.style.left = p.absLeft + 'px';
		ne.style.top = (p.absTop > 20 ? p.absTop : 20) + 'px';
		ne.style.width = '100px';
		ne.style.height = '100px';
		ne.className = 'mceVisualAid';

		if (!h)
			h = tinyMCE.getLang('lang_layer_content');

		ne.innerHTML = h;

		// Add it
		d.body.appendChild(ne);
	},

	_toggleAbsolute : function() {
		var inst = tinyMCE.selectedInstance, self = TinyMCE_LayerPlugin;
		var le = self._getParentLayer(inst.getFocusElement());

		if (le == null)
			le = tinyMCE.getParentElement(inst.getFocusElement(), 'div,p,img');

		if (le) {
			if (le.style.position.toLowerCase() == "absolute") {
				le.style.position = "";
				le.style.left = "";
				le.style.top = "";
			} else {
				le.style.position = "absolute";

				if (le.style.left == "")
					le.style.left = 20 + 'px';

				if (le.style.top == "")
					le.style.top = 20 + 'px';

				if (le.style.width == "")
					le.style.width = le.width ? (le.width + 'px') : '100px';

				if (le.style.height == "")
					le.style.height = le.height ? (le.height + 'px') : '100px';

				tinyMCE.handleVisualAid(inst.getBody(), true, inst.visualAid, inst);
			}

			inst.repaint();
			tinyMCE.triggerNodeChange();
		}
	}
};

tinyMCE.addPlugin("layer", TinyMCE_LayerPlugin);
