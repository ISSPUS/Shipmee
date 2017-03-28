<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="date" class="java.util.Date" />


  <body>

	<!-- *****************************************************************************************************************
	 FOOTER
	 ***************************************************************************************************************** -->
	 <div id="footerwrap">
	 	<div class="container">
		 	<div class="row">
		 		<div class="col-lg-4">
		 			<h4><spring:message code="master.page.about.foot" /></h4>
		 			<div class="hline-w"></div>
		 			<p><spring:message code="master.page.about.foot.text" /></p>
		 			
		 			<div class="language text-sample">
		 				<a id="es" href="?language=es"><img id="translate-flag" src="images/es.gif"/>Español</a> | 
						<a id="en" href="?language=en"><img id="translate-flag" src="images/en.gif"/>English</a>
					</div>
					
		 		</div>
		 		<div class="col-lg-4">
		 			<h4><spring:message code="master.page.social.foot" /></h4>
		 			<div class="hline-w"></div>
		 			<p>
		 				<!--<a href="#"><i class="fa fa-dribbble"></i></a>-->
		 				<!--<a href="#"><i class="fa fa-facebook"></i></a>-->
		 				<a href="https://github.com/ISPPUS/Shipmee" target="blank"><i class="fa fa-github"></i></a>
		 				<!--<a href="#"><i class="fa fa-instagram"></i></a>-->
		 				<!--<a href="#"><i class="fa fa-tumblr"></i></a>-->
		 			</p>-
		 		</div>
		 		<div class="col-lg-4">
		 			<h4><spring:message code="master.page.our.foot" /></h4>
		 			<div class="hline-w"></div>
		 			<p>
		 				Av. Reina Mercedes s/n,<br/>
		 				41012, Sevilla,<br/>
		 				<spring:message code="master.page.our.foot.country" /><br/>
		 			</p>
		 		</div>
		 	
		 	</div>
		 	
			<div class="licence text-sample">Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> Shipmee Co., Inc.</div>
	 	</div>
	 </div>

  </body>



