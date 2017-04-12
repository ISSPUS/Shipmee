
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<div class="container containerLogin">

	<div id="loginbox"
		class="mainbox col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3">


		<div class="panel panel-default">
			<div class="panel-heading">
				<div class="panel-title text-center">
					<spring:message code="login.login" />
				</div>
			</div>

			<div class="panel-body">

				<form:form action="j_spring_security_check" modelAttribute="credentials">


	<div class="input-group">
	<span class="input-group-addon">
	<i class="glyphicon glyphicon-user"></i></span>
	<form:input class="form-control" id="user" code="user.user" path="username" />	
	<form:errors cssClass="error" path="username" />
	</div>
	
	<br />

	<div class="input-group">
	<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
	<form:password code="user.password" path="password" id="password" class="form-control" />	
	<form:errors cssClass="error" path="password" />
	<br />
	</div>

	<jstl:if test="${showError == true}">
		<div class="error">
			<spring:message code="security.login.failed"/>
		</div>
	</jstl:if>
	
	<br />
	<input class="btn btn-theme flotante-derecha" type="submit" value="<spring:message code="security.login" />" />
	
</form:form>
</div>

<div class="frase-box">
						<spring:message code="login.notAccount.before" />
						<b><a href="user/register.do">
							<spring:message code="login.notAccount.link" />
						</a></b>
</div>
	</div>
		
	</div>
</div>


