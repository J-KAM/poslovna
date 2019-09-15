/**
 * Created by Jasmina on 06/06/2017.
 */
app.controller('DocumentUnitFormController', function ($scope, $http, $state, $mdDialog, documentUnitService, documentService, wareService, documentUnit, document) {

        var editingMode = documentUnit !== null;
        if(editingMode){
           $scope.documentUnit = JSON.parse(JSON.stringify(documentUnit));
        }

        wareService.read(function (response) {
            $scope.wares = response.data;
        });

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

        $scope.submit = function () {
            if(!editingMode){
                $scope.documentUnit.document = document;
                $scope.documentUnit.value = $scope.documentUnit.price * $scope.documentUnit.quantity;
                documentUnitService.create($scope.documentUnit, function () {
                    $scope.close();
                });
            } else {
                $scope.documentUnit.value = $scope.documentUnit.price * $scope.documentUnit.quantity;
                documentUnitService.update($scope.documentUnit, function () {
                    $scope.close();
                });
            }
        };

        $scope.close = function () {
            $mdDialog.hide();
        };
});