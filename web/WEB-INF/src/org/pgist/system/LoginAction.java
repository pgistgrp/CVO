package org.pgist.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;
import org.pgist.users.User;
import org.pgist.users.UserInfo;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class LoginAction extends Action {

       
    private SystemService systemService;
    private RegisterService registerService;
    
    
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }
    
    public void setRegisterService(RegisterService registerService){
    	this.registerService = registerService;
    }

    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
        
        //Create new session
        //HttpSession session = request.getSession(true);
    	HttpSession session = request.getSession(false);
        
        //Check to see if CyberGIS request
        if(!(request.getParameter("domain")==null)){
        	String result = verifyToken(request);  
        	
        	if (result.equals("success")){
        		User user = getUser(request);
        		if (user == null){
        			return mapping.findForward("loginPage");
        		}else{
        			putUserInSession(user, request, session);
        			return doForward(mapping, request, response, session);
        		}
        	}else
        		return mapping.findForward("loginPage");
        }else { //Process normal Login via index.jsp
        
        	String loginname = request.getParameter("loginname");
        	String password = request.getParameter("password");
        
        	if((loginname==null || "".equals(loginname)) && !(password==null || "".equals(password))){
        		request.setAttribute("reason", "Please Enter a User Name.");  
        		return mapping.findForward("loginPage");
        	} else if(!(loginname==null || "".equals(loginname)) && (password==null || "".equals(password))){
        		request.setAttribute("reason", "Please Enter a Password.");
        		return mapping.findForward("loginPage");
        	} else if ((loginname==null || "".equals(loginname))&& (password==null || "".equals(password))){
        		request.setAttribute("reason", "Please Enter a User Name and Password.");
        		return mapping.findForward("loginPage");
        	} 
 
        
        	User user = getUser(request);
        	if (user == null){
    			return mapping.findForward("loginPage");
    		}
        
        	if (user.checkPassword(password)) {
        	
        		putUserInSession(user, request, session);
     
             //Check if it's a intermediate login
        		if(request.getCookies()!=null){
        			for (Cookie cookie : request.getCookies()) {
        				if ("PG_INIT_URL".equals(cookie.getName())) {
        					String initURL = cookie.getValue();
                    
        					//Remove cookie
        					cookie.setMaxAge(0);
        					response.addCookie(cookie);
                    
        					if (initURL!=null && initURL.length()>0) {
        						//Redirect to the initial URL
        						return new ActionForward(initURL, true);
        					}
        				}
        			}
        		}
        		
        		return doForward(mapping, request, response, session);
            
        	} else if(!user.checkPassword(password)){
        		request.setAttribute("reason", "Your Password is Invalid. Please Try Again.");
        		return mapping.findForward("loginPage");
        	}
        }
        
      //if all else fails, return to login page
    	return mapping.findForward("loginPage");
    }
        
    private User getUser(HttpServletRequest request)throws Exception{
    	String loginname = request.getParameter("loginname");
		User user = systemService.getUserByName(loginname, true, false);
        if(user == null) {
        	//Check to see if the account is disabled
        	User user2 = systemService.getUserByName(loginname, false, false);
        	if(user2 != null) {
        		request.setAttribute("reason", "You account has been disabled. Please contact us at <a href=\"mailto:moderator@letsimprovetransportation.org\">moderator@letsimprovetransportation.org</a> for assistance.");
        	} else if (!(request.getParameter("domain")==null)) { //User is new & needs to be created on the fly
        		String [] assocs = {"CyberGIS"};
        		Long newUserId = registerService.addSarpUser(loginname, loginname, loginname+"@cybergis.org", "0", "M", "0", "0", "", loginname, "cybergis", "", new HashSet(Arrays.asList(assocs)));
        		user = systemService.getUserById(newUserId);
        	} else{
        		request.setAttribute("reason", "Invalid User Name.");
        	}
        }
        return user;
    }
    
    
    private void putUserInSession(User user, HttpServletRequest request, HttpSession session){
          UserInfo userInfo = new UserInfo(user);
          request.setAttribute("baseuser", userInfo);
          session.setAttribute("user", userInfo);
          WebUtils.setCurrentUser(userInfo);
          
          request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
    }
    
    private ActionForward doForward(ActionMapping mapping,HttpServletRequest request, HttpServletResponse response, HttpSession session){
    	ActionForward af; 
    	
    	//Check to see if feedback/BCT direct call otherwise go to main.jsp
    	if(!(request.getParameter("workflowId")==null)){
    		  String workflowId = request.getParameter("workflowId");
    		  String contextId = request.getParameter("contextId");
    		  String activityId = request.getParameter("activityId");
    	
    		  af = new ActionForward(request.getAttribute("httpPrefix")
    			+ mapping.findForward("workflow").getPath()
    			+ "?workflowId=" + workflowId 
    			+ "&contextId=" + contextId 
    			+ "&activityId=" + activityId,
                true);
    		  
    		  //appending jsessionid didn't work to maintain session, need to add cookie!
    		  Cookie cookie = new Cookie("JSESSIONID", session.getId());
              cookie.setMaxAge(-1);
              response.addCookie(cookie);
    		  
    	  }else{
    		  af = new ActionForward(
                request.getAttribute("httpPrefix")
                + mapping.findForward("main").getPath()
                + ";jsessionid="+session.getId(),
                true); 
    	  }
    	return af;
    }
    
    //The following methods are for CyberGIS Token Service
	private String verifyToken(HttpServletRequest request)
	throws KeyManagementException, HttpException,
	NoSuchAlgorithmException, IOException, JSONException {
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("token", request.getParameter("token")));
		formparams.add(new BasicNameValuePair("consumer", this.getServlet().getInitParameter("CONSUMER_ID")));
		formparams.add(new BasicNameValuePair("auth_request", "default"));
		formparams.add(new BasicNameValuePair("username", request.getParameter("username")));
		formparams.add(new BasicNameValuePair("remote_addr", request.getRemoteAddr()));
		//formparams.add(new BasicNameValuePair("remote_addr", "71.212.56.105"));
		
		String url = this.getServlet().getInitParameter("APIURL")+"token";
		String resultString = executePut(url, formparams);
	
		JSONObject jsonObject = new JSONObject(resultString);
		String result = jsonObject.getString("status");
		
		if (result.equals("error")){
			String errorMsg = "CyberGIS SSO failed. " + jsonObject.getJSONObject("result").getString("message") + ". Please login manually.";
			request.setAttribute("reason", errorMsg);
		}
		return result;
}
    

	private String executePut(String url, List<NameValuePair> formparams)
	throws HttpException, IOException, KeyManagementException,
	NoSuchAlgorithmException {
		HttpPut method = new HttpPut(url);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		method.setEntity(entity);
		return executeMethod(method);
	}

	
	private String getQueryString(List<NameValuePair> formparams) {
		StringBuffer buffer = new StringBuffer();
		for (NameValuePair nv : formparams) {
			buffer.append("&").append(URLEncoder.encode(nv.getName())).append("=")
					.append(URLEncoder.encode(nv.getValue()));
		}
		if (buffer.length() > 0) {
			return buffer.substring(1);
		} else {
			return null;
		}
	}

	private String executeMethod(HttpRequestBase method) throws HttpException,
			IOException, KeyManagementException, NoSuchAlgorithmException {
		SSLContext ctx = SSLContext.getInstance("TLS");
		ctx.init(new KeyManager[0],
				new TrustManager[] { new DefaultTrustManager() },
				new SecureRandom());
		SSLContext.setDefault(ctx);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(method);
		HttpEntity resEntity = response.getEntity();
		String responseString = "";
		if(resEntity!=null){
			BufferedReader br = new BufferedReader(new InputStreamReader(resEntity.getContent()));
			String line = "";
			while((line=br.readLine())!=null){
				responseString += line;
			}
		}
		return responseString;
	}

	private class DefaultTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

	}
}//class LoginAction
