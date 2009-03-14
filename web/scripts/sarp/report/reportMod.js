infoObject = {};
infoObject.saveReport = function() {
  displayIndicator(true);
  ReportAgent.saveReport({id:infoObject.targetId, content:tinyMCE.getContent()}, {
    callback:function(data){
      displayIndicator(false);
      if (data.successful){
        alert("saved");
      } else {
        alert(data.reason);
      }
    },
    errorHandler:function(errorString, exception){
      alert("saveReport error: "+errorString+" "+exception);
    }
  });      
};
tinyMCE.init({
    mode : "exact",
    theme : "advanced",
    plugins : "safari,spellchecker,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,imagemanager,filemanager",

    // Theme options
    theme_advanced_buttons1 : "save,newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,formatselect,fontselect,fontsizeselect",
    theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",
    theme_advanced_buttons3 : "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,ltr,rtl,|,fullscreen",
    theme_advanced_buttons4 : "insertlayer,moveforward,movebackward,absolute,|,styleprops,spellchecker,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking,template,blockquote,pagebreak,|,insertfile,insertimage",
    theme_advanced_toolbar_location : "top",
    theme_advanced_toolbar_align : "left",
    theme_advanced_statusbar_location : "bottom",

    content_css : "/scripts/tinymce/jscripts/tiny_mce/themes/simple/css/bigmce.css",
    extended_valid_elements : "blockquote[style='']"
});
infoObject.loadTarget = function() {
  tinyMCE.idCounter=0;
  tinyMCE.execCommand('mceAddControl',false,'reportContent');
};

