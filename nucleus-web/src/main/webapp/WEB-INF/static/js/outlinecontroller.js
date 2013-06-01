function OutlineController($scope, $http, $interpolate) {
    $scope.outlineTemplate = $interpolate("{{resources}}/templates/outline_template.html")($scope);

    $http.get($interpolate("{{basePath}}/outline")($scope)).success(function (data) {
        $scope.outline = data;
        $scope.loadingEntries = false;
    });

    $scope.feedUrl = function (feedId) {
        return $interpolate("{{basePath}}/" + feedId)($scope);
    };

    $scope.fitScreen = function () {
        Nucleus.fitScreen('#outline', 60);
    };

    $(window).resize(function () {
        $scope.fitScreen();
    });

    $scope.fitScreen();
}