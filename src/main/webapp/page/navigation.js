app.controller('NavigationController', function ($scope, $state, $location, $log, $rootScope, $mdSidenav, $mdDialog, authenticationService, warehouseService) {

    $scope.page = {
        title: 'Magacin',
        current: -1
    };

    $scope.user = authenticationService.getUser();
    $scope.authService = authenticationService;

    $scope.logout = function () {
        authenticationService.logout(function () {
            $state.transitionTo('login');
        }, function () {

        });
    };

    $scope.registerEmployee = function () {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createAccount.html',
            controller: 'CreateAccountController'
        }).finally(function() {
        });
    };

    warehouseService.read(function (response) {
       $scope.warehouses = response.data;
       $scope.warehouses.unshift({ name: "Svi magacini", id: -1 });
       if (warehouseService.getActiveWarehouse() !== null) {
           this.activeWarehouse = warehouseService.getActiveWarehouse();
       } else {
           this.activeWarehouse = $scope.warehouses[0];
       }
       $scope.activeWarehouseChanged();
    });

    $scope.activeWarehouseChanged = function () {
        warehouseService.setActiveWarehouse(this.activeWarehouse);
        $scope.$broadcast('warehouseChanged');
    };

    $scope.toggleSidenav = buildToggler('left');

    function buildToggler(navID) {
        return function () {
            $mdSidenav(navID).toggle();
        }
    }

    $scope.isActive = function (pageIndex) {
        return $scope.page.current === pageIndex;
    };

    $scope.add = function () {
        $scope.$broadcast('add');
        $scope.showSearch = false;
        $scope.emitSearchQuery(null);
    };

    $scope.emitSearchQuery = function (searchText) {
        $scope.$broadcast('search', {searchText: searchText});
    };

    $scope.goToHome = function () {
        $state.transitionTo('navigation.home');
        $mdSidenav('left').close();
    };

    $scope.goToMeasurementUnits = function () {
        $state.transitionTo('navigation.measurementUnit');
        $mdSidenav('left').close();
    };

    $scope.goToCompanies = function () {
        $state.transitionTo('navigation.company');
        $mdSidenav('left').close();
    };

    $scope.goToLocation = function () {
        $state.transitionTo('navigation.location');
        $mdSidenav('left').close();
    };

    $scope.goToWareGroups = function () {
        $state.transitionTo('navigation.wareGroup');
        $mdSidenav('left').close();
    };

    $scope.goToWares = function () {
        $state.transitionTo('navigation.ware');
        $mdSidenav('left').close();
    };

    $scope.goToBusinessPartners = function () {
        $state.transitionTo('navigation.businessPartner');
        $mdSidenav('left').close();
    };

    $scope.goToWarehouses = function () {
        $state.transitionTo('navigation.warehouse');
        $mdSidenav('left').close();
    };


});