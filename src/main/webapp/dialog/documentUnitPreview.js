/**
 * Created by Jasmina on 05/06/2017.
 */
app.controller('DocumentUnitPreviewController', function ($scope, $http, $state, $mdDialog, documentUnitService, document, wareService){

    $scope.selectedIndex = 0;
    $scope.secondLocked = true;
    $scope.saveDisabled = true;


    documentUnitService.read(document.id, function (response) {
        $scope.documentUnits = response.data;
    });

    wareService.read(function (response) {
        $scope.wares = response.data;
    });

    $scope.disableSave = function () {
        $scope.saveDisabled = true;
    }


    $scope.editDocumentUnit = function(documentUnit){
        if(documentUnit.document.status !== "PENDING"){
            $scope.selectedIndex = 0;
            $scope.secondLocked = false;
            $scope.notPending = true;
            $scope.pending = false;
        }else{
            $scope.selectedIndex = 1;
            $scope.secondLocked = false;
            $scope.saveDisabled = false;
            $scope.notPending = false;
            $scope.pending = true;
            $scope.documentUnit = JSON.parse(JSON.stringify(documentUnit));
        }
    }
    

    $scope.positiveInteger = (function () {
        var regexp = /^[1-9]\d*$/;
        return {
            test: function(value) {
                return regexp.test(value);
            }
        };
    })();

    $scope.queryWares = function (searchText) {
        if(searchText === null){
            return $scope.wares;
        }
        var queryResults = [];
        for(var i=0; i<$scope.wares.length; i++){
            if($scope.wares[i].name.toLowerCase().match(searchText.toLowerCase())
                || $scope.wares[i].id === parseInt(searchText)){
                queryResults.push($scope.wares[i]);
            }
        }
        return queryResults;
    };

    $scope.saveUnit = function () {
        $scope.documentUnit.value = $scope.documentUnit.price * $scope.documentUnit.quantity;
        documentUnitService.update($scope.documentUnit, function () {
            $scope.close();
        });
    };

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