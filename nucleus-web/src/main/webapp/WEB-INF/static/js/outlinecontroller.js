function OutlineController($scope, $http, $interpolate) {
    $http.get($interpolate("{{basePath}}/outline")($scope)).success(function (data) {
        $scope.outline = data;
        $scope.loadingEntries = false;
    });
}