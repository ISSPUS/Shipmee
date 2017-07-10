<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<style>

.buttonbajo {
    background-color: #4CAF50; /* Green */
    border: none;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
}

.navbar-collapse {
  max-height: none ! important;
}


.acortar{
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
	
}
</style>

	<!-- Fixed navbar -->
	<div class="navbar navbar-default navbar-fixed-top" role="navigation" style="z-index: 3;">
	
	
        
        
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="logoLetras navbar-brand" href="">Shipmee</a>
				<!--<img class="logo2" src="images/logo_circular.png" >-->
				<div class="logoShp">
					<a href=""> <img src="images/logoCircular.svg" class="imgSVG"
						alt="imagen SVG" title="imagen SVG" />

					</a>
				</div>


			</div>
			<div class="navbar-collapse collapse navbar-right">
				<ul class="nav navbar-nav">
					<li><a href=""><spring:message
								code="master.page.home" /></a></li>
					<security:authorize access="isAnonymous()">			
						<li><a href="about/info.do"><spring:message
									code="master.page.about" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('USER')">
						<li class="dropdown"><a href="#" class="fNiv dropdown-toggle"
							data-toggle="dropdown"><spring:message
									code="master.page.shipment" /><b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="shipment/user/create.do"><spring:message
											code="master.page.shipment.create" /></a></li>
								<li><a href="shipment/user/list.do"><spring:message
											code="master.page.shipment.list" /></a></li>
							</ul></li>

						<li class="dropdown"><a href="#" class="fNiv dropdown-toggle"
							data-toggle="dropdown"><spring:message
									code="master.page.route" /><b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="route/user/create.do"><spring:message
											code="master.page.route.create" /></a></li>
								<li><a href="route/user/list.do"><spring:message
											code="master.page.route.list" /></a></li>
							</ul></li>
					</security:authorize>

					<security:authorize access="hasRole('MODERATOR')">
						<li><a class="fNiv active"
							href="complaint/moderator/list.do?page=1"><spring:message
									code="master.page.complaints" /></a></li>
					</security:authorize>

					<security:authorize access="hasRole('ADMIN')">
						<spring:message code="complaint.omitted.capitalLetter" var="omitted" />
						<spring:message code="complaint.mild.capitalLetter" var="mild" />
						<spring:message code="complaint.serious.capitalLetter" var="serious" />

						<li class="dropdown"><a href="#" class="fNiv dropdown-toggle"
							data-toggle="dropdown"><spring:message
									code="master.page.complaints" /><b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a
									href="complaint/administrator/list.do?type=Serious&page=1"><jstl:out
											value="${serious}" /></a></li>
								<li><a
									href="complaint/administrator/list.do?type=Mild&page=1"><jstl:out
											value="${mild}" /></a></li>
								<li><a
									href="complaint/administrator/list.do?type=Omitted&page=1"><jstl:out
											value="${omitted}" /></a></li>
							</ul></li>
							
						<spring:message code="feePayment.accepted.capitalLetter" var="accepted" />
						<spring:message code="feePayment.pending.capitalLetter" var="pending" />
						<spring:message code="feepayment.rejected.capitalLetter" var="rejected" />
						<li class="dropdown"><a href="#" class="fNiv dropdown-toggle"
							data-toggle="dropdown"><spring:message
									code="master.page.feepayments" /><b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a
									href="feepayment/administrator/list.do?type=Accepted&page=1"><jstl:out
											value="${accepted}" /></a></li>
								<li><a
									href="feepayment/administrator/list.do?type=Pending&page=1"><jstl:out
											value="${pending}" /></a></li>
								<li><a
									href="feepayment/administrator/list.do?type=Rejected&page=1"><jstl:out
											value="${rejected}" /></a></li>
							</ul></li>
							
						<li><a href="user/administrator/list.do"><spring:message
								code="master.page.users" /></a></li>
					</security:authorize>
					
					<security:authorize access="isAuthenticated()">
						<li class="dropdown"><a href="#" class="fNiv dropdown-toggle"
							data-toggle="dropdown"><spring:message
									code="master.page.messages" /><b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="message/actor/received.do?page=1"><spring:message
											code="master.page.messages.received" /></a></li>
								<li><a href="message/actor/sent.do?page=1"><spring:message
											code="master.page.messages.sent" /></a></li>
								<li><a href="message/actor/create.do"><spring:message
											code="master.page.messages.create" /></a></li>
							</ul>
						</li>
						<li class="dropdown"><a href="#" class="fNiv dropdown-toggle"
							data-toggle="dropdown"><spring:message
									code="master.page.profile" /><b class="caret"></b></a>
							<ul class="dropdown-menu">
									<li style="text-align:center"><div class="acortar" style="color: #00b3fe;font-weight: 700;font-size: 16px;"><security:authentication
									property="principal.username" /></div><hr style="margin-top: 0px;margin-bottom: 0px;border: 0;border-top: 1px solid #eee;"></li>
								<security:authorize access="hasRole('USER')">
									<li><a href="user/profile.do"><spring:message
												code="master.page.info" /></a></li>
									<li><a href="feepayment/user/list.do?page=1"><spring:message
												code="master.page.feepayments" /></a></li>
									<li><a href="vehicle/user/list.do"><spring:message
												code="master.page.vehicles" /></a></li>
									<li><a href="alert/user/list.do"><spring:message
												code="master.page.alerts" /></a></li>
								</security:authorize>
								<li><a href="j_spring_security_logout"><spring:message
											code="master.page.logout" /> </a></li>
							</ul></li>
					</security:authorize>
					
					<security:authorize access="isAnonymous()">
						<li><a class="fNiv active" href="security/login.do"><spring:message
									code="master.page.login" /></a></li>
					</security:authorize>

				</ul>
			</div>
		</div>
		
	
	</div>
	
	
	<div class="navbar navbar-default navbar-fixed-top" role="navigation" style="position: initial;">
		
	</div>

	<!-- FEEDBACK -->	
	<a title="Feedback" class="cd-popup-trigger button glyphicon glyphicon-bullhorn" data-toggle="modal" data-target="#myFeedback" href="#feedback"
		style="z-index: 5;"></a>
	<!-- Modal -->
	<div class="modal fade" id="myFeedback" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title text-center"><spring:message
											code="master.page.feedback.header" /></h4>
      </div>
				<div class="modal-body" style="height: 700px; padding: 0px;">
					<iframe src="https://docs.google.com/forms/d/e/1FAIpQLSdVn-PiNkXWEMqI36uj_qnB5Lc5jL-4BSxkxuHC0eadYQLG2g/viewform?embedded=true" width="100%" height="100%">Cargando...</iframe>

				</div>
				<div class="text-center" style="background-color: #384452; font-size:18px;">
				<!--<a href="https://goo.gl/forms/slX7WVCkNk4Fo39m2" target="_blank">Enlace a encuesta</a> <button class="buttonbajo" style="background-color: #e7e7e7; color: black;">Cerrar</button>-->
					
				</div>
			</div>
		</div>
	</div>

	<!-- EN COSNTRUCIÓN -->
	<div class="en-construccion text-center" role="alert">
          <a class="alert-link"><span class="glyphicon glyphicon-warning-sign"></span> <spring:message code="master.page.construction" /> <span class="glyphicon glyphicon-warning-sign"></span></a>
        </div>


<script >
$(document).ready(function () {
    var page = window.location;
    $('ul.nav a[href="'+ page +'"]').parentsUntil( ".ul.nav" ).addClass('active');
    $('ul.nav a').filter(function() {
         return this.href == page;
    }).parentsUntil( ".ul.nav" ).addClass('active');
});


</script>