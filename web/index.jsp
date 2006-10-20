<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
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
				  
				<h1 style="font-size: 1.6em">Are <span style="color: #355800">you</span> concerned about the state of our transportation system?</h1>
				<p>What do you think about plans to build light rail to Northgate, replace the Alaskan Way Viaduct, or expand I-405? How should we pay for these improvements?
				 What do you think about plans to build light rail to Northgate, replace the Alaskan Way Viaduct, or expand I-405? How should we pay for these improvements?</p>
				 
				 <p><b style="color: #355800">Good question!</b> This website is designed to help you learn about the diverse concerns of other citizens and to understand how these concerns may be related to your own. Finding these relationships is the first step to identifying what kinds of transportation solutions we can all get excited about.</p>
				
				<p align="right" style="font-size: 1.3em"><a href="readmore.jsp">Learn More</a> or <a href="register.do">Register Now</a>!</p>
					<!--tags:discussion discussible="null" url="/test.do" count="10"/-->
				 
				</div>

</div>
</body>
</html:html>

