<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--####
	Project: Let's Improve Transportation!
	Page: Main index page - http://www.letsimprovetransportation.org
	Description: This page serves as the main index.  Here the user can read info 
	about the project, click to learn more, and/or log into the system.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] Txt and layout changes (Jordan and Adam)
#### -->
<html:html>

<head>
<title>PGIST main page</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/position.css";</style>
<style type="text/css" media="screen">@import "styles/styles.css";</style>
<!-- End Site Wide CSS -->
<!-- Site Wide JS -->
<script src="scripts/search.js" type="text/javascript"></script>
<style type="text/css">

body{text-align: center; font-size: 1.0em;}
#smallcontainer{
  text-align: left;
  margin-right: auto;
  margin-left: auto;
  width: 350px;
}

li{color:#8CAE4A;}
.bulletText{color:#333;}

#wrapper{
  text-align: left;
}
#login {
  border: 10px solid #CCCCCC;
  padding: 13px;
}
#pgistlogo {
  text-align: center;
}
.red {color: #FF0000}
</style>
</head>

<body>
<br />
<div id="smallcontainer">

        <div id="pgistlogo">
        <img src="images/mainlogo.png" alt="pgistlogo" >
        </div>
        <br />
        
        <html:form action="/login.do" method="POST" focus="user.loginname">
          <input type="hidden" name="PG_INIT_URL" value="${param['PG_INIT_URL']}">
          <div>
            <table id="login">
                      <tr>
                        <td>User Name:</td>
                        <td><html:text property="user.loginname"/></td>
                      </tr>
                      <tr>
                        <td>Password:</td>
                        <td><html:password property="user.password" redisplay="false"/></td>
                      </tr>
                      <tr>
                        <td>&nbsp;</td>
                        <td align="right">(<a href="register.do">Register</a>)
                          <html:submit property="submit" value="Login"/>
                        </td>
                      </tr>
                      <tr>
                        <td colspan="2" style="color: red; font-size: .8em;"><div align="center">${userForm.reason}</div></td>
                      </tr>
                    </table>
          </div>
        </html:form>
        
        <div style="margin-top: 20px; ">
          
        <h1 style="font-size: 1.6em">Are <span style="color: #355800">you</span> concerned about transportation in the central Puget Sound region?</h1>
<p>On this website you can:
    <ul><li><span class="bulletText">voice your concerns,</span></li>
    <li><span class="bulletText">learn about proposed improvements,</span></span>
    <li><span class="bulletText">and work with other citizens to determine which improvements are most important.</span></span></ul></p>

<p>How can we find common ground when there are so many competing ideas about the best ways to meet the transportation needs of our growing region? Good question! This website will help you learn about the diverse concerns of other citizens and understand how these concerns may be related to your own. Finding these relationships is the first step to identifying what kinds of transportation solutions we can all get excited about.</p>
        
        <p align="right" style="font-size: 1.3em"><a href="readmore.jsp">Read More</a> or <a href="register.do">Register Now</a>!</p>
          <!--tags:discussion discussible="null" url="/test.do" count="10"/-->
         
        </div>

</div>
</body>
</html:html>

