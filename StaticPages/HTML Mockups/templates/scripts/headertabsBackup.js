/*==================================================
  $Id$
  tabber.js by Patrick Fitzgerald pat@barelyfitz.com

  Documentation can be found at the following URL:
  http://www.barelyfitz.com/projects/tabber/

  License (http://www.opensource.org/licenses/mit-license.php)

  Copyright (c) 2006 Patrick Fitzgerald

  Permission is hereby granted, free of charge, to any person
  obtaining a copy of this software and associated documentation files
  (the "Software"), to deal in the Software without restriction,
  including without limitation the rights to use, copy, modify, merge,
  publish, distribute, sublicense, and/or sell copies of the Software,
  and to permit persons to whom the Software is furnished to do so,
  subject to the following conditions:

  The above copyright notice and this permission notice shall be
  included in all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
  BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
  ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  SOFTWARE.
  ==================================================*/

function headerObj(argsObj)
{
  var arg; /* name of an argument to override */

  /* Element for the main tabber div. If you supply this in argsObj,
     then the init() method will be called.
  */
  this.div = null;

  /* Class of the main header div */
  this.classHeadMain = "header";

  /* Rename classHeadMain to classHeadMainLive after tabifying
     (so a different style can be applied)
  */
  this.classHeadMainLive = "headerlive";

  /* Class of each DIV that contains a tab */
  this.classHead = "headertab";

  /* Class to indicate which tab should be active on startup */
  this.classHeadDefault = "headertabdefault";

  /* Class for the navigation UL */
  this.classHeadNav = "headernav";

  /* When a tab is to be hidden, instead of setting display='none', we
     set the class of the div to classHeadHide. In your screen
     stylesheet you should set classHeadHide to display:none.  In your
     print stylesheet you should set display:block to ensure that all
     the information is printed.
  */
  this.classHeadHide = "headertabhide";

  /* Class to set the navigation LI when the tab is active, so you can
     use a different style on the active tab.
  */
  this.classHeadNavActive = "headeractive";

  /* Elements that might contain the title for the tab, only used if a
     title is not specified in the TITLE attribute of DIV classHead.
  */
  this.titleHeadElements = ['h2','h3','h4','h5','h6'];

  /* Should we strip out the HTML from the innerHTML of the title elements?
     This should usually be true.
  */
  this.titleHeadElementsStripHTML = true;

  /* If the user specified the tab names using a TITLE attribute on
     the DIV, then the browser will display a tooltip whenever the
     mouse is over the DIV. To prevent this tooltip, we can remove the
     TITLE attribute after getting the tab name.
  */
  this.removeHeadTitle = true;

  /* If you want to add an id to each link set this to true */
  this.addHeadLinkId = false;

  /* If addIds==true, then you can set a format for the ids.
     <headerid> will be replaced with the id of the main header div.
     <tabnumberzero> will be replaced with the tab number (tab numbers starting at zero)
     <tabnumberone> will be replaced with the tab number (tab numbers starting at one)
     <tabtitle> will be replaced by the tab title (with all non-alphanumeric characters removed)
   */
  this.linkHeadIdFormat = '<tabberid>nav<tabnumberone>';

  /* You can override the defaults listed above by passing in an object:
     var mytab = new tabber({property:value,property:value});
  */
  for (arg in argsObj) { this[arg] = argsObj[arg]; }

  /* Create regular expressions for the class names; Note: if you
     change the class names after a new object is created you must
     also change these regular expressions.
  */
  this.REclassHeadMain = new RegExp('\\b' + this.classHeadMain + '\\b', 'gi');
  this.REclassHeadMainLive = new RegExp('\\b' + this.classHeadMainLive + '\\b', 'gi');
  this.REclassHead = new RegExp('\\b' + this.classHead + '\\b', 'gi');
  this.REclassHeadDefault = new RegExp('\\b' + this.classHeadDefault + '\\b', 'gi');
  this.REclassHeadHide = new RegExp('\\b' + this.classHeadHide + '\\b', 'gi');

  /* Array of objects holding info about each tab */
  this.tabs = new Array();

  /* If the main tabber div was specified, call init() now */
  if (this.div) {

    this.init(this.div);

    /* We don't need the main div anymore, and to prevent a memory leak
       in IE, we must remove the circular reference between the div
       and the tabber object. */
    this.div = null;
  }
}


/*--------------------------------------------------
  Methods for headerObj
  --------------------------------------------------*/


