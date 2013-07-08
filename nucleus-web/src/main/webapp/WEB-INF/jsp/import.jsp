<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<spring:url value="/import" var="basePath" htmlEscape="true"/>
<spring:url value="/static" var="resources" htmlEscape="true"/>

<!DOCTYPE html>
<html lang="en" ng-app
      ng-init="feedId='${feed.id}'; basePath='${basePath}'; totalEntryPages=${totalEntryPages}; resources='${resources}';">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">

    <title>Nucleus</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="${resources}/css/bootstrap.css" rel="stylesheet">
    <link href="${resources}/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="${resources}/css/bootstrap-fileupload.css" rel="stylesheet">
    <link href="${resources}/css/font-awesome.css" rel="stylesheet">
    <link href="${resources}/css/nucleus.css" rel="stylesheet">
    <link href="${resources}/css/login.css" rel="stylesheet">
    <link href="${resources}/css/import.css" rel="stylesheet">

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

<body ng-controller="FeedsController">

<!-- Top navigation bar -->
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid">

            <a class="brand" href="#">Nucleus - Import</a>

            <div class="nav-collapse collapse">
                <ul class="nav">
                    <li class="active"><a href="/">Feeds</a></li>
                    <li><a href="#about">About</a></li>
                    <li><a href="#contact">Contact</a></li>
                </ul>
            </div>
        </div>
    </div>

    <div class="affix shadow-top"></div>
</div>

<div class="container">

    <form class="form-signin" action="${basePath}/ompl" method="post" enctype="multipart/form-data">
        <header>
            <h1>Import Your Subscriptions</h1>

            <c:if test="${importFailed}">
                <div class="alert alert-error">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <strong>Oops!</strong> ${errorMessage}
                </div>
            </c:if>
        </header>

        <div class="fileupload fileupload-new" data-provides="fileupload">
            <div class="input-append">
                <div class="uneditable-input span3"><i class="icon-file fileupload-exists"></i>
                    <span class="fileupload-preview"></span>
                </div>
                    <span class="btn btn-file"><span class="fileupload-new">Select file</span>
                        <span class="fileupload-exists">Change</span><input type="file" name="file"/>
                    </span>
                <a href="#" class="btn fileupload-exists" data-dismiss="fileupload">Remove</a>
            </div>
        </div>

        <p>
            <button class="btn btn-primary btn-large" type="submit">Import &raquo;</button>
        </p>
    </form>
</div>

<!-- JavaScript Placed at the end of the document so the pages load faster -->
<script src="${resources}/js/jquery.js"></script>
<script src="${resources}/js/bootstrap.js"></script>
<script src="${resources}/js/bootstrap-fileupload.js"></script>
<script src="${resources}/js/nucleus.js"></script>

</body>
</html>