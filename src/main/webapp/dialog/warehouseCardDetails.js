app.controller('WarehouseCardDetailsController', function ($scope, $http, $state, $mdDialog, warehouseCard) {

    $scope.card = warehouseCard;

    $scope.close = function () {
        $mdDialog.hide();
    }
});
