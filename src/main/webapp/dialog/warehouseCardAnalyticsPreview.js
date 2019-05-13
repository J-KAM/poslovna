/**
 * Created by JELENA on 3.7.2017.
 */

app.controller('WarehouseCardAnalyticsPreviewController', function ($scope, $http, $state, $mdDialog, warehouseCardAnalyticsService, warehouseCard){

    $scope.trafficType = {};
    $scope.trafficType["RECEIPT"] = "PR";
    $scope.trafficType["DISPATCH"] = "OT";
    $scope.trafficType["INTER_WAREHOUSE_TRAFFIC"] = "MM";
    $scope.trafficType["LEVELING"] = "NI";
    $scope.trafficType["INITIAL_STATE"] = "PS";
    $scope.trafficType["CORRECTION"] = "KOR";

    $scope.direction = {};
    $scope.direction["INCOMING"] = "U";
    $scope.direction["OUTGOING"] = "I";

    warehouseCardAnalyticsService.read(warehouseCard.id, function (response) {
        $scope.warehouseCardAnalytics = response.data;
    });

    $scope.close = function () {
        $mdDialog.hide();
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
