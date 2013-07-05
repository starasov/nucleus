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
    <link href="${resources}/css/nucleus.css" rel="stylesheet">

    <style type="text/css">
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }

        .form-signin {
            /*max-width: 380px;*/
            width: 50%;
            padding: 19px 29px 29px;
            margin: 0 auto 20px;
            background-color: #fff;
            border: 1px solid #e5e5e5;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            -moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
        }

        .form-signin .form-signin-heading {
            margin-bottom: 10px;
        }

        .form-signin header {
            margin-bottom: 20px;
            border-bottom: 1px solid #cccccc;
        }

        .form-signin input[type="text"],
        .form-signin input[type="password"] {
            font-size: 16px;
            height: auto;
            margin-bottom: 15px;
            padding: 7px 9px;
        }
    </style>

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
            <h2 class="form-signin-heading">Sign in with</h2>
        </header>

        <div style="width: 100%; text-align: center">
            <button class="btn btn-large" type="submit">
                <img src="${resources}/img/google.svg" style="width: 24px; height: 24px"/> Google
            </button>
            <button class="btn btn-large disabled"><i class="icon-user icon-black"></i> Facebook</button>
            <button class="btn btn-large disabled"><i class="icon-user icon-black"></i> Twitter</button>
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