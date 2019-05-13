/**
 * Created by Olivera on 3.7.2017..
 */
app.controller('WarehouseCardController', function ($scope, $state, $rootScope, $mdDialog, warehouseCardService, warehouseService) {

    $scope.page.current = 0.1;

    var cards = [];

    var loadData = function () {
        if(warehouseService.getActiveWarehouse().id === -1) {
            warehouseCardService.readAll(function (response) {
                $scope.cards = response.data;
                cards = response.data;
            });
        } else {
            warehouseCardService.read(warehouseService.getActiveWarehouse(), function (response) {
                $scope.cards = response.data;
                cards = response.data;
            });
        }
    };

    loadData();

    $scope.$on('search', function(event, args) {
        var searchText = args.searchText;
        if(searchText === null || searchText === "") {
            $scope.cards = cards;
            return;
        }
        var criteria = searchText.match(/\S+/g);
        $scope.cards = [];
        for(var i = 0; i < cards.length; i++) {
            for(var j = 0; j < criteria.length; j++) {
                //TODO: Check all card properties if necessary (not only visible ones)
                if (cards[i].id === Number(criteria) ||
                    cards[i].ware.name.toLowerCase().match(criteria[j].toLowerCase()) ||
                    String(cards[i].averagePrice).toLowerCase().match(criteria[j].toLowerCase()) ||
                    String(cards[i].initialQuantity).toLowerCase().match(criteria[j].toLowerCase()) ||
                    String(cards[i].initialValue).toLowerCase().match(criteria[j].toLowerCase()) ||
                    String(cards[i].totalQuantity).toLowerCase().match(criteria[j].toLowerCase()) ||
                    String(cards[i].totalValue).toLowerCase().match(criteria[j].toLowerCase())) {
                    if ($scope.cards.indexOf(cards[i]) === -1) {
                        $scope.cards.push(cards[i]);
                    }
                }
            }
        }
    });

    $scope.showDetails = function(warehouseCard) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/warehouseCardDetails.html',
            controller: 'WarehouseCardDetailsController',
            locals: { warehouseCard: warehouseCard}
        });
    };

    $scope.showAnalytics = function (warehouseCard) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/warehouseCardAnalyticsPreview.html',
            controller: 'WarehouseCardAnalyticsPreviewController',
            locals: { warehouseCard: warehouseCard}
        });
    };

    $scope.$on('warehouseChanged', function() {
        loadData();
    });

    $scope.chooseDate = function (warehouseCard) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/chooseDateForm.html',
            controller: 'ChooseDateController',
            locals: { warehouseCard: warehouseCard}
        });
    };

    $scope.level = function (warehouseCard) {
        warehouseCardService.level(warehouseCard, function () {
            loadData();
        });
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
