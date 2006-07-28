/*
KOIVI TTW WYSIWYG Editor Copyright (C) 2005 Justin Koivisto
Version 3.2.4
Last Modified: 4/3/2006

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library; if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

    Full license agreement notice can be found in the LICENSE file contained
    within this distribution package.

    Justin Koivisto
    justin.koivisto@gmail.com
    http://koivi.com
*/

/**
*   WYSIWYG_Editor
*
*   Class constructor. Configures and displays the editor object according to values passed
*   This is an implementation of OO-Javascript based on my previous editor.
*
*   @param  instance_name   string  the name of the variable you assigned to this instance ( example: myEdt = new WYSIWYG_Editor('myEdt'); )
*                                   also used as the basis for the name and id attributes for this editor instance in the HTML (hidden input and iframe)
*   @param  content         string  a string of the content to display in the editor.
*   @param  path            string  the URI path to this directory to use for editor components (pallete, iamges, etc.)
*   @param  fwidth          int     the width in pixels of the editor interface on the screen
*   @param  fheight         int     the height in pixels of the editor interface on the screen
*   @param  styleHref       string  the URI of the stylesheet to use for the editor's content window
*   @param  spellCheckPath  string  the URI of the spellerpages install
**/
function WYSIWYG_Editor(instance_name, content, path, fwidth, fheight, styleHref, spellCheckPath){
    // for each of the passed parameters, we need to be sure they are defined, or we will use a default value

    // the name used to create the object - used to define the name of the field that contains the HTML on submission
    if(typeof(instance_name)=='undefined'){
        alert("ERROR: No instance name was passed for the editor.");
        return false;
    }else{
        this.instance_name=instance_name;
    }

    // the initial HTML source content for the editor
    if(typeof(content)=='undefined'){
        this.content='';
    }else{
        this.content=content;
    }

    // define the path to use for the editor components like images
    if(typeof(path)=='undefined'){
        this.wysiwyg_path='.'; // default value
    }else{
        path.replace(/[\/\\]$/,''); // remove trailing slashes
        this.wysiwyg_path=path;
    }

    // define the pixel dimensions of the editor
    if(typeof(fwidth)=='number' && Math.round(fwidth) > 50){
        this.frame_width=Math.round(fwidth); // default value
    }else{
        this.frame_width=548; // default width
    }
    if(typeof(fheight)=='number' && Math.round(fheight) > 50){
        this.frame_height=Math.round(fheight);
    }else{
        this.frame_height=250; // default height
    }

    // define the stylesheet to use for the editor components like images
    if(typeof(styleHref)=='undefined'){
        this.stylesheet=''; // default value
    }else{
        this.stylesheet=styleHref;
    }

    if(typeof(spellCheckPath)=='undefined'){
        this.spell_path=''; // default value
    }else{
        // show spell check button requires Speller Pages (http://spellerpages.sourceforge.net/)
        this.spell_path=spellCheckPath;
    }

    // properties that depended on the validated values above
    this.wysiwyg_content=this.instance_name+'_WYSIWYG_Editor';  // the editor IFRMAE element id
    this.wysiwyg_hidden=this.instance_name+'_content';          // the editor's hidden field to store the HTML in for the post
    this.wysiwyg_speller=this.instance_name+'_speller';         // the editor's hidden textarea to store the HTML in for the spellchecker
    this.ta_rows=Math.round(this.frame_height/15);              // number of rows for textarea for unsupported browsers
    this.ta_cols=Math.round(this.frame_width/8);                // number of cols for textarea for unsupported browsers

    // other property defaults
    this.viewMode=1;                                        // by default, set to design view
    this._X = this._Y = 0;                                  // these are used to determine mouse position when clicked

}

/**
*   WYSIWYG_Editor::prepareSubmit
*
*   Use this in the onSubmit event for the form that the editor is displayed inside.
*   Puts the HTML content into a hidden form field for the submission
**/
WYSIWYG_Editor.prototype.prepareSubmit = function (){
    if(this.viewMode == 2){
        // be sure this is in design view before submission
        this.toggleMode();
    }
    var htmlCode=document.getElementById(this.wysiwyg_content).contentWindow.document.body.innerHTML;
    document.getElementById(this.wysiwyg_hidden).value=htmlCode;
    return true;
}

