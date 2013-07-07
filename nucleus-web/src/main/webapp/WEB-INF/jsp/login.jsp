<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<spring:url value="/auth" var="basePath" htmlEscape="true"/>
<spring:url value="/static" var="resources" htmlEscape="true"/>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">

    <title>Nucleus - Login</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="${resources}/css/bootstrap.css" rel="stylesheet">
    <link href="${resources}/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="${resources}/css/font-awesome.css" rel="stylesheet">
    <link href="${resources}/css/login.css" rel="stylesheet">

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144"
          href="http://twitter.github.io/bootstrap/assets/ico/apple-touch-icon-144-precomposed.png">

    <link rel="apple-touch-icon-precomposed" sizes="114x114"
          href="http://twitter.github.io/bootstrap/assets/ico/apple-touch-icon-114-precomposed.png">

    <link rel="apple-touch-icon-precomposed" sizes="72x72"
          href="http://twitter.github.io/bootstrap/assets/ico/apple-touch-icon-72-precomposed.png">

    <link rel="apple-touch-icon-precomposed"
          href="http://twitter.github.io/bootstrap/assets/ico/apple-touch-icon-57-precomposed.png">

    <link rel="shortcut icon" href="http://twitter.github.io/bootstrap/assets/ico/favicon.png">
</head>
<body>

<div class="container">
    <form class="form-signin" action="${basePath}/google">
        <header>
            <c:if test="${authenticationFailed}">
                <div class="alert alert-error">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <strong>Oops!</strong> ${exception.message}
                </div>
            </c:if>

            <h2 class="form-signin-heading">Sign in with</h2>
        </header>

        <div class="auth-provider-container">
            <button class="btn btn-large" type="submit">
                <img src="${resources}/img/google.svg" class="auth-provider-icon"/> Google
            </button>

            <button class="btn btn-large disabled">
                <img src="${resources}/img/facebook.svg" class="auth-provider-icon"/> Facebook
            </button>

            <button class="btn btn-large disabled">
                <img src="${resources}/img/twitter.svg" class="auth-provider-icon"/> Twitter
            </button>
        </div>
    </form>
</div>
<!-- /container -->

<!-- JavaScript Placed at the end of the document so the pages load faster -->
<script src="${resources}/js/jquery.js"></script>
<script src="${resources}/js/bootstrap.js"></script>
<script src="${resources}/js/nucleus.js"></script>

</body>
</html>