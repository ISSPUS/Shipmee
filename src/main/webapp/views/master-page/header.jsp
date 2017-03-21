<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
 
  <body>

    <!-- Fixed navbar -->
    <div class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="logoLetras navbar-brand" href="">Shipmee</a>
           <!--<img class="logo2" src="images/logo_circular.png" >-->
           <div class="logoShp">
			<a href="" > 
			<img src="images/logoCircular.svg" class="imgSVG" alt="imagen SVG" title="imagen SVG" />
		
			</a>
		</div>
          
          
        </div>
        <div class="navbar-collapse collapse navbar-right">
          <ul class="nav navbar-nav">
            <li class="active"><a href=""><spring:message code="master.page.home" /></a></li>
            <li><a href="about/info.do"><spring:message code="master.page.about" /></a></li> 
            
            <security:authorize access="isAnonymous()">
				<li><a class="fNiv active" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			</security:authorize>
			
            <security:authorize access="isAuthenticated()">
            	<li class="dropdown">
              		<a href="#" class="fNiv dropdown-toggle" data-toggle="dropdown"><spring:message code="master.page.shipment" /><b class="caret"></b></a>
              			<ul class="dropdown-menu">
                			<li><a href="shipment/user/create.do"><spring:message code="master.page.shipment.create" /></a></li>
              			</ul>
            	</li>
            	
            	<li class="dropdown">
              		<a href="#" class="fNiv dropdown-toggle" data-toggle="dropdown"><spring:message code="master.page.route" /><b class="caret"></b></a>
              			<ul class="dropdown-menu">
                			<li><a href="route/user/create.do"><spring:message code="master.page.route.create" /></a></li>
              			</ul>
            	</li>
            
				<li class="dropdown">
              		<a href="#" class="fNiv dropdown-toggle" data-toggle="dropdown"><spring:message code="master.page.profile" /> (<security:authentication property="principal.username" />)<b class="caret"></b></a>
              			<ul class="dropdown-menu">
                			<li><a href="user/profile.do"><spring:message code="master.page.info" /></a></li>
                			<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
              			</ul>
            	</li>
			</security:authorize>
            
          </ul>
        </div>
      </div>
    </div>
    
    

  
  </body>

