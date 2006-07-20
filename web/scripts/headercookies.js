/* Optional: Temporarily hide the "header" class so it does not "flash" on the 
page as plain HTML. After header runs, the class is changed to "headerlive" and 
it will appear. /*
document.write('<style type="text/css">.header{display:none;}<\/style>');

/*==================================================
  Set the header options (must do this before including header.js)
  ==================================================*/
var headerOptions = {

  'cookie':"header", /* Name to use for the cookie */

  'onLoad': function(argsObj)
  {
    var t = argsObj.header;
    var i;

    /* Optional: Add the id of the header to the cookie name to allow
       for multiple header interfaces on the site.  If you have
       multiple header interfaces (even on different pages) I suggest
       setting a unique id on each one, to avoid having the cookie set
       the wrong tab.
    */
    if (t.id) {
      t.cookie = t.id + t.cookie;
    }

    /* If a cookie was previously set, restore the active tab */
    i = parseInt(getCookie(t.cookie));
    if (isNaN(i)) { return; }
    t.headShow(i);
    
  },

  'onClick':function(argsObj)
  {
    var c = argsObj.header.cookie;
    var i = argsObj.index;
    
    setCookie(c, i);
  }
};

/*==================================================
  Cookie functions
  ==================================================*/
function setCookie(name, value, expires, path, domain, secure) {
    document.cookie= name + "=" + escape(value) +
        ((expires) ? "; expires=" + expires.toGMTString() : "") +
        ((path) ? "; path=" + path : "") +
        ((domain) ? "; domain=" + domain : "") +
        ((secure) ? "; secure" : "");
}

function getCookie(name) {
    var dc = document.cookie;
    var prefix = name + "=";
    var begin = dc.indexOf("; " + prefix);
    if (begin == -1) {
        begin = dc.indexOf(prefix);
        if (begin != 0) return null;
    } else {
        begin += 2;
    }
    var end = document.cookie.indexOf(";", begin);
    if (end == -1) {
        end = dc.length;
    }
    return unescape(dc.substring(begin + prefix.length, end));
}
function deleteCookie(name, path, domain) {
    if (getCookie(name)) {
        document.cookie = name + "=" +
            ((path) ? "; path=" + path : "") +
            ((domain) ? "; domain=" + domain : "") +
            "; expires=Thu, 01-Jan-70 00:00:01 GMT";
    }
}
