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
<link rel="stylesheet" href="styles/assets/css/lateral-menu.css"
	type="text/css">
<link rel="stylesheet" href="styles/assets/css/style-form.css"  type="text/css">


<style>
.date .dropdown-menu{

	background-color:white ! important;
}

</style>



<div class="blue-barra">
	    <div class="container">
			<div class="row">
				<h3><spring:message code="complaint.new.complaint" /></h3>
			</div><!-- /row -->
	    </div>
	</div>


<div class="container">


<div class="span3 text-center"
				style="margin-top: 20px;">
				
				<a href="user/profile.do?userId=${shipment.creator.id}"> 
					<jstl:choose>
						<jstl:when test="${not empty complaint.involved.photo}">
							<jstl:set var="imageUser" value="${complaint.involved.photo}" />
						</jstl:when>
						<jstl:otherwise>
							<jstl:set var="imageUser" value="images/anonymous.png" />
						</jstl:otherwise>
					</jstl:choose> 
					<img src="${imageUser}" name="aboutme" width="140" height="140"border="0" class="img-circle">

				</a>
				<h3>
					<a href="user/profile.do?userId=${complaint.involved.id}">${complaint.involved.name}</a>
				</h3>
			</div>
			


	<div class="row formulario-sm">
	
	<spring:message code="complaint.confirm.create" var="mensaje_confirmacion"/>
	
		<form:form action="complaint/user/create.do" modelAttribute="complaint" method="post"
			class="form-horizontal" role="form" onSubmit="return confirm('${mensaje_confirmacion}')">

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="type" />
			<form:hidden path="moderator" />
			<form:hidden path="creator" />
			<form:hidden path="involved" />

			<div class="form-group">
				<form:label path="explanation" class="control-label col-md-2"
					for="explanation">
					<spring:message code="complaint.explanation" />
				</form:label>
				<div class="col-md-8">
					<form:textarea path="explanation" class="form-control" id="explanation" required="required"/>
					<form:errors class="error create-message-error" path="explanation" />
				</div>
			</div>
			
			<jstl:if test="${messageError != null}">
				<div class="error" style="text-align: center;">
					<spring:message code="${messageError}"/>
					<br/><br/>
				</div>
			</jstl:if>

						<!-- Action buttons -->
						
		<div class="text-center profile-userbuttons">		
			<button type="submit" name="create" class="btn button-delete">
				<spring:message code="complaint.create" />
			</button>
			<acme:cancel code="complaint.cancel" url="user/profile.do?userId=${complaint.involved.id}" />
		</div>	
		</form:form>


	</div>
</div>
