<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Form -->
<link rel="stylesheet" href="styles/assets/css/datetimepicker.min.css" />
<script type="text/javascript" src="scripts/moment.js"></script>
<script type="text/javascript" src="scripts/datetimepicker.min.js"></script>
<link rel="stylesheet" href="styles/assets/css/lateral-menu.css"
	type="text/css">
	
<div class="container">
	<div class="row formulario">
		<form:form action="${url}"
			modelAttribute="actorForm" method="post" class="form-horizontal"
			role="form">

			<!-- Username -->
			<div class="form-group">
				<form:label path="userName" class="control-label col-md-2"
					for="userName">
					<spring:message code="user.username" />
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon">
						<form:input path="userName" class="form-control" id="userName"/>
					</div>
					<form:errors class="error create-message-error" path="userName" />
				</div>
			</div>

			<!-- Name -->
			<div class="form-group">
				<form:label path="name" class="control-label col-md-2"
					for="name">
					<spring:message code="user.name" />
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon">
						<form:input path="name" class="form-control" id="name"/>
					</div>
					<form:errors class="error create-message-error" path="name" />
				</div>
			</div>
			
			<!-- Surname -->
			<div class="form-group">
				<form:label path="surname" class="control-label col-md-2"
					for="surname">
					<spring:message code="user.surname" />
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon">
						<form:input path="surname" class="form-control" id="surname"/>
					</div>
					<form:errors class="error create-message-error" path="surname" />
				</div>
			</div>
			
			<!-- Email -->
			<div class="form-group">
				<form:label path="email" class="control-label col-md-2"
					for="email">
					<spring:message code="user.email" />
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon">
						<form:input type="email" path="email" class="form-control" id="email"/>
					</div>
					<form:errors class="error create-message-error" path="email" />
				</div>
			</div>
			
			<!-- BirthDate -->
			<div class="form-group">
				<form:label path="birthDate" class="control-label col-md-2"
					for="birthDate">
					<spring:message code="user.birthDate" />
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon">
						<form:input path="birthDate" class="form-control" id="birthDate"/>
					</div>
					<form:errors class="error create-message-error" path="birthDate" />
				</div>
			</div>
			
			<!-- DNI -->
			<div class="form-group">
				<form:label path="dni" class="control-label col-md-2"
					for="dni">
					<spring:message code="user.dni" />
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon">
						<form:input path="dni" class="form-control" id="dni"/> No obligatorio
					</div>
					<form:errors class="error create-message-error" path="dni" />
				</div>
			</div>
			
			<!-- PhotoURL -->
			<div class="form-group">
				<form:label path="photo" class="control-label col-md-2"
					for="photo">
					<spring:message code="user.photo" />
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon">
						<form:input path="photo" class="form-control" id="photo"/> No obligatorio
					</div>
					<form:errors class="error create-message-error" path="photo" />
				</div>
			</div>
			
			<!-- PhoneNumber -->
			<div class="form-group">
				<form:label path="phone" class="control-label col-md-2"
					for="phone">
					<spring:message code="user.phone" />
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon">
						<form:input path="phone" class="form-control" id="phone"/> No obligatorio
					</div>
					<form:errors class="error create-message-error" path="phone" />
				</div>
			</div>
			
			<!-- Password -->
			<div class="form-group">
				<form:label path="password" class="control-label col-md-2"
					for="password">
					<spring:message code="user.pass" />
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon">
						<form:input type="password" path="password" class="form-control" id="password"/>
						<jstl:if test="${id != 0}">
							No obligatorio
						</jstl:if>
					</div>
					<form:errors class="error create-message-error" path="password" />
				</div>
			</div>
			
			<!-- RepeatedPassword -->
			<div class="form-group">
				<form:label path="repeatedPassword" class="control-label col-md-2"
					for="repeatedPassword">
					<spring:message code="user.pass" />
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon">
						<form:input type="password" path="repeatedPassword" class="form-control" id="repeatedPassword"/>
						<jstl:if test="${id != 0}">
							No obligatorio
						</jstl:if>
					</div>
					<form:errors class="error create-message-error" path="repeatedPassword" />
				</div>
			</div>
			
			<!-- AcceptLegalCondition -->
			<security:authorize access="isAnonymous()">
				<div class="form-group">
					<div class="col-md-2">
						<div class="inner-addon">
							<form:checkbox path="acceptLegalCondition" class="form-control" id="acceptLegalCondition"/>
						</div>
						<form:errors class="error create-message-error" path="acceptLegalCondition" />
					</div>
					<form:label path="acceptLegalCondition" class="control-label col-md-4"
						for="acceptLegalCondition">
						<spring:message code="user.acceptLegalCondition.before" />
						<a href="legalConditions/show.do">
							<spring:message code="user.acceptLegalCondition.link" />
						</a>
						<spring:message code="user.acceptLegalCondition.after" />
					</form:label>
				</div>
			</security:authorize>
			
			<!-- Buttons -->
			<div class="form-group text-center profile-userbuttons">
				<!-- Action buttons -->
				<acme:submit name="save" code="rating.save" />

				<acme:cancel code="rating.cancel" url="user/display.do?userId=${rating.user.id}" />

			</div>
			
		</form:form>


	</div>

</div>