headerObj.prototype.init = function(e)
{
  /* Set up the tabber interface.

     e = element (the main containing div)

     Example:
     init(document.getElementById('mytabberdiv'))
   */

  var
  childNodes, /* child nodes of the tabber div */
  i, i2, /* loop indices */
  t, /* object to store info about a single tab */
  defaultTab=0, /* which tab to select by default */
  DOM_ul, /* tabbernav list */
  DOM_li, /* tabbernav list item */
  DOM_a, /* tabbernav link */
  aId, /* A unique id for DOM_a */
  headerElement; /* searching for text to use in the tab */

  /* Verify that the browser supports DOM scripting */
  if (!document.getElementsByTagName) { return false; }

  /* If the main DIV has an ID then save it. */
  if (e.id) {
    this.id = e.id;
  }

  /* Clear the tabs array (but it should normally be empty) */
  this.tabs.length = 0;

  /* Loop through an array of all the child nodes within our tabber element. */
  childNodes = e.childNodes;
  for(i=0; i < childNodes.length; i++) {

    /* Find the nodes where class="tabbertab" */
    if(childNodes[i].className &&
       childNodes[i].className.match(this.REclassHead)) {
      
      /* Create a new object to save info about this tab */
      t = new Object();
      
      /* Save a pointer to the div for this tab */
      t.div = childNodes[i];
      
      /* Add the new object to the array of tabs */
      this.tabs[this.tabs.length] = t;

      /* If the class name contains classHeadDefault,
	 then select this tab by default.
      */
      if (childNodes[i].className.match(this.REclassHeadDefault)) {
	defaultTab = this.tabs.length-1;
      }
    }
  }

  /* Create a new UL list to hold the tab headers */
  DOM_ul = document.createElement("ul");
  DOM_ul.className = this.classHeadNav;
  
  /* Loop through each tab we found */
  for (i=0; i < this.tabs.length; i++) {

    t = this.tabs[i];

    /* Get the label to use for this tab:
       From the title attribute on the DIV,
       Or from one of the this.titleHeadElements[] elements,
       Or use an automatically generated number.
     */
    t.headerText = t.div.title;

    /* Remove the title attribute to prevent a tooltip from appearing */
    if (this.removeHeadTitle) { t.div.title = ''; }

    if (!t.headerText) {

      /* Title was not defined in the title of the DIV,
	 So try to get the title from an element within the DIV.
	 Go through the list of elements in this.titleHeadElements
	 (typically header elements ['h2','h3','h4'])
      */
      for (i2=0; i2<this.titleHeadElements.length; i2++) {
	headerElement = t.div.getElementsByTagName(this.titleHeadElements[i2])[0];
	if (headerElement) {
	  t.headerText = headerElement.innerHTML;
	  if (this.titleHeadElementsStripHTML) {
	    t.headerText.replace(/<br>/gi," ");
	    t.headerText = t.headerText.replace(/<[^>]+>/g,"");
	  }
	  break;
	}
      }
    }

    if (!t.headerText) {
      /* Title was not found (or is blank) so automatically generate a
         number for the tab.
      */
      t.headerText = i + 1;
    }

    /* Create a list element for the tab */
    DOM_li = document.createElement("li");

    /* Save a reference to this list item so we can later change it to
       the "active" class */
    t.li = DOM_li;

    /* Create a link to activate the tab */
    DOM_a = document.createElement("a");
    DOM_a.appendChild(document.createTextNode(t.headerText));
    DOM_a.href = "javascript:void(null);";
    DOM_a.title = t.headerText;
    DOM_a.onclick = this.navClick;

    /* Add some properties to the link so we can identify which tab
       was clicked. Later the navClick method will need this.
    */
    DOM_a.tabber = this;
    DOM_a.tabIndex = i;

    /* Do we need to add an id to DOM_a? */
    if (this.addHeadLinkId && this.linkHeadIdFormat) {

      /* Determine the id name */
      aId = this.linkHeadIdFormat;
      aId = aId.replace(/<tabberid>/gi, this.id);
      aId = aId.replace(/<tabnumberzero>/gi, i);
      aId = aId.replace(/<tabnumberone>/gi, i+1);
      aId = aId.replace(/<tabtitle>/gi, t.headerText.replace(/[^a-zA-Z0-9\-]/gi, ''));

      DOM_a.id = aId;
    }

    /* Add the link to the list element */
    DOM_li.appendChild(DOM_a);

    /* Add the list element to the list */
    DOM_ul.appendChild(DOM_li);
  }

  /* Add the UL list to the beginning of the tabber div */
  e.insertBefore(DOM_ul, e.firstChild);

  /* Make the tabber div "live" so different CSS can be applied */
  e.className = e.className.replace(this.REclassHeadMain, this.classHeadMainLive);

  /* Activate the default tab, and do not call the onclick handler */
  this.tabShow(defaultTab);

  /* If the user specified an onLoad function, call it now. */
  if (typeof this.onLoad == 'function') {
    this.onLoad({tabber:this});
	//alert("this sucks");
    dosize();
  }

  return this;
};


