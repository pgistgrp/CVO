var ajaxCaller = {

  shouldDebug: false,
  shouldEscapeVars: false,
  shouldMakeHeaderMap: true,

  calls : new Array(),
  pendingResponseCount : 0,

   /**************************************************************************
      PUBLIC METHODS
   *************************************************************************/

  getXML: function(url, callbackFunction) {
    this.get(url, null, callbackFunction, true, null);
  },

  getPlainText: function(url, callbackFunction) {
    this.get(url, null, callbackFunction, false, null);
  },

  postForPlainText: function(url, vars, callbackFunction) {
    this.postVars(url, vars, null, callbackFunction, false,
                    null, "POST", null, null, null);
  },

  postForXML: function(url, vars, callbackFunction) {
    this.postVars(url, vars, null, callbackFunction, true,
                    null, "POST", null, null, null);
  },

  get: function(url, urlVars, callbackFunction, expectingXML, callingContext) {
    this._callServer(url, urlVars, callbackFunction, expectingXML,
                    callingContext, "GET", null, null, null);
  },

  postVars:
    function(url, bodyVars, optionalURLVars, callbackFunction, expectingXML,
             callingContext) {
      this._callServer(url, optionalURLVars, callbackFunction, expectingXML,
                      callingContext, "POST", bodyVars, null, null);
  },

  postBody:
    function(url, optionalURLVars, callbackFunction, expectingXML,
             callingContext, bodyType, body) {
      this._callServer(url, optionalURLVars, callbackFunction, expectingXML,
                      callingContext, "POST", null, bodyType, body);
  },

  putBody:
    function(url, optionalURLVars, callbackFunction, expectingXML,
             callingContext, bodyType, body) {
      this._callServer(url, optionalURLVars, callbackFunction, expectingXML,
                      callingContext, "PUT", null, bodyType, body);
  },

  options:
    function(url, optionalURLVars, callbackFunction, expectingXML,
             callingContext, bodyType, body) {
      this._callServer(url, optionalURLVars, callbackFunction, expectingXML,
                      callingContext, "OPTIONS", null, bodyType, body);
  },

  trace:
    function(url, optionalURLVars, callbackFunction, expectingXML,
             callingContext, bodyType, body) {
      this._callServer(url, optionalURLVars, callbackFunction, expectingXML,
                      callingContext, "TRACE", null, bodyType, body);
  },

  deleteIt: function(url, urlVars, callbackFunction,
                     expectingXML, callingContext) {
    this._callServer(url, urlVars, callbackFunction, expectingXML,
                    callingContext, "DELETE", null, null, null);
  },

  head: function(url, urlVars, callbackFunction, expectingXML, callingContext)
  {
    this._callServer(url, urlVars, callbackFunction, expectingXML,
                    callingContext, "HEAD", null, null, null);
  },

  /**************************************************************************
     PRIVATE METHODS
  *************************************************************************/

  _callServer: function(url, urlVars, callbackFunction, expectingXML,
                       callingContext, requestMethod, bodyVars,
                       explicitBodyType, explicitBody) {

    if (urlVars==null) {
      urlVars = new Array();
    }

    this._debug("_callServer() called. About to request URL\n"
                + "call key: [" + this.calls.length + "]\n"
                + "url: [" + url + "]\n"
                + "callback function: [" + callbackFunction + "]\n"
                + "treat response as xml?: [" + expectingXML + "]\n"
                + "Request method?: [" + requestMethod + "]\n"
                + "calling context: [" + callingContext + "]\n"
                + "explicit body type: [" + explicitBodyType + "]\n"
                + "explicit body: [" + explicitBody + "]\n"
                + "urlVars: [" + this._describe(urlVars) + "]\n"
                + "bodyVars: [" + this._describe(bodyVars) + "]"
              );


    var xReq = this._createXMLHttpRequest();
    xReq.onreadystatechange = function() {
      ajaxCaller._onResponseStateChange(call);
    }

    var call = {xReq: xReq,
                callbackFunction: callbackFunction,
                expectingXML: expectingXML,
                callingContext: callingContext,
                url: url};

    if (urlVars!=null) {
      var urlVarsString = this._createHTTPVarSpec(urlVars);
      if (urlVarsString.length > 0) { // TODO check if appending with & instead
        url += "?" + urlVarsString;
      }
    }

    xReq.open(requestMethod, url, true);

    if (   requestMethod=="GET"
        || requestMethod=="HEAD"
        || requestMethod=="DELETE") {
      this._debug("Body-less request to URL " + url);
      xReq.send(null);
      return;
    }

    if (   requestMethod=="POST"
        || requestMethod=="PUT"
        || requestMethod=="OPTIONS"
        || requestMethod=="TRACE") {
      bodyType = null;
      body = null;
      if (explicitBodyType==null) { // It's a form
        bodyType = 'application/x-www-form-urlencoded; charset=UTF-8';
        body = this._createHTTPVarSpec(bodyVars);
      } else {
        bodyType = explicitBodyType;
        body = explicitBody;
      }
      this._debug("Content-Type: [" + bodyType + "]\nBody: [" + body + "].");
      xReq.setRequestHeader('Content-Type',  bodyType);
      xReq.send(body);
      return;
    }

    this._debug("ERROR: Unknown Request Method: " + requestMethod);


  },

  // The callback of xmlHttpRequest is a dynamically-generated function which
  // immediately calls this function.
  _onResponseStateChange: function(call) {

    xReq = call.xReq;

    if (xReq.readyState < 4) { //Still waiting
      return;
    }

    if (xReq.readyState == 4) { //Transmit to actual callback
      this._debug("Call " + ajaxCaller._describe(call)
                + " with context [" + call.callingContext+"]"
                + " to " + call.url + " has returned.");
      callbackFunction = call.callbackFunction;
      if (!callbackFunction) { // Maybe still loading, e.g. in another JS file
        setTimeout(function() {
          _onResponseStateChange(call);
        }, 100);
      }
      var content = call.expectingXML ? xReq.responseXML : xReq.responseText;
      responseHeaders = xReq.getAllResponseHeaders();
      headersForCaller = this.shouldMakeHeaderMap ?
        this._createHeaderMap(responseHeaders) : responseHeaders;
      callbackFunction(content, headersForCaller, call.callingContext);
    }

    call = null; // Technically the responsibility of GC
    this.pendingResponseCount--;

  },

  // Browser-agnostic factory function
  _createXMLHttpRequest: function() {
      try { return new ActiveXObject("Msxml2.XMLHTTP"); } catch (e) {}
      try { return new ActiveXObject("Microsoft.XMLHTTP"); } catch (e) {}
      try { return new XMLHttpRequest(); } catch(e) {}
      alert("XMLHttpRequest not supported");
      return null;
    },

  _createHTTPVarSpec: function(vars) {
      var varsString = "";
      for( key in vars ) {
        var value = vars[key];
        if (this.shouldEscapeVars) {
          escapePlusRE =  new RegExp("\\\+");
          value = value.replace(escapePlusRE, "%2B");
        }
        varsString += '&' + key + '=' + value;
      }
      if (varsString.length > 0) {
        varsString = varsString.substring(1); // chomp initial '&'
      }
      this._debug("Built var String: " + varsString)
      return varsString;
   },

  /* Creates associative array from header type to header */
  _createHeaderMap: function(headersText) {
    extractedHeaders = headersText.split("\n");
    delete extractedHeaders[extractedHeaders.length]; // Del blank line at end
    headerMap = new Array();
    for (i=0; i<extractedHeaders.length-2; i++) {
      head = extractedHeaders[i];
      fieldNameEnding = head.indexOf(":");
      field = head.substring(0, fieldNameEnding);
      value = head.substring(fieldNameEnding + 2, head.length);
      value = value.replace(/\s$/, "");
      headerMap[field] = value;
    }
    return headerMap;
  },

  _describe: function(obj) {
    return document.util ? 
      util.describe(obj) : "Include util.js to describe " + obj;
  },

  _debug: function(message) {
      if (this.shouldDebug) {
        alert("AjaxJS Message:\n\n" + message);
      }
  },

  _error: function(message) {
      if (this.shouldDebug) {
        alert("AjaxJS ERROR:\n\n" + message);
      }
  }

};
