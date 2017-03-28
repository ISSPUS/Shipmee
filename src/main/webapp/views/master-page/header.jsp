<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<body>

	<!-- Fixed navbar -->
	<div class="navbar navbar-default navbar-fixed-top" role="navigation">
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
					<li class="active"><a href=""><spring:message
								code="master.page.home" /></a></li>
					<li><a href="about/info.do"><spring:message
								code="master.page.about" /></a></li>

					<security:authorize access="isAnonymous()">
						<li><a class="fNiv active" href="security/login.do"><spring:message
									code="master.page.login" /></a></li>
					</security:authorize>

					<security:authorize access="isAuthenticated()">
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
							</ul></li>

						<li class="dropdown"><a href="#" class="fNiv dropdown-toggle"
							data-toggle="dropdown"><spring:message
									code="master.page.profile" /> (<security:authentication
									property="principal.username" />)<b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="user/profile.do"><spring:message
											code="master.page.info" /></a></li>
								<li><a href="vehicle/user/list.do"><spring:message
											code="master.page.vehicles" /></a></li>
								<li><a href="alert/user/list.do"><spring:message
											code="master.page.alerts" /></a></li>
								<security:authorize access="hasRole('USER')">
											<li><a href="rating/user/mylist.do">
												<spring:message code="master.page.rating" />
											</a></li>
								</security:authorize>
								<li><a href="j_spring_security_logout"><spring:message
											code="master.page.logout" /> </a></li>
							</ul></li>
					</security:authorize>

					<security:authorize access="hasRole('MODERATOR')">
						<li><a class="fNiv active"
							href="complaint/moderator/list.do?page=1"><spring:message
									code="master.page.complaints" /></a></li>
					</security:authorize>

					<security:authorize access="hasRole('ADMIN')">
						<spring:message code="complaint.omitted" var="omitted" />
						<spring:message code="complaint.mild" var="mild" />
						<spring:message code="complaint.serious" var="serious" />

						<li class="dropdown"><a href="#" class="fNiv dropdown-toggle"
							data-toggle="dropdown"><spring:message
									code="master.page.complaints" /><b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a
									href="complaint/administrator/list.do?type=${serious}&page=1"><jstl:out
											value="${serious}" /></a></li>
								<li><a
									href="complaint/administrator/list.do?type=${mild}&page=1"><jstl:out
											value="${mild}" /></a></li>
								<li><a
									href="complaint/administrator/list.do?type=${omitted}&page=1"><jstl:out
											value="${omitted}" /></a></li>
							</ul></li>
					</security:authorize>

				</ul>
			</div>
		</div>
	</div>
	<div class="navbar navbar-default navbar-fixed-top" role="navigation" style="position: initial;">
		
	</div>



</body>

