
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<link rel="stylesheet" href="styles/assets/css/datetimepicker.min.css" />
<script async type="text/javascript" src="scripts/moment.min.js"></script>
<script async type="text/javascript" src="scripts/datetimepicker.min.js"></script>
<script async type="text/javascript" src="scripts/es.min.js"></script>

<head>

</head>

<body>

<div id="headerwrap">
	    <div class="container">
			<div class="row">
				<div class="col-lg-8 col-lg-offset-2">
				<div class="titlePrincipal">
					<h3><spring:message code="welcome.sentence" /></h3>
					<h1><spring:message code="welcome.title" /></h1>
					</div>
					<h4><spring:message code="welcome.sentence2" /></h4>
								
				</div>
				<div class="col-lg-8 col-lg-offset-2 himg">

				</div>
			</div>


    <div id="panel" class="col-lg-offset-4 col-lg-4 col-md-offset-3 col-md-6 col-sm-offset-3 col-sm-6 col-xs-offset-1 col-xs-10" >
        <h3 class="titleSearch"><spring:message code="welcome.searcher.title" /></h3>
        <form class="form">
        
         	<div class="choose btn-group btn-toggle" data-toggle="buttons">
						<label class="btn btn-lg btn-primary active btn-default"> 
						<input name="options" value="shipment" checked type="radio"><spring:message code="welcome.searcher.choose1" /></label> 
						<label class="btn btn-lg btn-default"> 
						<input name="options" value="route" type="radio"><spring:message code="welcome.searcher.choose2" /></label>
			</div>
			

			
            <div class="group">
                <input id="origin" class="camp" type="text" placeholder="" required>
                <span class="highlight"></span>
                <span class="bar"></span>
                <label for="origin"><span class="glyphicon glyphicon-pushpin">&nbsp;</span> <spring:message code="welcome.searcher.origin" /> </label>
            </div>
                        

            <div class="group">
                <input id="destination" class="camp" type="text" placeholder=" " required>
                <span class="highlight"></span>
                <span class="bar"></span>
                <label for="destination"><span class="glyphicon glyphicon-pushpin">&nbsp;</span><spring:message code="welcome.searcher.destination" /></label>
            </div>
           
            <div class="group">
   				<div class='input-group date fondoDesplegable input-text' id='datetimepicker1'>
				<input id="date" name="fecha" style="backgroud-color: white;" type='text' class="form-control" /> 
				<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				</div>
			</div>
			       
            <div class="group">
               <button onclick="return sendForm()" type="submit" class="btnSearch btn-lg btnSample btn-block"><spring:message code="welcome.search" /> <span class="glyphicon glyphicon-search"></span></button>
       		</div> 
					
							
					
		</form>


    	</div>


		</div>
	    	    
   
	</div>
	
	
	
	
	 <div id="service">
	 	<div class="container">
 			<div class="row centered">
 				<div class="col-md-4">
 					<i class="fa fa-heart-o"></i>
 					<h4><spring:message code="welcome.foryou" /></h4>
 					<p><spring:message code="welcome.foryou.text" /></p>
 				</div>
 				<div class="col-md-4">
 					<i class="fa fa-flask"></i>
 					<h4><spring:message code="welcome.experiment" /></h4>
 					<p><spring:message code="welcome.experiment.text" /></p>
 				</div>
 				<div class="col-md-4">
 					<i class="fa fa-trophy"></i>
 					<h4><spring:message code="welcome.quality" /></h4>
 					<p><spring:message code="welcome.quality.text" /></p>
 				</div>		 				
	 		</div>
	 	</div>
	 </div>




<script type="text/javascript">

	function initialize() {

	var input = document.getElementById('origin');
	var input2 = document.getElementById('destination');
	var options = {
		types: ['(cities)'],
		componentRestrictions: {country: 'es'}
	};
	var autocomplete = new google.maps.places.Autocomplete(input, options);
	var autocomplete = new google.maps.places.Autocomplete(input2, options);
	}
	
	
	google.maps.event.addDomListener(window, 'load', initialize);
	
	$(function() {
		language = getCookie("language");
		$('#datetimepicker1').datetimepicker({
			viewMode : 'days',
			locale: language,
			format : 'DD/MM/YYYY'
		});
	});
	
	

function sendForm(){
	var origin = document.getElementById('origin').value;
	var destination = document.getElementById('destination').value;
	var date = document.getElementById('date').value;
	if(origin!=='' && destination!==''){
		if($('input[name=options]:checked').val()==='shipment'){
			window.location = 'shipment/search.do?origin='+origin+'&destination='+destination+'&date='+date;
		}else{
			window.location = 'route/search.do?origin='+origin+'&destination='+destination+'&date='+date;
		}
		return false;
	}else{
		return true;
	}
}


$('.btn-toggle').click(function() {
    $(this).children('.btn').toggleClass('active');  
    $(this).children('.btn').toggleClass('btn-primary');
});


      
</script>	
</body>	