headerObj.prototype.navClick = function(event)
{
  /* This method should only be called by the onClick event of an <A>
     element, in which case we will determine which tab was clicked by
     examining a property that we previously attached to the <A>
     element.

     Since this was triggered from an onClick event, the variable
     "this" refers to the <A> element that triggered the onClick
     event (and not to the headerObj).

     When headerObj was initialized, we added some extra properties
     to the <A> element, for the purpose of retrieving them now. Get
     the headerObj object, plus the tab number that was clicked.
  */

  var
  rVal, /* Return value from the user onclick function */
  a, /* element that triggered the onclick event */
  self, /* the tabber object */
  tabIndex, /* index of the tab that triggered the event */
  onClickArgs; /* args to send the onclick function */

  a = this;
  if (!a.tabber) { return false; }

  self = a.tabber;
  tabIndex = a.tabIndex;

  /* Remove focus from the link because it looks ugly.
     I don't know if this is a good idea...
  */
  a.blur();

  /* If the user specified an onClick function, call it now.
     If the function returns false then do not continue.
  */
  if (typeof self.onClick == 'function') {

    onClickArgs = {'tabber':self, 'index':tabIndex, 'event':event};

    /* IE uses a different way to access the event object */
    if (!event) { onClickArgs.event = window.event; }

    rVal = self.onClick(onClickArgs);
    if (rVal === false) { return false; }
  }

  self.tabShow(tabIndex);

  return true;
};


headerObj.prototype.tabHideAll = function()
{
  var i; /* counter */

  /* Hide all tabs and make all navigation links inactive */
  for (i = 0; i < this.tabs.length; i++) {
    this.tabHide(i);
  }
};


headerObj.prototype.tabHide = function(tabIndex)
{
  /* Hide a single tab and make its navigation link inactive */
  var div = this.tabs[tabIndex].div;

  /* Hide the tab contents by adding classHeadHide to the div */
  if (!div.className.match(this.REclassHeadHide)) {
    div.className += ' ' + this.classHeadHide;
  }
  this.navClearActive(tabIndex);
};


headerObj.prototype.tabShow = function(tabIndex)
{
  /* Show the tabIndex tab and hide all the other tabs */

  var div;

  if (!this.tabs[tabIndex]) { return false; }

  /* Hide all the tabs first */
  this.tabHideAll();

  /* Get the div that holds this tab */
  div = this.tabs[tabIndex].div;

  /* Remove classHeadHide from the div */
  div.className = div.className.replace(this.REclassHeadHide, '');

  /* Mark this tab navigation link as "active" */
  this.navSetActive(tabIndex);

  return this;
};


headerObj.prototype.navSetActive = function(tabIndex)
{
  /* Note: this method does *not* enforce the rule
     that only one nav item can be active at a time.
  */

  /* Set classHeadNavActive for the navigation list item */
  this.tabs[tabIndex].li.className = this.classHeadNavActive;

  return this;
};


headerObj.prototype.navClearActive = function(tabIndex)
{
  /* Note: this method does *not* enforce the rule
     that one nav should always be active.
  */

  /* Remove classHeadNavActive from the navigation list item */
  this.tabs[tabIndex].li.className = '';

  return this;
};


/*==================================================*/


function tabberAutomatic(tabberArgs)
{
  /* This function finds all DIV elements in the document where
     class=tabber.classHeadMain, then converts them to use the tabber
     interface.

     tabberArgs = an object to send to "new tabber()"
  */
  var
    tempObj, /* Temporary tabber object */
    divs, /* Array of all divs on the page */
    i; /* Loop index */

  if (!tabberArgs) { tabberArgs = {}; }

  /* Create a tabber object so we can get the value of classHeadMain */
  tempObj = new headerObj(tabberArgs);

  /* Find all DIV elements in the document that have class=tabber */

  /* First get an array of all DIV elements and loop through them */
  divs = document.getElementsByTagName("div");
  for (i=0; i < divs.length; i++) {
    
    /* Is this DIV the correct class? */
    if (divs[i].className &&
	divs[i].className.match(tempObj.REclassHeadMain)) {
      
      /* Now tabify the DIV */
      tabberArgs.div = divs[i];
      divs[i].tabber = new headerObj(tabberArgs);
    }
  }

  return this;
  
}


/*==================================================*/


function tabberAutomaticOnLoad(tabberArgs)
{
  /* This function adds tabberAutomatic to the window.onload event,
     so it will run after the document has finished loading.
  */
  var oldOnLoad;
	
  if (!tabberArgs) { tabberArgs = {}; }

  /* Taken from: http://simon.incutio.com/archive/2004/05/26/addLoadEvent */

  oldOnLoad = window.onload;
  if (typeof window.onload != 'function') {
    window.onload = function() {
      tabberAutomatic(tabberArgs);
      
    };
  } else {
    window.onload = function() {
      oldOnLoad();
      tabberAutomatic(tabberArgs);
	  
    };
  }

}


/*==================================================*/


/* Run tabberAutomaticOnload() unless the "manualStartup" option was specified */

if (typeof tabberOptions == 'undefined') {

    tabberAutomaticOnLoad();

} else {

  if (!tabberOptions['manualStartup']) {
    tabberAutomaticOnLoad(tabberOptions);
  }

}
