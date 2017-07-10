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
<script type="text/javascript" src="scripts/moment.min.js"></script>
<script type="text/javascript" src="scripts/datetimepicker.min.js"></script>

<link rel="stylesheet" href="styles/assets/css/lateral-menu.css" type="text/css">
<link rel="stylesheet" href="styles/assets/css/style-form.css"  type="text/css">
<script type="text/javascript" src="scripts/es.min.js"></script>

<div class="blue-barra">
	<div class="container">
		<div class="row">
			<h3>
				<jstl:if test="${actorForm.id == 0}">
					<spring:message code="user.new.user" />
				</jstl:if>
				<jstl:if test="${actorForm.id != 0}">
					<spring:message code="user.edit.user" />
				</jstl:if>
			</h3>
		</div>
		<!-- /row -->
	</div>
</div>


				<div class="modal-dialog">
				
					<div class="modal-content" style="padding:3%; border-color:#f1f3fa;">
						<div>
						<span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
						<span><spring:message code="user.required.info" /></span>
						</div>
						<jstl:if test="${actorForm.id != 0}">
				
						<div>
					<span title="<spring:message code="user.verifyUser" />" class="glyphicon glyphicon-ok-circle" style="color:#1abc9c;"></span>
						<span><spring:message code="user.verifyUser.info" /></span>
						</div>
					</jstl:if>
					
					</div>
				</div>
			

<div class="container">
	<div class="row formulario">
	
		
	
		<form:form action="${url}"
			modelAttribute="actorForm" method="post" class="form-horizontal"
			role="form" enctype="multipart/form-data">
			
			<form:hidden path="id" />
			 <jstl:if test="${actorForm.id == 0}">
				<form:hidden path="localePreferences"/>
			</jstl:if>

			<!-- Username -->
			<div class="form-group">
				<form:label path="userName" class="control-label col-md-2"
					for="userName">
					<spring:message code="user.username" /> <span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon">
						<form:input path="userName" class="form-control" id="userName" required="true"   maxlength="32" minlength="5"/>
					</div>
					<form:errors class="error create-message-error" path="userName" />
				</div>
			</div>

			<!-- Name -->
			<div class="form-group">
				<form:label path="name" class="control-label col-md-2"
					for="name">
					<spring:message code="user.name" /> <span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
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
					<spring:message code="user.surname" /> <span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon">
						<form:input path="surname" class="form-control" id="surname" required="required"/>
					</div>
					<form:errors class="error create-message-error" path="surname" />
				</div>
			</div>
			
			<!-- Email -->
			<div class="form-group">
				<form:label path="email" class="control-label col-md-2"
					for="email">
					<spring:message code="user.email" /> <span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon">
						<form:input type="email" path="email" class="form-control" id="email" required="required"/>
					</div>
					<form:errors class="error create-message-error" path="email" />
				</div>
			</div>
			
			<!-- BirthDate -->
			<div class="form-group">
				<form:label path="birthDate" class="control-label col-md-2"
					for="birthDate">
					<spring:message code="user.birthDate" /> <span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon input-group date fondoDesplegable input-text" id='datetimepicker1'>
						<form:input path="birthDate" class="form-control" id="birthDate" name="fecha" style="backgroud-color: white;" type='text' required="required"/>
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
					<spring:message code="user.dni" /> <span title="<spring:message code="user.verifyUser" />" class="glyphicon glyphicon-ok-circle" style="color:#1abc9c;"></span>
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
					<spring:message code="user.phone" /> <span title="<spring:message code="user.verifyUser" />" class="glyphicon glyphicon-ok-circle" style="color:#1abc9c;"></span>
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
					<spring:message code="user.pass" />
					<jstl:if test="${actorForm.id == 0}">
						<span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
					</jstl:if>
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
					<spring:message code="user.repeatPass" />
					<jstl:if test="${actorForm.id == 0}">
						<span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
					</jstl:if>
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon">
						<form:input type="password" path="repeatedPassword" class="form-control" id="repeatedPassword"/>
					</div>
					<form:errors class="error create-message-error" path="repeatedPassword" required="true"/>
				</div>
			</div>
			
			<!-- LocalePreferences -->
			<jstl:if test="${actorForm.id != 0}">
			<div class="form-group">
				<form:label path="localePreferences" class="control-label col-md-2"
					for="localePreferences">
					<spring:message code="user.localePreferences" />
					<span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
				</form:label>

				<div class="col-md-8">
					<div class="inner-addon">
						<form:select path="localePreferences" class="form-control" id="localePreferences">
							<form:option value="es"><spring:message code="user.localePreferences.spanish" /></form:option>
							<form:option value="en"><spring:message code="user.localePreferences.english" /></form:option>
						</form:select>
					</div>
					<form:errors class="error create-message-error" path="localePreferences" required="true"/>
				</div>
			</div>
			</jstl:if>
			
			
			<!-- PhotoURL -->
			<jstl:if test="${actorForm.id != 0}">
			<div class="form-group">
				<form:label path="photo" class="control-label col-md-2"
					for="photo">
					<spring:message code="user.photo" /> <span title="<spring:message code="user.verifyUser" />" class="glyphicon glyphicon-ok-circle" style="color:#1abc9c;"></span>
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon">
						<form:input type="file" path="photo"
							class="form-control btn btn-default btn-file" id="photo" accept=".jpg,.jpeg,.png"/>
					</div>
					<form:errors class="error create-message-error" path="photo" />
				</div>
			</div>
			</jstl:if>
			
			<!-- DNIPhotoURL -->
			<jstl:if test="${actorForm.id != 0}">
				<div class="form-group">
					<form:label path="dniPhoto" class="control-label col-md-2"
						for="dniPhoto">
						<spring:message code="user.dniPhoto" /> <span title="<spring:message code="user.verifyUser" />" class="glyphicon glyphicon-ok-circle" style="color:#1abc9c;"></span>
					</form:label>
					<div class="col-md-8">
						<div class="inner-addon">
							<form:input type="file" path="dniPhoto" class="form-control" id="dniPhoto" placeholder="Link" accept=".jpg,.jpeg,.png"/>
						</div>
						<form:errors class="error create-message-error" path="dniPhoto" />
					</div>
				</div>
			</jstl:if>
			
		
			
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
						<a href="userGeneralTerms/info.do" target="_blank">
							<spring:message code="user.acceptLegalCondition.link" />
						</a>
						<spring:message code="user.acceptLegalCondition.after" />
					</form:label>
				</div>
			</security:authorize>

			


			<!-- Buttons -->
			<div class="form-group text-center profile-userbuttons">
				<!-- Action buttons -->
				<button type="submit" name="save" class="btn  btn-primary">
					<span class="glyphicon glyphicon-floppy-disk"></span>
					<spring:message code="rating.save" />
				</button>
				<jstl:choose>
					<jstl:when test="${actorForm.id != 0}">
						<acme:cancel code="rating.cancel" url="user/profile.do?userId=${actorForm.id}" />
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
	language = getCookie("language");
	$('#datetimepicker1').datetimepicker({
		viewMode : 'days',
		locale: language,
		format : 'DD/MM/YYYY'
	});
});    

