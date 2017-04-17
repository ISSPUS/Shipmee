
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<link rel="stylesheet" href="styles/assets/css/datetimepicker.min.css" />
<script type="text/javascript" src="scripts/moment.js"></script>
<script type="text/javascript" src="scripts/datetimepicker.min.js"></script>

<head>

</head>
  <body>
    <div class="row">
      <div class="col-sm-12 col-md-6 col-md-offset-3 hcenter">
        <div class="card grey darken-1">
          <div class="card-content white"><span class="card-title">Introduzca su nueva contraseña</span>
            <div class="row">
              <form class="col s12" action="/reset" method="POST">
                <div class="row"><br>
                  <input name="user" type="hidden" value="${resetPasswordToken}">
                  <div class="input-field col-sm-12">
                    <input class="validate" id="password" name="password" type="password" required>
                    <label for="password" data-error="La contraseña no puede ser vacía">Contraseña</label>
                  </div><br>
                  <div class="input-field col-sm-12">
                    <input class="validate" id="confirmPassword" name="confirmPassword" type="password" required>
                    <label for="confirmPassword" data-error="La contraseña no puede ser vacía">Repita la contraseña</label><span class="validation"></span>
                  </div>
                </div>
                <button class="btn waves-effect waves-light" type="submit" name="action" style="background-color:#3F51B5">Submit<i class="material-icons right">send</i></button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
	
	