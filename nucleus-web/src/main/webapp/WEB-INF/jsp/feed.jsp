<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<spring:url value="/feed" var="basePath" htmlEscape="true"/>
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
    <link href="${resources}/css/font-awesome.css" rel="stylesheet">
    <link href="${resources}/css/nucleus.css" rel="stylesheet">

    <!-- AngularJS components should be placed under head element, otherwise the framework won't initialize correctly. -->
    <script src="${resources}/js/angular.js"></script>
    <script src="${resources}/js/feedscontroller.js"></script>
    <script src="${resources}/js/outlinecontroller.js"></script>

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

            <a class="brand" href="#">Nucleus</a>

            <div class="nav-collapse collapse">
                <ul class="nav pull-right">
                    <li><a href="#" ng-click="refreshFeed()"><i class="icon-repeat"></i> Refresh</a></li>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="icon-ok"></i> Mark As Read <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="#">Older Than 1 Day</a></li>
                            <li><a href="#">Older Than 1 Week</a></li>
                            <li><a href="#">Older Than 2 Weeks</a></li>
                            <li><a href="#">Older Than 1 Month</a></li>
                            <li class="divider"></li>
                            <li><a href="#">All</a></li>
                        </ul>
                    </li>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="icon-cog"></i> Settings <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="/import/">OMPL Import</a></li>
                            <li class="divider"></li>
                            <li><a href="/auth/logout/">Logout</a></li>
                        </ul>
                    </li>
                </ul>

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

<!-- Feeds container -->
<div class="container-fluid" style="margin: 0; padding: 0">
    <div class="alert" style="z-index: 9999; position: absolute; margin-top: -20px; margin-left: 45%;"
         ng-show="loadingEntries">
        <strong>Loading...</strong>
    </div>

    <div class="row-fluid">
        <!-- Sidebar with feeds tree -->
        <div class="span2" style="width: 15%; margin: 0; padding: 0;">
            <div id="outline" class="well sidebar-nav scrollable outline-tree">
                <ul class="nav nav-list" ng-controller="OutlineController">
                    <li ng-repeat="c in outline.children | orderBy:'ordinal'" ng-include="outlineTemplate"></li>
                </ul>
            </div>
        </div>

        <!-- Feed entries list -->
        <div class="span10" style="width: 85%; margin: 0; padding: 0;">
            <div id="content" class="row-fluid scrollable">
                <!-- Empty list message -->
                <div class="span6 offset2 hero-unit" ng-hide="entries.length">
                    <h4 class="brand empty-feed-message">
                        Ooops... We could not find anything interesting for you here.
                    </h4>
                </div>

                <div ng-repeat="entry in entries">
                    <div id="{{entry.id}}" ng-click="processEntryClick(entry)">
                        <div class="feed-entry-short">
                            <!-- For unread entries 'feed-entry-unread' class is applied -->
                            <span ng-class="{'feed-entry-unread': !entry.read}">{{entry.title}}</span>
                        </div>

                        <!--
                            Feed entry description contains 'unsafe' contents, but they are sanitized on server-side,
                            so we can assume that it's safe to show the contents as is.
                        -->
                        <div class="feed-entry-long" ng-bind-html-unsafe="entry.fullDescription"
                             ng-show="entry.fullDescriptionVisible"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

<!-- JavaScript Placed at the end of the document so the pages load faster -->
<script src="${resources}/js/jquery.js"></script>
<script src="${resources}/js/bootstrap.js"></script>
<script src="${resources}/js/nucleus.js"></script>

</body>
</html>