</script>

<jstl:if test="${actorForm.id != 0}">
	<script type="text/javascript">
		var inputImage1 = document.getElementById('photo');
		inputImage1.onchange = function(e) {
	    	if (document.contains(document.getElementById("photo.errors"))) {
	            document.getElementById("photo.errors").remove();
			}
			
			extension = this.value.split(".");
		    var nameFile = extension[extension.length-1];
		    switch (nameFile) {
			case 'jpg':
			case 'jpeg':
			case 'png':
				break;
			default:
				var mssg = '<spring:message code="message.error.imageUpload.incompatibleType" />';
				inputImage1.insertAdjacentHTML('afterend',
						'<span id="photo.errors" class="error">' + mssg
								+ '</span>');
				this.value = '';
			}
		};

		var inputImage2 = document.getElementById('dniPhoto');
		inputImage2.onchange = function(e) {
	    	if (document.contains(document.getElementById("dniPhoto.errors"))) {
	            document.getElementById("dniPhoto.errors").remove();
			}
			extension = this.value.split(".");
		    var nameFile = extension[extension.length-1];
		    switch (nameFile) {
			case 'jpg':
			case 'jpeg':
			case 'png':
				break;
			default:
				var mssg = '<spring:message code="message.error.imageUpload.incompatibleType" />';
				inputImage2.insertAdjacentHTML('afterend',
						'<span id="dniPhoto.errors" class="error">' + mssg
								+ '</span>');
				this.value = '';
			}
		};
	</script>
</jstl:if>