/**
*   WYSIWYG_Editor::display
*
*   Display the editor interface for the user
**/
WYSIWYG_Editor.prototype.display = function (){
    if(this.isSupported()){
        this._display_editor();
        this._load_content();
        var thedoc = document.getElementById(this.wysiwyg_content).contentWindow.document;
        thedoc.designMode='On';
        // MSIE has caching problems...
        // http://technet2.microsoft.com/WindowsServer/en/Library/8e06b837-0027-4f47-95d6-0a60579904bc1033.mspx
        thedoc = document.getElementById(this.wysiwyg_content).contentWindow.document;
        thedoc.open();
        thedoc.write('<html><head>');
        if(this.stylesheet){
            // must be done after the document has been opened
            thedoc.write('<style type="text/css">@import url('+this.stylesheet+');</style>');
        }
        thedoc.write('</head><body>');
        thedoc.write(this.content);
        thedoc.write('</body></html>');
        thedoc.close();
    }else{
        this._display_textarea();
        this._load_content();
    }
}

/**
*   WYSIWYG_Editor::_display_textarea
*
*   Used to display a substitute for the wysiwyg editor HTML interface to non-supported browsers
**/
WYSIWYG_Editor.prototype._display_textarea = function (){
    document.write('<p style="background-color: yellow; color: red; padding: 3px;"><b>WARNING:</b> Your browser does not support the WYSIWYG_Editor class. The script you are posting to may expect HTML code.</p>');
    document.write('<textarea name="'+this.wysiwyg_hidden+'" id="'+this.wysiwyg_hidden+'" rows="'+this.ta_rows+'" cols="'+this.ta_cols+'"></textarea><br>');
}

/**
*   WYSIWYG_Editor::_display_editor
*
*   Used to display the actual wysiwyg editor HTML interface to supported browsers
**/
WYSIWYG_Editor.prototype._display_editor = function (){
    document.write('   <textarea name="'+this.wysiwyg_hidden+'" id="'+this.wysiwyg_hidden+'" style="visibility:hidden;display:none;"></textarea>');
    document.write('      <table width="100%" border="0" cellspacing="0" cellpadding="0">');
    document.write('       <tr>');
    document.write('        <td valign="top" >');
    document.write('         <div id="'+this.instance_name+'_toolbars">');

        document.write('          <img alt="Bold" title="Bold" class="butClass" src="'+this.wysiwyg_path+'/bold.png" onClick="'+this.instance_name+'.doTextFormat(\'bold\',\'\')">');
        document.write('          <img alt="Italic" title="Italic" class="butClass" src="'+this.wysiwyg_path+'/italic.png" onClick="'+this.instance_name+'.doTextFormat(\'italic\',\'\')">');
        document.write('          <img alt="Underline" title="Underline" class="butClass" src="'+this.wysiwyg_path+'/underline.png" onClick="'+this.instance_name+'.doTextFormat(\'underline\',\'\')">');

        document.write('         <img alt="|" src="'+this.wysiwyg_path+'/images/separator.png">');

        document.write('          <img alt="Hyperlink" title="Hyperlink" class="butClass" src="'+this.wysiwyg_path+'/link.png" onClick="'+this.instance_name+'.doTextFormat(\'createlink\',\'\')">');
        document.write('          <img alt="Remove Link" title="Remove Link" class="butClass" src="'+this.wysiwyg_path+'/unlink.png" onClick="'+this.instance_name+'.doTextFormat(\'unlink\',\'\')">');
        document.write('         <img alt="|" src="'+this.wysiwyg_path+'/images/separator.png">');
        document.write('          <img alt="Cut" title="Cut" class="butClass" src="'+this.wysiwyg_path+'/cut.gif" onClick="'+this.instance_name+'.doTextFormat(\'cut\',\'\')">');
        document.write('          <img alt="Copy" title="Copy" class="butClass" src="'+this.wysiwyg_path+'/copy.gif" onClick="'+this.instance_name+'.doTextFormat(\'copy\',\'\')">');
        document.write('          <img alt="Paste" title="Paste" class="butClass" src="'+this.wysiwyg_path+'/paste.gif" onClick="'+this.instance_name+'.doTextFormat(\'paste\',\'\')">');
        document.write('         <img alt="|" src="'+this.wysiwyg_path+'/images/separator.png">');
        document.write('          <img alt="Undo" title="Undo" class="butClass" src="'+this.wysiwyg_path+'/undo.gif" onClick="'+this.instance_name+'.doTextFormat(\'undo\',\'\')">');
        document.write('          <img alt="Redo" title="Redo" class="butClass" src="'+this.wysiwyg_path+'/redo.gif" onClick="'+this.instance_name+'.doTextFormat(\'redo\',\'\')">');

    document.write('         </div>');
    document.write('        </td>');
    document.write('       </tr>');
    document.write('      </table>');

    document.write('      <iframe id="'+this.wysiwyg_content+'" style="margin-left: 3px; background-color: white; color: black; width:100%; height:200 px;" ></iframe>');
}

