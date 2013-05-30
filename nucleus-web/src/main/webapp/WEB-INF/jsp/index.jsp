<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<spring:url value="/feed" var="basePath" htmlEscape="true"/>
<spring:url value="/static" var="resources" htmlEscape="true"/>

<!DOCTYPE html>
<html lang="en" ng-app>

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

<body ng-init="feedId='${feed.id}'; basePath='${basePath}'; totalEntryPages=${totalEntryPages}">

<!-- Top navigation bar -->
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid">
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <a class="brand" href="#">Nucleus</a>

            <div class="nav-collapse collapse">
                <p class="navbar-text pull-right">
                    Logged in as <a href="#" class="navbar-link">Username</a>
                </p>
                <ul class="nav">
                    <li class="active"><a href="#">Feeds</a></li>
                    <li><a href="#">Digest</a></li>
                    <li><a href="#about">About</a></li>
                    <li><a href="#contact">Contact</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<!-- Feeds container -->
<div class="container-fluid">
    <div class="row-fluid">

        <!-- Sidebar with feeds tree -->
        <div class="span2">
            <div class="well sidebar-nav">
                <ul class="nav nav-list">
                    <li class="nav-header">Sidebar</li>
                    <li class="active"><a href="#">Link</a></li>
                    <li><a href="#">Link</a></li>
                    <li><a href="#">Link</a></li>
                    <li><a href="#">Link</a></li>
                    <li class="nav-header">Sidebar</li>
                    <li><a href="#">Link</a></li>
                    <li><a href="#">Link</a></li>
                    <li><a href="#">Link</a></li>
                    <li><a href="#">Link</a></li>
                    <li><a href="#">Link</a></li>
                    <li><a href="#">Link</a></li>
                    <li class="nav-header">Sidebar</li>
                    <li><a href="#">Link</a></li>
                    <li><a href="#">Link</a></li>
                    <li><a href="#">Link</a></li>
                </ul>
            </div>
        </div>

        <div class="span10" ng-controller="FeedsController">
            <!-- Feed details and control bar -->
            <div class="row-fluid navbar-entries-row">
                <div class="navbar">
                    <div class="navbar-inner navbar-entries">
                        <div class="container-fluid">
                            <ul class="nav pull-left">
                                <li><a class="brand" target="_blank" href="${feed.htmlUrl}">${feed.title}</a></li>
                                <!-- Loading spinner for entries loading operation -->
                                <li><i class="brand icon-spinner icon-spin icon-large" ng-show="loadingEntries"></i>
                                </li>
                            </ul>

                            <ul class="nav pull-right">
                                <li><a href="#" ng-click="refreshFeed()"><i class="icon-repeat"></i> Refresh</a></li>

                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                        <i class="icon-ok"></i> Mark As Read <b class="caret"></b>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a href="#">Action1</a></li>
                                        <li><a href="#">Another action</a></li>
                                        <li><a href="#">Something else here</a></li>
                                        <li class="divider"></li>
                                        <li><a href="#">Separated link</a></li>
                                    </ul>
                                </li>

                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                        <i class="icon-cog"></i> Settings <b class="caret"></b>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a href="#">Action</a></li>
                                        <li><a href="#">Another action</a></li>
                                        <li><a href="#">Something else here</a></li>
                                        <li class="divider"></li>
                                        <li><a href="#">Separated link</a></li>
                                    </ul>
                                </li>

                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Our pretty shadow goes here -->
            <div class="span10 affix shadow-top" style="margin-left: -20px;"></div>

            <!-- Empty list message -->
            <div class="span6 offset2 hero-unit" ng-hide="entries.length">
                <h4 class="brand empty-feed-message">
                    Ooops... We could not find anything interesting for you here.
                </h4>
            </div>

            <!-- Feed entries list -->
            <div id="content" class="row-fluid scrollable">
                <div ng-repeat="entry in entries">
                    <div id="{{entry.id}}" class="feed-entry" ng-click="processEntryClick(entry)">
                        <div class="feed-entry-short" style="">
                            <!-- For unread entries 'feed-entry-unread' class is applied -->
                            <span ng-class="{'feed-entry-unread': !entry.read}">{{entry.title}}</span>
                        </div>

                        <!--
                            Feed entry description contains 'unsafe' contents, but they are sanitized on server-side,
                            so we can assume that it's safe to show the contents as is.
                        -->
                        <div class="feed-entry-long" ng-bind-html-unsafe="entry.fullDescription"></div>
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