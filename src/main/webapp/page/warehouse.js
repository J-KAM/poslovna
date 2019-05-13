/**
 * Created by JELENA on 4.6.2017.
 */

app.controller('WarehouseController', function($scope, $state, $rootScope, $mdDialog, warehouseService, authenticationService){

    $scope.page.current = 3.8;
    $scope.authService = authenticationService;

    var warehouses = [];

    var loadData = function () {
        warehouseService.read(function (response) {
            $scope.warehouses = response.data;
            warehouses = response.data;
        });
    };

    loadData();

    $scope.$on('search', function(event, args) {
        var searchText = args.searchText;
        if (searchText === null || searchText === "") {
            $scope.warehouses = warehouses;
            return;
        }
        var criteria = searchText.match(/\S+/g);
        $scope.warehouses = [];
        for (var i = 0; i < warehouses.length; i++) {
            for (var j = 0; j < criteria.length; j++) {
                if (warehouses[i].id === Number(criteria) ||
                    warehouses[i].name.toLowerCase().match(criteria[j].toLowerCase()) ||
                    warehouses[i].employee.firstName.toLowerCase().match(criteria[j].toLowerCase()) ||
                    warehouses[i].employee.lastName.toLowerCase().match(criteria[j].toLowerCase()) ||
                    warehouses[i].company.name.toLowerCase().match(criteria[j].toLowerCase())) {
                    if($scope.warehouses.indexOf(warehouses[i]) === -1) {
                        $scope.warehouses.push(warehouses[i]);
                    }
                }
            }
        }
    });

    var openForm = function (warehouse) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/warehouseForm.html',
            controller: 'WarehouseFormController',
            locals: {warehouse : warehouse}
        }).finally(function () {
            loadData();
        });
    };

    $scope.$on('add', function () {
        openForm(null);
    });

    $scope.edit = function (warehouse) {
        openForm(warehouse);
    };

    $scope.delete = function (warehouse, event) {
        event.stopPropagation();
        warehouseService.delete(warehouse.id, function () {
            loadData();
        });
    };

    $scope.generateReport = function (warehouse, event) {
        event.stopPropagation();
        warehouseService.generateReport(warehouse, function(response) {
            if(response.data !== "") {
                window.open("data:application/pdf;base64, " + response.data);
            } else {
                showFailureInfo();
            }
        });
    };

    var showFailureInfo = function() {
        $mdDialog.show(
            $mdDialog.alert()
                .parent(angular.element(document.body))
                .title('Neuspeh')
                .content('Lager lista nije kreirana.')
                .ok('Ok')
        );
    };

    $scope.options = {
        rowSelection: true,
        boundaryLinks: true
    };

    $scope.query = {
        order: 'name',
        limit: 5,
        page: 1
    };
});