/**
*   WYSIWYG_Editor::doTextFormat
*
*   Apply a text formatting command to the selected text in the editor (or starting at the current cursor position)
*
*   @param  command string  Which of the editor/browser text formatting commands to apply
**/
WYSIWYG_Editor.prototype.doTextFormat = function (command, optn, evnt){
    if((command=='forecolor') || (command=='hilitecolor')){
        this.getPallete(command, optn, evnt);
    }else if(command=='createlink'){
        var szURL=prompt('Enter a URL:', '');
        if(document.getElementById(this.wysiwyg_content).contentWindow.document.queryCommandEnabled(command)){
            document.getElementById(this.wysiwyg_content).contentWindow.document.execCommand('CreateLink',false,szURL);
            return true;
        }else return false;
    }else{
        if(document.getElementById(this.wysiwyg_content).contentWindow.document.queryCommandEnabled(command)){
              document.getElementById(this.wysiwyg_content).contentWindow.document.execCommand(command, false, optn);
              return true;
          }else return false;
    }
    document.getElementById(this.wysiwyg_content).contentWindow.focus();
}

/**
*   WYSIWYG_Editor::_load_content
*
*   Puts the passed content into the editor or textarea (Use this one *after* displaying the editor.)
*
*   @param  content string  The string containing the properly-formatted HTML source to use
*/
WYSIWYG_Editor.prototype._load_content = function (){
    document.getElementById(this.wysiwyg_hidden).value=this.content;
}


/**
*   WYSIWYG_Editor::_get_offset_left
*
*   Used to define position of pop-up pallete window in Gecko browsers
**/
WYSIWYG_Editor.prototype._get_offset_left = function (elm){
    var mOffsetLeft=elm.offsetLeft;
    var mOffsetParent=elm.offsetParent;
    while(mOffsetParent){
        mOffsetLeft += mOffsetParent.offsetLeft;
        mOffsetParent=mOffsetParent.offsetParent;
    }
    return mOffsetLeft;
}

/**
*   WYSIWYG_Editor::_get_offset_top
*
*   Used to define position of pop-up pallete window in Gecko browsers
**/
WYSIWYG_Editor.prototype._get_offset_top = function (elm){
    var mOffsetTop=elm.offsetTop;
    var mOffsetParent=elm.offsetParent;
    while(mOffsetParent){
        mOffsetTop += mOffsetParent.offsetTop;
        mOffsetParent=mOffsetParent.offsetParent;
    }
    return mOffsetTop;
}


/**
*   WYSIWYG_Editor::_insert_element_gecko
*
*   Used by Gecko browsers to insert elements into the document for the insertTable method
*
*   @param  win The window object to insert the element into
*   @param  elem    The element to insert into the content window
**/
WYSIWYG_Editor.prototype._insert_element_gecko = function (win, elem){
    var sel = win.getSelection();           // get current selection
    var range = sel.getRangeAt(0);          // get the first range of the selection (there's almost always only one range)
    sel.removeAllRanges();                  // deselect everything
    range.deleteContents();                 // remove content of current selection from document
    var container = range.startContainer;   // get location of current selection
    var pos = range.startOffset;
    range=document.createRange();           // make a new range for the new selection

    if (container.nodeType==3 && elem.nodeType==3) {
        // if we insert text in a textnode, do optimized insertion
        container.insertData(pos, elem.nodeValue);

        // put cursor after inserted text
        range.setEnd(container, pos+elem.length);
        range.setStart(container, pos+elem.length);
    }else{
        var afterNode;
        if (container.nodeType==3) {
          // when inserting into a textnode we create 2 new textnodes and put the elem in between
          var textNode = container;
          container = textNode.parentNode;
          var text = textNode.nodeValue;

          var textBefore = text.substr(0,pos);  // text before the split
          var textAfter = text.substr(pos);     // text after the split

          var beforeNode = document.createTextNode(textBefore);
          var afterNode = document.createTextNode(textAfter);

          // insert the 3 new nodes before the old one
          container.insertBefore(afterNode, textNode);
          container.insertBefore(elem, afterNode);
          container.insertBefore(beforeNode, elem);

          // remove the old node
          container.removeChild(textNode);
        }else{
          // else simply insert the node
          afterNode = container.childNodes[pos];
          container.insertBefore(elem, afterNode);
        }
    }
}

