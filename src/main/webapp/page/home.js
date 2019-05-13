app.controller('HomeController', function ($scope, $state, $location, $log, $rootScope, $mdSidenav, $mdDialog, $interval, authenticationService) {

    $scope.page.current = 0;

    $scope.authService = authenticationService;

    $scope.goToDocuments = function () {
        $state.transitionTo('navigation.document');
    };

    $scope.goToBusinessYears = function () {
        $state.transitionTo('navigation.businessYear');
    };

    $scope.goToWares = function () {
        $state.transitionTo('navigation.ware');
    };

    $scope.goToWarehouseCards = function () {
        $state.transitionTo('navigation.warehouseCard');
    };
});
