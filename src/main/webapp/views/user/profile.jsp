<%--
 * action-2.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/jquery.simplePagination.js"></script>
<link rel="stylesheet" href="styles/assets/css/style-profile.css" type="text/css">


<div class="container">
	<div class="row">
		<div class="col-md-offset-2 col-md-8 col-lg-offset-3 col-lg-6">
			<div class="well profile-user profile">
			<a href="complaint/user/create.do?userId=${user.id}"><i class="glyphicon glyphicon-exclamation-sign img-report" title="Complaint"></i></a>
				<div class="modal-body text-center">

					<jstl:choose>
						<jstl:when test="${not empty user.photo}">
							<jstl:set var="imageUser" value="${user.photo}" />
						</jstl:when>
						<jstl:otherwise>
							<jstl:set var="imageUser" value="images/anonymous.png" />
						</jstl:otherwise>
					</jstl:choose>
					<img src="${imageUser}" name="aboutme" width="140" height="140" border="0" class="img-circle">	
					
                	
                    
                    <h3 class="media-heading profile-name">${user.name} <small>
                    <jstl:if test="${user.isVerified}">
                    	<i class="glyphicon glyphicon-ok img-verified" title="Verified"></i>
					</jstl:if>
						
                    	
                    
                    </small></h3>
                    <span><strong><spring:message code="user.achievements" />: </strong></span>
                        <span class="label label-warning">Nuevo</span>
                        <span class="label label-info">Primer envío</span>
                        <span class="label label-info">Primer viaje</span>
                        <span class="label label-success">Experimentado</span>
                    <hr>
					
					<div class="titulo-apartado"><strong><spring:message code="user.public" /></strong></div>
					<div class="datos text-left">
						<span> <strong><spring:message code="user.name" />: </strong>${user.name} ${user.surname}</span> 
						<br/>
						<span> <strong><spring:message code="user.birthDate" />: </strong><fmt:formatDate value="${user.birthDate}" pattern="dd/MM/yyyy '-' HH:mm" /></span>
						<br/>
					</div>

					<jstl:if test="${isPrincipal}">
                    	
					<div class="titulo-apartado"><strong><spring:message code="user.private" /></strong> <i class="glyphicon glyphicon-lock"></i></div>
					<div class="datos text-left">
						<span> <strong><spring:message code="user.username" />: </strong><security:authentication property="principal.username" /></span>
						<br/>
						<span> <strong><spring:message code="user.email" />: </strong>${user.email}</span>
						<br/>
						<span> <strong><spring:message code="user.phone" />: </strong>${user.phone}</span>
						<br/>
						<span> <strong><spring:message code="user.dni" />: </strong>${user.dni}</span>
					</div>
					</jstl:if>
					
					<hr>
					<div class="col-xs-12 col-sm-4 emphasis" id="clickeable" onclick="location.href='shipments/list.do?userId=${user.id}';" >
						<h2>
							<strong>
								<jstl:out value=" ${shipmentsCreated}" />
							</strong>
						</h2>
						<p>
							<small>
								<spring:message code="profile.shipments" />
							</small>
						</p>
					</div>
					<div class="col-xs-12 col-sm-4 emphasis"  id="clickeable" onclick="location.href='route/list.do?userId=${user.id}';">
						<h2>
							<strong>
								<jstl:out value=" ${routesCreated}" />
							</strong>
						</h2>
						<p>
							<small>
								<spring:message code="profile.routes" />
							</small>
						</p>
					</div>
					<div class="col-xs-12 col-sm-4 emphasis"  id="clickeable" onclick="location.href='shipment/list.do?userId=${user.id}';">
						<h2>
							<strong>
								<jstl:out value=" ${commentsCreated}" />
							</strong>
						</h2>
						<p>
							<small>
								<spring:message code="user.commentsCreated" />
							</small>
						</p>
					</div>
				</div>



				</div>
			</div>
		</div>
	</div>
	
<br />