/**
*   WYSIWYG_Editor::setColor
*
*   Used to set the text or highlight color of the selected text in Gecko engine browsers
**/
WYSIWYG_Editor.prototype.setColor = function (color, command){
    // close the window we made to display the pallete in
    if(typeof(pwin)=='object'){ // make sure it exists
        if(!pwin.closed){ // if it is still open
            pwin.close();
        }
    }

    // only one difference for MSIE
    if(this.isMSIE() && command == 'hilitecolor') command = 'backcolor';

    //get current selected range
    var sel=document.getElementById(this.wysiwyg_content).contentWindow.document.selection;
    if(sel!=null){
        rng=sel.createRange();
    }

    document.getElementById(this.wysiwyg_content).contentWindow.focus();
    if(document.getElementById(this.wysiwyg_content).contentWindow.document.queryCommandEnabled(command)){
        document.getElementById(this.wysiwyg_content).contentWindow.document.execCommand(command, false, color);
    }else return false;
    document.getElementById(this.wysiwyg_content).contentWindow.focus();
    return true;
}

/**
*   WYSIWYG_Editor::getPallete
*
*   Apply a text color to selected text or starting at current position
*
*   @param  command string  Used to determine which pallete pop-up to display
**/
WYSIWYG_Editor.prototype.getPallete = function (command, optn, evnt) {
    // get the pallete HTML code
    html = this._get_palette_html(command);

    // close the window we made to display the pallete in
    // this is in case someone clicked the hilitecolor, then clicked the forcolor button
    // without making a choice
    if(typeof(pwin)=='object'){ // make sure it exists
        if(!pwin.closed){ // if it is still open
            pwin.close();
        }
    }

    // OK, now I need to open a new window to capture a click from
    pwin = window.open('','colorPallete','dependent=yes, directories=no, fullscreen=no,hotkeys=no,height=120,width=172,left='+evnt.screenX+',top='+evnt.screenY+',locatoin=no,menubar=no,resizable=no,scrollbars=no,status=no,titlebar=no,toolbar=no');
    pwin.document.write(html);
    pwin.document.close(); // prevents page from attempting to load more code
}

/**
*   WYSIWYG_Editor::isSupported
*
*   Checks that the browser supports this programming by writing an invisible IFRAME and testing its properties
*/
WYSIWYG_Editor.prototype.isSupported = function () {
    // This is to get rid of the browser UA check that was previously implemented for this class.
    // should be called from somewhere in the body of the document for best results
    document.write('<iframe id="WYSIWYG_Editor_Testing_Browser_Features" style="display: none; visibility: hidden;"></iframe>');
    test = typeof(document.getElementById('WYSIWYG_Editor_Testing_Browser_Features').contentWindow);
    if(test == 'object'){
        return true;
    }else{
        return false;
    }
    return this.supported;
}

/**
*   WYSIWYG_Editor::isMSIE
*
*   Checks if browser is MSIE by testing the document.all property that is only supported by MSIE and AOL
*/
WYSIWYG_Editor.prototype.isMSIE = function (){
    if(typeof(document.all)=='object'){
        return true;
    }else{
        return false;
    }
}

