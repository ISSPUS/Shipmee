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

<link rel="stylesheet" href="styles/assets/css/lateral-menu.css" type="text/css">
<link rel="stylesheet" href="styles/assets/css/style-form.css"  type="text/css">

<div class="blue-barra">
	<div class="container">
		<div class="row">
			<h3>
				<spring:message code="alert.new.alert" />
			</h3>
		</div>
		<!-- /row -->
	</div>
</div>


<div class="container">
	<div class="row formulario-sm">
		<form:form action="${url}"
			modelAttribute="actorForm" method="post" class="form-horizontal"
			role="form">

			<!-- Username -->
			<div class="form-group">
				<form:label path="userName" class="control-label col-md-2"
					for="userName">
					<spring:message code="user.username" /> <span title="<spring:message code="user.required" />" style="color:#d9534f;">(*)</span>
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon">
						<form:input path="userName" class="form-control" id="userName" required="true"/>
					</div>
					<form:errors class="error create-message-error" path="userName" />
				</div>
			</div>

			<!-- Name -->
			<div class="form-group">
				<form:label path="name" class="control-label col-md-2"
					for="name">
					<spring:message code="user.name" /> <span title="<spring:message code="user.required" />" style="color:#d9534f;">(*)</span>
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon">
						<form:input path="name" class="form-control" id="name" required="true"/>
					</div>
					<form:errors class="error create-message-error" path="name" />
				</div>
			</div>
			
			<!-- Surname -->
			<div class="form-group">
				<form:label path="surname" class="control-label col-md-2"
					for="surname">
					<spring:message code="user.surname" /> <span title="<spring:message code="user.required" />" style="color:#d9534f;">(*)</span>
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
					<spring:message code="user.email" /> <span title="<spring:message code="user.required" />" style="color:#d9534f;">(*)</span>
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
					<spring:message code="user.birthDate" /> <span title="<spring:message code="user.required" />" style="color:#d9534f;">(*)</span>
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon input-group date fondoDesplegable input-text" id='datetimepicker1'>
						<form:input path="birthDate" class="form-control" id="birthDate" name="fecha" style="backgroud-color: white;" type='text'/>
						<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
					</div>
					<form:errors class="error create-message-error" path="birthDate" />
				</div>
			</div>
			
			
			<!-- DNI -->
			<jstl:if test="${actorForm.id != 0}">
			<div class="form-group">
				<form:label path="dni" class="control-label col-md-2"
					for="dni">
					<spring:message code="user.dni" />
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon">
						<form:input path="dni" class="form-control" id="dni"/>
					</div>
					<form:errors class="error create-message-error" path="dni" />
				</div>
			</div>
			</jstl:if>
			
			

			<!-- PhoneNumber -->
			<jstl:if test="${actorForm.id != 0}">
			<div class="form-group">
				<form:label path="phone" class="control-label col-md-2"
					for="phone">
					<spring:message code="user.phone" />
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon">
						<form:input path="phone" class="form-control" id="phone"/>
					</div>
					<form:errors class="error create-message-error" path="phone" />
				</div>
			</div>
			</jstl:if>
			<!-- Password -->
			<div class="form-group">
				<form:label path="password" class="control-label col-md-2"
					for="password">
					<spring:message code="user.pass" /> <span title="<spring:message code="user.required" />" style="color:#d9534f;">(*)</span>
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon">
						<form:input type="password" path="password" class="form-control" id="password"/>
					</div>
					<form:errors class="error create-message-error" path="password" required="true"/>
				</div>
			</div>
			
			<!-- RepeatedPassword -->
			<div class="form-group">
				<form:label path="repeatedPassword" class="control-label col-md-2"
					for="repeatedPassword">
					<spring:message code="user.repeatPass" /> <span title="<spring:message code="user.required" />" style="color:#d9534f;">(*)</span>
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon">
						<form:input type="password" path="repeatedPassword" class="form-control" id="repeatedPassword"/>
					</div>
					<form:errors class="error create-message-error" path="repeatedPassword" required="true"/>
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
						<form:input path="photo" class="form-control" id="photo" placeholder="Link"/>
					</div>
					<form:errors class="error create-message-error" path="photo" />
				</div>
			</div>
			
			<!-- AcceptLegalCondition -->
			<security:authorize access="isAnonymous()">
				<div class="form-group">
					<div class="col-md-2">
						<form:errors class="error create-message-error" path="acceptLegalCondition" />
					</div>
					<form:label path="acceptLegalCondition" class="control-label col-md-4"
						for="acceptLegalCondition" style="text-align: left;">
						
							<form:checkbox path="acceptLegalCondition" class="" id="acceptLegalCondition"/>
						
						<spring:message code="user.acceptLegalCondition.before" />
						<a href="legalConditions/show.do" target="_blank">
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
				<jstl:choose>
					<jstl:when test="${actorForm.id != 0}">
						<acme:cancel code="rating.cancel" url="user/display.do?userId=${rating.user.id}" />
					</jstl:when>
					<jstl:otherwise>
						<acme:cancel code="rating.cancel" url="" />
					</jstl:otherwise>
				</jstl:choose>
			</div>
			
		</form:form>


	</div>

</div>

<script type="text/javascript">
	
$(function() {
	$('#datetimepicker1').datetimepicker({
		viewMode : 'days',
		format : 'DD/MM/YYYY'
	});
});
              
</script>
