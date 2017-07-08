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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<link href='https://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css'>
<link
	href="styles/assets/css/font-awesome.min.css"
	rel="stylesheet">

    <link rel="stylesheet" href="styles/assets/css/style-lists-offers.css"  type="text/css">
<script async src="scripts/jquery.bootpag.min.js"></script>

<jstl:choose>					
<jstl:when test="${!empty ratings.content && userReceivedId > 0}">
	<div class="blue-barra"
		style="padding-top: 0.75%; padding-bottom: 0.75%;">
		<div class="container">
			<div class="row">
				<h3>
				<jstl:if test="${userReceivedId > 0}">
					<spring:message code="rating.list.for" />
					<a href="user/profile.do?userId=${ratings.content[0].user.id}">
						<jstl:out value="${ratings.content[0].user.name}" />
						<jstl:out value="${ratings.content[0].user.surname}" />
						(<jstl:out value="${ratings.content[0].user.userAccount.username}" />)
					</a>
				</jstl:if>
				</h3>
			</div>
			<!-- /row -->
		</div>
	</div>
</jstl:when>
<jstl:when test="${!empty ratings.content && authorId > 0}">
	<div class="blue-barra"
		style="padding-top: 0.75%; padding-bottom: 0.75%;">
		<div class="container">
			<div class="row">
				<h3>
				<jstl:if test="${authorId > 0}">
					<spring:message code="rating.list.by" />
					<a href="user/profile.do?userId=${ratings.content[0].author.id}">
						<jstl:out value="${ratings.content[0].author.name}" />
						<jstl:out value="${ratings.content[0].author.surname}" />
						(<jstl:out value="${ratings.content[0].author.userAccount.username}" />)
					</a>
				</jstl:if>
				</h3>
			</div>
			<!-- /row -->
		</div>
	</div>
</jstl:when>
<jstl:when test="${empty ratings.content}">
	<div class="blue-barra"
		style="padding-top: 0.75%; padding-bottom: 0.75%;">
		<div class="container">
			<div class="row">
				<h3>
				<spring:message code="rating.list" />

				</h3>
			</div>
			<!-- /row -->
		</div>
	</div>
</jstl:when>
</jstl:choose>

<jstl:if test="${empty ratings.content}">
	<div class="container" style="margin-top:25px">
			<div class="alert alert-info">
				<strong><spring:message code="rating.anything" /></strong>
			</div>
		</div>
</jstl:if>

<div class="container">
	<jstl:forEach items="${ratings.content}" var="ratingRow">
		<div class="row"
			style="margin-top: 2%; margin-bottom: 2%; margin-right: 0 !important;">
			<div class="col-xs-12 col-lg-9 offer-shipment"
				style="float: none; margin: 0 auto;">
				<div class="row perfil-info-offer">
					<div class="img-perfil-offer col-xs-4 col-sm-3 col-lg-2">
						<a href="user/profile.do?userId=${ratingRow.author.id}">
							<img src="${ratingRow.author.photo}" class="img-thumbnail  profile-offer-img" style="margin-top: 8%;">
						</a>
					</div>
					<div>
						<p><b><spring:message code="rating.author" /> : </b><a href="user/profile.do?userId=${ratingRow.author.id}"><jstl:out value="${ratingRow.author.userAccount.username}" /></a></p>
					</div>
					<div>
						<p><b><spring:message code="rating.user" /> : </b><a href="user/profile.do?userId=${ratingRow.user.id}"><jstl:out value="${ratingRow.user.userAccount.username}" /></a></p>
					</div>
					<div>
						<p><b><spring:message code="rating.value" /> : </b><a><jstl:out value="${ratingRow.value}" /></a></p>
					</div>
					<div style="padding-left: 1%;padding-right: 2%;">
						<p><b><spring:message code="rating.comment" /> : </b><jstl:out value="${ratingRow.comment}" /></p>
					</div>
					<div class="rfecha separador"></div><span class="cretaion-date media-meta pull-right" style="margin-right: 1%;"><fmt:formatDate value="${ratingRow.createdDate}" pattern="dd/MM/yyyy HH:mm" /></span>
						
 				</div>
			</div>
		</div>

	</jstl:forEach>

</div>

<div id="pagination" class="copyright" style="text-align: center;">

	<script>
		$('#pagination').bootpag({
			total : <jstl:out value="${total_pages}"></jstl:out>,
			page : <jstl:out value="${p}"></jstl:out>,
			maxVisible : 4,
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