/**
*   WYSIWYG_Editor::_get_palette_html
*
*   Generates the HTML that will be used in the color palette pop-ups.
*
*   @param  command string  The command that indicates which text color is being set
**/
WYSIWYG_Editor.prototype._get_palette_html = function (command) {
    s =     '<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">';
    s = s + '<html>';
    s = s + ' <head>';
    s = s + '  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">';
    s = s + '  <title>Color Pallete</title>';
    s = s + '  <style type="text/css">';
    s = s + '   <!--';
    s = s + '    html,body{margin: 0px; padding: 0px; color: black; background-color: white;}';
    s = s + '    td{border: black none 2px;}';
    s = s + '    td:hover{border: white solid 2px;}';
    s = s + '   -->';
    s = s + '  </style>';
    s = s + ' </head>';
    s = s + '';
    // we want the focus is brought to this new page
    s = s + ' <body onload="window.focus()" onblur="window.focus()">';
    s = s + '  <table border="0" cellpadding="0" cellspacing="2">';
    s = s + '   <tr>';
    s = s + '    <td id="cFFFFFF" bgcolor="#FFFFFF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFCCCC" bgcolor="#FFCCCC" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFCC99" bgcolor="#FFCC99" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFFF99" bgcolor="#FFFF99" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFFFCC" bgcolor="#FFFFCC" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c99FF99" bgcolor="#99FF99" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c99FFFF" bgcolor="#99FFFF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cCCFFFF" bgcolor="#CCFFFF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cCCCCFF" bgcolor="#CCCCFF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFCCFF" bgcolor="#FFCCFF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '   </tr>';
    s = s + '   <tr>';
    s = s + '    <td id="cCCCCCC" bgcolor="#CCCCCC" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFF6666" bgcolor="#FF6666" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFF9966" bgcolor="#FF9966" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFFF66" bgcolor="#FFFF66" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFFF33" bgcolor="#FFFF33" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c66FF99" bgcolor="#66FF99" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c33FFFF" bgcolor="#33FFFF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c66FFFF" bgcolor="#66FFFF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c9999FF" bgcolor="#9999FF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFF99FF" bgcolor="#FF99FF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '   </tr>';
    s = s + '   <tr>';
    s = s + '    <td id="cC0C0C0" bgcolor="#C0C0C0" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFF0000" bgcolor="#FF0000" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFF9900" bgcolor="#FF9900" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFCC66" bgcolor="#FFCC66" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFFF00" bgcolor="#FFFF00" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c33FF33" bgcolor="#33FF33" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c66CCCC" bgcolor="#66CCCC" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c33CCFF" bgcolor="#33CCFF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c6666CC" bgcolor="#6666CC" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cCC66CC" bgcolor="#CC66CC" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '   </tr>';
    s = s + '   <tr>';
    s = s + '    <td id="c999999" bgcolor="#999999" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cCC0000" bgcolor="#CC0000" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFF6600" bgcolor="#FF6600" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFCC33" bgcolor="#FFCC33" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFCC00" bgcolor="#FFCC00" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c33CC00" bgcolor="#33CC00" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c00CCCC" bgcolor="#00CCCC" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c3366FF" bgcolor="#3366FF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c6633FF" bgcolor="#6633FF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cCC33CC" bgcolor="#CC33CC" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '   </tr>';
    s = s + '   <tr>';
    s = s + '    <td id="c666666" bgcolor="#666666" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c990000" bgcolor="#990000" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cCC6600" bgcolor="#CC6600" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cCC9933" bgcolor="#CC9933" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c999900" bgcolor="#999900" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c009900" bgcolor="#009900" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c339999" bgcolor="#339999" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c3333FF" bgcolor="#3333FF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c6600CC" bgcolor="#6600CC" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c993399" bgcolor="#993399" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '   </tr>';
    s = s + '   <tr>';
    s = s + '    <td id="c333333" bgcolor="#333333" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c660000" bgcolor="#660000" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c993300" bgcolor="#993300" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c996633" bgcolor="#996633" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c666600" bgcolor="#666600" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c006600" bgcolor="#006600" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c336666" bgcolor="#336666" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c000099" bgcolor="#000099" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c333399" bgcolor="#333399" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c663366" bgcolor="#663366" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '   </tr>';
    s = s + '   <tr>';
    s = s + '    <td id="c000000" bgcolor="#000000" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c330000" bgcolor="#330000" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c663300" bgcolor="#663300" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c663333" bgcolor="#663333" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c333300" bgcolor="#333300" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c003300" bgcolor="#003300" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c003333" bgcolor="#003333" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c000066" bgcolor="#000066" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c330099" bgcolor="#330099" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c330033" bgcolor="#330033" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.'+this.instance_name+'.setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '   </tr>';
    s = s + '  </table>';
    s = s + ' </body>';
    s = s + '</html>';
    return s;
}
