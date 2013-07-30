function OutlineController($scope, $http, $interpolate) {
    $scope.outlineTemplate = $interpolate("{{resources}}/templates/outline_template.html")($scope);

    $http.get($interpolate("{{basePath}}/outline")($scope)).success(function (data) {
        $scope.outline = data;
    });

    $scope.feedUrl = function (feedId) {
        return $interpolate("{{basePath}}/" + feedId)($scope);
    };

    $scope.fitScreen = function () {
        Nucleus.fitScreen('#outline', 2);
    };

    $scope.elementStyle = function (outline) {
        return outline.id == $scope.feedId ? "outline-active" : "outline-header";
    };

    $scope.processOutlineClick = function (outline) {
        outline.hidden = !outline.hidden;
    };

    $(window).resize(function () {
        $scope.fitScreen();
    });

    $scope.fitScreen();
}