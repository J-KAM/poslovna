/**
 * Created by JELENA on 3.6.2017.
 */

app.controller('WareController', function ($scope, $state, $rootScope, $mdDialog, wareService, warehouseCardService) {

    $scope.page.current = 3.5;

    var wares = [];

    var loadData = function() {
        wareService.read(function (response) {
           $scope.wares = response.data;
           wares = response.data;
        });
    };

    loadData();

    $scope.$on('search', function(event, args) {
        var searchText = args.searchText;
        if (searchText === null || searchText === "") {
            $scope.wares = wares;
            return;
        }
        var criteria = searchText.match(/\S+/g);
        $scope.wares = [];
        for (var i = 0; i < wares.length; i++) {
            for (var j = 0; j < criteria.length; j++) {
                if (wares[i].id === Number(criteria) ||
                    wares[i].name.toLowerCase().match(criteria[j].toLowerCase()) ||
                    String(wares[i].packing).toLowerCase().match(criteria[j].toLowerCase()) ||
                    wares[i].measurementUnit.name.toLowerCase().match(criteria[j].toLowerCase()) ||
                    wares[i].wareGroup.name.toLowerCase().match(criteria[j].toLowerCase())) {
                    if($scope.wares.indexOf(wares[i]) === -1) {
                        $scope.wares.push(wares[i]);
                    }
                }
            }
        }
    });

    var openForm = function (ware) {
        $mdDialog.show( {
            parent: angular.element(document.body),
            templateUrl: 'dialog/wareForm.html',
            controller: 'WareFormController',
            locals: { ware: ware}
        }).finally(function () {
           loadData();
        });
    };

    $scope.$on('add', function() {
        openForm(null);
    });

    $scope.edit = function (ware) {
        openForm(ware);
    };
    
    $scope.showCardDetails = function (ware, event) {
        event.stopPropagation();
        warehouseCardService.getCardForWare(ware, function (response) {
            if(response.data === ""){
                $mdDialog.show(
                    $mdDialog.alert()
                        .parent(angular.element(document.body))
                        .clickOutsideToClose(true)
                        .title('Roba u magacinu')
                        .textContent('Roba nema magacinsku karticu u trenutno aktivnoj poslovnoj godini!')
                        .ariaLabel('Alert knjizenje')
                        .ok('OK')
                        .targetEvent(event)
                );
                return;
            }
            $mdDialog.show({
                parent: angular.element(document.body),
                templateUrl: 'dialog/warehouseCardDetails.html',
                controller: 'WarehouseCardDetailsController',
                locals: { warehouseCard: response.data}
            });
        });
    };

    $scope.delete = function (ware, event) {
        event.stopPropagation();
        warehouseCardService.getCardForWare(ware, function (response) {
            if (response.data !== "") {
                $mdDialog.show(
                    $mdDialog.alert()
                        .parent(angular.element(document.body))
                        .clickOutsideToClose(true)
                        .title('Roba u magacinu')
                        .textContent('Brisanje nije dozvoljeno. Roba ima magacinsku karticu u trenutno aktivnoj poslovnoj godini!')
                        .ariaLabel('Alert knjizenje')
                        .ok('OK')
                        .targetEvent(event)
                );
                return;
            }else {
                wareService.delete(ware.id, function() {
                    loadData();
                });
            }
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