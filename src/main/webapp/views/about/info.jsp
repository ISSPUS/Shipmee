<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<style>

.hline{

margin-bottom: 10%;

}

</style>

	<div class="blue-barra">
	    <div class="container">
			<div class="row">
				<h3><spring:message code="info.shipmee" /></h3>
			</div><!-- /row -->
	    </div>
	</div>

	 <div class="container mtb">
	 	<div class="row">
	 		<div class="col-lg-6">
	 			<img class="foto-shipmee" src="images/cajalogoV2.png" alt="">
	 		</div>
	 		
	 		<div class="col-lg-6">
		 		<p><spring:message code="info.about.text1" /></p>
		 		<p><spring:message code="info.about.text2" /></p>
		 		<p><span style="font-size:100%;font-weight: bold;"><spring:message code="info.about.text3" />:</span></p>
		 		<i class="fa fa-envelope fa-2x"></i> <a href="mailto:shipmee.contact@gmail.com" style="font-size:150%;">shipmee.contact@gmail.com</a>
		 		<!--<p><br/><a href="contact.html" class="btn btn-theme">Contact Us</a></p>-->
	 		</div>
	 	</div>


	<div class="row centered">
		<h3 class="mb"><spring:message code="equipo" /></h3>

		<div class="col-lg-4 col-md-4 col-sm-4">
			<div class="he-wrap tpl6">
				<img src="https://avatars2.githubusercontent.com/u/12049827?v=3&s=460" alt="">
				<div class="he-view">
					<div class="bg a0" data-animate="fadeIn">
						<h3 class="a1" data-animate="fadeInDown"><spring:message code="contactar" /></h3>
						<a href="#" class="dmbutton a2" data-animate="fadeInUp"><i
							class="fa fa-envelope"></i></a> <a href="#" class="dmbutton a2"
							data-animate="fadeInUp"><i class="fa fa-twitter"></i></a>
					</div>
					<!-- he bg -->
				</div>
				<!-- he view -->
			</div>
			<!-- he wrap -->
			<h4>Manuel López Ruiz</h4>
			<h5 class="ctitle"><spring:message code="perfil.manolo" /></h5>
			<div class="hline"></div>
		</div>

		<div class="col-lg-4 col-md-4 col-sm-4">
			<div class="he-wrap tpl6">
				<img src="https://avatars1.githubusercontent.com/u/8267403?v=3&s=460" alt="">
				<div class="he-view">
					<div class="bg a0" data-animate="fadeIn">
						<h3 class="a1" data-animate="fadeInDown"><spring:message code="contactar" /></h3>
						<a href="#" class="dmbutton a2" data-animate="fadeInUp"><i
							class="fa fa-envelope"></i></a> <a href="#" class="dmbutton a2"
							data-animate="fadeInUp"><i class="fa fa-twitter"></i></a>
					</div>
					<!-- he bg -->
				</div>
				<!-- he view -->
			</div>
			<!-- he wrap -->
			<h4>Juan Ramón Rodríguez</h4>
			<h5 class="ctitle"><spring:message code="perfil.juanrra" /></h5>
			<div class="hline"></div>
		</div>

		<div class="col-lg-4 col-md-4 col-sm-4">
			<div class="he-wrap tpl6">
				<img src="https://avatars0.githubusercontent.com/u/22616365?v=3&s=460" alt="">
				<div class="he-view">
					<div class="bg a0" data-animate="fadeIn">
						<h3 class="a1" data-animate="fadeInDown"><spring:message code="contactar" /></h3>
						<a href="#" class="dmbutton a2" data-animate="fadeInUp"><i
							class="fa fa-envelope"></i></a> <a href="#" class="dmbutton a2"
							data-animate="fadeInUp"><i class="fa fa-twitter"></i></a>
					</div>
					<!-- he bg -->
				</div>
				<!-- he view -->
			</div>
			<!-- he wrap -->
			<h4>Bartolomé Márquez</h4>
			<h5 class="ctitle"><spring:message code="perfil.bart" /></h5>
			<div class="hline"></div>
		</div>

		<div class="col-lg-4 col-md-4 col-sm-4">
			<div class="he-wrap tpl6">
				<img src="https://avatars3.githubusercontent.com/u/6894925?v=3&s=460" alt="">
				<div class="he-view">
					<div class="bg a0" data-animate="fadeIn">
						<h3 class="a1" data-animate="fadeInDown"><spring:message code="contactar" /></h3>
						<a href="#" class="dmbutton a2" data-animate="fadeInUp"><i
							class="fa fa-envelope"></i></a> <a href="#" class="dmbutton a2"
							data-animate="fadeInUp"><i class="fa fa-twitter"></i></a>
					</div>
					<!-- he bg -->
				</div>
				<!-- he view -->
			</div>
			<!-- he wrap -->
			<h4>José Antonio Rodríguez</h4>
			<h5 class="ctitle"><spring:message code="perfil.jose" /></h5>
			<div class="hline"></div>
		</div>

		<div class="col-lg-4 col-md-4 col-sm-4">
			<div class="he-wrap tpl6">
				<img src="https://avatars3.githubusercontent.com/u/11299118?v=3&s=460" alt="">
				<div class="he-view">
					<div class="bg a0" data-animate="fadeIn">
						<h3 class="a1" data-animate="fadeInDown"><spring:message code="contactar" /></h3>
						<a href="#" class="dmbutton a2" data-animate="fadeInUp"><i
							class="fa fa-envelope"></i></a> <a href="#" class="dmbutton a2"
							data-animate="fadeInUp"><i class="fa fa-twitter"></i></a>
					</div>
					<!-- he bg -->
				</div>
				<!-- he view -->
			</div>
			<!-- he wrap -->
			<h4>Miguel Rodríguez</h4>
			<h5 class="ctitle"><spring:message code="perfil.migue" /></h5>
			<div class="hline"></div>
		</div>

		<div class="col-lg-4 col-md-4 col-sm-4">
			<div class="he-wrap tpl6">
				<img src="https://avatars1.githubusercontent.com/u/12424182?v=3&s=400" alt="">
				<div class="he-view">
					<div class="bg a0" data-animate="fadeIn">
						<h3 class="a1" data-animate="fadeInDown"><spring:message code="contactar" /></h3>
						<a href="#" class="dmbutton a2" data-animate="fadeInUp"><i
							class="fa fa-envelope"></i></a> <a href="#" class="dmbutton a2"
							data-animate="fadeInUp"><i class="fa fa-twitter"></i></a>
					</div>
					<!-- he bg -->
				</div>
				<!-- he view -->
			</div>
			<!-- he wrap -->
			<h4>Guillermo Alcalá</h4>
			<h5 class="ctitle"><spring:message code="perfil.guille" /></h5>
			<div class="hline"></div>
		</div>

	</div>


</div>

	
	 <div id="twrap">
	 	<div class="container centered">
	 		<div class="row">
	 			<div class="col-lg-8 col-lg-offset-2">
	 			<i class="fa fa-comment-o"></i>
	 			<p>Nowadays we spend almost more time from one place to another than in one place, we must take advantage of this time and that better than helping people.</p>
	 			<h4><br/>Bartolomé Márquez</h4>
	 			<p>CHIEF DESIGNER</p>
	 			</div>
	 		</div>
	 	</div>
	 </div>
