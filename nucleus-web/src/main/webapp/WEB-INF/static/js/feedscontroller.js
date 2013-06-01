function FeedsController($scope, $http, $interpolate) {

    /**
     * Feed pagination configuration
     */
    $scope.config = {
        feedId: $scope.feedId,
        basePath: $scope.basePath,
        totalPages: $scope.totalEntryPages,
        page: 0,

        hasMorePages: function () {
            return this.page < this.totalPages;
        }
    };

    /**
     * Feed loading-in-progress flag.
     *
     * @type {boolean}
     */
    $scope.loadingEntries = false;

    /**
     * Feed entries.
     * Loaded dynamically by loadMoreEntries method.
     *
     * @type {Array}
     */
    $scope.entries = [];

    /**
     * Loads next page of feed entries (if available)
     * Increments current page by one.
     */
    $scope.loadMoreEntries = function () {
        console.log("[FeedsController][loadMoreEntries] - begin - $scope.config: %o", $scope.config);

        if ($scope.config.hasMorePages()) {
            $scope.loadingEntries = true;

            var url = $interpolate("{{basePath}}/{{feedId}}/entries/?page={{page}}")($scope.config);
            console.log("[FeedsController][loadMoreEntries] - url:  %s", url);

            $http.get(url).success(function (data) {
                $scope.entries = $scope.entries.concat(data);
                $scope.config.page += 1;
                $scope.loadingEntries = false;
            });

        } else {
            console.log("[FeedsController][loadMoreEntries] - last page reached - nothing to load");
        }

        console.log("[FeedsController][loadMoreEntries] - end");
    };

    $scope.processEntryClick = function (entry) {
        this.toggleEntryDescription(entry);
        this.markEntryAsRead(entry);
    };

    $scope.toggleEntryDescription = function (entry) {
        $("#" + entry.id).find('.feed-entry-long').toggle();
    };

    $scope.markEntryAsRead = function (entry) {
        if (!entry.read) {
            $http.get($scope.basePath + '/mark-as-read/' + entry.id).success(function () {
                $("#" + entry.id).find('.feed-entry-unread').removeClass().addClass('feed-entry-read');
                entry.read = true;
            });
        }
    };

    /**
     * Refreshes the feed.
     * We add all new entries into the front the list and update total pages number if changed.
     */
    $scope.refreshFeed = function () {
        $scope.loadingEntries = true;
        $http.get($interpolate("{{basePath}}/{{feedId}}/refresh")($scope.config)).success(function (data) {
            $scope.config.totalPages = data.totalEntryPages;
            $scope.entries = data.newFeedEntries.concat($scope.entries);
            $scope.loadingEntries = false;
        });
    };

    $scope.fitScreen = function () {
        Nucleus.fitScreen('#content', 20);
    };

    $scope.ready = function () {
        // Controls feed entries list height so it always fits the screen.
        $(window).resize(function () {
            $scope.fitScreen();
        });

        // Tracks scroll events and load more elements if necessary
        var contentDiv = $('#content');
        $(contentDiv).scroll(function () {
            if (Nucleus.isScrollBottom(contentDiv)) {
                // loadMoreEntires is called through the $apply binding because we are currently in jquery context, not
                // AngularJS.
                $scope.$apply($scope.loadMoreEntries);
            }
        });
    };

    $scope.ready();
    $scope.fitScreen();
    $scope.loadMoreEntries();
}