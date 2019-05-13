/**
 * Created by JELENA on 4.6.2017.
 */

app.controller('WarehouseFormController', function ($scope, $http, $state, $mdDialog, warehouseService, userService, companyService, warehouse) {

    var editingMode = warehouse !== null;
    if (editingMode) {
        $scope.warehouse = JSON.parse(JSON.stringify(warehouse));
    }


    userService.read(function (response) {
        $scope.employees = response.data;
    });

    $scope.queryEmployees = function (searchText) {
        if (searchText === null) {
            return $scope.employees;
        }
        var queryResults = [];
        for (var i = 0; i < $scope.employees.length; i++) {
            var name = $scope.employees[i].firstName + " " + $scope.employees[i].lastName;
            if (name.toLowerCase().match(searchText.toLowerCase())) {
                queryResults.push($scope.employees[i]);
            }
        }
        return queryResults;
    };

    companyService.read(function (response) {
        $scope.companies = response.data;
    });

    $scope.queryCompanies = function (searchText) {
        if (searchText === null) {
            return $scope.companies;
        }
        var queryResults = [];
        for (var i = 0; i < $scope.companies.length; i++) {
            if ($scope.companies[i].name.toLowerCase().match(searchText.toLowerCase())
                || $scope.companies[i].pib === parseInt(searchText)){
                queryResults.push($scope.companies[i]);
            }
        }
        return queryResults;
    };

    $scope.submit = function () {
        if (!editingMode) {
            warehouseService.create($scope.warehouse, function () {
                $scope.close();
            });
        } else {
            warehouseService.update($scope.warehouse, function () {
                $scope.close();
            });
        }
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});
