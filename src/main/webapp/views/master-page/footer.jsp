<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="date" class="java.util.Date" />
<style>

.well-cookies {
  min-height: 20px;
  /*background-color: #f5f5f5;*/
  /*background: rgba(0, 0, 0, 0.6);*/
  background :rgba(255, 255, 255, 0.6);
  
  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .05);
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, .05);
  position: fixed; bottom: 0; width: 100%;
  
}

</style>

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
		 			
		 			<p>
		 				<a href="userGeneralTerms/info.do" target="blank"><spring:message code="cgu.title" /></a> |
		 				<a href="userCookies/info.do" target="blank"><spring:message code="cookies.title" /></a>
		 			</p>
		 			
		 			<p>
						<a href="about/info.do"><spring:message code="info.shipmee" /></a>
					</p>
		 			
		 			<div class="language text-sample">
		 				<a id="es" href=""><img id="translate-flag" src="images/es.gif"/>Español</a> |
						<a id="en" href=""><img id="translate-flag" src="images/en.gif"/>English</a>
					</div>
					
		 		</div>
		 		<div class="col-lg-4">
		 			<h4><spring:message code="master.page.social.foot" /></h4>
		 			<div class="hline-w"></div>
		 			<p>
		 				<!--<a href="#"><i class="fa fa-dribbble"></i></a>-->
		 				<!--<a href="#"><i class="fa fa-facebook"></i></a>-->
		 				<a href="https://github.com/Shipmee/Shipmee" target="blank"><i class="fa fa-github"></i></a>
		 				<a href="https://www.youtube.com/channel/UCBzfG9e3KDZYHllfd7yGHgA" target="blank"><i class="fa fa-youtube"></i></a>
		 				<a href="https://goo.gl/forms/slX7WVCkNk4Fo39m2" target="blank"><i class="fa fa-list-alt"></i></a>
		 						 				
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
  
  <script>
	function getCookie(cname) {
    	var name = cname + "=";
    	var ca = document.cookie.split(';');
    	for(var i=0; i<ca.length; i++) {
        	var c = ca[i];
        	while (c.charAt(0)==' ') c = c.substring(1);
        	if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
    	}
    	return "";
	} 
	
	function loadCookies(cookies){
		// First cookie name, First cookie value
		// ; Second cookie name, Second cookie value
		arrayCookies = cookies.split(";");
		for(var i=0; i<arrayCookies.length; i++){
			var cook = arrayCookies[i].split(",");
			var name = cook[0];
			var value = cook[1];
			document.cookie = name + "=" + value + "; path=/ ";
		}
	}
	function hideInfoCookies() {
		$("#infoCookies").hide();
		loadCookies("infoCookies,hide");
	}
	
	if(getCookie("infoCookies")=="hide"){
		hideInfoCookies();
	}
	
	window.onload = function () {
		var documentURL = document.URL.replace(/&language=e[sn]/gi,'');
		documentURL = documentURL.replace(/\?language=e[sn]/gi,'?');
		var append = '';
		
		if (documentURL.includes('?')) {
			append = '&';
		} else {
			append = '?';
		}
		append = append + 'language=e';
		
		var es = document.getElementById('es');
		es.href = documentURL + append + 's';
	
		var en = document.getElementById('en');
		en.href = documentURL + append + 'n';
	}
</script>
