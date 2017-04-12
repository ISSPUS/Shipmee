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

<link rel="stylesheet" href="styles/assets/css/style-edits-offers.css"  type="text/css">
<link rel="stylesheet" href="styles/assets/css/style-form.css"  type="text/css">


<div class="blue-barra" style="padding-top: 0.75%;padding-bottom: 0.75%;">
	<div class="container">
		<div class="row">
			<h3>
			<spring:message code="rating.create.for" />
			
			<a href="user/profile.do?userId=${rating.user.id}">
				<jstl:out value="${rating.user.name}" />
				<jstl:out value="${rating.user.surname}" />
				(<jstl:out value="${rating.user.userAccount.username}" />)
			</a>
			</h3>
		</div>
		<!-- /row -->
	</div>
</div>

<div class="container">
	<div class="row formulario">
		<form:form action="rating/user/edit.do"
			modelAttribute="rating" method="post" class="form-horizontal"
			role="form">
			<form:hidden path="user" />

			<div class="form-group">
				<form:label path="value" class="control-label col-md-2"
					for="amount">
					<spring:message code="rating.value" />
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon left-addon input-price">
						<form:input path="value" class="form-control" id="value" min="0" max="5"
							step="1" type="number" />
					</div>
					<form:errors class="error create-message-error" path="value" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="comment" class="control-label col-md-2"
					for="itemPicture">
					<spring:message code="rating.comment" />
				</form:label>
				<div class="col-md-8">
					<form:textarea path="comment" class="form-control"
						id="comment" />
					<form:errors class="error create-message-error" path="comment" />
				</div>
			</div>
			<div class="form-group text-center profile-userbuttons">
				<!-- Action buttons -->
				<button type="submit" name="save" class="btn  btn-primary">
					<span class="glyphicon glyphicon-floppy-disk"></span>
					<spring:message code="rating.save" />
				</button>

				<acme:cancel code="rating.cancel" url="user/profile.do?userId=${rating.user.id}" />

			</div>
			
		</form:form>


	</div>

</div>

