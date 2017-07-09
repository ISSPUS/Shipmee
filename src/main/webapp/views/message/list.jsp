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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script async src="scripts/jquery.bootpag.min.js"></script>
<link rel="stylesheet" href="styles/assets/css/lateral-menu.css"
	type="text/css">
<link rel="stylesheet" href="styles/assets/css/style-list.css"
	type="text/css">

<link rel="stylesheet" href="styles/assets/css/style-list.css"
	type="text/css">


<link rel="stylesheet" href="styles/assets/css/style-messages.css"
	type="text/css">
<link rel="stylesheet" href="styles/assets/css/lateral-menu.css"
	type="text/css">
	
<style>
.row .msg{
margin-right: 0px !important

}
.media-photo{
max-width: none !important;
width: 40px;
}
.ellipses{
margin-bottom: 0px;
margin-top: 0px;
}

@media only screen
and (min-device-width : 250px)
and (max-device-width : 480px) {
.message-header {
		padding-left: 1px !important;
	}
}
.menu-lateral-mensajes{
	padding-left: 0px!important;
}
.lista-mensajes{
	padding-left: 0px!important;
}
.title ,
.pendiente{
	overflow-wrap: break-word;
    word-wrap: break-word;
}

</style>
<div class="blue-barra" >
	<div class="container">
		<div class="row">
			<h3>
				<spring:message code="message.message" />
			</h3>
		</div>
		<!-- /row -->
	</div>
</div>

<div class="container">

	<div class="row inbox " style="margin-top: 2%;">
		<div class="col-md-4 menu-lateral-mensajes">
			<div class="panel panel-default">
				<div class="panel-body inbox-menu ">
					<div class="text-center profile-userbuttons">
					<a href="message/actor/create.do" class="btn btn-danger btn-block"><spring:message
							code="message.create" /></a>
					</div>
					<ul>
						<li
							<jstl:if test="${infoMessages eq 'messages.received'}">
													
								class="active"
							</jstl:if>>
							<a href="message/actor/received.do?page=1"><i
								class="fa fa-inbox"></i> <spring:message
									code="messages.received" /> <span class="label label-danger">${total_received}</span></a>
						</li>

						<li
							<jstl:if test="${infoMessages eq 'messages.sent'}">
													
								class="active"
							</jstl:if>>

							<a href="message/actor/sent.do?page=1"><i class="fa fa-inbox"></i>
								<spring:message code="messages.sent" /> <span
								class="label label-danger">${total_sent}</span></a>
						</li>
					</ul>
				</div>
			</div>
		</div>

		<div class="col-md-8 lista-mensajes">
			<jstl:if test="${not empty messages}">
				<jstl:forEach items="${messages}" var="messageRow">
					<div class="row msg">
						<div class=" col-xs-12 table-container panel panel-default">

							<div class="row" style="margin-top: 2%;">
								<div class="col-xs-2 col-sm-1 col-md-1 message-header" style="display: inline-block" >
									<jstl:choose>
										<jstl:when test="${infoMessages eq 'messages.received'}">
											<a href="user/profile.do?userId=${messageRow.sender.id}"
												class="pull-left"> <img src="${messageRow.sender.photo}"
												class="media-photo" style="width: 50px"></a>

										</jstl:when>
										<jstl:otherwise>
											<a href="user/profile.do?userId=${messageRow.recipient.id}"
												class="pull-left"> <img
												src="${messageRow.recipient.photo}" class="media-photo" style="width: 50px"></a>

										</jstl:otherwise>
									</jstl:choose>

								</div>
								<div class="col-xs-10 col-sm-11 col-md-11">

									<div class="car-info">
										<span class="media-meta pull-right"> <fmt:formatDate
												type="both" dateStyle="medium" timeStyle="medium"
												value="${messageRow.moment}" />

										</span>

										<h4 class="title" style="margin-bottom: 1px !important">
											<jstl:choose>
												<jstl:when test="${infoMessages eq 'messages.received'}">
													<a href="user/profile.do?userId=${messageRow.sender.id}"><jstl:out
															value="${messageRow.sender.userAccount.username }" /></a>

												</jstl:when>
												<jstl:otherwise>
													<a href="user/profile.do?userId=${messageRow.recipient.id}"><jstl:out
															value="${messageRow.recipient.userAccount.username }" /></a>

												</jstl:otherwise>
											</jstl:choose>
										</h4>
										<span class="pendiente"> <jstl:out
												value="${messageRow.subject}" />
										</span>

									</div>
								</div>


							</div>
							<div class="row" style="padding: 2%; margin-top: 1%;">
								<p class="ellipses"
									style="overflow-wrap: break-word; word-wrap: break-word; /* Adds a hyphen where the word breaks */ -ms-hyphens: auto; -moz-hyphens: auto; hyphens: auto;">

									${messageRow.body}

								</p>
								<jstl:if test="${infoMessages eq 'messages.received'}">
									<div class="profile-userbuttons" style="text-align: right;"><a
										href="message/actor/create.do?userId=${messageRow.sender.id}&subject=RE: ${messageRow.subject}">
										<span class="btn  btn-primary"> <span
											class="glyphicon glyphicon-send"></span>&nbsp;<spring:message
												code="messages.reply" />
									</span>
									</a></div>
								</jstl:if>
							</div>
						
						</div>

					</div>

				</jstl:forEach>
				<div id="pagination" class="copyright" style="text-align: center;">

			<script>
				$('#pagination').bootpag({
					total : <jstl:out value="${total_pages}"></jstl:out>,
					page : <jstl:out value="${p}"></jstl:out>,
					maxVisible : 3,
					leaps : true,
					firstLastUse : true,
					first : '<',
            last: '>',
					wrapClass : 'pagination',
					activeClass : 'active',
					disabledClass : 'disabled',
					nextClass : 'next',
					prevClass : 'prev',
					lastClass : 'last',
					firstClass : 'first'
				}).on('page', function(event, num) {
					window.location.href = "${urlPage}" + num + "";
					page = 1
				});
			</script>

		</div>
			</jstl:if>
			
			<jstl:if test="${fn:length(messages) ==0}">
				<div class="alert alert-info">
					<strong><spring:message code="messages.anything" /></strong>
				</div>
			</jstl:if>
	
		</div>


	</div>


</div>
